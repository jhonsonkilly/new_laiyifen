package com.netease.nim.demo.discount.model;

/**
 * Created by jasmin on 2017/11/19.
 */

public class YHQ003Model {


    /**
     * code : -1
     * message : 优惠券未分享或者已被领取
     * errMsg : null
     * data : 11100108
     * trace : 33!$9#@2%&10!$,0,63397435146829177231342
     */

    private String code;
    private String message;
    private Object errMsg;
    private String data;
    private String trace;

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

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
