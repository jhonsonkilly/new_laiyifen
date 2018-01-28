package com.netease.nim.demo.main.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.login.LoginActivity;
import com.netease.nim.demo.login.LogoutHelper;
import com.netease.nim.demo.main.activity.MultiportActivity;
import com.netease.nim.demo.main.adapter.QuickAdapter;
import com.netease.nim.demo.main.model.MainTab;
import com.netease.nim.demo.main.model.RecognizeFilterModel;
import com.netease.nim.demo.main.model.SystemFriendModel;
import com.netease.nim.demo.main.reminder.ReminderManager;
import com.netease.nim.demo.main.util.SaveFile;
import com.netease.nim.demo.session.SessionHelper;
import com.netease.nim.demo.session.extension.BusinessCardAttachment;
import com.netease.nim.demo.session.extension.DiscountAttachment;
import com.netease.nim.demo.session.extension.DistinguishAttachment;
import com.netease.nim.demo.session.extension.GuessAttachment;
import com.netease.nim.demo.session.extension.RTSAttachment;
import com.netease.nim.demo.session.extension.RedPacketAttachment;
import com.netease.nim.demo.session.extension.RedPacketOpenedAttachment;
import com.netease.nim.demo.session.extension.SnapChatAttachment;
import com.netease.nim.demo.session.extension.StickerAttachment;
import com.netease.nim.demo.session.extension.SysFriendInfoAttachment;
import com.netease.nim.demo.session.extension.SystemMessageImageNewAttachment;
import com.netease.nim.demo.session.extension.SystemMessageOnlyTextAttachment;
import com.netease.nim.demo.session.extension.YiDianCardAttachment;
import com.netease.nim.demo.sync.OKhttpHelper;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.recent.RecentContactsCallback;
import com.netease.nim.uikit.recent.RecentContactsFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoujianghua on 2015/8/17.
 */
public class SessionListFragment extends MainTabFragment {

    private View notifyBar;

    private TextView notifyBarText;

    // 同时在线的其他端的信息
    private List<OnlineClient> onlineClients;

    private View multiportBar;

    private RecentContactsFragment fragment;

    //定制内容
    private QuickAdapter adapter;
    RecyclerView topListView;

    public SessionListFragment() {
        this.setContainerId(MainTab.RECENT_CONTACTS.fragmentId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMsgList(NimUIKit.getAccount());
        saveFronts();
        onCurrent();
    }

    @Override
    public void onDestroy() {
        registerObservers(false);
        super.onDestroy();
    }

    @Override
    protected void onInit() {
        findViews();
        registerObservers(true);
        //        addTopList();
        addRecentContactsFragment();
    }


    private void registerObservers(boolean register) {
        NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, register);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }

