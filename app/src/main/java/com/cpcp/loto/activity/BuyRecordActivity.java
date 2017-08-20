package com.cpcp.loto.activity;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.BuyRecordRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.bean.BuyRecordBean;
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
 * 功能描述：购买记录
 */

public class BuyRecordActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    BuyRecordRecyclerAdapter adapter;
    List<BuyRecordBean> data = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_buy_record;
    }

    @Override
    protected void initView() {
        setTitle("购买记录");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        getBuyRecordData();
    }

    @Override
    protected void initData() {

    }

    private void getBuyRecordData() {

        LoadingDialog.showDialog(this);
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        Map<String, String> map = new HashMap<>();
        map.put("username", tel);
        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getBuyRecord(map)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LoadingDialog.closeDialog(BuyRecordActivity.this);
                        LogUtils.i("BuyRecordActivity", "getBuyRecordData ---->" + response.getFlag());
                        if (response.getFlag() == 1) {

                            try {
                                JSONArray array = new JSONArray(response.getData());
                                if (array.length() < 1) {
                                    tvMsg.setText("暂时还没购买记录...");
                                    emptyRl.setVisibility(View.VISIBLE);

                                } else {
                                    emptyRl.setVisibility(View.GONE);
                                    Gson gson = new Gson();
                                    for (int i = 0; i < array.length(); i++) {
                                        BuyRecordBean bean = gson.fromJson(array.getJSONObject(i).toString(), BuyRecordBean.class);
                                        data.add(bean);
                                    }

                                    adapter = new BuyRecordRecyclerAdapter(BuyRecordActivity.this, data);
                                    recyclerView.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tvMsg.setText("暂时还没购买记录...");
                            emptyRl.setVisibility(View.VISIBLE);
                        }
                    }
                });



    }

}
