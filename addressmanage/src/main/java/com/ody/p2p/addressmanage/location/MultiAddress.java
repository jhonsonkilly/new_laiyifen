package com.ody.p2p.addressmanage.location;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ody.p2p.retrofit.city.Address;
import com.ody.p2p.retrofit.city.Province;

/**
 * Created by Administrator on 2017/1/19.
 */

public class MultiAddress implements MultiItemEntity {

    public static final int SELECTION = 0;
    public static final int TEXT_LOCATION = 1;
    public static final int TEXT_ADDRESS = 2;

    public int itemType;
    public String selectionName;
    public Province province;
    public Address address;

    @Override
    public int getItemType() {
        return itemType;
    }
}
