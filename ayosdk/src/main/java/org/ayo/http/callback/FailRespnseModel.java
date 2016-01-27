package org.ayo.http.callback;

public class FailRespnseModel extends ResponseModel{

	public int code = -1;
	public String msg;
	
	public FailRespnseModel(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	@Override
	public boolean isOk() {
		return false;
	}

	@Override
	public int getResultCode() {
		return -1;
	}

	@Override
	public String getFailMessage() {
		return msg;
	}

	@Override
	public String getResult() {
		return "null";
	}
	
	

}
