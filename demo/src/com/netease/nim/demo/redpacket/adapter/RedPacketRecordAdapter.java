package com.netease.nim.demo.redpacket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.demo.R;
import com.netease.nim.demo.redpacket.model.RedRecordModel;

import java.util.List;

/**
 * @author SevenCheng
 */

public class RedPacketRecordAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private RedRecordModel.DataBean                    data;
    private List<RedRecordModel.DataBean.SendListBean> mSendList;
    private int                                        redRecordType;

    public RedPacketRecordAdapter(Context context, RedRecordModel.DataBean data, List<RedRecordModel.DataBean.SendListBean> sendList, int redRecordType) {
        this.mContext = context;
        this.data = data;
        this.mSendList = sendList;
        this.redRecordType = redRecordType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_red_record, parent, false);
            return new ViewHolderHead(view);


        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.body_red_record, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ViewHolderHead) {
            if (data == null) {
                return;
            }
            ViewHolderHead viewHolderHead = (ViewHolderHead) holder;

            Glide.with(mContext).load(data.getHeadUrl()).asBitmap().into(viewHolderHead.mIcon);
            viewHolderHead.mAmount.setText(data.getAmount() + "");
            if (redRecordType == 1) {
                viewHolderHead.mName.setText(data.getNickName() + "共收到");
                viewHolderHead.mShoudao.setText(data.getQuantity() + "");
                viewHolderHead.mZuijia.setText(data.getLuckiest() + "");
            } else {
                viewHolderHead.mName.setText(data.getNickName() + "共发出");
                viewHolderHead.mShoudao.setVisibility(View.GONE);
                viewHolderHead.mZuijia.setVisibility(View.GONE);
                viewHolderHead.mZj.setVisibility(View.INVISIBLE);
                viewHolderHead.mSd.setVisibility(View.INVISIBLE);
            }

        } else if (holder instanceof ViewHolder) {
            RedRecordModel.DataBean.SendListBean sendListBean = mSendList.get(position - 1);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mAmount.setText(sendListBean.getAmount() + "元");
            viewHolder.mTime.setText(sendListBean.getDate());
            viewHolder.mName.setText(sendListBean.getNickName());

            if(redRecordType == 2){
                if(sendListBean.getRedType()==1){
                    viewHolder.mName.setText("普通红包");
                }else {
                    viewHolder.mName.setText("拼手气红包");
                }
            }

        }

    }


    @Override
    public int getItemCount() {
        return mSendList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setNewData(RedRecordModel.DataBean data, int redRecordType) {
        this.data = data;
        this.redRecordType = redRecordType;

    }

    class ViewHolderHead extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mName;
        TextView mAmount;
        TextView mShoudao;
        TextView mZuijia;
        TextView mSd;
        TextView mZj;

        public ViewHolderHead(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mName = (TextView) itemView.findViewById(R.id.name);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
            mShoudao = (TextView) itemView.findViewById(R.id.shoudao);
            mZuijia = (TextView) itemView.findViewById(R.id.zuijia);
            mSd = (TextView) itemView.findViewById(R.id.sd);
            mZj = (TextView) itemView.findViewById(R.id.zj);

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mName;
        private final TextView mTime;
        private final TextView mAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
