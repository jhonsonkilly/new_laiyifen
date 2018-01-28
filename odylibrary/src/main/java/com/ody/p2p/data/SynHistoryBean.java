package com.ody.p2p.data;

import java.util.List;

/**
 * Created by lxs on 2016/10/13.
 */
public class SynHistoryBean {

    public List<SynHistory> list;

    public static class SynHistory{
        public long browsingTime;
        public String mpId;
    }

}
