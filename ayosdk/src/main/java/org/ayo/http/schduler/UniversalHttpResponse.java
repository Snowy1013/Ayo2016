package org.ayo.http.schduler;

import java.io.InputStream;
import java.util.Map;

/**
 * http response
 * if http status code is not 200, then code = -1， data = error info
 * @author cowthan
 *
 */
public class UniversalHttpResponse {
	
	public InputStream inputStream;
	public String data;
	public int code;
	public Map<String, String> headers;
	
	/**
	 * http request is ok [200, 300)
	 * @return
	 */
	public boolean isSuccess(){
		return code >= 200 && code < 300;
	}

}
