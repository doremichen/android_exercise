package com.adam.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public abstract class Utils
{
    
    public static void info(String str) {
        Log.i("TimerTest", str);
    }
    
    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}

/*
 * ===========================================================================
 * 
 * Revision history
 * 
 * ===========================================================================
 */
