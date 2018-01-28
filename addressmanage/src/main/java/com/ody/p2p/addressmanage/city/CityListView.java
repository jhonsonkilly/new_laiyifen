package com.ody.p2p.addressmanage.city;

import com.ody.p2p.retrofit.city.MultiCity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface CityListView {
    void fillData(List<MultiCity> list, boolean isShowIndex);
    void fillCharacterData(List<String> list);
}
