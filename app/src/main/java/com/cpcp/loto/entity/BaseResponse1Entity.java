package com.cpcp.loto.entity;

/**
 * 功能描述：接口调用返回数据标准格式基类
 */

public class BaseResponse1Entity<T>{
    private String result;
    private T data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
