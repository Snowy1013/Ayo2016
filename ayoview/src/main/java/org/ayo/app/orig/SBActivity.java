package org.ayo.app.orig;

import org.ayo.view.layout.swipeback.SwipeBackActivity;

import android.os.Bundle;

public abstract class SBActivity extends SwipeBackActivity{
	
	protected ActivityDelegate agent = new ActivityDelegate();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		agent.attach(this);
	}
	
	@Override
	protected void onDestroy() {
		agent.detach();
		super.onDestroy();
	}

	protected SBActivity getActivity(){
		return this;
	}
	
}
