package com.lyfen.android.entity.network.home;

import java.util.List;

/**
 * Created by qj on 2017/5/17.
 */

public class HomeEntranceEntity {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"ad_entrance":[{"id":1076014701000028,"name":"邀请好友","startTime":1482724179000,"endTime":1516247379000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.lyf.dev.laiyifen.com:8123/share/appShare.html","imageUrl":"http://cdn.oudianyun.com/lyf-local/branch/ad-whale/1485065448931_738_JR72YW1uKv.png","imageTitle":null,"refString":null,"goods":false},{"id":1076014701000032,"name":"每日签到","startTime":1482724247000,"endTime":1515642647000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.lyf.dev.laiyifen.com:8123/community/my-signIn.html","imageUrl":"http://cdn.oudianyun.com/lyf-local/branch/ad-whale/1485065459244_99_t7Ik6QY0U2.png","imageTitle":null,"refString":null,"goods":false},{"id":1076014701000034,"name":"我的订单","startTime":1482724264000,"endTime":1547524264000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.lyf.dev.laiyifen.com:8123/my/my-order.html","imageUrl":"http://cdn.oudianyun.com/lyf-local/branch/ad-whale/1485065468098_48_XVmlqm7gpS.png","imageTitle":null,"refString":null,"goods":false},{"id":1016017500000025,"name":"门店查询","startTime":1485056676000,"endTime":1547091996000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"http://m.lyf.dev.laiyifen.com:8123/specialFun/findShop.html","imageUrl":"http://cdn.oudianyun.com/lyf-local/branch/ad-whale/1485065475759_633_YCWPDw1HMg.png","imageTitle":null,"refString":null,"goods":false}]}
     * trace : 34!$9#@2%&10!$,153537,62704449272165176901773
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<AdEntranceEntity> ad_entrance;

        public static class AdEntranceEntity {
            /**
             * id : 1076014701000028
             * name : 邀请好友
             * startTime : 1482724179000
             * endTime : 1516247379000
             * sort : 0
             * type : 1
             * title : null
             * content : null
             * refType : 0
             * refId : null
             * linkUrl : http://m.lyf.dev.laiyifen.com:8123/share/appShare.html
             * imageUrl : http://cdn.oudianyun.com/lyf-local/branch/ad-whale/1485065448931_738_JR72YW1uKv.png
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
