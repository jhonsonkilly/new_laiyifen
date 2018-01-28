package com.ody.p2p.search.searchkey;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.search.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索历史对应的adapter,未走接口
 */
public class OrdersearchHistoryNewAdapter extends RecyclerView.Adapter<OrdersearchHistoryNewAdapter.ViewHolder> {

    private List<OrdersearchRiCiBean> mData;
    private OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        public View v_divide_line;
        public RelativeLayout rl_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            v_divide_line = itemView.findViewById(R.id.v_divide_line);
            rl_name = (RelativeLayout) itemView.findViewById(R.id.rl_name);
        }
    }

    public OrdersearchHistoryNewAdapter() {
        mData = new ArrayList<>();
    }

    public void setDatas(List<OrdersearchRiCiBean> allSearchData) {
        mData.clear();
        mData.addAll(allSearchData);
        notifyDataSetChanged();
    }

    @Override
    public OrdersearchHistoryNewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history_new, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OrdersearchHistoryNewAdapter.ViewHolder holder, final int position) {
        OrdersearchRiCiBean searchRiCiBean = mData.get(position);
        try {
            String[] str = searchRiCiBean.getRiCiName().split(",");
            if (str.length == 2) {
                holder.tv_name.setText(str[0]);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        holder.rl_name.setTag(position);
        if (position == mData.size() - 1) {
            holder.v_divide_line.setVisibility(View.INVISIBLE);
        } else {
            holder.v_divide_line.setVisibility(View.VISIBLE);
        }
        //设置条目点击事件
        holder.rl_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) holder.rl_name.getTag();
                if (listener != null) {
                    listener.onItemClick(v, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public OrdersearchRiCiBean getItem(int position) {
        return mData.get(position);
    }

    //给recycleView的条目设置点击事件
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
