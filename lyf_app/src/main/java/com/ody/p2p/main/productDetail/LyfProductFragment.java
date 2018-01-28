package com.ody.p2p.main.productDetail;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseView;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.main.store.CouponListPopWindow;
import com.ody.p2p.productdetail.productdetail.bean.AddressBean;
import com.ody.p2p.productdetail.productdetail.bean.ProductInfoBean;
import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.productdetail.productdetail.frangment.productdetail.ProductFragment;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.recmmend.RecommendAdapter;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.retrofit.adviertisement.AdPageCode;
import com.ody.p2p.retrofit.coupon.CouponThemeBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.PreCommissionBean;
import com.ody.p2p.retrofit.store.ReceiveCouponBean;
import com.ody.p2p.retrofit.store.StoreActivityCountBean;
import com.ody.p2p.retrofit.store.StoreBaseInfo;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.TKUtil;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.ProgressDialog.ReminderDialog;
import com.ody.p2p.views.basepopupwindow.ProductBean;
import com.ody.p2p.views.odyscorllviews.OdyScrollView;
import com.ody.p2p.webactivity.WebActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dsshare.DrawPhotoBean;
import dsshare.SharePopupWindow;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.ody.p2p.base.OdyApplication.BASE_URL;
import static com.ody.p2p.base.OdyApplication.getValueByKey;


/**
 * Created by pzy on 2016/12/6.
 */
public class LyfProductFragment extends ProductFragment implements BaseView {
    OdyScrollView scroll_web;
    ImageView img_mark_price_illustration, img_about_price;

    LinearLayout ll_brand;
    LinearLayout ll_price;
    String branId;//品牌ID
    String brandName;//品牌ID
    String brand_icon;
    TextView tv_origina_price, tv_warehouseName;
    TextView txt_drop_price;//吊牌价格
    TextView txt_sto;//库存
    ImageView img_brandimg;//品牌的ico
    ImageView img_scripts, img_stock;
    LcoationAddressPopWindow window;
    ImageView iv_ziying;//自营标签

    private LinearLayout ll_shop;
    private LinearLayout ll_store;
    private TextView tv_shop_name;  //店铺名字
    private TextView tv_shop_score; //店铺评分
    private TextView tv_attention; //关注
    private TextView tv_all_goods; //全部商品
    private TextView tv_up_new; //上新
    private TextView tv_contact_merchant; //联系商家
    private TextView tv_enter_shop; //进入店铺
    private TextView tv_dispatching;//配送
    private ImageView iv_shop;
    private TextView tv_shop_label;

    //领券
    private LinearLayout ll_coupon;
    private TextView tv_coupon_one;
    private TextView tv_coupon_two;
    private TextView tv_coupon_three;

    private long merchantId;
    private String storeName;
    private String storeScore;
    private String attention;
    private String allGoods;
    private String newGoods;
    private String logoUrl;
    private String storeActivity;
    private String flagName;

//    private LyfChooseCouponPopwindow popwindow;

    private CouponListPopWindow couponListPopWindow;
    private CouponThemeBean mCouponThemeBean;

    //    private ImageView store_img_share;
    private TextView store_tv_share;
    private ArrayList<String> dataList = new ArrayList<>();

    private int isDistribution;
    private DrawPhotoBean drawPhotoBean;


