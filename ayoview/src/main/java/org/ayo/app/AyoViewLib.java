package org.ayo.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AyoViewLib {
	
	public static Context context;
	
	public static void init(Context context){
		AyoViewLib.context = context;
	}

	public static void setListViewHeight(ListView listView){
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight() + 30;
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 *
	 * @param v
	 * @param w  -1 = wrap_content， -2 = match_parent，or px
	 * @param h  -1 = wrap_content， -2 = match_parent，or px
	 */
	public static void setViewSize(View v, int w, int h){
		if(v == null) return;
		int w2 = w;
		if(w == -1)  w2 = ViewGroup.MarginLayoutParams.WRAP_CONTENT;
		if(w == -2)  w2 = ViewGroup.MarginLayoutParams.MATCH_PARENT;
		int h2 = h;
		if(h == -1)  h2 = ViewGroup.MarginLayoutParams.WRAP_CONTENT;
		if(h == -2)  h2 = ViewGroup.MarginLayoutParams.MATCH_PARENT;
		ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
		if(lp == null){
			lp = new ViewGroup.MarginLayoutParams(w2, h2);
		}else{
			lp.width = w2;
			lp.height = h2;
		}
		v.setLayoutParams(lp);
	}

	/**
	 * @param activity
	 * @param color Color.parseColor("#00000000")
	 */
	public static boolean setSystemBarColor(Activity activity, int color){
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return false;
		}
		prepareForStatusControl(activity);

		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setTintColor(color);
		//tintManager.setNavigationBarTintResource(R.drawable.my_tint);
		//tintManager.setStatusBarTintDrawable(MyDrawable);
		///----------
		return true;
	}

	private static void prepareForStatusControl(Activity a){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = a.getWindow();
			// Translucent status bar
			window.setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// Translucent navigation bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	/**
	 * show html in TextView, and try to parse span and style
	 * @param tv
	 * @param html
	 */
	public static void showHtml(TextView tv, String html){

	}

	public static void showView(View v, int animId){

	}

	public static void hideView(View v, int animId){

	}
	
}
