package com.ody.p2p.main.myhomepager.myWallet.yidiancard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.myWallet.Utils;
import com.ody.p2p.main.myhomepager.myWallet.yidiancard.bean.ECardListBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meijunqiang on 2017/3/21.
 * 描述：
 */
public class YidianCardAdapter extends RecyclerView.Adapter<YidianCardAdapter.YidianCardHolder> {
    private Context mContext;
    private ArrayList<ECardListBean.DataEntity.EcardListEntity> mData;

    public YidianCardAdapter(Context context, ArrayList<ECardListBean.DataEntity.EcardListEntity> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setData(List<ECardListBean.DataEntity.EcardListEntity> datas) {
        mData.clear();
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(List<ECardListBean.DataEntity.EcardListEntity> datas) {
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(ECardListBean.DataEntity.EcardListEntity... datas) {
        mData.addAll(Arrays.asList(datas));
        notifyDataSetChanged();
    }

    @Override
    public YidianCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new YidianCardHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ecard_bean, parent, false));

    }

    @Override
    public void onBindViewHolder(YidianCardHolder holder, int position) {
        ECardListBean.DataEntity.EcardListEntity bean = mData.get(position);
        holder.itemEcardTvBalance.setText(Utils.buildPrice("¥", bean.getBalance(), "", mContext));
        holder.itemEcardTvCardCode.setText("卡号:" + bean.getCardCode() + "");
        holder.itemEcardTvCardPwd.setText("密码:" + bean.getCardPwd() + "");
        holder.itemEcardTvCardDate.setText("有效期至:" + bean.getEffectiveDate() + "");
        switch (bean.getStatus()) {//状态 1 未使用 2 已使用 3 已赠送 4 已过期
            case 1:
                if (bean.getIsPresent() == 0) {//是否可转赠 0 不可赠送 1 未赠送 2 已赠已领 3 已赠未领
                    holder.itemCardTvExpire.setVisibility(View.GONE);
                    holder.itemCardTvPresent.setVisibility(View.VISIBLE);
                    holder.itemCardTvPresent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO:meiyizhi 未使用伊点卡并且可赠送他人的点击事件处理

                        }
                    });
                } else {

                }
                break;
            case 2:
                holder.itemCardTvExpire.setVisibility(View.GONE);
                holder.itemCardTvPresent.setVisibility(View.GONE);
                break;
            case 3:
                holder.itemCardTvExpire.setVisibility(View.GONE);
                holder.itemCardTvPresent.setVisibility(View.GONE);
                break;
            case 4:
                holder.itemCardTvExpire.setVisibility(View.VISIBLE);
                holder.itemCardTvPresent.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class YidianCardHolder extends RecyclerView.ViewHolder {
        public TextView itemEcardTvBalance;
        public TextView itemEcardTvCardCode;
        public TextView itemEcardTvCardPwd;
        public TextView itemEcardTvCardDate;
        public TextView itemCardTvPresent;
        public ImageView itemCardTvExpire;

        public YidianCardHolder(View itemView) {
            super(itemView);
            itemEcardTvBalance = (TextView) itemView.findViewById(R.id.item_ecard_tv_balance);
            itemEcardTvCardCode = (TextView) itemView.findViewById(R.id.item_ecard_tv_card_code);
            itemEcardTvCardPwd = (TextView) itemView.findViewById(R.id.item_ecard_tv_card_pwd);
            itemEcardTvCardDate = (TextView) itemView.findViewById(R.id.item_ecard_tv_card_date);
            itemCardTvPresent = (TextView) itemView.findViewById(R.id.item_card_tv_present);
            itemCardTvExpire = (ImageView) itemView.findViewById(R.id.item_card_tv_expire);
        }
    }
}
