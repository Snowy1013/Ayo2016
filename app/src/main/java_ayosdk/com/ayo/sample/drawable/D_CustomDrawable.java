package com.ayo.sample.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

public class D_CustomDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__custom_drawable);
	}

	public class MyDrawable extends Drawable{

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getOpacity() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setAlpha(int alpha) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class MyDrawableContainer extends DrawableContainer{
		
	}
}
