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
 * 功能描述：波色投注
 */

public class BoSeBetFragment extends BaseFragment {


    @BindView(R.id.bose_qishu)
    TextView boseQishu;
    @BindView(R.id.bose_remen)
    TextView boseRemen;
    @BindView(R.id.bose_rb_blue)
    RadioButton boseRbBlue;
    @BindView(R.id.bose_progress_blue)
    MagicProgressBar boseProgressBlue;
    @BindView(R.id.bose_txt_blue)
    TextView boseTxtBlue;
    @BindView(R.id.bose_ll_blue)
    LinearLayout boseLlBlue;
    @BindView(R.id.bose_rb_red)
    RadioButton boseRbRed;
    @BindView(R.id.bose_progress_red)
    MagicProgressBar boseProgressRed;
    @BindView(R.id.bose_txt_red)
    TextView boseTxtRed;
    @BindView(R.id.bose_ll_red)
    LinearLayout boseLlRed;
    @BindView(R.id.bose_rb_green)
    RadioButton boseRbGreen;
    @BindView(R.id.bose_progress_green)
    MagicProgressBar boseProgressGreen;
    @BindView(R.id.bose_txt_green)
    TextView boseTxtGreen;
    @BindView(R.id.bose_ll_green)
    LinearLayout boseLlGreen;
    @BindView(R.id.bose_toupiao)
    RelativeLayout boseToupiao;

    String choose;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_bose_bet_child;
    }

    @Override
    protected void initView() {

        DisplayUtil.setTouchState(boseLlBlue);
        DisplayUtil.setTouchState(boseLlRed);
        DisplayUtil.setTouchState(boseLlGreen);

        boseLlBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "蓝";
                boseRbBlue.setChecked(true);
                boseRbRed.setChecked(false);
                boseRbGreen.setChecked(false);
            }
        });

        boseLlRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "红";
                boseRbBlue.setChecked(false);
                boseRbRed.setChecked(true);
                boseRbGreen.setChecked(false);

            }
        });

        boseLlGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "绿";

                boseRbBlue.setChecked(false);
                boseRbRed.setChecked(false);
                boseRbGreen.setChecked(true);

            }
        });

        boseToupiao.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 投票
     */
    private void putVote(){

        if (TextUtils.isEmpty(choose)){
            DialogUtil.createDialog(mContext,"您还未选择波色..");
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

        map.put("type","1");
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

                                    JSONObject boseObject = dataObject.optJSONObject("bose");
                                    voteBean.blue = boseObject.getInt("lan");
                                    voteBean.red = boseObject.getInt("hong");
                                    voteBean.green = boseObject.getInt("lv");

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

        boseTxtBlue.setText(voteBean.blue+"%");
        boseTxtRed.setText(voteBean.red+"%");
        boseTxtGreen.setText(voteBean.green+"%");

        boseProgressBlue.setSmoothPercent((float)voteBean.blue/100,500);
        boseProgressRed.setSmoothPercent((float)voteBean.red/100,500);
        boseProgressGreen.setSmoothPercent((float)voteBean.green/100,500);

        boseQishu.setText(voteBean.qishu);

        if (voteBean.blue > voteBean.red && voteBean.blue > voteBean.green){
            boseRemen.setText("本期热门: 蓝");
        }

        if (voteBean.red > voteBean.blue && voteBean.red > voteBean.green){
            boseRemen.setText("本期热门: 红");
        }

        if (voteBean.green > voteBean.blue && voteBean.green > voteBean.red){
            boseRemen.setText("本期热门: 绿");
        }
    }

}
