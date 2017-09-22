package com.muzikun.lhfsyczl.bean;

/**
 * Created by chentao on 2017/8/15.
 */

public class CurrentRecommendBean {

//    "fail": 1,
//            "liansheng": 0,
//            "total": 1,
//            "shenglv": 0,
//            "xinshui": {
//        "id": "14",
//                "title": "预测大小哦",
//                "points": "2",
//                "type": "1",
//                "numbertype": "正码一",
//                "createtime": "2017-04-17 20:23:55",
//                "qishu": "2017-04-18",
//                "username": "13981798782",
//                "forecast": "小",
//                "periods": "045期",
//                "readnum": "0",
//                "desc": "大小"
//    },
//            "success": 0,
//            "is_read": 0
    public String id;
    public String leixing;
    public String liangsheng;
    public String fail;
    public String total;
    public String shenglv;
    public String title;
    public String points;
    public String desc;
    public String readnum;
    public String username;
    public String is_read;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getLiangsheng() {
        return liangsheng;
    }

    public void setLiangsheng(String liangsheng) {
        this.liangsheng = liangsheng;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getShenglv() {
        return shenglv;
    }

    public void setShenglv(String shenglv) {
        this.shenglv = shenglv;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReadnum() {
        return readnum;
    }

    public void setReadnum(String readnum) {
        this.readnum = readnum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }
}
