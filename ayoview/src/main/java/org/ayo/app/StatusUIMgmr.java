package org.ayo.app;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class StatusUIMgmr {
	
	public static StatusUIMgmr attach(View contentView, OnStatusViewAddedCallback callback){
		StatusUIMgmr s = new StatusUIMgmr(contentView, callback);
		return s;
	}
	
	/**
	 *
	 */
	public static interface OnStatusViewAddedCallback{
		void onErrorViewAdded(View errorOfLocalView, View errorOfServerView);
		void onEmptyViewAdded(View emptyView);
		void onLoadingViewAdded(View loadingView);
	}
	
	/**
	 */
	private View contentView;
	
	/**
	 * parent for contentView, should be RelativeLayout
	 */
	private RelativeLayout container;  
	
	/**
	 */
	private View viewEmpty, viewLoading, viewErrorOfLocal, viewErrorOfServer;
	private Context mContext;
	private OnStatusViewAddedCallback callback;
	
	private StatusUIMgmr(View contentView, OnStatusViewAddedCallback callback){
		this.contentView = contentView;
		this.mContext = contentView.getContext();
		this.container = (RelativeLayout) this.contentView.getParent();
		this.callback = callback;
	}
	
	public void setEmptyLayout(int resId){
		viewEmpty = View.inflate(mContext, resId, null);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		this.container.addView(viewEmpty, lp);
		viewEmpty.setVisibility(View.GONE);
		
		if(callback != null) callback.onEmptyViewAdded(viewEmpty);
	}
	
	public void setErrorLayout(int resIdOfLocal, int resIdOfServer){
		viewErrorOfLocal = View.inflate(mContext, resIdOfLocal, null);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		this.container.addView(viewErrorOfLocal, lp);
		viewErrorOfLocal.setVisibility(View.GONE);
		
		viewErrorOfServer = View.inflate(mContext, resIdOfServer, null);
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		this.container.addView(viewErrorOfServer, lp);
		viewErrorOfServer.setVisibility(View.GONE);
		
		if(callback != null) callback.onErrorViewAdded(viewErrorOfLocal, viewErrorOfServer);
	}
	
	public void setLoadingLayout(int resId){
		viewLoading = View.inflate(mContext, resId, null);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		this.container.addView(viewLoading, lp);
		viewLoading.setVisibility(View.GONE);
		
		if(callback != null) callback.onLoadingViewAdded(viewLoading);
	}
	
	private View currentShowingStatusView;
	
	/**
	 */
	public void showEmpty(){
		if(currentShowingStatusView == viewEmpty) return;
		if(currentShowingStatusView != null) currentShowingStatusView.setVisibility(View.GONE);
		viewEmpty.setVisibility(View.VISIBLE);
		currentShowingStatusView = viewEmpty;
		currentShowingStatusView.bringToFront();
	}
	
	/**
	 */
	private void showError(View viewError){
		if(currentShowingStatusView == viewError) return;
		if(currentShowingStatusView != null) currentShowingStatusView.setVisibility(View.GONE);
		viewError.setVisibility(View.VISIBLE);
		currentShowingStatusView = viewError;
		currentShowingStatusView.bringToFront();
	}
	
	public void showErrorOfLocal(){
		showError(viewErrorOfLocal);
	}
	
	public void showErrorOfServer(){
		showError(viewErrorOfServer);
	}
	
	/**
	 */
	public void showLoading(){
		if(currentShowingStatusView == viewLoading) return;
		if(currentShowingStatusView != null) currentShowingStatusView.setVisibility(View.GONE);
		viewLoading.setVisibility(View.VISIBLE);
		currentShowingStatusView = viewLoading;
		currentShowingStatusView.bringToFront();
	}
	
	/**
	 */
	public void clearStatus(){
		viewEmpty.setVisibility(View.GONE);
		viewErrorOfLocal.setVisibility(View.GONE);
		viewErrorOfServer.setVisibility(View.GONE);
		viewLoading.setVisibility(View.GONE);
		contentView.setVisibility(View.VISIBLE);
		currentShowingStatusView = null;
	}
}
