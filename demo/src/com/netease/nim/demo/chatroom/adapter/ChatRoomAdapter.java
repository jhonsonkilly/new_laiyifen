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
import com.netease.nim.demo.chatroom.model.ChatRoomListModel;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;

import java.util.List;

/**
 * @author SevenCheng
 */

public class ChatRoomAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ChatRoomListModel.DataBean.ChatRoomOfMineBean> mChatRoomOfMineBeen;
    private List<ChatRoomListModel.DataBean.ChatRoomsContainMeBean> mChatRoomsContainMeBeen;

    public ChatRoomAdapter(Context context, List<ChatRoomListModel.DataBean.ChatRoomOfMineBean> chatRoomOfMine, List<ChatRoomListModel.DataBean.ChatRoomsContainMeBean> chatRoomsContainMe) {
        this.mContext = context;
        this.mChatRoomOfMineBeen = chatRoomOfMine;
        this.mChatRoomsContainMeBeen = chatRoomsContainMe;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatlist, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatlist_type, parent, false);
            return new TypeViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            int mPosition = position;

            if (mChatRoomsContainMeBeen.size() > 0 && position > mChatRoomOfMineBeen.size()) {

                mPosition = position - mChatRoomOfMineBeen.size() - 1;
                final ChatRoomListModel.DataBean.ChatRoomsContainMeBean chatRoomsContainMeBean = mChatRoomsContainMeBeen.get(mPosition);
                Glide.with(mContext).load(chatRoomsContainMeBean.getCreatorAvatar()).asBitmap().placeholder(NimUIKit.getUserInfoProvider().getDefaultIconResId()).into(viewHolder.mIcon);
                viewHolder.mLeader.setText(chatRoomsContainMeBean.getCreator());
                viewHolder.mNo.setText(chatRoomsContainMeBean.getRoomId() + "");
                viewHolder.mName.setText(chatRoomsContainMeBean.getName() + "");
                viewHolder.mine.setVisibility(View.INVISIBLE);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进入聊天室
                        ChatRoomActivity.start(mContext, chatRoomsContainMeBean.getRoomId() + "", chatRoomsContainMeBean.getName());
                    }
                });

            } else {
                final ChatRoomListModel.DataBean.ChatRoomOfMineBean chatRoomOfMineBean = mChatRoomOfMineBeen.get(mPosition);
                Glide.with(mContext).load(chatRoomOfMineBean.getCreatorAvatar()).asBitmap().placeholder(NimUIKit.getUserInfoProvider().getDefaultIconResId()).into(viewHolder.mIcon);
                viewHolder.mLeader.setText(chatRoomOfMineBean.getCreator());
                viewHolder.mNo.setText(chatRoomOfMineBean.getRoomId() + "");
                viewHolder.mName.setText(chatRoomOfMineBean.getName() + "");
                viewHolder.mine.setVisibility(View.VISIBLE);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进入聊天室
                        ChatRoomActivity.start(mContext, chatRoomOfMineBean.getRoomId() + "", chatRoomOfMineBean.getName());
                    }
                });
            }
        }


    }

    @Override
    public int getItemCount() {
        if (mChatRoomsContainMeBeen.size() == 0) {
            return mChatRoomOfMineBeen.size();
        }
        return mChatRoomOfMineBeen.size() + mChatRoomsContainMeBeen.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mChatRoomOfMineBeen.size()) {
            return 0;
        }
        return 1;
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

    class TypeViewHolder extends RecyclerView.ViewHolder {
        public TypeViewHolder(View itemView) {
            super(itemView);

        }
    }
}
