package com.netease.nim.uikit.session.module.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseFetchLoadAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.IRecyclerView;
import com.netease.nim.uikit.common.ui.recyclerview.listener.OnItemClickListener;
import com.netease.nim.uikit.common.ui.recyclerview.loadmore.MsgListFetchLoadMoreView;
import com.netease.nim.uikit.common.util.C;
import com.netease.nim.uikit.common.util.file.AttachmentStore;
import com.netease.nim.uikit.common.util.media.BitmapDecoder;
import com.netease.nim.uikit.common.util.storage.StorageUtil;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.common.util.sys.ClipboardUtil;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.core.NimUIKitImpl;
import com.netease.nim.uikit.core.UserPreferences;
import com.netease.nim.uikit.forward.ForwardChooseActivity;
import com.netease.nim.uikit.forward.model.GroupSendModel;
import com.netease.nim.uikit.robot.parser.elements.group.LinkElement;
import com.netease.nim.uikit.session.activity.VoiceTrans;
import com.netease.nim.uikit.session.audio.MessageAudioControl;
import com.netease.nim.uikit.session.extension.DistinguishAttachment;
import com.netease.nim.uikit.session.helper.MessageHelper;
import com.netease.nim.uikit.session.helper.MessageListPanelHelper;
import com.netease.nim.uikit.session.module.AttachModel;
import com.netease.nim.uikit.session.module.CollectModel;
import com.netease.nim.uikit.session.module.Container;
import com.netease.nim.uikit.session.module.MsgModel;
import com.netease.nim.uikit.session.module.SuggestModel;
import com.netease.nim.uikit.session.viewholder.robot.RobotLinkView;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.uinfo.UserInfoHelper;
import com.netease.nim.uikit.uinfo.UserInfoObservable;
import com.netease.nim.uikit.util.QRCodeUtil;
import com.netease.nim.uikit.util.SaveFile;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.AttachmentProgress;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.netease.nimlib.sdk.robot.model.RobotAttachment;
import com.netease.nimlib.sdk.robot.model.RobotMsgType;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * 基于RecyclerView的消息收发模块
 * Created by huangjun on 2016/12/27.
 */
public class MessageListPanelEx {

    private static final int REQUEST_CODE_FORWARD_PERSON = 0x01;
    private static final int REQUEST_CODE_FORWARD_TEAM   = 0x02;
    private static final int REQUEST_CODE_FORWARD_ALL    = 0x03;

    // container
    private Container container;
    private View rootView;

    // message list view
    private RecyclerView    messageListView;
    private List<IMMessage> items;
    private MsgAdapter adapter;
    private ImageView listviewBk;

    // 新消息到达提醒
    private IncomingMsgPrompt incomingMsgPrompt;
    private Handler uiHandler;

    // 仅显示消息记录，不接收和发送消息
    private boolean recordOnly;
    // 从服务器拉取消息记录
    private boolean remote;

    // 语音转文字
    private VoiceTrans voiceTrans;

    // 待转发消息
    private IMMessage forwardMessage;

    // 背景图片缓存
    private static Pair<String, Bitmap> background;

    private Context mcontex;
    private CollectModel mModel;
    private AlertDialog mDialog;
    private AttachModel mAttachModel;

    public MessageListPanelEx(Context mContex, Container container, View rootView, boolean recordOnly, boolean remote) {
        this(mContex, container, rootView, null, recordOnly, remote);
    }

    public MessageListPanelEx(Container container, View rootView, boolean recordOnly, boolean remote) {
        this(container, rootView, null, recordOnly, remote);
    }

    public MessageListPanelEx(Context mContex, Container container, View rootView, IMMessage anchor, boolean recordOnly, boolean remote) {
        this.container = container;
        this.rootView = rootView;
        this.recordOnly = recordOnly;
        this.remote = remote;
        this.mcontex = mContex;
        init(anchor);
    }

    public MessageListPanelEx(Container container, View rootView, IMMessage anchor, boolean recordOnly, boolean remote) {
        this.container = container;
        this.rootView = rootView;
        this.recordOnly = recordOnly;
        this.remote = remote;

        init(anchor);
    }

    public void onResume() {
        setEarPhoneMode(UserPreferences.isEarPhoneModeEnable());

    }

    public void onPause() {
        MessageAudioControl.getInstance(container.activity).stopAudio();
    }

    public void onDestroy() {
        registerObservers(false);
    }

    public boolean onBackPressed() {
        uiHandler.removeCallbacks(null);
        MessageAudioControl.getInstance(container.activity).stopAudio(); // 界面返回，停止语音播放
        if (voiceTrans != null && voiceTrans.isShow()) {
            voiceTrans.hide();
            return true;
        }
        return false;
    }

    public void reload(Container container, IMMessage anchor) {
        this.container = container;

        if (adapter != null) {
            adapter.clearData();
        }

        initFetchLoadListener(anchor);
    }

    private void init(IMMessage anchor) {
        initListView(anchor);

        this.uiHandler = new Handler();
        if (!recordOnly) {
            incomingMsgPrompt = new IncomingMsgPrompt(container.activity, rootView, messageListView, adapter, uiHandler);
        }

        registerObservers(true);
    }

