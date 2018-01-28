package com.ody.p2p.productdetail.store.storecategory;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.BitmapUtils;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ody.p2p.Constants.SEARCH_KEY;

/**
 * Created by ${tang} on 2017/7/18.
 */

public class ShopCateSubAdapter extends RecyclerView.Adapter {

    private List<ShopCateSubBean.DataEntity.ChildListBean> mData;
    private Context context;
    private String merchantId;

    public ShopCateSubAdapter(Context context, String merchantId) {
        mData = new ArrayList<>();
        this.context = context;
        this.merchantId = merchantId;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).itemType;
    }

    public List<ShopCateSubBean.DataEntity.ChildListBean> getmData() {
        return mData;
    }

    public void setData(List<ShopCateSubBean.DataEntity.ChildListBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new OneClassHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
        } else {
            return new TwoClassHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selection_img, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 1) {
            OneClassHolder oneClassHolder = (OneClassHolder) holder;
            oneClassHolder.tv_category_name.setText(mData.get(position).name);
        } else if (getItemViewType(position) == 2) {
            TwoClassHolder twoClassHolder = (TwoClassHolder) holder;
            twoClassHolder.name.setText(mData.get(position).name);
            if (BitmapUtil.isImage(mData.get(position).pictureUrl)) {
                GlideUtil.display(context, mData.get(position).pictureUrl).override(150, 150).into(twoClassHolder.img);
            } else {
                twoClassHolder.img.setImageResource(R.drawable.icon_stub);
            }
            twoClassHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extra = new Bundle();
                    extra.putString(Constants.NAVCATEGORY_NAME, mData.get(position).name);
//                    extra.putString(Constants.NAVCATEGORY_ID, mData.get(position).categoryId);
                    extra.putString(Constants.SEARCH_KEY, mData.get(position).name);
                    extra.putString("merchantId", merchantId);
                    JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, extra);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class OneClassHolder extends RecyclerView.ViewHolder {
        private TextView tv_category_name;

        public OneClassHolder(View itemView) {
            super(itemView);
            tv_category_name = (TextView) itemView.findViewById(R.id.tv_category_name);
        }
    }

    private class TwoClassHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;

        public TwoClassHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
