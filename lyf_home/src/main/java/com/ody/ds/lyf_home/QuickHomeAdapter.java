package com.ody.ds.lyf_home;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.retrofit.home.ModuleDataCategoryBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ScreenUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.FuncAdapter;
import com.ody.p2p.views.MyGridView;
import com.ody.p2p.views.ScrollGridView;
import com.ody.p2p.views.SquareImageView;
import com.ody.p2p.views.scrollbanner.LyfScrollBanner;
import com.ody.p2p.views.scrollbanner.ScrollBanner;
import com.ody.p2p.views.slidepager.AutoScrollViewPager;
import com.ody.p2p.views.slidepager.BannerBean;
import com.ody.p2p.views.slidepager.BannerPager;
import com.ody.p2p.views.slidepager.RecommendBannerPager;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ody.p2p.utils.PxUtils.dipTopx;
import static com.ody.p2p.views.slidepager.AutoScrollViewPager.SLIDE_BORDER_AWAYS_PARENT;
import static com.ody.p2p.views.slidepager.AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE;

/**
 * Created by lxs on 2017/1/13.
 */
public class QuickHomeAdapter extends BaseMultiItemQuickAdapter<AppHomePageBean.HomeData, BaseViewHolder> {

    public HomeCatergyPresenter homeImpl;
    public long mModuleId;
    public RecyclerView recycler_category;
    public int btnBuy;
    public List<FuncAdapter> funcAdapters;
    public int goodsIndex;
    public boolean isLoading;

    private CountDownTimer timer;
    private CountDownTimer timer2;

    OnRetryListener onRetryListener;
    //解决计时器消失的问题
    public long time = 1;


    public List<AppHomePageBean.StaticData.Channels> channel = new ArrayList<>();

    public QuickHomeAdapter(HomeCatergyPresenter homeImpl) {
        this(new ArrayList<AppHomePageBean.HomeData>(), homeImpl);
    }

    public QuickHomeAdapter(List<AppHomePageBean.HomeData> data, HomeCatergyPresenter homeImpl) {
        super(data);
        this.homeImpl = homeImpl;


    }

    public void putNewData(List<AppHomePageBean.HomeData> data) {

        AppHomePageBean.HomeData data1 = new AppHomePageBean.HomeData();
        data1.templateCode = "channel";
        AppHomePageBean.StaticData data2 = new AppHomePageBean.StaticData();
        AppHomePageBean.StaticData.Channels data3 = new AppHomePageBean.StaticData.Channels();
        data3.src = "http://cdn.oudianyun.com/lyf-local/trunk/back-cms/1504513405750_5530_92.png@base@tag=imgScale&q=80";
        data3.text = "haha";
        AppHomePageBean.StaticData.Channels data4 = new AppHomePageBean.StaticData.Channels();
        data4.src = "http://cdn.oudianyun.com/lyf-local/trunk/back-cms/1504513405750_5530_92.png@base@tag=imgScale&q=80";
        data4.text = "haha";
        AppHomePageBean.StaticData.Channels data5 = new AppHomePageBean.StaticData.Channels();
        data5.src = "http://cdn.oudianyun.com/lyf-local/trunk/back-cms/1504513405750_5530_92.png@base@tag=imgScale&q=80";
        data5.text = "haha";
        AppHomePageBean.StaticData.Channels data6 = new AppHomePageBean.StaticData.Channels();
        data6.src = "http://cdn.oudianyun.com/lyf-local/trunk/back-cms/1504513405750_5530_92.png@base@tag=imgScale&q=80";
        data6.text = "haha";
        AppHomePageBean.StaticData.Channels data7 = new AppHomePageBean.StaticData.Channels();
        data7.src = "http://cdn.oudianyun.com/lyf-local/trunk/back-cms/1504513405750_5530_92.png@base@tag=imgScale&q=80";
        data7.text = "haha";
        AppHomePageBean.StaticData.Channels data8 = new AppHomePageBean.StaticData.Channels();
        data8.src = "http://cdn.oudianyun.com/lyf-local/trunk/back-cms/1504513405750_5530_92.png@base@tag=imgScale&q=80";
        data8.text = "haha";

        channel.add(data3);
        channel.add(data4);
        channel.add(data5);
        channel.add(data6);
        channel.add(data7);
        //channel.add(data8);
        data2.channels = channel;
        data1.staticData = data2;
        data.add(1, data1);


    }

