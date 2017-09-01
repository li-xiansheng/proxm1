package com.muzikun.lhfsyczl.activity;

import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.adapter.ShakeLotteryRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.listener.PopupWindowDismissListener;
import com.muzikun.lhfsyczl.uihelper.PopMenuHelper;
import com.muzikun.lhfsyczl.uihelper.PopupWindowHelper;
import com.muzikun.lhfsyczl.util.DisplayUtil;
import com.muzikun.lhfsyczl.view.SelectedLayerTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：恋人特码
 */

public class LoverActivity extends BaseActivity {


    @BindView(R.id.rbMan)
    AppCompatRadioButton rbMan;
    @BindView(R.id.rbWoman)
    AppCompatRadioButton rbWoman;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.tvMyBirthday)
    SelectedLayerTextView tvMyBirthday;
    @BindView(R.id.tvLoverBirthday)
    SelectedLayerTextView tvLoverBirthday;
    @BindView(R.id.tvStart)
    SelectedLayerTextView tvStart;

    //使用摇一摇规则产生数据
    private ShakeLotteryRecyclerAdapter shakeLotteryRecyclerAdapter;
    private List<Integer> mList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lover;
    }

    @Override
    protected void initView() {
        setTitle("恋人特码");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tvMyBirthday, R.id.tvLoverBirthday, R.id.tvStart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvMyBirthday://
                PopupWindowHelper.selectTime(mActivity, tvMyBirthday);
                break;
            case R.id.tvLoverBirthday:
                PopupWindowHelper.selectTime(mActivity, tvLoverBirthday);
                break;
            case R.id.tvStart:
                start();
                break;
        }
    }

    /**
     * 开始匹配
     */
    private void start() {
        String myBirthday = tvMyBirthday.getText().toString();
        String loverBirthDay = tvLoverBirthday.getText().toString();

        if (TextUtils.isEmpty(myBirthday)) {
            return;
        }

        if (TextUtils.isEmpty(loverBirthDay)) {
            return;
        }

        final PopupWindow window = PopMenuHelper.newBasicPopupWindow(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.popmenu_lottery, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 6));
        mList = new ArrayList<>();
        shakeLotteryRecyclerAdapter = new ShakeLotteryRecyclerAdapter(mContext, mList);
        recyclerView.setAdapter(shakeLotteryRecyclerAdapter);

        PopupWindowDismissListener popupWindowDismissListener = new PopupWindowDismissListener(mActivity);
        window.setOnDismissListener(popupWindowDismissListener);

        window.setContentView(view);
        window.setWidth((int) (DisplayUtil.getScreenWidth(mActivity) * (9.0 / 10)));
        window.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        randomLottery();

    }


    /**
     * 随机产生几个数
     */
    private void randomLottery() {


        mList.clear();
        Random random = new Random();
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);
        mList.add(random.nextInt(36) + 1);

        shakeLotteryRecyclerAdapter.notifyDataSetChanged();
    }
}
