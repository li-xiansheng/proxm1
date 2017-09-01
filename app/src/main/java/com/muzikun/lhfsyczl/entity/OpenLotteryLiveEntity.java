package com.muzikun.lhfsyczl.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 功能描述：开奖直播实体信息
 */

public class OpenLotteryLiveEntity {


    /**
     * 日期 : 2017-04-15
     * 期数 : 044期
     * nextqishu : 045期
     * haoma : ["01","29","33","04","05","44","39"]
     * yanse : ["hong","hong","lv","lan","lv","lv","lv"]
     * shengxiao : ["鸡","蛇","牛","马","蛇","虎","羊"]
     * notice : 香港六合彩官方合作伙伴，最权威的开奖结果，最先公布的平台，最大的六合彩高手心水发布平台，最大的彩民交流平台...更多功能等你体验
     * toubu : [{"slide_pic":"http://lac.83gw.com/data/upload/admin/20170502/59083c8ff3576.jpg","slide_url":"http://www.baidu.com"},{"slide_pic":"http://lac.83gw.com/data/upload/admin/20170502/59083cb354886.jpg","slide_url":"http://www.alipay.com"}]
     * zhongbu : [{"slide_pic":"http://lac.83gw.com/data/upload/admin/20170502/59083ccee552c.jpg","slide_url":"http://www.baidu.com"}]
     * recommend : [[{"catname":"必中10码","qishu":"2017041","content":"26,13,43,35,08,38,27,07,31,49,29","status":"0"}],[{"catname":"正一","qishu":"2017040","content":"大","status":"1"},{"catname":"正二","qishu":"2017040","content":"小","status":"1"}],[{"catname":"推荐3肖中平码","qishu":"2017039","content":"鼠，猪，龙，多少买点。赚点小钱！","status":"1"}]]
     * nextopentime : 第2017045期 04月18日21时30分 星期二
     * timeprompt : 1492522200
     */

    @JSONField(name = "日期")
    private String date;
    @JSONField(name = "期数")
    private String no;

    private String nextqishu;
    private String notice;
    private String nextopentime;
    private String timeprompt;
    private List<String> haoma;
    private List<String> yanse;
    private List<String> shengxiao;
    private List<ToubuBean> toubu;
    private List<ZhongbuBean> zhongbu;
    private List<List<RecommendBean>> recommend;

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

    public String getNextqishu() {
        return nextqishu;
    }

    public void setNextqishu(String nextqishu) {
        this.nextqishu = nextqishu;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getNextopentime() {
        return nextopentime;
    }

    public void setNextopentime(String nextopentime) {
        this.nextopentime = nextopentime;
    }

    public String getTimeprompt() {
        return timeprompt;
    }

    public void setTimeprompt(String timeprompt) {
        this.timeprompt = timeprompt;
    }

    public List<String> getHaoma() {
        return haoma;
    }

    public void setHaoma(List<String> haoma) {
        this.haoma = haoma;
    }

    public List<String> getYanse() {
        return yanse;
    }

    public void setYanse(List<String> yanse) {
        this.yanse = yanse;
    }

    public List<String> getShengxiao() {
        return shengxiao;
    }

    public void setShengxiao(List<String> shengxiao) {
        this.shengxiao = shengxiao;
    }

    public List<ToubuBean> getToubu() {
        return toubu;
    }

    public void setToubu(List<ToubuBean> toubu) {
        this.toubu = toubu;
    }

    public List<ZhongbuBean> getZhongbu() {
        return zhongbu;
    }

    public void setZhongbu(List<ZhongbuBean> zhongbu) {
        this.zhongbu = zhongbu;
    }

    public List<List<RecommendBean>> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<List<RecommendBean>> recommend) {
        this.recommend = recommend;
    }

    public static class ToubuBean {
        /**
         * slide_pic : http://lac.83gw.com/data/upload/admin/20170502/59083c8ff3576.jpg
         * slide_url : http://www.baidu.com
         */

        private String slide_pic;
        private String slide_url;

        public String getSlide_pic() {
            return slide_pic;
        }

        public void setSlide_pic(String slide_pic) {
            this.slide_pic = slide_pic;
        }

        public String getSlide_url() {
            return slide_url;
        }

        public void setSlide_url(String slide_url) {
            this.slide_url = slide_url;
        }
    }

    public static class ZhongbuBean {
        /**
         * slide_pic : http://lac.83gw.com/data/upload/admin/20170502/59083ccee552c.jpg
         * slide_url : http://www.baidu.com
         */

        private String slide_pic;
        private String slide_url;

        public String getSlide_pic() {
            return slide_pic;
        }

        public void setSlide_pic(String slide_pic) {
            this.slide_pic = slide_pic;
        }

        public String getSlide_url() {
            return slide_url;
        }

        public void setSlide_url(String slide_url) {
            this.slide_url = slide_url;
        }
    }

    public static class RecommendBean {
        /**
         * catname : 必中10码
         * qishu : 2017041
         * content : 26,13,43,35,08,38,27,07,31,49,29
         * status : 0
         */

        private String catname;
        private String qishu;
        private String content;
        private String status;

        public String getCatname() {
            return catname;
        }

        public void setCatname(String catname) {
            this.catname = catname;
        }

        public String getQishu() {
            return qishu;
        }

        public void setQishu(String qishu) {
            this.qishu = qishu;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
