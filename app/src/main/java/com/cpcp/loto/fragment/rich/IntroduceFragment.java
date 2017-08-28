package com.cpcp.loto.fragment.rich;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cpcp.loto.MApplication;
import com.cpcp.loto.R;
import com.cpcp.loto.activity.PublishXinShuiActivity;
import com.cpcp.loto.activity.SendXinShuiActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.CodeImgEntity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.entity.UserInfoEntity;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.GlideCircleTransform;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

import butterknife.OnClick;

/**
 * 功能描述：
 */

public class IntroduceFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_introduce;
    }

    @Override
    protected void initView() {
    }


    @Override
    public void onLazyLoadData() {

    }

    @OnClick({R.id.introduce_rl})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.introduce_rl:
                //该跳转为ct-Develop分支-代码没问题，但和lcb-develop分支相同，子Fragment实现复杂度略高，比较后保留lcb-develop
//                Intent intent = new Intent(mContext, PublishXinShuiActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(mContext, SendXinShuiActivity.class);
                startActivity(intent);

                break;

        }
    }


}
