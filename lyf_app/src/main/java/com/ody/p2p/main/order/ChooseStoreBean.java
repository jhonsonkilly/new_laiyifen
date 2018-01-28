package com.ody.p2p.main.order;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ${tang} on 2016/12/13.
 */

public class ChooseStoreBean extends BaseRequestBean {


    /**
     * errMsg : null
     * data : {"totalNum":5,"dataList":[{"merchantId":1,"shopId":5,"merchantShopName":"门店名称","awayFrom":300,"address":"详细地址-含省市区","phone":"联系方式","srcImgUrl":"http://dx.app/2016/aa.jpg","longitude":121.600543,"latitude":31.199196}]}
     * trace : 23!$0#@16%&172!$,1!$36#@168%&192!$,1!$75#@168%&192!$,null,62066919434710265140811
     */

    public Object errMsg;
    /**
     * totalNum : 5
     * dataList : [{"merchantId":1,"shopId":5,"merchantShopName":"门店名称","awayFrom":300,"address":"详细地址-含省市区","phone":"联系方式","srcImgUrl":"http://dx.app/2016/aa.jpg","longitude":121.600543,"latitude":31.199196}]
     */

    public DataBean data;
    public String trace;

    public static class DataBean {
        public int totalNum;
        /**
         * merchantId : 1
         * shopId : 5
         * merchantShopName : 门店名称
         * awayFrom : 300
         * address : 详细地址-含省市区
         * phone : 联系方式
         * srcImgUrl : http://dx.app/2016/aa.jpg
         * longitude : 121.600543
         * latitude : 31.199196
         */

        public List<DataListBean> dataList;

        public static class DataListBean {
            public String merchantId;
            public String shopId;
            public String merchantShopName;
            public String awayFrom;
            public String address;
            public String phone;
            public String srcImgUrl;
            public double longitude;
            public double latitude;
            public int isSelected;
        }
    }
}
