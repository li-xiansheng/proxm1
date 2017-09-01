package com.muzikun.lhfsyczl.fragment.rich;

import android.app.Activity;
import android.view.View;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.CaiShenRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BasePullRefreshFragment;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.CaiShenEntity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：我的财神
 */

public class CaiShenFragment extends BasePullRefreshFragment {


    private boolean isFirst = true;
    private List<CaiShenEntity> mList;

    @Override
    protected int getChildLayoutResId() {
        return R.layout.fragment_cai_shen;
    }

    @Override
    protected void initView() {
        super.initView();
        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mList = (List<CaiShenEntity>) mBaseList;
        mBaseRecycleViewAdapter = new CaiShenRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(mBaseRecycleViewAdapter);


    }

    @Override
    protected void getData() {
        if (isFirst) {
            LogUtils.i(TAG, "首次加载……");
            mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
            isFirst = false;
        } else {
            getMyMammom();
        }
    }

    /**
     * 我的财神数据
     */
    private void getMyMammom() {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        ((BaseActivity) mActivity).mSubscription = httpService.getMyMammom(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<List<CaiShenEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<List<CaiShenEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<List<CaiShenEntity>> response) {
                        if (response.getFlag() == 1) {
                            List<CaiShenEntity> list = response.getData();
                            if (list != null && list.size() > 0) {
                                mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
                                mList.addAll(list);
                            } else {
                                mPullToRefreshRecyclerView.setVisibility(View.INVISIBLE);
                            }
                            mBaseRecycleViewAdapter.notifyDataSetChanged();
                        } else {
                            mPullToRefreshRecyclerView.setVisibility(View.INVISIBLE);
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

    @Override
    public void onLazyLoadData() {
        getData();
    }
}
