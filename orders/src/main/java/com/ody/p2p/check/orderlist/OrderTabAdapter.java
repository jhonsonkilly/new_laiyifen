package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ody.p2p.check.R;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.utils.PxUtils;

import java.util.List;

/**
 * Created by ${tang} on 2016/8/8.
 */
public class OrderTabAdapter extends RecyclerView.Adapter {
    protected List<OrderTabBean> mData;
    protected int checkedPos=0;
    protected ItemClickListener listener;
    protected Context mContext;

    public OrderTabAdapter(Context context) {
        super();
        mContext = context;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_order_name;
        public View view_line;
        public LinearLayout ll_root;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_order_name = (TextView) itemView.findViewById(R.id.tv_order_name);
            view_line = itemView.findViewById(R.id.view_line);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        }
    }

    public void setChecked(int checkedPos) {
        this.checkedPos = checkedPos;
        notifyDataSetChanged();
    }

    public void setData(List<OrderTabBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder mHolder = (ItemViewHolder) holder;
        if (position == checkedPos) {
            mHolder.view_line.setVisibility(View.VISIBLE);
            mHolder.tv_order_name.setTextColor(mContext.getResources().getColor(R.color.theme_color));
        } else {
            mHolder.view_line.setVisibility(View.GONE);
            mHolder.tv_order_name.setTextColor(mContext.getResources().getColor(R.color.main_title_color));
        }
        mHolder.tv_order_name.setText(mData.get(position).orderStatusName);
        mHolder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClicklistener(position,mData.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ItemClickListener {
        void onItemClicklistener(int pos,OrderTabBean orderTabBean);
    }
}
