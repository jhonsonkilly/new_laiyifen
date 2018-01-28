package com.ody.p2p.productdetail.productdetail.frangment.productdetail;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ody.p2p.Constants;
import com.ody.p2p.PromotionAdapter;
import com.ody.p2p.PromotionInfo;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.data.ScanHistoryBean;
import com.ody.p2p.data.SynHistoryBean;
import com.ody.p2p.productdetail.ProductDetailActivity;
import com.ody.p2p.productdetail.photoamplification.ViewPagerActivity;
import com.ody.p2p.productdetail.productdepreciate.ProductThepriceActivity;
import com.ody.p2p.productdetail.productdetail.adapter.CommendToLatelyAdapter;
import com.ody.p2p.productdetail.productdetail.adapter.ProdutActionAdapter;
import com.ody.p2p.productdetail.productdetail.adapter.RecommendAdapter;
import com.ody.p2p.productdetail.productdetail.adapter.StandardAdapter;
import com.ody.p2p.productdetail.productdetail.bean.AddressBean;
import com.ody.p2p.productdetail.productdetail.bean.CheckIsFavouriteBean;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.productdetail.productdetail.bean.ProductInfoBean;
import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.productdetail.productdetail.bean.StandardBean;
import com.ody.p2p.productdetail.productdetail.bean.UserAdressBean;
import com.ody.p2p.productdetail.productrecommend.ProductAppriesActivity;
import com.ody.p2p.productdetail.views.DatabaseHelper;
import com.ody.p2p.productdetail.views.MyProductContentPage;
import com.ody.p2p.productdetail.views.MyProductDetailInfoPage;
import com.ody.p2p.productdetail.views.ProductSlideToAddress;
import com.ody.p2p.productdetail.views.ProductSlideToPresen;
import com.ody.p2p.productdetail.views.ProdutServiceSlide;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.recmmend.RecommendView;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.retrofit.home.StockPriceBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.TKUtil;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.basepopupwindow.CouponBean;
import com.ody.p2p.views.basepopupwindow.CouponWindow;
import com.ody.p2p.views.basepopupwindow.ProductBean;
import com.ody.p2p.views.basepopupwindow.PropertyBean;
import com.ody.p2p.views.basepopupwindow.PropertyWindow;
import com.ody.p2p.views.countdown.CountDownForDetailView;
import com.ody.p2p.views.flowLayout.FlowRadioLayout;
import com.ody.p2p.views.odyscorllviews.OdySnapPageLayout;
import com.ody.p2p.views.recyclerviewlayoutmanager.FullyGridLayoutManager;
import com.ody.p2p.views.scrollwebview.ScrollWebView;
import com.ody.p2p.views.selectaddress.RequestAddressBean;
import com.ody.p2p.views.selectaddress.SeclectAddressPopupWindow;
import com.ody.p2p.views.selectaddress.selectAddressListener;
import com.ody.p2p.views.slidepager.AutoScrollViewPager;
import com.ody.p2p.views.slidepager.BannerBean;
import com.ody.p2p.views.slidepager.BannerPager;
import com.ody.p2p.views.slidepager.RecommendBannerPager;
import com.tencent.smtt.sdk.WebSettings;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dsshare.SharePopupWindow;

import static com.ody.p2p.utils.PxUtils.dipTopx;


public class ProductFragment extends BaseFragment implements PropertyWindow.PropertyBack, ProductView, OdySnapPageLayout.PageSnapedListener, View.OnClickListener, ProdutActionAdapter.PromotionBack, CommendToLatelyAdapter.AppriesAdapterCallBack, RecommendAdapter.RecommendAdapterCallBack, SharePopupWindow.RefreshUIListener {

    protected PromotionAdapter promotionIconAdapter;
    protected List<PromotionInfo> promotionInfolist = new ArrayList<>();

    public static final String TAG = "ProdouctinfodeatilFragment";
    private static final int SERVICE_GUARANTEE_LIMIT = 4;//服务保障最多展示4个
    public int serailType;//系列属性or关联商品
    //上下分页
    public OdySnapPageLayout mcoySnapPageLayout = null;
    public OdySnapPageLayout.McoySnapPage bottomPage = null;
    public OdySnapPageLayout.McoySnapPage topPage = null;
    public com.ody.p2p.productdetail.views.MyScrollView product_scrollview;
    public ImageView img_defaultimg;//m默认图
    public BannerPager pager_banner;
    CountDownForDetailView countDownForDetailView;//倒计时
    View viewLine;

    RelativeLayout rl_skill;
    TextView tv_kill_price, tv_pred_num;
    /**
     * 是否需要选择系列属性
     */
    public boolean isNeedChooseStanrd = true;

    /**
     * 规格的数据储存
     */
    public PropertyBean propertyData;
    /**
     * 是否需要选中地址
     */
    public boolean isNewAddress = true;

    //选择规格        //  收货地址           促銷活動   满减   评价跳转
    public View layout_choose, layout_toadresses, ll_minus, ll_commend;

    //评价总数
    TextView txt_appraisesum;
    //评价好评度
    public TextView txt_rating;

    //促销的标签
    LinearLayout ll_promotion;
    RecyclerView recycler_promotion;
    //保障
    public View ll_tequan;
    public FlowRadioLayout ll_security;
    public ImageView img_securitymore;

    //促销列表
    public LinearLayout ll_fullcut;
    public RecyclerView list_cuxiao;
    //系列属性
    public PropertyWindow propertywindow;
    int choosesize = 1;
    //        全部评价        //为你推荐;  //排行榜     更多为你推荐
    public TextView text_allapparies, text_recommend, text_Billboard, text_otherrecommend;
    //收藏
    public ImageView img_ilike;
    //判断是否有登陆
    boolean isLoGin = false;
    public int LoGonType = 0;
/*    //加入购物车的时候是否需要弹窗
    int addType = 0;*/

    public String goodsName;
    public String goodsUrl;
    public String goodsPrice;
    public String goodsImage;
    //隐藏部分
    //         我的店鋪                      推荐
    public View ll_mydianpu, ll_tuijian;
    //     分享
    public ImageView img_share;
    public int ShareType = SharePopupWindow.SHARE_DETAIL;//分享类型
    protected SharePopupWindow shareindow;
    //满免                         降价通知
    public TextView txt_mianyunfei, txt_theprice;
    //猜你喜欢展示
    public RecommendBannerPager banner_grid;
    //关于猜你喜欢的详情跳转的方式
    private int RecommendAdapterJumpType = com.ody.p2p.recmmend.RecommendAdapter.SINGLETASK;

    public void setRecommendAdapterJumpType(int type) {
        this.RecommendAdapterJumpType = type;
    }

    //文描部分
    View textspxq;
    View textguige;
    View textshfw;
    TextView text_spxq1;
    TextView text_guige1;
    protected TextView text_shfw1;
    protected ScrollWebView webView;//H5的详情
    private String detailUrl;
    String standardUrl;
    String saleUrl;
    String showUrl = detailUrl;//要显示的URL
    int NOW_H5URL = 0;//当前所加载的0文描 1是规格 2服务协议
    //是否有数据
    FrameLayout fl_haveH5;
    LinearLayout ll_notH5;
    RecyclerView guige_list; //规格参数
    /**
     * //置顶按钮和联系客服
     */
    ImageView img_totop_button;


    public ProductSlideToAddress slideFromBottomPopup;

    //评价页面
    public RecyclerView listview_content;
    public CommendToLatelyAdapter adapter_commendAdapter;


    public TextView TxtProductName; //商品名称
    public TextView TxtProductprice;//商品价格
    public TextView txt_origina_price;//商品原价
    public TextView txt_freight;//运费
    public TextView txt_sales;//销量
    public TextView mSerialTxt;//以选择
    public TextView Textchoose;
    public TextView TxtProductramark;//商品备注
    public FlowRadioLayout produt_pingjia; //评价统计
    public TextView pointTip; //最多可反积分
    protected LinearLayout linear_jifen;
    public TextView tv_address;//地址
    public TextView tv_stock_content; //

    RelativeLayout pull_down; //上拉图文
    RelativeLayout pull_up; //下拉详情
    TextView tv_flag_up, tv_flag_down;


    //倒计时
    public View list_itemtime;
    public TextView hoursTv, minutesTv, secondsTv;
    public long mDay = 0;
    public long mHour = 0;
    public long mMin = 00;
    public long mSecond = 30;// 天 ,小时,分钟,秒
    public boolean isRun = true;

    //    public String mNames[] = {"全部(9999)", "正品(111)", "服务好(51521)", "性价比高(11212)", "物流快(8)", "差评(1)", "晒图(30)"};
    public String mNames[] = {"", "", "", "", "", "", ""};


    //商品ID 传入的
    public String mpId = "";

    public String mpId() {
        return mpId;
    }

    public void setMpId(String mpId) {
        this.mpId = mpId;
    }


    public ProductBean product;

    public int isShareDistribution = -1;

//    // mps_id
//    public static String MP_ID = "";

    public ProductPresent mPressent;

    protected FuncBean.Data.AdSource group;
    protected FuncBean.Data.AdSource cut;

    protected String h5DetailUrl;

    /**
     * 取消收藏
     */
    public void isNotcollect() {
        if (mPressent == null) return;
        mPressent.cancelEnshrine(mpId);
    }

    /**
     * 收藏
     */
    public void collect() {
        if (mPressent == null) return;
        mPressent.Enshrine(1, mpId);
    }

    /**
     * toppage上页默认的布局
     */
    int toppage = R.layout.produtdetail_produt_detail_layout;
    int bootmmPage = R.layout.produtdetail_fragment_prodoutdeatiesweb;

    /**
     * 设置上页
     *
     * @param topPage
     */
    public void setTopPage(int topPage) {
        if (topPage > 0) {
            this.toppage = topPage;
        }
    }

    public void setCommendThemeResource(int commendThemeResource) {
        this.commendThemeResource = commendThemeResource;
    }

