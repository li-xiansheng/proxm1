package com.cpcp.loto.activity;

import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.adapter.TabRichAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.fragment.liuhetuku.ColorPicFragment;
import com.cpcp.loto.fragment.liuhetuku.HeibaiPicFragment;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.util.ScreenUtil;
import com.cpcp.loto.view.EasyPickerView;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LiuHeImgActivity extends BaseActivity {

    @BindView(R.id.salary_tablayout)
    SegmentTabLayout salaryTablayout;
    @BindView(R.id.salary_viewpager)
    ViewPager salaryViewpager;

    private Dialog seletorDialog;
    EasyPickerView easyPickerView;
    ArrayList<String> stringList = new ArrayList<>();

    ColorPicFragment colorPicFragment;
    HeibaiPicFragment heibaiPicFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_liu_he_img;
    }

    @Override
    protected void initView() {

        setTitle("六合图库");

        setTopRightButton("全年图纸", new OnMenuClickListener() {

            @Override
            public void onClick() {
                seletorDialog.show();
            }
        });

        initAdapter();
        initDialog();


    }

    private void initAdapter(){
        String[] mTitles = {"彩色", "黑白"};
        salaryTablayout.setTabData(mTitles);

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(colorPicFragment = new ColorPicFragment());
        fragments.add(heibaiPicFragment = new HeibaiPicFragment());


        salaryViewpager.setAdapter(new TabRichAdapter(fragments, getSupportFragmentManager(), this));
        salaryTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                salaryViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        salaryViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                salaryTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDialog() {

        initList();
        if (seletorDialog == null) {
            seletorDialog = new Dialog(this, R.style.time_dialog);
            seletorDialog.setCancelable(true);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.pickview_selector);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(this).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }

        easyPickerView = (EasyPickerView) seletorDialog.findViewById(R.id.easyPickerView);
        easyPickerView.setDataList(stringList);

        TextView cancel = (TextView) seletorDialog.findViewById(R.id.tv_cancle);
        TextView sure = (TextView) seletorDialog.findViewById(R.id.tv_select);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seletorDialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (salaryTablayout.getCurrentTab() == 0){
                    LogUtils.i(TAG,stringList.get(easyPickerView.getCurIndex()));
                    colorPicFragment.getColorPic(stringList.get(easyPickerView.getCurIndex()));
                }else {
                    heibaiPicFragment.getHeibaiPic(stringList.get(easyPickerView.getCurIndex()));
                }
                seletorDialog.dismiss();
            }
        });
    }

    private void initList(){
        stringList.add("2017");
        stringList.add("2016");
        stringList.add("2015");
        stringList.add("2014");
        stringList.add("2013");
    }

    @Override
    protected void initData() {

    }
}
