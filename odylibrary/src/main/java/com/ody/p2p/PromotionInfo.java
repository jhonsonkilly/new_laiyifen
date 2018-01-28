package com.ody.p2p;

/**
 * Created by pzy on 2016/10/20.
 */
public class PromotionInfo {
    public String iconText;
    public String iconTxt;
    public String iconUrl;
    int type;
    int checkType;
    long countdown;
    String id;
    String price;
    int isCountDown;

    public String getIconTxt() {
        return iconTxt;
    }

    public void setIconTxt(String iconTxt) {
        this.iconTxt = iconTxt;
    }

    public int getIsCountDown() {
        return isCountDown;
    }

    public void setIsCountDown(int isCountDown) {
        this.isCountDown = isCountDown;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCheckType() {
        return checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIconText() {
        return iconText;
    }

    public void setIconText(String iconText) {
        this.iconText = iconText;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
