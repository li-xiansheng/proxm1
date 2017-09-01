package com.muzikun.one.data.bean;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by chentao on 2017/8/3.
 */

public class SendNewsBean {

    public String id;
    public String title;
    public String name;
    public String time;
    public String answerCount;
    public String headPath;

    public static Comparator<SendNewsBean> mAscComparator = new Comparator<SendNewsBean>() {

        @Override
        public int compare(SendNewsBean lhs, SendNewsBean rhs) {
            return Collator.getInstance().compare(rhs.time, lhs.time);
        }
    };
}
