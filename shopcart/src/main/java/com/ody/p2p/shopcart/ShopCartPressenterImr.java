package com.ody.p2p.shopcart;

import android.os.Bundle;
import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.retrofit.adviertisement.AdPageCode;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.basepopupwindow.CouponBean;
import com.ody.p2p.views.basepopupwindow.PropertyBean;
import com.ody.p2p.widget.ShopCartConstants;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class ShopCartPressenterImr implements ShopCartPressenter {

    ShopCartView mView;
    List<ShopData> ListShopData;
    List<ShopData> RecommedData;//猜你喜欢推荐
    int flage = 1;//标识是否全选
    List<ShopCartBean.ProductList> keyMpId;//购物车的id集合
    String allMpId = "";//购物车的id集合(逗号分开的)

    public ShopCartPressenterImr(ShopCartView view) {
        this.mView = view;
    }

    /**
     * 初始化购物车数据
     */
    @Override
    public void shopCartData() {
        EventbusMessage eventbus = new EventbusMessage();
        eventbus.flag = EventbusMessage.GET_CART_COUNT;
        EventBus.getDefault().post(eventbus);
        Map<String, String> params = new HashMap<>();
        params.put("v", mView.getShopCartVersion());
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("areaCode", OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
        params.put("sessionId", OdySysEnv.getSessionId());
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.SHOP_CART/*url*/, new OkHttpManager.ResultCallback<ShopCartBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadFaile();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                mView.loadFaile();
                ToastUtils.failToast(msg);
            }

            @Override
            public void onNetError() {
                super.onNetError();
                mView.loadFaile();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(ShopCartBean response) {
                mView.editShow(false);
                mView.hideLoading();
                if (null != response && null != response.getData() && null != response.getData().getSummary()) {
                    mView.initProductData(getShopData(response.getData()), flage);
                    mView.editShow(true);//购物车有数据
                } else {
                    recommedData("");
                    ListShopData = new ArrayList<ShopData>();
                    ShopData dataNull = new ShopData();
                    dataNull.setItemType(99);
                    ListShopData.add(dataNull);
                    mView.initProductData(ListShopData, flage);
                    mView.editShow(false);
                }
                if (null != response && null != response.getData() && null != response.getData().getSummary()) {
                    mView.initSummary(response.getData().getSummary());
                } else {
                    mView.initSummary(null);
                }
            }
        }, params);
    }

    private List<ShopData> getShopData(ShopCartBean.Data bean) {
        ListShopData = new ArrayList<ShopData>();
        keyMpId = new ArrayList<>();
        flage = 1;
        //注：：V=1.2,1.4的时候用
        if (mView.getShopCartVersion().equals(ShopCartConstants.VERSION_1_2) || mView.getShopCartVersion().equals(ShopCartConstants.VERSION_1_4)) {
            if (null != bean.getMerchantList() && bean.getMerchantList().size() > 0) {
                for (ShopCartBean.MerchantList mer : bean.getMerchantList()) {
                    ShopData data = new ShopData();
                    data.setItemType(ShopCartConstants.ITEM_MERCHANT);
                    data.setMerchantList(mer);
                    ListShopData.add(data);
                    if (null != mer.getProductGroups() && mer.getProductGroups().size() > 0) {
                        for (int i = 0; i < mer.getProductGroups().size(); i++) {
                            ShopCartBean.ProductGroups proGroups = mer.getProductGroups().get(i);
                            if (null != proGroups.getPromotion()) {
                                ShopData dataGroups = new ShopData();
                                dataGroups.setItemType(ShopCartConstants.ITEM_PROMOTION);
                                dataGroups.setPromotion(proGroups.getPromotion());
                                dataGroups.setGiftProductList(proGroups.getGiftProductList());
                                ListShopData.add(dataGroups);
                            } else if (i != 0) {
                                ShopData dataGroups = new ShopData();
                                dataGroups.setItemType(ShopCartConstants.ITEM_NBSP);
                                ListShopData.add(dataGroups);
                            }
                            if (null != proGroups.getProductList() && proGroups.getProductList().size() > 0) {
                                for (ShopCartBean.ProductList proList : proGroups.getProductList()) {
                                    ShopData proLists = new ShopData();
                                    proLists.setItemType(ShopCartConstants.ITEM_PRODUCT);
                                    proLists.setProductList(proList);
                                    ListShopData.add(proLists);
                                    if (proList.getDisabled() != 1) {//不可选的不管
                                        if (proList.getChecked() == 0/* || proList.getDisabled() == 1*/) {
                                            flage = 0;//是否全选
                                        }
                                    }
                                    keyMpId.add(proList);
                                }
                            }
                            if (null != proGroups.getGiftProductList() && proGroups.getGiftProductList().size() > 0) {
                                for (ShopCartBean.GiftProductList gif : proGroups.getGiftProductList()) {
                                    for (ShopCartBean.GiftProducts giftProducts : gif.getGiftProducts()) {
                                        if (giftProducts.getChecked() == 1) {
                                            ShopData giftProduct = new ShopData();
                                            giftProduct.setItemType(ShopCartConstants.ITEM_GIF_PRODUCT);
                                            if (null != proGroups.getPromotion()) {
                                                giftProduct.setPromotion(proGroups.getPromotion());
                                            }
                                            giftProduct.setGiftProducts(giftProducts);
                                            ListShopData.add(giftProduct);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
//            注：：V=1.3的时候用
            for (ShopCartBean.MerchantList mer : bean.getMerchantList()) {
                if (null != mer.getOverseas() && mer.getOverseas().size() > 0) {
                    for (ShopCartBean.Overseas over : mer.getOverseas()) {
                        ShopData data = new ShopData();
                        data.setItemType(ShopCartConstants.ITEM_OVERSEAS);
                        data.setOverseas(over);
                        ListShopData.add(data);
                        if (null != over.getProductGroups() && over.getProductGroups().size() > 0) {
                            for (ShopCartBean.ProductGroups proGroups : over.getProductGroups()) {
                                if (null != proGroups.getPromotion()) {
                                    ShopData dataGroups = new ShopData();
                                    dataGroups.setItemType(ShopCartConstants.ITEM_PROMOTION);
                                    dataGroups.setPromotion(proGroups.getPromotion());
                                    ListShopData.add(dataGroups);
                                }
                                if (null != proGroups.getProductList() && proGroups.getProductList().size() > 0) {
                                    for (ShopCartBean.ProductList proList : proGroups.getProductList()) {
                                        ShopData proLists = new ShopData();
                                        proLists.setItemType(ShopCartConstants.ITEM_PRODUCT);
                                        proLists.setProductList(proList);
                                        ListShopData.add(proLists);
                                        if (proList.getDisabled() != 1) {//不可选的不管
                                            if (proList.getChecked() == 0/* || proList.getDisabled() == 1*/) {
                                                flage = 0;//是否全选
                                            }
                                        }
                                        keyMpId.add(proList);
                                    }
                                }
                                if (null != proGroups.getGiftProductList() && proGroups.getGiftProductList().size() > 0) {
                                    for (ShopCartBean.GiftProductList gif : proGroups.getGiftProductList()) {
                                        for (ShopCartBean.GiftProducts giftProducts : gif.getGiftProducts()) {
                                            ShopData giftProduct = new ShopData();
                                            giftProduct.setItemType(ShopCartConstants.ITEM_GIF_PRODUCT);
                                            giftProduct.setGiftProducts(giftProducts);
                                            ListShopData.add(giftProduct);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //失效品
        if (null != bean.getFailureProducts() && bean.getFailureProducts().size() > 0) {
            ShopData fendata = new ShopData();
            fendata.setItemType(ShopCartConstants.ITEM_NBSP);
            ListShopData.add(fendata);
            for (ShopCartBean.ProductList failProduct : bean.getFailureProducts()) {
                ShopData faildata = new ShopData();
                faildata.setItemType(ShopCartConstants.ITEM_FAILE_PRODUCT);
                faildata.setFailureProducts(failProduct);
                ListShopData.add(faildata);
            }
            ShopData titledata = new ShopData();
            titledata.setItemType(ShopCartConstants.ITEM_CLEAN_FAILEPRODUCT);
            ListShopData.add(titledata);
        }
        //如果所有的mpid相同，则不重新拿猜你喜欢
        if (allMpId.equals(getAllMpId(ListShopData)) && null != RecommedData && RecommedData.size() > 0) {
            for (ShopData recom : RecommedData) {
                ListShopData.add(recom);
            }
        } else {
            allMpId = getAllMpId(ListShopData);
            recommedData(allMpId);
        }
        return ListShopData;
    }

    /**
     * 获取所有的mpid
     *
     * @param shopData
     * @return
     */
    public String getAllMpId(List<ShopData> shopData) {
        String mpids = "";
        if (null != keyMpId && keyMpId.size() > 0) {
            for (ShopCartBean.ProductList sData : keyMpId) {
                mpids += sData.getMpId() + ",";
            }
        }
        return mpids.length() > 1 ? mpids.toString().substring(0, mpids.length() - 1) : "";
    }

    /**
     * 猜你喜欢
     */
    @Override
    public void recommedData(String mpids) {
        Map<String, String> params = new HashMap<>();
        params.put("sceneNo", "2");//推荐商品场景，0,首页;1,详情页;,2购物车;3,订单页;4,搜索页无搜索结果
        params.put("mpIds", mpids);//商品Id，用,隔开
        params.put("pageNo", "1");
        params.put("pageSize", "32");//每页4个，最多8页
        params.put("areaCode", OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
        OkHttpManager.getAsyn(Constants.SHOPCART_RECOMMEND, params, new OkHttpManager.ResultCallback<Recommedbean>() {

            @Override
            public void onResponse(Recommedbean response) {
                if (null != response && null != response.getData()) {
                    getRecommedData(response);
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    /**
     * 初始化猜你喜欢
     *
     * @param bean
     */
    private void getRecommedData(Recommedbean bean) {
        if (ListShopData.size() > 0) {
//            mView.editShow(true);//购物车有数据
        } else {
            ShopData dataNull = new ShopData();
            dataNull.setItemType(ShopCartConstants.ITEM_NULDATA);
            ListShopData.add(dataNull);
            RecommedData.add(dataNull);
//            mView.editShow(false);//购物车为空
        }
        if (null != bean && null != bean.getData() && null != bean.getData().getDataList() && bean.getData().getDataList().size() > 0) {
            RecommedData = new ArrayList<>();
            ShopData data = new ShopData();
            data.setItemType(ShopCartConstants.ITEM_TITLE_RECOMMEND);
            ListShopData.add(data);
            RecommedData.add(data);
//          for (Recommedbean.Data datas : bean.getData()) {
            ShopData recommedData = new ShopData();
            recommedData.setItemType(ShopCartConstants.ITEM_RECOMMEND);
            recommedData.setRecommed(bean.getData().getDataList());
            ListShopData.add(recommedData);
            RecommedData.add(recommedData);
//            }
        }
        mView.initRecommedData(ListShopData);
    }

    /**
     * 编辑购物车数量
     *
     * @param holder
     * @param number
     */
    int addCount = 0;
    int minusCount = 0;

    @Override
    public void editShopcartNum(ShopCartBean.ProductList product, final int number, final int flag) {
//        http://test.odianyun.com/api/cart/editItemNum
        Map<String, String> params = new HashMap<>();
        params.put("num", number + "");
        params.put("mpId", product.getMpId() + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));

        //奖品新增两个参数
        params.put("itemType", product.getItemType() + "");
        params.put("objectId", product.getObjectId() + "");

        mView.showLoading();
        OkHttpManager.getAsyn(Constants.EDIT_CART_NUM, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (flag == 1) {//加购物车操作
                    addCount++;
                    //response.getData().itemNum+""-------------这个是购物车里当前商品的总数,但是目前要求只传本次操作改变的数量,所以用本地的count
//                    RecordHelper.getInstance().addCart(name, type, addCount + "", "null", "ShoppingCartActivity", "null", "ShoppingCartActivity");
                } else if (flag == 2) {//减购物车操作
                    minusCount++;
//                    RecordHelper.getInstance().minusCart(name, type, minusCount + "", "null", "ShoppingCartActivity", "null", "ShoppingCartActivity");
                }
                shopCartData();
            }
        });
    }

    /**
     * 删除
     *
     * @param product
     * @param postion
     */
    @Override
    public void deleteProduct(final ShopCartBean.ProductList product, final int postion) {
//        http://test.odianyun.com/api/cart/removeItem
        Map<String, String> params = new HashMap<>();
        params.put("mpId", product.getMpId() + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));

        //奖品新增两个参数
        params.put("itemType", product.getItemType() + "");
        params.put("objectId", product.getObjectId() + "");

        mView.showLoading();
        OkHttpManager.getAsyn(Constants.DELEDCT_PRODUCT, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                minusCount++;
//                RecordHelper.getInstance().minusCart(product.getName(), product.getSaleType() + "", minusCount + "", "null", "ShoppingCartActivity", "null", "ShoppingCartActivity");
                shopCartData();
            }
        });
    }

    @Override
    public void attentionProduct(ShopCartBean.ProductList product) {
        mView.loadOnError("onError");
    }

    /**
     * 清除失效商品
     */
    @Override
    public void clearFailProduct() {
//        http://test.odianyun.com/api/cart/clear
// companyId=1
// platfromId=0
// sessionId=860735030066732
// provinceId=10
// ut=f4353d1044d141029e6617d60773677d
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.DELEDCT_CLEARFAILURE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                shopCartData();
            }
        }, params);
    }

    /**
     * 编辑选中
     *
     * @param product
     */
    @Override
    public void checkedItem(final ShopCartBean.ProductList product) {
// http://test.odianyun.com/api/cart/editItemCheck
        Map<String, String> params = new HashMap<>();
        params.put("checkStr", product.getMpId() + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        //由于奖品可以加车,所以checkStr=mpId-1-itemType-objectId,mpId-0-itemType-objectId
        if (product.getChecked() == 0) {
//            params.put("checkStr", product.getMpId() + "-" + 1);
            params.put("checkStr", product.getMpId() + "-" + 1 + "-" + product.getItemType() + "-" + product.getObjectId());
        } else {
//            params.put("checkStr", product.getMpId() + "-" + 0);
            params.put("checkStr", product.getMpId() + "-" + 0 + "-" + product.getItemType() + "-" + product.getObjectId());
        }
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.CHECKED_ITEM_PRODUCT, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                shopCartData();
            }
        });
    }

    /**
     * 系列属性
     *
     * @param productID
     */
    @Override
    public void getProperty(String productID) {
        Map<String, String> params = new HashMap<>();
        mView.showLoading();
        params.put("mpsIds", productID + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("isDetail", true + "");
        OkHttpManager.getAsyn(Constants.PROPERTY_PRODUCT, params, new OkHttpManager.ResultCallback<PropertyBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(PropertyBean response) {
                mView.showPropertyWindow(response);
            }
        });
    }

    /**
     * 更换赠品
     *
     * @param promotionId 促销ID
     * @param mpIds       赠品ID,以逗号隔开
     */
    @Override
    public void UpdateGift(long promotionId, String mpIds) {
        Map<String, String> params = new HashMap<>();
        mView.showLoading();
//        ut	String	否	用户登录时，必填
//        mpIds	String	是	赠品ID，已逗号隔开
//        promotionId	Long	是	促销ID
        params.put("mpIds", mpIds);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("promotionId", promotionId + "");
        OkHttpManager.getAsyn(Constants.CART_UPDATEGIFT, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                shopCartData();
            }
        });
    }

    /**
     * 检查是否需要拆单
     */
    @Override
    public void prepareCheck() {
        Map<String, String> params = new HashMap<>();
        mView.showLoading();
//        ut	String	否	用户登录时，必填
//        mpIds	String	是	赠品ID，已逗号隔开
//        promotionId	Long	是	促销ID
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.PREPARE_CHECKOUT, params, new OkHttpManager.ResultCallback<SeparateBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(SeparateBean response) {
                if (response != null) {
                    mView.showPop(response);
                }
            }
        });
    }

    /**
     * 更换商品的系列属性
     *
     * @param oldMpId
     * @param newMpId
     * @param num
     */
    @Override
    public void UpdatepProduct(String oldMpId, String newMpId, int num) {
        Map<String, String> params = new HashMap<>();
        mView.showLoading();
//        ut	String	否	用户登录时，必填
//        oldMpId	Long	是	老的商品ID
//        newMpId	Long	是	新的商品ID
//        num	Integer	否	商品数量
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("oldMpId", oldMpId + "");
        params.put("newMpId", newMpId + "");
        params.put("num", "" + num);
        OkHttpManager.getAsyn(Constants.CART_UPDATEPRODUCT, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                shopCartData();
            }
        });
    }

    /**
     * 全选
     */
    @Override
    public void selectAll(String checkedStr) {
//      http://test.odianyun.com/api/cart/editItemCheck?
// companyId=1
// checkStr=946-0%2C656-0%2C689-0%2C632-0%2C826-0%2C1293-0%2C596-0%2C600-0%2C809-0%2C972-0%2C606-0%2C571-0%2C1290-0%2C460-0
// platfromId=0
// sessionId=860735030066732
// provinceId=10
// ut=f4353d1044d141029e6617d60773677d
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        if (null != checkedStr && checkedStr.length() > 0) {
            params.put("checkStr", checkedStr);
        } else {
            String str = "";
            int checked = 0;
            if (flage == 1) {
                checked = 0;
            } else {
                checked = 1;
            }
//            for (int i = 0; i < keyMpId.size(); i++) {
//                str += keyMpId.get(i).getMpId() + "-" + checked + ",";
//            }
            //包含奖品
            for (int i = 0; i < keyMpId.size(); i++) {
                str += keyMpId.get(i).getMpId() + "-" + checked + "-" + keyMpId.get(i).getItemType() + "-" + keyMpId.get(i).getObjectId() + ",";
            }
            if (str.length() > 0) {
                params.put("checkStr", str.substring(0, str.length() - 1));
            }
        }
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.CHECKED_ITEM_PRODUCT, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                shopCartData();
            }
        });
    }


    /**
     * 领券
     *
     * @param overseaId
     */
    @Override
    public void getCouponBean(String overseaId) {
        Map<String, String> params = new HashMap<>();
        mView.showLoading();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.PROPERTY_GET_COUPON, params, new OkHttpManager.ResultCallback<CouponBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onResponse(CouponBean response) {
                if (null != response.getData()) {
                    mView.getCoupon(response);
                }
            }
        });
    }

    /**
     * 去结算
     */
    @Override
    public void toConfirmorder() {
        String SelectedMpid = "";
        if (null != ListShopData && ListShopData.size() > 0) {
            for (ShopData sd : ListShopData) {
                if (sd.getItemType() == 2 && null != sd.getProductList() && sd.getProductList().getChecked() != 0) {
                    SelectedMpid += sd.getProductList().getMpId() + ",";
                }
            }
        }
        if (null != SelectedMpid && SelectedMpid.length() > 0) {
//            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://confirmorder");
//            activityRoute.withParams(Constants.SP_ID, SelectedMpid.substring(0, SelectedMpid.length() - 1));
//            activityRoute.open();

            Bundle bd = new Bundle();
            bd.putString(Constants.SP_ID, SelectedMpid.substring(0, SelectedMpid.length() - 1));
            JumpUtils.ToActivity(JumpUtils.CONFIRMORDER, bd);
        } else {
            //没有匹配的Mpid
        }
    }

    @Override
    public void deleteSelected(String mpIds) {
        Map<String, String> params = new HashMap<>();
        params.put("mpIds", mpIds);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));

        mView.showLoading();
        OkHttpManager.getAsyn(Constants.DELETE_SELECTED, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response.code.equals("0")) {
                    shopCartData();
                }
            }
        });
    }

    @Override
    public void addFavorite(String entityIds) {
        Map<String, String> params = new HashMap<>();
        params.put("entityIds", entityIds);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.ADDALL_FAVORITE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.loadOnError("onError");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response.code.equals("0")) {
                    shopCartData();
                    ToastUtils.sucessToast("收藏成功");
                }
            }
        }, params);
    }

    @Override
    public void getAdvertisement() {
//        Map<String, String> params = new HashMap<>();
//        params.put("categoryType", "2");
//        params.put("currentPage", "1");
////        params.put("code", "headlines");
//        params.put("code", "notice_shopping");
//        params.put("itemsPerPage", "5");
//        OkHttpManager.getAsyn(Constants.HEAD_LIST, params, mView.getClassContext().getClass().toString(), new OkHttpManager.ResultCallback<HeadLinesBean>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                Log.e("test", "onError=========================");
//            }
//
//            @Override
//            public void onResponse(HeadLinesBean response) {
//                if (response != null) {
//                    mView.initScrollBanner(response);
//                }
//            }
//        });

        RetrofitFactory.getAd(AdPageCode.APP_SHOPPING_CART_PAGE, "notice_shopping")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AdData>(new SubscriberListener<AdData>() {
                    @Override
                    public void onNext(AdData adData) {
                        if (null != adData && null != adData.notice_shopping && adData.notice_shopping.size() > 0) {
                            mView.initScrollBanner(adData);
                        } else {

                        }
                    }
                }));
    }

    /**
     * 去凑单
     */
    @Override
    public void getToPassAble(String profomtionId) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("promotionId", profomtionId);
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.CART_EXT, params, mView.getClassContext().getClass().toString(), new OkHttpManager.ResultCallback<PassAbleBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(PassAbleBean response) {
                if (response != null) {


                }
            }
        });
    }
}
