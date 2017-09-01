package com.muzikun.one.data.bean;

import android.support.v4.app.Fragment;

/**
 * Created by leeking on 16/6/1.
 */
public class NewViewPagerBean {
    public Fragment fragment = null;
    public String target  = null;
    public NewViewPagerBean(Fragment fragment , String target ){
        this.fragment = fragment;
        this.target = target;
    }
}
