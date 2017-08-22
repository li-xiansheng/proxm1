package com.cpcp.loto.activity;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.BuyRecordRecyclerAdapter;
import com.cpcp.loto.base.BasePullRefreshActivity;
import com.cpcp.loto.bean.BuyRecordBean;
import com.cpcp.loto.config.Constants;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.UserDB;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.SPUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

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

public class BuyRecordActivity extends BasePullRefreshActivity {

    @BindView(R.id.tvMsg)
    AppCompatTextView tvMsg;
    @BindView(R.id.empty_rl)
    RelativeLayout emptyRl;

    BuyRecordRecyclerAdapter mAdapter;
    List<BuyRecordBean> data = new ArrayList<>();

    private boolean isFirst = true;//是否第一次加载

    @Override
    protected int getChildLayoutResId() {
        return R.layout.activity_buy_record;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("购买记录");

        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        data = (List<BuyRecordBean>) mBaseList;
        mAdapter = new BuyRecordRecyclerAdapter(mContext, data);
        recyclerView.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
////                Intent intent = new Intent(AttentionActivity.this,SalaryActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("nickname",data.get(position).user_nicename);
//                bundle.putString("mobile",data.get(position).mobile);
//                bundle.putString("avatar","http://"+data.get(position).avatar);
//                jumpToActivity(SalaryActivity.class,bundle,false);
//            }
//        });

        getData();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getData() {
        if (isFirst) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LogUtils.i(TAG, "首次加载……");
                    mPullToRefreshRecyclerView.setRefreshing(true);//没有刷新，则执行下拉刷新UI
                    isFirst = false;
//                    fragments.get(0).setUserVisibleHint(true);
                }
            }, 1000);


        } else {
            getBuyRecordData();
        }
    }

    private void getBuyRecordData() {

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
                        return null;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
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

                                    mAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tvMsg.setText("暂时还没购买记录...");
                            emptyRl.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if (currentPage == 1 && mPullToRefreshRecyclerView != null) {
                            mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        currentPage += 1;
                        if (mPullToRefreshRecyclerView != null && mPullToRefreshRecyclerView.isRefreshing()) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                        }
                    }

                    @Override
                    public void _onError(int status, String msg) {
                        super._onError(status, msg);
                        if (mPullToRefreshRecyclerView != null && mPullToRefreshRecyclerView.isRefreshing()) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                        }
                    }
                });



    }

}
