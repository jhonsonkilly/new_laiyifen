package com.ody.p2p.retrofit.store;

/**
 * Created by user on 2017/7/18.
 */

public class ReceiveCouponBean {


    /**
     * code : 0
     * message : 请求成功
     * data : {}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
