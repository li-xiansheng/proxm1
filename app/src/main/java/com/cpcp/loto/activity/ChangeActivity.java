package com.cpcp.loto.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.bean.PingtaiBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.DialogUtil;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ScreenUtil;
import com.cpcp.loto.view.EasyPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：转换页
 */

public class ChangeActivity extends BaseActivity {

    @BindView(R.id.trans_account)
    EditText transAccount;
    @BindView(R.id.trans_jifen)
    EditText transJifen;
    @BindView(R.id.total_points)
    TextView totalPoints;
    @BindView(R.id.pingtai_txt)
    TextView pingtaiTxt;
    @BindView(R.id.pingtai_rl)
    RelativeLayout pingtaiRl;
    @BindView(R.id.trans_login)
    TextView transLogin;
    @BindView(R.id.trans_register)
    TextView transRegister;
    @BindView(R.id.submit_rl)
    RelativeLayout submitRl;

    String score;

    private Dialog seletorDialog;
    EasyPickerView easyPickerView;
    ArrayList<PingtaiBean> pingtaiList = new ArrayList<>();
    ArrayList<String> stringList = new ArrayList<>();

    int pingtaiChoose;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change;
    }

    @Override
    protected void initView() {
        setTitle("转换");

//        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
//        score = sp.getString(UserDB.SCORE, "");

//        if (!"".equals(score)) {
//
//        }


        pingtaiRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seletorDialog.show();
            }
        });

        submitRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    submitChange();
                }
            }
        });

        transLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("请选择".equals(pingtaiTxt.getText().toString())) {
                    DialogUtil.createDialog(ChangeActivity.this, "请选择平台");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", pingtaiList.get(pingtaiChoose).pingtainame);
                    bundle.putString("url", pingtaiList.get(pingtaiChoose).url);
                    ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                }
            }
        });

        transRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("请选择".equals(pingtaiTxt.getText().toString())) {
                    DialogUtil.createDialog(ChangeActivity.this, "请选择平台");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", pingtaiList.get(pingtaiChoose).pingtainame);
                    bundle.putString("url", pingtaiList.get(pingtaiChoose).url);
                    ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                }
            }
        });

        getPingTaiInfo();
    }

    @Override
    protected void initData() {

    }

    private boolean check() {
        if (TextUtils.isEmpty(pingtaiTxt.getText().toString())) {
            DialogUtil.createDialog(this, "平台不能为空");
            return false;
        }

        if (TextUtils.isEmpty(transAccount.getText().toString())) {
            DialogUtil.createDialog(this, "平台账号不能为空");
            return false;
        }

        if (TextUtils.isEmpty(transJifen.getText().toString())) {
            DialogUtil.createDialog(this, "转换积分不能为空");
            return false;
        }

        if (Integer.parseInt(transJifen.getText().toString()) > Integer.parseInt(score)) {
            DialogUtil.createDialog(this, "转换积分大于总积分");
            return false;
        }

        if (Integer.parseInt(score) < 2000) {
            DialogUtil.createDialog(this, "总积分达到2000才能进行兑换");
            return false;
        }

        return true;
    }

    private void initDialog() {

        totalPoints.setText(" 总积分:" + score);
        if (Integer.parseInt(score) < 2000) {
            DialogUtil.createDialog(this, "总积分达到2000才能进行兑换");
        }

        if (seletorDialog == null) {
            seletorDialog = new Dialog(this, R.style.time_dialog);
            seletorDialog.setCancelable(true);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.pickview_selector);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(this).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }

        easyPickerView = (EasyPickerView) seletorDialog.findViewById(R.id.easyPickerView);
        easyPickerView.setRecycleMode(false);
        easyPickerView.setDataList(stringList);
        TextView cancel = (TextView) seletorDialog.findViewById(R.id.tv_cancle);
        TextView sure = (TextView) seletorDialog.findViewById(R.id.tv_select);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seletorDialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pingtaiChoose = easyPickerView.getCurIndex();
                pingtaiTxt.setTextColor(getResources().getColor(R.color.black));
                pingtaiTxt.setText(stringList.get(easyPickerView.getCurIndex()));
                seletorDialog.dismiss();
            }
        });
    }

    /**
     * 提交转换
     */
    private void submitChange() {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        map.put("pingtainame", pingtaiTxt.getText().toString());
        map.put("pingtaiaccount", transAccount.getText().toString());
        map.put("points", transJifen.getText().toString());
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.publicXinShui(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        if (response.getFlag() == 1) {
                            DialogUtil.createDialog(ChangeActivity.this, "申请成功");
                        } else {
                            DialogUtil.createDialog(ChangeActivity.this, "申请失败", response.getErrmsg());
                        }
                    }

                });
    }

    /**
     * 获取平台等信息
     */
    private void getPingTaiInfo() {
        LoadingDialog.showDialog(this);
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getPingTaiInfo(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(ChangeActivity.this);
                        if (response.getFlag() == 1) {
                            try {
                                JSONObject object = new JSONObject(response.getData());
                                JSONArray array = object.getJSONArray("pingtai");
                                score = object.getString("score");
                                Gson gson = new Gson();
                                for (int i = 0; i < array.length(); i++) {
                                    PingtaiBean bean = gson.fromJson(array.get(i).toString(), PingtaiBean.class);
                                    stringList.add(bean.pingtainame);
                                    pingtaiList.add(bean);
                                }

                                initDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            DialogUtil.createDialog(ChangeActivity.this, "申请失败", response.getErrmsg());
                        }
                    }

                });
    }
}
