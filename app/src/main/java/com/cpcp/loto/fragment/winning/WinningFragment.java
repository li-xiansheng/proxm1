package com.cpcp.loto.fragment.winning;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.LotoKingActivity;
import com.cpcp.loto.activity.SalaryActivity;
import com.cpcp.loto.adapter.LotoKingRecyclerAdapter;
import com.cpcp.loto.adapter.WinningRecyclerAdapter;
import com.cpcp.loto.base.BasePullRefreshFragment;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.LotoKingEntity;
import com.cpcp.loto.entity.WinningEntity;
import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：高手资料-连胜榜
 */

public class WinningFragment extends BasePullRefreshFragment {


    private List<WinningEntity> mList;
    private String title = "";
    private boolean isFirst = true;//是否第一次加载
    private WinningRecyclerAdapter mBaseRecycleViewAdapter;

    @Override
    protected int getChildLayoutResId() {
        return R.layout.fragment_winning;
    }

    @Override
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            title = getArguments().getString("text");
        }

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = (List<WinningEntity>) mBaseList;
        mBaseRecycleViewAdapter = new WinningRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(mBaseRecycleViewAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBaseRecycleViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WinningEntity bean = mList.get(position);
                Bundle bundle = new Bundle();
                String username = "";
                if (bean.getUserinfo() != null) {
                    username = bean.getUserinfo().getUser_nicename();
                }
                bundle.putString("username", username);
                mActivity.jumpToActivity(SalaryActivity.class, bundle, false);
            }
        });
    }

    @Override
    public void getData() {
        if (isFirst) {
            LogUtils.i(TAG, "首次加载……");
            mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
            isFirst = false;
        } else {
            getLotoLottery();
        }
    }


    @Override
    public void onLazyLoadData() {
        LogUtils.i("懒加载" + title);
        getData();
    }

    private void getLotoLottery() {
        String type = "";

        if ("大小".equals(title)) {
            type = "1";
        } else if ("单双".equals(title)) {
            type = "2";
        } else if ("生肖".equals(title)) {
            type = "3";
        } else if ("号码".equals(title)) {
            type = "4";
        }
        mBaseRecycleViewAdapter.setType(type);
        Map<String, String> map = new HashMap<>();
        map.put("type", type);

        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getWinning(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<List<WinningEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<List<WinningEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<List<WinningEntity>> response) {
                        if (response.getFlag() == 1) {
                            List<WinningEntity> list = response.getData();
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
