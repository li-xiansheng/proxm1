package com.muzikun.lhfsyczl.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.muzikun.lhfsyczl.MApplication;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.AttentionActivity;
import com.muzikun.lhfsyczl.activity.BuyRecordActivity;
import com.muzikun.lhfsyczl.activity.ChangeActivity;
import com.muzikun.lhfsyczl.activity.ChangeRecordActivity;
import com.muzikun.lhfsyczl.activity.FansActivity;
import com.muzikun.lhfsyczl.activity.LoginActivity;
import com.muzikun.lhfsyczl.activity.MyInfoActivity;
import com.muzikun.lhfsyczl.activity.RichActivity;
import com.muzikun.lhfsyczl.activity.SalaryActivity;
import com.muzikun.lhfsyczl.activity.SettingActivity;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseFragment;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.entity.UserInfoEntity;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.uihelper.GlideCircleTransform;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;
import com.muzikun.lhfsyczl.view.SelectedLayerTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：个人中心
 */

public class MeFragment extends BaseFragment {


    @BindView(R.id.tvSetting)
    SelectedLayerTextView tvSetting;
    @BindView(R.id.ivHead)
    AppCompatImageView ivHead;
    @BindView(R.id.tvNickName)
    AppCompatTextView tvNickName;
    @BindView(R.id.tvIntegral)
    AppCompatTextView tvIntegral;
    @BindView(R.id.lilRich)
    LinearLayout lilRich;
    @BindView(R.id.lilChange)
    LinearLayout lilChange;
    @BindView(R.id.relMyInfo)
    RelativeLayout relMyInfo;
    @BindView(R.id.lilSalary)
    LinearLayout lilSalary;
    @BindView(R.id.lilAttention)
    LinearLayout lilAttention;
    @BindView(R.id.lilFans)
    LinearLayout lilFans;
    @BindView(R.id.lilBuyRecord)
    LinearLayout lilBuyRecord;
    @BindView(R.id.lilChangeRecord)
    LinearLayout lilChangeRecord;
    @BindView(R.id.lilLogin)
    LinearLayout lilLogin;
    @BindView(R.id.lilNotLogin)
    LinearLayout lilNotLogin;
    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;


    /**
     * 构造Fragment
     *
     * @return 当前Fragment对象
     */
    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
//        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
//        String tel = sp.getString(UserDB.TEL, "");
//        getUserInfo(tel);
    }

    @Override
    public void onResume() {
        super.onResume();
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin = sp.getBoolean(UserDB.isLogin, false);
        if (isLogin) {
            lilLogin.setVisibility(View.VISIBLE);
            lilNotLogin.setVisibility(View.GONE);
            String tel = sp.getString(UserDB.TEL, "");
            String name = sp.getString(UserDB.NAME, "");
            String score = sp.getString(UserDB.SCORE, "");
            String avatar = sp.getString(UserDB.AVATAR, "");
            tvNickName.setText(name);
            tvIntegral.setText("总积分：" + score);
            if (!TextUtils.isEmpty(avatar)) {
                Glide.with(mContext)
                        .load(avatar)
                        .placeholder(R.drawable.icon_default_head)
                        .transform(new GlideCircleTransform(mContext))
                        .into(ivHead);
            }
            getUserInfo(tel);
        } else {
            lilLogin.setVisibility(View.GONE);
            lilNotLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLazyLoadData() {

    }

    /**
     * 获取用户基本信息
     *
     * @param tel
     */
    private void getUserInfo(String tel) {
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getUserInfo(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<UserInfoEntity>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<UserInfoEntity>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<UserInfoEntity> response) {
                        if (1 == response.getFlag()) {
                            UserInfoEntity entity = response.getData();
                            String id=entity.getId();
                            String nickName = entity.getUser_nicename();
                            String avatar = entity.getAvatar();
                            String score = entity.getScore();
                            nickName = nickName == null ? "" : nickName;
                            if(avatar!=null&&!avatar.startsWith("http")){
                                avatar="http://"+avatar;
                            }
                            score = score == null ? "" : score;

                            Log.i(TAG, "getUserInfo avatar = " + avatar);
                            SPUtil sp = new SPUtil(MApplication.applicationContext, Constants.USER_TABLE);
                            sp.putString(UserDB.ID,id);
                            sp.putString(UserDB.NAME, nickName);
                            sp.putString(UserDB.AVATAR, avatar);
                            sp.putString(UserDB.SCORE, score);

                            tvNickName.setText(nickName);
                            tvIntegral.setText("总积分：" + score);
                            if (!TextUtils.isEmpty(avatar)) {
                                Glide.with(mContext)
                                        .load(avatar)
                                        .placeholder(R.drawable.icon_default_head)
                                        .transform(new GlideCircleTransform(mContext))
                                        .into(ivHead);
                            }
                        } else {
                            ToastUtils.show(response.getErrmsg() + "");
                        }
                    }
                });
    }


    @OnClick({R.id.tvSetting, R.id.lilRich, R.id.lilChange, R.id.relMyInfo, R.id.lilSalary,
            R.id.lilAttention, R.id.lilFans, R.id.lilBuyRecord, R.id.lilChangeRecord,
            R.id.btnLogin})
    public void onViewClicked(View view) {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin = sp.getBoolean(UserDB.isLogin, false);
        if (view.getId() != R.id.tvSetting) {//只要不是设置，都验证是否登录，未登录，则先登录
            if (!isLogin) {
                ((BaseActivity) mActivity).jumpToActivity(LoginActivity.class, false);
                return;
            }

        }
        switch (view.getId()) {
            case R.id.tvSetting:
                ((BaseActivity) mActivity).jumpToActivity(SettingActivity.class, false);
                break;
            case R.id.lilRich:
                ((BaseActivity) mActivity).jumpToActivity(RichActivity.class, false);
                break;
            case R.id.lilChange:
                ((BaseActivity) mActivity).jumpToActivity(ChangeActivity.class, false);
                break;
            case R.id.relMyInfo:
                ((BaseActivity) mActivity).jumpToActivity(MyInfoActivity.class, false);
                break;
            case R.id.lilSalary:

                String nickname = sp.getString(UserDB.NAME, "");
                String avatar = sp.getString(UserDB.AVATAR, "");
                String mobile = sp.getString(UserDB.TEL, "");
                Bundle bundle = new Bundle();
                bundle.putString("nickname", nickname);
                bundle.putString("avatar", avatar);
                bundle.putString("mobile", mobile);
                ((BaseActivity) mActivity).jumpToActivity(SalaryActivity.class, bundle, false);
                break;
            case R.id.lilAttention:
                ((BaseActivity) mActivity).jumpToActivity(AttentionActivity.class, false);
                break;
            case R.id.lilFans:
                ((BaseActivity) mActivity).jumpToActivity(FansActivity.class, false);
                break;
            case R.id.lilBuyRecord:
                ((BaseActivity) mActivity).jumpToActivity(BuyRecordActivity.class, false);
                break;
            case R.id.lilChangeRecord:
                ((BaseActivity) mActivity).jumpToActivity(ChangeRecordActivity.class, false);
                break;
            case R.id.btnLogin:
                ((BaseActivity) mActivity).jumpToActivity(LoginActivity.class, false);
                break;
        }
    }


}
