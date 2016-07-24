package com.example.businesstriphelper.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.businesstriphelper.R;
import com.example.businesstriphelper.desc.DescActivity;

/**
 * Created by Administrator on 2016/7/2.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        getSupportActionBar().hide();
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_desc);
        setContentView(R.layout.activity_welcome);

/*        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                startActivity(new Intent(welcome.this,MainActivity.class));
                finish();
            }
        }, 3000);//单位是毫秒
*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, DescActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    //只显示一次启动页（App没被kill的情况下）
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); 	不要调用父类的方法
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
