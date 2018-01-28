package com.netease.nim.demo.session.action;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.session.extension.BusinessCardAttachment;
import com.netease.nim.demo.session.extension.CustomAttachmentType;
import com.netease.nim.demo.session.extension.YiDianCardAttachment;
import com.netease.nim.demo.yidiancard.ChooseYDCard;
import com.netease.nim.demo.yidiancard.model.YKD001Model;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.session.constant.RequestCode;
import com.netease.nim.uikit.team.helper.TeamHelper;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;

/**
 * Created by hzxuwen on 2015/6/11.
 */
public class YiDianCardAction extends BaseAction {

    public YiDianCardAction() {
        super(R.drawable.send_yidiancard_selector, R.string.input_panel_yidian_card);
    }

    @Override
    public void onClick() {

//        ContactSelectActivity.Option advancedOption = TeamHelper.getContactSelectOption(null);
//        advancedOption.multi = false;
//        NimUIKit.startContactSelector(getActivity(), advancedOption, makeRequestCode(RequestCode.BUSINUSSCARD));
        ChooseYDCard.startActivityForResult(getActivity(), makeRequestCode(CustomAttachmentType.YiDianCard));

//        sendMessage(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CustomAttachmentType.YiDianCard) {

                YKD001Model.RowsBean  bean = (YKD001Model.RowsBean)data.getSerializableExtra("YDKMOEL");

                YiDianCardAttachment attachment = new YiDianCardAttachment(bean);
                IMMessage message;
                message = MessageBuilder.createCustomMessage(getAccount(), getSessionType(), "", attachment);
                sendMessage(message);

            }
        }
    }
}
