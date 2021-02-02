package com.adam.app.androidlistdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ParcelableActivity1 extends Activity {

	private Button myButton = null;
	
	private static final String TAG = "Parcelable";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		myButton = new Button(this);
		myButton.setText("Press me...");
		
//		Person benParcelable = new Person("testname", "testage");
		final Person benParcelable = new Person();
			
		myButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Intent.ACTION_SHUTDOWN);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				
				startActivity(it);
				
//				
//				Log.i(TAG, "start assign  name and age");				
//				benParcelable.name = "Adam";
//				benParcelable.age = "18";
//				Log.i(TAG, "start assign  name and age completedly");
//				Intent intent = new Intent();
//				intent.setClass(getApplicationContext(), ParcelableActivity2.class);
//				Bundle bundle = new Bundle();
//				bundle.putParcelable("person", benParcelable);
//				intent.putExtras(bundle);
//				startActivity(intent);
			}
			
		});
		
		this.setContentView(myButton);
		
	}

}
