package com.adam.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    private TextView mInfo;
    private TextView mBatteryInfo;
    private Button mBtn;
    
    private Timer mTimer;
    
    private long mCount = 1L;
    
    
    // For battery info
    private BroadcastReceiver mBatteryInfoReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                showBatteryInfo(intent);
            }
        }
        
    };
    
    private class TestTask implements Runnable {

        private final class updateUITask implements Runnable
        {
            @Override
            public void run()
            {
                String timerName = "Timer thread: " + String.valueOf(mCount);
                if (mInfo != null) {
                    mInfo.setText(timerName);
                }
            }
        }

        @Override
        public void run()
        {
            while (Thread.currentThread().isInterrupted() == false) {
                
                // delay 1ms
                try
                {
                    
                 // cancel timer
                    if (mTimer != null) {
                        mTimer.cancel();
                        mTimer = null;
                    }
                    // new Timer
                    mTimer = new Timer();
                    // update Ui
                    MainActivity.this.runOnUiThread(new updateUITask());
                    
                    // Schedule timer task
                    mTimer.schedule(new TimerTask() {

                        @Override
                        public void run()
                        {
                            Utils.info("Time out~~~");
                        }
                        
                    }, 3000L);
                    
                    
                    Thread.sleep(1L);
                }
                catch (InterruptedException e)
                {
                    Utils.info(Log.getStackTraceString(e));
                    
                    Thread.currentThread().interrupt();
                    
                }
                
                // update 
                mCount++;
                
            }
            
            Utils.info("Stop test~~~");
        }
        
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mBatteryInfo = (TextView)this.findViewById(R.id.battery_info);
        mInfo = (TextView)this.findViewById(R.id.info);
        mBtn = (Button)this.findViewById(R.id.btn_test);
        
        // register battery info receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatteryInfoReciever, filter);

        showBatteryInfo(null);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private boolean mActive;
    
    private Thread mThread;
    
    
    public void onStart(View v) {
        if (mActive == false) {
            Utils.info("Start test~~~");
            mThread = new Thread(new TestTask());
            
            Utils.showToast(this, "Start...");
            // start thread
            mThread.start();
            
            // update Ui
            mBtn.setText(this.getResources().getString(R.string.btn_stop_txt));
            
            // change state
            mActive = true;
        } else {
            Utils.info("Stop test~~~");
            // stop thread
            mThread.interrupt();
            
            // update Ui
            mBtn.setText(this.getResources().getString(R.string.btn_start_txt));
            
            // count reset
            mCount = 0L;
            
            mActive = false;
        }
        
    }
    
    private void showBatteryInfo(Intent intent) {
        
        final StringBuilder stb = new StringBuilder();
        stb.append("=== Battery info: ").append("\n");
        if (intent != null) {
            int status = intent.getIntExtra("status", 0);
            int health = intent.getIntExtra("health", 0);
            boolean present = intent.getBooleanExtra("present",false);
            int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 0);
            int icon_small = intent.getIntExtra("icon-small", 0);
            int plugged = intent.getIntExtra("plugged", 0);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature",0);
            String technology = intent.getStringExtra("technology");

            BatterySatus statusData = BatterySatus.get(status);
            String statusInfo = (statusData != null)? statusData.info(): "no data";
            
            BatteryHealth healthData = BatteryHealth.get(health);
            String healthInfo = (healthData != null)? healthData.info(): "no data";
            
            String acString = "no data";
            switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                acString = "plugged ac";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                acString = "plugged usb";
                break;
            }
            
            // temperature: int to float
            float temp = temperature/10.0f;
            
            
            stb.append("status: ").append(statusInfo).append("\n");
            stb.append("health: ").append(healthInfo).append("\n");
            stb.append("present: ").append(present).append("\n");
            stb.append("level: ").append(level).append("\n");
            stb.append("scale: ").append(scale).append("\n");
            stb.append("icon_small: ").append(icon_small).append("\n");
            stb.append("plugged: ").append(acString).append("\n");
            stb.append("voltage: ").append(voltage).append(" mV").append("\n");
            stb.append("temperature: ").append(temp).append(" c").append("\n");
            stb.append("technology: ").append(technology).append("\n");
        } else {
            stb.append("Empty").append("\n");
        }
        // show
        this.runOnUiThread(new Runnable() {

            @Override
            public void run()
            {
                mBatteryInfo.setText(stb.toString());
            }
            
        });
    }
    
    // Battery status
    private enum BatterySatus {
        
        UNKNOWN(BatteryManager.BATTERY_STATUS_UNKNOWN) {

            @Override
            public String info()
            {
                return "unknown";
            }
            
        },
        CHARGING(BatteryManager.BATTERY_STATUS_CHARGING) {

            @Override
            public String info()
            {
                return "charging";
            }
            
        },
        DISCHARGING(BatteryManager.BATTERY_STATUS_DISCHARGING) {

            @Override
            public String info()
            {
                return "discharging";
            }
            
        },
        NOT_CHARGING(BatteryManager.BATTERY_STATUS_NOT_CHARGING) {

            @Override
            public String info()
            {
                return "not charging";
            }
            
        },
        FULL(BatteryManager.BATTERY_STATUS_FULL) {

            @Override
            public String info()
            {
                return "full";
            }
            
        };

        private int mStatus;
        
        private BatterySatus(int status) {
            this.mStatus = status;
        }
        
        // factory
        public static BatterySatus get(int status) {
            for (BatterySatus data: BatterySatus.values()) {
                if (data.mStatus == status) {
                    return data;
                }
            }
            return null;
        }
        
        
        public abstract String info();
        
    }
    
    
 // Battery health
    private enum BatteryHealth {
        
        UNKNOWN(BatteryManager.BATTERY_HEALTH_UNKNOWN) {

            @Override
            public String info()
            {
                return "unknown";
            }
            
        },
        GOOD(BatteryManager.BATTERY_HEALTH_GOOD) {

            @Override
            public String info()
            {
                return "good";
            }
            
        },
        OVERHEAT(BatteryManager.BATTERY_HEALTH_OVERHEAT) {

            @Override
            public String info()
            {
                return "overheat";
            }
            
        },
        DEAD(BatteryManager.BATTERY_HEALTH_DEAD) {

            @Override
            public String info()
            {
                return "dead";
            }
            
        },
        VOLTAGE(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {

            @Override
            public String info()
            {
                return "voltage";
            }
            
        },
        FAILURE(BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {

            @Override
            public String info()
            {
                return "unspecified failure";
            }
            
        };

        private int mHealth;
        
        private BatteryHealth(int health) {
            this.mHealth = health;
        }
        
        // factory
        public static BatteryHealth get(int health) {
            for (BatteryHealth data: BatteryHealth.values()) {
                if (data.mHealth == health) {
                    return data;
                }
            }
            return null;
        }
        
        
        public abstract String info();
        
    }
    
    
}
