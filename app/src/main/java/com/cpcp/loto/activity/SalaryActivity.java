package com.cpcp.loto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabRichAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.bean.AttentionBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.fragment.xinshui.CurrentFragment;
import com.cpcp.loto.fragment.xinshui.HistoryFragment;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.GlideCircleTransform;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.SelectedLayerTextView;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：我的薪水
 */

public class SalaryActivity extends BaseActivity {

    @BindView(R.id.salary_head)
    AppCompatImageView salaryHead;
    @BindView(R.id.salary_name)
    TextView salaryName;
    @BindView(R.id.salary_tablayout)
    SegmentTabLayout salaryTablayout;
    @BindView(R.id.salary_viewpager)
    ViewPager salaryViewpager;
    @BindView(R.id.tvAttention)
    SelectedLayerTextView tvAttention;


    String nickname;
    String avatar;
    String mobile;

    CurrentFragment currentFragment;
    HistoryFragment historyFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_salary;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin = sp.getBoolean(UserDB.isLogin, false);
        if (isLogin) {
            String tel = sp.getString(UserDB.TEL, "");
            if (!TextUtils.isEmpty(mobile) && mobile.equals(tel)) {
                tvAttention.setVisibility(View.GONE);
            } else {
                //判断是否已关注，决定显示关注UI
                isFriend(tel);
            }
        } else {
            tvAttention.setVisibility(View.VISIBLE);
            tvAttention.setText("关注");
        }


    }

    /**
     * 是否为已关注
     */
    private void isFriend(String tel) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("username", tel + "");
        map.put("friendname", mobile);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.isFirend(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<Object>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<Object>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<Object> response) {
                        tvAttention.setVisibility(View.VISIBLE);
                        if (response.getFlag() == 1) {
                            tvAttention.setText("取消关注");
                        } else {
                            tvAttention.setText("关注");
                        }
                    }
                });
    }

    @Override
    protected void initView() {
        setTitle("心水");


        Log.i(TAG, "mobile = " + mobile);
        salaryName.setText(nickname);
        if (!"".equals(avatar)) {
            Glide.with(this)
                    .load(avatar)
                    .transform(new GlideCircleTransform(mContext))
                    .into(salaryHead);
        } else {
            salaryHead.setImageResource(R.drawable.icon_default_head);
        }

        String[] mTitles = {"本期推荐", "历史推荐"};
        salaryTablayout.setTabData(mTitles);

        List<BaseFragment> fragments = new ArrayList<>();

        currentFragment = new CurrentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mobile", mobile);
        currentFragment.setArguments(bundle);
        fragments.add(currentFragment);

        historyFragment = new HistoryFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("mobile", mobile);
        historyFragment.setArguments(bundle2);
        fragments.add(historyFragment);

        salaryViewpager.setAdapter(new TabRichAdapter(fragments, getSupportFragmentManager(), this));
        salaryTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                salaryViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        salaryViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                salaryTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            nickname = bundle.getString("nickname");
            avatar = bundle.getString("avatar");
            mobile = bundle.getString("mobile");
        }
    }


    @OnClick({R.id.tvAttention})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tvAttention:
                String text = tvAttention.getText().toString();
                if ("关注".equals(text)) {
                    //关注
                    addAttention();
                } else {
                    cancelAttention();
                }
                break;

        }
    }

    /**
     * 取消关注
     */
    private void cancelAttention() {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("friendname", mobile);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.cancelAttention(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        if (response.getFlag() == 1) {
                            ToastUtils.show("已取消关注");
                            tvAttention.setText("关注");
                        }else{
                            ToastUtils.show(response.getErrmsg()+"");
                        }
                    }

                });
    }

    /**
     * 添加为关注
     */
    private void addAttention() {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("friendname", mobile);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.addFriend(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<Object>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<Object>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<Object> response) {
                        if (response.getFlag() == 1) {
                            ToastUtils.show("已关注");
                            tvAttention.setText("取消关注");
                        }else{
                            ToastUtils.show(response.getErrmsg()+"");
                        }
                    }

                });
    }
}
