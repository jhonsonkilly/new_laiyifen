package com.ody.p2p.main.store;

import android.content.Context;
import android.os.Bundle;

import com.ody.p2p.productdetail.store.StoreHomeActivity;

/**
 * Created by meijunqiang on 2017/6/29.
 * 描述:商家店铺主页
 */

public class LyfStoreHomeActivity extends StoreHomeActivity {
//    private LyfStoreAllShopFragment.StoreAllShopHandler allShopHandler;
//    private LyfStoreActivityFragment.StoreActivityHandler storeActivityHandler;

    @Override
    public void doBusiness(Context mContext) {
        String merchantId = (String) getIntent().getExtras().get("merchantId");
        Bundle args = new Bundle();
        args.putString("merchantId", merchantId);
        mStoreAllShopFragment = new LyfStoreAllShopFragment();
        mStoreAllShopFragment.setArguments(args);
        mStoreNewShopFragment = new LyfStoreNewShopFragment();
        mStoreNewShopFragment.setArguments(args);
        mStoreActivityFragment = new LyfStoreActivityFragment();
        mStoreNewShopFragment.setArguments(args);
        super.doBusiness(mContext);
    }

    @Override
    public void initListener() {
        super.initListener();
//        storeHomeFlContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (!StringUtils.isEmpty(currentFragment) && currentFragment.equals("LyfStoreAllShopFragment")){
////                    if (scrollY > oldScrollY) {
////                        Log.e("test", "Scroll DOWN");
////                    }
////                    if (scrollY < oldScrollY) {
////                        Log.e("test", "Scroll UP");
////                    }
////
////                    if (scrollY == 0) {
////                        Log.e("test", "TOP SCROLL");
////                    }
//
//                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
////                        if (allShopHandler == null) {
////                            allShopHandler = new LyfStoreAllShopFragment.StoreAllShopHandler();
////                        }
////                        allShopHandler.sendEmptyMessage(1);
//                        if(loadMoreListener != null){
//                            loadMoreListener.loadMore();
//                        }
//                    }
//                }
//
//                if (!StringUtils.isEmpty(currentFragment) && currentFragment.equals("LyfStoreActivityFragment")){
//                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
////                        if(storeActivityHandler == null){
////                            storeActivityHandler = new LyfStoreActivityFragment.StoreActivityHandler();
////                        }
////                        storeActivityHandler.sendEmptyMessage(1);
//                        if(loadMoreListener != null){
//                            loadMoreListener.loadMore();
//                        }
//                    }
//                }
//
//            }
//        });
    }

    public interface LoadMoreListener{
        void loadMore();
    }

    public LoadMoreListener loadMoreListener;

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}