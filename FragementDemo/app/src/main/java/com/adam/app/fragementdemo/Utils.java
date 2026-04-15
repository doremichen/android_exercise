/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: Utils.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/4/23
 */

package com.adam.app.fragementdemo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * <h1>Utils</h1>
 * 
 * @autor AdamChen
 * @since 2018/4/23
 */
public final class Utils {

    private static final String TAG = "Demo";


    private Utils() {
        throw new UnsupportedOperationException("Utils cannot be instantiated!!!");
    }

    public static void showToast(Context context, String str) {
        // check if the main thread
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            new Handler(Looper.getMainLooper()).post(() -> {
                // show toast
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            });
        }

        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


    /**
     * 
     * <h1>Info</h1> for Instance method
     * 
     * @param obj
     * @param str
     * @return void
     * 
     */
    public static void Info(Object obj, String str) {
        Log.i(TAG, obj.getClass().getSimpleName() + ": " + str);
    }

    /**
     * 
     * <h1>Info</h1> for class method
     * 
     * @param clazz
     * @param str
     * @return void
     * 
     */
    public static void Info(Class<?> clazz, String str) {
        Log.i(TAG, clazz.getSimpleName() + ": " + str);
    }

}
