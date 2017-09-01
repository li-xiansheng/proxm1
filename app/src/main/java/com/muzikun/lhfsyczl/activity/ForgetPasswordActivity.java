package com.muzikun.lhfsyczl.activity;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.entity.BaseResponse1Entity;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.CheckUtils;
import com.muzikun.lhfsyczl.util.ToastUtils;
import com.muzikun.lhfsyczl.view.SelectedLayerTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.etTel)
    AppCompatEditText etTel;
    @BindView(R.id.etCode)
    AppCompatEditText etCode;
    @BindView(R.id.tvCode)
    SelectedLayerTextView tvCode;
    @BindView(R.id.etPwd)
    AppCompatEditText etPwd;
    @BindView(R.id.etPwdConfirm)
    AppCompatEditText etPwdConfirm;
    @BindView(R.id.relSubmit)
    RelativeLayout relSubmit;


    //时间
    private Timer mTimer;
    private int mCount;
    private Handler handler = new Handler();

    //
    private String tel;
    private String code;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        setTitle("忘记密码");
    }

    @Override
    protected void initData() {

    }

    /**
     * 忘记密码
     */
    private void forgetPwd() {
        String pwd = etPwd.getText().toString();
        String confirmPwd = etPwdConfirm.getText().toString();

        if (!CheckUtils.isMobileNO(tel)) {
            etTel.setError("请检查输入11手机号码");
            return;
        }

        if (TextUtils.isEmpty(code) || !code.equals(etCode.getText().toString())) {
            etCode.setError("请校验正确的短信验证码");
            return;
        }
        if (TextUtils.isEmpty(pwd) || pwd.length() < 8) {
            etPwd.setError("请输入8位数以上的密码");
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            etPwdConfirm.setError("请检查密码，两次输入的密码不一致");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("mobile", tel);
        map.put("mobile_code", code);
        map.put("password", pwd);

        HttpService httpService = HttpRequest.provideClientApi();
        httpService.forgetPwd(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        if (response.getFlag() == 1) {
                            ToastUtils.show(response.getErrmsg() + "");
                            finish();
                        } else {
                            ToastUtils.show(response.getErrmsg() + "");
                        }
                    }
                });

    }


    @OnClick({R.id.tvCode, R.id.relSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCode:
                sendSMS();
                break;
            case R.id.relSubmit:
                forgetPwd();
                break;
        }

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
                            code = response.getData();
                            setTimerAgainSendCode();
                        } else if ("失败".equals(response)) {
                            ToastUtils.show("短信发送失败，请勿频繁操作");
                        }
                    }
                });

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

}
