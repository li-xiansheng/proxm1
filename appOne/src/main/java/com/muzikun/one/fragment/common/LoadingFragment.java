package com.muzikun.one.fragment.common;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.muzikun.one.R;

/**
 * Created by likun on 16/7/23.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class LoadingFragment extends DialogFragment {
    private View mainView = null;
    private Dialog dialog = null;
    private static Bundle argus = null;
    public static final String BACK_SHADOW = "BACK_SHADOW";
    static {
        argus = new Bundle();
        argus.putBoolean(LoadingFragment.BACK_SHADOW,false);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView  = inflater.inflate(R.layout.dialog_fragment_loading,null);
        initConfig();

//        ImageView imageView = null;
//        imageView = (ImageView) mainView.findViewById(R.id.dialog_fragment_loading);
//        imageView.setAlpha(160);
//        dialog = getDialog();
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
//        animationDrawable.start();
        return mainView;
    }

    private void initConfig() {
        try {
            argus = getArguments();
        }catch (Exception e){
            argus.putBoolean(LoadingFragment.BACK_SHADOW,false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    private void initDialog() {
        dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        try {
            if (argus.getBoolean(LoadingFragment.BACK_SHADOW)) {
                lp.dimAmount = 0.3f;
            } else {
                lp.dimAmount = 0f;
            }
        }catch (Exception e){
            lp.dimAmount = 0f;
        }
        dialog.getWindow().setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);

    }


}
