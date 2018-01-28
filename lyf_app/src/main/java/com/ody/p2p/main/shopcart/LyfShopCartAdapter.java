package com.ody.p2p.main.shopcart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.PromotionInfo;
import com.ody.p2p.main.R;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.recycleviewutils.RecyclerViewDragHolder;
import com.ody.p2p.shopcart.ShopData;
import com.ody.p2p.shopcart.ShoppingCartAdapter;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.countdown.CountDownTextView;
import com.ody.p2p.widget.ShopCartConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pzy on 2016/12/9.
 */
public class LyfShopCartAdapter extends ShoppingCartAdapter {

    public LyfShopCartAdapter(Context mContext, List<ShopData> mdata, boolean editFlage) {
        super(mContext, mdata, editFlage);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == ShopCartConstants.ITEM_PROMOTION) {
            //促销
            return new PromationViewHoder(LayoutInflater.from(mContext).inflate(R.layout.shopcart_item_promation, viewGroup, false));
        } else if (i == ShopCartConstants.ITEM_PRODUCT) {
            //购物车商品
            //获取背景菜单
            View mybg = LayoutInflater.from(viewGroup.getContext()).inflate(com.ody.p2p.shopcart.R.layout.shopcart_item_recyclerview, null);
            mybg.setLayoutParams(new ViewGroup.LayoutParams(PxUtils.dipTopx(62), ViewGroup.LayoutParams.MATCH_PARENT));

            //获取item布局
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lyfshopcart_product, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return new ProductViewe(mContext, mybg, view, RecyclerViewDragHolder.EDGE_RIGHT).getDragViewHolder();
        } else if (i == ShopCartConstants.ITEM_GIF_PRODUCT) {
            //购物车赠品
            return new ViewHolder3(LayoutInflater.from(mContext).inflate(R.layout.item_shopcart_gif, viewGroup, false));
        } else if (i == ShopCartConstants.ITEM_TITLE_RECOMMEND) {
            //猜你喜欢
            return new ViewHolder_0(LayoutInflater.from(mContext).inflate(R.layout.item_lyfshopcart__recommendstr, viewGroup, false));
        } else if (i == ShopCartConstants.ITEM_NULDATA) {
            //购物车为空
            return new ViewHolder100(LayoutInflater.from(mContext).inflate(R.layout.item_shopcart_noproduct, viewGroup, false));
        } else {
            return super.onCreateViewHolder(viewGroup, i);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int postion) {
        super.onBindViewHolder(viewHolder, postion);
        final ShopData data = mdata.get(postion);
        if (itemtype == ShopCartConstants.ITEM_MERCHANT || itemtype == ShopCartConstants.ITEM_OVERSEAS) {
            ViewHolder0 holder = (ViewHolder0) viewHolder;
            holder.linear_merchant.setVisibility(View.VISIBLE);
            holder.tv_stro_name.setTextColor(mContext.getResources().getColor(R.color.main_title_color));
            holder.linear_merchant.setBackgroundResource(R.color.tint_white_background);
        } else if (itemtype == ShopCartConstants.ITEM_PROMOTION) {
            PromationViewHoder holder = (PromationViewHoder) viewHolder;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (postion > 0 && getItemViewType(postion - 1) != 0 && getItemViewType(postion - 1) != -1) {
                layoutParams.setMargins(0, 20, 0, 0);
            } else {
                layoutParams.setMargins(0, 0, 0, 0);
            }
            holder.linear_promation.setLayoutParams(layoutParams);

            if (data.getPromotion().getIsReachCondition() == 0) {
                holder.tv_to_passable.setVisibility(View.VISIBLE);
                holder.tv_to_passable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShopcartInter.toPassable(data.getPromotion().getPromotionId() + "");
                    }
                });
            } else {
                holder.tv_to_passable.setVisibility(View.GONE);
            }
        } else if (itemtype == ShopCartConstants.ITEM_PRODUCT) {
            if (null == data || null == data.getProductList()) {
                return;
            }
            final ProductViewe holder = (ProductViewe) RecyclerViewDragHolder.getHolder(viewHolder);
            //来伊份不选择系列属性
            holder.tv_standard.setVisibility(View.VISIBLE);
            holder.relative_standards.setVisibility(View.GONE);
            holder.tv_standard.setBackgroundResource(R.color.white);
            holder.tv_standard.setPadding(0, 0, 10, 0);
            holder.tv_standard.setCompoundDrawables(null, null, null, null);

            if (null != mdata.get(postion).getProductList().getPropertyTags() && mdata.get(postion).getProductList().getPropertyTags().size() > 0) {
                holder.tv_standard.setVisibility(View.VISIBLE);
            } else {
                holder.tv_standard.setText("");
                holder.tv_standards.setText("");
                holder.tv_standard.setVisibility(View.INVISIBLE);
            }
            holder.recycler_promation_label.setVisibility(View.GONE);
            holder.view_product_bottom_liner.setVisibility(View.GONE);
            //秒杀
            holder.tv_promas.setVisibility(View.GONE);
            holder.tv_number.setVisibility(View.GONE);
            holder.tv_number.setText("x" + data.getProductList().getNum());

             if (data.getProductList().getItemType() == 3) {//商品类型,普通商品:0,奖品:3,赠品:4,换购:5。不传默认为0
                holder.linear_editproduct_num.setVisibility(View.GONE);
                holder.tv_number.setVisibility(View.VISIBLE);
                if (!StringUtils.isEmpty(data.getProductList().getItemIconUrl())) {
                    GlideUtil.display(mContext, data.getProductList().getItemIconUrl()).into(holder.img_item_icon_url);
                    holder.img_item_icon_url.setVisibility(View.VISIBLE);
                } else {
                    holder.img_item_icon_url.setVisibility(View.GONE);
                }
            } else {
                holder.tv_number.setVisibility(View.GONE);
                holder.img_item_icon_url.setVisibility(View.GONE);
            }
            holder.tv_promas.setVisibility(View.GONE);
            holder.tv_promas.setStop();
            if (data.getProductList().getDisabled() == 1 && null != data.getProductList().getDisabledReason() && data.getProductList().getDisabledReason().length() > 0) {
                //不可购买
                holder.linear_promas.setVisibility(View.VISIBLE);
                holder.tv_promas.setVisibility(View.VISIBLE);
                holder.tv_promas.setText(data.getProductList().getDisabledReason());
//                holder.checkbox_buttom.setVisibility(View.INVISIBLE);
                holder.checkbox_buttom.setClickable(false);
                holder.checkbox_buttom.setImageResource(R.drawable.fail_checkbox);
            } else {
                holder.checkbox_buttom.setClickable(true);
//                holder.checkbox_buttom.setVisibility(View.VISIBLE);
                if (null != data.getProductList().getPromotions() && data.getProductList().getPromotions().size() > 0) {
                    holder.tv_product_name.setTag(postion);
//                    holder.tv_product_name.setText(data.getProductList().getName());
                    UiUtils.getStringSpan(holder.tv_product_name, mContext, data.getProductList().getPromotions().get(0).getIconUrl(), " " + data.getProductList().getName(), postion);
                    for (final PromotionInfo pr : data.getProductList().getPromotions()) {
                        if (pr.getCountdown() > 0) {
//                            if (pr.getType() == 1012) {//秒杀,不可以修改数量
//                                holder.linear_editproduct_num.setVisibility(View.GONE);
//                                holder.tv_number.setVisibility(View.VISIBLE);
//                            }
                            holder.linear_promas.setVisibility(View.VISIBLE);
                            holder.tv_promas.setVisibility(View.VISIBLE);
                            holder.tv_promas.setLeftText(pr.getIconTxt() + "商品，<font color='#666666'>请在</font>");
                            holder.tv_promas.setRightText("<font color='#666666'>内完成订单</font>");
                            holder.tv_promas.setCountTime(pr, CountDownTextView.HH_MM_SS);
                            break;
                        }
                    }
                } else {
                    holder.linear_promas.setVisibility(View.GONE);
                }
            }
            if (null != data.getProductList().getTipsMsg() && data.getProductList().getTipsMsg().length() > 0) {
                holder.linear_promas.setVisibility(View.VISIBLE);
                if (data.getProductList().getTipsType() == 2) {
                    holder.tv_tipsMsg.setText(R.string.tips_seckill);
                } else {
                    holder.tv_tipsMsg.setText(mdata.get(postion).getProductList().getTipsMsg());
                }
            } else {
//                holder.linear_promas.setVisibility(View.GONE);
            }
        } else if (itemtype == ShopCartConstants.ITEM_GIF_PRODUCT) {
            ViewHolder3 holder = (ViewHolder3) viewHolder;
            if (null != mdata.get(postion).getPromotion() && (mdata.get(postion).getPromotion().getPromotionType() == 1018 || mdata.get(postion).getPromotion().getPromotionType() == 1019)) {
                holder.tv_label_gift_itme.setText(R.string.redemption);
                holder.tv_label_gift_itme.setBackgroundResource(R.drawable.change_sale_lable_background);
                holder.tv_label_gift_itme.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                holder.tv_label_gift_itme.setText(R.string.gift);
                holder.tv_label_gift_itme.setBackgroundResource(R.drawable.circle_theme_stroke_bg);
                holder.tv_label_gift_itme.setTextColor(mContext.getResources().getColor(R.color.theme_color));
            }
            if (data.getGiftProducts().getCanSaleNum() == 0) {
                holder.kucun.setText(R.string.no_inventory);
                holder.tv_giftCount.setText(R.string.delect_nbsp);
                holder.kucun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShopcartInter.deleteGif(data.getGiftProducts().getPromotionId());
                    }
                });
            }
        } else if (itemtype == ShopCartConstants.ITEM_RECOMMEND) {
            ViewHolder7 holder = (ViewHolder7) viewHolder;
            holder.banner_grid_pager.linear.setBackgroundResource(R.drawable.viewpager_item_background);
            holder.banner_grid_pager.setBackgroundResource(R.color.background_color);
            holder.banner_grid_pager.linear.setPadding(PxUtils.dipTopx(15), 0, PxUtils.dipTopx(15), 0);
            holder.banner_grid_pager.setIndicatorPosition(PxUtils.dipTopx(28));
            holder.banner_grid_pager.linear.setGravity(Gravity.CENTER_VERTICAL);
        }
    }

    class ProductViewe extends ViewHolder2 {
        CountDownTextView tv_promas;
        TextView tv_number;
        LinearLayout linear_promas;
        ImageView img_item_icon_url;

        public ProductViewe(Context context, View bgView, View topView, int mTrackingEdges) {
            super(context, bgView, topView, mTrackingEdges);
        }

        @Override
        public void initView(View itemView) {
            super.initView(itemView);
            img_item_icon_url = (ImageView) itemView.findViewById(R.id.img_item_icon_url);
            linear_promas = (LinearLayout) itemView.findViewById(R.id.linear_promas);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_promas = (CountDownTextView) itemView.findViewById(R.id.tv_promas);
        }
    }

    class PromationViewHoder extends ViewHolder1 {
        TextView tv_to_passable;

        public PromationViewHoder(View itemView) {
            super(itemView);
            tv_to_passable = (TextView) itemView.findViewById(R.id.tv_to_passable);
        }
    }

    @Override
    public void onViewShoppingCart(Recommedbean.DataList dataList) {
        super.onViewShoppingCart(dataList);
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
}