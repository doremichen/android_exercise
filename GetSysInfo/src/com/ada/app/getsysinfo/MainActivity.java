package com.ada.app.getsysinfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    TextView tv = null;
    StringBuffer sbf = null;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sbf = new StringBuffer();
        
        tv = (TextView)this.findViewById(R.id.textview);
        getMemInfo();
        tv.setText(sbf.toString());
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void getMemInfo() {
        final ActivityManager activityManager = (ActivityManager)this.getSystemService(this.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        sbf.append("avalible memory: ").append("\n");
        sbf.append(outInfo.availMem >> 10).append(" KB\n");
        
        
        
    }

}
