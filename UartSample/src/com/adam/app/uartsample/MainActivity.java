package com.adam.app.uartsample;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	private UartSvr uart_svr = new UartSvr();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void onOpenUart(View v) {
		Log.i(TAG, "onOpenUart");
		uart_svr.OpenUart(2);
		
	}
	
	public void onCloseUart(View v) {
		Log.i(TAG, "onCloseUart");
		uart_svr.CloseUart();
	}

	public void onSetUart(View v) {
		Log.i(TAG, "onSetUart");
		uart_svr.SetUart(3);
	}

	public void onSentMsg(View v) {
		Log.i(TAG, "onSentMsg");
		uart_svr.SendMsgUart("Hello doremi...");
	}

	public void onRecMsg(View v) {
		Log.i(TAG, "onRecMsg");
		String str = "";
		str = uart_svr.RecieveMsgUart();
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	public void onExitUart(View v) {
		this.finish();
	}

}
