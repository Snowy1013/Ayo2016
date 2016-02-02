package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * 缩放：
 * ——setLevel()设置缩放比例，最大值10000
 * 
 * @author seven
 *
 */
@SuppressLint("NewApi")
public class D_ScaleDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__scale_drawable);
		
		ScaleDrawable scaleDrawable = new ScaleDrawable(
				getResources().getDrawable(R.drawable.animate_shower), 
				Gravity.CENTER, 
				0.1f,  //好像是缩放的范围，缩放范围是10%，即增加10%就给你到100%，即从90%开始缩放，也就是setLevel(1)就是90%，1到10000对应80%到100%
				0.0f);//这里的0表示缩放范围是0，即纵向不缩放
		
		final View v = findViewById(R.id.tv);
		v.setBackground(scaleDrawable);
		v.getBackground().setLevel(0);
		/**
		 * xml
		 * <?xml version="1.0" encoding="utf-8"?>  
			<scale xmlns:android="http://schemas.android.com/apk/res/android"  
			    android:scaleWidth="50%"  
			    android:scaleHeight="50%"  
			    android:drawable="@drawable/image1"   
			    android:scaleGravity="center_vertical|center_horizontal"  
			    >  
			</scale>
		 */
		SeekBar seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setMax(10000);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				v.getBackground().setLevel(progress);
			}
		});
	}


}
