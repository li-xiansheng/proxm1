package com.muzikun.lhfsyczl.fragment.rich;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.muzikun.lhfsyczl.MApplication;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseFragment;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.CodeImgEntity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;

import butterknife.BindView;

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

