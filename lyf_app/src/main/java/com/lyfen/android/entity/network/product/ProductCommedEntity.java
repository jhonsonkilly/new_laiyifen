package com.lyfen.android.entity.network.product;

import java.util.List;

/**
 * Created by qj on 2017/6/8.
 */

public class ProductCommedEntity {

    /**
     * status : 0
     * code : 0
     * success : true
     * data : {"positiveRate":97,"ratingUserCount":31,"mpcList":{"listObj":[{"id":1020027700044800,"userUsername":"1505****826","userImg":"","rate":5,"rateFlag":1,"content":"好吃的，推荐购买。","createTime":1496845335000,"orderCreateTime":1496418670000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":["http://cdn.oudianyun.com/lyf/prod/frontier-guide/1496845251409_8628_38.jpg@base@tag=imgScale&q=95&m=0&h=800&w=800"],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null},{"id":1020027700043744,"userUsername":"1381****001","userImg":"","rate":5,"rateFlag":1,"content":null,"createTime":1496790773000,"orderCreateTime":1495629442000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":[],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null},{"id":1020027700043733,"userUsername":"1381****001","userImg":"","rate":5,"rateFlag":1,"content":null,"createTime":1496790771000,"orderCreateTime":1495629442000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":[],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null}],"total":30},"mpLabelList":[{"labelflag":0,"labelName":"全部","labelNum":31},{"labelflag":1,"labelName":"好评","labelNum":30},{"labelflag":2,"labelName":"中评","labelNum":1},{"labelflag":3,"labelName":"差评","labelNum":0},{"labelflag":5,"labelName":"晒图","labelNum":1}]}
     * message : null
     * desc : null
     * trace : 5!$1#@18%&10!$,156794,62784784379302707260100
     */

    public int status;
    public String code;
    public boolean success;
    public DataEntity data;
    public String message;
    public String desc;
    public String trace;

    public static class DataEntity {
        /**
         * positiveRate : 97
         * ratingUserCount : 31
         * mpcList : {"listObj":[{"id":1020027700044800,"userUsername":"1505****826","userImg":"","rate":5,"rateFlag":1,"content":"好吃的，推荐购买。","createTime":1496845335000,"orderCreateTime":1496418670000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":["http://cdn.oudianyun.com/lyf/prod/frontier-guide/1496845251409_8628_38.jpg@base@tag=imgScale&q=95&m=0&h=800&w=800"],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null},{"id":1020027700043744,"userUsername":"1381****001","userImg":"","rate":5,"rateFlag":1,"content":null,"createTime":1496790773000,"orderCreateTime":1495629442000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":[],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null},{"id":1020027700043733,"userUsername":"1381****001","userImg":"","rate":5,"rateFlag":1,"content":null,"createTime":1496790771000,"orderCreateTime":1495629442000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":[],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null}],"total":30}
         * mpLabelList : [{"labelflag":0,"labelName":"全部","labelNum":31},{"labelflag":1,"labelName":"好评","labelNum":30},{"labelflag":2,"labelName":"中评","labelNum":1},{"labelflag":3,"labelName":"差评","labelNum":0},{"labelflag":5,"labelName":"晒图","labelNum":1}]
         */

        public int positiveRate;
        public int ratingUserCount;
        public MpcListEntity mpcList;
        public List<MpLabelListEntity> mpLabelList;

        public static class MpcListEntity {
            /**
             * listObj : [{"id":1020027700044800,"userUsername":"1505****826","userImg":"","rate":5,"rateFlag":1,"content":"好吃的，推荐购买。","createTime":1496845335000,"orderCreateTime":1496418670000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":["http://cdn.oudianyun.com/lyf/prod/frontier-guide/1496845251409_8628_38.jpg@base@tag=imgScale&q=95&m=0&h=800&w=800"],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null},{"id":1020027700043744,"userUsername":"1381****001","userImg":"","rate":5,"rateFlag":1,"content":null,"createTime":1496790773000,"orderCreateTime":1495629442000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":[],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null},{"id":1020027700043733,"userUsername":"1381****001","userImg":"","rate":5,"rateFlag":1,"content":null,"createTime":1496790771000,"orderCreateTime":1495629442000,"topflag":0,"isHideUserName":0,"mpAttrList":null,"mpShinePicList":[],"replyContent":null,"replyContentTime":null,"addMPCommentVOList":null}]
             * total : 30
             */

            public int total;
            public List<ListObjEntity> listObj;

            public static class ListObjEntity {
                /**
                 * id : 1020027700044800
                 * userUsername : 1505****826
                 * userImg : 
                 * rate : 5
                 * rateFlag : 1
                 * content : 好吃的，推荐购买。
                 * createTime : 1496845335000
                 * orderCreateTime : 1496418670000
                 * topflag : 0
                 * isHideUserName : 0
                 * mpAttrList : null
                 * mpShinePicList : ["http://cdn.oudianyun.com/lyf/prod/frontier-guide/1496845251409_8628_38.jpg@base@tag=imgScale&q=95&m=0&h=800&w=800"]
                 * replyContent : null
                 * replyContentTime : null
                 * addMPCommentVOList : null
                 */

                public long id;
                public String userUsername;
                public String userImg;
                public int rate;
                public int rateFlag;
                public String content;
                public long createTime;
                public long orderCreateTime;
                public int topflag;
                public int isHideUserName;
                public String mpAttrList;
                public String replyContent;
                public String replyContentTime;
                public String addMPCommentVOList;
                public List<String> mpShinePicList;
            }
        }

        public static class MpLabelListEntity {
            /**
             * labelflag : 0
             * labelName : 全部
             * labelNum : 31
             */

            public int labelflag;
            public String labelName;
            public int labelNum;
        }
    }
}
