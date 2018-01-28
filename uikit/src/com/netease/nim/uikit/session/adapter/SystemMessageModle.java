package com.netease.nim.uikit.session.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.MemberPushOption;
import com.netease.nimlib.sdk.msg.model.NIMAntiSpamOption;

import java.util.Map;

/**
 * Created by jasmin on 2017/11/1.
 */

public class SystemMessageModle implements IMMessage,MultiItemEntity {
    String json;
    int type;
    long time;

    public void setTime(long time) {
        this.time = time;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getUuid() {
        return null;
    }

    @Override
    public boolean isTheSame(IMMessage imMessage) {
        return false;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public SessionTypeEnum getSessionType() {
        return null;
    }

    @Override
    public String getFromNick() {
        return null;
    }

    @Override
    public MsgTypeEnum getMsgType() {
        return null;
    }

    @Override
    public MsgStatusEnum getStatus() {
        return null;
    }

    @Override
    public void setStatus(MsgStatusEnum msgStatusEnum) {

    }

    @Override
    public void setDirect(MsgDirectionEnum msgDirectionEnum) {

    }

    @Override
    public MsgDirectionEnum getDirect() {
        return null;
    }

    @Override
    public void setContent(String s) {

    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public void setFromAccount(String s) {

    }

    @Override
    public String getFromAccount() {
        return null;
    }

    @Override
    public void setAttachment(MsgAttachment msgAttachment) {

    }

    @Override
    public MsgAttachment getAttachment() {
        return null;
    }

    @Override
    public AttachStatusEnum getAttachStatus() {
        return null;
    }

    @Override
    public void setAttachStatus(AttachStatusEnum attachStatusEnum) {

    }

    @Override
    public CustomMessageConfig getConfig() {
        return null;
    }

    @Override
    public void setConfig(CustomMessageConfig customMessageConfig) {

    }

    @Override
    public Map<String, Object> getRemoteExtension() {
        return null;
    }

    @Override
    public void setRemoteExtension(Map<String, Object> map) {

    }

    @Override
    public Map<String, Object> getLocalExtension() {
        return null;
    }

    @Override
    public void setLocalExtension(Map<String, Object> map) {

    }

    @Override
    public String getPushContent() {
        return null;
    }

    @Override
    public void setPushContent(String s) {

    }

    @Override
    public Map<String, Object> getPushPayload() {
        return null;
    }

    @Override
    public void setPushPayload(Map<String, Object> map) {

    }

    @Override
    public MemberPushOption getMemberPushOption() {
        return null;
    }

    @Override
    public void setMemberPushOption(MemberPushOption memberPushOption) {

    }

    @Override
    public boolean isRemoteRead() {
        return false;
    }

    @Override
    public int getFromClientType() {
        return 0;
    }

    @Override
    public NIMAntiSpamOption getNIMAntiSpamOption() {
        return null;
    }

    @Override
    public void setNIMAntiSpamOption(NIMAntiSpamOption nimAntiSpamOption) {

    }

    @Override
    public int getItemType() {
        return 0;
    }
}
