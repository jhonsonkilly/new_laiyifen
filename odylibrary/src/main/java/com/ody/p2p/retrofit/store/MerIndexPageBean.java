package com.ody.p2p.retrofit.store;

/**
 * Created by meizivskai on 2017/7/14.
 */

public class MerIndexPageBean {


    /**
     * code : 0
     * message :
     * errMsg : null
     * data : http://m.lyf.test.laiyifen.com/view/h5/1015034500000080.html?merchantId=1076015501000001
     * trace : 201!$10#@18%&10!$,166546,62915414175800243253303
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
