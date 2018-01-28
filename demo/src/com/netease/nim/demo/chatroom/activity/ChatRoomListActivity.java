package com.netease.nim.demo.chatroom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.chatroom.adapter.ChatRoomAdapter;
import com.netease.nim.demo.chatroom.model.ChatRoomListModel;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomListActivity extends UI implements View.OnClickListener {

    private RecyclerView    mRcv;
    private ChatRoomAdapter mChatRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private List<ChatRoomListModel.DataBean.ChatRoomOfMineBean> chatRoomOfMine = new ArrayList<>();
    List<ChatRoomListModel.DataBean.ChatRoomsContainMeBean> chatRoomsContainMe = new ArrayList<>();

    private void initData() {
        chatRoomOfMine.clear();
        chatRoomsContainMe.clear();

        Map<Object, Object> header = new HashMap();
        header.put("token", Preferences.getUserMainToken());
        OKhttpHelper.getWithHeader(this, header, Common.AdapterPath + "chat_rooms", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    ChatRoomListModel model = new Gson().fromJson(s, new TypeToken<ChatRoomListModel>() {
                    }.getType());

                    if (model.getStatus() == 200) {
                        ChatRoomListModel.DataBean data = model.getData();
                        chatRoomOfMine.addAll(data.getChatRoomOfMine());
                        chatRoomsContainMe.addAll(data.getChatRoomsContainMe());

                        if (mChatRoomAdapter != null)
                            mChatRoomAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(ChatRoomListActivity.this, "哎呀,网络出错了");
            }
        });
    }

    private void initView() {
        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "我的聊天室";
        setToolBar(R.id.toolbar, options);


        findViewById(R.id.in_room).setOnClickListener(this);
        initRcv();

    }

    private void initRcv() {
        mRcv = (RecyclerView) findViewById(R.id.rcv);
        mChatRoomAdapter = new ChatRoomAdapter(this, chatRoomOfMine, chatRoomsContainMe);
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        mRcv.setAdapter(mChatRoomAdapter);

    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChatRoomListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.in_room) { //快进聊天室
            CreateChatRoomActivity.start(this);
        }

    }
}
