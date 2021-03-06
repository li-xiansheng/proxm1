package com.cpcp.loto.activity;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.RedPacketEntity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.SelectedLayerTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 功能描述：领取红包
 */

public class GetRedPacketActivity extends BaseActivity {
    @BindView(R.id.ivRedPacket)
    AppCompatImageView ivRedPacket;
    @BindView(R.id.tvScore)
    AppCompatTextView tvScore;

    @BindView(R.id.lilScore)
    LinearLayout lilScore;
    @BindView(R.id.tvGet)
    SelectedLayerTextView tvGet;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_get_red_packet;
    }

    @Override
    protected void initView() {
        setTitle("领取红包");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ivRedPacket, R.id.tvGet})
    public void onViewClicked() {

        getScore();

    }

    /**
     * 领取红包获取积分
     */
    private void getScore() {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        boolean isLogin = sp.getBoolean(UserDB.isLogin, false);
        String tel = "";
        if (isLogin) {
            tel = sp.getString(UserDB.TEL, "");
        } else {
            jumpToActivity(LoginActivity.class, false);
            return;
        }
        final Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getRedPacket(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<RedPacketEntity>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<RedPacketEntity>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<RedPacketEntity> response) {


                        lilScore.setVisibility(View.VISIBLE);
                        if (response.getFlag() == 1) {
                            if (response.getData() != null) {

                                String socre = response.getData().getScore();
                                if ("0".equals(socre)) {
                                    ivRedPacket.setImageResource(R.drawable.img_red_packet_open_not);
                                    tvScore.setText("");
                                } else {
                                    ivRedPacket.setImageResource(R.drawable.img_red_packet_open);
                                    tvScore.setText(socre + "分");
                                }

                            } else {
                                ivRedPacket.setImageResource(R.drawable.img_red_packet_open_tomorrow);
                                ToastUtils.show(response.getErrmsg() + "");
                                tvScore.setText("");

                            }
                        } else {
                            ivRedPacket.setImageResource(R.drawable.img_red_packet_open_tomorrow);
                            ToastUtils.show(response.getErrmsg() + "");
                            tvScore.setText("");

                        }
                    }
                });

    }
}
