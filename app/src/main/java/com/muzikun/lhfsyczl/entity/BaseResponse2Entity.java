package com.muzikun.lhfsyczl.entity;

/**
 * 功能描述：接口调用返回数据标准格式基类
 */

public class BaseResponse2Entity<T>{
    private Integer flag;
    private String errmsg;
    private T data;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
