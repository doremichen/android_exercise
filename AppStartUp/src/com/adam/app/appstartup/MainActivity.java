
package com.adam.app.appstartup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends Activity {
    
    Button mBtn_run_monkey;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mBtn_run_monkey = (Button)this.findViewById(R.id.btn_run_maonkey);
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
    
    public void onRunMonkey(View v) {
        Utils.info("onRunMonkey@MainActivity");
        Utils.showToast(this, "This button is pressed!!!");
        
        // start toolkits runM
        try {
            Runtime.getRuntime().exec("/system/bin/toolkits runM");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        ProcessBuilder processBuilder = new ProcessBuilder("/system/bin/toolkits", "runM");
//        try {
//            Process process = processBuilder.start();
//            process.waitFor();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        
        mBtn_run_monkey.setClickable(false);
        // finish ui
        this.finish();
        
    }
    
}
