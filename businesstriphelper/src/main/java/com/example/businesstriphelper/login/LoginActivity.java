package com.example.businesstriphelper.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 *
 *  注      册： ValidatePhoneNumActivity  -->  RegisterActivity
 *
 *  忘记密码   ForgetCodeActivity        -->  RepasswordActivity
 *
 *  @author liubao.zeng
 *
 */

import com.example.businesstriphelper.R;
import com.example.businesstriphelper.main.main.MainActivity;

/**
 * Created by Administrator on 2016/7/3.
 */
public class LoginActivity extends Activity implements View.OnClickListener,View.OnLongClickListener {
    // 声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginButton,mLoginError,mRegister,ONLYTEST;

    int selectIndex=1;
    int tempSelect=selectIndex;
    boolean isReLogin=false;
    private int SERVER_FLAG=0;
    private RelativeLayout countryselect;
    private TextView coutry_phone_sn, coutryName;//
    // private String [] coutry_phone_sn_array,coutry_name_array;
    public final static int LOGIN_ENABLE=0x01;    //注册完毕了
    public final static int LOGIN_UNABLE=0x02;    //注册完毕了
    public final static int PASS_ERROR=0x03;      //注册完毕了
    public final static int NAME_ERROR=0x04;      //注册完毕了

    final Handler UiMangerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch(msg.what){
                case LOGIN_ENABLE:
                    mLoginButton.setClickable(true);
//    mLoginButton.setText(R.string.login);
                    break;
                case LOGIN_UNABLE:
                    mLoginButton.setClickable(false);
                    break;
                case PASS_ERROR:

                    break;
                case NAME_ERROR:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Button bt_username_clear;
    private Button bt_pwd_clear;
   // private Button bt_pwd_eye;
    //记录账号、密码
    private SharedPreferences sp_userinfo;

    private CheckBox cb_remenber;
    private CheckBox cb_autologin;
    private String userNameValue,passwordValue;

    private TextWatcher username_watcher;
    private TextWatcher password_watcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//  requestWindowFeature(Window.FEATURE_NO_TITLE);
//  //不显示系统的标题栏
//  getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//    WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView(R.layout.activity_login);
        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);

        bt_username_clear = (Button)findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button)findViewById(R.id.bt_pwd_clear);
        //bt_pwd_eye = (Button)findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        //bt_pwd_eye.setOnClickListener(this);
        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);

        mLoginButton = (Button) findViewById(R.id.login);
        mLoginError  = (Button) findViewById(R.id.login_error);
        mRegister    = (Button) findViewById(R.id.register);
        //ONLYTEST     = (Button) findViewById(R.id.registfer);
        //ONLYTEST.setOnClickListener(this);
        //ONLYTEST.setOnLongClickListener((OnLongClickListener) this);
        mLoginButton.setOnClickListener(this);
        mLoginError.setOnClickListener(this);
        mRegister.setOnClickListener(this);

//  countryselect=(RelativeLayout) findViewById(R.id.countryselect_layout);
//  countryselect.setOnClickListener(this);
//  coutry_phone_sn=(TextView) findViewById(R.id.contry_sn);
//  coutryName=(TextView) findViewById(R.id.country_name);

