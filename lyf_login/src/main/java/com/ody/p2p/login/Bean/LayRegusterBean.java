package com.ody.p2p.login.Bean;

import com.ody.p2p.base.BaseRequestBean;

public class LayRegusterBean extends BaseRequestBean {

    public String ut;
    public boolean isPwd;
    public User data;

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut;
    }

    public boolean isPwd() {
        return isPwd;
    }

    public void setPwd(boolean pwd) {
        isPwd = pwd;
    }

    public static class User {
        public String mobile;
        public String nickname;
        public String headPicUrl;
        public String id;
    }
}
