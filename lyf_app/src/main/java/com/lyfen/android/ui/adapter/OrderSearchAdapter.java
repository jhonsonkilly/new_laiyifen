package com.lyfen.android.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.lyfen.android.entity.network.activity.OrderSearchEntity;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * <p> Created by qiujie on 2017/12/13/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class OrderSearchAdapter extends RecyclerView.Adapter<OrderSearchAdapter.LvListHolder> {

    List<OrderSearchEntity.DataEntityX.DataEntity> orderListEntity2s;
    int postion;



    @Override
    public LvListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_orderlist, null);
        return new LvListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(LvListHolder holder, int position) {
        holder.setData(orderListEntity2s.get(position));

    }

    @Override
    public int getItemCount() {
        return orderListEntity2s.size();
    }

    public void setData(List<OrderSearchEntity.DataEntityX.DataEntity> orderList) {
        orderListEntity2s = orderList;


    }

    public class LvListHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.common_img_4)
        ImageView commonImg4;
        @Bind(R.id.common_img_5)
        ImageView commonImg5;
        @Bind(R.id.common_tv_1)
        TextView commonTv1;
        @Bind(R.id.common_tv_2)
        TextView commonTv2;
        @Bind(R.id.ll_content)
        LinearLayout llContent;
        @Bind(R.id.common_tv_3)
        TextView commonTv3;
        @Bind(R.id.common_tv_4)
        TextView commonTv4;
        @Bind(R.id.common_btn_1)
        TextView commonBtn1;
        @Bind(R.id.common_btn_2)
        TextView commonBtn2;
        @Bind(R.id.common_btn_3)
        TextView commonBtn3;
        @Bind(R.id.common_btn_4)
        TextView commonBtn4;
        @Bind(R.id.common_btn_5)
        TextView commonBtn5;
        @Bind(R.id.common_btn_6)
        TextView commonBtn6;
        @Bind(R.id.ll_con)
        LinearLayout llCon;

        public LvListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(OrderSearchEntity.DataEntityX.DataEntity orderListEntity2) {
            llCon.setVisibility(View.GONE);
            commonTv1.setText(orderListEntity2.merchantName);
            commonTv2.setText(orderListEntity2.orderStatusName);
            commonTv3.setText("共计" + orderListEntity2.totalCount + "件商品");
            commonTv4.setText("¥" + orderListEntity2.amount);

            llContent.removeAllViews();

            for (int i = 0; i < orderListEntity2.productList.size(); i++) {

                if (i < 3) {


                    View inflate = View.inflate(UIUtils.getContext(), R.layout.item_order_img, null);
                    SimpleDraweeView viewById = (SimpleDraweeView) inflate.findViewById(R.id.img_product);
                    viewById.setVisibility(View.VISIBLE);
                    FrescoUtils.displayUrl(viewById, orderListEntity2.productList.get(i).picUrl);

                    llContent.addView(inflate);
                }


            }


        }
    }
}
