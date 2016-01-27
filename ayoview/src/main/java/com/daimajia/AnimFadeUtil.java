package com.daimajia;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 *
 * @author Administrator
 * 
 */
public class AnimFadeUtil {

	private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			View view=(View) msg.obj;
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
			super.handleMessage(msg);
		}

	};

	/**
	 *
	 * @param view
	 * @param duration
	 */
	public static void fadeOut(final View view, long duration) {
		Animation animation = new AlphaAnimation(1, 0);
		animation.setDuration(duration);

		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Message message=Message.obtain();
				message.obj=view;
				handler.sendMessage(message);
			}
		});

		view.startAnimation(animation);
	}

	public static void fadeIn(View view, long duration, long delay) {
		Animation animation = new AlphaAnimation(0, 1);
		animation.setDuration(duration);
		animation.setStartOffset(delay);

		view.startAnimation(animation);
	}

}
