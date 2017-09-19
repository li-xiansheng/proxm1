package com.muzikun.lhfsyczl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.FansRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BasePullRefreshActivity;
import com.muzikun.lhfsyczl.bean.AttentionBean;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.listener.OnItemClickListener;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：我的粉丝
 */

public class FansActivity extends BasePullRefreshActivity {


    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    FansRecyclerAdapter mAdapter;
    List<AttentionBean> data = new ArrayList<>();

    private boolean isFirst = true;//是否第一次加载

    @Override
    protected int getChildLayoutResId() {
        return R.layout.activity_fans;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的粉丝");

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        data = (List<AttentionBean>) mBaseList;
        mAdapter = new FansRecyclerAdapter(mContext, data);
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String avatar=data.get(position).avatar;
                if(avatar!=null&&!avatar.startsWith("http")){
                    avatar="http://"+avatar;
                }
                Bundle bundle = new Bundle();
                bundle.putString("nickname", data.get(position).user_nicename);
                bundle.putString("mobile", data.get(position).mobile);
                bundle.putString("avatar", avatar);
                jumpToActivity(SalaryActivity.class, bundle, false);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
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
            getMyFenSi();
        }
    }

    private void getMyFenSi() {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getMyFensi(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LogUtils.i(TAG, "getCurrentRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            if (response.getData() != null) {
                                emptyRl.setVisibility(View.GONE);
                                try {
                                    JSONArray array = new JSONArray(response.getData());
                                    if (array.length() < 1) {
                                        tvMsg.setText("暂时还没粉丝...");
                                        emptyRl.setVisibility(View.VISIBLE);

                                    } else {
                                        emptyRl.setVisibility(View.GONE);
                                        Gson gson = new Gson();
                                        for (int i = 0; i < array.length(); i++) {
                                            AttentionBean bean = gson.fromJson(array.getJSONObject(i).toString(), AttentionBean.class);
                                            data.add(bean);
                                        }

                                        mAdapter.notifyDataSetChanged();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tvMsg.setText("暂时还没粉丝...");
                                emptyRl.setVisibility(View.VISIBLE);
                            }


                        } else {
                            tvMsg.setText("暂时还没粉丝...");
                            emptyRl.setVisibility(View.VISIBLE);
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
