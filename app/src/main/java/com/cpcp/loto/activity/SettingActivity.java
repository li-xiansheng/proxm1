package com.cpcp.loto.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse1Entity;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.DisplayUtil;
import com.cpcp.loto.util.ImageUtil;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：设置页面
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tuisong)
    ImageView tuisong;
    @BindView(R.id.clear_crash)
    LinearLayout clearCrash;
    @BindView(R.id.mianze)
    LinearLayout mianze;
    @BindView(R.id.fankui)
    LinearLayout fankui;
    @BindView(R.id.haoping)
    LinearLayout haoping;

    SPUtil sp;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setTitle("设置");

        sp = new SPUtil(mContext, Constants.USER_TABLE);
        if (sp.getBoolean("isTipMsg", true)) {
            tuisong.setImageResource(R.drawable.dark_switch_on_3x);
        } else {
            tuisong.setImageResource(R.drawable.dark_switch_off_3x);
        }

        DisplayUtil.setTouchState(clearCrash);
        DisplayUtil.setTouchState(mianze);
        DisplayUtil.setTouchState(fankui);
        DisplayUtil.setTouchState(haoping);
    }

    @Override
    protected void initListener() {
        super.initListener();

        //接受消息通知
        tuisong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean State = sp.getBoolean("isTipMsg", false);
                if (State) {
                    sp.putBoolean("isTipMsg", false);
                    tuisong.setImageResource(R.drawable.dark_switch_off_3x);
                } else {
                    sp.putBoolean("isTipMsg", true);
                    tuisong.setImageResource(R.drawable.dark_switch_on_3x);
                }
            }
        });

        clearCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtil.clearImageDiskCache(SettingActivity.this);
                ImageUtil.clearImageMemoryCache(SettingActivity.this);
                ImageUtil.cleanCatchDisk(SettingActivity.this);
                ToastUtils.show("清理完成");
            }
        });

        mianze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getArtical();
            }
        });

        fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(FankuiActivity.class, false);
            }
        });

        haoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mActivity)
                        .title("评分")
                        .backgroundColorRes(R.color.white)
                        .customView(R.layout.dialog_pingfen, true)
                        .positiveText("提交")
                        .positiveColorRes(R.color.colorDeepOrangePrimary)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                ToastUtils.show("提交成功");
                            }
                        })
                        .show();
            }
        });


    }

    @Override
    protected void initData() {

    }

    private void getArtical() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "15");
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getArticle(map)
                .compose(RxSchedulersHelper.<BaseResponse1Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse1Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse1Entity<String> response) {
                        if ("成功".equals(response.getResult())) {
                            try {
                                JSONObject object = new JSONObject(response.getData());
                                Intent intent = new Intent(SettingActivity.this, MianzeActivity.class);
                                intent.putExtra("str", object.getString("post_content"));
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            ToastUtils.show(response.getResult() + "");
                        }
                    }
                });
    }


}
