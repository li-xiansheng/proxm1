package com.muzikun.lhfsyczl.activity;

import android.app.Activity;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.util.DialogUtil;
import com.muzikun.lhfsyczl.view.SelectedLayerTextView;

import butterknife.BindView;
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
                        DialogUtil.createDialog(mContext,"玄机锦囊",data+"");

                    }
                });
    }
}
