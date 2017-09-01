package com.muzikun.lhfsyczl.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.uihelper.LoadingDialog;
import com.muzikun.lhfsyczl.util.NetworkUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：项目展示App
 */

public class WebPageActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tvHome)
    AppCompatTextView tvHome;
    @BindView(R.id.tvBack)
    AppCompatTextView tvBack;
    @BindView(R.id.tvFront)
    AppCompatTextView tvFront;
    @BindView(R.id.tvRefresh)
    AppCompatTextView tvRefresh;
    private String titleName = "";
    private String url;

    /**
     * 记录退出按键的最后时刻，初始为0
     */
    private long lastTime = 0;

    @Override
    protected void initBase(Bundle savedInstanceState) {
        isShowToolBar = false;
        super.initBase(savedInstanceState);
    }

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
        return R.layout.activity_web_page;
    }

    @Override
    protected void initView() {
        setTitle(titleName);

        //判断网络未打开则提示，不进行加载
        if (!NetworkUtil.isConnected(mContext)) {
            ToastUtils.show("请检查网络连接……");
            return;
        }


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //添加js时间监听，交由web浏览器调用
        webView.removeJavascriptInterface("searchBoxJavaBredge_");//移除js调用漏洞
        webView.addJavascriptInterface(new Contact(), "webLink");


//        // wifi环境下不使用缓存：
//        if (NetworkUtil.isWifiAvailable(this)) {
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        } else {
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }


        webView.loadUrl(url);
        webView.setWebViewClient(new UserAgreementWebViewClient());
        /**
         * 下载监听，
         */
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url.endsWith(".apk")) {
                    openBrowser(url);
                } else {
                    /**
                     * 方法二：通过系统下载apk
                     */
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tvHome, R.id.tvBack, R.id.tvFront, R.id.tvRefresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvHome://首页
                while (webView.canGoBack()) {
                    webView.goBack();
                }

                break;
            case R.id.tvBack://后退
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    ToastUtils.show("已到首页");
                }

                break;
            case R.id.tvFront://前进
                if (webView.canGoForward()) {
                    webView.goForward();
                } else {
                    ToastUtils.show("没有更多访问历史");
                }

                break;
            case R.id.tvRefresh://刷新
                webView.reload();
                break;
        }
    }

    /**
     * 使用webViewClient
     *
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
            if (webView.canGoBack()) {
                tvBack.setAlpha(1f);
            } else {
                tvBack.setAlpha(0.5f);
            }
            if (webView.canGoForward()) {
                tvFront.setAlpha(1f);
            } else {
                tvFront.setAlpha(0.5f);
            }
            super.onPageFinished(view, url);
        }
    }

    /**
     * 设置回退
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //处理回退键，
            long curTime = System.currentTimeMillis();
            if (curTime - lastTime >= 2000) {
                lastTime = curTime;
                Toast.makeText(this, "在按一次退出", Toast.LENGTH_SHORT).show();
                return true;
            }
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 监听调用js处理方法
     */
    private final class Contact {
        @JavascriptInterface
        public void toBrowser(String url) {

            openBrowser(url);
        }
    }

    /**
     * 打开外部浏览器
     *
     * @param url
     */
    private void openBrowser(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
            ToastUtils.show("请选择其他浏览器进行下载……");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url + "");
            intent.setData(content_url);
            startActivity(intent);
        } else {
            ToastUtils.show("无法访问，无效链接……");
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
