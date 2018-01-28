package com.ody.p2p.retrofit.home;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by lxs on 2016/12/2.
 */
public class ModuleDataBean extends BaseRequestBean{


    public List<CmsModuleDataVO> listObj;
    public int total;
    public int totalPage;

    public static class CmsModuleDataVO {
        public String brandId;
        public String brandName;
        public String categoryId;
        public String categoryName;
        public String cmsModuleId;
        public String code;
        public int dataType;
        public String linkUrl;
        public double marketPrice;
        public String promotionPrice;
        public String merchantId;
        public String merchantName;
        public String moduleDataId;
        public String mpCode;
        public String mpId;
        public String mpName;
        public String mpsId;
        public String picUrl;
        public double price;
        public String productId;
        public String rootCategoryName;
        public int rootCategorySort;
        public String rootCategoryId;
        public int sort;
        public long stockNum;
        public long volume4sale;
        public List<String> iconTexts;
        public int jumpType;
    }
}
