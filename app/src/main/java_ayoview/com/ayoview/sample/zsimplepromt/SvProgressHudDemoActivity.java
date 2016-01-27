package com.ayoview.sample.zsimplepromt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by Administrator on 2016/1/25.
 */
public class SvProgressHudDemoActivity extends BaseActivity{
    int progress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sv_prgress_main);

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                show(view);
            }
        });

        findViewById(R.id.btn_showWithMaskType).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showWithMaskType(view);
            }
        });

        findViewById(R.id.btn_showWithStatus).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showWithStatus(view);
            }
        });

        findViewById(R.id.btn_showInfoWithStatus).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showInfoWithStatus(view);
            }
        });

        findViewById(R.id.btn_showSuccessWithStatus).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showSuccessWithStatus(view);
            }
        });

        findViewById(R.id.btn_showErrorWithStatus).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showErrorWithStatus(view);
            }
        });

        findViewById(R.id.btn_showWithProgress).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showWithProgress(view);
            }
        });

    }

    public void show(View view){
        SVProgressHUD.show(getActivity());
    }
    public void showWithMaskType(View view){
        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.None);
//        SVProgressHUD.showWithMaskType(getActivity(),SVProgressHUD.SVProgressHUDMaskType.Black);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.Clear);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.Gradient);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }
    public void showWithStatus(View view){
        SVProgressHUD.showWithStatus(getActivity(), "加载中...");
    }
    public void showInfoWithStatus(View view){
        SVProgressHUD.showInfoWithStatus(getActivity(), "这是提示", SVProgressHUD.SVProgressHUDMaskType.None);
    }
    public void showSuccessWithStatus(View view){
        SVProgressHUD.showSuccessWithStatus(getActivity(), "恭喜，提交成功！");
    }
    public void showErrorWithStatus(View view){
        SVProgressHUD.showErrorWithStatus(getActivity(), "不约，叔叔我们不约～", SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress = progress + 5;
            if (SVProgressHUD.getProgressBar(getActivity()).getMax() != SVProgressHUD.getProgressBar(getActivity()).getProgress()) {
                SVProgressHUD.getProgressBar(getActivity()).setProgress(progress);
                SVProgressHUD.setText(getActivity(), "进度 "+progress+"%");

                mHandler.sendEmptyMessageDelayed(0,500);
            }
            else{
                SVProgressHUD.dismiss(getActivity());
                SVProgressHUD.getProgressBar(getActivity()).setProgress(0);
            }

        }
    };
    public void showWithProgress(View view){
        SVProgressHUD.showWithProgress(getActivity(), "进度 "+progress+"%", SVProgressHUD.SVProgressHUDMaskType.Black);
        progress = 0;
        mHandler.sendEmptyMessageDelayed(0,500);
    }

    @Override
    public Boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(SVProgressHUD.isShowing(getActivity())){
                SVProgressHUD.dismiss(getActivity());
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);

    }
}
