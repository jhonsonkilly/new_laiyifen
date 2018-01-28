package com.ody.p2p.RefoundList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.myhomepager.R;
import com.ody.p2p.RefoundInfo.RefoundInfoActivity;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.UiUtils;

import java.util.List;

/**
 * Created by ody on 2016/6/30.
 */
public class RefoundListAdapter extends RecyclerView.Adapter<RefoundListAdapter.viewholder> {
    Context mContext;
    List<RefoundBaseBean> mData;

    public RefoundListAdapter(Context mContext, List<RefoundBaseBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setListData(List<RefoundBaseBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void addListData(List<RefoundBaseBean> mData) {
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(mContext).inflate(R.layout.myhomepager_item_refound_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final viewholder holder, final int position) {
        final AfterSaleBean.OrderRefundVOs data = mData.get(position).getOrderRefundVOs();
        holder.tv_deal_price.setText(mContext.getString(R.string.transaction_amount) + UiUtils.getMoney(mContext, data.getApplyReturnAmount()));
        holder.recy_salefround.setLayoutManager(new LinearLayoutManager(mContext));
        RefoundItmeListAdapter adapter = new RefoundItmeListAdapter(data.getAfterSalesProductVOs());
        holder.recy_salefround.setAdapter(adapter);

        holder.order_status.setTextColor(mContext.getResources().getColor(R.color.orange));
        if (mData.get(position).getType() == 1) {//退款
            holder.text_refoundList_name.setText(mContext.getString(R.string.refound)+"  " + DateTimeUtils.formatDateTime(data.getApplyTime(), DateTimeUtils.DF_YYYY_MM_DD));
            if (data.getCancelStatus() == 1) {
                holder.order_status.setText(mContext.getString(R.string.refund_in_the_audit));
            } else if (data.getCancelStatus() == 2) {
                holder.order_status.setText(mContext.getString(R.string.A_refund_of));
            } else if (data.getCancelStatus() == 3) {
                holder.order_status.setText(mContext.getString(R.string.refound_completed));
                holder.order_status.setTextColor(mContext.getResources().getColor(R.color.green));
            } else {
                holder.order_status.setText(mContext.getString(R.string.tocancle));
            }
        } else {//退货
            holder.text_refoundList_name.setText(mContext.getString(R.string.refund_in_the_audit)+"  " + DateTimeUtils.formatDateTime(data.getApplyTime(), DateTimeUtils.DF_YYYY_MM_DD));
            if (data.getReturnStatus() == 1) {
                holder.order_status.setText(mContext.getString(R.string.return_audit));
            } else if (data.getReturnStatus() == 2 || data.getReturnStatus() == 4) {
                holder.order_status.setText(mContext.getString(R.string.return_of));
            } else if (data.getReturnStatus() == 8) {
                holder.order_status.setText(mContext.getString(R.string.return_completed));
                holder.order_status.setTextColor(mContext.getResources().getColor(R.color.green));
            } else if (data.getReturnStatus() == 5) {
                holder.order_status.setText(mContext.getString(R.string.A_refund_of));
            } else {
                holder.order_status.setText(mContext.getString(R.string.tocancle));
            }
        }

        holder.tv_seedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看商品详情
                Intent intent = new Intent(mContext, RefoundInfoActivity.class);
                intent.putExtra("orderType", mData.get(position).getType());
                if (mData.get(position).getType() == 1) {
                    intent.putExtra("orderStatus", data.getCancelStatus());
                } else {
                    intent.putExtra("orderStatus", data.getReturnStatus());
                }
                intent.putExtra("orderAfterSalesId", data.getId() + "");
                ((Activity) mContext).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView tv_seedetails;
        TextView text_refoundList_name;
        TextView tv_deal_price;
        RecyclerView recy_salefround;
        TextView order_status;

        public viewholder(View itemView) {
            super(itemView);
            text_refoundList_name = (TextView) itemView.findViewById(R.id.text_refoundList_name);
            tv_seedetails = (TextView) itemView.findViewById(R.id.tv_seedetails);
            tv_deal_price = (TextView) itemView.findViewById(R.id.tv_deal_price);
            recy_salefround = (RecyclerView) itemView.findViewById(R.id.recy_salefround);
            order_status = (TextView) itemView.findViewById(R.id.order_status);
        }
    }


   public class RefoundItmeListAdapter extends RecyclerView.Adapter<RefoundListAdapter.itemViewholder> {
        List<AfterSaleBean.AfterSalesProductVOs> mItemData;

        public RefoundItmeListAdapter(List<AfterSaleBean.AfterSalesProductVOs> mItemData) {
            this.mItemData = mItemData;
        }

        @Override
        public itemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new itemViewholder(LayoutInflater.from(mContext).inflate(R.layout.myhomepager_refoundlist_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(itemViewholder holder, int position) {
            AfterSaleBean.AfterSalesProductVOs data = mItemData.get(position);
            GlideUtil.display(mContext, data.getProductPicPath()).override(200, 200).into(holder.img_refound_list_item_pic);
            holder.tv_refound_list_item_name.setText(data.getChineseName());
            holder.tv_refound_list_item_stock.setText("x" + data.getReturnProductItemNum());
        }

        @Override
        public int getItemCount() {
            return mItemData.size();
        }

    }

    public class itemViewholder extends RecyclerView.ViewHolder {
        ImageView img_refound_list_item_pic;
        TextView tv_refound_list_item_name;
        TextView tv_refound_list_item_stock;

        public itemViewholder(View itemView) {
            super(itemView);
            img_refound_list_item_pic = (ImageView) itemView.findViewById(R.id.img_refound_list_item_pic);
            tv_refound_list_item_name = (TextView) itemView.findViewById(R.id.tv_refound_list_item_name);
            tv_refound_list_item_stock = (TextView) itemView.findViewById(R.id.tv_refound_list_item_stock);
        }

    }
}
