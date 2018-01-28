package com.netease.nim.demo.session.action;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.discount.ChooseDiscountCard;
import com.netease.nim.demo.discount.model.YHQModel;
import com.netease.nim.demo.session.extension.CustomAttachmentType;
import com.netease.nim.demo.session.extension.DiscountAttachment;
import com.netease.nim.demo.session.extension.YiDianCardAttachment;
import com.netease.nim.demo.util.Constact;
import com.netease.nim.demo.yidiancard.ChooseYDCard;
import com.netease.nim.demo.yidiancard.model.YKD001Model;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by hzxuwen on 2015/6/11.
 */
public class DiscountAction extends BaseAction {

    public DiscountAction() {
        super(R.drawable.send_discount_selector, R.string.input_panel_discount_card);
    }

    @Override
    public void onClick() {

        //        ContactSelectActivity.Option advancedOption = TeamHelper.getContactSelectOption(null);
        //        advancedOption.multi = false;
        //        NimUIKit.startContactSelector(getActivity(), advancedOption, makeRequestCode(RequestCode.BUSINUSSCARD));
        ChooseDiscountCard.startActivityForResult(getActivity(), makeRequestCode(CustomAttachmentType.YiDianCard));

        //        sendMessage(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CustomAttachmentType.YiDianCard) {

                YHQModel.DataBean.CanUseCouponListBean  bean = (YHQModel.DataBean.CanUseCouponListBean)data.getSerializableExtra(Constact.YHQMODEL);

                DiscountAttachment attachment = new DiscountAttachment(new Gson().toJson(bean));
                IMMessage message;
                message = MessageBuilder.createCustomMessage(getAccount(), getSessionType(), "", attachment);
                sendMessage(message);

            }
        }
    }
}
