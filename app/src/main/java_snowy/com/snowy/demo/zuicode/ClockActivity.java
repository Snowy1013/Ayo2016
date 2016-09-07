package com.snowy.demo.zuicode;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by ts on 16-7-27.
 */
public class ClockActivity extends BaseActivity {
    Button bt_clock;
    Chronometer chronometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_uicode_clock);

        bt_clock = (Button) findViewById(R.id.bt_clock);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        bt_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) bt_clock.getText();
                if(str.equals("启动")){
                    bt_clock.setText("停止");
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }else {
                    bt_clock.setText("启动");
                    chronometer.stop();
                }
            }
        });
    }
}
