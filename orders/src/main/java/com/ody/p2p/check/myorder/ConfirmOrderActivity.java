package com.ody.p2p.check.myorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.MyListView;
import com.ody.p2p.views.MyScrollView;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.ProgressDialog.ProductChangeDialog;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;

import java.io.Serializable;

import cn.campusapp.router.annotation.RouterMap;

/**
 * 结算页
 * 从商品详情页进来需要传mpid 商品数量num 商家
 */
@RouterMap("activity://confirmorder")
public class ConfirmOrderActivity extends BaseActivity implements ConfirmOrderView, View.OnClickListener {

    private ImageView backIcon;
    protected TextView tvReceiverName;
    protected TextView tvReceiveAddress;
    protected RelativeLayout rlAddress;
    protected RelativeLayout rlNoAdress;
    protected TextView tv_default;
    protected MyListView productListView;
    protected TextView tvTip;
    protected CheckBox cbPoints;
    protected TextView tvSubmitOrder;
    protected TextView tvPrice;
    protected TextView tvTotalPrice;
    protected TextView tvAccountPrice;
    protected TextView tvTaxFee;
    protected TextView tv_tax;
    protected TextView tvTransportFee;
    protected TextView tvPhone;
    protected TextView tv_invoice;
    protected TextView tv_pay_name;
    protected TextView tv_total_num;
    protected TextView tv_heji;
    protected RelativeLayout rl_bill;
    protected RelativeLayout rl_gift_card;
    protected TextView tv_giftcard_tip;
    protected TextView tv_giftcard_value;
    protected RelativeLayout rl_pay_way;
    protected RelativeLayout rl_coupon;
    protected LinearLayout ll_dozen;
    protected TextView tv_coupon_tip;
    protected TextView tv_coupon_content;
    protected TextView tv_coupon;
    private EditText et_brokerage;
    protected RelativeLayout rl_brokerage;
    private MyScrollView sv_all;
    private RelativeLayout bottomLayout;
    private ConfirmOrderPresenter presenter;
    private String mpid;
    private String merchantId;
    private String num;
    private boolean isQuickpurchase = false;//true--商品详情页直接购买 false--购物车

    protected RelativeLayout rl_promotion;
    protected RelativeLayout rl_coupon_bill;
    protected RelativeLayout rl_giftcard;
    protected RelativeLayout rl_points_bill;
    protected RelativeLayout rl_brokage;
    protected RelativeLayout rl_tax;
    protected TextView tv_coupon_discount;
    protected TextView tv_giftcard_discount;
    protected TextView tv_points_discount;
    protected TextView tv_brokage_discount;
    private TextView tv_coupon_title;


    @Override
    public void initPresenter() {
        presenter = new ConfirmOrderPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_confirm_order;
    }

