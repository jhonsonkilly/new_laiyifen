package com.lyfen.android.entity.network.home;

import java.util.List;

/**
 * Created by qj on 2017/5/8.
 */

public class HomeChannalEntity {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"ad_channel":[{"id":1014020801000024,"name":"领早餐","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.laiyifen.com/getBreakfast/index.html","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1489055666587_711_rIeBtSkgBK.jpg","imageTitle":null,"refString":null,"goods":false},{"id":1014020801000028,"name":"来抢购","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.laiyifen.com/seckill.html","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1489055675022_967_3Jn8hHgUHV.jpg","imageTitle":null,"refString":null,"goods":false},{"id":1014020801000030,"name":"闪购","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.laiyifen.com/flashSales/index.html","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1491370841798_853_zSlF4kMLC6.jpg","imageTitle":null,"refString":null,"goods":false},{"id":1014020801000036,"name":"生鲜直供","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":1,"refId":1003022100000160,"linkUrl":"lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003022100000160.html%22%7D","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1491370834119_491_UGzJoktVum.jpg","imageTitle":null,"refString":null,"goods":true},{"id":1014020801000046,"name":"全球购","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":1,"refId":30,"linkUrl":"lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F30.html%22%7D","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1491820480343_582_WOSoioAbOT.jpg","imageTitle":null,"refString":null,"goods":true},{"id":1014020801000042,"name":"直播","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://laiyifen.umaman.com/zhibo/?#/index?channel=laiyifenapp&authType=laiyifenappshiming&source=laiyifenapp&r=","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1489055688800_318_LCgSzOByAM.jpg","imageTitle":null,"refString":null,"goods":false},{"id":1014020801000040,"name":"新品","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":1,"refId":1003021200000220,"linkUrl":"lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003021200000220.html%22%7D","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1493116267039_661_ox4KOSDOJO.jpg","imageTitle":null,"refString":null,"goods":true},{"id":1014020801000048,"name":"邀请好友","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.laiyifen.com/share/appShare.html","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1491471980889_875_4k9JQ9PYbh.jpg","imageTitle":null,"refString":null,"goods":false},{"id":1014020801000050,"name":"我的订单","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.laiyifen.com/my/my-order.html","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1489055811690_560_AWfP1HkAS2.jpg","imageTitle":null,"refString":null,"goods":false},{"id":1014020801000052,"name":"领券专区","startTime":1487907996000,"endTime":1554004116000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.laiyifen.com/coupon/coupon-center.html","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1491471936608_98_YSvfxI0qjr.jpg","imageTitle":null,"refString":null,"goods":false}]}
     * trace : 82!$1#@18%&10!$,146368,null
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<AdChannelEntity> ad_channel;

        public static class AdChannelEntity {
            /**
             * id : 1014020801000024
             * name : 领早餐
             * startTime : 1487907996000
             * endTime : 1554004116000
             * sort : 0
             * type : 1
             * title : null
             * content : null
             * refType : 0
             * refId : null
             * linkUrl : http://m.laiyifen.com/getBreakfast/index.html
             * imageUrl : http://cdn.oudianyun.com/lyf/prod/ad-whale/1489055666587_711_rIeBtSkgBK.jpg
             * imageTitle : null
             * refString : null
             * goods : false
             */

            public long id;
            public String name;
            public long startTime;
            public long endTime;
            public int sort;
            public int type;
            public String title;
            public String content;
            public int refType;
            public String refId;
            public String linkUrl;
            public String imageUrl;
            public String imageTitle;
            public String refString;
            public boolean goods;
        }
    }
}
