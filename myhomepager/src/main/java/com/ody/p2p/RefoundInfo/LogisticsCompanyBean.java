package com.ody.p2p.RefoundInfo;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ${tang} on 2016/10/12.
 */
public class LogisticsCompanyBean extends BaseRequestBean {

    /**
     * errMsg : null
     * data : [{"id":194,"name":"百福东方","logisticsCompanyId":"bfdf"},{"id":195,"name":"大田物流","logisticsCompanyId":"datianwuliu"}]
     * trace : 20!$6#@16%&172!$,null,61915129163999689691529
     */

    public String errMsg;
    public String trace;
    /**
     * id : 194
     * name : 百福东方
     * logisticsCompanyId : bfdf
     */

    public List<DataBean> data;

    public static class DataBean {
        public String id;
        public String name;
        public String logisticsCompanyId;
    }
}
