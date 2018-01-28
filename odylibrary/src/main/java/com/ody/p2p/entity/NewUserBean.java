package com.ody.p2p.entity;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by user on 2017/4/1.
 */

public class NewUserBean extends BaseRequestBean {

    public Data data;

    public static class Data {
        public UserExtMap userExtMap;
    }

    public static class UserExtMap {
        public int newuser;
    }
}
