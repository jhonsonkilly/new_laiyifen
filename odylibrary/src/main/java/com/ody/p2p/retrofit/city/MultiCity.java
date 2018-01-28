package com.ody.p2p.retrofit.city;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2016/12/5.
 */

public class MultiCity implements MultiItemEntity {
    public static final int SELECTION = 0;
    public static final int TEXT = 1;

    public int itemType;
    public String areaName;
    public String areaCode;

    public MultiCity(){
        itemType = 1;
    }
    @Override
    public int getItemType() {
        return itemType;
    }
}
