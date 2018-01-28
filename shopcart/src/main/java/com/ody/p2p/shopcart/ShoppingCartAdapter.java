package com.ody.p2p.shopcart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.PromotionAdapter;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.recmmend.RecommendAdapter;
import com.ody.p2p.recmmend.RecommendView;
import com.ody.p2p.recycleviewutils.RecyclerViewDragHolder;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.slidepager.AutoScrollViewPager;
import com.ody.p2p.views.slidepager.BannerPager;
import com.ody.p2p.views.slidepager.RecommendBannerPager;
import com.ody.p2p.widget.ShopCartConstants;

import java.util.List;

import static com.ody.p2p.utils.PxUtils.dipTopx;
import static com.ody.p2p.widget.ShopCartConstants.ITEM_MERCHANT;
import static com.ody.p2p.widget.ShopCartConstants.ITEM_OVERSEAS;
import static com.ody.p2p.widget.ShopCartConstants.ITEM_PRODUCT;
import static com.ody.p2p.widget.ShopCartConstants.ITEM_PROMOTION;

/**
 * Created by ody on 2016/6/14.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter {

    public Context mContext;
    public List<ShopData> mdata;
    public boolean editFlage;
    Drawable drawableLeft;
    public int itemtype = 0;

    public void setEditStatus(boolean falge) {
        this.editFlage = falge;
        notifyDataSetChanged();
    }

    int selectIcon_true;//选中按钮
    int selectIcon_false;//非选中按钮
    int priceColor;//价格颜色

    public ShoppingCartAdapter(Context mContext, List<ShopData> mdata, boolean editFlage) {
        this.mContext = mContext;
        this.mdata = mdata;
        this.editFlage = editFlage;
        drawableLeft = mContext.getResources().getDrawable(R.drawable.open);

        selectIcon_true = R.drawable.icon_shopcart_selected;//选中按钮
        selectIcon_false = R.drawable.icon_shopcart;//非选中按钮
        priceColor = mContext.getResources().getColor(R.color.theme_color);//价格颜色
    }

    @Override
    public int getItemViewType(int position) {
        return mdata.get(position).getItemType();
    }

    public void setData(List<ShopData> mdata) {
        this.mdata = mdata;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null == mdata ? 0 : mdata.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflate = LayoutInflater.from(mContext);
        if (i == ITEM_MERCHANT || i == ITEM_OVERSEAS) {
            return new ViewHolder0(inflate.inflate(R.layout.shopcart_item0, viewGroup, false));
        } else if (i == ITEM_PROMOTION) {
            return new ViewHolder1(inflate.inflate(R.layout.shopcart_item1, viewGroup, false));
        } else if (i == ITEM_PRODUCT) {
            //获取背景菜单
            View mybg = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopcart_item_recyclerview, null);
            mybg.setLayoutParams(new ViewGroup.LayoutParams(PxUtils.dipTopx(62), ViewGroup.LayoutParams.MATCH_PARENT));
            //获取item布局
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopcart_item_product, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //生成返回RecyclerView.ViewHolder
            return new ViewHolder2(mContext, mybg, view, RecyclerViewDragHolder.EDGE_RIGHT).getDragViewHolder();
        } else if (i == ShopCartConstants.ITEM_GIF_PRODUCT) {
            return new ViewHolder3(inflate.inflate(R.layout.shopcart_item_gifproduct_layout, viewGroup, false));
        } else if (i == ShopCartConstants.ITEM_FAILE_PRODUCT) {
            return new ViewHolder4(inflate.inflate(R.layout.shopcart_item4, viewGroup, false));
        } else if (i == ShopCartConstants.ITEM_CLEAN_FAILEPRODUCT) {
            return new ViewHolder5(inflate.inflate(R.layout.shopcart_item5, viewGroup, false));
        } else if (i == ShopCartConstants.ITEM_TITLE_RECOMMEND) {
            return new ViewHolder_0(inflate.inflate(R.layout.shopcart_item6, viewGroup, false));
        } else if (i == ShopCartConstants.ITEM_RECOMMEND) {
            return new ViewHolder7(inflate.inflate(R.layout.shopcart_item7, viewGroup, false));
        } else if (i == ShopCartConstants.ITEM_NULDATA) {
            return new ViewHolder100(inflate.inflate(R.layout.shopcart_item00, viewGroup, false));
        } else {
            return new ViewHolder_0(inflate.inflate(R.layout.shopcart_item_line_layout, viewGroup, false));
        }
    }

    /**
     * 判断是否全选
     *
     * @param data
     * @return
     */
    private boolean getSelectAll(ShopData data) {
        if (null != data.getOverseas() && null != data.getOverseas().getProductGroups() && data.getOverseas().getProductGroups().size() > 0) {
            for (ShopCartBean.ProductGroups prG : data.getOverseas().getProductGroups()) {
                if (null != prG.getProductList() && prG.getProductList().size() > 0) {
                    for (ShopCartBean.ProductList pl : prG.getProductList()) {
                        if (pl.getChecked() == 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 判断是否全选(V=1.2版本)
     * 因为版本取得字段不一样，所以多写一个
     *
     * @return
     */
    private boolean getSelectAllforMer(List<ShopCartBean.ProductGroups> datas) {
        if (null != datas && datas.size() > 0) {
            for (ShopCartBean.ProductGroups data : datas) {
                if (null != data.getProductList() && data.getProductList().size() > 0) {
                    for (ShopCartBean.ProductList pl : data.getProductList()) {
                        if (pl.getDisabled() != 1) {//不可选的不管
                            if (pl.getChecked() == 0) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param data
     * @return
     */
    public String getMpids(List<ShopCartBean.ProductGroups> data, int checked) {
        String mpids = "";
        if (null != data && data.size() > 0) {
            for (ShopCartBean.ProductGroups proGroups : data) {
                if (null != proGroups.getProductList() && proGroups.getProductList().size() > 0) {
                    for (ShopCartBean.ProductList proList : proGroups.getProductList()) {
                        mpids += proList.getMpId() + "-" + checked + ",";
                    }
                }
            }
        }
        return null == mpids ? "" : mpids.toString().substring(0, mpids.length() - 1);
    }

    /**
     * @param data
     * @return
     */
    public static String getMpidsChecked(List<ShopCartBean.ProductGroups> data) {
        String mpids = "";
        if (null != data && data.size() > 0) {
            for (ShopCartBean.ProductGroups proGroups : data) {
                if (null != proGroups.getProductList() && proGroups.getProductList().size() > 0) {
                    for (ShopCartBean.ProductList proList : proGroups.getProductList()) {
                        if (proList.getChecked() == 1) {
                            mpids += proList.getMpId() + ",";
                        }
                    }
                }
            }
        }
        return null == mpids ? "" : mpids.toString().substring(0, mpids.length() - 1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int postion) {
        final ShopData data = mdata.get(postion);
        itemtype = getItemViewType(postion);
        if (itemtype == ShopCartConstants.ITEM_OVERSEAS || itemtype == ShopCartConstants.ITEM_MERCHANT) {
            ViewHolder0 holder = (ViewHolder0) viewHolder;
            holder.linear_merchant.setVisibility(View.GONE);
        }
        if (itemtype == ShopCartConstants.ITEM_MERCHANT) {
            ViewHolder0 holder = (ViewHolder0) viewHolder;
            holder.tv_stro_name.setText(data.getMerchantList().getMerchantName());
            holder.tv_stro_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2017/10/12 注释店铺的跳转
                    Bundle extra = new Bundle();
                    extra.putString("merchantId", data.getMerchantList().getMerchantId());
                    JumpUtils.ToActivity(JumpUtils.SHOP_HOME, extra);
                }
            });
            // TODO: 2017/10/12 注释店铺的跳转
            if(StringUtils.isEmpty(data.getMerchantList().getMerchantId())){
                holder.iv_right.setVisibility(View.GONE);
            }else{
                holder.iv_right.setVisibility(View.VISIBLE);
            }

            if (getSelectAllforMer(data.getMerchantList().getProductGroups())) {
                holder.img_seleect_all.setTag(0);
                holder.img_seleect_all.setImageResource(selectIcon_true);
            } else {
                holder.img_seleect_all.setTag(1);
                holder.img_seleect_all.setImageResource(selectIcon_false);
            }
            holder.img_seleect_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        int checked = (Integer) v.getTag();
                        mShopcartInter.checkedAll(getMpids(data.getMerchantList().getProductGroups(), checked));
                    }
                }
            });
            holder.tv_titile_ticket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        mShopcartInter.getTicket(data.getMerchantList().getMerchantId());
                    }
                }
            });
        } else if (itemtype == ShopCartConstants.ITEM_OVERSEAS) {
            ViewHolder0 holder = (ViewHolder0) viewHolder;
            holder.tv_stro_name.setText(data.getOverseas().getOverseaName());
            if (getSelectAll(data)) {
                holder.img_seleect_all.setTag(0);
                holder.img_seleect_all.setImageResource(selectIcon_true);
            } else {
                holder.img_seleect_all.setTag(1);
                holder.img_seleect_all.setImageResource(selectIcon_false);
            }
            holder.img_seleect_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        int checked = (Integer) v.getTag();
                        String checkedstr = "";
                        for (ShopCartBean.ProductGroups prG : data.getOverseas().getProductGroups()) {
                            for (ShopCartBean.ProductList prL : prG.getProductList()) {
                                checkedstr += prL.getMpId() + "-" + checked + ",";
                            }
                        }
                        if (null != checkedstr && checkedstr.length() > 0) {
                            mShopcartInter.checkedAll(checkedstr.substring(0, checkedstr.length() - 1));
                        }
                    }
                }
            });
            holder.tv_titile_ticket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        mShopcartInter.getTicket(data.getOverseas().getOverseaId());
                    }
                }
            });
        } else if (itemtype == ShopCartConstants.ITEM_PROMOTION) {
            ViewHolder1 holder = (ViewHolder1) viewHolder;
            holder.tv_promation_name.setText(data.getPromotion().getDisplayName());
            GlideUtil.display(mContext, data.getPromotion().getPromIconUrl()).into(holder.img_promation_pic);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 20, 0, 0);
            holder.linear_promation.setLayoutParams(layoutParams);
            holder.linear_promation.setClickable(false);
