package com.adam.app.batteryinfo;

import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "BatteryInfo";
    
    private TextView tvShowInfo;
    
    
    private BroadcastReceiver mBatteryCgangeReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            String action = arg1.getAction();
            if(Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                showBatteryInfo(arg1);
            } else {
                ShowToastMsg(arg0, action);
                
            }
            
            
        }
        
    };
    
    private static String healthCodeToString(int health) {
        switch(health) {
        case BatteryManager.BATTERY_HEALTH_COLD: return "cold";
        case BatteryManager.BATTERY_HEALTH_DEAD: return "dead";
        case BatteryManager.BATTERY_HEALTH_GOOD: return "good";
        case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE: return "over voltage";
        case BatteryManager.BATTERY_HEALTH_OVERHEAT: return "over heat";
        case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE: return "unspecial failure";
        case BatteryManager.BATTERY_HEALTH_UNKNOWN:
        default: return "Unknown";
        }
    }
    
    private static String pluggedCodeToString(int plugged) {
        switch(plugged) {
        case 0: return "Battery";
        case BatteryManager.BATTERY_PLUGGED_AC: return "AC";
        case BatteryManager.BATTERY_PLUGGED_USB: return "USB";
        default: return "UnKnown";
        }
    }
    
    private static String statusCodeToString(int status) {
        switch(status) {
        case BatteryManager.BATTERY_STATUS_CHARGING: return "charging";
        case BatteryManager.BATTERY_STATUS_DISCHARGING: return "discharging";
        case BatteryManager.BATTERY_STATUS_FULL: return "full";
        case BatteryManager.BATTERY_STATUS_NOT_CHARGING: return "not charging";
        case BatteryManager.BATTERY_STATUS_UNKNOWN:
        default: return "UnKnown";
        }
    }
    

    private void showBatteryInfo(Intent intent) {
        StringBuffer sbf = new StringBuffer();
        if(intent != null) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
            String healthString = "Health: " + healthCodeToString(health);
            
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            float percentage = (scale != 0) ? (100.0f * ((float)level/(float)scale)) : 0.0f;
            String levelString = String.format("Level: %d/%d (%.2f%%)", level, scale, percentage);
            
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            String pluggedString = "Power source: " + pluggedCodeToString(plugged);
            
            boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
            String presentString = "Present? :" + (present? "Yes" : "No");
            
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            String statusString = "Status: " + statusCodeToString(status);
            
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            String technologyString = "Technology: " + technology;
            
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, Integer.MIN_VALUE);
            String temperatureString = "Temperature: " + temperature;
            
            
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, Integer.MIN_VALUE);
            String voltageString = "Voltage: " + voltage;
            
            sbf.append(healthString + "\n").
            append(levelString + "\n").
            append(pluggedString + "\n").
            append(presentString + "\n").
            append(statusString + "\n").
            append(technologyString + "\n").
            append(temperatureString + "\n").
            append(voltageString + "\n");
            
            tvShowInfo.setText(sbf.toString());
            
        } else {
            sbf.append("No battery information");
            tvShowInfo.setText(sbf.toString());
        }
    }
    
    private void ShowToastMsg(Context context, String action) {
        String text = "";
        
        if(Intent.ACTION_BATTERY_LOW.equals(action)) {
            text = "Low power";
        } else if(Intent.ACTION_BATTERY_OKAY.equals(action)) {
            text = "Battery is okay";
        } else if(Intent.ACTION_POWER_CONNECTED.equals(action)) {
            text = "Power now connected";
        } else if(Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
            text = "Power now disconnected";
        }
        
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_main);
        
        tvShowInfo = (TextView)this.findViewById(R.id.tv_batteryinfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.unregisterReceiver(mBatteryCgangeReciever);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
                
        this.registerReceiver(mBatteryCgangeReciever, intentFilter);
        
    }
    
    
    

}
