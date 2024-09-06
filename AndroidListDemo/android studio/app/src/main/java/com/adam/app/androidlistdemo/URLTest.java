package com.adam.app.androidlistdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class URLTest extends Activity {

    private static final String TAG = "URLTest";


    TextView tv = null;

    CustThread myThread = null;
    CustHandler myHandler = null;

    String myString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        tv = new TextView(this);

        myThread = new CustThread();
        myHandler = new CustHandler();


        myThread.start();
        tv.setText(myString);
        this.setContentView(tv);
    }

    public class CustHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            Log.i("URLtest", "handleMessage+++");
            String str = (String) msg.obj;

            tv.setText(str);
        }

    }

    class CustThread extends Thread {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            Log.i(TAG, "run~~~~");

            try {
                URL mURL = new URL("http://www.google.cn/");

                InputStream is = mURL.openStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;

                while ((current = bis.read()) != -1) {
                    baf.append(current);
                }

                myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
                Log.i(TAG, "send message...");
                Message msg = myHandler.obtainMessage(0, myString);
                myHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
