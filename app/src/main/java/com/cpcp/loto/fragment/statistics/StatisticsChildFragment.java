package com.cpcp.loto.fragment.statistics;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.InformationStatisticsActivity;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.uihelper.LoadingDialog;

import butterknife.BindView;

/**
 * 功能描述：资讯统计（六合统计）
 */

public class StatisticsChildFragment extends BaseFragment {
    @BindView(R.id.webView)
    WebView webView;
    private String titleName = "";
    private String url;

    /**
     * 构造Fragment
     *
     * @param name
     * @param url
     * @return 当前Fragment对象
     */
    public static StatisticsChildFragment newInstance(String name, String url) {
        StatisticsChildFragment fragment = new StatisticsChildFragment();
        if (name != null && url != null) {
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("name", name);
            fragment.setArguments(bundle);
        }

        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_statistics_child;
    }

    @Override
    protected void initView() {
        // mArgument = getActivity().getIntent().getStringExtra(ARGUMENT);
        Bundle bundle = getArguments();
        if (bundle != null) {
            titleName = bundle.getString("name");
            url = bundle.getString("url");
        }


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
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
        webView.setWebViewClient(new StatisticsChildFragment.UserAgreementWebViewClient());

    }

    @Override
    public void onLazyLoadData() {

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
    public void onDestroy() {
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
