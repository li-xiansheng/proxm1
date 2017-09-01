package com.muzikun.one.data.bean;

/**
 * Created by likun on 16/6/27.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class MessageDataBean {
    public String TITLE ;
    public String DESCRIPTION ;
    public String TIME ;
    public int IMAGE ;


    public MessageDataBean(String TITLE, String DESCRIPTION, String TIME, int IMAGE) {
        this.TITLE = TITLE;
        this.DESCRIPTION = DESCRIPTION;
        this.TIME = TIME;
        this.IMAGE = IMAGE;
    }

}
