package com.ody.p2p.main.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.myorder.ConfirmOrderPresenter;
import com.ody.p2p.check.myorder.ConfirmOrderPresenterImpl;
import com.ody.p2p.check.myorder.ConfirmOrderView;
import com.ody.p2p.check.myorder.ExpenseListAdapter;
import com.ody.p2p.check.myorder.NoAddressDialog;
import com.ody.p2p.check.myorder.OverSeaPurchaseDialog;
import com.ody.p2p.check.myorder.SubmitOrderBean;
import com.ody.p2p.main.R;
import com.ody.p2p.main.eventbus.TaklingDataEventMessage;
import com.ody.p2p.main.shopcart.shopcartview.ShopCartAdvertisementWindow;
import com.ody.p2p.main.specificfunction.DataUtils;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.retrofit.adviertisement.AdData;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.MyListView;
import com.ody.p2p.views.MyScrollView;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.ProgressDialog.ProductChangeDialog;
import com.ody.p2p.views.scrollbanner.ScrollBanner;
import com.tendcloud.appcpa.Order;

import org.datatist.sdk.data.CouponInfo;
import org.datatist.sdk.data.CouponItem;
import org.datatist.sdk.data.OrderInfo;
import org.datatist.sdk.data.ProductInfo;
import org.datatist.sdk.data.ProductItem;
import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${tang} on 2016/11/25.
 */
public class LYFConfirmOrderActivity extends BaseActivity implements ConfirmOrderView, View.OnClickListener, CustomDialog.CustomDialogCallBack, NoAddressDialog.AddAddressCallback {

    String receiverId = "";//地址的ID
    private ImageView backIcon;
    protected TextView tvReceiverName;
    protected TextView tvReceiveAddress;
    protected RelativeLayout rlAddress;
    protected RelativeLayout rlNoAdress;
    protected MyListView productListView;
    protected TextView tvTip;
    protected CheckBox cbPoints;
    protected TextView tvSubmitOrder;
    protected TextView tvTotalPrice;
    protected TextView tvTransportFee;
    protected TextView tvPhone;
    protected TextView tv_invoice;
    protected TextView tv_pay_name;
    protected RelativeLayout rl_bill;
    protected RelativeLayout rl_pay_way;
    protected RelativeLayout rl_coupon;
    protected LinearLayout ll_dozen;
    protected TextView tv_coupon_tip;
    protected TextView tv_coupon_content;
    protected TextView tv_coupon;
    protected RelativeLayout rl_brokerage;
    private MyScrollView sv_all;
    private ScrollBanner announcement;
    private ConfirmOrderPresenter presenter;
    private String mpid;
    private String merchantId;
    private String num;
    private boolean isQuickpurchase = false;//true--商品详情页直接购买 false--购物车

    private LinearLayout ll_bottom;
    private MyListView lv_expenseList;

    private TextView tv_money;
    private TextView tv_discount;

    private LinearLayout ll_second_skill;
    private TextView tv_second_skill;

    private TextView tv_youdian;
    private CheckBox cb_udian;
    private TextView tv_edian;
    private CheckBox cb_edian;
    private TextView tv_udes;
    private TextView tv_edes;

    // TODO: 2017/10/13 优惠券分组
//    private LyfChooseCouponPopwindow popwindow;

    private OrderCommonPopwindow popwindow;

    private ConfirmorderMerchantAdapter adapter;

    private CustomDialog customDialog;

    private ArrayList<String> un_invoice_pros;
    private ConfirmOrderBean.DataEntity mOrderBean;

    private CountDownTimer timer;
    private TextView tv_theme;

    private NoAddressDialog noAddressDialog;
    private TextView tv_noaddress;

    private ImageView iv_guidence;

    private int receiverStatus = 0;
    private EditText et_brokerage;
    private TextView tv_yuan;
    //    private KeyboardLayout kl_root;
    private String residualAmount;

