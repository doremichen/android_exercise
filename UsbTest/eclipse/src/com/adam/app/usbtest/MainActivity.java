package com.adam.app.usbtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    
     private boolean mConnected;
     private boolean mConfigured;
     private boolean mHwDisconnected;
     private boolean mIsPcKnowMe;
    
     private TextView tv_hello;
     
    private BroadcastReceiver mStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            
            String text = "";
            
            StringBuilder stb =  new StringBuilder(text);
            
            
            if(action.equals("android.hardware.usb.action.USB_STATE")){
                mConnected = intent.getBooleanExtra("connected", false);
                mConfigured = intent.getBooleanExtra("configured", false);
                mHwDisconnected = intent.getBooleanExtra("USB_HW_DISCONNECTED", false);
                mIsPcKnowMe = intent.getBooleanExtra("USB_IS_PC_KNOW_ME", false);
            }
            
            PrintI("mConnected = " + mConnected);
            PrintI("mConfigured = " + mConfigured);
            PrintI("mHwDisconnected = " + mHwDisconnected);
            PrintI("mIsPcKnowMe = " + mIsPcKnowMe);
            
            stb.append("mConnected = " + mConnected).append("\n");
            stb.append("mConfigured = " + mConfigured).append("\n");
            stb.append("mHwDisconnected = " + mHwDisconnected).append("\n");
            stb.append("mIsPcKnowMe = " + mIsPcKnowMe).append("\n");
            stb.append("Kernel function list: " + getCurrentFunction()).append("\n");
            
            tv_hello.setText(stb.toString());
        }
        
    };
    
    private String getCurrentFunction() {
        String functions = System.getProperty("persist.sys.usb.config",
                "none");
        Log.d(TAG, "current function: " + functions);
        int commandIndex = functions.indexOf(',');
        if (commandIndex > 0) {
            return functions.substring(0, commandIndex);
        } else {
            return functions;
        }
    }
    
    private void PrintI(String str) {
        Log.i(TAG, str);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv_hello = (TextView)findViewById(R.id.tv_hello);
        
        IntentFilter filter  = new IntentFilter();
        filter.addAction("android.hardware.usb.action.USB_STATE");
        
        this.registerReceiver(mStateReceiver, filter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    

}
