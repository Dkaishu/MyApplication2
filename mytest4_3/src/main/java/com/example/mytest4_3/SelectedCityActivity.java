package com.example.mytest4_3;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/19.
 */
public class SelectedCityActivity extends ExpandableListActivity {

    private String[] provinces = new String[]{"山东","山西","湖北"};
    private String[][] cities = new String[][]{
            {"1","2","3"},{"4","5","6"},{"7","8","9"}
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return provinces.length;
            }

            @Override
            public int getChildrenCount(int i) {
                return cities[i].length;
            }

            @Override
            public Object getGroup(int i) {
                return provinces[i];
            }

            @Override
            public Object getChild(int i, int i1) {
                return cities[i][i1];
            }

            @Override
            public long getGroupId(int i) {
                return i;
            }

            @Override
            public long getChildId(int i, int i1) {
                return i1;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
                LinearLayout ll = new LinearLayout(SelectedCityActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ImageView logo = new ImageView(SelectedCityActivity.this);
                ll.addView(logo);
                TextView textView = getTextView();
                textView.setText(getGroup(i).toString());
                textView.setTextSize(18);
                //为何下面两行没效果？
                textView.setHeight(50);
                textView.setWidth(80);
                ll.addView(textView);
                return ll;
            }

            @Override
            public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
                TextView textView = getTextView();
                textView.setText(getChild(i,i1).toString());
                textView.setTextSize(18);
                return textView;
            }

            @Override
            public boolean isChildSelectable(int i, int i1) {
                return true;
            }

            private TextView getTextView(){
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,64);
                TextView textView = new TextView(SelectedCityActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
                textView.setPadding(36,0,0,0);
                textView.setTextSize(24);
                return textView;
            }
        };
        setListAdapter(adapter);
//        getExpandableListView().setOnChildClickListener(new OnChildClickListener(){
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View source,int gropPosition,int chihldPosition,long id){
//                Intent intent = getIntent();
//                intent.putExtra("city",cities[gropPosition][chihldPosition]);
//                SelectedCityActivity.this.setResult(0,intent);
//                SelectedCityActivity.this.finish();
//                return false;
//            }
//        });
        getExpandableListView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = getIntent();
                intent.putExtra("city",cities[i][i1]);
                SelectedCityActivity.this.setResult(0,intent);
                SelectedCityActivity.this.finish();
                return false;
            }
        });



    }
}
