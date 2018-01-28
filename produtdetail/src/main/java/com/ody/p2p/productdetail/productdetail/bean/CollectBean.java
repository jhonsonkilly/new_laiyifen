package com.ody.p2p.productdetail.productdetail.bean;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/8/8.
 */
public class CollectBean extends BaseRequestBean {
    long data;
    String errMsg;
    String trace;

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
