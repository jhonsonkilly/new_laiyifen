package com.ody.p2p.base.action;

import java.util.List;

/**
 * Created by qiujie on 2017/10/23.
 */

public class GrestCouponBean {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"register_coupon_guide":[{"id":1016044700000004,"name":"注册送券引导","startTime":1508558165000,"endTime":1824695765000,"sort":0,"type":1,"title":null,"content":null,"refType":0,"refId":null,"linkUrl":"www.baidu.com","imageUrl":"http://cdn.oudianyun.com/lyf-local/branch/ad-whale/1508558219680_941_E2rR58MM3K.png","imageTitle":null,"refObject":null,"goods":false}]}
     * trace : 34!$9#@2%&10!$,176559,63280515536360040481139
     */

    public String code;
    public String message;
    public Object errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<RegisterCouponGuideEntity> register_coupon_guide;

        public static class RegisterCouponGuideEntity {
            /**
             * id : 1016044700000004
             * name : 注册送券引导
             * startTime : 1508558165000
             * endTime : 1824695765000
             * sort : 0
             * type : 1
             * title : null
             * content : null
             * refType : 0
             * refId : null
             * linkUrl : www.baidu.com
             * imageUrl : http://cdn.oudianyun.com/lyf-local/branch/ad-whale/1508558219680_941_E2rR58MM3K.png
             * imageTitle : null
             * refObject : null
             * goods : false
             */

            public long id;
            public String name;
            public long startTime;
            public long endTime;
            public int sort;
            public int type;
            public Object title;
            public Object content;
            public int refType;
            public Object refId;
            public String linkUrl;
            public String imageUrl;
            public Object imageTitle;
            public Object refObject;
            public boolean goods;
        }
    }
}
