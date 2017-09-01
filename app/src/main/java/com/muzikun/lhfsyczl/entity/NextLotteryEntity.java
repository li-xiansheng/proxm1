package com.muzikun.lhfsyczl.entity;

/**
 * 功能描述：下次开奖实体信息
 */

public class NextLotteryEntity {


    /**
     * option_id : 9
     * option_name : 下次开奖
     * option_value : 2017-10-26
     * autoload : 0
     */

    private String option_id;
    private String option_name;
    private String option_value;
    private String autoload;

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_value() {
        return option_value;
    }

    public void setOption_value(String option_value) {
        this.option_value = option_value;
    }

    public String getAutoload() {
        return autoload;
    }

    public void setAutoload(String autoload) {
        this.autoload = autoload;
    }
}
