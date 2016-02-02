package com.ayo.sample.drawable;

import android.annotation.SuppressLint;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * 外界存在一个数值范围，控件要根据这个数值切换背景或者图标，
 * 就可以用LevelDrawable
 * ——电源显示就是用的LevelDrawable
 * 
 * 调用Drawable的setLevel()方法可以加载level-list或代码中定义的某个drawable资源，判断加载某项的方式：level-list中某项的android:maxLevel数值大于或者等于setLevel设置的数值，就会被加载。
 * 
 * 使用LevelDrawable注意几点：
1、默认的level为0，如果没有和0匹配的level，那么就不显示。
2、level匹配以maxLevel优先。即如果有个item，min：1，max：2。   另一份item，min：2，max：3。
如果此时设置level=2，那么会匹配第一个item。
 * 
 * @author seven
 *
 */
@SuppressLint("NewApi")
public class D_LevelDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_d_level_drawable);
		
		//---代码创建
		LevelListDrawable levelListDrawable = new LevelListDrawable();
		levelListDrawable.addLevel(0, 20, getResources().getDrawable(R.drawable.animate_shower)); //min, max, drawable
		levelListDrawable.addLevel(20, 40, getResources().getDrawable(R.drawable.kaola));
		levelListDrawable.addLevel(40, 70, getResources().getDrawable(R.drawable.qie));
		levelListDrawable.addLevel(70, 100, getResources().getDrawable(R.drawable.shuimu));
		
		final View v = findViewById(R.id.tv);
		v.setBackground(levelListDrawable);
		
		SeekBar seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				v.getBackground().setLevel(progress);
			}
		});
	
	}


}
