package com.netease.nim.demo.groupsend.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.groupsend.GroupSendActivity;
import com.netease.nim.demo.groupsend.adapter.MessageListAdapter;
import com.netease.nim.demo.groupsend.model.MessageListModel;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by jasmin on 2017/11/8.
 */

public class MessageListActivity extends UI {

    public static final int TEAM = 1;
    public static final int P2P = 2;
    RecyclerView recyclerview;
    private MessageListAdapter adapter;
    private List<MessageListModel.DataBean> datas = new ArrayList<MessageListModel.DataBean>();

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MessageListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_messagelist_activity);


        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "群发消息内容";
        setToolBar(com.netease.nim.uikit.R.id.toolbar, options);



    }


    private void initView() {
        recyclerview =(RecyclerView) findViewById(R.id.recyclerview);

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageListActivity.this, GroupSendActivity.class));
            }
        });

        SharedPreferences sp = getSharedPreferences(SendMessageActivity.GROUPMESSAGE, Context.MODE_PRIVATE);
        String jsonStr = sp.getString(SendMessageActivity.GROUPMESSAGE, "");
        if (jsonStr.equals(""))
            return;
        MessageListModel model = new Gson().fromJson(jsonStr, new TypeToken<MessageListModel>() {
        }.getType());
        datas = model.getData();

        adapter = new MessageListAdapter(R.layout.item_messagelist, datas);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GroupSendActivity.start(MessageListActivity.this,datas.get(position));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

 /*   @OnClick(R.id.btn_send)
    public void onViewClicked() {

    }*/


}
