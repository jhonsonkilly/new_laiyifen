package com.netease.nim.demo.session.action;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.redpacket.RedpacketActivity;
import com.netease.nim.demo.redpacket.model.RedPacketModel;
import com.netease.nim.demo.session.extension.RedPacketAttachment;
import com.netease.nim.demo.session.model.PacketCheckReturnModel;
import com.netease.nim.demo.session.model.SendPacketReturnModel;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.HashMap;

public class RedPacketAction extends BaseAction {

    public RedPacketAction() {
        super(R.drawable.redpacket, R.string.red_packet);
    }

    private static final int CREATE_GROUP_RED_PACKET  = 51;
    private static final int CREATE_SINGLE_RED_PACKET = 10;

    @Override
    public void onClick() {
        int requestCode;
        String type = null;
        if (getContainer().sessionType == SessionTypeEnum.Team) {
            requestCode = makeRequestCode(CREATE_GROUP_RED_PACKET);
            type = "TEAM";
        } else if (getContainer().sessionType == SessionTypeEnum.P2P) {
            requestCode = makeRequestCode(CREATE_SINGLE_RED_PACKET);
            type = "P2P";
        } else {
            RedpacketActivity.startActivityForResult(getActivity(), "TEAM",requestCode = makeRequestCode(CREATE_GROUP_RED_PACKET));
            return;
        }
//                NIMRedPacketClient.startSendRpActivity(getActivity(), getContainer().sessionType, getAccount(), requestCode);
        RedpacketActivity.startActivityForResult(getActivity(), type, requestCode);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        sendRpMessage(data);
    }

    private void sendRpMessage(Intent data) {
        RedPacketModel model = (RedPacketModel) data.getSerializableExtra("data");

        packetCheck(model);


    }

    private void packetCheck(final RedPacketModel model) {
        String kind = "1";

        String url = "http://beta.touch.laiyifen.com/api/my/redPacket/packetCheck?";
        String utl2 = url + "ut=" + Preferences.getUserMainToken() + "&type=" + model.getType() + "&kind=" + kind + "&amount=" + model.getAmount() + "&number=" + model.getCount() + "&title=来伊份红包";
        HashMap body = new HashMap<Object, Object>();

        OKhttpHelper.send(getActivity(), new Gson().toJson(body), utl2, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    PacketCheckReturnModel model1 = new Gson().fromJson(s, new TypeToken<PacketCheckReturnModel>() {
                    }.getType());
                    if (model1.getCode().equals("0")) {
                        sendPacket(model);
                    } else {
                        ToastUtil.showLongToast(getActivity(), model1.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(getActivity(), "网络出错啦");
            }
        });

    }

    private void sendPacket(final RedPacketModel redPacketModel) {
        String kind = "1";

        String url = "http://beta.touch.laiyifen.com/api/my/redPacket/sendPacket?";
        String utl2 = url + "ut=" + Preferences.getUserMainToken() + "&type=" + redPacketModel.getType() + "&kind=" + kind + "&amount=" + redPacketModel.getAmount() + "&number=" + redPacketModel.getCount() + "&title=" + redPacketModel.getMsg();
        HashMap body = new HashMap<Object, Object>();

        OKhttpHelper.send(getActivity(), new Gson().toJson(body), utl2, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    SendPacketReturnModel model = new Gson().fromJson(s, new TypeToken<SendPacketReturnModel>() {
                    }.getType());
                    if (model.getCode().equals("0")) {
                        SendPacketReturnModel.DataBean data = model.getData();
                        RedPacketAttachment attachment = new RedPacketAttachment();
                        // 红包id，红包信息，红包名称
                        attachment.setRpId(data.getRedPacketId() + "");
                        attachment.setRpContent(redPacketModel.getMsg());
                        attachment.setRpTitle("来伊份红包");

                        String content = getActivity().getString(R.string.rp_push_content);
                        // 不存云消息历史记录
                        CustomMessageConfig config = new CustomMessageConfig();
                        config.enableHistory = false;

                        IMMessage message = MessageBuilder.createCustomMessage(getAccount(), getSessionType(), content, attachment, config);

                        sendMessage(message);
                    } else {
                        ToastUtil.showLongToast(getActivity(), model.getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(getActivity(), "哎呀,网络卡住了,请检查网络,稍后重试");
            }
        });

    }
}
