package com.snowy.demo.zfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by Snowy on 16/1/20.
 */
public class FragmentDynamicActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_frag_bottombar_first;
    private Button btn_frag_bottombar_second;
    private Button btn_frag_bottombar_third;
    private Button btn_frag_bottombar_fourth;

    private DynamicFirstFragment theFirst;
    private DynamicSecondFragment theSecond;
    private DynamicThirdFragment theThird;
    private DynamicFourthFragment theFourth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_fragment_dynamic);


        initView();

        // 设置默认的Fragment
        setDefaultFragment();
    }

    public void initView(){
        btn_frag_bottombar_first = findViewById(R.id.btn_frag_bottombar_first);
        btn_frag_bottombar_second = findViewById(R.id.btn_frag_bottombar_second);
        btn_frag_bottombar_third = findViewById(R.id.btn_frag_bottombar_third);
        btn_frag_bottombar_fourth = findViewById(R.id.btn_frag_bottombar_fourth);

        btn_frag_bottombar_first.setOnClickListener(this);
        btn_frag_bottombar_second.setOnClickListener(this);
        btn_frag_bottombar_third.setOnClickListener(this);
        btn_frag_bottombar_fourth.setOnClickListener(this);
    }

    public void setDefaultFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        //开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        theFirst = new DynamicFirstFragment();
        //使用当前布局替换content
        transaction.replace(R.id.flo_frag_content, theFirst);
        //提交事务
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction tran = fm.beginTransaction();

        switch (view.getId()) {
            case R.id.btn_frag_bottombar_first :
                if(theFirst == null) {
                    theFirst = new DynamicFirstFragment();
                }
                tran.replace(R.id.flo_frag_content, theFirst);
                break;
            case R.id.btn_frag_bottombar_second :
                if(theSecond == null) {
                    theSecond = new DynamicSecondFragment();
                }
                tran.replace(R.id.flo_frag_content, theSecond);
                break;
            case R.id.btn_frag_bottombar_third :
                if(theThird == null) {
                    theThird = new DynamicThirdFragment();
                }
                tran.replace(R.id.flo_frag_content, theThird);
                break;
            case R.id.btn_frag_bottombar_fourth :
                if(theFourth == null) {
                    theFourth = new DynamicFourthFragment();
                }
                tran.replace(R.id.flo_frag_content, theFourth);
                break;
        }
        tran.commit();
    }

}
