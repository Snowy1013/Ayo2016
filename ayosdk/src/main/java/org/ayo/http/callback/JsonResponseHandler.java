package org.ayo.http.callback;

import org.ayo.LogInner;
import org.ayo.http.HttpProblem;
import org.ayo.http.schduler.UniversalHttpResponse;
import org.ayo.lang.JsonUtils;
import org.ayo.lang.Lang;

/**
 * json handler
 * eg1 new JsonResponseHandler<TestOrder>(TestOrder.class)
 * eg2 new JsonResponseHandler<List<Order>>(Order.class)
 * @author cowthan
 *
 * @param <T>
 */
public class JsonResponseHandler<T> extends BaseResponseHandler {
	
	//BaseHttpCallback<T> callback;
	//UniversalHttpResponse resp;
	Class<?> elementClass;
	
	/**
	 * @param elementClass
	 */
	public JsonResponseHandler(Class<?> elementClass){
		this.elementClass = elementClass;
	}
	
	/**
	 * 
	 */
	public <T> void process(UniversalHttpResponse resp, BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz){
		//this.callback = callback;
		//this.resp = resp;
		try {
			_process(resp, callback, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			FailRespnseModel fm = new FailRespnseModel(-1, e.getMessage());
			callback.onFinish(false, HttpProblem.DATA_ERROR, fm, null);
		}
	}
	
	private <T> void _process(UniversalHttpResponse resp, BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz){
		
		if(!resp.isSuccess()){
			LogInner.print("Http请求失败，错误码：" + resp.code + ", 错误原因：" + resp.data);
			FailRespnseModel m = new FailRespnseModel(resp.code, "server error, status code is: " + resp.code);
			callback.onFinish(false, HttpProblem.SERVER_ERROR, m, null);
			return;
		}

		resp.data = callback.processRawResponse(resp.data);
		
		ResponseModel rm = parseResponseToModel(resp.data, clazz);
		
		if(rm.isOk()){
			///json parse
			T t = null;
			try {
				Class<?> c = elementClass;
				String json = rm.getResult();
				if(Lang.isEmpty(json) || json.equals("{}") || json.equals("[]")){
					
				}
				
				
				if(c == String.class){
					if(isArrayJson(json)){
						//would not parse ["aaa","dddd","eee"] to List<String>
						callback.onFinish(true, HttpProblem.OK, rm, (T)json);
					}else{
						callback.onFinish(true, HttpProblem.OK, rm, (T)json);
					}
				}else if(c == Boolean.class){
					callback.onFinish(true, HttpProblem.OK, rm, (T)new Boolean(true));
				}else{
					if(isArrayJson(json)){
						//try to parse ["aaa","dddd","eee"] to List<String>
						callback.onFinish(true, HttpProblem.OK, rm, (T)JsonUtils.getBeanList(json, c));
					}else{
						callback.onFinish(true, HttpProblem.OK, rm, (T)JsonUtils.getBean(json, c));
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				FailRespnseModel fm = new FailRespnseModel(-1, e.getMessage());
				callback.onFinish(false, HttpProblem.DATA_ERROR, fm, null);
			}
			
			///callback.onOK
		}else{
			callback.onFinish(false, HttpProblem.DATA_ERROR, rm, null);
		}
	}
	
	private boolean isArrayJson(String json){
		return json != null && json.startsWith("[");
	}


	@Override
	public ResponseModel parseResponseToModel(String response, Class<? extends ResponseModel> clazz) {
		ResponseModel rm = (ResponseModel) JsonUtils.getBean(response, clazz);
		return rm;
	}
	
}
