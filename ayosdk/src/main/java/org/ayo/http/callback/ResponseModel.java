package org.ayo.http.callback;

/**
 * 如果http请求的返回的状态码是[200...300)，说明请求成功，但基于业务逻辑，又有个成功和失败的区别
 * @author cowthan
 *
 */
public abstract class ResponseModel {

	/**
	 * 告诉解析器如何判断业务逻辑是成功的，如果返回true，就可以解析getResult的结果，否则就提取getResultCode和getFailMessage来提示用户
	 * @return
	 */
	public abstract boolean isOk();
	
	/**
	 * 是app server和app之间协商好的一组code，代表各种业务逻辑的状态
	 * @return
	 */
	public abstract int getResultCode();
	
	/**
	 * app server返回的用户可读的提示信息
	 * @return
	 */
	public abstract String getFailMessage();
	
	/**
	 * 业务逻辑成功的前提下，解析这个结果得到数据，如果是列表，这里可能得到空列表，请注意
	 * 基本上这里返回的不是{}就是[]
	 * @return
	 */
	public abstract String getResult();
	
	
	
}
