package com.cowthan.sample.ztmpl_listview;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cowthan.sample.R;

import org.ayo.VanGogh;
import org.ayo.app.OnViewClickListener;
import org.ayo.app.tmpl.BaseListAdapter;

import java.util.List;


/**
 * 文章列表适配器
 */
public class TmplAdapter extends BaseListAdapter<TmplBean> {

	protected Context mContext;
	protected OnViewClickListener<TmplBean> callback;
	
	public TmplAdapter(Activity context, List<TmplBean> list, OnViewClickListener<TmplBean> callback) {
		super(context, list, callback);
		mContext = context;
		this.callback = callback;
	}

	@Override
	public boolean isConvertViewUseable(View convertView, int position) {
		return true;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_tmpl_item_template;
	}
	
	
	@Override
	public void fillHolder(
			ViewHolder holder,
			final View convertView,final TmplBean a, final int position) {
		
		TextView tv_title = (TextView) holder.findViewById(R.id.tv_title);
		ImageView iv_photo = (ImageView) holder.findViewById(R.id.iv_photo);


		tv_title.setText(a.title);
		VanGogh.paper(iv_photo).paintSmallImage(a.cover_url, null);

		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				callback.onViewClicked(position, a, convertView);
			}
		});
	}

}