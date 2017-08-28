package com.cpcp.loto.fragment.liuhetuku;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cpcp.loto.R;
import com.cpcp.loto.activity.ShowPicActivity;
import com.cpcp.loto.adapter.SortAdapter;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.bean.TukuBean;
import com.cpcp.loto.entity.BaseResponse2Entity;
import com.cpcp.loto.net.HttpRequest;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.net.RxSchedulersHelper;
import com.cpcp.loto.net.RxSubscriber;
import com.cpcp.loto.uihelper.LoadingDialog;
import com.cpcp.loto.util.LogUtils;
import com.cpcp.loto.view.search.EditTextWithDel;
import com.cpcp.loto.view.search.PinyinComparator;
import com.cpcp.loto.view.search.PinyinUtils;
import com.cpcp.loto.view.search.SideBar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：
 */

public class ColorPicFragment extends BaseFragment {

    @BindView(R.id.et_search)
    EditTextWithDel etSearch;
    @BindView(R.id.lv_contact)
    ListView lvContact;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.sidrbar)
    SideBar sidrbar;


    private SortAdapter adapter;
    private List<TukuBean> SourceDateList = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pic_color;
    }

    @Override
    protected void initView() {
        getColorPic("2017");

        initDatas();
        initEvents();
    }

    private void initEvents() {
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                if (adapter!=null){
                    int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        lvContact.setSelection(position);
                    }
                }

            }
        });

        //ListView的点击事件
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(mContext, ShowPicActivity.class);
                intent.putExtra("url", SourceDateList.get(position).getThumb());
                startActivity(intent);

            }
        });

        //根据输入框输入值的改变来过滤搜索
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onLazyLoadData() {

    }

    public void getColorPic(String year) {

        LogUtils.i(TAG, "year = " + year);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getColorPic(year)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LogUtils.i(TAG, "getCurrentRecommend ---->" + response.getData());
                        if (response.getFlag() == 1) {
//                            if (response.getData() != null){
//                                emptyRl.setVisibility(View.GONE);
//
//                            }else {
//                                tvMsg.setText("暂时还没数据...");
//                                emptyRl.setVisibility(View.VISIBLE);
//                            }

                            SourceDateList.clear();
                            Gson gson = new Gson();
                            try {
                                JSONArray array = new JSONArray(response.getData());
                                for (int i = 0; i < array.length(); i++) {
                                    TukuBean bean = gson.fromJson(array.get(i).toString(), TukuBean.class);
                                    SourceDateList.add(bean);
                                }

                                setAdapter();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
//                            tvMsg.setText("暂时还没数据...");
//                            emptyRl.setVisibility(View.VISIBLE);
                        }
                    }
                });


    }

    private void setAdapter() {
        setSideBar(SourceDateList);
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(mContext, SourceDateList);
        lvContact.setAdapter(adapter);
    }

    private void initDatas() {
        sidrbar.setTextView(dialog);
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<TukuBean> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (TukuBean sortModel : SourceDateList) {
                String name = sortModel.getPost_title();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        if (adapter!=null){
            adapter.updateListView(mSortList);
        }

    }

    private void setSideBar(List<TukuBean> mSortList) {
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < mSortList.size(); i++) {
            String sortString = mSortList.get(i).firstletter;
            if (sortString.matches("[A-Z]")) {
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
        }
        Collections.sort(indexString);
        sidrbar.setIndexText(indexString);
    }
}
