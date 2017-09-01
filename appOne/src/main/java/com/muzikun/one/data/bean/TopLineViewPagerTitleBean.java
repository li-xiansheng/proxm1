package com.muzikun.one.data.bean;

/**
 * Created by muzikun on 2016-04-26.
 */
public class TopLineViewPagerTitleBean {
    private String name = null;
    private int cid = 0;
    public TopLineViewPagerTitleBean(String name, int cid){
        this.name = name;
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public int getCid() {
        return cid;
    }

}
