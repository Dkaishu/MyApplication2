package com.example.mytest6_44;

import android.app.Activity;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/6/29.
 */
public class ClipActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        final ClipDrawable clipDrawable = (ClipDrawable)imageView.getDrawable();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if (msg.what == 0x1233){
                    clipDrawable.setLevel(clipDrawable.getLevel() + 200);
                }

            }
        };
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0x1233;
                handler.sendMessage(msg);
                if (clipDrawable.getLevel() >= 10000){
                    timer.cancel();
                }
            }
        },0,300);
    }
}
