package com.ody.p2p.pay.payMode.payOnline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.pay.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付方式列表
 */
public class PayModeListAdapter extends RecyclerView.Adapter<PayModeListAdapter.ViewHolder> {

    private List<PayTypeListBean.PayType> mData;
    private OnItemClickListener listener;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_pay_icon;
        private TextView tv_pay_mode;
        private TextView tv_tag;
        private ImageView iv_select;
        private RelativeLayout rl_whole;
        private View v_divide_line;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_pay_icon = (ImageView) itemView.findViewById(R.id.iv_pay_icon);
            tv_pay_mode = (TextView) itemView.findViewById(R.id.tv_pay_mode);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
            iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
            rl_whole = (RelativeLayout) itemView.findViewById(R.id.rl_whole);
            v_divide_line = itemView.findViewById(R.id.v_divide_line);
        }
    }

    public PayModeListAdapter(Context context) {
        mData = new ArrayList<>();
        this.context = context;
    }

    public void setDatas(List<PayTypeListBean.PayType> payModedata) {
        mData.clear();
        mData.addAll(payModedata);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay_mode_list, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        GlideUtil.display(context,mData.get(position).paymentLogoUrl).into(holder.iv_pay_icon);
        holder.tv_pay_mode.setText(mData.get(position).paymentThirdparty);
        //holder.tv_tag.setText(mData.get(position).tag);

        if (mData.get(position).isChoose){
            holder.iv_select.setImageResource(R.drawable.icon_selected);
            holder.iv_select.setVisibility(View.VISIBLE);
        }else {
            holder.iv_select.setVisibility(View.INVISIBLE);
        }

        holder.rl_whole.setTag(position);
        //设置条目点击事件
        holder.rl_whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) holder.rl_whole.getTag();
                if(listener!=null){
                    listener.onItemClick(v,pos);
                }
            }
        });

        if (position==mData.size()-1){
            holder.v_divide_line.setVisibility(View.INVISIBLE);
        }else {
            holder.v_divide_line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public PayTypeListBean.PayType getItem(int position){
        return mData.get(position);
    }

    //给recycleView的条目设置点击事件
    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
