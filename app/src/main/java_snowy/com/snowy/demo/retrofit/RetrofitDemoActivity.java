package com.snowy.demo.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by zx on 17-3-9.
 */

public class RetrofitDemoActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_retrofit_clickme;
    private TextView tv_retrofit_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_retrofit_demo);

        bt_retrofit_clickme = findViewById(R.id.bt_retrofit_clickme);
        tv_retrofit_result = findViewById(R.id.tv_retrofit_result);

        bt_retrofit_clickme.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_retrofit_clickme:
                getMovie();
                break;
        }
    }

    //进行网络请求,目标地址https://api.douban.com/v2/movie/top250?start=0&count=10
    private void getMovie(){

    }
}
