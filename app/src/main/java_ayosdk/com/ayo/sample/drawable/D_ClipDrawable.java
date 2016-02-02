package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * 图片裁剪：
 * ——默认是0，即完全裁剪
 * ——最大是10000，即完全不裁剪
 * ——通过setLevel设置裁剪大小
 * 
------------------android:gravity
top	将这个对象放在容器的顶部，不改变其大小。当clipOrientation 是"vertical"，裁剪发生在drawable的底部（bottom）
bottom	将这个对象放在容器的底部，不改变其大小。当clipOrientation 是 "vertical"，裁剪发生在drawable的顶部（top）
left	将这个对象放在容器的左部，不改变其大小。当clipOrientation 是 "horizontal"，裁剪发生在drawable的右边（right）。默认的是这种情况。
right	将这个对象放在容器的右部，不改变其大小。当clipOrientation 是 "horizontal"，裁剪发生在drawable的左边（left）。
center_vertical	将对象放在垂直中间，不改变其大小。裁剪的情况和”center“一样。
fill_vertical	垂直方向上不发生裁剪。（除非drawable的level是 0，才会不可见，表示全部裁剪完）
center_horizontal	将对象放在水平中间，不改变其大小。裁剪的情况和”center“一样。
fill_horizontal	水平方向上不发生裁剪。（除非drawable的level是 0，才会不可见，表示全部裁剪完）
center	将这个对象放在水平垂直坐标的中间，不改变其大小。当clipOrientation 是 "horizontal"裁剪发生在左右。当clipOrientation是"vertical",裁剪发生在上下。
fill	填充整个容器，不会发生裁剪。(除非drawable的level是 0，才会不可见，表示全部裁剪完)。
clip_vertical   额外的选项，它能够把它的容器的上下边界，设置为子对象的上下边缘的裁剪边界。裁剪要基于对象垂直重力设置：如果重力设置为top，则裁剪下边，如果设置为bottom，则裁剪上边，否则则上下两边都要裁剪。
clip_horizontal  额外的选项，它能够把它的容器的左右边界，设置为子对象的左右边缘的裁剪边界。裁剪要基于对象垂直重力设置：如果重力设置为right，则裁剪左边，如果设置为left，则裁剪右边，否则则左右两边都要裁剪。
------------------android:clipOrientation
裁剪的方向。
horizontal	水平方向裁剪
vertical	垂直方向裁剪






 * @author seven
 *
 */
@SuppressLint("NewApi")
public class D_ClipDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__clip_drawable);
		
		ClipDrawable clipDrawable = new ClipDrawable(
				getResources().getDrawable(R.drawable.animate_shower), 
				Gravity.CENTER, 
				ClipDrawable.VERTICAL);
		
		final View v = findViewById(R.id.tv);
		v.setBackground(clipDrawable);
		v.getBackground().setLevel(100);
		/**
		 * xml
		 * <clip xmlns:android="http://schemas.android.com/apk/res/android"
		    android:drawable="@drawable/android"
		    android:clipOrientation="horizontal"
		    android:gravity="left" />
		 */
		SeekBar seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setMax(10000);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				v.getBackground().setLevel(progress);
			}
		});
	}


}
