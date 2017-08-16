package com.cpcp.loto.fragment.publish;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;

/**
 * 功能描述：
 */

public class ShengXiaoFragment extends BaseFragment {

    @BindView(R.id.shengxiao_title)
    EditText shengxiaoTitle;
    @BindView(R.id.shengxiao_count)
    EditText shengxiaoCount;
    @BindView(R.id.shengxiao_flowlayout)
    TagFlowLayout shengxiaoFlowlayout;

    private String[] mVals = new String[]
            {"鼠", "牛", "虎", "兔", "龙", "蛇",
                    "马", "羊", "猴", "鸡", "狗",
                    "猪",};

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shengxiao;
    }

    @Override
    protected void initView() {

        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        shengxiaoFlowlayout.setMaxSelectCount(5);
        shengxiaoFlowlayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_tv,
                        shengxiaoFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });

        shengxiaoFlowlayout.setSelected(true);

    }

    @Override
    public void onLazyLoadData() {

    }
}
