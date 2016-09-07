package com.snowy.demo.zuicode.zview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zx on 16-8-15.
 */
public class ScrollView extends android.widget.ScrollView {

    private OnScrollListener listener;

    public ScrollView(Context context) {
        super(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnScrollListener {
        void loadmore();
    }

    public void setOnScrollListener (OnScrollListener listener) {
        this.listener = listener;
    }

    //手指对屏幕的触摸事件---》监听是否滑到底
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("ScrollView" , "划到了：" + getScrollY());
        View childView = getChildAt(0);
        //获取测量高度（总高度）
        int measureHeight = childView.getMeasuredHeight();
        //获取划出屏幕高度
        int scrollY = getScrollY();
        //获取可视区域
        int height = getHeight();
        if (measureHeight == scrollY + height) {
//            Toast.makeText(getContext(), "划到底了", Toast.LENGTH_SHORT).show();
            listener.loadmore();
        }
        return super.onTouchEvent(ev);
    }
}
