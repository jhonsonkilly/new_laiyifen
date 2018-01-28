package com.lyfen.android.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.lyfen.android.entity.network.OrderDetailEntity;
import com.ody.p2p.main.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * <p> Created by qiujie on 2017/12/16/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.LvAdapter> {

    private List<OrderDetailEntity.DataBean.ChildOrderListBean.PackageListBean> data;

    @Override
    public LvAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderdetail, parent, false);
        return new LvAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(LvAdapter holder, int position) {
        holder.setData(data.get(0).productList.get(position));

    }

    @Override
    public int getItemCount() {
        return data.get(0).productList.size();
    }

    public void setData(List<OrderDetailEntity.DataBean.ChildOrderListBean.PackageListBean> data) {
        this.data = data;
    }

    public class LvAdapter extends RecyclerView.ViewHolder {
        @Bind(R.id.img)
        SimpleDraweeView img;
        @Bind(R.id.text_title)
        TextView textTitle;
        @Bind(R.id.text_price)
        TextView textPrice;
        @Bind(R.id.num)
        TextView num;

        public LvAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(OrderDetailEntity.DataBean.ChildOrderListBean.PackageListBean.ProductListBean packageListBean) {
            FrescoUtils.displayUrl(img, packageListBean.picUrl);
            textPrice.setText("¥ " + packageListBean.price + "");
            textTitle.setText(packageListBean.name + "");
            num.setText("x" + packageListBean.num + "");
        }
    }
}
