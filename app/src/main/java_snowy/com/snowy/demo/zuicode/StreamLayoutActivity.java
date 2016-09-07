package com.snowy.demo.zuicode;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.snowy.demo.zuicode.zutil.LoadDataFromAssets;
import com.snowy.demo.zuicode.zview.ScrollView;

import java.io.IOException;

/**
 * Created by zx on 16-8-15.
 */
public class StreamLayoutActivity extends BaseActivity {
    ScrollView sv_streaml;
    LinearLayout ll_stream1;
    LinearLayout ll_stream2;
    LinearLayout ll_stream3;

    private int imgvWidth;

    //加载次数，每加载一次，pageIndex++
    private int pageIndex = 0;
    //每次加载的数量
    private int pageSize = 21;


    private String[] imgNames;
    //布局有三个，放到数组里，以便更新UI的时候使用
    private LinearLayout[] lls = new LinearLayout[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_uicode_stream);

        init();
    }

    private void init() {
        sv_streaml = (ScrollView) findViewById(R.id.sv_stream);

        //用来盛放屏幕信息
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        //设置每个LinearLayout的宽度为整个屏幕宽度的1/3
        imgvWidth = outMetrics.widthPixels / 3;

        ll_stream1 = (LinearLayout) findViewById(R.id.ll_stream1);
        ll_stream2 = (LinearLayout) findViewById(R.id.ll_stream2);
        ll_stream3 = (LinearLayout) findViewById(R.id.ll_stream3);
        lls[0] = ll_stream1;
        lls[1] = ll_stream2;
        lls[2] = ll_stream3;

        try {
            imgNames = getActivity().getAssets().list("imgs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //第一次加载图片
        loadImage();
        sv_streaml.setOnScrollListener(new ScrollView.OnScrollListener(){
            @Override
            public void loadmore() {
                Log.i("StreamLayoutActivity", "pageSize * pageIndex = " + pageSize * pageIndex + ",  imgNames.length = " + imgNames.length);
                if (pageSize * pageIndex >= imgNames.length) {
//                    Toast.makeText(StreamLayoutActivity.this, "数据加载完成", Toast.LENGTH_SHORT).show();
                } else {
                    loadImage();
                }
            }
        });
    }

    //进行图片加载
    private void loadImage() {
        //如果加载的数量比所有文件少，每次加载18张
        for (int i = pageIndex * pageSize; i < pageSize * pageIndex + pageSize
                && i < imgNames.length; i ++) {
            //加载图片，实例化一个ImageView
            ImageView imageView = new ImageView(getActivity());
            //宽度是屏幕的1/3
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imgvWidth,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);
            //加载图片资源
            LoadDataFromAssets.loadImage(getActivity(), imgNames[i], imageView);
            //每次往不同的LinearLayout放置一个ImageView
            lls[i%3].addView(imageView);
        }
        pageIndex ++;
    }
}

