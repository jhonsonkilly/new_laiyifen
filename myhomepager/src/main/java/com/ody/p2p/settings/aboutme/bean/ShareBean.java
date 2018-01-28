package com.ody.p2p.settings.aboutme.bean;

/**
 * Created by ody on 2016/6/8.
 */
public class ShareBean {
    public String code;
    public String message;
    public Data data;

    public class Data {
        public String title;
        public String content;
        public String shareLogoUrl;
        public String linkUrl;
    }
}