    @Override
    public int bindLayout() {
        return R.layout.layout_lyf_confirmorder;
    }

    @Override
    public void initView(View view) {
//        kl_root = (KeyboardLayout) findViewById(R.id.kl_root);
        backIcon = (ImageView) findViewById(R.id.backIcon);
        tvReceiverName = (TextView) findViewById(R.id.tv_receiver_name);
        tvReceiveAddress = (TextView) findViewById(R.id.tv_receive_address);
        rlAddress = (RelativeLayout) findViewById(R.id.rl_address);
        rlNoAdress = (RelativeLayout) findViewById(R.id.rl_no_adress);
        productListView = (MyListView) findViewById(R.id.productListView);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        cbPoints = (CheckBox) findViewById(R.id.cb_points);
        tvSubmitOrder = (TextView) findViewById(R.id.tv_submit_order);
        tv_coupon_content = (TextView) findViewById(R.id.tv_coupon_content);
        tv_coupon_tip = (TextView) findViewById(R.id.tv_coupon_tip);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvTransportFee = (TextView) findViewById(R.id.tv_transport_fee);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        rl_bill = (RelativeLayout) findViewById(R.id.rl_bill);
        rl_brokerage = (RelativeLayout) findViewById(R.id.rl_brokerage);
        et_brokerage = (EditText) findViewById(R.id.et_brokerage);
        tv_yuan = (TextView) findViewById(R.id.tv_yuan);
        tv_pay_name = (TextView) findViewById(R.id.tv_pay_name);
        tv_invoice = (TextView) findViewById(R.id.tv_invoice);
        ll_dozen = (LinearLayout) findViewById(R.id.ll_dozen);
        rl_pay_way = (RelativeLayout) findViewById(R.id.rl_pay_way);
        rl_coupon = (RelativeLayout) findViewById(R.id.rl_coupon);
        sv_all = (MyScrollView) findViewById(R.id.sv_all);
        tv_coupon = (TextView) findViewById(R.id.tv_coupon);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        lv_expenseList = (MyListView) findViewById(R.id.lv_expenseList);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_discount = (TextView) findViewById(R.id.tv_discount);
        ll_second_skill = (LinearLayout) findViewById(R.id.ll_second_skill);
        tv_second_skill = (TextView) findViewById(R.id.tv_second_skill);
        announcement = (ScrollBanner) findViewById(R.id.announcement);
        cb_edian = (CheckBox) findViewById(R.id.cb_edian);
        tv_edian = (TextView) findViewById(R.id.tv_edian);
        cb_udian = (CheckBox) findViewById(R.id.cb_udian);
        tv_youdian = (TextView) findViewById(R.id.tv_youdian);
        tv_edes = (TextView) findViewById(R.id.tv_edes);
        tv_udes = (TextView) findViewById(R.id.tv_udes);
        tv_theme = (TextView) findViewById(R.id.tv_theme);
        tv_noaddress = (TextView) findViewById(R.id.tv_noaddress);
        customDialog = new CustomDialog(this, "您确定要取消订单回到购物车吗？", 4);
        customDialog.SetCustomDialogCallBack(this);
        noAddressDialog = new NoAddressDialog(this);
        noAddressDialog.setCallBack(this);

        backIcon.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        iv_guidence = (ImageView) findViewById(R.id.iv_guidence);
//        kl_root.setOnkbdStateListener(new KeyboardLayout.onKybdsChangeListener() {
//            @Override
//            public void onKeyBoardStateChange(int state) {
//                if (state == KeyboardLayout.KEYBOARD_STATE_HIDE) {
//                    if (et_brokerage.hasFocus()) {
//                        if (!TextUtils.isEmpty(et_brokerage.getText().toString())) {
//                            presenter.saveBrokerage(et_brokerage.getText().toString());
//                        } else {
//                            presenter.saveBrokerage(0 + "");
//                        }
//                    }
//                }
//            }
//        });
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        mpid = getIntent().getStringExtra(Constants.SP_ID);
        merchantId = getIntent().getStringExtra(Constants.MERCHANT_ID);
        num = getIntent().getStringExtra(Constants.CART_NUMBER);
        isQuickpurchase = getIntent().getBooleanExtra(Constants.BUY_TYPE, false);
        presenter.getAdvertisement();
        if (OdyApplication.getInt("isFirstSubmitOrder", 0) == 0) {
            iv_guidence.setVisibility(View.VISIBLE);
            OdyApplication.putInt("isFirstSubmitOrder", 1);
            iv_guidence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_guidence.setVisibility(View.GONE);
                    if (isQuickpurchase) {
                        presenter.quickpurchase(mpid, num, merchantId, 0);
                    } else {
                        presenter.initOrder(0, "");
                    }
                }
            });
        } else {
            iv_guidence.setVisibility(View.GONE);
            if (isQuickpurchase) {
                presenter.quickpurchase(mpid, num, merchantId, 0);
            } else {
                presenter.initOrder(0, "");
            }
        }

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                if (et_brokerage != null && !StringUtils.isEmpty(et_brokerage.getText().toString().trim())) {
                    if (Double.parseDouble(et_brokerage.getText().toString()) > Double.parseDouble(residualAmount)) {
                        ToastUtils.showToast(getString(com.ody.p2p.check.R.string.into_commission_less_than_money));
                        presenter.saveBrokerage(0 + "");
                    } else {
                        presenter.saveBrokerage(et_brokerage.getText().toString());
                    }
                } else {
                    presenter.saveBrokerage(0 + "");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.showOrder();
        ConfirmOrderBean.DataEntity.Errors errors = (ConfirmOrderBean.DataEntity.Errors) intent.getSerializableExtra("error");
        if (null != errors && null != errors.data) {
            final ProductChangeDialog productChangeDialog = new ProductChangeDialog(this, errors);
            productChangeDialog.setCallBack(new ProductChangeDialog.CallBack() {
                @Override
                public void goBacktoShopCar() {
                    productChangeDialog.dismiss();
                    finish();
                }

                @Override
                public void goOnCheck() {
                    productChangeDialog.dismiss();
                    presenter.initOrder(1, "");
                }

                @Override
                public void goAddress() {
                    productChangeDialog.dismiss();
                    JumpUtils.ToActivity(JumpUtils.SELECT_ADDRESS);
                }

                @Override
                public void deleteMpids(String mpIds) {
                    productChangeDialog.dismiss();
                    presenter.initOrder(1, mpIds);
                }
            });
            productChangeDialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backIcon) {
            customDialog.show();
        } else if (view.getId() == R.id.rl_no_adress) {
            if (receiverStatus == 1) {
                Bundle bundle = new Bundle();
                bundle.putInt("isEdit", 0);
                bundle.putBoolean("isFromOrder", true);
                JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS, bundle);
            } else if (receiverStatus == 2) {
                JumpUtils.ToActivity(JumpUtils.SELECT_ADDRESS);
            }
        } else if (view.getId() == R.id.rl_bill) {//发票
//            ToastUtils.showToast("未开发完成，待提测，敬请期待");
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String str = gson.toJson(view.getTag(), ConfirmOrderBean.DataEntity.OrderInvoiceEntity.class);
            bundle.putString("invoice_content", str);
            bundle.putStringArrayList("proUrl_list", un_invoice_pros);
            JumpUtils.ToActivity(JumpUtils.INVOICE, bundle);
        } else if (view.getId() == R.id.rl_pay_way) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("paywaylist", (Serializable) view.getTag());
            JumpUtils.ToActivity(JumpUtils.CHOOSEPAYWAY, bundle);
        } else if (view.getId() == R.id.rl_coupon) {
            if (popwindow == null) {
                // TODO: 2017/10/13 优惠券分组
//                popwindow = new LyfChooseCouponPopwindow(this);
//                popwindow.setListener(new LyfChooseCouponPopwindow.SubmitListener() {
//                    @Override
//                    public void showOrder() {
//                        presenter.showOrder();
//                    }
//                });

                popwindow = new OrderCommonPopwindow(this);
                popwindow.setListener(new OrderCommonPopwindow.SubmitListener() {
                    @Override
                    public void showOrder() {
                        presenter.showOrder();
                    }
                });
            }
            popwindow.showAtLocation(rl_coupon, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (view.getId() == R.id.tv_submit_order) {
            if (TextUtils.isEmpty(receiverId)) {
                if (receiverStatus == 1) {
                    ToastUtils.showToast("请添加收货地址！");
                } else if (receiverStatus == 2) {
                    ToastUtils.showToast("请选择收货地址！");
                }
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
    public void resume() {
    }

    @Override
    public void destroy() {

    }

    @Override
    public void initPresenter() {
        presenter = new ConfirmOrderPresenterImpl(this);
    }

    @Override
    public void result(final ConfirmOrderBean confirmOrderBean, int isInitOrder) {
        mOrderBean = confirmOrderBean.data;
        if (confirmOrderBean == null || confirmOrderBean.data == null) {
            return;
        }
        sv_all.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        receiverStatus = confirmOrderBean.data.receiverStatus;
        residualAmount = confirmOrderBean.data.brokerage.residualAmount;
        if (confirmOrderBean.data.receiver != null) {
            rlNoAdress.setVisibility(View.GONE);
            rlAddress.setVisibility(View.VISIBLE);
            tvReceiverName.setText(confirmOrderBean.data.receiver.name);
            receiverId = confirmOrderBean.data.receiver.receiverId;

            tvReceiveAddress.setText(confirmOrderBean.data.receiver.provinceName +
                    confirmOrderBean.data.receiver.cityName + confirmOrderBean.data.receiver.areaName +
                    confirmOrderBean.data.receiver.detailAddress);
            tvPhone.setText(confirmOrderBean.data.receiver.mobile);
            rlAddress.setOnClickListener(this);
            if (isInitOrder == 1) {
                if (confirmOrderBean.data.receiverStatus == 1) {
                    noAddressDialog.setNoAddress(0);
                    noAddressDialog.show();
                    tv_noaddress.setText(getResources().getString(R.string.noaddress_add));
                } else if (confirmOrderBean.data.receiverStatus == 2) {
                    noAddressDialog.setNoAddress(1);
                    noAddressDialog.show();
                    tv_noaddress.setText(getResources().getString(R.string.local_noaddress_add));
                }
            }
        } else {
            rlNoAdress.setVisibility(View.VISIBLE);
            rlAddress.setVisibility(View.GONE);
            rlNoAdress.setOnClickListener(this);
            if (isInitOrder == 1) {
                if (confirmOrderBean.data.receiverStatus == 1) {
                    noAddressDialog.setNoAddress(0);
                    noAddressDialog.show();
                    tv_noaddress.setText(R.string.noaddress_add);
                } else if (confirmOrderBean.data.receiverStatus == 2) {
                    noAddressDialog.setNoAddress(1);
                    noAddressDialog.show();
                    tv_noaddress.setText(R.string.local_noaddress_add);
                }
            }
        }
        tv_money.setText("待支付:  ¥" + UiUtils.getDoubleForDouble(confirmOrderBean.data.amount));
        if (!TextUtils.isEmpty(confirmOrderBean.data.promotionSavedAmount) && !TextUtils.isEmpty(confirmOrderBean.data.couponAmount)) {
            String discount = new BigDecimal(confirmOrderBean.data.promotionSavedAmount).add(new BigDecimal(confirmOrderBean.data.couponAmount)).toString();
            tv_discount.setText("优惠:  ¥" + UiUtils.getDoubleForDouble(discount));
        }
        tvSubmitOrder.setOnClickListener(this);

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

        if (confirmOrderBean.data.orderInvoice != null) {
            if (confirmOrderBean.data.orderInvoice.merchantSupportInvoiceType == 0) {
                tv_invoice.setText("不支持发票");
            } else {
                //得到不支持发票的商品图片列表，用于增值税发票展示
                if (confirmOrderBean.data.merchantList != null && confirmOrderBean.data.merchantList.size() > 0) {
                    un_invoice_pros = new ArrayList<>();
                    for (int i = 0; i < confirmOrderBean.data.merchantList.size(); i++) {
                        if (confirmOrderBean.data.merchantList.get(i).productList != null && confirmOrderBean.data.merchantList.get(i).productList.size() > 0) {
                            for (int j = 0; j < confirmOrderBean.data.merchantList.get(i).productList.size(); j++) {
                                if (confirmOrderBean.data.merchantList.get(i).productList.get(j).isVatInvoice == 0) {
                                    un_invoice_pros.add(confirmOrderBean.data.merchantList.get(i).productList.get(j).picUrl);
                                }
                            }
                        }
                    }
                }
                if (confirmOrderBean.data.orderInvoice.invoice != null) {
                    if (confirmOrderBean.data.orderInvoice.invoice.invoiceType == 0) {
                        tv_invoice.setText("不需要发票");
                    } else if (confirmOrderBean.data.orderInvoice.invoice.invoiceType == 1) {
                        tv_invoice.setText(confirmOrderBean.data.orderInvoice.invoice.invoiceTitleContent);
//                        if(!TextUtils.isEmpty(confirmOrderBean.data.orderInvoice.invoice.invoiceTitleContent)){
//                            tv_invoice.setText(confirmOrderBean.data.orderInvoice.invoice.invoiceTitleContent);
//                        }else if(!TextUtils.isEmpty(confirmOrderBean.data.orderInvoice.invoice.unitName)){
//                            tv_invoice.setText(confirmOrderBean.data.orderInvoice.invoice.unitName);
//                        }
                    } else if (confirmOrderBean.data.orderInvoice.invoice.invoiceType == 2) {
                        tv_invoice.setText("增值税发票");
                    }
                    rl_bill.setTag(confirmOrderBean.data.orderInvoice);
                    rl_bill.setOnClickListener(this);
                }
            }
        }
        //佣金
        if (confirmOrderBean.data.brokerage != null && confirmOrderBean.data.brokerage.isAvailable == 1) {
            rl_brokerage.setClickable(true);
            tv_yuan.setText("元");
            et_brokerage.setHint(getString(R.string.into_money_surplus) + confirmOrderBean.data.brokerage.residualAmount + getString(R.string.dollars) + getString(R.string.can_used));

        } else {
            et_brokerage.setHint("0.00元");
            tv_yuan.setText("不可用");
            et_brokerage.setKeyListener(null);
            rl_brokerage.setClickable(false);

        }
        if (confirmOrderBean.data.merchantList != null && confirmOrderBean.data.merchantList.size() > 0) {
            adapter = new ConfirmorderMerchantAdapter(this);
            productListView.setAdapter(adapter);
            if (confirmOrderBean.data.merchantDeliveryModeList != null && confirmOrderBean.data.merchantDeliveryModeList.size() > 0) {
                adapter.setDeliveryMode(confirmOrderBean.data.merchantDeliveryModeList);
            }
            adapter.addData(confirmOrderBean.data.merchantList);
        }

        if (confirmOrderBean.data.expenseList != null && confirmOrderBean.data.expenseList.size() > 0) {

            if (confirmOrderBean.data.expenseList.get(0) != null) {
                tvTotalPrice.setText("￥" + UiUtils.getDoubleForDouble(confirmOrderBean.data.expenseList.get(0).value));
            }
            if (confirmOrderBean.data.expenseList.get(1) != null) {
                tvTransportFee.setText("￥" + UiUtils.getDoubleForDouble(confirmOrderBean.data.expenseList.get(1).value));
            }

            if (confirmOrderBean.data.expenseList.size() > 2) {
                List<ConfirmOrderBean.DataEntity.ExpenseEntity> expense = new ArrayList<>();
                lv_expenseList.setVisibility(View.VISIBLE);
                for (int i = 0; i < confirmOrderBean.data.expenseList.size(); i++) {
                    if (i > 1) {
                        expense.add(confirmOrderBean.data.expenseList.get(i));
                    }
                }
                ExpenseListAdapter adapter = new ExpenseListAdapter(expense);
                lv_expenseList.setAdapter(adapter);
            } else {
                lv_expenseList.setVisibility(View.GONE);
            }
        }

        if (confirmOrderBean.data.countdown > 0) {
            ll_second_skill.setVisibility(View.VISIBLE);
            tv_theme.setText(confirmOrderBean.data.countdownTheme);
            startCountdown(confirmOrderBean.data.countdown * 1000);
        } else {
            ll_second_skill.setVisibility(View.GONE);
        }

        if (null != confirmOrderBean.data.points && confirmOrderBean.data.points.isAvailable == 1) {
            cbPoints.setEnabled(true);
            cbPoints.setButtonDrawable(R.drawable.checkbox_coupon);
            if (confirmOrderBean.data.points.discount > 0) {
                cbPoints.setChecked(true);
            } else {
                cbPoints.setChecked(false);
            }
            tvTip.setText("可用" + confirmOrderBean.data.points.canUseCount + getString(R.string.integral) + getString(R.string.can_use_can_arrive) + UiUtils.getMoneyDouble((confirmOrderBean.data.points.canUseCount / confirmOrderBean.data.points.rate) + 0.005) + getContext().getString(com.ody.p2p.check.R.string.dollars));
            cbPoints.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        presenter.savePoints(confirmOrderBean.data.points.canUseCount);
                    } else {
                        presenter.savePoints(0);
                    }
                }
            });
        } else {
            cbPoints.setButtonDrawable(R.drawable.cart_invalid);
            cbPoints.setEnabled(false);
            tvTip.setText(R.string.not_integral_can_arrive);

        }

        if (confirmOrderBean.data.allCoupon != null) {
            if (confirmOrderBean.data.allCoupon.availableQuantity > 0) {
                tv_coupon_tip.setText(confirmOrderBean.data.allCoupon.availableQuantity + getString(R.string.spread) + getString(R.string.can_used));
                tv_coupon_content.setText("-" + getString(R.string.money_symbol) + UiUtils.getDoubleForDouble(confirmOrderBean.data.allCoupon.preferentialPrice) + "");
            } else {
                tv_coupon_tip.setVisibility(View.GONE);
                tv_coupon_content.setText(R.string.not_can_used);
                tv_coupon_content.setTextColor(getResources().getColor(R.color.main_title_color));
            }
        }

        //悠点卡
        if (confirmOrderBean.data.orderUCard != null) {
            if (confirmOrderBean.data.orderUCard.isAvailable == 0) {
                cb_udian.setButtonDrawable(R.drawable.cart_invalid);
                tv_udes.setText("无可用悠点卡");
                cb_udian.setChecked(false);
                cb_udian.setEnabled(false);
            } else {
                cb_udian.setEnabled(true);
                cb_udian.setButtonDrawable(R.drawable.checkbox_coupon);
                tv_udes.setText("可使用悠点卡抵扣￥" + UiUtils.getDoubleForDouble(confirmOrderBean.data.orderUCard.availableAmount) + "元");
                if (confirmOrderBean.data.orderUCard.selected == 0) {
                    cb_udian.setChecked(false);
                } else {
                    cb_udian.setChecked(true);
                }
                cb_udian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cb_udian.isChecked()) {
                            presenter.saveUdian(1);
                        } else {
                            presenter.saveUdian(0);
                        }
                    }
                });
            }

        }

        //伊点卡
        if (confirmOrderBean.data.orderECard != null) {
            if (confirmOrderBean.data.orderECard.isAvailable == 0) {
                cb_edian.setButtonDrawable(R.drawable.cart_invalid);
                tv_edes.setText("无可用伊点卡");
                cb_edian.setChecked(false);
                cb_edian.setEnabled(false);
            } else {
                cb_edian.setEnabled(true);
                cb_edian.setButtonDrawable(R.drawable.checkbox_coupon);
                tv_edes.setText("可使用伊点卡抵扣￥" + UiUtils.getDoubleForDouble(confirmOrderBean.data.orderECard.availableAmount) + "元");
                if (confirmOrderBean.data.orderECard.selected == 0) {
                    cb_edian.setChecked(false);
                } else {
                    cb_edian.setChecked(true);
                }
                cb_edian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cb_edian.isChecked()) {
                            presenter.saveEdian(1);
                        } else {
                            presenter.saveEdian(0);
                        }
                    }
                });
            }
        }


        if (confirmOrderBean.data.overseaPurchase == 1 && confirmOrderBean.data.isIdCardVerifyRequired == 0) {

            final OverSeaPurchaseDialog overseaAddressDialog = new OverSeaPurchaseDialog(this, 1);

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

    private void startCountdown(final long time) {
        if (timer != null) {
            return;
        }
        timer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv_second_skill.setText(DateTimeUtils.getCountDown(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                ll_second_skill.setVisibility(View.GONE);
            }
        };
        timer.start();
    }


    @Override
    public void toLogin() {
        JumpUtils.ToActivity(JumpUtils.LOGIN);
    }

    @Override
    public void dealOrder(SubmitOrderBean bean) {//提交订单成功
        TaklingDataEventMessage msg = new TaklingDataEventMessage();
        msg.setAction(TaklingDataEventMessage.ONPLACEORDER);
        Map<String, String> map = new HashMap<>();
        map.put("accountid", OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE, "没有获取到用户名"));




        OrderInfo orderInfo = new OrderInfo(mpid, Double.parseDouble(mOrderBean.amount), Double.parseDouble(mOrderBean.totalDeliveryFee), mOrderBean.receiver.detailAddress, "");
        CouponInfo couponInfo = new CouponInfo();

        //CouponItem couponItem = new CouponItem("", Double.parseDouble(mOrderBean.couponAmount));
        try{
            for(ConfirmOrderBean.DataEntity.AllCouponEntity.OrderCoupons coupons:mOrderBean.allCoupon.orderCoupons){
                CouponItem couponItem = new CouponItem(coupons.themeTitle, Double.parseDouble(coupons.couponValue));
                couponInfo.add(couponItem);
            }
        }catch (Exception e){

        }



        ProductInfo productInfo = new ProductInfo();

        //TrackerKernel tracker = ((YourApplication) YourActivity.this.getApplication()).getTracker();
        //Track.track().order().trackOrder(orderInfo, couponInfo, productInfo, eventMap).submit(tracker);
        if (mOrderBean != null) {

            int amount = DataUtils.parseStringToTalkingPrice(mOrderBean.amount);
            Order Order = com.tendcloud.appcpa.Order.createOrder(mOrderBean.orderCode, amount, "CNY");


            if (mOrderBean.merchantList != null && mOrderBean.merchantList.size() > 0) {
                for (ConfirmOrderBean.DataEntity.MerchantListEntity Orderbean : mOrderBean.merchantList) {
                    if (Orderbean.productList != null && Orderbean.productList.size() > 0) {
                        for (ConfirmOrderBean.DataEntity.MerchantListEntity.ProductListEntity productBean : Orderbean.productList) {
                            Order.addItem(productBean.mpId, productBean.mpType + "", productBean.name, DataUtils.parseStringToTalkingPrice(productBean.price), productBean.num);

                            ProductItem ProductItem = new ProductItem(productBean.mpId, productBean.name, DataUtils.parseStringToTalkingPrice(productBean.price), DataUtils.parseStringToTalkingPrice(productBean.price), productBean.num, "");
                            productInfo.add(ProductItem);
                        }
                    }
                }
            }
            msg.setOrder(Order);

            msg.setNewOrder(orderInfo,couponInfo,productInfo);

        }
        msg.setExtra(map);
        EventBus.getDefault().post(msg);
        if (bean.data != null) {
            if (bean.data.isPaid == 1) {//0:未支付 1:已支付
                Bundle bd = new Bundle();
                bd.putString(Constants.ORDER_ID, bean.data.orderCode);
                JumpUtils.ToActivity(JumpUtils.PAYSUCCESS, bd);
            } else {
                Bundle bd = new Bundle();
                bd.putString(Constants.ORDER_ID, bean.data.orderCode);
                bd.putString(Constants.USER_ID, bean.data.userId);
                bd.putString(Constants.ORDER_MONEY, bean.data.amount + "");
                bd.putString(Constants.ORDER_DELIVERYfEE, bean.data.deliveryFee + "");
                bd.putInt("isfromNative", 1);
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
                presenter.initOrder(1, "");
            }

            @Override
            public void goAddress() {
                JumpUtils.ToActivity(JumpUtils.SELECT_ADDRESS);
            }

            @Override
            public void deleteMpids(String mpIds) {
                productChangeDialog.dismiss();
                presenter.initOrder(1, mpIds);
            }
        });
        productChangeDialog.show();
    }

    @Override
    public void showOutNumberDialog(String title) {
        final CustomDialog customDialog = new CustomDialog(this, title, 200);
        customDialog.setConfirmCallBack(new CustomDialog.ConfirmOrderOutNumberCallback() {
            @Override
            public void thinkAgain() {
                customDialog.dismiss();
                finish();
            }

            @Override
            public void goOn() {
                customDialog.dismiss();
                presenter.quickpurchase(mpid, num, merchantId, 1);
            }
        });
        customDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (announcement.runFlag) {
            announcement.stopScroll();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void savePointsFail() {
        cbPoints.setChecked(false);
    }

    @Override
    public void showMoneyExceedDialog() {
        final OverSeaPurchaseDialog overSeaPurchaseDialog = new OverSeaPurchaseDialog(this, 2);
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
    public void announcementList(final AdData response) {
        if (response.notice_settle != null && response.notice_settle.size() > 0) {
            announcement.setVisibility(View.VISIBLE);
            announcement.setList(response.notice_settle);
            announcement.setStyle(ScrollBanner.FOR_SHOPCART);
            announcement.setImage(R.drawable.cart_notice);
            announcement.setImageClose(R.drawable.cart_notice_remove);
            if (!announcement.runFlag) {
                announcement.startScroll();
            }
            final ShopCartAdvertisementWindow window = new ShopCartAdvertisementWindow(getContext(), response.notice_settle);
            announcement.setItemClick(new ScrollBanner.ItemClickListener() {
                @Override
                public void click(int positon) {
                    window.showAtLocation(sv_all, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            });
        } else {
            announcement.setVisibility(View.GONE);
        }
    }

    @Override
    public void onErr() {
        showFailed(true, 0);
        viewFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.initOrder(0, "");
            }
        });
    }

    @Override
    public void onNetworkErr() {
        showFailed(true, 1);
        viewFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.initOrder(0, "");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            customDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void Confirm() {
        finish();
    }


    @Override
    public void addAddress() {
        if (receiverStatus == 1) {
            Bundle bundle = new Bundle();
            bundle.putInt("isEdit", 0);
            bundle.putBoolean("isFromOrder", true);
            JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS, bundle);
        } else if (receiverStatus == 2) {
            JumpUtils.ToActivity(JumpUtils.SELECT_ADDRESS);
        }
    }
}
