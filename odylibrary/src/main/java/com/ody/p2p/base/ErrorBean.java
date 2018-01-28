package com.ody.p2p.base;

import com.ody.p2p.receiver.ConfirmOrderBean;

import java.util.List;

/**
 * Created by pzy on 2017/1/20.
 */

public class ErrorBean extends BaseRequestBean {
    private Data data;
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        ConfirmOrderBean.DataEntity.Errors error;

        public ConfirmOrderBean.DataEntity.Errors getError() {
            return error;
        }

        public void setError(ConfirmOrderBean.DataEntity.Errors error) {
            this.error = error;
        }
    }
}
