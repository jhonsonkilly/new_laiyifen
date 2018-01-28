package com.netease.nim.uikit.recent.model;

/**
 * Created by jasmin on 2017/11/30.
 */

public class Yiservice {
    String dateTime;
    String contentStr;
    int unReadNum;

    public Yiservice(String dateTime, String contentStr, int unReadNum) {
        this.dateTime = dateTime;
        this.contentStr = contentStr;
        this.unReadNum = unReadNum;
    }

    public Yiservice() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }
}
