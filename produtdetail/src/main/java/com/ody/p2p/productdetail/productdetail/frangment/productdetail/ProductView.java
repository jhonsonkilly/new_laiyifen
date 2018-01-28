package com.ody.p2p.productdetail.productdetail.frangment.productdetail;

import android.content.Context;

import com.ody.p2p.PromotionInfo;
import com.ody.p2p.base.BaseView;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.productdetail.productdetail.bean.AddressBean;
import com.ody.p2p.productdetail.productdetail.bean.CheckIsFavouriteBean;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.productdetail.productdetail.bean.ProductInfoBean;
import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.productdetail.productdetail.bean.RecommendAdapterBean;
import com.ody.p2p.productdetail.productdetail.bean.UserAdressBean;
import com.ody.p2p.productdetail.productdetail.bean.StandardBean;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.views.basepopupwindow.CouponBean;
import com.ody.p2p.views.basepopupwindow.PropertyBean;

import java.util.List;

/**
 * Created by ody on 2016/6/1.
 */
public interface ProductView extends BaseView {
    void getAllData(ProductInfoBean data,int type);

    void SerialProducts(PropertyBean response);//规格

    void toAddress(UserAdressBean adress);//地址

    void deliveryAddress(AddressBean response);//收货地址

    void setCommendData(ProductComment.Data.MpcList mpcList, String ratingUserCount, Integer positiveRate);//评价
/*
    void sales();

    void preferential();

    void minus(MinuBean bean);*/

    void onImageClick(List<String> urls, int postion);//点击图片放大

    void guessYouLike(Recommedbean data);

    void getTicket(CouponBean bean);


    void addShopCarCode();

    void standard(StandardBean bean);

    void cartNum(int data);


    void noHavePradut();

    void loadingError(String error);

    void toast(boolean b);

    void backGetAllDataToJson(String json, int type) ;

    void delivery(String data);

    void proMotion(List<PromotionBean.Data.PromotionInfo.Promotions> promotions);

    void setPromotionUrls(List<PromotionInfo> bean);

    void doError(boolean b, int type);

    void getDelivetyTime(String message);//预计送达时间

    Context context();

    void setCurrentPrice(StockPriceBean bean);

    void setGuessLikePrice(StockPriceBean bean);

    void checkIsfavourite(CheckIsFavouriteBean checkIsFavouriteBean);

    void initGroupAd(FuncBean bean);
}
