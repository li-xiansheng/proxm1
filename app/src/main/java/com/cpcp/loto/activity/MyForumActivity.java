package com.cpcp.loto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.ForumRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BasePullRefreshActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.ForumEntity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.fragment.ForumFragment;
import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：我的论坛帖子
 */

public class MyForumActivity extends BasePullRefreshActivity {
    private List<ForumEntity> mList;
    private boolean isFirst = true;//是否第一次加载
    private ForumRecyclerAdapter mBaseRecycleViewAdapter;


    @Override
    protected int getChildLayoutResId() {
        return R.layout.activity_my_forum;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的帖子");

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = (List<ForumEntity>) mBaseList;
        mBaseRecycleViewAdapter = new ForumRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(mBaseRecycleViewAdapter);


        getData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBaseRecycleViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ForumEntity forumEntity = mList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", forumEntity.getId());
                ((BaseActivity) mActivity).jumpToActivity(ForumDetailActivity.class, bundle, false);


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
//                    fragments.get(0).setUserVisibleHint(true);
                }
            }, 1000);


        } else {
            getForumInfo();
        }
    }

    /**
     * 获取论坛信息
     */
    private void getForumInfo() {
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
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getMyForumInfo(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<List<ForumEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<List<ForumEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<List<ForumEntity>> response) {
                        mList.clear();
                        if (response.getFlag() == 1) {
                            List<ForumEntity> list = response.getData();
                            if (list != null && list.size() > 0) {
                                mList.addAll(list);
                            } else {
                                ToastUtils.show("没有更多的数据");
                            }
                            mBaseRecycleViewAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.show(response.getErrmsg() + "");
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

}
