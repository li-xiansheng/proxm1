package com.cpcp.loto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.LiveExpandableListAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.entity.BaseResponse1Entity;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.OpenLotteryLiveEntity;
import com.cpcp.loto.entity.OpenLotteryLiveMEntity;
import com.cpcp.loto.entity.OpenLotteryZuiXinEntity;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.DateTimeUtils;
import com.cpcp.loto.util.DialogUtil;
import com.cpcp.loto.util.DisplayUtil;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.MarqueeTextView;
import com.cpcp.loto.view.SelectedLayerImageView;
import com.cpcp.loto.view.SelectedLayerTextView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：开奖直播
 */

public class OpenLotteryLiveActivity extends BaseActivity {
    @BindView(R.id.pullToRefreshScrollView)
    PullToRefreshScrollView pullToRefreshScrollView;

    @BindView(R.id.tvNotice)
    MarqueeTextView tvNotice;
    @BindView(R.id.lilHaoMa)
    LinearLayout lilHaoMa;
    @BindView(R.id.lilShengXiao)
    LinearLayout lilShengXiao;
    @BindView(R.id.tvNextOpenLottery)
    AppCompatTextView tvNextOpenLottery;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvQiShu)
    AppCompatTextView tvQiShu;
    @BindView(R.id.ivLive)
    SelectedLayerImageView ivLive;
    @BindView(R.id.tvWait)
    SelectedLayerTextView tvWait;


    //刷新时label控件
    protected String label = null;
    //
    private OpenLotteryLiveEntity openLotteryLiveEntity;
    private LiveExpandableListAdapter mExpandableListAdapter;
    private List<OpenLotteryLiveMEntity> mMEntities;
    //
    private OpenLotteryZuiXinEntity openLotteryZuiXinEntity;

    /**
     * 初始化刷新部分
     */
    private void initRefresh() {
//        mPullRefreshRecyclerView.setHasPullUpFriction(false); // 设置没有上拉阻力

        //
        ILoadingLayout startLabels = pullToRefreshScrollView.getLoadingLayoutProxy(true, false);
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

        ILoadingLayout endLabels = pullToRefreshScrollView.getLoadingLayoutProxy(false, true);
        endLabels.setLastUpdatedLabel("");
        endLabels.setPullLabel("上拉可以加载更多...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_open_lottery;
    }

    @Override
    protected void initView() {
        setTitle("开奖直播");

        mMEntities = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        initRefresh();
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLottery();
    }

    @Override
    protected void initListener() {
        super.initListener();
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                try {
                    label = DateTimeUtils.timestampConvertMonthTime(System.currentTimeMillis() + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                refreshView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上次刷新:" + label);
                getLottery();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.i(TAG, "首次加载……");
                pullToRefreshScrollView.setRefreshing(true);//没有刷新，则执行下拉刷新UI

            }
        }, 1000);
    }

    /**
     * 获取开奖彩票数据
     */
    private void getLottery() {
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.openLotteryLive()
                .compose(RxSchedulersHelper.<BaseResponse2Entity<OpenLotteryLiveEntity>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<OpenLotteryLiveEntity>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<OpenLotteryLiveEntity> response) {
                        if (1 == response.getFlag()) {
                            openLotteryLiveEntity = response.getData();
                            if (openLotteryLiveEntity != null) {
                                setDataToUI(openLotteryLiveEntity);
                                getLotteryInfo();//成功后刷新最新开奖
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();


                        if (pullToRefreshScrollView != null && pullToRefreshScrollView.isRefreshing()) {
                            pullToRefreshScrollView.onRefreshComplete();
                        }
                    }

                    @Override
                    public void _onError(int status, String msg) {
                        super._onError(status, msg);
                        if (pullToRefreshScrollView != null && pullToRefreshScrollView.isRefreshing()) {
                            pullToRefreshScrollView.onRefreshComplete();
                        }
                    }
                });
    }

    /**
     * 获取开奖结果
     */
    private void getLotteryInfo() {
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.openLotteryZuixin()
                .compose(RxSchedulersHelper.<BaseResponse1Entity<List<OpenLotteryZuiXinEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse1Entity<List<OpenLotteryZuiXinEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse1Entity<List<OpenLotteryZuiXinEntity>> response) {
                        if ("成功".equals(response.getResult())) {
                            List<OpenLotteryZuiXinEntity> list = response.getData();
                            if (list != null && list.size() > 0) {
                                openLotteryZuiXinEntity = list.get(0);
                                setDataToZuixinUI(openLotteryZuiXinEntity);
                            }

                        }
                    }

                    @Override
                    public void _onError(int status, String msg) {
                        super._onError(status, msg);
                        mSubscription = null;
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mSubscription = null;
                    }
                });
    }


    /**
     * 开奖直播
     *
     * @param entity
     */
    private void setDataToUI(OpenLotteryLiveEntity entity) {

        String notice = entity.getNotice();
        String nextTime = entity.getNextopentime();

        tvNotice.setText(notice + "");
        tvNextOpenLottery.setText(nextTime + "");

        //历史开奖
        List<List<OpenLotteryLiveEntity.RecommendBean>> lists = entity.getRecommend();
        mMEntities.clear();
        if (lists != null && lists.size() > 0) {
            for (int i = 0; i < lists.size(); i++) {
                List<OpenLotteryLiveEntity.RecommendBean> recommendBean = lists.get(i);
                if (recommendBean != null && recommendBean.size() > 0) {
                    OpenLotteryLiveEntity.RecommendBean bean = recommendBean.get(0);
                    OpenLotteryLiveMEntity openLotteryLiveMEntity = new OpenLotteryLiveMEntity();
                    openLotteryLiveMEntity.setQishu(bean.getQishu());
                    openLotteryLiveMEntity.setPartners(recommendBean);
                    mMEntities.add(openLotteryLiveMEntity);
                }
            }
        }
        mExpandableListAdapter = new LiveExpandableListAdapter(mContext, mMEntities);
        recyclerView.setAdapter(mExpandableListAdapter);
    }

    /**
     * 轮询最新开奖数据
     */
    private Timer timer;

    /**
     * 设置最新开奖号到UI
     *
     * @param entity
     */
    private void setDataToZuixinUI(OpenLotteryZuiXinEntity entity) {

        String date = entity.getNo();
        tvQiShu.setText("第" + date + "开奖结果");
        List<String> haomaList = new ArrayList<>();
        haomaList.add(entity.getCode1());
        haomaList.add(entity.getCode2());
        haomaList.add(entity.getCode3());
        haomaList.add(entity.getCode4());
        haomaList.add(entity.getCode5());
        haomaList.add(entity.getCode6());
        haomaList.add(entity.getTema());

        List<String> shengxiaoList = entity.getShengxiao();
        int withOrHeight = DisplayUtil.dip2px(mContext, 34);


        lilHaoMa.removeAllViews();
        lilShengXiao.removeAllViews();


        tvWait.setVisibility(View.GONE);
        long currentTime = System.currentTimeMillis();
        try {
            long lotteryTime = Long.parseLong(openLotteryLiveEntity.getTimeprompt()) * 1000;
            if (lotteryTime - currentTime > 0 && lotteryTime - currentTime < 1000 * 60 * 5) {//提前5分钟等待
                tvWait.setVisibility(View.VISIBLE);
                //设置离开奖时间多少秒后，每隔5秒，轮询最新开奖数据
                if (timer == null) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getLotteryInfo();
                        }
                    }, lotteryTime - currentTime, 5000);
                }


            } else if (TextUtils.isEmpty(openLotteryLiveEntity.getNo())) {//最新开奖显示为null
                tvWait.setVisibility(View.VISIBLE);
                //设置离开奖时间多少秒后，每隔5秒，轮询最新开奖数据
                if (timer == null) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getLotteryInfo();
                        }
                    }, 0, 5000);
                }
            } else {
                if (currentTime - lotteryTime > 0 && TextUtils.isEmpty(openLotteryZuiXinEntity.getTema())) {
                    //设置离开奖时间多少秒后，每隔5秒，轮询最新开奖数据
                    if (timer == null) {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                getLotteryInfo();
                            }
                        }, 0, 5000);
                    }
                    try {
                        for (int i = 0; i < haomaList.size(); i++) {
                            if (TextUtils.isEmpty(haomaList.get(i))) {
                                break;
                            }

                            if (i == 6) {
                                AppCompatTextView tv = new AppCompatTextView(mContext);
                                tv.setGravity(Gravity.CENTER);
                                tv.setBackgroundResource(R.drawable.icon_live_add);

                                lilHaoMa.addView(tv);
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                                params.setMargins(10, 0, 0, 0);
                                params.width = withOrHeight;
                                params.height = withOrHeight;
                                tv.setLayoutParams(params);
                                //
                                AppCompatTextView tvShengxiao = new AppCompatTextView(mContext);
                                lilShengXiao.addView(tvShengxiao);
                                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tvShengxiao.getLayoutParams();
                                params1.setMargins(10, 0, 0, 0);
                                params1.width = withOrHeight;
                                params1.height = withOrHeight;
                                tvShengxiao.setLayoutParams(params1);

                            }
                            AppCompatTextView tv = new AppCompatTextView(mContext);
                            tv.setGravity(Gravity.CENTER);

                            if (i == 1 || i == 2 || i == 3 || i == 5) {
                                tv.setBackgroundResource(R.drawable.redball);
                            } else if (i == 0) {
                                tv.setBackgroundResource(R.drawable.blueball);
                            } else {
                                tv.setBackgroundResource(R.drawable.greenball);
                            }
                            String haoma = haomaList.get(i) + "";
                            tv.setText(haoma);
                            lilHaoMa.addView(tv);

                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                            params.setMargins(10, 0, 0, 0);
                            params.width = withOrHeight;
                            params.height = withOrHeight;
                            tv.setLayoutParams(params);

                            //
                            AppCompatTextView tvShengXiao = new AppCompatTextView(mContext);
                            tvShengXiao.setGravity(Gravity.CENTER);

                            String shengxiao = shengxiaoList.get(i) + "";
                            tvShengXiao.setText(shengxiao);

                            lilShengXiao.addView(tvShengXiao);
                            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tvShengXiao.getLayoutParams();
                            params1.setMargins(10, 0, 0, 0);
                            params1.width = withOrHeight;
                            params1.height = withOrHeight;
                            tvShengXiao.setLayoutParams(params1);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (currentTime - lotteryTime > 0 && !TextUtils.isEmpty(openLotteryZuiXinEntity.getTema())) {
                    if (timer != null) {
                        timer.cancel();
                    }

                    try {
                        for (int i = 0; i < haomaList.size(); i++) {
                            if (TextUtils.isEmpty(haomaList.get(i))) {
                                break;
                            }

                            if (i == 6) {
                                AppCompatTextView tv = new AppCompatTextView(mContext);
                                tv.setGravity(Gravity.CENTER);
                                tv.setBackgroundResource(R.drawable.icon_live_add);

                                lilHaoMa.addView(tv);
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                                params.setMargins(10, 0, 0, 0);
                                params.width = withOrHeight;
                                params.height = withOrHeight;
                                tv.setLayoutParams(params);
                                //
                                AppCompatTextView tvShengxiao = new AppCompatTextView(mContext);
                                lilShengXiao.addView(tvShengxiao);
                                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tvShengxiao.getLayoutParams();
                                params1.setMargins(10, 0, 0, 0);
                                params1.width = withOrHeight;
                                params1.height = withOrHeight;
                                tvShengxiao.setLayoutParams(params1);

                            }
                            AppCompatTextView tv = new AppCompatTextView(mContext);
                            tv.setGravity(Gravity.CENTER);

                            if (i == 1 || i == 2 || i == 3 || i == 5) {
                                tv.setBackgroundResource(R.drawable.redball);
                            } else if (i == 0) {
                                tv.setBackgroundResource(R.drawable.blueball);
                            } else {
                                tv.setBackgroundResource(R.drawable.greenball);
                            }
                            String haoma = haomaList.get(i) + "";
                            tv.setText(haoma);
                            lilHaoMa.addView(tv);

                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                            params.setMargins(10, 0, 0, 0);
                            params.width = withOrHeight;
                            params.height = withOrHeight;
                            tv.setLayoutParams(params);

                            //
                            AppCompatTextView tvShengXiao = new AppCompatTextView(mContext);
                            tvShengXiao.setGravity(Gravity.CENTER);

                            String shengxiao = shengxiaoList.get(i) + "";
                            tvShengXiao.setText(shengxiao);

                            lilShengXiao.addView(tvShengXiao);
                            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tvShengXiao.getLayoutParams();
                            params1.setMargins(10, 0, 0, 0);
                            params1.width = withOrHeight;
                            params1.height = withOrHeight;
                            tvShengXiao.setLayoutParams(params1);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    @OnClick({R.id.ivLive, R.id.tvWait})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ivLive:
                long currentTime = System.currentTimeMillis();
                if (openLotteryLiveEntity != null && openLotteryZuiXinEntity != null) {
                    long lotteryTime = Long.parseLong(openLotteryLiveEntity.getTimeprompt()) * 1000;


                    if (lotteryTime - currentTime > 0 && lotteryTime - currentTime < 1000 * 60 * 5) {//提前5分钟
                        Bundle bundle = new Bundle();
                        bundle.putLong("nextOpenLottery", lotteryTime);
                        bundle.putString("nextNo", openLotteryLiveEntity.getNextqishu()+"");
                        jumpToActivity(OpenLotteryingActivity.class, bundle, false);
                    } else if (currentTime - lotteryTime > 0 && TextUtils.isEmpty(openLotteryZuiXinEntity.getTema()) || TextUtils.isEmpty(openLotteryLiveEntity.getNo())) {//正在开奖
                        Bundle bundle = new Bundle();
                        bundle.putLong("nextOpenLottery", lotteryTime);
                        bundle.putString("nextNo", openLotteryLiveEntity.getNextqishu()+"");
                        jumpToActivity(OpenLotteryingActivity.class, bundle, false);
                    } else if (currentTime - lotteryTime > 0 && !TextUtils.isEmpty(openLotteryZuiXinEntity.getTema())) {//特码不为空
                        DialogUtil.createDialog(mContext, "直播已结束");
                    } else {
                        try {
                            String time = DateTimeUtils.timestampConvertMonthTime(lotteryTime + "");
                            DialogUtil.createDialog(mContext, "提示", time + "开奖期间才能观看");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    ToastUtils.show("请刷新获取最新数据");
                }

                break;
            case R.id.tvWait:
                if (openLotteryLiveEntity != null) {
                    long currentTime1 = System.currentTimeMillis();

                    long lotteryTime = Long.parseLong(openLotteryLiveEntity.getTimeprompt()) * 1000;
                    if (currentTime1 - lotteryTime > 0) {//已经在开奖

                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putLong("nextOpenLottery", lotteryTime);
                        bundle.putString("nextNo", openLotteryLiveEntity.getNextqishu()+"");
                        jumpToActivity(OpenLotteryingActivity.class, bundle, false);
                        jumpToActivity(OpenLotteryingActivity.class, false);
                    }

                    break;
                }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
