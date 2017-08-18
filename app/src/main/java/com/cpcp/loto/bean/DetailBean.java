package com.cpcp.loto.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chentao on 2017/8/15.
 */

public class DetailBean {

    public String desc;
    public List<String> result = new ArrayList<>();
    public String avatar;
    public String user_nicename;
    public String addtime;

    @Override
    public String toString() {
        return "DetailBean{" +
                "desc='" + desc + '\'' +
                ", avatar='" + avatar + '\'' +
                ", user_nicename='" + user_nicename + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
