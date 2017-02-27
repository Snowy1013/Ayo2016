/*

 * Copyright © 2012-2015 Thunder Software Technology Co., Ltd.

 * All rights reserved.

 */

package com.snowy.demo.zgridview;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cowthan.sample.R;

import java.util.List;

/**
 * 用于GridView装载数据的适配器
 */
public class AppAdapter extends BaseAdapter {
    private List<ResolveInfo> mList;// 定义一个list对象
    private Context mContext;// 上下文
    private PackageManager pm;// 定义一个PackageManager对象

    /**
     * 构造方法
     *
     * @param context
     * @param list
     */
    public AppAdapter(Context context, List<ResolveInfo> list) {
        mContext = context;
        pm = context.getPackageManager();
        mList = list;
    }

    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_apps, parent, false);
        }
        ResolveInfo appInfo = mList.get(position);
        ImageView appicon = (ImageView) convertView.findViewById(R.id.iv_item_apps);
        TextView appname = (TextView) convertView.findViewById(R.id.tv_item_apps);
        appicon.setImageDrawable(appInfo.loadIcon(pm));
        appname.setText(appInfo.loadLabel(pm));
        return convertView;
    }

}
