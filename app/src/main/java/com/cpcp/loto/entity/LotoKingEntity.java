package com.cpcp.loto.entity;

import java.util.List;

/**
 * 功能描述：-高手资料-六合王实体信息
 */

public class LotoKingEntity {


    /**
     * bool : [1]
     * success : 1
     * total : 1
     * shenglv : 100
     * userinfo : {"user_nicename":"","mobile":"18615773227","avatar":"lac.83gw.comdata/upload/avatar/"}
     * liansheng : 1
     * fail : 0
     */

    private String success;
    private String total;
    private String shenglv;
    private UserinfoBean userinfo;
    private String liansheng;
    private String fail;
    private List<Integer> bool;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
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

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public String getLiansheng() {
        return liansheng;
    }

    public void setLiansheng(String liansheng) {
        this.liansheng = liansheng;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public List<Integer> getBool() {
        return bool;
    }

    public void setBool(List<Integer> bool) {
        this.bool = bool;
    }

    public static class UserinfoBean {
        /**
         * user_nicename :
         * mobile : 18615773227
         * avatar : lac.83gw.comdata/upload/avatar/
         */

        private String user_nicename;
        private String mobile;
        private String avatar;

        public String getUser_nicename() {
            return user_nicename;
        }

        public void setUser_nicename(String user_nicename) {
            this.user_nicename = user_nicename;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
