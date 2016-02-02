package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * 实现两张Drawable之间的渐入渐出切换：就两张，只能渐隐渐现，就一次，没法循环的来
 * 
 * @author seven
 *
 */
@SuppressLint("NewApi")
public class D_TransitionDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__transition_drawable);
		
		TransitionDrawable transitionDrawable=null;  
        transitionDrawable= new TransitionDrawable(new Drawable[] {
        	getResources().getDrawable(R.drawable.animate_shower),
        	getResources().getDrawable(R.drawable.kaola),
        });
        findViewById(R.id.tv).setBackground(transitionDrawable);
        transitionDrawable.startTransition(3000);//间隔3秒
        
        /*
         * xml方式：
         * 
         * <transition xmlns:android="http://schemas.android.com/apk/res/android" >  
			    <item android:drawable="@drawable/image01"/>  
			    <item android:drawable="@drawable/image02"/>  
			</transition> 
         * 
         */
	}


}
