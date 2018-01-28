package com.ody.p2p.productdetail.store.fragment;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.productdetail.store.bean.StoreActivityInfo;

/**
 * 店铺活动
 */

public interface StoreActivityContract {

    interface View extends BaseView {
        void setStoreActivityInfo(StoreActivityInfo info);
    }

    interface Presenter {

        void getStoreActivityInfo(long merchantId, int currentPage, int pageSize);
    }


}
