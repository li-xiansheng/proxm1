package com.muzikun.one.data.bean;

/**
 * Created by leeking on 16/6/6.
 */
public class ArticleListListViewBean {
    public String title = null;
    public String addtime = null;
    public String source = null;
    public String[] pic = null;
    public int pic_total = 0;
    public int articleId = 0;

    public ArticleListListViewBean(String title, String addtime, String source, String[] pic, int pic_total, int articleId) {
        this.title = title;
        this.addtime = addtime;
        this.source = source;
        this.pic = pic;
        this.pic_total = pic_total;
        this.articleId = articleId;
    }




}
