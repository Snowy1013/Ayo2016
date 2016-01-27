import org.ayo.LogInner;
import org.ayo.http.HttpHelper;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseHandler;
import org.ayo.http.callback.ResponseModel;
import org.ayo.http.schduler.UniversalHttpResponse;

import java.io.File;
import java.util.Map;


public class UploadUtils {
	public static <T> void uploadMethod(final String flag, final Map<String, String> params,
			final Map<String, String> headers,
			final Map<String, File> files,
			final String url, 
			final Class<? extends ResponseModel> clazz,
			final BaseResponseHandler responseHandler,
			final BaseHttpCallback<T> callback) {
		
		try {
			LogInner.debug("--------------------");
			LogInner.debug("request parameters：" + flag);
			HttpHelper.printMap(params);
			LogInner.debug("request headers：");
			HttpHelper.printMap(headers);
			LogInner.debug("upload files：");
			for(File f : files.values()){
				LogInner.debug("file--" + f.getAbsolutePath());
			}
			LogInner.debug("request url：" + url);
		} catch (Exception e) {
			
		}
		
		RequestParams rp = new RequestParams();
		if(params != null){
			for(String k : params.keySet()){
				rp.addBodyParameter(k, params.get(k));
			}
		}
		if(files != null){
			for(String k : files.keySet()){
				rp.addBodyParameter(k, files.get(k));
			}
		}
		if(headers != null){
			for(String k : headers.keySet()){
				rp.addHeader(k, headers.get(k));
			}
		}
		
		
		HttpUtils http = new HttpUtils();
		http.configSoTimeout(30000);
		http.configTimeout(30000);
		http.send(HttpRequest.HttpMethod.POST, url, rp,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						LogInner.debug("upload: " + "begin");

						//callback.onRequestStart(flag);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						LogInner.debug("upload: " + current + "/" + total);
						if (isUploading) {
							int max  = 100;
							int progress = (int) (max*current/total);
							//callback.onDoing(progress, max);
						} else {
							LogInner.debug("upload: " + "isUploading=false");
						}

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogInner.debug("upload:" + "ok--" + responseInfo.result);
						
						UniversalHttpResponse r = new UniversalHttpResponse();
						r.code = responseInfo.statusCode;
						r.data = responseInfo.result;
						r.headers = null; //responseInfo.getHeaders(name);
						responseHandler.process(r, callback, clazz);
						//JsonResponseHandler.newHandler(r, callback).process(clazz);
						//callback.onRequestFinished(true, responseInfo.result, "", flag);
					}

					@Override
					public void onFailure(
							com.lidroid.xutils.exception.HttpException error,
							String msg) {
						LogInner.debug("upload:" + "fail--" + msg + "\n"
								+ error.getMessage());
						UniversalHttpResponse r = new UniversalHttpResponse();
						r.code = -1;
						r.data = msg;
						r.headers = null; //responseInfo.getHeaders(name);
						responseHandler.process(r, callback, clazz);
						//JsonResponseHandler.newHandler(r, callback).process(clazz);
						//callback.onRequestFinished(false, "", error.getLocalizedMessage(), flag);
					}
				});
	}


}
