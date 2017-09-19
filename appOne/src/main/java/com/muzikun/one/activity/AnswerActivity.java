package com.muzikun.one.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muzikun.one.R;

import com.muzikun.one.data.bean.SendNewsBean;
import com.muzikun.one.data.config.A;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Helper;
import com.muzikun.one.util.EventUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

public class AnswerActivity extends AppCompatActivity {

    private LinearLayout activityLoginBack;
    private ImageView answerHead;
    private TextView answerName;
    private TextView answerTime;
    private TextView answerTitle;
    private EditText answerContent;
    private TextView answerAct;
    private TextView title;
    private LinearLayout answerInfoLl;
    private TextView answerTip;

    private Auth auth = null;
    private Map<String, String> userInfo = null;
    private String mid;
    private String access_token;
    private String id;
    String uiTitle;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        Helper.setColor(this, Color.parseColor("#891C21"));

        auth = Auth.getInstance();
        userInfo = auth.login(this);
        mid = userInfo.get(UserModel.USER_ID).toString();
        access_token = userInfo.get(UserModel.USER_PASS).toString();

        id = getIntent().getStringExtra("id");
        uiTitle = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", 0);

        initView();
        initEvent();


    }

    private void initView() {

        activityLoginBack = (LinearLayout) findViewById(R.id.activity_login_back);

        answerHead = (ImageView) findViewById(R.id.answer_head);

        answerName = (TextView) findViewById(R.id.answer_name);
        answerTime = (TextView) findViewById(R.id.answer_time);
        answerTitle = (TextView) findViewById(R.id.answer_title);
        answerContent = (EditText) findViewById(R.id.answer_content);
        answerAct = (TextView) findViewById(R.id.answer_act);
        title = (TextView) findViewById(R.id.title);
        answerInfoLl = (LinearLayout) findViewById(R.id.answer_info_ll);
        answerTip = (TextView) findViewById(R.id.answer_tip);


        if (type == 5) {
            answerInfoLl.setVisibility(View.GONE);
            answerTitle.setVisibility(View.GONE);
            answerTip.setVisibility(View.VISIBLE);
            answerAct.setText("发布");
            title.setText("发布咨询");
        } else {
            answerInfoLl.setVisibility(View.VISIBLE);
            answerTitle.setVisibility(View.VISIBLE);
            answerTip.setVisibility(View.GONE);
            answerAct.setText("回答");
            title.setText("回答" + uiTitle);
            getQuestionsList();
        }
    }

    private void initEvent() {

        answerAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(answerContent.getText().toString())) {
                    if (type == 5) {
                        sendQuestion();
                    } else {
                        sendAnswer();
                    }
                } else {
                    if (type == 5) {
                        Helper.T(AnswerActivity.this, "提问内容不能为空");

                    } else {
                        Helper.T(AnswerActivity.this, "回复内容不能为空");

                    }
                }
            }
        });
    }

    private void getQuestionsList() {

        OkHttpUtils
                .get()
                .url(A.getAnswerInfo(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        call.cancel();

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        list.clear();
                        try {
                            SendNewsBean bean = new SendNewsBean();
                            JSONObject object = new JSONObject(response);
                            bean.id = object.getString("id");
                            bean.title = object.getString("title");
                            bean.name = object.getJSONObject("user").getString("uname");
                            bean.time = EventUtil.getFormatTime(Long.parseLong(object.getString("pubdate")));
                            bean.headPath = object.getJSONObject("user").getString("face");

                            refreshView(bean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void refreshView(SendNewsBean bean) {
        if (bean.headPath.length() > 10) {
            bean.headPath=bean.headPath.replace("www.gzbjwb.cn","wanbao.7va.cn");
            Glide.with(this)
                    .load(bean.headPath)
                    .placeholder(R.drawable.mine_head)
                    .into(answerHead);
        } else {
            answerHead.setImageResource(R.drawable.mine_head);
        }

        answerName.setText(bean.name);
        answerTime.setText(bean.time);
        answerTitle.setText(bean.title);
    }

    private void sendAnswer() {

//        aid:文章ID
//
//        mid:用户ID
//
//        content:评论内容
//
//        access_token:用户认证参数 （通过登录获取）

        OkHttpUtils
                .post()
                .url(A.SEND_COMMENT)
                .addParams("aid", id)
                .addParams("mid", mid)
                .addParams("content", answerContent.getText().toString())
                .addParams("access_token", access_token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        list.clear();
                        try {
                            JSONObject result = new JSONObject(response);
                            if (result.getInt("code") == 1) {
                                Helper.T(AnswerActivity.this, "回复成功！");
//                                answerContent.setText("");
                                finish();
                            } else {
                                Helper.T(AnswerActivity.this, "回复失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void sendQuestion() {

//        mid:用户ID
//        title:标题
//        content:内容
//        access_token：密码

        OkHttpUtils
                .post()
                .url(A.SEND_QUESTION)
                .addParams("mid", mid)
                .addParams("title", answerContent.getText().toString())
                .addParams("content", answerContent.getText().toString())
                .addParams("access_token", access_token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        list.clear();
                        try {
                            JSONObject result = new JSONObject(response);
                            if (result.getInt("code") == 1) {
                                Helper.T(AnswerActivity.this, "发布成功！");
//                                answerContent.setText("");
                                finish();
                            } else {
                                Helper.T(AnswerActivity.this, "发布失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
