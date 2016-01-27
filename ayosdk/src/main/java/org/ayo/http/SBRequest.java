package org.ayo.http;

import org.ayo.http.callback.BaseHttpCallback;
import org.ayo.http.callback.BaseResponseHandler;
import org.ayo.http.callback.ResponseModel;
import org.ayo.http.schduler.HttpScheduler;
import org.ayo.http.schduler.HttpWorker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;


public class SBRequest {
	
	private SBRequest(){
	}
	
	public static SBRequest newInstance(){
		SBRequest r = new SBRequest();
		return r;
	}
	
	public Map<String, String> params = new HashMap<String, String>();
	public Map<String, String> headers = new HashMap<String, String>();
	public Map<String, File> files = new HashMap<String, File>();
	public String stringEntity;
	public String url = "";
	public String method = "get";
	public String flag = "";
	public HttpWorker worker;
	public Class<? extends ResponseModel> myRespClazz;
	
	
	//---------------------------------------------------------------//
	public SBRequest flag(String flag){
		if(!TextUtils.isEmpty(this.flag)){
			throw new RuntimeException("flag is duplicated.");
		}
		
		this.flag = flag;
		return this;
	}
	
	public SBRequest param(String name, String value){
		if(this.params == null) this.params = new HashMap<String, String>();
		if(value == null) value = "";
		params.put(name, value);
		return this;
	}
	
	private boolean uploadFile = false;
	private boolean needCompress = false;
	
	public SBRequest param(String name, File value){
		if(this.files == null) this.files = new HashMap<String, File>();
		files.put(name, value);
		uploadFile = true;
		return this;
	}
	
	public SBRequest header(String name, String value){
		if(this.headers == null) this.headers = new HashMap<String, String>();
		headers.put(name, value);
		return this;
	}
	
	public SBRequest method(String method){
		this.method = method;
		return this;
	}

    /**
     * don't know how to pass this in volly, now just work in xutils
     * @param entity
     * @return
     */
	public SBRequest stringEntity(String entity){
		this.stringEntity = entity;
		return this;
	}
	
	public SBRequest url(String url){
		this.url = url;
		return this;
	}
	
	public SBRequest worker(HttpWorker worker){
		this.worker = worker;
		return this;
	}
	
	/**
	 * depends on specific jons format
	 * @param myRespClazz
	 * @return
	 */
	public SBRequest myResponseClass(Class<? extends ResponseModel> myRespClazz){
		this.myRespClazz = myRespClazz;
		return this;
	}
	
	public <T> void go(final BaseResponseHandler responseHandler, final BaseHttpCallback<T> callback){
		HttpScheduler.start(this, responseHandler, callback);
	}
}
