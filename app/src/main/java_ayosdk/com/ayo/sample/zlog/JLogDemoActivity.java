package com.ayo.sample.zlog;

import android.os.Bundle;
import android.view.View;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.lang.OS;

/**
 * Created by Administrator on 2016/1/19.
 */
public class JLogDemoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sdk_log);

        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/cowthan/Ayo2016/blob/master/doc/doc-log.md";
                OS.startBrowser(url);
            }
        });
    }
}
