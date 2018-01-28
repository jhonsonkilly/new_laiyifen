package com.ody.p2p.retrofit.home;

import java.util.List;

/**
 * Created by lxs on 2016/6/23.
 */
public class ResultBean {

    public List<Child> navCategoryTreeResult;//这是新的
    public String sortBy;
    public int totalCount;
    public List<AttributeResult> attributeResult;
    public List<SortBean> sortByList;
    public List<ProductBean> productList;
    public List<Child> categoryTreeResult;//废弃掉了
    public String zeroRecommendWord;//搜索结果为空时，返回推荐词
    public List<String> maybeInterestedKeywords;
    public List<ProductBean> zeroRecommendResult;

    public static final int JUMP_DETAIL = 1;

    public static class SortBean {
        public String sortTypeCode;
        public String sortTypeDesc;
        public boolean isSelected;
    }

    public static class ProductBean {
        public String productId;
        public String mpId;
        public String mpsId;
        public String code;
        public String name;
        public String subTitle;
        public String type;
        public String price;
        public String marketPrice;
        public String tax;
        public long stockNum;
        public int lackOfStock;//商品库存是否充足 0 否 1 是
        public String brandIds;
        public String brandName;
        public String categoryId;
        public String promotionType;
        public String promotionPrice;
        public String promotionId;
        public String promotionIconUrl;
        public String merchantSeriesId;
        public String merchantId;
        public String merchantName;
        public String merchantType;
        public String freightAttribute;
        public String categoryTreeNodeId;

        public String picUrl;
        public List<String> tagList;
        public int showType;
        public List<promotionInfoes> promotionInfo;
        public int volume4sale;

        public int jumpType;

    }

    public static class promotionInfoes {
        public String mpId;
        public String iconText;
        public String iconUrl;
        public List<promotions> promotions;
    }

    public static class promotions {
        public String id;
        public String description;
        public String promotionId;
        public int promotionType;
        public String url;
    }

    public static class AttributeValue {
        public long id;
        public String value;
        public long attr_id;
        public int count;
        public boolean isChecked;
    }

    public static class AttributeResult {
        public long id;
        public String name;
        public List<AttributeValue> attributeValues;
        public int count;
        public boolean filterOpenFlag;
    }


    public class Child {
        public String id;
        public String name;
        public List<Child> children;
        public int count;
    }


}
