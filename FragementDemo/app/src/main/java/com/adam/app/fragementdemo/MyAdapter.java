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


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * <h1>MyAdapter</h1>
 *
 * @autor AdamChen
 * @since 2018/4/20
 */
public class MyAdapter extends FragmentStateAdapter {

    private static final int ITEMS = 2;

    // map: position -> Fragment
    private final static Fragment[] sFragments = new Fragment[ITEMS];

    static {
        sFragments[0] = ImageFragment.init(0);
        sFragments[1] = ArrayListFragment.init(1);
    }


    public MyAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        Utils.Info(this, "MyAdapter constructor (ViewPager2) enter");
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Utils.Info(this, "[createFragment] position: " + position);
        return sFragments[position];
    }

    @Override
    public int getItemCount() {
        return ITEMS;
    }
}
