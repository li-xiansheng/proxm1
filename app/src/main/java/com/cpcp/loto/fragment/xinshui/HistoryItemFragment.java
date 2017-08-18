package com.cpcp.loto.fragment.xinshui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.HistoryRecyclerAdapter;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.bean.XinshuiBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.SPUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 功能描述：
 */

public class HistoryItemFragment extends BaseFragment{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    List<XinshuiBean> data = new ArrayList<>();
    HistoryRecyclerAdapter mAdapter;

    int type;
    String mobile;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_xinshui_current;
    }

    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

//        mAdapter = new HistoryRecyclerAdapter(mContext, data);
//
//        recyclerView.setAdapter(mAdapter);
        LogUtils.i(TAG, "getHistoryRecommend ---->" + mobile + "-----" + type);
        getHistoryRecommend();

    }

    @Override
    public void onLazyLoadData() {

    }


    @Override
    protected void getIntentData() {
        super.getIntentData();
        Bundle bundle = getArguments();
        mobile = bundle.getString("mobile");
        String typeStr = bundle.getString("title");
        LogUtils.i(TAG, "getIntentData ---->" + typeStr +"----"+mobile);
        if ("综合".equals(typeStr)) {
            type = 5;
        } else if ("大小".equals(typeStr)) {
            type = 1;
        } else if ("单双".equals(typeStr)) {
            type = 2;
        } else if ("生肖".equals(typeStr)) {
            type = 3;
        } else if ("号码".equals(typeStr)) {
            type = 4;
        }
    }

    private void getHistoryRecommend() {

        LoadingDialog.showDialog(getActivity());
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", mobile);
        map.put("type", type + "");

        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getHistoryRecommend(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(getActivity());
                        LogUtils.i(TAG, "getHistoryRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            if (response.getData() != null) {
                                emptyRl.setVisibility(View.GONE);
                                try {
                                    JSONArray array = new JSONArray(response.getData());

                                    for (int i=0;i<array.length();i++){
                                        Gson gson = new Gson();
                                        XinshuiBean bean = gson.fromJson(array.getJSONObject(i).toString(),XinshuiBean.class);
                                        data.add(bean);
                                    }
                                    Log.i(TAG, "data = " + data.toString());

                                    mAdapter = new HistoryRecyclerAdapter(mContext, data);
                                    recyclerView.setAdapter(mAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tvMsg.setText("暂时还没数据...");
                                emptyRl.setVisibility(View.VISIBLE);
                            }


                        } else {
                            tvMsg.setText("暂时还没数据...");
                            emptyRl.setVisibility(View.VISIBLE);
                        }
                    }
                });


    }
}
