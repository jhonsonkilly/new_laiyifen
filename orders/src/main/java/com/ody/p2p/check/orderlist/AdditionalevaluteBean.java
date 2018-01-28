package com.ody.p2p.check.orderlist;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ${tang} on 2016/10/19.
 */
public class AdditionalevaluteBean extends BaseRequestBean {

    public UserComment data;
    /**
     * id : 123
     * mpId : 12312412
     * mpName : 商品名称
     * mpPicPath : http://www.baidu.com.png
     * orderId : 15132321
     * orderCode : w121ssd111
     * rate : 5
     * content : 我就是评价内容
     * mpShinePicList : ["http://pic29.nipic.com/20130505/9252150_174246645000_2.jpg","http://pic29.nipic.com/20130505/9252150_174246645000_2.jpg","http://pic29.nipic.com/20130505/9252150_174246645000_2.jpg","http://pic29.nipic.com/20130505/9252150_174246645000_2.jpg"]
     */



    public class UserComment{
        public List<UserMPCommentListBean> userMPCommentList;

        public class UserMPCommentListBean {
            public String id;
            public String mpId;
            public String mpName;
            public String mpPicPath;
            public String orderId;
            public String orderCode;
            public float rate;
            public String content;
            public List<String> mpShinePicList;
            public String addtionalcontent;
        }
    }

}
