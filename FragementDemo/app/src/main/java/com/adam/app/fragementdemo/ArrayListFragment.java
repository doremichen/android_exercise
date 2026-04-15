/*
 * Copyright (c) 2026 Adam Chen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.adam.app.fragementdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adam.app.fragementdemo.databinding.ListFragmentBinding;
import com.adam.app.fragementdemo.databinding.ListItemMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ListFragment</h1>
 *
 * @autor AdamChen
 * @since 2018/4/20
 */
public class ArrayListFragment extends Fragment {

    private static final String VALUE = "Value";
    private static List<String> sList = new ArrayList<String>();

    static {
        sList.add("Material Design 3");
        sList.add("RecyclerView Performance");
        sList.add("View Binding Fix");
    }

    private int mFragNum;
    // view binding
    private ListFragmentBinding mBinding;

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
        this.mFragNum = this.getArguments() != null
                ? this.getArguments().getInt(VALUE)
                : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Utils.Info(this, "[onCreateView] enter");

        // view binding
        mBinding = ListFragmentBinding.inflate(inflater, container, false);
        mBinding.text.setText("Fragment #: " + this.mFragNum);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.Adapter<MyViewHolder> adapter = buildAdapter();
        // set adapter
        mBinding.recyclerView.setAdapter(adapter);
    }

    private RecyclerView.Adapter<MyViewHolder> buildAdapter() {
        return new RecyclerView.Adapter<MyViewHolder>() {
            // view binding
            private ListItemMainBinding mBinding;

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // view binding
                mBinding = ListItemMainBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new MyViewHolder(mBinding);
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                String info = sList.get(position);
                holder.mBinding.itemText.setText(info);
                // item click listener
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // log
                        Utils.Info(ArrayListFragment.this, "[onItemClick]: " + info);
                        // show toast
                        Utils.showToast(getContext(), "[onItemClick]: " + info);
                    }

                });
            }


            @Override
            public int getItemCount() {
                return sList.size();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.Info(this, "[onDestroy] enter");
        mBinding = null;
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

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private final ListItemMainBinding mBinding;

        public MyViewHolder(@NonNull ListItemMainBinding binding) {
            super(binding.getRoot());
            Utils.Info(this, "[MyViewHolder] enter");
            mBinding = binding;
        }
    }
}
