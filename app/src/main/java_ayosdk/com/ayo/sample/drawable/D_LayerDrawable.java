package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 *创建LayerDrawable和使用
一个LayerDrawable是一个可以管理一组drawable对象的drawable。在LayerDrawable的drawable资源
 按照列表的顺序绘制，列表的最后一个drawable绘制在最上层。
它所包含的一组drawable资源用多个<item>元素表示，一个<item>元素代表一个drawable资源。

在默认的情况下，所有的drawable item都会缩放到合适的大小来适应视图。因此，在一个layer-list中定
 义不同的位置可能会增加视图的尺寸和被自动缩放。为了避免被缩放，可以再<item>节点里加上<bitmap>
 元素来指定一个drawable，并且定义一些不会被拉伸的gravity属性，例如center。
举个例子，下面在item里面定义一个drawable，图片就会自动缩放以适应视图的大小。
<item android:drawable="@drawable/image" />
为了避免缩放，可以使用<bitmap>的子元素来指定drawable资源
<item>
  <bitmap android:src="@drawable/image"
          android:gravity="center" />
</item>

例子：在xml文件中定义layerDrawable，然后使用
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/black_lotus"
        android:left="20dp"
        android:top="20dp">
    </item>
    <item android:drawable="@drawable/black_lotus"
        android:left="40dp"
        android:top="40dp">
    </item>
    <item android:drawable="@drawable/black_lotus"
        android:left="60dp"
        android:top="60dp">
    </item>
</layer-list>

<ImageView
    android:layout_height="wrap_content"
    android:layout_width="wrap_content"
    android:src="@drawable/layers" />

 * @author seven
 *
 */
public class D_LayerDrawable extends BaseActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__layer_drawable);
		
		//---代码中定义LayerDrawable
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.animate_shower);  
        Drawable[] drawables=new Drawable[3];  
//        drawables[0]=new PaintDrawable(Color.BLACK);  
//        drawables[1]=new PaintDrawable(Color.BLUE);  
        drawables[0]=new BitmapDrawable(bitmap);  
        drawables[1]=new BitmapDrawable(bitmap);  
        drawables[2]=new BitmapDrawable(bitmap);  
        LayerDrawable layer=new LayerDrawable(drawables);  
        layer.setLayerInset(0, 20, 20, 0, 0); //index（第几个Drawable）, 左，上，右，下--像素，说的是左上角相对于LayerDrawable左上角的位置 ，和右下角（后两个参数）相对于LayerDrawable右下角的位置
        layer.setLayerInset(1, 40, 40, 0, 0);  
        layer.setLayerInset(2, 60, 60, 60, 60);  
		
		 View v = findViewById(R.id.tv);
	     //v.setBackground(layer);//--用代码中创建的LayerDrawable，在这里图片会被拉伸
	     v.setBackgroundResource(R.drawable.d_layer_drawable);//用xml定义的layer-list，第三张图片没被拉伸。看：
	     /*
	      <layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/animate_shower"
        android:left="20dp"
        android:top="20dp">
    </item>
    <item android:drawable="@drawable/animate_shower"
        android:left="40dp"
        android:top="40dp">
    </item>
    <item
        android:left="60dp"
        android:top="60dp">>
	  <bitmap android:src="@drawable/animate_shower"
	          android:gravity="top" />---对于这张图片的显示方式：没被拉伸，但是有问题，其实item本身可以认为形成了一个Drawable，和上面两个一样，按照相对位置和大小摆放和拉伸了，然后把bitmap按照gravity放进来，行为上就是这样
	</item>
</layer-list>
	      */
	     
	     //---上面的方式会导致图像的拉伸，即LayerDrawable当背景用时，尺寸随控件尺寸走，所以内部的Drawable也相应的被拉伸
	     //--要解决拉伸，可以在xml里定义
	     
	
	}


}
