package com.cpcp.loto.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类，用于验证手机号，邮箱号，电话号码，是否符合格式
 */
public class CheckUtils {

    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 判断电话
     */
    public static boolean isTelephone(String phonenumber) {
        String phone = "0\\d{2,3}-\\d{7,8}";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }

    /**
     * 18位或者15位身份证验证 18位的最后一位可以是字母X(忽略大小写)
     *
     * @param text
     * @return
     */
    public static boolean isPersonIdValidation(String text) {
        String regx = "[0-9]{17}(?i)X";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        // text.matches(regx) || text.matches(reg1) || text.matches(regex);
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 车牌号正则表达式验证
     * 车牌号格式：汉字 + A-Z + 位A-Z或-
     * （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
     *
     * @return
     */
    public static boolean isCarNum(String carNum) {
        String carnumRegex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        if (TextUtils.isEmpty(carNum))
            return false;
        else
            return carNum.matches(carnumRegex);
    }
}

