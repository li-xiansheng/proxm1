package com.cpcp.loto.fragment;

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
import com.cpcp.loto.MApplication;
import com.cpcp.loto.R;
import com.cpcp.loto.activity.AttentionActivity;
import com.cpcp.loto.activity.BuyRecordActivity;
import com.cpcp.loto.activity.ChangeActivity;
import com.cpcp.loto.activity.ChangeRecordActivity;
import com.cpcp.loto.activity.FansActivity;
import com.cpcp.loto.activity.LoginActivity;
import com.cpcp.loto.activity.MyInfoActivity;
import com.cpcp.loto.activity.RichActivity;
import com.cpcp.loto.activity.SalaryActivity;
import com.cpcp.loto.activity.SettingActivity;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.entity.UserInfoEntity;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.GlideCircleTransform;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.SelectedLayerTextView;

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
            String score=sp.getString(UserDB.SCORE,"");
            String avatar=sp.getString(UserDB.AVATAR,"");
            tvNickName.setText(name);
            tvIntegral.setText("总积分："+score);
            if(!TextUtils.isEmpty(avatar)){
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
                            String nickName = entity.getUser_nicename();
                            String avatar = entity.getAvatar();
                            String score = entity.getScore();
                            nickName = nickName == null ? "" : nickName;
                            avatar = avatar == null ? "" : "http://"+avatar;
                            score = score == null ? "" : score;

                            Log.i(TAG,"getUserInfo avatar = " +avatar);
                            SPUtil sp = new SPUtil(MApplication.applicationContext, Constants.USER_TABLE);
                            sp.putString(UserDB.NAME, nickName);
                            sp.putString(UserDB.AVATAR, avatar);
                            sp.putString(UserDB.SCORE, score);

                            tvNickName.setText(nickName);
                            tvIntegral.setText("总积分："+score);
                            if(!TextUtils.isEmpty(avatar)){
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
                SPUtil sp = new SPUtil(MApplication.applicationContext, Constants.USER_TABLE);
                String nickname = sp.getString(UserDB.NAME, "");
                String avatar = sp.getString(UserDB.AVATAR, "");
                String mobile = sp.getString(UserDB.TEL, "");
                Bundle bundle = new Bundle();
                bundle.putString("nickname",nickname);
                bundle.putString("avatar",avatar);
                bundle.putString("mobile",mobile);
                ((BaseActivity) mActivity).jumpToActivity(SalaryActivity.class,bundle,false);
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
