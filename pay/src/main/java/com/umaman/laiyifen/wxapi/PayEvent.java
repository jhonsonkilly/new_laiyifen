package com.umaman.laiyifen.wxapi;

/**
 * Created by Sun on 2016/10/13.
 */

public class PayEvent {
    private int code;

    public PayEvent(int code){
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
