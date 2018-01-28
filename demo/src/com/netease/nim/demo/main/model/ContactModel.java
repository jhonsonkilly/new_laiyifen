package com.netease.nim.demo.main.model;

import java.util.List;

/**
 * @author SevenCheng
 */

public class ContactModel {


    private List<StrangersBean> strangers;
    private List<OutsidersBean> outsiders;
    private List<FriendsBean>   friends;
    private String   batch_invite_status;
    private String   code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBatch_invite_status() {
        return batch_invite_status;
    }

    public void setBatch_invite_status(String batch_invite_status) {
        this.batch_invite_status = batch_invite_status;
    }

    public List<StrangersBean> getStrangers() {
        return strangers;
    }

    public void setStrangers(List<StrangersBean> strangers) {
        this.strangers = strangers;
    }

    public List<OutsidersBean> getOutsiders() {
        return outsiders;
    }

    public void setOutsiders(List<OutsidersBean> outsiders) {
        this.outsiders = outsiders;
    }

    public List<FriendsBean> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsBean> friends) {
        this.friends = friends;
    }

    public static class StrangersBean {
        /**
         * name : 二姨
         * mobile : 186 1673 1152
         */

        private String name;
        private String mobile;
        private String icon;
        private String accid;
        private String status;

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class OutsidersBean {
        /**
         * name : 二姨
         * mobile : 186 1673 1152
         */

        private String name;
        private String mobile;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public static class FriendsBean {
        /**
         * name : 房益明
         * mobile : 15921513808
         * icon : https://nos.netease.com/nim/NDM4NjI0Mw==/bmltYV85NzU2MzgyOTJfMTUwODkwNTA1NDMzOV9kNzNiMTZmZC0yNDI5LTRmMTUtYjNlMi05NWViZmFjMzA1N2M=
         */

        private String name;
        private String mobile;
        private String icon;
        private String accid;
        private String status;
        private int    type;
        private boolean hasTitle = false;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public boolean isHasTitle() {
            return hasTitle;
        }

        public void setHasTitle(boolean hasTitle) {
            this.hasTitle = hasTitle;
        }

        public int getType() {
            return type;
        }

        /**
         * 0--好友 1--未注册 2--陌生人
         *
         * @param type
         */
        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
