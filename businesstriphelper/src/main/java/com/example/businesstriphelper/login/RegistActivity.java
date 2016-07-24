package com.example.businesstriphelper.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.businesstriphelper.R;

/**
 * Created by Administrator on 2016/7/5.
 */
public class RegistActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_name, et_pass1,et_pass2;
    private Button mRegistButton;

    private Button bt_username_clear;
    private Button bt_pwd_clear1,bt_pwd_clear2;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher1,password_watcher2;
    //是否显示注册信息核对框
    private boolean isInfoChecked = false;
    private String userNameValue,passwordValue1,passwordValue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_regiset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");

        et_name = (EditText) findViewById(R.id.username_regist);
        et_pass1 = (EditText) findViewById(R.id.password_regist_1);
        et_pass2 = (EditText) findViewById(R.id.password_regist_2);

        bt_username_clear = (Button) findViewById(R.id.bt_username_clear_regist);
        bt_pwd_clear1 = (Button) findViewById(R.id.bt_pwd_clear_regist_1);
        bt_pwd_clear2 = (Button) findViewById(R.id.bt_pwd_clear_regist_2);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear1.setOnClickListener(this);
        bt_pwd_clear2.setOnClickListener(this);

        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass1.addTextChangedListener(password_watcher1);
        et_pass2.addTextChangedListener(password_watcher2);

        mRegistButton = (Button) findViewById(R.id.regist);
        mRegistButton.setOnClickListener(this);
/*        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View main_view) {
                if (isInfoChecked == false){
                    isInfoChecked = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder().
                            setTitle("注册信息").
                            setIcon(R.drawable.login_illegal1).
                            setMessage("账号： "+et_name.getText().toString()+"\n密码： "
                                    +et_pass2.getText().toString()
                                    +"\n请再次点击“立即注册").
                            setPositiveButton("确定",null);
                    builder.create().show();
                }
            }
        };*/

    }

    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                isInfoChecked = false;

                if(s.toString().length()>0){
                    bt_username_clear.setVisibility(View.VISIBLE);
                }else{
                    bt_username_clear.setVisibility(View.INVISIBLE);
                }
            }
        };

        password_watcher1 = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                isInfoChecked = false;
                et_pass2.setText("");
                if(s.toString().length()>0){
                    bt_pwd_clear1.setVisibility(View.VISIBLE);
                }else{
                    bt_pwd_clear1.setVisibility(View.INVISIBLE);
                }
            }
        };
        password_watcher2 = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                isInfoChecked = false;
                if(s.toString().length()>0){
                    bt_pwd_clear2.setVisibility(View.VISIBLE);
                }else{
                    bt_pwd_clear2.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.regist:
                userNameValue = et_name.getText().toString();
                passwordValue1 = et_pass1.getText().toString();
                passwordValue2 = et_pass2.getText().toString();
                //判断信息是否符合
                if (userNameValue.length()==6 ) {
                    if (passwordValue1.length()==6){
                        if (passwordValue1.equals(passwordValue2)){
                            if (isInfoChecked == false) {
                                isInfoChecked = true;
                                AlertDialog.Builder builder = new AlertDialog.Builder(this).
                                    setTitle("注册信息").
                                    setIcon(R.drawable.login_illegal1).
                                    setMessage("账号： " + et_name.getText().toString() + "\n密码： "
                                            + et_pass2.getText().toString()
                                            + "\n请再次点击“立即注册”").
                                    setPositiveButton("确定", null);
                                builder.create().show();
                            }else {
                                //注册,携带注册信息返回
                                Intent intent = getIntent();

                                intent.putExtra("info",userNameValue);
                                RegistActivity.this.setResult(0,intent);
                                RegistActivity.this.finish();
                                //Intent intent_login = new Intent().setClass(RegistActivity.this, MainActivity.class);
                                //startActivity(intent_login);
                            }
                        }else{Toast.makeText(RegistActivity.this, "两次密码不一致！"+"\n"+passwordValue1+"\n"+passwordValue2, Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(RegistActivity.this, "密码格式错误！", Toast.LENGTH_LONG).show();}
                } else{Toast.makeText(RegistActivity.this, "账号格式错误！", Toast.LENGTH_LONG).show();}

                break;


            case R.id.bt_username_clear_regist:
                et_name.setText("");
                break;
            case R.id.bt_pwd_clear_regist_1:
                et_pass1.setText("");
                et_pass2.setText("");
                break;
            case R.id.bt_pwd_clear_regist_2:
                et_pass2.setText("");
                break;

        }
    }

    //标题栏返回按键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //不反回，判断语句的问题
            //android.R 和R的区别：
            //android.R是要获取系统资源时使用的；
            //R当前应用程序中的资源。

/*
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
*/
            Intent intent = getIntent();

            intent.putExtra("info",et_name.getText().toString());
            RegistActivity.this.setResult(0,intent);
            RegistActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //重写，防止按返回键崩溃
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); 	不要调用父类的方法
/*        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);*/
        Intent intent = getIntent();

        intent.putExtra("info",et_name.getText().toString());
        RegistActivity.this.setResult(0,intent);
        RegistActivity.this.finish();
    }



}
