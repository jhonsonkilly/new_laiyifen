package com.lyfen.android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyfen.android.entity.network.OrderDetailEntity;
import com.lyfen.android.ui.activity.qianggou.QiangGouActivity;
import com.ody.p2p.main.R;


/**
 * Created by mac on 2017/12/14.
 */

public class ProductDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    OrderDetailEntity entity;

    LayoutInflater mLayoutInflater;

    Context context;

    View view;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            final ViewHolderOne holderOne = (ViewHolderOne) holder;
            if (!TextUtils.isEmpty(entity.data.orderStatusName)) {
                holderOne.order_state.setText(entity.data.orderStatusName);
                holderOne.order_state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, QiangGouActivity.class));
                    }
                });
            }
            if (!TextUtils.isEmpty(entity.data.orderCanceOperateTypeContext)) {
                holderOne.order_reason.setText(entity.data.orderCanceOperateTypeContext);
            }


            holderOne.tx_name.setText(entity.data.receiver.receiverName);
            holderOne.tx_location.setText(entity.data.receiver.getLocation());
            holderOne.tx_phone.setText(entity.data.receiver.receiverMobile);
        }
        if (getItemViewType(position) == 1) {

            try {
                ViewHolderTwo holderTwo = (ViewHolderTwo) holder;
                holderTwo.total.setText("¥" + entity.data.productAmount + "");
                holderTwo.price1.setText("-¥ " + entity.data.taxAmount + "");
                holderTwo.price2.setText("-¥ " + entity.data.promotionAmount + "");
                holderTwo.price3.setText("-¥ " + entity.data.orderPaidByCoupon + "");
                holderTwo.order_id.setText(entity.data.orderCode + "");
                holderTwo.order_time.setText(entity.data.orderCreateTimeStr + "");
            } catch (Exception e) {

            }


        }


        if (getItemViewType(position) == 2) {


            ViewHolderFour holderFour = (ViewHolderFour) holder;

            ProductDetailItemAdapter adapter = new ProductDetailItemAdapter(context, entity.data.childOrderList.get(position - 1).packageList.get(0).productList);
            holderFour.recyclerView.setAdapter(adapter);

            holderFour.text_peisong.setText(entity.data.childOrderList.get(position - 1).deliveryModeName);

            holderFour.text_yunfei.setText(entity.data.childOrderList.get(position - 1).orderDeliveryFeeAccounting + "");

            holderFour.buy_name.setText(entity.data.childOrderList.get(position - 1).merchantName);

        }


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolderOne(mLayoutInflater.inflate(R.layout.item_personal_mes, parent, false));
        }
        if (viewType == 1) {
            return new ViewHolderTwo(mLayoutInflater.inflate(R.layout.item_personal_pay, parent, false));
        }
        if (viewType == 2) {
            return new ViewHolderFour(mLayoutInflater.inflate(R.layout.item_personal_goods, parent, false));
        } else {
            return null;
        }


    }


    @Override
    public int getItemCount() {
        try {
            return entity.data.childOrderList.size() + 2;
        } catch (Exception e) {
            return 0;
        }

    }

    public void refreshData(View view) {
        this.view = view;
        //notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == getItemCount() - 1) {
            return 1;
        } else {
            return 2;
        }

    }


    public ProductDetailAdapter(OrderDetailEntity entity, Context context, View view) {
        this.view = view;
        this.entity = entity;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {


        TextView order_state;

        TextView order_reason;

        TextView tx_name;

        TextView tx_location;

        TextView tx_phone;


        public ViewHolderOne(View convertView) {

            super(convertView);
            order_state = BaseViewHolder.get(convertView, R.id.order_state);
            order_reason = BaseViewHolder.get(convertView, R.id.order_reason);
            tx_name = BaseViewHolder.get(convertView, R.id.tx_name);

            tx_location = BaseViewHolder.get(convertView, R.id.tx_location);

            tx_phone = BaseViewHolder.get(convertView, R.id.tx_phone);


        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {


        TextView total;
        TextView price1;
        TextView price2;
        TextView price3;
        TextView order_id;
        TextView order_time;


        public ViewHolderTwo(View convertView) {

            super(convertView);
            total = BaseViewHolder.get(convertView, R.id.total);
            price1 = BaseViewHolder.get(convertView, R.id.price1);
            price2 = BaseViewHolder.get(convertView, R.id.price2);
            price3 = BaseViewHolder.get(convertView, R.id.price3);
            order_id = BaseViewHolder.get(convertView, R.id.order_id);
            order_time = BaseViewHolder.get(convertView, R.id.order_time);


        }
    }


    class ViewHolderFour extends RecyclerView.ViewHolder {


        RecyclerView recyclerView;


        TextView text_peisong;
        TextView text_yunfei;

        TextView buy_name;


        public ViewHolderFour(View convertView) {

            super(convertView);
            recyclerView = BaseViewHolder.get(convertView, R.id.recycle_view);
            text_peisong = BaseViewHolder.get(convertView, R.id.text_peisong);
            text_yunfei = BaseViewHolder.get(convertView, R.id.text_yunfei);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            buy_name = BaseViewHolder.get(convertView, R.id.buy_name);


        }
    }


}
