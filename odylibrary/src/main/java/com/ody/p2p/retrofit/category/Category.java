package com.ody.p2p.retrofit.category;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class Category {
    //    categoryId: 4362,
//    categoryName: "手机通讯",
//    pictureUrl: null,
//    categoryTreeNodeId: 4362
    public long categoryId;
    public String categoryName;
    public String pictureUrl;
    public String categoryTreeNodeId;
    public List<Category> children;
    public String linkUrl;
    public int imgId;
    public boolean isSelected;
}
