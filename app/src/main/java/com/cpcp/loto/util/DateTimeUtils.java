package com.cpcp.loto.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间戳转化工具
 */

public class DateTimeUtils {


    /**
     * 时间yyyy-MM-dd HH:mm:ss
     *
     * @param string 时间格式yyyy-MM-dd HH:mm:ss
     * @return yyyy-MM-dd HH:mm:ss格式日期+时间
     */
    public static String DataTimeConvertData(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(Long.parseLong(DateTimeConvertTimeStam(string))));
    }
    /**
     * 13位时间戳转换为DateTime时间yyyy-MM-dd HH:mm:ss
     *
     * @param string 时间戳
     * @return yyyy-MM-dd HH:mm:ss格式日期+时间
     */
    public static String timestampConvertDateTime(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(Long.parseLong(string)));
    }

    /**
     * 13位时间戳转换为DateTime时间yyyy-MM-dd
     *
     * @param string 时间戳
     * @return yyyy-MM-dd格式日期
     */
    public static String timestampConvertDate(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(Long.parseLong(string)));
    }

    /**
     * 13位时间戳转换为DateTime时间yyyy.MM.dd
     *
     * @param string 时间戳
     * @return yyyy.MM.dd格式日期
     */
    public static String timestampConvertDatePoint(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(new Date(Long.parseLong(string)));
    }

    /**
     * 13位时间戳转换为DateTime时间MM-dd
     *
     * @param string 时间戳
     * @returnMM-dd格式日期
     */
    public static String timestampConvertDateMonth(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(new Date(Long.parseLong(string)));
    }


    /**
     * 13位时间戳转换为DateTime时间yyyy/MM/dd
     *
     * @param string 时间戳
     * @return yyyy/MM/dd格式日期
     */
    public static String timestampConvertDate1(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(new Date(Long.parseLong(string)));
    }

    /**
     * 13位时间戳转换为DateTime时间yyyy/MM/dd
     *
     * @param string 时间戳
     * @return yyyy/MM格式日期
     */
    public static String timestampConvertDate3(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy/MM");
        return format.format(new Date(Long.parseLong(string)));
    }

    /**
     * 13位时间戳转换为DateTime时间yyyy.MM.dd
     *
     * @param string 时间戳
     * @returnyyyy.MM.dd格式日期
     */
    public static String timestampConvertDate2(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(new Date(Long.parseLong(string)));
    }

    /**
     * 13位时间戳转换为DateTime时间HH:mm:ss
     *
     * @param string 时间戳
     * @return HH:mm:ss格式时间
     */
    public static String timestampConvertTime(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date(Long.parseLong(string)));
    }


    /**
     * 13位时间戳转换为DateTime时间MM-dd HH:mm:ss
     *
     * @param string 时间戳
     * @return HH:mm:ss格式时间
     */
    public static String timestampConvertMonthTime(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        return format.format(new Date(Long.parseLong(string)));
    }

    /**
     * 日期时间格式为yyyy-MM-dd HH:mm:ss转换为13位时间戳
     *
     * @param dateTime 时间
     * @return 13位时间戳
     * @throws ParseException
     */
    static public String DateTimeConvertTimeStam(String dateTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(dateTime);
        return String.valueOf(date.getTime());
    }

    /**
     * 日期时间格式为yyyy-MM-dd转换为13位时间戳
     *
     * @param dateTime 时间
     * @return 13位时间戳
     * @throws ParseException
     */
    static public String DateConvertTimeStam(String dateTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateTime);
        return String.valueOf(date.getTime());
    }

    /**
     * 将Data 按yyyy-MM-dd HH:mm格式转化为时间字符串
     *
     * @param date
     * @return
     */
    public static String getDateYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    /**
     * 将Data 按yyyy-MM-dd HH:mm格式转化为时间字符串
     *
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 将Data 按yyyy-MM-dd HH:mm格式转化为时间字符串
     *
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return format.format(date);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getDayOfMonth(Date dt) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return day + "";
    }
}
