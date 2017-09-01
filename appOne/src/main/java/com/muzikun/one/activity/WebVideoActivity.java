package com.muzikun.one.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muzikun.one.R;

/**
 * 显示一些相似的网页内容
 * 创建时间：2015年12月3日 下午4:33:48
 */
public class WebVideoActivity extends Activity {
    private LinearLayout activityWebBack;
    private TextView activityWebTitle;
    private WebView info;

    @TargetApi(19)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        activityWebBack = (LinearLayout) findViewById(R.id.activity_web_back);
        activityWebTitle = (TextView) findViewById(R.id.activity_web_title);
        info = (WebView) findViewById(R.id.activity_web_webview);
        //获取相应信息
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        //设置网页信息
//        info = (WebView) findViewById(R2.id.info);
        info.getSettings().setJavaScriptEnabled(true);
//        info.getSettings().setPluginState();//设置webview支持插件
//        WebSettings.PluginState pluginState = new
        info.loadUrl(url);
        info.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

//        WebChromeClient wvcc = new WebChromeClient() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                Log.d("ANDROID_LAB", "TITLE=" + title);
//                activityWebTitle.setText(title);
//            }
//
//        };
//
//        // 设置setWebChromeClient对象
//        info.setWebChromeClient(wvcc);

        activityWebBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        info.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (keyCoder == KeyEvent.KEYCODE_BACK) {
            return back();
        } else {
            return false;
        }
    }

    private boolean back() {
//		if(info.canGoBack()){
//			info.goBack(); //goBack()表示返回webView的上一页面
//			return true;
//		}
        this.finish();
        return false;
    }
}
