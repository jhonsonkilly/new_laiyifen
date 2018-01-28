package com.netease.nim.demo.yidou.model;

/**
 * @author SevenCheng
 */

public class YdouListModel {
    public String  num;
    public boolean isChecked;

    public YdouListModel(String num, boolean isChecked) {
        this.num = num;
        this.isChecked = isChecked;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
