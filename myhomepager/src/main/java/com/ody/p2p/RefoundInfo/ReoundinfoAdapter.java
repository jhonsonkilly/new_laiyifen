package com.ody.p2p.RefoundInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.UiUtils;

import java.util.List;

/**
 * Created by ody on 2016/7/1.
 */
public class ReoundinfoAdapter extends RecyclerView.Adapter<ReoundinfoAdapter.viewHolder> {

    List<AfterSaleDetailBean.MerchantProductVOs> mData;
    Context mContext;

    public ReoundinfoAdapter(Context mContext, List<AfterSaleDetailBean.MerchantProductVOs> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public ReoundinfoAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.myhomepager_item_refound_info_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ReoundinfoAdapter.viewHolder holder, int position) {
        AfterSaleDetailBean.MerchantProductVOs data = mData.get(position);
        GlideUtil.display(mContext, data.getProductPicPath()).override(200, 200).into(holder.image_refoundinfo);
        holder.tv_refoundinfo_productname.setText(data.getChineseName());
        holder.tv_refoundinfo_price.setText(mContext.getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(data.getProductPayPrice()+""));
        holder.tv_refoundinfo_productNum.setText("x" + data.getReturnProductItemNum());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {
        ImageView image_refoundinfo;
        TextView tv_refoundinfo_productname, tv_refoundinfo_price, tv_refoundinfo_productNum;

        public viewHolder(View itemView) {
            super(itemView);
            image_refoundinfo = (ImageView) itemView.findViewById(R.id.image_refoundinfo);
            tv_refoundinfo_productname = (TextView) itemView.findViewById(R.id.tv_refoundinfo_productname);
            tv_refoundinfo_price = (TextView) itemView.findViewById(R.id.tv_refoundinfo_price);
            tv_refoundinfo_productNum = (TextView) itemView.findViewById(R.id.tv_refoundinfo_productNum);
        }
    }

}
