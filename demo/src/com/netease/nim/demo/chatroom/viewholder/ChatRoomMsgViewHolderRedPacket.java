package com.netease.nim.demo.chatroom.viewholder;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.redpacket.NIMOpenRpCallback;
import com.netease.nim.demo.redpacket.RedPacketDetailActivity;
import com.netease.nim.demo.redpacket.model.ViePacketReturnModel;
import com.netease.nim.demo.session.extension.RedPacketAttachment;
import com.netease.nim.demo.session.model.PacketStatusReturnModel;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.chatroom.adapter.ChatRoomMsgAdapter;
import com.netease.nim.uikit.chatroom.viewholder.ChatRoomMsgViewHolderBase;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.module.ModuleProxy;
import com.netease.nim.uikit.session.module.list.MsgAdapter;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.util.IOSDialog;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.HashMap;

public class ChatRoomMsgViewHolderRedPacket extends ChatRoomMsgViewHolderBase {

    private RelativeLayout sendView, revView;
    private TextView sendContentText, revContentText;    // 红包描述
    private TextView sendTitleText, revTitleText;    // 红包名称
    private Animator mAnimator;
    private Handler mHandler = new Handler();
    private PacketStatusReturnModel.DataBean mData;
    private SharedPreferences preferences;


    public ChatRoomMsgViewHolderRedPacket(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.red_packet_item;
    }

    @Override
    protected void inflateContentView() {
        sendContentText = findViewById(R.id.tv_bri_mess_send);
        sendTitleText = findViewById(R.id.tv_bri_name_send);
        sendView = findViewById(R.id.bri_send);
        revContentText = findViewById(R.id.tv_bri_mess_rev);
        revTitleText = findViewById(R.id.tv_bri_name_rev);
        revView = findViewById(R.id.bri_rev);
    }

    @Override
    protected void bindContentView() {
        RedPacketAttachment attachment = (RedPacketAttachment) message.getAttachment();
        preferences = context.getSharedPreferences("PACKET", Context.MODE_PRIVATE);
        if (!isReceivedMessage()) {// 消息方向，自己发送的
            sendView.setVisibility(View.VISIBLE);
            revView.setVisibility(View.GONE);
            sendContentText.setText(attachment.getRpContent());
            sendTitleText.setText(attachment.getRpTitle());
        } else {

            if (preferences.getBoolean(attachment.getRpId(), false)) {
                revView.setBackgroundResource(R.drawable.packet_rev_pressed);
            }
            sendView.setVisibility(View.GONE);
            revView.setVisibility(View.VISIBLE);
            revContentText.setText(attachment.getRpContent());
            revTitleText.setText(attachment.getRpTitle());
        }
    }

    @Override
    protected int leftBackground() {
        return R.color.transparent;
    }

    @Override
    protected int rightBackground() {
        return R.color.transparent;
    }

    @Override
    protected void onItemClick() {
        // 拆红包
        RedPacketAttachment attachment = (RedPacketAttachment) message.getAttachment();
        String rpId = attachment.getRpId();
        //查询红包状态
        packetStatus(rpId);

    }

