package com.ody.p2p;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by lxs on 2017/2/13.
 */
public class AliBCBean extends BaseRequestBean{

    public Data data;

    public static class Data{
        public String userId;
        public String appKey;
        public String receiveId;
        public String password;
    }
}
