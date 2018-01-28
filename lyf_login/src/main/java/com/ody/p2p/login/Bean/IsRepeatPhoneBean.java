package com.ody.p2p.login.Bean;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/6/7.
 */
public class IsRepeatPhoneBean extends BaseRequestBean {

    public int flag;

    public Data data;

    public class Data{
        boolean result;
        String ut;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public String getUt() {
            return ut;
        }

        public void setUt(String ut) {
            this.ut = ut;
        }
    }
}
