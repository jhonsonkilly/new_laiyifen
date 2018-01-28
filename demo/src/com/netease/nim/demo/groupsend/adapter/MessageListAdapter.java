package com.netease.nim.demo.groupsend.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.demo.groupsend.GroupSendActivity;
import com.netease.nim.demo.groupsend.model.GroupSendModel;
import com.netease.nim.demo.groupsend.model.MessageListModel;
import com.netease.nim.demo.main.model.QuickBean;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasmin on 2017/9/1.
 */

public class MessageListAdapter extends BaseQuickAdapter<MessageListModel.DataBean,BaseViewHolder> {

    public MessageListAdapter(int layoutResId, List<MessageListModel.DataBean> data) {
        super(layoutResId, data);
    }
    public MessageListAdapter(List<MessageListModel.DataBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MessageListModel.DataBean quickBean) {

        baseViewHolder.setText(R.id.tv_sendame,"收件人："+getSendName(quickBean.getSendList()));
        baseViewHolder.setText(R.id.tv_date,quickBean.getSendDate());
        baseViewHolder.setText(R.id.tv_send_content,quickBean.getContent());
    }

    private String  getSendName(List<GroupSendModel> datas){

        String sendTo = "";
        List<String> accounts = new ArrayList<String>();


        for (GroupSendModel model:datas){
            if (model.getItemType() == GroupSendActivity.P2P){

                final NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(model.getP2pAccount());
                if (model.isSelect())
                    accounts.add(model.getP2pAccount());
            }else{
                if (model.isSelect())
                    sendTo = sendTo+model.getName()+"，";
            }
        }
        NIMClient.getService(UserService.class).fetchUserInfo(accounts);

        for (String account:accounts){
            NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(account);

            sendTo = sendTo+NimUserInfoCache.getInstance().getUserDisplayNameEx(account)+"，";
        }

        sendTo = sendTo.substring(0,sendTo.length()-1);
        return sendTo;
    }

}
