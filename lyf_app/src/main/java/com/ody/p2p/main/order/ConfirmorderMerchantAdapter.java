package com.ody.p2p.main.order;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.myorder.OrderClassTwoAdapter;
import com.ody.p2p.main.R;
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
 * Created by ${tang} on 2016/12/6.
 */

public class ConfirmorderMerchantAdapter extends BaseAdapter {

    private List<ConfirmOrderBean.DataEntity.MerchantListEntity> mData;
    private List<ConfirmOrderBean.DataEntity.MerchantDeliveryModeListEntity> deliveryMode;
    private Activity context;
    private ConfirmOrderBean.DataEntity.MerchantDeliveryModeListEntity matchMode;

    public ConfirmorderMerchantAdapter(Activity context){
        mData=new ArrayList<>();
        this.context=context;
    }

    public void addData(List<ConfirmOrderBean.DataEntity.MerchantListEntity> data){
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setDeliveryMode(List<ConfirmOrderBean.DataEntity.MerchantDeliveryModeListEntity> deliveryMode){
        this.deliveryMode=deliveryMode;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ConfirmOrderBean.DataEntity.MerchantListEntity getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_order,parent,false);
            holder.tvItemTotal= (TextView) convertView.findViewById(R.id.tv_item_total);
            holder.tvMerchantName= (TextView) convertView.findViewById(R.id.tv_merchant_name);
            holder.lvProduct= (MyListView) convertView.findViewById(R.id.lv_product);
            holder.tv_delievry= (TextView) convertView.findViewById(R.id.tv_delievry);
            holder.tv_num= (TextView) convertView.findViewById(R.id.tv_num);
            holder.et_merchant_remark= (EditText) convertView.findViewById(R.id.et_merchant_remark);
            holder.tv_delievryfee= (TextView) convertView.findViewById(R.id.tv_delievryfee);
            holder.tv_dispatch_des= (TextView) convertView.findViewById(R.id.tv_dispatch_des);
            holder.tv_tag= (TextView) convertView.findViewById(R.id.tv_tag);
            holder.rl_dispatch= (RelativeLayout) convertView.findViewById(R.id.rl_dispatch);
            holder.tv_address= (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_shop_name= (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.ll_mendian= (LinearLayout) convertView.findViewById(R.id.ll_mendian);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvMerchantName.setText(mData.get(position).merchantName);

        holder.tvItemTotal.setText(UiUtils.getMoneyDouble(mData.get(position).amount));
        holder.tv_num.setText("\t"+mData.get(position).totalNum+"\t");
        if(mData.get(position).productList!=null&&mData.get(position).productList.size()>0){
            OrderClassTwoAdapter adapter=new OrderClassTwoAdapter(context);
            holder.lvProduct.setAdapter(adapter);
            adapter.addData(mData.get(position).productList);
        }

        //配送方式
        if(deliveryMode!=null&&deliveryMode.size()>0){
            //展示默认选中的
            for(int i=0;i<deliveryMode.size();i++){
                if(deliveryMode.get(i).id.equals(mData.get(position).id)){
                    matchMode=deliveryMode.get(i);
                    if(deliveryMode.get(i).deliveryModeList!=null&&deliveryMode.get(i).deliveryModeList.size()>0){
                       for(int j=0;j<deliveryMode.get(i).deliveryModeList.size();j++){
                           if(deliveryMode.get(i).deliveryModeList.get(j).isDefault==1){
                               holder.tv_delievry.setText(deliveryMode.get(i).deliveryModeList.get(j).name);
                               if(!TextUtils.isEmpty(deliveryMode.get(i).deliveryModeList.get(j).name)){
                                   holder.tv_dispatch_des.setVisibility(View.VISIBLE);
                                   holder.tv_dispatch_des.setText(deliveryMode.get(i).deliveryModeList.get(j).promiseDate);
                               }else{
                                   holder.tv_dispatch_des.setVisibility(View.GONE);
                               }
                               if(!TextUtils.isEmpty(deliveryMode.get(i).deliveryModeList.get(j).freight)){
                                   if(Float.parseFloat(deliveryMode.get(i).deliveryModeList.get(j).freight)>0){
                                       holder.tv_delievryfee.setText("运费:￥"+UiUtils.getDoubleForDouble(deliveryMode.get(i).deliveryModeList.get(j).freight));
                                   }else{
                                       holder.tv_delievryfee.setText("免邮");
                                   }
                               }
                               if(deliveryMode.get(i).deliveryModeList.get(j).isTakeTheir==1){
                                   holder.tv_tag.setVisibility(View.VISIBLE);
                                   holder.ll_mendian.setVisibility(View.VISIBLE);
                                   holder.tv_address.setText(deliveryMode.get(i).deliveryModeList.get(j).pickAddress);
                                   holder.tv_shop_name.setText(deliveryMode.get(i).deliveryModeList.get(j).pickName);
                               }else{
                                   holder.ll_mendian.setVisibility(View.GONE);
                                   holder.tv_tag.setVisibility(View.GONE);
                               }
                           }
                       }
                    }
                }
            }
           /* holder.rl_dispatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("dispatchMode",matchMode);
                    JumpUtils.ToActivity(JumpUtils.DISTRIBUTION,bundle);
                }
            });*/
        }


        if(!TextUtils.isEmpty(mData.get(position).remark)){
            holder.et_merchant_remark.setText(mData.get(position).remark);
        }

        holder.et_merchant_remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!TextUtils.isEmpty(holder.et_merchant_remark.getText().toString())&&!holder.et_merchant_remark.getText().toString().equals(mData.get(position).remark)){
                        saveRemark(holder.et_merchant_remark.getText().toString(),mData.get(position).id);
                    }
                }
            }
        });
        return convertView;
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

    class ViewHolder {
        private TextView tvMerchantName;
        private MyListView lvProduct;
        private TextView tvItemTotal;
        private TextView tv_delievry;
        private TextView tv_num;
        private EditText et_merchant_remark;
        private TextView tv_dispatch_des;
        private TextView tv_delievryfee;
        private TextView tv_tag;
        private RelativeLayout rl_dispatch;
        private TextView tv_address;
        private TextView tv_shop_name;
        private LinearLayout ll_mendian;
    }
}
