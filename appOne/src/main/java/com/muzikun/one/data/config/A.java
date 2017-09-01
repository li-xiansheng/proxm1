package com.muzikun.one.data.config;

/**
 * Created by leeking on 16/5/31.
 */
public class A {
    private static final String BASE = Config.BASE_P + Config.p[12] + Config.p[28] + Config.p[5] + Config.p[21] + Config.p[1] + Config.API_STR;
    private static final String TYPE_LIST = Config.BASE_P + "api/getType.php";
    private static final String ARTICLE_LIST = Config.BASE_P + "api/getArticleList.php";
    //    private static final String ARTICLE_ITEM        = BASE+"Article/get/item/";
    private static final String ARTICLE_ITEM = BASE + "Article/get?item=";
    public static final String ADD_STOW = BASE + "Article/addstow/";
    public static final String ADD_ARTICLE = BASE + "User/addarticle/";

    private static final String STOW_LIST = BASE + "User/stow/";
    public static final String HAVE_STOW = BASE + "Article/isstow/";
    private static final String USER_MESSAGE = BASE + "User/message";
    private static final String USER_LOGIN = BASE + "user/login";
    public static final String GET_PAPE_TODAY = BASE + "Paper/gettoday";
    public static final String GET_PAPE = BASE + "Paper/getpager";
    public static final String GET_PAPE_IMAGE = BASE + "Paper/image";
    public static final String PAPE_PAGER_LIST = BASE + "Paper/getlist";
    public static final String GET_MY_ARTICLE = BASE + "User/getmyarticle";
    public static final String GET_INFO_BY_QQ = BASE + "User/qqgetinfo";
    public static final String GET_VIDEO_LIST = BASE + "Video/getlist";
    //    private static final String ARTICLE_COMMENT     = BASE+"Article/comment/aid/";
    private static final String ARTICLE_COMMENT = BASE + "Article/comment?aid=";
    private static final String PAGE_MAIN = "";

    public static final String SEND_COMMENT = BASE + "Article/addcomment/";
    public static final String USER_COMMENT = BASE + "User/comment";
    public static final String SET_USER_ICON = BASE + "User/setface";
    public static final String SET_USER_NAME = BASE + "User/setinfo";
    public static final String SEND_SMS_CODE = BASE + "User/phoneregister";
    public static final String SEND_USER_PASS = BASE + "User/upuserphoneinfo";


    public static final String SEND_NEWS = BASE + "Article/getquestion";
    public static final String SEND_QUESTION = BASE + "Article/addquestion";

    public static final String QQ_GET_USER_INFO = "https://graph.qq.com/user/get_user_info";


    public static String getArticleItem(int id) {
        return ARTICLE_ITEM + id;
    }

    public static String getUserMessage() {
        return USER_MESSAGE;
    }

    public static String getArticleList() {
        return ARTICLE_LIST;
    }

    public static String getArticleItem() {
        return ARTICLE_ITEM;
    }

    public static String getUserLogin() {
        return USER_LOGIN;
    }

    public static String getArticleComment(int aid, int page) {
//        return ARTICLE_COMMENT + aid + "/p/" + page ;
        return ARTICLE_COMMENT + aid + "&p=" + page;
    }

    public static String getArticleComment(int aid, int page, int total) {
        return ARTICLE_COMMENT + aid + "/p/" + page + "/total/" + total;
    }

    public static String getArticleComment() {
        return ARTICLE_COMMENT + 0;
    }

    public static String getPageMain() {
        return PAGE_MAIN;
    }

    public static String getTypeList() {
        return TYPE_LIST;
    }

    public static String getStowList(int userId) {
        return STOW_LIST + "userid/" + userId;
    }

    public static String getGetVideoList(int page) {
        return GET_VIDEO_LIST + "?p=" + page;
    }

    public static String getGetVideoList() {
        return GET_VIDEO_LIST + "?p=" + String.valueOf(1);
    }

    public static String getAnswerInfo(String id) {
        return BASE + "Article/showquestion?id=" + id;
    }

    public static String getJiangZuoVideo(String page) {
        return BASE + "Video/getlist?p=" + page;
    }

    public static String getClassfyVideo(String page) {
        return BASE + "Video/getlist2?p=" + page;
    }
}
