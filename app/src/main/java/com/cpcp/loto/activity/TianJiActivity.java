package com.cpcp.loto.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.ShakeLotteryRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.listener.PopupWindowDismissListener;
import com.cpcp.loto.uihelper.PopMenuHelper;
import com.cpcp.loto.util.DisplayUtil;
import com.cpcp.loto.view.EasyPickerView;
import com.cpcp.loto.view.SelectedLayerTextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：天机测算
 */

public class TianJiActivity extends BaseActivity {


    @BindView(R.id.tvShengXiaoStart)
    SelectedLayerTextView tvShengXiaoStart;
    @BindView(R.id.tvTailStart)
    SelectedLayerTextView tvTailStart;
    //是否滚动
    private boolean isRunning = false;
    //是否最后一次滚动
    private boolean isLast = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tianji;
    }

    @Override
    protected void initView() {
        setTitle("天机测算");

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tvShengXiaoStart, R.id.tvTailStart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvShengXiaoStart:
                showPopupWindow(1);
                break;
            case R.id.tvTailStart:
                showPopupWindow(2);
                break;
        }
    }

    /**
     * 显示抽奖结果
     *
     * @param type 1.生肖；2.号码
     */
    private void showPopupWindow(final int type) {

        final PopupWindow window = PopMenuHelper.newBasicPopupWindow(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.popup_tianji_wheel, null);
        final EasyPickerView cpv1 = (EasyPickerView) view.findViewById(R.id.cpv1);
        final EasyPickerView cpv2 = (EasyPickerView) view.findViewById(R.id.cpv2);
        final EasyPickerView cpv3 = (EasyPickerView) view.findViewById(R.id.cpv3);

        final ArrayList<String> dataList = new ArrayList<>();
        if (type == 1) {
            tvShengXiaoStart.setText("");
            tvShengXiaoStart.setClickable(false);
            dataList.add("鼠");
            dataList.add("牛");
            dataList.add("虎");
            dataList.add("兔");
            dataList.add("龙");
            dataList.add("蛇");
            dataList.add("马");
            dataList.add("羊");
            dataList.add("猴");
            dataList.add("鸡");
            dataList.add("狗");
            dataList.add("猪");
        } else {
            tvTailStart.setText("");
            tvTailStart.setClickable(false);
            for (int i = 0; i < 10; i++) {
                dataList.add("" + i);
            }
        }


        cpv1.setDataList(dataList);
        cpv2.setDataList(dataList);
        cpv3.setDataList(dataList);
        EasyPickerView.OnScrollChangedListener changedListener = new EasyPickerView.OnScrollChangedListener() {

            @Override
            public void onScrollChanged(int curIndex) {

            }

            @Override
            public void onScrollFinished(int curIndex) {
                // your codes.
                if (isLast) {
                    if (type == 1) {
                        tvShengXiaoStart.append(dataList.get(curIndex));
                    } else {
                        tvTailStart.append(dataList.get(curIndex));
                    }
                }

            }
        };
        cpv1.setOnScrollChangedListener(changedListener);
        cpv2.setOnScrollChangedListener(changedListener);
        cpv3.setOnScrollChangedListener(changedListener);

        PopupWindowDismissListener popupWindowDismissListener = new PopupWindowDismissListener(mActivity);
        window.setOnDismissListener(popupWindowDismissListener);

        window.setContentView(view);
        window.setWidth((int) (DisplayUtil.getScreenWidth(mActivity) * (9.0 / 10)));
        window.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        //随机号
        final Handler handler = new Handler();
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (isRunning) {
                    return;
                }
                isRunning = true;
                isLast = false;
                final Random random = new Random();
                for (int i = 0; i < 50; i++) {
                    try {
                        sleep(50);
                        final int finalI = i;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                cpv1.moveBy(random.nextInt(5));
                                cpv2.moveBy(random.nextInt(5));
                                cpv3.moveBy(random.nextInt(5));
                                if (finalI == 49) {
                                    isLast = true;
                                }
                            }
                        }, 0);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isRunning = false;
            }
        }.start();
    }


}
