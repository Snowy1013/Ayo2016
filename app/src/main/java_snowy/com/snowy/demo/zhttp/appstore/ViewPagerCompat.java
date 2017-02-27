package com.snowy.demo.zhttp.appstore;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerCompat extends ViewPager {

    private boolean mViewTouchMode = false;

    public ViewPagerCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewTouchMode(boolean b) {
        if (b && !isFakeDragging()) {
            beginFakeDrag();
        } else if (!b && isFakeDragging()) {
            endFakeDrag();
        }
        mViewTouchMode = b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        if (mViewTouchMode) {
//            return false;
//        }
//        try {
//            return super.onInterceptTouchEvent(event);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            return false;
//        }
    	return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean arrowScroll(int direction) {
        if (mViewTouchMode)
            return false;
        if (direction != FOCUS_LEFT && direction != FOCUS_RIGHT)
            return false;
        return super.arrowScroll(direction);
    }

    
}
