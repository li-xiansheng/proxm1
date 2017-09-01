package com.muzikun.lhfsyczl.fragment.bet;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.LoginActivity;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseFragment;
import com.muzikun.lhfsyczl.bean.VoteBean;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.uihelper.LoadingDialog;
import com.muzikun.lhfsyczl.util.DialogUtil;
import com.muzikun.lhfsyczl.util.DisplayUtil;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：大小投注
 */

public class DaXiaoBetFragment extends BaseFragment {
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
        return R.layout.fragment_daxiao_bet;
    }

    @Override
    protected void initView() {
        DisplayUtil.setTouchState(llRed);
        DisplayUtil.setTouchState(llGreen);

        llRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "大";
                rbRed.setChecked(true);
                rbGreen.setChecked(false);

            }
        });

        llGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "小";

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
            DialogUtil.createDialog(mContext,"您还未选择大小..");
            return;
        }

        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin=sp.getBoolean(UserDB.isLogin,false);
        if(!isLogin){
            ((BaseActivity)mActivity).jumpToActivity(LoginActivity.class,false);
            return;
        }
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();

        map.put("type","2");
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

                        LogUtils.i(TAG, "getCurrentRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            DialogUtil.createDialog(mContext,"投票成功");
                            getVote();
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

                                    JSONObject boseObject = dataObject.optJSONObject("daxiao");
                                    voteBean.red = boseObject.getInt("da");
                                    voteBean.green = boseObject.getInt("xiao");

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
            remen.setText("本期热门: 大");
        }

        if (voteBean.green > voteBean.red){
            remen.setText("本期热门: 小");
        }
    }

}
