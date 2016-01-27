package com.ayoview.sample.ztmpl_listview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

public class TmplListActivity extends BaseActivity {
	
	public static void start(Context c, String p){
		Intent i = new Intent(c, TmplListActivity.class);
		i.putExtra("p", p);
		c.startActivity(i);
	}

	private TmplFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_tmpl_activity_list);
		
		fragment = (TmplFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment1);
		fragment.loadOrderList();
		
		///如果activity中有能改变查询条件的控件，只需修改Condition，然后调用fragment.loadOrderList();
	}

}
