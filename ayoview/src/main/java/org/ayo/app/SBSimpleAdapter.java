package org.ayo.app;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author seven
 *
 * @param <T>
 */
public abstract  class SBSimpleAdapter<T> extends BaseAdapter{

	//private int layoutId = 0;
	protected Activity context;
	protected List<T> list;
	
	/**
	 * @return
	 */
	protected abstract int getLayoutId();
	protected int getLayoutIdByType(int type){
		return getLayoutId();
	}
	/**
	 * @param convertView
	 * @return
	 */
	public abstract boolean isConvertViewUseable(View convertView, int position);
	
	public Context context(){
		return context;
	}
	
	protected void data(List<T> list){
		this.list = list;
	}
	
	public SBSimpleAdapter(Activity context, List<T> list){
		this.context = context;
		this.list = list;
		//this.layoutId = layoutId;
	}
	
	public void notifyDataSetChanged(List<T> list){
		this.list = list;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public T getItem(int arg0) {
		return list == null ? null : list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	
	/**
	 *
	 * @param holder
	 * @param convertView
	 * @param bean
	 * @param position
	 */
	public abstract void fillHolder(ViewHolder holder, View convertView, T bean, int position);
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if (convertView == null || !isConvertViewUseable(convertView, position)) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(getLayoutIdByType(getItemViewType(position)), null);
		}
		ViewHolder viewHolder = ViewHolder.getViewHolder(convertView);
		T t = list.get(position);
		viewHolder.setContentView(convertView);
		try {
			fillHolder(viewHolder, convertView, t, position);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}
	
	public static class ViewHolder {
		
		private SparseArray<View> viewHolder;
		private View view;
		
		public ViewHolder() {
			viewHolder = new SparseArray<View>();
		}
		public View findViewByID(int id, View view) {
			View holdedView = viewHolder.get(id);
			if (holdedView == null) {
				holdedView = view.findViewById(id);
				viewHolder.put(id, holdedView);
			}
			return holdedView;
		}
		
		public void setContentView(View v){
			view = v;
		}
		public View findViewById(int id){
			if(view != null){
				return findViewByID(id, view);
			}else{
				return null;
			}
		}
		
		public static ViewHolder getViewHolder(View view) {
			Object viewTag = view.getTag();
			if (viewTag != null && viewTag instanceof ViewHolder) {
				return (ViewHolder) viewTag;
			} else {
				viewTag = new ViewHolder();
				view.setTag(viewTag);
				return (ViewHolder) viewTag;
			}
		}

	}
}

