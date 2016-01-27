package org.ayo.view.dialog.loading;


import genius.android.view.R;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;


/**
 * 
 * loadDialog=new LoadingDialog(this);
   loadDialog.setTitle("loading...");
   
   if(loadDialog.isShowing()){
		loadDialog.dismiss();
	}
	loadDialog.show();
 * 
 * @author byl
 */
public class LoadingTopDialog extends Dialog {
	
	private TextView tv;
	private boolean cancelable = true;
	

	public LoadingTopDialog(Context context) {
		super(context, R.style.Dialog_bocop);
		init();
	}

	private void init() {
		View contentView = View.inflate(getContext(), R.layout.layout_loding_dialog, null);
		setContentView(contentView);
		
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelable) {
					dismiss();
				}
			}
		});
		tv = (TextView) findViewById(R.id.tv);
		getWindow().setWindowAnimations(R.anim.loading_dialog_alpha_in);
	}

	@Override
	public void show() {
		super.show();
	}
	
	@SuppressLint("NewApi")
	@Override
	public void dismiss() {
		super.dismiss();
	}
	
	
	@Override
	public void setCancelable(boolean flag) {
		cancelable = flag;
		super.setCancelable(flag);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		tv.setText(title);
	}
	
	@Override
	public void setTitle(int titleId) {
		setTitle(getContext().getString(titleId));
	}
}
