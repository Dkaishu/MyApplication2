package com.example.mytest4_4;

import android.app.Activity;
import android.app.ListFragment;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/6/21.
 */
public class BookListFragment extends ListFragment {
    private Callbacks mCallbacks;
    public interface Callbacks{
        void onTtemSelected(Integer id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<BookContent.book>(getActivity(),android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,BookContent.ITEMS));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)){
            throw new IllegalStateException("BookListFragment必须实现接口");
        }
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallbacks.onTtemSelected(BookContent.ITEMS.get(position).id);
    }
    public void setActivityOnItemClick(boolean isActivityOnItemClick){
        getListView().setChoiceMode(isActivityOnItemClick ? ListView.CHOICE_MODE_SINGLE:ListView.CHOICE_MODE_NONE);
    }
}
