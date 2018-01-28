package com.lyfen.android.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.laiyifen.lyfframework.listadapter.DefaultAdapter;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.lyfen.android.entity.network.product.ProductRecommendEntity;
import com.lyfen.android.utils.UIUtils;
import com.ody.ds.lyf_home.SlideDetailsLayout;
import com.ody.p2p.Constants;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by qj on 2017/6/8.
 */

public class ProductRecommendHolder extends BaseHolder<List<ProductRecommendEntity.DataEntity.DataListEntity>> {
    @Bind(R.id.banner_grid)
    ConvenientBanner mBannerGrid;

    @Bind(R.id.text_otherrecommend)
    TextView mTextOtherrecommend;
    @Bind(R.id.ll_tuijian)
    LinearLayout mLlTuijian;

    int group;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    private boolean showBottom;


    @Override
    protected View initView() {
        View inflate = View.inflate(UIUtils.getContext(), R.layout.layout_product_recommend, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void refreshView() {

        try {

            Field point = mBannerGrid.getClass().getDeclaredField("loPageTurningPoint");
            point.setAccessible(true);
            ViewGroup linear = (ViewGroup) point.get(mBannerGrid);
            linear.setBackgroundResource(R.drawable.viewpager_item_background);
        } catch (Exception e) {
            Log.i("ProductRecommendHolder", e.getMessage());
        }


        List<ProductRecommendEntity.DataEntity.DataListEntity> data = getData();


        ViewGroup.LayoutParams linearParams = mBannerGrid.getLayoutParams();
        if (data.size() > 4) {//推荐商品不满两个展示一行
            group = 4;
            linearParams.height = UIUtils.dip2px(540);
        } else {
            group = 2;
            linearParams.height = UIUtils.dip2px(280);

        }

        mBannerGrid.setLayoutParams(linearParams);


        List<List<ProductRecommendEntity.DataEntity.DataListEntity>> handledData = handleRecommendGoods(data, group);


        mBannerGrid.setManualPageable(handledData.size() == 1 ? false : true);

        mBannerGrid.setCanLoop(handledData.size() == 1 ? false : true);
        mBannerGrid.setPages(new CBViewHolderCreator<BannerHolder>() {
            @Override
            public BannerHolder createHolder() {
                return new BannerHolder();
            }
        }, handledData).setPageIndicator(new int[]{R.drawable.shap_point_grey, R.drawable.shap_point_yellow})

                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
        mBannerGrid.setCanLoop(true);
        llBottom.setVisibility(showBottom ? View.VISIBLE : View.GONE);

//        mBannerGrid.setLayoutCount(2);
//        mBannerGrid.setEasyData(listRe);
//        mBannerGrid.setAuto(false);
//
//
//        mBannerGrid.setIndicatorPosition(BannerPager.GRAVITY_CENTER, UIUtils.dip2px(18), 30, 0);
//        mBannerGrid.setslideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
//        mBannerGrid.setslidetouchMode(AutoScrollViewPager.SLIDE_BORDER_AWAYS_PARENT);
//
//
//        mBannerGrid.linear.setBackgroundResource(R.drawable.viewpager_item_background);
//        mBannerGrid.linear.setPadding(UIUtils.dip2px(15), 0, UIUtils.dip2px(15), 0);
//        mBannerGrid.setIndicatorPosition(UIUtils.dip2px(28));
//        mBannerGrid.linear.setGravity(Gravity.CENTER_VERTICAL);


//        mBannerGrid.setIndicatorPosition(UIUtils.dip2px(28));
////        mBannerGrid.linear.setBackgroundResource(R.drawable.viewpager_item_background);
//        mBannerGrid.linear.setBackgroundResource(R.color.cf0f0f0);
//        mBannerGrid.viewpa.setBackgroundResource(R.color.cf0f0f0);
//        mBannerGrid.linear.setPadding(UIUtils.dip2px(15), 0, UIUtils.dip2px(15), 0);

    }

    private List<List<ProductRecommendEntity.DataEntity.DataListEntity>> handleRecommendGoods(List<ProductRecommendEntity.DataEntity.DataListEntity> data, int group) {

        List<List<ProductRecommendEntity.DataEntity.DataListEntity>> handleData = new ArrayList<>();
        int length = data.size() / group;
        if (data.size() % group != 0) {
            length = data.size() / group + 1;
        }
        for (int i = 0; i < length; i++) {
            List<ProductRecommendEntity.DataEntity.DataListEntity> recommendGoods = new ArrayList<>();
            for (int j = 0; j < (i * group + j == data.size() ? 1 : group); j++) {
                recommendGoods.add(data.get(i * group + j));
            }
            handleData.add(recommendGoods);
        }
        return handleData;

    }

    public void setStarus(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            mTextOtherrecommend.setText("下拉查看商品信息");


        } else {
            mTextOtherrecommend.setText("上拉查看图文详情");

        }
    }

    public void setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
    }

    public class BannerHolder implements Holder<List<ProductRecommendEntity.DataEntity.DataListEntity>> {
        private View rootview;
        private GridView gv_recommend_goods;
//        RefreshRecyclerView mRefreshRecyclerView;

