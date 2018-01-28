package com.ody.p2p.main;


import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by lxs on 2016/7/6.
 */
public class InitDataBean extends BaseRequestBean {

    public Data data;

    public class Data{
        public UpGrade upGrade;
    }

    public class UpGrade {
        public String appName;
        public int versionCode;
        public String describe;
        public String versionName;
        public int updateType;
        public String packageSize;
        public String obtainUrl;
        public boolean updateFlag;
        public int platformType;
    }
}
