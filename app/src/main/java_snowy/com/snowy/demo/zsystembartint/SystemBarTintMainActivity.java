package com.snowy.demo.zsystembartint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by zx on 17-1-19.
 */

public class SystemBarTintMainActivity extends BaseActivity {
    private Button bt_systembar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_systembar_main);
        bt_systembar = findViewById(R.id.bt_systembar);
        bt_systembar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SystemBarTintActivity.class));
            }
        });
    }
}
