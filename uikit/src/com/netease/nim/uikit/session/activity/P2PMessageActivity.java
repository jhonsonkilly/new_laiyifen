package com.netease.nim.uikit.session.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.dialog.AddBuddyDialog;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.ShowMessageDialog;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.core.NimUIKitImpl;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.plugin.OnlineStateChangeListener;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nim.uikit.session.fragment.MessageFragment;
import com.netease.nim.uikit.session.module.SendMsgModel;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * 点对点聊天界面
 * <p/>
 * Created by huangjun on 2015/2/1.
 */
public class P2PMessageActivity extends BaseMessageActivity {

    private boolean isResume  = false;
    private String contactid = "";
    LinearLayout ll_addbuddy;
    boolean isSystemMsg = false;
    private NimUserInfo mUserInfo;
    private String source;

    public static void start(Context context, String contactId, SessionCustomization customization, IMMessage anchor, boolean isSystemMsg, String way) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        if (!isSystemMsg){
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        intent.putExtra(Extras.SYSTEMMSG, isSystemMsg);
        intent.putExtra(Extras.EXTRA_SOURCE, way);
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);


        context.startActivity(intent);
    }

    public static void start2(Context context, String contactId, SessionCustomization customization, IMMessage anchor, boolean isSystemMsg, String way) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        intent.putExtra(Extras.SYSTEMMSG, isSystemMsg);
        intent.putExtra(Extras.EXTRA_SOURCE, way);
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);


        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactid = getIntent().getStringExtra(Extras.EXTRA_ACCOUNT);
        findView();
        //获取用户信息
        mUserInfo = NimUserInfoCache.getInstance().getUserInfo(NimUIKitImpl.getAccount());

        // 单聊特例话数据，包括个人信息，
        requestBuddyInfo();
        displayOnlineState();
        registerObservers(true);
        registerOnlineStateChangeListener(true);
    }


    private void findView() {
        source = getIntent().getStringExtra(Extras.EXTRA_SOURCE);
        isSystemMsg = getIntent().getBooleanExtra(Extras.SYSTEMMSG, false);
        ll_addbuddy = (LinearLayout) findViewById(R.id.ll_addbuddy);
        if (isSystemMsg) {
            ll_addbuddy.setVisibility(View.GONE);
        }
        LinearLayout lahei = (LinearLayout) findViewById(R.id.bt_lahei);
        LinearLayout addbuddy = (LinearLayout) findViewById(R.id.bt_add);
        final TextView tgLahei = (TextView) findViewById(R.id.tv_lahei);
        final boolean[] black = {NIMClient.getService(FriendService.class).isInBlackList(contactid)};
        if (black[0]) {
            tgLahei.setText("移除黑名单");
        } else {
            tgLahei.setText("拉黑此人");
        }
        lahei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final ShowMessageDialog addDialog = new ShowMessageDialog(P2PMessageActivity.this, R.style.dialog, "确定要将\"" + NimUserInfoCache.getInstance().getUserName(contactid) + "\"添加到黑名单吗？");
                    addDialog.setListener(new ShowMessageDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                black[0] = NIMClient.getService(FriendService.class).isInBlackList(contactid);
                                if (black[0]) {
                                    NIMClient.getService(FriendService.class).removeFromBlackList(contactid).setCallback(new RequestCallback<Void>() {
                                        @Override
                                        public void onSuccess(Void param) {
                                            Toast.makeText(P2PMessageActivity.this, "移除黑名单成功", Toast.LENGTH_SHORT).show();
                                            tgLahei.setText("拉黑此人");
                                        }

                                        @Override
                                        public void onFailed(int code) {
                                            if (code == 408) {
                                                Toast.makeText(P2PMessageActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(P2PMessageActivity.this, "on failed:" + code, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onException(Throwable exception) {

                                        }
                                    });
                                } else {
                                    NIMClient.getService(FriendService.class).addToBlackList(contactid).setCallback(new RequestCallback<Void>() {
                                        @Override
                                        public void onSuccess(Void param) {
                                            Toast.makeText(P2PMessageActivity.this, "加入黑名单成功", Toast.LENGTH_SHORT).show();
                                            tgLahei.setText("移除黑名单");
                                        }

                                        @Override
                                        public void onFailed(int code) {
                                            if (code == 408) {
                                                Toast.makeText(P2PMessageActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(P2PMessageActivity.this, "on failed：" + code, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onException(Throwable exception) {

                                        }
                                    });
                                }
                            }
                            dialog.dismiss();
                        }
                    });
                    addDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        addbuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final AddBuddyDialog addDialog = new AddBuddyDialog(P2PMessageActivity.this, R.style.dialog, NimUserInfoCache.getInstance().getUserName(contactid));
                    addDialog.setListener(new AddBuddyDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                Toast.makeText(P2PMessageActivity.this, "发送验证", Toast.LENGTH_SHORT).show();

                                String msg = addDialog.getEditMessage();
                                doAddFriend(msg, false);
                            }
                            dialog.dismiss();
                        }
                    });
                    addDialog.setTitle("添加好友", contactid);
                    addDialog.setEditMessage("我是" + NIMClient.getService(UserService.class).getUserInfo(NimUIKitImpl.getAccount()).getName());
                    addDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
        registerOnlineStateChangeListener(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isResume = true;
        if (FriendDataCache.getInstance().isMyFriend(contactid)) {

            ll_addbuddy.setVisibility(View.GONE);
        } else {
            ll_addbuddy.setVisibility(View.VISIBLE);
            if (mView != null) {
                mView.setVisibility(View.GONE);
            }
        }
        findView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
    }

    private void requestBuddyInfo() {
        setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
    }

    private void registerObservers(boolean register) {
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(commandObserver, register);
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
    }

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }
    };

    private UserInfoObservable.UserInfoObserver uinfoObserver;

    OnlineStateChangeListener onlineStateChangeListener = new OnlineStateChangeListener() {
        @Override
        public void onlineStateChange(Set<String> accounts) {
            // 更新 toolbar
            if (accounts.contains(sessionId)) {
                // 按照交互来展示
                displayOnlineState();
            }
        }
    };

    private void registerOnlineStateChangeListener(boolean register) {
        if (!NimUIKitImpl.enableOnlineState()) {
            return;
        }
        if (register) {
            NimUIKitImpl.addOnlineStateChangeListeners(onlineStateChangeListener);
        } else {
            NimUIKitImpl.removeOnlineStateChangeListeners(onlineStateChangeListener);
        }
    }

    private void displayOnlineState() {
        if (!NimUIKitImpl.enableOnlineState()) {
            return;
        }
        String detailContent = NimUIKitImpl.getOnlineStateContentProvider().getDetailDisplay(sessionId);
        setSubTitle(detailContent);
    }

    private void registerUserInfoObserver() {
        if (uinfoObserver == null) {
            uinfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    if (accounts.contains(sessionId)) {
                        requestBuddyInfo();
                    }
                }
            };
        }

        UserInfoHelper.registerObserver(uinfoObserver);
    }

    private void unregisterUserInfoObserver() {
        if (uinfoObserver != null) {
            UserInfoHelper.unregisterObserver(uinfoObserver);
        }
    }

    /**
     * 命令消息接收观察者
     */
    Observer<CustomNotification> commandObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            if (!sessionId.equals(message.getSessionId()) || message.getSessionType() != SessionTypeEnum.P2P) {
                return;
            }
            showCommandMessage(message);
        }
    };

    protected void showCommandMessage(CustomNotification message) {
        if (!isResume) {
            return;
        }

        String content = message.getContent();
        try {
            JSONObject json = JSON.parseObject(content);
            int id = json.getIntValue("id");
            if (id == 1) {
                // 正在输入
                Toast.makeText(P2PMessageActivity.this, "对方正在输入...", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(P2PMessageActivity.this, "command: " + content, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }
    }

    @Override
    protected MessageFragment fragment() {
        Bundle arguments = getIntent().getExtras();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
        //        MessageFragment fragment = new MessageFragment(getIntent().getBooleanExtra(Extras.SYSTEMMSG, false));
        MessageFragment fragment = new MessageFragment(getIntent().getBooleanExtra(Extras.SYSTEMMSG, false));
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.message_fragment_container);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.nim_message_activity;
    }

    @Override
    protected void initToolBar() {
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.toolbar, options);

    }


    private void doAddFriend(final String msg, boolean addDirectly) {
        if (!NetworkUtil.isNetAvailable(this)) {
            Toast.makeText(P2PMessageActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }

        final VerifyType verifyType = addDirectly ? VerifyType.DIRECT_ADD : VerifyType.VERIFY_REQUEST;
        DialogMaker.showProgressDialog(this, "", true);
        NIMClient.getService(FriendService.class).addFriend(new AddFriendData(contactid, verifyType, msg))
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        DialogMaker.dismissProgressDialog();
                        if (VerifyType.DIRECT_ADD == verifyType) {
                            Toast.makeText(P2PMessageActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(P2PMessageActivity.this, "添加好友请求发送成功", Toast.LENGTH_SHORT).show();
                        }
                        sendMsg(msg);
                    }

                    @Override
                    public void onFailed(int code) {
                        DialogMaker.dismissProgressDialog();
                        if (code == 408) {
                            Toast.makeText(P2PMessageActivity.this, R.string.network_is_not_available, Toast
                                    .LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(P2PMessageActivity.this, "on failed:" + code, Toast
                                    .LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        DialogMaker.dismissProgressDialog();
                    }
                });

    }

    /**
     * 发送添加好友信息
     *
     * @param msg
     */
    private void sendMsg(String msg) {
        NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(NimUIKitImpl.getAccount());

        HashMap<Object, Object> map = new HashMap<>();
        SendMsgModel sendMsgModel = new SendMsgModel();
        sendMsgModel.setSysFriendInfoTitle("新好友提醒");
        String tip = "申请加您为好友";
        String deft = userInfoMe.getName() + ":我是" + userInfoMe.getName();
        sendMsgModel.setSysFriendInfoFormid(NimUIKitImpl.getAccount());
        sendMsgModel.setSysFriendInfoFormName(userInfoMe.getName());
        sendMsgModel.setSysFriendInfoMessage(msg.equals("") ? deft + tip : userInfoMe.getName() + ":" + "\"" + msg + "\"" + tip);
        sendMsgModel.setSysFriendInfoToId(sessionId);
        sendMsgModel.setSysFriendInfoToName(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        sendMsgModel.setSysFriendInfoIsTip("0");
        sendMsgModel.setSysFriendInfoGroupId("");
        sendMsgModel.setSource(source);
        map.put("type", 11);
        map.put("data", sendMsgModel);

        OKhttpHelper.send(this, new Gson().toJson(map), Common.AdapterPath + "sendMsg", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                System.out.println();
            }

            @Override
            public void onSendFail(String err) {
                System.out.println();
            }
        });


    }
}
