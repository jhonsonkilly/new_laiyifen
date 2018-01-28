package com.ody.p2p.productdetail.store.storecategory;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ${tang} on 2017/7/19.
 */

public class ShopCateParentBean extends BaseRequestBean {

    /**
     * id : 1
     * merchantId : 1289032701000008
     * description : null
     * type : 1
     * name : 商家树
     * companyId : 50
     * parentId : null
     * listSort : null
     * cateTreeType : null
     */
    public CategoryEntity data;

    public class CategoryEntity{
        public String id;
        public long merchantId;
        public String description;
        public int type;
        public String name;
        public int companyId;
        public String parentId;
        public Object listSort;
        public String cateTreeType;
    }


}
