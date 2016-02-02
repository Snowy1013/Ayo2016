package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * 旋转，范围1到10000， 对应角度是xml指定的android:fromDegrees="-90" 和android:toDegrees="180"
 * 对应选中中心是xml里指定的也是
 * 
 *  
 * @author seven
 *
 */
@SuppressLint("NewApi")
public class D_RotateDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__rotate_drawable);
	
		final View v = findViewById(R.id.tv);
		v.setBackgroundResource(R.drawable.d_rotate_drawable);
		//.setBackground(scaleDrawable);
		//v.getBackground().setLevel(0);
		/**
		 * xml
		 * <?xml version="1.0" encoding="utf-8"?>  
			<rotate xmlns:android="http://schemas.android.com/apk/res/android"  
			    android:drawable="@drawable/image02"  
			    android:visible="true"  
			    android:fromDegrees="-90"   ---逆时针90度
			    android:toDegrees="180"    ---顺时针180度，范围是270度，对应1到10000
			    android:pivotX="50%"  ---相对于Drawable自己
			    android:pivotY="50%">  ---相对于Drawable自己
			</rotate>
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
