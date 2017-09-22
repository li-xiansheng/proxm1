package com.muzikun.lhfsyczl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.bean.DetailBean;
import com.muzikun.lhfsyczl.bean.HistoryDetailBean;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.uihelper.GlideCircleTransform;
import com.muzikun.lhfsyczl.uihelper.LoadingDialog;
import com.muzikun.lhfsyczl.util.DisplayUtil;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 推荐详情
 */
public class DetailActivity extends BaseActivity {

    @BindView(R.id.head)
    AppCompatImageView head;
    @BindView(R.id.name)
    AppCompatTextView name;
    @BindView(R.id.date)
    AppCompatTextView date;
    @BindView(R.id.number1_iv)
    ImageView number1Iv;
    @BindView(R.id.number1)
    AppCompatTextView number1;
    @BindView(R.id.shengxiao1)
    AppCompatTextView shengxiao1;
    @BindView(R.id.daxiao1)
    AppCompatTextView daxiao1;
    @BindView(R.id.danshuang1)
    AppCompatTextView danshuang1;
    @BindView(R.id.number2_iv)
    ImageView number2Iv;
    @BindView(R.id.number2)
    AppCompatTextView number2;
    @BindView(R.id.shengxiao2)
    AppCompatTextView shengxiao2;
    @BindView(R.id.daxiao2)
    AppCompatTextView daxiao2;
    @BindView(R.id.danshuang2)
    AppCompatTextView danshuang2;
    @BindView(R.id.number3_iv)
    ImageView number3Iv;
    @BindView(R.id.number3)
    AppCompatTextView number3;
    @BindView(R.id.shengxiao3)
    AppCompatTextView shengxiao3;
    @BindView(R.id.daxiao3)
    AppCompatTextView daxiao3;
    @BindView(R.id.danshuang3)
    AppCompatTextView danshuang3;
    @BindView(R.id.number4_iv)
    ImageView number4Iv;
    @BindView(R.id.number4)
    AppCompatTextView number4;
    @BindView(R.id.shengxiao4)
    AppCompatTextView shengxiao4;
    @BindView(R.id.daxiao4)
    AppCompatTextView daxiao4;
    @BindView(R.id.danshuang4)
    AppCompatTextView danshuang4;
    @BindView(R.id.number5_iv)
    ImageView number5Iv;
    @BindView(R.id.number5)
    AppCompatTextView number5;
    @BindView(R.id.shengxiao5)
    AppCompatTextView shengxiao5;
    @BindView(R.id.daxiao5)
    AppCompatTextView daxiao5;
    @BindView(R.id.danshuang5)
    AppCompatTextView danshuang5;
    @BindView(R.id.number6_iv)
    ImageView number6Iv;
    @BindView(R.id.number6)
    AppCompatTextView number6;
    @BindView(R.id.shengxiao6)
    AppCompatTextView shengxiao6;
    @BindView(R.id.daxiao6)
    AppCompatTextView daxiao6;
    @BindView(R.id.danshuang6)
    AppCompatTextView danshuang6;
    @BindView(R.id.number7_iv)
    ImageView number7Iv;
    @BindView(R.id.number7)
    AppCompatTextView number7;
    @BindView(R.id.shengxiao7)
    AppCompatTextView shengxiao7;
    @BindView(R.id.daxiao7)
    AppCompatTextView daxiao7;
    @BindView(R.id.danshuang7)
    AppCompatTextView danshuang7;
    @BindView(R.id.detail_ll)
    LinearLayout detailLl;
    @BindView(R.id.desc)
    AppCompatTextView desc;
    @BindView(R.id.result_ll)
    LinearLayout resultLl;
    @BindView(R.id.kaijiang_time)
    AppCompatTextView kaijiangTime;

    String id;
    String type;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        setTitle("查看详情");

