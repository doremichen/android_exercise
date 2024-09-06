package com.adam.app.androidlistdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class ShadeTextView extends TextView {

    public ShadeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        drawText(canvas, Color.CYAN);
        super.onDraw(canvas);

    }

    private void drawText(Canvas canvas, int bg) {
        Paint paint = this.getPaint();
        String text = String.valueOf(this.getText());
        float startX = this.getLayout().getLineLeft(0);
        float startY = this.getBaseline();
        paint.setColor(bg);
        paint.setTypeface(Typeface.SANS_SERIF);
        canvas.drawText(text, startX + 1, startY, paint);
        canvas.drawText(text, startX, startY - 1, paint);
        canvas.drawText(text, startX, startY + 1, paint);
        canvas.drawText(text, startX - 1, startY, paint);

    }


}
