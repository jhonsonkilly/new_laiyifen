package com.ody.p2p.addressmanage.location;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ody.p2p.addressmanage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class ProvinceAdapter extends BaseMultiItemQuickAdapter<MultiAddress, BaseViewHolder> {
    public ProvinceAdapter() {
        this(new ArrayList<MultiAddress>());
    }

    public ProvinceAdapter(List<MultiAddress> data) {
        super(data);
        addItemType(MultiAddress.SELECTION, R.layout.addressmanager_item_selection);
        addItemType(MultiAddress.TEXT_LOCATION, R.layout.addressmanager_item_text);
        addItemType(MultiAddress.TEXT_ADDRESS, R.layout.addressmanager_item_text);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiAddress item) {
        switch (helper.getItemViewType()) {
            case MultiAddress.SELECTION:
                helper.setText(R.id.key, item.selectionName);
                break;
            case MultiAddress.TEXT_LOCATION:
                helper.setText(R.id.name, item.province.areaName);
                break;
            case MultiAddress.TEXT_ADDRESS:
                helper.setText(R.id.name, item.address.provinceName + item.address.cityName + item.address.regionName + item.address.detailAddress);
                break;
        }
    }
}
