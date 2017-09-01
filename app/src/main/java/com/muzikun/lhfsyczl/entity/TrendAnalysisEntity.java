package com.muzikun.lhfsyczl.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 功能描述：走势分析--生肖
 */

public class TrendAnalysisEntity {

    /**
     * date : 2017-04-15
     * no : 044
     * tema : 39
     * shengxiao : 羊
     * yanse : lv
     *
     * "wei": "0",
     "daxiao": "小",
     wuxing :土
     */
    @JSONField(name="日期")
    private String date;
    @JSONField(name="期数")
    private String no;
    @JSONField(name="特码")
    private String tema;
    private String shengxiao;
    private String yanse;
    private String danshuang;
    private String wei;
    private String daxiao;
    private String wuxing;

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

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getShengxiao() {
        return shengxiao;
    }

    public void setShengxiao(String shengxiao) {
        this.shengxiao = shengxiao;
    }


    public String getDanshuang() {
        return danshuang;
    }

    public void setDanshuang(String danshuang) {
        this.danshuang = danshuang;
    }

    public String getYanse() {
        return yanse;
    }

    public void setYanse(String yanse) {
        this.yanse = yanse;
    }

    public String getWei() {
        return wei;
    }

    public void setWei(String wei) {
        this.wei = wei;
    }

    public String getDaxiao() {
        return daxiao;
    }

    public void setDaxiao(String daxiao) {
        this.daxiao = daxiao;
    }

    public String getWuxing() {
        return wuxing;
    }

    public void setWuxing(String wuxing) {
        this.wuxing = wuxing;
    }
}
