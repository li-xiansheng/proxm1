package com.cpcp.loto.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.util.DateTimeUtils;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 功能描述：领取红包
 */

public class GetRedPacketActivity extends BaseActivity {
    @BindView(R.id.ivRedPacket)
    AppCompatImageView ivRedPacket;


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


    @OnClick(R.id.ivRedPacket)
    public void onViewClicked() {

        ivRedPacket.setClickable(false);

//        ivRedPacket.setImageResource(R.drawable.img_get_red_packet);
        //属性动画先压缩，再放大
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivRedPacket, "scaleY", 0.3f, 1.2f, 1f);
        animator.setDuration(500);//时间1s
        animator.start();


    }
}
