package com.muzikun.lhfsyczl.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.muzikun.lhfsyczl.MApplication;
import com.muzikun.lhfsyczl.MainActivity;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse1Entity;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.uihelper.LoadingDialog;
import com.muzikun.lhfsyczl.util.CheckUtils;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;
import com.muzikun.lhfsyczl.view.SelectedLayerTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：注册
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.etTel)
    AppCompatEditText etTel;
    @BindView(R.id.etNickName)
    AppCompatEditText etNickName;
    @BindView(R.id.tvCode)
    SelectedLayerTextView tvCode;
    @BindView(R.id.etCode)
    AppCompatEditText etCode;
    @BindView(R.id.etPwd)
    AppCompatEditText etPwd;

    @BindView(R.id.tvRegister)
    SelectedLayerTextView tvRegister;
    @BindView(R.id.etInviteNum)
    AppCompatEditText etInviteNum;
    @BindView(R.id.etQQNum)
    AppCompatEditText etQQNum;

    //时间
    private Timer mTimer;
    private int mCount;
    private Handler handler = new Handler();

    //
    private String tel;
    private String code;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register_one;
    }

    @Override
    protected void initView() {
        setTitle("注册");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tvCode, R.id.tvRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCode:
                sendSMS();
                break;
            case R.id.tvRegister:
                register();
                break;
        }
    }

    /**
     * 设置倒计时-再次发送验证吗
     */
    private void setTimerAgainSendCode() {
        mCount = 60;
        mTimer = new Timer();
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mCount--;
                if (mCount == 0) {
                    mTimer.cancel();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCount != 0) {
                            tvCode.setText(mCount + "s后重新获取");
                            tvCode.setClickable(false);
                        } else {
                            tvCode.setText("获取验证码");
                            tvCode.setClickable(true);
                        }

                    }
                });
            }
        };
        //开始一个定时任务
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /**
     * 发送验证码
     */
    private void sendSMS() {

        tel = etTel.getText().toString().replace(" ", "");
        if (!CheckUtils.isMobileNO(tel)) {
            ToastUtils.show("请输入正确的手机号码");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("mobile", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.sendSMS(map)
                .compose(RxSchedulersHelper.<BaseResponse1Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse1Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse1Entity<String> response) {
                        if ("成功".equals(response.getResult())) {
                            ToastUtils.show("短信发送成功，请填写短信");
                            code=   response.getData();
                            setTimerAgainSendCode();
                        } else if ("失败".equals(response)) {
                            ToastUtils.show("短信发送失败，请勿频繁操作");
                        }
                    }
                });

    }

    /**
     * 注册用户
     */
    private void register() {
        final String pwd = etPwd.getText().toString().replace(" ", "");
        final String name = etNickName.getText().toString().replace(" ", "");
        String inviteNum=etInviteNum.getText().toString();
        String qqNum=etQQNum.getText().toString().replace(" ","");

        if (!CheckUtils.isMobileNO(tel)) {
            etTel.setError("请检查输入11手机号码");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            etCode.setError("请填写用户昵称");
            return;
        }
        if (TextUtils.isEmpty(code) || !code.equals(etCode.getText().toString())) {
            etCode.setError("请校验正确的短信验证码");
            return;
        }
        if (TextUtils.isEmpty(pwd) || pwd.length() <8) {
            etPwd.setError("请输入8位数以上的密码");
            return;
        }
        if (TextUtils.isEmpty(qqNum) || qqNum.length() < 6) {
            etQQNum.setError("请输入QQ号");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("mobile", tel);
        map.put("password", pwd);
        map.put("mobile_code", code);
        map.put("user_nicename", name);
        map.put("qq", name);
        if(!TextUtils.isEmpty(inviteNum)){
            map.put("invite_id", inviteNum);

        }

        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.register(map)
                .compose(RxSchedulersHelper.<BaseResponse1Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse1Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse1Entity<String> response) {

                        if ("成功".equals(response.getResult())) {
                            ToastUtils.show("注册成功");
                            SPUtil sp = new SPUtil(MApplication.applicationContext, Constants.USER_TABLE);
                            sp.putString("tel", tel);
                            sp.putString("pwd", pwd);
                            sp.putString("nick_name", name);
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.putExtra("isLogin", true);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            LoadingDialog.closeDialog(mActivity);
                            finish();

                        } else {
                            ToastUtils.show((String) response.getData() + "\n检查填写参数或账户已存在");
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
