package com.cpcp.loto.activity;

import android.app.Activity;
import android.os.Handler;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.ShengXiaoTrendRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BasePullRefreshActivity;
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
 * 功能描述：六合公式
 */

public class LotoFormulaActivity extends BasePullRefreshActivity {
    private List<TrendAnalysisEntity> mList;
    private boolean isFirst = true;//是否第一次加载
    private ShengXiaoTrendRecyclerAdapter mBaseRecycleViewAdapter;


    @Override
    protected int getChildLayoutResId() {
        return R.layout.activity_loto_formula;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("公式");

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = (List<TrendAnalysisEntity>) mBaseList;
        mBaseRecycleViewAdapter = new ShengXiaoTrendRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(mBaseRecycleViewAdapter);
    }

    @Override
    protected void getData() {
        if (isFirst) {
            LogUtils.i(TAG, "首次加载……");
            //主动调取第一个页面可见执行懒加载
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
                    isFirst = false;
                }
            }, 1000);
        } else {
           getLotoFormula();
        }
    }

    /**
     * 获取公式数据
     */
    private void getLotoFormula() {

        Map<String, String> map = new HashMap<>();

        map.put("pageStart", currentPage+"");
        map.put("pageSize","20");
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
