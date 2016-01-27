package org.ayo.http.callback;

public class HtmlResponseModel extends ResponseModel{

	public String html;
	
	@Override
	public boolean isOk() {
		return true;
	}

	@Override
	public int getResultCode() {
		return 0;
	}

	@Override
	public String getFailMessage() {
		return "";
	}

	@Override
	public String getResult() {
		return html;
	}
	
	
}
