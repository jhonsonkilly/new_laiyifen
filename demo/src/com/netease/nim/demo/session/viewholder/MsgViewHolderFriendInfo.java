package com.netease.nim.demo.session.viewholder;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.contact.activity.UserProfileActivity;
import com.netease.nim.demo.discount.model.SendYHQModel;
import com.netease.nim.demo.session.extension.SysFriendInfoAttachment;
import com.netease.nim.demo.sync.OKhttpHelper;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.adapter.Type11Model;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.team.activity.AdvancedTeamInfoActivity;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.FriendFieldEnum;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzliuxuanlin on 17/9/15.
 */

public class MsgViewHolderFriendInfo extends MsgViewHolderBase {

    final static int TEAM = 2;
    final static int P2P  = 1;
    boolean     MsgisTeam = false;
    Type11Model dataModel = new Type11Model();
    int         agreeType = 1;
    String account   = "";

    private SysFriendInfoAttachment attachment;
    private TextView title;
    private TextView content;
    private TextView tv_content_end;
    private Button btOk;
    private Button btCancel;
    private String account2;
    private String mGroupId = "";

    @Override
    protected boolean isSystemMessage() {
        return true;
    }


    public MsgViewHolderFriendInfo(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.item_information;
    }

    @Override
    protected void inflateContentView() {
        title = (TextView) view.findViewById(R.id.tv_title);
        content = (TextView) view.findViewById(R.id.tv_content);
        tv_content_end = (TextView) view.findViewById(R.id.tv_content_end);
        btCancel = (Button) view.findViewById(R.id.bt_no);
        btOk = (Button) view.findViewById(R.id.bt_yes);
    }

    //是否显示气泡
    @Override
    protected boolean isShowBubble() {
        return false;
    }

    //是否居中
    @Override
    protected boolean isMiddleItem() {
        return true;
    }


