package com.muzikun.lhfsyczl.activity;

import android.app.Activity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.fragment.ForumFragment;
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
