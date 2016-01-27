package org.ayo.http.schduler;

import org.ayo.http.SBRequest;
import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseHandler;


/**
 *
 * there are a lot of http library in android, some support sync, some support async and some support both,
 * so here we provide 2 perform interfaces
 *
 * @author seven
 *
 */
public interface HttpWorker{
	/**
	 * start a request, get a response, all params you need is in reqeust
	 * @param request
	 */
	public abstract <T> UniversalHttpResponse performRequest(SBRequest request);
	
	public abstract <T> void performRequestAsync(SBRequest request, BaseResponseHandler responseHandler, BaseHttpCallback<T> callback);
	
	
	/**
	 * @return
	 */
	public abstract boolean enableAsync();
}
