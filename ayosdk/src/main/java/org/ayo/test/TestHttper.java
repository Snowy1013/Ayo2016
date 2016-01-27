package org.ayo.test;

import org.ayo.http.SBRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.JsonResponseHandler;
import org.ayo.http.callback.ResponseModel;
import org.ayo.http.schduler.HttpWorkerUseXUtils;

public class TestHttper {
	
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
	
}
