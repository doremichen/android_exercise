/**
 * Note: The app could receive boot complete in as the following case:
 *      1. Push app in system/app
 *      2. Only works if the app has already been launched by the user at least once
 *      if this app is used to adb install way
 */
package com.adam.app.appstartup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Utils.info("action: " + action);
        // get boot complete action
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Utils.info("Boot complete~~~");
            BootUpService.startBootUpService(context);
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
