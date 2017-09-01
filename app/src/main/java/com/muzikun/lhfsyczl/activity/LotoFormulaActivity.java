package com.muzikun.lhfsyczl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.LotoFormulaRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BasePullRefreshActivity;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.FormulaEntity;
import com.muzikun.lhfsyczl.listener.OnItemClickListener;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：六合公式
 */

public class LotoFormulaActivity extends BasePullRefreshActivity {
    private List<FormulaEntity> mList;
    private boolean isFirst = true;//是否第一次加载
    private LotoFormulaRecyclerAdapter mBaseRecycleViewAdapter;


    @Override
    protected int getChildLayoutResId() {
        return R.layout.activity_loto_formula;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("公式");

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = (List<FormulaEntity>) mBaseList;
        mBaseRecycleViewAdapter = new LotoFormulaRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(mBaseRecycleViewAdapter);

        getData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBaseRecycleViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position==0){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("content",mList.get(position-1).getPost_content());
                jumpToActivity(FormulaDetailActivity.class,bundle,false);
            }
        });
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
        httpService.getFormula(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<List<FormulaEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<List<FormulaEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<List<FormulaEntity>> response) {
                        if (response.getFlag() == 1) {
                            List<FormulaEntity> list = response.getData();
                            if (list != null && list.size() > 0) {
                                mList.addAll(list);
                            } else {
                                ToastUtils.show("没有更多的数据");
                                mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

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
