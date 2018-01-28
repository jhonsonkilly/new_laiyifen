package com.ody.p2p.search.searchresult.popupwindow;

import com.google.gson.annotations.SerializedName;
import com.ody.p2p.base.BaseRequestBean;

import java.util.LinkedList;
import java.util.List;
/**
 * Created by lxs on 2016/6/23.
 */
public class ResultBean extends BaseRequestBean {


    public Data data;

    public class Data {
        public List<Child> navCategoryTreeResult;//这是新的
        public String sortBy;
        public int totalCount;
        public LinkedList<AttributeResult> attributeResult;
        public List<SortBean> sortByList;
        public List<ProductBean> productList;
        public List<Child> categoryTreeResult;//废弃掉了
        public String zeroRecommendWord;//搜索结果为空时，返回推荐词
        public List<String> maybeInterestedKeywords;
        public List<ProductBean> zeroRecommendResult;

        @SerializedName("brandResult")
        public List<Brand> brand;
    }
    public static class Brand {
        public long id;
        public String name;
        public String count;
        public String logo;
    }
    public static class SortBean {
        public String sortTypeCode;
        public String sortTypeDesc;
        public String sortTypeShort;
        public boolean isSelected;
    }

    public static class ProductBean {
        public String productId;
        public long mpId;
        public String mpsId;
        public String code;
        public String name;
        public String subTitle;
        public String type;
        public double price;
        public String tax;
        public long stockNum;
        public int lackOfStock;//商品库存是否充足 0 否 1 是
        public String brandIds;
        public String brandName;
        public String categoryId;
        public String promotionType;
        public int saleType;
        public String saleIconUrl;
        public String promotionPrice;
        public String promotionIconUrl;
        public List<String> promotionIconUrls;
        public String merchantName;
        public int merchantType;
        public int freightAttribute;
        public String categoryTreeNodeId;
        public String picUrl;
        public String marketPrice;
        public List<String> promotionIconList;
        public List<String> tagList;
        public int showType;
        public long mpSalesVolume;//真销量
        public List<promotionInfoes> promotionInfo;
        public CommentInfo commentInfo;
        public List<Scripts> scripts;
        public List<String> titleIconUrls;
        public List<String> titleIconTexts;
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
        public  String url;
        public String iconUrl;
    }

    public static class CommentInfo{
        public String commentNum;
        public String goodRate;
    }

    public static class AttributeValue {
        public String id;
        public String value;
        public long attr_id;
        public int count;
        public boolean isChecked;
    }

    public static class AttributeResult{
        public String id;
        public String name;
        public LinkedList<AttributeValue> attributeValues;
        public int count;
        public boolean filterOpenFlag;
    }


    public class Child{
        public String id;
        public String name;
        public List<Child> children;
        public int count;
    }


    public static class Scripts{
        public int displayType;
        public String scriptIconUrl;
        public String scriptName;
    }

}
