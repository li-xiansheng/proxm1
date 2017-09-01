package com.muzikun.one.adapter.fragment;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.muzikun.one.R;

/**
 * Created by leeking on 16/6/6.
 */
public class FragmentArticleListDefaultAdapter extends BaseAdapter {
    private int count = 0;

    public FragmentArticleListDefaultAdapter(Context context, int count ) {
        this.count = count;
        this.context = context;
    }

    private Context context;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view  = LayoutInflater.from(context).inflate(R.layout.item_fragment_article_list_default,null);
        ImageView imageView = null;
        imageView = (ImageView) view.findViewById(R.id.fragment_article_default_loading);
        imageView.setAlpha(160);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        return view;
    }
}
