package com.muzikun.one.data.bean;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by chentao on 2017/8/3.
 */

public class VideoBean {

    public String id;
    public String title;
    public String litpic;
    public String time;
    public String youku;

    public static Comparator<VideoBean> mAscComparator = new Comparator<VideoBean>() {

        @Override
        public int compare(VideoBean lhs, VideoBean rhs) {
            return Collator.getInstance().compare(rhs.time, lhs.time);
        }
    };
}
