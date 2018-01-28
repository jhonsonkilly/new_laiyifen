package com.ody.p2p.shopcart;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseFragment;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.shopcartview.ChangeGifWindow;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.basepopupwindow.CouponBean;
import com.ody.p2p.views.basepopupwindow.CouponWindow;
import com.ody.p2p.views.basepopupwindow.MenuPopBean;
import com.ody.p2p.views.basepopupwindow.ProductBean;
import com.ody.p2p.views.basepopupwindow.PropertyBean;
import com.ody.p2p.views.basepopupwindow.PropertyWindow;
import com.ody.p2p.views.recyclerviewlayoutmanager.AtMostRecyclerView;
import com.ody.p2p.views.scrollbanner.ScrollBanner;
import com.ody.p2p.views.tablayout.CirTextView;
import com.ody.p2p.webactivity.WebActivity;
import com.ody.p2p.widget.ShopCartConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends BaseFragment implements PropertyWindow.PropertyBack, ShopCartView, View.OnClickListener, ShoppingCartAdapter.shopcartInterface {
    public boolean isRecommed = false;//是否包含猜你喜欢
    public boolean isEditRefrsh = true;//是否切换编辑时刷新购物车

    public AtMostRecyclerView shop_recycleview;

    public TextView tv_shopcart_editbutton, tv_shopcart_Zprice, tv_total, tv_faild;
    public LinearLayout linear_edit_bottom, linear_bottom_ber, linear_bottom_bar, man_relativelayout;
    public ImageView img_totop_button, img_allselect, iv_failed, img_shopcart_edit;
    public Button btn_shopcart_topay, btn_deleteselect, btn_shopcart_move_attention, btn_shopcart_share;
    public ChangeGifWindow gifWindow;
    public ScrollBanner scroll_advertis_push;
    public CustomDialog dialog;

    public String str;
    public View layout_loadfail;
    public ShoppingCartAdapter adapter;

    protected CirTextView iv_has_msg;

    protected int type = -1;

    private ImageView iv_back;
    public String VERSION;//购物车版本

    protected int isCheckedSplitBill;//0.不检验拆单，1.检验拆单

    protected DisasseblePopupWindow mPop;

    public String[] menuStr = {"消息"};
    public String[] meunSkip = {"message"};
    public int[] menuRes = {R.drawable.ic_message};

    public String[] menuStrAc = {"首页", "消息"};
    public String[] meunSkipAc = {"main", "message"};
    public int[] menuResAc = {R.drawable.ic_homepage, R.drawable.ic_message};

    /**
     * 标识是否是编辑状态
     */
    public boolean editFalge = false;
    public ShopCartPressenter mPressenter;

    public List<ShopData> data;

    public String getVERSION() {
        if (null == VERSION || VERSION.length() < 1) {
            setVERSION(ShopCartConstants.VERSION_1_4);//默认购物车v1.4版本
        }
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String getShopCartVersion() {
        return getVERSION();
    }

    @Override
    public void showPop(SeparateBean bean) {
        if (bean.getData() != null && bean.getData().checkoutGroups != null) {
            mPop = new DisasseblePopupWindow(getContext(), bean);
            mPop.showAtLocation(linear_bottom_bar, Gravity.CENTER, 0, 0);
        } else {
            mPressenter.toConfirmorder();
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_shopcart;
    }

    @Override
    public void initPresenter() {
        mPressenter = new ShopCartPressenterImr(this);
//        loadShopCart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && null != mPressenter) {
            setEditFalge(true);
            loadShopCart();
        }
    }

    public void loadShopCart() {
        if (null == mPressenter) {
            return;
        }
        mPressenter.shopCartData();
    }

    @Override
    public void clearData() {
        if (null != adapter) {
//            adapter.setData(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setEditFalge(true);
        loadShopCart();
    }

    public int selectIcon_true;//选中按钮
    public int selectIcon_false;//非选中按钮
    public int priceColor = R.color.theme_color;//价格颜色

    /**
     * 设置选中按钮图样
     *
     * @param selectIcon_true
     * @param selectIcon_false
     */
    public void setSelectedIcon(int selectIcon_true, int selectIcon_false) {
        this.selectIcon_true = selectIcon_true;
        this.selectIcon_false = selectIcon_false;
    }

    /**
     * 设置价格颜色
     *
     * @param priceColor
     */
    public void setPriceColor(int priceColor) {
        this.priceColor = priceColor;
        tv_shopcart_Zprice.setTextColor(getResources().getColor(priceColor));
        tv_total.setTextColor(getResources().getColor(priceColor));
    }

    /**
     * 设置去结算按钮样式
     *
     * @param backgroud
     * @param btnColor
     */
    public void setToBayButton(int backgroud, int btnColor) {
        btn_shopcart_topay.setBackgroundResource(backgroud);
        btn_shopcart_topay.setTextColor(getResources().getColor(btnColor));
    }

    int topay = R.string.to_pay;

    /**
     * 设置去结算按钮文本
     *
     * @param str
     */
    public void setToBayButton(int str) {
        topay = str;
    }

    @Override
    public Context getClassContext() {
        return getContext();
    }


    @Override
    public void initView(View view) {
        EventBus.getDefault().register(this);

        selectIcon_true = R.drawable.selected_true;//选中按钮
        selectIcon_false = R.drawable.selected_false;//非选中按钮

        scroll_advertis_push = (ScrollBanner) view.findViewById(R.id.scroll_advertis_push);//购物车广告

        btn_shopcart_topay = (Button) view.findViewById(R.id.btn_shopcart_topay);
        shop_recycleview = (AtMostRecyclerView) view.findViewById(R.id.shop_recycleview);
        img_shopcart_edit = (ImageView) view.findViewById(R.id.img_shopcart_edit);
        btn_shopcart_share = (Button) view.findViewById(R.id.btn_shopcart_share);
        btn_shopcart_move_attention = (Button) view.findViewById(R.id.btn_shopcart_move_attention);
        linear_bottom_bar = (LinearLayout) view.findViewById(R.id.linear_bottom_bar);
        tv_shopcart_Zprice = (TextView) view.findViewById(R.id.tv_shopcart_Zprice);
        tv_total = (TextView) view.findViewById(R.id.tv_total);
        man_relativelayout = (LinearLayout) view.findViewById(R.id.man_relativelayout);
        img_totop_button = (ImageView) view.findViewById(R.id.img_totop_button);
        tv_shopcart_editbutton = (TextView) view.findViewById(R.id.tv_shopcart_editbutton);
        linear_edit_bottom = (LinearLayout) view.findViewById(R.id.linear_edit_bottom);
        linear_bottom_ber = (LinearLayout) view.findViewById(R.id.linear_bottom_ber);
        img_allselect = (ImageView) view.findViewById(R.id.img_allselect);
        btn_deleteselect = (Button) view.findViewById(R.id.btn_deleteselect);

        iv_back = (ImageView) view.findViewById(R.id.iv_back);

        iv_has_msg = (CirTextView) view.findViewById(R.id.iv_has_msg);
        //加载失败的布局
        layout_loadfail = view.findViewById(R.id.layout_loadfail);
        iv_failed = (ImageView) layout_loadfail.findViewById(R.id.iv_failed);
        tv_faild = (TextView) layout_loadfail.findViewById(R.id.tv_faild);

        img_shopcart_edit.setOnClickListener(this);
        btn_shopcart_move_attention.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        layout_loadfail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                layout_loadfail.setVisibility(View.GONE);
                loadShopCart();
            }
        });
        shop_recycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !editFalge) {//滑动停止时判断
                    //取消置顶按钮
//                    if (shop_recycleview.getChildCount() > 0) {
//                        if (shop_recycleview.getChildAt(0).getY() > -10) {
//                            hideBtnTop();
//                        } else {
//                            showBtnTop();
//                        }
//                    }
                }
            }
        });

        adapter = new ShoppingCartAdapter(getContext(), null, editFalge);
        adapter.setShopcartInterface(this);
        adapter.setSelectIcon_true(selectIcon_true, selectIcon_false);
        adapter.setPriceColor(getResources().getColor(priceColor));

        shop_recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        shop_recycleview.setAdapter(adapter);
    }


    public void setSaasFlag(int saasFlag) {
        this.isCheckedSplitBill = saasFlag;
    }

    @Override
    public void doBusiness(Context mContext) {
        if (type == 1) {
            iv_back.setVisibility(View.VISIBLE);
        } else {
            iv_back.setVisibility(View.GONE);
        }
        img_shopcart_edit.setVisibility(View.VISIBLE);
        setSaasFlag(1);
        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false) && OdyApplication.getValueByKey(Constants.MSG_COUNT, 0) > 0) {
            iv_has_msg.setText(OdyApplication.getValueByKey(Constants.MSG_COUNT, 0) + "");
            iv_has_msg.setVisibility(View.VISIBLE);
        } else {
            iv_has_msg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void loadOnError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void editShow(boolean falge) {
        if (falge) {
            linear_bottom_bar.setVisibility(View.VISIBLE);
            tv_shopcart_editbutton.setVisibility(View.VISIBLE);
        } else {
            linear_bottom_bar.setVisibility(View.GONE);
            tv_shopcart_editbutton.setVisibility(View.INVISIBLE);
            hideBtnTop();
        }
    }

    @Override
    public void loadFaile() {
        layout_loadfail.setVisibility(View.VISIBLE);
        Log.d("sahdid", "sbdhsqd");
//        showFailed(true, 0);
//        setFailedMargins(0, 48, 0, 48);
//        viewFailed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showFailed(false, 0);
//                loadShopCart();
//            }
//        });
    }

    /**
     * 设置加载失败时的样式
     */
    public void setLoadFailType(int img, String text) {
        iv_failed.setImageResource(img);
        tv_faild.setText(text);
    }

    /**
     * 初始化
     */
    @Override
    public void initProductData(List<ShopData> data, int isSelectAll) {
        this.data = data;
        layout_loadfail.setVisibility(View.GONE);
        linear_bottom_bar.setVisibility(View.VISIBLE);
        //有数据才可以触发点击
        img_totop_button.setOnClickListener(this);
        tv_shopcart_editbutton.setOnClickListener(this);
        img_allselect.setOnClickListener(this);
        btn_shopcart_topay.setOnClickListener(this);
        btn_deleteselect.setOnClickListener(this);
        adapter.setData(data);
        if (isSelectAll == 1) {
            img_allselect.setImageResource(selectIcon_true);
        } else {
            img_allselect.setImageResource(selectIcon_false);
        }
    }

    /**
     * 猜你喜欢
     *
     * @param data
     */
    @Override
    public void initRecommedData(List<ShopData> data) {
        adapter.setData(data);
    }


    @Override
    public void initSummary(ShopCartBean.Summary summary) {
        if (null == tv_shopcart_Zprice) {
            return;
        }
        if (null != summary) {
            tv_shopcart_Zprice.setText(UiUtils.getMoney(getContext(), summary.getAmount()));
            if (summary.getTotalNum() > 0) {
                btn_shopcart_topay.setText(getString(topay) + "(" + summary.getTotalNum() + ")");
                btn_shopcart_topay.setClickable(true);
                btn_shopcart_topay.setTextColor(getResources().getColor(R.color.white));
            } else {
                btn_shopcart_topay.setClickable(false);
                btn_shopcart_topay.setText(getString(topay));
                btn_shopcart_topay.setTextColor(getResources().getColor(R.color.sub_title_color));
            }
        } else {
            tv_shopcart_Zprice.setText(UiUtils.getMoney(getContext(), 0));
            btn_shopcart_topay.setTextColor(getResources().getColor(R.color.sub_title_color));
            btn_shopcart_topay.setClickable(false);
            img_allselect.setClickable(false);
        }
    }

    /**
     * 显示置顶按钮
     */
    private void showBtnTop() {
        // TODO Auto-generated method stub
        if (img_totop_button.getVisibility() == View.GONE) {
            img_totop_button.setVisibility(View.VISIBLE);
            img_totop_button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_enter));
        }
    }

    /**
     * 隐藏置顶按钮
     */
    private void hideBtnTop() {
        // TODO Auto-generated method stub
        if (null != img_totop_button && img_totop_button.getVisibility() == View.VISIBLE) {
            img_totop_button.setVisibility(View.GONE);
            img_totop_button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_exit));
        }
    }

    public void setEditFalge(boolean editFalge) {
        this.editFalge = editFalge;
        changeEdit();
    }

    public void changeEdit() {
        if (null == tv_shopcart_editbutton || null == linear_edit_bottom || null == linear_bottom_ber) {
            editFalge = false;
            return;
        }
        if (editFalge) {
            editFalge = false;
            tv_shopcart_editbutton.setText(R.string.editor);
            linear_edit_bottom.setVisibility(View.GONE);
            linear_bottom_ber.setVisibility(View.VISIBLE);
        } else {
            editFalge = true;
            hideBtnTop();
            tv_shopcart_editbutton.setText(R.string.complete);
            linear_edit_bottom.setVisibility(View.VISIBLE);
            linear_bottom_ber.setVisibility(View.GONE);
        }
        if (null != adapter && isEditRefrsh) {
            adapter.setEditStatus(editFalge);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_shopcart_editbutton) {
            changeEdit();
        } else if (v.getId() == R.id.img_allselect) {
            mPressenter.selectAll(null);
        } else if (v.getId() == R.id.img_totop_button) {
            shop_recycleview.smoothScrollToPosition(0);
        } else if (v.getId() == R.id.btn_shopcart_topay) {
            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                if (isCheckedSplitBill == 1) {
                    mPressenter.prepareCheck();
                } else {
                    mPressenter.toConfirmorder();//已经登录，去结算
                }
            } else {//未登录
                Bundle bd = new Bundle();
                bd.putInt(Constants.LOSINGTAP, 100);
                JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
            }
        } else if (v.getId() == R.id.btn_deleteselect) {
            final String deleteMps = getMpidsCheckedAll(data);
            if (deleteMps != null) {
                str = getContext().getString(R.string.delect_product);
                dialog = new CustomDialog(getContext(), str);
                dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                    @Override
                    public void Confirm() {
                        //确定删除
                        mPressenter.deleteSelected(deleteMps);
                    }
                });
                dialog.show();
            } else {
                ToastUtils.showShort(this.getString(R.string.choose_delect_product));
            }
        } else if (v.getId() == R.id.iv_back) {
            getActivity().finish();
        } else if (v.getId() == R.id.btn_shopcart_move_attention) {
            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                final String deleteMps = getMpidsCheckedAll(data);
                if (deleteMps != null) {
                    mPressenter.addFavorite(deleteMps);
                } else {
                    ToastUtils.showShort(this.getString(R.string.please_choose_product));
                }
            } else {//未登录
                Bundle bd = new Bundle();
                bd.putInt(Constants.LOSINGTAP, 100);
                JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
            }
        } else if (v.getId() == R.id.btn_shopcart_share) {//分享
            if (getMpidsCheckedAll(data) != null) {
                //
            } else {
                //ToastUtils.showShort(this.getString(R.string.please_choose_product));
            }
        } else if (v.getId() == R.id.img_shopcart_edit) {
            if (type == -1) {
                final List<MenuPopBean> popList = new ArrayList<>();
                for (int i = 0; i < menuStr.length; i++) {
                    MenuPopBean bean = new MenuPopBean();
                    bean.content = menuStr[i];
                    bean.message = meunSkip[i];
                    bean.picRes = menuRes[i];
                    popList.add(bean);
                }
                final com.ody.p2p.views.basepopupwindow.SelectMenu menu = new com.ody.p2p.views.basepopupwindow.SelectMenu(getActivity(), popList);
                menu.setClickSelectListener(new com.ody.p2p.views.basepopupwindow.SelectMenu.clickSelectMenuListener() {
                    @Override
                    public void click(int position) {
                        if (position == 0) {
                            menu.dismiss();
                            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                                JumpUtils.ToWebActivity(JumpUtils.H5, OdyApplication.H5URL + "/my/my-message.html", WebActivity.NO_TITLE, -1, "");
                            } else {
                                JumpUtils.ToActivity(JumpUtils.LOGIN);
                            }
                        }
//                    if (position == 2) {
//                        getOperation().forward(null);
//                    }
                    }
                });
                menu.showAsDropDown(img_shopcart_edit, PxUtils.dipTopx(-55), 0);
            }

        }
    }

    @Override
    public void removeItem(int postion) {
        adapter.notifyItemRemoved(postion);
    }

    /**
     * 获取选中的id
     *
     * @param mData
     * @return
     */
    public static String getMpidsCheckedAll(List<ShopData> mData) {
        String mpids = "";
        if (mData != null && mData.size() > 0) {
            for (ShopData productList : mData) {
                if (productList.getMerchantList() != null) {
                    List<ShopCartBean.ProductGroups> data = productList.getMerchantList().getProductGroups();
                    if (null != data && data.size() > 0) {
                        for (ShopCartBean.ProductGroups proGroups : data) {
                            if (null != proGroups.getProductList() && proGroups.getProductList().size() > 0) {
                                for (ShopCartBean.ProductList proList : proGroups.getProductList()) {
                                    if (proList.getChecked() == 1) {
//                                        mpids += proList.getMpId()+ ",";
                                        //奖品新增两个参数
                                        mpids += proList.getMpId() + "-" + proList.getItemType() + "-" + proList.getObjectId() + ",";
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        return mpids.equals("") ? null : mpids.toString().substring(0, mpids.length() - 1);
    }

    /**
     * 获取系列属性
     *
     * @param product
     */
    @Override
    public void getProductStandard(ShopCartBean.ProductList product) {
        this.product = product;
        mPressenter.getProperty(product.getMpId());
    }

    /**
     * 编辑购物车数量
     *
     * @param holder
     * @param number
     */
    @Override
    public void editShopcartNum(ShoppingCartAdapter.ViewHolder2 holder, ShopCartBean.ProductList product, int number, String name, String type, int flag) {
        mPressenter.editShopcartNum(product, number, flag);
    }

    @Override
    public void checkedItem(ShopCartBean.ProductList product) {
        mPressenter.checkedItem(product);
    }

    /**
     * 删除商品
     *
     * @param product
     * @param postion
     */
    @Override
    public void deleteProduct(final ShopCartBean.ProductList product, final int postion) {
        this.product = product;
        this.postion = postion;
        mPressenter.deleteProduct(product, postion);
    }

    public ShopCartBean.ProductList product;
    int postion = 0;

    /**
     * 关注
     *
     * @param product
     */
    @Override
    public void attentionProduct(final ShopCartBean.ProductList product) {
//        mPressenter.attentionProduct(product);
        this.product = product;
        str = getContext().getString(R.string.guanzhu);
        dialog = new CustomDialog(getContext(), str);
        dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
            @Override
            public void Confirm() {
                mPressenter.attentionProduct(product);
            }
        });
        dialog.show();
    }

    @Override
    public void clearFailProduct(ShopCartBean.ProductList product) {
        mPressenter.clearFailProduct();
    }

    @Override
    public void checkedAll(String mpids) {
        mPressenter.selectAll(mpids);
    }

    @Override
    public void changeGif(final ShopCartBean.Promotion promotion, int canSeleckedNum, ShopCartBean.GiftProductList data, boolean falge) {
        gifWindow = new ChangeGifWindow(getContext(), data, promotion, falge, canSeleckedNum);
        gifWindow.showAtLocation(man_relativelayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        gifWindow.setmChangeGifCallBack(new ChangeGifWindow.ChangeGifCallBack() {
            @Override
            public void updateGif(String mpids) {
                mPressenter.UpdateGift(promotion.getPromotionId(), mpids);
            }

            @Override
            public void windowdismiss() {
                gifWindow = null;
            }
        });
//        gifWindow.btn_complete_changegif_window.setBackgroundResource(R.drawable.select_ds_button);
        if (!gifWindow.isShowing()) {
            gifWindow.showAtLocation(man_relativelayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    @Override
    public void refresh() {
        mPressenter.shopCartData();
    }

    @Override
    public void getTicket(String overseaId) {
        mPressenter.getCouponBean(overseaId);
    }

    @Override
    public void getCoupon(CouponBean Bean) {
        CouponWindow couponWindow = new CouponWindow(getContext(), Bean);
        couponWindow.showAtLocation(man_relativelayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public PropertyWindow popwindow;

    int popwindowBtn = R.layout.layout_flowitem;

    public void setPopwindowBtn(int popwindowBtn) {
        this.popwindowBtn = popwindowBtn;
    }

    @Override
    public void showPropertyWindow(PropertyBean bean) {
        if (null != popwindow && popwindow.isShowing()) {
            return;
        }
        if (null != bean && null != bean.getData() && null != bean.getData().getSerialProducts() && bean.getData().getSerialProducts().size() > 0) {
            popwindow = new PropertyWindow(getContext(), bean, product.getNum(), popwindowBtn);
        } else {
            popwindow = new PropertyWindow(getContext(), product.getPicUrl(), product.getPrice(), product.getName() + "", product.getNum(), product.getStockNum(), popwindowBtn);
        }
        popwindow.setPropertyBack(this);
        popwindow.showAtLocation(man_relativelayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 跳转至商品详情
     *
     * @param Mpi
     */
    @Override
    public void skipInten(String Mpi) {
//        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://");
//        activityRoute.withParams(Constants.SP_ID, Mpi + "").open();
        Bundle bd = new Bundle();
        bd.putString(Constants.SP_ID, Mpi + "");
        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
    }


    @Override
    public void PropertyCallBack(ProductBean productnum, int num) {
        if (null != productnum) {
            if (product.getNum() != num || !product.getMpId().equals(productnum.mpId)) {
                mPressenter.UpdatepProduct(product.getMpId(), String.valueOf(productnum.mpId), num);
            }
        } else {
            if (product.getNum() != num) {
                mPressenter.UpdatepProduct(product.getMpId(), product.getMpId(), num);
            }
        }

    }
    //    @Override
//    public void addEditNumber(int checkedNumber) {
//        ToastUtils.showShort("addEditNumber");
//    }
//
//    @Override
//    public void subEditNumber(int checkedNumber) {
//        ToastUtils.showShort("subEditNumber");
//    }

    @Override
    public void bayNow(ProductBean product, int productNumber) {
//        ToastUtils.showShort("bayNow");
    }

    @Override
    public void addToShopCart(ProductBean product, int productNumber) {
//        ToastUtils.showShort("addToShopCart");
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(EventMessage event) {
        if (event.flag == EventMessage.MSG_REFRESH) {
            if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false) && OdyApplication.getValueByKey(Constants.MSG_COUNT, 0) > 0) {
                iv_has_msg.setText(OdyApplication.getValueByKey(Constants.MSG_COUNT, 0) + "");
                iv_has_msg.setVisibility(View.VISIBLE);
            } else {
                iv_has_msg.setVisibility(View.INVISIBLE);
            }
        }
    }

    //购物车广告推荐
    @Override
    public void initScrollBanner(final AdData bean) {

    }

    /**
     * 去凑单
     *
     * @param prfomtionID
     */
    @Override
    public void toPassable(String prfomtionID) {

    }

    @Override
    public void deleteGif(long profomtionID) {
        mPressenter.UpdateGift(profomtionID, "");
    }
};