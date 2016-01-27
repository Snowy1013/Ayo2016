日志
===========================
* 日志
    * 调试日志
        * 快速打log，方便过滤
        * 方便开关
        * 保存到文件
        * json和xml友好
    * 崩溃日志
        * 靠谱就行
    * 用户行为日志
        * 怎么能对程序员透明化呢！！！

####
注意，打印json是经常性的工作，但logcat默认有个4K字符的限制，稍长的json就打印不全，所以得考虑另辟蹊径。

这里用的就这个：https://github.com/JiongBull/jlog
文档：https://github.com/JiongBull/jlog/blob/master/README_ZH.md

__Logger要被删除了，请不要再使用，留着仅仅是为了兼容之前代码__

####

##1 jlog怎么用

###初始化：在Ayo.init中：
```java
List<LogLevel> logLevels = new ArrayList<>(); //哪些级别的日志可以输出到文件中
logLevels.add(LogLevel.ERROR);
logLevels.add(LogLevel.JSON);
JLog.init(context)
        .setDebug(openLog)
        .writeToFile(logToFile)
        .setLogDir(Ayo.ROOT + "log")
        .setLogPrefix("log_")
        .setLogSegment(LogSegment.ONE_HOUR)  //志按照时间切片写入到不同的文件中
        .setLogLevelsForFile(logLevels)
        .setCharset("UTF-8");
```
* 注意：
    * openLog和logToFile由init传入
    * 如果要将日志保存到文件，路径是sd卡/应用ROOT/log目录下，以log_开头
    * ERROR和JSON级别会被保存到文件

以下方法不指定tag，则默认tag是当前类名

###
```java
//LogLevel有以下几个：
VERBOSE("VERBOSE"),   v
DEBUG("DEBUG"),       d
INFO("INFO"),         i
WARN("WARN"),         w
ERROR("ERROR"),       e
WTF("WTF"),           wtf
JSON("JSON");         json

//打log
JLog.i(TAG, String)
JLog.json(json)
```

* 注意
    * 怎么只有JLog.i,e,wtf好用呢, v和d出不来
    * json()已经改成用i来打印，格式也还可以，至少能打印全了，当然格式还可以继续美化
    * 日志文件目录本来是传入目录名，现在改成传入全路径了


### 注意事项

* 实践中如何配置：
    * 开发调试，开log，关文件：Ayo.init(this, "ayo", false, true);
    * 打包测试，关log，开文件：Ayo.init(this, "ayo", true, false);
    * 打包发布，关log，关文件：Ayo.init(this, "ayo", false, false);
    * 按照上述配置，日志文件在sd卡/ayo/log/下


##2 崩溃日志



##3 用户行为日志