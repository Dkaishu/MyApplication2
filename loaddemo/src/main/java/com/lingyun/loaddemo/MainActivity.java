package com.lingyun.loaddemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity implements LoadPreView.LoadListener{

    private static final String TAG = "MainActivity";

    private LoadView loadView;

    private float downY;

    private Handler mHandler = new Handler();

    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadView = (LoadView)findViewById(R.id.loadView);
        loadView.setLoadListener(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                loadView.bezierDelay(Math.abs(event.getY() - downY) / 3.0f);
                break;
            case MotionEvent.ACTION_UP:
                if(!loading){
                    loadView.loadCancle();
                }
                break;
        }
        return true;
    }

    @Override
    public void loadStart() {
        Log.d(TAG,"[loadStart] ENTER...");
        loading = true;
        loadView.startLoad();
        mHandler.postDelayed(loadSuccess, 3000);
    }

    private Runnable loadSuccess = new Runnable() {
        @Override
        public void run() {
            loadView.loadSuccess();
            mHandler.postDelayed(loadFinish,1000);
        }
    };

    private Runnable loadFinish = new Runnable() {
        @Override
        public void run() {
            loading = false;
            loadView.loadFinish();
        }
    };
}
