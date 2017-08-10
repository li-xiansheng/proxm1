package com.cpcp.loto;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.util.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;


/**
 * <p>
 * 功能描述：闪屏页
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.ivSplash)
    AppCompatImageView ivSplash;

    @Override
    protected void initBase(Bundle savedInstanceState) {
        LogUtils.e(TAG, "初始页面");
        isFullScreen = true;
        super.initBase(savedInstanceState);
        //
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        Glide.with(mContext)
                .load(R.drawable.splash)
                .into(ivSplash);
//        //初始化创建数据库
//        SQLiteDatabase db = LitePal.getDatabase();

    }

    @Override
    protected void initData() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                jumpToActivity(MainActivity.class, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 2000);
    }


}
