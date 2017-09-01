package com.muzikun.lhfsyczl.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.util.DateTimeUtils;
import com.muzikun.lhfsyczl.view.DividerItemDecoration;
import com.muzikun.lhfsyczl.view.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：下拉刷新基础Activity，封装好了刷新，子类需要定义layout中含有PullToRefreshRecyclerView
 * 并且id为pullToRefreshRecyclerView
 */

public abstract class BasePullRefreshActivity extends BaseActivity {
    protected RecyclerView recyclerView;
    @BindView(R.id.pullToRefreshRecyclerView)
    public PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    //刷新时label控件
    protected String label = null;
    protected int currentPage = 1;
    //刷新列表
    protected BaseRecycleViewAdapter mBaseRecycleViewAdapter;
    protected List<?> mBaseList;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected int getLayoutResId() {
        return getChildLayoutResId();
    }

    /**
     * @return 布局文件
     */
    protected abstract int getChildLayoutResId();

    @Override
    protected void initView() {


        recyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        mBaseList = new ArrayList<>();
        initRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                try {
                    label = DateTimeUtils.timestampConvertMonthTime(System.currentTimeMillis() + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上次刷新:" + label);
                currentPage = 1;
                mBaseList.clear();
                getData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

                getData();
            }
        });
    }

    @Override
    protected void initData() {

        getData();
    }

    /**
     * 调用接口获取数据
     */
    protected abstract void getData();

    /**
     * 初始化刷新部分
     */
    private void initRefresh() {
//        mPullRefreshRecyclerView.setHasPullUpFriction(false); // 设置没有上拉阻力

        //
        ILoadingLayout startLabels = mPullToRefreshRecyclerView.getLoadingLayoutProxy(true, false);
        // 设置下拉时显示的日期和时间
        try {
            label = DateTimeUtils.timestampConvertMonthTime(System.currentTimeMillis() + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startLabels.setLastUpdatedLabel("上次刷新:" + label);
        startLabels.setPullLabel("下拉可以刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("加载...");// 刷新时
        startLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = mPullToRefreshRecyclerView.getLoadingLayoutProxy(false, true);
        endLabels.setLastUpdatedLabel("");
        endLabels.setPullLabel("上拉可以加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示
    }

}