    @Override
    public void initView(View view) {
        backIcon = (ImageView) findViewById(R.id.backIcon);
        tvReceiverName = (TextView) findViewById(R.id.tv_receiver_name);
        tvReceiveAddress = (TextView) findViewById(R.id.tv_receive_address);
        rlAddress = (RelativeLayout) findViewById(R.id.rl_address);  
        rlNoAdress = (RelativeLayout) findViewById(R.id.rl_no_adress);
        tv_default = (TextView) findViewById(R.id.tv_default);
        productListView = (MyListView) findViewById(R.id.productListView);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        cbPoints = (CheckBox) findViewById(R.id.cb_points);
        tvSubmitOrder = (TextView) findViewById(R.id.tv_submit_order);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tv_coupon_content = (TextView) findViewById(R.id.tv_coupon_content);
        tv_coupon_tip = (TextView) findViewById(R.id.tv_coupon_tip);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvAccountPrice = (TextView) findViewById(R.id.tv_account_price);
        tvTaxFee = (TextView) findViewById(R.id.tv_tax_fee);
        tv_tax = (TextView) findViewById(R.id.tv_tax);
        tvTransportFee = (TextView) findViewById(R.id.tv_transport_fee);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        rl_bill = (RelativeLayout) findViewById(R.id.rl_bill);
        tv_pay_name = (TextView) findViewById(R.id.tv_pay_name);
        tv_invoice = (TextView) findViewById(R.id.tv_invoice);
        rl_gift_card = (RelativeLayout) findViewById(R.id.rl_gift_card);
        tv_giftcard_value= (TextView) findViewById(R.id.tv_giftcard_value);
        tv_giftcard_tip= (TextView) findViewById(R.id.tv_giftcard_tip);
        tv_total_num = (TextView) findViewById(R.id.tv_total_num);
        tv_heji = (TextView) findViewById(R.id.tv_heji);
        ll_dozen = (LinearLayout) findViewById(R.id.ll_dozen);
        rl_pay_way = (RelativeLayout) findViewById(R.id.rl_pay_way);
        rl_coupon = (RelativeLayout) findViewById(R.id.rl_coupon);
        et_brokerage = (EditText) findViewById(R.id.et_brokerage);
        rl_brokerage = (RelativeLayout) findViewById(R.id.rl_brokerage);
        sv_all = (MyScrollView) findViewById(R.id.sv_all);
        bottomLayout = (RelativeLayout) findViewById(R.id.bottomLayout);
//        lv_expenseList= (MyListView) findViewById(R.id.lv_expenseList);
        rl_promotion= (RelativeLayout) findViewById(R.id.rl_promotion);
        rl_coupon_bill= (RelativeLayout) findViewById(R.id.rl_coupon_bill);
        rl_giftcard= (RelativeLayout) findViewById(R.id.rl_giftcard);
        rl_points_bill= (RelativeLayout) findViewById(R.id.rl_points_bill);
        rl_brokage= (RelativeLayout) findViewById(R.id.rl_brokage);
        rl_tax= (RelativeLayout) findViewById(R.id.rl_tax);
        tv_coupon_discount= (TextView) findViewById(R.id.tv_coupon_discount);
        tv_giftcard_discount= (TextView) findViewById(R.id.tv_giftcard_discount);
        tv_points_discount= (TextView) findViewById(R.id.tv_points_discount);
        tv_brokage_discount= (TextView) findViewById(R.id.tv_brokage_discount);
        tv_coupon= (TextView) findViewById(R.id.tv_coupon);
        tv_coupon_title= (TextView) findViewById(R.id.tv_coupon_title);
    }

