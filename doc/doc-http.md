HTTP请求
===========================
* HTTP请求
    * 怎么使用
    * sdk简介
    * 普通请求
    * 文件上传
    * 文件下载
    * Cookie
    * 统一的接口和不同的实现

####
 ##1 如何接入你的代码

 先贴出一段代码：
 ```java
public static SBRequest getSBRequest(Class<? extends ResponseModel> clazz) {

     return SBRequest.newInstance()
            .myResponseClass(clazz)
            .header("os", "android")
            .header("sid", "")
            .header("version", "1");
}

public static void getArticle(String flag, int pageNow,  final BaseHttpCallback<TestOrderList> callback, Class<? extends ResponseModel> clazz){
    TestHttper.getSBRequest(clazz)
            .flag(flag)
            .url("http://api.daogou.bjzzcb.com/v3/channel/home2_2_1")//
            .worker(new HttpWorkerUseXUtils(true))
            //.worker(new HttpWorkerUseVolly(true))
            .method("post")
            .param("startId", "0")
            .param("tord", "up")
            .param("cate", "0")
            .param("topicId", "0")
            .param("count", "20")
            .param("tagId", "0")
            .go(new JsonResponseHandler<TestOrderList>(TestOrderList.class), callback);
}
```

###(1) 构造你的request：

包括url，请求方式，get参数，post参数等

###(2) 定义你的ResponseModel实现类和BaseResponseHandler实现类

注意，只有在http返回成功的情况下，即statusCode=200时，才会走到这里

* ResponseModel提供了响应结果中，由app server提供的成功和失败信息
* BaseResponseHandler提供了：
    * 响应结果转换为ResponseModel，由用户实现
    * 如果成功，result字段转换为对应的业务bean对象，回调callback
    * 如果失败，取出error message，回调callback

注意这都是个什么东西：
如果是json，那这个类就是json的顶层类型，例如json格式统一为：
```
{
    "code": 0,
    "result": {}或[]或""
    "message": 错误信息
}
```
则你定义的ResponseModel之类就是：
```java
public class MyHttpResponse extends ResponseModel {

    public int code;
	public String message;
	public String result;

	@Override
	public boolean isOk() {
		return code == 0;
	}

	@Override
	public int getResultCode() {
		return code;
	}

	@Override
	public String getFailMessage() {
		return message;
	}

	@Override
	public String getResult() {
		return result;
	}

}
```

ayo中提供了默认的JsonResponseHandler实现，但json顶层格式不同的项目很难统一，所以ResponseModel需要你自己来定义。
此外，如果默认的JsonResponseHandler没有考虑到你的项目中的一些细节问题，请先联系你的服务器开发人员，实在不行再自己定义。

###(3) 你的业务Bean

你的业务Bean应该基于result中的结果定义

###(4) HtmlResponseHandler和HtmlResponseModel：返回结果为html

当你的http请求感兴趣的是返回html时，请使用这两个类

###(5) xml的处理：没有提供，你懂的

###(6) 如何自定义你的JsonHandler：

* 主要任务就是：
    * 解析出ResponseModel对象
    * 分析ResponseModel的成功和失败信息
    * 成功，则解析出业务bean对象，调用callback
    * 失败，则解析出失败信息，调用callback

请参考ayo中的JsonResponseHandler

###(7) 以上都有了之后，就是定义你的callback，即BaseHttpCallback的实现类

```java
BaseHttpCallback<TestOrderList> callback = new BaseHttpCallback<TestOrderList>(){

    @Override
	public void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, TestOrderList t) {
		if(isSuccess){
			///t就是你感兴趣的对象
		}else{
			///problem和resp就是你感兴趣的信息
		}
	}
};
```

###(8) JsonResponseHandler中的泛型问题

因为本库并没有使用有关TypeToken的东西，所以如果你的BaseHttpCallback感兴趣的是List，而非纯bean，你应该这样实现：

```java
public static void queryNewsByCateNo_0(String flag,  String startId, String cate, final BaseHttpCallback<List<Article>> callback){
    ZYHttp.getSBRequest().flag(flag)
    .url(Urls.GET_ARTICLE_LIST_V2)
    .method("get")
    .param("startId", startId)
    .param("tord", "up")
    .param("cate", cate)
    .param("topicId", "0")
    .param("count", PAGE_SIZE)
    .param("tagId", "0")
    .go(new JsonResponseHandler<List<Article>>(Article.class), callback);
}
//注意JsonResponseHandler传入的泛型和参数
```
* 结论：
    * 如果BaseHttpCallback感兴趣的是List，则传给JsonResponseHandler的泛型就是List，而构造方法的参数是List的元素类型的class
    * 如果BaseHttpCallback感兴趣的是纯bean，则传给JsonResponseHandler的泛型参数和构造参数都是同一个类型

##2 定制你的http底层请求库

本框架对外提供了一个统一的接口，但底层可以由不同的第三方库来实现，现在已经有了：

    * volly实现：HttpWorkerUseVolly，仅支持异步
    * XUtils 3.0实现：HttpWorkerUseXUtils，仅支持异步
    * okhttp实现：其实还没实现，需要配合RxJava


注意`HttpWorker`这个类，你的定制类必须实现此接口，基本上这个类的功能就是返回InputStream，
而返回之后的工作，则由ResponseHandler接手，基本工作流程：

    * HttpWorker根据SBRequest中的表单信息发起请求
    * ResponseHandler将InputStream解析成感兴趣的Response类型，如String，文件等
    * ResponseHandler将Response解析成业务bean，调用callback
    * 通过实现BaseHttpCallback，回到业务逻辑中


但由于大多数开源第三方库提供的层次都太高，所以大多数情况下，第二步被屏蔽


##3 上传文件




##4 下载文件

