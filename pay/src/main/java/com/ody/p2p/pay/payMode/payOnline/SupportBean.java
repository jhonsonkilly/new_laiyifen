package com.ody.p2p.pay.payMode.payOnline;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by meijunqiang on 2017/6/9.
 * 描述:判断收银台是否显示悠点卡伊点卡的标识
 */

public class SupportBean extends BaseRequestBean {

    /**
     * trace : 34!$9#@2%&10!$,156756,62788574426441156360796
     * data : {"canUseUCard":1,"canUseECard":1}
     * errMsg : null
     */
    private String trace;
    private DataEntity data;
    private String errMsg;

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getTrace() {
        return trace;
    }

    public DataEntity getData() {
        return data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public static class DataEntity {
        /**
         * canUseUCard : 1
         * canUseECard : 1
         */
        private int canUseUCard;
        private int canUseECard;

        public void setCanUseUCard(int canUseUCard) {
            this.canUseUCard = canUseUCard;
        }

        public void setCanUseECard(int canUseECard) {
            this.canUseECard = canUseECard;
        }

        public int getCanUseUCard() {
            return canUseUCard;
        }

        public int getCanUseECard() {
            return canUseECard;
        }
    }
}
