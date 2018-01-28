package com.netease.nim.demo.session.action;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.session.extension.DistinguishAttachment;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.session.module.SuggestModel;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by hzxuwen on 2015/6/11.
 */
public class DistinguishAction extends BaseAction {

    public DistinguishAction() {
        super(R.drawable.send_businesscard_selector, R.string.input_panel_businesscard);
    }

    @Override
    public void onClick() {
//        ChooseYDCard.startActivityForResult(getActivity(), makeRequestCode(200));

//        CYKD001Model.RowsBean  bean = (YKD001Model.RowsBean)data.getSerializableExtra("YDKMOEL");
        SharedPreferences sp = NimUIKit.getContext().getSharedPreferences("InputPanel", Context.MODE_PRIVATE);
        String str = sp.getString("front","");
        getFronts(str);

    }


    /**
     * 根据词库查询产品
     */
    private void getFronts(String fronts) {
        if (fronts.length()<= 0){
            return;
        }
        String url = Common.AdapterPath + "suggest/"+fronts;
        OKhttpHelper.getSend(getContainer().activity, url, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    SuggestModel model = new Gson().fromJson(s, new TypeToken<SuggestModel>() {
                    }.getType());

                    if (model.getStatus()==200) {
                        DistinguishAttachment attachment = new DistinguishAttachment(new Gson().toJson(model.getData()));
                        IMMessage message;
                        message = MessageBuilder.createCustomMessage(getAccount(), getSessionType(), "", attachment);
                        sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSendFail(String err) {
            }
        });
    }

}