//          购物车列表中增加了一个promotionType判断是否是赠品还是换购
//          promotionType**|Integer|前台促销类型|1005是满额赠
//          1006是满量赠，1007是买一赠一，
//          1018是满额换购，1019是满量换购
//          1005/1006是满赠  1018/1019是换购

            if (mdata.get(postion).getPromotion().isFlag()) {
                if (null != mdata.get(postion).getGiftProductList() && mdata.get(postion).getGiftProductList().size() > 0 && mdata.get(postion).getGiftProductList().get(0).getCanSelectedGiftsNum() > 0) {
                    holder.tv_showgift.setVisibility(View.VISIBLE);
                    if (mdata.get(postion).getPromotion().getPromotionType() == 1005 || mdata.get(postion).getPromotion().getPromotionType() == 1006) {
                        holder.tv_showgift.setText(mContext.getString(R.string.choose) + mContext.getString(R.string.gift));
                    } else if (mdata.get(postion).getPromotion().getPromotionType() == 1018 || mdata.get(postion).getPromotion().getPromotionType() == 1019) {
                        holder.tv_showgift.setText(mContext.getString(R.string.choose) + mContext.getString(R.string.redemption));
                    } else {
                        holder.tv_showgift.setVisibility(View.INVISIBLE);
                    }
                    holder.tv_showgift.setTag(true);
                    holder.tv_showgift.setClickable(true);
                } else {
                    holder.tv_showgift.setVisibility(View.INVISIBLE);
                    holder.tv_showgift.setClickable(false);
                }
                holder.tv_showgift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mShopcartInter && null != mdata.get(postion).getGiftProductList() && mdata.get(postion).getGiftProductList().size() > 0) {
                            mShopcartInter.changeGif(mdata.get(postion).getPromotion(), mdata.get(postion).getGiftProductList().get(0).getCanSelectedGiftsNum(), mdata.get(postion).getGiftProductList().get(0), true);
                        }
                    }
                });
            } else {
                holder.linear_promation.setClickable(true);
                holder.tv_showgift.setVisibility(View.VISIBLE);
                if (mdata.get(postion).getPromotion().getPromotionType() == 1005 || mdata.get(postion).getPromotion().getPromotionType() == 1006) {
                    holder.tv_showgift.setText(R.string.show_gift);
                } else if (mdata.get(postion).getPromotion().getPromotionType() == 1018 || mdata.get(postion).getPromotion().getPromotionType() == 1019) {
                    holder.tv_showgift.setText(R.string.showredemption);
                } else {
                    holder.tv_showgift.setVisibility(View.INVISIBLE);
                }
                holder.tv_showgift.setTag(false);
                holder.tv_showgift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mShopcartInter && null != mdata.get(postion).getGiftProductList() && mdata.get(postion).getGiftProductList().size() > 0) {
                            mShopcartInter.changeGif(mdata.get(postion).getPromotion(), mdata.get(postion).getGiftProductList().get(0).getCanSelectedGiftsNum(), mdata.get(postion).getGiftProductList().get(0), false);
                        }
                    }
                });
            }
        } else if (itemtype == ShopCartConstants.ITEM_PRODUCT) {
            if (null == mdata || null == mdata.get(postion) || null == mdata.get(postion).getProductList()) {
                return;
            }
            final ViewHolder2 holder = (ViewHolder2) RecyclerViewDragHolder.getHolder(viewHolder);
            holder.tv_product_name.setTag(postion);
            holder.linear_editproduct_num.setVisibility(View.VISIBLE);
            if (data.getProductList().getSaleType() == 2 || data.getProductList().getSaleType() == 3) {
                if (data.getProductList().getSaleIconUrl() != null) {
                    UiUtils.getStringSpan(holder.tv_product_name, mContext, data.getProductList().getSaleIconUrl(), data.getProductList().getName(), postion);
                }
            } else {
                holder.tv_product_name.setText(data.getProductList().getName());
            }
            if (null != mdata.get(postion).getProductList().getTipsMsg() && mdata.get(postion).getProductList().getTipsMsg().length() > 0) {
                holder.tv_tipsMsg.setVisibility(View.VISIBLE);
                holder.tv_tipsMsg.setText(mdata.get(postion).getProductList().getTipsMsg());
                holder.tv_tipsMsg.setTextColor(priceColor);
            } else {
                holder.tv_tipsMsg.setVisibility(View.GONE);
            }
            GlideUtil.display(mContext, data.getProductList().getPicUrl()).into(holder.img_shopcart);
            holder.tv_shopcart_price.setText(UiUtils.getMoney(mContext, data.getProductList().getPrice()));
            holder.tv_shopcart_price.setTextColor(priceColor);
            holder.tv_shopcart_num.setText("" + data.getProductList().getNum());
            holder.checkbox_buttom.setClickable(true);
            if (data.getProductList().getChecked() == 0) {//未选中
                holder.checkbox_buttom.setImageResource(selectIcon_false);
            } else {//选中
                holder.checkbox_buttom.setImageResource(selectIcon_true);
            }
            if (data.getProductList().getStockNum() > 5) {
                holder.tv_product_kucun.setVisibility(View.INVISIBLE);
            } else if (data.getProductList().getStockNum() > 0) {
                holder.tv_product_kucun.setVisibility(View.VISIBLE);
                holder.tv_product_kucun.setText(R.string.inventory_nervou);
            } else {
                holder.tv_product_kucun.setVisibility(View.VISIBLE);
                holder.tv_product_kucun.setText(R.string.no_inventory);

                holder.checkbox_buttom.setImageResource(R.drawable.fail_checkbox);
                holder.checkbox_buttom.setClickable(false);
                holder.linear_editproduct_num.setVisibility(View.INVISIBLE);
            }
            if (editFlage) {//编辑状态
                holder.tv_standard.setBackgroundResource(R.drawable.circular_bead_border);
                holder.tv_standard.setPadding(10, 0, 10, 0);
                holder.tv_standard.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
            } else {
                holder.tv_standard.setBackgroundResource(R.color.white);
                holder.tv_standard.setPadding(0, 0, 10, 0);
                holder.tv_standard.setCompoundDrawables(null, null, null, null);
            }
            if (mdata.size() > postion + 1 && null != mdata.get(postion + 1) && mdata.get(postion + 1).getItemType() == 3) {
                holder.view_product_bottom_liner.setVisibility(View.GONE);//如果下一个item是赠品则隐藏分割线
            } else {
                holder.view_product_bottom_liner.setVisibility(View.VISIBLE);
            }
            if (null != mdata.get(postion).getProductList().getPromotions() && mdata.get(postion).getProductList().getPromotions().size() > 0) {
                PromotionAdapter promotionAdapter = new PromotionAdapter(mContext, mdata.get(postion).getProductList().getPromotions());
                LinearLayoutManager rankLayoutManager = new LinearLayoutManager(mContext);
                rankLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                holder.recycler_promation_label.setLayoutManager(rankLayoutManager);
                holder.recycler_promation_label.setAdapter(promotionAdapter);
                holder.recycler_promation_label.setVisibility(View.VISIBLE);
            } else {
                holder.recycler_promation_label.setVisibility(View.GONE);
            }

            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.close();
                    if (null != mShopcartInter) {//删除商品
                        mShopcartInter.deleteProduct(data.getProductList(), postion);
                    }
                }
            });
