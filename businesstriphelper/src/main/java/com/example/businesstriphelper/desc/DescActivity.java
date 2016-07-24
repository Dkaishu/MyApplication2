package com.example.businesstriphelper.desc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.businesstriphelper.R;
import com.example.businesstriphelper.login.LoginActivity;

/**
 *
 * @{# GuideActivity.java Create on 2013-5-2 下午10:59:08
 *
 *     class desc: 引导界面
 *
 *     <p>
 *     Copyright: Copyright(c) 2013
 *     </p>
 * @Version 1.0
 * @Author <a href="mailto:gaolei_xj@163.com">Leo</a>
 *
 *
 */
public class DescActivity extends AppCompatActivity implements OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;
    Boolean isFirst;

    //判断是第几次启动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_desc);

        // 初始化页面
        initViews();

        // 初始化底部小点
        initDots();
    }

    private void initViews() {

        SharedPreferences pref = getSharedPreferences("first",Activity.MODE_PRIVATE);
        isFirst = pref.getBoolean("status",true);

        if(!isFirst){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout desc3 = (RelativeLayout) inflater.inflate(R.layout.desc_3, null);
        desc3.findViewById(R.id.toMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.desc_1, null));
        views.add(inflater.inflate(R.layout.desc_2, null));
        //views.add(inflater.inflate(R.layout.desc_3, null));
        views.add(desc3);
        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);

        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);


    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }

}




/*
package com.example.businesstriphelper.desc;

*/
/**
 * Created by Administrator on 2016/7/2.
 *//*

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.main_view.PagerAdapter;
import android.support.v4.main_view.ViewPager;
import android.support.v4.main_view.ViewPager.OnPageChangeListener;
import android.main_view.LayoutInflater;
import android.main_view.View;
import android.main_view.ViewGroup;
import android.main_view.ViewGroup.LayoutParams;
import android.main_view.Window;
import android.widget.ImageView;

import com.example.businesstriphelper.R;

public class DescActivity extends Activity {
    ViewPager viewPager;
    ArrayList<View> list;
    ViewGroup main, group;
    ImageView imageView;
    ImageView[] imageViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();
        list = new ArrayList<View>();
        list.add(inflater.inflate(R.layout.desc_1, null));
        list.add(inflater.inflate(R.layout.desc_2, null));
        list.add(inflater.inflate(R.layout.desc_3, null));

        imageViews = new ImageView[list.size()];
        ViewGroup main = (ViewGroup) inflater.inflate(R.layout.activity_desc, null);
        // group是R.layout.desc中的负责包裹小圆点的LinearLayout.
        ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);

        viewPager = (ViewPager) main.findViewById(R.id.viewPager);

        for (int i = 0; i < list.size(); i++) {
            imageView = new ImageView(DescActivity.this);
            imageView.setLayoutParams(new LayoutParams(10,10));
            imageView.setPadding(10, 0, 10, 0);
            imageViews[i] = imageView;
            if (i == 0) {
                // 默认进入程序后第一张图片被选中;
                imageViews[i].setBackgroundResource(R.drawable.desc_white_dot);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.desc_dark_dot);
            }
            group.addView(imageView);
        }

        setContentView(main);

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new MyListener());
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(list.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).addView(list.get(arg1));
            return list.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    class MyListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.desc_white_dot);
                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.desc_dark_dot);
                }
            }

        }

    }
}*/
