package com.netease.nim.demo.groupsend.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by jasmin on 2017/11/8.
 */

public class GroupSendModel implements Serializable,MultiItemEntity{
    private String teamId;
    private String name;
    private String iconUrl;
    private boolean isSelect;
    private String p2pAccount;
    private int itemType;
    private String sendDate;

    public GroupSendModel(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getP2pAccount() {
        return p2pAccount;
    }

    public void setP2pAccount(String p2pAccount) {
        this.p2pAccount = p2pAccount;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
