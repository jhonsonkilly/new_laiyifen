package com.ody.p2p.productdetail.store;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.productdetail.store.bean.StoreBaseInfo;
import com.ody.p2p.retrofit.store.AttentionBean;
import com.ody.p2p.retrofit.store.AttentionNumberBean;
import com.ody.p2p.retrofit.store.DoAttentionBean;

/**
 * 店铺
 */

public interface StoreHomeContract {

    interface View extends BaseView{
        void setStoreBaseInfo(StoreBaseInfo info);

        void likeStatus(AttentionBean attentionBean);

        void likeOrCancel(DoAttentionBean doAttentionBean);

        void getAttentionNumber(AttentionNumberBean attentionNumberBean);

    }

    interface Presenter {

        void getStoreBaseInfo(String merchantId);

        void getLikeStatus(String entityId);

        void like(String entityId);

        void cancelLike(String entityId);

        void getAttentionNumber(String entityId);

    }


}
