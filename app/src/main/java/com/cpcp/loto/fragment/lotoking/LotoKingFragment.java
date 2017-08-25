package com.cpcp.loto.fragment.lotoking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.GetRedPacketActivity;
import com.cpcp.loto.activity.LotoKingActivity;
import com.cpcp.loto.activity.PublishXinShuiActivity;
import com.cpcp.loto.activity.SalaryActivity;
import com.cpcp.loto.adapter.LotoKingRecyclerAdapter;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.base.BasePullRefreshFragment;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.LotoKingEntity;
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
                LotoKingEntity bean = mList.get(position);
                if (bean == null) {
                    ToastUtils.show("无法查看心水");
                    return;
                }
                Bundle bundle = new Bundle();
                LotoKingEntity.UserinfoBean userinfoBean = bean.getUserinfo();
                if (userinfoBean != null) {
                    bundle.putString("nickname", userinfoBean.getUser_nicename() + "");
                    bundle.putString("avatar", "http://"+userinfoBean.getAvatar());
                    bundle.putString("mobile", userinfoBean.getMobile());
                    mActivity.jumpToActivity(SalaryActivity.class, bundle, false);
                } else {
                    ToastUtils.show("无法查看心水");
                }

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
