package org.ayo.app;

import android.view.View;

/**
 */
public interface OnViewClickListener<T> {

    /**
     *
     * @param position
     * @param t
     * @param targetView
     */
    void onViewClicked(int position, T t, View targetView);
}
