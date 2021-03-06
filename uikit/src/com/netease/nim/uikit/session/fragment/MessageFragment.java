package com.netease.nim.uikit.session.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.ait.AitManager;
import com.netease.nim.uikit.cache.RobotInfoCache;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.core.NimUIKitImpl;
import com.netease.nim.uikit.plugin.CustomPushContentProvider;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.session.actions.ImageAction;
import com.netease.nim.uikit.session.actions.VideoAction;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nim.uikit.session.module.Container;
import com.netease.nim.uikit.session.module.ModuleProxy;
import com.netease.nim.uikit.session.module.input.InputPanel;
import com.netease.nim.uikit.session.module.list.MessageListPanelEx;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.MemberPushOption;
import com.netease.nimlib.sdk.msg.model.MessageReceipt;
import com.netease.nimlib.sdk.robot.model.NimRobotInfo;
import com.netease.nimlib.sdk.robot.model.RobotAttachment;
import com.netease.nimlib.sdk.robot.model.RobotMsgType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天界面基类
 * <p/>
 * Created by huangjun on 2015/2/1.
 */
public class MessageFragment extends TFragment implements ModuleProxy {

    private boolean isSystemMsg = false;

    private View rootView;

    private SessionCustomization customization;

    private BaseAction DistinguishAction;//识别消息action

    protected static final String TAG = "MessageActivity";

    // 聊天对象
    protected String sessionId; // p2p对方Account或者群id

    protected SessionTypeEnum sessionType;

    // modules
    protected InputPanel inputPanel;
    protected MessageListPanelEx messageListPanel;

    protected AitManager aitManager;

    protected Handler mHandler = new Handler();

