package com.cpcp.loto.entity;

import cn.bmob.v3.BmobObject;

/**
 * 功能描述：继承Bmob对象默认字段，
 */

public class WebFlag extends BmobObject{
    private String url;//访问地址
    private boolean flag;//是否能访问

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
