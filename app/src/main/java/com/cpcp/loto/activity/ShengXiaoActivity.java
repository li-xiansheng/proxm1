package com.cpcp.loto.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.ShengXiaoRecyclerViewAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.uihelper.ScrollGridLayoutManager;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.DividerItemDecorationGridHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：生肖卡牌
 */

public class ShengXiaoActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ShengXiaoRecyclerViewAdapter mAdapter;
    private List<Map<String, Object>> mData;

    private List<Integer> resImg;

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
                        map.put("isOpen",true);
                        AppCompatImageView imageView = (AppCompatImageView) view;
                        int valuesRes = new Random().nextInt(12);
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
