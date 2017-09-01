package com.muzikun.lhfsyczl.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.muzikun.lhfsyczl.MainActivity;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.activity.GetRedPacketActivity;
import com.muzikun.lhfsyczl.activity.LiuHeImgActivity;
import com.muzikun.lhfsyczl.activity.OpenLotteryLiveActivity;
import com.muzikun.lhfsyczl.activity.SuperiorInfoActivity;
import com.muzikun.lhfsyczl.activity.WebCommonPageActivity;
import com.muzikun.lhfsyczl.activity.CommunicationForumActivity;
import com.muzikun.lhfsyczl.activity.HistoryLotteryActivity;
import com.muzikun.lhfsyczl.activity.InformationStatisticsActivity;
import com.muzikun.lhfsyczl.activity.LotoFormulaActivity;
import com.muzikun.lhfsyczl.activity.QueryHelperActivity;
import com.muzikun.lhfsyczl.activity.TextDataActivity;
import com.muzikun.lhfsyczl.activity.ToolBoxActivity;
import com.muzikun.lhfsyczl.activity.TrendAnalysisActivity;
import com.muzikun.lhfsyczl.adapter.HomeGridRecyclerAdapter;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.base.BaseFragment;
import com.muzikun.lhfsyczl.entity.AdvertEntity;
import com.muzikun.lhfsyczl.entity.BaseResponse1Entity;
import com.muzikun.lhfsyczl.listener.OnItemClickListener;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.uihelper.ScrollGridLayoutManager;
import com.muzikun.lhfsyczl.view.DividerItemDecorationGridHome;
import com.muzikun.lhfsyczl.view.convenientbanner.ConvenientBanner;
import com.muzikun.lhfsyczl.view.convenientbanner.LocalImageHolderView;
import com.muzikun.lhfsyczl.view.convenientbanner.NetImageHolderView;

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

    //本地图
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    //网络
    private ArrayList<String> netImages = new ArrayList<>();
    //网络返回的广告图
    private List<AdvertEntity> results;

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
        getAdvert();
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
    private void initLocationImage() {
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
     *
     */
    private void initNetWorkImage() {

        convenientBanner.setPages(new CBViewHolderCreator<NetImageHolderView>() {
            @Override
            public NetImageHolderView createHolder() {
                return new NetImageHolderView();
            }
        }, netImages)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                // 设置指示器的方向
                // .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                // .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //通过系统浏览器加载
                        if (results != null && results.size() > 0 && results.get(position) != null) {
                            String url = results.get(position).getSlide_url();
                            Uri uri = Uri.parse(url);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }
                });
    }

    /**
     * 获取广告图
     */
    private void getAdvert() {
        //清除本地图，重新加载
        localImages.clear();
        initLocationImage();
        //
        Map<String, String> map = new HashMap<>();
        map.put("slide_cid", "2");
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.getAdvert(map)
                .compose(RxSchedulersHelper.<BaseResponse1Entity<List<AdvertEntity>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse1Entity<List<AdvertEntity>>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse1Entity<List<AdvertEntity>> response) {
                        if ("成功".equals(response.getResult())) {
                            results = response.getData();

                            if (results != null && results.size() > 0) {
                                //清除网络图，重新加载
                                netImages.clear();
                                for (AdvertEntity entity : results) {
                                    netImages.add("http://" + entity.getSlide_pic());

                                }
                                initNetWorkImage();
                            }
                        }

                    }
                });
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
                LiuHeImgActivity.class,
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
//                if ("六合图库".equals(name)) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("name", "六合图库");
//                    bundle.putString("url", HttpService.lotoPictures);
//                    ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
//                } else
                    if ("历史开奖".equals(name)) {
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
//                Bundle bundle = new Bundle();
//                bundle.putString("name", "开奖直播");
//                bundle.putString("url", HttpService.lotteryLive);
//                ((BaseActivity) mActivity).jumpToActivity(WebCommonPageActivity.class, bundle, false);
                ((BaseActivity) mActivity).jumpToActivity(OpenLotteryLiveActivity.class, false);


                break;
            case R.id.lilHailiao:
                ((BaseActivity) mActivity).jumpToActivity(SuperiorInfoActivity.class, false);
                break;
            case R.id.lilGetRedPacket:
                ((BaseActivity) mActivity).jumpToActivity(GetRedPacketActivity.class, false);

                break;
        }
    }
}
