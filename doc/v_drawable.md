TextView
===========================

* 本文探讨的是：
    * 安卓提供的几个Drawable类型
    * 自定义Drawable
    * Drawable的使用场景


* 前言
    * 我觉得这篇文章我得贴图，真他妈麻烦，后来我有意向
    * 注意Drawable用在哪儿，一般能设置background和src的地方都能设置Drawable，就是R.drawable

##1 内置Drawable类型

###BitmapDrawable和NineOldPatchDrawable：位图

对应的xml：
```java
<bitmap xmlns:android="http://schemas.android.com/apk/res/android"
    android:src="@drawable/line"
    android:tileMode="repeat"
    android:antialias=""
    android:filter=""
    android:dither=""
    android:gravity=""
    >
</bitmap>
```

* 介绍：
    * 一个BitmapDrawable就是封装了一个位图
    * 直接以文件的方式，就是封装了一个原始的位图
    * 以Xml方式，可以对原始的位图进行一系列的处理，比如说抗锯齿，拉伸，对齐等等。
    * 要了解BitmapDrawable的使用，还需要明白Bitmap、BitmapFactory等类
        * Bitmap代表了一个原始的位图，并且可以对位图进行一系列的变换操作
        * BitmapFactory提供一系列的方法用于产生一个Bitmap对象， 多用在Canvas中

* Tile模式：
    * 代码设置：`drawable2.setTileModeXY(TileMode.REPEAT, TileMode.CLAMP)`
    * 当tile模式被启用，位图是重复的，并且gravity属性将被忽略
    * REPEAT：一般重复
    * MIRROR：镜面反射
    * CLAMP：边缘拉伸，不好看，不知道什么时候可以用

__通过代码控制__
```java
//方式1
InputStream inputStream = getResources().openRawResource(R.drawable.animate_shower);
BitmapDrawable drawable = new BitmapDrawable(inputStream);
Bitmap bitmap = drawable.getBitmap();

//方式2
Resources res = getResources();
BitmapDrawable drawable2 = (BitmapDrawable) res.getDrawable(R.drawable.animate_shower);

//获取
drawable2.getCurrent();//对于没有状态变化的Drawable，这个就是drawable.this，对于选择器或者level drawable，这个就是当前被激活的drawable，因为是多个drawable的组合


//常见属性
drawable2.setTileModeXY(TileMode.REPEAT, TileMode.CLAMP);//Sets the repeat behavior，当Bitmap小于这个drawable时，需要指定重复模式
drawable2.setGravity(Gravity.CENTER);//设置bitmap在bound中的位置，和tile模式冲突，即只有tile不是disable时，gravity才起作用，要么重复，要么考虑gravity
drawable2.setAntiAlias(true);//抗锯齿
drawable2.setFilterBitmap(false);//大体上就是使用Bitmap时，如果要缩放或旋转，会改善视觉效果，但速度变慢
drawable2.setDither(false);//如果位图与屏幕的像素配置不同时，是否允许抖动.（例如：一个位图的像素设置是 ARGB 8888，但屏幕的设置是RGB 565）


```

* 场景
    * Repeat模式：可以画虚线



###ClipDrawable: 裁剪

对应的xml
```java
<clip xmlns:android="http://schemas.android.com/apk/res/android"
    android:drawable="@drawable/android"
    android:clipOrientation="horizontal"
    android:gravity="left" />
```

图片裁剪：
 * ——默认是0，即完全裁剪
 * ——最大是10000，即完全不裁剪
 * ——通过setLevel设置裁剪大小

* 重点属性：
    * level：
        * 默认是0，即完全裁剪
        * 最大是10000，即完全不裁剪
        * 通过setLevel设置裁剪大小

    * gravity：和View的关系，也和裁剪方向一起影响裁剪行为
        * top	将这个对象放在容器的顶部，不改变其大小。当clipOrientation 是"vertical"，裁剪发生在drawable的底部（bottom）
        * bottom	将这个对象放在容器的底部，不改变其大小。当clipOrientation 是 "vertical"，裁剪发生在drawable的顶部（top）
        * left	将这个对象放在容器的左部，不改变其大小。当clipOrientation 是 "horizontal"，裁剪发生在drawable的右边（right）。默认的是这种情况。
        * right	将这个对象放在容器的右部，不改变其大小。当clipOrientation 是 "horizontal"，裁剪发生在drawable的左边（left）。
        * center_vertical	将对象放在垂直中间，不改变其大小。裁剪的情况和”center“一样。
        * fill_vertical	垂直方向上不发生裁剪。（除非drawable的level是 0，才会不可见，表示全部裁剪完）
        * center_horizontal	将对象放在水平中间，不改变其大小。裁剪的情况和”center“一样。
        * fill_horizontal	水平方向上不发生裁剪。（除非drawable的level是 0，才会不可见，表示全部裁剪完）
        * center	将这个对象放在水平垂直坐标的中间，不改变其大小。当clipOrientation 是 "horizontal"裁剪发生在左右。当clipOrientation是"vertical",裁剪发生在上下。
        * fill	填充整个容器，不会发生裁剪。(除非drawable的level是 0，才会不可见，表示全部裁剪完)。
        * clip_vertical   额外的选项，它能够把它的容器的上下边界，设置为子对象的上下边缘的裁剪边界。裁剪要基于对象垂直重力设置：如果重力设置为top，则裁剪下边，如果设置为bottom，则裁剪上边，否则则上下两边都要裁剪。
        * clip_horizontal  额外的选项，它能够把它的容器的左右边界，设置为子对象的左右边缘的裁剪边界。裁剪要基于对象垂直重力设置
    * clipOrientation：裁剪方向
        * horizontal	水平方向裁剪
        * vertical	垂直方向裁剪

__java代码__
```java
ClipDrawable clipDrawable = new ClipDrawable(
        getResources().getDrawable(R.drawable.animate_shower),
        Gravity.CENTER,
        ClipDrawable.VERTICAL);

final View v = findViewById(R.id.tv);
v.setBackground(clipDrawable);
v.getBackground().setLevel(100);
```


###InsetDrawable


###LayerDrawable


###LevelDrawable


###StateListDrawable

###TransitionDrawable


###RotateDrawable

###ScaleDrawable


###GradientDrawable和ShapeDrawable


##2 自定义Drawable



##3 使用场景