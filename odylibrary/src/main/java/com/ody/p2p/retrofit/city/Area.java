package com.ody.p2p.retrofit.city;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2017/1/20.
 */

public class Area implements MultiItemEntity {
    public String code;
    public String name;
    public String id;

    @Override
    public int getItemType() {
        return 1;
    }
}
