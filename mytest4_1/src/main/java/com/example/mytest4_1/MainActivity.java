package com.example.mytest4_1;

import android.app.LauncherActivity;
import android.content.Intent;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends LauncherActivity {

    String[] classNames = {"设置程序参数","查看星际兵种"};
    Class<?>[] clazzs = {PreferenceActivityTest.class,ExpandableListActivityTest.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,classNames);
        setListAdapter(adapter);
        //setContentView(R.layout.activity_main);
    }

    @Override
    public Intent intentForPosition(int position){
        return new Intent(MainActivity.this,clazzs[position]);
    }
}
