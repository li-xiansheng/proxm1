package com.muzikun.one.viewholder;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.muzikun.one.R;
import com.muzikun.one.data.bean.VideoBean;

/**
 * Created by chentao on 2017/8/7.
 */

public class VideoListViewHolder extends BaseViewHolder<VideoBean> {

    ImageView imgPicture;
    TextView title;

    public VideoListViewHolder(ViewGroup parent) {
        super(parent, R.layout.fragment_video_item);
        imgPicture = $(R.id.imageView);
        title = $(R.id.video_title);
        imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void setData(VideoBean data) {

//        ViewGroup.LayoutParams params = imgPicture.getLayoutParams();
//
//        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
//        int width = dm.widthPixels / 3;//宽度为屏幕宽度一半
////        int height = data.getHeight()*width/data.getWidth();//计算View的高度
//
//        params.height = (int) (width * 1.1);
//        imgPicture.setLayoutParams(params);
//        ImageLoader.load(getContext(), data.thumb, imgPicture);
        Log.i("VideoListFragment",data.litpic);
        Glide.with(getContext()).load(data.litpic).into(imgPicture);
        title.setText(data.title);
    }
}
