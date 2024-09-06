package com.adam.app.androidlistdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class CustBtnMain extends Activity {

    private static final String TAG = "CustBtnMain";

    private MyLayout myBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.custbutton_layout);

        myBtn = (MyLayout) this.findViewById(R.id.cust_btn);

        myBtn.setImageResource(R.drawable.ball);
        myBtn.setTextViewText("Hello Button");

        float scale = this.getResources().getDisplayMetrics().density;

        int status_bar_height = getStatusBarHeight();
        int pxInt = (int) (status_bar_height / scale + 0.5f);

        Log.i(TAG, "status_bar_height = " + status_bar_height);
        Log.i(TAG, "pxInt = " + pxInt);


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
