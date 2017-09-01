package com.muzikun.lhfsyczl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.AttentionRecyclerAdapter;
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
 * 功能描述：我的关注
 */

public class AttentionActivity extends BasePullRefreshActivity {

    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;
    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;

    AttentionRecyclerAdapter mAdapter;
    List<AttentionBean> data = new ArrayList<>();

    private boolean isFirst = true;//是否第一次加载

    @Override
    protected int getChildLayoutResId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的关注");

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        data = (List<AttentionBean>) mBaseList;
        mAdapter = new AttentionRecyclerAdapter(mContext, data);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(AttentionActivity.this,SalaryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nickname", data.get(position).user_nicename);
                bundle.putString("mobile", data.get(position).mobile);
                bundle.putString("avatar", "http://" + data.get(position).avatar);
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
            getAttentionData();
        }
    }

    public void getAttentionData() {

        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getMyAttention(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        if (response.getFlag() == 1) {
                            data.clear();
                            Log.i(TAG, "getAttentionData ---->" + response.getData());
                            try {
                                JSONArray array = new JSONArray(response.getData());
                                if (array.length() < 1) {
                                    tvMsg.setText("还没关注任何人...");
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
