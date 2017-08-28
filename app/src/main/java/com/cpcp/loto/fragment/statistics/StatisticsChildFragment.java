package com.cpcp.loto.fragment.statistics;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.cpcp.loto.uihelper.PopupWindowHelper;
import com.cpcp.loto.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

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
        settings.setUseWideViewPort(false);
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
        webView.setWebViewClient(new StatisticsChildFragment.UserAgreementWebViewClient());

        /**
         * 初始设为是否隐藏，false，不隐藏
         */
        onHiddenChanged(false);
    }

    @Override
    public void onLazyLoadData() {

    }

    //切换Fragment时调用
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.i(TAG, titleName + "显示为" + hidden);
        if (!hidden) {//已经有了Fragment后才会调用，当前fragment正在显示

            String menuStr = ((BaseActivity) mActivity).menuStr;
            String type = "";
            if (url.contains("type=")) {
                int indexLast = url.lastIndexOf("=");
                type = url.substring(indexLast + 1);

//                url = url.substring(0, indexLast) + "" + type;
            }
            //如果，期数或者年份和menu显示相同，则不进行刷新menu，否则刷新menu
            if (TextUtils.isEmpty(type) || menuStr.contains(type)) {
                ((BaseActivity) mActivity).invalidateOptionsMenu();
                return;
            }

            if ("属性参照".equals(titleName)) {
                ((BaseActivity) mActivity).menuStr = " ";

            } else if ("尾数大小".equals(titleName) || "家禽野兽".equals(titleName) ||
                    "连码走势".equals(titleName) || "连肖走势".equals(titleName)) {//选择年份
                ((BaseActivity) mActivity).menuStr = "年份:" + type;


            } else {//选择期数
                ((BaseActivity) mActivity).menuStr = "期数:" + type;

            }
            ((BaseActivity) mActivity).invalidateOptionsMenu();

        }


    }

    /**
     * 设置最新URL
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置最新URL
     *
     * @param url
     */
    public void refreshWebUrl(String url) {
        this.url = url;//设置url后，刷新页面
        webView.loadUrl(url);
        onHiddenChanged(false);
        LogUtils.i(TAG, "菜单选择筛选" + url);

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
