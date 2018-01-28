package com.ody.p2p.addressmanage.location;

import android.support.annotation.IntRange;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ody.p2p.addressmanage.R;
import com.ody.p2p.retrofit.city.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */

public class SecondLevelAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public SecondLevelAdapter() {
        this(new ArrayList<MultiItemEntity>());
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SecondLevelAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_city);
        addItemType(TYPE_LEVEL_1, R.layout.item_district);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                CityLevel city = (CityLevel) item;
                helper.setText(R.id.cityName, city.city.name);
                break;
            case TYPE_LEVEL_1:
                Area area = (Area) item;
                helper.setText(R.id.districtName, area.name);
                break;
        }
    }

    @Override
    public int expand(@IntRange(from = 0L) int position) {
        CityLevel city = (CityLevel) mData.get(position);
        city.isExpand = true;
        return super.expand(position);
    }

    @Override
    public int collapse(@IntRange(from = 0L) int position) {
        CityLevel city = (CityLevel) mData.get(position);
        city.isExpand = false;
        return super.collapse(position);
    }
}
