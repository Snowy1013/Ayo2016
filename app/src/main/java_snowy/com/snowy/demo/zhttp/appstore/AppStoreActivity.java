package com.snowy.demo.zhttp.appstore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 16-12-22.
 */

public class AppStoreActivity extends BaseActivity {
    private PagerSlidingTab pst_appstore_indicator;
    private ViewPagerCompat vpc_appstore_list;
    private List<Fragment> appStoreFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_http_appstore);

        pst_appstore_indicator = findViewById(R.id.pst_appstore_indicator);
        vpc_appstore_list = findViewById(R.id.vpc_appstore_list);

        appStoreFragments = new ArrayList<>();
        appStoreFragments.add(new AllAppsFragment());
        appStoreFragments.add(new ClassifyAppsFragment());
        AppStoreAdapter adapter = new AppStoreAdapter(getActivity().getSupportFragmentManager());
        vpc_appstore_list.setAdapter(adapter);
        pst_appstore_indicator.setViewPager(vpc_appstore_list);
    }

    class AppStoreAdapter extends FragmentPagerAdapter{

        private String[] tabNames;
        public AppStoreAdapter(FragmentManager fm){
            super(fm);
            tabNames = new String[]{
                getActivity().getResources().getString(R.string.allapps),
                    getActivity().getResources().getString(R.string.classify)
            };
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public Fragment getItem(int position) {
            return appStoreFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }
    }
}