    @Override
    protected void bindContentView() {
        try {
            //imageView.setVisibility(View.GONE);

            if (message.getAttachment() == null) {
                return;
            }

            attachment = (SysFriendInfoAttachment) message.getAttachment();

            String contentStr = attachment.getContent();
            final Type11Model model = new Gson().fromJson(contentStr, new TypeToken<Type11Model>() {
            }.getType());

            setSource(model.getSysFriendInfoToId(),model.getSource());

            title.setText(model.getSysFriendInfoTitle());
            account = model.getSysFriendInfoFormid();
            account2 = model.getSysFriendInfoToId();
            mGroupId = model.getSysFriendInfoGroupId();
            String string = model.getSysFriendInfoMessage();
            String endString = "";
            if (!model.getSysFriendInfoGroupId().equals("")) {
                int i = string.indexOf("<");
                if (i == -1) {

                } else {
                    endString = string.substring(i, string.length());
                }
            }

            if(model.getSysFriendInfoFormName().length()>0){
                string = string.replace(model.getSysFriendInfoFormName(), "<font color='#B2E2F6'>" + model.getSysFriendInfoFormName() + "</font>");
            }

            if(model.getSysFriendInfoToName().length()>0){
                string = string.replace(model.getSysFriendInfoToName(), "<font color='#B2E2F6'>" + model.getSysFriendInfoToName() + "</font>");
            }

            content.setText(Html.fromHtml(string));
            tv_content_end.setText(endString);

            //content.setText(string);
            boolean isTeam = false;

            if (model.getSysFriendInfoGroupId() == null || model.getSysFriendInfoGroupId().equals("")) {
                isTeam = false;
            } else isTeam = true;

            final SharedPreferences sp = context.getSharedPreferences("FriendInfo", Context.MODE_PRIVATE);

            btOk.setText("同意");
            btOk.setVisibility(View.VISIBLE);
            btCancel.setText("拒绝");
            btCancel.setVisibility(View.VISIBLE);
            btOk.setEnabled(true);
            model.setCanEnabled(true);

            int type = sp.getInt(model.getSysFriendInfoValidID(), 0);
            if (type == 1) {
                model.setTag("0");
                /*btCancel.setVisibility(View.GONE);
                btOk.setText("已同意");
                btOk.setEnabled(false);*/
            } else if (sp.getInt(model.getSysFriendInfoValidID(), 0) == 2) {
                model.setTag("1");
                /*btCancel.setVisibility(View.GONE);
                btOk.setText("已拒绝");
                btOk.setEnabled(false);*/
            } else {
                final boolean finalIsTeam = isTeam;
                btOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getSysFriendInfoTitle().equals("入群申请")) {

                            //同意入群申请
                            NIMClient.getService(TeamService.class).passApply(model.getSysFriendInfoGroupId(), model.getSysFriendInfoToId()).setCallback(new RequestCallback<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    addFriend(3, model, true);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putInt(model.getSysFriendInfoValidID(), 1);
                                    editor.commit();
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailed(int i) {

                                }

                                @Override
                                public void onException(Throwable throwable) {

                                }
                            });

                            return;
                        }


                        MsgisTeam = finalIsTeam;
                        dataModel = model;
                        agreeType = 1;
                        //                        addFriend(1, model, finalIsTeam);

                        if (finalIsTeam) {
                            NIMClient.getService(TeamService.class).queryMemberList(model.getSysFriendInfoGroupId()).setCallback(new RequestCallbackWrapper<List<TeamMember>>() {
                                @Override
                                public void onResult(int code, final List<TeamMember> members, Throwable exception) {
                                    boolean isMember = false;
                                    for (TeamMember member : members) {
                                        if (member.getAccount().equals(DemoCache.getAccount())) {
                                            isMember = true;
                                            break;
                                        } else {
                                            isMember = false;
                                        }
                                    }
                                    if (!isMember) {
                                        NIMClient.getService(TeamService.class).acceptInvite(model.getSysFriendInfoGroupId(), model.getSysFriendInfoFormid()).setCallback(callback);
                                        Log.e("----getTargetId", model.getSysFriendInfoGroupId());
                                        Log.e("----getFromAccount", model.getSysFriendInfoFormid());
                                    } else {
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt(model.getSysFriendInfoValidID(), agreeType);
                                        editor.commit();
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });


                        } else {
                            boolean isMyFriend = NIMClient.getService(FriendService.class).isMyFriend(model.getSysFriendInfoFormid());
                            if (!isMyFriend) {
                                NIMClient.getService(FriendService.class).ackAddFriendRequest(model.getSysFriendInfoFormid(), true).setCallback(callback);
                            } else {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt(model.getSysFriendInfoValidID(), agreeType);
                                editor.commit();
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getSysFriendInfoTitle().equals("入群申请")) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt(model.getSysFriendInfoValidID(), 2);
                            editor.commit();
                            adapter.notifyDataSetChanged();

                            //拒绝入群申请
                            NIMClient.getService(TeamService.class).rejectApply(model.getSysFriendInfoGroupId(), model.getSysFriendInfoToId(), "您已被拒绝").setCallback(new RequestCallback<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    addFriend(2, model, true);
                                }

                                @Override
                                public void onFailed(int i) {

                                }

                                @Override
                                public void onException(Throwable throwable) {

                                }
                            });

                            return;
                        }


                        MsgisTeam = finalIsTeam;
                        dataModel = model;
                        agreeType = 2;


                        if (finalIsTeam) {
                            NIMClient.getService(TeamService.class).declineInvite(model.getSysFriendInfoGroupId(), model.getSysFriendInfoFormid(), "").setCallback(callback);
                        } else {
                            NIMClient.getService(FriendService.class).ackAddFriendRequest(model.getSysFriendInfoFormid(), false).setCallback(callback);
                        }
                    }
                });
            }


            String tip = model.getSysFriendInfoIsTip();

            if (model.getTag().equals("-1")) {
                if (tip.equals("0")) {       //有同意拒绝
                    btOk.setVisibility(View.VISIBLE);
                    btCancel.setVisibility(View.VISIBLE);
                } else if (tip.equals("1")) {//没有同意拒绝
                    btOk.setVisibility(View.GONE);
                    btCancel.setVisibility(View.GONE);
                }
            } else if (model.getTag().equals("0")) {
                btCancel.setVisibility(View.GONE);
                btOk.setText("已同意");
                btOk.setEnabled(false);
                model.setCanEnabled(false);
            } else if (model.getTag().equals("1")) {
                btCancel.setVisibility(View.GONE);
                btOk.setText("已拒绝");
                btOk.setEnabled(false);
                model.setCanEnabled(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param getype 1：同意 2：拒绝 3:申请同意
     * @param model
     */

    private void addFriend(final int getype, final Type11Model model, final boolean isTeam) {

        NimUserInfoCache.getInstance().getUserInfoFromRemote(model.getSysFriendInfoFormid(), new RequestCallback<NimUserInfo>() {
            @Override
            public void onSuccess(NimUserInfo nimUserInfo) {
                NimUserInfo self = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());

                Team team = NIMClient.getService(TeamService.class).queryTeamBlock(model.getSysFriendInfoGroupId());
                String formName;
                String toStr = "";
                if (team == null) {

                    formName = model.getSysFriendInfoMessage();
                    if (formName.indexOf("<") != -1 && formName.indexOf(">") != -1) {
                        int indexStart = formName.indexOf("<");

                        int endIndex = formName.indexOf(">");

                        toStr = formName.substring(indexStart + 1, endIndex);
                    }

                    setSource(model.getSysFriendInfoFormid(),model.getSource());

                } else {
                    toStr = team.getName();
                }


                String mobile = "";
                String formPhone = "";
                if (self.getMobile() == null)
                    mobile = "";
                else
                    mobile = self.getMobile();

                if (nimUserInfo.getMobile() == null)
                    formPhone = "";
                else
                    formPhone = nimUserInfo.getMobile();

                Map<String, Object> body = new HashMap<>();
                body.put("sysFriendInfoFormid", model.getSysFriendInfoFormid());
                body.put("sysFriendInfoFormName", model.getSysFriendInfoFormName());
                body.put("sysFriendInfoFormTelephone", formPhone);
                body.put("sysFriendInfoToId", model.getSysFriendInfoToId());
                body.put("sysFriendInfoToName", model.getSysFriendInfoToName());
                body.put("sysFriendInfoToTelephone", mobile);
                body.put("sysFriendInfoGroupId", model.getSysFriendInfoGroupId());
                body.put("sysFriendInfoGroupName", toStr);
                body.put("source", model.getSource());
                body.put("type", getype);

                String json = new Gson().toJson(body);

                OKhttpHelper.send(context, json, Common.AdapterPath + "addFriend", new OKhttpHelper.OnSendSuccessListener() {
                    @Override
                    public void onSendSuccess(String s) {
                        //
                        System.out.println();
                        try {
                            SendYHQModel request = new Gson().fromJson(s, new TypeToken<SendYHQModel>() {
                            }.getType());
                            if (request.getStatus().equals("1")) {


                                //                                if (isTeam) {
                                //                                    if (getype == 1) {
                                //                                        NIMClient.getService(TeamService.class).acceptInvite(model.getSysFriendInfoGroupId(), model.getSysFriendInfoFormid()).setCallback(callback);
                                //                                    } else {
                                //                                        NIMClient.getService(TeamService.class).declineInvite(model.getSysFriendInfoGroupId(),  model.getSysFriendInfoFormid(), "").setCallback(callback);
                                //                                    }
                                //                                } else {
                                //                                    if (getype == 1)
                                //                                        NIMClient.getService(FriendService.class).ackAddFriendRequest(model.getSysFriendInfoFormid(), true).setCallback(callback);
                                //                                    else
                                //                                        NIMClient.getService(FriendService.class).ackAddFriendRequest(model.getSysFriendInfoFormid(), false).setCallback(callback);
                                //
                                //                                }
                            } else {
                                //                                Toast.makeText(context, request.getMsg(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onSendFail(String err) {

                    }
                });
            }

            @Override
            public void onFailed(int i) {
                System.out.println();
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println();
            }
        });


    }

    RequestCallback callback = new RequestCallback<Void>() {
        @Override
        public void onSuccess(Void param) {
            Log.e("callback", param + "");
            addFriend(agreeType, dataModel, MsgisTeam);

            SharedPreferences sp = context.getSharedPreferences("FriendInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(dataModel.getSysFriendInfoValidID(), agreeType);
            editor.commit();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailed(int code) {
            Log.e("callback", code + "");
            ToastUtil.showLongToast(context, "错误" + code);
        }

        @Override
        public void onException(Throwable exception) {

        }
    };


    //    /**
    //     * 发送添加好友信息
    //     *
    //     * @param context
    //     * @param account
    //     */
    //    private static void sendMsg(Context context, String account) {
    //        NimUserInfo userInfoMe = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());
    //        NimUserInfo userInfoOther = NimUserInfoCache.getInstance().getUserInfo(account);
    //        HashMap<Object, Object> map = new HashMap<>();
    //        SendMsgModel sendMsgModel = new SendMsgModel();
    //        sendMsgModel.setSysFriendInfoTitle("加好友提醒");
    //        sendMsgModel.setSysFriendInfoFormid(account);
    //        sendMsgModel.setSysFriendInfoFormName(userInfoMe.getName());
    //        sendMsgModel.setSysFriendInfoMessage(userInfoMe.getName() + "邀请" + userInfoOther.getName() + "加入群" + "<" + team.getName() + ">");
    //        sendMsgModel.setSysFriendInfoToId(account);
    //        sendMsgModel.setSysFriendInfoToName(userInfoOther.getName());
    //        sendMsgModel.setSysFriendInfoIsTip("0");
    //        sendMsgModel.setSysFriendInfoGroupId(team.getId());
    //        map.put("type", 11);
    //        map.put("data", sendMsgModel);
    //
    //        com.netease.nim.uikit.sync.OKhttpHelper.send(context, new Gson().toJson(map), com.netease.nim.uikit.sync.Common.AdapterPath + "sendMsg", new com.netease.nim.uikit.sync.OKhttpHelper.OnSendSuccessListener() {
    //            @Override
    //            public void onSendSuccess(String s) {
    //
    //            }
    //
    //            @Override
    //            public void onSendFail(String err) {
    //
    //            }
    //        });
    //    }


    @Override
    protected void onItemClick() {
        super.onItemClick();

        if (!mGroupId.equals("")) {
            AdvancedTeamInfoActivity.start(context, mGroupId);
        } else {


            if (account.equals(Preferences.getUserAccount())) {

                UserProfileActivity.start(context, account2, "系统消息添加");
            } else {
                UserProfileActivity.start(context, account, "系统消息添加");

            }
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
}
