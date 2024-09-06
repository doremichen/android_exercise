package com.adam.app.androidlistdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AsyncTaskTest extends Activity implements OnClickListener {
	
	private static final String TAG = "AsyncTaskTest";
	
	private TextView tv = null;
	private Button btn = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.asyntask_layout);
		
		tv = (TextView)this.findViewById(R.id.tv_load_web);
		btn = (Button)this.findViewById(R.id.btn_load_web);
		
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.btn_load_web:
			CustThread t = new CustThread();
			t.start();
//			readWebPage();
			break;
		}
	}
	
	
	private class DownLoadWebPageTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG, " ==> doInBackground");
			String response = "";
			for(String url: arg0) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();
					
					BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
					String s = "";
					
					while((s = buffer.readLine()) != null) {
						response += s;
					}
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Log.i(TAG, " ==> onPostExecute");
			super.onPostExecute(result);
			tv.setText(result);
		}
		
	}
	
	
	protected void readWebPage() {
		Log.i(TAG, " ==> readWebPage");
		DownLoadWebPageTask task = new DownLoadWebPageTask();
		task.execute(new String[]{"http://www.google.com"});
	}

	
	public class CustThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Log.i(TAG, " run~~~");
			readWebPage();
//			while(true);
		}
		
	}
	
}