    /**
     * 评价样式
     */
    int commendThemeResource = 0;


    /**
     * 设置下页
     * setBotomm
     *
     * @param
     */
    public void setBotomm(int bootmmPage) {
        if (bootmmPage > 0) {
            this.bootmmPage = bootmmPage;
        }
    }

    /**
     * themeColor默认的颜色
     */
    int themeColor = R.color.theme_color;

    public void setTextcolor(int textcolor) {
        if (textcolor > 0) {
            this.themeColor = textcolor;
            TxtProductramark.setTextColor(ContextCompat.getColor(getContext(), textcolor));//商品备注
            TxtProductprice.setTextColor(ContextCompat.getColor(getContext(), textcolor));//商品价格
            txt_rating.setTextColor(ContextCompat.getColor(getContext(), textcolor));//好评度
            if (NOW_H5URL == 0) {
                text_spxq1.setTextColor(ContextCompat.getColor(getContext(), textcolor));//文描的首页默认选中的
            } else if (NOW_H5URL == 1) {
                text_guige1.setTextColor(ContextCompat.getColor(getContext(), textcolor));//文描的首页默认选中的
            } else if (NOW_H5URL == 2) {
                text_shfw1.setTextColor(ContextCompat.getColor(getContext(), textcolor));//文描的首页默认选中的
            }
        }
    }

    /**
     * textThemeColor弹窗里面的字体颜色
     */
    int textThemeColor = R.color.white;

    public void setTextThemeColor(int textThemeColor) {
        this.textThemeColor = textThemeColor;
    }

    /**
     * butoon默认的主题
     */
    int themebutoonbg = R.color.theme_color;

    public void setThemebutoonbg(int themebutoonbg) {
        this.themebutoonbg = themebutoonbg;
    }

    /**
     * BaseInfo 拿到的json数据
     *
     * @param
     * @param type
     */
    @Override
    public void backGetAllDataToJson(String json, int type) {
//        guessYouLike(null);
    }

    @Override
    public void guessYouLike(Recommedbean data) {
        initGuessYouLike(data);
    }

    public void setMarginTop(int page) {
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParam.setMargins(0, page, 0, 0);
    }


    @Override
    public int bindLayout() {
        return R.layout.produtdetail_fragment_prdouctinfodeatil;
    }

    public void initView(View view) {
        serailType = mPressent.TYPE_SERIALPRODUCT;//系列属性

        //上下分页
        mcoySnapPageLayout = (OdySnapPageLayout) view.findViewById(R.id.flipLayout);
        mcoySnapPageLayout.setPageSnapListener(this);
        View toPageView = null;
        try {
            toPageView = LayoutInflater.from(getContext()).inflate(toppage, null);
            bottomPage = new MyProductContentPage(getActivity(), LayoutInflater.from(getContext()).inflate(bootmmPage, null));
        } catch (Exception ex) {
            if (getActivity() != null) {
                getActivity().finish();
            }
            return;
        }

        topPage = new MyProductDetailInfoPage(getActivity(), toPageView);

        mcoySnapPageLayout.setSnapPages(topPage, bottomPage);
        pull_down = (RelativeLayout) view.findViewById(R.id.pull_down);
        pull_up = (RelativeLayout) view.findViewById(R.id.pull_up);
        tv_flag_up = (TextView) view.findViewById(R.id.tv_flag_up);
        tv_flag_down = (TextView) view.findViewById(R.id.tv_flag_down);

        rl_skill = (RelativeLayout) view.findViewById(R.id.rl_skill);
        tv_kill_price = (TextView) view.findViewById(R.id.tv_kill_price);
        tv_pred_num = (TextView) view.findViewById(R.id.tv_pred_num);

        //商品图片轮播
        pager_banner = (BannerPager) view.findViewById(R.id.pager_banner);
        listview_content = (RecyclerView) view.findViewById(R.id.listview_content);
        ll_promotion = (LinearLayout) view.findViewById(R.id.ll_promotion);
        pointTip = (TextView) view.findViewById(R.id.txt_pointTip);
        recycler_promotion = (RecyclerView) view.findViewById(R.id.recycler_promotion);
        list_cuxiao = (RecyclerView) view.findViewById(R.id.list_cuxiao);//商品活动
        ll_fullcut = (LinearLayout) view.findViewById(R.id.ll_fullcut);//这个是促销列表
        tv_stock_content = (TextView) view.findViewById(R.id.tv_stock_content);//库存
        TxtProductName = (TextView) view.findViewById(R.id.TxtProductName); //商品名称
        TxtProductprice = (TextView) view.findViewById(R.id.TxtProductprice);///商品价格
        txt_origina_price = (TextView) view.findViewById(R.id.txt_origina_price);//商品原价格
        txt_origina_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        txt_freight = (TextView) view.findViewById(R.id.txt_freight);//运费
        txt_sales = (TextView) view.findViewById(R.id.txt_sales);//销量
        mSerialTxt = (TextView) view.findViewById(R.id.serial);//以选择
        Textchoose = (TextView) view.findViewById(R.id.text_choose);
        TxtProductramark = (TextView) view.findViewById(R.id.TxtProductramark); //商品备注
        banner_grid = (RecommendBannerPager) view.findViewById(R.id.banner_grid); //推荐侧滑gridriew
        produt_pingjia = (FlowRadioLayout) view.findViewById(R.id.produt_pingjia);   //商品评价统计
        //隱藏部分
        ll_tuijian = view.findViewById(R.id.ll_tuijian);
        txt_appraisesum = (TextView) view.findViewById(R.id.txt_appraisesum);//评价总数
        txt_rating = (TextView) view.findViewById(R.id.txt_rating);//好评度
        ll_mydianpu = view.findViewById(R.id.ll_mydianpu);//店铺
        img_share = (ImageView) view.findViewById(R.id.img_share);//分享
        txt_theprice = (TextView) view.findViewById(R.id.txt_theprice);//降价通知
        img_ilike = (ImageView) view.findViewById(R.id.img_ilike);       //收藏
        img_ilike.setVisibility(View.VISIBLE);
        ll_tequan = view.findViewById(R.id.ll_tequan);
        ll_security = (FlowRadioLayout) view.findViewById(R.id.ll_security);//服务保障
        img_securitymore = (ImageView) view.findViewById(R.id.img_securitymore);//更多的服务保障
        text_recommend = (TextView) view.findViewById(R.id.text_recommend); //为你推荐
        text_otherrecommend = (TextView) view.findViewById(R.id.text_otherrecommend);//更多为你推荐
        layout_toadresses = view.findViewById(R.id.layout_toadresses);      //收货地址
        text_Billboard = (TextView) view.findViewById(R.id.text_Billboard);     //排行榜
        layout_choose = view.findViewById(R.id.layout_choose);              //选择规格
        tv_address = (TextView) view.findViewById(R.id.tv_address);//显示I地址
        ll_commend = view.findViewById(R.id.ll_commend);    //评价查看
        text_allapparies = (TextView) view.findViewById(R.id.text_allapparies);//点击全部评价跳转

        //倒计时
        list_itemtime = view.findViewById(R.id.list_itemtime);
        hoursTv = (TextView) view.findViewById(R.id.hours_tv);
        minutesTv = (TextView) view.findViewById(R.id.minutes_tv);
        secondsTv = (TextView) view.findViewById(R.id.seconds_tv);

        //文描部分
        fl_haveH5 = (FrameLayout) view.findViewById(R.id.fl_haveH5);
        ll_notH5 = (LinearLayout) view.findViewById(R.id.ll_notH5);
        guige_list = (RecyclerView) view.findViewById(R.id.guige_list);
        textspxq = view.findViewById(R.id.text_spxq);
        textguige = view.findViewById(R.id.text_guige);
        textshfw = view.findViewById(R.id.text_shfw);
        text_spxq1 = (TextView) view.findViewById(R.id.text_spxq1);
        text_guige1 = (TextView) view.findViewById(R.id.text_guige1);
        text_shfw1 = (TextView) view.findViewById(R.id.text_shfw1);
        img_totop_button = (ImageView) view.findViewById(R.id.img_totop_button);
        webView = (ScrollWebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_DEFAULT);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 设置无边框
        com.tencent.smtt.sdk.WebSettings settings = webView.getSettings(); // 获取webView的设置对象
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // web内容强制满屏
        webView.setVisibility(View.VISIBLE);
        guige_list.setVisibility(View.GONE);

        countDownForDetailView = (CountDownForDetailView) view.findViewById(R.id.count_down_for_detail);
        viewLine = view.findViewById(R.id.lineTwo);

        EventBus.getDefault().register(this);
    }

