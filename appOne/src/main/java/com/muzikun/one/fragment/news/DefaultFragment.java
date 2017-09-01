package com.muzikun.one.fragment.news;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.muzikun.one.R;

/**
 * Created by leeking on 16/6/1.
 */
public class DefaultFragment extends Fragment {
    private View mainView = null;
    private ImageView imageView = null;
    private AnimationDrawable animationDrawable = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.item_fragment_article_list_default,container ,false);
        imageView = (ImageView) mainView.findViewById(R.id.fragment_article_default_loading);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();

        return mainView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            animationDrawable.start();
        }
    }
}
