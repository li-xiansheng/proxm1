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

    @Override
    public String toString() {
        return "CurrentRecommendBean{" +
                "id='" + id + '\'' +
                ", leixing='" + leixing + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
