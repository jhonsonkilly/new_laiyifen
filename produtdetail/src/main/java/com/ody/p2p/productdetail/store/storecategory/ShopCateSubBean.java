package com.ody.p2p.productdetail.store.storecategory;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ${tang} on 2017/7/19.
 */

public class ShopCateSubBean extends BaseRequestBean {

    public DataEntity data;

    public class DataEntity{

        public String categoryId;
        public String categoryName;
        public String description;
        public String mCateTreeId;
        public String mCateTreeNodeId;
        public String mParentNodeId;
        public int listSort;
        public long merchantId;
        public int companyId;
        public String pictureName;
        public String pictureUrl;
        public String isVisible;

        public List<ChildListBean> childList;

        public  class ChildListBean {
            public String categoryId;
            public String name;
            public String description;
            public String mCateTreeId;
            public String mCateTreeNodeId;
            public String mParentNodeId;
            public int listSort;
            public long merchantId;
            public int companyId;
            public String pictureName;
            public String pictureUrl;
            public Object isVisible;
            public List<ChildListBean> childList;
            public int itemType;

//            public class TwoChildListBean {
//                public String categoryId;
//                public String categoryName;
//                public String description;
//                public String mCateTreeId;
//                public String mCateTreeNodeId;
//                public String mParentNodeId;
//                public int listSort;
//                public long merchantId;
//                public int companyId;
//                public String pictureName;
//                public String pictureUrl;
//                public String isVisible;
//                /**
//                 * categoryId : 1176033402000000
//                 * categoryName : 3335454
//                 * description : 545454
//                 * mCateTreeId : 1
//                 * mCateTreeNodeId : 1176033402000000
//                 * mParentNodeId : 1176033401000004
//                 * listSort : 1
//                 * merchantId : 1289032701000008
//                 * companyId : 50
//                 * pictureName : null
//                 * pictureUrl : null
//                 * isVisible : 1
//                 * childList : []
//                 */
//
//                public List<ChildSubListBean> childList;
//
//                public class ChildSubListBean {
//                    public String categoryId;
//                    public String categoryName;
//                    public String description;
//                    public String mCateTreeId;
//                    public String mCateTreeNodeId;
//                    public String mParentNodeId;
//                    public int listSort;
//                    public long merchantId;
//                    public int companyId;
//                    public String pictureName;
//                    public String pictureUrl;
//                    public int isVisible;
//                    public List<?> childList;
//                }
//            }
        }
    }

}
