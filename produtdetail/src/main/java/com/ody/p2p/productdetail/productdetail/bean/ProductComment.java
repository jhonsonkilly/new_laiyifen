package com.ody.p2p.productdetail.productdetail.bean;

import com.ody.p2p.base.BaseRequestBean;

import java.io.Serializable;
import java.util.List;


/**
 * Created by ody on 2016/8/23.
 */
public class ProductComment extends BaseRequestBean {


    public int status;
    public boolean success;

    public Data data;
    public String desc;

    public static class Data {

        public int positiveRate;  //好评率|
        public int ratingUserCount;    //评价总数

        public MpcList mpcList;


        public List<MpLabelList> mpLabelList;

        public static class MpcList {
            public String total;


            public List<ListObj> listObj;


            public static class ListObj implements Serializable {
                public long id;
                public String userUsername;
                public String userImg;
                public int rate;
                public String rateFlag;
                public String content;
                public long createTime;
                public String orderCreateTime;
                public String replyContent;
                public String topflag;
                public int isHideUserName;
                public List<mpAttrList> mpAttrList;
                public List<String> mpShinePicList;

                public static class mpAttrList {
                    public String name;
                    public String value;
                }


                public String getUserUsername() {
                    return userUsername;
                }

                public String getUserImg() {
                    return userImg;
                }

                public int getRate() {
                    return rate;
                }

                public int getIsHideUserName() {
                    return isHideUserName;
                }

                public List<ListObj.mpAttrList> getMpAttrList() {
                    return mpAttrList;
                }

                public void setMpAttrList(List<ListObj.mpAttrList> mpAttrList) {
                    this.mpAttrList = mpAttrList;
                }

                public List<String> getMpShinePicList() {
                    return mpShinePicList;
                }

                public void setMpShinePicList(List<String> mpShinePicList) {
                    this.mpShinePicList = mpShinePicList;
                }
            }


        }

        public static class MpLabelList {
            public String labelflag;
            public String labelName;
            public String labelNum;
        }
    }
}
