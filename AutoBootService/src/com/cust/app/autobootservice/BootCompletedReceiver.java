/**
 * File: BootCompletedReceiver.java
 * Breif: Recieve boot compeleted and then trigger boot service
 *
 * Name:
 * Date: 
 */

package com.cust.app.autobootservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;

public class BootCompletedReceiver extends BroadcastReceiver {
    
    private static final String TAG = BootCompletedReceiver.class.getSimpleName();
    
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "[onReceive] enter");
        String action = intent.getAction();
        
        Log.i(TAG, "action: " + action);
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            // show toast
            Toast.makeText(context, "Start SERVICE", Toast.LENGTH_LONG).show();
            // Move to locked boot completed action because this is heliosp system server.
            // the heliosp system server must start as soon as possible.
            //context.startServiceAsUser(new Intent(context, HpBootService.class), UserHandle.SYSTEM);
        } else if (action.equals(Intent.ACTION_LOCKED_BOOT_COMPLETED)) {
            // show toast
            Toast.makeText(context, "Got ACTION_LOCKED_BOOT_COMPLETED", Toast.LENGTH_LONG).show();
            context.startServiceAsUser(new Intent(context, AutoBootService.class), UserHandle.SYSTEM);
        }
        Log.i(TAG, "[onReceive] exit");
    
    }

}
