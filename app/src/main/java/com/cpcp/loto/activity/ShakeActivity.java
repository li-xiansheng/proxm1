package com.cpcp.loto.activity;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.ShakeLotteryRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.util.LogUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * 功能描述：摇一摇
 */

public class ShakeActivity extends BaseActivity implements SensorEventListener {
    private static final int START_SHAKE = 0x1;
    private static final int AGAIN_SHAKE = 0x2;
    private static final int END_SHAKE = 0x3;

    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private Vibrator mVibrator;//手机震动
    private SoundPool mSoundPool;//摇一摇音效

    //记录摇动状态
    private boolean isShake = false;


    private MyHandler mHandler;
    private int mWeiChatAudio;

    @BindView(R.id.ivShake)
    AppCompatImageView ivShake;
    @BindView(R.id.relShake)
    RelativeLayout relShake;
    @BindView(R.id.tvNumber)
    AppCompatTextView tvNumber;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ShakeLotteryRecyclerAdapter shakeLotteryRecyclerAdapter;
    private List<Integer> mList;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shake;
    }

    @Override
    protected void initBase(Bundle savedInstanceState) {
//        isShowToolBar = false;
//        isFitsSystemWindows = false;
//        statusBarColor = R.color.transparent;//初始化状态栏颜色
        super.initBase(savedInstanceState);
    }

    @Override
    protected void initView() {
        setTitle("摇一摇");

        mHandler = new MyHandler(this);

        //初始化SoundPool
        mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        mWeiChatAudio = mSoundPool.load(this, R.raw.weichat_audio, 1);

        //获取Vibrator震动服务
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        //
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 6));
        mList = new ArrayList<>();
        shakeLotteryRecyclerAdapter = new ShakeLotteryRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(shakeLotteryRecyclerAdapter);

        tvNumber.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    ///////////////////////////////////////////////////////////////////////////
// SensorEventListener回调方法
///////////////////////////////////////////////////////////////////////////
    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !isShake) {
                isShake = true;
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            LogUtils.d(TAG, "onSensorChanged: 摇动");

                            //开始震动 发出提示音 展示动画效果
                            mHandler.obtainMessage(START_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            //再来一次震动提示
                            mHandler.obtainMessage(AGAIN_SHAKE).sendToTarget();
                            Thread.sleep(500);
                            mHandler.obtainMessage(END_SHAKE).sendToTarget();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private static class MyHandler extends Handler {
        private WeakReference<ShakeActivity> mReference;
        private ShakeActivity mActivity;

        public MyHandler(ShakeActivity activity) {
            mReference = new WeakReference<ShakeActivity>(activity);
            if (mReference != null) {
                mActivity = mReference.get();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_SHAKE:
                    //This method requires the caller to hold the permission VIBRATE.
                    mActivity.mVibrator.vibrate(300);
                    //发出提示音
                    mActivity.mSoundPool.play(mActivity.mWeiChatAudio, 1, 1, 0, 0, 1);
                    mActivity.startAnimation();

                    break;
                case AGAIN_SHAKE:
                    mActivity.mVibrator.vibrate(300);
                    break;
                case END_SHAKE:
                    //整体效果结束, 将震动设置为false
                    mActivity.isShake = false;
                    mActivity.randomLottery();
                    break;
            }
        }
    }

    /**
     * 开启 摇一摇动画
     */
    private void startAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(relShake, "rotation", 15, -15, 0);
        // 动画的持续时间，执行多久？
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }

    /**
     * 随机产生几个数
     */
    private void randomLottery() {
        tvNumber.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        mList.clear();
        Random random = new Random();
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);

        shakeLotteryRecyclerAdapter.notifyDataSetChanged();
    }

}
