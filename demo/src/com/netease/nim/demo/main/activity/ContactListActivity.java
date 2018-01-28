package com.netease.nim.demo.main.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.main.adapter.SortAdapter;
import com.netease.nim.demo.main.model.ContactModel;
import com.netease.nim.demo.main.model.ContactsInfoModel;
import com.netease.nim.demo.main.model.InterfaceModel;
import com.netease.nim.demo.main.model.SortModel;
import com.netease.nim.demo.main.util.Cn2Spell;
import com.netease.nim.demo.main.util.SaveFile;
import com.netease.nim.demo.main.widget.PinYinKit;
import com.netease.nim.demo.main.widget.PinyinComparator;
import com.netease.nim.demo.main.widget.SearchEditText;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.demo.yidiancard.widget.IOSDialog;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.session.module.SendMsgModel;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.PermissionUtils;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by jasmin on 17/1/24.
 */
@RuntimePermissions
public class ContactListActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private SharedPreferences preferences;
    public PinyinComparator comparator = new PinyinComparator();
    private View view;
    private TextView userListNumTxt;
    //    private SideBar         sideBar;
    private ListView sortListView;
    private SearchEditText mSearchEditText;
    private SortAdapter adapter;
    private ProgressDialog progress;
    private List<SortModel> sortModelList;
    private ArrayList<ContactsInfoModel> list = new ArrayList<ContactsInfoModel>();
    private AlertDialog mDialog;
    private Button mButton;
    private List<ContactModel.OutsidersBean> mOutsiders;
    private EditText mSearch;
    private ContactModel mModel;
    private boolean mShieldingInvited = true;
    private int     num               = 0;
    private int mSize;
    private String source;

    public static void start(Context context, String way) {
        Intent intent = new Intent();
        intent.setClass(context, ContactListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("way",way);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBarTitle("添加联系人");
        tv_right.setText("邀请");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_right.getText().equals("取消")) {
                    adapter.showCb(false);
                    tv_right.setText("邀请");
                    return;
                }

                inviteList.clear();
                for (ContactModel.FriendsBean friendsBean : mFriendsBeen) {
                    if (friendsBean.isChecked()) {
                        inviteList.add(friendsBean);
                    }
                }
                if (inviteList.size() == 0) {

                    adapter.showCb(true);
                    sortListView.setSelection(mSize);
                    tv_right.setText("取消");
                } else {
                    invite();
                }

            }
        });
        preferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        source = getIntent().getStringExtra("way");
        findView();
        ContactListActivityPermissionsDispatcher.showContactsWithPermissionCheck(this);

        setBarTitle("我的通讯录");
        // on touching listener of side bar
        /*sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            public void onTouchingLetterChanged(String str) {
                int position = adapter.getPositionForSection(str.charAt(0));
                if (position != -1)
                    sortListView.setSelection(position);
            }
        });*/


        sortListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //                Intent intent = getIntent();
                //                intent.putExtra("phone", sortModelList.get(position).getMobile());//添加要返回给页面1的数据
                //                intent.putExtra("name", sortModelList.get(position).getName());//添加要返回给页面1的数据
                //                setResult(ContactListActivity.RESULT_OK, intent);//返回页面1
                //                finish();
            }
        });

        // filter
        mSearchEditText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence str, int arg1, int arg2, int arg3) {
                try {
                    filerData(str.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
            }
        });

        //        setContacts();

        //        getList();
        String interfaceStr = SaveFile.read(this, "INTERFACE001");
        if (interfaceStr != null)
            initResponse(interfaceStr);

    }

    private void invite() {
        final IOSDialog dialog = new IOSDialog(ContactListActivity.this, R.style.customDialog, R.layout.dialog_invite_friend);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView content = (TextView) dialog.findViewById(R.id.tv_content);
        final EditText msg = (EditText) dialog.findViewById(R.id.et_message);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);

        msg.setText("我是" + NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount()).getName());
        content.setVisibility(View.INVISIBLE);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doInviteAllFriend(msg.getText().toString());
                dialog.dismiss();

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private List<ContactModel.FriendsBean> inviteList = new ArrayList<>();

    public void findView() {
        this.userListNumTxt = (TextView) findViewById(R.id.txt_user_list_user_num);
        //        sideBar = (SideBar) findViewById(R.id.sild_bar);
        //        sideBar.setmTextDialog(dialogTxt);
        sortListView = (ListView) findViewById(R.id.list_view_user_list);
        // search
        mSearchEditText = (SearchEditText) findViewById(R.id.txt_filter_edit);


        //        mButton = (Button) findViewById(R.id.bt_invite_all);
        /*mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteList.clear();


                if (inviteList.size() == 0) {
                    ToastUtil.showLongToast(ContactListActivity.this, "请选择需要邀请的好友");
                    return;
                }

                final IOSDialog dialog = new IOSDialog(ContactListActivity.this, R.style.customDialog, R.layout.dialog_invite_friend);
                dialog.show();
                TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
                TextView content = (TextView) dialog.findViewById(R.id.tv_content);
                final EditText msg = (EditText) dialog.findViewById(R.id.et_message);
                TextView tvOk = (TextView) dialog.findViewById(R.id.ok);

                msg.setText("我是" + NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount()).getName());
                content.setVisibility(View.INVISIBLE);
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doInviteAllFriend(msg.getText().toString());
                        dialog.dismiss();

                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });*/

        mSearch = (EditText) findViewById(R.id.et_search);

        mSearch.addTextChangedListener(new TextWatcher() {
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
                    adapter.setNewdata(mFriendsBeen);
                    return;
                }

                //搜索关键词
                List<ContactModel.FriendsBean> mSearchList = new ArrayList();
                for (ContactModel.FriendsBean friendsBean : mFriendsBeen) {
                    if (friendsBean.getMobile().contains(content) || friendsBean.getName().contains(content)) {
                        mSearchList.add(friendsBean);
                    } else {

                        if (Cn2Spell.getPinYin(friendsBean.getName()).contains(content)) {
                            mSearchList.add(friendsBean);
                        } else if (Cn2Spell.getPinYinHeadChar(friendsBean.getName()).contains(content)) {
                            mSearchList.add(friendsBean);
                        }
                    }

                }

                adapter.setNewdata(mSearchList);

            }
        });
    }

    @Override
    public int getResourceId() {
        return R.layout.activity_contactlist;
    }


    private void filerData(String str) throws BadHanyuPinyinOutputFormatCombination {
        List<ContactModel.FriendsBean> fSortModels = new ArrayList<>();

        if (TextUtils.isEmpty(str))
            fSortModels = mFriendsBeen;
        else {
            fSortModels.clear();
            for (ContactModel.FriendsBean sortModel : mFriendsBeen) {
                String name = sortModel.getName();
                if (name.indexOf(str.toString()) != -1 ||
                        PinYinKit.getPingYin(name).startsWith(str.toString()) || PinYinKit.getPingYin(name).startsWith(str.toUpperCase().toString())) {
                    fSortModels.add(sortModel);
                }
            }

        }
        //        Collections.sort(fSortModels, comparator);
        adapter.updateListView(fSortModels, mShieldingInvited);
    }

    @NeedsPermission({Manifest.permission.READ_CONTACTS})
    void showContacts() {
        // NOTE: Perform action that requires the permission.
        // If this is run by PermissionsDispatcher, the permission will have been granted
        setContacts();
    }

    @OnPermissionDenied({Manifest.permission.READ_CONTACTS})
    void doReadContactSPermissionDenied() {
        showHintDialog();
    }

    private void showHintDialog() {
        final IOSDialog dialog = new IOSDialog(this, R.style.customDialog, R.layout.dialog_contact_permission);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView content = (TextView) dialog.findViewById(R.id.content);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);

        content.setText("请允许打开本应用的通讯录权限,若取消则无法进行下一步");

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 300);

                dialog.dismiss();

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    public void setContacts() {
        //        ContactListActivityPermissionsDispatcher.showContactsWithCheck(this);

        sortModelList = new ArrayList<SortModel>();
        String jsonString = SaveFile.read(this, "contacts");
        if (jsonString == null || jsonString.equals("null")) {

            getList();
            if (list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {

                    SortModel sortModel = new SortModel();
                    sortModel.setMobile(list.get(i).getNumber());
                    sortModel.setName(list.get(i).getName());

                    //汉字转换成拼音
                    String pinyin = null;
                    try {
                        pinyin = PinYinKit.getPingYin(list.get(i).getName());
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                    String sortString = pinyin.substring(0, 1).toUpperCase();

                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")) {
                        sortModel.setSortLetters(sortString.toUpperCase());
                    } else {
                        sortModel.setSortLetters("#");
                    }


                    sortModelList.add(sortModel);
                    // sort by a-z

                }
            }

        } else {
            try {
                Log.i("test", jsonString);
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    SortModel sortModel = new SortModel();
                    String mobile = object.get("getNumber") + "";
                    mobile = mobile.replace("\n", "");
                    mobile = mobile.replace(" ", "");
                    mobile = mobile.replace("+86", "");
                    mobile = mobile.replace("-", "");
                    sortModel.setMobile(mobile);
                    sortModel.setName(object.get("getName") + "");

                    //汉字转换成拼音
                    String pinyin = null;
                    try {
                        pinyin = PinYinKit.getPingYin(object.get("getName") + "");
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                    String sortString = pinyin.substring(0, 1).toUpperCase();

                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")) {
                        sortModel.setSortLetters(sortString.toUpperCase());
                    } else {
                        sortModel.setSortLetters("#");
                    }
                    sortModelList.add(sortModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //        Collections.sort(sortModelList, comparator);


        InInterface002();
        checkFriends();


        adapter = new SortAdapter(this, mFriendsBeen, false, num);
        adapter.setOnInviteClickListener(new SortAdapter.OnInviteClickListener() {
            @Override
            public void onInvite(final ContactModel.FriendsBean mContent) {  //邀请用户

                final IOSDialog dialog = new IOSDialog(ContactListActivity.this, R.style.customDialog, R.layout.dialog_invite_friend);
                dialog.show();
                TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
                TextView content = (TextView) dialog.findViewById(R.id.tv_content);
                final EditText msg = (EditText) dialog.findViewById(R.id.et_message);
                TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
                NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());
                msg.setText(userInfo.getName());
                content.setText(mContent.getName());
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doInviteFriend(mContent, msg.getText().toString());
                        dialog.dismiss();

                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onaddFriend(String account) { //添加为好友
                //                UserProfileActivity.start(ContactListActivity.this, num);
                doAddFriend(account);
            }

            @Override
            public void onSelectChange(String tag) {

                switch (tag) {
                    case "0":
                        num--;

                        break;

                    case "1":
                        num++;
                        break;
                }

                if (num == 0) {
                    tv_right_num.setText("");
                    tv_right.setText("取消");
                } else {
                    tv_right.setText("发送");
                    tv_right_num.setText("(" + num + ")");
                }
                adapter.setNewNum(num);
            }
        });
        sortListView.setAdapter(adapter);

        /*String interfaceStr = SaveFile.read(this, "INTERFACE001");
        if (interfaceStr != null)
            initResponse(interfaceStr);*/
    }

    /**
     * 是否开启邀请
     */
    private void InInterface002() {

        OKhttpHelper.send(this, "", Common.AdapterPath + "INTERFACE002", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                InterfaceModel model = new Gson().fromJson(s, new TypeToken<InterfaceModel>() {
                }.getType());

                if (model.getCode().equals("200")) {
                    mShieldingInvited = model.isShieldingInvited();
                    if (mShieldingInvited) {
                        //                        mButton.setVisibility(View.GONE);
                    } else {
                        //                        mButton.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onSendFail(String err) {
            }
        });
    }


    private static final String[] IPPFXS4 = {"1790", "1791", "1793", "1795",
            "1796", "1797", "1799"};
    private static final String[] IPPFXS5 = {"12583", "12593", "12589",
            "12520", "10193", "11808"};
    private static final String[] IPPFXS6 = {"118321"};


    /**
     * 消除电话号码中 可能含有的 IP号码、+86、0086等前缀
     *
     * @param telNum
     * @return
     */
    public static String trimTelNum(String telNum) {

        if (telNum == null || "".equals(telNum)) {
            System.out.println("trimTelNum is null");
            return null;
        }

        String ippfx6 = substring(telNum, 0, 6);
        String ippfx5 = substring(telNum, 0, 5);
        String ippfx4 = substring(telNum, 0, 4);

        if (telNum.length() > 7
                && (substring(telNum, 5, 1).equals("0")
                || substring(telNum, 5, 1).equals("1") || substring(
                telNum, 5, 3).equals("400") || substring(
                telNum, 5, 3).equals("+86"))
                && (inArray(ippfx5, IPPFXS5) || inArray(ippfx4, IPPFXS4)))
            telNum = substring(telNum, 5);
        else if (telNum.length() > 8
                && (substring(telNum, 6, 1).equals("0")
                || substring(telNum, 6, 1).equals("1") || substring(
                telNum, 6, 3).equals("400") || substring(
                telNum, 6, 3).equals("+86"))
                && inArray(ippfx6, IPPFXS6))
            telNum = substring(telNum, 6);
        // remove ip dial

        telNum = telNum.replace("-", "");
        telNum = telNum.replace(" ", "");

        if (substring(telNum, 0, 4).equals("0086"))
            telNum = substring(telNum, 4);
        else if (substring(telNum, 0, 3).equals("+86"))
            telNum = substring(telNum, 3);
        else if (substring(telNum, 0, 5).equals("00186"))
            telNum = substring(telNum, 5);

        return telNum;
    }

    /**
     * 判断一个字符串是否在一个字符串数组中
     *
     * @param target
     * @param arr
     * @return
     */
    protected static boolean inArray(String target, String[] arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        if (target == null) {
            return false;
        }
        for (String s : arr) {
            if (target.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 截取字符串
     *
     * @param s
     * @param from
     * @return
     */
    protected static String substring(String s, int from) {
        try {
            return s.substring(from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static String substring(String s, int from, int len) {
        try {
            return s.substring(from, from + len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private void doInviteFriend(ContactModel.FriendsBean mContent, String name) {
        NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());

        Map<Object, Object> body = new HashMap();
        body.put("token", Preferences.getUserMainToken());
        body.put("name", name + "(" + userInfo.getMobile() + ")");
        ArrayList<String> mobiles = new ArrayList<>();
        if (mContent.getMobile().length() >= 11) {
            String mobile = mContent.getMobile();
            String substring = mobile.substring(mobile.length() - 11, mobile.length());
            boolean matches = substring.matches("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|145|147|149|173|175|176|177|178)+\\d{8})$");
            if (matches) {
                mobiles.add(substring);
            }

        }
        if (mobiles.size() == 0) {
            ToastUtil.showLongToast(ContactListActivity.this, "手机号码格式不正确");
            return;
        }
        body.put("userMobiles", mobiles);

        OKhttpHelper.send(this, new Gson().toJson(body), Common.AdapterPath + "/address_book/friends_invitation", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                ToastUtil.showLongToast(ContactListActivity.this, "发送邀请成功");
                checkFriends();
            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(ContactListActivity.this, "发送邀请失败");
            }
        });

    }


    /**
     * 批量群发邀请
     *
     * @param
     */
    private void doInviteAllFriend(String msg) {
        Map<Object, Object> body = new HashMap();
        body.put("token", Preferences.getUserMainToken());
        body.put("name", msg);
        ArrayList<String> mobiles = new ArrayList<>();

        for (ContactModel.FriendsBean friendsBean : inviteList) {
            if (friendsBean.getMobile().length() >= 11) {
                String mobile = friendsBean.getMobile();
                String substring = mobile.substring(mobile.length() - 11, mobile.length());
                boolean matches = substring.matches("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|145|147|149|173|175|176|177|178)+\\d{8})$");
                if (matches) {
                    mobiles.add(substring);
                }
            }
        }
        if (mobiles.size() == 0) {
            ToastUtil.showLongToast(ContactListActivity.this, "手机号码格式不正确");
            return;
        }
        body.put("userMobiles", mobiles);

        OKhttpHelper.send(this, new Gson().toJson(body), Common.AdapterPath + "/address_book/friends_invitation", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {

                ToastUtil.showLongToast(ContactListActivity.this, "发送邀请成功");
                checkFriends();
                adapter.showCb(false);
                tv_right.setText("邀请");
                tv_right_num.setText("");

            }

            @Override
            public void onSendFail(String err) {
                ToastUtil.showLongToast(ContactListActivity.this, "发送邀请失败");

            }
        });

    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 查询通讯录中是否有注册用户
     */
    private void checkFriends() {
        /*String body = SyncRequestReport.getInstance(this).ReportINTERFACE001(sortModelList);
        SyncPostClient syncPostClient = new SyncPostClient(Common.INTERFACE001, body, new SyncClientListener() {
            @Override
            public void onDownloadSyncSuccess(String result) {

            }

            @Override
            public void onDownloadSyncFail(String Exception) {

            }
        });
        //        syncPostClient.ShowProgressDialog(this);
        SyncClientManager.getInstance().executeSyncClient(syncPostClient);*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.customDialog);

        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_dialog, null));
        mDialog = builder.create();


        String interfaceStr = SaveFile.read(this, "INTERFACE001");
        if (interfaceStr == null)
            mDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Response response = null;

                    Map<String, Object> body = new HashMap<>();
                    ArrayList<Map<String, Object>> maps = new ArrayList<>();
                    for (SortModel sortModel : sortModelList) {
                        Map<String, Object> body2 = new HashMap<>();
                        String mobile = sortModel.getMobile().replace(" ", "").replace("-", "");
                        if (mobile.length() >= 11) {
                            String substring = mobile.substring(mobile.length() - 11, mobile.length());
                            boolean matches = substring.matches("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|145|147|149|173|175|176|177|178)+\\d{8})$");
                            if (matches) {
                                body2.put("name", sortModel.getName());
                                body2.put("mobile", mobile);
                                maps.add(body2);
                            }
                        }

                    }

                    body.put("accid", DemoCache.getAccount());
                    body.put("all_mail", maps);

                    Log.e("body", new Gson().toJson(body));
                    RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(body));
                    Request request = new Request.Builder()
                            .url(Common.AdapterPath + "INTERFACE001")
                            .post(requestBody)
                            .build();
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        final String string = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initResponse(string);
                                /*try {
                                    SaveFile.write(string, "INTERFACE001");
                                } catch (FileNotFoundException e) {

                                }*/
                                Log.e("下载内容", string);
                                mDialog.dismiss();
                            }
                        });
                    } else {
                        Log.e("下载失败", response.message());
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private List<ContactModel.FriendsBean> mFriendsBeen = new ArrayList<>();

    private void initResponse(String result) {

        try {
            mFriendsBeen.clear();
            mModel = new Gson().fromJson(result, new TypeToken<ContactModel>() {
            }.getType());
            if (mModel.getCode().equals("0")) {

                //批量邀请
                if (mModel.getBatch_invite_status().equals("0")) {
                    tv_right.setVisibility(View.VISIBLE);
                } else {
                    tv_right.setVisibility(View.GONE);
                }

                List<ContactModel.FriendsBean> friends = mModel.getFriends();
                mOutsiders = mModel.getOutsiders();
                List<ContactModel.StrangersBean> strangers = mModel.getStrangers();
                if (friends != null) {
                    for (ContactModel.FriendsBean friend : friends) {
                        ContactModel.FriendsBean friendsBean = new ContactModel.FriendsBean();
                        friendsBean.setIcon(friend.getIcon());
                        friendsBean.setMobile(friend.getMobile());
                        friendsBean.setName(friend.getName());
                        friendsBean.setAccid(friend.getAccid());
                        if (friends.indexOf(friend) == 0) {
                            friendsBean.setHasTitle(true);
                        }
                        friendsBean.setType(0);
                        mFriendsBeen.add(friendsBean);
                    }
                }


                if (strangers != null) {
                    for (ContactModel.StrangersBean stranger : strangers) {
                        ContactModel.FriendsBean friendsBean = new ContactModel.FriendsBean();
                        friendsBean.setIcon("");
                        friendsBean.setMobile(stranger.getMobile());
                        friendsBean.setName(stranger.getName());
                        friendsBean.setIcon(stranger.getIcon());
                        friendsBean.setAccid(stranger.getAccid());
                        friendsBean.setStatus(stranger.getStatus());
                        if (strangers.indexOf(stranger) == 0) {
                            friendsBean.setHasTitle(true);
                        }
                        friendsBean.setType(2);
                        mFriendsBeen.add(friendsBean);
                    }
                }

                if (mOutsiders != null) {
                    mSize = mFriendsBeen.size();
                    for (ContactModel.OutsidersBean outsider : mOutsiders) {
                        ContactModel.FriendsBean friendsBean = new ContactModel.FriendsBean();
                        friendsBean.setIcon("");
                        friendsBean.setMobile(outsider.getMobile());
                        friendsBean.setName(outsider.getName());
                        friendsBean.setStatus(outsider.getStatus());
                        if (mOutsiders.indexOf(outsider) == 0) {
                            friendsBean.setHasTitle(true);
                        }
                        friendsBean.setType(1);
                        mFriendsBeen.add(friendsBean);
                    }
                }

                adapter.updateListView(mFriendsBeen, mShieldingInvited);
            } else {
                ToastUtil.showLongToast(ContactListActivity.this, "网络错误");
            }
        } catch (Exception e) {

        }


    }

    //获取通讯录
    public void getList() {
        try {
            list = new ArrayList<ContactsInfoModel>();
            Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = this.getContentResolver().query(contactUri,
                    new String[]{"display_name", "sort_key", "contact_id", "data1"},
                    null, null, "sort_key");
            String contactName;
            String contactNumber;
            String contactSortKey;
            int contactId;
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                contactSortKey = getSortkey(cursor.getString(1));
                ContactsInfoModel contactsInfo = new ContactsInfoModel(contactName, contactNumber, contactSortKey, contactId);

                //                getData1(this.getContentResolver(),contactId+"",ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//联系人名称
                //                getData1(this.getContentResolver(),contactId+"",ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);//联系人电话
                //                getData1(this.getContentResolver(),contactId+"",ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);//邮件
                //                getData1(this.getContentResolver(),contactId+"",ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE);//公司
                //                getData1(this.getContentResolver(),contactId+"",ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);//备注
                //                getData1(this.getContentResolver(),contactId+"",ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);//地址
                if (contactName != null)
                    list.add(contactsInfo);
            }

            cursor.close();//使用完后一定要将cursor关闭，不然会造成内存泄露等问题

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getSortkey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        } else
            return "#";   //获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
    }

    /**
     * 根据MIMETYPE类型, 返回对应联系人的data1字段的数据
     */
    private String getData1(final ContentResolver contentResolver, String contactId, final String mimeType) {
        StringBuilder stringBuilder = new StringBuilder();

        Cursor dataCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data.DATA1},
                ContactsContract.Data.CONTACT_ID + "=?" + " AND "
                        + ContactsContract.Data.MIMETYPE + "='" + mimeType + "'",
                new String[]{String.valueOf(contactId)}, null);
        if (dataCursor != null && dataCursor.getCount() > 0) {
            if (dataCursor.moveToFirst()) {
                do {
                    stringBuilder.append(dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DATA1)));
                    stringBuilder.append("_");//多个值,之间的分隔符.可以自定义;
                } while (dataCursor.moveToNext());
            }
            dataCursor.close();
        }
        Log.i("test", stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!PermissionUtils.hasSelfPermissions(this, "android.permission.READ_CONTACTS")) {
            doReadContactSPermissionDenied();
            return;
        }
        if (PermissionUtils.verifyPermissions(grantResults)) {
            showContacts();
        }
    }

    private void doAddFriend(final String account) {
        if (!NetworkUtil.isNetAvailable(this)) {
            Toast.makeText(ContactListActivity.this, R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(account) && account.equals(DemoCache.getAccount())) {
            Toast.makeText(ContactListActivity.this, "不能加自己为好友", Toast.LENGTH_SHORT).show();
            return;
        }
        final VerifyType verifyType = VerifyType.VERIFY_REQUEST;
        //        DialogMaker.showProgressDialog(this, "", true);
        addIMFriend(account);
        //        NIMClient.getService(FriendService.class).addFriend(new AddFriendData(account, verifyType, ""))
        //                .setCallback(new RequestCallback<Void>() {
        //                    @Override
        //                    public void onSuccess(Void param) {
        //
        //                        DialogMaker.dismissProgressDialog();
        //                        //                        updateUserOperatorView();
        //                        if (VerifyType.DIRECT_ADD == verifyType) {
        //                            Toast.makeText(ContactListActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
        //                            sendMsg(account);
        //                        } else {
        //                            Toast.makeText(ContactListActivity.this, "添加好友请求发送成功", Toast.LENGTH_SHORT).show();
        //                            sendMsg(account);
        //                        }
        //                    }
        //
        //                    @Override
        //                    public void onFailed(int code) {
        //                        DialogMaker.dismissProgressDialog();
        //                        if (code == 408) {
        //                            Toast.makeText(ContactListActivity.this, R.string.network_is_not_available, Toast
        //                                    .LENGTH_SHORT).show();
        //                        } else {
        //                            Toast.makeText(ContactListActivity.this, "on failed:" + code, Toast
        //                                    .LENGTH_SHORT).show();
        //                        }
        //                    }
        //
        //                    @Override
        //                    public void onException(Throwable exception) {
        //                        DialogMaker.dismissProgressDialog();
        //                    }
        //                });

    }

    /**
     * 发送添加好友信息
     *
     * @param account
     */
    private void sendMsg(final String account) {
        final NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());
        NimUserInfoCache.getInstance().getUserInfoFromRemote(account, new RequestCallback<NimUserInfo>() {
            @Override
            public void onSuccess(NimUserInfo nimUserInfo) {
                HashMap<Object, Object> map = new HashMap<>();
                SendMsgModel sendMsgModel = new SendMsgModel();
                sendMsgModel.setSysFriendInfoTitle("新好友提醒");
                sendMsgModel.setSysFriendInfoFormid(DemoCache.getAccount());
                sendMsgModel.setSysFriendInfoFormName(userInfoMe.getName());
                sendMsgModel.setSysFriendInfoMessage(userInfoMe.getName() + "申请加您为好友");
                sendMsgModel.setSysFriendInfoToId(account);
                sendMsgModel.setSysFriendInfoToName(nimUserInfo.getName());
                sendMsgModel.setSysFriendInfoIsTip("0");
                sendMsgModel.setSysFriendInfoGroupId("");
                sendMsgModel.setSource(source);
                map.put("type", 11);
                map.put("data", sendMsgModel);
                DialogMaker.dismissProgressDialog();
                OKhttpHelper.send(ContactListActivity.this, new Gson().toJson(map), com.netease.nim.uikit.sync.Common.AdapterPath + "sendMsg", new OKhttpHelper.OnSendSuccessListener() {
                    @Override
                    public void onSendSuccess(String s) {
                        Toast.makeText(ContactListActivity.this, "添加好友请求发送成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSendFail(String err) {

                    }
                });
            }

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            ContactListActivityPermissionsDispatcher.showContactsWithPermissionCheck(this);
        }
    }


    private void addIMFriend(final String accid) {
        Map<String, Object> body = new HashMap<>();
        body.put("faccid", accid);
        com.netease.nim.demo.sync.OKhttpHelper.send(this, new Gson().toJson(body), Common.AdapterPath + "addIMFriend", new com.netease.nim.demo.sync.OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    String code = object.getString("code");
                    if (code.equals("200")) {
                        sendMsg(accid);
                    } else {
                        DialogMaker.dismissProgressDialog();
                        Toast.makeText(ContactListActivity.this, "添加好友失败", Toast.LENGTH_LONG);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }


}
