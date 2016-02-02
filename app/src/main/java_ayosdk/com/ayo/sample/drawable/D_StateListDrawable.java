package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

@SuppressLint("NewApi")
public class D_StateListDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d__state_list_drawable);
		
		StateListDrawable drawable=new StateListDrawable();  
		//如果要设置莫项为false，在前面加负号 ，比如android.R.attr.state_focesed标志true，-android.R.attr.state_focesed就标志false  
		drawable.addState(new int[]{android.R.attr.state_focused}, this.getResources().getDrawable(R.drawable.kaola));  
		drawable.addState(new int[]{android.R.attr.state_pressed}, this.getResources().getDrawable(R.drawable.qie));  
		drawable.addState(new int[]{android.R.attr.state_selected}, this.getResources().getDrawable(R.drawable.shuimu));  
		drawable.addState(new int[]{}, this.getResources().getDrawable(R.drawable.shuimu));//默认  
		
		View v = findViewById(R.id.tv);
	    v.setBackground(drawable);
	    //v.setBackgroundResource(R.drawable.d_layer_drawable);
	    
	    /*
	     xml方式：
	     
	     	<?xml version="1.0" encoding="UTF-8"?>  
			<selector  
			  xmlns:android="http://schemas.android.com/apk/res/android">  
			    <item android:state_focused="true" android:drawable="@drawable/botton_add" />  
			    <item android:state_pressed="true" android:drawable="@drawable/botton_add_down" />  
			    <item android:state_selected="true" android:drawable="@drawable/botton_add" />  
			    <item android:drawable="@drawable/botton_add" />//默认  
			</selector> 
	     android:drawable
		必须的，指向一个drawable资源
		android:state_pressed  是否按下
		android:state_focused  是否获得获得焦点
		android:state_hovered  鼠标在上面滑动的状态。通常和state_focused使用同样的drawable
		android:state_selected 是否选中
		android:state_checkable 是否可以被勾选（checkable）。只能用在可以勾选的控件
		android:state_checked 是否被勾选上
		android:state_enabled 是否可用
		android:state_activated 是否被激活并持久的选择
		android:state_window_focused 当前应用程序是否获得焦点
		注意：Android系统将会选中第一条符合当前状态的item。。因此，如果第一项列表中包含
		了所有的状态属性，那么它是每次就只用他。这就是为什么你的默认值应该放在最后面。
		++++也就说这些状态所占范围越大的，越必须放后面，基本桑就照着上面的顺序放就行
	     */
	}


}
