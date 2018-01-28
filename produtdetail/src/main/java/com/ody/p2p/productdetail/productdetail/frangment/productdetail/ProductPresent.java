package com.ody.p2p.productdetail.productdetail.frangment.productdetail;

import com.ody.p2p.productdetail.productdetail.bean.AddressBean;
import com.ody.p2p.views.slidepager.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ody on 2016/6/1.
 */
public interface ProductPresent {
    int TYPE_SERIALPRODUCT = 1;//系列属性
    int TYPE_ASSOCIATEPRODUCTS = 2;//关联商品
    void selectProduct(String mpID, int type);//总数据

    void SerialProducts(String mpID,int Serialtype);//规格

    void toAddress(String mpID);    //选择地址

    void getDeliveryAddress();//获取收获地址

    void addShopCard(String mpId, int ProductNums);

    void latelyCommend(String mpId, int hasPic);//评价

/*
    void sales(String s);//促销
    void getTicket(String s);//优惠券
    void minus(String s);//满减
*/

    void newGuessYouLike(String mpid);//最新的猜你喜欢，以后都用这种
//    void guessYouLike(String mpid);//猜你喜欢

    void clickPhoto(ArrayList<BannerBean> bannerList, int position);

    void Enshrine(int i, String mpId);

    void cancelEnshrine(String mpId);

    void productStandard(String pid);


    void cartNum();

    void proMotion(String mpId);


    void deliveryFeeDesc(String mpId);

    void setPromotionUrl(List<String> promotionIconUrls);

    void addHistory(String mpId);

    void getDeliveryTime(String mpid, AddressBean.Address address);

    void getCurrentPrice(String mpIds);

    void getGuessLikePrice(String mpIds);

    void getCollectionStatus(String entityId);

    void getGroupAd(String adCode);

}
