package com.snowy.demo.zuicode.zview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ts on 16-7-26.
 */
public class DrawView extends View {

    public float currentX = 40;
    public float currentY = 50;

    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 15, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        currentX = event.getX();
        currentY = event.getY();

        invalidate();
        return true;
    }
}
