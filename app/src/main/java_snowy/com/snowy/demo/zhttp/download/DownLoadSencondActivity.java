/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.snowy.demo.zhttp.download;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.eventbus.EventBus;

import java.io.File;

/**
 * 类/接口描述
 *
 * @author wangjian
 * @date 2016/3/28.
 */
public class DownLoadSencondActivity extends BaseActivity implements View.OnClickListener {
    private ProgressBar mProgressBar2;
    private Button mButton2;
    private Button mButtonPause2;
    private Button mButtonCancel2;
    private Button mButtonResume2;
    private TextView mTvStatus2;

    private ProgressBar mProgressBar3;
    private Button mButton3;
    private Button mButtonPause3;
    private Button mButtonCancel3;
    private Button mButtonResume3;
    private TextView mTvStatus3;

    private static final String URL_360_ID = "url_360";
    private static final String URL_QQ_ID = "url_qq";

    private final int START = 0;
    private final int PAUSE = 1;
    private final int CONTINNUE = 2;
    private final int CANCEL = 3;
    private final int DOWNLOADING = 4;
    private final int ERROR = 5;
    private final int DOWNLOADSUCCESS = 6;

    private String url_360 = "http://msoftdl.360.cn/mobilesafe/shouji360/360safesis/360StrongBox_1.0.9.1008.apk";

    private String url_qq = "http://221.228.67.156/dd.myapp.com/16891/62B928C30FE677EDEEA9C504486444E9"
        + ".apk?mkey=5736f6098218f3cf&f=1b58&c=0&fsname=com.tencent.mobileqq_6.3.3_358.apk&p=.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_http_download2);
        initView();

        EventBus.getDefault().register(this);
    }

    private void initView() {
        //----------第一组下载----------------
        mProgressBar2 = findViewById(R.id.progressBar2);
        mButton2 = findViewById(R.id.button2);
        mButton2.setOnClickListener(this);

        mButtonPause2 = findViewById(R.id.buttonpause2);
        mButtonPause2.setOnClickListener(this);

        mButtonCancel2 = findViewById(R.id.buttoncancel2);
        mButtonCancel2.setOnClickListener(this);

        mButtonResume2 = findViewById(R.id.buttonresume2);
        mButtonResume2.setOnClickListener(this);

        mTvStatus2 = findViewById(R.id.tv_status2);

        //-------------第二组下载--------------

        mProgressBar3 = findViewById(R.id.progressBar3);
        mButton3 = findViewById(R.id.button3);
        mButton3.setOnClickListener(this);

        mButtonPause3 = findViewById(R.id.buttonpause3);
        mButtonPause3.setOnClickListener(this);

        mButtonCancel3 = findViewById(R.id.buttoncancel3);
        mButtonCancel3.setOnClickListener(this);

        mButtonResume3 = findViewById(R.id.buttonresume3);
        mButtonResume3.setOnClickListener(this);

        mTvStatus3 = findViewById(R.id.tv_status3);
    }

    @Override
    public void onClick(View v) {
        if (mButton2 == v) {
            download360();
        }
        else if (mButtonCancel2 == v) {
            startDownloadService(CANCEL, URL_360_ID, null, null);
        }
        else if (mButtonPause2 == v) {
            startDownloadService(PAUSE, URL_360_ID, null, null);
        }
        else if (mButtonResume2 == v) {
            startDownloadService(CONTINNUE, URL_360_ID, null, null);
        }

        //-----------------第二组下载
        if (mButton3 == v) {
            downloadQQ();
        }
        else if (mButtonCancel3 == v) {
            startDownloadService(CANCEL, URL_QQ_ID, null, null);
        }
        else if (mButtonPause3 == v) {
            startDownloadService(PAUSE, URL_QQ_ID, null, null);
        }
        else if (mButtonResume3 == v) {
            startDownloadService(CONTINNUE, URL_QQ_ID, null, null);
        }
    }

    private void download360() {
        Log.i("download", "start to download 360 in second");
        startDownloadService(START, URL_360_ID, url_360, "360safe.apk");
    }

    private void downloadQQ() {
        startDownloadService(START, URL_QQ_ID, url_qq, "qq.apk");
    }

    private void startDownloadService(int status, String urlQqId, String url_qq, String name) {
        Intent service = new Intent(getActivity(), DownloadService.class);
        service.putExtra("status", status);
        service.putExtra("id", urlQqId);
        service.putExtra("url", url_qq);
        service.putExtra("name", name);
        getActivity().startService(service);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(DownloadEvent event) {

        int status = event.getStatus();
        String id = event.getId();
        switch (id) {
            case URL_360_ID:
                String percent_downloading = event.getPercent();
                switch (status) {
                    case DOWNLOADING:
                        mProgressBar2.setProgress(Integer.parseInt(percent_downloading));
                        mTvStatus2.setText("正在下载..." + percent_downloading + "%");
                        break;
                    case PAUSE:
                        mTvStatus2.setText("下载已暂停,已下载：" + percent_downloading + "%");
                        break;
                    case CANCEL:
                        mTvStatus2.setText("下载已取消");
                        mProgressBar2.setProgress(0);
                        break;
                    case DOWNLOADSUCCESS:
                        File file = event.getFile();
                        mTvStatus2.setText("下载完成 path：" + file.getAbsolutePath());
                        break;
                    case ERROR:
                        int errorCode = event.getErrorCode();
                        mTvStatus2.setText("下载失败errorCode=" + errorCode);
                        break;
                }
                break;
            case URL_QQ_ID:
                String percent = event.getPercent();
                switch (status) {
                    case DOWNLOADING:
                        mProgressBar3.setProgress(Integer.parseInt(percent));
                        mTvStatus3.setText("正在下载..." + percent + "%");
                        break;
                    case PAUSE:
                        mTvStatus3.setText("下载已暂停,已下载：" + percent + "%");
                        break;
                    case CANCEL:
                        mTvStatus3.setText("下载已取消");
                        mProgressBar3.setProgress(0);
                        break;
                    case DOWNLOADSUCCESS:
                        File file = event.getFile();
                        mTvStatus3.setText("下载完成 path：" + file.getAbsolutePath());
                        break;
                    case ERROR:
                        int errorCode = event.getErrorCode();
                        mTvStatus3.setText("下载失败errorCode=" + errorCode);
                        break;
                }
                break;
        }
    }
}
