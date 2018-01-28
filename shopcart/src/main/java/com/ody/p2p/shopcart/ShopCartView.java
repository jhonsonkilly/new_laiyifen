package com.ody.p2p.shopcart;

import android.content.Context;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.views.basepopupwindow.CouponBean;
import com.ody.p2p.views.basepopupwindow.PropertyBean;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public interface ShopCartView extends BaseView {

    void loadOnError(String msg);

    void clearData();

    void loadFaile();

    void initSummary(ShopCartBean.Summary summary);

    void initProductData(List<ShopData> data, int isSelectAll);

    void initRecommedData(List<ShopData> data);

    void showPropertyWindow(PropertyBean bean);

    void editShow(boolean falge);

    void removeItem(int postion);

    Context getClassContext();

    void getCoupon(CouponBean Bean);

    String getShopCartVersion();

    void showPop(SeparateBean bean);
    void initScrollBanner(AdData bean);

}
