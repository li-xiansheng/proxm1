package com.cpcp.loto.activity;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：幸运转盘
 */

public class LuckActivity extends BaseActivity {


    @BindView(R.id.ivBg)
    AppCompatImageView ivBg;
    @BindView(R.id.ivStart)
    AppCompatImageView ivStart;
    @BindView(R.id.lilLuckCode)
    LinearLayout lilLuckCode;
    //正在摇奖
    private boolean isRunning;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_luck;
    }

    @Override
    protected void initView() {
        setTitle("幸运转盘");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ivStart})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ivStart:
                if (isRunning){
                    return;
                }
                final Handler handler = new Handler();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        isRunning = true;

                        for (int i = 0; i < 50; i++) {
                            try {
                                sleep(50);
                                int url = 0;
                                if (i % 2 == 0) {
                                    url = R.drawable.img_luck_bg1;
                                } else {
                                    url = R.drawable.img_luck_bg2;
                                }
                                final int finalUrl = url;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivBg.setImageResource(finalUrl);
                                    }
                                }, 0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        isRunning = false;

                    }
                }.start();


                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivStart, "rotation", 0f, 5 * 360f);

                objectAnimator.setDuration(3000);
                objectAnimator.start();
                randomCode();

                break;
        }
    }

    /**
     * 随机摇奖
     */
    private void randomCode() {
        lilLuckCode.removeAllViews();

        final List<Integer> list = new ArrayList<>();
        Random random = new Random();
        list.add(random.nextInt(49) + 1);
        list.add(random.nextInt(49) + 1);
        list.add(random.nextInt(49) + 1);
        list.add(random.nextInt(49) + 1);
        list.add(random.nextInt(49) + 1);
        list.add(random.nextInt(49) + 1);
        list.add(-1);
        list.add(random.nextInt(49) + 1);

        final int greenBg = R.drawable.icon_boll_green_bg;
        final int redBg = R.drawable.icon_boll_red_bg;

        final Handler handler = new Handler();
        new Thread() {
            @Override
            public void run() {
                super.run();

                for (int i = 0; i < 8; i++) {
                    try {
                        sleep(400);
                        final int values = list.get(i);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (values == -1) {
                                    AppCompatTextView textValues = new AppCompatTextView(mContext);
                                    textValues.setBackgroundResource(R.drawable.icon_luck_add);

                                    lilLuckCode.addView(textValues);

                                    startFlyAnimator(textValues);

                                } else {
                                    AppCompatTextView textValues = new AppCompatTextView(mContext);

                                    int colorType = new Random().nextInt(2);
                                    if (0 == colorType) {
                                        textValues.setBackgroundResource(greenBg);
                                    } else if (1 == colorType) {
                                        textValues.setBackgroundResource(redBg);
                                    }
                                    textValues.setGravity(Gravity.CENTER);

                                    textValues.setText(String.format("%02d", values)+ "");
                                    lilLuckCode.addView(textValues);
                                    startFlyAnimator(textValues);
                                }

                            }
                        }, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 开始属性动画
     *
     * @param view
     */
    private void startFlyAnimator(AppCompatTextView view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", -1000f, 0f);
        objectAnimator.setDuration(400);
        objectAnimator.start();
    }
}
