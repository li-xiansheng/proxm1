package com.muzikun.one.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by likun on 16/8/20.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class ScrollViewExpand extends ScrollView{

    private OnScrollExpandChangeListener onScrollExpandChangeListener = null;

    public ScrollViewExpand(Context context) {
        super(context);
    }

    public ScrollViewExpand(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewExpand(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollExpandChangeListener!=null){
            onScrollExpandChangeListener.onChange(l, t, oldl, oldt);
        }
    }

    public OnScrollExpandChangeListener getOnScrollExpandChangeListener() {
        return onScrollExpandChangeListener;
    }

    public void setOnScrollExpandChangeListener(OnScrollExpandChangeListener onScrollExpandChangeListener) {
        this.onScrollExpandChangeListener = onScrollExpandChangeListener;
    }

    public interface OnScrollExpandChangeListener{
       void onChange(int l, int t, int oldl, int oldt);
    }
}
