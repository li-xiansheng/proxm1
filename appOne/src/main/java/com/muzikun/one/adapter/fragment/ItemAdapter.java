package com.muzikun.one.adapter.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by likun on 16/7/15.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class ItemAdapter extends BaseAdapter {
    private Context context = null;
    private String[] titles = null;

    public ItemAdapter(Context context,String[] titles) {
        this.titles = titles;
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        textView.setLayoutParams(params);
        textView.setPadding(60,30,60,30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
        textView.setText(titles[i]);
        return textView;
    }
}
