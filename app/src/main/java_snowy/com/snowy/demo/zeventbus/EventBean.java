package com.snowy.demo.zeventbus;

/**
 * Created by zx on 16-9-16.
 */
public class EventBean {
    private String msg;

    public EventBean(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
