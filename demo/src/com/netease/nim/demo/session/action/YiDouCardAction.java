package com.netease.nim.demo.session.action;

import android.app.Activity;
import android.content.Intent;

import com.netease.nim.demo.R;
import com.netease.nim.demo.session.extension.CustomAttachmentType;
import com.netease.nim.demo.yidou.YDOUActivity;
import com.netease.nim.uikit.session.actions.BaseAction;

/**
 * Created by hzxuwen on 2015/6/11.
 */
public class YiDouCardAction extends BaseAction {

    public YiDouCardAction() {
        super(R.drawable.send_yidou_selector, R.string.input_panel_yidou_card);
    }

    @Override
    public void onClick() {

        //        ContactSelectActivity.Option advancedOption = TeamHelper.getContactSelectOption(null);
        //        advancedOption.multi = false;
        //        NimUIKit.startContactSelector(getActivity(), advancedOption, makeRequestCode(RequestCode.BUSINUSSCARD));
        YDOUActivity.startActivityForResult(getActivity(), makeRequestCode(CustomAttachmentType.YIDOU));

        //        sendMessage(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CustomAttachmentType.YIDOU) {

                /*YKD001Model.RowsBean ydkmodel = (YKD001Model.RowsBean) data.getSerializableExtra("YDKMODEL");

                YiDianCardAttachment attachment = new YiDianCardAttachment(ydkmodel);
                IMMessage message;
                message = MessageBuilder.createCustomMessage(getAccount(), getSessionType(), attachment.getContent(), attachment);
                sendMessage(message);*/

            }
        }
    }
}