    private void initListView(IMMessage anchor) {
        listviewBk = (ImageView) rootView.findViewById(R.id.message_activity_background);

        // RecyclerView
        messageListView = (RecyclerView) rootView.findViewById(R.id.messageListView);
        messageListView.setLayoutManager(new LinearLayoutManager(container.activity));
        messageListView.requestDisallowInterceptTouchEvent(true);
        messageListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    container.proxy.shouldCollapseInputPanel();
                }
            }
        });
        messageListView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // adapter
        items = new ArrayList<>();
        adapter = new MsgAdapter(messageListView, items, container);
        adapter.setFetchMoreView(new MsgListFetchLoadMoreView());
        adapter.setLoadMoreView(new MsgListFetchLoadMoreView());
        adapter.setEventListener(new MsgItemEventListener());
        initFetchLoadListener(anchor);
        messageListView.setAdapter(adapter);
        messageListView.addOnItemTouchListener(listener);
    }

    /**
     * 根据词库查询产品
     */
    private void getFronts(String fronts, final int position) {
        if (fronts.length()<= 0){
            return;
        }
        String url = Common.AdapterPath + "suggest/"+fronts;
        OKhttpHelper.getSend(container.activity, url, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    SuggestModel model = new Gson().fromJson(s, new TypeToken<SuggestModel>() {
                    }.getType());

                    if (model.getStatus()==200) {
                        DistinguishAttachment attachment = new DistinguishAttachment(new Gson().toJson(model.getData()));
                        IMMessage message;
                        Map<String, Object> content = new HashMap<>(1);
                        content.put("content", "true");
                        message = MessageBuilder.createCustomMessage(container.account, SessionTypeEnum.P2P, "", attachment);
                        message.setLocalExtension(content);
                        List<IMMessage> messages = new ArrayList<IMMessage>();

                        for (int i=0;i<position+1;i++){
                            messages.add(items.get(i));
                        }
                        messages.add(message);
                        for (int j = position+1;j<items.size();j++){
                            messages.add(items.get(j));
                        }

                        items.clear();
                        items.addAll(messages);
                        adapter.notifyDataSetChanged();
                        messageListView.scrollToPosition(adapter.getItemCount()-1);
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

    private OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(IRecyclerView adapter1, View view, int position) {

            //产品识别注销
//            IMMessage thisMessage = items.get(position);
//            String key = thisMessage.getContent();
//
//            if (StringUtil.isEmpty(key)){
//                return;
//            }
//
//
//            if (position+1<items.size() ){
//                IMMessage nextMessage = items.get(position+1);
//                String str;
//                if (nextMessage.getLocalExtension()!= null&&nextMessage.getLocalExtension().containsKey("content")) {
//                    str = nextMessage.getLocalExtension().get("content") + "";
//                } else{
//                    str = "";
//                }
//                if (str.length()>0){
//                    deleteItem(nextMessage,false);
//                }else{
//                    List<String> list = new Gson().fromJson(SaveFile.read(container.activity,"RecognizeData"), new TypeToken<List<String>>() {}.getType());
//                    for (String string:list){
//                        if (string.indexOf(key)!= -1){
//                            getFronts(key,position);
//                        }
//                    }
//                }
//            }else if (position+1==items.size()){
//                List<String> list = new Gson().fromJson(SaveFile.read(container.activity,"RecognizeData"), new TypeToken<List<String>>() {}.getType());
//                for (String string:list){
//                    if (string.indexOf(key)!= -1){
//                        getFronts(key,position);
//                    }
//                }
//            }


        }

        @Override
        public void onItemLongClick(IRecyclerView adapter, View view, int position) {
        }

        @Override
        public void onItemChildClick(IRecyclerView adapter2, View view, int position) {
            if (isSessionMode() && view != null && view instanceof RobotLinkView) {
                RobotLinkView robotLinkView = (RobotLinkView) view;
                // robotLinkView.onClick();
                LinkElement element = robotLinkView.getElement();
                if (element != null) {
                    element.getTarget();
                    if (LinkElement.TYPE_URL.equals(element.getType())) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(element.getTarget());
                        intent.setData(content_url);
                        try {
                            container.activity.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(container.activity, "路径错误", Toast.LENGTH_SHORT).show();
                        }

                    } else if (LinkElement.TYPE_BLOCK.equals(element.getType())) {
                        // 发送点击的block
                        IMMessage message = adapter.getItem(position);
                        if (message != null) {
                            String robotAccount = ((RobotAttachment) message.getAttachment()).getFromRobotAccount();
                            IMMessage robotMsg = MessageBuilder.createRobotMessage(message.getSessionId(), message.getSessionType(), robotAccount,
                                    robotLinkView.getShowContent(), RobotMsgType.LINK, "", element.getTarget(), element.getParams());
                            container.proxy.sendMessage(robotMsg);
                        }
                    }
                }
            }
        }
    };

    public boolean isSessionMode() {
        return !recordOnly && !remote;
    }

    private void initFetchLoadListener(IMMessage anchor) {
        MessageLoader loader = new MessageLoader(anchor, remote);

        if (recordOnly && !remote) {
            // 双向Load
            adapter.setOnFetchMoreListener(loader);
            adapter.setOnLoadMoreListener(loader);
        } else {
            // 只下来加载old数据
            adapter.setOnFetchMoreListener(loader);
        }
    }

    // 刷新消息列表
    public void refreshMessageList() {
        container.activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void scrollToBottom() {
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doScrollToBottom();
            }
        }, 200);
    }

    private void doScrollToBottom() {
        messageListView.scrollToPosition(adapter.getBottomDataPosition());
    }

    public void onIncomingMessage(List<IMMessage> messages) {
        boolean needScrollToBottom = isLastMessageVisible();
        boolean needRefresh = false;
        List<IMMessage> addedListItems = new ArrayList<>(messages.size());
        for (IMMessage message : messages) {
            if (isMyMessage(message)) {
                items.add(message);
                addedListItems.add(message);
                needRefresh = true;
            }
        }
        if (needRefresh) {
            sortMessages(items);
            adapter.notifyDataSetChanged();
        }

        adapter.updateShowTimeItem(addedListItems, false, true);

        // incoming messages tip
        IMMessage lastMsg = messages.get(messages.size() - 1);
        if (isMyMessage(lastMsg)) {
            if (needScrollToBottom) {
                doScrollToBottom();
            } else if (incomingMsgPrompt != null && lastMsg.getSessionType() != SessionTypeEnum.ChatRoom) {
                incomingMsgPrompt.show(lastMsg);
            }
        }
    }

    private boolean isLastMessageVisible() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) messageListView.getLayoutManager();
        int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
        return lastVisiblePosition >= adapter.getBottomDataPosition();
    }

    // 发送消息后，更新本地消息列表
    public void onMsgSend(IMMessage message) {
        List<IMMessage> addedListItems = new ArrayList<>(1);
        addedListItems.add(message);
        adapter.updateShowTimeItem(addedListItems, false, true);

        adapter.appendData(message);

        doScrollToBottom();
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortMessages(List<IMMessage> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private static Comparator<IMMessage> comp = new Comparator<IMMessage>() {

        @Override
        public int compare(IMMessage o1, IMMessage o2) {
            long time = o1.getTime() - o2.getTime();
            return time == 0 ? 0 : (time < 0 ? -1 : 1);
        }
    };

    /**
     * ************************* 观察者 ********************************
     */

    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeMsgStatus(messageStatusObserver, register);
        service.observeAttachmentProgress(attachmentProgressObserver, register);
        service.observeRevokeMessage(revokeMessageObserver, register);
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }

        MessageListPanelHelper.getInstance().registerObserver(incomingLocalMessageObserver, register);
    }

    /**
     * 消息状态变化观察者
     */
    private Observer<IMMessage> messageStatusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
            if (isMyMessage(message)) {
                onMessageStatusChange(message);
            }
        }
    };

    /**
     * 消息附件上传/下载进度观察者
     */
    private Observer<AttachmentProgress> attachmentProgressObserver = new Observer<AttachmentProgress>() {
        @Override
        public void onEvent(AttachmentProgress progress) {
            onAttachmentProgressChange(progress);
        }
    };

    /**
     * 本地消息接收观察者
     */
    private MessageListPanelHelper.LocalMessageObserver incomingLocalMessageObserver = new MessageListPanelHelper.LocalMessageObserver() {
        @Override
        public void onAddMessage(IMMessage message) {
            if (message == null || !container.account.equals(message.getSessionId())) {
                return;
            }

            onMsgSend(message);
        }

        @Override
        public void onClearMessages(String account) {
            items.clear();
            refreshMessageList();
            adapter.fetchMoreEnd(null, true);
        }
    };

    /**
     * 消息撤回观察者
     */
    private Observer<IMMessage> revokeMessageObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
            if (message == null || !container.account.equals(message.getSessionId())) {
                return;
            }

            deleteItem(message, false);
        }
    };

    private void onMessageStatusChange(IMMessage message) {
        int index = getItemIndex(message.getUuid());
        if (index >= 0 && index < items.size()) {
            IMMessage item = items.get(index);
            item.setStatus(message.getStatus());
            item.setAttachStatus(message.getAttachStatus());

            // 处理语音、音视频通话
            if (item.getMsgType() == MsgTypeEnum.audio || item.getMsgType() == MsgTypeEnum.avchat) {
                item.setAttachment(message.getAttachment()); // 附件可能更新了
            }

            // resend的的情况，可能时间已经变化了，这里要重新检查是否要显示时间
            List<IMMessage> msgList = new ArrayList<>(1);
            msgList.add(message);
            adapter.updateShowTimeItem(msgList, false, true);

            refreshViewHolderByIndex(index);
        }
    }

    private void onAttachmentProgressChange(AttachmentProgress progress) {
        int index = getItemIndex(progress.getUuid());
        if (index >= 0 && index < items.size()) {
            IMMessage item = items.get(index);
            float value = (float) progress.getTransferred() / (float) progress.getTotal();
            adapter.putProgress(item, value);
            refreshViewHolderByIndex(index);
        }
    }

    public boolean isMyMessage(IMMessage message) {
        return message.getSessionType() == container.sessionType
                && message.getSessionId() != null
                && message.getSessionId().equals(container.account);
    }

    /**
     * 刷新单条消息
     */
    private void refreshViewHolderByIndex(final int index) {
        container.activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (index < 0) {
                    return;
                }

                adapter.notifyDataItemChanged(index);
            }
        });
    }

    private int getItemIndex(String uuid) {
        for (int i = 0; i < items.size(); i++) {
            IMMessage message = items.get(i);
            if (TextUtils.equals(message.getUuid(), uuid)) {
                return i;
            }
        }

        return -1;
    }

    public void setChattingBackground(String uriString, int color) {
        if (uriString != null) {
            Uri uri = Uri.parse(uriString);
            if (uri.getScheme().equalsIgnoreCase("file") && uri.getPath() != null) {
                listviewBk.setImageBitmap(getBackground(uri.getPath()));
            } else if (uri.getScheme().equalsIgnoreCase("android.resource")) {
                List<String> paths = uri.getPathSegments();
                if (paths == null || paths.size() != 2) {
                    return;
                }
                String type = paths.get(0);
                String name = paths.get(1);
                String pkg = uri.getHost();
                int resId = container.activity.getResources().getIdentifier(name, type, pkg);
                if (resId != 0) {
                    listviewBk.setBackgroundResource(resId);
                }
            }
        } else if (color != 0) {
            listviewBk.setBackgroundColor(color);
        }
    }

    //设置本地背景
    public void setChattingBackground(String uriString) {
        if (uriString != null) {
            listviewBk.setImageBitmap(getBackground(uriString));

        }
    }

    /**
     * ***************************************** 数据加载 *********************************************
     */

    private class MessageLoader implements BaseFetchLoadAdapter.RequestLoadMoreListener, BaseFetchLoadAdapter.RequestFetchMoreListener {

        private static final int LOAD_MESSAGE_COUNT = 20;

        private QueryDirectionEnum direction = null;

        private IMMessage anchor;
        private boolean   remote;

        private boolean firstLoad = true;

        public MessageLoader(IMMessage anchor, boolean remote) {
            this.anchor = anchor;
            this.remote = remote;
            if (remote) {
                loadFromRemote();
            } else {
                if (anchor == null) {
                    loadFromLocal(QueryDirectionEnum.QUERY_OLD);
                } else {
                    loadAnchorContext(); // 加载指定anchor的上下文
                }
            }
        }

        private RequestCallback<List<IMMessage>> callback = new RequestCallbackWrapper<List<IMMessage>>() {
            @Override
            public void onResult(int code, List<IMMessage> messages, Throwable exception) {
                if (code != ResponseCode.RES_SUCCESS || exception != null) {
                    if (direction == QueryDirectionEnum.QUERY_OLD) {
                        adapter.fetchMoreFailed();
                    } else if (direction == QueryDirectionEnum.QUERY_NEW) {
                        adapter.loadMoreFail();
                    }

                    return;
                }

                if (messages != null) {
                    onMessageLoaded(messages);
                }
            }
        };

        private void loadAnchorContext() {
            // query new, auto load old
            direction = QueryDirectionEnum.QUERY_NEW;
            NIMClient.getService(MsgService.class).queryMessageListEx(anchor(), direction, LOAD_MESSAGE_COUNT, true)
                    .setCallback(new RequestCallbackWrapper<List<IMMessage>>() {
                        @Override
                        public void onResult(int code, List<IMMessage> messages, Throwable exception) {
                            if (code != ResponseCode.RES_SUCCESS || exception != null) {
                                return;
                            }

                            onAnchorContextMessageLoaded(messages);
                        }
                    });
        }

        private void loadFromLocal(QueryDirectionEnum direction) {
            this.direction = direction;
            NIMClient.getService(MsgService.class).queryMessageListEx(anchor(), direction, LOAD_MESSAGE_COUNT, true)
                    .setCallback(callback);
        }

        private void loadFromRemote() {
            this.direction = QueryDirectionEnum.QUERY_OLD;
            NIMClient.getService(MsgService.class).pullMessageHistory(anchor(), LOAD_MESSAGE_COUNT, true)
                    .setCallback(callback);
        }

        private IMMessage anchor() {
            if (items.size() == 0) {
                return anchor == null ? MessageBuilder.createEmptyMessage(container.account, container.sessionType, 0) : anchor;
            } else {
                int index = (direction == QueryDirectionEnum.QUERY_NEW ? items.size() - 1 : 0);
                return items.get(index);
            }
        }

        private void onMessageLoaded(final List<IMMessage> messages) {
            if (messages == null) {
                return;
            }

            boolean noMoreMessage = messages.size() < LOAD_MESSAGE_COUNT;

            if (remote) {
                Collections.reverse(messages);
            }

            // 在第一次加载的过程中又收到了新消息，做一下去重
            if (firstLoad && items.size() > 0) {
                for (IMMessage message : messages) {
                    int removeIndex = 0;
                    for (IMMessage item : items) {
                        if (item.isTheSame(message)) {
                            adapter.remove(removeIndex);
                            break;
                        }
                        removeIndex++;
                    }
                }
            }

            // 加入anchor
            if (firstLoad && anchor != null) {
                messages.add(anchor);
            }

            // 在更新前，先确定一些标记
            List<IMMessage> total = new ArrayList<>();
            total.addAll(items);
            boolean isBottomLoad = direction == QueryDirectionEnum.QUERY_NEW;
            if (isBottomLoad) {
                total.addAll(messages);
            } else {
                total.addAll(0, messages);
            }
            adapter.updateShowTimeItem(total, true, firstLoad); // 更新要显示时间的消息
            //            updateReceipt(total); // 更新已读回执标签

            // 加载状态修改,刷新界面
            if (isBottomLoad) {
                // 底部加载
                if (noMoreMessage) {
                    adapter.loadMoreEnd(messages, true);
                } else {
                    adapter.loadMoreComplete(messages);
                }
            } else {
                // 顶部加载
                if (noMoreMessage) {
                    adapter.fetchMoreEnd(messages, true);
                } else {
                    adapter.fetchMoreComplete(messages);
                }
            }

            // 如果是第一次加载，updateShowTimeItem返回的就是lastShowTimeItem
            if (firstLoad) {
                doScrollToBottom();
                //                sendReceipt(); // 发送已读回执
            }

            firstLoad = false;
        }

        private void onAnchorContextMessageLoaded(final List<IMMessage> messages) {
            if (messages == null) {
                return;
            }

            if (remote) {
                Collections.reverse(messages);
            }

            int loadCount = messages.size();
            if (firstLoad && anchor != null) {
                messages.add(0, anchor);
            }

            // 在更新前，先确定一些标记
            adapter.updateShowTimeItem(messages, true, firstLoad); // 更新要显示时间的消息
            //            updateReceipt(messages); // 更新已读回执标签

            // new data
            if (loadCount < LOAD_MESSAGE_COUNT) {
                adapter.loadMoreEnd(messages, true);
            } else {
                adapter.appendData(messages);
            }

            firstLoad = false;
        }

        @Override
        public void onFetchMoreRequested() {
            // 顶部加载历史数据
            if (remote) {
                loadFromRemote();
            } else {
                loadFromLocal(QueryDirectionEnum.QUERY_OLD);
            }
        }

        @Override
        public void onLoadMoreRequested() {
            // 底部加载新数据
            if (!remote) {
                loadFromLocal(QueryDirectionEnum.QUERY_NEW);
            }
        }
    }

    private class MsgItemEventListener implements MsgAdapter.ViewHolderEventListener {

        @Override
        public void onFailedBtnClick(IMMessage message) {
            if (message.getDirect() == MsgDirectionEnum.Out) {
                // 发出的消息，如果是发送失败，直接重发，否则有可能是漫游到的多媒体消息，但文件下载
                if (message.getStatus() == MsgStatusEnum.fail) {
                    resendMessage(message); // 重发
                } else {
                    if (message.getAttachment() instanceof FileAttachment) {
                        FileAttachment attachment = (FileAttachment) message.getAttachment();
                        if (TextUtils.isEmpty(attachment.getPath())
                                && TextUtils.isEmpty(attachment.getThumbPath())) {
                            showReDownloadConfirmDlg(message);
                        }
                    } else {
                        resendMessage(message);
                    }
                }
            } else {
                showReDownloadConfirmDlg(message);
            }
        }

        @Override
        public boolean onViewHolderLongClick(View clickView, View viewHolderView, IMMessage item) {
            if (container.proxy.isLongClickEnabled()) {
                showLongClickAction(item);
            }
            return true;
        }

        @Override
        public void onFooterClick(IMMessage message) {
            // 与 robot 对话
            container.proxy.onItemFooterClick(message);
        }

        // 重新下载(对话框提示)
        private void showReDownloadConfirmDlg(final IMMessage message) {
            EasyAlertDialogHelper.OnDialogActionListener listener = new EasyAlertDialogHelper.OnDialogActionListener() {

                @Override
                public void doCancelAction() {
                }

                @Override
                public void doOkAction() {
                    // 正常情况收到消息后附件会自动下载。如果下载失败，可调用该接口重新下载
                    if (message.getAttachment() != null && message.getAttachment() instanceof FileAttachment)
                        NIMClient.getService(MsgService.class).downloadAttachment(message, true);
                }
            };

            final EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(container.activity, null,
                    container.activity.getString(R.string.repeat_download_message), true, listener);
            dialog.show();
        }

        // 重发消息到服务器
        private void resendMessage(IMMessage message) {
            // 重置状态为unsent
            int index = getItemIndex(message.getUuid());
            if (index >= 0 && index < items.size()) {
                IMMessage item = items.get(index);
                item.setStatus(MsgStatusEnum.sending);
                deleteItem(item, true);
                onMsgSend(item);
            }

            NIMClient.getService(MsgService.class).sendMessage(message, false);
        }

        /**
         * ****************************** 长按菜单 ********************************
         */

        // 长按消息Item后弹出菜单控制
        private void showLongClickAction(IMMessage selectedItem) {
            onNormalLongClick(selectedItem);
        }

        /**
         * 长按菜单操作
         *
         * @param item
         */
        private void onNormalLongClick(IMMessage item) {
            CustomAlertDialog alertDialog = new CustomAlertDialog(container.activity);
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);

            prepareDialogItems(item, alertDialog);
            alertDialog.show();
        }

        // 长按消息item的菜单项准备。如果消息item的MsgViewHolder处理长按事件(MsgViewHolderBase#onItemLongClick),且返回为true，
        // 则对应项的长按事件不会调用到此处
        private void prepareDialogItems(final IMMessage selectedItem, CustomAlertDialog alertDialog) {
            try {
                mAttachModel = new Gson().fromJson(selectedItem.getAttachment().toJson(false), new TypeToken<AttachModel>() {
                }.getType());
            } catch (Exception e) {

            }


            MsgTypeEnum msgType = selectedItem.getMsgType();

            MessageAudioControl.getInstance(container.activity).stopAudio();
            //video image

            // 0 EarPhoneMode
            longClickItemEarPhoneMode(alertDialog, msgType);
            // 1 resend
            longClickItemResend(selectedItem, alertDialog);
            // 2 copy
            longClickItemCopy(selectedItem, alertDialog, msgType);
            // 3 revoke
            if (selectedItem.getDirect() == MsgDirectionEnum.Out && selectedItem.getStatus() == MsgStatusEnum.success
                    && !NimUIKitImpl.getMsgRevokeFilter().shouldIgnore(selectedItem) && !recordOnly) {

                //去除优惠券伊点卡的撤销
                if (mAttachModel != null) {
                    if (mAttachModel.getType() != 12 && mAttachModel.getType() != 13 && mAttachModel.getType() != 16) {
                        longClickRevokeMsg(selectedItem, alertDialog);
                    }
                } else {
                    longClickRevokeMsg(selectedItem, alertDialog);

                }
            }
            // 4 delete
            longClickItemDelete(selectedItem, alertDialog);
            // 5 trans
            longClickItemVoidToText(selectedItem, alertDialog, msgType);
            longClickImageSave(selectedItem, alertDialog, msgType);

            if (!NimUIKitImpl.getMsgForwardFilter().shouldIgnore(selectedItem) && !recordOnly) {
                //                // 6 forward to person
                //                longClickItemForwardToPerson(selectedItem, alertDialog, msgType);
                //                // 7 forward to team
                //                longClickItemForwardToTeam(selectedItem, alertDialog);
                if (mAttachModel != null) {
                    if (mAttachModel.getType() != 12 && mAttachModel.getType() != 13 && mAttachModel.getType() != 16 && mAttachModel.getType() != 11) {
                        longClickItemForwardToAll(selectedItem, alertDialog);
                    }
                } else {
                    longClickItemForwardToAll(selectedItem, alertDialog);
                }

            }

            if (msgType == MsgTypeEnum.image){
                longClickItemQRCode(selectedItem,alertDialog);
            }

            if (msgType == MsgTypeEnum.video || msgType == MsgTypeEnum.image) {
                longClickItemVideoImage(selectedItem, alertDialog);
            }
        }

        //图片视频
        private void longClickItemVideoImage(final IMMessage selectedItem, CustomAlertDialog alertDialog) {
            alertDialog.addItem("收藏", new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {
                    collect(selectedItem);
                }
            });

        }

        //图片二维码
        private void longClickItemQRCode(final IMMessage selectedItem, CustomAlertDialog alertDialog) {

            setQRCode(alertDialog,selectedItem);


        }

        // 长按菜单项--重发
        private void longClickItemResend(final IMMessage item, CustomAlertDialog alertDialog) {
            if (item.getStatus() != MsgStatusEnum.fail) {
                return;
            }
            alertDialog.addItem(container.activity.getString(R.string.repeat_send_has_blank), new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {
                    onResendMessageItem(item);
                }
            });
        }

        private void onResendMessageItem(IMMessage message) {
            int index = getItemIndex(message.getUuid());
            if (index >= 0) {
                showResendConfirm(message, index); // 重发确认
            }
        }

        private void showResendConfirm(final IMMessage message, final int index) {
            EasyAlertDialogHelper.OnDialogActionListener listener = new EasyAlertDialogHelper.OnDialogActionListener() {

                @Override
                public void doCancelAction() {
                }

                @Override
                public void doOkAction() {
                    resendMessage(message);
                }
            };
            final EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(container.activity, null,
                    container.activity.getString(R.string.repeat_send_message), true, listener);
            dialog.show();
        }

        // 长按菜单项--复制
        private void longClickItemCopy(final IMMessage item, CustomAlertDialog alertDialog, MsgTypeEnum msgType) {
            if (msgType == MsgTypeEnum.text ||
                    (msgType == MsgTypeEnum.robot && item.getAttachment() != null && !((RobotAttachment) item.getAttachment()).isRobotSend())) {
                alertDialog.addItem(container.activity.getString(R.string.copy_has_blank), new CustomAlertDialog.onSeparateItemClickListener() {

                    @Override
                    public void onClick() {
                        onCopyMessageItem(item);
                    }
                });
            }
        }

        private void onCopyMessageItem(IMMessage item) {
            ClipboardUtil.clipboardCopyText(container.activity, item.getContent());
        }

        // 长按菜单项--删除
        private void longClickItemDelete(final IMMessage selectedItem, CustomAlertDialog alertDialog) {
            if (recordOnly) {
                return;
            }
            alertDialog.addItem(container.activity.getString(R.string.delete_has_blank), new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {
                    deleteItem(selectedItem, true);
                }
            });
        }

        // 长按菜单项 -- 音频转文字
        private void longClickItemVoidToText(final IMMessage item, CustomAlertDialog alertDialog, MsgTypeEnum msgType) {
            if (msgType != MsgTypeEnum.audio) return;

            if (item.getDirect() == MsgDirectionEnum.In
                    && item.getAttachStatus() != AttachStatusEnum.transferred)
                return;
            if (item.getDirect() == MsgDirectionEnum.Out
                    && item.getAttachStatus() != AttachStatusEnum.transferred)
                return;

            alertDialog.addItem(container.activity.getString(R.string.voice_to_text), new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {
                    onVoiceToText(item);
                }
            });
        }

        // 语音转文字
        private void onVoiceToText(IMMessage item) {
            if (voiceTrans == null)
                voiceTrans = new VoiceTrans(container.activity);
            voiceTrans.voiceToText(item);
            if (item.getDirect() == MsgDirectionEnum.In && item.getStatus() != MsgStatusEnum.read) {
                item.setStatus(MsgStatusEnum.read);
                NIMClient.getService(MsgService.class).updateIMMessageStatus(item);
                adapter.notifyDataSetChanged();
            }
        }

        // 长按菜单项 -- 收藏图片
        private void longClickImageSave(final IMMessage item, CustomAlertDialog alertDialog, MsgTypeEnum msgType) {
            if (msgType != MsgTypeEnum.image) return;
            alertDialog.addItem(container.activity.getString(R.string.save_to_device), new CustomAlertDialog.onSeparateItemClickListener() {
                @Override
                public void onClick() {
                    savePicture(item);
                }
            });
        }

        // 长按菜单项 -- 听筒扬声器切换
        private void longClickItemEarPhoneMode(CustomAlertDialog alertDialog, MsgTypeEnum msgType) {
            if (msgType != MsgTypeEnum.audio) return;

            String content = UserPreferences.isEarPhoneModeEnable() ? "切换成扬声器播放" : "切换成听筒播放";
            final String finalContent = content;
            alertDialog.addItem(content, new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {
                    Toast.makeText(container.activity, finalContent, Toast.LENGTH_SHORT).show();
                    setEarPhoneMode(!UserPreferences.isEarPhoneModeEnable());
                }
            });
        }

        // 长按菜单项 -- 转发到个人
        private void longClickItemForwardToPerson(final IMMessage item, CustomAlertDialog alertDialog, final MsgTypeEnum typeEnum) {
            alertDialog.addItem(container.activity.getString(R.string.forward_to_person), new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {
                    //                    if (typeEnum != MsgTypeEnum.custom){
                    forwardMessage = item;
                    ContactSelectActivity.Option option = new ContactSelectActivity.Option();
                    option.title = "选择转发的人";
                    option.type = ContactSelectActivity.ContactSelectType.BUDDY;
                    option.multi = true;//是否多选
                    option.maxSelectNum = 2;
                    NimUIKit.startContactSelector(container.activity, option, REQUEST_CODE_FORWARD_PERSON);
                    //                    }
                }
            });
        }

        // 长按菜单项 -- 转发到个人
        private void longClickItemForwardToAll(final IMMessage item, CustomAlertDialog alertDialog) {
            alertDialog.addItem(container.activity.getString(R.string.forward_to_all), new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {

                    forwardMessage = item;
                    ForwardChooseActivity.startActivityForResult(container.activity, REQUEST_CODE_FORWARD_ALL);
                }
            });
        }

        // 长按菜单项 -- 转发到群组
        private void longClickItemForwardToTeam(final IMMessage item, CustomAlertDialog alertDialog) {
            alertDialog.addItem(container.activity.getString(R.string.forward_to_team), new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {
                    forwardMessage = item;
                    ContactSelectActivity.Option option = new ContactSelectActivity.Option();
                    option.title = "选择转发的群";
                    option.type = ContactSelectActivity.ContactSelectType.TEAM;
                    option.multi = true;
                    option.maxSelectNum = 3;
                    NimUIKit.startContactSelector(container.activity, option, REQUEST_CODE_FORWARD_TEAM);
                }
            });
        }

        // 长按菜单项 -- 撤回消息
        private void longClickRevokeMsg(final IMMessage item, CustomAlertDialog alertDialog) {
            alertDialog.addItem(container.activity.getString(R.string.withdrawn_msg), new CustomAlertDialog.onSeparateItemClickListener() {

                @Override
                public void onClick() {
                    if (!NetworkUtil.isNetAvailable(container.activity)) {
                        Toast.makeText(container.activity, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    NIMClient.getService(MsgService.class).revokeMessage(item).setCallback(new RequestCallback<Void>() {
                        @Override
                        public void onSuccess(Void param) {
                            deleteItem(item, false);
                            MessageHelper.getInstance().onRevokeMessage(item);
                        }

                        @Override
                        public void onFailed(int code) {
                            if (code == ResponseCode.RES_OVERDUE) {
                                Toast.makeText(container.activity, R.string.revoke_failed, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(container.activity, "revoke msg failed, code:" + code, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onException(Throwable exception) {

                        }
                    });
                }
            });
        }

    }


    private List<CollectModel> mCollectModels = new ArrayList<>();
    public static String PATH           = "collect.txt";

    private void collect(final IMMessage selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontex, R.style.customDialog);

        builder.setView(LayoutInflater.from(mcontex).inflate(R.layout.alert_dialog, null));
        mDialog = builder.create();
        mCollectModels.clear();
        MsgAttachment attachment = selectedItem.getAttachment();
        Gson gson = new Gson();
        String attachmentJson = gson.toJson(attachment);
        try {
            mModel = gson.fromJson(attachmentJson, new TypeToken<CollectModel>() {
            }.getType());

            String cache = getCache();
            if (!cache.equals("")) {
                List<CollectModel> models = gson.fromJson(cache, new TypeToken<List<CollectModel>>() {
                }.getType());

                //如果收藏相同,删除老数据,收藏新数据
                int i = -1;
                for (CollectModel collectModel : models) {
                    if (collectModel.getFromTime() == selectedItem.getTime()) {
                        i = models.indexOf(collectModel);
                    }
                }
                if (i != -1)
                    models.remove(i);
                mCollectModels.addAll(models);
            }

            NimUserInfoCache.getInstance().getUserInfoFromRemote(selectedItem.getFromAccount(), new RequestCallback<NimUserInfo>() {
                @Override
                public void onSuccess(NimUserInfo nimUserInfo) {

                    MsgModel msgModel = new Gson().fromJson(selectedItem.getAttachment().toJson(false), new TypeToken<MsgModel>() {
                    }.getType());
                    //加发送人名字
                    mModel.setFromNick(nimUserInfo.getName());
                    //加发送人ID
                    mModel.setFromID(selectedItem.getFromAccount());
                    //加发送时间戳
                    mModel.setFromTime(selectedItem.getTime());
                    //收藏时间
                    mModel.setCollect_time(System.currentTimeMillis());


                    //头像URL
                    mModel.setFormIcon(nimUserInfo.getAvatar());
                    //地址
                    if (mModel.getExtension().equals("jpg") || mModel.getExtension().equals("png") || mModel.getExtension().equalsIgnoreCase("JPEG")) {
                        ImageAttachment imageAttachment = (ImageAttachment) selectedItem.getAttachment();
                        String path = imageAttachment.getPath();
                        if (path == null) {
                            //下载图片
                            downLoadPic(imageAttachment);
                        } else {
                            mModel.setPath(path);
                            mCollectModels.add(0, mModel);
                            addCache(new Gson().toJson(mCollectModels));
                        }
                    } else if (mModel.getExtension().equals("mp4")) {
                        VideoAttachment videoAttachment = (VideoAttachment) selectedItem.getAttachment();
                        String path = videoAttachment.getPath();

                        if (path == null) {
                            //下载视频
                            downVideo(videoAttachment);
                        } else {
                            mModel.setPath(videoAttachment.getPath());
                            mCollectModels.add(mModel);
                            addCache(new Gson().toJson(mCollectModels));
                        }


                    }







            /*if (model.getExtension().equals("mp4")) {

                model.setBitmap(createVideoThumbnail(model.getUrl()));
            }*/

                }

                @Override
                public void onFailed(int i) {

                }

                @Override
                public void onException(Throwable throwable) {

                }
            });


        } catch (Exception e) {

        }
    }

    /**
     * 下载视频到本地
     *
     * @param videoAttachment
     */
    private void downVideo(final VideoAttachment videoAttachment) {
        mDialog.show();

        final File file = new File(mcontex.getCacheDir(), videoAttachment.getFileName() + ".mp4");//将要保存图片的路径
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (file.exists()) {
                        file.createNewFile();
                    }
                    // 构造URL
                    URL url = new URL(videoAttachment.getUrl());
                    // 打开连接
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(3000);
                    con.setReadTimeout(3000);
                    con.connect();
                    // 输入流
                    InputStream is = con.getInputStream();
                    // 1K的数据缓冲
                    byte[] bs = new byte[1024];
                    // 读取到的数据长度
                    int len;
                    // 输出的文件流
                    OutputStream os = new FileOutputStream(file);
                    // 开始读取
                    while ((len = is.read(bs)) != -1) {
                        os.write(bs, 0, len);
                    }
                    // 完毕，关闭所有链接
                    os.close();
                    is.close();

                } catch (IOException e) {
                    file.delete();
                } finally {
                    ((Activity) mcontex).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mModel.setPath(file.getPath());
                            mCollectModels.add(0, mModel);
                            addCache(new Gson().toJson(mCollectModels));
                        }
                    });

                    mDialog.dismiss();
                }
            }
        }).start();

    }


    /**
     * 存文件
     *
     * @param cacheData
     */
    public void addCache(String cacheData) {
        try {
            FileOutputStream outStream = mcontex.openFileOutput(PATH, Context.MODE_PRIVATE);
            outStream.write(cacheData.getBytes());
            outStream.close();
            Toast.makeText(mcontex, "收藏成功", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    /**
     * 取文件
     *
     * @return
     */
    public String getCache() {
        try {
            FileInputStream inStream = mcontex.openFileInput(PATH);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            stream.close();
            inStream.close();
            return stream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    private void setEarPhoneMode(boolean earPhoneMode) {
        UserPreferences.setEarPhoneModeEnable(earPhoneMode);
        MessageAudioControl.getInstance(container.activity).setEarPhoneModeEnable(earPhoneMode);
    }

    private Bitmap getBackground(String path) {
        if (background != null && path.equals(background.first) && background.second != null) {
            return background.second;
        }

        if (background != null && background.second != null) {
            background.second.recycle();
        }

        Bitmap bitmap = null;
        if (path.startsWith("/android_asset")) {
            String asset = path.substring(path.indexOf("/", 1) + 1);
            try {
                InputStream ais = container.activity.getAssets().open(asset);
                bitmap = BitmapDecoder.decodeSampled(ais, ScreenUtil.screenWidth, ScreenUtil.screenHeight);
                ais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bitmap = BitmapDecoder.decodeSampled(path, ScreenUtil.screenWidth, ScreenUtil.screenHeight);
        }
        background = new Pair<>(path, bitmap);
        return bitmap;
    }

    private UserInfoObservable.UserInfoObserver uinfoObserver;

    private void registerUserInfoObserver() {
        if (uinfoObserver == null) {
            uinfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    if (container.sessionType == SessionTypeEnum.P2P) {
                        if (accounts.contains(container.account) || accounts.contains(NimUIKit.getAccount())) {
                            adapter.notifyDataSetChanged();
                        }
                    } else { // 群的，简单的全部重刷
                        adapter.notifyDataSetChanged();
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
     * 收到已读回执（更新VH的已读label）
     */

    public void receiveReceipt() {
        //        updateReceipt(items);
        //        refreshMessageList();
    }

    public void updateReceipt(final List<IMMessage> messages) {
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (receiveReceiptCheck(messages.get(i))) {
                adapter.setUuid(messages.get(i).getUuid());
                break;
            }
        }
    }

    private boolean receiveReceiptCheck(final IMMessage msg) {
        if (msg != null && msg.getSessionType() == SessionTypeEnum.P2P
                && msg.getDirect() == MsgDirectionEnum.Out
                && msg.getMsgType() != MsgTypeEnum.tip
                && msg.getMsgType() != MsgTypeEnum.notification
                && msg.isRemoteRead()) {
            return true;
        }

        return false;
    }

    /**
     * 发送已读回执（需要过滤）
     */

    public void sendReceipt() {
        if (container.account == null || container.sessionType != SessionTypeEnum.P2P) {
            return;
        }

        IMMessage message = getLastReceivedMessage();
        if (!sendReceiptCheck(message)) {
            return;
        }

        NIMClient.getService(MsgService.class).sendMessageReceipt(container.account, message);
    }

    private IMMessage getLastReceivedMessage() {
        IMMessage lastMessage = null;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (sendReceiptCheck(items.get(i))) {
                lastMessage = items.get(i);
                break;
            }
        }


        return lastMessage;
    }

    private boolean sendReceiptCheck(final IMMessage msg) {
        if (msg == null || msg.getDirect() != MsgDirectionEnum.In ||
                msg.getMsgType() == MsgTypeEnum.tip || msg.getMsgType() == MsgTypeEnum.notification) {
            return false; // 非收到的消息，Tip消息和通知类消息，不要发已读回执
        }

        return true;
    }

    // 删除消息
    private void deleteItem(IMMessage messageItem, boolean isRelocateTime) {
        NIMClient.getService(MsgService.class).deleteChattingHistory(messageItem);
        List<IMMessage> messages = new ArrayList<>();
        for (IMMessage message : items) {
            if (message.getUuid().equals(messageItem.getUuid())) {
                continue;
            }
            messages.add(message);
        }
        updateReceipt(messages);
        adapter.deleteItem(messageItem, isRelocateTime);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
        if (selected != null && !selected.isEmpty()) {
            switch (requestCode) {
                case REQUEST_CODE_FORWARD_TEAM:
                    for (String sessionId : selected) {
                        doForwardMessage(sessionId, SessionTypeEnum.Team);
                    }

                    break;
                case REQUEST_CODE_FORWARD_PERSON:
                    for (String sessionId : selected) {
                        doForwardMessage(sessionId, SessionTypeEnum.P2P);
                    }
                    break;
            }
        }
        if (requestCode == REQUEST_CODE_FORWARD_ALL) {
            ArrayList<GroupSendModel> datas = (ArrayList<GroupSendModel>) data.getSerializableExtra(ForwardChooseActivity.RESULT_DATA);
            if (datas != null) {
                for (GroupSendModel model : datas) {
                    if (model.getItemType() == ForwardChooseActivity.P2P) {
                        doForwardMessage(model.getP2pAccount(), SessionTypeEnum.P2P);
                    } else {
                        doForwardMessage(model.getTeamId(), SessionTypeEnum.Team);
                    }
                }

            }
        }

    }

    // 转发消息
    private void doForwardMessage(final String sessionId, SessionTypeEnum sessionTypeEnum) {
        IMMessage message;
        if (forwardMessage.getMsgType() == MsgTypeEnum.robot) {
            message = buildForwardRobotMessage(sessionId, sessionTypeEnum);
        } else {
            message = MessageBuilder.createForwardMessage(forwardMessage, sessionId, sessionTypeEnum);
        }

        if (message == null) {
            Toast.makeText(container.activity, "该类型不支持转发", Toast.LENGTH_SHORT).show();
            return;
        }

        NIMClient.getService(MsgService.class).sendMessage(message, false);
        if (container.account.equals(sessionId)) {
            onMsgSend(message);
        }
        Toast.makeText(container.activity, "已发送", Toast.LENGTH_SHORT).show();
    }

    private IMMessage buildForwardRobotMessage(final String sessionId, SessionTypeEnum sessionTypeEnum) {
        if (forwardMessage.getMsgType() == MsgTypeEnum.robot && forwardMessage.getAttachment() != null) {
            RobotAttachment robotAttachment = (RobotAttachment) forwardMessage.getAttachment();
            if (robotAttachment.isRobotSend()) {
                return null; // 机器人发的消息不能转发了
            }

            return MessageBuilder.createTextMessage(sessionId, sessionTypeEnum, forwardMessage.getContent());
        }

        return null;
    }


    public void setQRCode(final CustomAlertDialog alertDialog, final IMMessage selectedItem) {

        new Thread(new Runnable(){

            @Override
            public void run() {
                ImageAttachment attachment = (ImageAttachment) selectedItem.getAttachment();
                final String url = attachment.getUrl();

                final Drawable drawable = loadImageFromNetwork(url);
                BitmapDrawable bd = (BitmapDrawable) drawable;
                Bitmap bm         = bd.getBitmap();

                int width = bm.getWidth();
                int height = bm.getHeight();
                int[] data = new int[width * height];
                bm.getPixels(data, 0, width, 0, 0, width, height);
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
                QRCodeReader reader = new QRCodeReader();
                Result re = null;
                try {
                    re = reader.decode(bitmap1);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (ChecksumException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
                if (re == null) {
//            showAlert(obmp);
                } else {
                    alertDialog.addItem("识别二维码", new CustomAlertDialog.onSeparateItemClickListener() {

                        @Override
                        public void onClick() {
                            ToastUtil.showLongToast(mcontex, QRCodeUtil.getStringFromQRCode(drawable));
                        }
                    });
                }
            }

        }).start()  ;



    }

    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }

        return drawable ;
    }

    // 保存图片
    public void savePicture(IMMessage message) {
        ImageAttachment attachment = (ImageAttachment) message.getAttachment();
        String path = attachment.getPath();
        if (TextUtils.isEmpty(path)) {
            return;
        }

        String srcFilename = attachment.getFileName();
        //默认jpg
        String extension = TextUtils.isEmpty(attachment.getExtension()) ? "jpg" : attachment.getExtension();
        srcFilename += ("." + extension);

        String picPath = StorageUtil.getSystemImagePath();
        String dstPath = picPath + srcFilename;
        if (AttachmentStore.copy(path, dstPath) != -1) {
            try {
                ContentValues values = new ContentValues(2);
                values.put(MediaStore.Images.Media.MIME_TYPE, C.MimeType.MIME_JPEG);
                values.put(MediaStore.Images.Media.DATA, dstPath);
                mcontex.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Toast.makeText(mcontex, "图片已保存到手机", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // may be java.lang.UnsupportedOperationException
                Toast.makeText(mcontex, mcontex.getString(R.string.picture_save_fail), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(mcontex, mcontex.getString(R.string.picture_save_fail), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 下载原图到本地
     *
     * @param imageAttachment
     */
    public void downLoadPic(final ImageAttachment imageAttachment) {
        mDialog.show();
        new Thread() {
            @Override
            public void run() {
                //获取okHttp对象get请求,

                try {
                    OkHttpClient client = new OkHttpClient();

                    //获取请求对象
                    Request request = new Request.Builder().url(imageAttachment.getUrl()).build();

                    //获取响应体

                    ResponseBody body = client.newCall(request).execute().body();

                    //获取流
                    InputStream in = body.byteStream();
                    //转化为bitmap
                    final Bitmap bitmap = BitmapFactory.decodeStream(in);
                    ((Activity) mcontex).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            File file = new File(mcontex.getCacheDir(), imageAttachment.getFileName() + ".jpg");//将要保存图片的路径
                            FileOutputStream fileOutStream = null;
                            try {
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                //                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                                fileOutStream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream);
                                fileOutStream.flush();
                                fileOutStream.close();

                                mModel.setPath(file.getPath());
                                mCollectModels.add(0, mModel);
                                addCache(new Gson().toJson(mCollectModels));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    mDialog.dismiss();
                }


            }
        }.start();

    }


}
