package com.example.mytest4_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button bt = (Button)findViewById(R.id.bn);
        bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText name = (EditText) findViewById(R.id.name);
                EditText passwd = (EditText)findViewById(R.id.passwd);
                RadioButton male = (RadioButton)findViewById(R.id.male);
                String gender = male.isChecked()?"男":"女";
                Person p = new Person(name.getText().toString(),passwd.getText().toString(),gender);
                Bundle data = new Bundle();
                data.putSerializable("person",p);
                Intent intent =new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

    }
}
