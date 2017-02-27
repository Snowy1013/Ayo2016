package com.snowy.demo.zhttp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.snowy.demo.zhttp.appstore.AppStoreActivity;
import com.snowy.demo.zhttp.download.DownLoadFirstActivity;

import org.ayo.app.base.ActivityAttacher;

/**
 * Created by zx on 16-9-16.
 */
public class HttpMainActivity extends BaseActivity implements View.OnClickListener {
    Button bt_http_download, bt_http_appstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_http_main);

        bt_http_download = findViewById(R.id.bt_http_download);
        bt_http_appstore = findViewById(R.id.bt_http_appstore);

        bt_http_download.setOnClickListener(this);
        bt_http_appstore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_http_download:
                ActivityAttacher.startActivity(getActivity(), DownLoadFirstActivity.class);
                break;
            case R.id.bt_http_appstore:
                ActivityAttacher.startActivity(getActivity(), AppStoreActivity.class);
                break;
        }
    }
}
