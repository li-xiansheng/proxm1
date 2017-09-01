package com.muzikun.lhfsyczl.view.search;

import com.muzikun.lhfsyczl.bean.TukuBean;

import java.util.Comparator;

/**
 * 用来对ListView中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
 */
public class PinyinComparator implements Comparator<TukuBean> {

    public int compare(TukuBean o1, TukuBean o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getFirstletter().equals("@")
                || o2.getFirstletter().equals("#")) {
            return -1;
        } else if (o1.getFirstletter().equals("#")
                || o2.getFirstletter().equals("@")) {
            return 1;
        } else {
            return o1.getFirstletter().compareTo(o2.getFirstletter());
        }
    }
}
