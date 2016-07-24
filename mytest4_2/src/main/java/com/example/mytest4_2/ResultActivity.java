package com.example.mytest4_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/19.
 */
public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        TextView name = (TextView)findViewById(R.id.name);
        TextView passwd = (TextView)findViewById(R.id.passwd);
        TextView gennder = (TextView)findViewById(R.id.gender);
        Intent intent = getIntent();
        Person p = (Person)intent.getSerializableExtra("person");
        name.setText("用户名：" + p.getName());
        passwd.setText("密码：" + p.getPasswd());
        gennder.setText("性别：" + p.getGender());

    }

}
