package com.netease.nim.demo.session.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.discount.model.YHQModel;
import com.netease.nim.demo.session.model.Type13Model;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;
import com.netease.nimlib.sdk.team.model.DismissAttachment;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class CustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        try {
            JSONObject object = JSON.parseObject(json);
            int type = object.getInteger(KEY_TYPE);
            JSONObject data = object.getJSONObject(KEY_DATA);
            switch (type) {
                case CustomAttachmentType.Guess:
                    attachment = new GuessAttachment();
                    break;
                case CustomAttachmentType.SnapChat:
                    return new SnapChatAttachment(data);
                case CustomAttachmentType.Sticker:
                    attachment = new StickerAttachment();
                    break;
                case CustomAttachmentType.RTS:
                    attachment = new RTSAttachment();
                    break;
                case CustomAttachmentType.RedPacket:
                    attachment = new RedPacketAttachment();
                    break;
                case CustomAttachmentType.OpenedRedPacket:
                    attachment = new RedPacketOpenedAttachment();
                    break;
                case CustomAttachmentType.BusinessCard:
                    attachment = new BusinessCardAttachment("");
                    break;
                case CustomAttachmentType.SystemMessageOnlyText:
                    attachment = new SystemMessageOnlyTextAttachment();
                    break;
                case CustomAttachmentType.SystemMessage:
                    attachment = new SystemMessageImageNewAttachment();
                    break;
                case CustomAttachmentType.SysFriendInfo:
                    attachment = new SysFriendInfoAttachment();
                    break;
                case CustomAttachmentType.YiDianCard:
                    try {
                        String yhqObject = data.getString("yidou");
                        JSONObject string = JSON.parseObject(yhqObject);
                        if (string.getString("statusType").equals("优惠券"))
                            attachment = new DiscountAttachment();
                        else
                            attachment = new YiDianCardAttachment();
                    }catch (Exception e){
                        attachment = new YiDianCardAttachment();
                    }

                    break;
                case CustomAttachmentType.Discount:
                    attachment = new DiscountAttachment();
                    break;
                case CustomAttachmentType.Distinguish:
                    attachment = new DistinguishAttachment();
                    break;
                default:
                    attachment = new DefaultCustomAttachment();
                    break;
            }
            if (attachment != null) {
                attachment.fromJson(data);
            }
        } catch (Exception e) {

        }

        return attachment;
    }

    public static String packData(int type, JSONObject data) {
        JSONObject object = new JSONObject();
        object.put(KEY_TYPE, type);
        if (data != null) {
            object.put(KEY_DATA, data);
        }

        return object.toJSONString();
    }
}
