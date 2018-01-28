package com.netease.nim.demo.session.viewholder;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.discount.model.SendYHQModel;
import com.netease.nim.demo.discount.model.YHQModel;
import com.netease.nim.demo.main.util.TimeUtil;
import com.netease.nim.demo.session.extension.YiDianCardAttachment;
import com.netease.nim.demo.session.model.Type12Model;
import com.netease.nim.demo.sync.OKhttpHelper;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.demo.yidiancard.model.YKD001Model;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderYiDianCard extends MsgViewHolderBase {

    private YiDianCardAttachment attachment;
    private TextView             title;
    private TextView             type;
    private TextView             date;
    private ImageView            imageView;
    private FrameLayout          background;
    Type12Model model;

    public MsgViewHolderYiDianCard(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.custom_yidiancard;
    }


    @Override
    protected boolean isSystemMessage() {
        return false;
    }


    @Override
    protected void inflateContentView() {
        //        imageView = (ImageView) view.findViewById(R.id.image_view);
        title = (TextView) view.findViewById(R.id.tv_title);
        date = (TextView) view.findViewById(R.id.tv_date);
        type = (TextView) view.findViewById(R.id.tv_type);
        imageView = (ImageView) view.findViewById(R.id.imageview);
        background = (FrameLayout) findViewById(R.id.background);
    }

    @Override
    protected void bindContentView() {
        background.getBackground().setAlpha(255);
        SharedPreferences sp;

        attachment = (YiDianCardAttachment) message.getAttachment();
        String contentStr = attachment.getContent();

        try {
            //            imageView.setVisibility(View.GONE);
            if (message.getAttachment() == null) {
                return;
            }

            if (attachment.getBean() != null) {
                YKD001Model.RowsBean bean = attachment.getBean();
                title.setText("伊点卡");
                sp = context.getSharedPreferences("YDK", Context.MODE_PRIVATE);

                title.setText(bean.getIntro());
                if (sp.getBoolean(bean.getCardNo(), false)) {
                    type.setText("已领取");
                    background.getBackground().setAlpha(200);
                } else
                    type.setText(bean.getStatus());
                date.setText("有效期 " + TimeUtil.getStrTime(bean.getEndTime() + "000"));
            } else {
                contentStr = contentStr.replace("\\", "");
                contentStr = contentStr.replace("\"{", "{");
                contentStr = contentStr.replace("}\"", "}");
                model = new Gson().fromJson(contentStr, new TypeToken<Type12Model>() {
                }.getType());
                //                    String typeStr = model.getDiscount().getStatusType();
                sp = context.getSharedPreferences("YDK", Context.MODE_PRIVATE);
                //                    title.setText("伊点卡");

                title.setText(model.getYidou().getIntro());
                if (sp.getBoolean(model.getYidou().getCardNo(), false)) {
                    type.setText("已领取");
                    background.getBackground().setAlpha(200);
                } else
                    type.setText(model.getYidou().getStatus());
                date.setText("有效期 " + TimeUtil.getStrTime(model.getYidou().getEndTime() + "000"));
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected boolean isShowMessageBackground() {
        return false;
    }

    @Override
    protected void onItemClick() {

        super.onItemClick();
        SharedPreferences sp = context.getSharedPreferences("YDK", Context.MODE_PRIVATE);

        try {
            adapter.notifyDataSetChanged();
            attachment = (YiDianCardAttachment) message.getAttachment();
            String contentStr = attachment.getContent();
            String content = attachment.getBeanJson();
            YKD001Model.RowsBean bean = attachment.getBean();
            if (bean != null) {
                if (sp.getBoolean(bean.getCardNo(), false)) {
                    Toast.makeText(context, "伊点卡已被领取", Toast.LENGTH_LONG).show();
                    return;
                }
                getYDK(0, bean, null);
            } else {
                contentStr = contentStr.replace("\\", "");
                contentStr = contentStr.replace("\"{", "{");
                contentStr = contentStr.replace("}\"", "}");
                model = new Gson().fromJson(contentStr, new TypeToken<Type12Model>() {
                }.getType());
                if (sp.getBoolean(model.getYidou().getCardNo(), false)) {
                    Toast.makeText(context, "伊点卡已被领取", Toast.LENGTH_LONG).show();
                    return;
                }
                getYDK(0, model.getYidou(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param getype 判断是优惠券还是伊点卡 0：伊点卡 1：优惠券
     * @param data
     */
    private void getYDK(final int getype, final YKD001Model.RowsBean data, final YHQModel.DataBean.CanUseCouponListBean dataYHQ) {
        NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());

        final String mobile = userInfo.getMobile();
        if (mobile == null) {
            Toast.makeText(context, "无手机号码", Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> body = new HashMap<>();
        String type = "";
        if (data != null) {
            body.put("recMobile", mobile);
            body.put("ycardNo", data.getCardNo());
            body.put("memberId", data.getMemberId());
            type = "YDK003";
        } else {
            body.put("recMobile", mobile);
            body.put("ycardNo", dataYHQ.getCouponCode());
            body.put("memberId", dataYHQ.getMemberId());
            type = "YHQ003";
        }


        OKhttpHelper.send(context, new Gson().toJson(body), Common.AdapterPath + type, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                SendYHQModel model = new Gson().fromJson(s, new TypeToken<SendYHQModel>() {
                }.getType());
                if (model.getMsg().equals("操作成功")) {
                    SharedPreferences sp = context.getSharedPreferences("YDK", Context.MODE_PRIVATE);
                    if (model.getStatus().equals("1")) {

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(data.getCardNo(), true);
                        editor.commit();
                        adapter.notifyDataSetChanged();

                        IMMessage imessage = MessageBuilder.createTipMessage(data.getSendUserId(), SessionTypeEnum.P2P);
                        Map<String, Object> content = new HashMap<>(1);
                        imessage.setContent(NimUserInfoCache.getInstance().getUserDisplayName(DemoCache.getAccount()) + "领取了" + "您的伊点卡");
                        NIMClient.getService(MsgService.class).sendMessage(imessage, false);
                        NIMClient.getService(MsgService.class).deleteChattingHistory(imessage);


                        imessage = MessageBuilder.createTipMessage(message.getSessionId(), SessionTypeEnum.P2P);
                        content = new HashMap<>(1);
                        content.put("content", "您领取了" + NimUserInfoCache.getInstance().getUserDisplayName(message.getFromAccount()) + "的伊点卡");
                        imessage.setRemoteExtension(content);
                        // 自定义消息配置选项
                        CustomMessageConfig config = new CustomMessageConfig();
                        // 消息不计入未读
                        config.enableUnreadCount = false;
                        imessage.setConfig(config);
                        // 消息发送状态设置为success
                        imessage.setStatus(MsgStatusEnum.success);
                        // 保存消息到本地数据库，但不发送到服务器
                        NIMClient.getService(MsgService.class).saveMessageToLocal(imessage, true);
                    } else {
                        Log.e("yidianka", s);
                        Toast.makeText(context, model.getMsg(), Toast.LENGTH_LONG).show();
                        if (model.getMsg().indexOf("已领取") != -1) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean(data.getCardNo(), true);
                            editor.commit();
                            adapter.notifyDataSetChanged();
                        }
                        //                            IMMessage imessage = MessageBuilder.createTipMessage(message.getSessionId(), SessionTypeEnum.P2P);
                        //                            Map<String, Object> content = new HashMap<>(1);
                        //                            String messageStr = model.getMsg();
                        //                            content.put("content", messageStr);
                        //                            imessage.setRemoteExtension(content);
                        //                            // 自定义消息配置选项
                        //                            CustomMessageConfig config = new CustomMessageConfig();
                        //                            // 消息不计入未读
                        //                            config.enableUnreadCount = false;
                        //                            imessage.setConfig(config);
                        //                            // 消息发送状态设置为success
                        //                            imessage.setStatus(MsgStatusEnum.success);
                        //                            // 保存消息到本地数据库，但不发送到服务器
                        //                            NIMClient.getService(MsgService.class).saveMessageToLocal(imessage, true);
                    }

                } else {
                    ToastUtil.showLongToast(context, model.getMsg());
                }
            }

            @Override
            public void onSendFail(String err) {

            }
        });
    }


}


