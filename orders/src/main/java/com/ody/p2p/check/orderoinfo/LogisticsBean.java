package com.ody.p2p.check.orderoinfo;



import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/6/21.
 */
public class LogisticsBean extends BaseRequestBean{

    /**
     * errMsg : null
     * data : {"orderInfo":{"orderId":"160712305633990296","distributors":"顺丰速运","trackingNumber":"160712305633990296","packageStatusName":"派送中","productList":[{"mpId":292,"name":"王老吉","picUrl":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg","url120x120":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0","url60x60":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0"}]},"orderMessageList":[{"message":"订单已提交，等待付款","createTime":"2016-07-12 09:29:55"},{"message":"已签收 签收人：柳帅","createTime":"2016-07-12 09:29:55"}]}
     * trace : 165!$1#@168%&192!$,55579,61585688808183769600037
     */

    public Object errMsg;
    /**
     * orderInfo : {"orderId":"160712305633990296","distributors":"顺丰速运","trackingNumber":"160712305633990296","packageStatusName":"派送中","productList":[{"mpId":292,"name":"王老吉","picUrl":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg","url120x120":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0","url60x60":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0"}]}
     * orderMessageList : [{"message":"订单已提交，等待付款","createTime":"2016-07-12 09:29:55"},{"message":"已签收 签收人：柳帅","createTime":"2016-07-12 09:29:55"}]
     */

    public DataBean data;
    public String trace;

    public static class DataBean {
        /**
         * orderId : 160712305633990296
         * distributors : 顺丰速运
         * trackingNumber : 160712305633990296
         * packageStatusName : 派送中
         * productList : [{"mpId":292,"name":"王老吉","picUrl":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg","url120x120":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0","url220x220":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0","url60x60":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0","url100x100":"http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0"}]
         */

        public OrderInfoBean orderInfo;
        /**
         * message : 订单已提交，等待付款
         * createTime : 2016-07-12 09:29:55
         */

        public List<OrderMessageListBean> orderMessageList;

        public static class OrderInfoBean {
            public String orderId;
            public String distributors;
            public String trackingNumber;
            public String packageStatusName;
            /**
             * mpId : 292
             * name : 王老吉
             * picUrl : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg
             * url120x120 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=120&w=120&m=0
             * url160x160 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=160&w=160&m=0
             * url220x220 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=220&w=220&m=0
             * url500x500 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=500&w=500&m=0
             * url800x800 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=800&w=800&m=0
             * url60x60 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=60&w=60&m=0
             * url100x100 : http://ksyun.storage.rw.kssws.ks-cdn.com/1448604041544_87.67915047827061_79f0f736afc379314da302f0eac4b74542a9111b.jpg@base@tag=imgScale&h=100&w=100&m=0
             */

            public List<ProductListBean> productList;

            public static class ProductListBean {
                public long mpId;
                public String name;
                public String picUrl;
                public String url120x120;
                public String url160x160;
                public String url220x220;
                public String url500x500;
                public String url800x800;
                public String url60x60;
                public String url100x100;
            }
        }

        public static class OrderMessageListBean {
            public String message;
            public String createTime;
        }
    }
}
