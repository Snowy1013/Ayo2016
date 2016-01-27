package org.ayo.view.progress;

import genius.android.view.R;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class ProgressView extends ImageView{

	public ProgressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ProgressView(Context context) {
		super(context);
		init();
	}
	private void init() {
		this.setImageResource(R.anim.progress_animation_list);
		AnimationDrawable ad = (AnimationDrawable) this.getDrawable();
		ad.start();
	}
}
