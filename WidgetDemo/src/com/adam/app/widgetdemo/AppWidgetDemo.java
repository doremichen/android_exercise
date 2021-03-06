package com.adam.app.widgetdemo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AppWidgetDemo extends AppWidgetProvider {
    
    private static final String TAG = "AppWidgetDemo";

    @Override
    public void onAppWidgetOptionsChanged(Context context,
            AppWidgetManager appWidgetManager, int appWidgetId,
            Bundle newOptions) {
        // TODO Auto-generated method stub
        Log.i(TAG ,"onAppWidgetOptionsChanged");
        
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId,
                newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // TODO Auto-generated method stub
        Log.i(TAG ,"onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        // TODO Auto-generated method stub
        Log.i(TAG ,"onDisabled");
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        // TODO Auto-generated method stub
        Log.i(TAG ,"onEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
//        Log.i(TAG ,"onReceive");
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        // TODO Auto-generated method stub
        Log.i(TAG ,"onUpdate");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    

}
