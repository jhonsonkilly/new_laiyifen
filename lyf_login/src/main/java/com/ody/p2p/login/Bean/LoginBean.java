package com.ody.p2p.login.Bean;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/6/7.
 */
public class LoginBean extends BaseRequestBean {

    public String ut;
    public String distributorId;
    public boolean checkImage;
    public User data;

    public static class User {
        public String mobile;
        public String nickname;
        public String headPicUrl;
        public String id;
    }

}
