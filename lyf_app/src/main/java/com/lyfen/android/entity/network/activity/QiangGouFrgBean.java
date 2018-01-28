package com.lyfen.android.entity.network.activity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2017/7/21.
 */

public class QiangGouFrgBean implements Serializable {
    public String code;
    public String message;
    public String errMsg;
    public QiangGouData data;

    public class QiangGouData implements Serializable {
        public List<QiangGouList> listObj;
        public int total;

        public class QiangGouList implements Serializable {
            public String promotionId;
            public String startTime;
            public String endTime;
            public String sysTime;
            public String status;
            public String statusStr;
            public List<MerchantProducts> merchantProducts;
            public String categoryList;
            public String frontPromotionType;

            public class MerchantProducts implements Serializable {
                public String promotionId;
                public String mpId;
                public String name;
                public String originalPrice;
                public String promotionPrice;
                public String stockNum;
                public String picUrl;
                public String status;
                public String statusStr;
                public String subTitle;
                public String otherSeriesMPList;
                public String merchantSeriesId;
                public String categoryId;
                public String categoryName;
                public String updateNum;
                public String lackOfStock;
                public String allStock;
                public String saleStock;
                public String merchantName;
                public String merchantId;
                public String canAreaSold;
                public String sortIndex;
                public String startTime;
                public String noticeStatus;
                public String noticeStatusStr;
                public String noticeCount;

                public String getSaleProgress(String allStock, String saleStock) {
                    Double progress = (Double.parseDouble(saleStock)) / (Double.parseDouble(allStock));

                    return progress * 100 == 100 ? "已抢完" : "已抢" + Math.round(progress * 100) + "%";
                }




            }


        }
    }
}
