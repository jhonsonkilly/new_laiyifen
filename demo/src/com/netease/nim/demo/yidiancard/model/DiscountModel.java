package com.netease.nim.demo.yidiancard.model;

/**
 * @author SevenCheng
 */

public class DiscountModel {

    public boolean isShow;

    public DiscountModel(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }


    public void setShow(boolean show) {
        isShow = show;
    }
}
