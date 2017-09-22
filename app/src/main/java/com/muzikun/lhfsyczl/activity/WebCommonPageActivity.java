package com.muzikun.lhfsyczl.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.uihelper.LoadingDialog;
import com.muzikun.lhfsyczl.uihelper.PopupWindowHelper;
import com.muzikun.lhfsyczl.util.NetworkUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;

import butterknife.BindView;

/**
 * 功能描述：通用的web页
 */

public class WebCommonPageActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String titleName = "";
    private String url;

    private AppCompatTextView tvTemp;


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


        //判断网络未打开则提示，不进行加载
        if (!NetworkUtil.isConnected(mContext)) {
            ToastUtils.show("请检查网络连接……");
            return;
        }

        if (!TextUtils.isEmpty(titleName) && "历史开奖".equals(titleName)) {

            setTopRightButton("选择年份", new OnMenuClickListener() {
                @Override
                public void onClick() {
                    PopupWindowHelper.selectYear(mActivity, tvTemp);
                }
            });
        } else if (!TextUtils.isEmpty(titleName) && ("六合助手".equals(titleName) || "查询助手".equals(titleName))) {

            setTopRightButton("搜索", R.drawable.icon_white_search, new OnMenuClickListener() {
                @Override
                public void onClick() {

                    webView.loadUrl("javascript:search()");
                }
            });
        }


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
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
    protected void initListener() {
        super.initListener();

        if (!TextUtils.isEmpty(titleName) && "历史开奖".equals(titleName)) {
            tvTemp = new AppCompatTextView(mContext);
            tvTemp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String values = tvTemp.getText().toString();

                    menuStr = values + "年";
                    invalidateOptionsMenu();
                    if (!TextUtils.isEmpty(url)) {
                        if (url.endsWith("kaijianglishi")) {//根据UIL最后是否有参数确定如何替换参数
                            url = url + "&year=" + values;
                        } else {
                            int indexLast = url.lastIndexOf("&");
                            url = url.substring(0, indexLast) + "&year=" + values;
                        }
                        webView.loadUrl(url);
                    }


                }
            });
        }
    }

    @Override
    protected void initData() {

    }


    // 设置回退
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
