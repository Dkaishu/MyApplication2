package com.example.mytest5_2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        Button edit = (Button) findViewById(R.id.edit);
        Button call = (Button) findViewById(R.id.call);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                Intent intent = new Intent();
                String data = "http://nifangge.xyz";
                Uri uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
*/
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://nifangge.xyz"));
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String data = "content://com.android.contacts/contacts/1";
                Uri uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_EDIT);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String data = "tell:10010";
                Uri uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }
}
