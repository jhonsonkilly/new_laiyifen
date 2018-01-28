package com.ody.p2p.search.searchresult;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.search.R;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2016/6/12.
 */
public class SearchResultAdapter extends BaseRecyclerViewAdapter {

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;
    private View headView;
    private View footView;
    private int headViewSize = 0;
    private int footViewSize = 0;
    private ChangeGridLayoutManagerSpance changeGridLayoutManager;
    public boolean isAddFoot = false;
    public boolean isAddHead = false;

    public int showFlag;

    public SearchResultAdapter(Context context, List datas, int showFlag) {
        super(context, datas);
        this.showFlag = showFlag;
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (headViewSize == 1 && position == 0) {
            type = TYPE_HEADER;
        } else if (footViewSize == 1 && position == getItemCount() - 1) {
            //最后一个位置
            type = TYPE_FOOT;
        }
        return type;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        if (position < mDatas.size()) {
            SearchResultViewHolder viewHolder = (SearchResultViewHolder) holder;
            final ResultBean.ProductBean bean = (ResultBean.ProductBean) getDatas().get(position);
            if (null != bean.promotionIconList) {
                bean.promotionIconList = null;
            }
            bean.promotionIconList = new ArrayList<>();
            if (bean.saleType == 2) {
                if (bean.saleIconUrl != null) {
                    viewHolder.tv_product_name.setText(bean.name);
                    UiUtils.getStringSpan(viewHolder.tv_product_name, mContext, bean.saleIconUrl, bean.name);
                } else {
                    viewHolder.tv_product_name.setText(bean.name);
                }
            } else if (bean.saleType == 3) {
                if (bean.saleIconUrl != null) {
                    UiUtils.getStringSpan(viewHolder.tv_product_name, mContext, bean.saleIconUrl, bean.name);
                } else {
                    viewHolder.tv_product_name.setText(bean.name);
                }
            } else {
                viewHolder.tv_product_name.setText(bean.name);
            }
            if (bean.merchantType == 10) {//自营
                viewHolder.iv_ziying.setVisibility(View.VISIBLE);
            } else {
                viewHolder.iv_ziying.setVisibility(View.GONE);
            }
            if (null != bean.promotionIconUrls && bean.promotionIconUrls.size() > 0) {//如果有特价就选择特价标签
                for (int i = 0; i < bean.promotionIconUrls.size(); i++) {
                    bean.promotionIconList.add(bean.promotionIconUrls.get(i));
                }
            }
            if (null != bean.promotionInfo && bean.promotionInfo.size() > 0 && null != bean.promotionInfo.get(0).promotions && bean.promotionInfo.get(0).promotions.size() > 0) {
                for (int k = 0; k < bean.promotionInfo.get(0).promotions.size(); k++) {
                    bean.promotionIconList.add(bean.promotionInfo.get(0).promotions.get(k).iconUrl);
                }
            }
            viewHolder.tv_product_sold.setText(mContext.getString(R.string.already_sell) + "：" + bean.mpSalesVolume);
            GlideUtil.display(OdyApplication.gainContext(), 300, bean.picUrl).placeholder(R.drawable.icon_stub).into(viewHolder.iv_product_pic);
            viewHolder.tv_productcost_old.getPaint().setAntiAlias(true);//抗锯齿
            viewHolder.tv_productcost_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if (!StringUtils.isEmpty(bean.promotionPrice)) {
                viewHolder.tv_product_cost.setText(UiUtils.getMoney(mContext, bean.promotionPrice));
                viewHolder.tv_productcost_old.setText(mContext.getString(R.string.money_symbol) + bean.price);
                viewHolder.tv_productcost_old.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_product_cost.setText(UiUtils.getMoney(mContext, bean.price));
                viewHolder.tv_productcost_old.setVisibility(View.GONE);
            }
            if (bean.commentInfo != null) {
                viewHolder.tv_praise_num.setText(bean.commentInfo.commentNum);
                viewHolder.tv_product_praise_good.setText(bean.commentInfo.goodRate + "%");
            }
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.recycler_promotion.setLayoutManager(manager);
            if (bean.promotionIconList != null && bean.promotionIconList.size() > 0) {
                PromotionIconAdapter adapter = new PromotionIconAdapter(mContext, bean.promotionIconList);
                viewHolder.recycler_promotion.setAdapter(adapter);
            } else {
                List<String> list = new ArrayList<>();
                PromotionIconAdapter adapter = new PromotionIconAdapter(mContext, list);
                viewHolder.recycler_promotion.setAdapter(adapter);
            }
            viewHolder.iv_addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.stockNum > 0) {
                        if (addBuyCart != null) {
                            addBuyCart.addtoCart(String.valueOf(bean.mpId), position);
                        }
                    } else {
//                        ToastUtils.showShort(mContext.getResources().getString(R.string.inventory_insufficient));
                        Toast mToast = Toast.makeText(mContext, mContext.getResources().getString(R.string.inventory_insufficient), Toast.LENGTH_SHORT);
                        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        mToast.show();
                    }
                }
            });
            viewHolder.rl_search_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toSpInfo != null) {
                        toSpInfo.toShop(String.valueOf(bean.mpId));
                    }
                }
            });

            viewHolder.iv_tag1.setVisibility(View.GONE);
            viewHolder.iv_tag2.setVisibility(View.GONE);
            viewHolder.iv_tag3.setVisibility(View.GONE);
            viewHolder.iv_tag4.setVisibility(View.GONE);
            if (bean.scripts != null && bean.scripts.size() > 0) {
                for (int i = 0; i < bean.scripts.size(); i++) {
                    if (bean.scripts.get(i).displayType == 0) {
                        viewHolder.iv_tag1.setVisibility(View.VISIBLE);
                        GlideUtil.display(mContext, bean.scripts.get(i).scriptIconUrl).into(viewHolder.iv_tag1);
                    } else if (bean.scripts.get(i).displayType == 1) {
                        viewHolder.iv_tag2.setVisibility(View.VISIBLE);
                        GlideUtil.display(mContext, bean.scripts.get(i).scriptIconUrl).into(viewHolder.iv_tag2);
                    } else if (bean.scripts.get(i).displayType == 2) {
                        viewHolder.iv_tag3.setVisibility(View.VISIBLE);
                        GlideUtil.display(mContext, bean.scripts.get(i).scriptIconUrl).into(viewHolder.iv_tag3);
                    } else if (bean.scripts.get(i).displayType == 3) {
                        viewHolder.iv_tag4.setVisibility(View.VISIBLE);
                        GlideUtil.display(mContext, bean.scripts.get(i).scriptIconUrl).into(viewHolder.iv_tag4);
                    }
                }
            }
        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        SearchResultViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = headView;
                holder = new SearchResultViewHolder(view);
                break;
            case TYPE_ITEM:
                if (showFlag == SearchResultActivity.TAB_TYPE) {
                    holder = new SearchResultViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_gridview_searchresult, parent, false));
                } else if (showFlag == SearchResultActivity.LIST_TYPE) {
                    holder = new SearchResultViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_listview_searchresult, parent, false));
                }
                break;
            case TYPE_FOOT:
                view = footView;
                holder = new SearchResultViewHolder(view);
                break;
        }
        return holder;
    }

    public interface ChangeGridLayoutManagerSpance {
        public void change(int size, boolean isAddHead, boolean isAddFoot);
    }

    //提供接口给 让LayoutManager根据添加尾部 头部与否来做判断 显示头部与底部的SpanSize要在添加头部和尾部之后
    public void setChangeGridLayoutManager(ChangeGridLayoutManagerSpance changeGridLayoutManager) {
        this.changeGridLayoutManager = changeGridLayoutManager;
        changeGridLayoutManager.change(getItemCount() - 1, isAddHead, isAddFoot);
    }

    public static class SearchResultViewHolder extends BaseRecyclerViewHolder {

        public ImageView iv_product_pic;
        public TextView tv_product_name;
        public TextView tv_product_cost;
        public ImageView iv_addtocart;
        public RecyclerView recycler_tag;
        public RelativeLayout rl_search_item;
        public TextView tv_productcost_old;
        public TextView tv_product_sold;
        public RecyclerView recycler_promotion;

        public TextView tv_product_praise;
        public TextView tv_praise;
        public TextView tv_product_praise_good;
        public TextView tv_praise_num;

        public ImageView iv_tag1;
        public ImageView iv_tag2;
        public ImageView iv_tag3;
        public ImageView iv_tag4;

        public View view_cart;

        public ImageView iv_ziying;

        public SearchResultViewHolder(View view) {
            super(view);
            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_product_cost = (TextView) view.findViewById(R.id.tv_product_cost);
            tv_product_praise = (TextView) view.findViewById(R.id.tv_product_praise);
            tv_product_praise_good = (TextView) view.findViewById(R.id.tv_product_praise_good);
            iv_product_pic = (ImageView) view.findViewById(R.id.iv_product_pic);
            iv_addtocart = (ImageView) view.findViewById(R.id.iv_addtocart);
            rl_search_item = (RelativeLayout) view.findViewById(R.id.rl_search_item);
            recycler_promotion = (RecyclerView) view.findViewById(R.id.recycler_promotion);
            tv_product_sold = (TextView) view.findViewById(R.id.tv_product_sold);
            tv_productcost_old = (TextView) view.findViewById(R.id.tv_productcost_old);

            tv_product_praise = (TextView) view.findViewById(R.id.tv_product_praise);
            tv_praise = (TextView) view.findViewById(R.id.tv_praise);
            tv_product_praise_good = (TextView) view.findViewById(R.id.tv_product_praise_good);
            tv_praise_num = (TextView) view.findViewById(R.id.tv_praise_num);

            iv_tag1 = (ImageView) view.findViewById(R.id.iv_tag1);
            iv_tag2 = (ImageView) view.findViewById(R.id.iv_tag2);
            iv_tag3 = (ImageView) view.findViewById(R.id.iv_tag3);
            iv_tag4 = (ImageView) view.findViewById(R.id.iv_tag4);

            view_cart = view.findViewById(R.id.view_cart);

            iv_ziying = (ImageView) view.findViewById(R.id.iv_ziying);
        }
    }


    public interface AddBuyCart {
        void addtoCart(String mpId, int position);
    }

    public AddBuyCart addBuyCart;

    public void setAddBuyCart(AddBuyCart addBuyCart) {
        this.addBuyCart = addBuyCart;
    }

    public interface ToSpInfo {
        void toShop(String mpId);
    }

    public ToSpInfo toSpInfo;

    public void setToSpInfo(ToSpInfo toSpInfo) {
        this.toSpInfo = toSpInfo;
    }


    public void setShowFlag(int showFlag) {
        this.showFlag = showFlag;
        for (int i = 0; i < getDatas().size(); i++) {
            ResultBean.ProductBean bean = (ResultBean.ProductBean) getDatas().get(i);
            bean.showType = showFlag;
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size() + headViewSize + footViewSize;
    }

    public void addHeadView(View view) {
        headView = view;
        headViewSize = 1;
        isAddHead = true;
    }

    public void addFootView(View view) {
        footView = view;
        footViewSize = 1;
        isAddFoot = true;
    }
}