    private CategoryAdapter categoryAdapter;

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case AppHomePageBean.HomeData.TYPE_HEAD:
                viewHolder = new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_banner_pager, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_CHANNEL:
                viewHolder = new FuncViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_fun, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_H5:
                viewHolder = new PicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pic, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_SECKILL:
                viewHolder = new SecViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sec_kill, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_ENTRANCE:
                viewHolder = new TopicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_topic, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_RANK:
                viewHolder = new HotViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hot, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_PRODUCT:
                viewHolder = new CategoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category_home, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_GOODS:
                viewHolder = new GoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_NEWS:
                viewHolder = new HeadLineViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_headlines, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_PAVILION:
                viewHolder = new SubjectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_subject, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_SLIDER:
                viewHolder = new SliderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_slider, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_SPACE:
                viewHolder = new SpaceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_space, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_MF:
                viewHolder = new MFViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mf, parent, false));
                break;

            case AppHomePageBean.HomeData.TYPE_CN:
                viewHolder = new CNViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_channel, parent, false));
                break;
            case AppHomePageBean.HomeData.TYPE_SPEC_GOODS:
                viewHolder = new SpGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_special_goods, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final AppHomePageBean.HomeData homeData) {
        //baseViewHolder.setIsRecyclable(false);
        switch (baseViewHolder.getItemViewType()) {
            case AppHomePageBean.HomeData.TYPE_HEAD://轮播
                final BannerViewHolder holder0 = (BannerViewHolder) baseViewHolder;
                if (homeData.staticData != null && homeData.staticData.adList != null && homeData.staticData.adList.size() > 0) {
                    ArrayList<BannerBean> list = new ArrayList<>();
                    for (int i = 0; i < homeData.staticData.adList.size(); i++) {
                        BannerBean info = new BannerBean();
                        info.image = homeData.staticData.adList.get(i).imageUrl;
                        info.title = homeData.staticData.adList.get(i).name;
                        info.url = homeData.staticData.adList.get(i).linkUrl;
                        list.add(info);
                    }
                    if (list != null && list.size() > 0) {
                        holder0.page_banner.setIndicator(R.drawable.shape_des_banner_normal, R.drawable.shape_banner_selected);
                        holder0.page_banner.setEasyData(list);
                        holder0.page_banner.setAuto(false);
                        holder0.page_banner.setDuring(5000);
                        holder0.page_banner.setLooper(true);
                        holder0.page_banner.setIndicatorPosition(BannerPager.GRAVITY_RIGHT, dipTopx(20), 30, 20);
                        holder0.page_banner.setImageClickListener(new BannerPager.ImageClickLintener() {
                            @Override
                            public void click(int position) {
                                JumpUtils.toActivity(homeData.staticData.adList.get(position));
                            }
                        });
                    }
                    holder0.page_banner.setVisibility(View.VISIBLE);
                } else {
                    holder0.page_banner.setVisibility(View.GONE);
                }
                break;
            case AppHomePageBean.HomeData.TYPE_CHANNEL://专题
                FuncViewHolder holder1 = (FuncViewHolder) baseViewHolder;
                if (funcAdapters != null && funcAdapters.size() > 0 && holder1.banner_func.adapterses == null) {
                    holder1.banner_func.setVisibility(View.VISIBLE);
                    holder1.banner_func.setBackgroundColorId(1);
                    holder1.banner_func.setLayoutCount(5);
                    holder1.banner_func.setEasyData(funcAdapters);
                    holder1.banner_func.setAuto(false);
                    holder1.banner_func.setIndicatorPosition(BannerPager.GRAVITY_CENTER, dipTopx(4), 30, 0);
                    holder1.banner_func.setslideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
                    holder1.banner_func.setslidetouchMode(SLIDE_BORDER_AWAYS_PARENT);
                }
                break;
            case AppHomePageBean.HomeData.TYPE_NEWS://头条
                final HeadLineViewHolder holder2 = (HeadLineViewHolder) baseViewHolder;
                if (homeData.staticData != null && homeData.staticData.headLinesBean != null) {
                    holder2.sb_banner.setList(homeData.staticData.headLinesBean);
                    holder2.sb_banner.startScroll();
                    holder2.sb_banner.setMoreClickListener(new ScrollBanner.MoreClickListener() {
                        @Override
                        public void clickMore() {
                            if (homeData != null && homeData.staticData != null && homeData.staticData.headLinesBean != null && homeData.staticData.headLinesBean.data != null) {
                                JumpUtils.ToActivity(homeData.staticData.headLinesBean.data.categoryLink);
                            }
                        }
                    });
                }
                break;
            case AppHomePageBean.HomeData.TYPE_ENTRANCE://入口
                TopicViewHolder holder3 = (TopicViewHolder) baseViewHolder;
                if (homeData.staticData != null && homeData.staticData.adList != null && homeData.staticData.adList.size() > 0) {
                    TopicAdapter topicAdapter = new TopicAdapter(mContext, homeData.staticData.adList);
                    holder3.recycler_topic.setAdapter(topicAdapter);
                }
                break;
            case AppHomePageBean.HomeData.TYPE_SECKILL://秒杀
                SecViewHolder holder4 = (SecViewHolder) baseViewHolder;
                if (homeData.staticData != null) {
                    if (!StringUtils.isEmpty(homeData.staticData.image1) && !StringUtils.isEmpty(homeData.staticData.image2) && !StringUtils.isEmpty(homeData.staticData.image3) && !StringUtils.isEmpty(homeData.staticData.image4)) {
                        holder4.ll_item.setVisibility(View.GONE);//新版本隐藏秒杀
                        GlideUtil.display(mContext, homeData.staticData.image1).placeholder(R.drawable.icon_stub).centerCrop().dontAnimate().into(holder4.iv_sec1);
                        holder4.iv_sec1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (homeData.staticData.link1 != null) {
                                    JumpUtils.ToWebActivity(mContext, homeData.staticData.link1.data);
                                }
                            }
                        });
                        GlideUtil.display(mContext, homeData.staticData.image2).placeholder(R.drawable.icon_stub).centerCrop().dontAnimate().into(holder4.iv_sec2);
                        holder4.iv_sec2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (homeData.staticData.link2 != null) {
                                    JumpUtils.ToWebActivity(mContext, homeData.staticData.link2.data);
                                }
                            }
                        });
                        GlideUtil.display(mContext, homeData.staticData.image3).placeholder(R.drawable.icon_stub).centerCrop().dontAnimate().into(holder4.iv_sec3);
                        holder4.iv_sec3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (homeData.staticData.link3 != null) {
                                    JumpUtils.ToWebActivity(mContext, homeData.staticData.link3.data);
                                }
                            }
                        });
                        GlideUtil.display(mContext, homeData.staticData.image4).placeholder(R.drawable.icon_stub).centerCrop().dontAnimate().into(holder4.iv_sec4);
                        holder4.iv_sec4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (homeData.staticData.link4 != null) {
                                    JumpUtils.ToWebActivity(mContext, homeData.staticData.link4.data);
                                }
                            }
                        });
                    } else {
                        holder4.ll_item.setVisibility(View.GONE);
                    }
                }
                break;
            case AppHomePageBean.HomeData.TYPE_H5://大图
                PicViewHolder holder5 = (PicViewHolder) baseViewHolder;
                if (homeData.staticData != null && homeData.staticData.images != null && homeData.staticData.images.size() > 0) {
                    holder5.linear_home_pic.setVisibility(View.VISIBLE);
                    holder5.linear_home_pic.removeAllViews();
                    for (int i = 0; i < homeData.staticData.images.size(); i++) {
                        AppHomePageBean.Image imgs = homeData.staticData.images.get(i);
                        ImageView img = new ImageView(mContext);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        if (i == 0) {
                            params.setMargins(0, 0, 0, 0);
                        } else {
                            params.setMargins(0, dipTopx((int) homeData.staticData.margin / 2), 0, 0);
                        }
                        params.width = OdySysEnv.SCREEN_WIDTH;
                        if (imgs.oriHeight > 0 && imgs.oriWidth > 0) {
                            params.height = OdySysEnv.SCREEN_WIDTH * (int) imgs.oriHeight / (int) imgs.oriWidth;
                        } else {
                            params.width = 0;
                            params.height = 0;
                        }
                        Glide.with(mContext).load(GlideUtil.getUrl(mContext, imgs.src)).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).into(img);

                        holder5.linear_home_pic.addView(img, params);
                        final int finalI = i;
                        try {
                            img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (homeData.staticData.images.get(finalI).link != null) {
                                        JumpUtils.ToActivity(homeData.staticData.images.get(finalI).link.appData);
                                    }
                                }
                            });
                        } catch (Exception e) {

                        }

                    }
                } else {
                    holder5.linear_home_pic.setVisibility(View.GONE);
                }
                break;
            case AppHomePageBean.HomeData.TYPE_PAVILION://小轮播
                SubjectViewHolder holder6 = (SubjectViewHolder) baseViewHolder;
                if (homeData.staticData != null && homeData.staticData.items != null && homeData.staticData.items.size() > 0) {
                    SubjectAdapter subjectAdapter = new SubjectAdapter(mContext, homeData.staticData.items);
                    if (homeData.staticData.imgWidth > 0 && homeData.staticData.aspectRatio > 0) {
                        subjectAdapter.setImgWidth(homeData.staticData.imgWidth);
                        subjectAdapter.setAspectRatio(homeData.staticData.aspectRatio);
                    }
                    holder6.recycler_subject.setAdapter(subjectAdapter);
                    holder6.linear_item_subject.setVisibility(View.VISIBLE);
                } else {
                    holder6.linear_item_subject.setVisibility(View.GONE);
                }
                break;
            case AppHomePageBean.HomeData.TYPE_RANK://热销榜
                final HotViewHolder holder7 = (HotViewHolder) baseViewHolder;
                HotAdapter hotAdapter;
                holder7.item_img_home_rank_details.setVisibility(View.GONE);
                if (homeData.staticData != null && homeData.staticData.resultBean != null && homeData.staticData.resultBean.listObj != null && homeData.staticData.resultBean.listObj.size() > 0) {
                    holder7.recycler_hot.setVisibility(View.VISIBLE);
                    hotAdapter = new HotAdapter(mContext, homeData.staticData.resultBean.listObj, homeData.staticData);
                    holder7.recycler_hot.setAdapter(hotAdapter);
                } else {
                    if (null != homeData.staticData && homeData.staticData.displayNav == 1) {
                        holder7.item_img_home_rank_details.setVisibility(View.VISIBLE);
                        holder7.item_img_home_rank_details.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (null != homeData.staticData.link) {
                                    JumpUtils.ToActivity(homeData.staticData.link.appData);
                                }
                            }
                        });
                    } else {
                        holder7.item_img_home_rank_details.setVisibility(View.GONE);
                    }
                }
                break;
            case AppHomePageBean.HomeData.TYPE_PRODUCT:
                final CategoryViewHolder holderCategory = (CategoryViewHolder) baseViewHolder;
                if (homeData.staticData != null) {
                    recycler_category = holderCategory.recycler_cate;
                    btnBuy = homeData.staticData.displayBuyBtn;
                    if (homeData.staticData.displayNav == 0) {
                        recycler_category.setVisibility(View.GONE);
                    } else {
                        recycler_category.setVisibility(View.VISIBLE);
                        if (homeData.moduleData != null && homeData.moduleData.categoryList != null && homeData.moduleData.categoryList.size() > 1) {
                            if (categoryAdapter == null) {
                                categoryAdapter = new CategoryAdapter(mContext);
                                categoryAdapter.setDatas(homeData.moduleData.categoryList);
                                categoryAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, Object model) {
                                        if (!homeData.moduleData.categoryList.get(position).choose && !isLoading) {


                                            refreshCateList(homeData.moduleData.categoryList, position);
                                            categoryAdapter.notifyDataSetChanged();
                                            View leftView = recycler_category.getLayoutManager().getChildAt(0);
                                            homeData.moduleData.leftOffset = leftView.getLeft();
                                            homeData.moduleData.leftPosition = (recycler_category.getLayoutManager()).getPosition(leftView);
                                            homeImpl.getCategoryProduct(homeData.moduleId, homeData.moduleData.categoryList.get(position).categoryId, 1);
                                            EventMessage msg = new EventMessage();
                                            msg.flag = EventMessage.FRUSH_FILTER;
                                            msg.homeData = homeData;
                                            msg.leftOffset = homeData.moduleData.leftOffset;
                                            msg.leftPositon = homeData.moduleData.leftPosition;
                                            EventBus.getDefault().post(msg);

                                        }
                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position, Object model) {

                                    }
                                });

                                recycler_category.setAdapter(categoryAdapter);
                                EventMessage msg = new EventMessage();
                                msg.flag = EventMessage.FRUSH_FILTER;
                                msg.homeData = homeData;
                                msg.leftOffset = homeData.moduleData.leftOffset;
                                msg.leftPositon = homeData.moduleData.leftPosition;
                                EventBus.getDefault().post(msg);

                            }
                        } else {
                            recycler_category.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case AppHomePageBean.HomeData.TYPE_GOODS://商品
                mModuleId = homeData.moduleId;
                goodsIndex = getTypeIndex(AppHomePageBean.GOODS);
                GoodsViewHolder goodsViewHolder = (GoodsViewHolder) baseViewHolder;
                if (homeData.moduleData.moduleDataList != null && homeData.moduleData.moduleDataList.size() > 0) {
                    final ModuleDataBean.CmsModuleDataVO bean = homeData.moduleData.moduleDataList.get(0);
                    goodsViewHolder.tv_product_name.setText(bean.mpName);
                    goodsViewHolder.tv_product_cost.setText("¥" + bean.price);
                    if (StringUtils.isEmpty(bean.promotionPrice)) {
                        goodsViewHolder.tv_productcost_old.setVisibility(View.INVISIBLE);
                    } else {
                        goodsViewHolder.tv_productcost_old.setVisibility(View.VISIBLE);
                        goodsViewHolder.tv_product_cost.setText("¥" + bean.promotionPrice);
                        goodsViewHolder.tv_productcost_old.setText("¥" + UiUtils.getDoubleForDouble(bean.price));
                    }
                    String iconTexts = "";
                    if (bean.iconTexts != null && bean.iconTexts.size() > 0) {
                        for (int i = 0; i < bean.iconTexts.size(); i++) {
                            iconTexts = iconTexts + " " + bean.iconTexts.get(i);
                        }
                    }
                    goodsViewHolder.tv_product_activity.setText(iconTexts);
                    goodsViewHolder.tv_productcost_old.getPaint().setAntiAlias(true);//抗锯齿
                    goodsViewHolder.tv_productcost_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    GlideUtil.display(mContext, 300, bean.picUrl).into(goodsViewHolder.iv_product_pic);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) goodsViewHolder.rl_search_item.getLayoutParams();
                    if ((goodsViewHolder.getPosition() - goodsIndex) % 2 == 0) {
                        params.leftMargin = dipTopx(4) + 1;
                        params.rightMargin = dipTopx(15);
                    } else {
                        params.leftMargin = dipTopx(15);
                        params.rightMargin = dipTopx(4) + 1;
                    }
                    goodsViewHolder.rl_search_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bd = new Bundle();
                            bd.putString(Constants.SP_ID, bean.mpId);
                            JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
                        }
                    });
                    if (btnBuy == 0) {
                        goodsViewHolder.iv_addtocart.setVisibility(View.GONE);
                    } else {
                        goodsViewHolder.iv_addtocart.setVisibility(View.VISIBLE);
                        goodsViewHolder.iv_addtocart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addToCart(bean.mpId);
                            }
                        });
                    }
                }
                break;
            case AppHomePageBean.HomeData.TYPE_SLIDER://小轮播
                SliderViewHolder holder10 = (SliderViewHolder) baseViewHolder;
                if (homeData.staticData != null && homeData.staticData.images != null && homeData.staticData.images.size() > 0) {
                    int width = (int) homeData.staticData.images.get(0).oriWidth;
                    int height = (int) homeData.staticData.images.get(0).oriHeight;
                    ArrayList<BannerBean> list = new ArrayList<>();
                    for (int i = 0; i < homeData.staticData.images.size(); i++) {
                        BannerBean info = new BannerBean();
                        info.image = homeData.staticData.images.get(i).src;
                        info.title = homeData.staticData.images.get(i).desc;
                        if (homeData.staticData.images.get(i).link != null) {
                            info.url = homeData.staticData.images.get(i).link.data;
                        }
                        list.add(info);
                    }
                    if (list != null && list.size() > 0) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder10.banner_slider.getLayoutParams();
                        params.width = ScreenUtils.getScreenWidth(mContext);
                        if (width > 0) {
                            params.height = (int) (ScreenUtils.getScreenWidth(mContext) * (height * 1.0f / width));
                        } else {
                            params.height = 0;
                        }
                        holder10.banner_slider.setLayoutParams(params);
                        holder10.banner_slider.setIndicator(R.drawable.shape_des_banner_normal, R.drawable.shape_banner_selected);
                        holder10.banner_slider.setEasyData(list);
                        holder10.banner_slider.setAuto(true);
                        holder10.banner_slider.setDuring(5000);
                        holder10.banner_slider.setLooper(true);
                        holder10.banner_slider.setImageClickListener(new BannerPager.ImageClickLintener() {
                            @Override
                            public void click(int position) {
                                if (homeData.staticData.images.get(position).link != null) {
                                    if (homeData.staticData.images.get(position).link.data != null && homeData.staticData.images.get(position).link.data.contains("zhibo")) {
                                        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                                            try {
                                                JumpUtils.ToWebActivity(mContext, homeData.staticData.images.get(position).link.data + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, "") + "&avatar=" + URLEncoder.encode(OdyApplication.getString(Constants.HEAD_PIC_URL, ""), "UTF-8") + "&ut=" + OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
                                            } catch (UnsupportedEncodingException e) {
                                                JumpUtils.ToWebActivity(mContext, homeData.staticData.images.get(position).link.data + "&nickname=" + OdyApplication.getString(Constants.NICK_NAME, "") + "&mobile=" + OdyApplication.getString(Constants.LOGIN_MOBILE_PHONE, "") + "&ut=" + OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
                                                e.printStackTrace();
                                            }
                                        } else {
                                            JumpUtils.ToActivity(JumpUtils.LOGIN);
                                        }
                                    } else {
                                        JumpUtils.ToWebActivity(mContext, homeData.staticData.images.get(position).link.data);
                                    }
                                }
                            }
                        });
                    }
                    holder10.banner_slider.setVisibility(View.VISIBLE);
                } else {
                    holder10.banner_slider.setVisibility(View.GONE);
                }
                break;
            case AppHomePageBean.HomeData.TYPE_SPACE:
                SpaceViewHolder holder11 = (SpaceViewHolder) baseViewHolder;
                ViewGroup.LayoutParams params = holder11.space.getLayoutParams();
                if (homeData.staticData != null && homeData.staticData.height > 0) {
                    params.height = dipTopx((int) homeData.staticData.height * 10 / 2) / 10;
                    holder11.space.setLayoutParams(params);
                    holder11.space.setBackgroundColor(Color.parseColor(homeData.staticData.color));
                }
                break;
            case AppHomePageBean.HomeData.TYPE_MF:
                final MFViewHolder holder12 = (MFViewHolder) baseViewHolder;


                if (homeData.staticData != null && homeData.staticData.cdata != null && homeData.staticData.cdata.appLayout != null) {

                    holder12.ll_mf.removeAllViews();


                    View view = null;
                    if (homeData.staticData.cdata.appLayout.equals("0") || homeData.staticData.cdata.appLayout.equals("1") || homeData.staticData.cdata.appLayout.equals("2") || homeData.staticData.cdata.appLayout.equals("3") || homeData.staticData.cdata.appLayout.equals("6") || homeData.staticData.cdata.appLayout.equals("7")) {
                        view = getView(R.layout.item_0);
                    } else if (homeData.staticData.cdata.appLayout.equals("9") || homeData.staticData.cdata.appLayout.equals("4")) {
                        view = getView(R.layout.item_9);
                    } else if (homeData.staticData.cdata.appLayout.equals("5") || homeData.staticData.cdata.appLayout.equals("8")) {
                        view = getView(R.layout.item_2);
                    } else if (homeData.staticData.cdata.appLayout.equals("10")) {
                        view = getView(R.layout.item_10);
                    }
                    if (homeData.staticData.cdata != null && homeData.staticData.cdata.children != null) {
                        if (view == null) {
                            return;
                        }
                        if (homeData.staticData.cdata.children.size() > 0) {
                            ImageView iv_00 = (ImageView) view.findViewById(R.id.iv_00);
                            setImageData(iv_00, homeData.staticData.cdata.children.get(0));
                        }
                        if (homeData.staticData.cdata.children.size() > 1) {
                            ImageView iv_01 = (ImageView) view.findViewById(R.id.iv_01);
                            setImageData(iv_01, homeData.staticData.cdata.children.get(1));
                        }
                        if (homeData.staticData.cdata.children.size() > 2) {
                            ImageView iv_02 = (ImageView) view.findViewById(R.id.iv_02);
                            setImageData(iv_02, homeData.staticData.cdata.children.get(2));
                        }
                        if (homeData.staticData.cdata.children.size() > 3) {
                            ImageView iv_03 = (ImageView) view.findViewById(R.id.iv_03);
                            setImageData(iv_03, homeData.staticData.cdata.children.get(3));
                        }
                        if (homeData.staticData.cdata.children.size() > 4) {
                            ImageView iv_04 = (ImageView) view.findViewById(R.id.iv_04);
                            setImageData(iv_04, homeData.staticData.cdata.children.get(4));
                        }
                        if (homeData.staticData.cdata.children.size() > 5) {
                            ImageView iv_05 = (ImageView) view.findViewById(R.id.iv_05);
                            setImageData(iv_05, homeData.staticData.cdata.children.get(5));
                        }
                        if (homeData.staticData.cdata.children.size() > 6) {
                            ImageView iv_06 = (ImageView) view.findViewById(R.id.iv_06);
                            setImageData(iv_06, homeData.staticData.cdata.children.get(6));
                        }
                        if (homeData.staticData.cdata.children.size() > 7) {
                            ImageView iv_07 = (ImageView) view.findViewById(R.id.iv_07);
                            setImageData(iv_07, homeData.staticData.cdata.children.get(7));
                        }
                        if (homeData.staticData.cdata.children.size() > 8) {
                            ImageView iv_08 = (ImageView) view.findViewById(R.id.iv_08);
                            setImageData(iv_08, homeData.staticData.cdata.children.get(8));
                        }
                        if (homeData.staticData.cdata.children.size() > 9) {
                            ImageView iv_09 = (ImageView) view.findViewById(R.id.iv_09);
                            setImageData(iv_09, homeData.staticData.cdata.children.get(9));
                        }
                        try {

                            if (homeData.staticData.cdata.children.get(0).purchase == 1) {
                                if (timer != null) {
                                    // holder12.tx_count.setText(getTimeShort(0));
                                    timer.cancel();
                                    timer = null;
                                }
                                holder12.rl_left.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(homeData.staticData.cdata.children.get(0).startTime)) {
                                    holder12.tx_start_time.setText("下一场" + homeData.staticData.cdata.children.get(0).startTime + "开始");
                                }


                                if (!TextUtils.isEmpty(homeData.staticData.cdata.children.get(0).countTime)) {
                                    long countTime = 0;


                                    if (time == 1) {

                                        countTime = Long.parseLong(homeData.staticData.cdata.children.get(0).countTime);
                                    } else {

                                        countTime = time;
                                    }
                                    timer = new CountDownTimer(countTime, 1000) {

                                        public void onTick(final long millisUntilFinished) {
                                            time = millisUntilFinished;
                                            // holder12.tx_count.setText(getTimeShort(millisUntilFinished / 1000));
                                            if(holder12 != null && holder12.tx_count_ll1!= null){
                                                holder12.tx_count_ll1.setText(getTimeShort(millisUntilFinished / 1000).split(":")[0]);
                                                holder12.tx_count_ll2.setText(getTimeShort(millisUntilFinished / 1000).split(":")[1]);
                                                holder12.tx_count_ll3.setText(getTimeShort(millisUntilFinished / 1000).split(":")[2]);
                                            }
                                        }

                                        public void onFinish() {
                                            holder12.tx_count_ll1.setText(getTimeShort(0).split(":")[0]);
                                            holder12.tx_count_ll2.setText(getTimeShort(0).split(":")[0]);
                                            holder12.tx_count_ll3.setText(getTimeShort(0).split(":")[0]);
                                            timer = null;
                                            time = 1;
                                            holder12.tx_count_ll1.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (onRetryListener != null) {
                                                        onRetryListener.retryCountTime();
                                                    }
                                                }
                                            }, 200);

                                        }
                                    };
                                    timer.start();


                                }

                            } else {

                                holder12.rl_left.setVisibility(View.GONE);
                            }
                            if (homeData.staticData.cdata.children.get(1).purchase == 1) {
                                holder12.rl_right.setVisibility(View.VISIBLE);
                                if (!TextUtils.isEmpty(homeData.staticData.cdata.children.get(0).startTime)) {
                                    holder12.tx_start_time2.setText("下一场" + homeData.staticData.cdata.children.get(0).startTime + "开始");
                                }
                                if (!TextUtils.isEmpty(homeData.staticData.cdata.children.get(0).countTime)) {
                                    if (timer2 == null) {
                                        timer2 = new CountDownTimer(Long.parseLong(homeData.staticData.cdata.children.get(0).countTime), 1000) {
                                            public void onTick(long millisUntilFinished) {


                                                //ToastUtils.showToast(getTimeShort(millisUntilFinished / 1000));
                                                //button.setText(millisUntilFinished / 1000 + "秒");

                                                // mAdapter.notifyDataSetChanged();
                                                holder12.tx_count2.setText(getTimeShort(millisUntilFinished / 1000));
                                            }

                                            public void onFinish() {
                                                holder12.tx_count2.setText(getTimeShort(0));
                                                timer2 = null;

                                                holder12.tx_count2.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (onRetryListener != null) {
                                                            onRetryListener.retryCountTime();
                                                        }
                                                    }
                                                }, 200);

                                            }
                                        };
                                        timer2.start();
                                    }

                                }
                            } else {
                                holder12.rl_right.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {

                        }

                    }
                    holder12.ll_mf.addView(view);


                }
                break;


            case AppHomePageBean.HomeData.TYPE_CN:
                CNViewHolder cnHolder = (CNViewHolder) baseViewHolder;

                if (homeData.staticData != null && homeData.staticData.channels != null) {

                    cnHolder.recycler_channel.setLayoutManager(new GridLayoutManager(mContext, homeData.staticData.channels.size()));
                    //manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    ChannelAdapter channelAdapter = new ChannelAdapter(mContext, homeData.staticData.channels);
                    cnHolder.recycler_channel.setAdapter(channelAdapter);
                }
                break;
            //三列的数据
            case AppHomePageBean.HomeData.TYPE_SPEC_GOODS:

                SpGoodsViewHolder spHolder = (SpGoodsViewHolder) baseViewHolder;
                try {
                    if (homeData.moduleData.spModuleDataList != null && homeData.moduleData.spModuleDataList.size() > 0) {
                        spHolder.recycler_special_goods.setLayoutManager(new GridLayoutManager(mContext, 3));
                        spHolder.recycler_special_goods.setAdapter(new SpecGoodsAdapter(mContext, homeData.moduleData.spModuleDataList));
                    }
                } catch (Exception e) {

                }

                break;

        }
    }

    /**
     * 更新商品筛选栏位置
     *
     * @param lastOffset
     */

    public void scrollFilter(int lastPosition, int lastOffset) {
        if (recycler_category != null) {
            ((LinearLayoutManager) recycler_category.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    //轮播
    public static class BannerViewHolder extends BaseViewHolder {

        public BannerPager page_banner;
        public LinearLayout ll_item;

        public BannerViewHolder(View view) {
            super(view);
            ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
            page_banner = (BannerPager) view.findViewById(R.id.page_banner);
            page_banner.setslideBorderMode(SLIDE_BORDER_MODE_CYCLE);
            page_banner.setslidetouchMode(SLIDE_BORDER_AWAYS_PARENT);
        }
    }

    //专题
    public static class FuncViewHolder extends BaseViewHolder {
        public RecommendBannerPager banner_func;

        public FuncViewHolder(View view) {
            super(view);
            banner_func = (RecommendBannerPager) view.findViewById(R.id.ban_func);
        }
    }

    //头条
    public static class HeadLineViewHolder extends BaseViewHolder {

        public LyfScrollBanner sb_banner;

        public HeadLineViewHolder(View view) {
            super(view);
            sb_banner = (LyfScrollBanner) view.findViewById(R.id.sb_banner);
        }
    }

    //秒杀
    public static class SecViewHolder extends BaseViewHolder {

        public TextView tv_sectitle1;
        public TextView tv_seccontent1;
        public ImageView iv_sec1;

        public TextView tv_sectitle2;
        public TextView tv_seccontent2;
        public ImageView iv_sec2;

        public TextView tv_sectitle3;
        public TextView tv_seccontent3;
        public ImageView iv_sec3;

        public TextView tv_sectitle4;
        public TextView tv_seccontent4;
        public ImageView iv_sec4;

        public LinearLayout ll_item;

        public SecViewHolder(View view) {
            super(view);
            tv_sectitle1 = (TextView) view.findViewById(R.id.tv_sectitle1);
            tv_seccontent1 = (TextView) view.findViewById(R.id.tv_seccontent1);
            iv_sec1 = (ImageView) view.findViewById(R.id.iv_sec1);

            tv_sectitle2 = (TextView) view.findViewById(R.id.tv_sectitle2);
            tv_seccontent2 = (TextView) view.findViewById(R.id.tv_seccontent3);
            iv_sec2 = (ImageView) view.findViewById(R.id.iv_sec2);

            tv_sectitle3 = (TextView) view.findViewById(R.id.tv_sectitle3);
            tv_seccontent3 = (TextView) view.findViewById(R.id.tv_seccontent3);
            iv_sec3 = (ImageView) view.findViewById(R.id.iv_sec3);

            tv_sectitle4 = (TextView) view.findViewById(R.id.tv_sectitle4);
            tv_seccontent4 = (TextView) view.findViewById(R.id.tv_seccontent4);
            iv_sec4 = (ImageView) view.findViewById(R.id.iv_sec4);

            ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        }
    }


    public static class HotViewHolder extends BaseViewHolder {

        public RecyclerView recycler_hot;
        ImageView item_img_home_rank_details;

        public HotViewHolder(View view) {
            super(view);
            item_img_home_rank_details = (ImageView) view.findViewById(R.id.item_img_home_rank_details);
            recycler_hot = (RecyclerView) view.findViewById(R.id.recycler_hot);
            recycler_hot.addItemDecoration(new HotSpace(1));
            LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycler_hot.setLayoutManager(manager);
        }
    }

    public static class SubjectViewHolder extends BaseViewHolder {

        public LinearLayout linear_item_subject;
        public RecyclerView recycler_subject;

        public SubjectViewHolder(View view) {
            super(view);
            linear_item_subject = (LinearLayout) view.findViewById(R.id.linear_item_subject);
            recycler_subject = (RecyclerView) view.findViewById(R.id.recycler_subject);
            recycler_subject.addItemDecoration(new HotSpace(5));
            LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycler_subject.setLayoutManager(manager);
        }
    }

    public static class ProductViewHolder extends BaseViewHolder {

        public ScrollGridView productLv;
        public RecyclerView recycler_cate;

        public ProductViewHolder(View view) {
            super(view);
            recycler_cate = (RecyclerView) view.findViewById(R.id.recycler_category);
            productLv = (ScrollGridView) view.findViewById(R.id.lv_product);
            LinearLayoutManager categoryManger = new LinearLayoutManager(view.getContext());
            categoryManger.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycler_cate.setLayoutManager(categoryManger);
        }
    }

    public static class CategoryViewHolder extends BaseViewHolder {
        public RecyclerView recycler_cate;

        public CategoryViewHolder(View view) {
            super(view);
            recycler_cate = (RecyclerView) view.findViewById(R.id.recycler_cate);
            LinearLayoutManager categoryManger = new LinearLayoutManager(view.getContext());
            categoryManger.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycler_cate.setLayoutManager(categoryManger);
        }
    }

    public static class GoodsViewHolder extends BaseViewHolder {
        public RelativeLayout rl_search_item;
        public SquareImageView iv_product_pic;
        public TextView tv_product_activity;
        public TextView tv_product_name;
        public RecyclerView recycler_promotion;
        public TextView tv_product_cost;
        public TextView tv_product_sold;
        public TextView tv_productcost_old;
        public ImageView iv_addtocart;

        public GoodsViewHolder(View view) {
            super(view);
            rl_search_item = (RelativeLayout) view.findViewById(R.id.rl_search_item);
            iv_product_pic = (SquareImageView) view.findViewById(R.id.iv_product_pic);
            tv_product_activity = (TextView) view.findViewById(R.id.tv_product_activity);
            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_product_cost = (TextView) view.findViewById(R.id.tv_product_cost);
            tv_productcost_old = (TextView) view.findViewById(R.id.tv_productcost_old);
            iv_addtocart = (ImageView) view.findViewById(R.id.iv_addtocart);
        }

    }

    public static class TopicViewHolder extends BaseViewHolder {

        public MyGridView recycler_topic;

        public TopicViewHolder(View view) {
            super(view);
            recycler_topic = (MyGridView) view.findViewById(R.id.recycler_topic);
        }
    }

    public static class PicViewHolder extends BaseViewHolder {

        public LinearLayout linear_home_pic;

        public PicViewHolder(View view) {
            super(view);
            linear_home_pic = (LinearLayout) view.findViewById(R.id.linear_home_pic);
        }
    }

    public static class SliderViewHolder extends BaseViewHolder {

        public BannerPager banner_slider;

        public SliderViewHolder(View view) {
            super(view);
            banner_slider = (BannerPager) view.findViewById(R.id.banner_slider);
            banner_slider.setslideBorderMode(SLIDE_BORDER_MODE_CYCLE);
            banner_slider.setslidetouchMode(SLIDE_BORDER_AWAYS_PARENT);
            banner_slider.setIndicatorPosition(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, UiUtils.dip2px(OdyApplication.gainContext(), 5), 0, 0);
        }
    }

    public static class SpaceViewHolder extends BaseViewHolder {
        private View space;

        public SpaceViewHolder(View view) {
            super(view);
            space = view.findViewById(R.id.space);
        }
    }

    public class MFViewHolder extends BaseViewHolder {


        public LinearLayout ll_mf;

        public RelativeLayout rl_left;

        public TextView tx_count;

        public TextView tx_start_time;
        public TextView tx_count_ll1;
        public TextView tx_count_ll2;
        public TextView tx_count_ll3;


        public RelativeLayout rl_right;

        public TextView tx_count2;

        public TextView tx_start_time2;


        public MFViewHolder(View view) {
            super(view);
            ll_mf = (LinearLayout) view.findViewById(R.id.ll_mf);

            rl_left = (RelativeLayout) view.findViewById(R.id.ll_left);
            tx_start_time = (TextView) view.findViewById(R.id.tx_start_time);
            tx_count_ll1 = (TextView) view.findViewById(R.id.tx_count_ll1);
            tx_count_ll2 = (TextView) view.findViewById(R.id.tx_count_ll2);
            tx_count_ll3 = (TextView) view.findViewById(R.id.tx_count_ll3);


            rl_right = (RelativeLayout) view.findViewById(R.id.ll_right);
            tx_count2 = (TextView) view.findViewById(R.id.tx_count2);
            tx_start_time2 = (TextView) view.findViewById(R.id.tx_start_time2);


        }

    }

    public static class CNViewHolder extends BaseViewHolder {
        public RecyclerView recycler_channel;

        public CNViewHolder(View view) {
            super(view);
            recycler_channel = (RecyclerView) view.findViewById(R.id.recycler_channel);

        }

    }

    public static class SpGoodsViewHolder extends BaseViewHolder {
        public RecyclerView recycler_special_goods;

        public SpGoodsViewHolder(View view) {
            super(view);
            recycler_special_goods = (RecyclerView) view.findViewById(R.id.recycler_special_goods);


        }

    }

    public void refreshCateList(List<ModuleDataCategoryBean.CategoryBean> cateList,
                                int position) {
        for (int i = 0; i < cateList.size(); i++) {
            if (position == i) {
                cateList.get(i).choose = true;
            } else {
                cateList.get(i).choose = false;
            }
        }
    }

    public int getPosition(List<ModuleDataCategoryBean.CategoryBean> cateList) {
        int po = -1;
        for (int i = 0; i < cateList.size(); i++) {
            if (cateList.get(i).choose) {
                po = i;
                break;
            }
        }
        return po;
    }

    public void addToCart(String mpId) {
        Map<String, String> params = new HashMap<>();
        params.put("mpId", mpId);
        params.put("num", "1");
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT, "");
        params.put("ut", ut);
        params.put("sessionId", OdySysEnv.getSessionId());
        OkHttpManager.getAsyn(Constants.ADD_TO_CART, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showShort(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null) {
                    ToastUtils.showShort("添加成功");
                    EventbusMessage msg = new EventbusMessage();
                    msg.flag = EventbusMessage.GET_CART_COUNT;
                    EventBus.getDefault().post(msg);
                }
            }
        });
    }

    /**
     * 根据类型获取 index
     *
     * @param templateCode
     * @return
     */
    public int getTypeIndex(String templateCode) {
        int index = -1;
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).templateCode.equals(templateCode)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * 清除goods商品
     */
    public void cleanProduct() {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).templateCode.equals(AppHomePageBean.PRODUCT)) {
                mData.remove(i);
                i--;
            }
        }
    }

    public void refreshCategoryAdapter(int position) {
        if (categoryAdapter != null) {
            refreshCateList(categoryAdapter.getDatas(), position);
            categoryAdapter.notifyDataSetChanged();
        }
    }


    private View getView(int viewId) {
        return LayoutInflater.from(mContext).inflate(viewId, new RelativeLayout(mContext));
    }

    public void setImageData(ImageView imageView, final AppHomePageBean.Children children) {
        try {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            double width = Double.parseDouble(children.width.replace("%", ""));
            layoutParams.width = (int) ((ScreenUtils.getScreenWidth(mContext) * width) / 100 + 0.5f);
            layoutParams.height = (int) (layoutParams.width * children.appHeight / children.appWidth + 0.5f);
            GlideUtil.display(mContext, 300, children.imgUrl).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (children.link != null && children.link.appData != null) {
                        JumpUtils.ToActivity(children.link.appData);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    public String getTimeShort(long i) {

        long h = i / 3600;
        long m = (i % 3600) / 60;
        long s = (i % 3600) % 60;


        String dateString = "0" + h + ":" + m + ":" + s;

        if (m < 10) {
            dateString = "0" + h + ":" + "0" + m + ":" + s;
        }
        if (s < 10) {
            dateString = "0" + h + ":" + m + ":" + "0" + s;
        }
        if (m < 10 && s < 10) {

            dateString = "0" + h + ":" + "0" + m + ":" + "0" + s;
        }

        return dateString;
    }

    interface OnRetryListener {
        void retryCountTime();
    }

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        this.onRetryListener = onRetryListener;
    }


}
