package org.ayo.app.base;

import android.os.Bundle;
import android.view.View;

import org.ayo.view.layout.swipeback.SwipeBackActivityBase;
import org.ayo.view.layout.swipeback.SwipeBackActivityHelper;
import org.ayo.view.layout.swipeback.SwipeBackLayout;
import org.ayo.view.layout.swipeback.SwipeBackUtils;

/**
 * Created by Administrator on 2016/1/21.
 */
public class SwipeBackActivityAttacher extends ActivityAttacher implements SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(getActivity());
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    public <T extends View> T findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return (T)mHelper.findViewById(id);
        return (T)v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(getActivity());
        getSwipeBackLayout().scrollToFinishActivity();
    }

}
