package com.muzikun.one.fragment.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzikun.one.R;
import com.muzikun.one.adapter.fragment.FragmentNewViewPagerAdapter;
import com.muzikun.one.data.bean.NewViewPagerBean;
import com.muzikun.one.data.bean.TopLineViewPagerTitleBean;
import com.muzikun.one.fragment.news.ArticleListFragment;
import com.muzikun.one.lib.util.Net;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeking on 16/5/31.
 */
public class NewsFragment extends Fragment{

    private String TAG = "NewsFragment";
    private View mainView = null;
    private ViewPager fragmentBox = null;
    private int lastItem = 0;
    private TabLayout tabLayout = null;

    private List<TopLineViewPagerTitleBean> defaultTopLine = null;

    private FragmentNewViewPagerAdapter newViewPagerAdapter = null;

    private  List<NewViewPagerBean> fragmentList;

    private String[] defaultTitles = new String[]{
            "推荐",
            "特稿",
            "健康",
            "股票",
            "楼市",
            "廉哨",
            "他们说",
            "正能量",
            "炫镜头",
            "动新闻",
            "深一度",
            "资政事",
            "切民生"
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_new,container,false);
        initData();
        initView();
        return mainView;
    }


    public void initData(){


    }
    public void initView(){
        tabLayout = (TabLayout) mainView.findViewById(R.id.fragment_new_tablayout);
        fragmentBox = (ViewPager) mainView.findViewById(R.id.fragment_new_fragment_box);



        try{
//            new GetTypeList().execute("http://www.gzbjwb.cn/api/getType.php");
            new GetTypeList().execute("http://wanbao.7va.cn/api/getType.php");
        }catch (Exception e){

        }
    }




    private void startView(String string) {


        fragmentList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(string);
            String[] titles = new String[jsonArray.length()+1];
            titles[0] = "推荐";
            for(int i = 0 ; i < jsonArray.length(); i ++ ){
                ArticleListFragment articleListFragment =new ArticleListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", jsonArray.getJSONObject(i).getInt("id"));
                titles[i] = jsonArray.getJSONObject(i).getString("typename");

                articleListFragment.setArguments(bundle);
                fragmentList.add(new NewViewPagerBean(articleListFragment,String.valueOf(i)));
            }
            newViewPagerAdapter = new FragmentNewViewPagerAdapter(getChildFragmentManager(),fragmentList,titles);

            this.fragmentBox.setAdapter(newViewPagerAdapter);
            this.fragmentBox.addOnPageChangeListener(new NewFragmentOnPageChangeListener());



            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.setSmoothScrollingEnabled(false);
            tabLayout.setupWithViewPager(this.fragmentBox);
            tabLayout.setOnTabSelectedListener(new TabViewSelected());
        } catch (JSONException e) {


            for(int i = 0 ; i < defaultTitles.length ; i ++ ){
                ArticleListFragment articleListFragment =new ArticleListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",0);
                articleListFragment.setArguments(bundle);
                fragmentList.add(new NewViewPagerBean(articleListFragment,String.valueOf(i)));
            }
            newViewPagerAdapter = new FragmentNewViewPagerAdapter(getChildFragmentManager(),fragmentList,defaultTitles);

            this.fragmentBox.setAdapter(newViewPagerAdapter);
            this.fragmentBox.addOnPageChangeListener(new NewFragmentOnPageChangeListener());



            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.setSmoothScrollingEnabled(false);
            tabLayout.setupWithViewPager(this.fragmentBox);
            tabLayout.setOnTabSelectedListener(new TabViewSelected());
        }


    }


    public class NewFragmentOnPageChangeListener implements ViewPager.OnPageChangeListener{
        private ArticleListFragment articleListFragment = null;
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            articleListFragment = (ArticleListFragment) newViewPagerAdapter.getItem(position);

            articleListFragment.startLoad();



        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public class GetTypeList extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG,"strings[0] = " + strings[0]);
            return Net.getApiJson(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            startView(s);
        }
    }

    public class TabViewSelected implements TabLayout.OnTabSelectedListener {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                fragmentBox.setCurrentItem(position,false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

    }
}
