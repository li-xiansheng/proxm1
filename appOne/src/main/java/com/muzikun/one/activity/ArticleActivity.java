package com.muzikun.one.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.adapter.activity.ArticleActivityCommentListViewAdapter;
import com.muzikun.one.data.bean.ArticleCommentListViewBean;
import com.muzikun.one.data.config.A;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.fragment.common.LoadingFragment;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Helper;
import com.muzikun.one.lib.util.Net;
import com.muzikun.one.lib.view.ScrollViewExpand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by leeking on 16/6/7.
 */
public class ArticleActivity extends FragmentActivity implements View.OnClickListener {
    private String TAG = "ArticleActivity";
    private WebView webView = null;
    private WebSettings webSettings = null;
    private String apiUrl = "";
    private int articleId = 0;
    private int windowType = 0;
    private LinearLayout backButton = null;
    private LinearLayout mainBox = null;
    private LinearLayout commentView = null;
    private LinearLayout commentBackground = null;
    private LinearLayout topBar = null;
    private CookieManager cookieManager = null;
    private ImageView stowArticle = null;
    //    private ImageView shareBox                                              = null;
    private String articleTitle = "文章";
    private TextView tvTitle=null;
    private ImageView commentIcon = null;
    private ScrollViewExpand scrollView = null;
    private TextView clickEnter = null;
    private TextView commentCancel = null;
    private RelativeLayout commentEditBox = null;
    private RelativeLayout commentEnterBox = null;
    private EditText commentEdit = null;
    private Auth auth = null;
    private LinearLayout commentListViewBox = null;
    private ListView commentListView = null;
    private TextView noDataView = null;
    private List<ArticleCommentListViewBean> articleListListViewBeanList = null;
    private ArticleActivityCommentListViewAdapter commentListAdapter = null;
    private Map<String, String> userInfo = null;
    private ProgressBar progressBar = null;
    private TextView sendCommentView = null;
    private Dialog commmentAlert = null;
    private Dialog shareAlert = null;
    private LoadingFragment loadingFragment = null;
    private TextView footerTextView = null;
    private List<ArticleCommentListViewBean> jsonList = null;
    private int commentPage = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_article);

        loadingFragment = new LoadingFragment();
        loadingFragment.show(getSupportFragmentManager(), "loading");
        initView();
    }

    @SuppressWarnings("unchecked")
    private void initView() {
        tvTitle= (TextView) findViewById(R.id.tvTitle);
        webView = (WebView) findViewById(R.id.activity_article_webview);
        backButton = (LinearLayout) findViewById(R.id.activity_article_back);
        topBar = (LinearLayout) findViewById(R.id.activity_article_topbar);
        mainBox = (LinearLayout) findViewById(R.id.activity_article_mainbox);
        stowArticle = (ImageView) findViewById(R.id.activity_article_stow);
        commentView = (LinearLayout) findViewById(R.id.activity_article_comment);
        commentBackground = (LinearLayout) findViewById(R.id.activity_article_shadow_bg);
        commentListViewBox = (LinearLayout) findViewById(R.id.activity_article_comment_listview_box);
        commentIcon = (ImageView) findViewById(R.id.activity_article_comment_icon);
        scrollView = (ScrollViewExpand) findViewById(R.id.activity_article_scrollview);
        clickEnter = (TextView) findViewById(R.id.activity_article_click_enter);
        commentCancel = (TextView) findViewById(R.id.activity_article_comment_cancel);
        commentEditBox = (RelativeLayout) findViewById(R.id.activity_article_comment_edit_box);
        commentEnterBox = (RelativeLayout) findViewById(R.id.activity_article_comment_box);
        commentEdit = (EditText) findViewById(R.id.activity_article_edit);
        noDataView = (TextView) findViewById(R.id.activity_article_nodata_message);
        commentListView = (ListView) findViewById(R.id.activity_article_comment_listview);
        progressBar = (ProgressBar) findViewById(R.id.myProgressBar);
        sendCommentView = (TextView) findViewById(R.id.activity_article_send_comment);
//        shareBox            = (ImageView)       findViewById(R.id.activity_article_share);

        setCommentEditListener();

        cookieManager = CookieManager.getInstance();
        webSettings = webView.getSettings();

        try {

            articleId = getIntent().getIntExtra("articleId", 0);
            articleTitle = getIntent().getStringExtra("articleTitle");
            windowType = getIntent().getIntExtra("windowType", 0);
        } catch (Exception e) {

            articleId = 0;
            windowType = 0;
        }
        //
        articleTitle=TextUtils.isEmpty(articleTitle)?"文章":articleTitle;
        tvTitle.setText(articleTitle);

        if (windowType == 1) {
            Helper.setColor(this, Color.parseColor("#000000"));
            webView.setBackgroundColor(Color.parseColor("#000000"));
            topBar.setBackgroundColor(Color.parseColor("#000000"));
            mainBox.setBackgroundColor(Color.parseColor("#000000"));
            progressBar.setVisibility(View.GONE);
            commentView.setVisibility(View.GONE);
            commentEnterBox.setVisibility(View.GONE);
        } else {
            Helper.setColor(this, Color.parseColor("#891C21"));
        }

//        apiUrl              = A.getArticleItem(articleId) + "/type/" + String.valueOf(windowType);
        apiUrl = A.getArticleItem(articleId);

        backButton.setOnClickListener(this);
        commentCancel.setOnClickListener(this);
        commentIcon.setOnClickListener(this);
        clickEnter.setOnClickListener(this);
        commentBackground.setOnClickListener(this);
        sendCommentView.setOnClickListener(this);
//        shareBox                .setOnClickListener(this);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebActivityWebViewClient());
        webView.setWebChromeClient(new WebActivityChromeClient());

        webView.loadUrl(apiUrl);
        if (getUserInfo() != null) {
            new HaveStow().execute(getUserInfo());
        } else {
            stowArticle.setOnClickListener(ArticleActivity.this);
        }
    }

    private void setCommentEditListener() {
        commentEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
    }

    @Override
    public void onClick(View view) {
//        Tencent tencent;
//        Bundle params;
        int i = view.getId();
        if (i == R.id.activity_article_back) {
            finish();

        } else if (i == R.id.activity_article_stow) {
            stow();

        } else if (i == R.id.activity_article_comment_icon) {
            scrollView.setScrollY((int) commentView.getY());

        } else if (i == R.id.activity_article_click_enter) {
            showEditBox();

//            case R.id.activity_article_share:
//                createShareBox();
//                break;
        } else if (i == R.id.activity_article_comment_cancel) {
            hideEditBox();

        } else if (i == R.id.fragment_send_comment) {
            sendComment();

        } else if (i == R.id.activity_article_send_comment) {
            sendComment();

//            case R.id.dialog_share_box_to_qq:
//                Helper.T(this,"正在打开QQ分享...");
//                tencent = Tencent.createInstance(Config.QQ_APP_ID,this);
//                params = new Bundle();
//                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                params.putString(QQShare.SHARE_TO_QQ_TITLE, articleTitle);
//                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, apiUrl);
//                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://www.gzbjwb.cn/icon.png");
//                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "毕节晚报");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
//                tencent.shareToQQ(this, params,null);
//                break;
//            case R.id.dialog_share_box_to_qzone:
//
//                Helper.T(this,"正在打开QQ空间分享...");
//                 tencent = Tencent.createInstance(Config.QQ_APP_ID,this);
//                 params = new Bundle();
//                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
//                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, articleTitle);//必填
//                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, apiUrl);//必填
//                ArrayList<String> arrayList = new ArrayList<String>();
//                arrayList.add("http://www.gzbjwb.cn/icon.png");
//                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,arrayList );
//                tencent.shareToQzone(this, params,null);
//                break;
        } else {
            return;
        }
    }

    private void sendComment() {
        if (getUserInfo() == null) {
            Helper.T(this, "请先登录后再发表");
            return;
        }
        if (commentEdit.getText().toString() == null || commentEdit.getText().toString().length() < 1) {
            Helper.T(this, "请填写评论内容");
            return;
        }
        new SendComment().execute(
                getUserInfo().get(UserModel.USER_ID).toString(),
                getUserInfo().get(UserModel.USER_PASS).toString(),
                commentEdit.getText().toString()
        );

    }

    @SuppressWarnings("unchecked")
    private void stow() {
        Map<String, String> user = getUserInfo();
        if (user == null) {
            getAuth().startLogin(this, new OnAddStowListener());
            return;
        }
        new AddStow().execute(user);
    }


    public void showEditBox() {
        auth = getAuth();
        Map<String, String> userInfo = auth.login(this);
        if (userInfo == null) {
            auth.startLogin(this, new OnCommentAuthChangeListener());
            return;
        }
        createEditBox();
    }

