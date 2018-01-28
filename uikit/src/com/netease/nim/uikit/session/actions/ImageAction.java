package com.netease.nim.uikit.session.actions;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;

/**
 * Created by hzxuwen on 2015/6/12.
 */
public class ImageAction extends PickImageAction {

    public ImageAction() {
        super(R.drawable.pic, R.string.input_panel_photo, true);
    }

    @Override
    protected void onPicked(File file) {
        IMMessage message;
        if (getContainer() != null && getContainer().sessionType == SessionTypeEnum.ChatRoom) {
            message = ChatRoomMessageBuilder.createChatRoomImageMessage(getAccount(), file, file.getName());
        } else {
            message = MessageBuilder.createImageMessage(getAccount(), getSessionType(), file, file.getName());
        }

        ImageAttachment imageAttachment = (ImageAttachment) message.getAttachment();
        if (imageAttachment.getSize() / 1024 / 1024 >= 20) {
            ToastUtil.showLongToast(getActivity(), "图片过大(超过20M),无法发送");
        } else {
            sendMessage(message);
        }
    }
}

