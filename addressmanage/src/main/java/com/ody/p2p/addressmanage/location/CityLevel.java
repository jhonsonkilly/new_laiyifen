package com.ody.p2p.addressmanage.location;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ody.p2p.retrofit.city.Area;

/**
 * Created by Administrator on 2017/1/20.
 */

public class CityLevel extends AbstractExpandableItem<Area> implements MultiItemEntity {
    public boolean isExpand = false;
    public Area city;

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return SecondLevelAdapter.TYPE_LEVEL_0;
    }
}
