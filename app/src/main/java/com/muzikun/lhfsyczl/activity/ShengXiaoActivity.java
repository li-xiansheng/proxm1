package com.muzikun.lhfsyczl.activity;

import android.animation.ObjectAnimator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.ShengXiaoRecyclerViewAdapter;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.listener.OnItemClickListener;
import com.muzikun.lhfsyczl.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;

/**
 * 功能描述：生肖卡牌
 */

public class ShengXiaoActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ShengXiaoRecyclerViewAdapter mAdapter;
    private List<Map<String, Object>> mData;

    private List<Integer> resImg;
    private List<Integer> listHistory;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shengxiao;
    }

    @Override
    protected void initView() {
        setTitle("生肖卡牌");

        mData = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", R.drawable.img_shengxiao_closed);
            map.put("isOpen", false);
            map.put("name", "");
            mData.add(map);
        }

        mAdapter = new ShengXiaoRecyclerViewAdapter(mContext, mData);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(mAdapter);


        //
        resImg = new ArrayList<>();
        resImg.add(R.drawable.img_shengxiao_gou);
        resImg.add(R.drawable.img_shengxiao_hou);
        resImg.add(R.drawable.img_shengxiao_hu);
        resImg.add(R.drawable.img_shengxiao_ji);
        resImg.add(R.drawable.img_shengxiao_long);
        resImg.add(R.drawable.img_shengxiao_ma);
        resImg.add(R.drawable.img_shengxiao_niu);
        resImg.add(R.drawable.img_shengxiao_se);
        resImg.add(R.drawable.img_shengxiao_shu);
        resImg.add(R.drawable.img_shengxiao_tu);
        resImg.add(R.drawable.img_shengxiao_yang);
        resImg.add(R.drawable.img_shengxiao_zhu);

        listHistory = new ArrayList<>();
    }

    @Override
    protected void initListener() {
        super.initListener();


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                int values = 0;
                for (Map<String, Object> map : mData) {
                    boolean isOpen = (boolean) map.get("isOpen");
                    if (isOpen) {
                        values++;
                    }
                }
                if (values >= 5) {
                    ToastUtils.show("每期最多查看5个");
                    return;
                }

                Map<String, Object> map = mData.get(position);
                boolean isOpen = (boolean) map.get("isOpen");
                if (map != null && isOpen == false) {

                    if (view instanceof AppCompatImageView) {
                        map.put("isOpen", true);
                        AppCompatImageView imageView = (AppCompatImageView) view;
                        int valuesRes = -1;
                        while (true) {
                            valuesRes = new Random().nextInt(12);
                            if (listHistory == null) {
                                listHistory = new ArrayList<>();
                            }

                            boolean isHad = false;
                            for (Integer valuesHistory : listHistory) {
                                if (valuesHistory.intValue() == valuesRes) {
                                    isHad = true;
                                    break;
                                }
                            }
                            if (isHad) {
                                continue;
                            }
                            listHistory.add(valuesRes);
                            break;
                        }


                        imageView.setImageResource(resImg.get(valuesRes));
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotationY", 180f, 0f);
                        objectAnimator.setDuration(500);
                        objectAnimator.start();
                    }
                }


            }
        });
    }

    @Override
    protected void initData() {


    }


}
