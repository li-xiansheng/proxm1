package com.cpcp.loto.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.ToastUtils;

import butterknife.BindView;

/**
 * 功能描述：通用的web页
 */

public class WebCommonPageActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String titleName = "";
    private String url;

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            titleName = bundle.getString("name");
            url = bundle.getString("url");
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_common_web_page;
    }

    @Override
    protected void initView() {
        setTitle(titleName);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(false);
        //添加js时间监听，交由web浏览器调用
        webView.removeJavascriptInterface("searchBoxJavaBredge_");//移除js调用漏洞


//        // wifi环境下不使用缓存：
//        if (NetworkUtil.isWifiAvailable(this)) {
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        } else {
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }


        webView.loadUrl(url);
        webView.setWebViewClient(new UserAgreementWebViewClient());
    }

    @Override
    protected void initData() {

    }

    /**
     * 使用webViewClient
     *
     * @author lichuanbei
     * @date 2016-7-11 下午4:32:04
     */
    class UserAgreementWebViewClient extends WebViewClient {

        public boolean shouldOverviewUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LoadingDialog.showDialog(mActivity);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            LoadingDialog.closeDialog(mActivity);
            super.onPageFinished(view, url);
        }


    }

    /**
     * 规避5.1系统问题
     */
    @Override
    protected void onDestroy() {
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
