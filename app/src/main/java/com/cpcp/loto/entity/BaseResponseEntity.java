package com.cpcp.loto.entity;

/**
 * 功能描述：接口调用返回数据标准格式基类
 */

public class BaseResponseEntity<T>{
    private int status;
    private T results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
