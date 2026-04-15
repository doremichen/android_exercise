/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: ListFragment.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/4/20
 */

package com.adam.app.fragementdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ListFragment</h1>
 * 
 * @autor AdamChen
 * @since 2018/4/20
 */
public class ArrayListFragment extends ListFragment {

    private static final String VALUE = "Value";
    private int mFragNum;
    private static List<String> sList = new ArrayList<String>();

    static {
        sList.add("One");
        sList.add("Two");
        sList.add("Three");
    }

    static ArrayListFragment init(int val) {
        Utils.Info(ArrayListFragment.class, "[init] enter");

        final ArrayListFragment fragment = new ArrayListFragment();

        final Bundle args = new Bundle();
        args.putInt(VALUE, val);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.Info(this, "[onCreate] enter");

        this.mFragNum = this.getArguments() != null ? this.getArguments()
                .getInt(VALUE) : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Utils.Info(this, "[onCreateView] enter");

        final View layoutView = inflater.inflate(R.layout.list_fragment,
                container, false);

        final TextView tv = (TextView) layoutView.findViewById(R.id.text);
        tv.setText("Fragment #: " + this.mFragNum);

        return layoutView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Utils.Info(this, "[onListItemClick] enter position = " + position
                + " id: " + id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.Info(this, "[onActivityCreated] enter");
        String[] arr = new String[sList.size()];
        arr = sList.toArray(arr);

        this.setListAdapter(new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, arr));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.Info(this, "[onDestroy] enter");
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.Info(this, "[onPause] enter");
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.Info(this, "[onResume] enter");
    }

}
