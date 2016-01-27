package com.snowy.demo.zviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by Snowy on 16/1/20.
 */
public class ViewPagerDemoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_viewpager_demo);
        Button btn_viewpager_main = findViewById(R.id.btn_viewpager_main);
        btn_viewpager_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewPagerMainActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
