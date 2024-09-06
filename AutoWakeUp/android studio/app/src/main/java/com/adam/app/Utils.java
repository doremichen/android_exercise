package com.adam.app;

import android.app.Instrumentation;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


public abstract class Utils {
    private static final String TAG = "Test";

    // log
    public static void info(String str) {
        Log.i(TAG, str);
    }

    // toast
    public static void showToast(Context ctx, String str) {
        Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
    }


    public static void invokeCommand(String cmd) {
        info("Command: " + cmd);
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(cmd);
        } catch (IOException e) {
            info(e.toString());
            //Log.e(TAG, e.toString());
        }
    }


    public static void simulateKeyByCommand(final int KeyCode) {
        info("simulateKeyByCommand: " + KeyCode);
        try {
            String keyCommand = "input keyevent " + KeyCode;
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(keyCommand);
        } catch (IOException e) {
            info(e.toString());
            //Log.e(TAG, e.toString());
        }
    }

    public static void simulateKey(final int KeyCode) {
        info("simulateKey: " + KeyCode);
        new Thread() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

        }.start();
    }

    public static void wakeScreen(Context context) {

        // Waking the screen so the user will see the notification
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);


        boolean isScreenOn;

        isScreenOn = pm.isScreenOn();

        if (!isScreenOn) {

            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP, "MyLock");

            wl.acquire(1000);
            wl.release();
        }
    }

}

/*
 * ===========================================================================
 *
 * Revision history
 *
 * ===========================================================================
 */
