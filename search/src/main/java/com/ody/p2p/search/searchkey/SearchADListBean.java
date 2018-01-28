package com.ody.p2p.search.searchkey;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/8/30.
 * 德升  搜索默认词统一模版配置的广告bean
 */
public class SearchADListBean extends BaseRequestBean {

    public Data data;

    public static class Data {
        /**
         * id : 226
         * name : 默认搜索词
         * startTime : 1472177314000
         * endTime : 1482718354000
         * sort : -996119068
         * type : 0
         * title : 默认搜索词
         * content : 默认跳转搜索文本
         * refType : 0
         * refId : null
         * linkUrl : http://baidu.com
         * imageUrl : null
         * imageTitle : null
         * refObject : {"productId":null,"mpId":null,"mpsId":null,"code":null,"name":null,"subTitle":null,"type":null,"price":null,"marketPrice":null,"tax":null,"stockNum":null,"lackOfStock":null,"brandId":null,"brandName":null,"brandImgUrl":null,"categoryId":null,"promotionType":null,"promotionPrice":null,"preferentialPrice":null,"promotionId":null,"promotionIconUrl":null,"promotionIconUrls":null,"categoryTreeNodeId":null,"merchantSeriesId":null,"companyId":null,"merchantId":null,"merchantName":null,"merchantType":null,"freightAttribute":null,"grossWeight":null,"merchantProdVolume":null,"shopId":null,"shopName":null,"shopType":null,"freightTemplateId":null,"warehouseNo":null,"calculationUnit":null,"standard":null,"saleType":null,"saleIconUrl":null,"offset":null,"mpSalesVolume":null,"mpSource":null,"isBargain":null,"isRent":null,"status":null,"managementState":null,"remark":null,"volume4sale":null,"picUrl":null,"promotionInfo":null,"url160x160":null,"url220x220":null,"url500x500":null,"url800x800":null,"url60x60":null,"url100x100":null,"url120x120":null}
         * goods : false
         */

        public List<Searchword> searchword;
        public List<Searchword> app_hotword;

        public static class Searchword {
            public long id;
            public String name;
            public long startTime;
            public long endTime;
            public int sort;
            public int type;
            public String title;
            public String content;
            public int refType;
            public Object refId;
            public String linkUrl;
            public Object imageUrl;
            public Object imageTitle;

            @Override
            public String toString() {
                return content;
            }

            /**
             * productId : null
             * mpId : null
             * mpsId : null
             * code : null
             * name : null
             * subTitle : null
             * type : null
             * price : null
             * marketPrice : null
             * tax : null
             * stockNum : null
             * lackOfStock : null
             * brandId : null
             * brandName : null
             * brandImgUrl : null
             * categoryId : null
             * promotionType : null
             * promotionPrice : null
             * preferentialPrice : null
             * promotionId : null
             * promotionIconUrl : null
             * promotionIconUrls : null
             * categoryTreeNodeId : null
             * merchantSeriesId : null
             * companyId : null
             * merchantId : null
             * merchantName : null
             * merchantType : null
             * freightAttribute : null
             * grossWeight : null
             * merchantProdVolume : null
             * shopId : null
             * shopName : null
             * shopType : null
             * freightTemplateId : null
             * warehouseNo : null
             * calculationUnit : null
             * standard : null
             * saleType : null
             * saleIconUrl : null
             * offset : null
             * mpSalesVolume : null
             * mpSource : null
             * isBargain : null
             * isRent : null
             * status : null
             * managementState : null
             * remark : null
             * volume4sale : null
             * picUrl : null
             * promotionInfo : null
             * url160x160 : null
             * url220x220 : null
             * url500x500 : null
             * url800x800 : null
             * url60x60 : null
             * url100x100 : null
             * url120x120 : null
             */

            public RefObject refObject;
            public boolean goods;

            public static class RefObject {
                public long productId;
                public long mpId;
                public long mpsId;
                public Object code;
                public Object name;
                public Object subTitle;
                public Object type;
                public Object price;
                public Object marketPrice;
                public Object tax;
                public Object stockNum;
                public Object lackOfStock;
                public Object brandId;
                public Object brandName;
                public Object brandImgUrl;
                public Object categoryId;
                public Object promotionType;
                public Object promotionPrice;
                public Object preferentialPrice;
                public Object promotionId;
                public Object promotionIconUrl;
                public Object promotionIconUrls;
                public Object categoryTreeNodeId;
                public Object merchantSeriesId;
                public Object companyId;
                public Object merchantId;
                public Object merchantName;
                public Object merchantType;
                public Object freightAttribute;
                public Object grossWeight;
                public Object merchantProdVolume;
                public Object shopId;
                public Object shopName;
                public Object shopType;
                public Object freightTemplateId;
                public Object warehouseNo;
                public Object calculationUnit;
                public Object standard;
                public Object saleType;
                public Object saleIconUrl;
                public Object offset;
                public Object mpSalesVolume;
                public Object mpSource;
                public Object isBargain;
                public Object isRent;
                public Object status;
                public Object managementState;
                public Object remark;
                public Object volume4sale;
                public Object picUrl;
                public Object promotionInfo;
                public Object url160x160;
                public Object url220x220;
                public Object url500x500;
                public Object url800x800;
                public Object url60x60;
                public Object url100x100;
                public Object url120x120;
            }
        }
    }
}
