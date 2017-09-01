package com.muzikun.lhfsyczl.fragment.trend;

import android.app.Activity;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.TailTrendRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BasePullRefreshFragment;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.TrendAnalysisEntity;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.DateUtils;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：尾数走势
 */

public class TailTrendFragment extends BasePullRefreshFragment {


    private List<TrendAnalysisEntity> mList;
    private String title = "";
    private boolean isFirst = true;//是否第一次加载
    private TailTrendRecyclerAdapter mBaseRecycleViewAdapter;

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
        mBaseRecycleViewAdapter = new TailTrendRecyclerAdapter(mContext, mList);
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

        map.put("type", "weishu");
        map.put("page", currentPage + "");
        String year = "";
        String menuStr = ((BaseActivity) mActivity).menuStr;
        if ("年限查询".equals(menuStr)) {
            year = DateUtils.getYear() + "";
        } else {
            year = menuStr;
        }
        map.put("year", year + "");
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
