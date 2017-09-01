package com.muzikun.lhfsyczl.entity;

/**
 * 功能描述：用户基本信息
 */

public class UserInfoEntity {


    /**
     * user_nicename : 李先生
     * mobile : 15282896429
     * avatar : ad.076668.com/data/upload/avatar/avatar.png
     * score : 3456
     */
    private String id;
    private String user_nicename;
    private String mobile;
    private String avatar;
    private String score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
