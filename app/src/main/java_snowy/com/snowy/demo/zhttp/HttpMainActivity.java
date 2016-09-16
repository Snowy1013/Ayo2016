package com.snowy.demo.zhttp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.snowy.demo.zeventbus.EventBean;
import com.snowy.demo.zeventbus.EventBusSecondActivity;

import org.ayo.eventbus.EventBus;

/**
 * Created by zx on 16-9-16.
 */
public class HttpMainActivity extends BaseActivity implements View.OnClickListener {
    Button bt_http_download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_http_main);

        bt_http_download = findViewById(R.id.bt_http_download);

        bt_http_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_http_download:
                Intent intent = new Intent();
                intent.setClass(getActivity(), DownLoadFirstActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
