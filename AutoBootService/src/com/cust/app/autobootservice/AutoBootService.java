/**
 * File: AutoBootService.java
 * Breif: Used to invoke systen server to add system service
 *
 * Name:
 * Date: 
 */

package com.cust.app.autobootservice;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class AutoBootService extends Service {

    private static final String TAG = AutoBootService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "[onCreate]");
        // initial
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "[onDestroy]");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "[onStartCommand] enter");
        // action
        Log.i(TAG, "[onStartCommand] exit");
        return START_STICKY;
    }

}
