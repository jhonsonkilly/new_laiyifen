package com.ody.p2p.main.shopcart;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.main.BuildConfig;
import com.ody.p2p.main.R;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.main.shopcart.shopcartview.ShopCartAdvertisementWindow;
import com.ody.p2p.main.specificfunction.DataUtils;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.shopcart.ShopCartBean;
import com.ody.p2p.shopcart.ShopData;
import com.ody.p2p.shopcart.ShoppingCartFragment;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.scrollbanner.ScrollBanner;
import com.ody.p2p.widget.ShopCartConstants;
import com.tendcloud.appcpa.ShoppingCart;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by pzy on 2016/12/5.
 */
public class LyfShopCartFragment extends ShoppingCartFragment {

    TextView tv_favorable_price, tv_vipSavedAmount, tv_vipDiscount,tv_list;
    LinearLayout linear_vip;

    @Override
    public int bindLayout() {
        setVERSION(ShopCartConstants.VERSION_1_2);
        return R.layout.fragment_lyf_shopcart;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        isRecommed = true;//是否需要猜你喜欢
        isEditRefrsh = false;//是否切换编辑时刷新购物车
        isCheckedSplitBill = 1;//拆单检验
        tv_vipDiscount = (TextView) view.findViewById(R.id.tv_vipDiscount);
        tv_favorable_price = (TextView) view.findViewById(R.id.tv_favorable_price);
        tv_vipSavedAmount = (TextView) view.findViewById(R.id.tv_vipSavedAmount);
        tv_list = (TextView) view.findViewById(R.id.tv_list);
        linear_vip = (LinearLayout) view.findViewById(R.id.linear_vip);
        adapter = new LyfShopCartAdapter(getContext(), null, editFalge);
        adapter.setShopcartInterface(this);
        adapter.setSelectIcon_true(selectIcon_true, selectIcon_false);
        adapter.setPriceColor(getResources().getColor(priceColor));
        shop_recycleview.setLayoutManager(RecycleUtils.getLayoutManager(getContext()));
        shop_recycleview.setAdapter(adapter);

        setToBayButton(R.string.to_settle_accounts);
        tv_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtils.ToWebActivity(getActivity(), BuildConfig.H5URL+"/comFun/buy-list.html");

            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);
        mPressenter.getAdvertisement();//获取广告推荐
        img_shopcart_edit.setVisibility(View.GONE);
    }

    @Override
    public void initProductData(List<ShopData> data, int isSelectAll) {
        super.initProductData(data, isSelectAll);
        if (isSelectAll == 1) {
            img_allselect.setImageResource(R.drawable.cart_edit_chose_all_selected);
        }
        try {
            //获取全部购物车商品
            TaklingDataEventMessage msg = new TaklingDataEventMessage();
            msg.setAction(TaklingDataEventMessage.ONVIEWSHOPPINGCART);
            ShoppingCart shoppingCart = ShoppingCart.createShoppingCart();
            for (ShopData shopData : data) {
                if (shopData.getMerchantList() != null && shopData.getMerchantList().getProductGroups() != null && shopData.getMerchantList().getProductGroups().size() > 0) {
                    for (ShopCartBean.ProductGroups productGroup : shopData.getMerchantList().getProductGroups()) {
                        if (productGroup.getProductList() != null && productGroup.getProductList().size() > 0) {
                            for (ShopCartBean.ProductList productList : productGroup.getProductList()) {
                                shoppingCart.addItem(productList.getMpId(), "item_category", productList.getName(), DataUtils.parseStringToTalkingPrice(productList.getPrice() + ""), productList.getNum());
                            }
                        }
                    }
                }
            }
            msg.setShoppingCart(shoppingCart);
            EventBus.getDefault().post(msg);
        } catch (Exception e) {

        }
    }

    @Override
    public void initSummary(ShopCartBean.Summary summary) {
        super.initSummary(summary);
        tv_favorable_price.setText(getString(R.string.already_favorable_) + UiUtils.getMoney(getContext(), summary.getDiscount()) + "  " + getString(R.string.unfreight));
        if (summary.getVipSavedAmount() > 0) {
            linear_vip.setVisibility(View.VISIBLE);
            tv_vipSavedAmount.setText("-" + UiUtils.getMoneyDouble(summary.getVipSavedAmount()));
            tv_vipDiscount.setText("白金会员  " + UiUtils.getDoubleForInteger(summary.getVipDiscount() * 100) + "折优惠");
        } else {
            linear_vip.setVisibility(View.GONE);
        }
    }

    /**
     * 广告推荐
     *
     * @param bean
     */
    @Override
    public void initScrollBanner(final AdData bean) {
        super.initScrollBanner(bean);
        if (bean != null && null != bean.notice_shopping && bean.notice_shopping.size() > 0) {
            scroll_advertis_push.setVisibility(View.VISIBLE);
            scroll_advertis_push.setList(bean.notice_shopping);
            scroll_advertis_push.setStyle(ScrollBanner.FOR_SHOPCART);
//            scroll_advertis_push.setColor(getResources().getColor(priceColor));
//            scroll_advertis_push.setImage(R.drawable.ic_launcher);
            scroll_advertis_push.setColor(R.color.theme_color);
            scroll_advertis_push.setImage(R.drawable.cart_notice);
            scroll_advertis_push.setImageClose(R.drawable.cart_notice_remove);
            if (!scroll_advertis_push.runFlag) {
                scroll_advertis_push.startScroll();
            }
            scroll_advertis_push.setItemClick(new ScrollBanner.ItemClickListener() {
                @Override
                public void click(int positon) {
//                    JumpUtils.ToWebActivity(JumpUtils.H5, bean.data.pageResult.listObj.get(positon).articleLink, WebActivity.PIC_TITLE, com.ody.p2p.shopcart.R.drawable.icon_headlines, null);
                    ShopCartAdvertisementWindow window = new ShopCartAdvertisementWindow(getContext(), bean.notice_shopping);
                    window.showAtLocation(man_relativelayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            });
            scroll_advertis_push.setVisibility(View.VISIBLE);
        } else {
            scroll_advertis_push.setVisibility(View.GONE);
        }
    }

    @Override
    public void toPassable(String prfomtionID) {
        super.toPassable(prfomtionID);
        Bundle bd = new Bundle();
        bd.putString(Constants.PRO_ID, prfomtionID);
        JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, bd);
//        mPressenter.getToPassAble(prfomtionID);
    }

    @Override
    public void changeGif(ShopCartBean.Promotion promotion, int canSeleckedNum, ShopCartBean.GiftProductList data, boolean falge) {
        super.changeGif(promotion, canSeleckedNum, data, falge);
        gifWindow.tv_max_num.setVisibility(View.GONE);
    }
}
