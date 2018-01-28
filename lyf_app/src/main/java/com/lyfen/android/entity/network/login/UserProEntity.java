package com.lyfen.android.entity.network.login;

import java.util.List;

/**
 * Created by qiujie on 2017/7/6.
 */

public class UserProEntity
{


    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"reg_protocol":[{"id":1014025700000002,"name":"注册协议","startTime":1492156126000,"endTime":1608537046000,"sort":0,"type":0,"title":"注册协议","content":"注册协议","refType":0,"refId":null,"linkUrl":"http://m.laiyifen.com/cms/view/h5/article/1003025700000016.html","imageUrl":null,"imageTitle":null,"refString":null,"goods":false}]}
     * trace : 39!$1#@18%&10!$,153537,62886409379963378651226
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        public List<RegProtocolEntity> reg_protocol;

        public static class RegProtocolEntity {
            /**
             * id : 1014025700000002
             * name : 注册协议
             * startTime : 1492156126000
             * endTime : 1608537046000
             * sort : 0
             * type : 0
             * title : 注册协议
             * content : 注册协议
             * refType : 0
             * refId : null
             * linkUrl : http://m.laiyifen.com/cms/view/h5/article/1003025700000016.html
             * imageUrl : null
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
