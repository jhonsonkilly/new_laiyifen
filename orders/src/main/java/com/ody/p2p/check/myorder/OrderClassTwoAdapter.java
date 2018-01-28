package com.ody.p2p.check.myorder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangmeijuan on 16/6/14.
 */
public class OrderClassTwoAdapter extends BaseAdapter {

    protected Context context;
    protected List<ConfirmOrderBean.DataEntity.MerchantListEntity.ProductListEntity> mData;

    public OrderClassTwoAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void addData(List<ConfirmOrderBean.DataEntity.MerchantListEntity.ProductListEntity> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.confirm_order_two_item, null);
            holder.iv_product = (ImageView) view.findViewById(R.id.iv_product);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_describe = (TextView) view.findViewById(R.id.tv_describe);
            holder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            holder.tv_num = (TextView) view.findViewById(R.id.tv_num);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        showItem(holder, i);
        return view;
    }

    protected void showItem(ViewHolder holder, int i) {

        if (!TextUtils.isEmpty(mData.get(i).picUrl)) {
            GlideUtil.display(context, mData.get(i).picUrl).into(holder.iv_product);
        }
        holder.tv_name.setText(mData.get(i).name);
        if(TextUtils.isEmpty(mData.get(i).promotionType)){//普通商品
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.main_title_color));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.theme_color));
        }else{
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.sub_title_color));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.sub_title_color));
            if(mData.get(i).promotionType.equals("1005")||mData.get(i).promotionType.equals("1006")){//满赠
                holder.tv_name.setText(UiUtils.getStick(context,"[满赠]"+mData.get(i).name));

            }else if(mData.get(i).promotionType.equals("1018")||mData.get(i).promotionType.equals("1019")){//换购
                holder.tv_name.setText(UiUtils.getStick(context,"[换购]"+mData.get(i).name));
            }
        }
        holder.tv_num.setText("X" + mData.get(i).num + "");
        holder.tv_price.setText(context.getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(mData.get(i).price));
    }

    protected class ViewHolder {
        public ImageView iv_product;
        public TextView tv_name, tv_describe, tv_price, tv_num;
    }
}
