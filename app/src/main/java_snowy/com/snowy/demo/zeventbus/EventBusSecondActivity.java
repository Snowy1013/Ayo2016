package com.snowy.demo.zeventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.eventbus.EventBus;

/**
 * Created by zx on 16-9-16.
 */
public class EventBusSecondActivity extends BaseActivity implements View.OnClickListener {
    Button bt_eb_sendtomain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_eventbus_second);

        bt_eb_sendtomain = findViewById(R.id.bt_eb_sendtomain);

        bt_eb_sendtomain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_eb_sendtomain:
                EventBus.getDefault().post(new EventBean("this message is ued to test"));
                break;
        }
    }
}
