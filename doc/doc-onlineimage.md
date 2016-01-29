网络图片加载
===========================
* 网络图片加载
    * 对外的统一接口
    * UIL实现
    * Fresco实现
    * XUtils实现


主要针对UniversalImageLoader分析了实现网络图片加载框架需要考虑的问题。
* 大体需要考虑的问题有：
    * 图片下载
    * 图片缓存：Disk
    * 图片缓存：Memory
    * 图片decode
    * 图片显示效果

* 还有其他问题：
    * 哪些工作可以异步来做
    * 图片占位图

##1 下载

下载功能是可以配置的，接口统一为`ImageDownloader`，输入Uri，输出`InputStream（ContentLengthInputStream）`，

* 根据图片的来源，分为以下几个Scheme：
    * http或者https
    * file：存sd卡或上下文目录
    * content：和MediaStore有关
    * assets：assets中的图片
    * drawable：raw中的图片

上面这几个就是你传的URI的前缀

去掉前缀，Scheme.crop()方法
####

* __提供了一个BaseImageDownloader__
    * http或者https： 从网络获取：HttpUrlConnection
    * file：自动判断是否vedio，是就取缩略图，普通文件就BufferedInputStream
    * content：估计这个就是相册选择之后返回来的Uri
        * 视频：缩略图，但是以MediaStore的方式读
        * 以content://com.android.contacts开头：好像是联系人头像
    * assets：去掉前缀之后，直接调用context.getAssets().open(filePath)
    * drawable：这个直接上代码吧，估计传的uri应该是： "drawable://" + R.raw.img

```java
String drawableIdString = Scheme.DRAWABLE.crop(imageUri);
int drawableId = Integer.parseInt(drawableIdString);
return context.getResources().openRawResource(drawableId);
```

下面是各种来源的图片加载示例：
```java

```

##2 Disk缓存

下载前检查缓存，下载后存到缓存
####

```java
//UIL中提供的缓存配置：
.diskCache(DiskCache)
.diskCacheFileNameGenerator(new Md5FileNameGenerator())
.diskCacheSize(50 * 1024 * 1024) // 100
.diskCacheFileCount(500)
.diskCacheExtraOptions()
```

* 先说这个DiskCache接口，具体实现参考抽象基类BaseDiskCache
    * getDirectory()   获取缓存在哪个目录下
    * save(String imageUri, InputStream imageStream, IoUtils.CopyListener listener)：将从imageUri下载到的imageStream存到文件中
    * save(String imageUri, Bitmap bitmap): 把Bitmap缓存起来，100%压缩比，png格式
    * File get(String imageUri)：获取缓存的文件
    * remove(String imageUri);删除缓存的文件

* DiskCache是硬盘缓存的统一接口，BaseDiskCache是抽象基类，默认提供的是：
    * LimitedAgeDiskCache： 超时机制
        * 初始化：需要传入个maxAge，就是缓存保存的最长时间
        * save时：需要修改一下文件修改时间，file.setLastModified(millis)，然后放到一个map里，叫loadingDate，存的是文件和上次访问时间
        * get时：先在loadingDate里找上次访问时间，如果没有，就访问file.lastModified()，然后跟当前时间对比，超时就删除文件
        * 结果：返回的File可能已经被delete了！
    * UnlimitedDiskCache： 无机制，就直接继承自BaseDiskCache，不对大小和时间做任何限制
    * LruDiskCache： LruCache策略，直接实现DiskCache


##3 解码

接口ImageDecoder，提供了接口：

Bitmap decode(ImageDecodingInfo imageDecodingInfo) throws IOException;


先说这个ImageDecodingInfo从哪儿来，我也不知道，其实就是decode需要用到的所有信息，基本都是从ImageView取出来的长宽高等
肯定是二次采样了


* 问题：对应不同的ImageView宽高设定，如何decode
    * 如果ImageView设置了宽高，则照着这个宽高设置缩放比
    * 如果ImageView都是wrap，应该就是以屏幕宽高为限制了
    * 如果ImageView宽高设置比较乱，怎么弄？


* decode流程：拿到InputStream之后
    * 以options.inJustDecodeBounds取出宽高，和旋转角度，组成一个ImageFileInfo对象
    * 重置InputStream，还得继续采啊
    * 计算sample，就是缩放比例
    * 调用BitmapFactory.decodeStream采样bitmap


##4 显示

