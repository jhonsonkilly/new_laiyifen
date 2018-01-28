package com.lyfen.android.entity.network.dialog;

import java.util.List;

/**
 * <p> Created by qiujie on 2017/12/5/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class HomeNewUserEntity {


    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"registered_coupon":[],"newcomers_popup":[{"id":1014024400000074,"name":"首页弹屏","startTime":1491043172000,"endTime":1556015972000,"sort":0,"type":1,"title":null,"content":null,"refType":1,"refId":1003040201000221,"linkUrl":"lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003040201000221.html%22%7D","imageUrl":"http://cdn.oudianyun.com/lyf/prod/ad-whale/1510567327417_851_ahXCy2gW1E.png","imageTitle":null,"refString":null,"goods":false}]}
     * trace : 40!$1#@18%&10!$,199378,63436970282368456771765
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<NewcomersPopupEntity> registered_coupon;
        public List<NewcomersPopupEntity> newcomers_popup;

        public static class NewcomersPopupEntity {
            /**
             * id : 1014024400000074
             * name : 首页弹屏
             * startTime : 1491043172000
             * endTime : 1556015972000
             * sort : 0
             * type : 1
             * title : null
             * content : null
             * refType : 1
             * refId : 1003040201000221
             * linkUrl : lyf://h5?body=%7B%22url%22%3A%22http%3A%2F%2Fm.laiyifen.com%2Fcms%2Fview%2Fh5%2F1003040201000221.html%22%7D
             * imageUrl : http://cdn.oudianyun.com/lyf/prod/ad-whale/1510567327417_851_ahXCy2gW1E.png
             * imageTitle : null
             * refString : null
             * goods : false
             */

            public String id;
            public String name;
            public String startTime;
            public String endTime;
            public String sort;
            public String type;
            public String title;
            public String content;
            public String refType;
            public String refId;
            public String linkUrl;
            public String imageUrl;
            public String imageTitle;
            public String refString;
            public boolean goods;
        }
    }
}
