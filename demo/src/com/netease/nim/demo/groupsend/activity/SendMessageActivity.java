package com.netease.nim.demo.groupsend.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.groupsend.model.GroupSendModel;
import com.netease.nim.demo.groupsend.model.MessageListModel;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by jasmin on 2017/10/25.
 */

public class SendMessageActivity extends UI {

    public static final int    TEAM         = 1;
    public static final int    P2P          = 2;
    public static final String GROUPMESSAGE = "GROUPMESSAGE";
    TextView  tvSendto;
    ImageView ivAdd;
    EditText  etContent;

    private List<GroupSendModel> datas = new ArrayList<>();
    MessageListModel.DataBean MessageData;
    private boolean isReSend = false;
    private TextView send;

    public static final void start(Context context, MessageListModel.DataBean dataBean) {
        Intent intent = new Intent();
        intent.putExtra("DATA", (Serializable) dataBean);
        intent.putExtra("RESEND", true);
        intent.setClass(context, SendMessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_send_activity);


        initView();

        send = (TextView) findViewById(R.id.tv_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString()+"";
                if (content.equals("") || null == content){
                    Toast.makeText(SendMessageActivity.this,"内容不能为空",Toast.LENGTH_LONG);
                    return;
                }
                sendMsg();
            }
        });


        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "群发消息内容";
        setToolBar(com.netease.nim.uikit.R.id.toolbar, options);
        this.getToolBar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                setResult(RESULT_CANCELED, in);
                finish();
            }
        });

        isReSend = getIntent().getBooleanExtra("RESEND", false);
        if (isReSend) {
            MessageData = (MessageListModel.DataBean) getIntent().getSerializableExtra("DATA");
            datas = MessageData.getSendList();
            initResendData();
        } else {
            datas = (List<GroupSendModel>) getIntent().getSerializableExtra("DATA");

            initData();
        }


    }

    private void initView() {
        final SharedPreferences sp = getSharedPreferences(GROUPMESSAGE, Context.MODE_PRIVATE);

        tvSendto = (TextView) findViewById(R.id.tv_sendto);
        ivAdd = (ImageView) findViewById(R.id.iv_add);
        etContent = (EditText) findViewById(R.id.et_content);
        etContent.setText(sp.getString("content",""));

        findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("content", etContent.getText().toString());
                editor.commit();

                Intent in = new Intent();
                setResult(99, in);
                finish();
            }
        });
    }

    private void initResendData() {
        String sendTo = "收件人： ";
        sendTo = sendTo + getSendName();
        tvSendto.setText(sendTo);
        etContent.setText(MessageData.getContent());
    }

    private void initData() {
        String sendTo = "收件人： ";
        sendTo = sendTo + getSendName();
        tvSendto.setText(sendTo);
    }

    private String getSendName() {
        String sendTo = "";
        for (GroupSendModel model : datas) {
            if (model.getItemType() == P2P) {

                String name = NimUserInfoCache.getInstance().getUserDisplayNameEx(model.getP2pAccount());
                if (model.isSelect())
                    sendTo = sendTo + name + "，";
            } else {
                if (model.isSelect())
                    sendTo = sendTo + model.getName() + "，";
            }
        }
        sendTo = sendTo.substring(0, sendTo.length() - 1);
        return sendTo;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_send_activity_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_message:

                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void sendMsg() {
        ArrayList<String> sessionId = new ArrayList<String>();
        for (GroupSendModel model : datas) {
            if (model.getItemType() == P2P) {
                sessionId.add(model.getP2pAccount());
                if (model.isSelect()) {
                    IMMessage textMessage = MessageBuilder.createTextMessage(model.getP2pAccount(), SessionTypeEnum.P2P, etContent.getText().toString());
                    NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
                }

            } else {
                if (model.isSelect()) {
                    sessionId.add(model.getTeamId());
                    IMMessage textMessage = MessageBuilder.createTextMessage(model.getTeamId(), SessionTypeEnum.Team, etContent.getText().toString());
                    NIMClient.getService(MsgService.class).sendMessage(textMessage, false);
                }

            }
        }


        MessageListModel model = new MessageListModel();
        MessageListModel.DataBean dataBean = new MessageListModel.DataBean();
        List<MessageListModel.DataBean> dataBeenList = new ArrayList<MessageListModel.DataBean>();
        SharedPreferences sp = getSharedPreferences(GROUPMESSAGE, Context.MODE_PRIVATE);
        String jsonStr = sp.getString(GROUPMESSAGE, "");
        if (!jsonStr.equals("")) {
            model = new Gson().fromJson(jsonStr, new TypeToken<MessageListModel>() {
            }.getType());
            dataBeenList = model.getData();
        }
        if (dataBeenList == null) {
            dataBeenList = new ArrayList<MessageListModel.DataBean>();
        }
        dataBean.setSendList(datas);
        dataBean.setContent(etContent.getText().toString());
        //                dataBean.setName(tvSendto.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = sdf.format(curDate);
        dataBean.setSendDate(str);

        dataBeenList.add(dataBean);

        MessageListModel data = new MessageListModel();
        data.setData(dataBeenList);

        //                JSONArray jsonArray = new JSONArray();
        //                JSONObject object = new JSONObject();
        //                JSONObject mainObject = new JSONObject();
        //
        //                jsonArray.put(object);
        //                try {
        //                    object.put("name",tvSendto.getText().toString());
        //                    object.put("content",etContent.getText().toString());
        //                    jsonArray.put(object);
        //                    jsonArray.put(object);
        //                    mainObject.put("data",jsonArray);
        //                } catch (JSONException e) {
        //                    e.printStackTrace();
        //                }

        //                Log.i("tag",jsonArray.toString());


        SharedPreferences.Editor editor = sp.edit();
        editor.putString(GROUPMESSAGE, new Gson().toJson(data));
        editor.putString("content", "");
        editor.commit();
        Intent in = new Intent();
        setResult(88, in);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
