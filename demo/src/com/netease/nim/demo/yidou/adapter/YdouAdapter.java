package com.netease.nim.demo.yidou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.yidou.model.YdouListModel;

import java.util.List;



/**
 * @author SevenCheng
 */

public class YdouAdapter extends RecyclerView.Adapter {


    private Context             mContext;
    private List<YdouListModel> mModels;

    public YdouAdapter(Context context, List<YdouListModel> list, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mModels = list;
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ydou, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final YdouListModel model = mModels.get(position);
            viewHolder.mNum.setText(model.getNum());

            if (model.getNum().equals("ALL")) {
                viewHolder.mTvUnit.setVisibility(View.GONE);
            } else {
                viewHolder.mTvUnit.setVisibility(View.VISIBLE);

            }

            if (model.isChecked) {
                changeColor(viewHolder, 1);
            } else {
                changeColor(viewHolder, 0);

            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(model);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mItemView;

        TextView mNum;

        TextView mTvUnit;

        TextView mTvDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemView = itemView;
            mNum=(TextView) mItemView.findViewById(R.id.num);
            mTvUnit=(TextView) mItemView.findViewById(R.id.tv_unit);
            mTvDesc=(TextView) mItemView.findViewById(R.id.tv_desc);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(YdouListModel model);
    }

    /**
     * @param tag 1--选中  0--未选中
     */
    private void changeColor(ViewHolder viewHolder, int tag) {

        switch (tag) {
            case 1:
                viewHolder.mItemView.setBackgroundResource(R.drawable.border_grey_line_3dp_pre);
                viewHolder.mNum.setTextColor(mContext.getResources().getColor(R.color.lyf_yellow));
                viewHolder.mTvUnit.setTextColor(mContext.getResources().getColor(R.color.lyf_yellow));
                viewHolder.mTvDesc.setTextColor(mContext.getResources().getColor(R.color.lyf_yellow));
                break;

            case 0:
                viewHolder.mItemView.setBackgroundResource(R.drawable.border_grey_line_3dp);
                viewHolder.mNum.setTextColor(mContext.getResources().getColor(R.color.black));
                viewHolder.mTvUnit.setTextColor(mContext.getResources().getColor(R.color.black));
                viewHolder.mTvDesc.setTextColor(mContext.getResources().getColor(R.color.gray_text));
                break;
        }
    }

}
