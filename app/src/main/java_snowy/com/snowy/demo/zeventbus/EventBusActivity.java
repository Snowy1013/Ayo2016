package com.snowy.demo.zeventbus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.eventbus.EventBus;

/**
 * Created by zx on 16-9-16.
 */
public class EventBusActivity extends BaseActivity implements View.OnClickListener {
    Button bt_eb_toSecond;
    TextView tv_eb_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_eventbus_main);

        //在接收消息的页面注册EventBus
        EventBus.getDefault().register(this);

        bt_eb_toSecond = findViewById(R.id.bt_eb_toSecond);
        tv_eb_msg = findViewById(R.id.tv_eb_msg);

        bt_eb_toSecond.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_eb_toSecond:
                ActivityAttacher.startActivity(getActivity(), EventBusSecondActivity.class);
                break;
        }
    }

    public void onEvent(EventBean event) {

        String msg = "onEvent收到了消息：" + event.getMsg();
        Log.i("EventBusActivity", "onEvent: event msg =" + msg + "THreadId: " + android.os.Process.myTid());
    }

    public void onEventAsync(EventBean event) {

        String msg = "onEventAsync收到了消息：" + event.getMsg();
        Log.i("EventBusActivity", "onEventAsync: event msg =" + msg  + "THreadId: "+ android.os.Process.myTid());
    }

    public void onEventMainThread(EventBean event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.i("EventBusActivity", "onEventMainThread: event msg =" + msg + "THreadId: " + android.os.Process.myTid());
        tv_eb_msg.setText(msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
