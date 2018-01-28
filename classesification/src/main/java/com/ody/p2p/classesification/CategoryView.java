package com.ody.p2p.classesification;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.classesification.Bean.MultiCategory;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.category.Category;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public interface CategoryView extends BaseView{

    void setParentCategory(List<Category> list);

    void setSubCategory(List<MultiCategory> list);

    void setCategoryAd(Ad ad);

    void refreshAdapter(int index);

    void setHintValue(String hintValue);

    void onRefreshComplete();

    void setUnReadCount(int count);
}
