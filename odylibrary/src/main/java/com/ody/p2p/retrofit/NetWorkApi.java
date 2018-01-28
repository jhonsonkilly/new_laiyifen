package com.ody.p2p.retrofit;

import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.retrofit.adviertisement.AdBean;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.retrofit.category.CategoryBean;
import com.ody.p2p.retrofit.city.AddressBean;
import com.ody.p2p.retrofit.city.AreaBean;
import com.ody.p2p.retrofit.city.CityBean;
import com.ody.p2p.retrofit.city.LocationBean;
import com.ody.p2p.retrofit.city.MultiCity;
import com.ody.p2p.retrofit.coupon.CouponThemeBean;
import com.ody.p2p.retrofit.file.UploadFile;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.HomeBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.retrofit.home.ModuleDataCategoryBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.AttentionBean;
import com.ody.p2p.retrofit.store.DoAttentionBean;
import com.ody.p2p.retrofit.store.GoodBean;
import com.ody.p2p.retrofit.store.MerIndexPageBean;
import com.ody.p2p.retrofit.store.PreCommissionBean;
import com.ody.p2p.retrofit.store.PreCommissionsBean;
import com.ody.p2p.retrofit.store.ReceiveCouponBean;
import com.ody.p2p.retrofit.store.ShopBean;
import com.ody.p2p.retrofit.store.StoreActivityBean;
import com.ody.p2p.retrofit.store.StoreActivityCountBean;
import com.ody.p2p.retrofit.store.StoreBaseInfo;
import com.ody.p2p.retrofit.user.Alias;
import com.ody.p2p.retrofit.user.MsgSummary;
import com.ody.p2p.retrofit.user.PointBean;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/30.
 */

public interface NetWorkApi {
    @GET("/api/category/list")
    Observable<CategoryBean> getCategory(@Query("parentId") long parentId, @Query("level") int level);

    @GET("/api/category/list")
    Call<CategoryBean> getCacheCategory(@Query("parentId") long parentId, @Query("level") int level);

    @GET("/api/location/areaGroupList")
    Observable<HttpResult<List<CityBean>>> getAreaList();

    @GET("/api/location/areaList")
    Observable<HttpResult<List<MultiCity>>> getSearchAreaList(@Query("keyword") String keyword);

    @GET("/api/dolphin/list?&platform=3&platformId=0")
    Observable<AdBean> getAd(@Query("pageCode") String pageeCode, @Query("adCode") String adCode, @Query("areaCode") String areaCode);

    @GET("/api/dolphin/list?&platform=3&platformId=0")
    Call<AdBean> getCacheAd(@Query("pageCode") String pageeCode, @Query("adCode") String adCode, @Query("areaCode") String areaCode);

    @GET("/api/dolphin/list?&platform=3&platformId=0")
    Observable<HttpResult<AdData>> getAd(@QueryMap Map<String, String> params);

    @GET("/api/dolphin/list?&platform=3&platformId=0")
    Call<AdBean> getCacheAd(@QueryMap Map<String, String> params);

    @Multipart
    @POST("/api/fileUpload/putObjectWithForm.do")
    Observable<HttpResult<UploadFile>> uploadFile(@Part("file\"; filename=\"image.jpg") RequestBody file);

    @GET("/cms/page/getAppHomePage")
    Observable<HomeBean> getHomePage();

    @GET("/cms/page/getAppHomePage")
    Call<HomeBean> getCacheHomePage();

    @GET("/cms/view/h5/headlinesList?platformId=0&categoryType=2&currentPage=1&itemsPerPage=5&code=headlines")
    Observable<HeadLinesBean> getHeadlines();

    @GET("/cms/view/h5/headlinesList?platformId=0&categoryType=2&currentPage=1&itemsPerPage=8&code=headlines")
    Call<HeadLinesBean> getCacheHeadlines();

    @GET("/cms/page/module/getModuleData?pageSize=10&pageNo=1&categoryId")
    Observable<HttpResult<ModuleDataBean>> getRank(@Query("moduleId") long moduleId);

    @GET("/cms/page/module/getModuleDataCategory")
    Observable<ModuleDataCategoryBean> getCategory(@Query("moduleId") long moduleId);

    @GET("/cms/page/module/getModuleData?pageSize=20")
    Observable<HttpResult<ModuleDataBean>> getCategoryProduct(@Query("moduleId") long moduleId, @Query("categoryId") long categoryId, @Query("pageNo") int pageNo);

