package com.cpcp.loto.fragment.bet;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.LoginActivity;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.bean.VoteBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.DialogUtil;
import com.cpcp.loto.util.DisplayUtil;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.SPUtil;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：单双投票
 */

public class DanShuangBetFragment extends BaseFragment {
    @BindView(R.id.qishu)
    TextView qishu;
    @BindView(R.id.remen)
    TextView remen;
    @BindView(R.id.rb_red)
    RadioButton rbRed;
    @BindView(R.id.progress_red)
    MagicProgressBar progressRed;
    @BindView(R.id.txt_red)
    TextView txtRed;
    @BindView(R.id.ll_red)
    LinearLayout llRed;
    @BindView(R.id.rb_green)
    RadioButton rbGreen;
    @BindView(R.id.progress_green)
    MagicProgressBar progressGreen;
    @BindView(R.id.txt_green)
    TextView txtGreen;
    @BindView(R.id.ll_green)
    LinearLayout llGreen;
    @BindView(R.id.toupiao)
    RelativeLayout toupiao;

    String choose;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_danshuang_bet;
    }

    @Override
    protected void initView() {
        DisplayUtil.setTouchState(llRed);
        DisplayUtil.setTouchState(llGreen);

        llRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "单";
                rbRed.setChecked(true);
                rbGreen.setChecked(false);

            }
        });

        llGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "双";

                rbRed.setChecked(false);
                rbGreen.setChecked(true);

            }
        });

        toupiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putVote();
            }
        });
    }

    @Override
    public void onLazyLoadData() {
        getVote();
    }

    private void putVote(){

        if (TextUtils.isEmpty(choose)){
            DialogUtil.createDialog(mContext,"您还未选择单双..");
            return;
        }

        LoadingDialog.showDialog(getActivity());
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin=sp.getBoolean(UserDB.isLogin,false);
        if(!isLogin){
            ((BaseActivity)mActivity).jumpToActivity(LoginActivity.class,false);
            return;
        }
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();

        map.put("type","3");
        map.put("username",tel);
        map.put("poll",choose);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.putCaiMinVote(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(getActivity());
                        LogUtils.i(TAG, "getCurrentRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            DialogUtil.createDialog(mContext,"投票成功");
                        }else {
                            DialogUtil.createDialog(mContext,response.getErrmsg());
                        }
                    }
                });
    }

    private void getVote(){
        LoadingDialog.showDialog(getActivity());
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getCaiMinVote()
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(getActivity());
                        LogUtils.i(TAG, "getCurrentRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            if (response.getData() != null){
                                try {
                                    VoteBean voteBean = new VoteBean();
                                    JSONObject object = new JSONObject(response.getData());
                                    JSONObject dataObject = object.optJSONObject("data");
                                    voteBean.qishu = object.getString("shijian");

                                    JSONObject boseObject = dataObject.optJSONObject("danshuang");
                                    voteBean.red = boseObject.getInt("dan");
                                    voteBean.green = boseObject.getInt("shuang");

                                    refreshView(voteBean);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    private void refreshView(VoteBean voteBean){

        txtRed.setText(voteBean.red+"%");
        txtGreen.setText(voteBean.green+"%");

        progressRed.setSmoothPercent((float)voteBean.red/100,500);
        progressGreen.setSmoothPercent((float)voteBean.green/100,500);

        qishu.setText(voteBean.qishu);


        if (voteBean.red > voteBean.green){
            remen.setText("本期热门: 单");
        }

        if (voteBean.green > voteBean.red){
            remen.setText("本期热门: 双");
        }
    }
}
