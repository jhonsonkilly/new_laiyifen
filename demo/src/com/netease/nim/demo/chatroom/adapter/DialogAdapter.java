package com.netease.nim.demo.chatroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nim.demo.R;

import java.util.List;

/**
 * @author SevenCheng
 */

public class DialogAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> roomList;
    private OnItemClickListener mOnItemClickListener;

    public DialogAdapter(Context context, List<String> roomList, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.roomList = roomList;
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_name, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            final String s = roomList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.name.setText(s);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(s);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public interface OnItemClickListener {
        void onClick(String s);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
