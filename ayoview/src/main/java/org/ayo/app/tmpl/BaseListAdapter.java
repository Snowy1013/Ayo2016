package org.ayo.app.tmpl;

import android.app.Activity;

import org.ayo.app.OnViewClickListener;
import org.ayo.app.SBSimpleAdapter;

import java.util.List;

public abstract class BaseListAdapter<T> extends SBSimpleAdapter<T> {

	public BaseListAdapter(Activity context, List<T> list, OnViewClickListener<T> callback) {
		super(context, list);
	}
}