package com.cowthan.sample;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class SBFragment extends Fragment{
	
	protected abstract int getLayoutId();
	
	protected ActivityAttacherPrev agent = new ActivityAttacherPrev();
	protected View root;
	protected Handler mHandler;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		root = inflater.inflate(getLayoutId(), null);
		agent.attach(getActivity());
		mHandler = new Handler();
		onCreateView(root);
		return root;
	}
	
	@Override
	public void onDestroyView() {
		agent.detach();
		mHandler = null;
		super.onDestroyView();
	}
	
	public abstract void onCreateView(View root);
	
	public View findViewById(int id){
		return root.findViewById(id);
	}
	
	public View findViewWithTag(Object o){
		return root.findViewWithTag(o);
	}
}
