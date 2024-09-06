package com.adam.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    private EditText mInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mInput = this.findViewById(R.id.edit_duration);
        // init
        Test.newInstance().init(this.getApplicationContext());
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

    public void onStart(View v) {
        Utils.info("[onStart] enter");
        // get input
        if (this.mInput.getText() == null) {
            Utils.showToast(this, "Please input the valid number~~~!");
            return;
        }
        String inputStr = this.mInput.getText().toString();
        if ("".equals(inputStr)) {
            Utils.showToast(this, "Please input the valid number~~~!");
            return;
        }
        int duration = Integer.parseInt(inputStr);
        Test.newInstance().setUp(duration);

        // invoke
//        Utils.invokeCommand("toolkits autopressP");


        // call service to start
        MyIntentService.startTest(this);

        // finish ui
        this.finish();

        Utils.info("[onStart] exit");
    }

    public void onStop(View v) {
        Utils.info("[onStop] enter");

        // call service to stop
        MyIntentService.stopTest(this);

        // finish ui
        this.finish();

        Utils.info("[onStop] exit");
    }

}
