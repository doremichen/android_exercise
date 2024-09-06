package com.adam.app.androidlistdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ParcelableActivity2 extends Activity {

    private static final String TAG = "Parcelable";

    private TextView text = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        text = new TextView(this.getApplicationContext());
        text.setText("Hello Adam...");

        Person parcelable = this.getIntent().getParcelableExtra("person");
        Log.i(TAG, "get value from parcelable");
        System.out.println(parcelable.name);
        System.out.println(parcelable.age);


    }

}