        LogUtils.i(TAG, "type ---->" + type + ",id----->" + id);
        if ("current".equals(type)) {
            detailLl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        if ("current".equals(type)) {
            getCurrentDetailDta();
        } else {
            getHistoryDetailDta();
        }
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            id = bundle.getString("id");
            type = bundle.getString("type");
        }
    }

    private void getCurrentDetailDta() {

        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.seeCurrentRecommend(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                         if (response.getFlag() == 1) {
                            try {
                                JSONObject object = new JSONObject(response.getData());
                                DetailBean bean = new DetailBean();
                                bean.desc = object.optString("desc");
                                JSONArray array = object.getJSONArray("result");
                                for (int i = 0; i < array.length(); i++) {
                                    LogUtils.i(TAG, "getDetailDta ----> result=" + array.getString(i));
                                    bean.result.add(array.getString(i));
                                }
                                bean.avatar = object.getJSONObject("user").getString("avatar");
                                if (bean.avatar != null && !bean.avatar.startsWith("http")) {
                                    bean.avatar = "http://" + bean.avatar;
                                }
                                bean.user_nicename = object.getJSONObject("user").getString("user_nicename");
                                bean.addtime = object.getJSONObject("user").getString("addtime");

                                LogUtils.i(TAG, "getDetailDta ----> bean=" + bean.toString());

                                refreshView(bean);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.getErrmsg() + "");
                        }
                    }
                });
    }

    private void getHistoryDetailDta() {

        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.seeHistoryRecommend(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LogUtils.i(TAG, "getDetailDta ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            parseJson(response.getData());
                        }else{
                            ToastUtils.show(response.getErrmsg()+"");
                        }
                    }
                });
    }

    private void parseJson(String data) {
        try {
            JSONObject object = new JSONObject(data);
            HistoryDetailBean bean = new HistoryDetailBean();
            DetailBean detailBean = new DetailBean();
            detailBean.desc = object.optString("desc");
            JSONArray array = object.getJSONArray("forecast");
            for (int i = 0; i < array.length(); i++) {
                LogUtils.i(TAG, "getDetailDta ----> result=" + array.getString(i));
                detailBean.result.add(array.getString(i));
            }
            detailBean.avatar = object.getJSONObject("user").getString("avatar");
            if (detailBean.avatar != null && !detailBean.avatar.startsWith("http")) {
                detailBean.avatar = "http://" + detailBean.avatar;
            }
            detailBean.user_nicename = object.getJSONObject("user").getString("user_nicename");
            detailBean.addtime = object.getJSONObject("user").getString("addtime");

            bean.detailBean = detailBean;
            bean.kaijiang_time = object.getString("kaijiang_time");
            JSONObject kaiobject = object.getJSONObject("kaijiang");
            bean.haoma.add(kaiobject.getString("号码1"));
            bean.haoma.add(kaiobject.getString("号码2"));
            bean.haoma.add(kaiobject.getString("号码3"));
            bean.haoma.add(kaiobject.getString("号码4"));
            bean.haoma.add(kaiobject.getString("号码5"));
            bean.haoma.add(kaiobject.getString("号码6"));
            bean.haoma.add(kaiobject.getString("特码"));

            JSONArray shengxiaoArray = object.getJSONArray("shengxiao");
            for (int i = 0; i < shengxiaoArray.length(); i++) {
                LogUtils.i(TAG, "getDetailDta ----> result=" + shengxiaoArray.getString(i));
                bean.shengxiao.add(shengxiaoArray.getString(i));
            }

            JSONArray daxiaoArray = object.getJSONArray("daxiao");
            for (int i = 0; i < daxiaoArray.length(); i++) {
                LogUtils.i(TAG, "getDetailDta ----> result=" + daxiaoArray.getString(i));
                bean.daxiao.add(daxiaoArray.getString(i));
            }

            JSONArray danshuangArray = object.getJSONArray("danshuang");
            for (int i = 0; i < danshuangArray.length(); i++) {
                LogUtils.i(TAG, "getDetailDta ----> result=" + danshuangArray.getString(i));
                bean.danshuang.add(danshuangArray.getString(i));
            }

            JSONArray yanseArray = object.getJSONArray("color");
            for (int i = 0; i < yanseArray.length(); i++) {
                LogUtils.i(TAG, "getDetailDta ----> result=" + yanseArray.getString(i));
                bean.yanse.add(yanseArray.getString(i));
            }

            LogUtils.i(TAG, "getDetailDta ----> bean=" + bean.toString());

            refreshHistoryView(bean);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refreshView(DetailBean bean) {
        if (!TextUtils.isEmpty(bean.avatar)) {
            Glide.with(this)
                    .load(bean.avatar)
                    .transform(new GlideCircleTransform(mContext))
                    .into(head);
        } else {
            head.setImageResource(R.drawable.icon_default_head);
        }

        name.setText(bean.user_nicename);
        date.setText(bean.addtime);
        desc.setText(bean.desc);

        for (int i = 0; i < bean.result.size(); i++) {
            AppCompatTextView textView = new AppCompatTextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.width = (int) DisplayUtil.getDensity(this) * 40;
            lp.height = (int) DisplayUtil.getDensity(this) * 40;//lp.height=LayoutParams.WRAP_CONTENT;
            textView.setLayoutParams(lp);
            textView.setText(bean.result.get(i));
            textView.setTextColor(getResources().getColor(R.color.grayText));
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.aa_fans_bg_cir);
            resultLl.addView(textView);
        }

    }

    private void refreshHistoryView(HistoryDetailBean bean) {
        refreshView(bean.detailBean);

        kaijiangTime.setText(bean.kaijiang_time);
        for (int i = 0; i < bean.haoma.size(); i++) {
            if (i == 0) {
                number1.setText(bean.haoma.get(i));
                shengxiao1.setText(bean.shengxiao.get(i));
                daxiao1.setText(bean.daxiao.get(i));
                danshuang1.setText(bean.danshuang.get(i));
                setBackground(bean.yanse.get(i), number1Iv);
            } else if (i == 1) {
                number2.setText(bean.haoma.get(i));
                shengxiao2.setText(bean.shengxiao.get(i));
                daxiao2.setText(bean.daxiao.get(i));
                danshuang2.setText(bean.danshuang.get(i));
                setBackground(bean.yanse.get(i), number2Iv);
            } else if (i == 2) {
                number3.setText(bean.haoma.get(i));
                shengxiao3.setText(bean.shengxiao.get(i));
                daxiao3.setText(bean.daxiao.get(i));
                danshuang3.setText(bean.danshuang.get(i));
                setBackground(bean.yanse.get(i), number3Iv);
            } else if (i == 3) {
                number4.setText(bean.haoma.get(i));
                shengxiao4.setText(bean.shengxiao.get(i));
                daxiao4.setText(bean.daxiao.get(i));
                danshuang4.setText(bean.danshuang.get(i));
                setBackground(bean.yanse.get(i), number4Iv);
            } else if (i == 4) {
                number5.setText(bean.haoma.get(i));
                shengxiao5.setText(bean.shengxiao.get(i));
                daxiao5.setText(bean.daxiao.get(i));
                danshuang5.setText(bean.danshuang.get(i));
                setBackground(bean.yanse.get(i), number5Iv);
            } else if (i == 5) {
                number6.setText(bean.haoma.get(i));
                shengxiao6.setText(bean.shengxiao.get(i));
                daxiao6.setText(bean.daxiao.get(i));
                danshuang6.setText(bean.danshuang.get(i));
                setBackground(bean.yanse.get(i), number6Iv);
            } else if (i == 6) {
                number7.setText(bean.haoma.get(i));
                shengxiao7.setText(bean.shengxiao.get(i));
                daxiao7.setText(bean.daxiao.get(i));
                danshuang7.setText(bean.danshuang.get(i));
                setBackground(bean.yanse.get(i), number7Iv);
            }

        }

    }

    private void setBackground(String color, ImageView iv) {
        if ("lan".equals(color)) {
            iv.setImageResource(R.drawable.fans_blue);
        } else if ("lv".equals(color)) {
            iv.setImageResource(R.drawable.fans_green);
        } else {
            iv.setImageResource(R.drawable.fans_red);
        }
    }
}
