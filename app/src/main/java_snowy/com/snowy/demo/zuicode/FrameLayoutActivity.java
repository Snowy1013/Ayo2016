package com.snowy.demo.zuicode;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ts on 16-7-27.
 */
public class FrameLayoutActivity extends BaseActivity {

    public int currentColor = 0;
    public int[] colors = new int[]{
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6
    };

    public int[] views = new int[]{
            R.id.tv_frame01,
            R.id.tv_frame02,
            R.id.tv_frame03,
            R.id.tv_frame04,
            R.id.tv_frame05,
            R.id.tv_frame06
    };

    TextView[] textViews = new TextView[views.length];

    android.os.Handler hander = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x123){
                for (int i=0; i<views.length; i++) {
                    textViews[i].setBackgroundResource(colors[(currentColor + i) % views.length]);
                }
                currentColor ++ ;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_uicode_framelayout);

        for (int i=0; i<views.length; i++){
            textViews[i] = findViewById(views[i]);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                hander.sendEmptyMessage(0x123);
            }
        }, 0, 500);
    }
}
