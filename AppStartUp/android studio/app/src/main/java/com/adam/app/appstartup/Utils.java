/**
 * Used for tool set
 */

package com.adam.app.appstartup;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public abstract class Utils {
    private static final String TAG = "AppStartUp";

    public static void info(String msg) {
        Log.i(TAG, msg);
    }

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }


    public static void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Get top activity by activity manager
     *
     * @param
     * @return
     */
    public static String getCurrentActivityName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);

        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getClassName();
    }

}

/*
 * ===========================================================================
 *
 * Revision history
 *
 * ===========================================================================
 */
