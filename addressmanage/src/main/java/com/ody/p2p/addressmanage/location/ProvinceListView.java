package com.ody.p2p.addressmanage.location;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface ProvinceListView {

    void setProvinceData(List<MultiAddress> list);

    void setSecondLevelData(List<MultiItemEntity> list);

    void expand(int position, List<MultiItemEntity> list);

    void fillCharacterData(List<String> list);

    void finishActivity();
}
