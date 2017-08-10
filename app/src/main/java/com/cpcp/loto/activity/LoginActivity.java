package com.cpcp.loto.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.cpcp.loto.MApplication;
import com.cpcp.loto.MainActivity;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse1Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.CheckUtils;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.SelectedLayerTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：登录
 *
 * login类中保存有SharePrefrence信息
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.etTel)
    AppCompatEditText etTel;
    @BindView(R.id.etPwd)
    AppCompatEditText etPwd;

    @BindView(R.id.tvLogin)
    SelectedLayerTextView tvLogin;
    @BindView(R.id.tvRegister)
    SelectedLayerTextView tvRegister;
    @BindView(R.id.tvForgetPwd)
    SelectedLayerTextView tvForgetPwd;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTitle("登录");
        SPUtil sp=new SPUtil(mContext,Constants.USER_TABLE);
        String tel=sp.getString(UserDB.TEL,"");
        String pwd=sp.getString(UserDB.PWD,"");
        if(!TextUtils.isEmpty(tel)){
            etTel.setText(tel);
        }
        if(!TextUtils.isEmpty(pwd)){
            etPwd.setText(pwd);
        }
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tvLogin, R.id.tvRegister, R.id.tvForgetPwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                login();
                break;
            case R.id.tvRegister:
                jumpToActivity(RegisterActivity.class, false);
                break;
            case R.id.tvForgetPwd:
                jumpToActivity(ForgetPasswordActivity.class, false);
                break;
        }
    }


    /**
     * 登录
     */
    private void login() {
        final String tel = etTel.getText().toString().replace(" ", "");
        final String pwd = etPwd.getText().toString().replace(" ", "");
        if (!CheckUtils.isMobileNO(tel)) {
            etTel.setError("请检查输入11手机号码");
            return;
        }
        if (TextUtils.isEmpty(pwd) || pwd.length() < 8) {
            etPwd.setError("请输入8位数以上的密码");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("password", pwd);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.login(map)
                .compose(RxSchedulersHelper.<BaseResponse1Entity<Object>>io_main())
                .subscribe(new RxSubscriber<BaseResponse1Entity<Object>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse1Entity<Object> response) {
                        if ("成功".equals(response.getResult())) {
                            ToastUtils.show("登录成功");
                            SPUtil sp = new SPUtil(MApplication.applicationContext, Constants.USER_TABLE);
                            sp.putString(UserDB.TEL, tel);
                            sp.putString(UserDB.PWD, pwd);
                            sp.putBoolean(UserDB.isLogin,true);

//                            Intent intent = new Intent(mContext, MainActivity.class);
//                            intent.putExtra("isLogin", true);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
                            LoadingDialog.closeDialog(mActivity);
                            finish();
                        } else {
                            ToastUtils.show(response.getData() + "\n请检查账号密码");
                        }
                    }

                });

    }
}
