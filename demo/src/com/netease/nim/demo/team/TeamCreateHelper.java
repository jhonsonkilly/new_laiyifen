package com.netease.nim.demo.team;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.session.SessionHelper;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.session.module.SendMsgModel;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.team.helper.TeamHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.constant.TeamInviteModeEnum;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.model.CreateTeamResult;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hzxuwen on 2015/9/25.
 */
public class TeamCreateHelper {
    private static final String TAG                   = TeamCreateHelper.class.getSimpleName();
    private static final int    DEFAULT_TEAM_CAPACITY = 200;

    /**
     * 创建讨论组
     */
    public static void createNormalTeam(final Context context, List<String> memberAccounts, final boolean isNeedBack, final RequestCallback<CreateTeamResult> callback) {

        String teamName = "讨论组";

        DialogMaker.showProgressDialog(context, context.getString(com.netease.nim.uikit.R.string.empty), true);
        // 创建群
        HashMap<TeamFieldEnum, Serializable> fields = new HashMap<TeamFieldEnum, Serializable>();
        fields.put(TeamFieldEnum.Name, teamName);
        NIMClient.getService(TeamService.class).createTeam(fields, TeamTypeEnum.Normal, "",
                memberAccounts).setCallback(
                new RequestCallback<CreateTeamResult>() {
                    @Override
                    public void onSuccess(CreateTeamResult result) {
                        DialogMaker.dismissProgressDialog();

                        ArrayList<String> failedAccounts = result.getFailedInviteAccounts();
                        if (failedAccounts != null && !failedAccounts.isEmpty()) {
                            TeamHelper.onMemberTeamNumOverrun(failedAccounts, context);
                        } else {
                            Toast.makeText(DemoCache.getContext(), com.netease.nim.uikit.R.string.create_team_success, Toast.LENGTH_SHORT).show();
                        }

                        //                        if (isNeedBack) {
                        //                            SessionHelper.startTeamSession(context, result.getTeam().getId(), MainActivity.class, null); // 进入创建的群
                        //                        } else {
                        SessionHelper.startTeamSession(context, result.getTeam().getId());
                        //                        }
                        if (callback != null) {
                            callback.onSuccess(result);
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        DialogMaker.dismissProgressDialog();
                        if (code == 801) {
                            String tip = context.getString(com.netease.nim.uikit.R.string.over_team_member_capacity, DEFAULT_TEAM_CAPACITY);
                            Toast.makeText(DemoCache.getContext(), tip,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DemoCache.getContext(), com.netease.nim.uikit.R.string.create_team_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        Log.e(TAG, "create team error: " + code);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        DialogMaker.dismissProgressDialog();
                    }
                }
        );
    }

    /**
     * 创建高级群
     */
    public static void createAdvancedTeam(final Context context, final List<String> memberAccounts) {

        String teamName = "高级群";

        DialogMaker.showProgressDialog(context, context.getString(com.netease.nim.uikit.R.string.empty), true);
        // 创建群
        TeamTypeEnum type = TeamTypeEnum.Advanced;
        HashMap<TeamFieldEnum, Serializable> fields = new HashMap<>();
        SharedPreferences sp = context.getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        teamName = sp.getString("GroupName", null);
        fields.put(TeamFieldEnum.Name, teamName);
        fields.put(TeamFieldEnum.InviteMode, TeamInviteModeEnum.All);
        NIMClient.getService(TeamService.class).createTeam(fields, type, "",
                memberAccounts).setCallback(
                new RequestCallback<CreateTeamResult>() {
                    @Override
                    public void onSuccess(CreateTeamResult result) {
                        Log.i(TAG, "create team success, team id =" + result.getTeam().getId() + ", now begin to update property...");
                        for (String memberAccount : memberAccounts) {
                            sendMsg(context, result.getTeam(), memberAccount);
                        }
                        onCreateSuccess(context, result);
                    }

                    @Override
                    public void onFailed(int code) {
                        DialogMaker.dismissProgressDialog();
                        String tip;
                        if (code == 801) {
                            tip = context.getString(com.netease.nim.uikit.R.string.over_team_member_capacity,
                                    DEFAULT_TEAM_CAPACITY);
                        } else if (code == 806) {
                            tip = context.getString(com.netease.nim.uikit.R.string.over_team_capacity);
                        } else {
                            tip = context.getString(com.netease.nim.uikit.R.string.create_team_failed) + ", code=" +
                                    code;
                        }

                        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();

                        Log.e(TAG, "create team error: " + code);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        DialogMaker.dismissProgressDialog();
                    }
                }
        );
    }

    /**
     * 群创建成功回调
     */
    private static void onCreateSuccess(final Context context, CreateTeamResult result) {
        if (result == null) {
            Log.e(TAG, "onCreateSuccess exception: team is null");
            return;
        }
        final Team team = result.getTeam();
        if (team == null) {
            Log.e(TAG, "onCreateSuccess exception: team is null");
            return;
        }

        Log.i(TAG, "create and update team success");

        DialogMaker.dismissProgressDialog();
        // 检查有没有邀请失败的成员
        ArrayList<String> failedAccounts = result.getFailedInviteAccounts();
        if (failedAccounts != null && !failedAccounts.isEmpty()) {
            TeamHelper.onMemberTeamNumOverrun(failedAccounts, context);
        } else {
            Toast.makeText(DemoCache.getContext(), com.netease.nim.uikit.R.string.create_team_success, Toast.LENGTH_SHORT).show();
            //访问接口保存好友信息和群信息
            initTeam(context, result.getTeam());
            initFriend(context, result.getFailedInviteAccounts());
        }


        //        // 演示：向群里插入一条Tip消息，使得该群能立即出现在最近联系人列表（会话列表）中，满足部分开发者需求
        //        Map<String, Object> content = new HashMap<>(1);
        //        content.put("content", "成功创建高级群");
        //        IMMessage msg = MessageBuilder.createTipMessage(team.getId(), SessionTypeEnum.Team);
        //        msg.setRemoteExtension(content);
        //        CustomMessageConfig config = new CustomMessageConfig();
        //        config.enableUnreadCount = false;
        //        msg.setConfig(config);
        //        msg.setStatus(MsgStatusEnum.success);
        //        NIMClient.getService(MsgService.class).saveMessageToLocal(msg, true);

        // 发送后，稍作延时后跳转
        new Handler(context.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionHelper.startTeamSession(context, team.getId()); // 进入创建的群
            }
        }, 50);
    }

    private static void initFriend(Context context, ArrayList<String> failedInviteAccounts) {
        if (failedInviteAccounts.size() == 0) {
            return;
        }


    }

    private static void initTeam(Context context, Team team) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("groupId", team.getId());
        map.put("groupName", team.getName());
        map.put("type", "1");
        map.put("status", "1");
        map.put("userId", team.getCreator());
        map.put("userName", NimUserInfoCache.getInstance().getUserInfo(team.getCreator()).getName());

        OKhttpHelper.send(context, new Gson().toJson(map), com.netease.nim.uikit.sync.Common.AdapterPath + "addGroup", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });


    }

    /**
     * 发送添加好友信息
     *
     * @param context
     * @param team
     * @param account
     */
    private static void sendMsg(Context context, Team team, String account) {
        NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());
        NimUserInfo userInfoOther = NimUserInfoCache.getInstance().getUserInfo(account);
        HashMap<Object, Object> map = new HashMap<>();
        SendMsgModel sendMsgModel = new SendMsgModel();
        sendMsgModel.setSysFriendInfoTitle("加群提醒");
        sendMsgModel.setSysFriendInfoFormid(team.getCreator());
        sendMsgModel.setSysFriendInfoFormName(userInfoMe.getName());
        sendMsgModel.setSysFriendInfoMessage(userInfoMe.getName() + "邀请" + userInfoOther.getName() + "加入群" + "<" + team.getName() + ">");
        sendMsgModel.setSysFriendInfoToId(account);
        sendMsgModel.setSysFriendInfoToName(userInfoOther.getName());
        sendMsgModel.setSysFriendInfoIsTip("0");
        sendMsgModel.setSysFriendInfoGroupId(team.getId());
        sendMsgModel.setSource("");
        map.put("type", 11);
        map.put("data", sendMsgModel);

        OKhttpHelper.send(context, new Gson().toJson(map), com.netease.nim.uikit.sync.Common.AdapterPath + "sendMsg", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });
    }
}
