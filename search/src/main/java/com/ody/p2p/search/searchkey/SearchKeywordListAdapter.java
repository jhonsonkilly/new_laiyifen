package com.ody.p2p.search.searchkey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.search.R;

import java.util.List;

/**
 * 在搜索框输入内容时弹出的recyclerVeiw对应的adapter
 */
public class SearchKeywordListAdapter extends BaseRecyclerViewAdapter<SearchKeywordListBean.Data> {

    public SearchKeywordListAdapter(Context context, List<SearchKeywordListBean.Data> datas) {
        super(context, datas);
    }

    public static class ViewHolder extends BaseRecyclerViewHolder {

        public TextView tv_name;
        //        public FlowTagLayout ftl_tag;
        public RelativeLayout rl_whole_item;
        public View v_divide_line;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            rl_whole_item = (RelativeLayout) itemView.findViewById(R.id.rl_whole_item);
            v_divide_line = itemView.findViewById(R.id.v_divide_line);
        }
    }

    @Override
    public SearchKeywordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_keyword_list, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHolder viewHodler = (ViewHolder) holder;
        if (position == 0) {
            viewHodler.v_divide_line.setVisibility(View.INVISIBLE);
        } else {
            viewHodler.v_divide_line.setVisibility(View.VISIBLE);
        }
        viewHodler.tv_name.setText(mDatas.get(position).keyword);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        View view = mInflater.inflate(R.layout.item_search_keyword_list, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}
