package org.ayo.http.callback;

import org.ayo.http.HttpProblem;

/**
 *
 * request.callback(
 * 		new GeniusHttpCallback<List<Article>>(Article.class, MyHttpResponse.class){
 * 			void onFinish(){}
 * 		}
 * 	).go();
 * 
 * @author cowthan
 *
 * @param <K>
 * @param <T>
 */
public abstract class BaseHttpCallback<T> {
	
	//public Class<?> elementClazz;
	
	
	/**
	 * if T is normal POJO, class = T.class
	 * if T is List<E>, class = E.class
	 */
	public BaseHttpCallback(){
	}
	
	public abstract void onFinish(boolean isSuccess, HttpProblem problem, ResponseModel resp, T t);
	
	/**
	 * @param current
	 * @param total
	 */
	public void onLoading(long current, long total){
		
	}
	
	
	/**
	 * the raw reponse will be pre-processed here
	 * @param rawResponse
	 * @return
	 */
	public String processRawResponse(String rawResponse){
		
		return rawResponse;
		
	}
	
}
