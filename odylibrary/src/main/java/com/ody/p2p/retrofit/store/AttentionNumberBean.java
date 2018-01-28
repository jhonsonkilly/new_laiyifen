package com.ody.p2p.retrofit.store;

import java.util.List;

/**
 * Created by user on 2017/11/24.
 */

public class AttentionNumberBean {

    /**
     * flag : true
     * errorMessage : null
     * successMsg : 处理成功!
     * data : 1
     * code : 0
     * fullErrStackTraceStr :
     * errStatckTrace : []
     * serviceSucceed : true
     */

    private boolean flag;
    private Object errorMessage;
    private String successMsg;
    private int data;
    private String code;
    private String fullErrStackTraceStr;
    private boolean serviceSucceed;
    private List<?> errStatckTrace;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullErrStackTraceStr() {
        return fullErrStackTraceStr;
    }

    public void setFullErrStackTraceStr(String fullErrStackTraceStr) {
        this.fullErrStackTraceStr = fullErrStackTraceStr;
    }

    public boolean isServiceSucceed() {
        return serviceSucceed;
    }

    public void setServiceSucceed(boolean serviceSucceed) {
        this.serviceSucceed = serviceSucceed;
    }

    public List<?> getErrStatckTrace() {
        return errStatckTrace;
    }

    public void setErrStatckTrace(List<?> errStatckTrace) {
        this.errStatckTrace = errStatckTrace;
    }
}