    private void findViews() {
        notifyBar = getView().findViewById(R.id.status_notify_bar);
        notifyBarText = (TextView) getView().findViewById(R.id.status_desc_label);
        notifyBar.setVisibility(View.GONE);

        multiportBar = getView().findViewById(R.id.multiport_notify_bar);
        multiportBar.setVisibility(View.GONE);
        multiportBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiportActivity.startActivity(getActivity(), onlineClients);
            }
        });
        topListView = (RecyclerView) getView().findViewById(R.id.recycler_view);
    }

    /**
     * 用户状态变化
     */
    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                kickOut(code);
            } else {
                if (code == StatusCode.NET_BROKEN) {
                    notifyBar.setVisibility(View.VISIBLE);
                    notifyBarText.setText(R.string.net_broken);
                } else if (code == StatusCode.UNLOGIN) {
                    notifyBar.setVisibility(View.VISIBLE);
                    notifyBarText.setText(R.string.nim_status_unlogin);
                } else if (code == StatusCode.CONNECTING) {
                    notifyBar.setVisibility(View.VISIBLE);
                    notifyBarText.setText(R.string.nim_status_connecting);
                } else if (code == StatusCode.LOGINING) {
                    notifyBar.setVisibility(View.VISIBLE);
                    notifyBarText.setText(R.string.nim_status_logining);
                } else {
                    notifyBar.setVisibility(View.GONE);
                }
            }
        }
    };

    Observer<List<OnlineClient>> clientsObserver = new Observer<List<OnlineClient>>() {
        @Override
        public void onEvent(List<OnlineClient> onlineClients) {
            SessionListFragment.this.onlineClients = onlineClients;
            if (onlineClients == null || onlineClients.size() == 0) {
                multiportBar.setVisibility(View.GONE);
            } else {
                multiportBar.setVisibility(View.VISIBLE);
                TextView status = (TextView) multiportBar.findViewById(R.id.multiport_desc_label);

                multiportBar.setVisibility(View.GONE);
                status.setVisibility(View.GONE);
                OnlineClient client = onlineClients.get(0);
                switch (client.getClientType()) {
                    case ClientType.Windows:
                    case ClientType.MAC:
                        status.setText(getString(R.string.multiport_logging) + getString(R.string.computer_version));
                        break;
                    case ClientType.Web:
                        status.setText(getString(R.string.multiport_logging) + getString(R.string.web_version));
                        break;
                    case ClientType.iOS:
                    case ClientType.Android:
                        status.setText(getString(R.string.multiport_logging) + getString(R.string.mobile_version));
                        break;
                    default:
                        multiportBar.setVisibility(View.GONE);
                        break;
                }
            }
        }
    };

    /**
     * 账号被其他设备登录
     *
     * @param code
     */
    private void kickOut(StatusCode code) {
        Preferences.saveUserToken("");

        if (code == StatusCode.PWD_ERROR) {
            LogUtil.e("Auth", "user password error");
            Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
        } else {
            LogUtil.i("Auth", "Kicked!");
        }
        //        onLogout();
    }

    // 注销
    private void onLogout() {
        // 清理缓存&注销监听&清除状态
        LogoutHelper.logout();

        LoginActivity.start(getActivity(), true);
        getActivity().finish();
    }

    // 将最近联系人列表fragment动态集成进来。 开发者也可以使用在xml中配置的方式静态集成。
    private void addRecentContactsFragment() {

        fragment = new RecentContactsFragment();
        fragment.setContainerId(R.id.message_list);



//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.add(R.id.message_list, fragment);
//        fragmentTransaction.commit();

//        final UI activity = (UI) getActivity();

//         如果是activity从堆栈恢复，FM中已经存在恢复而来的fragment，此时会使用恢复来的，而new出来这个会被丢弃掉
        fragment = (RecentContactsFragment) addFragment(fragment);


        fragment.setCallback(new RecentContactsCallback() {
            @Override
            public void onRecentContactsLoaded() {
                // 最近联系人列表加载完毕
            }

            @Override
            public void onUnreadCountChange(int unreadCount) {
                ReminderManager.getInstance().updateSessionUnreadNum(unreadCount);
            }

            @Override
            public void onItemClick(RecentContact recent) {
                String contactid = recent.getContactId();
                // 回调函数，以供打开会话窗口时传入定制化参数，或者做其他动作
                switch (recent.getSessionType()) {
                    case P2P:
                        if (contactid.equals("desmart4")) {
                            SessionHelper.startP2PSession(getActivity(), recent.getContactId(), true);
                        } else {
                            SessionHelper.startP2PSession(getActivity(), recent.getContactId(), false);
                        }

                        break;
                    case Team:
                        SessionHelper.startTeamSession(getActivity(), recent.getContactId());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public String getDigestOfAttachment(RecentContact recentContact, MsgAttachment attachment) {
                // 设置自定义消息的摘要消息，展示在最近联系人列表的消息缩略栏上
                // 当然，你也可以自定义一些内建消息的缩略语，例如图片，语音，音视频会话等，自定义的缩略语会被优先使用。
                if (attachment instanceof GuessAttachment) {
                    GuessAttachment guess = (GuessAttachment) attachment;
                    return guess.getValue().getDesc();
                } else if (attachment instanceof RTSAttachment) {
                    return "[白板]";
                } else if (attachment instanceof StickerAttachment) {
                    return "[贴图]";
                } else if (attachment instanceof SnapChatAttachment) {
                    return "[阅后即焚]";
                } else if (attachment instanceof RedPacketAttachment) {
                    return "[红包]";
                } else if (attachment instanceof SysFriendInfoAttachment) {//新好友,加群提醒
                    try {
                        SystemFriendModel model = new Gson().fromJson(((SysFriendInfoAttachment) attachment).getContent(), new TypeToken<SystemFriendModel>() {
                        }.getType());
                        RequestCallbackWrapper callback = new RequestCallbackWrapper() {
                            @Override
                            public void onResult(int code, Object result, Throwable exception) {
                            }
                        };
                        if (model.getSysFriendInfoTitle().equals("新好友提醒")){
                            Map<FriendFieldEnum, Object> map = new HashMap<>();
                            Map<String, Object> exts = new HashMap<>();
                            exts.put("sourceNote", model.getSource());
                            map.put(FriendFieldEnum.EXTENSION, exts);
                            NIMClient.getService(FriendService.class).updateFriendFields(model.getSysFriendInfoToId(), map).setCallback(callback);
                        }
                        return "[" + model.getSysFriendInfoTitle() + "]";
                    } catch (Exception e) {

                    }
                    return "";
                } else if (attachment instanceof SystemMessageImageNewAttachment) {
                    return "[系统信息]";
                } else if (attachment instanceof SystemMessageOnlyTextAttachment) {
                    return "[系统信息]";
                } else if (attachment instanceof YiDianCardAttachment) {
                    return "[伊点卡]";
                } else if (attachment instanceof DiscountAttachment) {
                    return "[优惠券]";
                } else if (attachment instanceof BusinessCardAttachment) {
                    return "[名片分享]";
                } else if (attachment instanceof RedPacketOpenedAttachment) {
                    return ((RedPacketOpenedAttachment) attachment).getDesc(recentContact.getSessionType(), recentContact.getContactId());
                }else if (attachment instanceof DistinguishAttachment) {
                    return "[产品推荐]";
                }


                return null;
            }

            @Override
            public String getDigestOfTipMsg(RecentContact recent) {
                String msgId = recent.getRecentMessageId();
                List<String> uuids = new ArrayList<>(1);
                uuids.add(msgId);
                List<IMMessage> msgs = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuids);
                if (msgs != null && !msgs.isEmpty()) {
                    IMMessage msg = msgs.get(0);
                    Map<String, Object> content = msg.getRemoteExtension();
                    if (content != null && !content.isEmpty()) {
                        return (String) content.get("content");
                    }
                }

                return null;
            }
        });
    }

    /**
     * 触发获取系统消息
     *
     * @param accId 用户id
     */
    private void getMsgList(String accId) {
        Map<String, Object> body = new HashMap<>();
        body.put("token", Preferences.getUserMainToken());
        body.put("sysFriendInfoToId", accId);

        OKhttpHelper.send(getActivity(), new Gson().toJson(body), Common.AdapterPath + "getMsgList", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                //
                System.out.println(s);
            }

            @Override
            public void onSendFail(String err) {
                System.out.println(err);
            }
        });

    }

    public TFragment addFragment(TFragment fragment) {
        List<TFragment> fragments = new ArrayList<TFragment>(1);
        fragments.add(fragment);

        List<TFragment> fragments2 = addFragments(fragments);
        return fragments2.get(0);
    }

    public List<TFragment> addFragments(List<TFragment> fragments) {
        List<TFragment> fragments2 = new ArrayList<TFragment>(fragments.size());

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        boolean commit = false;
        for (int i = 0; i < fragments.size(); i++) {
            // install
            TFragment fragment = fragments.get(i);
            int id = fragment.getContainerId();

            // exists
            TFragment fragment2 = (TFragment) fm.findFragmentById(id);

            if (fragment2 == null) {
                fragment2 = fragment;
                transaction.add(id, fragment);
                commit = true;
            }

            fragments2.add(i, fragment2);
        }

        if (commit) {
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {

            }
        }

        return fragments2;
    }

    /**
     * 保存词库
     */
    private void saveFronts() {
        final SharedPreferences preferences = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("version", preferences.getString("version",preferences.getString("version","")));
        OKhttpHelper.send(getActivity(), new Gson().toJson(map), Common.AdapterPath + "getRecognizeFilter", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    RecognizeFilterModel model = new Gson().fromJson(s, new TypeToken<RecognizeFilterModel>() {
                    }.getType());

                    if (model.getCode().equals("1")) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("version",model.getVersion());
                        SaveFile.write(new Gson().toJson(model.getRecognizeData()),"RecognizeData");
                        SaveFile.write(new Gson().toJson(model.getFilterData()),"FilterData");

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
