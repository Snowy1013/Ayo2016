package com.ayo.sample.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.cowthan.sample.BaseActivity;

/**
 * 一、发现奇怪的问题？ 在研究Android Drawable资源的时候，发现了一个奇怪的问题。在官方API介绍中：
 * ShapeDrawable
 * 介绍：This object can be defined in an XML file with the <shape>
 * element（这个对象可以用<shape>元素在xml文件中定义）
 *
 * GradientDrawable 介绍：This object can be
 * defined in an XML file with the <shape> element（这个对象可以用<shape>元素在xml文件中定义）
 * 两者的介绍一模一样，都说可以使用<shape>标签在xml文件中定义。 那么，到底用<shape>标签定义的是什么的呢？
 * 经过下面的实验：
 * TextView textView=(TextView)findViewById(R.id.textView);
 * ShapeDrawable gradientDrawable=(ShapeDrawable)textView.getBackground();
 * 报错，类型转换错误java.lang.ClassCastException:
 * android.graphics.drawable.GradientDrawable 
 * ————所以是GradientDrawable
 * —————那么，ShapeDrawable是怎么定义的，找了网上的资料，结果硬是没找到如何在XML文件中定义它，
 * 只能通过代码的方式实现
 * 
 * 
 * ----------------<gradient>指定这个shape的渐变颜色
android:angle  渐变的角度。 0 代表从 left 到 right。90 代表bottom到 top。必须是45的倍数，默认为0
android:centerX  渐变中心的相对X坐标，在0到1.0之间。
android:centerY  渐变中心的相对Y坐标，在0到1.0之间。
android:centerColor  可选的颜色值。基于startColor和endColor之间
android:endColor  结束的颜色
android:gradientRadius  渐变的半径。只有在 android:type="radial"才使用
android:startColor  开始的颜色值。
android:type 渐变的模式，下面值之一：
"linear"	线形渐变。这也是默认的模式
"radial"	辐射渐变。startColor即辐射中心的颜色
"sweep"	扫描线渐变。
android:useLevel  如果在LevelListDrawable中使用，则为true

----------------<padding>内容与视图边界的距离
android:left 左边填充距离.
android:top 顶部填充距离.
android:right  右边填充距离.
android:bottom 底部填充距离.

----------------<size>这个shape的大小
android:height  这个shape的高度。
android:width  这个shape的宽度。
注意：默认情况下，这个shape会缩放到与他所在容器大小成正比。当你在一个ImageView中使用这个shape，你可以使用 android:scaleType="center"来限制这种缩放。

----------------<solid>填充这个shape的纯色
android:color  颜色值，十六进制数，或者一个Color资源
 
----------------<stroke> 这个shape使用的笔画，当android:shape="line"的时候，必须设置改元素。
android:width 笔画的粗细。
android:color 笔画的颜色
android:dashGap 每画一条线就间隔多少。只有当android:dashWidth也设置了才有效。
android:dashWidth 每画一条线的长度。只有当 android:dashGap也设置了才有效。

<?xml version="1.0" encoding="utf-8"?>
<shape
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape=["rectangle" | "oval" | "line" | "ring"] >
    <corners
        android:radius="integer"
        android:topLeftRadius="integer"
        android:topRightRadius="integer"
        android:bottomLeftRadius="integer"
        android:bottomRightRadius="integer" />
    <gradient
        android:angle="integer"
        android:centerX="integer"
        android:centerY="integer"
        android:centerColor="integer"
        android:endColor="color"
        android:gradientRadius="integer"
        android:startColor="color"
        android:type=["linear" | "radial" | "sweep"]
        android:useLevel=["true" | "false"] />
    <padding
        android:left="integer"
        android:top="integer"
        android:right="integer"
        android:bottom="integer" />
    <size
        android:width="integer"
        android:height="integer" />
    <solid
        android:color="color" />
    <stroke
        android:width="integer"
        android:color="color"
        android:dashWidth="integer"
        android:dashGap="integer" />
</shape>


 * 
 */
