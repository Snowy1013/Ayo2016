package org.ayo.http.schduler;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.ayo.Ayo;
import org.ayo.LogInner;
import org.ayo.http.HttpProblem;
import org.ayo.http.SBRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseHandler;
import org.ayo.http.callback.FailRespnseModel;

import java.util.HashMap;
import java.util.Map;

public class HttpWorkerUseVolly implements HttpWorker{

	private boolean enableAsync = true;
	private static RequestQueue queue;
	
	public HttpWorkerUseVolly(boolean enableAsync){
		if(!enableAsync){
			throw new RuntimeException("Volly only has async mode");
		}
		this.enableAsync = enableAsync;
		if(queue == null) queue = Volley.newRequestQueue(Ayo.context);
	}
	
	@Override
	public <T> void performRequestAsync(final SBRequest request,
			final BaseResponseHandler responseHandler, 
			final BaseHttpCallback<T> callback) {
		
		RequestQueue mQueue = queue; 
		String url = request.url;
		int m = request.method.equalsIgnoreCase("get") ? Method.GET : Method.POST;
		
		StringRequest stringRequest = new StringRequest(m, url,  
                new Response.Listener<String>() {  
                    @Override  
                    public void onResponse(String response) {
						LogInner.print("请求结果(" + request.flag + "): \n" + response);
                    	UniversalHttpResponse resp = new UniversalHttpResponse();
                    	resp.code = 200;
                    	resp.data = response;
                    	resp.headers = null; //responseInfo.getAllHeaders();
                    	resp.inputStream = null;
                    	responseHandler.process(resp, callback, request.myRespClazz);
                    	//JsonResponseHandler.newHandler(resp, callback).process(request.myRespClazz);
                    }  
                }, new Response.ErrorListener() {  
                    @Override  
                    public void onErrorResponse(VolleyError error) {  
                    	LogInner.debug("HTTP reqeust failed（" + request.flag + "）--" + error.getMessage());
                        error.printStackTrace();
                        callback.onFinish(false, HttpProblem.SERVER_ERROR, new FailRespnseModel(-1, error.getMessage()), null);
                    }  
                })
		{
			/**
			 */
			@Override  
		    protected Map<String, String> getParams() throws AuthFailureError {  
				if(request.method.equals("post")) return request.params;
				return new HashMap<String, String>();
		    }  
			
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return request.headers;
			}
			
//			@Override
//			public String getBodyContentType() {
//				// TODO Auto-generated method stub
//				System.out.println("haha: " + super.getBodyContentType());
//				return super.getBodyContentType();
//			}
//			
//			@Override  
//			public RetryPolicy getRetryPolicy()  
//			{  
//				RetryPolicy retryPolicy = new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);  
//				return retryPolicy;  
//			}  
			
			@Override  
            protected Response<String> parseNetworkResponse(  
                    NetworkResponse response) {  
                try {  
                       
                    Map<String, String> responseHeaders = response.headers;  
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    LogInner.debug("get cookie: ------" + rawCookies);
                    return super.parseNetworkResponse(response);
                } catch (Exception e) {
                    return Response.error(new ParseError(e));  
                }   
            }  
			
		}; 
		stringRequest.setTag(request.flag);
		//stringRequest.setShouldCache(request.useCache);
//		stringRequest.setCacheEntry(entry);
//		stringRequest.setRequestQueue(requestQueue);
//		stringRequest.setRetryPolicy(retryPolicy);
//		stringRequest.setSequence(sequence);
		
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		
		mQueue.add(stringRequest);
	}

	@Override
	public <T> UniversalHttpResponse performRequest(SBRequest request) {
		throw new RuntimeException("Volly only has async mode");
	}

	@Override
	public boolean enableAsync() {
		return enableAsync;
	}

}
