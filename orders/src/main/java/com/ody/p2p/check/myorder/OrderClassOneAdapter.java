package com.ody.p2p.check.myorder;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.MyListView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by tangmeijuan on 16/6/14.
 */
public class OrderClassOneAdapter extends android.widget.BaseAdapter {

    protected List<ConfirmOrderBean.DataEntity.MerchantListEntity> mData;
    protected Context context;
    protected OrderClassTwoAdapter adapter;
    public OrderClassOneAdapter(Context mContext) {
        mData=new ArrayList<>();
        this.context=mContext;
    }

    public void addData(List<ConfirmOrderBean.DataEntity.MerchantListEntity> data){
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<ConfirmOrderBean.DataEntity.MerchantListEntity> getData(){
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ConfirmOrderBean.DataEntity.MerchantListEntity getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.confirm_order_one_item, null);
            holder.tvItemTotal= (TextView) view.findViewById(R.id.tv_item_total);
            holder.tvMerchantName= (TextView) view.findViewById(R.id.tv_merchant_name);
            holder.lvProduct= (MyListView) view.findViewById(R.id.lv_product);
            holder.tv_delievry= (TextView) view.findViewById(R.id.tv_delievry);
            holder.tv_num= (TextView) view.findViewById(R.id.tv_num);
            holder.et_merchant_remark= (EditText) view.findViewById(R.id.et_merchant_remark);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        showItem(holder,i);
        return view;
    }

    public void showItem(final ViewHolder holder, final int i){

        holder.tvMerchantName.setText(context.getString(R.string.order)+(i+1));
        if(UiUtils.getDoubleForDouble(mData.get(i).deliveryFee).equals("0.00")){
            holder.tv_delievry.setText(R.string.no_mail);
        }else{
            holder.tv_delievry.setText(context.getString(R.string.feright)+":"+context.getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(mData.get(i).deliveryFee));
        }

        holder.tvItemTotal.setText(UiUtils.getMoneyDouble(mData.get(i).amount));

        holder.tv_num.setText("\t"+mData.get(i).totalNum+"\t");
        if(mData.get(i).productList!=null&&mData.get(i).productList.size()>0){
            adapter=new OrderClassTwoAdapter(context);
            holder.lvProduct.setAdapter(adapter);
            adapter.addData(mData.get(i).productList);
        }
        if(!TextUtils.isEmpty(mData.get(i).remark)){
            holder.et_merchant_remark.setText(mData.get(i).remark);
        }
        holder.et_merchant_remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!TextUtils.isEmpty(holder.et_merchant_remark.getText().toString())&&!holder.et_merchant_remark.getText().toString().equals(mData.get(i).remark)){
                        saveRemark(holder.et_merchant_remark.getText().toString(),mData.get(i).id);
                    }
                }
            }
        });

    }

    private void saveRemark(String remark, String id) {
        Map<String,String> params=new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("remark",remark);
        params.put("id",id);
        OkHttpManager.postAsyn(Constants.SAVE_REMARK ,new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseRequestBean response) {
            }
        },params);
    }


    public class ViewHolder {
         public TextView tvMerchantName;
         public MyListView lvProduct;
         public TextView tvItemTotal;
         public TextView tv_delievry;
         public TextView tv_num;
        public EditText et_merchant_remark;

    }

}