public class D_GradientDrawable extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	   setContentView(new SampleView(getActivity()));
	  }  
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_d__gradient_drawable);
//
//		final View v = findViewById(R.id.tv);
//		v.setBackgroundResource(R.drawable.d_rotate_drawable);
//		// .setBackground(scaleDrawable);
//		// v.getBackground().setLevel(0);
//		SeekBar seekbar = (SeekBar) findViewById(R.id.seekbar);
//		seekbar.setMax(10000);
//		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//
//			@Override
//			public void onStopTrackingTouch(SeekBar seekBar) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onStartTrackingTouch(SeekBar seekBar) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onProgressChanged(SeekBar seekBar, int progress,
//					boolean fromUser) {
//				// TODO Auto-generated method stub
//				v.getBackground().setLevel(progress);
//			}
//		});
//	}


	private static class SampleView extends View {
		private Path mPath;
		private Paint mPaint;
		private Rect mRect;
		private GradientDrawable mDrawable;

		public SampleView(Context context) {
			super(context);
			setFocusable(true);

			this.setBackgroundColor(Color.parseColor("#000000"));

			mPath = new Path();
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mRect = new Rect(0, 0, 120, 120);

			mDrawable = new GradientDrawable(
					GradientDrawable.Orientation.TL_BR, new int[] { 0xFFFF0000,
							0xFF00FF00, 0xFF0000FF });
			mDrawable.setShape(GradientDrawable.RECTANGLE);
			mDrawable.setGradientRadius((float) (Math.sqrt(2) * 60));
		}

		static void setCornerRadii(GradientDrawable drawable, float r0,
				float r1, float r2, float r3) {
			drawable.setCornerRadii(new float[] { r0, r0, r1, r1, r2, r2, r3,
					r3 });
		}

		// 重点的绘制过程
		@Override
		protected void onDraw(Canvas canvas) {

			mDrawable.setBounds(mRect);

			float r = 16;

			canvas.save();
			canvas.translate(10, 10);
			mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
			setCornerRadii(mDrawable, r, r, 0, 0);
			mDrawable.draw(canvas);
			canvas.restore();

			canvas.save();
			canvas.translate(10 + mRect.width() + 10, 10);
			mDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
			setCornerRadii(mDrawable, 0, 0, r, r);
			mDrawable.draw(canvas);
			canvas.restore();

			canvas.translate(0, mRect.height() + 10);

			canvas.save();
			canvas.translate(10, 10);
			mDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
			setCornerRadii(mDrawable, 0, r, r, 0);
			mDrawable.draw(canvas);
			canvas.restore();

			canvas.save();
			canvas.translate(10 + mRect.width() + 10, 10);
			mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
			setCornerRadii(mDrawable, r, 0, 0, r);
			mDrawable.draw(canvas);
			canvas.restore();

			canvas.translate(0, mRect.height() + 10);

			canvas.save();
			canvas.translate(10, 10);
			mDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
			setCornerRadii(mDrawable, r, 0, r, 0);
			mDrawable.draw(canvas);
			canvas.restore();

			canvas.save();
			canvas.translate(10 + mRect.width() + 10, 10);
			mDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
			setCornerRadii(mDrawable, 0, r, 0, r);
			mDrawable.draw(canvas);
			canvas.restore();
		}
	}



	class PictureLayout extends ViewGroup {
		private final Picture mPicture = new Picture();

		public PictureLayout(Context context) {
			super(context);
		}

		public PictureLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void addView(View child) {
			if (getChildCount() > 1) {
				throw new IllegalStateException(
						"PictureLayout can host only one direct child");
			}

			super.addView(child);
		}

		@Override
		public void addView(View child, int index) {
			if (getChildCount() > 1) {
				throw new IllegalStateException(
						"PictureLayout can host only one direct child");
			}

			super.addView(child, index);
		}

		@Override
		public void addView(View child, LayoutParams params) {
			if (getChildCount() > 1) {
				throw new IllegalStateException(
						"PictureLayout can host only one direct child");
			}

			super.addView(child, params);
		}

		@Override
		public void addView(View child, int index, LayoutParams params) {
			if (getChildCount() > 1) {
				throw new IllegalStateException(
						"PictureLayout can host only one direct child");
			}

			super.addView(child, index, params);
		}

		@Override
		protected LayoutParams generateDefaultLayoutParams() {
			return new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			final int count = getChildCount();

			int maxHeight = 0;
			int maxWidth = 0;

			for (int i = 0; i < count; i++) {
				final View child = getChildAt(i);
				if (child.getVisibility() != GONE) {
					measureChild(child, widthMeasureSpec, heightMeasureSpec);
				}
			}

			maxWidth += getPaddingLeft() + getPaddingRight();
			maxHeight += getPaddingTop() + getPaddingBottom();

			Drawable drawable = getBackground();
			if (drawable != null) {
				maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
				maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
			}

			setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec),
					resolveSize(maxHeight, heightMeasureSpec));
		}

		private void drawPict(Canvas canvas, int x, int y, int w, int h, float sx,
							  float sy) {
			canvas.save();
			canvas.translate(x, y);
			canvas.clipRect(0, 0, w, h);
			canvas.scale(0.5f, 0.5f);
			canvas.scale(sx, sy, w, h);
			canvas.drawPicture(mPicture);
			canvas.restore();
		}

		@Override
		protected void dispatchDraw(Canvas canvas) {
			super.dispatchDraw(mPicture.beginRecording(getWidth(), getHeight()));
			mPicture.endRecording();

			int x = getWidth() / 2;
			int y = getHeight() / 2;

			if (false) {
				canvas.drawPicture(mPicture);
			} else {
				drawPict(canvas, 0, 0, x, y, 1, 1);
				drawPict(canvas, x, 0, x, y, -1, 1);
				drawPict(canvas, 0, y, x, y, 1, -1);
				drawPict(canvas, x, y, x, y, -1, -1);
			}
		}

		@Override
		public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
			location[0] = getLeft();
			location[1] = getTop();
			dirty.set(0, 0, getWidth(), getHeight());
			return getParent();
		}

		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			final int count = super.getChildCount();

			for (int i = 0; i < count; i++) {
				final View child = getChildAt(i);
				if (child.getVisibility() != GONE) {
					final int childLeft = getPaddingLeft();
					final int childTop = getPaddingTop();
					child.layout(childLeft, childTop,
							childLeft + child.getMeasuredWidth(),
							childTop + child.getMeasuredHeight());

				}
			}
		}
	}
}

//class GraphicsActivity extends BaseActivity {
//	// set to true to test Picture
//	private static final boolean TEST_PICTURE = false;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	public void setContentView(View view) {
//		if (TEST_PICTURE) {
//			ViewGroup vg = new PictureLayout(this);
//			vg.addView(view);
//			view = vg;
//		}
//
//		super.setContentView(view);
//	}
//}