    @Override
    public void initView(View view) {
        setTopPage(R.layout.lyf_produtdetail_produt_detail);
        setBotomm(R.layout.fragment_lyfproduct_detail_web);
        super.initView(view);
        img_mark_price_illustration = (ImageView) view.findViewById(R.id.img_mark_price_illustration);
        img_about_price = (ImageView) view.findViewById(R.id.img_about_price);
        scroll_web = (OdyScrollView) view.findViewById(R.id.scroll_web);
        txt_sto = (TextView) view.findViewById(R.id.txt_sto);
        img_scripts = (ImageView) view.findViewById(R.id.img_scripts);
        img_stock = (ImageView) view.findViewById(R.id.img_stock);
        tv_warehouseName = (TextView) view.findViewById(R.id.tv_warehouseName);
        tv_origina_price = (TextView) view.findViewById(R.id.tv_origina_price);
        ll_brand = (LinearLayout) view.findViewById(R.id.ll_brand);
        txt_drop_price = (TextView) view.findViewById(R.id.txt_drop_price);
        txt_drop_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        img_brandimg = (ImageView) view.findViewById(R.id.img_brandimg);
        ll_price = (LinearLayout) view.findViewById(R.id.ll_price);

        ll_brand.setOnClickListener(this);
//        text_shfw1.setText(R.string.brand_story);

        ll_shop = (LinearLayout) view.findViewById(R.id.ll_shop);
        ll_store = (LinearLayout) view.findViewById(R.id.ll_store);
        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        tv_shop_score = (TextView) view.findViewById(R.id.tv_shop_score);
        tv_attention = (TextView) view.findViewById(R.id.tv_attention);
        tv_all_goods = (TextView) view.findViewById(R.id.tv_all_goods);
        tv_up_new = (TextView) view.findViewById(R.id.tv_up_new);
        tv_contact_merchant = (TextView) view.findViewById(R.id.tv_contact_merchant);
        tv_enter_shop = (TextView) view.findViewById(R.id.tv_enter_shop);
        tv_dispatching = (TextView) view.findViewById(R.id.tv_dispatching);
        iv_shop = (ImageView) view.findViewById(R.id.iv_shop);
        tv_shop_label = (TextView) view.findViewById(R.id.tv_shop_label);

        ll_coupon = (LinearLayout) view.findViewById(R.id.ll_coupon);
        tv_coupon_one = (TextView) view.findViewById(R.id.tv_coupon_one);
        tv_coupon_two = (TextView) view.findViewById(R.id.tv_coupon_two);
        tv_coupon_three = (TextView) view.findViewById(R.id.tv_coupon_three);
        iv_ziying = (ImageView) view.findViewById(R.id.iv_ziying);

//        store_img_share = (ImageView) view.findViewById(R.id.store_img_share);
        store_tv_share = (TextView) view.findViewById(R.id.store_tv_share);

//        store_img_share.setVisibility(View.VISIBLE);
//        store_img_share.setImageResource(R.drawable.ic_distribution);

//        store_tv_share.setVisibility(View.VISIBLE);

        ll_shop.setOnClickListener(this);
        ll_coupon.setOnClickListener(this);
        tv_contact_merchant.setOnClickListener(this);
        tv_enter_shop.setOnClickListener(this);
//        store_img_share.setOnClickListener(this);
        store_tv_share.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);

        setTextcolor(R.color.theme_color);
        text_recommend.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        text_recommend.setEnabled(false);
        setTextThemeColor(R.color.theme_color);
        setThemebutoonbg(R.color.theme_color);
        img_ilike.setVisibility(View.GONE);
        serailType = mPressent.TYPE_ASSOCIATEPRODUCTS;//关联商品
//        isNeedChooseStanrd = false;//来伊份不需要选择商品规格，直接加入购物车
        isNewAddress = false;//来伊份收货地址
//        layout_choose.setVisibility(View.GONE);//隐藏商品规格
        layout_toadresses.setVisibility(View.VISIBLE);//地址隐藏
        produt_pingjia.setVisibility(View.GONE);//评价类目的隐藏
        setCommendThemeResource(R.layout.layout_ratingbar);//评价样式
        img_share.setImageResource(R.drawable.detail_btn_share);//分享图标的更换
        ShareType = SharePopupWindow.SHARE_DETAIL_SMM_MESSAGE;//分享类型
        ll_security.setSingle(true);//服务保障单行显示
        ll_brand.setVisibility(View.GONE);

        adapter_commendAdapter.setViewLayout(R.layout.item_lyfproduct_evaluate);
        getProductAdData("mark_price_illustration,about_price");

        getCouponThemeList();

    }

    private void getPreCommission(String mpId, String salaPrice) {
        RetrofitFactory.getPreCommission(mpId, salaPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<PreCommissionBean>(new SubscriberListener<PreCommissionBean>() {
                    @Override
                    public void onNext(PreCommissionBean preCommissionBean) {
                        if (preCommissionBean != null && preCommissionBean.getData() != null && preCommissionBean.getData().size() > 0) {
                            List<PreCommissionBean.DataBean> list = preCommissionBean.getData();
                            dataList.clear();
                            if (list.size() == 1) {
                                dataList.add(String.valueOf(UiUtils.getDoubleForDouble(list.get(0).getMoney())));
                            }

                            if (list.size() == 2) {
                                dataList.add(String.valueOf(UiUtils.getDoubleForDouble(list.get(0).getMoney())));
                                dataList.add(String.valueOf(UiUtils.getDoubleForDouble(list.get(1).getMoney())));
                            }

                            if (list.size() == 3) {
                                dataList.add(String.valueOf(UiUtils.getDoubleForDouble(list.get(0).getMoney())));
                                dataList.add(String.valueOf(UiUtils.getDoubleForDouble(list.get(1).getMoney())));
                                dataList.add(String.valueOf(UiUtils.getDoubleForDouble(list.get(2).getMoney())));
                            }
                        }

                    }
                }));
    }

