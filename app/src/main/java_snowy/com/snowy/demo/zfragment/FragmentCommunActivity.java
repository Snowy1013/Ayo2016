package com.snowy.demo.zfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cowthan.sample.R;

/**
 * Created by snowy on 16/1/22.
 */
public class FragmentCommunActivity extends FragmentActivity implements
        CommunOneFragment.FOneBtnClickListener, CommunTwoFragment.FTwoBtnClickListener {

    private CommunTwoFragment cTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //生命周期测试
        System.out.println("我是Activity的生命周期函数------onCreat");

        setContentView(R.layout.ac_fragment_communicate);

        if(savedInstanceState == null) {    // 判断bundle是不是null，是为了在屏幕旋转是不让fragment再创建新对象
            CommunOneFragment communOneFragment = new CommunOneFragment();
            communOneFragment.setFOneBtnClickListener(this);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.frag_commun_content, communOneFragment, "One");
            transaction.commit();
        }

    }

    //响应Fragment一中按钮的点击事件
    @Override
    public void onFOneBtnClick() {

        System.out.println("我执行了没？？？？？");

        if (cTwo == null) {
            cTwo = new CommunTwoFragment();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frag_commun_content, cTwo, "Two"); //与管理Fragment回退栈相关
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //响应Fragment二中按钮的点击事件
    @Override
    public void onFTwoBtnClick() {
        CommunThreeFragment cThree = new CommunThreeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.frag_commun_content, cThree, "Three");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //生命周期测试
        System.out.println("我是Activity的生命周期函数------onDestroy");

    }

    @Override
    protected void onStart() {
        super.onStart();

        //生命周期测试
        System.out.println("我是Activity的生命周期函数------onStart");

    }

    @Override
    protected void onPause() {
        super.onPause();

        //生命周期测试
        System.out.println("我是Activity的生命周期函数------onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();

        //生命周期测试
        System.out.println("我是Activity的生命周期函数------onResume");

    }

    @Override
    protected void onStop() {
        super.onStop();

        //生命周期测试
        System.out.println("我是Activity的生命周期函数------onStop");

    }
}
