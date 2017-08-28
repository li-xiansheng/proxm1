package com.cpcp.loto.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TurntableRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.TurntableEntity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.SPUtil;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.CirclePointView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：波肖转盘
 */

public class TurntableActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.circlePointView)
    CirclePointView circlePointView;
    @BindView(R.id.tvStart)
    AppCompatTextView tvStart;
    @BindView(R.id.tvQiShu)
    AppCompatTextView tvQiShu;

    private TurntableRecyclerAdapter mAdapter;
    private List<Map<String, Object>> maps;
    //是否正在转动
    private boolean isRunning;

    private int[] shegnxiaoIndexs = {0, 1, 2, 3, 7, 11, 15, 14, 13, 12, 8, 4};
    /**
     * 下期开奖
     */
    private TurntableEntity entity;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_turnable;
    }

    @Override
    protected void initView() {
        setTitle("波肖转盘");
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        //
        maps = new ArrayList<>();
        mAdapter = new TurntableRecyclerAdapter(mContext, maps);
        recyclerView.setAdapter(mAdapter);

        tvStart.setClickable(false);


    }

    @Override
    protected void initData() {
        List<Integer> imgResList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            imgResList.add(R.drawable.icon_luck_blue_fill);
            imgResList.add(R.drawable.icon_luck_red_fill);
            imgResList.add(R.drawable.icon_luck_green_fill);
        }
        circlePointView.setImgResList(imgResList);
        initCard();
        //
        turntableNextLottery();
    }

    private void initCard() {
        maps.clear();
        //
        Map<String, Object> map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_shu);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "鼠");
        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_niu);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "牛");

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_hu);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "虎");

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_tu);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "兔");

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_zhu);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "猪");

        maps.add(map);
        //
        map = new HashMap<>();
        maps.add(map);
        //
        map = new HashMap<>();
        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_long);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "龙");
        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_gou);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "狗");
        maps.add(map);
        //
        map = new HashMap<>();
        maps.add(map);
        //
        map = new HashMap<>();
        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_se);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "蛇");

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_ji);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "鸡");

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_hou);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "猴");

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_yang);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "羊");

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_ma);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        map.put("name", "马");

        maps.add(map);

    }


    @OnClick(R.id.tvStart)
    public void onViewClicked() {

        tvStart.setBackgroundColor(Color.TRANSPARENT);
        tvStart.setTextColor(Color.YELLOW);
        tvStart.setText("");
        final int maxValues = new Random().nextInt(12) + 50;
        final Handler handler = new Handler();
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (isRunning||entity==null) {
                    return;
                }
                isRunning = true;
                initCard();

                for (int i = 0; i <= maxValues; i++) {
                    try {
                        Thread.sleep(50);
                        final int finalI = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //生肖卡片
                                for (Map<String, Object> map : maps) {
                                    if (map.get("img") != null) {
                                        boolean lockChecked = (boolean) map.get("lockChecked");
                                        if (!lockChecked) {
                                            map.put("isChecked", false);
                                        }
                                    }
                                }
                                int selectIndex = finalI % 12;
                                Map<String, Object> map = maps.get(shegnxiaoIndexs[selectIndex]);
                                map.put("isChecked", true);

                                if (finalI == maxValues || finalI == maxValues / 2 || finalI == maxValues / 3) {
                                    map.put("lockChecked", true);

                                    tvStart.append(map.get("name") + " ");
                                }


                                mAdapter.notifyDataSetChanged();
                                //圆点波色
                                circlePointView.setCurrentSelect(finalI);
                                if (finalI == maxValues) {


                                    if (maxValues % 3 == 0) {
                                        tvStart.append("\n蓝波");
                                    } else if (maxValues % 3 == 1) {
                                        tvStart.append("\n红波");
                                    } else if (maxValues % 3 == 2) {
                                        tvStart.append("\n绿波");
                                    }

                                    tvStart.setClickable(false);

                                    SPUtil sp = new SPUtil(mContext, "turntableActivity");
                                    sp.clearData();

                                    sp.putString("name", tvStart.getText().toString());
                                    sp.putInt("index", maxValues);
                                    sp.putString("time",entity.getTime());

                                }
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isRunning = false;


            }
        }.start();

    }

    /**
     * 波肖转盘下期开奖
     */
    private void turntableNextLottery() {

        HttpService httpService = HttpRequest.provideClientApi();
        httpService.turntableNextLottery()
                .compose(RxSchedulersHelper.<BaseResponse2Entity<TurntableEntity>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<TurntableEntity>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<TurntableEntity> response) {
                        if (response.getFlag() == 1) {
                            entity = response.getData();
                            if (entity != null) {
                                String qishu = TextUtils.isEmpty(entity.getDesc()) ? "" : entity.getDesc();
                                tvQiShu.setText(qishu);


                                SPUtil sp = new SPUtil(mContext, "turntableActivity");
                                String name = sp.getString("name", "");
                                String time = sp.getString("time", "");
                                int index = sp.getInt("index", -1);
                                if (!TextUtils.isEmpty(time) && time.equals(entity.getTime())) {
                                    tvStart.setClickable(false);
                                    tvStart.setBackgroundResource(R.color.transparent);
                                    tvStart.setTextColor(mContext.getResources().getColor(R.color.yellowText));
                                    tvStart.setText(name);
                                    circlePointView.setCurrentSelect(index);
                                    for (Map<String, Object> objectMap : maps) {
                                        if (objectMap.get("name") != null && name.contains((String) objectMap.get("name"))) {
                                            objectMap.put("isChecked", true);
                                        }
                                    }
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    tvStart.setClickable(true);
                                    sp.clearData();
                                }

                            }
                        }
                    }
                });
    }
}
