/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.snowy.demo.zhttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cowthan.sample.R;
import com.snowy.okhttp.download.DownloadManager;

import org.ayo.eventbus.EventBus;

import java.io.File;

/**
 *
 */
public class DownLoadFirstActivity extends Activity implements View.OnClickListener {
    private ProgressBar mProgressBar;
    private Button mButton;
    private Button mButtonPause;
    private Button mButtonCancel;
    private Button mButtonResume;
    private TextView mTvStatus;

    private ProgressBar mProgressBar1;
    private Button mButton1;
    private Button mButtonPause1;
    private Button mButtonCancel1;
    private Button mButtonResume1;
    private TextView mTvStatus1;

    private Button bt_download_tosencond;

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
        setContentView(R.layout.ac_http_download1);
        initView();

        EventBus.getDefault().register(this);
    }

    private void initView() {
        //----------第一组下载----------------
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);

        mButtonPause = (Button) findViewById(R.id.buttonpause);
        mButtonPause.setOnClickListener(this);

        mButtonCancel = (Button) findViewById(R.id.buttoncancel);
        mButtonCancel.setOnClickListener(this);

        mButtonResume = (Button) findViewById(R.id.buttonresume);
        mButtonResume.setOnClickListener(this);

        mTvStatus = (TextView) findViewById(R.id.tv_status);

        //-------------第二组下载--------------

        mProgressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);

        mButtonPause1 = (Button) findViewById(R.id.buttonpause1);
        mButtonPause1.setOnClickListener(this);

        mButtonCancel1 = (Button) findViewById(R.id.buttoncancel1);
        mButtonCancel1.setOnClickListener(this);

        mButtonResume1 = (Button) findViewById(R.id.buttonresume1);
        mButtonResume1.setOnClickListener(this);

        mTvStatus1 = (TextView) findViewById(R.id.tv_status1);

        bt_download_tosencond = (Button)findViewById(R.id.bt_download_tosencond);
        bt_download_tosencond.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mButton == v) {
            download360();
        }
        else if (mButtonCancel == v) {
            startDownloadService(CANCEL, URL_360_ID, null, null);
        }
        else if (mButtonPause == v) {
            startDownloadService(PAUSE, URL_360_ID, null, null);
        }
        else if (mButtonResume == v) {
            startDownloadService(CONTINNUE, URL_360_ID, null, null);
        }

        //-----------------第二组下载
        if (mButton1 == v) {
            downloadQQ();
        }
        else if (mButtonCancel1 == v) {
            startDownloadService(CANCEL, URL_QQ_ID, null, null);
        }
        else if (mButtonPause1 == v) {
            startDownloadService(PAUSE, URL_QQ_ID, null, null);
        }
        else if (mButtonResume1 == v) {
            startDownloadService(CONTINNUE, URL_QQ_ID, null, null);
        }

        if (bt_download_tosencond == v) {
            Intent intent = new Intent(this, DownLoadSencondActivity.class);
            startActivity(intent);
        }
    }

    private void download360() {
        Log.i("download", "start to download 360");
        startDownloadService(START, URL_360_ID, url_360, "360safe.apk");
    }

    private void downloadQQ() {
        startDownloadService(START, URL_QQ_ID, url_qq, "qq.apk");
    }

    private void startDownloadService(int status, String urlQqId, String url_qq, String name) {
        Intent service = new Intent(this, DownloadService.class);
        service.putExtra("status", status);
        service.putExtra("id", urlQqId);
        service.putExtra("url", url_qq);
        service.putExtra("name", name);
        startService(service);
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
                        mProgressBar.setProgress(Integer.parseInt(percent_downloading));
                        mTvStatus.setText("正在下载..." + percent_downloading + "%");
                        break;
                    case PAUSE:
                        mTvStatus.setText("下载已暂停,已下载：" + percent_downloading + "%");
                        break;
                    case CANCEL:
                        mTvStatus.setText("下载已取消");
                        mProgressBar.setProgress(0);
                        break;
                    case DOWNLOADSUCCESS:
                        File file = event.getFile();
                        mTvStatus.setText("下载完成 path：" + file.getAbsolutePath());
                        break;
                    case ERROR:
                        int errorCode = event.getErrorCode();
                        mTvStatus.setText("下载失败errorCode=" + errorCode);
                        break;
                }
                break;
            case URL_QQ_ID:
                String percent = event.getPercent();
                switch (status) {
                    case DOWNLOADING:
                        mProgressBar1.setProgress(Integer.parseInt(percent));
                        mTvStatus1.setText("正在下载..." + percent + "%");
                        break;
                    case PAUSE:
                        mTvStatus1.setText("下载已暂停,已下载：" + percent + "%");
                        break;
                    case CANCEL:
                        mTvStatus1.setText("下载已取消");
                        mProgressBar1.setProgress(0);
                        break;
                    case DOWNLOADSUCCESS:
                        File file = event.getFile();
                        mTvStatus1.setText("下载完成 path：" + file.getAbsolutePath());
                        break;
                    case ERROR:
                        int errorCode = event.getErrorCode();
                        mTvStatus1.setText("下载失败errorCode=" + errorCode);
                        break;
                }
                break;
        }
    }
}
