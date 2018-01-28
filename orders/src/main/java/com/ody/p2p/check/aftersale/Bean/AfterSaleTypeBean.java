package com.ody.p2p.check.aftersale.Bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ${tang} on 2016/8/24.
 */
public class AfterSaleTypeBean extends BaseRequestBean {

    /**
     * errMsg : null
     * data : [{"operateType":2,"operateName":"退货"},{"operateType":4,"operateName":"换货"}]
     * trace : 56!$6#@16%&172!$,null,61646825178100334291331
     */

    public String errMsg;
    public String trace;
    /**
     * operateType : 2
     * operateName : 退货
     */

    public List<DataBean> data;

    public static class DataBean {
        public int operateType;
        public String operateName;
    }
}
