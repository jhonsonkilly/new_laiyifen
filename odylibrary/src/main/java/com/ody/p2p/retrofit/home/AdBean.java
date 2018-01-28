package com.ody.p2p.retrofit.home;

import java.util.List;

/**
 * Created by mac on 2018/1/23.
 */

public class AdBean {

    public Data data;

    public static class Data {
        public List<AdBanner> APP_boot_ad;

        public static class AdBanner{
            public String linkUrl;
            public String imageUrl;

        }
    }
}
