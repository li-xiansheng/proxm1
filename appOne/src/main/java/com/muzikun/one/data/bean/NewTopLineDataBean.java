package com.muzikun.one.data.bean;

/**
 * Created by leeking on 16/6/3.
 */
public class NewTopLineDataBean {
    public int TYPE_ID;
    public String TYPE_NAME;
    public int WINDOW_TYPE = 0;

    public NewTopLineDataBean(int TYPE_ID, String TYPE_NAME) {
        this.TYPE_ID = TYPE_ID;
        this.TYPE_NAME = TYPE_NAME;
    }
    public NewTopLineDataBean(int TYPE_ID, String TYPE_NAME,int WINDOW_TYPE) {
        this.TYPE_ID = TYPE_ID;
        this.TYPE_NAME = TYPE_NAME;
        this.WINDOW_TYPE = WINDOW_TYPE;
    }
}
