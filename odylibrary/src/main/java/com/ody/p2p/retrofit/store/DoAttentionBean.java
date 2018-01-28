package com.ody.p2p.retrofit.store;

/**
 * Created by user on 2017/7/17.
 */

public class DoAttentionBean {


    /**
     * code : 0
     * message : 请求成功
     * errMsg : null
     * data : 1001035100000053
     * trace : 203!$10#@18%&10!$,161760,62926377692819830540692
     */

    private String code;
    private String message;
    private String errMsg;
    private long data;
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

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
