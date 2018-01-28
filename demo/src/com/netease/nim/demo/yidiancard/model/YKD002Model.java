package com.netease.nim.demo.yidiancard.model;

/**
 * @author SevenCheng
 */

public class YKD002Model {

    /**
     * msg : 操作成功
     * success : true
     * validData : false
     * status : 1
     * memberId : 10007789
     */

    private String msg;
    private boolean success;
    private boolean validData;
    private String  status;
    private String  memberId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
