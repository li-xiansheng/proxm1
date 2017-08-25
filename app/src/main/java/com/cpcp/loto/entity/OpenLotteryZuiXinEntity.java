package com.cpcp.loto.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 功能描述：开奖直播最新实体信息
 */

public class OpenLotteryZuiXinEntity {


    /**
     * 日期 : 2017-08-25
     * 期数 : 083期
     * code1 : 10
     * code2 : 30
     * code3 : 29
     * code4 : 30
     * code5 : 21
     * code6 :
     * 特码 :
     * shengxiao : ["鼠","龙","蛇","龙","牛","猪","猪","鼠","龙","蛇","龙","牛","猪","猪"]
     */
    @JSONField(name = "日期")
    private String date;
    @JSONField(name = "期数")
    private String no;

    @JSONField(name = "号码1")
    private String code1;
    @JSONField(name = "号码2")
    private String code2;
    @JSONField(name = "号码3")
    private String code3;
    @JSONField(name = "号码4")
    private String code4;
    @JSONField(name = "号码5")
    private String code5;
    @JSONField(name = "号码6")
    private String code6;
    @JSONField(name = "特码")
    private String tema;

    private List<String> haoma;
    private List<String> shengxiao;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public String getCode4() {
        return code4;
    }

    public void setCode4(String code4) {
        this.code4 = code4;
    }

    public String getCode5() {
        return code5;
    }

    public void setCode5(String code5) {
        this.code5 = code5;
    }

    public String getCode6() {
        return code6;
    }

    public void setCode6(String code6) {
        this.code6 = code6;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public List<String> getHaoma() {
        return haoma;
    }

    public void setHaoma(List<String> haoma) {
        this.haoma = haoma;
    }

    public List<String> getShengxiao() {
        return shengxiao;
    }

    public void setShengxiao(List<String> shengxiao) {
        this.shengxiao = shengxiao;
    }
}
