TextView
===========================

* 本文探讨的是：
    * TextView到底能将文本显示到什么程度
    * 常用的显示模式
    * 字体

##1 显示html

###用法
```java
Spanned s = Html.fromHtml(string);
tv_title.setText(s);
```

###默认支持的标签

注意，<span style="">text</span>默认不支持，只认识<font color="">text</font>

###自定义标签

##2 spannable到底怎么用


##3 显示图片


##4 长文本格式


##5 AwesomeTextView

