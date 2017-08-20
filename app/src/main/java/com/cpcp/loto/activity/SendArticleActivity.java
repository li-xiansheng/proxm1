package com.cpcp.loto.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import com.cpcp.loto.MApplication;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.ForumEntity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.fragment.ForumFragment;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

/**
 * 功能描述：发表文章
 */

public class SendArticleActivity extends BaseActivity {


    @BindView(R.id.etTitle)
    AppCompatEditText etTitle;
    @BindView(R.id.etContent)
    AppCompatEditText etContent;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_send_article;
    }

    @Override
    protected void initView() {
        setTitle("发布帖子");
        setTopRightButton("发布", new OnMenuClickListener() {
            @Override
            public void onClick() {
                sendArticle();
            }
        });
    }

    /**
     * 发布论坛帖子
     */
    private void sendArticle() {
        String title = etTitle.getText().toString();
        String msg = etContent.getText().toString();

        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin = sp.getBoolean(UserDB.isLogin, false);
        if (!isLogin) {
            jumpToActivity(LoginActivity.class, false);
            return;
        }
        String username = sp.getString(UserDB.TEL, "");
        if (TextUtils.isEmpty(username)) {
            jumpToActivity(LoginActivity.class, false);
            ToastUtils.show("登录过期，请重新登录");
            return;
        }
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(msg)) {
            ToastUtils.show("请填写发布内容");
             return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("full_name", username + "");
        map.put("title", title + "");
        map.put("msg", msg + "");


        HttpService httpService = HttpRequest.provideClientApi();
        httpService.releaseInfo(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<Object>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<Object>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<Object> response) {
                        if (response.getFlag() == 1) {
                            ToastUtils.show("发表成功,请下拉刷新");
                            setResult(ForumFragment.REQUEST_CODE);
                            finish();
                        } else {
                            ToastUtils.show(response.getErrmsg() + "");
                        }
                    }
                });

    }

    @Override
    protected void initData() {

    }


}
