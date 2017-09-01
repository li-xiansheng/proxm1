package com.muzikun.lhfsyczl.util;

import android.support.v7.widget.AppCompatTextView;

import com.muzikun.lhfsyczl.R;


/**
 * 功能描述：开奖求背景设置
 */

public class BallColorUtil {
    public static void ballColor(String num, AppCompatTextView textView) {
        if ("1".equals(num) || "01".equals(num) || "2".equals(num) || "02".equals(num) ||
                "7".equals(num) || "07".equals(num) || "8".equals(num) || "08".equals(num)
                || "12".equals(num) || "13".equals(num) || "18".equals(num) || "19".equals(num)
                || "23".equals(num) || "24".equals(num) || "29".equals(num) || "30".equals(num)
                || "34".equals(num) || "35".equals(num) || "40".equals(num) || "45".equals(num)
                || "46".equals(num)) {
            textView.setBackgroundResource(R.drawable.redball);
        } else if ("3".equals(num) || "03".equals(num) || "4".equals(num) || "04".equals(num) ||
                "9".equals(num) || "09".equals(num) || "10".equals(num)
                || "14".equals(num) || "15".equals(num) || "20".equals(num) || "25".equals(num)
                || "26".equals(num) || "31".equals(num) || "36".equals(num) || "36".equals(num)
                || "41".equals(num) || "42".equals(num) || "47".equals(num) || "48".equals(num)) {
            textView.setBackgroundResource(R.drawable.blueball);
        } else {
            textView.setBackgroundResource(R.drawable.greenball);
        }
    }
}
