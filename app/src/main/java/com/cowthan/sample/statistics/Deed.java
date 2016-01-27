package com.cowthan.sample.statistics;

/**
 * 一个Deed就是一个行为，一个行为可以是：
 * ——打开个应用，关闭个应用
 * ——打开个界面，关闭个界面
 * ——按个按钮
 *
 * 注意，这里的概念有点不同
 * 1 虽然Deed概念是可以嵌套的，但嵌套只发生在UI和Event之间，UI之间不会嵌套！
 * 2 UI之间的关系全是串行的，这个关系必须由程序员自己控制
 * 3 UI事件没有开始和结束之分，在界面的onStart时开始一个UI事件，下一个UI事件来时，上一个自动结束
 *
 * Deed库讲究的是一次使用，怎么就算是一次使用呢？
 * 1 从主页开始，不断往里点，就是一次使用
 * 2 一旦点击了后退，则是一个新的UI事件，直到退到主页，再回到1
 * 3 弹出页不会记入UI事件
 *
 * Created by Administrator on 2016/1/24.
 */
public class Deed {

    public String id;
    public String name; //如登陆页，或事件名
    public String info;

    /**
     *
     * @param id 如果是Activity或者Fragment，务必传入Class的full name
     * @param name 显示名
     * @param info 说明
     */
    public Deed(String id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }
}
