package com.muzikun.lhfsyczl.fragment.rich;

import android.content.Intent;
import android.view.View;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.SendXinShuiActivity;
import com.muzikun.lhfsyczl.base.BaseFragment;

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
    public void click(View view) {
        switch (view.getId()) {
            case R.id.introduce_rl:
                //该跳转为ct-Develop分支-代码没问题，但和lcb-develop分支相同，子Fragment实现复杂度略高，比较后保留lcb-develop
//                Intent intent = new Intent(mContext, PublishXinShuiActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(mContext, SendXinShuiActivity.class);
                startActivity(intent);

                break;

        }
    }


}
