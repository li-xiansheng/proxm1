package com.cpcp.loto.view.convenientbanner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.cpcp.loto.R;

/**
 * 功能描述：
 */

public class NetImageHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
//        imageView.setImageResource(data);
        Glide.with(context)
                .load(data)
                .placeholder(R.drawable.img_banner_ad0)
                .into(imageView);
    }
}
