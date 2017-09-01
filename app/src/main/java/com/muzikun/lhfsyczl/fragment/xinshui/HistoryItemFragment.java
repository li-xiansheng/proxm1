package com.muzikun.lhfsyczl.fragment.xinshui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.HistoryRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BasePullRefreshFragment;
import com.muzikun.lhfsyczl.bean.XinshuiBean;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
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
 * 功能描述：
 */

public class HistoryItemFragment extends BasePullRefreshFragment {

    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    List<XinshuiBean> data = new ArrayList<>();
    HistoryRecyclerAdapter mAdapter;

    int type;
    String mobile;

    boolean isFirst = true;

    @Override
    protected int getChildLayoutResId() {
        return R.layout.fragment_xinshui_current;
    }

    @Override
    protected void initView() {
        super.initView();
        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        data = (List<XinshuiBean>) mBaseList;
        mAdapter = new HistoryRecyclerAdapter(mContext, data);
        recyclerView.setAdapter(mAdapter);

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
            }, 500);


        } else {
            getHistoryRecommend();
        }
    }

    @Override
    public void onLazyLoadData() {
        getData();
    }


    @Override
    protected void getIntentData() {
        super.getIntentData();
        Bundle bundle = getArguments();
        mobile = bundle.getString("mobile");
        String typeStr = bundle.getString("title");
        LogUtils.i(TAG, "getIntentData ---->" + typeStr + "----" + mobile);
        if ("综合".equals(typeStr)) {
            type = 5;
        } else if ("大小".equals(typeStr)) {
            type = 1;
        } else if ("单双".equals(typeStr)) {
            type = 2;
        } else if ("生肖".equals(typeStr)) {
            type = 3;
        } else if ("号码".equals(typeStr)) {
            type = 4;
        }
    }

    private void getHistoryRecommend() {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", mobile);
        map.put("type", type + "");

        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getHistoryRecommend(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LogUtils.i(TAG, "getHistoryRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            if (response.getData() != null) {
                                emptyRl.setVisibility(View.GONE);
                                try {
                                    JSONArray array = new JSONArray(response.getData());

                                    for (int i = 0; i < array.length(); i++) {
                                        Gson gson = new Gson();
                                        XinshuiBean bean = gson.fromJson(array.getJSONObject(i).toString(), XinshuiBean.class);
                                        data.add(bean);
                                    }
                                    Log.i(TAG, "data = " + data.toString());

                                    mAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tvMsg.setText("暂时还没数据...");
                                emptyRl.setVisibility(View.VISIBLE);
                            }


                        } else {
                            tvMsg.setText("暂时还没数据...");
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
