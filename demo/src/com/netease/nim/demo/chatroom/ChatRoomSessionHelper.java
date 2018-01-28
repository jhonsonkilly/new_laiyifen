package com.netease.nim.demo.chatroom;

import com.netease.nim.demo.NimApplication;
import com.netease.nim.demo.chatroom.action.ChatRoomRedPacketAction;
import com.netease.nim.demo.chatroom.viewholder.ChatRoomMsgViewHolderBusinessCard;
import com.netease.nim.demo.chatroom.viewholder.ChatRoomMsgViewHolderDiscount;
import com.netease.nim.demo.chatroom.viewholder.ChatRoomMsgViewHolderOpenRedPacket;
import com.netease.nim.demo.chatroom.viewholder.ChatRoomMsgViewHolderRedPacket;
import com.netease.nim.demo.chatroom.viewholder.ChatRoomMsgViewHolderYiDianCard;
import com.netease.nim.demo.session.action.BusinessCardAction;
import com.netease.nim.demo.session.action.DiscountAction;
import com.netease.nim.demo.session.action.MyfavoriteAction;
import com.netease.nim.demo.session.action.YiDianCardAction;
import com.netease.nim.demo.session.extension.BusinessCardAttachment;
import com.netease.nim.demo.session.extension.DiscountAttachment;
import com.netease.nim.demo.session.extension.RedPacketAttachment;
import com.netease.nim.demo.session.extension.RedPacketOpenedAttachment;
import com.netease.nim.demo.session.extension.YiDianCardAttachment;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.chatroom.ChatRoomSessionCustomization;
import com.netease.nim.uikit.chatroom.viewholder.ChatRoomMsgViewHolderPicture;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.session.actions.ImageAction;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;

import java.util.ArrayList;

/**
 * UIKit自定义聊天室消息界面用法展示类
 * <p>
 * Created by huangjun on 2017/9/18.
 */

public class ChatRoomSessionHelper {
    private static ChatRoomSessionCustomization customization;

    public static void init() {
        registerViewHolders();
        NimUIKit.setCommonChatRoomSessionCustomization(getChatRoomSessionCustomization());
    }

    private static void registerViewHolders() {
        NimUIKit.registerChatRoomMsgItemViewHolder(RedPacketAttachment.class, ChatRoomMsgViewHolderRedPacket.class);
        NimUIKit.registerChatRoomMsgItemViewHolder(RedPacketOpenedAttachment.class, ChatRoomMsgViewHolderOpenRedPacket.class);
        NimUIKit.registerChatRoomMsgItemViewHolder(BusinessCardAttachment.class, ChatRoomMsgViewHolderBusinessCard.class);
        NimUIKit.registerChatRoomMsgItemViewHolder(YiDianCardAttachment.class, ChatRoomMsgViewHolderYiDianCard.class);
        NimUIKit.registerChatRoomMsgItemViewHolder(DiscountAttachment.class, ChatRoomMsgViewHolderDiscount.class);
        NimUIKit.registerChatRoomMsgItemViewHolder(ImageAttachment.class, ChatRoomMsgViewHolderPicture.class);

    }

    private static ChatRoomSessionCustomization getChatRoomSessionCustomization() {
        if (customization == null) {
            customization = new ChatRoomSessionCustomization();
            ArrayList<BaseAction> actions = new ArrayList<>();
            if (NimApplication.HB3.equals("1"))
                actions.add(new ChatRoomRedPacketAction());
            if (NimApplication.YDK.equals("1"))
                actions.add(new YiDianCardAction());
            if (NimApplication.YHQ.equals("1"))
                actions.add(new DiscountAction());
            if (NimApplication.MP.equals("1"))
                actions.add(new BusinessCardAction());
            if (NimApplication.SC.equals("1"))
                actions.add(new MyfavoriteAction());
            actions.add(new ImageAction());
            //            actions.add(new VideoAction());
            customization.actions = actions;
        }

        return customization;
    }
}
