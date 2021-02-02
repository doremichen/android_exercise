package com.adam.app.widgetdemo;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateService extends Service {

    boolean is_run = false;
    boolean flag =  true;
    
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        final RemoteViews updateView = new RemoteViews(this.getPackageName(), R.layout.widgetmain);
        final ComponentName thisWidget = new ComponentName(this, AppWidgetDemo.class);
        final AppWidgetManager manager = AppWidgetManager.getInstance(this);
        
        if(!is_run) {
            new Thread() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    int i = 0;
                    is_run = true;
                    
                    while(flag) {
                        
                    }
                    
                    super.run();
                }
                
            }.start();
        }
        
        return super.onStartCommand(intent, flags, startId);
    }

    
}
