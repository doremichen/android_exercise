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

import androidx.fragment.app.Fragment;

import com.adam.app.fragementdemo.databinding.ImageFragementBinding;


/**
 * <h1>ImageFragment</h1>
 *
 * @autor AdamChen
 * @since 2018/4/20
 */
public class ImageFragment extends Fragment {

    private static final String VALUE = "value";
    private int mFragNum;
    // view binding
    private ImageFragementBinding mBinding;


    static ImageFragment init(int val) {
        Utils.Info(ImageFragment.class, "[ImageFragment] enter");

        final ImageFragment fragment = new ImageFragment();

        // Save value in Bundle
        final Bundle args = new Bundle();
        args.putInt(VALUE, val);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.Info(this, "[onCreate] enter");
        // Get value from bundle
        mFragNum = this.getArguments() != null ? this.getArguments().getInt(
                VALUE) : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Utils.Info(this, "[onCreateView] enter");

        // view binding
        mBinding = ImageFragementBinding.inflate(inflater, container, false);
        final View layoutView = mBinding.getRoot();

        mBinding.text.setText("Fragment #: " + mFragNum);

        return layoutView;
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

}
