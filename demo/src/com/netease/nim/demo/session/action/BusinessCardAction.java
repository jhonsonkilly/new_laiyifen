package com.netease.nim.demo.session.action;

import android.content.Intent;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.session.extension.BusinessCardAttachment;
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
public class BusinessCardAction extends BaseAction {

    public BusinessCardAction() {
        super(R.drawable.send_businesscard_selector, R.string.input_panel_businesscard);
    }

    @Override
    public void onClick() {
//        ChooseYDCard.startActivityForResult(getActivity(), makeRequestCode(200));

        ContactSelectActivity.Option advancedOption = TeamHelper.getContactSelectOption(null);
        advancedOption.multi = false;
        NimUIKit.startContactSelector(getActivity(), advancedOption, makeRequestCode(RequestCode.BUSINUSSCARD));
//        BusinessSelectActivity.start(getActivity(),makeRequestCode(RequestCode.BUSINUSSCARD));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RequestCode.BUSINUSSCARD) {

                final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                if (selected != null && !selected.isEmpty()) {
                    BusinessCardAttachment attachment = new BusinessCardAttachment(selected.get(0));
                    IMMessage message;
                    if (getContainer() != null && getContainer().sessionType == SessionTypeEnum.ChatRoom) {
                        message = ChatRoomMessageBuilder.createChatRoomCustomMessage(getAccount(), attachment);
                    } else {
                        message = MessageBuilder.createCustomMessage(getAccount(), getSessionType(), "", attachment);
                    }
                    sendMessage(message);
                } else {
                    Toast.makeText(getContainer().activity, "请选择一个联系人！", Toast.LENGTH_SHORT).show();
                }

            }

//        if (requestCode == 200) {
//            Toast.makeText(getContainer().activity, "发送了伊点卡", Toast.LENGTH_SHORT).show();
//
//        }


    }
}
