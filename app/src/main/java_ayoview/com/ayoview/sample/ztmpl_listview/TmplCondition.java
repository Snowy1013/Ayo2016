package com.ayoview.sample.ztmpl_listview;


import org.ayo.app.tmpl.Condition;

public class TmplCondition extends Condition {
	public int pageNow;

	@Override
	public void onPullDown() {
		pageNow = 0;
	}

	@Override
	public void onPullUp() {
		pageNow += 1;
	}

	@Override
	public void reset() {
		pageNow = 0;
	}
}
