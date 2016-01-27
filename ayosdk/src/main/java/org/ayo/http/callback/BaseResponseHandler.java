package org.ayo.http.callback;

import org.ayo.http.schduler.UniversalHttpResponse;

/**
 *
 * process the http response, and call the api of BaseHttpCallback
 * @author cowthan
 *
 */
public abstract class BaseResponseHandler {

	/**
	 * 处process the http response, and call the api of BaseHttpCallback
	 * @param resp data already has value, just don't use InputStream
	 * 
	 * if resp.code != 200, data may not have value, or data is error info
	 * 
	 * @param callback
	 * @param clazz  ResponseModel.class, the subclass of ResponseModel is the top level json
	 */
	public abstract <T> void process(UniversalHttpResponse resp, BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz);
	
	/**
	 * @param response
	 * @return
	 */
	public abstract ResponseModel parseResponseToModel(String response, Class<? extends ResponseModel> clazz);
}
