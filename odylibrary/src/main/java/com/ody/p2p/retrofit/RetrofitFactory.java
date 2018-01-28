package com.ody.p2p.retrofit;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
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
import com.ody.p2p.retrofit.home.HomeBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.retrofit.home.ModuleDataCategoryBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.store.Commission;
import com.ody.p2p.retrofit.store.MerIndexPageBean;
import com.ody.p2p.retrofit.store.StoreBaseInfo;
import com.ody.p2p.retrofit.user.Alias;
import com.ody.p2p.retrofit.user.MsgSummary;
import com.ody.p2p.retrofit.user.PointBean;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/30.
 */

public class RetrofitFactory {
    protected static final Object monitor = new Object();
    static NetWorkApi sNetWorkApi = null;


    public static NetWorkApi getNetWorkApi() {
        synchronized (monitor) {
            if (sNetWorkApi == null) {
                sNetWorkApi = new RetrofitHelper().getCategoryService();
            }
            return sNetWorkApi;
        }
    }

    /**
     * 获取分类页数据
     *
     * @param parentId
     * @param level
     * @return
     */
    public static Observable<CategoryBean> getCategory(long parentId, int level) {
        return getNetWorkApi().getCategory(parentId, level);
    }

    /**
     * 获取分类页数据
     *
     * @param parentId
     * @param level
     * @return
     */
    public static Observable<CategoryBean> getCacheCategory(final long parentId, final int level) {
        return Observable.create(new Observable.OnSubscribe<CategoryBean>() {
            @Override
            public void call(Subscriber<? super CategoryBean> subscriber) {
                Call<CategoryBean> callBean = getNetWorkApi().getCacheCategory(parentId, level);
                CategoryBean bean = null;
                try {
                    Response<CategoryBean> rBean = callBean.execute();
                    bean = rBean.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io());
    }

    public static Observable<List<CityBean>> getAreaList() {
        return getNetWorkApi().getAreaList()
                .map(new Func1<HttpResult<List<CityBean>>, List<CityBean>>() {
                    @Override
                    public List<CityBean> call(HttpResult<List<CityBean>> result) {
                        if (result != null && result.getCode().equals("0") && result.getData() != null) {
                            return result.getData();
                        } else {
                            return new ArrayList<CityBean>();
                        }
                    }
                });
    }

    public static Observable<List<MultiCity>> getSearchAreaList(String keyword) {
        return getNetWorkApi().getSearchAreaList(keyword)
                .map(new Func1<HttpResult<List<MultiCity>>, List<MultiCity>>() {
                    @Override
                    public List<MultiCity> call(HttpResult<List<MultiCity>> result) {
                        if (result != null && result.getCode().equals("0") && result.getData() != null) {
                            return result.getData();
                        } else {
                            return new ArrayList<MultiCity>();
                        }
                    }
                });
    }

    public static Observable<AdBean> getCacheAd(final String pageCode, final String adCode) {
        return Observable.create(new Observable.OnSubscribe<AdBean>() {
            @Override
            public void call(Subscriber<? super AdBean> subscriber) {
                Call<AdBean> callBean = getNetWorkApi().getCacheAd(pageCode, adCode, OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
                AdBean bean = null;
                try {
                    Response<AdBean> rBean = callBean.execute();
                    bean = rBean.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io());
    }

    public static Observable<AdData> getAd(String pageCode, String adCode) {
        return getNetWorkApi()
                .getAd(pageCode, adCode, OdyApplication.getValueByKey(Constants.AREA_CODE, ""))
                .map(new Func1<AdBean, AdData>() {
                    @Override
                    public AdData call(AdBean adDataHttpResult) {
                        if (adDataHttpResult != null && adDataHttpResult.code.equals("0") && adDataHttpResult.data != null) {
                            return adDataHttpResult.data;
                        } else {
                            return new AdData();
                        }
                    }
                });
    }


    public static Observable<AdData> getAd(Map<String, String> params) {
        if (null == params) {
            params = new HashMap<String, String>();
        }
        params.put("areaCode", OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
        return getNetWorkApi().getAd(params)
                .map(new Func1<HttpResult<AdData>, AdData>() {
                    @Override
                    public AdData call(HttpResult<AdData> adDataHttpResult) {
                        if (adDataHttpResult != null && adDataHttpResult.getCode().equals("0") && adDataHttpResult.getData() != null) {
                            return adDataHttpResult.getData();
                        } else {
                            return new AdData();
                        }
                    }
                });
    }

    public static Observable<AdBean> getCacheAd(Map<String, String> params) {
        if (null == params) {
            params = new HashMap<String, String>();
        }
        params.put("areaCode", OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
        final Map<String, String> finalParams = params;
        return Observable.create(new Observable.OnSubscribe<AdBean>() {
            @Override
            public void call(Subscriber<? super AdBean> subscriber) {
                Call<AdBean> callBean = getNetWorkApi().getCacheAd(finalParams);
                AdBean bean = null;
                try {
                    Response<AdBean> rBean = callBean.execute();
                    bean = rBean.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io());
    }

    public static Observable<String> uploadFile(String filePath) {
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), new File(filePath));
        return getNetWorkApi().uploadFile(body)
                .map(new Func1<HttpResult<UploadFile>, String>() {
                    @Override
                    public String call(HttpResult<UploadFile> uploadFileHttpResult) {
                        if (uploadFileHttpResult != null && uploadFileHttpResult.getData() != null && !StringUtils.isEmpty(uploadFileHttpResult.getData().filePath)) {
                            return uploadFileHttpResult.getData().filePath;
                        } else {
                            return "";
                        }
                    }
                });
    }


    public static Observable<HomeBean> getHomePage() {
        return getNetWorkApi().getHomePage();
    }

    public static Observable<HomeBean> getCacheHomePage() {
        return Observable.create(new Observable.OnSubscribe<HomeBean>() {
            @Override
            public void call(Subscriber<? super HomeBean> subscriber) {
                Call<HomeBean> callBean = getNetWorkApi().getCacheHomePage();
                HomeBean bean = null;
                try {
                    Response<HomeBean> rBean = callBean.execute();
                    bean = rBean.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io());
    }

    public static Observable<HeadLinesBean> getHeadLines() {
        return getNetWorkApi().getHeadlines().map(new Func1<HeadLinesBean, HeadLinesBean>() {
            @Override
            public HeadLinesBean call(HeadLinesBean headLinesBean) {
                if (headLinesBean != null && headLinesBean.code.equals("0") && headLinesBean != null) {
                    return headLinesBean;
                } else {
                    return new HeadLinesBean();
                }
            }
        });
    }

    public static Observable<HeadLinesBean> getCacheHeadLines() {
        return Observable.create(new Observable.OnSubscribe<HeadLinesBean>() {
            @Override
            public void call(Subscriber<? super HeadLinesBean> subscriber) {
                Call<HeadLinesBean> callBean = getNetWorkApi().getCacheHeadlines();
                HeadLinesBean bean = null;
                try {
                    Response<HeadLinesBean> rBean = callBean.execute();
                    bean = rBean.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io());
    }

    public static Observable<ModuleDataBean> getRank(long moduleId) {
        return getNetWorkApi().getRank(moduleId).map(new Func1<HttpResult<ModuleDataBean>, ModuleDataBean>() {
            @Override
            public ModuleDataBean call(HttpResult<ModuleDataBean> Result) {
                if (Result != null && Result.getCode().equals("0") && Result.getData() != null) {
                    return Result.getData();
                } else {
                    return new ModuleDataBean();
                }
            }
        });
    }

    public static Observable<List<ModuleDataCategoryBean.CategoryBean>> getCategory(long moduleId) {
        return getNetWorkApi().getCategory(moduleId).map(new Func1<ModuleDataCategoryBean, List<ModuleDataCategoryBean.CategoryBean>>() {
            @Override
            public List<ModuleDataCategoryBean.CategoryBean> call(ModuleDataCategoryBean result) {
                if (result != null && result.code.equals("0") && result.data != null) {
                    return result.data;
                } else {
                    return new ArrayList<ModuleDataCategoryBean.CategoryBean>();
                }
            }
        });
    }

    public static Observable<ModuleDataBean> getCategoryProduct(long moduleId, long categoryId, int pageNo) {
        return getNetWorkApi().getCategoryProduct(moduleId, categoryId, pageNo).map(new Func1<HttpResult<ModuleDataBean>, ModuleDataBean>() {
            @Override
            public ModuleDataBean call(HttpResult<ModuleDataBean> Result) {
                if (Result != null && Result.getCode().equals("0") && Result.getData() != null) {
                    return Result.getData();
                } else {
                    return new ModuleDataBean();
                }
            }
        });
    }

    public static Observable<ModuleDataBean> getSpecCategoryProduct(long moduleId, long categoryId, int pageNo,int pageSize) {
        return getNetWorkApi().getSpecCategoryProduct(moduleId, categoryId, pageNo,pageSize).map(new Func1<HttpResult<ModuleDataBean>, ModuleDataBean>() {
            @Override
            public ModuleDataBean call(HttpResult<ModuleDataBean> Result) {
                if (Result != null && Result.getCode().equals("0") && Result.getData() != null) {
                    return Result.getData();
                } else {
                    return new ModuleDataBean();
                }
            }
        });
    }

    public static Observable<LocationBean> getGroupProvince(String areaLevel) {
        return getNetWorkApi().getGroupProvince(areaLevel);
    }

    public static Observable<AreaBean> getAreaList(String code) {
        return getNetWorkApi().getAreaList(code);
    }

    public static Observable<AddressBean> getAddress() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
        params.put("platformId", "0");
        return getNetWorkApi().getAllAddress(params);
    }

    /**
     * 根据定位出来的四级地址名称检索系统中对应的地区信息
     *
     * @param provinceName
     * @param cityName
     * @param areaName
     * @return
     */
    public static Observable<AreacodeBean> getArea(String provinceName, String cityName, String areaName) {
        return getNetWorkApi().getArea(provinceName, cityName, areaName);
    }

    public static Observable<Alias> bundleAlias() {
        Map<String, String> params = new HashMap<>();
        params.put("deviceNo", OdyApplication.getGUID());
        params.put("ut", OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
        params.put("appType", "1");
        return getNetWorkApi().bundleAlias(params);
    }

    public static Observable<MsgSummary> getMsgSummary() {
        return getNetWorkApi().getMsgSummary(OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
    }

    public static Observable<PointBean> getSharePoint(String refId, String refType) {

        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
        params.put("refId", refId);
        params.put("refType", refType);
        return getNetWorkApi().getSharePoint(params);
    }

    public static Observable<PointBean> getSharePoint(String refId, String refType, String activityType) {

        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
        params.put("refId", refId);
        params.put("refType", refType);
        params.put("activityType", activityType);
        return getNetWorkApi().getSharePoint(params);
    }

    public static Observable<PointBean> getSharePoint(String refType) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
        params.put("refType", refType);
        return getNetWorkApi().getSharePoint(params);
    }

    public static Observable<BaseRequestBean> couponShare(String couponCode, String couponId, String type) {
        return getNetWorkApi().couponShare(OdyApplication.getString(Constants.USER_LOGIN_UT, ""), couponCode, couponId, type);
    }

    public static Observable<BaseRequestBean> activateApp() {
        return getNetWorkApi().activateApp(OdyApplication.getGUID());
    }

    public static Observable<StockPriceBean> getStockPrice(String mpIds) {
        return getNetWorkApi().getStockPrice(mpIds);
    }

    public static Observable<BaseRequestBean> eCardShare(String cardCode) {
        return getNetWorkApi().eCardShare(OdyApplication.getString(Constants.USER_LOGIN_UT, ""), cardCode, 0);
    }

    /**
     * 获取店铺基本信息
     *
     * @param merchantId 店铺Id
     * @return
     */
    public static Observable<StoreBaseInfo> getStoreBaseInfo(long merchantId) {
        return getNetWorkApi().getStoreBaseInfo(merchantId);
    }

    /**
     * 获取优惠券列表
     *
     * @return
     */
    public static Observable<CouponThemeBean> getCouponThemeList(String ut) {
        return getNetWorkApi().getCouponThemeList(ut);
    }

    /**
     * 获取门店是否有店铺首页
     *
     * @param merchantId 店铺Id
     * @return
     */
    public static Observable<MerIndexPageBean> getMerIndexPage(long merchantId) {
        return getNetWorkApi().getMerIndexPage(merchantId);
    }

    /**
     * 获取所有商品
     *
     * @param merchantId 店铺Id
     * @param pageNo     页码
     * @param pageSize   每页的数量
     * @return
     */
    public static Observable getAllGoods(long merchantId, Integer pageNo, Integer pageSize) {
        return getNetWorkApi().getAllGoods(merchantId, pageNo, pageSize);
    }

    /**
     * 获取关注状态
     *
     * @param ut       用户token
     * @param type     3：店铺
     * @param entityId 店铺Id
     * @return
     */
    public static Observable getAttentionStatus(String ut, Integer type, String entityId) {
        return getNetWorkApi().getAttentionStatus(ut, type, entityId);
    }

    /**
     * 关注
     *
     * @param ut       用户token
     * @param type     类型
     * @param entityId 店铺Id
     * @return
     */
    public static Observable doAttention(String ut, Integer type, String entityId) {
        return getNetWorkApi().doAttention(ut, type, entityId);
    }

    /**
     * 取消关注
     *
     * @param ut       用户token
     * @param type     类型
     * @param entityId 店铺Id
     * @return
     */
    public static Observable cancelAttention(String ut, Integer type, String entityId) {
        return getNetWorkApi().cancelAttention(ut, type, entityId);
    }

    /**
     * 领取优惠券
     *
     * @param ut            用户token
     * @param couponThemeId 券活动Id
     * @return
     */
    public static Observable receiveCoupon(String ut, String couponThemeId) {
        return getNetWorkApi().receiveCoupon(ut, couponThemeId);
    }

    /**
     * 获取店铺活动
     *
     * @param merchantId   店铺Id
     * @param currentPage  当前页码
     * @param itemsPerPage 每页条数
     * @return
     */
    public static Observable getMerchantPromotionList(String merchantId, String promotionTypeList, Integer platformId, Integer currentPage, Integer itemsPerPage) {
        return getNetWorkApi().getMerchantPromotionList(merchantId, promotionTypeList, platformId, currentPage, itemsPerPage);
    }

    /**
     * 获取店铺活动
     *
     * @param merchantId 店铺Id
     * @return
     */
    public static Observable getMerchantPromotionListCount(String merchantId, String promotionTypeList, Integer platformId, boolean isNeedTotal, boolean isNeedList) {
        return getNetWorkApi().getMerchantPromotionListCount(merchantId, promotionTypeList, platformId, isNeedTotal, isNeedList);
    }

    /**
     * 获取商品列表
     *
     * @param merchantId 店铺Id
     * @param ut         用户token
     * @param pageNo     页码
     * @param pageSize   每页条数
     * @return
     */
    public static Observable getShopList(String merchantId, String ut, Integer pageNo, Integer pageSize) {
        return getNetWorkApi().getShopList(merchantId, ut, pageNo, pageSize);
    }

    /**
     * 获取热门搜索关键词
     *
     * @param merchantId 店铺Id
     * @return
     */
    public static Observable getTrendingKeywords(String merchantId) {
        return getNetWorkApi().getTrendingKeywords(merchantId);
    }

    /**
     * 商品预计佣金接口
     *
     * @param mpId      商品Id
     * @param salaPrice 商品售价
     * @return
     */
    public static Observable getPreCommission(String mpId, String salaPrice) {
//        return getNetWorkApi().getPreCommission(mpId, salaPrice);
        Commission commission = new Commission(mpId, salaPrice);
        return getNetWorkApi().getPreCommission(new Gson().toJson(commission));
    }

    /**
     * 商品预计佣金接口
     *
     * @param mpId      商品Id
     * @param salaPrice 商品售价
     * @return
     */
    public static Observable getPreCommissions(String mpId, String salaPrice) {
        return getNetWorkApi().getPreCommissions(mpId, salaPrice);
    }

}
