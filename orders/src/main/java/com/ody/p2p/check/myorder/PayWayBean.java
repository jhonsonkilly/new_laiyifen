package com.ody.p2p.check.myorder;




import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by tangmeijuan on 16/6/16.
 */
public class PayWayBean extends BaseRequestBean {

    public DataEntity data;

    public static class DataEntity {
        /**
         * id : 100
         * name : 在线支付
         * checked : 0
         */

        public List<PaywaylistEntity> paywaylist;

        public static class PaywaylistEntity {
            public int id;
            public String name;
            public int checked;
        }
    }
}
