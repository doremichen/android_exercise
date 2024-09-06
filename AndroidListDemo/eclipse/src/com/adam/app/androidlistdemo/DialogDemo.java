package com.adam.app.androidlistdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DialogDemo extends Activity implements OnClickListener{

	private Button btn =  null;
	
	private void showDialog() {
		AlertDialog.Builder db = new AlertDialog.Builder(this);
		db.setTitle("Alert Msg");
		db.setMessage("Back is ok?");
		db.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		db.setNegativeButton("No", null);
		db.show();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dlg_layout);
		
		btn = (Button)this.findViewById(R.id.dlg_btn1);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.dlg_btn1:
			showDialog();
			break;
		default:
			break;
		}
	}
	
	
	
}
