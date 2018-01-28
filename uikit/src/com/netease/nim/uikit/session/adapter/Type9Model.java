package com.netease.nim.uikit.session.adapter;

/**
 * Created by jasmin on 2017/11/3.
 */

public class Type9Model {


    /**
     * sysMessageInfo : 中国天气网讯 昨天（28日），受较强冷空气影响，北方开启气温速降模式，局地降幅近20°C。今天，随着冷空气继续扩张，东北大部、华北中南部一直到华南地区都将有降温出现。此次过程，大范围地区的最低温将创下半年来新低，30日早晨太原将跌破0°C，武汉、合肥将不足10°C。
     * sysMessageTitle : 系统通知
     * sysMessageDetaiUrl : http://www.weather.com.cn/weather/101020100.shtml
     */

    private String sysMessageInfo;
    private String sysMessageTitle;
    private String sysMessageDetaiUrl;

    public String getSysMessageInfo() {
        return sysMessageInfo;
    }

    public void setSysMessageInfo(String sysMessageInfo) {
        this.sysMessageInfo = sysMessageInfo;
    }

    public String getSysMessageTitle() {
        return sysMessageTitle;
    }

    public void setSysMessageTitle(String sysMessageTitle) {
        this.sysMessageTitle = sysMessageTitle;
    }

    public String getSysMessageDetaiUrl() {
        return sysMessageDetaiUrl;
    }

    public void setSysMessageDetaiUrl(String sysMessageDetaiUrl) {
        this.sysMessageDetaiUrl = sysMessageDetaiUrl;
    }
}
