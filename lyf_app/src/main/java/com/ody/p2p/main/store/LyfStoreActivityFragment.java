package com.ody.p2p.main.store;

import android.content.Context;
import android.view.View;

import com.ody.p2p.productdetail.store.fragment.StoreActivityFragment;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.store.StoreActivityBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 2017/7/21.
 */

public class LyfStoreActivityFragment extends StoreActivityFragment {


    @Override
    public void initView(View view) {
        super.initView(view);

        LyfStoreHomeActivity lyfStoreHomeActivity = (LyfStoreHomeActivity) getContext();
        lyfStoreHomeActivity.setLoadMoreListener(new LyfStoreHomeActivity.LoadMoreListener() {
            @Override
            public void loadMore() {
                loadMoreData();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initStoreActivity(StoreActivityBean storeActivityBean) {
        super.initStoreActivity(storeActivityBean);

    }

//    static class StoreActivityHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1) {
//                loadMoreData();
//            }
//        }
//    }

    private void loadMoreData() {
        ++currentPage;
        int totalPage = countTotal / 20;
        int count = countTotal % 20;

        if (count > 0) {
            ++totalPage;
        }
        if (currentPage > totalPage) {
            ll_footer.setVisibility(View.VISIBLE);
            ll_loading.setVisibility(View.GONE);
            tv_no_more.setVisibility(View.VISIBLE);
            return;
        }
        RetrofitFactory.getMerchantPromotionList(merchantId, "2,3,4", currentPage, currentPage, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StoreActivityBean>(new SubscriberListener<StoreActivityBean>() {
                    @Override
                    public void onNext(StoreActivityBean storeActivityBean) {
                        updateData(storeActivityBean);
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);

                    }
                }));
    }
}
