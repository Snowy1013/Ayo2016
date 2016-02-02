package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.io.InputStream;

/**
 * 一个BitmapDrawable就是封装了一个位图。
 * 直接以文件的方式，就是封装了一个原始的位图。
 * 以Xml方式，可以对原始的位图进行一系列的处理，比如说抗锯齿，拉伸，对齐等等。
 * 要了解BitmapDrawable的使用，还需要明白Bitmap、BitmapFactory等类。Bitmap代表了一个原始的位图，
 * 并且可以对位图进行一系列的变换操作。BitmapFactory提供一系列的方法用于产生一个Bitmap对象。多用在Canvas中。
 * @author seven
 *
 */
public class D_BitmapDrawable extends BaseActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__bitmap);
		
		//----代码创建1：
		InputStream inputStream = getResources().openRawResource(R.drawable.animate_shower);
		BitmapDrawable drawable = new BitmapDrawable(inputStream);
		Bitmap bitmap = drawable.getBitmap(); 
		//利用Bitmap对象创建缩略图  
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, 40, 40); 
        //imageView.setImageBitmap(bitmap);
        
        //----代码创建2：
        Resources res = getResources();  
        BitmapDrawable drawable2 = (BitmapDrawable) res.getDrawable(R.drawable.animate_shower);  
        //实际上这是一个BitmapDrawable对象  
        //可以在调用getBitmap方法，得到这个位图  
        bitmap = drawable2.getBitmap();
        
        drawable2.getCurrent();//对于没有状态变化的Drawable，这个就是drawable.this，对于选择器或者level drawable，这个就是当前被激活的drawable，因为是多个drawable的组合
        Rect r = drawable2.copyBounds();
        Rect bounds = new Rect();
        drawable2.copyBounds(bounds);
        System.out.println("--drawable bounds = (" + r.left + "," + r.right + "," + r.top + "," + r.bottom + ")");
        
        //---属性：xml标签：<bitmap>
        /*
         <bitmap xmlns:android="http://schemas.android.com/apk/res/android"
		    android:src="@drawable/line"
		    android:tileMode="repeat"
		    android:antialias=""
		    android:filter=""
		    android:dither=""
		    android:gravity=""
		    >
		</bitmap>
         */
        //当tile模式被启用，位图是重复的，并且gravity属性将被忽略
        //REPEAT：一般重复
        //MIRROR：镜面反射
        //CLAMP：边缘拉伸，不好看，不知道什么时候可以用，replicate the edge color if the shader draws outside of its original bounds 
        drawable2.setTileModeXY(TileMode.REPEAT, TileMode.CLAMP);//Sets the repeat behavior，当Bitmap小于这个drawable时，需要指定重复模式
        drawable2.setGravity(Gravity.CENTER);//设置bitmap在bound中的位置，和tile模式冲突，即只有tile不是disable时，gravity才起作用，要么重复，要么考虑gravity
        drawable2.setAntiAlias(true);//抗锯齿
        drawable2.setFilterBitmap(false);//大体上就是使用Bitmap时，如果要缩放或旋转，会改善视觉效果，但速度变慢
        drawable2.setDither(false);//如果位图与屏幕的像素配置不同时，是否允许抖动.（例如：一个位图的像素设置是 ARGB 8888，但屏幕的设置是RGB 565）
        
        View v = findViewById(R.id.tv);
        v.setBackground(drawable2);
	}


}