    @Override
    public void initPresenter() {
        mPressent = new ProductImpl(this, ProductImpl.PRODUCT_TYPE);
        if (webView != null) {
            ontoch();
        }

        //判断是否有请求过数据 如果有就不再进行请求
        if (null != ((ProductDetailActivity) getActivity()).getProductInfoData()) {
            existData();
        } else {
//            downloadBaseInfo(mpId);
        }
        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {//登录状态))
            mPressent.addHistory(mpId);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String historyStr = OdyApplication.getValueByKey("history", null);
                    Gson gson = new Gson();
                    if (historyStr != null && historyStr.length() > 0) {
                        List<SynHistoryBean.SynHistory> list = gson.fromJson(historyStr, new TypeToken<List<SynHistoryBean.SynHistory>>() {
                        }.getType());
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).mpId.equals(mpId)) {
                                list.remove(i);
                            }
                        }
                        SynHistoryBean.SynHistory syn = new SynHistoryBean.SynHistory();
                        syn.mpId = mpId;
                        syn.browsingTime = System.currentTimeMillis();
                        list.add(syn);
                        historyStr = gson.toJson(list);
                    } else {
                        SynHistoryBean.SynHistory syn = new SynHistoryBean.SynHistory();
                        List<SynHistoryBean.SynHistory> list = new ArrayList<>();
                        syn.mpId = mpId;
                        syn.browsingTime = System.currentTimeMillis();
                        list.add(syn);
                        historyStr = gson.toJson(list);
                    }
                    OdyApplication.putValueByKey("history", historyStr);
                }
            });
        }
    }

    /**
     * 检索是否有请求过数据
     */
    public void existData() {
        type();      //评价统计
        getAllData(((ProductDetailActivity) getActivity()).getProductInfoData(), 1);//详情数据
    }


    /**
     * 请求商品基本数据
     */
    public void downloadBaseInfo(String baseMpId) {
        mPressent.selectProduct(baseMpId, 1);//详情数据 全部数据
        mPressent.getCollectionStatus(baseMpId);
    }

    /**
     * 请求数据的方法
     */
    public void downloadData(String loadToMpId) {

        //购物车
        if (((ProductDetailActivity) getActivity()).getCartnum() > 0 || ((ProductDetailActivity) getActivity()).getCartnum() == 0) {
            cartNum(((ProductDetailActivity) getActivity()).getCartnum());
        } else {
            mPressent.cartNum();
        }
        //运费
        if (!!StringUtils.isEmpty(((ProductDetailActivity) getActivity()).getDeliveMoney())) {
            delivery(((ProductDetailActivity) getActivity()).getDeliveMoney());
        } else {
            mPressent.deliveryFeeDesc(loadToMpId);
        }
        //促销
        if (null != ((ProductDetailActivity) getActivity()).getPromotions()) {
            proMotion(((ProductDetailActivity) getActivity()).getPromotions());
        } else {
            mPressent.proMotion(loadToMpId);
        }
//        type();      //评价统计
        if (null != ((ProductDetailActivity) getActivity()).getCommendToRecentlyData()) {
            setCommendData(((ProductDetailActivity) getActivity()).getCommendToRecentlyData(), ((ProductDetailActivity) getActivity()).getRatingUserCount(), ((ProductDetailActivity) getActivity()).getPositiveRate());//评价数据
        } else {
            mPressent.latelyCommend(loadToMpId, 0);//评价
        }


    }

    /* 商品数量 */
    public int ProductNums = 1;
    protected String defaultPhoto;
    @Override
    public void getAllData(ProductInfoBean data, int type) {
        //埋点
//        RecordHelper.getInstance().viewDetailPage(product.name, product.merchantType + "", "null", "ProdouctdeatieslFragment", "null", "ProdouctdeatieslFragment");
//        startRun();//倒计时的开启

        ((ProductDetailActivity) getActivity()).setProductInfoData(data);
        IprodutDetailActivityCallback.setDataSucceed(true, 0);
        product = data.getData().get(0);
        defaultPhoto = product.pics.get(0).url;
        h5DetailUrl = product.h5DetailUrl;
        if (type == 1) {
            //请求其他数据 比如评价等数据
            downloadData(product.mpId + "");
        }

        //请求到的mpId

        mpId = String.valueOf(product.mpId);
        setViewValues(product);
        initListenter();
    }

    @Override
    public void delivery(String data) {
        ((ProductDetailActivity) getActivity()).setDeliveMoney(data);
        //运费
        if (!StringUtils.isEmpty(data)) {
            txt_freight.setText(getContext().getString(R.string.delivery) + "：" + getString(R.string.money_symbol) + data);
        } else {
            txt_freight.setText(R.string.notFreight);
        }
    }

    public void initListenter() {
        txt_theprice.setOnClickListener(this);//降价通知
        text_otherrecommend.setOnClickListener(this);//分享
        text_recommend.setOnClickListener(this); //推荐
        text_Billboard.setOnClickListener(this); //排行
        ll_tequan.setOnClickListener(this); //服务保障
        img_share.setOnClickListener(this);

        layout_choose.setOnClickListener(this);                     //选择规格
        layout_toadresses.setOnClickListener(this);                 //选择地址
        text_allapparies.setOnClickListener(this);
        ll_commend.setOnClickListener(this);
        img_ilike.setOnClickListener(this);
        textspxq.setOnClickListener(this);
        textguige.setOnClickListener(this);
        textshfw.setOnClickListener(this);
        img_totop_button.setOnClickListener(this);
        mcoySnapPageLayout.scrollTo(0, 0);

    }

    Drawable drawable = null;
    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            if (drawable == null) {
                Glide.with(getContext()).load(source).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        drawable = new BitmapDrawable(resource);
                        TxtProductName.setText(Html.fromHtml(htmlName, imgGetter, null));
                    }
                });
            } else {
                int height = PxUtils.dipTopx(16);
                int width = (int) (height * drawable.getIntrinsicWidth() * 1.0f / drawable.getIntrinsicHeight());
                drawable.setBounds(0, 0, width, height);
            }
            return drawable;
        }
    };

    /**
     * 给界面注入值
     *
     * @param product
     */

    String htmlName;

    public void setViewValues(ProductBean product) {
        goodsName = product.name;
        goodsPrice = getString(R.string.money_symbol) + "：" + product.preferentialPrice;
        goodsUrl = product.h5DetailUrl;
        if (product.pics != null && product.pics.size() > 0) {
            goodsImage = product.pics.get(0).url;
        }
//        saveSearchData(goods);//浏览足迹
        if (null != product) {
            img_share.setVisibility(View.VISIBLE);
            /**
             * 为控件设置图片
             */
            getImageLunbo(product);
            /**
             * 商品名字
             */
            if (!StringUtils.isEmpty(product.name)) {
                htmlName = "<img src=\"%s\" /> %s";
                if (!StringUtils.isEmpty(product.saleIconUrl)) {
//                    htmlName = String.format(htmlName, product.saleIconUrl, product.name);
//                    TxtProductName.setText(Html.fromHtml(htmlName, imgGetter, null));
                    UiUtils.getStringSpan(TxtProductName, getContext(), product.saleIconUrl, product.name);
                } else if (null != product.titleIconUrls && product.titleIconUrls.size() > 0 && !StringUtils.isEmpty(product.titleIconUrls.get(0))) {
//                    htmlName = String.format(htmlName, product.titleIconUrls.get(0), product.name);
//                    TxtProductName.setText(Html.fromHtml(htmlName, imgGetter, null));
                    UiUtils.getStringSpan(TxtProductName, getContext(), product.titleIconUrls.get(0), product.name);
                } else {
                    TxtProductName.setText(product.name);
                }
            }
            /**
             * 商品价格
             */
            //先判断是否有促销价
            if (0 < product.promotionPrice) { //商品价格
                //如果有两种价格的话 会显示特价标签
                TxtProductprice.setText(UiUtils.getMoney(getActivity(), product.promotionPrice + "")); //促销价
                txt_origina_price.setText(getContext().getResources().getString(R.string.oldprice_) + UiUtils.getMoneyDouble(product.price));// 原价
            } else {
                TxtProductprice.setText(UiUtils.getMoney(getActivity(), product.price));
                ll_promotion.setVisibility(View.GONE);
                recycler_promotion.setVisibility(View.GONE);
                txt_origina_price.setVisibility(View.GONE);
            }
            /**
             * 促销标签
             */
            if (null != product.promotionIconUrls && product.promotionIconUrls.size() > 0) {
                mPressent.setPromotionUrl(product.promotionIconUrls);
            } else {
                ll_promotion.setVisibility(View.GONE);
                recycler_promotion.setVisibility(View.GONE);
            }

            /**
             * 最多积分展示
             */
            if (StringUtils.isEmpty(product.pointTips)) {
                pointTip.setVisibility(View.GONE);
            } else {
                pointTip.setVisibility(View.VISIBLE);
            }
            pointTip.setText(product.pointTips);

            /**
             * 销量
             */
            txt_sales.setText(getContext().getResources().getString(R.string.sals_) + product.mpSalesVolume);
            /**
             * //商品说明
             */
            if (null != product.subTitle) {
                TxtProductramark.setText(product.subTitle + "");
            } else {
                TxtProductramark.setVisibility(View.GONE);
            }
            /**
             *商品保障
             */
            if (null != product.securityVOList && product.securityVOList.size() > 0) {
                ll_tequan.setVisibility(View.VISIBLE);
                if (product.securityVOList.size() > 3) {
                    ll_tequan.setEnabled(true);
                    ll_security.setVisibility(View.VISIBLE);
                    img_securitymore.setVisibility(View.VISIBLE);
                } else {
                    ll_tequan.setEnabled(false);
                    ll_security.setVisibility(View.VISIBLE);
                    img_securitymore.setVisibility(View.GONE);
                }
//                if (product.securityVOList.size() > 3) {
//                    product.securityVOList = product.securityVOList.subList(0, SERVICE_GUARANTEE_LIMIT);
//                    product.securityVOList = product.securityVOList.subList(0, product.securityVOList.size());
//                }
                ll_security.removeAllViews();
                for (int i = 0; i < (product.securityVOList.size() > SERVICE_GUARANTEE_LIMIT ? SERVICE_GUARANTEE_LIMIT : product.securityVOList.size()); i++) {
                    View layout = LayoutInflater.from(getActivity()).inflate(R.layout.produtdetail_securit_item, null);
                    TextView textView = (TextView) layout.findViewById(R.id.txt_text);
                    ImageView img = (ImageView) layout.findViewById(R.id.img_img);
                    textView.setText(product.securityVOList.get(i).title);
                    if (null != product.securityVOList.get(i).url && !"".equals(product.securityVOList.get(i).url)) {
                        GlideUtil.display(getContext(), product.securityVOList.get(i).url + "").into(img);
                    } else {
                        img.setImageResource(R.drawable.icon_safrtydefault);
                    }
                    ll_security.addView(layout);
                    layout.setTag(i);
                }
            } else {
                //没有数据就隐藏掉
                ll_tequan.setVisibility(View.GONE);
            }
            /**
             *   //文描信息的传入
             */
            if (null != product.h5DetailUrl) {
                fl_haveH5.setVisibility(View.VISIBLE);
                ll_notH5.setVisibility(View.GONE);
                detailUrl = product.h5DetailUrl;

            } else {
                fl_haveH5.setVisibility(View.GONE);
                ll_notH5.setVisibility(View.VISIBLE);
            }
            standardUrl = OdyApplication.H5URL + ProductDetailActivity.STANDARD_H5URL + "?mpId=" + mpId;//规格H5
            saleUrl = OdyApplication.H5URL + ProductDetailActivity.SAL_H5URL + "?mpId=" + mpId;//服务协议H5
            IprodutDetailActivityCallback.getH5Url(detailUrl, standardUrl, saleUrl);
            if (NOW_H5URL == 0) {
                showUrl = detailUrl;
            } else if (NOW_H5URL == 1) {
                showUrl = standardUrl;
            } else if (NOW_H5URL == 2) {
                showUrl = saleUrl;
            }
            webView.loadUrl(showUrl);

            /**
             * 有默认选择规格的时候的时候
             */
            standardDispose(product); //规格参数的判断
            /**
             *   //显示该商品是否被收藏
             */
//            if (product != null && product.isFavorite != 0) {
//                isCheck(true);
//                IprodutDetailActivityCallback.setIfCollect(true);
//            } else {
//                isCheck(false);
//                IprodutDetailActivityCallback.setIfCollect(false);
//            }
            //促销活动  字段废弃。。
            if (!"".equals(product.promotionType) && product.promotionIconUrls != null) {

            } else {
            }
            //库存
            stocknum = product.stockNum;
            if (product.managementState == 0) {
                tv_stock_content.setText(getResources().getString(R.string.notment));
                IprodutDetailActivityCallback.layout_addshoppingsetEnabled(false, 2);
            } else {
                // 商品详情界面的库存信息展示
                if (product.stockNum == 0) {
                    tv_stock_content.setText(getResources().getString(R.string.no_inventory));
                    IprodutDetailActivityCallback.layout_addshoppingsetEnabled(false, 1);
                } else {
                    tv_stock_content.setText(getResources().getString(R.string.inventory_enough));
                    IprodutDetailActivityCallback.layout_addshoppingsetEnabled(true, 0);
                }
            }
        }
        countDownForDetailView.setVisibility(View.GONE);
        viewLine.setVisibility(View.VISIBLE);
        mPressent.getCurrentPrice(mpId);
        //根据是否是秒杀
        if (!TextUtils.isEmpty(product.isSeckill + "") && !TextUtils.isEmpty(product.isForcast + "")) {
            if (product.isSeckill == 1) {

                if (!TextUtils.isEmpty((product.nowDate + "")) && !TextUtils.isEmpty((product.promotionStartTime + "")) && !TextUtils.isEmpty((product.promotionEndTime + ""))) {
                    if (product.promotionStartTime < product.nowDate && product.nowDate < product.promotionEndTime) {
                        viewLine.setVisibility(View.GONE);
                        countDownForDetailView.setVisibility(View.VISIBLE);
                        countDownForDetailView.setCountTime(product.promotionEndTime - product.nowDate);
                    }
                }

                if (!TextUtils.isEmpty((product.nowDate + "")) && !TextUtils.isEmpty((product.promotionStartTime + "")) && !TextUtils.isEmpty((product.promotionEndTime + ""))) {
                    if (product.isForcast == 0) {
                        viewLine.setVisibility(View.GONE);
                        rl_skill.setVisibility(View.VISIBLE);
                        tv_kill_price.setText(UiUtils.getMoney(getActivity(), product.promotionPrice));
//                        ll_skill_cast.setVisibility(View.GONE);
//                        endTime = product.promotionEndTime;
                        countDownForDetailView.setCountTime(product.promotionEndTime - product.nowDate);
//                        ll_price.setVisibility(View.GONE);
                    } else if (product.isForcast == 1) {
                        rl_skill.setVisibility(View.GONE);
                        viewLine.setVisibility(View.VISIBLE);
//                        ll_skill_cast.setVisibility(View.VISIBLE);
                        Date date = new Date(product.forcastPromotionStartTime);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd HH:mm");
                        String time = sdf.format(date);
//                        tv_cast_skill.setText("预计" + time + "开始" + " 活动价￥" + product.forcastPromotionPrice);
//                        ll_price.setVisibility(View.VISIBLE);
                    }
                } else {
                    rl_skill.setVisibility(View.GONE);
                    viewLine.setVisibility(View.VISIBLE);
//                    ll_price.setVisibility(View.VISIBLE);
//                    ll_skill_cast.setVisibility(View.GONE);
                }

            } else {
                if (product.isForcast == 1) {//预售
                    rl_skill.setVisibility(View.GONE);
                    viewLine.setVisibility(View.VISIBLE);
//                    ll_skill_cast.setVisibility(View.VISIBLE);
                    Date date = new Date(product.forcastPromotionStartTime);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM.dd HH:mm");
                    String time = sdf.format(date);
//                    tv_cast_skill.setText("预计" + time + "开始" + " 活动价￥" + product.forcastPromotionPrice);
//                    ll_price.setVisibility(View.VISIBLE);
                } else {
                    rl_skill.setVisibility(View.GONE);
                    viewLine.setVisibility(View.VISIBLE);
//                    ll_price.setVisibility(View.VISIBLE);
//                    ll_skill_cast.setVisibility(View.GONE);
                    if (product.isPresell == 1) {
                        rl_skill.setVisibility(View.VISIBLE);
                        tv_pred_num.setVisibility(View.VISIBLE);
//                        rl_skill.setBackgroundResource(R.drawable.presale_time_bg);
                        countDownForDetailView.setCountTime(product.promotionEndTime - product.nowDate);
                        tv_kill_price.setText(UiUtils.getMoney(getActivity(), product.presellTotalPrice));
                        tv_pred_num.setText("已预订" + product.presellBookedNum + "件");
//                        ll_cast.setVisibility(View.VISIBLE);
//                        tv_cast_money.setText("￥" + UiUtils.getDoubleForDouble(product.presellDownPrice) + "抵扣" + "￥" + UiUtils.getDoubleForDouble(product.presellOffsetPrice));
                        Date date = new Date(product.presellFinalStartTime);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
                        String time = sdf.format(date);
//                        tv_lost_paytime.setText(time + "支付尾款");
//                        tv_lost_paymoney.setText("￥" + UiUtils.getDoubleForDouble(product.presellDownPrice));
                        viewLine.setVisibility(View.GONE);
                    } else {
                        rl_skill.setVisibility(View.GONE);
//                        ll_cast.setVisibility(View.GONE);
                        viewLine.setVisibility(View.VISIBLE);
                    }
                }

            }
        }

    }

    /**
     * 获取轮播数据
     *
     * @param product
     * @return
     */
    public void getImageLunbo(final ProductBean product) {
        ArrayList<String> allImage = new ArrayList<String>();
        ArrayList<String> allName = new ArrayList<String>();
        allImage.clear();
        for (ProductBean.Pics pic : product.pics) {
            if (null != pic) {
//                allImage.add(pic.url);
                allImage.add(pic.url400x400);
                allName.add(pic.name);
            }
        }
        loadImageLunbo(allImage, allName);
    }

    public void loadImageLunbo(ArrayList<String> allImage, ArrayList<String> allName) {
        if (null != allImage) {
            slideshowView(allImage);
        }
    }

    public void slideshowView(final ArrayList<String> allImage) {
        lists.clear();
        defaultpicurl = allImage.get(0);
        for (int i = 0; i < allImage.size(); i++) {
            BannerBean info = new BannerBean();
            info.image = allImage.get(i);
            info.title = "photo" + i;
            lists.add(info);
        }

        pager_banner.setEasyData(lists);
        pager_banner.setDuring(2000);
        pager_banner.setLooper(false);
        pager_banner.setAuto(false);
        pager_banner.setIndicatorPosition(BannerPager.GRAVITY_CENTER, 50, 0, 0);
        pager_banner.setslideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        pager_banner.setslidetouchMode(AutoScrollViewPager.SLIDE_BORDER_AWAYS_PARENT);
        pager_banner.setImageClickListener(new BannerPager.ImageClickLintener() {
            @Override
            public void click(int position) {
                mPressent.clickPhoto(lists, position);
            }
        });
    }

    /**
     * 规格参数显示处理
     */
    public void standardDispose(ProductBean product) {
        if (!isNeedChooseStanrd) {//某些情况不需要展示选择商品规格
            layout_choose.setVisibility(View.GONE);
        } else {
            layout_choose.setVisibility(View.VISIBLE);
            if (!(null == product.attrs) && product.attrs.size() > 0) {
                mSerialTxt.setText("");

                //以选择
                Textchoose.setText(getResources().getString(R.string.already_choose));
                for (int i = 0; i < product.attrs.size(); i++) {
                    mSerialTxt.append(product.attrs.get(i).attrVal.value + ",");
                }
                if (ProductNums > 0) {
                    if (null != product.calculationUnit) {//商品单位
                        mSerialTxt.append("" + ProductNums + product.calculationUnit + ",");
                    } else {
                        mSerialTxt.append(ProductNums + getResources().getString(R.string.unit));
                    }
                }
                layout_choose.setEnabled(true);
            } else {
                Textchoose.setText(getResources().getString(R.string.already_choose));
                mSerialTxt.setText("");
                if (null != product.calculationUnit) {//商品单位
                    mSerialTxt.append(ProductNums + product.calculationUnit);
                } else {
                    mSerialTxt.append(ProductNums + getResources().getString(R.string.unit));
                }

            }
        }
    }

    /**
     * 促销数据的放入
     */
    @Override
    public void proMotion(List<PromotionBean.Data.PromotionInfo.Promotions> promotions) {
        ((ProductDetailActivity) getActivity()).setPromotions(promotions);
        if (null != promotions && promotions.size() > 0) {
            ll_fullcut.setVisibility(View.VISIBLE);
            ProdutActionAdapter promotionAdapter = new ProdutActionAdapter(getContext(), promotions);
            promotionAdapter.setPromotionBack(this);
            promotionAdapter.setMpId(mpId);
            list_cuxiao.setVisibility(View.VISIBLE);
            list_cuxiao.setLayoutManager(RecycleUtils.getLayoutManager(getContext()));
            list_cuxiao.setAdapter(promotionAdapter);
            mPressent.getGroupAd("pintuan_entry,kanjia_entry,pintuan_rule_instruction");
//            List<PromotionInfo> promotionlist = new ArrayList<>();
//            for (int i = 0; i < promotions.size(); i++) {
//                PromotionInfo bean = new PromotionInfo();
//                bean.setIconText(promotions.get(i).iconText);
//                bean.setIconUrl(promotions.get(i).iconUrl);
//                promotionlist.add(bean);
//                promotionlist.add(bean);
//                promotionlist.add(bean);
//                promotionlist.add(bean);
//            }
//            promotionInfolist.addAll(promotionlist);
//            promotionIconAdapter.notifyDataSetChanged();
//            ll_promotion.setVisibility(View.VISIBLE);
//            recycler_promotion.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 单一促销的数据
     *
     * @param bean
     */
    @Override
    public void setPromotionUrls(List<PromotionInfo> bean) {
//        if (null != bean && bean.size() > 0) {
//            promotionInfolist.clear();
//            promotionInfolist.addAll(bean);
//            promotionIconAdapter.notifyDataSetChanged();
//            ll_promotion.setVisibility(View.VISIBLE);
//            recycler_promotion.setVisibility(View.VISIBLE);
//        }

    }


    /**
     * 收藏失败或取消收藏失败
     */
    @Override
    public void doError(boolean ischeck, int type) {
        IprodutDetailActivityCallback.collectChecked(ischeck, type);
        if (ischeck && type == 1) {
            ToastUtils.showToast(getString(R.string.collect_succeed));
        } else if (!ischeck && type == 1) {
            ToastUtils.showToast(getString(R.string.collect_faile));
        } else if (!ischeck && type == 11006001) {//已经重复收藏了
            ToastUtils.showToast(getString(R.string.repetition));
        }
        if (ischeck && type == 0) {
            ToastUtils.showToast(getString(R.string.cancel_collect_succeed));
        } else if (!ischeck && type == 0) {
            ToastUtils.showToast(getString(R.string.cancel_collect_faile));
        }

    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void setCurrentPrice(StockPriceBean bean) {
        /**
         * 商品价格
         */
        //先判断是否有促销价
        if (bean != null && bean.data != null && bean.data.plist != null && bean.data.plist.size() > 0) {
            if (!StringUtils.isEmpty(bean.data.plist.get(0).promotionPrice)) { //商品价格
                //如果有两种价格的话 会显示特价标签
                TxtProductprice.setText(UiUtils.getMoney(getActivity(), bean.data.plist.get(0).promotionPrice)); //促销价
                txt_origina_price.setText(getContext().getResources().getString(R.string.oldprice_) + UiUtils.getMoneyDouble(bean.data.plist.get(0).price));// 原价
            } else {
                TxtProductprice.setText(UiUtils.getMoney(getActivity(), bean.data.plist.get(0).price));
                ll_promotion.setVisibility(View.GONE);
                recycler_promotion.setVisibility(View.GONE);
                txt_origina_price.setVisibility(View.GONE);
            }
            product.price = bean.data.plist.get(0).price;
            try {
                product.promotionPrice = Double.parseDouble(bean.data.plist.get(0).promotionPrice);
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void setGuessLikePrice(StockPriceBean bean) {
        if (bean.data != null && bean.data.plist != null && bean.data.plist.size() > 0) {
            for (int i = 0; i < recommedbean.data.getDataList().size(); i++) {
                for (int j = 0; j < bean.data.plist.size(); j++) {
                    if (bean.data.plist.get(j).mpId.equals(recommedbean.data.getDataList().get(i).getMpId())) {
                        if (!StringUtils.isEmpty(bean.data.plist.get(j).promotionPrice)) {
                            try {
                                recommedbean.data.getDataList().get(i).setSalePrice(Double.parseDouble(bean.data.plist.get(j).promotionPrice));
                                recommedbean.data.getDataList().get(i).setSourcePrice(bean.data.plist.get(j).price);
                            } catch (Exception e) {
                            }
                        } else {
                            recommedbean.data.getDataList().get(i).setSalePrice(bean.data.plist.get(j).price);
                        }
                    }
                }
            }
            banner_grid.notifyDataChanged();
        }
    }

    @Override
    public void checkIsfavourite(CheckIsFavouriteBean checkIsFavouriteBean) {
        if (checkIsFavouriteBean != null && checkIsFavouriteBean.data != null) {
            if (checkIsFavouriteBean.data.isFavorite == 1) {
                isCheck(true);
                IprodutDetailActivityCallback.setIfCollect(true);
            } else {
                isCheck(false);
                IprodutDetailActivityCallback.setIfCollect(false);
            }
        }
    }

    @Override
    public void initGroupAd(FuncBean bean) {
        if (bean != null && bean.data != null && bean.data.kanjia_entry != null) {
            if (bean.data.kanjia_entry.size() > 0) {
                cut = bean.data.kanjia_entry.get(0);
            }
        }
        if (bean != null && bean.data != null && bean.data.pintuan_entry != null) {
            if (bean.data.pintuan_entry.size() > 0) {
                group = bean.data.pintuan_entry.get(0);
            }
        }
    }

    /**
     * 点击促销满赠的消息
     */
    @Override
    public void promotionToPresentOnclik() {

    }

    //查看促销列表
    @Override
    public void lookToGiftPromotion(PromotionBean.Data.PromotionInfo.Promotions data) {
        ProductSlideToPresen productSlideTopresen = new ProductSlideToPresen(getActivity(), themebutoonbg, textThemeColor, data.promotionGiftDetailList, data.promotionRuleList, data.contentType);
        productSlideTopresen.showAtLocation(mcoySnapPageLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    @Override
    public void doBusiness(Context mContext) {
        adapter_commendAdapter = new CommendToLatelyAdapter(getActivity(), null);
        promotionIconAdapter = new PromotionAdapter(getContext(), promotionInfolist);
        promotionIconAdapter.setPromotionCount(100);
//        LinearLayoutManager rankLayoutManager = new LinearLayoutManager(getContext());
//        rankLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FullyGridLayoutManager rankLayoutManager = new FullyGridLayoutManager(getContext(), 8);
        recycler_promotion.addItemDecoration(new ProductSpace(2));
        recycler_promotion.setLayoutManager(rankLayoutManager);
        recycler_promotion.setAdapter(promotionIconAdapter);

    }


    @Override
    public void toast(boolean ischeck) {
        if (ischeck) {
//            ToastUtils.icondefineToast(R.drawable.icon_soucang, getString(R.string.collect_succeed));
            ToastUtils.showToast(getString(R.string.collect_succeed));
        } else {
            ToastUtils.showToast(getString(R.string.cancel_collect_succeed));
        }
        isCheck(ischeck);

    }

    void isCheck(boolean ifcollect) {
        img_ilike.setSelected(ifcollect);
    }

    @Override
    public void onClick(View v) { //收藏
        if (v.getId() == R.id.img_ilike) {
            if (null != OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null) && !"".equals(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null))) {
                if (img_ilike.isSelected()) {
                    isNotcollect();
//                    RecordHelper.getInstance().undoFavorite(product.name, product.merchantType + "", "null", "ProdouctdeatieslFragment", "null", "ProdouctdeatieslFragment");
                } else {
                    collect();
//                    RecordHelper.getInstance().favorite(product.name, product.merchantType + "", "null", "ProdouctdeatieslFragment", "null", "ProdouctdeatieslFragment");
                }
            } else {
//                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://login");
//                activityRoute.withParams(Constants.LOSINGTAP, "100").open();
                Bundle bd = new Bundle();
                bd.putString(Constants.LOSINGTAP, "100");
                JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
                LoGonType = 1;
            }
        } else if (v.getId() == R.id.img_share) {//分享
            String distributorId = OdyApplication.getValueByKey(Constants.DISTRIBUTOR_ID, "");
            TKUtil.upload(OdyApplication.gainContext(), "click_default_share", distributorId, "", "", 2);
            if (OdyApplication.SCHEME.equals("saas")) {
                ToastUtils.showToast(getString(R.string.waite_code));
            } else {
                goToShare();
            }
           /* ProductSlideToshare productSlideToshare = new ProductSlideToshare(getActivity());
            productSlideToshare.showAtLocation(mcoySnapPageLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);*/
           /* sharePopupWindow = new SharePopupWindow(this,0);
            sharePopupWindow.showAtLocation(getWindow().getDecorView(),Gravity.BOTTOM,0,0);*/
        } else if (v.getId() == R.id.layout_choose) {        //  规格
            if (null != propertyData) {
                SerialProducts(propertyData);
            } else {
                mPressent.SerialProducts(mpId, serailType);
            }
        } else if (v.getId() == R.id.txt_theprice) { //降价通知
            Intent intent1 = new Intent(getActivity(), ProductThepriceActivity.class);
            startActivity(intent1);
        } else if (v.getId() == R.id.ll_tequan) {//更多服务保障
            ProdutServiceSlide srviceAssuranceTominus = new ProdutServiceSlide(getActivity(), product.securityVOList, themebutoonbg, textThemeColor);
            srviceAssuranceTominus.showAtLocation(mcoySnapPageLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (v.getId() == R.id.layout_toadresses) { //选择地址
//            mPressent.toAddress("");//有用户地址的选择
            if (isNewAddress) {
                SeclectAddressPopupWindow seclectAddressPopupWindow = new SeclectAddressPopupWindow(getActivity());
                if (null != seclectAddressPopupWindow && !seclectAddressPopupWindow.isShowing()) {
                    seclectAddressPopupWindow.showAtLocation(getView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    seclectAddressPopupWindow.setSelectAddressListener(new selectAddressListener() {
                        @Override
                        public void getAddress(RequestAddressBean.Data first, RequestAddressBean.Data second, RequestAddressBean.Data third) {
                            tv_address.setText("" + first + second + third);
                        }
                    });
                }
            }
        } else if (v.getId() == R.id.tvShowAddCar) { //添加购物车
            if (!(null == product.attrs || " ".equals(product.attrs)) && product.attrs.size() > 0) {
                mPressent.SerialProducts(mpId, serailType);
            } else {
                mPressent.addShopCard(mpId, 1);
            }
        } else if (v.getId() == R.id.tvBuyItNow) {               //立即购买
            if (!(null == product.attrs || " ".equals(product.attrs)) && product.attrs.size() > 0) {
                mPressent.SerialProducts(mpId, serailType);
            } else {
  /*              ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://");
                activityRoute.open();*/
                JumpUtils.ToActivity(JumpUtils.ADDCOUPON);
            }
        } else if (v.getId() == R.id.text_recommend) {           //推荐
            text_recommend.setTextColor(getActivity().getResources().getColor(R.color.theme_color));
            text_Billboard.setTextColor(getActivity().getResources().getColor(R.color.main_title_color));
        } else if (v.getId() == R.id.text_otherrecommend) {     //更多推荐
            Intent intent = new Intent(getActivity(), ProductAppriesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.text_Billboard) {        //排行
            text_recommend.setTextColor(getActivity().getResources().getColor(R.color.main_title_color));
            text_Billboard.setTextColor(getActivity().getResources().getColor(R.color.theme_color));
        } else if (v.getId() == R.id.ll_commend) {         //点击评价跳转第三个也卡
            IprodutDetailActivityCallback.changeFragment(2);
        } else if (v.getId() == R.id.text_allapparies) {        //点击评价跳转第三个也卡
            IprodutDetailActivityCallback.changeFragment(2);
        }
        //文描部分
        else if (v.getId() == R.id.text_spxq) {
            NOW_H5URL = 0;
            text_spxq1.setTextColor(getContext().getResources().getColor(themeColor));
            text_guige1.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
            text_shfw1.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
            showUrl = detailUrl;
            if (null != showUrl && !"".equals(showUrl)) {
                fl_haveH5.setVisibility(View.VISIBLE);
                webView.loadUrl(showUrl);
            } else {
                fl_haveH5.setVisibility(View.GONE);
                ll_notH5.setVisibility(View.VISIBLE);
            }
        } else if (v.getId() == R.id.text_guige) {
             /*  //之前不是一个H5是走接口请求道的数据作展示
             mPressent.productStandard(mpid);
            */
            NOW_H5URL = 1;
            text_guige1.setTextColor(ContextCompat.getColor(getContext(), themeColor));
            text_shfw1.setTextColor(ContextCompat.getColor(getContext(), R.color.main_title_color));
            text_spxq1.setTextColor(ContextCompat.getColor(getContext(), R.color.main_title_color));
            showUrl = standardUrl;
            if (null != showUrl && !"".equals(showUrl)) {
                fl_haveH5.setVisibility(View.VISIBLE);
                webView.loadUrl(showUrl);
            } else {
                fl_haveH5.setVisibility(View.GONE);
                ll_notH5.setVisibility(View.VISIBLE);
            }
        } else if (v.getId() == R.id.text_shfw) {
            //            webView.loadUrl(Constants.SHOUHOU_SERVICE);//本地的H5
            NOW_H5URL = 2;
            text_shfw1.setTextColor(getContext().getResources().getColor(themeColor));
            text_spxq1.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
            text_guige1.setTextColor(getContext().getResources().getColor(R.color.main_title_color));
            showUrl = saleUrl;
            if (null != showUrl && !"".equals(showUrl)) {
                fl_haveH5.setVisibility(View.VISIBLE);
                if (OdyApplication.SCHEME.equals("ds")) {
                    String html;
                    if (StringUtils.isEmpty(product.brandStory)) {
                        html = getString(R.string.empty_brand_story);
                    } else {
                        html = product.brandStory;
                    }
                    webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                } else {
                    webView.loadUrl(showUrl);
                }
            } else {
                fl_haveH5.setVisibility(View.GONE);
                ll_notH5.setVisibility(View.VISIBLE);
            }
        } else if (v.getId() == R.id.img_totop_button) {
            if (webView.getVisibility() == View.VISIBLE) {
                webView.scrollTo(0, 0);
            } else {
                guige_list.smoothScrollToPosition(0);
            }
        }
    }

    public void addProductShopCart() {
        mPressent.addShopCard(mpId, 1);
    }

    /**
     * 获取系列属性
     */
    public void SerialProducts() {
        mPressent.SerialProducts(mpId, serailType);
    }

    @Override
    public void onResume() {
        super.onResume();
        LoGonType = 1;
        isLoGin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);
        mPressent.cartNum();
        mPressent.selectProduct(mpId, LoGonType);//LoGonType==1 查询所有的数据（包含评价猜你喜欢等）
        mPressent.getCollectionStatus(mpId);
        if (LoGonType == 1) {//LoGonType==0只查询基本数据
            LoGonType = 0;
        }
    }

    protected String defaultpicurl = "";

    @Override
    public void SerialProducts(PropertyBean response) {
//        addType = 1;
        propertyData = response;
        if (null != propertyData.getData().getSerialProducts() && propertyData.getData().getSerialProducts().size() > 0) {//查询到规格信息的时候
            propertywindow = new PropertyWindow(getActivity(), propertyData, ProductNums);
        } else {//没有属性的时候 要把图片和价格，数量，主题颜色拿过去
            if (null != product && null != product.preferentialPrice + "" && Double.valueOf(product.promotionPrice) > 0) {
                propertywindow = new PropertyWindow(getActivity(), defaultpicurl, Double.valueOf(product.promotionPrice), product.name, ProductNums, stocknum);
            } else {
                propertywindow = new PropertyWindow(getActivity(), defaultpicurl, product.price, product.name, ProductNums, stocknum);
            }
        }
        if (null != propertywindow) {
            if (serailType == mPressent.TYPE_ASSOCIATEPRODUCTS) {
                propertywindow.setTYPE(propertywindow.TYPE_ADD_SUB);
            } else {
                propertywindow.setTYPE(propertywindow.TYPE_ADD_BAYNOW);
            }
            propertywindow.setPropertyBack(this);
            showPropertyWindow();
        }
    }

    /**
     * 展示系列属性 or 关联商品
     */
    public void showPropertyWindow() {
        if (!propertywindow.isShowing()) {
            propertywindow.showAtLocation(mcoySnapPageLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ViewCreated", "ViewCreated+f1");
    }

    //地址的选择
    @Override
    public void toAddress(UserAdressBean adress) {
        if (null == slideFromBottomPopup) {
            slideFromBottomPopup = new ProductSlideToAddress(getActivity(), adress.getData().getUsualAddress());
        }
        slideFromBottomPopup.showAtLocation(mcoySnapPageLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //评价
    @Override
    public void setCommendData(ProductComment.Data.MpcList bean, String ratingUserCount, Integer positiveRate) {
        ((ProductDetailActivity) getActivity()).setCommendToRecentlyData(bean, ratingUserCount, positiveRate);
        if (bean.listObj.size() > 0) {
            if (!StringUtils.isEmpty(ratingUserCount) && Long.parseLong(ratingUserCount) > 0) {
                txt_appraisesum.setText("(" + ratingUserCount + ")");//好评总数
            }
            if (positiveRate > 0) {
                txt_rating.setText("");//好评度
            } else {
                txt_rating.setText("");//好评度
            }

            ll_commend.setVisibility(View.VISIBLE);
            listview_content.setLayoutManager(RecycleUtils.getLayoutManager(getContext()));
            adapter_commendAdapter.setDatas(bean.listObj);
            if (commendThemeResource != 0) {
                adapter_commendAdapter.setRb_style(commendThemeResource);
            }
            adapter_commendAdapter.setCallBack(this);
            listview_content.setFocusable(false);
            listview_content.setFocusableInTouchMode(false);
            listview_content.setAdapter(adapter_commendAdapter);
            listview_content.setOnClickListener(this);
        } else {
            ll_commend.setVisibility(View.GONE);
        }

    }

    //评价跳转
    @Override
    public void onclick() {
        IprodutDetailActivityCallback.changeFragment(2);
    }

    //点击评价里面的图片进入图片放大
    @Override
    public void clickPhoto(ProductComment.Data.MpcList.ListObj listObj, int postion) {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < listObj.getMpShinePicList().size(); i++) {
            urls.add(listObj.getMpShinePicList().get(i));
        }
        Intent intent = new Intent(getContext(), ViewPagerActivity.class);
        intent.putExtra("urls", (Serializable) urls);
        intent.putExtra("postion", postion);
        intent.putExtra("userImg", listObj.getUserImg());
        intent.putExtra("username", listObj.getUserUsername());
        intent.putExtra("apprisea", listObj.content);
        intent.putExtra("type", "apprieList");
        startActivity(intent);
    }

    //评价
    public void type() {
        if (null != mNames) {
            produt_pingjia.removeAllViews();
            produt_pingjia.setHorizontalSpacing(PxUtils.pxTodip(100));
            produt_pingjia.setVerticalSpacing(PxUtils.pxTodip(100));

            for (int i = 0; i < mNames.length; i++) {
                final CheckBox checkButton = (CheckBox) LayoutInflater.from(getActivity()).inflate(R.layout.produtdetail_layout_textview, null);
                checkButton.setText(mNames[i]);
                checkButton.setTag(i);
                if (i == 0) {
                    checkButton.setChecked(true);
                    checkButton.setTextColor(Color.WHITE);
                }
                checkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reSetRadio(produt_pingjia, (int) v.getTag());
                    }
                });
                produt_pingjia.addView(checkButton);
            }
        }
    }

    public void reSetRadio(FlowRadioLayout layout, int chooseNum) {
        if (null != layout && layout.getChildCount() > 0) {
            for (int i = 0; i < layout.getChildCount(); i++) {
                if (i == chooseNum) {
                    ((CheckBox) layout.getChildAt(i)).setChecked(true);
                    ((CheckBox) layout.getChildAt(i)).setTextColor(Color.WHITE);
                } else {
                    ((CheckBox) layout.getChildAt(i)).setChecked(false);
                    ((CheckBox) layout.getChildAt(i)).setTextColor(getActivity().getResources().getColor(R.color.main_title_color));

                }
            }
        }
    }


    //        //单点图片事件
    public void onImageClick(List<String> urls, int postion) {
        // TODO 单击图片处理事件
        Intent intent = new Intent(getContext(), ViewPagerActivity.class);
        intent.putExtra("urls", (Serializable) urls);
        intent.putExtra("postion", postion);
        startActivity(intent);
    }


    final ArrayList<BannerBean> lists = new ArrayList<>();
    /**
     * recommendPageSize 每一页显示多少个
     */
    int recommendPageSize = 4;

    public void setRecommendPageSize(int recommendPageSize) {
        this.recommendPageSize = recommendPageSize;
    }

    /**
     * recommendPageRowNum 每一hang显示多少个
     */
    int recommendPageRowNum = 2;

    public void setRecommendPageRowNum(int recommendPageRowNum) {
        this.recommendPageRowNum = recommendPageRowNum;
    }

    protected String guessLikeMpIds = "";


    protected Recommedbean recommedbean;

    //猜你喜欢
    public void initGuessYouLike(Recommedbean bean) {
        this.recommedbean = bean;
        if (null != bean && null != bean.getData() && bean.getData().getDataList().size() > 0) {
            ll_tuijian.setVisibility(View.VISIBLE);
            banner_grid.setVisibility(View.VISIBLE);
            ((ProductDetailActivity) getActivity()).setBean(bean);
            if (bean.data.getDataList() != null && bean.data.getDataList().size() > 0) {
                for (int i = 0; i < bean.data.getDataList().size(); i++) {
                    if (StringUtils.isEmpty(guessLikeMpIds)) {
                        guessLikeMpIds += bean.data.getDataList().get(i).getMpId();
                    } else {
                        guessLikeMpIds += ",";
                        guessLikeMpIds += bean.data.getDataList().get(i).getMpId();
                    }
                }
                mPressent.getGuessLikePrice(guessLikeMpIds);
            }
//            List<RecommendAdapter> listRe;
//            RecommendAdapter adapter = null;
//            List<RecommendAdapterBean.RecommendData> recommendList;
//            RecommendAdapterBean.RecommendData recommendData;
//            int page;
//            if (bean.getDatas().size() % recommendPageSize == 0) {
//                listRe = new ArrayList<>();
//                page = bean.getDatas().size() / recommendPageSize;
//                for (int i = 0; i < page; i++) {
//                    recommendList = new ArrayList<>();
//                    for (int j = 0; j < recommendPageSize; j++) {
//                        recommendList.add(bean.getDatas().get(recommendPageSize * i + j));
//                    }
//                    adapter = new RecommendAdapter(getActivity(), recommendList, themeColor);
//                    adapter.setRecommendAdapterCallBack(this);
//                    listRe.add(adapter);
//                }
//            } else {
//                listRe = new ArrayList<>();
//                page = bean.getDatas().size() / recommendPageSize;
//                for (int i = 0; i < page; i++) {
//                    recommendList = new ArrayList<>();
//                    for (int j = 0; j < recommendPageSize; j++) {
//                        recommendList.add(bean.getDatas().get(recommendPageSize * i + j));
//                    }
//                    adapter = new RecommendAdapter(getActivity(), recommendList, themeColor);
//                    adapter.setRecommendAdapterCallBack(this);
//                    listRe.add(adapter);
//                }
//                for (int k = page * recommendPageSize; k < bean.getDatas().size(); k++) {
//                    recommendList = new ArrayList<>();
//                    recommendList.add(bean.getDatas().get(k));
//                    adapter = new RecommendAdapter(getActivity(), recommendList, themeColor);
//                    adapter.setRecommendAdapterCallBack(this);
//                    listRe.add(adapter);
//                }
//            }
            //如果是默认的
//            int row = recommendPageSize / recommendPageRowNum;
//            LinearLayout.LayoutParams ps;
//            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) banner_grid.getLayoutParams();
//            linearParams.height = dipTopx(300);
//            banner_grid.setLayoutParams(linearParams);
//            if (row == 1) {
//                int higt = banner_grid.getHeight();
//                int width = banner_grid.getWidth();
//                ps = (LinearLayout.LayoutParams) banner_grid.getLayoutParams();
//                ps.height = higt * row;
//                ps.width = width;
//                banner_grid.setLayoutParams(ps);
//            } else {
//                int higt = banner_grid.getHeight();
//                int width = banner_grid.getWidth();
//                ps = (LinearLayout.LayoutParams) banner_grid.getLayoutParams();
//                ps.height = higt * (row + 1);
//                ps.width = width;
//                banner_grid.setLayoutParams(ps);
//            }
            ViewGroup.LayoutParams linearParams = banner_grid.getLayoutParams();
            if (bean.getData().getDataList().size() > recommendPageSize) {//推荐商品不满两个展示一行
                linearParams.height = dipTopx(580);
            } else if (bean.getData().getDataList().size() > recommendPageRowNum) {
                linearParams.height = dipTopx(560);
            } else {
                linearParams.height = dipTopx(280);
            }
            banner_grid.setLayoutParams(linearParams);
            banner_grid.setLayoutCount(recommendPageRowNum);
            banner_grid.setEasyData(RecommendView.setRecommendData(getContext(), bean.getData().getDataList(), recommendPageSize, RecommendAdapterJumpType, getRecommendCallBack()));
            banner_grid.setAuto(false);
            banner_grid.setIndicatorPosition(BannerPager.GRAVITY_CENTER, dipTopx(18), 30, 0);
            banner_grid.setslideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
            banner_grid.setslidetouchMode(AutoScrollViewPager.SLIDE_BORDER_AWAYS_PARENT);
        } else {
            ll_tuijian.setVisibility(View.GONE);
        }
    }

    public com.ody.p2p.recmmend.RecommendAdapter.RecommendCallBack getRecommendCallBack() {
        return null;
    }

    //优惠券
    @Override
    public void getTicket(CouponBean bean) {
        CouponWindow cuponWindow = new CouponWindow(getActivity(), bean);
        cuponWindow.showAtLocation(mcoySnapPageLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    //上下分页
    int ViewPostion = 0;
    public IprodutDetailActivityCallback IprodutDetailActivityCallback;

    public void setIprodutDetailActivityCallback(IprodutDetailActivityCallback t) {
        this.IprodutDetailActivityCallback = t;
    }

    /***
     * /上下分页的切换
     *
     * @param
     */
    @Override
    public void onSnapedCompleted(int derection) {
        if (null != IprodutDetailActivityCallback && ViewPostion != derection) {
            if (derection == 0) {
                IprodutDetailActivityCallback.hideTitle();
            } else if (derection == 1) {
                IprodutDetailActivityCallback.showTitle();
                pull_up.setVisibility(View.GONE);
                pull_down.setVisibility(View.VISIBLE);
//                tv_flag_up.setText(R.string.lookProdut2);
//                tv_flag_down.setText(R.string.photoDeatile2);
            } else {
                IprodutDetailActivityCallback.hideTitle();
                pull_up.setVisibility(View.VISIBLE);
                pull_down.setVisibility(View.GONE);
//                tv_flag_down.setText(R.string.photoDeatile);
//                tv_flag_up.setText(R.string.lookProdut);

            }
            ViewPostion = derection;
        }
    }

    //购买的时候判断是否有选择过规格
    public void buyIng() {
        //注释调的是购买的时候会进行弹框
 /*       if (!(null == product.attrs || "".equals(product.attrs))
                && product.attrs.size() > 0) {
//            if (ProductNums != 0) {
//                toPay(ProductNums + "");
//            } else {
                SerialProducts();
//            }
        } else {
                    toPa);
        }*/
        if (OdyApplication.SCHEME.equals("ds")) {
            if (null != product && null != product.attrs && product.attrs.size() > 0) {
                SerialProducts();
            } else {
                toPay(ProductNums + "");
            }
        } else {
            //现在是直接进行购买
            toPay(ProductNums + "");
        }
    }

    //加入购物车的时候判断是否有选择过规格
    public void addcarIng() {
        if (isNeedChooseStanrd && product.isSeries == 1) {
//            if (ProductNums != 0 ) {
//                mPressent.addShopCard(MP_ID, ProductNums);
//            } else {
            SerialProducts();
//            }
        } else {
            mPressent.addShopCard(mpId, ProductNums);
        }

    }


    //推荐商品里面的点击回掉
    @Override
    public void itmeOnclik(String s) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://shoppingdetail");
//        activityRoute
//                .withParams(Constants.SP_ID, s)
//                .open();

        Bundle bd = new Bundle();
        bd.putString(Constants.SP_ID, s);
        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
    }

    /**
     * 分享
     */
    public void goToShare() {
        if (null != shareindow) {
            shareindow.dismiss();
            shareindow = null;
        }
        shareindow = new SharePopupWindow(getContext(), ShareType, Long.parseLong(mpId));
        shareindow.setIsShareDistribution(isShareDistribution);
        shareindow.setRefreshUIListener(this);
        shareindow.showAtLocation(mcoySnapPageLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 点击分享弹窗中刷新
     */
    @Override
    public void refreshUI() {
        ((ProductDetailActivity) getActivity()).cleaDada(1);//清楚数据
        downloadBaseInfo(mpId);
    }


    /*//加入购物车
    @Override
    public void addCart(String s, int i) {
        mPressent.addShopCard(s, ProductNums);
    }*/


    public interface IprodutDetailActivityCallback {
        void showTitle();

        void hideTitle();


        void changeFragment(int index);

        void getH5Url(String detailUrl, String standardUrl, String saleUrl);

        void layout_addshoppingsetEnabled(boolean b, int sale);

        void addShopCarCode(int productNums);

        void setpid(String mpId);

        void CarCode(long data);

        void setIfCollect(boolean ifcollect);

        void collectChecked(boolean ischeck, int type);

        void setDataSucceed(boolean state, int type);
    }

    //规格的回调
    Long stocknum;//这是一个当前库存数量


    @Override
    public void PropertyCallBack(ProductBean products, int num) {
        ProductNums = num;
        //有规格的时候
        //现在是采用重新请求的方式
        if (null != products && product.mpId != products.mpId) {
            downloadBaseInfo(products.mpId + "");//重新请求数据
            ((ProductDetailActivity) getActivity()).cleaDada(1);//清空数据请求
            IprodutDetailActivityCallback.setpid(products.mpId + "");
        } else {//如果是同一个商品  就只是更改数量
            standardDispose(product);
        }
    }

    @Override
    public void bayNow(ProductBean productNow, int productNumber) {
        if (null != productNow) {
            if (productNumber > productNow.stockNum) {
                ToastUtils.showToast(getContext().getString(R.string.surpass));
            } else {
                if (null != productNow && productNow.mpId != product.mpId) {
                    ((ProductDetailActivity) getActivity()).cleaDada(0);//清空数据请求
                    downloadBaseInfo(productNow.mpId + "");//重新请求数据
                    IprodutDetailActivityCallback.setpid(productNow.mpId + "");
                    product = productNow;
                    mpId = productNow.mpId + "";
                    toPay(productNumber + "");
                } else {//如果是同一个商品  就直接执行
                    toPay(productNumber + "");
                }
            }
        } else {
            if (productNumber > product.stockNum) {
                ToastUtils.showToast(getContext().getString(R.string.surpass));
            } else {
                toPay(productNumber + "");
            }
        }
    }

    @Override
    public void addToShopCart(ProductBean productNow, int productNumber) {
        if (null != productNow) {
            if (productNumber > productNow.stockNum) {
                ToastUtils.showToast(getContext().getString(R.string.surpass));
            } else {
                if (null != productNow && productNow.mpId != product.mpId) {
                    downloadBaseInfo(productNow.mpId + "");//重新请求数据
                    ((ProductDetailActivity) getActivity()).cleaDada(0);//清空数据请求
                    IprodutDetailActivityCallback.setpid(productNow.mpId + "");
                    mPressent.addShopCard(productNow.mpId + "", productNumber);
                } else {//如果是同一个商品  就只是更改数量
                    mPressent.addShopCard(mpId, productNumber);
                }
            }
        } else {
            if (productNumber > product.stockNum) {
                ToastUtils.showToast(getContext().getString(R.string.surpass));
            } else {
                mPressent.addShopCard(mpId, productNumber);
            }
        }
    }


    public void addShopCarCode() {
        if (propertywindow != null) {
            propertywindow.dismiss();
        }
        IprodutDetailActivityCallback.addShopCarCode(ProductNums);
    }

    //规格数据放入
    @Override
    public void standard(StandardBean bean) {
        StandardAdapter guigedeatilAdapter = new StandardAdapter(bean.getData(), getActivity());
        guige_list.setLayoutManager(new LinearLayoutManager(getContext()));
        guige_list.setAdapter(guigedeatilAdapter);
        guigedeatilAdapter.notifyDataSetChanged();
    }

    long count;//这是购物车加入的数量

    @Override
    //购物车数量的摄入
    public void cartNum(int data) {
        if (data == 0) {
            ((ProductDetailActivity) getActivity()).setCartnum(0);
            IprodutDetailActivityCallback.CarCode(0);
        } else {
            ((ProductDetailActivity) getActivity()).setCartnum(data);
            IprodutDetailActivityCallback.CarCode(data);
        }
        count = data;
    }

    //没有库存的时候
    @Override
    public void noHavePradut() {
        ToastUtils.showToast(getString(R.string.noprodut));
    }

    @Override
    public void loadingError(String erroro) {
        if (null != erroro && !"".equals(erroro)) {
            ToastUtils.showToast(erroro);
            if (null == IprodutDetailActivityCallback) {
                return;
            }
            if ("Note".equals(erroro)) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.webErro));
                IprodutDetailActivityCallback.setDataSucceed(false, 1);
            } else {
                IprodutDetailActivityCallback.setDataSucceed(false, 0);
            }
        } else {
            ToastUtils.showToast(getContext().getResources().getString(R.string.webErro));
        }
    }


    public void ontoch() {
        webView.setOnScrollChangedCallback(new ScrollWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy, double alpha) {
                showBtnTop();
            }

            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                showBtnTop();
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                hideBtnTop();
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

            }
        });
        //处理weBview滑动失去焦点
        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mcoySnapPageLayout.requestDisallowInterceptTouchEvent(false);
                } else {
                    int scroll = webView.getScrollY();
                    if (scroll == 0) {
                        mcoySnapPageLayout.requestDisallowInterceptTouchEvent(false);
                    } else {
                        mcoySnapPageLayout.requestDisallowInterceptTouchEvent(true);
                    }
                }
                return false;
            }


        });//H5页面

//轮播图滑倒到最后一张的时候切换到第二个也卡
        pager_banner.getViewpa().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //问题
                if (position + 1 == product.pics.size() + 2) {
                    IprodutDetailActivityCallback.changeFragment(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 显示置顶按钮
     */
    private void showBtnTop() {
        // TODO Auto-generated method stub
        if (img_totop_button.getVisibility() == View.GONE) {
            img_totop_button.setVisibility(View.VISIBLE);
            img_totop_button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_enter));
        }
    }

    /**
     * 隐藏置顶按钮
     */
    private void hideBtnTop() {
        // TODO Auto-generated method stub
        if (img_totop_button.getVisibility() == View.VISIBLE) {
            img_totop_button.setVisibility(View.GONE);
            img_totop_button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_exit));
        }
    }

    /*保存搜索纪录**/
    public void saveSearchData(ScanHistoryBean scanHistoryBean) {
        DatabaseHelper helper = DatabaseHelper.getHelper(getActivity());
        if (!StringUtils.isEmpty(scanHistoryBean.getSpId())) {
//            ToastUtils.sucessToast(getResources().getString(R.string.keynoempty));
            return;
        }
        if (hasData(scanHistoryBean.getSpId())) {//有同名的riCiName
            // delete from 表名 where name ='****'
            SQLiteDatabase db = helper.getReadableDatabase();
            db.delete("history_search", "spId =?", new String[]{scanHistoryBean.getSpId()});//名字拼接上",1"...type为1
        }
        try {
            helper = DatabaseHelper.getHelper(getActivity());
            //直接暂时拼接传进去,后面再做处理
            helper.getriciDao().create(scanHistoryBean);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void toPay(String number) {
        if (null != OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null) && !"".equals(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null))) {
            Bundle bd = new Bundle();
            bd.putBoolean(Constants.BUY_TYPE, true);
            bd.putString(Constants.SP_ID, mpId);
            bd.putString(Constants.CART_NUMBER, number);
            bd.putString(Constants.MERCHANT_ID, product.merchantId + "");
            JumpUtils.ToActivity(JumpUtils.CONFIRMORDER, bd);
           /* ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://confirmorder");
            activityRoute
                    .withParams(Constants.BUY_TYPE, true)
                    .withParams(Constants.SP_ID, MP_ID)
                    .withParams(Constants.CART_NUMBER, number)
                    .withParams(Constants.MERCHANT_ID, product.getMerchantId() + "")
                    .open();*/
        } else {
//            ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://login");
//            activityRoute.open();
            Bundle bd = new Bundle();
            bd.putString(Constants.LOSINGTAP, "100");
            JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
            LoGonType = 1;
        }
        if (propertywindow != null) {
            propertywindow.dismiss();
        }

    }


    /**
     * 检查数据库中是否已经有该条记录
     */
    public boolean hasData(String tempName) {
        String name = tempName + ",1";//名字是加上逗号和type为1来拼接的
        DatabaseHelper helper = DatabaseHelper.getHelper(getActivity());
        Cursor cursor = helper.getReadableDatabase().rawQuery("select _id as _id,spId from history_search where spId =?", new String[]{name});
        return cursor.moveToNext();
        //判断是否有下一个
    }


    //倒计时

    /**
     * 开启倒计时
     */
    public void startRun() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message message = Message.obtain();
                        message.what = 1;
                        timeHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                if (mHour < 10) {
                    hoursTv.setText("0" + mHour);
                } else {
                    hoursTv.setText(mHour + "");
                }
                if (mMin < 10) {
                    minutesTv.setText("0" + mMin);
                } else {
                    minutesTv.setText(mMin + "");
                }
                if (mSecond < 10) {
                    secondsTv.setText("0" + mSecond);
                } else {
                    secondsTv.setText(mSecond + "");
                }

                if (mDay == 0 && mHour == 0 && mMin == 0 && mSecond == 0) {
                    list_itemtime.setVisibility(View.GONE);
                }
            }
        }
    };

    /**
     * 倒计时计算
     */
    public void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                }
            }
        }
    }

    @Override
    public void deliveryAddress(AddressBean response) {

    }

    @Subscribe
    public void onEventMainThread(EventbusMessage event) {
        if (event.flag == EventbusMessage.GET_CART_COUNT) {
            mPressent.cartNum();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //预计送达时间
    @Override
    public void getDelivetyTime(String message) {

    }
}


