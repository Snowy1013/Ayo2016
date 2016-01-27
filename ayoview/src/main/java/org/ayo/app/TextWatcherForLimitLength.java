package org.ayo.app;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 限制EditText输入的字数，并提供统计当前字数的接口
 * 
 * commentEt.addTextChangedListener(new TextWatcherForLimitLength(commentEt){

			@Override
			public int getLimitedLength() {
				return 300;
			}

			@Override
			public void onLengthChanged(int length) {
				charLenTv.setText(length + "/300");
			}
			
		});
 * 
 * @author wangkai
 * @creation 2014年10月31日 下午2:46:24
 */
public abstract class TextWatcherForLimitLength implements TextWatcher{
	private CharSequence temp;
	private int selectionStart;
	private int selectionEnd;
	private EditText tv;
	
	public TextWatcherForLimitLength(EditText tv){
		this.tv = tv;
	}
	
	public abstract int getLimitedLength();
	public abstract void onLengthChanged(int length);
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		temp = s; //s是输入之前的字符串，即刚输入的还没加上
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}
	
	@Override
	public void afterTextChanged(Editable s) {
       selectionStart = tv.getSelectionStart();
       selectionEnd = tv.getSelectionEnd();
       int len = s.length();
       if (temp.length() > getLimitedLength()) {
           //s.delete(selectionStart - 1, selectionEnd);
    	   //s.replace(selectionStart - 1, selectionEnd, "");
           CharSequence cs = s.subSequence(0, getLimitedLength());
           len = cs.length();
    	   int tempSelection = selectionStart;
           tv.setText(cs);
           tv.setSelection(len);//设置光标在最后
       }
       this.onLengthChanged(len);
	}
}
