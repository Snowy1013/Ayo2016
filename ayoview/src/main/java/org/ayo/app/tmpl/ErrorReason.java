package org.ayo.app.tmpl;

/**
 * Created by Administrator on 2016/1/21.
 */
public class ErrorReason {
    /** 本地解析数据错误，注意，大多数情况下虽然是本地错误，但也是服务器给的数据不对 */
    public static final int LOCAL = 1;

    /** 服务器500,404之类的错误 */
    public static final int SERVER = 2;

    /** 没联网 */
    public static final int OFFLINE = 3;
}
