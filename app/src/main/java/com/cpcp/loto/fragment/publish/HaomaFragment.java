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

public class HaomaFragment extends BaseFragment {

    @BindView(R.id.shengxiao_title)
    EditText shengxiaoTitle;
    @BindView(R.id.shengxiao_count)
    EditText shengxiaoCount;
    @BindView(R.id.shengxiao_flowlayout)
    TagFlowLayout shengxiaoFlowlayout;

    private String[] mVals = new String[]
            {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12","13","14","15","16",
                    "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28","29","30","31","32",
                    "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44","45","46","47","48","49"};

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shengxiao;
    }

    @Override
    protected void initView() {

        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        shengxiaoFlowlayout.setMaxSelectCount(12);
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
