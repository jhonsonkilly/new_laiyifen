package com.ody.p2p.check.orderoinfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.check.aftersale.Bean.ApplyAfterSaleCauseListBean;
import com.ody.p2p.check.aftersale.ChooseRefoundWindow;
import com.ody.p2p.check.orderlist.OrderTabBean;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.MessageUtil;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.MyListView;
import com.ody.p2p.views.ProgressDialog.CustomDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderInfoActivity extends BaseActivity implements OrderInfoView, View.OnClickListener, ChooseRefoundWindow.RefoundCallBack, OrderInfoPopwindow.CallBack {
    protected OrderInfoImpr mPressenter;
    protected ImageView img_orderinfo_finish;
    protected LinearLayout linear_address;
    protected LinearLayout linear_orderback;
    protected TextView tv_orderinfo_code;
    protected TextView tv_order_createtime;
    protected TextView pay_amount;
    protected TextView tv_order_status;
    protected TextView tv_productAmmount;
    protected TextView tv_promotionAmount;
    protected TextView tv_taxAmount;
    protected TextView tv_orderDeliveryFeeAccounting;
    protected TextView tv_order_message;
    protected TextView tv_order_receiverName;
    protected TextView tv_order_receiver_address;
    protected TextView tv_order_pay_way;
    protected TextView tv_one;
    protected TextView tv_two;
    protected TextView tv_more;
    protected String ordercode;
    protected MyListView productListView;
    protected TextView tv_parent_code;
    protected TextView tv_pay_time;
    protected TextView tv_ship_time;
    protected TextView tv_finish_time;
    protected TextView tv_tax;
    private CountDownTimer timer;
    private OrderInfoPopwindow orderInfoPopwindow;
    protected LinearLayout ll_bottom;
    private int[] location;
    private ChooseRefoundWindow mPopupwindow;
    protected List<OrderTabBean> tabs;
    private ImageView iv_more;

    protected RelativeLayout rl_promotion;
    protected RelativeLayout rl_coupon;
    protected RelativeLayout rl_giftcard;
    protected RelativeLayout rl_points;
    protected RelativeLayout rl_brokage;
    protected RelativeLayout rl_tax;
    protected TextView tv_coupon;
    protected TextView tv_gift_card;
    protected TextView tv_points;
    protected TextView tv_brokage;
    private TextView tv_coupon_title;


    @Override
    public Activity getContext() {
        return super.getContext();
    }

    @Override
    public void initPresenter() {
        mPressenter = new OrderInfoImpr(this);
        ordercode = getIntent().getStringExtra(Constants.ORDER_ID);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_info;
    }

    @Override
    public void initView(View view) {
        tv_order_status = (TextView) view.findViewById(R.id.tv_order_status);
        tv_order_message = (TextView) view.findViewById(R.id.tv_order_message);
        tv_order_receiverName = (TextView) view.findViewById(R.id.order_receiverName);
        tv_order_receiver_address = (TextView) view.findViewById(R.id.order_receiver_address);
        tv_order_pay_way = (TextView) view.findViewById(R.id.tv_order_pay_way);
        tv_orderDeliveryFeeAccounting = (TextView) view.findViewById(R.id.tv_orderDeliveryFeeAccounting);
        tv_taxAmount = (TextView) view.findViewById(R.id.tv_taxAmount);
        tv_promotionAmount = (TextView) view.findViewById(R.id.tv_promotionAmount);
        tv_productAmmount = (TextView) view.findViewById(R.id.tv_productAmmount);
        pay_amount = (TextView) view.findViewById(R.id.pay_amount);
        tv_order_createtime = (TextView) view.findViewById(R.id.tv_orderinfo_createtime);
        tv_orderinfo_code = (TextView) view.findViewById(R.id.tv_orderinfo_code);
        linear_address = (LinearLayout) view.findViewById(R.id.linear_address);
        linear_orderback = (LinearLayout) view.findViewById(R.id.linear_orderback);
        img_orderinfo_finish = (ImageView) view.findViewById(R.id.img_orderinfo_finish);
        productListView= (MyListView) view.findViewById(R.id.productListView);
        tv_one= (TextView) findViewById(R.id.tv_one);
        tv_two= (TextView) findViewById(R.id.tv_two);
        tv_more= (TextView) findViewById(R.id.tv_more);
        ll_bottom= (LinearLayout) findViewById(R.id.ll_bottom);
        tv_parent_code= (TextView) findViewById(R.id.tv_parent_code);
        tv_pay_time= (TextView) findViewById(R.id.tv_pay_time);
        tv_ship_time= (TextView) findViewById(R.id.tv_ship_time);
        tv_finish_time= (TextView) findViewById(R.id.tv_finish_time);
        tv_tax= (TextView) findViewById(R.id.tv_tax);
        iv_more= (ImageView) findViewById(R.id.iv_more);

        rl_promotion= (RelativeLayout) findViewById(R.id.rl_promotion);
        rl_coupon=(RelativeLayout) findViewById(R.id.rl_coupon);
        rl_giftcard=(RelativeLayout) findViewById(R.id.rl_giftcard);
        rl_points=(RelativeLayout) findViewById(R.id.rl_points);
        rl_brokage=(RelativeLayout) findViewById(R.id.rl_brokage);
        rl_tax=(RelativeLayout) findViewById(R.id.rl_tax);
        tv_coupon= (TextView) findViewById(R.id.tv_coupon);
        tv_gift_card= (TextView) findViewById(R.id.tv_gift_card);
        tv_points= (TextView) findViewById(R.id.tv_points);
        tv_brokage= (TextView) findViewById(R.id.tv_brokage);

        tv_coupon_title= (TextView) findViewById(R.id.tv_coupon_title);
        tv_coupon_title.setText(RUtils.getStringRes(this,RUtils.COUPON));

        orderInfoPopwindow=new OrderInfoPopwindow(this);
        orderInfoPopwindow.setCallBack(this);
        linear_address.setOnClickListener(this);
        img_orderinfo_finish.setOnClickListener(this);
        iv_more.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
    }

    @Override
    public void resume() {
        if (null != ordercode && ordercode.length() > 0) {
            mPressenter.getOrderInfo(ordercode);
        }
    }

    @Override
    public void destroy() {
        if(timer!=null){
            timer.cancel();
        }
    }


    protected void setTopStyle(int status){
        if (status== 1 || status== 2||status==3) {
            linear_orderback.setBackgroundResource(R.drawable.order_gradual_change_backgroud);
        } else if(status== 8){
            linear_orderback.setBackgroundResource(R.drawable.order_gradual_change2_backgroud);
        }else{
            linear_orderback.setBackgroundResource(R.drawable.cancel_order_gradient);
        }
    }

    @Override
    public void initOrderInfo(final OrderInfoBean.DataBean  orderinfo) {
        if(orderinfo==null){
            return;
        }
        tv_order_status.setText(orderinfo.orderStatusName);
        if(orderinfo.receiver!=null){
            tv_order_receiverName.setText(orderinfo.receiver.receiverName+ "\t\t" + orderinfo.receiver.receiverMobile);
            tv_order_receiver_address.setText(orderinfo.receiver.cityName + "\t" + orderinfo.receiver.areaName + "\t" + orderinfo.receiver.detailAddress);
        }
        tv_order_pay_way.setText(orderinfo.payMethod);
        pay_amount.setText(getString(R.string.money_symbol)+orderinfo.paymentAmount);

        /**
         * 清单
         * 商品总价和运费为必选
         * 其他优惠不为0显示，否则不显示
         */
        //必填
        tv_productAmmount.setText(getString(R.string.money_symbol)+ UiUtils.getDoubleForDouble(orderinfo.productAmount));
        tv_orderDeliveryFeeAccounting.setText("+"+getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(orderinfo.orderDeliveryFeeAccounting)+"");
        //选填
        if(UiUtils.getDoubleForDouble(orderinfo.promotionAmount).equals("0.00")){
            rl_promotion.setVisibility(View.GONE);
        }else{
            rl_promotion.setVisibility(View.VISIBLE);
            tv_promotionAmount.setText("-"+getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(orderinfo.promotionAmount));
        }

        if(UiUtils.getDoubleForDouble(orderinfo.orderPaidByCoupon).equals("0.00")){
            rl_coupon.setVisibility(View.GONE);
        }else{
            rl_coupon.setVisibility(View.VISIBLE);
            tv_coupon.setText("-"+getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(orderinfo.orderPaidByCoupon));
        }

        if(UiUtils.getDoubleForDouble(orderinfo.orderPaidByCard).equals("0.00")){
            rl_giftcard.setVisibility(View.GONE);
        }else{
            rl_giftcard.setVisibility(View.VISIBLE);
            tv_gift_card.setText("-"+getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(orderinfo.orderPaidByCard));
        }

        if(UiUtils.getDoubleForDouble(orderinfo.orderPaidByPoint).equals("0.00")){
            rl_points.setVisibility(View.GONE);
        }else{
            rl_points.setVisibility(View.VISIBLE);
            tv_points.setText("-"+getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(orderinfo.orderPaidByPoint));
        }

        if(UiUtils.getDoubleForDouble(orderinfo.orderPaidByCommission).equals("0.00")){
            rl_brokage.setVisibility(View.GONE);
        }else{
            rl_brokage.setVisibility(View.VISIBLE);
            tv_brokage.setText("-"+getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(orderinfo.orderPaidByCommission));
        }

        if(UiUtils.getDoubleForDouble(orderinfo.taxAmount).equals("0.00")){
            rl_tax.setVisibility(View.GONE);
        }else{
            rl_tax.setVisibility(View.VISIBLE);
            tv_taxAmount.setText(getString(R.string.money_symbol)+orderinfo.taxAmount);
        }


        tv_order_createtime.setText(getString(R.string.creat_time)+":" + orderinfo.orderCreateTimeStr);
        tv_orderinfo_code.setText(getString(R.string.order_coed)+":" + orderinfo.orderCode);
        if(!TextUtils.isEmpty(orderinfo.parentOrderCode)){
            tv_parent_code.setVisibility(View.VISIBLE);
            tv_parent_code.setText(getString(R.string.father_order_number)+":"+orderinfo.parentOrderCode);
        }
        if(!TextUtils.isEmpty(orderinfo.paymentTimeStr)){
            tv_pay_time.setVisibility(View.VISIBLE);
            tv_pay_time.setText(getString(R.string.pay_time)+":"+orderinfo.paymentTimeStr);
        }

        if(!TextUtils.isEmpty(orderinfo.deliveryTimeStr)){
            tv_ship_time.setVisibility(View.VISIBLE);
            tv_ship_time.setText(getString(R.string.deliver_time)+":"+orderinfo.deliveryTimeStr);
        }
        if(!TextUtils.isEmpty(orderinfo.receiveTimeStr)){
            tv_finish_time.setVisibility(View.VISIBLE);
            tv_finish_time.setText(getString(R.string.make_bargain_time)+":"+orderinfo.receiveTimeStr);
        }
        if (null != orderinfo.childOrderList && orderinfo.childOrderList.size() > 0) {
            OrderListAdapter orderListAdapter=new OrderListAdapter();
            orderListAdapter.setData(orderinfo.childOrderList);
            productListView.setAdapter(orderListAdapter);
        }
        if (null != orderinfo.childOrderList && orderinfo.childOrderList.size() > 0) {
            tabs=new ArrayList<>();
            /**
             * 遍历子单，将所有的包裹code和对应的订单code装进列表，查看物流信息到物流界面，获取包裹对应的物流信息
             */
            for(int i=0;i<orderinfo.childOrderList.size();i++){
                if(orderinfo.childOrderList.get(i).packageList!=null&&orderinfo.childOrderList.get(i).packageList.size()>0){
                    for(int j=0;j<orderinfo.childOrderList.get(i).packageList.size();j++){
                        if(!TextUtils.isEmpty(orderinfo.childOrderList.get(i).packageList.get(j).packageCode)){
                            OrderTabBean orderTabBean=new OrderTabBean();
                            orderTabBean.orderStatusName=mContext.getString(R.string.parcel)+(j+1);
                            orderTabBean.orderCode=orderinfo.childOrderList.get(i).orderCode;
                            orderTabBean.packageCode=orderinfo.childOrderList.get(i).packageList.get(j).packageCode;
                            tabs.add(orderTabBean);
                        }
                    }
                }
            }
        }
        // 1待付款 2待发货 3待收货 4待评价 8已完成 10已取消 12送货失败
        setTopStyle(orderinfo.orderStatus);
        showByOrderstatus(orderinfo);
    }

    protected void showByOrderstatus(final OrderInfoBean.DataBean  orderinfo){
        if (orderinfo.orderStatus == 1) {
            tv_more.setVisibility(View.GONE);
            if (orderinfo.cancelTime > 0) {
                runTime(orderinfo.cancelTime);
                tv_one.setBackgroundResource(R.drawable.red_solid_circle_coner);
                tv_one.setText(mContext.getString(R.string.to_pay));
                tv_one.setTextColor(getResources().getColor(R.color.white));
                tv_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString(Constants.ORDER_ID, ordercode);
                        bundle.putString(Constants.USER_ID, orderinfo.userId);
                        bundle.putString(Constants.ORDER_MONEY, orderinfo.paymentAmount);
                        JumpUtils.ToActivity(JumpUtils.PAYONLINE,bundle);
                    }
                });
            }else{
                tv_one.setVisibility(View.GONE);
            }
            tv_two.setVisibility(View.VISIBLE);
            tv_two.setBackgroundResource(R.drawable.grey_stroke_white_solid);
            tv_two.setText(mContext.getString(R.string.cancel_order));
            tv_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog customDialog = new CustomDialog(OrderInfoActivity.this, getString(R.string.confirm_cancel_order));
                    customDialog.show();
                    customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                        @Override
                        public void Confirm() {
                            mPressenter.cancleOrder(ordercode);
                        }
                    });
                }
            });
        }else if(orderinfo.orderStatus==2){
            /**
             * 代发货订单可直接申请退款
             * 根据orderRefund是否为空判断是否已申请退款
             */
            if(orderinfo.orderRefund!=null){
                ll_bottom.setVisibility(View.GONE);
                if(orderinfo.orderRefund.refundStatus==-1){//退款状态.-1是审核中，1是退款中，2是已退款
                    tv_order_status.setText(getString(R.string.refund_in_the_audit));
                    tv_order_message.setText(R.string.submitted_review_please_wait);
                }else if(orderinfo.orderRefund.refundStatus==1){
                    tv_order_status.setText(getString(R.string.A_refund_of));
                    tv_order_message.setText(R.string.audit_pass_will_refund);
                }
            }else{
                tv_order_message.setText(R.string.order_pay_will_deliver);
                tv_two.setVisibility(View.GONE);
                tv_more.setVisibility(View.GONE);
                tv_one.setVisibility(View.VISIBLE);
                tv_one.setText(R.string.apply_refund);
                tv_one.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                tv_one.setTextColor(getResources().getColor(R.color.main_title_color));
                tv_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPressenter.afterSaleReasonlist();
                    }
                });
            }
        }else if(orderinfo.orderStatus==3){
            tv_order_message.setText(R.string.order_give_out);
            tv_one.setVisibility(View.VISIBLE);
            tv_two.setVisibility(View.VISIBLE);
            tv_one.setText(R.string.confirm_take_delivery_of_goods);
            tv_one.setBackgroundResource(R.drawable.red_stroke_white_solid);
            tv_one.setTextColor(getResources().getColor(R.color.theme_color));
            tv_two.setText(R.string.show_logistics);
            tv_two.setBackgroundResource(R.drawable.grey_stroke_white_solid);
            if(orderinfo.canAfterSale==1){
                tv_more.setVisibility(View.VISIBLE);
                tv_more.setText(R.string.apply_after);
                tv_more.setTextColor(getResources().getColor(R.color.main_title_color));
                tv_more.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                tv_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 德生的业务需求：待收货申请售后为仅退款，而不是退货
                         */
                        Bundle bundle=new Bundle();
                        bundle.putString("OrderCode",ordercode);
                        bundle.putString("type",5+"");//仅退款
                        JumpUtils.ToActivity(JumpUtils.AFTERSALE,bundle);
                    }
                });
            }else{
                tv_more.setVisibility(View.GONE);
            }
            tv_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPressenter.confrimReceive(ordercode);
                }
            });
            tv_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("packageList", (Serializable) tabs);
                    JumpUtils.ToActivity(JumpUtils.LOGISTICS,bundle);
                }
            });

        }else if(orderinfo.orderStatus==4||orderinfo.orderStatus==8){
            tv_order_message.setText(R.string.buyer_confirmed_receiving);
            tv_one.setVisibility(View.VISIBLE);
            tv_two.setVisibility(View.VISIBLE);
            tv_more.setVisibility(View.VISIBLE);

            if(orderinfo.canComment==1){
                tv_one.setVisibility(View.VISIBLE);
                tv_one.setText(R.string.to_evaluate);
                tv_one.setBackgroundResource(R.drawable.red_solid_circle_coner);
                tv_one.setTextColor(getResources().getColor(R.color.white));
                tv_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("orderCode",orderinfo.orderCode);
                        JumpUtils.ToActivity(JumpUtils.SHOP_EVALUATE,bundle);
                    }
                });
                if(orderinfo.canAfterSale==1){
                    tv_two.setText(R.string.apply_after);
                    tv_two.setTextColor(getResources().getColor(R.color.main_title_color));
                    tv_two.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                    tv_more.setText(R.string.more);
                    tv_more.setTextColor(getResources().getColor(R.color.theme_color));
                    tv_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("OrderCode",ordercode);
                            JumpUtils.ToActivity(JumpUtils.AFTERSALE,bundle);
                        }
                    });
                    tv_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(orderInfoPopwindow.isShowing()){
                                orderInfoPopwindow.dismiss();
                            }else{
                                location = new int[2];
                                tv_more.getLocationOnScreen(location);
                                orderInfoPopwindow.showAsDropDown(tv_more, location[0]-PxUtils.dipTopx(100)/5,  - (PxUtils.dipTopx(64) +  tv_more.getHeight() + 20));
                            }
                        }
                    });
                }else{
                    tv_two.setText(R.string.show_logistics);
                    tv_two.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                    tv_more.setText(R.string.delect_order);
                    tv_more.setTextColor(getResources().getColor(R.color.main_title_color));
                    tv_more.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                    tv_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("packageList", (Serializable) tabs);
                            JumpUtils.ToActivity(JumpUtils.LOGISTICS,bundle);
                        }
                    });
                    tv_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog customDialog = new CustomDialog(OrderInfoActivity.this, getString(R.string.confirm)+getString(R.string.delect_order)+"?");
                            customDialog.show();
                            customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                                @Override
                                public void Confirm() {
                                    mPressenter.deleteOrder(ordercode);
                                }
                            });

                        }
                    });
                }

            }else{
                if(orderinfo.canAfterSale==1){
                    tv_one.setText(R.string.apply_after);
                    tv_one.setTextColor(getResources().getColor(R.color.main_title_color));
                    tv_one.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                    tv_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("OrderCode",ordercode);
                            JumpUtils.ToActivity(JumpUtils.AFTERSALE,bundle);
                        }
                    });
                }else{
                    tv_one.setVisibility(View.GONE);
                }

                tv_two.setText(R.string.show_logistics);
                tv_two.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                tv_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("packageList", (Serializable) tabs);
                        JumpUtils.ToActivity(JumpUtils.LOGISTICS,bundle);
                    }
                });
                if(orderinfo.canDelete==1){
                    tv_more.setText(R.string.delect_order);
                    tv_more.setTextColor(getResources().getColor(R.color.main_title_color));
                    tv_more.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                    tv_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog customDialog = new CustomDialog(OrderInfoActivity.this, getString(R.string.confirm)+getString(R.string.delect_order)+"?");
                            customDialog.show();
                            customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                                @Override
                                public void Confirm() {
                                    mPressenter.deleteOrder(ordercode);
                                }
                            });
                        }
                    });
                }else{
                    tv_more.setVisibility(View.GONE);
                }
            }

        }else if(orderinfo.orderStatus==10){
            tv_order_message.setText(R.string.order_already_cancel);
            if(orderinfo.orderRefund!=null){
                if(orderinfo.orderRefund.refundStatus==2){
                    tv_order_message.setText(getString(R.string.have_refund)+orderinfo.orderRefund.refundAmount+getString(R.string.dollars)+getString(R.string.to)+"您的账户中");
                }
            }
            tv_one.setVisibility(View.VISIBLE);
            tv_one.setText(R.string.delect_order);
            tv_one.setTextColor(getResources().getColor(R.color.main_title_color));
            tv_one.setBackgroundResource(R.drawable.grey_stroke_white_solid);
            tv_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog customDialog = new CustomDialog(OrderInfoActivity.this, getString(R.string.confirm)+getString(R.string.delect_order)+"?");
                    customDialog.show();
                    customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                        @Override
                        public void Confirm() {
                            mPressenter.deleteOrder(ordercode);
                        }
                    });
                }
            });
        }
    }



    public void runTime(long time) {
        if(timer!=null)return;
        timer = new CountDownTimer(time*1000+5000, 1000) {//给他加5秒，防止后台状态来不及刷新

            @Override
            public void onTick(long millisUntilFinished) {
                tv_order_message.setText(getString(R.string.submit_order_) + DateTimeUtils.formatTime(millisUntilFinished) + getString(R.string.please_pay_will_cancel));
            }

            @Override
            public void onFinish() {
                mPressenter.getOrderInfo(ordercode);
            }
        };
        timer.start();
    }

    @Override
    public void cancleOrder(){
        mPressenter.getOrderInfo(ordercode);
    }

    @Override
    public void aftersaleReasonList(List<ApplyAfterSaleCauseListBean.OrderAfterSalesCauseVOs> list) {
        if(list!=null&&list.size()>0){
            mPopupwindow = new ChooseRefoundWindow(getContext(), list);
            mPopupwindow.setRefoundCallBack(this);
            mPopupwindow.showAtLocation(ll_bottom, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    @Override
    public void deleteOrder() {
        finish();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_orderinfo_finish){
            finish();
        }else if(v.getId()==R.id.iv_more){
            MessageUtil.setMegScan(getContext(), iv_more);
        }
    }

    @Override
    public void chooseRefound(ApplyAfterSaleCauseListBean.OrderAfterSalesCauseVOs key) {
        mPopupwindow.dismiss();
        mPressenter.applyRefund(ordercode,key.getKey()+"");
    }

    @Override
    public void delete() {
        orderInfoPopwindow.dismiss();
        CustomDialog customDialog = new CustomDialog(OrderInfoActivity.this, getString(R.string.confirm_cancel_order));
        customDialog.show();
        customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
            @Override
            public void Confirm() {
                mPressenter.deleteOrder(ordercode);
            }
        });
    }

    @Override
    public void logistics() {
        orderInfoPopwindow.dismiss();
        Bundle bundle=new Bundle();
        bundle.putSerializable("packageList", (Serializable) tabs);
        JumpUtils.ToActivity(JumpUtils.LOGISTICS,bundle);
    }
}