        @Override
        public View createView(Context context) {
//            View inflate = UIUtils.inflate(R.layout.refresh_recyclerview);
//            mRefreshRecyclerView = (RefreshRecyclerView) inflate.findViewById(R.id.id_recyclerview);
            rootview = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.lyf_item_recommend, null);
            gv_recommend_goods = (GridView) rootview.findViewById(R.id.gv_recommend_goods);
            return rootview;
        }

        @Override
        public void UpdateUI(Context context, int position, List<ProductRecommendEntity.DataEntity.DataListEntity> data) {

//
//            ProductEcommendAdapter productEcommendAdapter = new ProductEcommendAdapter(data);
//
//
//            RecyclerViewManager.with(productEcommendAdapter, new GridLayoutManager(context, 2))
//                    .setMode(RecyclerMode.NONE).into(mRefreshRecyclerView, context);

            gv_recommend_goods.setAdapter(new DefaultAdapter<ProductRecommendEntity.DataEntity.DataListEntity>(gv_recommend_goods, data) {
                @Override
                protected BaseHolder<ProductRecommendEntity.DataEntity.DataListEntity> getHolder() {
                    return new BaseHolder<ProductRecommendEntity.DataEntity.DataListEntity>() {

                        SimpleDraweeView mImgRecommend;
                        TextView mTvRecommedName;

                        TextView mTvRecommendPrice;
                        TextView mTvSourceprice;

                        TextView mTvEvaluateRate;
                        ImageView mImgAddcart;
                        LinearLayout mLinerView;


                        @Override
                        protected View initView() {

                            View inflate = View.inflate(UIUtils.getContext(), R.layout.item_recommend_adapter, null);
                            mImgRecommend = (SimpleDraweeView) inflate.findViewById(R.id.img_recommend);
                            mTvRecommedName = (TextView) inflate.findViewById(R.id.tv_recommed_name);
                            mTvRecommendPrice = (TextView) inflate.findViewById(R.id.tv_recommend_price);
                            mTvSourceprice = (TextView) inflate.findViewById(R.id.tv_sourceprice);
                            mTvEvaluateRate = (TextView) inflate.findViewById(R.id.tv_evaluate_rate);
                            mImgAddcart = (ImageView) inflate.findViewById(R.id.img_addcart);
                            mLinerView = (LinearLayout) inflate.findViewById(R.id.liner_view);
                            return inflate;
                        }

                        @Override
                        public void refreshView() {
                            ProductRecommendEntity.DataEntity.DataListEntity prdata = getData();


                            mTvRecommedName.setText(prdata.mpName);
                            FrescoUtils.displayUrl(mImgRecommend, prdata.srcImgUrl);
                            mTvRecommendPrice.setText("¥" + prdata.salePrice);

                            if (!TextUtils.isEmpty(prdata.sourcePrice + "") && prdata.sourcePrice > 0) {
                                mTvSourceprice.setVisibility(View.VISIBLE);
                                mTvSourceprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                                mTvSourceprice.setText("¥" + prdata.sourcePrice);
                            } else {
                                mTvSourceprice.setVisibility(View.GONE);
                            }
                            if (null != prdata && null != prdata.commentInfo) {
                                String evaluate = prdata.commentInfo.commentNum + "<font color='#808080'>条评论  好评</font>" + prdata.commentInfo.goodRate + "%";
                                mTvEvaluateRate.setText(Html.fromHtml(evaluate));
                            } else {
                                String evaluate = 0 + "<font color='#333'>条评论  好评</font>" + 0 + "%";
                                mTvEvaluateRate.setText(Html.fromHtml(evaluate));
                            }
//            //促销标签
//            if (null != prdata.getTagList() && prdata.getTagList().size() > 0) {
//                List<PromotionInfo> promotionInfos = new ArrayList<>();
//                for (Recommedbean.TagList list : prdata.getTagList()) {
//                    PromotionInfo pInfo = new PromotionInfo();
//                    pInfo.setIconUrl(list.getTagUrl());
//                    promotionInfos.add(pInfo);
//                }
//                PromotionAdapter promotionAdapter = new PromotionAdapter(mContext, promotionInfos);
//                promotionAdapter.setGapDistance(6, 0, 0);//设置间距、宽、高
//                LinearLayoutManager rankLayoutManager = new LinearLayoutManager(mContext);
//                rankLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                vHolder.recycler_promation_label.setLayoutManager(rankLayoutManager);
//                vHolder.recycler_promation_label.setAdapter(promotionAdapter);
//                vHolder.recycler_promation_label.setVisibility(View.VISIBLE);
//            } else {
//                vHolder.recycler_promation_label.setVisibility(View.INVISIBLE);
//            }
                            mImgRecommend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle extra = new Bundle();
                                    extra.putString(Constants.SP_ID, prdata.mpId);
                                    JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, extra);

//                                    UrlEntity urlEntity = new UrlEntity();
//                                    urlEntity.mpId = prdata.mpId;
//                                    RouterHelper.getHelper().Native2Activity(RouterHelper.getHelper().Schema(ProductDectilActivity.class.getName()), urlEntity);

                                }
                            });

                            mImgAddcart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UIUtils.showToastSafe("购物车");
                                }
                            });


                        }
                    };
                }

                @Override
                public boolean hasMore() {
                    return false;
                }
            });


        }


    }
}
