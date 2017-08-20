package com.cpcp.loto.fragment.trend;

import android.app.Activity;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.BoseTrendRecyclerAdapter;
import com.cpcp.loto.adapter.DanShuangTrendRecyclerAdapter;
import com.cpcp.loto.base.BasePullRefreshFragment;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.TrendAnalysisEntity;
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
 * 功能描述：单双走势
 */

public class DanShuangTrendFragment extends BasePullRefreshFragment {


    private List<TrendAnalysisEntity> mList;
    private String title = "";
    private boolean isFirst = true;//是否第一次加载
    private DanShuangTrendRecyclerAdapter mBaseRecycleViewAdapter;

    @Override
    protected int getChildLayoutResId() {
        return R.layout.fragment_trend;
    }

    @Override
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            title = getArguments().getString("text");
        }

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = (List<TrendAnalysisEntity>) mBaseList;
        mBaseRecycleViewAdapter = new DanShuangTrendRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(mBaseRecycleViewAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();

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


        Map<String, String> map = new HashMap<>();

        map.put("type", "danshuang");
        map.put("page", currentPage+"");
        map.put("year","2017");
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getTrend(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<List<TrendAnalysisEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<List<TrendAnalysisEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<List<TrendAnalysisEntity>> response) {
                        if (response.getFlag() == 1) {
                            List<TrendAnalysisEntity> list = response.getData();
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
                        if (currentPage == 1 && mPullToRefreshRecyclerView != null) {
                            mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        currentPage += 1;
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
