package com.ayo.sample.zinject;

import android.os.Bundle;
import android.view.View;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.lang.OS;

/**
 * Created by Administrator on 2016/1/27.
 */
public class InjectDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sdk_inject);
        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/cowthan/Ayo2016/blob/master/doc/doc-inject.md";
                OS.startBrowser(url);
            }
        });
    }
}