先看配置项吧
```java
DisplayImageOptions.Builder opt = new DisplayImageOptions.Builder();
//opt.bitmapConfig(Bitmap.Config.ALPHA_8);
//opt.cacheInMemory(true);
//opt.cacheOnDisk(true);
//opt.considerExifParams(true);
//opt.decodingOptions();- //?????
//opt.delayBeforeLoading(delayInMillis);
//opt.delayBeforeLoading(300);
opt.displayer(new FadeInBitmapDisplayer(300));//CircleBitmapDisplayer, RoundedBitmapDisplayer, RoundedVignetteBitmapDisplayer, SimpleBitmapDisplayer
//opt.extraForDownloader(Object);
//opt.handler(Handler);
//opt.imageScaleType(ImageView.ScaleType)

//opt.preProcessor(BitmapProcessor);
//opt.postProcessor(BitmapProcessor);
//opt.resetViewBeforeLoading(true);
//opt.showImageOnLoading(int|Drawable);
//opt.showImageOnFail(int|Drawable);
//opt.showImageForEmptyUri(int|Drawable);

ImageLoaderConfiguration.defaultDisplayImageOptions(opt.build())
```

* 可以配置几个常用的显示效果：
    * FadeInBitmapDisplayer
    * CircleBitmapDisplayer
    * RoundedBitmapDisplayer
    * RoundedVignetteBitmapDisplayer
    * SimpleBitmapDisplayer

其他不多说了，好像和主配置也重复了


##5 Ayo中对UIL的配置

参考VanGogh，名字起的有装逼嫌疑，你懂的

```java
public static void init(Context context){
    DisplayImageOptions.Builder opt = new DisplayImageOptions.Builder();
    //opt.bitmapConfig(Bitmap.Config.ALPHA_8);
    //opt.cacheInMemory(true);
    //opt.cacheOnDisk(true);
    //opt.considerExifParams(true);
    //opt.decodingOptions();- //?????
    //opt.delayBeforeLoading(delayInMillis);
    //opt.delayBeforeLoading(300);
    opt.displayer(new CircleBitmapDisplayer());//CircleBitmapDisplayer, RoundedBitmapDisplayer, RoundedVignetteBitmapDisplayer, SimpleBitmapDisplayer
    //opt.extraForDownloader(Object);
    //opt.handler(Handler);
    //opt.imageScaleType(ImageView.ScaleType)

    //opt.preProcessor(BitmapProcessor);
    //opt.postProcessor(BitmapProcessor);
    //opt.resetViewBeforeLoading(true);
    //opt.showImageOnLoading(int|Drawable);
    //opt.showImageOnFail(int|Drawable);
    //opt.showImageForEmptyUri(int|Drawable);

    ImageLoaderConfiguration config =
            new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    //.diskCacheFileNameGenerator(new SimpleFileNameGenerator())
                    .memoryCacheExtraOptions(480, 800)
                    .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                    .memoryCacheSize(2 * 1024 * 1024)
                    .diskCacheSize(50 * 1024 * 1024) // 100
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .diskCacheFileCount(500)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .writeDebugLogs() // Remove
                    .threadPoolSize(2)
                    .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout-5s, readTimeout-30s
                    .defaultDisplayImageOptions(opt.build())
                    .build();
    ImageLoader.getInstance().init(config);
}

```

##6 顶层VanGogh图片加载兼容框架

这个框架，本来是学习时，为了测试不同的网络图片加载框架做的，对外统一接口嘛！！！

###初始化

一般放在全局Application的onCreate中，前三行是配置全局的占位图，

* 这里有几个自作主张的地方：
    * 这里自作主张的把一个项目中需要加载的图片分成了
    * 大中小三个等级，对应也应该提供大中小3个占位图
    * 默认三个占位图都是一个，纯属简单起见，你也可以调用3个参数的方法单独设置
        * loading图
        * 错误图
        * 空白图
    * 最自作主张的地方就是所有UIL的配置都在init中给了默认值了，想改只能改源码了你

```java
VanGogh.initImageBig(R.mipmap.loading_big);
VanGogh.initImageMiddle(R.mipmap.loading_big);
VanGogh.initImageSmall(R.mipmap.loading_big);
VanGogh.init(this);
```

###加载图片

* 分几种情况
    * 大图：VanGogh.paper(imageview).paintBigImage(url, null);
    * 中图：VanGogh.paper(imageview).paintMiddleImage(url, null);
    * 小图：VanGogh.paper(imageview).paintSmallImage(url, null);
    * 其他：如果不想用上面设置的默认占位图，可以调用VanGogh.paper(imageview).paint(url, null, null);
注意，参数2和3是状态和进度监听

###访问缓存的图片

```java
String cachePath = VanGogh.getLocalCachePath(url);
if(Lang.isEmpty(cachePath)){
    ///没有缓存
}
```

###下载图片

VanGogh.loadImage(String url, final String savePath, final ImageLoadingListener listener)

没有提供进度监听，为啥？再说吧，写到这里时，不爱动弹了

###还要继续写

* 即将兼容
    * fresco
    * Picasso

* 还要研究
    * 哪些可以异步处理
    * 先命中缓存，再请求网络
    * jni decode
    * 小图替换怎么做：给不同图片指定对应的小图占位
    * GifDrawable如何兼容进来
    * ScaleType和ImageView的wrap的处理
    * decode计算sample的优化
