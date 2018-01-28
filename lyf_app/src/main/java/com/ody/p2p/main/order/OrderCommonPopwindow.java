package com.ody.p2p.main.order;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.coupon.UseCouponBean;
import com.ody.p2p.main.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${tang} on 2016/12/5.
 */

public class OrderCommonPopwindow extends PopupWindow implements AdapterView.OnItemClickListener {

    private SubmitListener listener;

    //添加优惠券相关
    private ImageView iv_back;
    private LinearLayout ll_add_coupon;
    private ImageView iv_add_close;
    private EditText et_cardnum;
    private EditText et_password;
    private TextView tv_submit;

    //优惠券列表相关
    private LinearLayout ll_coupon_list;
    private TextView tv_add;
    private ImageView iv_close;
    private CheckBox cb_check;
    private ListView lv_coupon;
    private TextView tv_choose_submit;

    private CouponUseAdapter adapter;


    public OrderCommonPopwindow(Context context){//type--1 收银台 2 优惠券
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.view_popwindow,null);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);

        iv_back= (ImageView) view.findViewById(R.id.iv_back);
        et_cardnum= (EditText) view.findViewById(R.id.et_cardnum);
        et_password= (EditText) view.findViewById(R.id.et_password);
        ll_add_coupon= (LinearLayout) view.findViewById(R.id.ll_add_coupon);
        iv_add_close= (ImageView) view.findViewById(R.id.iv_add_close);
        tv_submit= (TextView) view.findViewById(R.id.tv_submit);

        ll_coupon_list= (LinearLayout) view.findViewById(R.id.ll_coupon_list);
        iv_close= (ImageView) view.findViewById(R.id.iv_close);
        tv_add= (TextView) view.findViewById(R.id.tv_add);
        cb_check= (CheckBox) view.findViewById(R.id.cb_check);
        lv_coupon= (ListView) view.findViewById(R.id.lv_coupon);
        tv_choose_submit= (TextView) view.findViewById(R.id.tv_choose_submit);

        lv_coupon.setOnItemClickListener(this);

        adapter=new CouponUseAdapter(context);
        lv_coupon.setAdapter(adapter);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList(true);
            }
        });
        iv_add_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_cardnum.getText().toString())){
                    ToastUtils.showToast("请输入卡号");
                    return;
                }
                addCoupon(et_cardnum.getText().toString());
            }
        });


        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList(false);
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(adapter.getCount()>0){
                        for(int i=0;i<adapter.getCount();i++){
                            adapter.getItem(i).selected=0;
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        tv_choose_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_check.isChecked()){
                    saveUseCoupon("");
                    dismiss();
                }else{
                    chooseCoupon();
                }

            }
        });

        useCouponlist();

        this.setContentView(view);
    }

    private void showList(boolean ishow){
        if(ishow){
            ll_coupon_list.setVisibility(View.VISIBLE);
            ll_add_coupon.setVisibility(View.GONE);
        }else{
            ll_add_coupon.setVisibility(View.VISIBLE);
            ll_coupon_list.setVisibility(View.GONE);
        }
    }

    private void chooseCoupon(){
        String couponId="";
        if(adapter.getCount()>0){
            for(int i=0;i<adapter.getCount();i++){
                if(adapter.getItem(i).selected==1){
                    couponId=adapter.getItem(i).couponId;
                }
            }
        }
        if(!TextUtils.isEmpty(couponId)){
            dismiss();
            saveUseCoupon(couponId);
        }else{
            ToastUtils.showToast("请选择优惠券");
        }
    }

    private void useCouponlist() {
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.USE_COUPON,params, new OkHttpManager.ResultCallback<UseCouponBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(UseCouponBean response) {
                if(response!=null&&response.code.equals("0")){
                    if(response.data!=null&&response.data.coupons!=null&&response.data.coupons.size()>0){
                        adapter.addData(response.data.coupons);
                    }else{
                        cb_check.setChecked(true);
                        cb_check.setEnabled(false);
                    }
                }
            }
        });
    }
    private void saveUseCoupon(String couponId) {
        Map<String,String> params=new HashMap<>();
        if(TextUtils.isEmpty(couponId)){
            params.put("selected",0+"");
            params.put("couponId","0");
        }else{
            params.put("selected",1+"");
            params.put("couponId",couponId+"");
        }
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.SAVE_COUPON, params,new OkHttpManager.ResultCallback<BaseRequestBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    listener.showOrder();
                }
            }
        });
    }

    private void addCoupon(String couponCode) {
        Map<String,String> params=new HashMap<>();
        params.put("couponCode",couponCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.BIND_COUPON, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showShort(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response!=null&&response.code.equals("0")){
                    showList(true);
                    useCouponlist();
                }
            }
        },params);
    }

    public void setListener(SubmitListener listener){
        this.listener=listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cb_check.setChecked(false);
        if(adapter.getItem(position).isAvailable==0){
            return;
        }
        for(int i=0;i<adapter.getCount();i++){
            if(i==position){
                if(adapter.getItem(i).selected==1){
                    adapter.getItem(i).selected=0;
                }else{
                    adapter.getItem(i).selected=1;
                }
            }else{
                adapter.getItem(i).selected=0;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public interface SubmitListener{
        void showOrder();
    }
}
