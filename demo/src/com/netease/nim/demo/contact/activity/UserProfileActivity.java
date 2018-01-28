package com.netease.nim.demo.contact.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.contact.constant.UserConstant;
import com.netease.nim.demo.contact.helper.MiPictureHelper;
import com.netease.nim.demo.groupsend.GroupSendActivity;
import com.netease.nim.demo.groupsend.model.GroupSendModel;
import com.netease.nim.demo.main.model.Extras;
import com.netease.nim.demo.session.extension.BusinessCardAttachment;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.dialog.AddBuddyDialog;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialog;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.ui.imageview.ShowHeadImage;
import com.netease.nim.uikit.common.ui.widget.SwitchButton;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.core.NimUIKitImpl;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.session.module.SendMsgModel;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.team.model.TeamRequestCode;
import com.netease.nim.uikit.util.IOSDialog;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.FriendServiceObserve;
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.netease.nimlib.sdk.friend.model.Friend;
import com.netease.nimlib.sdk.friend.model.MuteListChangedNotify;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.GenderEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.netease.nim.demo.NimApplication.mContext;

/**
 * 用户资料页面
 * Created by huangjun on 2015/8/11.
 */
@RuntimePermissions
public class UserProfileActivity extends UI {
    private static final int REQUEST_CODE_SETBG = 105;

    private static final String TAG = UserProfileActivity.class.getSimpleName();

    private final boolean FLAG_ADD_FRIEND_DIRECTLY = false; // 是否直接加为好友开关，false为需要好友申请
    private final String KEY_BLACK_LIST           = "black_list";
    private final String KEY_MSG_NOTICE           = "msg_notice";

    private String account;

    // 基本信息
    private HeadImageView headImageView;
    private TextView nameText;
    private ImageView genderImage;
    private TextView accountText;
    private TextView birthdayText;
    private TextView mobileText;
    private TextView emailText;
    private TextView signatureText;
    private RelativeLayout birthdayLayout;
    private RelativeLayout phoneLayout;
    private RelativeLayout emailLayout;
    private RelativeLayout signatureLayout;
    private RelativeLayout aliasLayout;
    private RelativeLayout phoneRemarkLayout;
    private RelativeLayout sourceLayout;
    private TextView nickText;

    // 开关
    private ViewGroup toggleLayout;
    private ViewGroup toggleLayout2;
    private Button addFriendBtn;
    private Button removeFriendBtn;
    private Button chatBtn;
    private SwitchButton blackSwitch;
    private SwitchButton noticeSwitch;
    private Map<String, Boolean> toggleStateMap;
    private NimUserInfo mUserInfo;
    private TextView mBZName;
    private TextView mPMName;//手机号备注内容
    private TextView sourceText;//好友来源

    private String contactName="";
    private String way="";

    public static void start(Context context, String account, String way) {
        Intent intent = new Intent();
        intent.setClass(context, UserProfileActivity.class);
        intent.putExtra(Extras.EXTRA_ACCOUNT, account);
        intent.putExtra(Extras.WAY, way);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        ToolBarOptions options = new ToolBarOptions();
        options.titleId = R.string.user_profile;
        setToolBar(R.id.toolbar, options);

        account = getIntent().getStringExtra(Extras.EXTRA_ACCOUNT);
        way = getIntent().getStringExtra(Extras.WAY);
        initActionbar();

        findViews();
        registerObserver(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUserInfo();
        updateToggleView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObserver(false);
    }

    private void registerObserver(boolean register) {
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
        NIMClient.getService(FriendServiceObserve.class).observeMuteListChangedNotify(muteListChangedNotifyObserver, register);
    }

    Observer<MuteListChangedNotify> muteListChangedNotifyObserver = new Observer<MuteListChangedNotify>() {
        @Override
        public void onEvent(MuteListChangedNotify notify) {
            setToggleBtn(noticeSwitch, !notify.isMute());
        }
    };

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> account) {
            updateUserOperatorView();
        }

