package com.netease.nim.demo.chatroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.demo.R;
import com.netease.nim.demo.chatroom.activity.ChatRoomActivity;
import com.netease.nim.demo.chatroom.model.SearchRoomModel;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;

import java.util.List;

/**
 * @author SevenCheng
 */

public class ChatRoomChoiceAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<SearchRoomModel.DataBean.ChatRoomsBean> chatRoomsList;

    public ChatRoomChoiceAdapter(Context context, List<SearchRoomModel.DataBean.ChatRoomsBean> chatRoomsList) {
        this.mContext = context;
        this.chatRoomsList = chatRoomsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatlist, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

            final SearchRoomModel.DataBean.ChatRoomsBean chatRoomsBean = chatRoomsList.get(position);
            Glide.with(mContext).load(chatRoomsBean.getCreatorAvatar()).asBitmap().placeholder(NimUIKit.getUserInfoProvider().getDefaultIconResId()).into(viewHolder.mIcon);
                viewHolder.mLeader.setText(chatRoomsBean.getCreator());
                viewHolder.mNo.setText(chatRoomsBean.getRoomId() + "");
                viewHolder.mName.setText(chatRoomsBean.getName() + "");
                viewHolder.mine.setVisibility(View.VISIBLE);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进入聊天室
                        ChatRoomActivity.start(mContext, chatRoomsBean.getRoomId() + "", chatRoomsBean.getName());
                    }
                });

        }


    }

    @Override
    public int getItemCount() {
        return chatRoomsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public HeadImageView mIcon;
        TextView mLeader;
        TextView mNo;
        TextView mName;
        View mine;

        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = (HeadImageView) itemView.findViewById(R.id.icon);
            mLeader = (TextView) itemView.findViewById(R.id.leader);
            mNo = (TextView) itemView.findViewById(R.id.no);
            mName = (TextView) itemView.findViewById(R.id.name);
            mine = itemView.findViewById(R.id.mine);
        }
    }

}
