package com.muzikun.lhfsyczl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.entity.WebFlag;
import com.muzikun.lhfsyczl.uihelper.LoadingDialog;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.NetworkUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


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
        Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getMobData();
//                        jumpToActivity(MainActivity.class, true);
                    }
                });

            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000);

    }

    /**
     * 获取mob后台数据
     */
    private void getMobData() {
        if (!NetworkUtil.isConnected(mContext)) {
            ToastUtils.show("请检查网络……");
            return;
        }
        BmobQuery<WebFlag> query = new BmobQuery<>();
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        LoadingDialog.showDialog(mActivity);
        query.findObjects(new FindListener<WebFlag>() {
            @Override
            public void done(List<WebFlag> list, BmobException e) {
                LoadingDialog.closeDialog(mActivity);
                if (e == null) {
                    if (list != null && list.size() > 0) {

                        WebFlag webFlag = list.get(0);
                        if (webFlag.isFlag() == true) {
                            jumpToActivity(MainActivity.class, true);
                        } else {
                            jumpToActivity(com.muzikun.one.activity.MainActivity.class, true);
                        }
                        LogUtils.i(TAG, "查询成功：" + webFlag.isFlag());
                    } else {
                        LogUtils.i(TAG, "查询无数据：");
                        jumpToActivity(com.muzikun.one.activity.MainActivity.class, true);
                    }
                } else {
                    LogUtils.i(TAG, "查询失败：" + e.getMessage());
                    jumpToActivity(com.muzikun.one.activity.MainActivity.class, true);
                }
            }
        });

    }

}
