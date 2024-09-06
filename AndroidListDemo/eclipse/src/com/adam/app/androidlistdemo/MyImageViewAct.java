package com.adam.app.androidlistdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class MyImageViewAct extends Activity {

	private MyImageView my_imageview = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.custimageview_layout);
		
		my_imageview = (MyImageView)this.findViewById(R.id.my_imageview);
		my_imageview.setBorderWidth(5);
		my_imageview.setMyColor(Color.WHITE);
	}

	
}
