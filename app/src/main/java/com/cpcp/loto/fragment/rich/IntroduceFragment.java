package com.cpcp.loto.fragment.rich;

import android.content.Intent;
import android.view.View;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.PublishXinShuiActivity;
import com.cpcp.loto.base.BaseFragment;

import butterknife.OnClick;

/**
 * 功能描述：
 */

public class IntroduceFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_introduce;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLazyLoadData() {

    }

    @OnClick({R.id.introduce_rl})
    public void click(View view){
        switch (view.getId()){
            case R.id.introduce_rl:
                Intent intent = new Intent(mContext, PublishXinShuiActivity.class);
                startActivity(intent);
                break;
        }
    }

}
