package com.cowthan.sample;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ActivityAttacherPrev {
	
	private Activity killer;
	public ActivityAttacherPrev(){
	}
	public Activity getActivity(){
		return killer;
	}
	private void check(){
		if(killer == null){
			throw new IllegalStateException("ActivityAttacher所代理的Activity为空");
		}
	}
	private boolean checkSilencely(){
		try {
			check();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 需要在onCreate里调用
	 * @param activity
	 */
	public void attach(Activity activity){
		this.killer = activity;
	}
	/**
	 * 需要在onDestroy里调用
	 */
	public void detach(){
		this.killer = null;
	}
	
	/** ======================Toast=============================== */
	/** ======================================================== */
	
	/**
	 * 隐藏键盘
	 */
	protected void hideInputMethod() {
		if(!checkSilencely()) return ;
		View view = killer.getCurrentFocus();
		if (view != null) {
			((InputMethodManager) killer.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(view.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/** ============================进度条 ====================*/
	/** ==================================================== */
}
