package com.cpcp.loto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.entity.RedPacketEntity;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.util.ToastUtils;
import com.cpcp.loto.view.SelectedLayerTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能描述：玄机锦囊
 */

public class XuanJiActivity extends BaseActivity {

    @BindView(R.id.tvOpen)
    SelectedLayerTextView tvOpen;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_xuanji;
    }

    @Override
    protected void initView() {
        setTitle("玄机锦囊");

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.tvOpen)
    public void onViewClicked() {
        getXuanJiPacket();
//        ToastUtils.show("这里是玄机锦囊111，为你找到玄机锦囊，赶快参悟吧");
    }

    /**
     * 获取玄机锦囊
     */
    private void getXuanJiPacket() {

        HttpService httpService = HttpRequest.provideClientApi();
        mSubscription = httpService.getXuanJiPacket()
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        String data = response.getData();
                        ToastUtils.show(data + "");

                    }
                });
    }
}
