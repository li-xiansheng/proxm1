package com.muzikun.lhfsyczl.entity;

import cn.bmob.v3.BmobObject;

/**
 * 功能描述：继承Bmob对象默认字段，
 */

public class WebFlag extends BmobObject{

    private boolean flag;//是否能访问

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
