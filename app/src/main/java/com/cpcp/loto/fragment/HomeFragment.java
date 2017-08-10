package com.cpcp.loto.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.cpcp.loto.MainActivity;
import com.cpcp.loto.R;
import com.cpcp.loto.activity.SuperiorInfoActivity;
import com.cpcp.loto.activity.WebCommonPageActivity;
import com.cpcp.loto.activity.CommunicationForumActivity;
import com.cpcp.loto.activity.HistoryLotteryActivity;
import com.cpcp.loto.activity.InformationStatisticsActivity;
import com.cpcp.loto.activity.LotoFormulaActivity;
import com.cpcp.loto.activity.QueryHelperActivity;
import com.cpcp.loto.activity.TextDataActivity;
import com.cpcp.loto.activity.ToolBoxActivity;
import com.cpcp.loto.activity.TrendAnalysisActivity;
import com.cpcp.loto.adapter.HomeGridRecyclerAdapter;
import com.cpcp.loto.base.BaseActivity;
import com.cpcp.loto.base.BaseFragment;
import com.cpcp.loto.listener.OnItemClickListener;
import com.cpcp.loto.net.HttpService;
import com.cpcp.loto.uihelper.ScrollGridLayoutManager;
import com.cpcp.loto.view.DividerItemDecorationGridHome;
import com.cpcp.loto.view.convenientbanner.ConvenientBanner;
import com.cpcp.loto.view.convenientbanner.LocalImageHolderView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：首页
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.rootScrollView)
    ScrollView scrollView;

    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    @BindView(R.id.lilLotteryLive)
    LinearLayout lilLotteryLive;
    @BindView(R.id.lilHailiao)
    LinearLayout lilHailiao;
    @BindView(R.id.lilGetRedPacket)
    LinearLayout lilGetRedPacket;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    private HomeGridRecyclerAdapter homeGridRecyclerAdapter;
    private List<Map<String, Object>> data;

    /**
     * 构造Fragment
     *
     * @return 当前Fragment对象
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        initBanner();

        initGridData();
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);

            }
        });

    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    public void onLazyLoadData() {

    }

    /**
     * 初始加载banner
     */
    private void initBanner() {
        // 本地图片例子
        loadTestData();
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        // 设置指示器的方向
        // .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
        // .setOnPageChangeListener(this)//监听翻页事件

    }

    /**
     * 加入测试Views
     */
    private void loadTestData() {
//        // 本地图片集合
//        for (int position = 0; position < 3; position++)
//
//            localImages.add(getResId("ad" + position, R.drawable.class));
        localImages.add(R.drawable.img_banner_ad0);


    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 初始化运用模块数据
     */
    private void initGridData() {
        ScrollGridLayoutManager manager = new ScrollGridLayoutManager(mContext, 3);
        manager.setScrollEnabled(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecorationGridHome(mContext, 3, this.getResources().getColor(R.color.grayBgLight)));

        data = new ArrayList<>();

        int[] resId = new int[]{
                R.drawable.item_pictures,
                R.drawable.item_query,
                R.drawable.item_communication_forum,
                R.drawable.item_text_data,
                R.drawable.item_statistics,
                R.drawable.item_analysis,
                R.drawable.item_formula,
                R.drawable.item_history_lottery,
                R.drawable.item_tool_box,


        };

        String[] strIndex = new String[]{
                "六合图库", "走势分析", "交流论坛",
                "历史开奖", "六合资料", "资讯统计",
                "特码公式", "六合宝箱", "六合助手"};

        // 待定跳转的class
        Class[] uri = new Class[]{
                WebCommonPageActivity.class,
                TrendAnalysisActivity.class,
                CommunicationForumActivity.class,
                HistoryLotteryActivity.class,
                TextDataActivity.class,
                InformationStatisticsActivity.class,
                LotoFormulaActivity.class,
                ToolBoxActivity.class,
                QueryHelperActivity.class,

        };

        for (int i = 0; i < resId.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", strIndex[i]);
            map.put("img", resId[i]);
            map.put("uri", uri[i]);
            data.add(map);
        }

        homeGridRecyclerAdapter = new HomeGridRecyclerAdapter(mActivity, data);
        recyclerView.setAdapter(homeGridRecyclerAdapter);
        homeGridRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Class aClass = (Class<?>) data.get(position).get("uri");
                String name = (String) data.get(position).get("name");
                if ("六合图库".equals(name)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "六合图库");
                    bundle.putString("url", HttpService.lotoPictures);
                    ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                } else if ("历史开奖".equals(name)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "历史开奖");
                    bundle.putString("url", HttpService.historyLottery);
                    ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                } else if ("六合助手".equals(name)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "六合助手");
                    bundle.putString("url", HttpService.queryHelper);
                    ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                } else if ("六合资料".equals(name)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "六合资料");
                    bundle.putString("url", HttpService.lotoInfo);
                    ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                } else {
                    Intent intent = new Intent((MainActivity) mActivity, (Class<?>) data.get(position).get("uri"));
                    startActivity(intent);
                }


            }
        });
    }


    @OnClick({R.id.lilLotteryLive, R.id.lilHailiao, R.id.lilGetRedPacket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lilLotteryLive:
                Bundle bundle = new Bundle();
                bundle.putString("name", "开奖直播");
                bundle.putString("url", HttpService.lotteryLive);
                ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                break;
            case R.id.lilHailiao:
                ((BaseActivity) mActivity).jumpToActivity(SuperiorInfoActivity.class, false);
                break;
            case R.id.lilGetRedPacket:
                break;
        }
    }
}
