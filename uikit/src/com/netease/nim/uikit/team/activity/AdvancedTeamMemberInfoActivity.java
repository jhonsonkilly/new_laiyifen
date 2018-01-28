package com.netease.nim.uikit.team.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.SimpleCallback;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.dialog.AddBuddyDialog;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.dialog.MenuDialog;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.ui.widget.SwitchButton;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.core.NimUIKitImpl;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.session.module.SendMsgModel;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamMemberType;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群成员详细信息界面
 * Created by hzxuwen on 2015/3/19.
 */
public class AdvancedTeamMemberInfoActivity extends UI implements View.OnClickListener {

    private static final String TAG                    = AdvancedTeamMemberInfoActivity.class.getSimpleName();
    // constant
    public static final  int    REQ_CODE_REMOVE_MEMBER = 11;
    private static final String EXTRA_ID               = "EXTRA_ID";
    private static final String EXTRA_TID              = "EXTRA_TID";
    public static final String EXTRA_ISADMIN          = "EXTRA_ISADMIN";
    public static final String EXTRA_ISREMOVE         = "EXTRA_ISREMOVE";
    public static final String WAY       = "way";
    private final String KEY_MUTE_MSG           = "mute_msg";

    // data
    private String account;
    private String teamId;
    private TeamMember viewMember;
    private boolean              isSetAdmin;
    private Map<String, Boolean> toggleStateMap;

    // view
    private HeadImageView headImageView;
    private TextView memberName;
    private TextView nickName;
    private TextView identity;
    private View nickContainer;
    private Button removeBtn;
    private View identityContainer;
    private MenuDialog setAdminDialog;
    private MenuDialog cancelAdminDialog;
    private ViewGroup toggleLayout;
    private SwitchButton muteSwitch;
    private String source;

    // state
    private boolean isSelfCreator = false;
    private boolean isSelfManager = false;
    private Button addFriend;

