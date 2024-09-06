package com.adam.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.info("Time is arrived~~~");
        Utils.wakeScreen(context);
        Utils.simulateKey(KeyEvent.KEYCODE_POWER);

    }
}
