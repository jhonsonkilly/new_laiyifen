package com.lyfen.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.lyfen.android.entity.network.dialog.BuyAgainEntity;
import com.ody.p2p.main.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

;

/**
 * 作者：qiujie on 16/4/25
 * 邮箱：qiujie@laiyifen.com
 */
public class BuyAginAdapter extends RecyclerView.Adapter<BuyAginAdapter.ListHolder> {

    List<BuyAgainEntity.DataEntity.AvailableProductListEntity> listEntities;

    public BuyAginAdapter(Context context, List<BuyAgainEntity.DataEntity.AvailableProductListEntity> availableProductList) {
        this.listEntities = availableProductList;
    }


    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_shopcart_bug_again, null);
        return new ListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.setData(listEntities.get(position));

    }

    @Override
    public int getItemCount() {
        return listEntities.size();
    }


    public class ListHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_gift_pic)
        SimpleDraweeView imgGiftPic;
        @Bind(R.id.tv_giftname)
        TextView tvGiftname;
        @Bind(R.id.tv_changeGif)
        TextView tvChangeGif;

        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(BuyAgainEntity.DataEntity.AvailableProductListEntity availableProductListEntity) {

            FrescoUtils.displayUrl(imgGiftPic, availableProductListEntity.picUrl);
            tvGiftname.setText(availableProductListEntity.name);
        }
    }
}
