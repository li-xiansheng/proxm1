package com.cpcp.loto.activity;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.ChangeRecordRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.bean.ChangeRecordBean;
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
 * 功能描述：转换记录
 */

public class ChangeRecordActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    ChangeRecordRecyclerAdapter adapter;
    List<ChangeRecordBean> data = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_record;
    }

    @Override
    protected void initView() {
        setTitle("转换记录");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        getChangeRecordData();
    }

    @Override
    protected void initData() {

    }

    private void getChangeRecordData() {

        LoadingDialog.showDialog(this);
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getChangeRecord(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(ChangeRecordActivity.this);
                        LogUtils.i(TAG, "getChangeRecordData ---->" + response.getFlag());
                        if (response.getFlag() == 1) {

                            try {
                                JSONArray array = new JSONArray(response.getData());
                                if (array.length() < 1) {
                                    tvMsg.setText("暂时还没转换记录...");
                                    emptyRl.setVisibility(View.VISIBLE);

                                } else {
                                    emptyRl.setVisibility(View.GONE);
                                    Gson gson = new Gson();
                                    for (int i = 0; i < array.length(); i++) {
                                        ChangeRecordBean bean = gson.fromJson(array.getJSONObject(i).toString(), ChangeRecordBean.class);
                                        data.add(bean);
                                    }

                                    adapter = new ChangeRecordRecyclerAdapter(ChangeRecordActivity.this, data);
                                    recyclerView.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tvMsg.setText("暂时还没转换记录...");
                            emptyRl.setVisibility(View.VISIBLE);
                        }
                    }
                });



    }
}
