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

import android.util.Log;

/**
 * <h1>Utils</h1>
 * 
 * @autor AdamChen
 * @since 2018/4/23
 */
public abstract class Utils {

    private static final String TAG = "Demo";

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
