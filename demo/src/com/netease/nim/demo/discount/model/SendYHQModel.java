package com.netease.nim.demo.discount.model;

/**
 * @author SevenCheng
 */

public class SendYHQModel {

    /**
     * msg : 操作成功
     * result : true
     * status : 1
     * success : true
     * validData : true
     */

    private String msg;
    private boolean result;
    private String  status;
    private boolean success;
    private boolean validData;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isValidData() {
        return validData;
    }

    public void setValidData(boolean validData) {
        this.validData = validData;
    }
}
