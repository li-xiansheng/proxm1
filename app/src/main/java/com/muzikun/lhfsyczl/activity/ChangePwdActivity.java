package com.muzikun.lhfsyczl.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.DialogUtil;
import com.muzikun.lhfsyczl.util.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：修改密码
 */

public class ChangePwdActivity extends BaseActivity {
    @BindView(R.id.old_pass)
    EditText oldPass;
    @BindView(R.id.new_pass)
    EditText newPass;
    @BindView(R.id.re_pass)
    EditText rePass;
    @BindView(R.id.toupiao)
    RelativeLayout toupiao;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView() {
        setTitle("修改密码");
    }

    @Override
    protected void initData() {

        toupiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()){
                    changePassword();
                }
            }
        });

    }

    private boolean check() {
        if (TextUtils.isEmpty(oldPass.getText())){
            DialogUtil.createDialog(this,"旧密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(newPass.getText())){
            DialogUtil.createDialog(this,"新密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(rePass.getText())){
            DialogUtil.createDialog(this,"确认密码不能为空");
            return false;
        }
        if (!newPass.getText().toString().equals(rePass.getText().toString())){
            DialogUtil.createDialog(this,"两次输入密码不一致");
            return false;
        }

        return true;
    }

    public void changePassword() {

        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("oldpassword", oldPass.getText().toString());
        map.put("newpassword", newPass.getText().toString());
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.changePassword(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        if (response.getFlag() == 1) {
                            Log.i(TAG, "getAttentionData ---->" + response.getData());
                            SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
                            sp.putString(UserDB.PWD,newPass.getText().toString());
                            DialogUtil.createDialog(ChangePwdActivity.this,"修改成功");
                            finish();
                        } else {
                            DialogUtil.createDialog(ChangePwdActivity.this,response.getErrmsg());
                        }
                    }

                });

    }
}
