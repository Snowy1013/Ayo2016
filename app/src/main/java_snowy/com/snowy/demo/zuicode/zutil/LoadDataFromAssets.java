package com.snowy.demo.zuicode.zutil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

/**
 * 从assets中加载数据
 * Created by zx on 16-8-25.
 */
public class LoadDataFromAssets {
    public static void loadImage (final Activity activity, final String name, final ImageView imamgView){
        new Thread(){
            @Override
            public void run() {
                try {
                    //从assets/imgs中获取图片资源，传入图片名称
                    InputStream ins = activity.getAssets().open("imgs/" + name);
                    final Bitmap bitmap = BitmapFactory.decodeStream(ins);
                    //主线程更新UI
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //获取图片宽高
                            int bitH = bitmap.getHeight();
                            int bitW = bitmap.getWidth();
                            //获取LayoutParams设置宽高，，使图片填充ImageView
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imamgView.getLayoutParams();
                            /**
                             * 图片的宽高比等于ImageView的宽高比，因为ImageView宽度已经确定(1/3父窗体)，通过计算
                             * 得到ImageView的高度
                             */
                            lp.height = bitH * lp.width/bitW;
                            //将参数设置到ImageView
                            imamgView.setLayoutParams(lp);
                            imamgView.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }
}