    @Override
    public void initListener() {
        backIcon.setOnClickListener(this);
    }


    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        mpid = getIntent().getStringExtra(Constants.SP_ID);
        merchantId = getIntent().getStringExtra(Constants.MERCHANT_ID);
        num = getIntent().getStringExtra(Constants.CART_NUMBER);
        isQuickpurchase = getIntent().getBooleanExtra(Constants.BUY_TYPE, false);
        if (isQuickpurchase) {
            presenter.quickpurchase(mpid, num, merchantId,0);
        } else {
            presenter.initOrder(0,"");
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backIcon) {
            finish();
        } else if (view.getId() == R.id.rl_no_adress) {
            JumpUtils.ToActivity(JumpUtils.SELECT_ADDRESS);
        } else if (view.getId() == R.id.rl_bill) {
            Bundle bundle=new Bundle();
            Gson gson = new Gson();
            String str = gson.toJson(view.getTag(),ConfirmOrderBean.DataEntity.OrderInvoiceEntity.class);
            bundle.putString("invoice_content",str);
            JumpUtils.ToActivity(JumpUtils.INVOICE,bundle);
        } else if (view.getId() == R.id.rl_gift_card) {
            JumpUtils.ToActivity(JumpUtils.USE_GIFTCARD);
        } else if (view.getId() == R.id.rl_pay_way) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("paywaylist", (Serializable) view.getTag());
            JumpUtils.ToActivity(JumpUtils.CHOOSEPAYWAY, bundle);
        } else if (view.getId() == R.id.rl_coupon) {
            JumpUtils.ToActivity(JumpUtils.USECOUPON);
        } else if (view.getId() == R.id.tv_submit_order) {
            /**
             * 提交订单前做地址非空判断、和保存用户的留言
             */
            if (TextUtils.isEmpty(tvReceiverName.getText().toString())) {
                ToastUtils.showToast(getString(R.string.add_shipping_address));
                return;
            }
//            if(adapter!=null&&adapter.getCount()>0){
//                for(int i=0;i<adapter.getCount();i++){
//                    if(adapter.getItem(i)!=null&&!TextUtils.isEmpty(adapter.getItem(i).remark)){
//                        presenter.saveRemark(adapter.getItem(i).remark,adapter.getItem(i).id);
//                    }
//                }
//            }
            presenter.submitorder();
        } else if (view.getId() == R.id.rl_address) {
            JumpUtils.ToActivity(JumpUtils.SELECT_ADDRESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 对地址、礼金卡、优惠券等操作后在相应界面做了保存，重新回到结算页在这里重新刷数据showOrder()
     * 注：initOrder()与showOrder()返回数据格式一致
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.showOrder();
    }

    protected OrderClassOneAdapter adapter;

    @Override
    public void result(final ConfirmOrderBean confirmOrderBean, int isInitOrder) {
        if (confirmOrderBean == null || confirmOrderBean.data == null) {
            return;
        }
        sv_all.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.VISIBLE);

        //地址
        if (confirmOrderBean.data.receiver != null) {
            rlNoAdress.setVisibility(View.GONE);
            rlAddress.setVisibility(View.VISIBLE);
            tvReceiverName.setText(confirmOrderBean.data.receiver.name);
            tvReceiveAddress.setText(confirmOrderBean.data.receiver.provinceName +
                    confirmOrderBean.data.receiver.cityName + confirmOrderBean.data.receiver.areaName +
                    confirmOrderBean.data.receiver.detailAddress);
            tvPhone.setText(confirmOrderBean.data.receiver.mobile);
            if (confirmOrderBean.data.receiver.isDefault == 1) {
                tv_default.setVisibility(View.VISIBLE);
            } else {
                tv_default.setVisibility(View.GONE);
            }
        } else {
            rlNoAdress.setVisibility(View.VISIBLE);
            rlAddress.setVisibility(View.GONE);
            rlNoAdress.setOnClickListener(this);
        }

        //支付方式选择
        if (confirmOrderBean.data.payments != null && confirmOrderBean.data.payments.size() > 0) {
            rl_pay_way.setTag(confirmOrderBean.data.payments);
            for (int i = 0; i < confirmOrderBean.data.payments.size(); i++) {
                if (confirmOrderBean.data.payments.get(i) != null) {
                    if (confirmOrderBean.data.payments.get(i).selected == 1) {
                        tv_pay_name.setText(confirmOrderBean.data.payments.get(i).name);
                    }
                }
            }
            if (confirmOrderBean.data.payments.size() > 1) {
                rl_pay_way.setOnClickListener(this);
            }
        }

        //发票
        if (confirmOrderBean.data.orderInvoice != null) {
            if(confirmOrderBean.data.orderInvoice.merchantSupportInvoiceType==0){
                tv_invoice.setText("不支持发票");
            }else{
                if(confirmOrderBean.data.orderInvoice.invoice!=null){
                    if(confirmOrderBean.data.orderInvoice.invoice.invoiceType==0){
                        tv_invoice.setText("不需要发票");
                    }else if(confirmOrderBean.data.orderInvoice.invoice.invoiceType==1){
                        tv_invoice.setText(confirmOrderBean.data.orderInvoice.invoice.invoiceTitleContent);
                    }else if(confirmOrderBean.data.orderInvoice.invoice.invoiceType==2){
                        tv_invoice.setText(confirmOrderBean.data.orderInvoice.invoice.unitName);
                    }
                    rl_bill.setTag(confirmOrderBean.data.orderInvoice);
                    rl_bill.setOnClickListener(this);
                }
            }
        }

        if (confirmOrderBean.data.merchantList != null && confirmOrderBean.data.merchantList.size() > 0) {
            adapter = new OrderClassOneAdapter(this);
            productListView.setAdapter(adapter);
            adapter.addData(confirmOrderBean.data.merchantList);
        }

        //费用清单

//        if(confirmOrderBean.data.expenseList!=null&&confirmOrderBean.data.expenseList.size()>0){
//            ExpenseListAdapter adapter=new ExpenseListAdapter(confirmOrderBean.data.expenseList);
//            lv_expenseList.setAdapter(adapter);
//        }
        //必选
        tvTotalPrice.setText(getString(R.string.money_symbol) + UiUtils.getDoubleForDouble(confirmOrderBean.data.productAmount) + "");
        tvTransportFee.setText("+ " + getString(R.string.money_symbol)+ UiUtils.getDoubleForDouble(confirmOrderBean.data.totalDeliveryFee) + "");

        //可选
        if(UiUtils.getDoubleForDouble(confirmOrderBean.data.promotionSavedAmount).equals("0.00")){
            rl_promotion.setVisibility(View.GONE);
        }else{
            rl_promotion.setVisibility(View.VISIBLE);
            tvAccountPrice.setText("- " + getString(R.string.money_symbol) + UiUtils.getDoubleForDouble(confirmOrderBean.data.promotionSavedAmount) + "");
        }

        if(UiUtils.getDoubleForDouble(confirmOrderBean.data.couponAmount).equals("0.00")){
            rl_coupon_bill.setVisibility(View.GONE);
        }else{
            rl_coupon_bill.setVisibility(View.VISIBLE);
            tv_coupon_discount.setText("- " + getString(R.string.money_symbol) +UiUtils.getDoubleForDouble(confirmOrderBean.data.couponAmount));
        }

        if(UiUtils.getDoubleForDouble(confirmOrderBean.data.giftCardAmount).equals("0.00")){
            rl_giftcard.setVisibility(View.GONE);
        }else{
            rl_giftcard.setVisibility(View.VISIBLE);
            tv_giftcard_discount.setText("- " + getString(R.string.money_symbol) +UiUtils.getDoubleForDouble(confirmOrderBean.data.giftCardAmount));
        }

        if(confirmOrderBean.data == null || confirmOrderBean.data.points == null || UiUtils.getDoubleForDouble(confirmOrderBean.data.points.discount+"").equals("0.00")){
            rl_points_bill.setVisibility(View.GONE);
        }else{
            rl_points_bill.setVisibility(View.VISIBLE);
            tv_points_discount.setText("- " + getString(R.string.money_symbol) +UiUtils.getDoubleForDouble(confirmOrderBean.data.points.discount+""));
        }

        if(confirmOrderBean.data == null || confirmOrderBean.data.brokerage == null || UiUtils.getDoubleForDouble(confirmOrderBean.data.brokerage.usageAmount).equals("0.00")){
            rl_brokage.setVisibility(View.GONE);
        }else{
            rl_brokage.setVisibility(View.VISIBLE);
            tv_brokage_discount.setText("- " + getString(R.string.money_symbol) +UiUtils.getDoubleForDouble(confirmOrderBean.data.brokerage.usageAmount));
        }

        if(UiUtils.getDoubleForDouble(confirmOrderBean.data.totalTax).equals("0.00")){
            rl_tax.setVisibility(View.GONE);
        }else{
            rl_tax.setVisibility(View.VISIBLE);
            tvTaxFee.setText("+ " + getString(R.string.money_symbol) + UiUtils.getDoubleForDouble(confirmOrderBean.data.totalTax) + "");
        }

        tvPrice.setText(UiUtils.getDoubleForDouble(confirmOrderBean.data.amount) + "");

        tvSubmitOrder.setOnClickListener(this);
        rlAddress.setOnClickListener(this);

        //佣金
        if (ll_dozen.getVisibility() == ll_dozen.VISIBLE) {
            if (confirmOrderBean.data.brokerage != null && confirmOrderBean.data.brokerage.isAvailable == 1) {
                et_brokerage.setHint(getString(R.string.into_money_surplus) + confirmOrderBean.data.brokerage.residualAmount + getString(R.string.dollars)+getString(R.string.can_used));
                et_brokerage.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s != null && s.length() > 0){
                            if (Double.parseDouble(et_brokerage.getText().toString()) > Double.parseDouble(confirmOrderBean.data.brokerage.residualAmount)) {
                                ToastUtils.showToast(getString(R.string.into_commission_less_than_money));
                                presenter.saveBrokerage(0+"");
                            } else {
                                presenter.saveBrokerage(et_brokerage.getText().toString());
                            }
                        }else{
                            presenter.saveBrokerage(0+"");
                        }
                    }
                });
            } else {
                et_brokerage.setHint("0.00");
                et_brokerage.setKeyListener(null);
                rl_brokerage.setClickable(false);
            }

            //积分
            if (null != confirmOrderBean.data.points && confirmOrderBean.data.points.isAvailable == 1) {
                cbPoints.setEnabled(true);
                if (confirmOrderBean.data.points.discount > 0) {
                    cbPoints.setChecked(true);
                } else {
                    cbPoints.setChecked(false);
                }
                tvTip.setText(confirmOrderBean.data.points.canUseCount + getString(R.string.integral)+getString(R.string.can_use_can_arrive) + UiUtils.getMoneyDouble(confirmOrderBean.data.points.canUseCount / confirmOrderBean.data.points.rate ) + getContext().getString(R.string.dollars));
                cbPoints.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            presenter.savePoints(confirmOrderBean.data.points.canUseCount);
                        }else{
                            presenter.savePoints(0);
                        }
                    }
                });
            } else {
                cbPoints.setEnabled(false);
                tvTip.setText(R.string.not_integral_can_arrive);
            }

            //优惠券
            if (confirmOrderBean.data.allCoupon != null) {
                if (confirmOrderBean.data.allCoupon.availableQuantity > 0) {
                    tv_coupon_tip.setText(confirmOrderBean.data.allCoupon.availableQuantity + getString(R.string.spread)+getString(R.string.can_used));
                    tv_coupon_content.setText("-"+getString(R.string.money_symbol) +UiUtils.getDoubleForDouble(confirmOrderBean.data.allCoupon.preferentialPrice)+"");
                    rl_coupon.setOnClickListener(this);
                } else {
                    tv_coupon_tip.setVisibility(View.GONE);
                    tv_coupon_content.setText(R.string.not_can_used);
                    tv_coupon_content.setTextColor(getResources().getColor(R.color.main_title_color));
                }
            }

            //礼金卡
            if (confirmOrderBean.data.giftCard != null) {
                if (confirmOrderBean.data.giftCard.availableQuantity > 0) {
                    tv_giftcard_tip.setText(confirmOrderBean.data.giftCard.availableQuantity + getString(R.string.spread)+getString(R.string.can_used));
                    tv_giftcard_value.setText("-"+getString(R.string.money_symbol) +UiUtils.getDoubleForDouble(confirmOrderBean.data.giftCard.selectedBalance)+"");
                } else {
                    tv_giftcard_tip.setVisibility(View.GONE);
                    tv_giftcard_value.setText(R.string.not_can_used);
                    tv_giftcard_value.setTextColor(getResources().getColor(R.color.main_title_color));
                }
            }else{
                rl_gift_card.setVisibility(View.GONE);
            }
        }

        //如果是海购商品，收货地址需要实名认证，如果没有认证弹框提示去认证
        if(confirmOrderBean.data.overseaPurchase==1&&confirmOrderBean.data.isIdCardVerifyRequired==0){
            final OverSeaPurchaseDialog overseaAddressDialog=new OverSeaPurchaseDialog(this,1);
            overseaAddressDialog.setLinstener(new OverSeaPurchaseDialog.OverSeaClick() {
                @Override
                public void goShoppingcar() {
                    overseaAddressDialog.dismiss();
                    finish();
                }

                @Override
                public void goAddress() {
                    overseaAddressDialog.dismiss();
                    JumpUtils.ToActivity(JumpUtils.SELECT_ADDRESS);
                }
            });
            overseaAddressDialog.show();
        }

    }


    @Override
    public void toLogin() {
        JumpUtils.ToActivity(JumpUtils.LOGIN);
    }

    @Override
    public void dealOrder(SubmitOrderBean bean) {
        if (bean.data != null) {
            /**
             * 订单金额被抵用为0 视为已支付，直接跳转支付成功页
             */
            if(bean.data.isPaid==1){//0:未支付 1:已支付
                Bundle bd = new Bundle();
                bd.putString(Constants.ORDER_ID, bean.data.orderCode);
                JumpUtils.ToActivity(JumpUtils.PAYSUCCESS, bd);
            }else{
                Bundle bd =  new Bundle();
                bd.putString(Constants.ORDER_ID, bean.data.orderCode);
                bd.putString(Constants.USER_ID, bean.data.userId);
                bd.putString(Constants.ORDER_MONEY, bean.data.amount + "");
                bd.putString(Constants.ORDER_DELIVERYfEE, bean.data.deliveryFee + "");
                JumpUtils.ToActivity(JumpUtils.PAYONLINE, bd);
            }
            finish();
        }
    }

    @Override
    public void saveAddress() {
        presenter.showOrder();
    }


    @Override
    public void showErrorDialog(ConfirmOrderBean.DataEntity.Errors error) {

        /**
         * 提交订单时商品库存发生变化时，弹框提示继续购买或者返回购物车
         */

        final ProductChangeDialog productChangeDialog = new ProductChangeDialog(this, error);
        productChangeDialog.setCallBack(new ProductChangeDialog.CallBack() {
            @Override
            public void goBacktoShopCar() {
                productChangeDialog.dismiss();
                finish();
            }

            @Override
            public void goOnCheck() {
                productChangeDialog.dismiss();
                presenter.initOrder(1,"");
            }

            @Override
            public void goAddress() {

            }

            @Override
            public void deleteMpids(String mpIds) {

            }
        });
        productChangeDialog.show();
    }

    @Override
    public void showOutNumberDialog(String title) {
        /**
         * 购买数量超出限购数量是弹框提示，选择继续购买超出的数量按原价购买
         */
        final CustomDialog customDialog=new CustomDialog(this,title,200);
        customDialog.setConfirmCallBack(new CustomDialog.ConfirmOrderOutNumberCallback() {
            @Override
            public void thinkAgain() {
                customDialog.dismiss();
                finish();
            }

            @Override
            public void goOn() {
                customDialog.dismiss();
                presenter.quickpurchase(mpid, num, merchantId,1);
            }
        });
        customDialog.show();
    }

    @Override
    public void savePointsFail() {
        cbPoints.setChecked(false);
    }

    @Override
    public void showMoneyExceedDialog() {
        //海购商品金额超出限额
        final OverSeaPurchaseDialog overSeaPurchaseDialog=new OverSeaPurchaseDialog(this,2);
        overSeaPurchaseDialog.setLinstener(new OverSeaPurchaseDialog.OverSeaClick() {
            @Override
            public void goShoppingcar() {
                overSeaPurchaseDialog.dismiss();
                finish();
            }

            @Override
            public void goAddress() {
            }
        });
        overSeaPurchaseDialog.show();
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void announcementList(AdData adData) {

    }

    @Override
    public void onErr() {
        showFailed(true,0);

    }

    @Override
    public void onNetworkErr() {
        showFailed(true,1);
    }
}