    public MessageFragment(boolean isSystemMsg) {
        this.isSystemMsg = isSystemMsg;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.nim_message_fragment, container, false);
        return rootView;
    }

    /**
     * ***************************** life cycle *******************************
     */

    @Override
    public void onPause() {
        super.onPause();

        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE,
                SessionTypeEnum.None);
        inputPanel.onPause();
        messageListPanel.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        messageListPanel.onResume();

        NIMClient.getService(MsgService.class).setChattingAccount(sessionId, sessionType);
        getActivity().setVolumeControlStream(AudioManager.STREAM_VOICE_CALL); // 默认使用听筒播放

        if (customization != null) {
            //读取自定义背景
            SharedPreferences sp = getActivity().getSharedPreferences("SET", Context.MODE_PRIVATE);
            messageListPanel.setChattingBackground(sp.getString(sessionId, null));

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        messageListPanel.onDestroy();
        registerObservers(false);
        if (inputPanel != null) {
            inputPanel.onDestroy();
        }
        aitManager.reset();
    }

    public boolean onBackPressed() {
        if (inputPanel.collapse(true)) {
            return true;
        }

        if (messageListPanel.onBackPressed()) {
            return true;
        }
        return false;
    }

    public void refreshMessageList() {
        messageListPanel.refreshMessageList();
    }

    private void parseIntent() {
        sessionId = getArguments().getString(Extras.EXTRA_ACCOUNT);
        sessionType = (SessionTypeEnum) getArguments().getSerializable(Extras.EXTRA_TYPE);
        IMMessage anchor = (IMMessage) getArguments().getSerializable(Extras.EXTRA_ANCHOR);

        customization = (SessionCustomization) getArguments().getSerializable(Extras.EXTRA_CUSTOMIZATION);
        Container container = new Container(getActivity(), sessionId, sessionType, this);

        if (messageListPanel == null) {
            messageListPanel = new MessageListPanelEx(getActivity(), container, rootView, anchor, false, false);
        } else {
            messageListPanel.reload(container, anchor);
        }

        if (inputPanel == null) {
            inputPanel = new InputPanel(container, rootView, getActionList());
            inputPanel.setCustomization(customization);
        } else {
            inputPanel.reload(container, customization);
        }

        //是否是系统消息
        if (isSystemMsg) {
            customization.buttons = null;
            inputPanel.isBottomLayoutShow(false);
        } else {
            inputPanel.isBottomLayoutShow(true);
        }

        aitManager = new AitManager(getContext(), sessionType == SessionTypeEnum.Team ? sessionId : null, true);

        inputPanel.addAitTextWatcher(aitManager);

        aitManager.setTextChangeListener(inputPanel);

        inputPanel.switchRobotMode(RobotInfoCache.getInstance().getRobotByAccount(sessionId) != null);

        registerObservers(true);

        if (customization != null) {

            //读取自定义背景
            SharedPreferences sp = getActivity().getSharedPreferences("SET", Context.MODE_PRIVATE);
            //            messageListPanel.setChattingBackground(customization.backgroundUri, customization.backgroundColor);
            //            messageListPanel.setChattingBackground(sp.getString(sessionId,null), customization.backgroundColor);
            messageListPanel.setChattingBackground(sp.getString(sessionId, null));
        }
    }

    /**
     * ************************* 消息收发 **********************************
     */
    // 是否允许发送消息
    protected boolean isAllowSendMessage(final IMMessage message) {
        return true;
    }

    /**
     * ****************** 观察者 **********************
     */

    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(incomingMessageObserver, register);
        service.observeMessageReceipt(messageReceiptObserver, register);
    }

    /**
     * 消息接收观察者
     */
    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            if (messages == null || messages.isEmpty()) {
                return;
            }

            messageListPanel.onIncomingMessage(messages);
            //            sendMsgReceipt(); // 发送已读回执
        }
    };

    private Observer<List<MessageReceipt>> messageReceiptObserver = new Observer<List<MessageReceipt>>() {
        @Override
        public void onEvent(List<MessageReceipt> messageReceipts) {
            receiveReceipt();
        }
    };


    /**
     * ********************** implements ModuleProxy *********************
     */
    @Override
    public boolean sendMessage(IMMessage message) {
        if (!isAllowSendMessage(message)) {
            return false;
        }

        appendTeamMemberPush(message);
        message = changeToRobotMsg(message);
        appendPushConfig(message);
        // send message to server and save to db
        NIMClient.getService(MsgService.class).sendMessage(message, false);
        messageListPanel.onMsgSend(message);
        aitManager.reset();
        final IMMessage finalMessage = message;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (finalMessage.getStatus() == MsgStatusEnum.fail) {
                    // 向群里插入一条Tip消息，使得该群能立即出现在最近联系人列表（会话列表）中，满足部分开发者需求。
                    Map<String, Object> content = new HashMap<>(1);
                    content.put("content", "对方已设置拒绝接收您的消息");
                    // 创建tip消息，teamId需要开发者已经存在的team的teamId
                    IMMessage msg = MessageBuilder.createTipMessage(finalMessage.getSessionId(), SessionTypeEnum.P2P);
                    msg.setRemoteExtension(content);
                    // 自定义消息配置选项
                    CustomMessageConfig config = new CustomMessageConfig();
                    // 消息不计入未读
                    config.enableUnreadCount = false;
                    msg.setConfig(config);
                    // 消息发送状态设置为success
                    msg.setStatus(MsgStatusEnum.success);
                    // 保存消息到本地数据库，但不发送到服务器
                    NIMClient.getService(MsgService.class).saveMessageToLocal(msg, true);
                }
            }
        }, 300);
        return true;
    }

    private void appendTeamMemberPush(IMMessage message) {
        if (sessionType == SessionTypeEnum.Team) {
            List<String> pushList = aitManager.getAitTeamMember();
            if (pushList == null || pushList.isEmpty()) {
                return;
            }
            MemberPushOption memberPushOption = new MemberPushOption();
            memberPushOption.setForcePush(true);
            memberPushOption.setForcePushContent(message.getContent());
            memberPushOption.setForcePushList(pushList);
            message.setMemberPushOption(memberPushOption);
        }
    }

    private IMMessage changeToRobotMsg(IMMessage message) {
        if (message.getMsgType() == MsgTypeEnum.robot) {
            return message;
        }
        if (isChatWithRobot()) {
            if (message.getMsgType() == MsgTypeEnum.text && message.getContent() != null) {
                String content = message.getContent().equals("") ? " " : message.getContent();
                message = MessageBuilder.createRobotMessage(message.getSessionId(), message.getSessionType(), message.getSessionId(), content, RobotMsgType.TEXT, content, null, null);
            }
        } else {
            String robotAccount = aitManager.getAitRobot();
            if (TextUtils.isEmpty(robotAccount)) {
                return message;
            }
            String text = message.getContent();
            String content = aitManager.removeRobotAitString(text, robotAccount);
            content = content.equals("") ? " " : content;
            message = MessageBuilder.createRobotMessage(message.getSessionId(), message.getSessionType(), robotAccount, text, RobotMsgType.TEXT, content, null, null);

        }
        return message;
    }

    private boolean isChatWithRobot() {
        return RobotInfoCache.getInstance().getRobotByAccount(sessionId) != null;
    }

    private void appendPushConfig(IMMessage message) {
        CustomPushContentProvider customConfig = NimUIKitImpl.getCustomPushContentProvider();
        if (customConfig != null) {
            String content = customConfig.getPushContent(message);
            Map<String, Object> payload = customConfig.getPushPayload(message);
            message.setPushContent(content);
            message.setPushPayload(payload);
        }
    }

    @Override
    public void onInputPanelExpand() {
        messageListPanel.scrollToBottom();
    }

    @Override
    public void shouldCollapseInputPanel() {
        inputPanel.collapse(false);
    }

    @Override
    public boolean isLongClickEnabled() {
        return !inputPanel.isRecording();
    }

    @Override
    public void onItemFooterClick(IMMessage message) {
        if (messageListPanel.isSessionMode()) {
            RobotAttachment attachment = (RobotAttachment) message.getAttachment();
            NimRobotInfo robot = RobotInfoCache.getInstance().getRobotByAccount(attachment.getFromRobotAccount());
            aitManager.insertAitRobot(robot.getAccount(), robot.getName(), inputPanel.getEditSelectionStart());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        aitManager.onActivityResult(requestCode, resultCode, data);
        inputPanel.onActivityResult(requestCode, resultCode, data);
        messageListPanel.onActivityResult(requestCode, resultCode, data);
    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {

        List<BaseAction> actions = new ArrayList<>();
        List<BaseAction> items = new ArrayList<>();
        if (customization != null && customization.actions != null) {
//            actions.addAll(customization.actions);
            int j = 0;
            for (BaseAction action:customization.actions){
                if (j == customization.actions.size()-1){
                    DistinguishAction = action;
                    Container container = new Container(getActivity(), sessionId, sessionType, this);
                    DistinguishAction.setContainer(container);
                }
                items.add(action);
                j++;
            }
            for (int i =0;i<customization.actions.size()-1;i++){
                actions.add(items.get(i));
            }
        }
        actions.add(new ImageAction());
        actions.add(new VideoAction());
        //        actions.add(new LocationAction());


        return actions;
    }

    /**
     * 发送已读回执
     */
    private void sendMsgReceipt() {
        messageListPanel.sendReceipt();
    }

    /**
     * 收到已读回执
     */
    public void receiveReceipt() {
        messageListPanel.receiveReceipt();
    }

    @Override
    public void sendDisMessage(String msg, IMMessage message) {
        DistinguishAction.onClick();
    }
}
