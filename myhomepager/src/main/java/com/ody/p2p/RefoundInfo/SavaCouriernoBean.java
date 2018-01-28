package com.ody.p2p.RefoundInfo;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/7/22.
 */
public class SavaCouriernoBean  extends BaseRequestBean{
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }


    public class Data {
        private String operationCode;

        private String operationMessage;

        public void setOperationCode(String operationCode) {
            this.operationCode = operationCode;
        }

        public String getOperationCode() {
            return this.operationCode;
        }

        public void setOperationMessage(String operationMessage) {
            this.operationMessage = operationMessage;
        }

        public String getOperationMessage() {
            return this.operationMessage;
        }

    }
}
