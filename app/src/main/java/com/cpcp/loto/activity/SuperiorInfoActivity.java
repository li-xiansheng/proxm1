package com.cpcp.loto.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：高手资料
 */

public class SuperiorInfoActivity extends BaseActivity {
    @BindView(R.id.lilLotoKing)
    LinearLayout lilLotoKing;
    @BindView(R.id.lilWinning)
    LinearLayout lilWinning;
    @BindView(R.id.lilMyAttention)
    LinearLayout lilMyAttention;
    @BindView(R.id.lilSendXinShui)
    LinearLayout lilSendXinShui;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_superior_info;
    }

    @Override
    protected void initView() {
        setTitle("高手资料");
    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.lilLotoKing, R.id.lilWinning, R.id.lilMyAttention, R.id.lilSendXinShui})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lilLotoKing:
                jumpToActivity(LotoKingActivity.class,false);
                break;
            case R.id.lilWinning:
                jumpToActivity(WinningActivity.class,false);
                break;
            case R.id.lilMyAttention:
                jumpToActivity(AttentionActivity.class,false);
                break;
            case R.id.lilSendXinShui:
                jumpToActivity(SendXinShuiActivity.class,false);
                break;
        }
    }
}
