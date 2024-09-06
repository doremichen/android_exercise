package com.adam.app.androidlistdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HandlerTest extends Activity implements OnClickListener {

    private static final String TAG = "HandlerTest";

    private static final int SHOW_PROGRESS = 0;
    private static final int REFRESH = 1;
    private static final int REMOVE_PROGRESS = 2;

    private ProgressBar mProgressBar = null;
    private ImageView img = null;
    private Button btn = null;
    private Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.handlertest_layout);

        img = (ImageView) this.findViewById(R.id.imgic);
        btn = (Button) this.findViewById(R.id.btn_download);

        btn.setOnClickListener(this);

        mProgressBar = new ProgressBar(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        this.addContentView(mProgressBar, params);

        mProgressBar.setVisibility(View.INVISIBLE);


    }    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            switch (msg.what) {
                case SHOW_PROGRESS:
                    //show waite display
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case REFRESH:
                    //Update UI
                    onRefresh();
                    break;
                case REMOVE_PROGRESS:
                    //Remove waite display
                    mHandler.removeMessages(SHOW_PROGRESS);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.btn_download:
                sendMessage(SHOW_PROGRESS);
                Thread t = new Thread(new DownLoad("http://pic3.nipic.com/20090511/2421152_185535027_2.jpg"));
                t.start();
                break;
            default:
                break;
        }
    }

    protected void onRefresh() {
        if (mBitmap != null) {
            img.setImageBitmap(mBitmap);
        }

        sendMessage(REMOVE_PROGRESS);
    }

    protected final void sendMessage(int what) {
        mHandler.sendMessage(mHandler.obtainMessage(what));
    }

    //download thread
    public class DownLoad implements Runnable {

        private String muri = null;


        public DownLoad(String uri) {
            this.muri = uri;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                URL url = new URL(this.muri);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.connect();

                InputStream inputStream = conn.getInputStream();
                mBitmap = BitmapFactory.decodeStream(inputStream);

                //After download completedly, send message to UI thread
                sendMessage(REFRESH);


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
