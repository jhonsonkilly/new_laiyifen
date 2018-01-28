package com.netease.nim.demo.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.demo.R;
import com.netease.nim.demo.groupsend.GroupSendActivity;
import com.netease.nim.demo.groupsend.adapter.BillItemAdapter;
import com.netease.nim.demo.groupsend.model.GroupSendModel;
import com.netease.nim.demo.main.util.Cn2Spell;
import com.netease.nim.demo.session.extension.ShareAttachment;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasmin on 2018/1/15.
 */

public class ShareSelectActivity extends UI {
    EditText etSearch;
    RecyclerView recyclerview;
    Button btnNext;

    public static final int TEAM = 1;
    public static final int P2P  = 2;
    private BaseQuickAdapter adapter;
    private List<GroupSendModel> datas       = new ArrayList<GroupSendModel>();
    private List<GroupSendModel> searchDatas = new ArrayList<GroupSendModel>();
    List<Team> teams;
    List<String> friends;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent();
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 1:
                    String data = etSearch.getText().toString();

                    searchDatas.clear();

                    getmDataSub(data);

                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    public static final void start(Activity activity, int code) {
        Intent intent = new Intent();
        intent.setClass(activity, GroupSendActivity.class);
        activity.startActivityForResult(intent, code);
    }

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, GroupSendActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_choose_activity);

        ToolBarOptions options = new ToolBarOptions();
        //        options.navigateId = com.netease.nim.uikit.R.drawable.nim_actionbar_white_back_icon;
        options.titleString = "选择共享对象";
        setToolBar(com.netease.nim.uikit.R.id.toolbar, options);


        teams = NIMClient.getService(TeamService.class).queryTeamListBlock();
        friends = NIMClient.getService(FriendService.class).getFriendAccounts();

        initData();



        setSearchView();
    }

    private void setSearchView() {

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                handler.sendEmptyMessage(1);
            }
        });
    }


    private void getmDataSub(String data) {

        JSONObject object = new JSONObject();

        for (GroupSendModel model : datas) {


            if (model.getItemType() == P2P) {

                final NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(model.getP2pAccount());
                String mobile = userInfo.getMobile();
                if (mobile.contains(data))
                    searchDatas.add(model);
            } else {

                if (model.getName().contains(data))
                    searchDatas.add(model);
            }

            if (Cn2Spell.getPinYin(model.getName()).contains(data)) {
                searchDatas.add(model);
            } else if (Cn2Spell.getPinYinHeadChar(model.getName()).contains(data)) {
                searchDatas.add(model);
            }
        }
    }

    private void initData() {

        StringBuffer stringBuffer = new StringBuffer();
        for (NimUserInfo nimUserInfo : NimUserInfoCache.getInstance().getAllUsersOfMyFriend()) {

            stringBuffer.append(nimUserInfo.getAccount());
        }

        for (Team team : teams) {
            GroupSendModel model = new GroupSendModel(TEAM);

            model.setName(team.getName());
            model.setIconUrl(team.getIcon());
            model.setSelect(false);
            model.setTeamId(team.getId());
            model.setItemType(TEAM);
            datas.add(model);
        }

        for (String friend : friends) {
            if (getIntent().getStringExtra("TAG") != null) {
                if (friend.equals(getIntent().getStringExtra("TAG"))) {
                    continue;
                }
            }

            if (stringBuffer.toString().contains(friend)) {
                GroupSendModel model = new GroupSendModel(P2P);
                //            model.setName(NimUserInfoCache.getInstance().getUserDisplayNameEx(friend));
                model.setSelect(false);
                model.setItemType(P2P);
                model.setP2pAccount(friend);
                datas.add(model);
            }


        }

        searchDatas.addAll(datas);

        initView();
    }

    private void initView() {


        etSearch = (EditText) findViewById(R.id.et_search);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        btnNext = (Button) findViewById(R.id.btn_next);


        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<GroupSendModel> lists = new ArrayList<GroupSendModel>();

                if (searchDatas.size() > 0) {
                    for (GroupSendModel model : searchDatas) {
                        if (model.isSelect())
                            lists.add(model);
                    }

                    if (lists.size() >= 200) {
                        Toast.makeText(ShareSelectActivity.this, "群发人数不得超过200人", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (GroupSendModel model : lists) {
                        if (model.getItemType() == P2P) {

                            if (model.isSelect()) {
                                ShareAttachment attachment = new ShareAttachment();
                                IMMessage message;
                                message = MessageBuilder.createCustomMessage(model.getP2pAccount(), SessionTypeEnum.P2P,attachment);
                                NIMClient.getService(MsgService.class).sendMessage(message, false);
                            }

                        } else {
                            if (model.isSelect()) {
                                ShareAttachment attachment = new ShareAttachment();
                                IMMessage message;
                                message = MessageBuilder.createCustomMessage(model.getP2pAccount(), SessionTypeEnum.Team,attachment);
                                NIMClient.getService(MsgService.class).sendMessage(message, false);
                            }

                        }
                    }
                } else {
                    Toast.makeText(ShareSelectActivity.this, "请选择对象", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter = new BillItemAdapter(R.layout.item_groupsend_list, searchDatas);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GroupSendModel model = searchDatas.get(position);
                if (model.isSelect()) {
                    model.setSelect(false);
                } else {
                    model.setSelect(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(adapter);
    }

}
