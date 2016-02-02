package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * InsetDrawable本身是一张Drawable，再往里嵌入一个：
 * 当控件需要的背景比实际的边框小的时候比较适合使用InsetDrawable，指定了上下左右空出的距离
 * ——注意：对应View的内容也以InsetDRAWable的边距为边距
 * 
 * @author seven
 *
 */
@SuppressLint("NewApi")
public class D_InsetDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__inset_drawable);
		
		InsetDrawable insetDrawable=new InsetDrawable(getResources().getDrawable(R.drawable.animate_shower), 20, 30, 30, 20); 
	
		/*
		 * xml:
		 * 
		 * <inset xmlns:android="http://schemas.android.com/apk/res/android"   
			    android:drawable="@drawable/image4"  
			    android:insetLeft="50dp"  
			    android:insetRight="50dp"  
			    android:insetTop="20dp"  
			    android:insetBottom="20dp">  
			</inset>
		 */
		findViewById(R.id.tv).setBackground(insetDrawable);
	}


}