//  coutryName.setText(coutry_name_array[selectIndex]);    //默认为1
//  coutry_phone_sn.setText("+"+coutry_phone_sn_array[selectIndex]);
        sp_userinfo = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if(sp_userinfo.getBoolean("ISCHECK", false))
        {
            //设置默认是记录密码状态
            //rem_pw.setChecked(true);
            et_name.setText(sp_userinfo.getString("USER_NAME", ""));
            et_pass.setText(sp_userinfo.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if(
                    //sp_userinfo.getBoolean("AUTO_ISCHECK", false)
                //测试，设为false
                false
                    )
            {
                //设置默认是自动登录状态
                //auto_login.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }

        cb_remenber = (CheckBox)findViewById(R.id.checkBox1);
        cb_autologin =(CheckBox)findViewById(R.id.checkBox2);
        CompoundButton.OnCheckedChangeListener listener1 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //选中，保存密码
                    sp_userinfo.edit().putBoolean("ISCHECK", true).commit();
                    Toast.makeText(LoginActivity.this, compoundButton.getText()+"被选择",Toast.LENGTH_SHORT ).show();
                }else{
                    //取消选中，删除保存的密码
                    sp_userinfo.edit().putBoolean("ISCHECK", false).commit();
                    Toast.makeText(LoginActivity.this, compoundButton.getText()+"取消选择",Toast.LENGTH_SHORT ).show();
                }
            }
        };
        cb_remenber.setOnCheckedChangeListener(listener1);
        CompoundButton.OnCheckedChangeListener listener2 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //选中，保存密码
                    sp_userinfo.edit().putBoolean("AUTO_ISCHECK", true).commit();
                    Toast.makeText(LoginActivity.this, compoundButton.getText()+"被选择",Toast.LENGTH_SHORT ).show();
                }else{
                    //取消选中，删除保存的密码
                    sp_userinfo.edit().putBoolean("AUTO_ISCHECK", false).commit();
                    Toast.makeText(LoginActivity.this, compoundButton.getText()+"取消选择",Toast.LENGTH_SHORT ).show();
                }
            }
        };
        cb_autologin.setOnCheckedChangeListener(listener2);
    }
    /**
     * 手机号，密码输入控件公用这一个watcher
     */
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                et_pass.setText("");
                if(s.toString().length()>0){
                    bt_username_clear.setVisibility(View.VISIBLE);
                }else{
                    bt_username_clear.setVisibility(View.INVISIBLE);
                }
            }
        };

        password_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                }else{
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }



    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {

            case R.id.login:  //登陆
                userNameValue = et_name.getText().toString();
                passwordValue = et_pass.getText().toString();
                //判断信息是否符合
                if (userNameValue.toString().length()==6 ) {
                    if (passwordValue.toString().length()==6){
                        if (cb_remenber.isChecked()) {
                            //记住用户名、密码、
                            SharedPreferences.Editor editor = sp_userinfo.edit();
                            editor.putString("USER_NAME", userNameValue);
                            editor.putString("PASSWORD", passwordValue);
                            editor.commit();
                        }
                        Intent intent_login = new Intent().setClass(LoginActivity.this, MainActivity.class);
                        //Intent intent_login = new Intent();
                        //intent_login.putExtra("fragment",0);
                        startActivity(intent_login);
                    }else{Toast.makeText(LoginActivity.this, "密码格式错误！", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(LoginActivity.this, "账号格式错误！", Toast.LENGTH_LONG).show();}
                break;

            case R.id.login_error: //无法登陆(忘记密码了吧)
                AlertDialog.Builder builder = new AlertDialog.Builder(this).
                        setTitle("忘记密码").
                        setIcon(R.drawable.login_illegal1).
                        setMessage("请拨打：  110 \n").
                        setPositiveButton("确定",null);
                builder.create().show();
                break;

            case R.id.register:    //注册新的用户
                Intent intent_regist = new Intent(LoginActivity.this, RegistActivity.class);
                startActivityForResult(intent_regist,0);//请求码为0
                break;

            case R.id.bt_username_clear:
                et_name.setText("");
                et_pass.setText("");
                break;

            case R.id.bt_pwd_clear:
                et_pass.setText("");
                break;
        }
    }

    //重写，回调方式来获取RegistActivity返回的数据

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 0){
            Bundle d = data.getExtras();
            String info = d.getString("info");
            et_name.setText(info);
        }
    }

    /**
     * 登陆
     */
    private void login() {
    }
    @Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            //case R.id.registfer:
              //  if(SERVER_FLAG>9){

               // }
                //   SERVER_FLAG++;
              //  break;
        }
        return true;
    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(isReLogin){
                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                LoginActivity.this.startActivity(mHomeIntent);
            }else{
                LoginActivity.this.finish();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

}