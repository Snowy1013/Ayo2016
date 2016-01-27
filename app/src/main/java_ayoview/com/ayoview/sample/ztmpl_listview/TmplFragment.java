package com.ayoview.sample.ztmpl_listview;

import android.view.View;

import com.cowthan.sample.R;
import com.cowthan.sample.Utils;

import org.ayo.Configer;
import org.ayo.app.tmpl.BaseListAdapter;
import org.ayo.app.tmpl.BaseListViewFragment;
import org.ayo.app.tmpl.Condition;
import org.ayo.http.HttpProblem;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.ResponseModel;
import org.ayo.lang.Lang;
import org.ayo.test.TestHttper;
import org.ayo.test.TestOrder;
import org.ayo.test.TestOrderList;
import org.ayo.view.listview.pulltorefresh.PullToRefreshListView;
import org.ayo.view.toast.Toaster;

import java.util.ArrayList;
import java.util.List;

public class TmplFragment extends BaseListViewFragment<TmplBean> {

	public static final String CACHE_KEY = "dsfsdf";
	
	@Override
    protected int getLayoutId() {
        return R.layout.tmpl_fragment_list;
    }
	
	@Override
	public List<TmplBean> getCache() {
		if(needCache()){
			List<TmplBean> list = Configer.getList(TmplFragment.CACHE_KEY, TmplBean.class);
			return list;
		}else{
			return null;
		}
	}

	@Override
	public void loadOrderList() {
		final TmplCondition cond = (TmplCondition) getCondition();
		BaseHttpCallback<TestOrderList> callback = new BaseHttpCallback<TestOrderList>() {
			
			@Override
			public void onFinish(boolean isSuccess, HttpProblem problem,
					ResponseModel resp, TestOrderList t) {
				if(isSuccess){
					
					//--
					if(t == null){
						onLoadOk(null);
						return;
					}
					
					//--
					List<TmplBean> list = new ArrayList<TmplBean>();
					for(TestOrder o : t.artlist){
						TmplBean b = new TmplBean();
						b.title = o.title;
						b.cover_url = o.cover_url;
						list.add(b);
					}
					onLoadOk(list);
					
					//---
					if(needCache()){
						if(cond.pageNow == 0 && Lang.isNotEmpty(list)){
							//是第一页，且有数据，缓存下来
							Configer.putObject(TmplFragment.CACHE_KEY, list);
						}
					}
					
				}else{
					onLoadFail(Utils.parseErrorType(problem), true);
				}
				
			}

		};
		
		TestHttper.getArticle("haha", cond.pageNow, callback, MyHttpResponse.class);
	}

	@Override
	public Condition initCondition() {
		TmplCondition cnd = new TmplCondition();
		cnd.reset();
		return cnd;
	}

	@Override
	public boolean needCache() {
		return false;
	}

	@Override
	public BaseListAdapter<TmplBean> initAdapter() {
		return new TmplAdapter(getActivity(), getList(), this);
	}

	@Override
	public PullToRefreshListView findListView() {
		return (PullToRefreshListView) root.findViewById(R.id.lv_articles);
	}

	@Override
	public void onViewClicked(int position, TmplBean t, View targetView) {
		int id = targetView.getId();
		if(id == R.id.item_root){
			//item被点击
			Toaster.toastLong("点击-" + position);
		}
	}

}
