package com.ody.p2p.productdetail.productdetail.frangment.productdetail;

import android.util.Log;

import com.ody.p2p.Constants;
import com.ody.p2p.PromotionInfo;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.productdetail.productdetail.bean.AddressBean;
import com.ody.p2p.productdetail.productdetail.bean.CheckIsFavouriteBean;
import com.ody.p2p.productdetail.productdetail.bean.CollectBean;
import com.ody.p2p.productdetail.productdetail.bean.DeliveryTimeBean;
import com.ody.p2p.productdetail.productdetail.bean.FreightBean;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.productdetail.productdetail.bean.ProductInfoBean;
import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.productdetail.productdetail.bean.RecommendBean;
import com.ody.p2p.productdetail.productdetail.bean.ShoppingCountBean;
import com.ody.p2p.productdetail.productdetail.bean.StandardBean;
import com.ody.p2p.productdetail.productdetail.bean.UserAdressBean;
import com.ody.p2p.productdetail.productdetail.frangment.ProductWebView;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.retrofit.adviertisement.AdPageCode;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.basepopupwindow.PropertyBean;
import com.ody.p2p.views.slidepager.BannerBean;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ody on 2016/6/1.
 */
public class ProdouctImpl implements ProductPresent {

    public static final String provinceId = "10";
    public static final String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null);
    public static final String companyId = OdyApplication.COMPANYID;
    ProdouctView mView;
    ProductWebView webView;

    private int ImplType = -1;

    public static final int PRODUCT_TYPE = 0;
    public static final int WEB_TYPE = 1;


    public ProdouctImpl(ProdouctView mView, int ImplType) {
        super();
        this.mView = mView;
        this.ImplType = ImplType;
    }


    public ProdouctImpl(ProductWebView webView, int ImplType) {
        super();
        this.webView = webView;
        this.ImplType = ImplType;
    }


    //基本数据  type可以判断我是否要做接下来的请求
    @Override
    public void selectProduct(String mpid, final int type) {//baseInfo不用传ut
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpId", mpid);//商品ID
        map.put("areaCode", OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
//        map.put("provinceId", provinceId);  //  seh
//        mView.showLoading();
        OkHttpManager.getAsyn(Constants.PRODUCT_INFO, map, mView.context().getClass().toString(), new OkHttpManager.ResultCallback<ProductInfoBean>() {

            @Override
            public void onError(Request request, Exception e) {
                mView.loadingError("查询失败");
            }

            @Override
            public void onResponse(ProductInfoBean response) {
                ProductInfoBean bean = response;
                if (bean != null && bean.getData() != null && bean.getData().size() > 0) {
                    mView.getAllData(bean, type);
                    newGuessYouLike(bean.getData().get(0).mpId + "");
                }
            }

            @Override
            public void onNetError() {
                super.onNetError();
                mView.loadingError("Note");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                mView.loadingError(msg);
                mView.hideLoading();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }


            @Override
            public void onResponse(String json) {
                Log.e("json", json);
                mView.backGetAllDataToJson(json, type);
            }
        });
    }

    //  运费请求
    @Override
    public void deliveryFeeDesc(String mpId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpId", mpId);//商品ID
        map.put("provinceCode", "310000");//省份的code   目前是默认上海
        map.put("quantity", "1");//商品ID

        mView.showLoading();
        OkHttpManager.getAsyn(Constants.PRODUCT_FREIGHT, map, mView.context().getClass().toString(), new OkHttpManager.ResultCallback<FreightBean>() {

            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(FreightBean response) {
                //默认那第一个  因为其他集合里面是其他地方使用的的运费 一个是商品详情的
                if (null != response.getData() && !StringUtils.isEmpty(response.getData().get(0).freight + "")) {
                    mView.delivery(response.getData().get(0).freight + "");
                } else {
                    mView.delivery("0.0");
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

        });

    }

    /**
     * 单一促销的数据放入
     *
     * @param promotionIconUrls
     */
    @Override
    public void setPromotionUrl(List<String> promotionIconUrls) {
        final List<PromotionInfo> bean = new ArrayList<>();
        for (int i = 0; i < promotionIconUrls.size(); i++) {
            if (null != promotionIconUrls.get(i)) {
                PromotionInfo Info = new PromotionInfo();
                Info.setIconText("onlyPromortion");
                Info.setIconUrl(promotionIconUrls.get(i) + "");
                bean.add(Info);
            }
        }
        mView.setPromotionUrls(bean);
    }

    @Override
    public void addHistory(String mpId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpId", mpId);//商品ID
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.HISTORY_ADD, map, mView.context().getClass().toString(), new OkHttpManager.ResultCallback<PromotionBean>() {

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(PromotionBean response) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

        });
    }

    //多种促销
    @Override
    public void proMotion(String mpId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpIds", mpId);//商品ID
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.PRODUCT_PROMOTIONINFO, map, mView.context().getClass().toString(), new OkHttpManager.ResultCallback<PromotionBean>() {

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(PromotionBean response) {
                PromotionBean bean = response;
                if (null != response.data && null != response.data.promotionInfo && response.data.promotionInfo.size() > 0) {
                    mView.proMotion(bean.getData().getPromotionInfo().get(0).getPromotions());//固定从第1个集合开始收取数据
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

        });
    }


    //评价数据
    public void latelyCommend(String mpId, int hasPic) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mpId", mpId);
        params.put("hasPic", hasPic + "");    // 	是否有图 0:全部;1:有图;2:无图
        params.put("rateFlag", 1 + "");//0:全部；1:好评;2:中评;3:差评
        params.put("pageNo", "1");  //  页数
        params.put("pageSize", 1 + "");//条数
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.PRODUCT_APPRAISE, params, mView.context().getClass().toString(), new OkHttpManager.ResultCallback<ProductComment>() {

            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(ProductComment response) {
                if (null != response && response.code.equals("0")) {
                    if (response != null && null != response.data) {
                        mView.setCommendData(response.data.mpcList, String.valueOf(response.data.ratingUserCount), response.data.positiveRate);
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }
        });

    }


    /**
     * 购物车数量
     */
    public void cartNum() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        map.put("sessionId", OdySysEnv.getSessionId());
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.PRODUCT_CARTCOUNT, map, new OkHttpManager.ResultCallback<ShoppingCountBean>() {

            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(ShoppingCountBean response) {
                ShoppingCountBean bean = response;
                if (null != response && response.code.equals("0") && bean.getData() > 0) {
                    mView.cartNum(bean.getData());
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

        });
    }

    @Override
    public void newGuessYouLike(String mpids) {
        Map<String, String> params = new HashMap<>();
        params.put("sceneNo", "1");//推荐商品场景，0,首页;1,详情页;,2购物车;3,订单页;4,搜索页无搜索结果
        params.put("mpIds", mpids);//商品Id，用,隔开
        params.put("pageNo", "1");
        params.put("pageSize", "16");//每页2个，最多8页
        params.put("areaCode", OdyApplication.getValueByKey(Constants.AREA_CODE, ""));
        OkHttpManager.getAsyn(Constants.SHOPCART_RECOMMEND, params, new OkHttpManager.ResultCallback<Recommedbean>() {

            @Override
            public void onResponse(Recommedbean response) {
                if (null != response && null != response.getData()) {
//                    getRecommedData(response);
                    mView.guessYouLike(response);
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

//    //商品推荐  目前P2P里面是没有 的
//    public void guessYouLike(String mpid) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("mpId", mpid);//商品ID
//        map.put("pageNo", "1");//页数
//        map.put("pageSize", "12");//条数
//        mView.showLoading();
//        OkHttpManager.getAsyn(Constants.PRODUCT_RECOMMMEND, map, new OkHttpManager.ResultCallback<RecommendBean>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                mView.hideLoading();
//            }
//
//            @Override
//            public void onResponse(RecommendBean response) {
//                RecommendBean bean = response;
//
//                if (bean != null && bean.getData() != null && bean.getData().size() > 0) {
//                    transitionData(bean);
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                mView.hideLoading();
//
//            }
//        });
//    }

    //转换数据
    public void transitionData(RecommendBean bean) {
//        RecommendAdapterBean data = new RecommendAdapterBean();
//        List<RecommendAdapterBean.RecommendData> list = new ArrayList<RecommendAdapterBean.RecommendData>();
//        RecommendAdapterBean.RecommendData datas;
//        for (int i = 0; i < bean.getData().size(); i++) {
//            datas = new RecommendAdapterBean.RecommendData();
//            datas.setProdutImgUrl(bean.getData().get(i).getPicUrl() + "");
//            datas.setProdutName(bean.getData().get(i).getName());
//            datas.setProdutPrice(bean.getData().get(i).getPrice());
//            datas.setMpId(bean.getData().get(i).getMpId());
//            datas.setProdutSales(125);
//            /*for (int j = 0; j < bean.getData().get(i).getlist.size()) {
//                datas.setPromtionUrls(null);
//            }*/
//            list.add(datas);
//            data.setDatas(list);
//        }
//
//        mView.guessYouLike(data);   //猜你喜欢
    }

    //取消收藏=====post
    @Override
    public void cancelEnshrine(String mpId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("type", "1");
        params.put("entityId", mpId);// 	商品ID 	不是必传
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.CLEAN_ALL_SHOU_CANG, new OkHttpManager.ResultCallback<BaseRequestBean>() {

            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
                mView.doError(false, 0);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                mView.toast(false);
                mView.doError(true, 0);

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                mView.doError(false, 0);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();

            }
        }, params);


    }

    //收藏-----post
    @Override
    public void Enshrine(int i, String mpId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("type", "1");
        params.put("entityId", mpId);
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.SHOU_CANG, new OkHttpManager.ResultCallback<CollectBean>() {

            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
                mView.doError(false, 1);
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                if (!StringUtils.isEmpty(code) && "11006001".equals(code)) {
                    mView.doError(false, 11006001);
                } else {
                    mView.doError(false, 1);
                }
            }

            @Override
            public void onResponse(CollectBean response) {
                mView.hideLoading();
                if (!StringUtils.isEmpty(response.code) && "0".equals(response.code)) {
                    mView.toast(true);
                    mView.doError(true, 1);

                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }
        }, params);
    }

    //规格参数
    @Override
    public void productStandard(String pid) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("mpsId", pid);//商品ID
        if (ImplType == WEB_TYPE) {
            webView.showLoading();
        } else if (ImplType == PRODUCT_TYPE) {
            mView.showLoading();
        }

        OkHttpManager.postAsyn(Constants.SHOPPING_GUIGE, new OkHttpManager.ResultCallback<StandardBean>() {

            @Override
            public void onError(Request request, Exception e) {
                if (ImplType == WEB_TYPE) {
                    webView.hideLoading();
                } else if (ImplType == PRODUCT_TYPE) {
                    mView.hideLoading();
                }

            }

            @Override
            public void onResponse(StandardBean response) {
                StandardBean bean = response;
                if (null != response && response.code.equals("0")) {

                    if (bean != null && bean.getData() != null && bean.getData().size() > 0) {
                        if (webView != null) {
                            webView.standard(bean);   //规格
                        } else {
                            mView.standard(bean);   //规格
                        }
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (webView != null) {
                    webView.hideLoading();   //规格
                } else {
                    mView.hideLoading();   //规格
                }

            }

            @Override
            public void onResponse(String json) {
                super.onResponse(json);
            }
        }, params);


    }


    /**
     * 加入购物车
     */
    @Override
    public void addShopCard(String mpId, int ProductNums) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("mpId", mpId);
        params.put("num", String.valueOf(ProductNums));
        params.put("sessionId", OdySysEnv.getSessionId());
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.ADD_SHOPPING, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null && "0".equals(response.code)) {
                    mView.addShopCarCode();
                }
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                if ("001001003".equals(code)) {
                    mView.noHavePradut();
                } else {
//                    ToastUtils.showStr(mView.context().getString(R.string.add_to_shopcart_faile));
                    ToastUtils.showStr(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }
        }, params);

    }

    /*  //促销
      @Override
      public void sales(String s) {

          mView.sales();
      }


      @Override
      public void minus(String s) {
          Map<String, String> map = new HashMap<String, String>();
          map.put("mpId", "");
  //        mView.showLoading();
          OkHttpManager.getAsyn(Constants.PRODUCT_Minu, map, new OkHttpManager.ResultCallback<MinuBean>() {


              @Override
              public void onError(Request request, Exception e) {
                  mView.loadingError();
  //                mView.hideLoading();
              }

              @Override
              public void onResponse(MinuBean response) {
  //                mView.hideLoading();
                  MinuBean bean = response;
                  if (null != response && response.code.equals("0")) {

                      if (bean != null && bean.getData() != null && bean.getData().size() > 0) {
                          mView.minus(bean);

                      }
                  } else {
                      mView.loadingError();

                  }
              }
          });


      }
       //领卷
      @Override
      public void getTicket(String s) {
          Map<String, String> map = new HashMap<String, String>();
          map.put("mpId", s);
  //        mView.showLoading();

          OkHttpManager.getAsyn(Constants.PRODUCT_GETCOUPON, map, new OkHttpManager.ResultCallback<CouponBean>() {

              @Override
              public void onError(Request request, Exception e) {
                  mView.loadingError();
  //                mView.hideLoading();
              }

              @Override
              public void onResponse(CouponBean response) {
  //                mView.hideLoading();
                  CouponBean bean = response;
                  if (null != response && response.code.equals("0")) {

                      if (bean != null && bean.getData() != null && bean.getData().size() > 0) {
                          mView.getTicket(bean);   //猜你喜欢
                      }
                  } else {
                      mView.loadingError();
                  }
              }
          });
      }
  */
    @Override
    public void clickPhoto(ArrayList<BannerBean> list, int postion) {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            urls.add(list.get(i).image);
        }
        mView.onImageClick(urls, postion);
    }

    //系列属性
    @Override
    public void SerialProducts(String mpID, final int type) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpId", mpID);
        map.put("isDetail", true + "");
        mView.showLoading();
        String url = "";
        if (type == TYPE_SERIALPRODUCT) {
            url = Constants.PRODUCT_SERIALPRRODUCES;//系列属性
        } else {
            url = Constants.PRODUCT_ASSOCIATEPRODUCTS;//关联商品
        }
        OkHttpManager.getAsyn(url, map, new OkHttpManager.ResultCallback<PropertyBean>() {

            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();

            }

            @Override
            public void onResponse(PropertyBean response) {

                if (null != response && null != response.getData()) {
                    //关联商品的时候吧属性设置过去
                    if (type == TYPE_ASSOCIATEPRODUCTS && null != response.getData().getAssociateProducts() && response.getData().getAssociateProducts().size() > 0) {
                        response.getData().setSerialProducts(response.getData().getAssociateProducts());
                    }
                    mView.SerialProducts(response);
                } else if (!StringUtils.isEmpty(response.code) && "-1".equals(response.code)) {
                    ToastUtils.showShort(mView.context().getString(R.string.query_product_info_faile));
                } else {
                    mView.SerialProducts(null);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }
        });

    }

    //选择地址
    @Override
    public void toAddress(String mpID) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpId", mpID);
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.PRODUCT_THIRDADDRESS, map, new OkHttpManager.ResultCallback<UserAdressBean>() {

            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(UserAdressBean response) {
                UserAdressBean bean = response;
                if ("0".equals(response.code) && bean != null && bean.getData() != null && bean.getData().getUsualAddress().size() > 0) {
                    mView.toAddress(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }
        });
    }

    /**
     * 获取收货人地址
     */
    @Override
    public void getDeliveryAddress() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.ADDRESS_LIST, new OkHttpManager.ResultCallback<AddressBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(AddressBean response) {
                mView.deliveryAddress(response);
            }
        }, params);
    }

    /**
     * 获取配送时间
     */
    @Override
    public void getDeliveryTime(String mpid, AddressBean.Address address) {
//        mpId	Long	是	商品id
//        provinceCode	int	是	省级区域code
//        cityCode	int	是	市级区域code
//        regionCode	int	是	区级区域code
        Map<String, String> params = new HashMap<>();
        params.put("mpid", mpid);
        params.put("provinceCode", address.getProvinceCode() + "");
        params.put("cityCode", address.getCityId() + "");
        params.put("regionCode", address.getRegionId() + "");
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.DELIVERY_TIME, new OkHttpManager.ResultCallback<DeliveryTimeBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(DeliveryTimeBean response) {
                mView.getDelivetyTime(response.getData().getDeliveryDescription());
            }
        }, params);
    }

    @Override
    public void getCurrentPrice(String mpIds) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpIds", mpIds);//商品ID
        OkHttpManager.getAsyn(Constants.PRODUCT_CURRENT_PRICE, map, mView.context().getClass().toString(), new OkHttpManager.ResultCallback<StockPriceBean>() {

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(StockPriceBean response) {
                if (response != null && response.data != null) {
                    mView.setCurrentPrice(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

        });
    }

    @Override
    public void getGuessLikePrice(String mpIds) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mpIds", mpIds);//商品ID
        OkHttpManager.getAsyn(Constants.PRODUCT_CURRENT_PRICE, map, mView.context().getClass().toString(), new OkHttpManager.ResultCallback<StockPriceBean>() {

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(StockPriceBean response) {
                if (response != null && response.data != null) {
                    mView.setGuessLikePrice(response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

        });
    }

    @Override
    public void getCollectionStatus(String entityId) {
        Map<String, String> params = new HashMap<>();
        params.put("entityId", entityId);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("type", "1");
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.GET_COLLECTION, new OkHttpManager.ResultCallback<CheckIsFavouriteBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(CheckIsFavouriteBean response) {
                if (response != null && response.code.equals("0")) {
                    mView.checkIsfavourite(response);
                }
            }
        }, params);
    }

    @Override
    public void getGroupAd(String adCode) {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", adCode);
        params.put("platform", "3");
        params.put("pageCode", AdPageCode.APP_COMMODITY_DETAILS_PAGE);
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, mView.getNetTAG(), new OkHttpManager.ResultCallback<FuncBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(FuncBean response) {
                if (response != null) {
                    mView.initGroupAd(response);
                }
            }
        });
    }

}
