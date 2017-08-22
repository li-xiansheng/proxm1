package com.cpcp.loto.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.cpcp.loto.util.DialogUtil;
import com.cpcp.loto.util.SPUtil;

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
                            DialogUtil.createDialog(ChangePwdActivity.this,"修改成功");
                            finish();
                        } else {
                            DialogUtil.createDialog(ChangePwdActivity.this,response.getErrmsg());
                        }
                    }

                });

    }
}
