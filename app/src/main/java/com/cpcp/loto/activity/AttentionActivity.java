package com.cpcp.loto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.AttentionRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.bean.AttentionBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
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
 * 功能描述：我的关注
 */

public class AttentionActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;
    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;

    AttentionRecyclerAdapter adapter;
    List<AttentionBean> data = new ArrayList<>();



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initView() {
        setTitle("我的关注");


        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        getAttentionData();
    }

    @Override
    protected void initData() {

    }

    private void refreshView(){

        adapter = new AttentionRecyclerAdapter(AttentionActivity.this, data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(AttentionActivity.this,SalaryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nickname",data.get(position).user_nicename);
                bundle.putString("mobile",data.get(position).mobile);
                bundle.putString("avatar","http://"+data.get(position).avatar);
                jumpToActivity(SalaryActivity.class,bundle,false);
            }
        });
    }

    public void getAttentionData() {

        LoadingDialog.showDialog(this);
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getMyAttention(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(AttentionActivity.this);
                        if (response.getFlag() == 1) {
                            Log.i(TAG, "getAttentionData ---->" + response.getData());
                            try {
                                JSONArray array = new JSONArray(response.getData());
                                if (array.length() < 1) {
                                    tvMsg.setText("还没关注任何人...");
                                    emptyRl.setVisibility(View.VISIBLE);

                                } else {
                                    emptyRl.setVisibility(View.GONE);
                                    Gson gson = new Gson();
                                    for (int i = 0; i < array.length(); i++) {
                                        AttentionBean bean = gson.fromJson(array.getJSONObject(i).toString(), AttentionBean.class);
                                        data.add(bean);
                                    }

                                    refreshView();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }

                });

    }

}
