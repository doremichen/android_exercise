package com.adam.app.androidlistdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ReadTextTest extends Activity {

    private TextView text = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        text = new TextView(this);

        text.setBackgroundColor(0xffff0000);
        text.setTextColor(Color.WHITE);
        text.setGravity(Gravity.CENTER);

        InputStreamReader inputStreamReader = null;
        InputStream inputStream = getResources().openRawResource(R.raw.test);

        inputStreamReader = new InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8);

        BufferedReader reader = new BufferedReader(inputStreamReader);

        StringBuffer sbf = new StringBuffer();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sbf.append(line);
                sbf.append("\n");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        text.setText(sbf.toString());
        Log.i("Adam", sbf.toString());

        this.setContentView(text);


    }


}
