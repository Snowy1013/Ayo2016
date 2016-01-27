package com.ayo.opensource.zlayout;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cowthan.sample.R;

import org.ayo.app.orig.SBActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/25.
 */
public class DemoScrollDownActivity extends SBActivity {

    private ScrollDownLayout mScrollDownLayout;
    private ArrayList<Girl> mAllGirlList;
    private TextView mGirlDesText;
    private TextView tv_aiyou;

    private MainPagerAdapter.OnClickItemListenerImpl mOnClickItemListener = new MainPagerAdapter.OnClickItemListenerImpl() {
        @Override
        public void onClickItem(View item, int position) {
            Toast.makeText(getActivity(), "You click at" + position, Toast.LENGTH_SHORT).show();

            if (mScrollDownLayout.getCurrentStatus() == ScrollDownLayout.Status.OPENED) {
                mScrollDownLayout.scrollToClose();
            }
        }
    };

    private ScrollDownLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollDownLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            float origSize = 14;
            float currentSize = (int) (origSize * (1 + currentProgress));
            tv_aiyou.setTextSize(currentSize);
        }

        @Override
        public void onScrollFinished(ScrollDownLayout.Status currentStatus) {
            if(currentStatus.equals(ScrollDownLayout.Status.EXIT)){
                finish();
            }
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mGirlDesText.setText(mAllGirlList.get(position).getDesContent());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scroll_down_menu_main, menu);
        menu.findItem(R.id.action_to_exit).setChecked(mScrollDownLayout.isSupportExit());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_close) {
            mScrollDownLayout.setToClosed();
            return true;
        }else if(item.getItemId() == R.id.action_to_exit){
            mScrollDownLayout.setIsSupportExit(!mScrollDownLayout.isSupportExit());
            getActivity().invalidateOptionsMenu();
            return true;
        }else if(item.getItemId() == R.id.action_open){
            mScrollDownLayout.setToOpen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_scroll_down);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mGirlDesText = (TextView)findViewById(R.id.text_view);
        mScrollDownLayout = (ScrollDownLayout) findViewById(R.id.scroll_down_layout);

        mScrollDownLayout.setMinOffset(0);
        mScrollDownLayout.setMaxOffset(800);
        mScrollDownLayout.setExitOffset(1674);
        mScrollDownLayout.setToOpen();
        mScrollDownLayout.setIsSupportExit(true);
        mScrollDownLayout.setAllowHorizontalScroll(true);
        mScrollDownLayout.setOnScrollChangedListener(mOnScrollChangedListener);

        tv_aiyou = (TextView) findViewById(R.id.tv_aiyou);
        float origSize = 14;
        tv_aiyou.setTextSize(origSize);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getActivity());
        mainPagerAdapter.setOnClickItemListener(mOnClickItemListener);
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOnPageChangeListener(mOnPageChangeListener);
        initGirlUrl();
        mainPagerAdapter.initViewUrl(mAllGirlList);
        mGirlDesText.setText(mAllGirlList.get(0).getDesContent());
    }


    private void initGirlUrl() {
        mAllGirlList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Girl girl = new Girl();
            girl.setImageUrl(Contstants.ImageUrl[i]);
            girl.setDesContent(Contstants.DesContent[i]);
            mAllGirlList.add(girl);
        }
    }

}
