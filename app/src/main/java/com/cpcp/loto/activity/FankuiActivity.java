package com.cpcp.loto.activity;

import android.app.Activity;
import android.text.TextUtils;
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
 * 意见反馈
 */
public class FankuiActivity extends BaseActivity {

    @BindView(R.id.fankui)
    EditText fankui;
    @BindView(R.id.submit)
    RelativeLayout submit;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fankui;
    }

    @Override
    protected void initView() {

        setTitle("意见反馈");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(fankui.getText().toString())){
                    DialogUtil.createDialog(mActivity,"请输入反馈内容");
                }else {
                    fanKui();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void fanKui(){
        Map<String, String> map = new HashMap<>();
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        map.put("username", tel);
        map.put("fankui", fankui.getText().toString());
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.fanKui(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        if (1 == response.getFlag()) {
                            fankui.setText("");
                            DialogUtil.createDialog(mActivity,"反馈成功");
                        } else {
                            DialogUtil.createDialog(mActivity,"反馈失败");
                        }
                    }
                });
    }

}