        @Override
        public void onDeletedFriends(List<String> account) {
            updateUserOperatorView();
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            updateUserOperatorView();
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            updateUserOperatorView();
        }
    };

    private void findViews() {
        headImageView = findView(R.id.user_head_image);
        nameText = findView(R.id.user_name);
        genderImage = findView(R.id.gender_img);
        accountText = findView(R.id.user_account);
        toggleLayout = findView(R.id.toggle_layout);
        toggleLayout2 = findView(R.id.toggle_layout2);
        addFriendBtn = findView(R.id.add_buddy);
        chatBtn = findView(R.id.begin_chat);
        removeFriendBtn = findView(R.id.remove_buddy);
        birthdayLayout = findView(R.id.birthday);
        nickText = findView(R.id.user_nick);
        birthdayText = (TextView) birthdayLayout.findViewById(R.id.value);
        phoneLayout = findView(R.id.phone);
        mobileText = (TextView) phoneLayout.findViewById(R.id.value);
        emailLayout = findView(R.id.email);
        emailText = (TextView) emailLayout.findViewById(R.id.value);
        signatureLayout = findView(R.id.signature);
        signatureText = (TextView) signatureLayout.findViewById(R.id.value);
        aliasLayout = findView(R.id.alias);
        phoneRemarkLayout = findView(R.id.setphone);
        sourceLayout = findView(R.id.source);
        ((TextView) birthdayLayout.findViewById(R.id.attribute)).setText(R.string.birthday);
        ((TextView) phoneLayout.findViewById(R.id.attribute)).setText(R.string.phone);
        ((TextView) emailLayout.findViewById(R.id.attribute)).setText(R.string.email);
        ((TextView) signatureLayout.findViewById(R.id.attribute)).setText(R.string.signature);
        ((TextView) aliasLayout.findViewById(R.id.attribute)).setText(R.string.alias);
        ((TextView) phoneRemarkLayout.findViewById(R.id.attribute)).setText(R.string.phone_number_remark);
        ((TextView) sourceLayout.findViewById(R.id.attribute)).setText(R.string.source_remark);
        mBZName = (TextView) aliasLayout.findViewById(R.id.value);
        mPMName = (TextView) phoneRemarkLayout.findViewById(R.id.value);
        sourceText = (TextView)sourceLayout.findViewById(R.id.value);
        initCustomBac();
        initRecommend();
        addFriendBtn.setOnClickListener(onClickListener);
        chatBtn.setOnClickListener(onClickListener);
        removeFriendBtn.setOnClickListener(onClickListener);
        aliasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileEditItemActivity.startActivity(UserProfileActivity.this, UserConstant.KEY_ALIAS, account);
            }
        });
        phoneRemarkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileEditItemActivity.startActivity(UserProfileActivity.this, UserConstant.KEY_PHONE_REMARK, account);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_userprofile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.black_to_friend) {
            NIMClient.getService(FriendService.class).addToBlackList(account).setCallback(new RequestCallback<Void>() {
                @Override
                public void onSuccess(Void param) {
                    addBlack(account, "frozenFriend");
                    Toast.makeText(UserProfileActivity.this, "加入黑名单成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(int code) {
                    if (code == 408) {
                        Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserProfileActivity.this, "on failed：" + code, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onException(Throwable exception) {

                }
            });


        } else if (i == R.id.delete_friend) {
            onRemoveFriend();

        }
        return super.onOptionsItemSelected(item);

    }

    private void initActionbar() {
        TextView toolbarView = findView(R.id.action_bar_right_clickable_textview);
        if (!DemoCache.getAccount().equals(account)) {
            toolbarView.setVisibility(View.GONE);
            return;
        } else {
            toolbarView.setVisibility(View.VISIBLE);
        }
        toolbarView.setText(R.string.edit);
        toolbarView.setVisibility(View.GONE);
        toolbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileSettingActivity.start(UserProfileActivity.this, account);
            }
        });
    }

    /**
     * 初始化自定义背景
     */
    private void initCustomBac() {

        View customBacLayout = findViewById(R.id.p2p_custombac_config_layout);
        //        ((TextView) customBacLayout.findViewById(R.id.item_title)).setText(R.string.custombac);
        //        SwitchButton switchButton = (SwitchButton) customBacLayout.findViewById(R.id.user_profile_toggle);
        //        customBacLayout.findViewById(R.id.divider).setVisibility(View.GONE);


        ((TextView) customBacLayout.findViewById(R.id.item_title)).setText(com.netease.nim.uikit.R.string.custombac);
        TextView textView = ((TextView) customBacLayout.findViewById(R.id.item_detail));
        textView.setText("");
        customBacLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                if (Build.VERSION.SDK_INT >= 19) {
                    intent = new Intent(Intent.ACTION_PICK);
                }
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_SETBG);
            }
        });

        if (account.equals(NimUIKit.getAccount())) {
            customBacLayout.setVisibility(View.GONE);
        } else {
            customBacLayout.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 初始化推荐给好友
     */
    private void initRecommend() {
        View recommend = findViewById(R.id.p2p_recommend_config_layout);
        ((TextView) recommend.findViewById(R.id.item_title)).setText(R.string.recommendstr);
        ((TextView) recommend.findViewById(R.id.item_detail)).setHint("");

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入联系人选择器
                GroupSendActivity.start(UserProfileActivity.this, account, TeamRequestCode.REQUEST_RECOMMEND);
            }
        });

        if (account.equals(NimUIKit.getAccount())) {
            recommend.setVisibility(View.GONE);
        } else {
            recommend.setVisibility(View.VISIBLE);
        }
    }


    private void addToggleBtn(boolean black, boolean notice) {
        //        blackSwitch = addToggleItemView(KEY_BLACK_LIST, R.string.black_list, black);
        noticeSwitch = addToggleItemView2(KEY_MSG_NOTICE, R.string.msg_notice, notice);
    }

    private void setToggleBtn(SwitchButton btn, boolean isChecked) {
        btn.setCheck(isChecked);
    }

    private void updateUserInfo() {
//        if (NimUserInfoCache.getInstance().hasUser(account)) {
//            updateUserInfoView();
//            return;
//        }

        NimUserInfoCache.getInstance().getUserInfoFromRemote(account, new RequestCallbackWrapper<NimUserInfo>() {
            @Override
            public void onResult(int code, NimUserInfo result, Throwable exception) {
                updateUserInfoView();
            }
        });
    }

    private void updateUserInfoView() {
        //        accountText.setText("帐号：" + account);
        headImageView.loadBuddyAvatar(account);
        headImageView.setOnClickListener(new View.OnClickListener() {
            final UserInfoProvider.UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(account);
            NimUserInfo nimUserInfo =  NimUserInfoCache.getInstance().getUserInfo(account);

            @Override
            public void onClick(View v) {
                String url = userInfo != null ? userInfo.getAvatar() : nimUserInfo != null ? userInfo.getAvatar() : null;
                Intent intent = new Intent();
                intent.putExtra(ShowHeadImage.DATA,url);
                intent.setClass(UserProfileActivity.this,ShowHeadImage.class);
                startActivity(intent);
            }
        });

        if (DemoCache.getAccount().equals(account)) {
            nameText.setText(NimUserInfoCache.getInstance().getUserName(account));

        }

        final NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(account);
        if (userInfo == null) {
            LogUtil.e(TAG, "userInfo is null when updateUserInfoView");
            return;
        }

        if (userInfo.getGenderEnum() == GenderEnum.MALE) {
            genderImage.setVisibility(View.VISIBLE);
            genderImage.setBackgroundResource(R.drawable.nim_male);
        } else if (userInfo.getGenderEnum() == GenderEnum.FEMALE) {
            genderImage.setVisibility(View.VISIBLE);
            genderImage.setBackgroundResource(R.drawable.nim_female);
        } else {
            genderImage.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(userInfo.getBirthday())) {
            birthdayLayout.setVisibility(View.VISIBLE);
            birthdayText.setText(userInfo.getBirthday());
        } else {
            birthdayLayout.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(userInfo.getMobile())) {
            phoneLayout.setVisibility(View.VISIBLE);
            mobileText.setText(userInfo.getMobile());
        } else {
            phoneLayout.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(userInfo.getEmail())) {
            emailLayout.setVisibility(View.VISIBLE);
            emailText.setText(userInfo.getEmail());
        } else {
            emailLayout.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(userInfo.getSignature())) {
            signatureLayout.setVisibility(View.VISIBLE);
            signatureText.setText(userInfo.getSignature());
        } else {
            signatureLayout.setVisibility(View.GONE);
        }

    }

    RequestCallbackWrapper callback = new RequestCallbackWrapper() {
        @Override
        public void onResult(int code, Object result, Throwable exception) {
            DialogMaker.dismissProgressDialog();
            if (code == ResponseCode.RES_SUCCESS) {
                SharedPreferences sp = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(account,true);
                editor.commit();
                onUpdateCompleted();
            } else if (code == ResponseCode.RES_ETIMEOUT) {
                Toast.makeText(UserProfileActivity.this, R.string.user_info_update_failed, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void onUpdateCompleted() {
        showKeyboard(false);
        Toast.makeText(UserProfileActivity.this, R.string.user_info_update_success, Toast.LENGTH_SHORT).show();
    }

    private void updateUserOperatorView() {
        chatBtn.setVisibility(View.VISIBLE);
        if (NIMClient.getService(FriendService.class).isMyFriend(account)) {
//            removeFriendBtn.setVisibility(View.VISIBLE);
            addFriendBtn.setVisibility(View.GONE);
            updateAlias(true);
        } else {
            addFriendBtn.setVisibility(View.VISIBLE);
//            removeFriendBtn.setVisibility(View.GONE);
            updateAlias(false);
        }

        if (mBZName.getText().toString().equals(""))
            UserProfileActivityPermissionsDispatcher.showContactsWithPermissionCheck(this);
    }

    private void updateToggleView() {
        if (DemoCache.getAccount() != null && !DemoCache.getAccount().equals(account)) {
            boolean black = NIMClient.getService(FriendService.class).isInBlackList(account);
            boolean notice = NIMClient.getService(FriendService.class).isNeedMessageNotify(account);
            if (noticeSwitch == null) {
                addToggleBtn(black, notice);
            } else {
                //                setToggleBtn(blackSwitch, black);
                setToggleBtn(noticeSwitch, notice);
            }
            Log.i(TAG, "black=" + black + ", notice=" + notice);
            updateUserOperatorView();
        }
    }

    private SwitchButton addToggleItemView2(String key, int titleResId, boolean initState) {
        ViewGroup vp = (ViewGroup) getLayoutInflater().inflate(R.layout.nim_user_profile_toggle_item, null);
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.isetting_item_height));
        vp.setLayoutParams(vlp);

        TextView titleText = ((TextView) vp.findViewById(R.id.user_profile_title));
        titleText.setText(titleResId);

        vp.findViewById(R.id.line).setVisibility(View.GONE);

        SwitchButton switchButton = (SwitchButton) vp.findViewById(R.id.user_profile_toggle);
        switchButton.setCheck(initState);
        switchButton.setOnChangedListener(onChangedListener);
        switchButton.setTag(key);

        toggleLayout2.addView(vp);

        if (toggleStateMap == null) {
            toggleStateMap = new HashMap<>();
        }
        toggleStateMap.put(key, initState);
        return switchButton;
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

        vp.findViewById(R.id.line).setVisibility(View.GONE);

        toggleLayout.addView(vp);

        if (toggleStateMap == null) {
            toggleStateMap = new HashMap<>();
        }
        toggleStateMap.put(key, initState);
        return switchButton;
    }

    private void updateAlias(boolean isFriend) {
        if (isFriend) {
            aliasLayout.setVisibility(View.VISIBLE);
            aliasLayout.findViewById(R.id.arrow_right).setVisibility(View.VISIBLE);
            Friend friend = FriendDataCache.getInstance().getFriendByAccount(account);
            String phoneRemark;
            String sourceRemark;
            if (friend.getExtension()!= null&&friend.getExtension().containsKey("phoneNote")) {
                phoneRemark = friend.getExtension().get("phoneNote") + "";
            } else{
                phoneRemark = "";
            }

            if (friend.getExtension()!= null&&friend.getExtension().containsKey("sourceNote")) {
                sourceRemark = friend.getExtension().get("sourceNote") + "";
            } else{
                sourceRemark = "";
            }

            if (friend != null && !TextUtils.isEmpty(sourceRemark) && !sourceRemark.equals("null")) {
                sourceText.setText(sourceRemark);
            }else{
                sourceText.setText("");
            }


            if (friend != null && !TextUtils.isEmpty(friend.getAlias())) {
                nickText.setVisibility(View.VISIBLE);
                nameText.setText(friend.getAlias());
                nickText.setText("昵称：" + NimUserInfoCache.getInstance().getUserName(account));
                mBZName.setText(friend.getAlias());
                mPMName.setText(phoneRemark);
            } else {
                nickText.setVisibility(View.GONE);
                nameText.setText(NimUserInfoCache.getInstance().getUserName(account));
                mBZName.setText("");
                mPMName.setText("");
            }
        } else {
            aliasLayout.setVisibility(View.GONE);
            aliasLayout.findViewById(R.id.arrow_right).setVisibility(View.GONE);
            nickText.setVisibility(View.GONE);
            nameText.setText(NimUserInfoCache.getInstance().getUserName(account));
            mBZName.setText("");
            mPMName.setText("");
            sourceText.setText("");
        }
    }

    private SwitchButton.OnChangedListener onChangedListener = new SwitchButton.OnChangedListener() {
        @Override
        public void OnChanged(View v, final boolean checkState) {
            final String key = (String) v.getTag();
            if (!NetworkUtil.isNetAvailable(UserProfileActivity.this)) {
                Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                if (key.equals(KEY_BLACK_LIST)) {
                    //                    blackSwitch.setCheck(!checkState);
                } else if (key.equals(KEY_MSG_NOTICE)) {
                    noticeSwitch.setCheck(!checkState);
                }
                return;
            }

            updateStateMap(checkState, key);

            if (key.equals(KEY_BLACK_LIST)) {
                if (checkState) {
                    NIMClient.getService(FriendService.class).addToBlackList(account).setCallback(new RequestCallback<Void>() {
                        @Override
                        public void onSuccess(Void param) {
                            addBlack(account, "frozenFriend");
                            Toast.makeText(UserProfileActivity.this, "加入黑名单成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(int code) {
                            if (code == 408) {
                                Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserProfileActivity.this, "on failed：" + code, Toast.LENGTH_SHORT).show();
                            }
                            updateStateMap(!checkState, key);
                            //                            blackSwitch.setCheck(!checkState);
                        }

                        @Override
                        public void onException(Throwable exception) {

                        }
                    });
                } else {
                    NIMClient.getService(FriendService.class).removeFromBlackList(account).setCallback(new RequestCallback<Void>() {
                        @Override
                        public void onSuccess(Void param) {
                            addBlack(account, "doFrozenFriend");
                            Toast.makeText(UserProfileActivity.this, "移除黑名单成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(int code) {
                            if (code == 408) {
                                Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserProfileActivity.this, "on failed:" + code, Toast.LENGTH_SHORT).show();
                            }
                            updateStateMap(!checkState, key);
                            //                            blackSwitch.setCheck(!checkState);
                        }

                        @Override
                        public void onException(Throwable exception) {

                        }
                    });
                }
            } else if (key.equals(KEY_MSG_NOTICE)) {
                NIMClient.getService(FriendService.class).setMessageNotify(account, checkState).setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        if (checkState) {
                            Toast.makeText(UserProfileActivity.this, "开启消息提醒成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserProfileActivity.this, "关闭消息提醒成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == 408) {
                            Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserProfileActivity.this, "on failed:" + code, Toast.LENGTH_SHORT).show();
                        }
                        updateStateMap(!checkState, key);
                        noticeSwitch.setCheck(!checkState);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
            }
        }
    };

    private void addBlack(String account, String url) {

        NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(NimUIKit.getAccount());
        NimUserInfo userInfoOther = NimUserInfoCache.getInstance().getUserInfo(account);

        Map<Object, Object> map = new HashMap();
        map.put("sysFriendInfoFormid", NimUIKit.getAccount());
        map.put("sysFriendInfoFormName", userInfoMe.getName());
        map.put("sysFriendInfoFormTelephone", userInfoMe.getMobile());
        map.put("sysFriendInfoToId", account);
        map.put("sysFriendInfoToName", userInfoOther.getName());
        map.put("sysFriendInfoToTelephone", userInfoOther.getMobile());


        OKhttpHelper.send(this, new Gson().toJson(map), Common.AdapterPath + url, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });


    }


    private void updateStateMap(boolean checkState, String key) {
        if (toggleStateMap.containsKey(key)) {
            toggleStateMap.put(key, checkState);  // update state
            Log.i(TAG, "toggle " + key + "to " + checkState);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == addFriendBtn) {
                if (FLAG_ADD_FRIEND_DIRECTLY) {
                    doAddFriend(null, true);  // 直接加为好友
                } else {
                    onAddFriendByVerify(); // 发起好友验证请求
                }
            } else if (v == removeFriendBtn) {
                onRemoveFriend();
            } else if (v == chatBtn) {
                onChat();
            }
        }
    };

    /**
     * 通过验证方式添加好友
     */
    private void onAddFriendByVerify() {
        //        final EasyEditDialog requestDialog = new EasyEditDialog(this,account);
        //        requestDialog.setEditTextMaxLength(32);
        //        requestDialog.setTvNick(NimUserInfoCache.getInstance().getUserName(account));
        //        requestDialog.setTitle("aaaa");
        //        requestDialog.addNegativeButtonListener(R.string.cancel, new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                requestDialog.dismiss();
        //            }
        //        });
        //        requestDialog.addPositiveButtonListener(R.string.send, new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                requestDialog.dismiss();
        //                String msg = requestDialog.getEditMessage();
        //                doAddFriend(msg, false);
        //            }
        //        });
        //        requestDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        //            @Override
        //            public void onCancel(DialogInterface dialog) {
        //
        //            }
        //        });
        //        requestDialog.show();

        //修改添加好友样式
        try {
            final AddBuddyDialog addDialog = new AddBuddyDialog(this, R.style.dialog, NimUserInfoCache.getInstance().getUserName(account));
            addDialog.setListener(new AddBuddyDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        Toast.makeText(UserProfileActivity.this, "发送验证", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(account) && account.equals(DemoCache.getAccount())) {
            Toast.makeText(UserProfileActivity.this, "不能加自己为好友", Toast.LENGTH_SHORT).show();
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
                        updateUserOperatorView();
                        if (VerifyType.DIRECT_ADD == verifyType) {
                            Toast.makeText(UserProfileActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(UserProfileActivity.this, "添加好友请求发送成功", Toast.LENGTH_SHORT).show();
                            addFriendBtn.setText("已添加,等待对方同意");
                            addFriendBtn.setTextColor(getResources().getColor(R.color.gray7));
                            addFriendBtn.setClickable(false);
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        DialogMaker.dismissProgressDialog();
                        if (code == 408) {
                            Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast
                                    .LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserProfileActivity.this, "on failed:" + code, Toast
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

    private void onRemoveFriend() {
        Log.i(TAG, "onRemoveFriend");
        if (!NetworkUtil.isNetAvailable(this)) {
            Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        EasyAlertDialog dialog = EasyAlertDialogHelper.createOkCancelDiolag(this, getString(R.string.remove_friend),
                getString(R.string.remove_friend_tip), true,
                new EasyAlertDialogHelper.OnDialogActionListener() {

                    @Override
                    public void doCancelAction() {

                    }

                    @Override
                    public void doOkAction() {
                        DialogMaker.showProgressDialog(UserProfileActivity.this, "", true);
                        NIMClient.getService(FriendService.class).deleteFriend(account).setCallback(new RequestCallback<Void>() {
                            @Override
                            public void onSuccess(Void param) {
                                DialogMaker.dismissProgressDialog();
                                Toast.makeText(UserProfileActivity.this, R.string.remove_friend_success, Toast.LENGTH_SHORT).show();
                                //删除好友成功,通知后台
                                deleteFriend();
                                setSource(account,"");
                                finish();
                            }

                            @Override
                            public void onFailed(int code) {
                                DialogMaker.dismissProgressDialog();
                                if (code == 408) {
                                    Toast.makeText(UserProfileActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UserProfileActivity.this, "on failed:" + code, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onException(Throwable exception) {
                                DialogMaker.dismissProgressDialog();
                            }
                        });
                    }
                });
        if (!isFinishing() && !isDestroyedCompatible()) {
            dialog.show();
        }
    }

    private void setSource(String account, String source){
        Map<FriendFieldEnum, Object> map = new HashMap<>();
        Map<String, Object> exts = new HashMap<>();
        exts.put("sourceNote", source);
        map.put(FriendFieldEnum.EXTENSION, exts);
        NIMClient.getService(FriendService.class).updateFriendFields(account, map).setCallback(new RequestCallbackWrapper() {
            @Override
            public void onResult(int code, Object result, Throwable exception) {
            }
        });
    }

    /**
     * 删除好友通知
     */
    private void deleteFriend() {

        NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());
        NimUserInfo userInfoOther = NimUserInfoCache.getInstance().getUserInfo(account);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("sysFriendInfoFormid", DemoCache.getAccount());
        map.put("sysFriendInfoFormName", userInfoMe.getName());
        map.put("sysFriendInfoFormTelephone", userInfoMe.getMobile());
        map.put("sysFriendInfoToId", account);
        map.put("sysFriendInfoToName", userInfoOther.getName());
        map.put("sysFriendInfoToTelephone", userInfoOther.getMobile());
        map.put("sysFriendInfoGroupId", "");
        map.put("sysFriendInfoGroupName", "");

        OKhttpHelper.send(this, new Gson().toJson(map), Common.AdapterPath + "deleteFriend", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }

    private void onChat() {
        Log.i(TAG, "onChat");
//        SessionHelper.startP2PSession(, account, false);
        NimUIKitImpl.startP2PSession2(UserProfileActivity.this, account, null, false);
//        startActivity(new Intent(this, P2PMessageActivity.class));
    }

    /**
     * 发送添加好友信息
     *
     * @param msg
     */
    private void sendMsg(String msg) {
        NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());
        NimUserInfo userInfoOther = NimUserInfoCache.getInstance().getUserInfo(account);
        String tip = "申请加您为好友";
        String deft = userInfoMe.getName() + ":我是" + userInfoMe.getName();

        HashMap<Object, Object> map = new HashMap<>();
        SendMsgModel sendMsgModel = new SendMsgModel();
        sendMsgModel.setSysFriendInfoTitle("新好友提醒");
        sendMsgModel.setSysFriendInfoFormid(DemoCache.getAccount());
        sendMsgModel.setSysFriendInfoFormName(userInfoMe.getName());
        sendMsgModel.setSysFriendInfoMessage(msg.equals("") ? deft + tip : userInfoMe.getName() + ":" + "\"" + msg + "\"" + tip);
        sendMsgModel.setSysFriendInfoToId(account);
        sendMsgModel.setSysFriendInfoToName(userInfoOther.getName());
        sendMsgModel.setSysFriendInfoIsTip("0");
        sendMsgModel.setSysFriendInfoGroupId("");
        sendMsgModel.setSource(way);
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

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == TeamRequestCode.REQUEST_RECOMMEND) {
            final List<GroupSendModel> groupSendModel = (List<GroupSendModel>) data.getSerializableExtra("DATA");
            if (groupSendModel != null) {

                final IOSDialog dialog = new IOSDialog(this, R.style.customDialog, R.layout.dialog_m);
                dialog.show();
                TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
                TextView content = (TextView) dialog.findViewById(R.id.content);
                TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < groupSendModel.size(); i++) {
                    GroupSendModel sendModel = groupSendModel.get(i);
                    String name = sendModel.getName();
                    if (name == null) {
                        name = NimUIKit.getUserInfoProvider().getUserInfo(sendModel.getP2pAccount()).getName();
                    }
                    if (i == groupSendModel.size() - 1) {
                        stringBuffer.append(name + "?");
                    } else {
                        if (stringBuffer.length() >= 15) {
                            stringBuffer.append("等" + groupSendModel.size() + "个人?");
                            break;
                        } else {
                            stringBuffer.append(name + "、");
                        }
                    }
                }

                content.setText("确认推荐\"" + NimUIKit.getUserInfoProvider().getUserInfo(account).getName() + "\"给" + " " + stringBuffer);

                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BusinessCardAttachment attachment = new BusinessCardAttachment(account);
                        for (GroupSendModel sendModel : groupSendModel) {
                            if (sendModel.getItemType() == 2) {
                                IMMessage message = MessageBuilder.createCustomMessage(sendModel.getP2pAccount(), SessionTypeEnum.P2P, "", attachment);

                                NIMClient.getService(MsgService.class).sendMessage(message, false);
                            } else {
                                IMMessage message = MessageBuilder.createCustomMessage(sendModel.getTeamId(), SessionTypeEnum.Team, "", attachment);
                                NIMClient.getService(MsgService.class).sendMessage(message, false);
                            }
                        }
                        Toast.makeText(UserProfileActivity.this, "已发送", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }


                });

            } else {
                Toast.makeText(UserProfileActivity.this, "请选择至少一个联系人！", Toast.LENGTH_SHORT).show();
            }
        }

        switch (requestCode) {
            case REQUEST_CODE_SETBG:
                Uri uri = data.getData();
                //uri = geturi(data);//解决方案
                String[] pro = {MediaStore.Images.Media.DATA};
                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = getContentResolver().query(uri, pro, null, null, null);
                //拿到引索
                try {
                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //移动到光标开头
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String urlpath = cursor.getString(index);

                    SharedPreferences sp = this.getSharedPreferences("SET", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(account, urlpath);
                    editor.commit();
                    ToastUtil.showLongToast(UserProfileActivity.this, "保存背景成功");
                } catch (Exception e) { //拿不到图片路径的手机处理,如小米
                    SharedPreferences sp = this.getSharedPreferences("SET", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(account, MiPictureHelper.getPath(mContext, uri));
                    editor.commit();
                    ToastUtil.showLongToast(UserProfileActivity.this, "保存背景成功");
                }

                break;
            default:
                break;
        }


    }

    public class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        private final static String TAG = "My Thread ===> ";
        public void run(){
            getList();
        }
    }

    //获取通讯录
    public void getList() {
        try {
            Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = this.getContentResolver().query(contactUri,
                    new String[]{"display_name", "sort_key", "contact_id", "data1"},
                    null, null, "sort_key");
            String contactNumber;
            String contactSortKey;
            int contactId;
            final NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(account);
            Friend friendItem = FriendDataCache.getInstance().getFriendByAccount(account);
            SharedPreferences sp = this.getSharedPreferences("UserProfile", Context.MODE_PRIVATE);

            if (!sp.getBoolean(account,false)){
                if (userInfo.getMobile().length()>0){
                    if (friendItem != null && TextUtils.isEmpty(friendItem.getAlias())) {
                        while (cursor.moveToNext()) {
                            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                            if (replaceBlank(contactNumber).equals(userInfo.getMobile())){
                                handler.sendEmptyMessage(100);
                                return;
                            }
                        }
                    }

                }
            }

            cursor.close();//使用完后一定要将cursor关闭，不然会造成内存泄露等问题

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NeedsPermission({Manifest.permission.READ_CONTACTS})
    void showContacts() {
        // NOTE: Perform action that requires the permission.
        // If this is run by PermissionsDispatcher, the permission will have been granted
        new MyThread().start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UserProfileActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("-|\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                final IOSDialog dialog = new IOSDialog(UserProfileActivity.this, R.style.customDialog, R.layout.dialog_m);
                dialog.show();
                TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
                final TextView content = (TextView) dialog.findViewById(R.id.content);
                TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
                content.setText("检测到本地通讯录存在此手机号，是否更新备注");
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogMaker.showProgressDialog(UserProfileActivity.this, null, true);
                        Map<FriendFieldEnum, Object> map = new HashMap<>();
//                                        nickName = nickName.substring(0,nickName.indexOf("("));
                        map.put(FriendFieldEnum.ALIAS, contactName);
                        NIMClient.getService(FriendService.class).updateFriendFields(account, map).setCallback(callback);
                        dialog.dismiss();
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sp = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(account,true);
                        editor.commit();
                        dialog.dismiss();
                    }


                });
            }
        }
    };
}
