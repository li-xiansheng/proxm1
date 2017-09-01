package com.muzikun.one.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.muzikun.one.R;
import com.muzikun.one.lib.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {


    private LinearLayout back;

    private LinearLayout title;

    private ConvenientBanner banner;

    private List<Integer> imgRes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Helper.setColor(this, Color.parseColor("#891C21"));
        init();
    }

    private void init() {
        back = (LinearLayout) findViewById(R.id.back);
        title = (LinearLayout) findViewById(R.id.title);
        banner = (ConvenientBanner) findViewById(R.id.banner);


        imgRes.add(R.drawable.course1);
        imgRes.add(R.drawable.course2);
        imgRes.add(R.drawable.course3);
        imgRes.add(R.drawable.course4);

        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, imgRes)
                .setPageIndicator(new int[]{R.drawable.course_dit_gray, R.drawable.course_dit_red});
//                .setPointViewVisible(false)
//                .startTurning(3000)
//                .setManualPageable(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            Glide.with(context)
                    .load(data)
                    .into(imageView);

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            banner.startTurning(3000);
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            banner.stopTurning();
        }
        return super.dispatchTouchEvent(event);

    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        banner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        banner.stopTurning();
    }
}
