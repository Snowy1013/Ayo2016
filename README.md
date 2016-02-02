Ayo2016
========================

##前言：这是什么

对，没错，这他妈到底是什么？
问得好，这个项目乃是：ayo库的测试工程，ayo库的sample，开源工程质量检验，常见问题收集，个人学习笔记，代码模板，可复用demo等的集合，
总之，就是很牛逼！本项目的唯一目的就是吓尿你！



##如何添加你的代码？

* 分4步：
    * 在main目录创建你的专属目录，以java_开头，如java_snowy，表示这是snowy的个人目录
    * 在模块的build.gradle下注册你的目录，方法在下面
    * 配置你的笔记入口：在Config类中搜`菜单1：笔记`，配置你自己的page和菜单列表
    * 开始写你的代码
        * 如果要使用Activity代理框架，请移步：[Activity代理框架](./doc/doc-ayoview-acagent.md)
        * 如果不想用Activity代理框架，请参考org.ayo.app.orig
        * 如果都不感兴趣，直接写你的Activity就行


####
__注册你的个人目录__
```java
sourceSets {
    main {
        jniLibs.srcDirs = ['libs']
        java.srcDirs = ['src/main/java',
                        'src/main/java_ayosdk',
                        'src/main/java_ayoview',
                        'src/main/java_issues',
                        'src/main/java_opensource',
                        'src/main/java_seven',
                        'src/main/java_snowy',
                        'src/main/lib_particle']
    }
```

####
* 务必注意：
    * 写之前先过一遍现有的库，避免重复引入，省的干重复的事
    * 尽量给个文档，文档目录在README.md

##目录

* 基础设施:
    * [怎么接入你的代码中](./doc/doc-doc.md)
    * [常用工具类](./doc/doc-common.md)
    * [缓存](./doc/doc-cache.md)
    * [数据库](./doc/doc-database.md)
    * [控件注入](./doc/doc-inject.md)
    * [日志系统](./doc/doc-log.md)
    * [IO操作](./doc/doc-io.md)
    * [http请求](./doc/doc-http.md)
    * [网络图片加载](./doc/doc-onlineimage.md)
    * [多线程](./doc/doc-async.md)
    * [事件总线：EventBus](./doc/doc-eventbus.md)
    * [Activity状态保存](./doc/doc-state.md)
    * [加密解密](./doc/doc-crypt.md)


####
* UIFramework：控件
    * [Animation系列讲座](./doc/README-ayo.md)
    * [Drawable系列](./doc/v_drawable.md)
    * [TextView系列](./doc/v_textview.md)
    * [ImageView系列](./doc/v_imageview.md)
    * [ProgressView系列](./doc/README-ayo.md)
    * [View切换系列](./doc/README-ayo.md)
    * [表单系列](./doc/README-ayo.md)
    * [布局系列](./doc/README-ayo.md)
    * [列表系列](./doc/README-ayo.md)
    * [模板：ListView和GridView](./doc/README-ayo.md)
    * [模板：ViewPager主框架](./doc/README-ayo.md)
    * [模板：Tab主框架](./doc/README-ayo.md)
    * [模板：个人页常见模式](./doc/README-ayo.md)

####
* UIFramework：用户提示
    * [Dialog](./doc/README-ayo.md)
    * [Popup](./doc/README-ayo.md)
    * [Toast](./doc/README-ayo.md)
    * [Snackbar](./doc/README-ayo.md)
    * [Headup](./doc/README-ayo.md)
    * [Notification](./doc/README-ayo.md)
    * [声音，LED，震动，亮屏](./doc/README-ayo.md)
    * [其他](./doc/README-ayo.md)

####
* 安卓res详解
    * [res下几个目录到底怎么回事](./doc/README-2016.md)
    * [values目录](./doc/README-2016.md)
    * [主题和style](./doc/README-2016.md)
    * [适配问题](./doc/README-2016.md)
    * [资源文件切换问题：换肤](./doc/README-2016.md)

####
* 单元测试
    * [单元测试怎么写](./doc/README-2016.md)

####
* [常见问题和代码段](./doc/README-issue.md)
    * [ScrollView嵌套ViewPager冲突](./doc/README-issue.md)
    * [ScrollView嵌套ListView或GridView](./doc/README-issue.md)
    * [小键盘管理](./doc/README-issue.md)
    * [三星手机拍照问题](./doc/README-issue.md)
    * [windowIsTranslusent为true导致Activity无法横竖切换](./doc/README-issue.md)

####
* 基于git的work flow
    * [git怎么用起来比较高大上？](./doc/README-2016.md)

####
* 打包编译
    * 从gradle说起
    * 库管理，上传module的jcenter
    * 多渠道打包
    * 热补丁

####
* 杂七杂八
    * [MVP模式](./doc/README-2016.md)
    * [基于状态管理的复杂业务逻辑如何实现？](./doc/README-2016.md)


####
* 其他第三方常用库介绍
    * 统计：友盟统计
    * 更新：友盟自动更新
    * 反馈：友盟反馈
    * 第三方登录
    * 第三方分享
    * 七牛：图片云服务
    * 图片选择器
    * 自定义相机
    * 视频播放：第三方平台依赖
    * 视频播放：视频自己管理
    * 录像
    * 音频播放器
    * 自定义app内置浏览器
