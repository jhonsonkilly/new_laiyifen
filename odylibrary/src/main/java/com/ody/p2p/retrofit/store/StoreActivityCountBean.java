package com.ody.p2p.retrofit.store;

/**
 * Created by user on 2017/7/21.
 */

public class StoreActivityCountBean {


    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"listObj":null,"total":4}
     * trace : 196!$10#@18%&10!$,0,62939849137789830592912
     */

    private String code;
    private String message;
    private Object errMsg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public static class DataBean {
        /**
         * listObj : null
         * total : 4
         */

        private Object listObj;
        private int total;

        public Object getListObj() {
            return listObj;
        }

        public void setListObj(Object listObj) {
            this.listObj = listObj;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
