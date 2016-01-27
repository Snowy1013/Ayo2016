Android2016：Ayo库的sample工程以及团队成员学习demo展示平台
===========================
* 简介
    * 这个工程是Ayo库的sample，也是我们平时自己学习和好的开源项目的demo展示平台。
    * `Ayo库`包含两个部分，`AyoSdk`和`AyoViewLib`，这两个库以精简和接口统一为原则
    * 刚刚迁移到了安卓6.0上，各个地方还有些不足，敬请期待。。。

****
* 如果有任何问题或意见，请联系我
    * Auor: Damon, Snowy
    * E-mail: cowthan@163.com

===========================
#一 免声明的Activity小框架

* 为什么会有这个小框架？
    * 你的manifest里还是声明了一堆Activity吗？
    * 你还在使用Intent在Activity间传递数据吗？ 

__OK，现在不用了__ :sweat: 


* 这个框架什么时候就没用了？什么时候需要自己定义和声明Activity?
    * 你的Activity需要特殊的intent-filter配置时
    * 几个TmplActivity在主题，配置上不满足你的需求时
    * 你觉得这个框架不咋地
   
* 要自己定义Activity时，怎么办？
    * 请参考org.ayo.app.orig里的类
    
__原生v4的Fragment和v7的AppCompactActivity__


#1 框架基本思想

* 思想
    * 整个应用只声明5个Activity
    * 主Activity，即launcher
    * 4个模板Activity，对应4个启动模式
    * 参数传递使用自定义的SimpleBundle
    * 除了启动Activity和参数传递机制，其他使用方式都和普通Activity使用方式相同

__Manifest中的声明：__

```Java
<activity
            android:name="com.cowthan.sample.MainActivity"
            android:configChanges="orientation|keyboardHidden|navigation"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme"
            >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity
            android:name="org.ayo.app.base.TmplActivityStandard"
            android:configChanges="orientation|keyboardHidden|navigation"
            android:screenOrientation="portrait"
            android:launchMode="standard"
            android:theme="@style/AyoTransparentTheme" />

        <activity
            android:name="org.ayo.app.base.TmplActivitySingleTask"
            android:configChanges="orientation|keyboardHidden|navigation"
            android:screenOrientation="portrait"
            android:launchMode="singleTask"
            android:theme="@style/AyoTransparentTheme" />

        <activity
            android:name="org.ayo.app.base.TmplActivitySingleTop"
            android:configChanges="orientation|keyboardHidden|navigation"
            android:screenOrientation="portrait"
            android:launchMode="singleTop"
            android:theme="@style/AyoTransparentTheme" />

        <activity
            android:name="org.ayo.app.base.TmplActivitySingleInstance"
            android:configChanges="orientation|keyboardHidden|navigation"
            android:screenOrientation="portrait"
            android:launchMode="singleInstance"
            android:theme="@style/AyoTransparentTheme" />
```
注意其中不同的launchMode和统一的theme

* ActivityAttacher的意义：
    * ActivityAttacher就是附着在上面4个模板Activity里的Activity代理
    * ActivityAttacher中持有一个Activity实例对象，是在onCreate时赋值的
    * ActivityAttacher可以处理Activity中的所有配置和生命周期
    * ActivityAttacher提供的接口，原则上应该完全仿Activity，这一点类似v7源码中的和AppCompactActivity相关的一个delegate类，但不知道这个类是干什么用的  


请时刻记住：定义一个ActivityAttacher，其实就是相当于定义了一
个Activity，二者只需要通过切换基类，就可以互相转换，不需要多修
改一行代码，如果不是这样，那是框架的问题。

##2 如何定义ActivityAttacher：

```Java
public class ToolkitSampleActivity extends ActivityAttacher{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tmpl);
    }
}
```
    最后再说一次：ActivityAttacher和Activity的定义方式都是一样的，如果不一样，必须联系我，我来改成一样

##3 怎么打开一个Activity？
```Java
ActivityAttacher.startActivity(Context, Class<? extends ActivityAttacher> attacherClazz )

ActivityAttacher.startActivity(Context, Class<? extends ActivityAttacher> attacherClazz, SimpleBundle bundle)

ActivityAttacher.startActivity(Context context,
                                     Class<? extends ActivityAttacher> attacherClazz,
                                     SimpleBundle bundle,
                                     boolean needNewStack,
                                     int launchMode)
```
例子：
```Java
ActivityAttacher.startActivity(getActivity(), LoginActivity.class, bundle);
```

##4 怎么传递参数？

* 通过SimpleBundle，大体上使用方式和Intent, Bundle一样
* SimpleBundle统一由BundleManager来管理，并且是通过id来管理，id基本上就是根据时间随机生成的 

```Java
SimpleBundle bundle = BundleManager.getDefault().fetch();
bundle.putExtra("name", 1);
```
 怎么在ActivityAttacher中得到传入的bundle： 在onCreate中,
 ```Java
 getBundle()--就是传入的参数
 ```

    大体讲一下这个过程：
    虽然完全可以用原生的Intent来处理参数，但Intent是个重量级的东西，主要是从系统层面为了跨进程的各个组建通信
    而存在，同一个app之间传递参数，完全可以只用引用来传递，
    打开模板Activity时，其实从Intent中带了两个参数，1个是attacher对象，要附着在Activity中的，另一个就是SimpleBundle
    的id，用id就可以从BundleManager中取出bundle，而对外暴露的接口就是startActivity是传入bundle，和在attache中通过
    getBundle来获取bundle


怎么处理 startActivityForResult

？？？？？？？？？？还没搞定？？？？？？？？？？？？

主要是还没想好要怎么对外提供接口


##5 AyoView里提供了一个Aivity主题：
```Java
<style name="AyoTransparentTheme" parent="Theme.AppCompat.Light.NoActionBar">
    <item name="android:windowNoTitle">true</item>
    <item name="android:windowContentOverlay">@null</item>
    <item name="android:windowIsTranslucent">true</item>
</style>
```

* 大体上是：无标题栏，背景透明
* 背景透明是为了AyoView库中的SwipeBack特性准备的，防止滑动关闭时，看不到下面的Activity
* 注意：如果想使用滑动关闭的特性，就要在你自定义的主题中保证Activity的背景透明

##6 滑动关闭Activity

SwipeBack是ayo库中提供的一个布局，但这个功能一般放到Activity基类中比
较合适，所以提供了一个SwipeBackActivityAttacher


##7 最后注意：

* 注意事项
    * 每个项目都应该有个自己的BaseActivity，处理自己的基本业务逻辑，一般情况下只需要继承自`SwipeBackActivityAttacher`就行了
    * 模板Activity，即Tmpl开头的四个Activity，继承自`AppCompatActivity`，来自appcompat-v7
    * 在ActivityAttacher中通过`getActivity()`来得到被代理的Activity实例