//    private void getPreCommissions(String mpId, String salaPrice){
//        RetrofitFactory.getPreCommissions(mpId, salaPrice)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ApiSubscriber<PreCommissionsBean>(new SubscriberListener<PreCommissionsBean>() {
//                    @Override
//                    public void onNext(PreCommissionsBean preCommissionsBean) {

//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        super.onError(msg);

//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();

//                    }
//                }));
//    }

    private void getMerchantPromotionListCount(long merchantId) {
        RetrofitFactory.getMerchantPromotionListCount(String.valueOf(merchantId), "2,3,4", 1, true, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StoreActivityCountBean>(new SubscriberListener<StoreActivityCountBean>() {
                    @Override
                    public void onNext(StoreActivityCountBean storeActivityCountBean) {
                        if (storeActivityCountBean != null) {
                            storeActivity = storeActivityCountBean.getData().getTotal() + "";
                        }

                    }
                }));
    }

    boolean getChooseSerial = false;

    @Override
    public void showPropertyWindow() {
        layout_choose.setVisibility(View.VISIBLE);
        Textchoose.setText(getResources().getString(R.string.already_choose));
        layout_choose.setClickable(true);
        if (getChooseSerial && null != propertywindow) {
            super.showPropertyWindow();
        } else {
            getChooseSerial = true;
            if (null != propertywindow) {
                mSerialTxt.setText(propertywindow.getSerialString() + getString(R.string.piece));
            }
        }
    }

    @Override
    public void ontoch() {
        super.ontoch();
        scroll_web.setScrollListener(new OdyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(OdyScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y == 0) {
                    mcoySnapPageLayout.setScrollType(true);
                } else {
                    mcoySnapPageLayout.setScrollType(false);
                }
            }
        });
    }

    //获取商品规格说明
    public void getProductAdData(final String adCode) {
        RetrofitFactory.getAd(AdPageCode.APP_COMMODITY_DETAILS_PAGE, adCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AdData>(new SubscriberListener<AdData>() {
                    @Override
                    public void onNext(AdData adData) {
                        if (null != adData) {
                            if (null != adData.mark_price_illustration && adData.mark_price_illustration.size() > 0
                                    && BitmapUtil.isImage(adData.mark_price_illustration.get(0).imageUrl)) {
                                img_mark_price_illustration.setVisibility(View.VISIBLE);
                                GlideUtil.display(getContext(), adData.mark_price_illustration.get(0).imageUrl).into(img_mark_price_illustration);
                            } else {
                                img_mark_price_illustration.setVisibility(View.GONE);
                            }
                            if (null != adData.about_price && adData.about_price.size() > 0
                                    && BitmapUtil.isImage(adData.about_price.get(0).imageUrl)) {
                                img_about_price.setVisibility(View.VISIBLE);
                                GlideUtil.display(getContext(), adData.about_price.get(0).imageUrl).into(img_about_price);
                            } else {
                                img_about_price.setVisibility(View.GONE);
                            }
                        } else {
                            img_mark_price_illustration.setVisibility(View.GONE);
                            img_about_price.setVisibility(View.GONE);
                        }
                    }
                }));
    }

    /**
     * 获取优惠券列表
     */
    private void getCouponThemeList() {
        RetrofitFactory.getCouponThemeList(getValueByKey(Constants.USER_LOGIN_UT, ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<CouponThemeBean>(new SubscriberListener<CouponThemeBean>() {
                    @Override
                    public void onNext(CouponThemeBean couponThemeBean) {
                        if (couponThemeBean != null) {
                            mCouponThemeBean = couponThemeBean;
                            initCouponWindow(mCouponThemeBean);
                            couponListPopWindow.refreshUI();
                            if (couponThemeBean.getData() != null && couponThemeBean.getData().getListObj() != null && couponThemeBean.getData().getListObj().size() > 0) {
                                List<CouponThemeBean.DataBean.ListObjBean> list = couponThemeBean.getData().getListObj();
                                for (int i = 0; i < list.size(); i++) {
                                    CouponThemeBean.DataBean.ListObjBean bean = list.get(i);
                                    if (i == 0) {
                                        tv_coupon_one.setVisibility(View.VISIBLE);
                                        tv_coupon_one.setText("满" + bean.getUseLimit() + "减" + bean.getCouponAmount());
                                    }
                                    if (i == 1) {
                                        tv_coupon_two.setVisibility(View.VISIBLE);
                                        tv_coupon_two.setText("满" + bean.getUseLimit() + "减" + bean.getCouponAmount());
                                    }
                                    if (i == 2) {
                                        tv_coupon_three.setVisibility(View.VISIBLE);
                                        tv_coupon_three.setText("满" + bean.getUseLimit() + "减" + bean.getCouponAmount());
                                    }

                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 初始化优惠券窗口
     */
    private void initCouponWindow(CouponThemeBean mCouponThemeBean) {
        if (couponListPopWindow == null) {
            couponListPopWindow = new CouponListPopWindow(getContext(), mCouponThemeBean);
        } else {
            couponListPopWindow.setData(mCouponThemeBean);
        }
        couponListPopWindow.setCouponItemClickListener(new CouponListPopWindow.CouponItemClickListener() {
            @Override
            public void couponItemClick(int position, String couponThemeId) {
                receiveCoupon(position, couponThemeId);
            }
        });
    }

    /**
     * 领取优惠券
     */
    private void receiveCoupon(int position, String couponThemeId) {
        RetrofitFactory.receiveCoupon(getValueByKey(Constants.USER_LOGIN_UT, ""), couponThemeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber(new SubscriberListener<ReceiveCouponBean>() {
                    @Override
                    public void onNext(ReceiveCouponBean receiveCouponBean) {
                        if (null != receiveCouponBean) {
                            if (receiveCouponBean.getCode().equals("0")) {
                                ToastUtils.showLongToast(getString(R.string.receive_success));
                            } else {
                                ToastUtils.showLongToast(receiveCouponBean.getMessage());
                            }
                            getCouponThemeList();
                        }
                    }
                }));
    }

    /**
     * 获取店铺基本信息
     *
     * @param merchantId 店铺ID
     */
    private void getStoreBaseInfo(long merchantId) {
        RetrofitFactory.getStoreBaseInfo(merchantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StoreBaseInfo>(new SubscriberListener<StoreBaseInfo>() {
                    @Override
                    public void onNext(StoreBaseInfo storeBaseInfo) {
                        if (null != storeBaseInfo && null != storeBaseInfo.getData()) {
                            StoreBaseInfo.DataBean dataBean = storeBaseInfo.getData();
                            storeName = dataBean.getShopName();
                            if (StringUtils.isEmpty(storeName)) {
                                tv_dispatching.setVisibility(View.GONE);
                            } else {
                                tv_dispatching.setVisibility(View.VISIBLE);
                                tv_dispatching.setText("由" + storeName + "提供配送服务");
                            }
                            logoUrl = (String) dataBean.getLogo();
                            GlideUtil.display(getContext(), logoUrl).into(iv_shop);

                            if (dataBean.getFlags() == null || dataBean.getFlags().size() == 0) {
                                tv_shop_label.setVisibility(View.GONE);
                            } else {
                                tv_shop_label.setText(dataBean.getFlags().get(0) + "");
                            }

                            storeScore = (String) (dataBean.getSkuNum() == null ? "0" : dataBean.getSkuNum());
                            attention = String.valueOf(dataBean.getFavoriteNum());
                            allGoods = String.valueOf(dataBean.getMpNums());
                            newGoods = String.valueOf(dataBean.getNewMpNums());
                            flagName = dataBean.getFlagName();
                            tv_shop_name.setText(storeName);
//                            tv_shop_score.setText("店铺评分：" + storeScore);
                            if (!StringUtils.isEmpty(dataBean.getDsrScore())) {
                                tv_shop_score.setText("店铺评分：" + dataBean.getDsrScore().substring(0, 3) + "分");
                            } else {
                                tv_shop_score.setText("店铺评分：0分");
                            }
                            tv_attention.setText(attention);
                            tv_all_goods.setText(allGoods);
                            tv_up_new.setText(newGoods);
                        }
                    }
                }));
    }

    @Override
    public void PropertyCallBack(ProductBean products, int num) {
        super.PropertyCallBack(products, num);
        if (null != propertywindow) {
            mSerialTxt.setText(propertywindow.getSerialString() + getString(R.string.piece));
        }

    }

    /**
     * 已选关联商品属性
     *
     * @param product
     */
    @Override
    public void standardDispose(ProductBean product) {
        //不展示baseinfo里面的属性
//        super.standardDispose(product);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_brand:
                if (product != null && !StringUtils.isEmpty(product.brandPageUrl) && product.brandPageUrl.contains("http")) {
                    JumpUtils.ToWebActivity(product.brandPageUrl, WebActivity.NO_TITLE, -1, "");
                } else {
                    Bundle extra = new Bundle();
                    extra.putString("brandIds", branId + "");
                    extra.putString("brandName", brandName + "");
                    extra.putString("brandImgUrl", brand_icon + "");
                    JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, extra);
                }
                break;
            case R.id.txt_origina_price://价格说明
//                ToastUtils.showShort("价格说明");
                getAdData("PRICE_INTRODUCE");
                break;
            case R.id.layout_toadresses:
                if (!isNewAddress) {
                    getAddress();
                }
                break;
            case R.id.ll_shop:
//                ToastUtils.showShort("store is clicked !");
                break;
            case R.id.tv_contact_merchant:
                ToastUtils.showShort("tv_contact_merchant is clicked !");
                break;
            case R.id.tv_enter_shop:
                Bundle extra = new Bundle();
                extra.putString("merchantId", merchantId + "");
                extra.putString("storeName", storeName);
                extra.putString("storeScore", storeScore);
                extra.putString("attention", attention);
                extra.putString("allGoods", allGoods);
                extra.putString("newGoods", newGoods);
                extra.putString("logoUrl", logoUrl);
                extra.putString("storeActivity", storeActivity);
                extra.putString("flagName", flagName);
                JumpUtils.ToActivity(JumpUtils.SHOP_HOME, extra);
                break;
            case R.id.ll_coupon:
                couponListPopWindow.showAtLocation(ll_coupon, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.store_tv_share:
                if (OdyApplication.getIsLogin()) {
                    String distributorId = OdyApplication.getValueByKey(Constants.DISTRIBUTOR_ID, "");
                    TKUtil.upload(OdyApplication.gainContext(), "click_default_share", distributorId, "", "", 2);
                    goToDistributorsShare(dataList, isDistribution);
                } else {
                    JumpUtils.ToActivity(JumpUtils.LOGIN);
                }

//                ToastUtils.showLongToast("You click store share!");
//                goToDistributorsShare("100", "20");
//                goToShare();
                break;
        }
    }

    private void goToDistributorsShare(ArrayList<String> dataList, int isDistribution) {
        if (null != shareindow) {
            shareindow.dismiss();
            shareindow = null;
        }
        if (drawPhotoBean == null) {
            drawPhotoBean = new DrawPhotoBean();
        }
        drawPhotoBean.setName(TxtProductName.getText().toString().trim());
        drawPhotoBean.setDescription(TxtProductramark.getText().toString().trim());
        drawPhotoBean.setPrice(TxtProductprice.getText().toString().trim());
        drawPhotoBean.setLogo(BitmapFactory.decodeResource(getResources(), R.drawable.draw_logo));

        new Thread(new Runnable() {
            @Override
            public void run() {
                drawPhotoBean.setPhoto(com.odysaxx.util.BitmapUtils.getNewBitmap(defaultPhoto));
            }
        }).start();
        drawPhotoBean.setQRImage(com.odysaxx.util.BitmapUtils.createQRImage(BASE_URL + "/detail.html?itemId=" + mpId, 300, 300));
        shareindow = new SharePopupWindow(getContext(), ShareType, Long.parseLong(mpId), dataList, isDistribution, drawPhotoBean);
        shareindow.setIsShareDistribution(isShareDistribution);
        shareindow.setIsDistributorProductDetail(true);
        shareindow.setRefreshUIListener(this);
        shareindow.showAtLocation(mcoySnapPageLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    //获取价格说明
    public void getAdData(final String adCode) {
        RetrofitFactory.getAd(AdPageCode.APP_COMMODITY_DETAILS_PAGE, adCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AdData>(new SubscriberListener<AdData>() {
                    @Override
                    public void onNext(AdData adData) {
                        if (null != adData && null != adData.PRICE_INTRODUCE && adData.PRICE_INTRODUCE.size() > 0) {
                            CustomDialog pricerDialog = new CustomDialog(getContext(), adData.PRICE_INTRODUCE.get(0).name, 2);
                            pricerDialog.show();
                        } else {
                            JumpUtils.ToWebActivity(JumpUtils.H5, OdyApplication.H5URL + "/priceCaption.html", WebActivity.CONTENT_TITLE, -1, null);
                        }
                    }
                }));
    }

    /**
     * 选择配送地址
     */
    public void getAddress() {
        boolean isLogin = getValueByKey(com.ody.p2p.Constants.LOGIN_STATE, false);//登录状态
        if (isLogin) {
            mPressent.getDeliveryAddress();
        } else {
            JumpUtils.ToActivity(JumpUtils.LOGIN);
        }
    }

    ReminderDialog reminderDialog;

    //给界面注入值
    @Override
    public void setViewValues(ProductBean product) {
        super.setViewValues(product);
        if (null != product) {
            isDistribution = product.isDistribution;
            Log.e("xiaobin", "isDistribution: " + isDistribution);
            if (isDistribution != 0) {
                img_share.setVisibility(View.GONE);
                store_tv_share.setVisibility(View.VISIBLE);
            } else {
                img_share.setVisibility(View.VISIBLE);
                store_tv_share.setVisibility(View.GONE);
            }
            merchantId = product.merchantId;
            if (merchantId <= 0) {
                ll_store.setVisibility(View.GONE);
            }
            getStoreBaseInfo(merchantId);

            getMerchantPromotionListCount(merchantId);
//            if (product.merchantType == 10){
//                UiUtils.getStringSpan(TxtProductName, getContext(), R.drawable.icon_self_sale);
//            }
            if (!getChooseSerial) {
                SerialProducts();//第一次进来拿一次关联商品
            }
            if (null != propertywindow) {
                mSerialTxt.setText(propertywindow.getSerialString() + getString(R.string.piece));
            }
            if (0 < product.promotionPrice) { //商品价格
                //如果有两种价格的话 会显示特价标签
                txt_origina_price.setVisibility(View.VISIBLE);
                txt_origina_price.setText(UiUtils.getMoneyDouble(product.price));// 原价
                txt_origina_price.setOnClickListener(this);
//            } else if (product.marketPrice>0){
//                txt_origina_price.setVisibility(View.VISIBLE);
//                txt_origina_price.setText(UiUtils.getMoneyDouble(product.marketPrice));// 市场价
            } else {
                txt_origina_price.setVisibility(View.GONE);
            }
            if (product.stockNum > 0) {
                txt_sto.setText(getString(R.string.stock) + ":" + product.stockNum);
                img_stock.setVisibility(View.GONE);
            } else {
                txt_sto.setText(R.string.sale_null);
                img_stock.setVisibility(View.VISIBLE);
            }
            isShareDistribution = product.isDistribution;
            if (product.isDistribution == 1) {
                img_share.setImageResource(R.drawable.ic_distribution);//分享图标的更换
            }
            /**
             * 销量
             */
            txt_sales.setText(getContext().getResources().getString(R.string.already_sell) + ":" + product.mpSalesVolume);
            /**
             * 商品保障
             */
            if (null != product.securityVOList && product.securityVOList.size() > 0) {
                ll_fullcut.setVisibility(View.VISIBLE);
            }
            if (!StringUtils.isEmpty(product.warehouseName)) {
                tv_warehouseName.setVisibility(View.VISIBLE);
                tv_warehouseName.setText(product.warehouseName);
            } else {
                tv_warehouseName.setVisibility(View.INVISIBLE);
            }
            if (!StringUtils.isEmpty(getValueByKey(Constants.AREA_CODE_ADDRESS, ""))) {
                tv_address.setText(getValueByKey(Constants.AREA_CODE_ADDRESS, ""));
            }
            //4角的角图
            if (null != product.getScripts() && null != product.getScripts().getScriptIconUrl() && BitmapUtil.checkCanSend(product.getScripts().getScriptIconUrl())) {
                GlideUtil.display(getContext(), product.getScripts().getScriptIconUrl()).into(img_scripts);
                img_scripts.setVisibility(View.VISIBLE);
                //显示类型：0，主图左上；1，主图右上；2，主图右下；3，主图左下
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) img_scripts.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                if (product.getScripts().getDisplayType() == 0) {
                    params.gravity = Gravity.TOP | Gravity.LEFT;
                } else if (product.getScripts().getDisplayType() == 1) {
                    params.gravity = Gravity.TOP | Gravity.RIGHT;
                } else if (product.getScripts().getDisplayType() == 2) {
                    params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                    params.setMargins(0, 0, 0, PxUtils.dipTopx(40));
                } else {
                    params.gravity = Gravity.BOTTOM | Gravity.LEFT;
                }
                img_scripts.setLayoutParams(params);
            } else {
                img_scripts.setVisibility(View.GONE);
            }
            if (product.managementState != 0) {
                if (product.isAreaSale == 0) {
                    if (null == reminderDialog) {
                        reminderDialog = new ReminderDialog(getContext(), "温馨提示", "该商品在该地区暂不支持销售\n非常抱歉");
                    }
                    reminderDialog.show();
                    IprodutDetailActivityCallback.layout_addshoppingsetEnabled(false, 100);
                }
            }
        }
        ll_price.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(product.isSeckill + "") && !TextUtils.isEmpty(product.isForcast + "")) {
            if (product.isSeckill == 1) {
                tv_origina_price.setText("平台价 " + UiUtils.getMoneyDouble(product.price));
                ll_price.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getAllData(ProductInfoBean data, int type) {
        super.getAllData(data, type);
        if (!StringUtils.isEmpty(data.getData().get(0).merchantType + "") && (data.getData().get(0).merchantType + "").equals("10")) {
            iv_ziying.setVisibility(View.VISIBLE);
        } else {
            iv_ziying.setVisibility(View.GONE);
        }
        txt_drop_price.setVisibility(View.GONE);
        TaklingDataEventMessage msg = new TaklingDataEventMessage();
        msg.setAction(TaklingDataEventMessage.ONVIEWITEM);
        Map<String, String> map = new HashMap<>();
        map.put("itemId", product.mpId + "");
        map.put("category", product.categoryName);
        map.put("name", product.name);
        map.put("unitPrice", product.price + "");

        if (product.promotionPrice > 0) {
            map.put("nowPrice", product.promotionPrice + "");
        } else {
            map.put("nowPrice", product.preferentialPrice + "");
        }

        //原价
        map.put("orginalPrice", product.preferentialPrice + "");

        msg.setExtra(map);
        EventBus.getDefault().post(msg);
        if (!StringUtils.isEmpty(brand_icon)) {
            GlideUtil.display(getContext(), brand_icon + "").into(img_brandimg);
        } else {
//            img_brandimg.setImageResource(R.drawable.);//默认图标
        }
        getPreCommission(mpId, product.price + "");
    }

    @Override
    public void addShopCarCode() {//加车
        super.addShopCarCode();
        TaklingDataEventMessage msg = new TaklingDataEventMessage();
        msg.setAction(TaklingDataEventMessage.ONADDITEMTOSHOPPINGCART);
        Map<String, String> map = new HashMap<>();
        map.put("itemId", product.mpId + "");
        map.put("category", product.categoryName);
        map.put("name", product.name);
        map.put("unitPrice", product.price + "");
        // map.put("unitPrice", product.price + "");

        msg.setExtra(map);
        EventBus.getDefault().post(msg);
    }

    @Override
    public void lookToGiftPromotion(PromotionBean.Data.PromotionInfo.Promotions data) {
//          特价：1 满折：2 满减：3  满赠：4 秒杀：7 换购：11
//          1006是满量赠，1007是买一赠一，
//          1018是满额换购，1019是满量换购
//          1005/1006是满赠  1018/1019是换购
//          满减满赠時点击去商品聚合页
//        if(data.getPromotionType()==2||data.getPromotionType()==3||data.getPromotionType()==4||data.getPromotionType()==11||data.getPromotionType()==1005||data.getPromotionType()==1006||data.getPromotionType()==1018||data.getPromotionType()==1019){
        if ((data.contentType == 2001 || data.contentType == 2002)) {
            if (group != null) {
                JumpUtils.toActivity(group);
            }
        } else if (data.contentType == 3001) {
            if (cut != null) {
                JumpUtils.toActivity(cut);
            }
        } else {
            //TODOa:meiyizhi 跳转至商品聚合页，根据ProdutActionAdapter的contentType类型，上面需要穷举聚合页的其他类型,目前有拼团和砍价!
            Bundle bd = new Bundle();
            bd.putString(Constants.PRO_ID, data.promotionId + "");
            JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, bd);
        }
//        }else{
//            super.lookToGiftPromotion(data);
//        }
    }

    /**
     * 猜你喜欢
     *
     * @param bean
     */
    @Override
    public void initGuessYouLike(Recommedbean bean) {
        super.initGuessYouLike(bean);
        banner_grid.linear.setBackgroundResource(R.drawable.viewpager_item_background);
        banner_grid.setBackgroundResource(R.color.background_color);
        banner_grid.linear.setPadding(PxUtils.dipTopx(15), 0, PxUtils.dipTopx(15), 0);
        banner_grid.setIndicatorPosition(PxUtils.dipTopx(28));
        banner_grid.linear.setGravity(Gravity.CENTER_VERTICAL);
    }

    /**
     * 猜你喜欢中的加车事件
     *
     * @return
     */
    @Override
    public RecommendAdapter.RecommendCallBack getRecommendCallBack() {
        return new RecommendAdapter.RecommendCallBack() {
            @Override
            public void addCart() {

            }

            @Override
            public void addCart(Recommedbean.DataList dataList) {
                TaklingDataEventMessage msg = new TaklingDataEventMessage();
                msg.setAction(TaklingDataEventMessage.ONADDITEMTOSHOPPINGCART);
                Map<String, String> map = new HashMap<>();
                map.put("itemId", dataList.getMpId() + "");
                map.put("category", dataList.getCategoryId());
                map.put("name", dataList.getMpName());
                map.put("unitPrice", dataList.getSalePrice() + "");
                map.put("amount", "1");
                msg.setExtra(map);
                EventBus.getDefault().post(msg);
            }
        };
    }

    @Override
    public void deliveryAddress(AddressBean response) {
        if (null != response && null != response.getData()) {
            window = new LcoationAddressPopWindow(getContext(), response);
        } else {
            window = new LcoationAddressPopWindow(getContext(), null);
        }
//        if (null != addressData) {
//            window.setPostion(addressData.getId());
//        } else {
//            window.setPostion(null);
//        }
        window.setmCallBack(new LcoationAddressPopWindow.chooseCallBack() {
            @Override
            public void chooseAddress(AddressBean.Address address) {
                if (null != address) {
                    String addressStr = address.getProvinceName() + "  " + address.getCityName() + "  " + address.getRegionName() + "  " + address.getDetailAddress();
                    tv_address.setText(addressStr);
//                    ppreesent.getDeliveryTime(product.mpId + "", address);
                    OdyApplication.putString(Constants.AREA_CODE_ADDRESS, addressStr);
                    OdyApplication.putString(Constants.AREA_CODE, address.getRegionCode());
                    OdyApplication.putString(Constants.AREA_NAME, address.getRegionName());
                    OdyApplication.putString(Constants.PROVINCE, address.getProvinceName());
                    OdyApplication.putString(Constants.CITY, address.getCityName());

                    OdyApplication.putString(Constants.ADDRESS_ID, address.getId());

//                    ppreesent.selectProduct(product.mpId + "", 0);
                    downloadBaseInfo(product.mpId + "");
                } else {
//                    tv_address.setText(getString(com.ody.p2p.produtdetail.R.string.please_select));
                }
            }
        });
        window.showAtLocation(getView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void getDelivetyTime(String message) {//获取配送到达时间
        super.getDelivetyTime(message);
    }

    @Override
    public void setCurrentPrice(StockPriceBean bean) {
        super.setCurrentPrice(bean);
        if (0 < product.promotionPrice) { //商品价格
            //如果有两种价格的话 会显示特价标签
            txt_origina_price.setVisibility(View.VISIBLE);
            txt_origina_price.setText(UiUtils.getMoneyDouble(product.price));// 原价
            txt_origina_price.setOnClickListener(this);
//            } else if (product.marketPrice>0){
//                txt_origina_price.setVisibility(View.VISIBLE);
//                txt_origina_price.setText(UiUtils.getMoneyDouble(product.marketPrice));// 市场价
        } else {
            txt_origina_price.setVisibility(View.GONE);
        }
    }
}
