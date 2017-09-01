package com.muzikun.lhfsyczl.view.htmltextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


/**
 * 功能描述：
 */

public class UrlImageGetter implements Html.ImageGetter {
    Context c;
    TextView container;
    int width;

    /**
     * @param t
     * @param c
     */
    public UrlImageGetter(TextView t, Context c) {
        this.c = c;
        this.container = t;
        width = c.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public Drawable getDrawable(String source) {
//        final UrlDrawable urlDrawable = new UrlDrawable();
//        ImageLoader.getInstance().loadImage(source,
//                new SimpleImageLoadingListener() {
//                    @Override
//                    public void onLoadingComplete(String imageUri, View view,
//                                                  Bitmap loadedImage) {
//                        // 计算缩放比例
//                        float scaleWidth = ((float) width) / loadedImage.getWidth();
//
//                        // 取得想要缩放的matrix参数
//                        Matrix matrix = new Matrix();
//                        matrix.postScale(scaleWidth, scaleWidth);
//                        loadedImage = Bitmap.createBitmap(loadedImage, 0, 0,
//                                loadedImage.getWidth(), loadedImage.getHeight(),
//                                matrix, true);
//                        urlDrawable.bitmap = loadedImage;
//                        urlDrawable.setBounds(0, 0, loadedImage.getWidth(),
//                                loadedImage.getHeight());
//                        container.invalidate();
//                        container.setText(container.getText()); // ??????
//                    }
//                });
//        return urlDrawable;

        final UrlDrawable urlDrawable = new UrlDrawable();
        Glide.with(c).load(source)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> glideAnimation) {

                        // 计算缩放比例
                        float scaleWidth = ((float) width) / loadedImage.getWidth();

                        // 取得想要缩放的matrix参数
                        Matrix matrix = new Matrix();
                        matrix.postScale(scaleWidth, scaleWidth);
                        loadedImage = Bitmap.createBitmap(loadedImage, 0, 0,
                                loadedImage.getWidth(), loadedImage.getHeight(),
                                matrix, true);
                        urlDrawable.bitmap = loadedImage;
                        urlDrawable.setBounds(0, 0, loadedImage.getWidth(),
                                loadedImage.getHeight());
                        container.invalidate();
                        container.setText(container.getText()); // 解决图文重叠

                    }
                });

        return urlDrawable;

    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
