package com.ody.ds.lyf_home;

import android.util.Log;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.conversation.YWConversation;
import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.adviertisement.AdBean;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.retrofit.adviertisement.AdPageCode;
import com.ody.p2p.retrofit.cache.CacheManager;
import com.ody.p2p.retrofit.cache.NetworkCache;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.HomeBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.retrofit.home.ModuleDataCategoryBean;
import com.ody.p2p.retrofit.home.QiangGouBean;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.retrofit.user.MsgSummary;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lxs on 2016/12/14.
 */
public class HomeCatergyImpl implements HomeCatergyPresenter {

    public HomeCatergyView mView;
    private int count = 0;

    public HomeCatergyImpl(HomeCatergyView mView) {
        this.mView = mView;
    }

    @Override
    public void getHomePage(final boolean isFreshAll) {
        CacheManager.getInstance()
                .load("cache_home_page", HomeBean.class, new NetworkCache<HomeBean>() {
                    @Override
                    public Observable<HomeBean> get(String key, Class<HomeBean> cls) {
                        return RetrofitFactory.getCacheHomePage()
                                .subscribeOn(Schedulers.io());
                    }
                })
                .map(new Func1<HomeBean, AppHomePageBean>() {
                    @Override
                    public AppHomePageBean call(HomeBean result) {
                        if (result != null && result.code.equals("0") && result.data != null) {
                            return result.data;
                        } else {
                            return new AppHomePageBean();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AppHomePageBean>(new SubscriberListener<AppHomePageBean>() {


                    @Override
                    public void onNext(AppHomePageBean bean) {
                        mView.initPager(bean,isFreshAll);
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        Log.d("tag", "onError: msg = " + msg);
                    }

                    @Override
                    public void onCompleted() {
                        mView.onRefreshComplete();
                        super.onCompleted();
                    }
                }));
    }

    @Override
    public void getAdData(final String adCode) {
        CacheManager.getInstance().load("cache_home_" + adCode, AdBean.class, new NetworkCache<AdBean>() {
            @Override
            public Observable<AdBean> get(String key, Class<AdBean> cls) {
                return RetrofitFactory.getCacheAd(AdPageCode.APP_HOME, adCode)
                        .subscribeOn(Schedulers.io());
            }
        })
                .map(new Func1<AdBean, AdData>() {
                    @Override
                    public AdData call(AdBean adDataHttpResult) {
                        if (adDataHttpResult != null && adDataHttpResult.code.equals("0") && adDataHttpResult.data != null) {
                            return adDataHttpResult.data;
                        } else {
                            return new AdData();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AdData>(new SubscriberListener<AdData>() {
                    @Override
                    public void onNext(AdData adData) {
                        Gson gson = new Gson();
                        String adJson = gson.toJson(adData, AdData.class);
                        if (adData != null) {
                            mView.initAdData(adCode, adJson);
                        }
                    }
                }));
    }

    public void getFloatTail() {
        RetrofitFactory.getAd(AdPageCode.APP_HOME, "float_tail")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AdData>(new SubscriberListener<AdData>() {
                    @Override
                    public void onNext(AdData adData) {
                        if (adData != null && adData.float_tail != null && adData.float_tail.size() > 0) {
                            mView.initFloatTail(adData.float_tail.get(0));
                        }
                    }
                }));
    }

    @Override
    public void getHeadlines() {
        CacheManager.getInstance().load("cache_home_head_line", HeadLinesBean.class, new NetworkCache<HeadLinesBean>() {
            @Override
            public Observable<HeadLinesBean> get(String key, Class<HeadLinesBean> cls) {
                return RetrofitFactory.getCacheHeadLines()
                        .subscribeOn(Schedulers.io());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<HeadLinesBean>(new SubscriberListener<HeadLinesBean>() {
                    @Override
                    public void onNext(HeadLinesBean headLinesBean) {
                        mView.initHeadlinesData(headLinesBean);
                    }
                }));
    }

    @Override
    public void getRank(final long moduleId) {
//        CacheManager.getInstance().load("cache_home_rank_" + moduleId, ModuleDataBean.class, new NetworkCache<ModuleDataBean>() {
//            @Override
//            public Observable<ModuleDataBean> get(String key, Class<ModuleDataBean> cls) {
//                return RetrofitFactory.getRank(moduleId)
////                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io());
//            }
//        })
        RetrofitFactory.getRank(moduleId)
//                        .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<ModuleDataBean>(new SubscriberListener<ModuleDataBean>() {
                    @Override
                    public void onNext(ModuleDataBean moduleDataBean) {
                        mView.initRankData(moduleDataBean);
                    }
                }));
    }

    @Override
    public void getCategory(final long moduleId) {
        RetrofitFactory.getCategory(moduleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<ModuleDataCategoryBean.CategoryBean>>(new SubscriberListener<List<ModuleDataCategoryBean.CategoryBean>>() {
                    @Override
                    public void onNext(List<ModuleDataCategoryBean.CategoryBean> moduleDataList) {
                        mView.initCategory(moduleId, moduleDataList);
                    }
                }));
    }

    @Override
    public void getCategoryProduct(final long moduleId, final long categoryId, final int pageNo) {
        RetrofitFactory.getCategoryProduct(moduleId, categoryId, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<ModuleDataBean>(new SubscriberListener<ModuleDataBean>() {
                    @Override
                    public void onNext(ModuleDataBean moduleDataBean) {
                        mView.initCategoryProduct(moduleId, moduleDataBean, categoryId);
                    }
                }));
    }

    public void getMsgSummary() {
        count = 0;
        String userId = OdyApplication.getString(Constants.BC_USER_ID, "");
        if (!StringUtils.isEmpty(userId)) {
            String app_key = OdyApplication.getValueByKey(Constants.APP_KEY, "");
            YWIMKit mIMKit = YWAPI.getIMKitInstance(userId, app_key);
            YWIMCore imCore = mIMKit.getIMCore();

            List<YWConversation> conversationList = imCore.getConversationService().getConversationList();
            if (conversationList != null && conversationList.size() > 0) {
                YWConversation conversation = conversationList.get(0);
                if (conversation != null && conversation.getUnreadCount() > 0) {
                    count += conversation.getUnreadCount();
                }
            }
        }

        RetrofitFactory.getMsgSummary()
                .filter(new Func1<MsgSummary, Boolean>() {
                    @Override
                    public Boolean call(MsgSummary msgSummary) {
                        return msgSummary != null && msgSummary.code.equals("0");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<MsgSummary>(new SubscriberListener<MsgSummary>() {
                    @Override
                    public void onNext(MsgSummary summary) {
                        if (summary != null && summary.data != null) {
                            for (MsgSummary.DataBean dataBean : summary.data) {
                                if (dataBean.unReadMsgCount > 0) {
                                    count += dataBean.unReadMsgCount;
                                }
                            }
                        }
                        mView.setUnReadCount(count);
                    }

                    @Override
                    public void onJsonError(String json) {
                        JSONObject jsonString = null;
                        try {
                            jsonString = new JSONObject(json);
                            JSONObject data = jsonString.getJSONObject("data");
                            count = data.getInt("unReadMsgCount");
                            mView.setUnReadCount(count);
                            //这是是判断是否需要请求全部数据
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }

    @Override
    public void getStockPriceRank(String mpIds) {
        RetrofitFactory.getStockPrice(mpIds)
                .filter(new Func1<StockPriceBean, Boolean>() {
                    @Override
                    public Boolean call(StockPriceBean stockPriceBean) {
                        return stockPriceBean != null && stockPriceBean.code.equals("0");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StockPriceBean>(new SubscriberListener<StockPriceBean>() {
                    @Override
                    public void onNext(StockPriceBean o) {
                        if (o.data != null && o.data.plist != null && o.data.plist.size() > 0) {
                            mView.setRankPrice(o);
                        }
                    }
                }));
    }

    @Override
    public void getStockPriceRecommend(String mpIds) {
        RetrofitFactory.getStockPrice(mpIds)
                .filter(new Func1<StockPriceBean, Boolean>() {
                    @Override
                    public Boolean call(StockPriceBean stockPriceBean) {
                        return stockPriceBean != null && stockPriceBean.code.equals("0");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StockPriceBean>(new SubscriberListener<StockPriceBean>() {
                    @Override
                    public void onNext(StockPriceBean o) {
                        if (o.data != null && o.data.plist != null && o.data.plist.size() > 0) {
                            mView.setRecommendPrice(o);
                        }
                    }
                }));
    }

    @Override
    public void getPriceCmsModuleDataVO(String mpIds) {
        RetrofitFactory.getStockPrice(mpIds)
                .filter(new Func1<StockPriceBean, Boolean>() {
                    @Override
                    public Boolean call(StockPriceBean stockPriceBean) {
                        return stockPriceBean != null && stockPriceBean.code.equals("0");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StockPriceBean>(new SubscriberListener<StockPriceBean>() {
                    @Override
                    public void onNext(StockPriceBean o) {
                        if (o.data != null && o.data.plist != null && o.data.plist.size() > 0) {
                            mView.setPriceCmsModuleDataVO(o);
                        }
                    }
                }));
    }



    @Override
    public void getSpecCategoryProduct(final long moduleId, final long categoryId, int pageNo, int pageSize) {
        RetrofitFactory.getSpecCategoryProduct(moduleId, categoryId, pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<ModuleDataBean>(new SubscriberListener<ModuleDataBean>() {
                    @Override
                    public void onNext(ModuleDataBean moduleDataBean) {
                        mView.initSpecCategoryProduct(moduleId, moduleDataBean, categoryId);
                    }
                }));
    }

    @Override
    public void getGiangGouTime(final AppHomePageBean.Children children) {
        Map<String, String> params = new HashMap<>();
        params.put("nocache", System.currentTimeMillis() + "");

        OkHttpManager.getAsyn(Constants.QIANGGOU, params, new OkHttpManager.ResultCallback<QiangGouBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");

            }

            @Override
            public void onResponse(QiangGouBean response) {


                if (response != null) {
                    try {

                        mView.initTimeList(response,children);
                    } catch (Exception e) {

                    }
                    //qiangGouView.initData(response);
                    Log.i("test", "onSucess=========================");
                }
            }
        });
    }



}
