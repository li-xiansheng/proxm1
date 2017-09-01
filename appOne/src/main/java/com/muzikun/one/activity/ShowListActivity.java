package com.muzikun.one.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.data.model.UserModel;
import com.muzikun.one.fragment.myhome.CommentFragment;
import com.muzikun.one.fragment.myhome.MessageFragment;
import com.muzikun.one.fragment.myhome.SendArticleFragment;
import com.muzikun.one.fragment.myhome.SendNewsFragment;
import com.muzikun.one.fragment.myhome.SetFragment;
import com.muzikun.one.fragment.myhome.SetInfoFragment;
import com.muzikun.one.fragment.myhome.UserStowFragment;
import com.muzikun.one.fragment.news.SendCommentFragment;
import com.muzikun.one.lib.util.Helper;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * Created by likun on 16/6/27.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class ShowListActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String ITEM_POSITION = "ITEM_POSITION";

    private LinearLayout backButton             = null;
    private LinearLayout viewBox                = null;
    private FragmentTransaction transaction     = null;
    private FragmentManager fragmentManager     = null;
    private List<Fragment> fragmentList         = null;
    private Fragment fragment                   = null;
    private int position                        = 0;
    private TextView titleView                  = null;
    private ImageView answerIcon                  = null;
    private String otherTitle                   = null;

    private String[] titles = new String[]{
            "我的消息",
            "我的收藏",
            "我的评论",
            "风水咨询",
            "设置",
            "设置资料",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_showlist);
        Helper.setColor(this, Color.parseColor("#891C21"));
        JCVideoPlayer.TOOL_BAR_EXIST = true;
        JCVideoPlayer.ACTION_BAR_EXIST = true;
        initData();
        initView();
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        fragmentList = new ArrayList<>();
        Intent intent  = getIntent();
        position = intent.getIntExtra(ITEM_POSITION,0);

    }

    private void initView() {
        backButton      = (LinearLayout) findViewById(R.id.activity_showlist_back);
        viewBox         = (LinearLayout) findViewById(R.id.activity_showlist_viewbox);
        titleView       = (TextView) findViewById(R.id.activity_showlist_title);
        answerIcon       = (ImageView) findViewById(R.id.answer_icon);

        backButton.setOnClickListener(this);

        try{
            switch (position){
                case 0:
                    answerIcon.setVisibility(View.GONE);
                    fragment = new MessageFragment();
                    break;
                case 1:
                    answerIcon.setVisibility(View.GONE);
                    fragment = new UserStowFragment();
                    break;
                case 2:
                    answerIcon.setVisibility(View.GONE);
                    fragment = new CommentFragment();
                    break;

                case 300:
                    answerIcon.setVisibility(View.GONE);
                    otherTitle = "设置资料";
                    fragment = new SetInfoFragment();
                    break;

                case 306:
                    answerIcon.setVisibility(View.GONE);
                    otherTitle = "发布爆料";
                    fragment = new SendArticleFragment();
                    break;

                case 3:
                    answerIcon.setVisibility(View.VISIBLE);
                    fragment = new SendNewsFragment();
                    break;
                case 4:
                    fragment = new SetFragment();
                    break;
                case 301:
                    otherTitle = "设置资料";
                    fragment = new SetInfoFragment();
                    break;
                case 100:
                    fragment = new SendCommentFragment();
                    break;
                default:
                    Helper.T(this,"找不到页面");
                    finish();
            }
            transaction.add(R.id.activity_showlist_viewbox,fragment);
            transaction.commit();
            if(position<100){
                titleView.setText(titles[position]);
            }else{
                if(otherTitle != null){
                    titleView.setText(otherTitle);
                }else{
                    titleView.setText("");
                }
            }
        }catch (Exception e){
            Helper.T(this,"找不到页面");
            finish();
        }

        answerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowListActivity.this,AnswerActivity.class);
                intent.putExtra("type",5);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            JCVideoPlayer.releaseAllVideos();
        }catch (Exception e){

        }
    }


    public static void startHelper(Context context, int position, String userPhone , String userPass) {
        Intent starter = new Intent(context, ShowListActivity.class);
        starter.putExtra(ITEM_POSITION,position);
        starter.putExtra(UserModel.USER_PHONE,userPhone);
        starter.putExtra(UserModel.USER_PASS,userPass);
        context.startActivity(starter);
    }

}
