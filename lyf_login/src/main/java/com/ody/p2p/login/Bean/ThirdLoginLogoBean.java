package com.ody.p2p.login.Bean;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/6.
 */
public class ThirdLoginLogoBean extends BaseRequestBean {

    public LogoData data;

    public static class LogoData {
        /**
         * logoPic : http://img1.zgxcw.com/v1/tfs//T2NHbTBgEv1RCvBVdK
         */

        public List<Logos> logos;

        public static class Logos {
            public String logoName;
            public String logoPic;
        }
    }
}
