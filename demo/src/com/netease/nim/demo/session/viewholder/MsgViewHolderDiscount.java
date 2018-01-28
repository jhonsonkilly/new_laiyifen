package com.netease.nim.demo.session.viewholder;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.discount.model.YHQModel;
import com.netease.nim.demo.main.util.TimeUtil;
import com.netease.nim.demo.session.extension.DiscountAttachment;
import com.netease.nim.demo.session.model.Type13Model;
import com.netease.nim.demo.sync.OKhttpHelper;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
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
public class MsgViewHolderDiscount extends MsgViewHolderBase {

    private DiscountAttachment attachment;
    private TextView title;
    private TextView type;
    private TextView date;
    private ImageView imageView;
    private FrameLayout background;
    Type13Model model;

    public MsgViewHolderDiscount(BaseMultiItemFetchLoadAdapter adapter) {
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
        title = (TextView)view.findViewById(R.id.tv_title);
        date = (TextView)view.findViewById(R.id.tv_date);
        type = (TextView)view.findViewById(R.id.tv_type);
        background = (FrameLayout)findViewById(R.id.background);
        imageView = (ImageView)findViewById(R.id.imageview);
    }

    @Override
    protected void bindContentView() {
        background.getBackground().setAlpha(255);
        SharedPreferences sp =context.getSharedPreferences("YHQ", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        if (message.getAttachment() == null) {
            return;
        }

        try{
//            imageView.setVisibility(View.GONE);
            type.setText("未领取");
            attachment = (DiscountAttachment) message.getAttachment();
            String contentStr = attachment.getContent();
            if (contentStr == null){
                YHQModel.DataBean.CanUseCouponListBean bean = new Gson().fromJson(attachment.getBeanJson(), new TypeToken<YHQModel.DataBean.CanUseCouponListBean>() {
                }.getType());
                title.setText("优惠券");
                Glide.with(context).load(bean.getIconUrl()).into(imageView);
                if (sp.getBoolean(bean.getCouponCode(),false)){
                    type.setText("已领取");
                    background.getBackground().setAlpha(200);
                }
                String endTiem = bean.getEndTime()+"";
                if (endTiem.length() <13){
                    endTiem = endTiem+"000";
                }

                date.setText("有效期 "+ TimeUtil.getStrTime(endTiem));
            }else{

//                contentStr = contentStr.replace("\\","");
//                contentStr = contentStr.replace("\"{","{");
//                contentStr = contentStr.replace("}\"","}");
                Log.e("model",contentStr);
//            Type13Model model = new Gson().fromJson(contentStr, new TypeToken<Type13Model>() {
//            }.getType());
                JSONObject object = JSON.parseObject(contentStr);
                String yidouStr = object.getString("yidou");
                JSONObject data = JSON.parseObject(yidouStr);
                title.setText("优惠券");
                Glide.with(context).load(data.getString("iconUrl")).into(imageView);
                if (sp.getBoolean(data.getString("couponCode"),false)){
                    type.setText("已领取");
                    background.getBackground().setAlpha(200);
                }else
                    type.setText("未领取");
                String endTiem = data.getString("endTime")+"";
                if (endTiem.length() <13){
                    endTiem = endTiem+"000";
                }
                date.setText("有效期 "+ TimeUtil.getStrTime(endTiem));
            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    @Override
    protected boolean isShowMessageBackground() {
        return false;
    }

    @Override
    protected void onItemClick() {

        super.onItemClick();
        SharedPreferences sp =context.getSharedPreferences("YHQ", Context.MODE_PRIVATE);
        attachment = (DiscountAttachment) message.getAttachment();
        String contentStr = attachment.getContent();
        if (contentStr==null){
            YHQModel.DataBean.CanUseCouponListBean bean = new Gson().fromJson(attachment.getBeanJson(), new TypeToken<YHQModel.DataBean.CanUseCouponListBean>() {
            }.getType());
            if (sp.getBoolean(bean.getCouponCode(),false)){
                Toast.makeText(context,"优惠券已被领取",Toast.LENGTH_LONG).show();
                return;
            }
            getYDK(bean.getCouponCode(),bean.getMemberId(),bean.getType()+"",bean.getCouponReceiveId(),bean.getSendUserId());
        }else {
            contentStr = contentStr.replace("\\","");
            contentStr = contentStr.replace("\"{","{");
            contentStr = contentStr.replace("}\"","}");

            Log.e("model",contentStr);
//            Type13Model model = new Gson().fromJson(contentStr, new TypeToken<Type13Model>() {
//            }.getType());
            JSONObject object = JSON.parseObject(contentStr);
            JSONObject data = object.getJSONObject("yidou");
            if (sp.getBoolean(data.getString("couponCode"),false)){
                Toast.makeText(context,"优惠券已被领取",Toast.LENGTH_LONG).show();
                return;
            }
            getYDK(data.getString("couponCode"),data.get("memberId")+"",data.get("type")+"",data.getString("couponReceiveId"),data.getString("sendUserId"));
        }
    }

    /**
     * 领取优惠券
     * @param code 卡号
     */
    private void getYDK(final String code, String memberId, String typeStr, String couponId, final String sendId) {
        NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(DemoCache.getAccount());

        final String mobile = userInfo.getMobile();
        if (mobile ==null){
            Toast.makeText(context,"无手机号码",Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> body = new HashMap<>();
            body.put("mobile", mobile);
            body.put("cardNo", code);
            body.put("memberId",memberId);
            body.put("type",typeStr);
            body.put("couponId",couponId);


        OKhttpHelper.send(context, new Gson().toJson(body), Common.AdapterPath + "YHQ003", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                //
                System.out.println();
                try {
                    SharedPreferences sp =context.getSharedPreferences("YHQ", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();

                    JSONObject object = JSON.parseObject(s);
                    String codeNo = object.getString("code");
                    String messageStr = object.getString("message");
                    if (codeNo.equals("0")){
                        Toast.makeText(context,"领取成功",Toast.LENGTH_LONG).show();
                        editor.putBoolean(code, true);
                        editor.commit();
                        adapter.notifyDataSetChanged();

                        IMMessage imessage = MessageBuilder.createTipMessage(sendId, SessionTypeEnum.P2P);
                        Map<String, Object> content = new HashMap<>(1);
                        imessage.setContent(NimUserInfoCache.getInstance().getUserDisplayName(DemoCache.getAccount())+"领取了"+"您的优惠券");
                        NIMClient.getService(MsgService.class).sendMessage(imessage, false);
                        NIMClient.getService(MsgService.class).deleteChattingHistory(imessage);


                        imessage = MessageBuilder.createTipMessage(message.getSessionId(), SessionTypeEnum.P2P);
                        content = new HashMap<>(1);
                        content.put("content", "您领取了"+ NimUserInfoCache.getInstance().getUserDisplayName(message.getFromAccount())+"的优惠券");
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

                    }else {
//                        Toast.makeText(context,messageStr,Toast.LENGTH_LONG).show();
                        if (messageStr.indexOf("已被领取") != -1){
                            editor.putBoolean(code, true);
                            editor.commit();
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSendFail(String err) {
                IMMessage imessage = MessageBuilder.createTipMessage(message.getSessionId(), SessionTypeEnum.P2P);
                Map<String, Object> content = new HashMap<>(1);
                content.put("content", "网络异常，请稍后重试");
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
            }
        });

    }
}


