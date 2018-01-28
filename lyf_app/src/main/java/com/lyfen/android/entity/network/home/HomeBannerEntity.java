package com.lyfen.android.entity.network.home;

import java.util.List;

/**
 * Created by qj on 2017/5/5.
 */

public class HomeBannerEntity {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"ad_banner":[{"id":1014027500000000,"name":"妈妈茶食记","startTime":1493690135000,"endTime":1494813335000,"sort":0,"type":1,"title":null,"content":null,"refType":1,"refId":1003027100000306,"linkUrl":"lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003027100000306.html%22%7D","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1493784200713_39_8OCqNY5N8L.jpg","imageTitle":null,"refObject":null,"goods":true},{"id":1014026400000002,"name":"早餐很\u201c粽\u201d要","startTime":1492765375000,"endTime":1496221375000,"sort":0,"type":1,"title":null,"content":null,"refType":1,"refId":1003024400000216,"linkUrl":"lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003024400000216.html%22%7D","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1493814662489_977_jaxhrRXTXl.jpg","imageTitle":null,"refObject":null,"goods":true},{"id":1014027100000001,"name":"北极深海美味","startTime":1493352583000,"endTime":1494562183000,"sort":0,"type":1,"title":"北极深海美味","content":"北极深海美味","refType":1,"refId":1003027700000101,"linkUrl":"lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003027700000101.html%22%7D","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1493881239196_79_nFC1GOyKD4.jpg","imageTitle":null,"refObject":null,"goods":true},{"id":1014023300000001,"name":"新人专享","startTime":1490066477000,"endTime":1556594477000,"sort":0,"type":1,"title":null,"content":null,"refType":1,"refId":1003021000000323,"linkUrl":"lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003021000000323.html%22%7D","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1493375653227_939_oSIFctreXc.jpg","imageTitle":null,"refObject":null,"goods":true}]}
     * trace : 40!$1#@18%&10!$,146368,null
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<AdBannerEntity> ad_banner;
        public List<AdBannerEntity> searchword;

        public static class AdBannerEntity {
            /**
             * id : 1014027500000000
             * name : 妈妈茶食记
             * startTime : 1493690135000
             * endTime : 1494813335000
             * sort : 0
             * type : 1
             * title : null
             * content : null
             * refType : 1
             * refId : 1003027100000306
             * linkUrl : lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003027100000306.html%22%7D
             * imageUrl : http://cdn.oudianyun.com/lyf/prod/ad-whale/1493784200713_39_8OCqNY5N8L.jpg
             * imageTitle : null
             * refObject : null
             * goods : true
             */

            public String id;
            public String name;
            public String startTime;
            public String endTime;
            public String sort;
            public String type;
            public String title;
            public String content;
//            public String refType;
//            public String refId;
            public String linkUrl;
            public String imageUrl;
            public String imageTitle;
//            public String refObject;
//            public boolean goods;
        }
    }
}