    public static void startActivityForResult(Activity activity, String account, String tid, String way) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, account);
        intent.putExtra(EXTRA_TID, tid);
        intent.putExtra(WAY,way);
        intent.setClass(activity, AdvancedTeamMemberInfoActivity.class);
        activity.startActivityForResult(intent, REQ_CODE_REMOVE_MEMBER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nim_advanced_team_member_info_layout);

        ToolBarOptions options = new ToolBarOptions();
        options.titleId = R.string.team_member_info;
        setToolBar(R.id.toolbar, options);

        parseIntentData();

        findViews();


        loadMemberInfo();

        initMemberInfo();
    }

    /**
     * 更新UI 判断是否已经为好友
     */
    private void upDateViews() {
        List<NimUserInfo> allUsersOfMyFriend = NimUserInfoCache.getInstance().getAllUsersOfMyFriend();
        for (NimUserInfo nimUserInfo : allUsersOfMyFriend) {
            if (nimUserInfo.getAccount().equals(account)) {
                addFriend.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateToggleView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (setAdminDialog != null) {
            setAdminDialog.dismiss();
        }
        if (cancelAdminDialog != null) {
            cancelAdminDialog.dismiss();
        }
    }

    private void parseIntentData() {
        account = getIntent().getStringExtra(EXTRA_ID);
        teamId = getIntent().getStringExtra(EXTRA_TID);
        source = getIntent().getStringExtra(WAY);
    }

    private void findViews() {
        nickContainer = findViewById(R.id.nickname_container);
        identityContainer = findViewById(R.id.identity_container);
        headImageView = (HeadImageView) findViewById(R.id.team_member_head_view);
        memberName = (TextView) findViewById(R.id.team_member_name);
        nickName = (TextView) findViewById(R.id.team_nickname_detail);
        identity = (TextView) findViewById(R.id.team_member_identity_detail);
        removeBtn = (Button) findViewById(R.id.team_remove_member);
        toggleLayout = findView(R.id.toggle_layout);
        addFriend = (Button) findViewById(R.id.add_friend);
        setClickListener();
    }

    private void setClickListener() {
        nickContainer.setOnClickListener(this);
        identityContainer.setOnClickListener(this);
        removeBtn.setOnClickListener(this);
        addFriend.setOnClickListener(this);
    }

    private void updateToggleView() {
        if (getMyPermission()) {
            boolean isMute = TeamDataCache.getInstance().getTeamMember(teamId, account).isMute();
            if (muteSwitch == null) {
                addToggleBtn(isMute);
            } else {
                setToggleBtn(muteSwitch, isMute);
            }
            Log.i(TAG, "mute=" + isMute);
        }

    }

    // 判断是否有权限
    private boolean getMyPermission() {
        if (isSelfCreator && !isSelf(account)) {
            return true;
        }
        if (isSelfManager && identity.getText().toString().equals(getString(R.string.team_member))) {
            return true;
        }
        return false;
    }

    private void addToggleBtn(boolean isMute) {
        muteSwitch = addToggleItemView(KEY_MUTE_MSG, R.string.mute_msg, isMute);
    }

    private void setToggleBtn(SwitchButton btn, boolean isChecked) {
        btn.setCheck(isChecked);
    }

    private SwitchButton addToggleItemView(String key, int titleResId, boolean initState) {
        ViewGroup vp = (ViewGroup) getLayoutInflater().inflate(R.layout.nim_user_profile_toggle_item, null);
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.isetting_item_height));
        vp.setLayoutParams(vlp);

        TextView titleText = ((TextView) vp.findViewById(R.id.user_profile_title));
        titleText.setText(titleResId);

        SwitchButton switchButton = (SwitchButton) vp.findViewById(R.id.user_profile_toggle);
        switchButton.setCheck(initState);
        switchButton.setOnChangedListener(onChangedListener);
        switchButton.setTag(key);

        toggleLayout.addView(vp);

        if (toggleStateMap == null) {
            toggleStateMap = new HashMap<>();
        }
        toggleStateMap.put(key, initState);
        return switchButton;
    }

    private SwitchButton.OnChangedListener onChangedListener = new SwitchButton.OnChangedListener() {
        @Override
        public void OnChanged(View v, final boolean checkState) {
            final String key = (String) v.getTag();
            if (!NetworkUtil.isNetAvailable(AdvancedTeamMemberInfoActivity.this)) {
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                if (key.equals(KEY_MUTE_MSG)) {
                    muteSwitch.setCheck(!checkState);
                }
                return;
            }

            updateStateMap(checkState, key);

            if (key.equals(KEY_MUTE_MSG)) {
                NIMClient.getService(TeamService.class).muteTeamMember(teamId, account, checkState).setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        if (checkState) {
                            Toast.makeText(AdvancedTeamMemberInfoActivity.this, "群禁言成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdvancedTeamMemberInfoActivity.this, "取消群禁言成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == 408) {
                            Toast.makeText(AdvancedTeamMemberInfoActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdvancedTeamMemberInfoActivity.this, "on failed:" + code, Toast.LENGTH_SHORT).show();
                        }
                        updateStateMap(!checkState, key);
                        muteSwitch.setCheck(!checkState);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
            }
        }
    };

    private void updateStateMap(boolean checkState, String key) {
        if (toggleStateMap.containsKey(key)) {
            toggleStateMap.put(key, checkState);  // update state
            Log.i(TAG, "toggle " + key + "to " + checkState);
        }
    }

    private void loadMemberInfo() {
        viewMember = TeamDataCache.getInstance().getTeamMember(teamId, account);
        if (viewMember != null) {
            updateMemberInfo();
        } else {
            requestMemberInfo();
        }
    }

    /**
     * 查询群成员的信息
     */
    private void requestMemberInfo() {
        TeamDataCache.getInstance().fetchTeamMember(teamId, account, new SimpleCallback<TeamMember>() {
            @Override
            public void onResult(boolean success, TeamMember member) {
                if (success && member != null) {
                    viewMember = member;
                    updateMemberInfo();
                }
            }
        });
    }

    private void initMemberInfo() {
        memberName.setText(NimUIKitImpl.getContactProvider().getUserDisplayName(account));
        headImageView.loadBuddyAvatar(account);
    }

    private void updateMemberInfo() {
        updateMemberIdentity();
        updateMemberNickname();
        updateSelfIndentity();
        updateRemoveBtn();
        upDateViews();
    }

    /**
     * 更新群成员的身份
     */
    private void updateMemberIdentity() {
        if (viewMember.getType() == TeamMemberType.Manager) {
            identity.setText(R.string.team_admin);
            isSetAdmin = true;
        } else {
            isSetAdmin = false;
            if (viewMember.getType() == TeamMemberType.Owner) {
                identity.setText(R.string.team_creator);
            } else {
                identity.setText(R.string.team_member);
            }
        }
    }

    /**
     * 更新成员群昵称
     */
    private void updateMemberNickname() {
        nickName.setText(viewMember.getTeamNick() != null ? viewMember.getTeamNick() : getString(R.string.team_nickname_none));
    }

    /**
     * 获得用户自己的身份
     */
    private void updateSelfIndentity() {
        TeamMember selfTeamMember = TeamDataCache.getInstance().getTeamMember(teamId, NimUIKit.getAccount());
        if (selfTeamMember == null) {
            return;
        }
        if (selfTeamMember.getType() == TeamMemberType.Manager) {
            isSelfManager = true;
        } else if (selfTeamMember.getType() == TeamMemberType.Owner) {
            isSelfCreator = true;
        }
    }

    /**
     * 更新是否显移除本群按钮
     */
    private void updateRemoveBtn() {
        if (viewMember.getAccount().equals(NimUIKit.getAccount())) {
            removeBtn.setVisibility(View.GONE);
            addFriend.setVisibility(View.GONE);

        } else {
            if (isSelfCreator) {
                removeBtn.setVisibility(View.VISIBLE);
            } else if (isSelfManager) {
                if (viewMember.getType() == TeamMemberType.Owner) {
                    removeBtn.setVisibility(View.GONE);

                } else if (viewMember.getType() == TeamMemberType.Normal) {
                    removeBtn.setVisibility(View.VISIBLE);
                } else {
                    removeBtn.setVisibility(View.GONE);

                }
            } else {
                removeBtn.setVisibility(View.GONE);

            }

        }
    }

    /**
     * 更新群昵称
     *
     * @param name
     */
    private void setNickname(final String name) {
        DialogMaker.showProgressDialog(this, getString(R.string.empty), true);
        NIMClient.getService(TeamService.class).updateMemberNick(teamId, account, name).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                DialogMaker.dismissProgressDialog();
                nickName.setText(name != null ? name : getString(R.string.team_nickname_none));
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int code) {
                DialogMaker.dismissProgressDialog();
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, String.format(getString(R.string.update_failed), code),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable exception) {
                DialogMaker.dismissProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.nickname_container) {
            editNickname();

        } else if (i == R.id.identity_container) {
            showManagerButton();

        } else if (i == R.id.team_remove_member) {
            showConfirmButton();

        } else if (i == R.id.add_friend) {
            onAddFriendByVerify(); // 发起好友验证请求
        }
    }

    private void onAddFriendByVerify() {
        try {
            final AddBuddyDialog addDialog = new AddBuddyDialog(this, R.style.dialog, NimUserInfoCache.getInstance().getUserName(account));
            addDialog.setListener(new AddBuddyDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        Toast.makeText(AdvancedTeamMemberInfoActivity.this, "发送验证", Toast.LENGTH_SHORT).show();
                        String msg = addDialog.getEditMessage();
                        doAddFriend(msg, false);
                    }
                    dialog.dismiss();
                }
            });
            addDialog.setTitle("添加好友", account);
            addDialog.setEditMessage("我是" + NIMClient.getService(UserService.class).getUserInfo(NimUIKitImpl.getAccount()).getName());
            addDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void doAddFriend(final String msg, boolean addDirectly) {
        if (!NetworkUtil.isNetAvailable(this)) {
            Toast.makeText(AdvancedTeamMemberInfoActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(account) && account.equals(NimUIKit.getAccount())) {
            Toast.makeText(AdvancedTeamMemberInfoActivity.this, "不能加自己为好友", Toast.LENGTH_SHORT).show();
            return;
        }
        final VerifyType verifyType = addDirectly ? VerifyType.DIRECT_ADD : VerifyType.VERIFY_REQUEST;
        DialogMaker.showProgressDialog(this, "", true);
        NIMClient.getService(FriendService.class).addFriend(new AddFriendData(account, verifyType, msg))
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        sendMsg(msg);
                        DialogMaker.dismissProgressDialog();
                        if (VerifyType.DIRECT_ADD == verifyType) {
                            Toast.makeText(AdvancedTeamMemberInfoActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(AdvancedTeamMemberInfoActivity.this, "添加好友请求发送成功", Toast.LENGTH_SHORT).show();
                            addFriend.setText("已添加,等待对方同意");
                            addFriend.setTextColor(getResources().getColor(R.color.gray7));
                            addFriend.setClickable(false);
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        DialogMaker.dismissProgressDialog();
                        if (code == 408) {
                            Toast.makeText(AdvancedTeamMemberInfoActivity.this, R.string.network_is_not_available, Toast
                                    .LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdvancedTeamMemberInfoActivity.this, "on failed:" + code, Toast
                                    .LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        DialogMaker.dismissProgressDialog();
                    }
                });

        Log.i(TAG, "onAddFriendByVerify");

    }

    private void sendMsg(String msg) {

        NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(NimUIKit.getAccount());
        NimUserInfo userInfoOther = NimUserInfoCache.getInstance().getUserInfo(account);
        String tip = "申请加您为好友";
        String deft = userInfoMe.getName() + ":我是" + userInfoMe.getName();

        HashMap<Object, Object> map = new HashMap<>();
        SendMsgModel sendMsgModel = new SendMsgModel();
        sendMsgModel.setSysFriendInfoTitle("新好友提醒");
        sendMsgModel.setSysFriendInfoFormid(NimUIKit.getAccount());
        sendMsgModel.setSysFriendInfoFormName(userInfoMe.getName());
        sendMsgModel.setSysFriendInfoMessage(msg.equals("") ? deft + tip : userInfoMe.getName() + ":" + "\"" + msg + "\"" + tip);
        sendMsgModel.setSysFriendInfoToId(account);
        sendMsgModel.setSysFriendInfoToName(userInfoOther.getName());
        sendMsgModel.setSysFriendInfoIsTip("0");
        sendMsgModel.setSysFriendInfoGroupId("");
        sendMsgModel.setSource(source);
        map.put("type", 11);
        map.put("data", sendMsgModel);

        OKhttpHelper.send(this, new Gson().toJson(map), Common.AdapterPath + "sendMsg", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AdvancedTeamNicknameActivity.REQ_CODE_TEAM_NAME && resultCode == Activity.RESULT_OK) {
            final String teamName = data.getStringExtra(AdvancedTeamNicknameActivity.EXTRA_NAME);
            setNickname(teamName);
        }
    }

    /**
     * 设置群昵称
     */
    private void editNickname() {
        if (isSelfCreator || isSelf(account)) {
            AdvancedTeamNicknameActivity.start(AdvancedTeamMemberInfoActivity.this, nickName.getText().toString());
        } else if (isSelfManager && identity.getText().toString().equals(getString(R.string.team_member))) {
            AdvancedTeamNicknameActivity.start(AdvancedTeamMemberInfoActivity.this, nickName.getText().toString());
        } else {
            Toast.makeText(this, R.string.no_permission, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 显示设置管理员按钮
     */
    private void showManagerButton() {
        if (identity.getText().toString().equals(getString(R.string.team_creator))) {
            return;
        }
        if (!isSelfCreator)
            return;

        if (identity.getText().toString().equals(getString(R.string.team_member))) {
            switchManagerButton(true);
        } else {
            switchManagerButton(false);
        }
    }

    /**
     * 转换设置或取消管理员按钮
     *
     * @param isSet 是否设置
     */
    private void switchManagerButton(boolean isSet) {
        if (isSet) {
            if (setAdminDialog == null) {
                List<String> btnNames = new ArrayList<>();
                btnNames.add(getString(R.string.set_team_admin));
                setAdminDialog = new MenuDialog(this, btnNames, new MenuDialog.MenuDialogOnButtonClickListener() {
                    @Override
                    public void onButtonClick(String name) {
                        addManagers();
                        setAdminDialog.dismiss();
                    }
                });
            }
            setAdminDialog.show();
        } else {
            if (cancelAdminDialog == null) {
                List<String> btnNames = new ArrayList<>();
                btnNames.add(getString(R.string.cancel_team_admin));
                cancelAdminDialog = new MenuDialog(this, btnNames, new MenuDialog.MenuDialogOnButtonClickListener() {
                    @Override
                    public void onButtonClick(String name) {
                        removeManagers();
                        cancelAdminDialog.dismiss();
                    }
                });
            }
            cancelAdminDialog.show();
        }
    }

    /**
     * 添加管理员权限
     */
    private void addManagers() {
        DialogMaker.showProgressDialog(this, getString(R.string.empty));
        ArrayList<String> accountList = new ArrayList<>();
        accountList.add(account);
        NIMClient.getService(TeamService.class).addManagers(teamId, accountList).setCallback(new RequestCallback<List<TeamMember>>() {
            @Override
            public void onSuccess(List<TeamMember> managers) {
                DialogMaker.dismissProgressDialog();
                identity.setText(R.string.team_admin);
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, R.string.update_success, Toast.LENGTH_LONG).show();

                viewMember = managers.get(0);
                updateMemberInfo();
            }

            @Override
            public void onFailed(int code) {
                DialogMaker.dismissProgressDialog();
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, String.format(getString(R.string.update_failed), code), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onException(Throwable exception) {
                DialogMaker.dismissProgressDialog();
            }
        });
    }

    /**
     * 撤销管理员权限
     */
    private void removeManagers() {
        DialogMaker.showProgressDialog(this, getString(R.string.empty));
        ArrayList<String> accountList = new ArrayList<>();
        accountList.add(account);
        NIMClient.getService(TeamService.class).removeManagers(teamId, accountList).setCallback(new RequestCallback<List<TeamMember>>() {
            @Override
            public void onSuccess(List<TeamMember> members) {
                DialogMaker.dismissProgressDialog();
                identity.setText(R.string.team_member);
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, R.string.update_success, Toast.LENGTH_LONG).show();

                viewMember = members.get(0);
                updateMemberInfo();
            }

            @Override
            public void onFailed(int code) {
                DialogMaker.dismissProgressDialog();
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, String.format(getString(R.string.update_failed), code), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onException(Throwable exception) {
                DialogMaker.dismissProgressDialog();
            }
        });
    }

    /**
     * 移除群成员确认
     */
    private void showConfirmButton() {
        EasyAlertDialogHelper.OnDialogActionListener listener = new EasyAlertDialogHelper.OnDialogActionListener() {

            @Override
            public void doCancelAction() {
            }

            @Override
            public void doOkAction() {
                removeMember();
            }
        };
        final EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(this, null, getString(R.string.team_member_remove_confirm),
                getString(R.string.remove), getString(R.string.cancel), true, listener);
        dialog.show();
    }

    /**
     * 移除群成员
     */
    private void removeMember() {
        DialogMaker.showProgressDialog(this, getString(R.string.empty));
        NIMClient.getService(TeamService.class).removeMember(teamId, account).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                DialogMaker.dismissProgressDialog();
                makeIntent(account, isSetAdmin, true);
                deleteTeam(account);
                finish();
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, R.string.update_success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailed(int code) {
                DialogMaker.dismissProgressDialog();
                Toast.makeText(AdvancedTeamMemberInfoActivity.this, String.format(getString(R.string.update_failed), code), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onException(Throwable exception) {
                DialogMaker.dismissProgressDialog();
            }
        });
    }

    /**
     * 移除出本群
     *
     * @param account
     */
    private void deleteTeam(String account) {
        NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(account);
        Map<Object, Object> hashMap = new HashMap();
        hashMap.put("sysFriendInfoFormid", "");
        hashMap.put("sysFriendInfoFormName", "");
        hashMap.put("sysFriendInfoFormTelephone", "");
        hashMap.put("sysFriendInfoToId", account);
        hashMap.put("sysFriendInfoToName", userInfoMe.getName());
        hashMap.put("sysFriendInfoToTelephone", userInfoMe.getMobile());
        hashMap.put("sysFriendInfoGroupId", teamId);
        hashMap.put("sysFriendInfoGroupName", TeamDataCache.getInstance().getTeamById(teamId).getName());

        OKhttpHelper.send(this, new Gson().toJson(hashMap), com.netease.nim.uikit.sync.Common.AdapterPath + "deleteFriend", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        makeIntent(account, isSetAdmin, false);
        super.onBackPressed();
    }

    /**
     * 设置返回的Intent
     *
     * @param account    帐号
     * @param isSetAdmin 是否设置为管理员
     * @param value      是否移除群成员
     */
    private void makeIntent(String account, boolean isSetAdmin, boolean value) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, account);
        intent.putExtra(EXTRA_ISADMIN, isSetAdmin);
        intent.putExtra(EXTRA_ISREMOVE, value);
        setResult(RESULT_OK, intent);
    }

    private boolean isSelf(String account) {
        return NimUIKit.getAccount().equals(account);
    }
}
