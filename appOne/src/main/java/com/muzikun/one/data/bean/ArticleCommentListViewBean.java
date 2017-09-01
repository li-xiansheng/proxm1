package com.muzikun.one.data.bean;

/**
 * Created by likun on 16/7/9.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class ArticleCommentListViewBean {
    public int COMMENT_ID;
    public String USER_ICON;
    public String USER_NAME;
    public String TIME_STRING;
    public int GOOD;
    public String CONTENT;

    public ArticleCommentListViewBean(int COMMENT_ID,String USER_NAME,String USER_ICON, String TIME_STRING, int GOOD, String CONTENT) {
        this.COMMENT_ID = COMMENT_ID;
        this.USER_NAME = USER_NAME;
        this.USER_ICON = USER_ICON;
        this.TIME_STRING = TIME_STRING;
        this.GOOD = GOOD;
        this.CONTENT = CONTENT;
    }
}
