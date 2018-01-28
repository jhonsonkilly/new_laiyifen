package com.netease.nim.demo.contact.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.netease.nim.demo.R;
import com.netease.nim.demo.contact.viewholder.BlackListViewHolder;
import com.netease.nim.demo.main.activity.CustomScanActivity;
import com.netease.nim.demo.main.activity.GlobalSearchActivity;
import com.netease.nim.demo.main.activity.MainActivity;
import com.netease.nim.demo.main.activity.SettingsActivity;
import com.netease.nim.demo.main.widget.MyPopWindow;
import com.netease.nim.demo.team.TeamCreateHelper;
import com.netease.nim.demo.yidiancard.widget.IOSDialog;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.adapter.TAdapterDelegate;
import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.netease.nim.uikit.contact.core.item.ContactIdFilter;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.team.helper.TeamHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 黑名单
 * Created by huangjun on 2015/8/12.
 */
public class BlackListActivity extends UI implements TAdapterDelegate {
    private static final String TAG                   = "BlackListActivity";
    private static final int    REQUEST_CODE_BLACK    = 1;
    private static final int    REQUEST_CODE_ADVANCED = 2;

    private ListView listView;
    private List<UserInfoProvider.UserInfo> data = new ArrayList<>();
    private BlackListAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BlackListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_list_activity);

        ToolBarOptions options = new ToolBarOptions();
        options.titleId = R.string.black_list;
        setToolBar(R.id.toolbar, options);

        initData();
        findViews();
        initActionbar();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_activity_menu, menu);
//        super.onCreateOptionsMenu(menu);
//        return true;
//    }



    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public Class<? extends TViewHolder> viewHolderAtPosition(int position) {
        return BlackListViewHolder.class;
    }

    @Override
    public boolean enabled(int position) {
        return false;
    }

    private void initData() {
        final List<String> accounts = NIMClient.getService(FriendService.class).getBlackList();
        List<String> unknownAccounts = new ArrayList<>();

        for (String account : accounts) {
            if (!NimUserInfoCache.getInstance().hasUser(account)) {
                unknownAccounts.add(account);
            } else {
                data.add(NimUserInfoCache.getInstance().getUserInfo(account));
            }
        }

        if (!unknownAccounts.isEmpty()) {
            NimUserInfoCache.getInstance().getUserInfoFromRemote(unknownAccounts, new RequestCallbackWrapper<List<NimUserInfo>>() {
                @Override
                public void onResult(int code, List<NimUserInfo> users, Throwable exception) {
                    if (code == ResponseCode.RES_SUCCESS) {
                        data.addAll(users);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void initActionbar() {
        /*TextView toolbarView = findView(R.id.action_bar_right_clickable_textview);
        toolbarView.setText(R.string.add);
        toolbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactSelectActivity.Option option = new ContactSelectActivity.Option();
                option.title = "选择黑名单";
                option.maxSelectNum = 5;
                ArrayList<String> excludeAccounts = new ArrayList<>();
                for (UserInfoProvider.UserInfo user : data) {
                    if (user != null) {
                        excludeAccounts.add(user.getAccount());
                    }
                }
                option.itemFilter = new ContactIdFilter(excludeAccounts, true);
                NimUIKit.startContactSelector(BlackListActivity.this, option, REQUEST_CODE_BLACK);
            }
        });*/
    }

    private void findViews() {
        //        TextView notifyText = ((TextView) findView(R.id.notify_bar).findViewById(R.id.status_desc_label));
        //        notifyText.setText(R.string.black_list_tip);
        //        notifyText.setBackgroundColor(getResources().getColor(R.color.color_yellow_fcf3cd));
        //        notifyText.setTextColor(getResources().getColor(R.color.color_yellow_796413));
        listView = findView(R.id.black_list_view);
        adapter = new BlackListAdapter(this, data, this, viewHolderEventListener);
        listView.setAdapter(adapter);
    }

    private BlackListAdapter.ViewHolderEventListener viewHolderEventListener = new BlackListAdapter.ViewHolderEventListener() {
        @Override
        public void onRemove(final UserInfoProvider.UserInfo user) {
            showDialog(user);


        }

        @Override
        public void onItemClick(UserInfoProvider.UserInfo userInfo) {
            Log.i(TAG, "onItemClick, user account=" + userInfo.getAccount());
        }
    };

    /**
     * 移除黑名单对话框
     */
    private void showDialog(final UserInfoProvider.UserInfo user) {
        final IOSDialog dialog = new IOSDialog(this, R.style.customDialog, R.layout.dialog_black);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView msg = (TextView) dialog.findViewById(R.id.msg);
        msg.setText("是否将 " + user.getName() + " 移除黑名单");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                remove(user);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }


        });

    }

    private void remove(final UserInfoProvider.UserInfo user) {
        NIMClient.getService(FriendService.class).removeFromBlackList(user.getAccount()).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Toast.makeText(BlackListActivity.this, "移出黑名单成功", Toast.LENGTH_SHORT).show();
                data.remove(user);
                removeBlack(user.getAccount(),"doFrozenFriend");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code) {
                Toast.makeText(BlackListActivity.this, "移出黑名单失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable exception) {

            }
        });
    }


    private void removeBlack(String account,String url) {

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

    private void addUserToBlackList(ArrayList<String> selected) {
        for (final String account : selected) {
            NIMClient.getService(FriendService.class).addToBlackList(account).setCallback(new RequestCallback<Void>() {
                @Override
                public void onSuccess(Void param) {
                    data.add(NimUserInfoCache.getInstance().getUserInfo(account));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailed(int code) {
                    Toast.makeText(BlackListActivity.this, "加入黑名单失败,code:" + code, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onException(Throwable exception) {

                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_BLACK:
                    final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                    if (selected != null && !selected.isEmpty()) {
                        addUserToBlackList(selected);
                    }
                    break;

                case REQUEST_CODE_ADVANCED:
                    final ArrayList<String> selected2 = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
                    TeamCreateHelper.createAdvancedTeam(BlackListActivity.this, selected2);

                    break;
            }
        }
    }
}
