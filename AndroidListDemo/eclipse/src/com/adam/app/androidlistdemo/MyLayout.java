package com.adam.app.androidlistdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyLayout extends LinearLayout {

	private ImageView iv = null;
	private TextView tv = null;
	
	
	public MyLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.button_layout, this, true);
		iv = (ImageView)this.findViewById(R.id.iv);
		tv = (TextView)this.findViewById(R.id.tv);
		
	}
	
	
	public void setImageResource(int resId) {
		iv.setImageResource(resId);
	}
	
	
	public void setTextViewText(String str) {
		tv.setText(str);
	}
	
}
