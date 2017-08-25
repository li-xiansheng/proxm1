package com.cpcp.loto.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TurntableRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.view.CirclePointView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private TurntableRecyclerAdapter mAdapter;
    private List<Map<String, Object>> maps;
    //是否正在转动
    private boolean isRunning;

    private int[] shegnxiaoIndexs={0,1,2,3,7,11,15,14,13,12,8,4};
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_turnable;
    }

    @Override
    protected void initView() {
        setTitle("波肖转盘");
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        //
        maps= new ArrayList<>();
        mAdapter=new TurntableRecyclerAdapter(mContext,maps);
        recyclerView.setAdapter(mAdapter);
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




    }

    private void initCard() {
        maps.clear();
        //
        Map<String, Object> map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_shu);
        map.put("isChecked", false);
        map.put("lockChecked", false);
        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_niu);
        map.put("isChecked", false);
        map.put("lockChecked", false);

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_hu);
        map.put("isChecked", false);
        map.put("lockChecked", false);

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_tu);
        map.put("isChecked", false);
        map.put("lockChecked", false);

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_zhu);
        map.put("isChecked", false);
        map.put("lockChecked", false);

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

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_gou);
        map.put("isChecked", false);
        map.put("lockChecked", false);

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
        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_ji);
        map.put("isChecked", false);
        map.put("lockChecked", false);

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_hou);
        map.put("isChecked", false);
        map.put("lockChecked", false);

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_yang);
        map.put("isChecked", false);
        map.put("lockChecked", false);

        maps.add(map);
        //
        map = new HashMap<>();
        map.put("img", R.drawable.icon_luck_ma);
        map.put("isChecked", false);
        map.put("lockChecked", false);

        maps.add(map);

    }


    @OnClick(R.id.tvStart)
    public void onViewClicked() {
        final int maxValues = new Random().nextInt(12) + 50;
        final Handler handler = new Handler();
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (isRunning){
                    return;
                }
                initCard();
                isRunning = true;
                for (int i = 0; i <= maxValues; i++) {
                    try {
                        Thread.sleep(50);
                        final int finalI = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //生肖卡片
                                for (Map<String, Object> map:maps){
                                    if(map.get("img")!=null){
                                        boolean lockChecked= (boolean) map.get("lockChecked");
                                        if (!lockChecked){
                                            map.put("isChecked",false);
                                        }
                                    }
                                }
                                int  selectIndex=finalI % 12;
                                Map<String, Object> map=maps.get(shegnxiaoIndexs[selectIndex]);
                                map.put("isChecked",true);

                                if(finalI==maxValues||finalI==maxValues/2||finalI==maxValues/3){
                                    map.put("lockChecked",true);
                                }


                                mAdapter.notifyDataSetChanged();
                                //圆点波色
                                circlePointView.setCurrentSelect(finalI % 9);
                                if (finalI==maxValues){
                                    tvStart.setBackgroundColor(Color.TRANSPARENT);
                                    tvStart.setTextColor(Color.YELLOW);
                                    if (maxValues % 3 == 0) {
                                        tvStart.setText("蓝");
                                    } else if (maxValues % 3 == 1) {
                                        tvStart.setText("红");
                                    } else if (maxValues % 3 == 2) {
                                        tvStart.setText("绿");
                                    }
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
}
