package com.cpcp.loto.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.entity.BaseResponse1Entity;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.OpenLotteryLiveEntity;
import com.cpcp.loto.entity.OpenLotteryZuiXinEntity;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.DisplayUtil;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.SelectedLayerImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：开奖直播中，直播结果
 */

public class OpenLotteryingActivity extends BaseActivity implements SpeechSynthesizerListener {


    @BindView(R.id.ivBottom)
    AppCompatImageView ivBottom;
    @BindView(R.id.ivStatic)
    AppCompatImageView ivStatic;
    @BindView(R.id.ivDismiss)
    SelectedLayerImageView ivDismiss;
    @BindView(R.id.tvNo)
    AppCompatTextView tvNo;
    @BindView(R.id.lilBall)
    LinearLayout lilBall;
    @BindView(R.id.lilShengXiao)
    LinearLayout lilShengXiao;

    private OpenLotteryLiveEntity entity;

    // 语音合成客户端
    private SpeechSynthesizer mSpeechSynthesizer;

    //计算时间开奖前
    private Timer startTimer;
    //轮询开奖
    private Timer mTimer;

    private int currentIndex;

    private long nextOpenLottery;
    private String nextNo;

    @Override
    public void getIntentData() {
        super.getIntentData();
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null) {
            nextOpenLottery = intent.getExtras().getLong("nextOpenLottery", 0);
            nextNo = intent.getExtras().getString("nextNo");

        }
    }

    @Override
    protected void initBase(Bundle savedInstanceState) {
        isFitsSystemWindows = false;
        isShowToolBar = false;
        statusBarColor = R.color.transparent;
        super.initBase(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_open_lotterying;
    }

    @Override
    protected void initView() {
        startTTS();

        tvNo.setText("第" + nextNo);
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dealWithData();
            }
        }, 1000);//1s以后开始判断是否处理数据


    }

    /**
     * 是否处理数据
     */
    private void dealWithData() {
        final Handler handler = new Handler();
        long currentTime = System.currentTimeMillis();
        if (nextOpenLottery - currentTime > 1000 * 60) {//开奖时间一分钟前，还未到开奖时间
            startTimer = new Timer();
            startTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    long shijian = nextOpenLottery - System.currentTimeMillis();
                    if (shijian > 1000 * 59 && shijian <= 1000 * 60) {
                        mSpeechSynthesizer.speak("距离开奖时间还剩1分钟");
                        LogUtils.i(TAG, "开奖提示音1分钟");
                    } else if (System.currentTimeMillis() - nextOpenLottery > 0) {//开始轮询开奖
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.i(TAG, "开奖开始");
                                startAnimalAndVoice();
                                startTimer.cancel();
                            }
                        });
                        //轮询，每隔4s访问一次开奖直播接口
                        mTimer = new Timer();
                        mTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mSubscription == null) {
                                            LogUtils.i(TAG, "轮询开始");
                                            getLotteryInfo();
                                        }
                                    }
                                });
                            }
                        }, 0, 4000);
                    }
                }
            }, 0, 1000);
        } else if (nextOpenLottery - currentTime < 1000 * 60 && nextOpenLottery - currentTime > 0) {
            mSpeechSynthesizer.speak("距离开奖时间还剩" + ((nextOpenLottery - currentTime) / 1000) + "秒");
            LogUtils.i(TAG, "开奖提示音秒");
            startTimer = new Timer();
            startTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - nextOpenLottery > 0) {//开始轮询开奖
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.i(TAG, "开奖开始--秒之后");
                                startAnimalAndVoice();
                                startTimer.cancel();
                            }
                        });
                        //轮询，每隔4s访问一次开奖直播接口
                        mTimer = new Timer();
                        mTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mSubscription == null) {
                                            LogUtils.i(TAG, "轮询开始--秒之后");
                                            getLotteryInfo();
                                        }
                                    }
                                });
                            }
                        }, 0, 4000);
                    }
                }
            }, 0, 1000);

        } else {
            startAnimalAndVoice();
            //轮询，每隔4s访问一次开奖直播接口
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            LogUtils.i(TAG, "直接轮询");
                            getLotteryInfo();
                        }
                    });
                }
            }, 0, 4000);

        }

    }

    /**
     * 获取开奖结果
     */
    private void getLotteryInfo() {
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.openLotteryZuixin()
                .compose(RxSchedulersHelper.<BaseResponse1Entity<List<OpenLotteryZuiXinEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse1Entity<List<OpenLotteryZuiXinEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse1Entity<List<OpenLotteryZuiXinEntity>> response) {
                        if ("成功".equals(response.getResult())) {
                            List<OpenLotteryZuiXinEntity> list = response.getData();
                            if (list != null && list.size() > 0) {
                                OpenLotteryZuiXinEntity entity = list.get(0);
                                setDataToUI(entity);
                            }

                        }
                    }

                    @Override
                    public void _onError(int status, String msg) {
                        super._onError(status, msg);
                        mSubscription = null;
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mSubscription = null;
                    }
                });
    }


    /**
     * 设置ui信息
     *
     * @param entity
     */
    private void setDataToUI(OpenLotteryZuiXinEntity entity) {


        String date = entity.getNo();
        if (date != null && !date.equals(nextNo)) {
            return;
        }
        tvNo.setText("第" + date + "开奖结果");

        List<String> haomaList = new ArrayList<>();
        haomaList.add(entity.getCode1());
        haomaList.add(entity.getCode2());
        haomaList.add(entity.getCode3());
        haomaList.add(entity.getCode4());
        haomaList.add(entity.getCode5());
        haomaList.add(entity.getCode6());
        haomaList.add(entity.getTema());

        List<String> shengxiaoList = entity.getShengxiao();
        int withOrHeight = DisplayUtil.dip2px(mContext, 34);


        lilBall.removeAllViews();
        lilShengXiao.removeAllViews();
        if (!TextUtils.isEmpty(entity.getTema())) {
            return;
        }
        try {
            for (int i = 0; i < haomaList.size(); i++) {
                if (TextUtils.isEmpty(haomaList.get(i))) {
                    break;
                }

                if (i == 6) {
                    AppCompatTextView tv = new AppCompatTextView(mContext);
                    tv.setGravity(Gravity.CENTER);
                    tv.setBackgroundResource(R.drawable.icon_live_add);

                    lilBall.addView(tv);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                    params.setMargins(10, 0, 0, 0);
                    params.width = withOrHeight;
                    params.height = withOrHeight;
                    tv.setLayoutParams(params);
                    //
                    AppCompatTextView tvShengxiao = new AppCompatTextView(mContext);
                    lilShengXiao.addView(tvShengxiao);
                    LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tvShengxiao.getLayoutParams();
                    params1.setMargins(10, 0, 0, 0);
                    params1.width = withOrHeight;
                    params1.height = withOrHeight;
                    tvShengxiao.setLayoutParams(params1);

                }
                AppCompatTextView tv = new AppCompatTextView(mContext);
                tv.setGravity(Gravity.CENTER);

                String haoma = haomaList.get(i) + "";

                if ("01".equals(haoma) || "02".equals(haoma) || "07".equals(haoma) || "08".equals(haoma)
                        || "12".equals(haoma) || "13".equals(haoma) || "18".equals(haoma) || "19".equals(haoma)
                        || "23".equals(haoma) || "24".equals(haoma) || "29".equals(haoma) || "30".equals(haoma)
                        || "34".equals(haoma) || "35".equals(haoma) || "40".equals(haoma) || "45".equals(haoma)
                        || "46".equals(haoma)) {
                    tv.setBackgroundResource(R.drawable.redball);
                } else if ("03".equals(haoma) || "04".equals(haoma) || "09".equals(haoma) || "10".equals(haoma)
                        || "14".equals(haoma) || "15".equals(haoma) || "20".equals(haoma) || "25".equals(haoma)
                        || "26".equals(haoma) || "31".equals(haoma) || "36".equals(haoma) || "36".equals(haoma)
                        || "41".equals(haoma) || "42".equals(haoma) || "47".equals(haoma) || "48".equals(haoma)) {
                    tv.setBackgroundResource(R.drawable.blueball);
                } else {
                    tv.setBackgroundResource(R.drawable.greenball);
                }
                tv.setText(haoma);
                lilBall.addView(tv);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                params.setMargins(10, 0, 0, 0);
                params.width = withOrHeight;
                params.height = withOrHeight;
                tv.setLayoutParams(params);

                //
                AppCompatTextView tvShengXiao = new AppCompatTextView(mContext);
                tvShengXiao.setGravity(Gravity.CENTER);

                String shengxiao = shengxiaoList.get(i) + "";
                tvShengXiao.setText(shengxiao);

                lilShengXiao.addView(tvShengXiao);
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tvShengXiao.getLayoutParams();
                params1.setMargins(10, 0, 0, 0);
                params1.width = withOrHeight;
                params1.height = withOrHeight;
                tvShengXiao.setLayoutParams(params1);


                if (i == currentIndex) {
                    speak(i, haoma, shengxiao);
                    currentIndex += 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @OnClick(R.id.ivDismiss)
    public void onViewClicked() {
        finish();
    }

    private MediaPlayer mediaPlayer;

    /**
     * 播放
     *
     * @param index
     * @param code
     * @param shengxiao
     */
    private void speak(int index, String code, String shengxiao) {
        if (index == 6) {
            stoptAnimalAndVoice();
        } else {
            startAnimalAndVoice();
        }

        mSpeechSynthesizer.speak(code + "号" + shengxiao);

    }

    /**
     * 开始动画和声音
     */
    private void startAnimalAndVoice() {
        Glide.with(mContext)
                .load(R.drawable.img_lotterying_gif)
                .into(ivStatic);

        if (mediaPlayer == null) {
                /* 从res/raw 资源中获取文件 */
            mediaPlayer = MediaPlayer.create(this, R.raw.zhibobgm);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            try {

                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }


            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.reset();
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                }
            }, 3000);

        }
    }


    /**
     * 停止动画和声音
     */
    private void stoptAnimalAndVoice() {
        Glide.with(mContext)
                .load(0)
                .into(ivStatic);
        Glide.with(mContext)
                .load(R.drawable.img_lotterying_static)
                .into(ivStatic);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    /***
     * -----------------------------------文字转语音监听---------------------------------------------
     * @param
     */
    // 初始化语音合成客户端并启动
    private void startTTS() {
        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(this);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        mSpeechSynthesizer.setApiKey("hGTOSNRRo7S71E4oYO70yqXO", "f274bdee7d695cafa9699450295dedab");
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        mSpeechSynthesizer.setAppId("10046062");
//        // 设置语音合成文本模型文件
//        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, "your_txt_file_path");
//        // 设置语音合成声音模型文件
//        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, "your_speech_file_path");
//        // 设置语音合成声音授权文件
//        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "your_licence_path");
        // 获取语音合成授权信息
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
        // 判断授权信息是否正确，如果正确则初始化语音合成器并开始语音合成，如果失败则做错误处理
        if (authInfo.isSuccess()) {
            mSpeechSynthesizer.initTts(TtsMode.MIX);
//            mSpeechSynthesizer.speak("百度语音合成示例程序正在运行");
        } else {
            // 授权失败
            ToastUtils.show("暂不能语音播放");
        }
    }


    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        this.mSpeechSynthesizer.release();
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (startTimer != null) {
            startTimer.cancel();
        }
        if (mediaPlayer != null) {
             /* 释放MeidaPlayer 对象 */
            mediaPlayer.release();
        }
    }


}
