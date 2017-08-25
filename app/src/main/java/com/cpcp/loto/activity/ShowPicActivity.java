package com.cpcp.loto.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseActivity;

import butterknife.BindView;

public class ShowPicActivity extends BaseActivity {


    @BindView(R.id.show)
    ImageView show;

    String url;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_show_pic;
    }

    @Override
    protected void initView() {
        setTitle("显示图片");

        if (!TextUtils.isEmpty(url)){
            String str = url.substring(url.length()-3);
            if ("gif".equals(str)){
                Glide.with(mContext)
                        .load("http://"+url)
                        .asGif()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(show);
            }else {
                Glide.with(mContext)
                        .load("http://"+url)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(show);
            }

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getIntentData() {
        super.getIntentData();

        Intent intent = getIntent();
        if (intent != null){
            url = intent.getStringExtra("url");
        }
    }
}
