package com.cowthan.sample;

import android.app.Application;

import com.snowy.demo.zhttp.OkHttpUtils;

import org.ayo.Ayo;
import org.ayo.CrashHandler;
import org.ayo.Logger;
import org.ayo.VanGogh;
import org.ayo.app.AyoViewLib;

/**
 * Created by cowthan on 2016/1/24.
 */
public class App extends Application{

    public static Application app;
    private static App mInstance;

    public static App instance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mInstance = this;

        //初始化Ayo SDK
        Ayo.init(this, "ayo", true, true);
        Ayo.debug = true;
        AyoViewLib.init(this);

        //初始化日志类
        Logger.init(Logger.DEBUG);

        //初始化网络图片加载工具类
        VanGogh.initImageBig(R.mipmap.loading_big);
        VanGogh.initImageMiddle(R.mipmap.loading_big);
        VanGogh.initImageSmall(R.mipmap.loading_big);
        VanGogh.init(this);

        //初始化全局异常处理
        CrashHandler crashHandler = CrashHandler.getInstance();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);

        //初始化okhttpconfig
        OkHttpUtils.initOkHttpConfig(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        exit();
    }

    public void exit() {
        System.exit(0);
    }
}
