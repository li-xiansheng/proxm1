package com.cpcp.loto.activity;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.FansRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.bean.AttentionBean;
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
 * 功能描述：我的粉丝
 */

public class FansActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    FansRecyclerAdapter adapter;
    List<AttentionBean> data = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fans;
    }

    @Override
    protected void initView() {
        setTitle("我的粉丝");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        getMyFenSi();
    }

    @Override
    protected void initData() {

    }

    private void getMyFenSi() {
        LoadingDialog.showDialog(this);
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getMyFensi(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(FansActivity.this);
                        LogUtils.i(TAG, "getCurrentRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
                            if (response.getData() != null) {
                                emptyRl.setVisibility(View.GONE);
                                try {
                                    JSONArray array = new JSONArray(response.getData());
                                    if (array.length() < 1) {
                                        tvMsg.setText("暂时还没粉丝...");
                                        emptyRl.setVisibility(View.VISIBLE);

                                    } else {
                                        emptyRl.setVisibility(View.GONE);
                                        Gson gson = new Gson();
                                        for (int i = 0; i < array.length(); i++) {
                                            AttentionBean bean = gson.fromJson(array.getJSONObject(i).toString(), AttentionBean.class);
                                            data.add(bean);
                                        }

                                        adapter = new FansRecyclerAdapter(FansActivity.this, data);
                                        recyclerView.setAdapter(adapter);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                tvMsg.setText("暂时还没粉丝...");
                                emptyRl.setVisibility(View.VISIBLE);
                            }


                        } else {
                            tvMsg.setText("暂时还没粉丝...");
                            emptyRl.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
