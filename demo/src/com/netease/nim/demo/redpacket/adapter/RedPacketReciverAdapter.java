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
import com.netease.nim.demo.redpacket.model.PacketDetailReturnModel;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;

import java.util.List;

/**
 * @author SevenCheng
 */

public class RedPacketReciverAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private PacketDetailReturnModel.DataBean.PacketInfoBean        mPacketInfoBean;
    private List<PacketDetailReturnModel.DataBean.ReceiveListBean> mReceiveListBeen;
    private String mType;
    private int                                                    mIsMine;
    private String mRedType;

    public RedPacketReciverAdapter(Context context, PacketDetailReturnModel.DataBean.PacketInfoBean packetInfo, List<PacketDetailReturnModel.DataBean.ReceiveListBean> receiveListBean, String TYPE, int isMine) {
        this.mContext = context;
        this.mPacketInfoBean = packetInfo;
        this.mReceiveListBeen = receiveListBean;
        this.mType = TYPE;
        this.mIsMine = isMine;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.redpacket_head, parent, false);
            ViewHolderHead viewHolderHead = new ViewHolderHead(view);
            return viewHolderHead;

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redpacket_receive, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderHead) {
            if (mPacketInfoBean == null) {
                return;
            }
            ViewHolderHead viewHolder = (ViewHolderHead) holder;
            //普通红包
            Glide.with(mContext).load(mPacketInfoBean.getHeadUrl()).asBitmap().placeholder(NimUIKit.getUserInfoProvider().getDefaultIconResId()).into(viewHolder.avatar);
            viewHolder.mFromMsg.setText(mPacketInfoBean.getSendUserName() + "发放的红包");
            viewHolder.msg.setText(mPacketInfoBean.getTitle());
            viewHolder.mAmount.setText(mPacketInfoBean.getAmount());


            //随机红包
            viewHolder.mNum.setText("已领取" + mPacketInfoBean.getReceivedNum() + "/" + mPacketInfoBean.getNumber() + "个");
            viewHolder.list_amount.setText("共计" + mPacketInfoBean.getPacketAmount() + "元");
            viewHolder.tv_unit.setText("元");
            if (mType.equals("P2P")) {
                viewHolder.list_amount.setVisibility(View.INVISIBLE);
                viewHolder.ll_num.setVisibility(View.GONE);
            } else {
                viewHolder.list_amount.setVisibility(View.VISIBLE);
                viewHolder.ll_num.setVisibility(View.VISIBLE);
            }


            //是自己的包
            if (mIsMine == 1) {
                viewHolder.tv_notice.setVisibility(View.GONE);
                viewHolder.list_amount.setVisibility(View.VISIBLE);
            } else {
                if (mType.equals("P2P")) {
                    viewHolder.tv_notice.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tv_notice.setVisibility(View.GONE);
                }
                if (mPacketInfoBean.getType().equals("2")) {
                    viewHolder.list_amount.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.list_amount.setVisibility(View.INVISIBLE);
                }
            }

            if (mIsMine == 1 && mPacketInfoBean.getType().equals("1")) {
                viewHolder.mAmount.setVisibility(View.GONE);
            } else {
                viewHolder.mAmount.setVisibility(View.VISIBLE);
            }

            //是否显示"拼"
            if (mPacketInfoBean.getType().equals("2")) {
                viewHolder.pin.setVisibility(View.VISIBLE);
            } else {
                viewHolder.pin.setVisibility(View.GONE);
            }


            if (mPacketInfoBean.getIsReceived().equals("1")) {
                viewHolder.tv_notice.setVisibility(View.VISIBLE);
                viewHolder.tv_unit.setVisibility(View.VISIBLE);
                viewHolder.mAmount.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mAmount.setVisibility(View.GONE);
                viewHolder.tv_unit.setVisibility(View.GONE);
                viewHolder.tv_notice.setVisibility(View.GONE);
            }

        }

        if (holder instanceof ViewHolder) {
            PacketDetailReturnModel.DataBean.ReceiveListBean bean = mReceiveListBeen.get(position - 1);
            ViewHolder viewHolder = (ViewHolder) holder;
            Glide.with(mContext).load(bean.getHeadUrl()).asBitmap().placeholder(NimUIKit.getUserInfoProvider().getDefaultIconResId()).into(viewHolder.mIcon);
            viewHolder.mName.setText(bean.getUserName() + "");
            viewHolder.mDate.setText(bean.getDateTime() + "");
            viewHolder.money.setText(bean.getAmount() + "元");

            //显示手气第一
            if (bean.getIsBigest().equals("1") && mPacketInfoBean.getType().equals("2") && mPacketInfoBean.getReceivedNum().equals(mPacketInfoBean.getNumber())) {
                viewHolder.lucky.setVisibility(View.VISIBLE);
            } else {
                viewHolder.lucky.setVisibility(View.GONE);
            }


        }
    }

    @Override
    public int getItemCount() {
        if (mType.equals("P2P") && mIsMine == 0) {
            return 1;
        } else {
            return mReceiveListBeen.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setData(PacketDetailReturnModel.DataBean.PacketInfoBean packetInfo) {
        this.mPacketInfoBean = packetInfo;
    }

    class ViewHolderHead extends RecyclerView.ViewHolder {


        public HeadImageView avatar;
        public TextView mFromMsg;
        public TextView msg;
        public TextView mAmount;
        public TextView mNum;
        public View ll_num;
        public TextView list_amount;
        public TextView tv_notice;
        public TextView tv_unit;
        public TextView pin;

        public ViewHolderHead(View itemView) {
            super(itemView);
            avatar = (HeadImageView) itemView.findViewById(R.id.avatar);
            mFromMsg = (TextView) itemView.findViewById(R.id.from_msg);
            msg = (TextView) itemView.findViewById(R.id.msg);
            mAmount = (TextView) itemView.findViewById(R.id.amount);
            mNum = (TextView) itemView.findViewById(R.id.num);
            ll_num = itemView.findViewById(R.id.ll_num);
            list_amount = (TextView) itemView.findViewById(R.id.list_amount);
            tv_notice = (TextView) itemView.findViewById(R.id.tv_notice);
            tv_unit = (TextView) itemView.findViewById(R.id.tv_unit);
            pin = (TextView) itemView.findViewById(R.id.pin);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public HeadImageView mIcon;
        public TextView mName;
        public TextView mDate;
        public TextView money;
        public ImageView lucky;


        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = (HeadImageView) itemView.findViewById(R.id.icon);
            mName = (TextView) itemView.findViewById(R.id.name);
            mDate = (TextView) itemView.findViewById(R.id.date);
            money = (TextView) itemView.findViewById(R.id.money);
            lucky = (ImageView) itemView.findViewById(R.id.lucky);

        }
    }
}
