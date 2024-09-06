package com.adam.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


public class Test {
    private int mDuration;
    private AlarmManager mAlarmMgr;
    private PendingIntent mPendingIntent;

    public static Test newInstance() {
        return Helper.INTANCE;
    }

    public void init(Context ctx) {
        mAlarmMgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        // config alarm
        Intent it = new Intent(ctx, MyReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(ctx, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void setUp(int duration) {
        Utils.info("setUp duration: " + duration);
        this.mDuration = duration * 1000;
    }

    public void run() {
        Utils.info("run test");
        // set alarm
        if (this.mDuration == 0) {
            Utils.info("Please input the valid number!!![>0]");
            return;
        }

        // set alarm
        this.mAlarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + this.mDuration,
                this.mDuration, this.mPendingIntent);

    }

    public void stop() {
        Utils.info("stop test");
        // cancel alarm
        if (this.mAlarmMgr != null) {
            this.mAlarmMgr.cancel(mPendingIntent);
        }
    }

    private static class Helper {
        private static final Test INTANCE = new Test();
    }


}

/*
 * ===========================================================================
 *
 * Revision history
 *
 * ===========================================================================
 */
