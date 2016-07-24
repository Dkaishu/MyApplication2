package com.example.mytest4_4;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by Administrator on 2016/6/21.
 */
public class BookDetailsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("详情");

        //如果在详情页中，由竖屏变为横屏，则退回到MainActivity
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            onBackPressed();
            //Log.d("bianhengp","jjjj");
        }

        //作用:
        if (savedInstanceState == null){

 /*         BookDetailFragment fragment = new BookDetailFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(BookDetailFragment.ITEM_ID,getIntent().getIntExtra(BookDetailFragment.ITEM_ID,0));
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction().add(R.id.book_detail_container1,fragment).commit();
*/

            //试验：另一方式实现。这两种方式均可，MainActivity中的Intent语句不改。其实就是getExtras（）与getIntExtra（）的区别，
            // 且BookDetailFragment.ITEM_ID,0这一key——value向上转型为Bundle，很智能。
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.book_detail_container1,fragment).commit();

        }
    }
    //actionBar 的动作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //不反回，判断语句的问题
            //android.R 和R的区别：
            //android.R是要获取系统资源时使用的；
            //R当前应用程序中的资源。

/*
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
*/
            onBackPressed();
            return true;
        }
         return super.onOptionsItemSelected(item);
    }

}
