package com.cpcp.loto.fragment.trend;

import android.app.Activity;
import android.view.View;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.LotoKingActivity;
import com.cpcp.loto.adapter.LotoKingRecyclerAdapter;
import com.cpcp.loto.adapter.ShengXiaoTrendRecyclerAdapter;
import com.cpcp.loto.base.BasePullRefreshFragment;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.LotoKingEntity;
import com.cpcp.loto.entity.TrendAnalysisEntity;
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
 * 功能描述：生肖走势
 */

public class ShengXiaoTrendFragment extends BasePullRefreshFragment {


    private List<TrendAnalysisEntity> mList;
    private String title = "";
    private boolean isFirst = true;//是否第一次加载
    private ShengXiaoTrendRecyclerAdapter mBaseRecycleViewAdapter;

    @Override
    protected int getChildLayoutResId() {
        return R.layout.fragment_loto_king;
    }

    @Override
    protected void initView() {
        super.initView();
        if (getArguments() != null) {
            title = getArguments().getString("text");
        }

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = (List<TrendAnalysisEntity>) mBaseList;
        mBaseRecycleViewAdapter = new ShengXiaoTrendRecyclerAdapter(mContext, mList);
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

        String type = "";
        if ("生肖走势".equals(title)) {
            type = "shengxiao";
        } else if ("波色走势".equals(title)) {
            type = "bose";
        } else if ("单双走势".equals(title)) {
            type = "danshuang";
        } else if ("段位走势".equals(title)) {
            type = "duanwei";
        }else if ("头数走势".equals(title)) {
            type = "toushu";
        }else if ("尾数走势".equals(title)) {
            type = "weishu";
        }else if ("五行走势".equals(title)) {
            type = "wuxing";
        }

        Map<String, String> map = new HashMap<>();

        map.put("type", type);
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