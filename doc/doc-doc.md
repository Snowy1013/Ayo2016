Ayo库使用手册
===========================

##1 初始化
初始化涉及到的类是Ayo，在Application.onCreate()里

```java
package com.cowthan.sample;

import android.app.Application;

import org.ayo.Ayo;
import org.ayo.CrashHandler;
import org.ayo.Logger;
import org.ayo.VanGogh;

/**
 * Created by cowthan on 2016/1/24.
 */
public class App extends Application{

    public static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        //初始化Genius SDK
        Ayo.init(this, "ayo", true, true);
        Ayo.debug = true;
        AyoViewLib.init(this);

        //初始化日志类：待废弃，由JLog代替
        Logger.init(Logger.DEBUG);

        //初始化网络图片加载工具类
        VanGogh.initImageBig(R.mipmap.loading_big);
        VanGogh.initImageMiddle(R.mipmap.loading_big);
        VanGogh.initImageSmall(R.mipmap.loading_big);
        VanGogh.init(this);

        //初始化全局异常处理
        CrashHandler crashHandler = CrashHandler.getInstance();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }
}
```

要访问全局上下文，可以使用`App.app`或`Ayo.context`

##2 Application定义

一般都会在项目中注册个Application，即manifest中<application>标签的name属性