//            holder.tv_guanzhu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.close();
//                    if (null != mShopcartInter) {//关注商品
//                        mShopcartInter.attentionProduct(data.getProductList());
//                    }
//                }
//            });
            holder.conterper.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CustomDialog dialog = new CustomDialog(mContext, mContext.getResources().getString(R.string.delect_product), 2);
                    dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                        @Override
                        public void Confirm() {
                            mShopcartInter.deleteProduct(data.getProductList(), postion);
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
            holder.img_shopcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isEmpty(data.getProductList().getSeriesParentId())) {
                        mShopcartInter.skipInten(data.getProductList().getMpId());
                    } else {
                        mShopcartInter.skipInten(data.getProductList().getSeriesParentId());
                    }
                }
            });
            holder.tv_product_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isEmpty(data.getProductList().getSeriesParentId())) {
                        mShopcartInter.skipInten(data.getProductList().getMpId());
                    } else {
                        mShopcartInter.skipInten(data.getProductList().getSeriesParentId());
                    }
                }
            });
//            holder.conterper.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mShopcartInter.skipInten(data.getProductList().getSeriesParentId());
//                }
//            });
            holder.tv_standard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editFlage && null != mShopcartInter) {//是有效商品而且是编辑状态
                        mShopcartInter.getProductStandard(data.getProductList());//获取系类属性
                    }
                }
            });

            //购物车加
            holder.btn_shopcart_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        int num = Integer.valueOf(holder.tv_shopcart_num.getText().toString());
                        if (num < data.getProductList().getStockNum()) {
                            mShopcartInter.editShopcartNum(holder, data.getProductList(), ++num, data.getProductList().getName(),
                                    data.getProductList().getSaleType() + "", 1);//添加参数是为了埋点使用name和type
                        } else {
                            ToastUtils.showStr(mContext.getResources().getString(R.string.inventory_insufficient));
                        }
                    }
                }
            });

            //购物车减
            holder.btn_shopcart_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        int num = Integer.valueOf(holder.tv_shopcart_num.getText().toString());
                        if (num > 1) {
                            mShopcartInter.editShopcartNum(holder, data.getProductList(), --num
                                    , data.getProductList().getName(), data.getProductList().getSaleType() + "", 2);
                        } else {
                            CustomDialog dialog = new CustomDialog(mContext, mContext.getResources().getString(R.string.delect_product), 2);
                            dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                                @Override
                                public void Confirm() {
                                    mShopcartInter.deleteProduct(data.getProductList(), postion);
                                }
                            });
                            dialog.show();
