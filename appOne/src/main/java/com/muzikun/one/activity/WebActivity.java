package com.muzikun.one.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.muzikun.one.R;
import com.muzikun.one.fragment.common.LoadingFragment;
import com.muzikun.one.lib.util.Helper;

/**
 * Created by likun on 16/7/16.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class WebActivity extends FragmentActivity implements View.OnClickListener{
    public static final String INTENT_PARAM_URL = "url";

    private String url              = null;
    private WebView webView         = null;
    private LinearLayout backButton = null;
    private LoadingFragment loadingFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_web);
        Helper.setColor(this, Color.parseColor("#891C21"));
        loadingFragment = new LoadingFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        loadingFragment.show(fragmentManager,"string");
        initView();

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.activity_web_webview);
        backButton = (LinearLayout) findViewById(R.id.activity_web_back);

        webView.setWebViewClient(new WebActivityViewClient());

        Intent intent = getIntent();
        url = intent.getStringExtra(WebActivity.INTENT_PARAM_URL);
        if(url != null){
            backButton.setOnClickListener(this);
            webView.loadUrl(url);
        }else{
            Helper.T(this,"无效链接，请检查连接地址是否有误");
            finish();
        }

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.activity_web_back) {
            finish();

        } else {
            return;
        }
    }

    /**
     * WebView控件的客户端程序
     */
    public class WebActivityViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingFragment.dismiss();
        }
    }
    
    public static void startHelper(Context context,String url) {
        Intent starter = new Intent(context, WebActivity.class);
        starter.putExtra(INTENT_PARAM_URL,url);
        context.startActivity(starter);
    }

}