//    public void createShareBox() {
//        shareAlert = new Dialog(this, R.style.Dialog_FS);
//        shareAlert.setCanceledOnTouchOutside(true);
//        shareAlert.setContentView(R.layout.dialog_share_box);
//        WindowManager.LayoutParams layoutParams = shareAlert.getWindow().getAttributes();
//        shareAlert.getWindow().findViewById(R.id.dialog_share_box_to_qq).setOnClickListener(this);
//        shareAlert.getWindow().findViewById(R.id.dialog_share_box_to_qzone).setOnClickListener(this);
//
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        shareAlert.getWindow().setGravity(Gravity.BOTTOM);
//        shareAlert.getWindow().setAttributes(layoutParams);
//        shareAlert.show();
//    }

    public void createEditBox() {
        commmentAlert = new Dialog(this, R.style.Dialog_FS);
        Window window = commmentAlert.getWindow();
        commmentAlert.setCanceledOnTouchOutside(true);
        window.setContentView(R.layout.fragment_comment);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.BOTTOM);
        commmentAlert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        commmentAlert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        commentEdit = (EditText) window.findViewById(R.id.fragment_comment_edit);
        sendCommentView = (TextView) window.findViewById(R.id.fragment_send_comment);
        sendCommentView.setOnClickListener(this);
        window.setAttributes(lp);
        commmentAlert.show();
    }

    public void hideEditBox() {

    }

    public class WebActivityWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            articleTitle = view.getTitle();
            String CookieStr = cookieManager.getCookie(url);
            if (windowType != 1) {
                commentView.setVisibility(View.VISIBLE);
            }
            if (windowType == 1) {

            }
            setCommentListView();
            loadingFragment.dismiss();
            super.onPageFinished(view, url);
        }
    }

    public class WebActivityChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress >= 100) {
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    public void setCommentListView() {
        Log.i(TAG, "articleId = " + articleId);
//        http://wanbao.7va.cn/qiokn/api.php/Article/comment?aid=1663&p=0
        String api = A.getArticleComment(articleId, 0);
        new GetArticleCommentData().execute(api);

    }

    private class GetArticleCommentData extends AsyncTask<String, String, String> {
        public final static String SET = "SET";
        public final static String ADD = "ADD";
        private String commentError = "未知错误!";
        private String type = SET;

        public GetArticleCommentData(String type) {
            this.type = type;
        }

        public GetArticleCommentData() {

        }

        @Override
        protected String doInBackground(String... strings) {
            return Net.getApiJson(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            List<ArticleCommentListViewBean> mjsonList;

            mjsonList = jsonToData(s);
            if (type == SET) {
                if (mjsonList != null) {
                    jsonList = mjsonList;
                    commentListAdapter = new ArticleActivityCommentListViewAdapter(ArticleActivity.this, jsonList);
                    // jsonToData(s);
                    commentListView.setVisibility(View.VISIBLE);
                    noDataView.setVisibility(View.GONE);
                    commentListView.setAdapter(commentListAdapter);
                    footerTextView = new TextView(ArticleActivity.this);
                    AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    footerTextView.setLayoutParams(layoutParams);
                    footerTextView.setPadding(20, 50, 20, 50);
                    footerTextView.setGravity(Gravity.CENTER);
                    footerTextView.setBackgroundColor(Color.parseColor("#fefefe"));
                    footerTextView.setTextColor(Color.parseColor("#999999"));
                    footerTextView.setText("查看更多");

                    footerTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            footerTextView.setText("正在加载...");
                            String api = A.getArticleComment(articleId, commentPage);
                            new GetArticleCommentData(GetArticleCommentData.ADD).execute(api);
                        }
                    });

                    commentListView.addFooterView(footerTextView);
                    scrollView.setOnScrollExpandChangeListener(new MyOnScrollChange());
                } else {
                    noDataView.setVisibility(View.VISIBLE);
                }
            } else if (type == ADD) {
                if (mjsonList != null) {
                    jsonList.addAll(mjsonList);
                    commentListAdapter.notifyDataSetChanged();
                    footerTextView.setText("查看更多");
                    commentPage++;
                } else {
                    if (footerTextView != null) {
                        footerTextView.setText("没有了...");
                        footerTextView.setOnClickListener(null);
                    }
                }
            }
        }

        public List<ArticleCommentListViewBean> jsonToData(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                int total = jsonObject.getInt("total");
                if (total <= 0) {
                    this.commentError = "没有消息拉";
                    return null;
                }

                JSONArray listArray = jsonObject.getJSONArray("data");
                articleListListViewBeanList = new ArrayList<>();
                for (int i = 0; i < listArray.length(); i++) {
                    JSONObject listObject = listArray.getJSONObject(i);
                    articleListListViewBeanList.add(
                            new ArticleCommentListViewBean(
                                    listObject.getInt("id"),
                                    listObject.getJSONObject("userinfo").getString("uname"),
                                    listObject.getJSONObject("userinfo").getString("face"),
                                    listObject.getString("dtime"),
                                    listObject.getInt("good"),
                                    listObject.getString("msg")
                            ));
                }
                return articleListListViewBeanList;
            } catch (JSONException e) {
                this.commentError = "数据格式错误，请稍后再试...";
                return null;
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        auth.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (commentEditBox.getVisibility() == View.VISIBLE) {
                hideEditBox();
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    class OnCommentAuthChangeListener implements Auth.OnAuthCallbackListener {

        @Override
        public void onSuccess(Intent data) {
            Helper.T(ArticleActivity.this, "Success");
        }

        @Override
        public void onError(Intent data) {

        }

        @Override
        public void onCancel(Intent data) {

        }
    }

    class OnAddStowListener implements Auth.OnAuthCallbackListener {

        @Override
        public void onSuccess(Intent data) {

        }

        @Override
        public void onError(Intent data) {

        }

        @Override
        public void onCancel(Intent data) {

        }
    }

    public Map<String, String> getUserInfo() {
        if (userInfo == null) {
            userInfo = getAuth().login(this);
        }
        return userInfo;
    }

    public Auth getAuth() {
        if (auth == null) {
            auth = Auth.getInstance();
        }
        return auth;
    }

    public class AddStow extends AsyncTask<Map<String, String>, String, String> {


        @Override
        protected String doInBackground(Map<String, String>... maps) {
            Map<String, String> user = maps[0];
            String params
                    = "mid=" + user.get(UserModel.USER_ID)
                    + "&aid=" + articleId
                    + "&title=" + articleTitle
                    + "&access_token=" + user.get(UserModel.USER_PASS).toString();
            String result;
            try {
                result = new String(Net.sendPostRequestByForm(A.ADD_STOW, params), "UTF-8");
            } catch (Exception e) {
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject result = new JSONObject(s);
                int code = result.getInt("code");
                switch (code) {
                    case -1:
                        Helper.T(ArticleActivity.this, "用户信息错误，请重新登录再试");
                        break;
                    case -3:
                        Helper.T(ArticleActivity.this, "认证失败，请重新登录再试");
                        break;
                    case -2:
                        Helper.T(ArticleActivity.this, "用户不存在，请登录后再试");
                        break;
                    case -5:
                        Helper.T(ArticleActivity.this, "取消收藏失败");
                        break;
                    case 0:
                        Helper.T(ArticleActivity.this, "收藏失败，请稍后再试");
                        break;
                    case 1:
                        Helper.T(ArticleActivity.this, "收藏成功");
                        stowArticle.setImageResource(R.drawable.star_stow_select);
                        break;
                    case 2:
                        Helper.T(ArticleActivity.this, "已取消收藏");
                        stowArticle.setImageResource(R.drawable.star_no);
                        break;
                    default:
                        Helper.T(ArticleActivity.this, "未知错误");
                }
            } catch (JSONException e) {

            }
        }
    }

    class HaveStow extends AsyncTask<Map<String, String>, String, String> {
        @Override
        protected String doInBackground(Map<String, String>... maps) {
            Map<String, String> user = maps[0];
            String params = "aid=" + articleId + "&mid=" + user.get(UserModel.USER_ID).toString();
            String result;
            try {
                result = new String(Net.sendPostRequestByForm(A.HAVE_STOW, params), "UTF-8");
            } catch (Exception e) {
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject result = new JSONObject(s);
                int code = result.getInt("code");
                if (stowArticle == null) {
                    stowArticle.setOnClickListener(ArticleActivity.this);
                    return;
                }
                if (code == 1) {
                    stowArticle.setImageResource(R.drawable.star_stow_select);
                } else {
                    stowArticle.setImageResource(R.drawable.star_no);
                }
            } catch (JSONException e) {
                stowArticle.setOnClickListener(ArticleActivity.this);
                return;
            }
            stowArticle.setOnClickListener(ArticleActivity.this);
        }
    }

    public class SendComment extends AsyncTask<String, String, String> {
        public String errorMessage = "";

        @Override
        protected String doInBackground(String... strings) {
            String api = A.SEND_COMMENT;
            String params = "mid=" + strings[0]
                    + "&aid=" + articleId
                    + "&access_token=" + strings[1]
                    + "&content=" + strings[2];
            String result;

            try {
                result = new String(Net.sendPostRequestByForm(api, params), "UTF-8");
            } catch (Exception e) {
                errorMessage = "网络连接失，请稍后再试";
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                Helper.T(ArticleActivity.this, errorMessage);
            } else {
                try {
                    JSONObject result = new JSONObject(s);
                    if (result.getInt("code") == 1) {
                        Helper.T(ArticleActivity.this, "发布成功！");
                    } else {
                        Helper.T(ArticleActivity.this, "发布失败");
                    }
                } catch (JSONException e) {

                }
                commentEdit.setText("");
                commmentAlert.dismiss();
                Log.i("SendComment", s);
            }
        }

    }


    /**
     * @param context
     * @param articleId
     * @param articleTitle
     */
    public static void startHelper(Context context, int articleId, String articleTitle, int windowType) {
        Intent starter = new Intent(context, ArticleActivity.class);
        starter.putExtra("articleId", articleId);
        starter.putExtra("articleTitle", articleTitle);
        starter.putExtra("windowType", windowType);
        context.startActivity(starter);
    }


    class MyOnScrollChange implements ScrollViewExpand.OnScrollExpandChangeListener {
        @Override
        public void onChange(int l, int t, int oldl, int oldt) {

        }
    }
}
