package com.ody.p2p.search.searchkey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.search.R;

import java.util.List;

/**
 * 搜索历史走接口时使用的adapter
 */
public class SearchHistoryAdapter extends BaseRecyclerViewAdapter<SearchHistoryBean.SearchHistoryData.SearchHistoryList> {

    public SearchHistoryAdapter(Context context, List<SearchHistoryBean.SearchHistoryData.SearchHistoryList> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holders, final int position) {
        viewholder holder = (viewholder) holders;
        holder.tv_item_searchhistory.setText(mDatas.get(position).keyword);
        holder.tv_item_searchhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(mDatas.get(position).keyword);
                }
            }
        });
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
    }


    public static class viewholder extends BaseRecyclerViewHolder {
        private TextView tv_item_searchhistory;

        public viewholder(View itemView) {
            super(itemView);
            tv_item_searchhistory = (TextView) itemView.findViewById(R.id.tv_item_searchhistory);
        }
    }

//    public SearchHistoryAdapter() {
//        mDataList = new ArrayList<>();
//    }

//    public void setDatas(List<SearchHistoryBean.SearchHistoryData.SearchHistoryList> searchHistoryList) {
//        mDataList.clear();
//        mDataList.addAll(searchHistoryList);
//        notifyDataSetChanged();
//    }

//    @Override
//    public SearchHistoryAdapter.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
//
////        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history_tag, null);
////        ViewHolder vh = new ViewHolder(v);
//        return new viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history_tag, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(viewholder holder, final int position) {
//        holder.tv_tag.setText(mDataList.get(position).keyword);
//
//        holder.tv_tag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onItemClick(v, position);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataList == null ? 0 : mDataList.size();
//    }
//
//    public SearchHistoryBean.SearchHistoryData.SearchHistoryList getItem(int position) {
//        return mDataList.get(position);
//    }

    private OnItemClickListener listener;

    //给recycleView的条目设置点击事件
    public interface OnItemClickListener {
        void onItemClick(String keyword);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
