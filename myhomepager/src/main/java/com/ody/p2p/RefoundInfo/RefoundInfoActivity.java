package com.ody.p2p.RefoundInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 售后详情
 * 传参说明：orderCode--用于判断是否可以重新申请售后
 *          orderAfterSalesId--一般情况传orderAfterSalesId，自销佣金传returnCode（orderAfterSalesId，returnCode二选一）
 */

public class RefoundInfoActivity extends BaseActivity implements RefoundInfoView, View.OnClickListener {
    private ImageView img_finish_icon;
    protected TextView tv_order_time, tv_order_code, tv_order_price;
    protected LinearLayout liner_bottom, liner_logits;
    protected TextView tv_logistics_company;
    protected EditText et_num;
    protected String orderAfterSalesId="";
    protected RefoundInfoPressenter mPressenter;
    protected RecyclerView refound_info_list;
    protected TextView tv_order_status;
    protected TextView tv_ok;
    protected TextView tv_modif;
    protected TextView tv_confir_tijiao;
    protected TextView tv_cancleOrAgein;
    protected TextView tv_refound_order_code;
    protected TextView tv_refound_applytimestr;
    protected TextView tv_refound_price;
    protected TextView tv_returnRemark;
    protected TextView tv_refound_reason;
    private String orderCode;//订单编号
    protected RecyclerView recycle_img_list;
    protected LinearLayout ll_refuse;
    protected TextView tv_refuse_des;
    protected RecyclerView rv_refuse_pic;
    protected TextView tv_name;
    protected TextView tv_phone;
    protected TextView tv_address;
    protected LinearLayout ll_logistic;
    protected TextView tv_company;
    protected TextView tv_num;
    protected TextView tv_type;
    protected LinearLayout ll_return_money;
    protected LinearLayout ll_product;
    protected LinearLayout ll_address;
    protected TextView tv_transaction;
    private ImageView iv_address_bg;
    protected int isAftersale=0;//1可售后0不能售后
    private String returnCode;

    @Override
    public void initPresenter() {
        mPressenter = new RefoundInfoImpr(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_refound_info;
    }

    @Override
    public void initView(View view) {
        orderAfterSalesId = getIntent().getStringExtra("orderAfterSalesId");
        orderCode=getIntent().getStringExtra("orderCode");
        returnCode=getIntent().getStringExtra("returnCode");
        tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        img_finish_icon = (ImageView) view.findViewById(R.id.img_finish_icon);
        tv_order_status = (TextView) view.findViewById(R.id.tv_order_status);
        tv_refound_order_code = (TextView) view.findViewById(R.id.tv_refound_order_code);
        tv_refound_applytimestr = (TextView) view.findViewById(R.id.tv_refound_applytimestr);
        tv_refound_price = (TextView) view.findViewById(R.id.tv_refound_price);
        refound_info_list = (RecyclerView) view.findViewById(R.id.refound_info_list);
        tv_confir_tijiao = (TextView) view.findViewById(R.id.tv_confir_tijiao);
        recycle_img_list = (RecyclerView) view.findViewById(R.id.recycle_img_list);
        tv_order_time = (TextView) view.findViewById(R.id.tv_order_time);
        tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);
        tv_refound_reason = (TextView) view.findViewById(R.id.tv_refound_reason);
        tv_returnRemark = (TextView) view.findViewById(R.id.tv_returnRemark);
        tv_order_price = (TextView) view.findViewById(R.id.tv_order_price);
        tv_cancleOrAgein = (TextView) view.findViewById(R.id.tv_cancleOrAgein);
        liner_bottom = (LinearLayout) view.findViewById(R.id.liner_bottom);
        liner_logits = (LinearLayout) view.findViewById(R.id.liner_logits);
        tv_modif = (TextView) view.findViewById(R.id.tv_modif);
        tv_transaction= (TextView) findViewById(R.id.tv_transaction);
        tv_logistics_company= (TextView) findViewById(R.id.tv_logistics_company);
        et_num= (EditText) findViewById(R.id.et_num);
        ll_refuse= (LinearLayout) view.findViewById(R.id.ll_refuse);
        tv_refuse_des=(TextView) view.findViewById(R.id.tv_refuse_des);
        rv_refuse_pic=(RecyclerView) view.findViewById(R.id.rv_refuse_pic);
        ll_logistic =(LinearLayout) view.findViewById(R.id.ll_logistic);
        tv_company=(TextView) view.findViewById(R.id.tv_company);
        tv_num=(TextView) view.findViewById(R.id.tv_num);
        tv_type= (TextView) findViewById(R.id.tv_type);
        ll_return_money= (LinearLayout) findViewById(R.id.ll_return_money);
        ll_product= (LinearLayout) findViewById(R.id.ll_product);
        ll_address= (LinearLayout) findViewById(R.id.ll_address);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        iv_address_bg= (ImageView) findViewById(R.id.iv_address_bg);
        img_finish_icon.setOnClickListener(this);
    }

