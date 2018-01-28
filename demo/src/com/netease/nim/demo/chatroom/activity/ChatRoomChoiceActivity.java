package com.netease.nim.demo.chatroom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.chatroom.adapter.ChatRoomChoiceAdapter;
import com.netease.nim.demo.chatroom.model.SearchRoomModel;

import java.io.Serializable;
import java.util.List;

public class ChatRoomChoiceActivity extends AppCompatActivity {
    private static String DATA = "data";
    private List<SearchRoomModel.DataBean.ChatRoomsBean> mChatRoomsList;
    private RecyclerView                                 rcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_choice);

        mChatRoomsList = (List<SearchRoomModel.DataBean.ChatRoomsBean>) getIntent().getSerializableExtra(DATA);


        initView();
    }

    private void initView() {
        rcm = (RecyclerView) findViewById(R.id.rcv);
        ChatRoomChoiceAdapter chatRoomChoiceAdapter = new ChatRoomChoiceAdapter(this, mChatRoomsList);
        rcm.setLayoutManager(new LinearLayoutManager(this));
        rcm.setAdapter(chatRoomChoiceAdapter);


    }


    public static void startActivity(Context context, List<SearchRoomModel.DataBean.ChatRoomsBean> chatRooms) {
        Intent intent = new Intent(context, ChatRoomChoiceActivity.class);
        intent.putExtra(DATA, (Serializable) chatRooms);
        context.startActivity(intent);
    }
}
