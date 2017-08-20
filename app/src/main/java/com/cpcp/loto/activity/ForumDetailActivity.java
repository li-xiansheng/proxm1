package com.cpcp.loto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.cpcp.loto.MApplication;
import com.cpcp.loto.R;
import com.cpcp.loto.adapter.ForumDetailRecyclerViewAdapter;
import com.cpcp.loto.base.BasePullRefreshActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.ForumDetailEntity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.listener.KeyboardChangeListener;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.KeyBoardUtils;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.SelectedLayerImageView;
import com.cpcp.loto.view.SelectedLayerTextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：论坛详细
 */

public class ForumDetailActivity extends BasePullRefreshActivity {


    @BindView(R.id.tvCancel)
    SelectedLayerTextView tvCancel;
    @BindView(R.id.tvReplay)
    SelectedLayerTextView tvReplay;
    @BindView(R.id.etInput)
    AppCompatEditText etInput;
    @BindView(R.id.lilInput)
    LinearLayout lilInput;
    @BindView(R.id.ivSendMsg)
    SelectedLayerImageView ivSendMsg;


    private List<ForumDetailEntity.ReplyBean> mListData;
    private String title = "";
    private boolean isFirst = true;//是否第一次加载
    private ForumDetailRecyclerViewAdapter mBaseRecycleViewAdapter;
    //帖子id
    private String id;


    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null) {
            id = intent.getExtras().getString("id");
        }
        //
        if (TextUtils.isEmpty(id)) {
            ToastUtils.show("该帖子不存在");
            finish();
        }


    }

    @Override
    protected int getChildLayoutResId() {
        return R.layout.activity_forum_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("详情");
        setTopRightButton("举报", new OnMenuClickListener() {
            @Override
            public void onClick() {
                report();
            }
        });
        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mListData = (List<ForumDetailEntity.ReplyBean>) mBaseList;
        mBaseRecycleViewAdapter = new ForumDetailRecyclerViewAdapter(mContext, mListData);
        mBaseRecycleViewAdapter.setView(lilInput, etInput);

        recyclerView.setAdapter(mBaseRecycleViewAdapter);


    }

    /**
     * 帖子举报
     */
    private void report() {
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
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        jumpToActivity(ReportArticleActivity.class, bundle, false);

    }

    @Override
    protected void initListener() {
        super.initListener();
        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                LogUtils.d(TAG, "isShow = [" + isShow + "], keyboardHeight = [" + keyboardHeight + "]");
                if (!isShow) {
                    cleanInput();
                }
            }
        });
    }

    @Override
    protected void getData() {
        if (isFirst) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LogUtils.i(TAG, "首次加载……");
                    mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
                    isFirst = false;
                }
            }, 1000);

        } else {
            getForumInfoDetail();
        }

    }

    /**
     * 获取论坛帖子详细信息
     */
    private void getForumInfoDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getForumInfoDetail(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<ForumDetailEntity>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<ForumDetailEntity>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<ForumDetailEntity> response) {

                        mListData.clear();
                        if (response.getFlag() == 1) {
                            ForumDetailEntity dataBean = response.getData();

                            List<ForumDetailEntity.ReplyBean> replyBean = dataBean.getReply();
                            //楼主数据插入到列表中0位元素显示
                            ForumDetailEntity.ReplyBean bean = new ForumDetailEntity.ReplyBean();
                            bean.setParent_id(dataBean.getId());
                            bean.setTitle(dataBean.getTitle());
                            bean.setCreatetime(dataBean.getCreatetime());
                            bean.setEmail(dataBean.getEmail());
                            bean.setFull_name(dataBean.getFull_name());
                            bean.setId(dataBean.getId());
                            bean.setMsg(dataBean.getMsg());
                            bean.setStatus(dataBean.getStatus());
                            bean.setUserinfo(dataBean.getUserinfo());
                            replyBean.add(0, bean);

                            mListData.addAll(replyBean);
                            mBaseRecycleViewAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();

                        if (mPullToRefreshRecyclerView != null && mPullToRefreshRecyclerView.isRefreshing()) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                        }
                    }

                    @Override
                    public void _onError(int status, String msg) {
                        super._onError(status, msg);
                        if (mPullToRefreshRecyclerView != null && mPullToRefreshRecyclerView.isRefreshing()) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                        }
                    }
                });
    }


    @OnClick({R.id.tvCancel, R.id.tvReplay, R.id.ivSendMsg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                cleanInput();
                break;
            case R.id.tvReplay:
                replayInfo();
                break;
            case R.id.ivSendMsg:
                if (mListData.size() > 0) {
                    ForumDetailEntity.UserinfoBean userinfo = mListData.get(0).getUserinfo();
                    if (userinfo != null) {
                        lilInput.setVisibility(View.VISIBLE);
                        String reply = TextUtils.isEmpty(userinfo.getUser_nicename()) ? "" : userinfo.getUser_nicename();
                        etInput.setHint("回复：" + reply);
                        KeyBoardUtils.openKeyboard(etInput, mContext);
                    }

                }

                break;
        }
    }

    /**
     * 回复信息
     */
    private void replayInfo() {
        String msg = etInput.getText().toString();
        SPUtil sp = new SPUtil(MApplication.applicationContext, Constants.USER_TABLE);
        boolean isLogin = sp.getBoolean(UserDB.isLogin, false);
        if (!isLogin) {
            jumpToActivity(LoginActivity.class, false);
            return;
        }
        String tel = sp.getString(UserDB.TEL, "");
        if (TextUtils.isEmpty(tel)) {
            ToastUtils.show("未登录，请登录后操作");
            jumpToActivity(LoginActivity.class, false);
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.show("请填写回复内容");
            return;
        }

        JSONObject jsonObj = (JSONObject) etInput.getTag();
        String parent_id;
        String tworeply = null;
        if (jsonObj != null) {
            parent_id = jsonObj.getString("parent_id");
            tworeply = jsonObj.getString("tworeply");
        } else {
            parent_id = id;
        }

        Map<String, String> map = new HashMap<>();
        map.put("full_name", tel);
        map.put("msg", msg);
        map.put("parent_id", parent_id);
        if (!TextUtils.isEmpty(tworeply)) {
            map.put("tworeply", tworeply);
        }
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.replayInfo(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<Object>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<Object>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<Object> response) {
                        if (response.getFlag() == 1) {
                            cleanInput();
                            getForumInfoDetail();
                        }
                        ToastUtils.show(response.getErrmsg() + "");

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        cleanInput();
                    }

                    @Override
                    public void _onError(int status, String msg) {
                        super._onError(status, msg);
                        cleanInput();
                    }

                });

    }


    /**
     * 清理输入框
     */
    private void cleanInput() {
        lilInput.setVisibility(View.GONE);
        etInput.setText("");
        etInput.setTag(null);
        etInput.setHint("回复楼主");
        KeyBoardUtils.hideSoftInput(mActivity);
    }
}
