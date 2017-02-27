package com.snowy.demo.zgridview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 17-1-20.
 */

public class GridViewMainActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private GridView gv_ui_gridview;
    public int column;
    public int appPageSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_uicode_gridview_main);
        gv_ui_gridview = findViewById(R.id.gv_ui_gridview);
        getScreenDp();
        try {
            initViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取系统所有的应用程序，并根据APP_PAGE_SIZE生成相应的GridView页面
     */
    public void initViews() throws Exception {
        ArrayList<GridView> array = new ArrayList<GridView>();
        // get all apps
        List<ResolveInfo> apps = getAllApps(getActivity()) ;
        gv_ui_gridview.setAdapter(new AppAdapter(getActivity(), apps));
        gv_ui_gridview.setOnItemClickListener(this);
        gv_ui_gridview.setOnItemLongClickListener(this);
    }

    /**
     * 获取luancher页面的以下载app
     *
     * @param context
     * @return
     */
    public static List<ResolveInfo> getAllApps(Context context) {

        PackageManager pManager = context.getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // get all apps
        List<ResolveInfo> applist = pManager.queryIntentActivities(mainIntent, 0);
        return applist;
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                ResolveInfo info = (ResolveInfo) parent.getItemAtPosition(position);
                // 该应用的包名
                String pkgName = info.activityInfo.packageName;
                // 应用的主activity类
                String launchActivity = info.activityInfo.name;
                ComponentName componentName = new ComponentName(pkgName, launchActivity);
                Intent intent = new Intent();
                intent.setComponent(componentName);
                getActivity().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                ResolveInfo info = (ResolveInfo) parent.getItemAtPosition(position);
                // 该应用的包名
                String pkgName = info.activityInfo.packageName;

                if (pkgName.equals("com.thundersoft.mdm")) {
                    Toast.makeText(getActivity(), "不可卸载", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Uri packageURI = Uri.parse("package:" + pkgName);
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(packageURI);
                getActivity().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResolveInfo info = (ResolveInfo) parent.getItemAtPosition(position);
        String pkgName = info.activityInfo.packageName;
        String laucherActivity = info.activityInfo.name;
        ComponentName componentName = new ComponentName(pkgName, laucherActivity);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        getActivity().startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ResolveInfo info = (ResolveInfo) parent.getItemAtPosition(position);
        String pkgName = info.activityInfo.packageName;
        Uri uri = Uri.parse("package:" + pkgName);
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(uri);
        getActivity().startActivity(intent);
        return true;
    }

    /****
     * 根据屏幕尺寸设置每行icon的个数
     */
    private void getScreenDp() {
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
        width = px2dip(getActivity(), width);
        height = px2dip(getActivity(), height);
        int num = width / 90; // 设置每行图标个数
        switch (num) {
            case 2:
            case 3:
            case 4:
                column = 4;
                break;
            case 5:
            case 6:
                column = 5;
                break;
            case 7:
            case 8:
            case 9:
                column = 6;
                break;
            default:
                column = 4;
                break;
        }
//        appPageSize = line * column;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
