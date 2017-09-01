package com.muzikun.one.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.muzikun.one.R;
import com.muzikun.one.fragment.main.MyHomeFragment;
import com.muzikun.one.fragment.main.NewsFragment;
import com.muzikun.one.fragment.main.PagerFragment;
import com.muzikun.one.fragment.main.PeopleLifeFragment;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity {
    private LinearLayout fragmentBox = null;

    private FragmentManager fragmentManager = null;
    private FragmentTransaction transaction = null;
    private Map<String, Fragment> fragments = new HashMap<>();
    private Map<String, Fragment> transactionMap = new HashMap<>();
    private List<ImageView> imageViewList = null;
    private List<TextView> textViewList = null;
    private List<LinearLayout> linearLayoutList = null;
    private int selectedPosition =-1;
    private long exitTime = 0;
    private int[] bottomIds = new int[]{
            R.id.common_bottom_nav_news,
            R.id.common_bottom_nav_page,
            R.id.common_bottom_nav_peoplelife,
            R.id.common_bottom_nav_my
    };

    private int[] bottomTvIds = new int[]{
            R.id.common_bottom_tv_news,
            R.id.common_bottom_tv_page,
            R.id.common_bottom_tv_peoplelife,
            R.id.common_bottom_tv_my
    };

    private int[] bottomLlIds = new int[]{
            R.id.common_bottom_ll_news,
            R.id.common_bottom_ll_page,
            R.id.common_bottom_ll_peoplelife,
            R.id.common_bottom_ll_my
    };

    private int[] bottomImages = new int[]{
            R.drawable.news,
            R.drawable.page,
            R.drawable.peopleslife,
            R.drawable.my
    };

    private int[] bottomImagesSelect = new int[]{
            R.drawable.news_select,
            R.drawable.page_select,
            R.drawable.peopleslife_select,
            R.drawable.my_select
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);

        Helper.setColor(this, Color.parseColor("#891C21"));


        initView();

        try {

            Map<String, String> string = Auth.getInstance().login(this);

            //Helper.T(this,string.get(UserModel.USER_NAME));
        } catch (Exception e) {
            Helper.T(this, "获取失败");
        }

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();

        try {
            JCVideoPlayer.releaseAllVideos();
        } catch (Exception e) {

        }
    }

    private void initView() {
        imageViewList = new ArrayList<>();
        textViewList = new ArrayList<>();
        linearLayoutList = new ArrayList<>();
        for (int i = 0; i < bottomIds.length; i++) {
            imageViewList.add((ImageView) findViewById(bottomIds[i]));
            textViewList.add((TextView) findViewById(bottomTvIds[i]));
            linearLayoutList.add((LinearLayout) findViewById(bottomLlIds[i]));

        }


        for (int i = 0; i < imageViewList.size(); i++) {
            linearLayoutList.get(i).setOnClickListener(new OnBottomSelectListener(i));
        }

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        initFragment();
    }

    private void initFragment() {
        fragments.put("news", new NewsFragment());
        fragments.put("peoplelife", new PeopleLifeFragment());
        fragments.put("page", new PagerFragment());
        fragments.put("home", new MyHomeFragment());

        selectItem(0);
    }

    private void selectItem(int position) {
        //选中的
//        Glide.with(MainActivity.this)
//                .load(bottomImagesSelect[position])
//                .into(imageViewList.get(position));
        imageViewList.get(position).setImageResource(bottomImages[position]);
        textViewList.get(position).setTextColor(getResources().getColor(R.color.orange));
        //未选中的
        if(selectedPosition!=-1&&(selectedPosition>=0&&selectedPosition<4)){

            imageViewList.get(selectedPosition).setImageResource(bottomImages[selectedPosition]);
            textViewList.get(selectedPosition).setTextColor(getResources().getColor(R.color.white));
        }
        selectedPosition = position;

//        for (int i = 0,size= imageViewList.size() ;i < size; i++) {
//            if (i == position) {
//                Glide.with(MainActivity.this)
//                        .load(bottomImagesSelect[i])
//                        .into(imageViewList.get(i));
//                textViewList.get(i).setTextColor(getResources().getColor(R.color.orange));
//            } else {
//                Glide.with(MainActivity.this)
//                        .load(bottomImages[i])
//                        .into(imageViewList.get(i));
////                imageViewList.get(i).setImageResource(bottomImages[i]);
//                textViewList.get(i).setTextColor(getResources().getColor(R.color.white));
//            }
//
//        }

        switch (position) {
            case 0:

                showTransaction(fragments.get("news"), "news");
                break;
            case 1:
                showTransaction(fragments.get("peoplelife"), "peoplelife");
                break;

            case 2:
                showTransaction(fragments.get("page"), "page");
                break;
            case 3:
                showTransaction(fragments.get("home"), "home");
                break;
            default:

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Auth.onActivityResult(getSupportFragmentManager(), requestCode, resultCode, data);
    }

    //控制fragment的切换
    private void showTransaction(Fragment fragment, String target) {
        this.transaction = this.getSupportFragmentManager().beginTransaction();
        for (String key : this.transactionMap.keySet()) { //先全部隐藏
            this.transaction.hide(this.transactionMap.get(key));
        }
        if (this.transactionMap.containsKey(target)) {
            this.transaction.show(fragment);
        } else {
            this.transactionMap.put(target, fragment);
            this.transaction.add(R.id.common_fragment_box, fragment);
        }
        this.transaction.commit();
    }

    public class OnBottomSelectListener implements View.OnClickListener {
        private int position = 0;

        public OnBottomSelectListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            selectItem(position);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (selectedPosition == 0) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    moveTaskToBack(true);
                }

            } else {
                selectItem(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
