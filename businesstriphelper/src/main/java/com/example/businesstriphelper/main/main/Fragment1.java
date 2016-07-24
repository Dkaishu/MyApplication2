package com.example.businesstriphelper.main.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.businesstriphelper.R;

/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment1 extends Fragment

    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item1, null);
        return view;
        }
    }

