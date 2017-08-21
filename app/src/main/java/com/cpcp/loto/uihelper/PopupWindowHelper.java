package com.cpcp.loto.uihelper;

import android.app.Activity;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.cpcp.loto.R;
import com.cpcp.loto.entity.TimePickerEntity;
import com.cpcp.loto.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lichuanbei on 2016/11/4.
 * <p>
 * 弹出框帮助类，处理公用弹出事件
 */

public class PopupWindowHelper {

    /**
     * 选择时间返回年
     *
     * @param tvTime
     */
    public static void selectYear(Activity activity, final TextView tvTime) {
        //时间选择器
        TimePickerView pvTime = new TimePickerView(activity, TimePickerView.Type.YEAR);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(2016, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTitle("选择年份");
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.show();
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvTime.setText(DateTimeUtils.getDateYear(date));
                tvTime.setTag(0);
            }
        });
    }

    /**
     * 选择时间--返回日期
     *
     * @param tvTime
     */
    public static void selectTime(Activity activity, final TextView tvTime) {
        //时间选择器
        TimePickerView pvTime = new TimePickerView(activity, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 10, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTitle("选择时间");
        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        pvTime.show();
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvTime.setText(DateTimeUtils.getDate(date));
                tvTime.setTag(0);
            }
        });
    }

    /**
     * 弹出框选择方量
     */
    public static void selectLoad(Activity activity, final TextView tvLoad) {
        OptionsPickerView pvOptions;
        //选项选择器
        pvOptions = new OptionsPickerView(activity);
        final ArrayList<String> options1Items = new ArrayList<>();
        for (int i = 8; i <= 25; i++) {
            options1Items.add(i + "");
        }
        //选择
        pvOptions.setPicker(options1Items);
        //设置选择的三级单位
        pvOptions.setTitle("选择方量");
        pvOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(7);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String load = options1Items.get(options1);
                tvLoad.setText(load);
            }
        });
        pvOptions.show();
    }

    /**
     * 单选一个数据，获取id，和名称，最终
     *
     * @param activity
     * @param entities
     * @param textView 最终显示在控件上，同事赋给该控件选择项的tag
     * @param title
     */
    public static void selectOneData(Activity activity, final List<TimePickerEntity> entities, final TextView textView, String title) {
        OptionsPickerView pvOptions;
        //选项选择器
        pvOptions = new OptionsPickerView(activity);
        //选择
        final ArrayList<TimePickerEntity> entities1 = (ArrayList<TimePickerEntity>) entities;
        pvOptions.setPicker(entities1);

        //设置选择名称
        pvOptions.setTitle(title == null ? "" : title);
        pvOptions.setCyclic(false);
        //监听确定选择按钮
        pvOptions.setSelectOptions(0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String tvName = entities1.get(options1).getPickerViewText();
                String id = entities1.get(options1).getId() + "";
                textView.setText(tvName);
                textView.setTag(id);
                textView.setTag(R.id.tag, entities1.get(options1).getEntity());


            }
        });
        pvOptions.show();

    }

    /**
     * 单选一个数据，获取id，和名称，最终
     *
     * @param activity
     * @param entities
     * @param textView 最终显示在控件上，同事赋给该控件选择项的tag
     * @param title
     */
    public static void selectOneStrData(Activity activity, final List<String> entities, final TextView textView, String title) {
        OptionsPickerView pvOptions;
        //选项选择器
        pvOptions = new OptionsPickerView(activity);
        //选择
        final ArrayList<String> entities1 = (ArrayList<String>) entities;
        pvOptions.setPicker(entities1);

        //设置选择名称
        pvOptions.setTitle(title == null ? "" : title);
        pvOptions.setCyclic(false);
        //监听确定选择按钮
        pvOptions.setSelectOptions(0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String tvName = entities1.get(options1);
                textView.setText(tvName);

            }
        });
        pvOptions.show();

    }


}
