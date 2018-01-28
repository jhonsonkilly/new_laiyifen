package com.netease.nim.demo.chatroom.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.chatroom.model.ChatRoomNameModel;
import com.netease.nim.demo.chatroom.model.CreateRoomReturnModel;
import com.netease.nim.demo.chatroom.model.JoinRoomModel;
import com.netease.nim.demo.chatroom.model.SearchRoomModel;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.sync.Common;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CreateChatRoomActivity extends AppCompatActivity implements View.OnClickListener {

    //头像
    private HeadImageView mAvatar;
    //信息
    private TextView mMsg;
    //设置密码
    private EditText mEdit_pwd;
    //设置聊天室名字
    private EditText mEdit_name;
    //进入
    private Button mEnter_into;
    //退出
    private TextView mBack;
    //提示语
    private TextView mRemind;
    private double        mLatitude;
    private double        mLongitude;
    private String mPwd;
    private String mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat_room);

        initView();

        initListener();

        initData();
    }


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CreateChatRoomActivity.class);
        activity.startActivity(intent);
    }


    private void initView() {
        mAvatar = (HeadImageView) findViewById(R.id.avatar);
        mMsg = (TextView) findViewById(R.id.msg);
        mEdit_pwd = (EditText) findViewById(R.id.edit_pwd);
        mEdit_name = (EditText) findViewById(R.id.edit_name);
        mEnter_into = (Button) findViewById(R.id.enter_into);
        mBack = (TextView) findViewById(R.id.tv_back);
        mRemind = (TextView) findViewById(R.id.remind);
    }


    private void initListener() {
        mEnter_into.setOnClickListener(this);
        mBack.setOnClickListener(this);

        mEdit_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (content.equals("")) {
                    return;
                }
                if (content.length() >= 4) {
                    getNameByCode(content);
                }


            }
        });
    }

    private void getNameByCode(String content) {

        Map<Object, Object> header = new HashMap();
        header.put("token", Preferences.getUserMainToken());
        header.put("longitude", mLongitude + "");
        header.put("latitude", mLatitude + "");

        OKhttpHelper.getWithHeader(this, header, Common.AdapterPath + "chat_room/getNameByCode/" + content, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    ChatRoomNameModel model = new Gson().fromJson(s, new TypeToken<ChatRoomNameModel>() {
                    }.getType());

                    if (model.getStatus() == 200) {
                        //设置匹配到的ChatRoom名字
                        List<String> roomList = model.getData();
                        if (roomList.size() > 1) {
                            joinRoom();
                        } else {
                            mEdit_name.setText(model.getData().get(0));
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(CreateChatRoomActivity.this, getResources().getString(R.string.net_error));
            }
        });


    }

    /*private void showRoomDialog(List<String> roomList) {
        final IOSDialog dialog = new IOSDialog(this, R.style.customDialog, R.layout.dialog_room_list);
        dialog.show();
        RecyclerView rcv = (RecyclerView) dialog.findViewById(R.id.rcv);
        DialogAdapter dialogAdapter = new DialogAdapter(this, roomList, new DialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(String s) {
                mEdit_name.setText(s);
            }
        });
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(dialogAdapter);

    }*/


    private void initData() {
        NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(NimUIKit.getAccount());
        mMsg.setText(userInfo.getName() + " 创建此聊天室");
        Glide.with(this).load(userInfo.getAvatar()).asBitmap().into(mAvatar);

        //保存用户位置信息
        initPermission();
        saveLocation();
    }

    private void saveLocation() {
        CreateChatRoomActivityPermissionsDispatcher.doACacheNeedsPermissionWithPermissionCheck(this);
    }

    private void initPermission() {
        ArrayList<String> perList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            perList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            perList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (perList.size() > 0) {
            String[] perArr = new String[perList.size()];
            perList.toArray(perArr);
            ActivityCompat.requestPermissions(this, perArr, 100);
        }
    }


    private ProgressDialog mProgressDialog;
    private double         lat;
    private double         lng;
    private TextView address;
    private Location mLocation;
    private int count = 0;

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void doACacheNeedsPermission() {
        try {
            //处理当用户允许该权限时需要处理的方法
            String locationPro = null;
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }


            // 查找到服务信息
            Criteria criteria = new Criteria();
            // 高精度
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);
            // 低功耗
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            // 获取GPS信息
            String provider = manager.getBestProvider(criteria, true);
            // 通过GPS获取位置
            mLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("获取位置信息中...");
            mProgressDialog.show();
            while (mLocation == null) {
                //监视地理位置变化
                count++;
                if (count >= 3) {
                    Toast.makeText(this, "获取位置信失败,请检查权限或开启GPS和网络", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    finish();
                    return;
                }
                //
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, locationListener);
            }

            if (mLocation != null) {
                //不为空,显示地理位置经纬度
                count = 0;
                showLocation(mLocation);
            } else {

            }
        } catch (Exception e) {
            Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            mLocation = location;
        }
    };

    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private void showLocation(Location location) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        String locationStr = "维度：" + location.getLatitude() + "\n"
                + "经度：" + location.getLongitude();
        Log.i("locationStr", "showLocation: " + locationStr);
        //纬度
        mLatitude = location.getLatitude();
        //经度
        mLongitude = location.getLongitude();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.enter_into) { //进入聊天室

            mPwd = mEdit_pwd.getText().toString();
            mName = mEdit_name.getText().toString();
            if (TextUtils.isEmpty(mPwd)) {
                ToastUtil.showLongToast(this, "请设置聊天室密码");
                return;
            } else if (TextUtils.isEmpty(mName)) {
                ToastUtil.showLongToast(this, "请设置聊天室名称");
                return;
            }

            joinRoom();


        } else if (id == R.id.tv_back) { //退出界面
            finish();
        }

    }

    private void joinRoom() {
        Map<Object, Object> header = new HashMap();
        header.put("token", Preferences.getUserMainToken());
        header.put("longitude", mLongitude + "");
        header.put("latitude", mLatitude + "");

        OKhttpHelper.getWithHeader(this, header, Common.AdapterPath + "chat_rooms/code/" + mPwd, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    SearchRoomModel model = new Gson().fromJson(s, new TypeToken<SearchRoomModel>() {
                    }.getType());

                    if (model.getStatus() == 200) {
                        int type = model.getData().getType();
                        if (type == 1) { // 更新自己聊天室的名字和匹配码
                            mRemind.setVisibility(View.VISIBLE);
                            upDateRoom(model.getData().getId());
                        } else if (type == 2) { // 创建聊天室
                            createRoom(model.getData().getChatRooms());
                        } else if (type == 3) { // 加入聊天室
                            join(model.getData().getChatRooms().get(0).getRoomId() + "", model.getData().getChatRooms().get(0).getName(), model.getData().getChatRooms());
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(CreateChatRoomActivity.this, getResources().getString(R.string.net_error));

            }
        });

    }

    private void join(final String id, final String name) {

        Map<Object, Object> header = new HashMap();
        header.put("token", Preferences.getUserMainToken());

        Map<Object, Object> body = new HashMap();

        OKhttpHelper.sendWithHeader(this, header, new Gson().toJson(body), Common.AdapterPath + "chat_room/" + id + "/user", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    JoinRoomModel model = new Gson().fromJson(s, new TypeToken<JoinRoomModel>() {
                    }.getType());

                    if (model.getStatus() == 200) {
                        //加入房间成功
                        //进入聊天室列表
                        ChatRoomActivity.start(CreateChatRoomActivity.this, id, name);
                        finish();
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(CreateChatRoomActivity.this, getResources().getString(R.string.net_error));
            }
        });
    }

    private void join(final String id, final String name, final List<SearchRoomModel.DataBean.ChatRoomsBean> chatRooms) {

        Map<Object, Object> header = new HashMap();
        header.put("token", Preferences.getUserMainToken());

        Map<Object, Object> body = new HashMap();

        OKhttpHelper.sendWithHeader(this, header, new Gson().toJson(body), Common.AdapterPath + "chat_room/" + id + "/user", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    JoinRoomModel model = new Gson().fromJson(s, new TypeToken<JoinRoomModel>() {
                    }.getType());

                    if (model.getStatus() == 200) {
                        //加入房间成功
                        //进入聊天室列表
                        ChatRoomChoiceActivity.startActivity(CreateChatRoomActivity.this, chatRooms);
                        finish();
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(CreateChatRoomActivity.this, getResources().getString(R.string.net_error));
            }
        });
    }

    private void upDateRoom(final int id) {
        Map<Object, Object> header = new HashMap();
        header.put("Content-Type", "application/json");
        header.put("token", Preferences.getUserMainToken());

        Map<Object, Object> body = new HashMap();
        body.put("code", mPwd);
        body.put("name", mName);
        OKhttpHelper.putWithHeader(this, header, new Gson().toJson(body), Common.AdapterPath + "chat_room/" + id, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                join(id + "", mName);

            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(CreateChatRoomActivity.this, getResources().getString(R.string.net_error));

            }
        });

    }

    private void createRoom(final List<SearchRoomModel.DataBean.ChatRoomsBean> chatRooms) {

        Map<Object, Object> header = new HashMap();
        header.put("token", Preferences.getUserMainToken());
        header.put("Content-Type", "application/json");

        Map<Object, Object> body = new HashMap();
        body.put("code", mPwd);
        body.put("name", mName);
        body.put("longitude", mLongitude);
        body.put("latitude", mLatitude);
        OKhttpHelper.sendWithHeader(this, header, new Gson().toJson(body), Common.AdapterPath + "chat_room", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    CreateRoomReturnModel model = new Gson().fromJson(s, new TypeToken<CreateRoomReturnModel>() {
                    }.getType());

                    if (model.getStatus() == 200) {
                        int roomId = model.getData().getRoomId();
                        join(model.getData().getRoomId() + "", mName);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(CreateChatRoomActivity.this, getResources().getString(R.string.net_error));

            }
        });

    }
}
