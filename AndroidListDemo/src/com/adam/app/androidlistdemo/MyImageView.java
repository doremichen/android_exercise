package com.adam.app.androidlistdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {

	private int color = 0;
	private int borderwidth = 0;
	public Canvas canvas = new Canvas();
	public int status = 0;
	
	
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setMyColor(int color) {
		this.color = color;
	}
	
	public void setBorderWidth(int boardwidth) {
		this.borderwidth = boardwidth;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		this.canvas = canvas;
		
		Rect rect = canvas.getClipBounds();
		rect.bottom--;
		rect.right--;
		
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(borderwidth);
		
		canvas.drawRect(rect, paint);
	}
	
	

}
