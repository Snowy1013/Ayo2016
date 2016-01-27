package org.ayo.app.base;

/**
 * Created by Administrator on 2016/1/19.
 */
public class BundleIdGenerator {
    public int getNext(){
        return (int) (System.currentTimeMillis()/1000) + 1;
    }
}