//                            mShopcartInter.deleteProduct(data.getProductList(), postion);
//                            //编辑数量时，当数量为1时【-】应该时不可点击，不需要做是否删除二次确认
                        }
                    }
                }
            });
            holder.checkbox_buttom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        mShopcartInter.checkedItem(data.getProductList());
                    }
                }
            });
            String standard = "";
            if (null != mdata.get(postion).getProductList().getPropertyTags() && mdata.get(postion).getProductList().getPropertyTags().size() > 0) {
                for (ShopCartBean.PropertyTags propertyTags : mdata.get(postion).getProductList().getPropertyTags()) {
                    standard += propertyTags.getName() + " " + propertyTags.getValue() + "  ";
                }
            } else {
                standard = mContext.getResources().getString(R.string.not_standard);
            }
            holder.tv_standard.setText(standard);
            holder.tv_standards.setText(standard);

            if (editFlage) {
                holder.relative_standards.setVisibility(View.VISIBLE);
                holder.view_product_bottom_liner.setVisibility(View.INVISIBLE);
                holder.tv_standard.setVisibility(View.INVISIBLE);
            } else {
                holder.tv_standard.setVisibility(View.VISIBLE);
                holder.relative_standards.setVisibility(View.GONE);
                holder.view_product_bottom_liner.setVisibility(View.VISIBLE);
            }
            holder.relative_standards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editFlage && null != mShopcartInter) {//是有效商品而且是编辑状态
                        mShopcartInter.getProductStandard(mdata.get(postion).getProductList());//获取系类属性
                    }
                }
            });
        } else if (itemtype == ShopCartConstants.ITEM_GIF_PRODUCT) {
            ViewHolder3 holder = (ViewHolder3) viewHolder;
            holder.tv_label_gift_itme.setTextColor(priceColor);
            holder.tv_giftname.setText(data.getGiftProducts().getGiftName());
            GlideUtil.display(mContext, data.getGiftProducts().getPicUrl()).override(200, 200).into(holder.img_gift_pic);
            holder.tv_gifprice.setText(UiUtils.getMoney(mContext, data.getGiftProducts().getPrice()));
            holder.tv_giftCount.setText("x" + data.getGiftProducts().getCheckNum());
            holder.tv_changeGif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        mShopcartInter.changeGif(mdata.get(postion).getPromotion(), mdata.get(postion).getGiftProductList().get(0).getCanSelectedGiftsNum(), mdata.get(postion).getGiftProductList().get(0), false);
                    }
                }
            });
            if (data.getGiftProducts().getCanSaleNum() < 5) {
                holder.kucun.setText(R.string.inventory_nervou);
                holder.kucun.setVisibility(View.VISIBLE);
            } else if (data.getGiftProducts().getCanSaleNum() == 0) {
                holder.kucun.setText(R.string.no_inventory);
                holder.kucun.setVisibility(View.VISIBLE);
            } else {
                holder.kucun.setVisibility(View.INVISIBLE);
            }
        } else if (itemtype == ShopCartConstants.ITEM_FAILE_PRODUCT) {
            ViewHolder4 holder = (ViewHolder4) viewHolder;
            holder.tv_failproduct_name.setText(data.getFailureProducts().getName());
            GlideUtil.display(mContext, data.getFailureProducts().getPicUrl()).override(200, 200).into(holder.img_fail_pic);
            holder.tv_failproduct_price.setText(UiUtils.getMoney(mContext, data.getFailureProducts().getPrice()));
            holder.tv_find_similarity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
//                        mShopcartInter.refresh(data.getFailureProducts().getMpId());
                    }
                }
            });
        } else if (itemtype == ShopCartConstants.ITEM_CLEAN_FAILEPRODUCT) {
            ViewHolder5 holder = (ViewHolder5) viewHolder;
            holder.clean_failproduct_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShopcartInter) {
                        mShopcartInter.clearFailProduct(data.getProductList());
                    }
                }
            });
        } else if (itemtype == ShopCartConstants.ITEM_RECOMMEND) {
            ViewHolder7 holder = (ViewHolder7) viewHolder;
            holder.banner_grid_pager.setVisibility(View.VISIBLE);
            holder.grild_recyclerview.setVisibility(View.GONE);


            if (null != data.getRecommed() && data.getRecommed().size() > 0) {

                holder.banner_grid_pager.setLayoutCount(2);//每行展示2个
                ViewGroup.LayoutParams linearParams = holder.liner_match.getLayoutParams();
                if (data.getRecommed().size() > 4) {//推荐商品不满两个展示一行
                    linearParams.height = dipTopx(600);
                } else if (data.getRecommed().size() > 2) {
                    linearParams.height = dipTopx(580);
                } else {
                    linearParams.height = dipTopx(300);
                }
                holder.liner_match.setLayoutParams(linearParams);

                //每页展示4个item
                holder.banner_grid_pager.setEasyData(RecommendView.setRecommendData(mContext, data.getRecommed(), 4, new RecommendAdapter.RecommendCallBack() {
                    @Override
                    public void addCart() {
                        if (null != mShopcartInter) {
                            mShopcartInter.refresh();
                        }
                    }

                    @Override
                    public void addCart(Recommedbean.DataList dataList) {
                        if (null != mShopcartInter) {
                            mShopcartInter.refresh();
                        }
                        onViewShoppingCart(dataList);
                    }
                }));
                holder.banner_grid_pager.setAuto(false);
//                holder.banner_grid_pager.setIndicator(com.ody.p2p.R.drawable.shape_banner_normal, com.ody.p2p.R.drawable.shape_banner_selected);
                holder.banner_grid_pager.setIndicatorPosition(BannerPager.GRAVITY_CENTER, dipTopx(18), 30, 0);
                holder.banner_grid_pager.setslideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
                holder.banner_grid_pager.setslidetouchMode(AutoScrollViewPager.SLIDE_BORDER_AWAYS_PARENT);
            } else {
                holder.banner_grid_pager.setVisibility(View.GONE);
            }

//            //以下是向下滑动的猜你喜歡
//            holder.grild_recyclerview.setLayoutManager(RecycleUtils.getGridLayoutManager(mContext, 2));//这里切换为girdview的样式
//            holder.grild_recyclerview.setAdapter(new RecyclerView.Adapter() {
//                @Override
//                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                    return new ViewHolderRecommed(inflate.inflate(R.layout.shopcart_item_recommed_girdl, parent, false));
//                }
//
//                @Override
//                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//                    ViewHolderRecommed holderRecommedHolder = (ViewHolderRecommed) holder;
//                    final ShopCartBean.ProductList prdata = data.getRecommed().get(position);
//                    holderRecommedHolder.tv_recommed_name.setText(prdata.getName());
//                    GlideUtil.display(mContext, prdata.getPicUrl()).override(200, 200).into(holderRecommedHolder.img_recommend);
//                    holderRecommedHolder.tv_recommend_price.setText(UiUtils.getMoney(mContext, prdata.getPrice()));
//                    holderRecommedHolder.img_recommend.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (null != mShopcartInter) {
//                                mShopcartInter.skipInten(prdata.getSeriesParentId());
//                            }
//                        }
//                    });
//                }
//
//                @Override
//                public int getItemCount() {
//                    return data.getRecommed().size();
//                }
//            });
        } else if (itemtype == ShopCartConstants.ITEM_NULDATA) {
            ViewHolder100 viewHolder100 = (ViewHolder100) viewHolder;
            viewHolder100.txt_goshopp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bd = new Bundle();
                    bd.putInt(Constants.GO_MAIN, 0);
                    JumpUtils.ToActivity(JumpUtils.MAIN, bd);
                }
            });
        }
    }

    public void onViewShoppingCart(Recommedbean.DataList dataList) {

    }

    public class ViewHolder0 extends RecyclerView.ViewHolder {
        public TextView tv_stro_name;
        ImageView img_seleect_all, iv_right;
        TextView tv_titile_ticket;
        public LinearLayout linear_merchant;

        public ViewHolder0(View itemView) {
            super(itemView);
            linear_merchant = (LinearLayout) itemView.findViewById(R.id.linear_merchant);
            tv_stro_name = (TextView) itemView.findViewById(R.id.tv_stro_name);
            img_seleect_all = (ImageView) itemView.findViewById(R.id.img_seleect_all);
            tv_titile_ticket = (TextView) itemView.findViewById(R.id.tv_titile_ticket);
            iv_right = (ImageView) itemView.findViewById(R.id.iv_right);
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public ImageView img_promation_pic;
        public TextView tv_promation_name, tv_showgift;
        public LinearLayout linear_promation;

        public ViewHolder1(View itemView) {
            super(itemView);
            linear_promation = (LinearLayout) itemView.findViewById(R.id.linear_promation);
            img_promation_pic = (ImageView) itemView.findViewById(R.id.img_promation_pic);
            tv_promation_name = (TextView) itemView.findViewById(R.id.tv_promation_name);
            tv_showgift = (TextView) itemView.findViewById(R.id.tv_showgift);

        }
    }

    public class ViewHolder2 extends RecyclerViewDragHolder {
        public TextView tv_product_name;
        public TextView tv_delete;
        //        TextView tv_guanzhu;暂时没有关注了
        public ImageView checkbox_buttom;
        public LinearLayout linear_editproduct_num;
        public ImageView img_shopcart;
        public TextView tv_shopcart_num;
        public TextView tv_shopcart_price;
        public Button btn_shopcart_sub;
        public Button btn_shopcart_add;
        public TextView tv_standard, tv_standards;
        public ImageView img_item_icon_url;
        public TextView tv_product_kucun;
        public View view_product_bottom_liner;
        public LinearLayout conterper;
        public RecyclerView recycler_promation_label;
        public TextView tv_tipsMsg;

        public RelativeLayout relative_standards;

        public ViewHolder2(Context context, View bgView, View topView, int mTrackingEdges) {
            super(context, bgView, topView, mTrackingEdges);
        }

        @Override
        public void initView(View itemView) {
            //右侧菜单
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            //tv_guanzhu = (TextView) itemView.findViewById(R.id.tv_guanzhu);

            //view界面
            tv_tipsMsg = (TextView) itemView.findViewById(R.id.tv_tipsMsg);
            recycler_promation_label = (RecyclerView) itemView.findViewById(R.id.recycler_promation_label);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            checkbox_buttom = (ImageView) itemView.findViewById(R.id.checkbox_buttom);
            linear_editproduct_num = (LinearLayout) itemView.findViewById(R.id.linear_editproduct_num);
            tv_shopcart_price = (TextView) itemView.findViewById(R.id.tv_shopcart_price);
            img_shopcart = (ImageView) itemView.findViewById(R.id.img_shopcart);
            tv_shopcart_num = (TextView) itemView.findViewById(R.id.tv_shopcart_num);
            btn_shopcart_sub = (Button) itemView.findViewById(R.id.btn_shopcart_sub);
            conterper = (LinearLayout) itemView.findViewById(R.id.conterper);
            btn_shopcart_add = (Button) itemView.findViewById(R.id.btn_shopcart_add);
            tv_standard = (TextView) itemView.findViewById(R.id.tv_standard);
            tv_standards = (TextView) itemView.findViewById(R.id.tv_standards);
            tv_product_kucun = (TextView) itemView.findViewById(R.id.tv_product_kucun);
            view_product_bottom_liner = itemView.findViewById(R.id.view_product_bottom_liner);
            relative_standards = (RelativeLayout) itemView.findViewById(R.id.relative_standards);
        }
    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {
        public TextView tv_giftname;
        public ImageView img_gift_pic;
        public TextView tv_gifprice;
        public TextView tv_giftCount;
        public TextView tv_changeGif;
        public TextView kucun, tv_label_gift_itme;

        public ViewHolder3(View itemView) {
            super(itemView);
            tv_label_gift_itme = (TextView) itemView.findViewById(R.id.tv_label_gift_itme);
            tv_giftname = (TextView) itemView.findViewById(R.id.tv_giftname);
            img_gift_pic = (ImageView) itemView.findViewById(R.id.img_gift_pic);
            tv_gifprice = (TextView) itemView.findViewById(R.id.tv_gifprice);
            tv_giftCount = (TextView) itemView.findViewById(R.id.tv_giftCount);
            tv_changeGif = (TextView) itemView.findViewById(R.id.tv_changeGif);
            kucun = (TextView) itemView.findViewById(R.id.kucun);
        }
    }

    class ViewHolder4 extends RecyclerView.ViewHolder {
        public TextView tv_failproduct_name;
        ImageView img_fail_pic;
        TextView tv_failproduct_price;
        TextView tv_find_similarity;

        public ViewHolder4(View itemView) {
            super(itemView);
            tv_failproduct_name = (TextView) itemView.findViewById(R.id.tv_failproduct_name);
            img_fail_pic = (ImageView) itemView.findViewById(R.id.img_fail_pic);
            tv_failproduct_price = (TextView) itemView.findViewById(R.id.tv_failproduct_price);
            tv_find_similarity = (TextView) itemView.findViewById(R.id.tv_find_similarity);
        }
    }

    public class ViewHolder5 extends RecyclerView.ViewHolder {
        public TextView clean_failproduct_button;

        public ViewHolder5(View itemView) {
            super(itemView);
            clean_failproduct_button = (TextView) itemView.findViewById(R.id.clean_failproduct_button);
        }
    }

    public class ViewHolder7 extends RecyclerView.ViewHolder {
        public RecyclerView grild_recyclerview;
        public RecommendBannerPager banner_grid_pager;
        public LinearLayout liner_match;

        public ViewHolder7(View itemView) {
            super(itemView);
            liner_match = (LinearLayout) itemView.findViewById(R.id.liner_match);
            grild_recyclerview = (RecyclerView) itemView.findViewById(R.id.grild_recyclerview);
            banner_grid_pager = (RecommendBannerPager) itemView.findViewById(R.id.banner_grid_pager);
        }
    }

    public void setShopcartInterface(shopcartInterface mShopcartInter) {
        this.mShopcartInter = mShopcartInter;
    }

    public shopcartInterface mShopcartInter;

    public class ViewHolder100 extends RecyclerView.ViewHolder {

        public TextView txt_goshopp;

        public ViewHolder100(View itemView) {
            super(itemView);
            txt_goshopp = (TextView) itemView.findViewById(R.id.txt_goshop);
        }
    }

    public class ViewHolder_0 extends RecyclerView.ViewHolder {
        public ViewHolder_0(View itemView) {
            super(itemView);
        }
    }

    public interface shopcartInterface {
        //flag是用来标记是进行加/减购物车操作的,由于p2p接口走的是同一个,但是埋点走的是2个方法,所以要区分,1代表加操作,2代表减操作
        void editShopcartNum(ViewHolder2 holder, ShopCartBean.ProductList product, int number, String name, String type, int flag);//编辑购物车数量

        void getProductStandard(ShopCartBean.ProductList product);//获取系列属性

        void deleteProduct(ShopCartBean.ProductList product, int postion);//删除商品

        void attentionProduct(ShopCartBean.ProductList product);//关注

        void checkedItem(ShopCartBean.ProductList product);//选中

        void clearFailProduct(ShopCartBean.ProductList product);//清空失效品

        void checkedAll(String mpids);//全选

        void changeGif(ShopCartBean.Promotion promotion, int canSeleckedNum, ShopCartBean.GiftProductList data, boolean falge);//更换赠品

        void refresh();//刷新

        void getTicket(String overseaId);//领券

        void skipInten(String Mpi);//跳转至商品详情

        void toPassable(String prfomtionID);//去凑单

        void deleteGif(long profomtionID);//删除赠品
    }

    /**
     * 设置选中的按钮
     *
     * @param selectIcon_true
     */
    public void setSelectIcon_true(int selectIcon_true, int selectIcon_false) {
        this.selectIcon_true = selectIcon_true;
        this.selectIcon_false = selectIcon_false;
    }

    /**
     * 设置价格颜色
     *
     * @param color
     */
    public void setPriceColor(int color) {
        this.priceColor = color;
    }

}
