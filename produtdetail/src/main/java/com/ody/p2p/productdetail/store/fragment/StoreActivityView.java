package com.ody.p2p.productdetail.store.fragment;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.retrofit.store.StoreActivityBean;
import com.ody.p2p.retrofit.store.StoreActivityCountBean;

/**
 * Created by user on 2017/7/19.
 */

public interface StoreActivityView extends BaseView {
    /**
     * 初始化店铺活动
     * @param storeActivityBean
     */
    void initStoreActivity(StoreActivityBean storeActivityBean);

    /**
     * 获取总数
     * @param storeActivityCountBean
     */
    void getCount(StoreActivityCountBean storeActivityCountBean);
}
