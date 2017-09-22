package com.muzikun.lhfsyczl.fragment.lotoking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.LotoKingActivity;
import com.muzikun.lhfsyczl.activity.SalaryActivity;
import com.muzikun.lhfsyczl.adapter.LotoKingRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BasePullRefreshFragment;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.LotoKingEntity;
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
 * 功能描述：高手资料-六合王
 */

public class LotoKingFragment extends BasePullRefreshFragment {


    public List<LotoKingEntity> mList;
    private String title = "";
    private boolean isFirst = true;//是否第一次加载
    private LotoKingRecyclerAdapter mBaseRecycleViewAdapter;

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
        mList = (List<LotoKingEntity>) mBaseList;
        mBaseRecycleViewAdapter = new LotoKingRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(mBaseRecycleViewAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBaseRecycleViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mList!=null&&mList.size()>position){
                    LotoKingEntity bean = mList.get(position);
                    if (bean == null) {
                        ToastUtils.show("无法查看心水");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    LotoKingEntity.UserinfoBean userinfoBean = bean.getUserinfo();
                    if (userinfoBean != null) {
                        String avatar = userinfoBean.getAvatar();
                        if (avatar != null && !avatar.startsWith("http")) {
                            avatar = "http://" + avatar;
                        }
                        bundle.putString("nickname", userinfoBean.getUser_nicename() + "");
                        bundle.putString("avatar", avatar);
                        bundle.putString("mobile", userinfoBean.getMobile());
                        mActivity.jumpToActivity(SalaryActivity.class, bundle, false);
                    } else {
                        ToastUtils.show("无法查看心水");
                    }
                }else{
                    ToastUtils.show("刷新数据后，请重试");
                    if (mPullToRefreshRecyclerView != null) {
                        mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
                    }
                }


            }
        });
    }

    @Override
    public void getData() {
        if (isFirst) {
            LogUtils.i(TAG, "首次加载……");
            if (mPullToRefreshRecyclerView != null) {
                mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
            }
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
        String date = "";
        String type = "";
        int checkedId = ((LotoKingActivity) mActivity).radioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.rbWeek) {
            date = "week";
        } else {
            date = "month";
        }

        if ("大小".equals(title)) {
            type = "1";
        } else if ("单双".equals(title)) {
            type = "2";
        } else if ("生肖".equals(title)) {
            type = "3";
        } else if ("号码".equals(title)) {
            type = "4";
        }

        Map<String, String> map = new HashMap<>();
        map.put("date", date);
        map.put("type", type);

        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getLotoKing(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<List<LotoKingEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<List<LotoKingEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<List<LotoKingEntity>> response) {
                        if (response.getFlag() == 1) {
                            List<LotoKingEntity> list = response.getData();
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
