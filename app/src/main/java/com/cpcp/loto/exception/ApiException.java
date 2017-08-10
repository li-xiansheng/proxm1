package com.cpcp.loto.exception;

/**
 * 功能描述：自定义接口异常--处理提示网络请求中异常信息
 */

public class ApiException extends Exception{
    /**
     * 异常错误码
     */
    public int code;
    /**
     * 异常信息提示
     */
    public String msg;

    /**
     * 构造异常信息
     * @param throwable
     * @param code
     */
    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.msg=throwable.getMessage();
        this.code = code;

    }



}
