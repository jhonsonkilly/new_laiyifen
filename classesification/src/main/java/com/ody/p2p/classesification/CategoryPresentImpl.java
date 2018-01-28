package com.ody.p2p.classesification;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.conversation.YWConversation;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.classesification.Bean.MultiCategory;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.retrofit.adviertisement.AdBean;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.retrofit.adviertisement.AdPageCode;
import com.ody.p2p.retrofit.cache.CacheManager;
import com.ody.p2p.retrofit.cache.NetworkCache;
import com.ody.p2p.retrofit.category.Category;
import com.ody.p2p.retrofit.category.CategoryBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.retrofit.user.MsgSummary;
import com.ody.p2p.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/30.
 */

public class CategoryPresentImpl {
    private long categoryId;
    private CategoryView view;
    private int count = 0;
    private YWConversation conversation;

    public CategoryPresentImpl(CategoryView view) {
        this.view = view;
    }


    public void getCategory(final long parentId, final int level) {
        view.showLoading();
        String key = "cache_category_pid_" + parentId + "_level_" + level;
        CacheManager.getInstance().load(key, CategoryBean.class, new NetworkCache<CategoryBean>() {
            @Override
            public Observable<CategoryBean> get(String key, Class<CategoryBean> cls) {
                return RetrofitFactory.getCacheCategory(parentId, level)
                        .subscribeOn(Schedulers.io());
            }
        })
                .filter(new Func1<CategoryBean, Boolean>() {
                    @Override
                    public Boolean call(CategoryBean result) {
                        return result != null && result.code.equals("0") && result.data != null;
                    }
                })
                .map(new Func1<CategoryBean, List<Category>>() {
                    @Override
                    public List<Category> call(CategoryBean result) {
                        if (result.data.categorys == null || result.data.categorys.size() == 0) {
                            return new ArrayList<Category>();
                        } else {
                            return result.data.categorys;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<Category>>(new SubscriberListener<List<Category>>() {
                    @Override
                    public void onNext(List<Category> list) {

                        Category category = new Category();
                        category.isSelected = false;
                        category.categoryId = -1;
                        category.imgId = R.drawable.classify_recommand_default;
                        category.categoryName = "伊仔推荐";
                        list.add(0, category);
                        view.setParentCategory(list);
                        list.get(1).isSelected = true;
                        //getCategoryAd(list.get(1).categoryId);
                        getSubCategory(list.get(1).categoryId, 2, new ArrayList<MultiCategory>());
                        //getSubCategory(list.get(0).categoryId, 2);
                    }

                    @Override
                    public void onCompleted() {
                        view.onRefreshComplete();
                        super.onCompleted();
                    }
                }));
    }

    public void getSubCategory(final long parentId, final int level, final List<MultiCategory> items) {
        categoryId = parentId;
        String key = "cache_category_sub_" + parentId + "_level_" + level;
        CacheManager.getInstance().load(key, CategoryBean.class, new NetworkCache<CategoryBean>() {
            @Override
            public Observable<CategoryBean> get(String key, Class<CategoryBean> cls) {
                return RetrofitFactory.getCacheCategory(parentId, level)
                        .subscribeOn(Schedulers.io());
            }
        })
                .map(new Func1<CategoryBean, List<Category>>() {
                    @Override
                    public List<Category> call(CategoryBean result) {
                        if (result == null || result.data == null || result.data.categorys == null || result.data.categorys.size() == 0) {
                            return new ArrayList<Category>();
                        } else {
                            return result.data.categorys;
                        }
                    }
                })
                .map(new Func1<List<Category>, List<MultiCategory>>() {
                    @Override
                    public List<MultiCategory> call(List<Category> list) {
                        List<MultiCategory> categoryList = new ArrayList<MultiCategory>();
                        categoryList.addAll(items);
                        for (int i = 0; i < list.size(); i++) {
                            MultiCategory item = new MultiCategory();
                            item.itemType = MultiCategory.TYPE_SELECTION;
                            item.category = list.get(i);
                            categoryList.add(item);
                            if (list.get(i).children != null && list.get(i).children.size() > 0) {
                                for (Category c : list.get(i).children) {
                                    MultiCategory mi = new MultiCategory();
                                    mi.itemType = MultiCategory.TYPE_CONTENT;
                                    mi.category = c;
                                    categoryList.add(mi);
                                }
                            }
                        }
                        return categoryList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<MultiCategory>>(new SubscriberListener<List<MultiCategory>>() {
                    @Override
                    public void onNext(List<MultiCategory> list) {
                        view.setSubCategory(list);
                        view.hideLoading();
                        //getCategoryAd(parentId);
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {
                        view.hideLoading();
                        super.onCompleted();
                    }
                }));

    }

    public void getSearchWord() {
        RetrofitFactory.getAd(AdPageCode.APP_HOME, "searchword")
                .filter(new Func1<AdData, Boolean>() {
                    @Override
                    public Boolean call(AdData adData) {
                        return adData != null && adData.searchword != null && adData.searchword.size() > 0 && !StringUtils.isEmpty(adData.searchword.get(0).content);
                    }
                })
                .map(new Func1<AdData, String>() {
                    @Override
                    public String call(AdData adData) {
                        return adData.searchword.get(0).content;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<String>(new SubscriberListener<String>() {
                    @Override
                    public void onNext(String value) {
                        view.setHintValue(value);
                    }
                }));
    }

    public void click(List<Category> list, int index) {
        for (Category category : list) {
            if (category.isSelected) {
                category.isSelected = false;
            }
        }
        if (list != null && list.size() > index) {
            list.get(index).isSelected = true;
            if (list.get(index).categoryId < 0) {
                getCategoryAd(list.get(index).categoryId);
            } else {
                getSubCategory(list.get(index).categoryId, 2, new ArrayList<MultiCategory>());
            }
        }
        view.showLoading();
        view.refreshAdapter(index);
    }

    public void updateSubCategory() {
        if (categoryId < 0) {
            getCategoryAd(categoryId);
        } else {
            getSubCategory(categoryId, 2, new ArrayList<MultiCategory>());
        }
    }

    public void getCategoryAd(final long categoryId) {
        final Map<String, String> params = new HashMap<>();
        params.put("adCode", "img_sort_spread,title_hot_product,hot_product,title_common_product,common_product");
        params.put("pageCode", "APP_CATEGORY_PAGE");
        params.put("assocId", String.valueOf(categoryId));
        String key = "cache_category_category_ad";
        CacheManager.getInstance().load(key, AdBean.class, new NetworkCache<AdBean>() {
            @Override
            public Observable<AdBean> get(String key, Class<AdBean> cls) {
                return RetrofitFactory.getCacheAd(params)
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
                .map(new Func1<AdData, List<MultiCategory>>() {
                    @Override
                    public List<MultiCategory> call(AdData o) {
                        List<MultiCategory> list = new ArrayList<MultiCategory>();
                        if (o != null && o.img_sort_spread != null && o.img_sort_spread.size() > 0) {
                            MultiCategory mAd = new MultiCategory();
                            mAd.itemType = MultiCategory.TYPE_AD;
                            mAd.ad = o.img_sort_spread.get(0);
                            list.add(mAd);
                        }
                        if (o.title_hot_product != null && o.title_hot_product.size() > 0) {
                            MultiCategory mAd = new MultiCategory();
                            mAd.ad = o.title_hot_product.get(0);
                            mAd.itemType = MultiCategory.TYPE_SELECTION;
                            Category category = new Category();
                            category.categoryId = -1;
                            category.linkUrl = o.title_hot_product.get(0).linkUrl;
                            category.categoryName = o.title_hot_product.get(0).content;
                            mAd.category = category;
                            list.add(mAd);
                        }
                        if (o.hot_product != null && o.hot_product.size() > 0) {
                            for (Ad ad : o.hot_product) {
                                MultiCategory mAd = new MultiCategory();
                                mAd.ad = ad;
                                mAd.itemType = MultiCategory.TYPE_CONTENT;
                                Category category = new Category();
                                category.categoryId = -1;
                                category.linkUrl = ad.linkUrl;
                                category.categoryName = ad.name;
                                category.pictureUrl = ad.imageUrl;
                                mAd.category = category;
                                list.add(mAd);
                            }
                        }
                        if (o.title_common_product != null && o.title_common_product.size() > 0) {
                            MultiCategory mAd = new MultiCategory();
                            mAd.itemType = MultiCategory.TYPE_SELECTION;
                            mAd.ad = o.title_common_product.get(0);
                            Category category = new Category();
                            category.categoryId = -1;
                            category.linkUrl = o.title_common_product.get(0).linkUrl;
                            category.categoryName = o.title_common_product.get(0).content;
                            mAd.category = category;
                            list.add(mAd);
                        }
                        if (o.common_product != null && o.common_product.size() > 0) {
                            for (Ad ad : o.common_product) {
                                MultiCategory mAd = new MultiCategory();
                                mAd.ad = ad;
                                mAd.itemType = MultiCategory.TYPE_CONTENT;
                                Category category = new Category();
                                category.linkUrl = ad.linkUrl;
                                category.categoryId = -1;
                                category.categoryName = ad.name;
                                category.pictureUrl = ad.imageUrl;
                                mAd.category = category;
                                list.add(mAd);
                            }
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<List<MultiCategory>>(new SubscriberListener<List<MultiCategory>>() {
                    @Override
                    public void onNext(List<MultiCategory> list) {
                        view.setSubCategory(list);
                        view.hideLoading();
//                        getSubCategory(categoryId, 2, list);
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {
                        view.hideLoading();
                        super.onCompleted();
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
                        if (summary.data != null) {
                            for (MsgSummary.DataBean dataBean : summary.data) {
                                if (dataBean.unReadMsgCount > 0) {
                                    count += dataBean.unReadMsgCount;
                                }
                            }
                        }
                        view.setUnReadCount(count);
                    }

                    @Override
                    public void onJsonError(String json) {
                        JSONObject jsonString = null;
                        try {
                            jsonString = new JSONObject(json);
                            JSONObject data = jsonString.getJSONObject("data");
                            count = data.getInt("unReadMsgCount");
                            view.setUnReadCount(count);
                            //这是是判断是否需要请求全部数据
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }));

    }
}
