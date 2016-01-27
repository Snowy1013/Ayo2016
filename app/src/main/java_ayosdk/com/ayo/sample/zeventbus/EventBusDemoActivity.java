package com.ayo.sample.zeventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.eventbus.EventBus;

/**
 * Created by Administrator on 2016/1/26.
 */
public class EventBusDemoActivity extends BaseActivity{

    TextView tv_receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_eventbus);

        Button btn_post = findViewById(R.id.btn_post);
        tv_receiver = findViewById(R.id.tv_reveiver);


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
        EventBus.getDefault().register(EventBusDemoActivity.this);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnyEvent e = new AnyEvent();
                e.time = System.currentTimeMillis() + "";
                EventBus.getDefault().post(e);
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public static class AnyEvent{
        public String time;
    }

    public void onEventMainThread(AnyEvent e){
        tv_receiver.setText("收到个事件：" + e.time);
    }
}
