package org.ayo.http.callback;

import org.ayo.http.HttpProblem;
import org.ayo.http.schduler.UniversalHttpResponse;

/**
 * html reesponse handler
 * eg 1 new JsonResponseHandler<TestOrder>(TestOrder.class)
 * eg 2 new JsonResponseHandler<List<Order>>(Order.class)
 * @author cowthan
 *
 */
public class HtmlResponseHandler extends BaseResponseHandler {
	
	public HtmlResponseHandler(){
	}
	
	/**
	 * 
	 * @param clazz top level json
	 */
	public <T> void process(UniversalHttpResponse resp,
			BaseHttpCallback<T> callback, Class<? extends ResponseModel> clazz){
		try {
			callback.onFinish(true, HttpProblem.OK, null, (T)resp.data);
		} catch (Exception e) {
			e.printStackTrace();
			FailRespnseModel fm = new FailRespnseModel(-1, e.getMessage());
			callback.onFinish(false, HttpProblem.DATA_ERROR, fm, null);
		}
	}
	

	@Override
	public ResponseModel parseResponseToModel(String response, Class<? extends ResponseModel> clazz) {
		HtmlResponseModel rm = new HtmlResponseModel();
		rm.html = response;
		return rm;
	}


	
}