    @GET("/cms/page/module/getModuleData")
    Observable<HttpResult<ModuleDataBean>> getSpecCategoryProduct(@Query("moduleId") long moduleId, @Query("categoryId") long categoryId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @GET("/api/location/areaGroupList")
    Observable<LocationBean> getGroupProvince(@Query("areaLevel") String areaLevel);

    @GET("/api/location/list/{code}")
    Observable<AreaBean> getAreaList(@Path("code") String code);

    @POST("/ouser-web/address/getAllAddressForm.do")
    Observable<AddressBean> getAllAddress(@QueryMap() Map<String, String> params);

    @GET("/api/location/getArea?countryName=中国")
    Observable<AreacodeBean> getArea(@Query("provinceName") String provinceName, @Query("cityName") String cityName, @Query("areaName") String areaName);

    @GET("/api/cart/addItem")
    Observable<HttpResult<AppHomePageBean>> addCart(@Query("mpId") String mpId, @Query("num") String num, @Query("ut") String ut);

    @POST("/api/social/vl/device/bundleAlias")
    Observable<Alias> bundleAlias(@QueryMap() Map<String, String> params);

    @GET("/api/social/vl/message/getMsgSummary")
    Observable<MsgSummary> getMsgSummary(@Query("ut") String ut);

    @POST("/api/social/write/share/getSharePoint")
    Observable<PointBean> getSharePoint(@QueryMap() Map<String, String> params);

    @GET("/api/social/write/pandaPlay/activateApp")
    Observable<BaseRequestBean> activateApp(@Query("idfa") String idfa);

    @GET("/api/realTime/getPriceStockList")
    Observable<StockPriceBean> getStockPrice(@Query("mpIds") String mpIds);

    @POST("/api/promotion/coupon/share?needUpdateStatus=1")
        //type 优惠券类型 1欧电云 2来伊份
        //needUpdateStatus	Integer	否	是否修改优惠券状态。0否，1是。默认1
    Observable<BaseRequestBean> couponShare(@Query("ut") String ut, @Query("couponCode") String couponCode, @Query("couponId") String couponId, @Query("type") String type);

    @POST("/api/my/wallet/share?needUpdateStatus=1")
    Observable<BaseRequestBean> eCardShare(@Query("ut") String ut, @Query("cardCode") String cardCode, @Query("platformId") int platformId);

    //新接口：店铺基本信息
    @GET("/back-merchant-web/shop/baseInfo.do")
    Observable<StoreBaseInfo> getStoreBaseInfo(@Query("merchantId") long merchantId);

    //获取优惠券列表
    @GET("/api/promotion/coupon/couponThemeList")
    Observable<CouponThemeBean> getCouponThemeList(@Query("ut") String ut);

    //获取门店是否有店铺首页
    @GET("/cms/page/getMerIndexPage")
    Observable<MerIndexPageBean> getMerIndexPage(@Query("merchantId") long merchantId);

    //获取全部商品
    @GET("/api/search/searchList")
    Observable<GoodBean> getAllGoods(@Query("merchantId") long merchantId, @Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize);

    //获取关注的状态
    @POST("/ouser-center/api/favorite/check.do")
    Observable<AttentionBean> getAttentionStatus(@Query("ut") String ut, @Query("type") Integer type, @Query("entityId") String entityId);

    //添加关注
//    @POST("/api/my/favorite/add")
    @POST("/ouser-center/api/favorite/add.do")
    Observable<DoAttentionBean> doAttention(@Query("ut") String ut, @Query("type") Integer type, @Query("entityId") String entityId);

    //取消关注
//    @POST("/api/my/favorite/clear")
    @POST("/ouser-center/api/favorite/delete.do")
    Observable<DoAttentionBean> cancelAttention(@Query("ut") String ut, @Query("type") Integer type, @Query("entityId") String entityId);

    //领取优惠券
    @POST("/api/promotion/coupon/receiveCoupon")
    Observable<ReceiveCouponBean> receiveCoupon(@Query("ut") String ut, @Query("couponThemeId") String couponThemeId);

    //促销，店铺活动
    @GET("/api/promotion/merchantPromotionList")
    Observable<StoreActivityBean> getMerchantPromotionList(@Query("merchantId") String merchantId, @Query("promotionTypeList") String promotionTypeList,
                                                           @Query("platformId") Integer platformId, @Query("currentPage") Integer currentPage, @Query("itemsPerPage") Integer itemsPerPage);

    //促销，店铺活动总数
    @GET("/api/promotion/merchantPromotionList")
    Observable<StoreActivityCountBean> getMerchantPromotionListCount(@Query("merchantId") String merchantId, @Query("promotionTypeList") String promotionTypeList,
                                                                     @Query("platformId") Integer platformId, @Query("isNeedTotal") boolean isNeedTotal, @Query("isNeedList") boolean isNeedList);

    //获取商品列表
    @GET("/api/search/searchList")
    Observable<ShopBean> getShopList(@Query("merchantId") String merchantId, @Query("ut") String ut, @Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize);

    //获取热门搜索关键词
    @GET("/back-merchant-web/api/merchant/trendingKeywords.do")
    Observable<ShopBean> getTrendingKeywords(@Query("merchantId") String merchantId);

    //商品预计佣金接口
//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/agent-fx-web/api/preCommission.do")
//    Observable<PreCommissionBean> getPreCommission(@Query("mpId") String mpId, @Query("salaPrice") String salaPrice);
    Observable<PreCommissionBean> getPreCommission(@Query("requestJson") String requestJson);

    //商品批量预计佣金接口
    @POST("/agent-fx-web/api/preCommissions.do")
    Observable<PreCommissionsBean> getPreCommissions(@Query("mpId") String mpId, @Query("salaPrice") String salaPrice);
}
