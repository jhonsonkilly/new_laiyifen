package com.ody.p2p.productdetail.store.storecategory;

import com.ody.p2p.Constants;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${tang} on 2017/7/18.
 */

public class ShopCatePresenter {
    private ShopCateView shopCateView;

    public ShopCatePresenter(ShopCateView shopCateView){
        this.shopCateView=shopCateView;
    }

    protected void getCategoryId(String merchantId){
        Map<String,String> params=new HashMap<>();
        params.put("merchantId",merchantId);
        params.put("cateTreeType","1");
        OkHttpManager.postAsyn(Constants.GET_SHOP_CATEGORY_ID, new OkHttpManager.ResultCallback<ShopCateParentBean>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ShopCateParentBean response) {
                if(response.code.equals("0")){
                    shopCateView.cateIdresult(response);
                }
            }
        }, params);
    }

    protected void getCategoryTree(String merchantId,String id){
        Map<String,String> params=new HashMap<>();
        params.put("merchantId",merchantId);
        params.put("id",id);
        OkHttpManager.postAsyn(Constants.GET_SHOP_CATEGORY_TREE, new OkHttpManager.ResultCallback<ShopCateSubBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ShopCateSubBean response) {
                if(response.code.equals("0")){
                    shopCateView.shopCateTree(response);
                }
            }
        }, params);
    }
}
