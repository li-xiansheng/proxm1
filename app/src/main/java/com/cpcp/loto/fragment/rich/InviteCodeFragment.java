package com.cpcp.loto.fragment.rich;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cpcp.loto.MApplication;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.CodeImgEntity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 功能描述：分享邀请码
 */

public class InviteCodeFragment extends BaseFragment {
    @BindView(R.id.ivCodeImg)
    AppCompatImageView ivCodeImg;
    @BindView(R.id.tvInviteCode)
    AppCompatTextView tvInviteCode;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_invite_code;
    }

    @Override
    protected void initView() {
        SPUtil sp = new SPUtil(MApplication.applicationContext, Constants.USER_TABLE);
        String id = sp.getString(UserDB.ID, "");
        tvInviteCode.append(id);
        getCodeImg();
    }

    @Override
    public void onLazyLoadData() {

    }

    /**
     * 获取二维码下载图
     */
    private void getCodeImg() {
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getCodeImg()
                .compose(RxSchedulersHelper.<BaseResponse2Entity<CodeImgEntity>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<CodeImgEntity>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<CodeImgEntity> response) {
                        if (1 == response.getFlag()) {
                            CodeImgEntity entity = response.getData();
                            if (entity != null) {
                                String url = entity.getUrl();
                                Glide.with(mContext)
                                        .load("http://" + url)
                                        .into(ivCodeImg);
                            }
                        } else {
                            ToastUtils.show(response.getErrmsg() + "");
                        }
                    }
                });

    }


}

