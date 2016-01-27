package com.cowthan.sample;

import android.content.Context;
import android.util.DisplayMetrics;

import com.cowthan.sample.zpartial_explode.Particle;

import org.ayo.app.tmpl.ErrorReason;
import org.ayo.http.HttpProblem;

/**
 * Created by cowthan on 2016/1/17.
 */
public class Utils {

    // /--------------------
    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }

    /**
     * 根据手机的屏幕属性从 sp的单位 转成为px(像素)
     *
     * @param context
     * @param value
     * @return
     */
    public static float sp2px(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return value * metrics.scaledDensity;
    }

    /**
     * 根据手机的屏幕属性从 px(像素) 的单位 转成为 sp
     *
     * @param context
     * @param value
     * @return
     */
    public static float px2sp(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return value / metrics.scaledDensity;
    }

    public static int parseErrorType(HttpProblem p){
        if(p == HttpProblem.DATA_ERROR){
            return ErrorReason.LOCAL;
        }else if(p == HttpProblem.OFFLINE){
            return ErrorReason.OFFLINE;
        }else if(p == HttpProblem.OK) {
            return 200;
        }else if(p == HttpProblem.SERVER_ERROR){
            return ErrorReason.SERVER;
        }else if(p == HttpProblem.UNKNOWN){
            return ErrorReason.SERVER;
        }else{
            return ErrorReason.SERVER;
        }
    }

}
