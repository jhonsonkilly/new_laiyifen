package com.ody.p2p.shoucang;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.UiUtils;

import java.util.List;

/**
 * Created by ody on 2016/6/30.
 */
public class LikeProdutAdapter extends RecyclerView.Adapter {
    List<MyShouCangListBean.Data.ShouCangData> list;
    Context context;
    private int status = 1;//0,显示删除，1，恢复默认
    private String selectId = new String();
    int priceColor;
    int checkedBtn;
    int checkedBtnfalse;

    public LikeProdutAdapter(Context context, List<MyShouCangListBean.Data.ShouCangData> list) {
        this.list = list;
        this.context = context;
        priceColor = R.color.theme_color;
        checkedBtn = R.drawable.selected_true;
        checkedBtnfalse = R.drawable.selected_false;
    }

    public void setPriceColor(int priceColor, int checkedBtn, int checkedBtnfalse) {
        this.priceColor = priceColor;
        this.checkedBtn = checkedBtn;
        this.checkedBtnfalse = checkedBtnfalse;
    }


    public void setStatus(int status) {
        this.status = status;
        selectId = new String();
        notifyDataSetChanged();
    }

    public void setDatas(List<MyShouCangListBean.Data.ShouCangData> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void addItemLast(List<MyShouCangListBean.Data.ShouCangData> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public String getSelectId() {
        return selectId;
    }

    public static class baseViewHolder extends RecyclerView.ViewHolder {
        ImageView img_produtimg;
        TextView txt_produttitle;
        TextView txt_produtprice;
        View rl_item;
        View first;
        ImageView iv_select;
        TextView tv_stock;
        LinearLayout ll_lyout1;
        TextView tv_sale;

        public baseViewHolder(View itemView) {
            super(itemView);
            ll_lyout1 = (LinearLayout) itemView.findViewById(R.id.ll_lyout1);
            img_produtimg = (ImageView) itemView.findViewById(R.id.img_produtimg);
            txt_produttitle = (TextView) itemView.findViewById(R.id.txt_produttitle);
            txt_produtprice = (TextView) itemView.findViewById(R.id.txt_produtprice);
            rl_item = itemView.findViewById(R.id.rl_item);
            first = itemView.findViewById(R.id.view_first);
            iv_select = (ImageView) itemView.findViewById(R.id.iv_product_select);
            tv_stock = (TextView) itemView.findViewById(R.id.tv_like_stock);
            tv_sale= (TextView) itemView.findViewById(R.id.tv_sale);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获取item布局
        View view = LayoutInflater.from(context).inflate(R.layout.myhomepager_itme_like_produt, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new baseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final baseViewHolder holders = (baseViewHolder) holder;
        GlideUtil.display(context, list.get(position).getPicUrl()).override(200, 200).into(holders.img_produtimg);
        holders.txt_produttitle.setText("" + list.get(position).getChineseName());
        holders.txt_produtprice.setText(UiUtils.getMoney(context, list.get(position).getPrice()));
        holders.txt_produtprice.setTextColor(context.getResources().getColor(priceColor));
//        if (position == 0) {
//            holders.first.setVisibility(View.VISIBLE);
//        } else {
//            holders.first.setVisibility(View.GONE);
//        }

        if(!TextUtils.isEmpty(list.get(position).mpSalesVolume)){
            holders.tv_sale.setText(context.getString(R.string.already_sell)+list.get(position).mpSalesVolume+context.getString(R.string.pen));
        }else{
            holders.tv_sale.setText(context.getString(R.string.already_sell)+"0"+context.getString(R.string.pen));
        }
        MyShouCangListBean.Data.ShouCangData data = list.get(position);
        //下架
        if (data.getManagementState() == 0) {
            holders.tv_stock.setText(R.string.product_already_sold);
            holders.tv_stock.setVisibility(View.VISIBLE);
        }
        //上架
        else {
            //无货
            if (data.getStockNum() <= 0) {
                holders.tv_stock.setText(R.string.out_stock);
                holders.tv_stock.setVisibility(View.VISIBLE);
            }
        }

        if (status == 0) {
            String str = list.get(position).getId() + ",";
            if (selectId.indexOf(str) != -1) {
                holders.iv_select.setImageResource(checkedBtn);
            } else {
                holders.iv_select.setImageResource(checkedBtnfalse);
            }
            holders.iv_select.setVisibility(View.VISIBLE);
            holders.iv_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = list.get(position).getId() + ",";
                    if (selectId.indexOf(str) != -1) {
                        holders.iv_select.setImageResource(checkedBtnfalse);
                        selectId = selectId.replaceAll(str, "");

                    } else {
                        holders.iv_select.setImageResource(checkedBtn);
                        selectId += str;
                    }
                    if (adapteroclik != null) {
                        String str1 = new String(selectId);
                        String[] strs = str1.split(",");
                        int size = strs.length;
                        if (StringUtils.isEmpty(strs[0])) {
                            size = 0;
                        }
                        adapteroclik.showDeleteBtn(size);
                    }
                }
            });
        } else {
            holders.iv_select.setVisibility(View.GONE);
        }
        holders.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mpId = list.get(position).getMpId();
//                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://shoppingdetail");
//                activityRoute.withParams(Constants.SP_ID, mpId + "").open();

                Bundle bd = new Bundle();
                bd.putString(Constants.SP_ID, mpId);
                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
            }
        });

        holders.rl_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != adapteroclik) {
                    adapteroclik.delectItem(list.get(position).getId() + "");
                }
                return false;
            }
        });
    }

    private Adapteroclik adapteroclik;

    public void setAdapteroclik(Adapteroclik adapteroclik) {
        this.adapteroclik = adapteroclik;
    }

    public interface Adapteroclik {
        void del(MyShouCangListBean.Data.ShouCangData data, int postion, String ids);

        void showDeleteBtn(int size);

        void delectItem(String id);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
