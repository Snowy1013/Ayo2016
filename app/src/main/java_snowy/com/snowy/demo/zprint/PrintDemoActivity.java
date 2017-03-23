package com.snowy.demo.zprint;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by zx on 17-3-23.
 */

public class PrintDemoActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_print_demo);
        findViewById(R.id.btn_print_photo).setOnClickListener(this);
        findViewById(R.id.btn_print_html).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_print_photo:
                doPhotoPrint();
                break;
            case R.id.btn_print_html:
                doHtmlPrint();
                break;
        }
    }

    private void doPhotoPrint(){
        PrintHelper photoPrint = new PrintHelper(getActivity());
        photoPrint.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowy_cute1);
        photoPrint.printBitmap("cute1.jpg - test print", bitmap);
    }

    private void doHtmlPrint(){
        WebView webView = new WebView(getActivity());
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("snowy", "page finish loading " + url);
                //请确保在WebViewClient)中的onPageFinished()方法内调用创建打印任务的方法。
                // 如果没有等到页面加载完毕就进行打印，打印的输出可能会不完整或空白，甚至可能会失败。
                createWebviewPrint(view);
                mWebView = null;
            }
        });

        if (isNetworkConnected(getActivity())) {
            // Print an existing web page (remember to request INTERNET permission!):
            webView.loadUrl("http://developer.android.com/about/index.html");
        } else {
            String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                    "testing, testing...</p></body></html>";
            webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
        }

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        //保留了一个WebView对象实例的引用，这样能够确保它不会在打印任务创建之前就被垃圾回收器所回收。
        // 在编写代码时请务必这样做，否则打印的进程可能会无法继续执行。
        mWebView = webView;
    }

    @TargetApi(19)
    private void createWebviewPrint(WebView webView){
        //Get a PrintManager Instance
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);

        //Get a Print adapter instance
        PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getActivity().getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printDocumentAdapter, new PrintAttributes.Builder().build());
    }

    /**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
