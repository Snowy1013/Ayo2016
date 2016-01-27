package com.snowy.demo.zviewpager;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cowthan.sample.R;

import org.ayo.view.listview.pulltorefresh.PullToRefreshBase;
import org.ayo.view.listview.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Snowy on 16/1/20.
 */
public class ViewPagerMainActivity extends Activity {

    private ViewPager viewpager_main_body;
    private DrawerLayout viewPager_drawLayout;
    private ListView draw_left_content;
    private PullToRefreshListView draw_left_ptrListView;
    private ArrayAdapter<String> listViewAdapter;
    private ActionBarDrawerToggle barDrawerToggle;
    private List<View> viewList;
    private LinkedList<String> planetTitles;
    private boolean isDrawOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_viewpager_main);

        initViewPager();
//        initListView();
        initPullToRfreshListView();
        initDrawLayout();
    }

    //初始化侧滑菜单中的下拉刷新菜单
    public void initPullToRfreshListView() {
        String[] planetArrays = getResources().getStringArray(R.array.planetArray);
        planetTitles = new LinkedList<String>();
        planetTitles.addAll(Arrays.asList(planetArrays));//ListView中需要填充的内容
        draw_left_ptrListView = (PullToRefreshListView) findViewById(R.id.draw_left_ptrlistview);//找到PullToRefreshListView控件
        listViewAdapter = new ArrayAdapter<String>(this, R.layout.draw_list_item, R.id.draw_list_text, planetTitles);//创建Adapter对象

        //绑定Adapter有两种方法,选择其一
        //方法一
//        draw_left_ptrListView.setAdapter(listViewAdapter);
        //方法二
        ListView refreshableView = draw_left_ptrListView.getRefreshableView();
        refreshableView.setAdapter(listViewAdapter);

        /*
         * Mode.BOTH：同时支持上拉下拉
         * Mode.PULL_FROM_START：只支持下拉Pulling Down
         * Mode.PULL_FROM_END：只支持上拉Pulling Up
         *
         * 如果Mode设置成Mode.BOTH，需要设置刷新Listener为OnRefreshListener2，并实现onPullDownToRefresh()、onPullUpToRefresh()两个方法。
         * 如果Mode设置成Mode.PULL_FROM_START或Mode.PULL_FROM_END，需要设置刷新Listener为OnRefreshListener，同时实现onRefresh()方法。
         * 当然也可以设置为OnRefreshListener2，但是Mode.PULL_FROM_START的时候只调用onPullDownToRefresh()方法，
         * Mode.PULL_FROM的时候只调用onPullUpToRefresh()方法.
         */
        draw_left_ptrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        //设置刷新的监听事件
        draw_left_ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                //刷新的动作在后台执行
                new GetDataTask().execute();
            }
        });


        //设置每个条目的点击事件
        draw_left_ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //运行结果发现PullToRfreshListView的position是从1开始的，planetTitles的脚标index是从0开始的，所以需要position-1
                Toast.makeText(ViewPagerMainActivity.this, "I'm " + planetTitles.get(position - 1), Toast.LENGTH_SHORT).show();
                viewPager_drawLayout.closeDrawer(draw_left_ptrListView);
            }
        });
    }

    //初始化侧滑菜单中的ListView
   /* public void initListView() {
        planetTitles = getResources().getStringArray(R.array.planetArray);//ListView中需要填充的内容
        draw_left_content = (ListView) findViewById(R.id.darw_left_content);//抽屉中的ListView
        draw_left_content.setAdapter(new ArrayAdapter<String>(this, R.layout.draw_list_item, R.id.draw_list_text, planetTitles));
        draw_left_content.setOnItemClickListener(new AdapterView.OnItemClickListener() { // 设置ListView条目点击事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                draw_left_content.setItemChecked(position, true);
                Toast.makeText(ViewPagerMainActivity.this, "I'm " + planetTitles[position], Toast.LENGTH_SHORT).show();
                viewPager_drawLayout.closeDrawer(draw_left_content);
            }
        });
    }*/

    public void initDrawLayout() {
        isDrawOpen = false;
        viewPager_drawLayout = (DrawerLayout) findViewById(R.id.viewpager_drawlayout);//抽屉的view

        // 设置抽屉打开时，主要内容区被自定义阴影覆盖
        viewPager_drawLayout.setDrawerShadow(R.drawable.shape_loading_shadow, GravityCompat.START);

        // 设置ActionBar可见，并且切换菜单和内容视图
        getActionBar().setDisplayHomeAsUpEnabled(true); //给home-icon的左边加上一个返回的图标
        getActionBar().setHomeButtonEnabled(true); //使home-icon可点击

        viewPager_drawLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    //初始化主要界面
    private void initViewPager() {
        viewpager_main_body = (ViewPager) findViewById(R.id.viewpager_main_body);

        viewList = new ArrayList<View>();

        //填充界面
        LayoutInflater inflater = getLayoutInflater();
        View viewPager_one = inflater.inflate(R.layout.viewpager_one, null);
        viewList.add(viewPager_one);
        View viewPager_two = inflater.inflate(R.layout.viewpager_two, null);
        viewList.add(viewPager_two);
        View viewPager_three = inflater.inflate(R.layout.viewpager_three, null);
        viewList.add(viewPager_three);

        viewpager_main_body.setAdapter(new MyViewPagerAdapter(viewList));
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        //后台处理部分
        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }

            String strAdd = "Added after refresh...I add";
            return strAdd;
        }

        ////这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
        //根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
        @Override
        protected void onPostExecute(String result) {
            //在头部增加新内容
            planetTitles.addFirst(result);
            //通知程序数据集已经改变，如果不做通知，那么将不会刷新planetTitles的集合
            listViewAdapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            draw_left_ptrListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    /**
     *加载菜单栏
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawlayout_left_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.draw_left_menu:
                if(isDrawOpen){
                    viewPager_drawLayout.closeDrawer(draw_left_ptrListView);
                } else {
                    viewPager_drawLayout.openDrawer(draw_left_ptrListView);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
