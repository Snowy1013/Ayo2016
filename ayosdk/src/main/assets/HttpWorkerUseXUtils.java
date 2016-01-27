import org.ayo.LogInner;
import org.ayo.http.HttpProblem;
import org.ayo.http.SBRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseHandler;
import org.ayo.http.callback.FailRespnseModel;
import org.ayo.http.schduler.HttpWorker;
import org.ayo.http.schduler.UniversalHttpResponse;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.StringBody;
import org.xutils.http.request.HttpRequest;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class HttpWorkerUseXUtils implements HttpWorker{
	
	private boolean enableAsync = true;
	
	public HttpWorkerUseXUtils(boolean enableAsync){
		this.enableAsync = enableAsync;
	}

	@Override
	public <T> UniversalHttpResponse performRequest(final SBRequest request) {
		String url = request.url;
        HttpMethod m = request.method.equalsIgnoreCase("get") ? HttpMethod.GET : HttpMethod.POST;

        RequestParams params = new RequestParams();
        if(request.headers != null){
            for(Map.Entry<String, String> entry: request.headers.entrySet()){
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if(request.params != null){
            for(Map.Entry<String, String> entry: request.params.entrySet()){
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }

        if(request.files != null){
			for(String k : request.files.keySet()){
				params.addBodyParameter(k, request.files.get(k));
			}
		}

        if(request.stringEntity != null && !request.stringEntity.equals("")){
            try {
                StringBody ent = new StringBody(request.stringEntity, "utf-8");
                params.setRequestBody(ent); //.setBodyEntity(ent);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        //HttpUtils http = new HttpUtils();
        try {
			ResponseStream rs = x.http().requestSync(m, params, ResponseS); //.sendSync(m, url, params);
			UniversalHttpResponse r = new UniversalHttpResponse();
			r.code = rs.getStatusCode();
			r.headers = new HashMap<String, String>();
			r.inputStream = rs.getBaseStream();
			return r;
		} catch (HttpException e) {
			e.printStackTrace();
			UniversalHttpResponse r = new UniversalHttpResponse();
			r.code = -1;
			r.headers = null;
			r.inputStream = null;
			r.data = e.getMessage();
			return r;
		}
	}

	@Override
	public <T> void performRequestAsync(final SBRequest request, final BaseResponseHandler responseHandler, final BaseHttpCallback<T> callback) {
		
	        String url = request.url;
	        HttpRequest.HttpMethod m = request.method.equalsIgnoreCase("get") ? HttpRequest.HttpMethod.GET : HttpRequest.HttpMethod.POST;

	        RequestParams params = new RequestParams();
	        if(request.headers != null){
	            for(Map.Entry<String, String> entry: request.headers.entrySet()){
	                params.addHeader(entry.getKey(), entry.getValue());
	            }
	        }

	        if(request.params != null){
	            for(Map.Entry<String, String> entry: request.params.entrySet()){
	                params.addBodyParameter(entry.getKey(), entry.getValue());
	            }
	        }
	        
	        if(request.files != null){
				for(String k : request.files.keySet()){
					params.addBodyParameter(k, request.files.get(k));
				}
			}

	        if(request.StringBody != null && !request.StringBody.equals("")){
	            try {
	                StringBody ent = new StringBody(request.StringBody, "utf-8");
	                params.setBodyEntity(ent);
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	        }


	        HttpUtils http = new HttpUtils();
	        http.configHttpCacheSize(0);
	        http.send(m,
	                url,
	                params,
	                new RequestCallBack<String>() {

	                    @Override
	                    public void onStart() {

	                    }

	                    @Override
	                    public void onLoading(long total, long current, boolean isUploading) {
	                       callback.onLoading(current, total);
	                    }

	                    @Override
	                    public void onSuccess(ResponseInfo<String> responseInfo) {
	                        //testTextView.setText("reply: " + responseInfo.result);
	                        LogInner.print("statusCode(" + request.flag + ")--" + responseInfo.statusCode);
	                        LogInner.print("reasonPhrase(" + request.flag + ")--" + responseInfo.reasonPhrase);
	                        LogInner.print("contentEncoding(" + request.flag + ")--" + responseInfo.contentEncoding);
	                        LogInner.print("contentType(" + request.flag + ")--" + responseInfo.contentType);
							LogInner.print("result(" + request.flag + ")--" + responseInfo.result);
	                    	UniversalHttpResponse resp = new UniversalHttpResponse();
	                    	resp.code = 200;
	                    	resp.data = responseInfo.result;
	                    	resp.headers = null; //responseInfo.getAllHeaders();
	                    	resp.inputStream = null;
	                    	//JsonResponseHandler.newHandler(resp, callback).process(request.myRespClazz);
	                    	responseHandler.process(resp, callback, request.myRespClazz);
	                    }

	                    @Override
	                    public void onFailure(HttpException error, String msg) {
	                    	LogInner.print("request failed: (" + request.flag + ")--" + msg + "(" + error.getMessage() + ")");
	                    	callback.onFinish(false, HttpProblem.SERVER_ERROR, new FailRespnseModel(-1, msg), null);
	                    }
	                });
		
	}
	
	@Override
	public boolean enableAsync() {
		return enableAsync;
	}

	

}
