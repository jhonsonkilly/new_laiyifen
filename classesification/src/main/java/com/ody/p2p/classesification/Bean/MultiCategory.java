package com.ody.p2p.classesification.Bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.category.Category;

/**
 * Created by Administrator on 2016/11/30.
 */

public class MultiCategory implements MultiItemEntity {
    //    categoryId: 4362,
//    categoryName: "手机通讯",
//    pictureUrl: null,
//    categoryTreeNodeId: 4362
    public static final int TYPE_SELECTION = 0;
    public static final int TYPE_CONTENT = 1;
    public static final int TYPE_AD = 2;
    public int itemType;
    public Ad ad;
    public Category category;

    @Override
    public int getItemType() {
        return itemType;
    }
}