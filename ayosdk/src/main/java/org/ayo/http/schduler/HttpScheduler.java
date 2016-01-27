package org.ayo.http.schduler;

import org.ayo.LogInner;
import org.ayo.file.IOUtils;
import org.ayo.http.HttpHelper;
import org.ayo.http.SBRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseHandler;
import org.ayo.lang.Strings;
import android.os.AsyncTask;

public class HttpScheduler {
	
	/**
	 * 发起http请求
	 * @param req
	 * @param callback
	 */
	public static <T> void start(final SBRequest req, final BaseResponseHandler responseHandler, final BaseHttpCallback<T> callback){
		
	    if(req.method.equals("get")){
	        req.url = HttpHelper.makeURL(req.url, req.params);
	    }
		
		///
		printRequest(req);
		
		if(req.worker.enableAsync()){
			req.worker.performRequestAsync(req, responseHandler, callback);
		}else{
			startSync(req, responseHandler, callback);
		}
		
	}
	
	private static <T> void startSync(final SBRequest req, final BaseResponseHandler responseHandler, final BaseHttpCallback<T> callback){
		new AsyncTask<Void, Void, UniversalHttpResponse>(){

			@Override
			protected UniversalHttpResponse doInBackground(Void... params) {
				
				UniversalHttpResponse resp = req.worker.performRequest(req);
				if(resp.isSuccess()){
					String s = Strings.fromStream(resp.inputStream);
					resp.data = s;
					IOUtils.closeQuietly(resp.inputStream);
				}
				return resp;
			}
			
			protected void onPostExecute(UniversalHttpResponse r) {
				responseHandler.process(r, callback, req.myRespClazz);
				//JsonResponseHandler.newHandler(r, callback).process(req.myRespClazz);
			}
			
		}.execute();
	}
	
	/**
	 * @param req
	 * @param callback
	 */
	public static <T> void startUpload(SBRequest req, final BaseResponseHandler responseHandler, BaseHttpCallback<T> callback){
		//UploadUtils.uploadMethod(req.flag, req.params, req.headers, req.files, req.url, req.myRespClazz, responseHandler, callback);
	}

	/**
	 * @param flag
	 * @param current
	 * @param total
	 * @param callback
	 */
	public static <T> void notifyProgress(String flag, int current, int total, BaseHttpCallback<T> callback){
	}
	
	public static void cancel(String flag){
		
	}
	
	private static void printRequest(SBRequest request) {
		try {
            LogInner.debug("--------------------");
            LogInner.debug("request param：");
            HttpHelper.printMap(request.params);
            LogInner.debug("request header：");
            HttpHelper.printMap(request.headers);
            LogInner.debug("request eintity：");
            LogInner.debug(request.stringEntity);
            LogInner.debug("reqeust URL：");
            LogInner.debug(request.url);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
