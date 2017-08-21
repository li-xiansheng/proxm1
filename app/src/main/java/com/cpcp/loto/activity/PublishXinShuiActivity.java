package com.cpcp.loto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabFragmentAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.fragment.publish.DaxiaoFragment;
import com.cpcp.loto.fragment.publish.HaomaFragment;
import com.cpcp.loto.fragment.publish.ShengXiaoFragment;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.DialogUtil;
import com.cpcp.loto.util.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PublishXinShuiActivity extends BaseActivity {


    List<BaseFragment> fragments = new ArrayList<>();
    @BindView(R.id.publish_tablayout)
    TabLayout publishTablayout;
    @BindView(R.id.publish_viewpager)
    ViewPager publishViewpager;
    DaxiaoFragment daxiaoFragment;
    DaxiaoFragment danshuangFragment;
    ShengXiaoFragment shengXiaoFragment;
    HaomaFragment haomaFragment;

    String title;
    String points;
    int type;
    String numbertype;
    String forecast;
//    String forecast2[];

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_publish_xin_shui;
    }

    @Override
    protected void initView() {

        setTitle("发布心水");
        setTopRightButton("发布", new OnMenuClickListener() {
            @Override
            public void onClick() {
                if (publishTablayout.getSelectedTabPosition() == 0) {
                    type = 1;
                    title = daxiaoFragment.getTitle();
                    points = daxiaoFragment.getPoints();
                    numbertype = daxiaoFragment.getChooseRec();
                    forecast = daxiaoFragment.getChooseCir();
                } else if (publishTablayout.getSelectedTabPosition() == 1) {
                    type = 2;
                    title = danshuangFragment.getTitle();
                    points = danshuangFragment.getPoints();
                    numbertype = danshuangFragment.getChooseRec();
                    forecast = danshuangFragment.getChooseCir();
                } else if (publishTablayout.getSelectedTabPosition() == 2) {
                    type = 3;
                    title = shengXiaoFragment.getTitle();
                    points = shengXiaoFragment.getPoints();
                    forecast = shengXiaoFragment.getChooseCir();
                } else if (publishTablayout.getSelectedTabPosition() == 3) {
                    type = 4;
                    title = haomaFragment.getTitle();
                    points = haomaFragment.getPoints();
                    forecast = haomaFragment.getChooseCir();
                }
                publishXinshui();
            }
        });

        String[] titles = {"大小", "单双", "生肖", "号码"};

        daxiaoFragment = new DaxiaoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", "大小");
        daxiaoFragment.setArguments(bundle);
        fragments.add(daxiaoFragment);

        danshuangFragment = new DaxiaoFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("type", "单双");
        danshuangFragment.setArguments(bundle2);//上个版本这里有个BUG，没注意，现在修改过来了
        fragments.add(danshuangFragment);

        fragments.add(shengXiaoFragment = new ShengXiaoFragment());
        fragments.add(haomaFragment = new HaomaFragment());

        publishViewpager.setAdapter(new TabFragmentAdapter(fragments, titles, getSupportFragmentManager(), this));
//        richTablayout.setupWithViewPager(richViewpager);
        publishTablayout.setupWithViewPager(publishViewpager, false);

    }

    @Override
    protected void initData() {

    }

    private void publishXinshui() {

       if (check()){
           LoadingDialog.showDialog(this);
           SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
           String tel = sp.getString(UserDB.TEL, "");
           Map<String, String> map = new HashMap<>();
           map.put("username", tel);

           map.put("type", type + "");
           map.put("title", title);
           map.put("points", points);
           if (type == 1 || type == 2) {
               map.put("numbertype", numbertype);
           }
           map.put("forecast", forecast);


           HttpService httpService = HttpRequest.provideClientApi();
           mSubscription = httpService.publicXinShui(map)
                   .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                   .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                       @Override
                       public Activity getCurrentActivity() {
                           return mActivity;
                       }

                       @Override
                       public void _onNext(int status, BaseResponse2Entity<String> response) {
                           LoadingDialog.closeDialog(PublishXinShuiActivity.this);
                           if (response.getFlag() == 1) {
                               DialogUtil.createDialog(PublishXinShuiActivity.this, "发布成功");
                           } else {
                               DialogUtil.createDialog(PublishXinShuiActivity.this, "发布失败",response.getErrmsg());
                           }
                       }

                   });
       }


    }

    private boolean check(){
        if (TextUtils.isEmpty(title)) {
            DialogUtil.createDialog(this, "标题不能为空");
            return false;
        }
        if (TextUtils.isEmpty(points)) {
            DialogUtil.createDialog(this, "积分不能为空");
            return false;
        }
        if (TextUtils.isEmpty(forecast)) {
            if (type == 1) {
                DialogUtil.createDialog(this, "请选择大小");
                return false;
            } else if (type == 2) {
                DialogUtil.createDialog(this, "请选择单双");
                return false;
            } else if (type == 3) {
                DialogUtil.createDialog(this, "请选择生肖");
                return false;
            } else if (type == 4) {
                DialogUtil.createDialog(this, "请选择号码");
                return false;
            }
        }

        if ((type == 1 || type == 2) && TextUtils.isEmpty(numbertype)) {
            DialogUtil.createDialog(this, "请选择正码");
            return false;
        }
        if (Integer.parseInt(points) > 50 || Integer.parseInt(points) < 0) {
            DialogUtil.createDialog(this, "积分应在0~50之间");
            return false;
        }

        return true;
    }
}