    @Override
    public void cancel() {
        mPressenter.getOrderInfo(orderAfterSalesId,returnCode);
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        liner_bottom.setVisibility(View.GONE);
        tv_modif.setVisibility(View.GONE);
        tv_cancleOrAgein.setVisibility(View.GONE);
        liner_logits.setVisibility(View.GONE);
    }

    @Override
    public void resume() {
        mPressenter.isAftersale(orderCode);
        mPressenter.getOrderInfo(orderAfterSalesId,returnCode);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_finish_icon) {
            finish();
        } else if (v.getId() == R.id.tv_confir_tijiao) {
            if(TextUtils.isEmpty(tv_logistics_company.getText().toString())){
                ToastUtils.showToast(getString(R.string.please_enter_logistics_company));
                return;
            }
            if(TextUtils.isEmpty(et_num.getText().toString())){
                ToastUtils.showToast(getString(R.string.input_logistics_number));
                return;
            }
            mPressenter.courierNumber(et_num.getText().toString(),tv_logistics_company.getText().toString(),orderAfterSalesId+"");
        }else if(v.getId()==R.id.tv_logistics_company){
            Bundle bundle=new Bundle();
            JumpUtils.ToActivityFoResult(JumpUtils.CHOOSE_LOGISTICS,bundle,1001,this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if(requestCode==1001){
            String logisticsName=data.getStringExtra("logisticsName");
            tv_logistics_company.setText(logisticsName);
        }
    }

    @Override
    public void isAftersale(String json) {
        try {
            JSONObject jsonObject=new JSONObject(json);
            String code=jsonObject.getString("code");
            if(code.equals("0")){
                isAftersale=jsonObject.getJSONObject("data").getInt("isAfterSale");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveOK() {
        mPressenter.getOrderInfo(orderAfterSalesId,returnCode);
    }

    @Override
    public void getData(final AfterSaleDetailBean.Data data) {
        //退货状态（1退换货申请待审核，2退换货申请审核通过，3退换货申请审核不通过，4：待收件，5：退换货验货通过，6：退换货验货不通过，8:退货完成9：已撤消退换货）
        ll_return_money.setVisibility(View.GONE);
        if(data.getType()==2){//（1、退款2、退货4、换货）
            if(data.getReturnStatus()==1){
                tv_order_status.setText(R.string.terrace_platform);
                tv_cancleOrAgein.setText(R.string.cancel_return);
            }else if(data.getReturnStatus()==2){
                tv_order_status.setText(R.string.return_of);
            }else if(data.getReturnStatus()==4){
                tv_order_status.setText(R.string.return_of);
            }else if(data.getReturnStatus()==3){
                tv_order_status.setText(R.string.audit_not_through);
            }else if(data.getReturnStatus()==6){
                tv_order_status.setText(R.string.inspection_not_through);
            }else if(data.getReturnStatus()==9){
                tv_order_status.setText(R.string.tocancle);
            }else if(data.getReturnStatus()==5||data.getReturnStatus()==8){
                if(data.getReturnStatus()==5){
                    tv_order_status.setText(R.string.terrace_platform);
                }else if(data.getReturnStatus()==8){
                    tv_order_status.setText(R.string.After_complete);
                    tv_order_status.setTextColor(getResources().getColor(R.color.main_title_color));
                    tv_transaction.setVisibility(View.VISIBLE);
                    tv_transaction.setText(getString(R.string.refund_water_)+data.refundNumber);
                }
                ll_return_money.setVisibility(View.VISIBLE);
                tv_refound_price.setText("\t"+getString(R.string.money_symbol) + UiUtils.getDoubleForDouble(data.getActualReturnAmount()));
            }
            tv_type.setText(getString(R.string.tv_aftersale_type)+getString(R.string.sales_return));
            if(data.getReturnStatus()==4||data.getReturnStatus()==5||data.getReturnStatus()==2){
                ll_address.setVisibility(View.VISIBLE);
                tv_name.setText(data.consigneeName);
                tv_phone.setText(data.consigneeMobile);
                tv_address.setText(data.consigneeAddress);
            }else{
                ll_address.setVisibility(View.GONE);
                iv_address_bg.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(data.getReturnReason())){
                tv_refound_reason.setText(getString(R.string.return_reason_) + data.getReturnReason());
            }else{
                tv_refound_reason.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(data.getReturnRemark())){
                tv_returnRemark.setText(getString(R.string.Return_instructions)+data.getReturnRemark());
            }else{
                tv_returnRemark.setVisibility(View.GONE);
            }
        }else if(data.getType()==4){
            if(data.getReturnStatus()==5){
                if(data.exchangeStatus==1){// 1：待出库 2:已出库 3:已完成
                    tv_order_status.setText(R.string.terrace_platform);
                    tv_order_status.setTextColor(getResources().getColor(R.color.main_title_color));
                    tv_cancleOrAgein.setVisibility(View.GONE);
                }else if(data.exchangeStatus==2){
                    liner_bottom.setVisibility(View.VISIBLE);
                    tv_order_status.setText(R.string.The_goods_have_been_sent_again);
                    if(!TextUtils.isEmpty(data.returnCourierNumber)){
                        ll_logistic.setVisibility(View.VISIBLE);
                        tv_num.setText(data.returnCourierNumber);
                    }else{
                        ll_logistic.setVisibility(View.GONE);
                    }
                    tv_cancleOrAgein.setVisibility(View.VISIBLE);
                    tv_cancleOrAgein.setText(R.string.confirm_take_delivery_of_goods);
                    tv_cancleOrAgein.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPressenter.confirmReceivePro(data.getId());
                        }
                    });
                    tv_modif.setVisibility(View.VISIBLE);
                    tv_modif.setText(R.string.show_logistics);
                    tv_modif.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("orderCode",data.getOrderCode()+"");
                            bundle.putString("returnCode",data.getReturnCode()+"");
                            bundle.putString("exchangeCode",data.exchangeCode);
                            bundle.putInt("Logisticstype",3);
                            JumpUtils.ToActivity(JumpUtils.LOGISTICS,bundle);
                        }
                    });
                }
            }else if(data.getReturnStatus()==8){
                liner_bottom.setVisibility(View.VISIBLE);
                tv_order_status.setText(R.string.After_complete);
                tv_order_status.setTextColor(getResources().getColor(R.color.main_title_color));
                tv_cancleOrAgein.setVisibility(View.GONE);
                tv_modif.setVisibility(View.VISIBLE);
                tv_modif.setText(R.string.show_logistics);
                tv_modif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("orderCode",data.getOrderCode()+"");
                        bundle.putString("returnCode",data.getReturnCode()+"");
                        bundle.putString("exchangeCode",data.exchangeCode);
                        bundle.putInt("Logisticstype",3);
                        JumpUtils.ToActivity(JumpUtils.LOGISTICS,bundle);
                    }
                });
            }else if(data.getReturnStatus()==3){
                tv_order_status.setText(R.string.audit_not_through);
            }else if(data.getReturnStatus()==6){
                tv_order_status.setText(R.string.inspection_not_through);
            }else if(data.getReturnStatus()==9){
                tv_order_status.setText(R.string.tocancle);
            }else if(data.getReturnStatus()==1){
                tv_order_status.setText(R.string.terrace_platform);
                tv_cancleOrAgein.setText(R.string.cancel_exchange_goods);
            }else if(data.getReturnStatus()==4||data.getReturnStatus()==2){
                tv_order_status.setText(R.string.exchange_goods_now);
            }
            tv_type.setText(getString(R.string.tv_aftersale_type)+getString(R.string.exchange_goods));
            if(data.getReturnStatus()==4||data.getReturnStatus()==2){
                ll_address.setVisibility(View.VISIBLE);
                tv_name.setText(data.consigneeName);
                tv_phone.setText(data.consigneeMobile);
                tv_address.setText(data.consigneeAddress);
            }else{
                ll_address.setVisibility(View.GONE);
                iv_address_bg.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(data.getReturnReason())){
                tv_refound_reason.setText(getString(R.string.exchange_goods_instructions_t) + data.getReturnReason());
            }else{
                tv_refound_reason.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(data.getReturnRemark())){
                tv_returnRemark.setText(getString(R.string.exchange_goods_instructions)+data.getReturnRemark());
            }else{
                tv_returnRemark.setVisibility(View.GONE);
            }
        }else if(data.getType()==1){
            if(data.getCancelStatus()==1){//取消订单退款状态（1：待审核 2：待退款 3：已退款 4 审核不通过）
                tv_order_status.setText(R.string.to_audit);
            }else if(data.getCancelStatus()==2){
                tv_order_status.setText(R.string.for_refund);
            }else if(data.getCancelStatus()==3){
                tv_order_status.setText(R.string.have_refund);
                ll_return_money.setVisibility(View.VISIBLE);
                tv_refound_price.setText("\t"+getString(R.string.money_symbol) + UiUtils.getDoubleForDouble(data.getActualReturnAmount()));
                tv_transaction.setVisibility(View.VISIBLE);
                tv_transaction.setText(getString(R.string.refund_water_)+data.refundNumber);
            }else if(data.getCancelStatus()==4){
                tv_order_status.setText(R.string.audit_not_through);
            }
            tv_type.setText(getString(R.string.tv_aftersale_type)+getString(R.string.refund));
            if(!TextUtils.isEmpty(data.getReturnReason())){
                tv_refound_reason.setText(getString(R.string.refund_reason_) + data.getReturnReason());
            }else{
                tv_refound_reason.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(data.getReturnRemark())){
                tv_returnRemark.setText(getString(R.string.refund_instructions_t)+data.getReturnRemark());
            }else{
                tv_returnRemark.setVisibility(View.GONE);
            }
            ll_address.setVisibility(View.GONE);
            iv_address_bg.setVisibility(View.GONE);
        }
        if(data.getReturnStatus()==4||data.getReturnStatus()==2){
            if(TextUtils.isEmpty(data.getCourierNumber())||TextUtils.isEmpty(data.logisticsCompany)){
                liner_logits.setVisibility(View.VISIBLE);
                tv_logistics_company.setOnClickListener(this);
                tv_confir_tijiao.setOnClickListener(this);
            }else{
                liner_logits.setVisibility(View.GONE);
            }
        }
        tv_refound_order_code.setText(getString(R.string.after_code) + data.getReturnCode());
        tv_refound_applytimestr.setText(getString(R.string.apply_time) + data.getApplyTimeStr());
        if(data.getType()==2||data.getType()==4){
            if(!TextUtils.isEmpty(data.getServiceDesc())&&data.getReturnStatus()==6){
                ll_refuse.setVisibility(View.VISIBLE);
                tv_refuse_des.setVisibility(View.VISIBLE);
                tv_refuse_des.setText(data.getServiceDesc());
            }
            if(!TextUtils.isEmpty(data.auditReason)&&data.getReturnStatus()==3){
                ll_refuse.setVisibility(View.VISIBLE);
                tv_refuse_des.setVisibility(View.VISIBLE);
                tv_refuse_des.setText(data.auditReason);
            }
        }else if(data.getType()==1){
            if(!TextUtils.isEmpty(data.auditReason)&&data.getCancelStatus()==4){
                ll_refuse.setVisibility(View.VISIBLE);
                tv_refuse_des.setVisibility(View.VISIBLE);
                tv_refuse_des.setText(data.auditReason);
            }
        }

        if(data.merchantPicUrlList!=null&&data.merchantPicUrlList.size()>0){
            ll_refuse.setVisibility(View.VISIBLE);
            rv_refuse_pic.setVisibility(View.VISIBLE);
            LinearLayoutManager merchantRefuse=new LinearLayoutManager(this);
            merchantRefuse.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_refuse_pic.setLayoutManager(merchantRefuse);
            rv_refuse_pic.setAdapter(new RecycleAdapter(this,data.merchantPicUrlList));
        }else{
            rv_refuse_pic.setVisibility(View.GONE);
        }
        if (null != data.getPicUrlList() && data.getPicUrlList().size() > 0) {
            recycle_img_list.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycle_img_list.setLayoutManager(linearLayoutManager);
            recycle_img_list.setAdapter(new RecycleAdapter(getContext(), data.getPicUrlList()));
        } else {
            recycle_img_list.setVisibility(View.GONE);
        }

        if(data.getMerchantProductVOs()!=null&&data.getMerchantProductVOs().size()>0){
            ReoundinfoAdapter reoundinfoAdapter = new ReoundinfoAdapter(getContext(), data.getMerchantProductVOs());
            refound_info_list.setLayoutManager(new LinearLayoutManager(this));
            refound_info_list.setAdapter(reoundinfoAdapter);
        }else{
            ll_product.setVisibility(View.GONE);
        }
        tv_order_code.setText(getString(R.string.order_code_) + data.getOrderCode());
        tv_order_time.setText(getString(R.string.order_time_) + data.getOrderCreateTimeStr());
        if(data.getType()==4||data.getType()==2){
            if(data.getReturnStatus()==1){
                liner_bottom.setVisibility(View.VISIBLE);
                tv_cancleOrAgein.setVisibility(View.VISIBLE);
                tv_modif.setVisibility(View.VISIBLE);
                tv_cancleOrAgein.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog dialog = new CustomDialog(getContext(), getResources().getString(R.string.cancleapply));
                        dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                            @Override
                            public void Confirm() {
                                mPressenter.cancelReturnProduct(data.getReturnCode());
                            }
                        });
                        dialog.show();
                    }
                });
                tv_modif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("OrderCode",data.getOrderCode()+"");
                        bundle.putBoolean("isEdit", true);
                        Gson gson = new Gson();
                        String str = gson.toJson(data,AfterSaleDetailBean.Data.class);
                        bundle.putString("AfterSaleDetailBean",str);
                        JumpUtils.ToActivity(JumpUtils.AFTERSALE,bundle);
                        finish();
                    }
                });
            }else if(data.getReturnStatus()==3||data.getReturnStatus()==6){
                if(isAftersale==1){
                    liner_bottom.setVisibility(View.VISIBLE);
                    tv_cancleOrAgein.setVisibility(View.GONE);
                    tv_modif.setVisibility(View.VISIBLE);
                    tv_modif.setText(R.string.apply_again);
                    tv_modif.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //again
                            Bundle bundle=new Bundle();
                            bundle.putString("OrderCode",data.getOrderCode()+"");
                            JumpUtils.ToActivity(JumpUtils.AFTERSALE,bundle);
                            finish();
                        }
                    });
                }

            }
        }


    }
}
