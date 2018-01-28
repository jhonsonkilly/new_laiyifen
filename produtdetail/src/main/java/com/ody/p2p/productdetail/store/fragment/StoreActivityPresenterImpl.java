package com.ody.p2p.productdetail.store.fragment;

import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.store.StoreActivityBean;
import com.ody.p2p.retrofit.store.StoreActivityCountBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 2017/7/19.
 */

public class StoreActivityPresenterImpl implements StoreActivityPresenter {

    private String merchantId;
    private StoreActivityView lyfStoreActivityView;

    public StoreActivityPresenterImpl(StoreActivityView lyfStoreActivityView, String merchantId){
        this.lyfStoreActivityView = lyfStoreActivityView;
        this.merchantId = merchantId;
    }
    @Override
    public void getAllStoreActivity(Integer currentPage) {
        lyfStoreActivityView.showLoading();
        RetrofitFactory.getMerchantPromotionList(merchantId, "2,3,4", 1, 1, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StoreActivityBean>(new SubscriberListener<StoreActivityBean>() {
                    @Override
                    public void onNext(StoreActivityBean storeActivityBean) {
                        lyfStoreActivityView.hideLoading();
                        if (storeActivityBean != null){
                            lyfStoreActivityView.initStoreActivity(storeActivityBean);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        lyfStoreActivityView.hideLoading();
                    }
                }));
    }

    @Override
    public void getCount() {
        RetrofitFactory.getMerchantPromotionListCount(merchantId, "2,3,4", 1, true, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StoreActivityCountBean>(new SubscriberListener<StoreActivityCountBean>() {
                    @Override
                    public void onNext(StoreActivityCountBean storeActivityCountBean) {
                        if (storeActivityCountBean != null){
                            lyfStoreActivityView.getCount(storeActivityCountBean);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);

                    }
                }));
    }
}
