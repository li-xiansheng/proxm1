package com.muzikun.lhfsyczl.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.uihelper.LoadingDialog;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.ToastUtils;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

public class ShowPicActivity extends BaseActivity {


    /* @BindView(R.id.show)
     ImageView show;
 */
    @BindView(R.id.photo_view)
    PhotoView photo_view;
    String url;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_show_pic;
    }

    @Override
    protected void initView() {
        setTitle("显示图片");

        if (!TextUtils.isEmpty(url)) {
//            String str = url.substring(url.length() - 3);
//            if (!TextUtils.isEmpty(str) && str.endsWith("gif")) {
//                Glide.with(mContext)
//                        .load("http://" + url)
//                        .asGif()
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .into(photo_view);
//            } else {
            if (!(url.startsWith("http"))) {
                url = "http://" + url;
                LogUtils.i(TAG,"添加");
            }else{
                LogUtils.i(TAG,"不添加");
            }
                LoadingDialog.showDialog(mActivity);
                Glide.with(mContext)
                        .load(url)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                LoadingDialog.closeDialog(mActivity);
                                ToastUtils.show("图片加载失败");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                LoadingDialog.closeDialog(mActivity);
                                return false;
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(photo_view);
//            }

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getIntentData() {
        super.getIntentData();

        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
        }
    }
}
