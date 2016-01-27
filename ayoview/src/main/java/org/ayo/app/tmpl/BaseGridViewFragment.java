package org.ayo.app.tmpl;


import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import org.ayo.app.OnViewClickListener;
import org.ayo.app.StatusUIMgmr;
import org.ayo.app.orig.SBFragment;
import org.ayo.view.listview.pulltorefresh.PullToRefreshBase;
import org.ayo.view.listview.pulltorefresh.PullToRefreshGridView;
import org.ayo.view.listview.pulltorefresh.PullToRefreshListView;

import java.util.Collection;
import java.util.List;

import genius.android.view.R;


public abstract class BaseGridViewFragment<T> extends SBFragment implements OnViewClickListener<T> {

	private PullToRefreshGridView lv_articles;

	protected List<T> list;
	private BaseListAdapter<T> mAdapter;

	protected boolean isLoadMore = false;
	private boolean isFirstCome = true;
	private StatusUIMgmr statusMgmr;

	protected Handler mHandler;


	public BaseGridViewFragment(){
	}
	
    public abstract PullToRefreshGridView findListView();

    @Override
    public void onCreateView(View root) {
    	mHandler = new Handler();
    	lv_articles = findListView();
    	condition = initCondition();
        initListView();
    }

	private void initListView() {
		///绑定presenter
		
		///状态切换
		statusMgmr = StatusUIMgmr.attach(lv_articles, callback);
		statusMgmr.setEmptyLayout(R.layout.genius_view_empty);
		statusMgmr.setErrorLayout(R.layout.genius_view_error_local, R.layout.genius_view_error_server);
		statusMgmr.setLoadingLayout(R.layout.genius_view_loading);
		
		///header和footer
		
		///事件响应
		lv_articles.setMode(PullToRefreshBase.Mode.BOTH);
		lv_articles.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				isLoadMore = false;
				condition.onPullDown();
				loadOrderList();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				isLoadMore = true;
				condition.onPullUp();
				loadOrderList();
			}
		});
		
		///默认adapter
		mAdapter = initAdapter();
		lv_articles.setAdapter(mAdapter);
	}
	
	

	private StatusUIMgmr.OnStatusViewAddedCallback callback = new StatusUIMgmr.OnStatusViewAddedCallback() {
		
		@Override
		public void onLoadingViewAdded(View loadingView) {
			
		}
		
		public void onErrorViewAdded(View errorOfLocalView, View errorOfServerView) {
		}
		
		@Override
		public void onEmptyViewAdded(View emptyView) {
			
		}
	};
	
	///-----------------------------
	public void onLoadFinish(){
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				lv_articles.onRefreshComplete();
			}
		}, 200);
	}

	/**
	 * 错误提示：
	 * 原则是：只要界面上有数据，就不会切换页面，
	 * 但是如果页面上本来就没数据，那就得按照错误的分类来，
	 * @param reason
	 * @param forceChageUI  是否不管当前页面是什么，都强制切换到错误页，一般应该是false
	 */
	public void onLoadFail(int reason, boolean forceChageUI){
		onLoadFinish();
		if(!isEmpty(list) && !forceChageUI){
			//界面不是空，也不强制切换UI，则什么都不干
			//Toaster.toastLong(errorInfo);
		}else{
			if(reason == ErrorReason.LOCAL){
				statusMgmr.showErrorOfLocal();
			}else if(reason == ErrorReason.SERVER){
				statusMgmr.showErrorOfServer();
			}else{
				statusMgmr.showErrorOfServer();
			}
		}
	}
	
	public void onLoadOk(List<T> data){
		onLoadFinish();
		if(isLoadMore && isEmpty(data)){
			///没有更多页了，并且这一页也是空
			//Toaster.toastLong("没有下一页了");
			return;
		}
		
		if(isLoadMore){
			this.list = (List<T>) combine(this.list, data);
		}else{
			this.list = data;
		}
		
		if(isEmpty(this.list)){
			statusMgmr.showEmpty();
		}else{
			statusMgmr.clearStatus();
			mAdapter.notifyDataSetChanged(list);
		}
		
	}

	
	///----
	/**
	 * 加载列表：从服务器
	 */
	public abstract void loadOrderList();
	
	/**
	 * 加载列表：从本地缓存
	 * @return
	 */
	public abstract List<T> getCache();
	public abstract boolean needCache();
	
	private Condition condition;
	public abstract Condition initCondition();
	
	
	public abstract BaseListAdapter<T> initAdapter();
	
	public List<T> getList(){
		return this.list;
	}
	
	public Condition getCondition(){
		return this.condition;
	}


	private <T> Collection<T> combine(Collection<T> c1,
											Collection<T> c2) {
		if (c1 == null && c2 == null)
			return null;
		if (c1 == null)
			return c2;
		if (c2 == null)
			return c1;
		c1.addAll(c2);
		return c1;
	}

	public <T> boolean isEmpty(List<T> list){
		return list == null || list.size() == 0;
	}
}

