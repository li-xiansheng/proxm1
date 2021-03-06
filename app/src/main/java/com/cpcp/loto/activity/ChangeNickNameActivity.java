package com.cpcp.loto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：修改昵称
 */

public class ChangeNickNameActivity extends BaseActivity {
    @BindView(R.id.etNickName)
    AppCompatEditText etNickName;
    @BindView(R.id.relSubmit)
    RelativeLayout relSubmit;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_nick_name;
    }

    @Override
    protected void initView() {
        setTitle("修改昵称");

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.relSubmit)
    public void onViewClicked() {
        changeNickName();
    }

    private void changeNickName() {
        final String nickName = etNickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            ToastUtils.show("请填写新的昵称");
            return;
        }
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("nickname", nickName);
        HttpService httpservice = HttpRequest.provideClientApi();
        httpservice.changeNickName(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<Object>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<Object>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<Object> response) {
                        ToastUtils.show(response.getErrmsg() + "");
                        if (response.getFlag() == 1) {
                            SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
                            sp.putString(UserDB.NAME, nickName);
                            setResult(MyInfoActivity.REQUEST_CODE_NICKNAME);
                            finish();
                        }
                    }
                });

    }
}
