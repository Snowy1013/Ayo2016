package com.ayo.sample.zasync;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.lang.Async;

/**
 * Created by Administrator on 2016/1/19.
 */
public class AsyncDemoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sdk_async);

        final TextView tv_info = findViewById(R.id.tv_info);

        findViewById(R.id.btn_post1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_info.setText("子线程运行...");
                Async.newTask(new Runnable() {
                    @Override
                    public void run() {
                        ///后台任务，直接使用AsyncTask默认的线程管理，怎么个行为？
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).post(new Runnable() {
                    @Override
                    public void run() {
                        ///主线程回调
                        tv_info.setText("主线程回调！");
                    }
                }).go();
            }
        });

        findViewById(R.id.btn_post2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_info.setText("主线程任务延时...");
                Async.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_info.setText("主线程任务执行！");
                    }
                }, 1000);
            }
        });
    }
}
