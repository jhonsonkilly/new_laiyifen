package com.ody.p2p.im;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Sun on 2016/6/20.
 */
public class IMHelper {
    public static final int CHAT = 0;
    public static final int GROUP_CHAT = 1;
    public static final int CHAT_ROOM = 2;
    private EMConnectionListener connectionListener;
    private EMMessageListener messageListener;
    private EMChatRoomChangeListener emChatRoomChangeListener;
    private static IMHelper instance = null;

    private IMHelper() {
    }

    public synchronized static IMHelper getInstance() {
        if (instance == null) {
            instance = new IMHelper();
        }
        return instance;
    }

    //初始化在app
    public void init(Context context) {

        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    //注册接口帮做了,app端不用了
//    public void register(String username,String pwd){
//        try {
//            EMClient.getInstance().createAccount(username, pwd);
//        } catch (HyphenateException e) {
//            e.printStackTrace();
//        }
//    }

    //从接口拿到进入聊天室的账号密码就先登录
    public void login(String username, String password, EMCallBack callBack) {
        EMClient.getInstance().login(username, password, callBack);
    }

    //登出
    public void logout(EMCallBack callBack) {
        EMClient.getInstance().logout(true, callBack);
    }

    //连接监听
    public void addConnectionListener(EMConnectionListener listener) {
        connectionListener = listener;
        EMClient.getInstance().addConnectionListener(listener);
    }

    public void removeConnectionListener() {
        if (connectionListener != null)
            EMClient.getInstance().removeConnectionListener(connectionListener);
    }


    //收发消息
    public void addMessageListener(EMMessageListener listener) {
        messageListener = listener;
        EMClient.getInstance().chatManager().addMessageListener(listener);
    }

    public void removeMessageListener() {
        if (messageListener != null)
            EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    //创建文本消息  target是对方用户或者群聊的id
    public void sendTextMessage(String content, String target, int chatType, final NewMessageStatusCallback callBack) {
        final EMMessage message = EMMessage.createTxtSendMessage(content, target);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == CHAT_ROOM){
            message.setChatType(EMMessage.ChatType.ChatRoom);
        }else if (chatType == GROUP_CHAT){
            message.setChatType(EMMessage.ChatType.GroupChat);
        }else {
            message.setChatType(EMMessage.ChatType.Chat);
        }
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                callBack.onSuccess(message);
            }

            @Override
            public void onError(int i, String s) {
                callBack.onError(i, s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 该接口是对环信EMCallBack接口的封装,为的是能拿到sendTextMessage()里的message,且降低对环信的依赖
     * 都要将环信里的接口如此封装一次,这里能降低对环信的依赖,下次如果换了即时通讯的第三方,那么只需要改本类里的这些自定义的接口的摆放位置就行,activity里
     * 具体调用接口的地方不用再改了
     */
    public interface NewMessageStatusCallback{
        void onSuccess(EMMessage message);
        void onError(int i, String s);
    }
    /**
     * 进入聊天室
     * @param roomId
     * @param emValueCallBack
     */
    public void enterRoom(String roomId, EMValueCallBack emValueCallBack){
        EMClient.getInstance().chatroomManager().joinChatRoom(roomId,emValueCallBack);
    }

    /**
     * 离开聊天室
     */
    public void leaveChatroom(String chatroomId){
        EMClient.getInstance().chatroomManager().leaveChatRoom(chatroomId);
    }

    /**
     * 在会话页面注册监听，来监听成员被踢和聊天室被删除
     */
    public void addChatRoomChangeListener(EMChatRoomChangeListener emChatRoomChangeListener){
        this.emChatRoomChangeListener = emChatRoomChangeListener;
        EMClient.getInstance().chatroomManager().addChatRoomChangeListener(emChatRoomChangeListener);
    }

    /**
     * 移除聊天室监听
     */
    public void removeEMChatRoomChangeListener(){
        if (emChatRoomChangeListener!=null){
            EMClient.getInstance().chatroomManager().removeChatRoomChangeListener(emChatRoomChangeListener);
        }
    }

    private static String getAppName(Context context, int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }


    /*在会话页面注册监听，来监听成员被踢和聊天室被删除。
     * 现在只是用来监听成员的加入 */
    public interface NewEMChatRoomChangeListener {
        void MemberJoined(String roomId, String participant);
        void ChatRoomDestroyed(String roomId, String roomName);

    }
    public void addNewChatRoomChangeListener( final NewEMChatRoomChangeListener listener){
        IMHelper.getInstance().addChatRoomChangeListener(new EMChatRoomChangeListener() {
            public void onChatRoomDestroyed(String roomId, String roomName) {
                listener.ChatRoomDestroyed(roomId,roomName);
            }

            @Override
            public void onMemberJoined(String roomId, String participant) {
                listener.MemberJoined(roomId,participant);
            }

            @Override
            public void onMemberExited(String roomId, String roomName, String participant) {
            }

            @Override
            public void onMemberKicked(String roomId, String roomName, String participant) {
            }
        });
    }
}