    /**
     * @param packetDetail
     * @param message
     * @param rpId
     * @param i            0---未过期 1---已过期 2--没领到可查看  3--没领到不可查看
     */
    private void showPacket(PacketStatusReturnModel.DataBean.PacketDetailBean packetDetail, final IMMessage message, final String rpId, int i) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.dialog_redpacket);
        dialog.show();

        TextView name = (TextView) dialog.findViewById(R.id.name);
        final TextView see_detail = (TextView) dialog.findViewById(R.id.see_detail);
        final TextView tv_notice = (TextView) dialog.findViewById(R.id.tv_notice);
        ImageView icon = (ImageView) dialog.findViewById(R.id.icon);
        final ImageView open = (ImageView) dialog.findViewById(R.id.open);
        Glide.with(context).load(packetDetail.getHeadUrl()).asBitmap().placeholder(NimUIKit.getUserInfoProvider().getDefaultIconResId()).into(icon);
        name.setText(packetDetail.getSendUserName());

        tv_notice.setText(packetDetail.getTitle() + "");
        if (i == 1) { //红包过期
            open.setVisibility(View.INVISIBLE);
            tv_notice.setText("此红包已过期");
        } else if (i == 2) {
            open.setVisibility(View.INVISIBLE);
            tv_notice.setText("手速太慢啦,红包已经被抢光了");
            see_detail.setVisibility(View.VISIBLE);
            see_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(rpId), message.getSessionType() == SessionTypeEnum.P2P ? "P2P" : "TEAM", 0);
                    dialog.dismiss();
                }
            });
        } else if (i == 3) {
            open.setVisibility(View.GONE);
            tv_notice.setText("该红包已被领取");
        }

        if (i == 1 || i == 2 || i == 3) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(rpId, true);
            editor.commit();
        }

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*ObjectAnimator oaY=ObjectAnimator.ofFloat(open, "rotationY", 0,360);

                oaY.setDuration(1500);

                oaY.setRepeatMode(ValueAnimator.REVERSE);
                oaY.setRepeatCount(3);

                oaY.start();*/

                mAnimator = AnimatorInflater.loadAnimator(context, R.animator.rotate_anim);
                mAnimator.setTarget(open);


                mAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(final Animator animation) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //领取红包接口
                                viePacket(rpId, animation, dialog, see_detail, tv_notice, open);
                            }
                        }, 1500);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                mAnimator.start();

            }
        });
    }

    private void packetStatus(final String rpId) {
        String kind = "1";
        String url = "http://beta.touch.laiyifen.com/api/my/redPacket/packetStatus?";
        String utl2 = url + "ut=" + Preferences.getUserMainToken() + "&redPacketId=" + rpId;

        OKhttpHelper.getSend(context, utl2, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    PacketStatusReturnModel model1 = new Gson().fromJson(s, new TypeToken<PacketStatusReturnModel>() {
                    }.getType());
                    if (model1.getCode().equals("0")) {

                        mData = model1.getData();

                        //判断是个人还是群
                        if (message.getSessionType() == SessionTypeEnum.P2P) { //个人

                            if (mData.getIsMine() == 1) {  //自己的红包,直接看详情
                                RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(mData.getPacketDetail().getRedPacketId()), message.getSessionType() == SessionTypeEnum.P2P ? "P2P" : "TEAM", mData.getIsMine());
                            } else {
                                if (mData.getStatus() == 0) { //可以领取 打开详情拆红包界面
                                    showPacket(mData.getPacketDetail(), message, rpId, 0);
                                } else if (mData.getStatus() == 1) { //已领过 直接进去看详情
                                    RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(mData.getPacketDetail().getRedPacketId()), message.getSessionType() == SessionTypeEnum.P2P ? "P2P" : "TEAM", 0);
                                } else if (mData.getStatus() == 3) {  //过期 提示过期
                                    showPacket(mData.getPacketDetail(), message, rpId, 1);
                                }
                            }

                        } else if (message.getSessionType() == SessionTypeEnum.Team || message.getSessionType() == SessionTypeEnum.ChatRoom) { //群
                            if (mData.getStatus() == 1) { //已经领过,无论是不是自己的,进去查看详情
                                RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(mData.getPacketDetail().getRedPacketId()), message.getSessionType() == SessionTypeEnum.P2P ? "P2P" : "TEAM", mData.getIsMine());
                            } else {
                                if (mData.getIsMine() == 1) {  //自己的红包
                                    //判断红包类型
                                    if (mData.getPacketDetail().getType().equals("1")) { //普通红包
                                        RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(mData.getPacketDetail().getRedPacketId()), message.getSessionType() == SessionTypeEnum.P2P ? "P2P" : "TEAM", mData.getIsMine());
                                    } else {  //随机红包
                                        //打开红包领取界面
                                        if (mData.getStatus() == 0) { //可以领取
                                            showPacket(mData.getPacketDetail(), message, rpId, 0);
                                        } else if (mData.getStatus() == 3) {//已经过期
                                            RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(mData.getPacketDetail().getRedPacketId()), message.getSessionType() == SessionTypeEnum.P2P ? "P2P" : "TEAM", mData.getIsMine());
                                        } else if (mData.getStatus() == 2) { //被别人领完,看详情
                                            RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(mData.getPacketDetail().getRedPacketId()), message.getSessionType() == SessionTypeEnum.P2P ? "P2P" : "TEAM", mData.getIsMine());
                                        }
                                    }

                                } else {  //他人发来的红包
                                    if (mData.getStatus() == 0) { //可以领取 打开红包领取界面
                                        showPacket(mData.getPacketDetail(), message, rpId, 0);
                                    } else if (mData.getStatus() == 2) {//已领完
                                        if (mData.getPacketDetail().getType().equals("1")) { //普通红包,不可查看
                                            showPacket(mData.getPacketDetail(), message, rpId, 3);
                                        } else {//提示手速慢了,可查看详情
                                            showPacket(mData.getPacketDetail(), message, rpId, 2);
                                        }
                                    } else if (mData.getStatus() == 3) { //已过期
                                        showPacket(mData.getPacketDetail(), message, rpId, 1);
                                    }
                                }


                            }

                        }


                    } else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(context, "网络异常");
            }
        });
    }

    /**
     * 抢红包
     *
     * @param rpId
     * @param animation
     * @param dialog
     * @param see_detail
     * @param tv_notice
     * @param open
     */
    private void viePacket(final String rpId, final Animator animation, final IOSDialog dialog, final TextView see_detail, final TextView tv_notice, final ImageView open) {
        String url = "http://beta.touch.laiyifen.com/api/my/redPacket/viePacket?";
        String utl2 = url + "ut=" + Preferences.getUserMainToken() + "&redPacketId=" + rpId;
        HashMap<Object, Object> body = new HashMap();
        OKhttpHelper.send(context, new Gson().toJson(body), utl2, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    ViePacketReturnModel model = new Gson().fromJson(s, new TypeToken<ViePacketReturnModel>() {
                    }.getType());

                    if (model.getCode().equals("0")) {
                        if (model.getData().getStatus() == 1) {
                            preferences = context.getSharedPreferences("PACKET", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(rpId, true);
                            editor.commit();
                            SessionTypeEnum sessionType = message.getSessionType();
                            String type = "";
                            if (sessionType == SessionTypeEnum.P2P) {
                                type = "P2P";
                            } else if (sessionType == SessionTypeEnum.Team || sessionType == SessionTypeEnum.ChatRoom) {
                                type = "TEAM";
                            }

                            //领取红包成功
                            success(rpId);
                            RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(rpId), type, 0);
                            dialog.dismiss();

                        } else if (model.getData().getStatus() == 2) { //没抢到
                            preferences = context.getSharedPreferences("PACKET", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(rpId, true);
                            editor.commit();
                            open.setVisibility(View.INVISIBLE);
                            tv_notice.setText("手速太慢啦,红包已经被抢光了");
                            see_detail.setVisibility(View.VISIBLE);
                            see_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    RedPacketDetailActivity.startActivity((Activity) context, Integer.parseInt(rpId), message.getSessionType() == SessionTypeEnum.P2P ? "P2P" : "TEAM", 0);
                                }
                            });

                        } else if (model.getData().getStatus() == 3) { //过期
                            open.setVisibility(View.INVISIBLE);
                            tv_notice.setText("此红包已过期");
                            see_detail.setVisibility(View.INVISIBLE);

                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showLongToast(context, model.getMessage());
                    }

                } catch (Exception e) {
                    System.out.println();

                } finally {
                    if (animation.isRunning()) {
                        animation.pause();
                    }


                }
            }

            @Override
            public void onSendFail(String err) {
                if (animation.isRunning()) {
                    animation.pause();
                }
                ToastUtil.showLongToast(context, "网络异常");
                dialog.dismiss();

            }
        });
    }

    /**
     * 抢红包成功 发送一个tip
     *
     * @param rpId
     */
    private void success(String rpId) {
        BaseMultiItemFetchLoadAdapter adapter = getAdapter();
        ModuleProxy proxy = null;
        if (adapter instanceof MsgAdapter) {
            proxy = ((MsgAdapter) adapter).getContainer().proxy;
        } else if (adapter instanceof ChatRoomMsgAdapter) {
            proxy = ((ChatRoomMsgAdapter) adapter).getContainer().proxy;
        }
        NIMOpenRpCallback cb = new NIMOpenRpCallback(message.getFromAccount(), message.getSessionId(), message.getSessionType(), proxy);
        cb.sendMessage(NimUIKit.getAccount(), rpId, false);
    }
}
