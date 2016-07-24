package com.example.mytest4_1;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class PreferenceActivityTest extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("设置操作");
            setListFooter(button);
        }
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    //重写该方法，验证各个PreferenceFragment是否有效
    @Override
    public boolean isValidFragment(String fragmentName) {
        return true;
    }

    public static class Prefs1Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    public static class Prefs2Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.display_prefs);
            //获取传入的Fragment参数
            String websits = getArguments().getString("website");
            Toast.makeText(getActivity(),"网站是："+websits,Toast.LENGTH_LONG).show();
        }
    }
}





















