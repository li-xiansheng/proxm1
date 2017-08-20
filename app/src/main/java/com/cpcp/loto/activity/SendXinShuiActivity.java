package com.cpcp.loto.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabFragmentAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.fragment.sendxinshui.DaXiaoFragment;
import com.cpcp.loto.fragment.sendxinshui.DanShuangFragment;
import com.cpcp.loto.fragment.sendxinshui.HaoMaFragment;
import com.cpcp.loto.fragment.sendxinshui.ShengXiaoFragment;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：高手资料---发布心水
 */

public class SendXinShuiActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<BaseFragment> fragments;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_send_xin_shui;
    }

    @Override
    protected void initView() {
        setTitle("发布心水");

        setTopRightButton("发布", new OnMenuClickListener() {
            @Override
            public void onClick() {
                sendXinShui();
            }


        });

        String[] titles = {"大小", "单双", "生肖", "号码"};
        fragments = new ArrayList<>();


        for (int i = 0; i < titles.length; i++) {
            BaseFragment fragment = null;
            if (i == 0) {
                fragment = new DaXiaoFragment();
            } else if (i == 1) {
                fragment = new DanShuangFragment();
            } else if (i == 2) {
                fragment = new ShengXiaoFragment();
            } else if (i == 3) {
                fragment = new HaoMaFragment();
            }

            Bundle bundle = new Bundle();
            bundle.putString("text", titles[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new TabFragmentAdapter(fragments, titles, getSupportFragmentManager(), mContext));


        // 将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        tabLayout.setTabTextColors(Color.BLACK, Color.RED);
        tabLayout.setTabMode(tabLayout.MODE_FIXED);
        //主动调取第一个页面可见执行懒加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragments.get(0).setUserVisibleHint(true);
            }
        }, 1000);
    }

    @Override
    protected void initData() {

    }

    /**
     * 发布心水
     */
    private void sendXinShui() {
        //电话
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        if (TextUtils.isEmpty(tel)) {
            jumpToActivity(LoginActivity.class, false);
            return;
        }
        //类型
        String type = "";
        //获取标题
        String title = "";
        //获取查看心水
        String points = "";
        //获取是否上传单双大小
        String numbertype = "";
        //大小，单双，或者其他生肖号码
        String forecast = null;
        int currentPage = viewPager.getCurrentItem();
        if (currentPage == 0) {
            type = "1";
            DaXiaoFragment fragment = (DaXiaoFragment) fragments.get(0);
            title = fragment.etTitle.getText().toString();
            points = fragment.etScore.getText().toString();

            AppCompatRadioButton rb = (AppCompatRadioButton) findViewById(fragment.radioGroup1.getCheckedRadioButtonId());
            numbertype = rb.getText().toString();
            int checkedId = fragment.radioGroup2.getCheckedRadioButtonId();

            if (checkedId == R.id.rbBig) {
                forecast = "大";
            } else {
                forecast = "小";
            }

        } else if (currentPage == 1) {
            type = "2";
            DanShuangFragment fragment = (DanShuangFragment) fragments.get(1);
            title = fragment.etTitle.getText().toString();
            points = fragment.etScore.getText().toString();
            AppCompatRadioButton rb = (AppCompatRadioButton) findViewById(fragment.radioGroup1.getCheckedRadioButtonId());
            numbertype = rb.getText().toString();

            int checkedId = fragment.radioGroup2.getCheckedRadioButtonId();
            if (checkedId == R.id.rbSingle) {
                forecast = "单";
            } else {
                forecast = "双";
            }
        } else if (currentPage == 2) {
            type = "3";
            ShengXiaoFragment fragment = (ShengXiaoFragment) fragments.get(2);
            title = fragment.etTitle.getText().toString();
            points = fragment.etScore.getText().toString();
            List<Map<String, Object>> maps = fragment.data;
            for (Map<String, Object> map : maps) {
                boolean isChecked = (boolean) map.get("isChecked");
                if (isChecked) {
                    String name = (String) map.get("name");

                    forecast += name + ",";
                }
            }
            if(forecast.endsWith(",")){
                forecast.substring(0,forecast.length()-1);
            }
        } else if (currentPage == 3) {
            type = "4";
            HaoMaFragment fragment = (HaoMaFragment) fragments.get(3);
            title = fragment.etTitle.getText().toString();
            points = fragment.etScore.getText().toString();
            List<Map<String, Object>> maps = fragment.data;
            for (Map<String, Object> map : maps) {
                boolean isChecked = (boolean) map.get("isChecked");
                if (isChecked) {
                    String name = (String) map.get("name");

                    forecast += name + ",";
                }
            }
            if(forecast.endsWith(",")){
                forecast.substring(0,forecast.length()-1);
            }
        }

        if (TextUtils.isEmpty(title)) {
            ToastUtils.show("请填写标题");
            return;
        }
        if (TextUtils.isEmpty(points) || Integer.parseInt(points) > 50 || Integer.parseInt(points) < 1) {
            ToastUtils.show("请填写1-50的心水");
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("type", type);
        map.put("title", title);
        map.put("points", points);
        if ("1".equals(type) || "2".equals(type)) {
            if (TextUtils.isEmpty(numbertype) || TextUtils.isEmpty(forecast)) {
                ToastUtils.show("请选择类型");
                return;
            }

            map.put("numbertype", numbertype);
            map.put("forecast", forecast);
        } else if ("3".equals(type) || "4".equals(type)) {
//            map.put("forecast", forecast);
        }


        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.sendXinShui(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<Object>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<Object>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<Object> response) {
                        if (1 == response.getFlag()) {
                            ToastUtils.show(response.getErrmsg() + "");
                        } else {
                            ToastUtils.show(response.getErrmsg() + "");
                        }
                    }

                });
    }
}
