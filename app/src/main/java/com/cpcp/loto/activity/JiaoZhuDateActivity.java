package com.cpcp.loto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.JiaozhuDateRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.entity.BaseResponse1Entity;
import com.cpcp.loto.entity.NextLotteryEntity;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.DateUtils;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.view.SelectedLayerImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：搅珠日期
 */

public class JiaoZhuDateActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ivLeft)
    SelectedLayerImageView ivLeft;
    @BindView(R.id.ivDate)
    AppCompatTextView ivDate;
    @BindView(R.id.ivRight)
    SelectedLayerImageView ivRight;

    @BindView(R.id.tvNextDate)
    AppCompatTextView tvNextDate;
    private JiaozhuDateRecyclerAdapter dateRecyclerAdapter;

    private int year;
    private int month;
    //获取系统，时间二维数组
    private int[][] days = new int[6][7];

    private List<Integer> mList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_jiaozhu_date;
    }

    @Override
    protected void initView() {
        setTitle("搅珠日期");
        mList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        dateRecyclerAdapter = new JiaozhuDateRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(dateRecyclerAdapter);

        //
        ivLeft.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

        //
        year = DateUtils.getYear();
        month = DateUtils.getMonth();

        days = DateUtils.getDayOfMonthFormat(year, month);
        LogUtils.i(TAG, "日期" + days.toString());
        setDate();
        transformDays();

        getNextLotteryDate();
    }

    /**
     * 获取下次开奖日期
     */
    private void getNextLotteryDate() {
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getNextDate()
                .compose(RxSchedulersHelper.<NextLotteryEntity>io_main())
                .subscribe(new RxSubscriber<NextLotteryEntity>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, NextLotteryEntity response) {

                        if (response != null) {
                            String date = response.getOption_value();
                            tvNextDate.setText(date + "");
                            dateRecyclerAdapter.setTime(month, date);
                            dateRecyclerAdapter.notifyDataSetChanged();
                        }

                    }

                });
    }

    /**
     * 下一个月
     *
     * @return
     */
    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        setDate();
        return days;
    }

    /**
     * 将二维数组转换为集合
     */
    private void transformDays() {
        if (days != null && days.length > 0) {
            //将二维数组转化为一维数组，方便使用
            mList.clear();
            for (int i = 0; i < days.length; i++) {
                for (int j = 0; j < days[i].length; j++) {

                    mList.add(days[i][j]);
                }
            }
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i) > 1) {
                    mList.set(i, -1);
                } else {
                    break;
                }
            }
            for (int i = mList.size() - 1; i > 0; i--) {
                if (mList.get(i) < 28) {
                    mList.remove(i);
                } else {
                    break;
                }
            }
            dateRecyclerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 上一个月
     *
     * @return
     */
    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        setDate();
        return days;
    }

    /**
     * 设置日期
     */
    private void setDate() {
        ivDate.setText(year + "-" + month);
    }


    @OnClick({R.id.ivLeft, R.id.ivRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                ivLeft.setVisibility(View.INVISIBLE);
                ivRight.setVisibility(View.VISIBLE);
                prevMonth();
                transformDays();

                break;
            case R.id.ivRight:
                //
                ivLeft.setVisibility(View.VISIBLE);
                ivRight.setVisibility(View.INVISIBLE);
                nextMonth();
                transformDays();
                break;
        }
    }
}
