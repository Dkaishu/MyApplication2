package com.example.mytest4_4;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends Activity implements BookListFragment.Callbacks {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLand() ? R.layout.activity_book_twopane:R.layout.activity_book_list);
    }

     public boolean getLand(){
         boolean current = true;
         if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            // 横屏
             current = true;
         }
         else if (this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT) {
             // 竖屏
             current = false;
         }
         return current;
     }

    @Override
    public void onTtemSelected(Integer id) {
        Bundle arguments = new Bundle();
        arguments.putInt(BookDetailFragment.ITEM_ID,id);

        if(getLand()){
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction().replace(R.id.book_detail_container,fragment).commit();
        }else if (!getLand()){
            Intent detailIntent = new Intent(this,BookDetailsActivity.class);
            //试验：另一方式实现
            //detailIntent.putExtras(arguments);这种方式行不通
            detailIntent.putExtra(BookDetailFragment.ITEM_ID,id);
            startActivity(detailIntent);
        }
    }
}
