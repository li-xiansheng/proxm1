package com.cpcp.loto.entity;

/**
 * 功能描述：论坛信息实体
 */

public class ForumEntity {


    /**
     * id : 3
     * full_name : 13981798782
     * email :
     * title : 111
     * msg : 222
     * createtime : 2017-04-11 09:35:37
     * status : 1
     * is_zhiding : 1
     * is_jinghua : 0
     * readnum : 0
     * qishu : 023期
     * countreply : 0
     */

    private String id;
    private String full_name;
    private String email;
    private String title;
    private String msg;
    private String createtime;
    private String status;
    private String is_zhiding;
    private String is_jinghua;
    private String readnum;
    private String qishu;
    private String countreply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_zhiding() {
        return is_zhiding;
    }

    public void setIs_zhiding(String is_zhiding) {
        this.is_zhiding = is_zhiding;
    }

    public String getIs_jinghua() {
        return is_jinghua;
    }

    public void setIs_jinghua(String is_jinghua) {
        this.is_jinghua = is_jinghua;
    }

    public String getReadnum() {
        return readnum;
    }

    public void setReadnum(String readnum) {
        this.readnum = readnum;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public String getCountreply() {
        return countreply;
    }

    public void setCountreply(String countreply) {
        this.countreply = countreply;
    }
}
