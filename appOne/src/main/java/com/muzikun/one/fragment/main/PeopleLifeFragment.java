package com.muzikun.one.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.one.R;
import com.muzikun.one.adapter.fragment.FragmentPeopleLifeViewPagerAdapter;
import com.muzikun.one.data.bean.NewTopLineDataBean;
import com.muzikun.one.fragment.VideoClassfyFragment;
import com.muzikun.one.fragment.VideoListFragment;
import com.muzikun.one.fragment.news.ArticleListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeking on 16/6/15.
 */
public class PeopleLifeFragment extends Fragment {
    private View mainView                                           = null;
    private ViewPager viewPager                                     = null;
    private FragmentPeopleLifeViewPagerAdapter fragmentPeopleLifeViewPagerAdapter = null;
    private List<Fragment> fragmentLsit                             = null;
    private List<NewTopLineDataBean>  newTopLineDataBeans           = null;
    private TabLayout tabLayout                                     = null;
    private ArticleListFragment articleListFragment                 = null;
    private List<Integer> fragmentLoading                           = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_peoplelife,container,false);
        initDate();
        initView();
        return mainView;
    }

    private void initDate() {
        newTopLineDataBeans = new ArrayList<>();
        fragmentLsit        = new ArrayList<>();
        newTopLineDataBeans.add(new NewTopLineDataBean(5,"经典视频",1));
        //newTopLineDataBeans.add(new NewTopLineDataBean(4,"动新闻",1));
       // newTopLineDataBeans.add(new NewTopLineDataBean(17,"爆料",1));
        fragmentLoading = new ArrayList<>();
      //  fragmentLoading.add(0);
        fragmentLoading.add(0);
       // fragmentLoading.add(0);


        fragmentLsit.add(new VideoListFragment());
        fragmentLsit.add(new VideoClassfyFragment());
//        for(int i = 0 ; i < newTopLineDataBeans.size() ; i ++ ){
//            Fragment  fragment  = new ArticleListFragment();
//            Bundle bundle       = new Bundle();
//
//            bundle.putInt("id",newTopLineDataBeans.get(i).TYPE_ID);
//            fragment.setArguments(bundle);
//
//            fragmentLsit.add(fragment);
//        }

    }

    private void initView() {
        viewPager       = (ViewPager) mainView.findViewById(R.id.fragment_peoplelife_viewpager);
        tabLayout       = (TabLayout) mainView.findViewById(R.id.fragment_peoplelife_tablayout);


        /**
         * 初始化Viewpager
         */
        fragmentPeopleLifeViewPagerAdapter = new FragmentPeopleLifeViewPagerAdapter(getChildFragmentManager(),fragmentLsit);
        this.viewPager.setOffscreenPageLimit(3);
        this.viewPager.setAdapter(fragmentPeopleLifeViewPagerAdapter);

        /**
         * 初始化tablayout
         */

        tabLayout.setupWithViewPager(viewPager);
//        articleListFragment = (ArticleListFragment) fragmentLsit.get(1);
//        articleListFragment.startLoad();

        viewPager.addOnPageChangeListener(new OnSelectLoading() );
    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        try {
//            JCVideoPlayer.releaseAllVideos();
//        }catch (Exception e){
//
//        }
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        try {
//            JCVideoPlayer.releaseAllVideos();
//        }catch (Exception e){
//
//        }
//    }
//
    public class OnSelectLoading implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position>2){

                return;
            }
//            try {
//                JCVideoPlayer.releaseAllVideos();
//            }catch (Exception e){
//
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
