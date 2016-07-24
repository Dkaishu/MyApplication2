package com.example.businesstriphelper.main.main;

/**
 * Created by Administrator on 2016/7/9.
 */
import android.app.Application;
import android.content.Context;

//用于获取全局context:MyApplication.getContext()...

public class MyApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();
    }
}