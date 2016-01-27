package com.cowthan.sample.statistics;

/**
 * Created by Administrator on 2016/1/24.
 */
public class DeedEvent{
    public String id;
    public String name; //如登陆页，或事件名
    public String info;

    /**
     *
     * @param id 如果是Activity或者Fragment，务必传入Class的full name
     * @param name 显示名
     * @param info 说明
     */
    public DeedEvent(String id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }
}
