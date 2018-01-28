package com.netease.nim.uikit.forward;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.forward.adapter.ForwardListAdapter;
import com.netease.nim.uikit.forward.model.GroupSendModel;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jasmin on 2017/10/25.
 */

public class ForwardChooseActivity extends UI {
    public static final String RESULT_DATA = "RESULT_DATA"; // 返回结果

    EditText     etSearch;
    RecyclerView recyclerview;
    Button       btnNext;

    public static final int TEAM = 1;
    public static final int P2P  = 2;
    private BaseQuickAdapter adapter;
    private List<GroupSendModel> datas       = new ArrayList<GroupSendModel>();
    private List<GroupSendModel> searchDatas = new ArrayList<GroupSendModel>();
    List<Team>   teams;
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

    public static void startActivityForResult(Context context, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, ForwardChooseActivity.class);

        ((Activity) context).startActivityForResult(intent, requestCode);
    }


    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ForwardChooseActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_choose_activity);


        ToolBarOptions options = new ToolBarOptions();
        //        options.navigateId = com.netease.nim.uikit.R.drawable.nim_actionbar_white_back_icon;
        options.titleString = "选择转发对象";
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


        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initData() {
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

            GroupSendModel model = new GroupSendModel(P2P);
            //            model.setName(NimUserInfoCache.getInstance().getUserDisplayNameEx(friend));
            model.setSelect(false);
            model.setItemType(P2P);
            model.setP2pAccount(friend);
            datas.add(model);
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
                for (GroupSendModel model : searchDatas) {
                    if (model.isSelect()){
                        lists.add(model);
                    }

                }

                Intent intent = new Intent();
                intent.putExtra(RESULT_DATA, lists);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        adapter = new ForwardListAdapter(R.layout.item_groupsend_list,searchDatas);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == 99) {
                    finish();
                } else {
//                    MessageListModel.DataBean dataBean = (MessageListModel.DataBean) getIntent().getSerializableExtra("DATA");
//                    for (GroupSendModel searchData : searchDatas) {
//                        for (GroupSendModel model : dataBean.getSendList()) {
//                            if (model.getItemType() == P2P) {
//                                if (model.getP2pAccount().equals(searchData.getP2pAccount())) {
//                                    searchData.setSelect(true);
//                                }
//                            } else {
//                                if (model.getTeamId().equals(searchData.getTeamId())) {
//                                    searchData.setSelect(true);
//                                }
//                            }
//                        }
//                    }
                }
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
