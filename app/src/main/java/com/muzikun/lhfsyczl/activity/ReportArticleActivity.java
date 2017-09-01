package com.muzikun.lhfsyczl.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：举报帖子
 */

public class ReportArticleActivity extends BaseActivity {
    @BindView(R.id.etTitle)
    AppCompatEditText etTitle;
    @BindView(R.id.etContent)
    AppCompatEditText etContent;
    private String id;

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null) {
            id = intent.getExtras().getString("id");
            if (TextUtils.isEmpty(id)) {
                ToastUtils.show("帖子信息丢失");
                finish();
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_report_article;
    }

    @Override
    protected void initView() {
        setTitle("举报");
        setTopRightButton("提交", new OnMenuClickListener() {
            @Override
            public void onClick() {
                report();
            }
        });
    }

    /**
     * 帖子举报
     */
    private void report() {
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
        if (TextUtils.isEmpty(title)) {
            ToastUtils.show("请填写标题");
            return;
        }
         if (TextUtils.isEmpty(msg)) {
            ToastUtils.show("请填写举报内容");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("title", title);
        map.put("content", msg);
        map.put("id", id);

        HttpService httpService = HttpRequest.provideClientApi();
        httpService.report(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<Object>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<Object>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<Object> response) {

                        if (response.getFlag() == 1) {
                            ToastUtils.show(response.getErrmsg() + "");
                            finish();
                        }

                    }
                });
    }


    @Override
    protected void initData() {

    }


}
