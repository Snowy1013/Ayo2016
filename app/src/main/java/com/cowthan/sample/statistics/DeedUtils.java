package com.cowthan.sample.statistics;

/**
 * Created by Administrator on 2016/1/24.
 */
public class DeedUtils {

    public static void addUIStatus(Class clazz, String name, String info){
        Deed d = new Deed(clazz.getName(), name, info);
    }

    public static void addEvent(String id, String info){
        Deed d = new Deed(id, id, info);
    }

}
