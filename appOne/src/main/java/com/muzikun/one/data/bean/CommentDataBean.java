package com.muzikun.one.data.bean;

/**
 * Created by likun on 16/7/1.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class CommentDataBean {
    public String articleTitle;
    public String userName ;
    public String userIcon;
    public String commentContent;
    public String commentTime;

    public CommentDataBean(String articleTitle, String userName, String userIcon, String commentContent, String commentTime) {
        this.articleTitle = articleTitle;
        this.userName = userName;
        this.userIcon = userIcon;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }
}
