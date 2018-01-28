package com.netease.nim.demo.session.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author SevenCheng
 */

public class QuerySwitchModel implements Serializable {

    /**
     * errMsg :
     * switchData : [{"code":"QZ","createTime":1515642039000,"name":"新建群组","id":2,"type":1,"status":1},{"code":"QF","createTime":1515642039000,"name":"群发消息","id":3,"type":1,"status":1},{"code":"DDH","createTime":1515642039000,"name":"创建多人电话","id":4,"type":1,"status":2},{"code":"DSP","createTime":1515642039000,"name":"创建多人视频","id":5,"type":1,"status":2},{"code":"CZHY","createTime":1515642039000,"name":"查找好友","id":6,"type":1,"status":2},{"code":"QR","createTime":1515642039000,"name":"扫一扫","id":7,"type":1,"status":2},{"code":"HB1","createTime":1515642039000,"name":"红包（一对一）","id":8,"type":1,"status":1},{"code":"HB2","createTime":1515642039000,"name":"红包（群）","id":9,"type":1,"status":1},{"code":"HB3","createTime":1515642039000,"name":"红包（聊天室）","id":10,"type":1,"status":1},{"code":"YDK","createTime":1515642039000,"name":"伊点卡","id":11,"type":1,"status":2},{"code":"YHQ","createTime":1515642039000,"name":"优惠券","id":12,"type":1,"status":1},{"code":"YD","createTime":1515642039000,"name":"伊豆","id":13,"type":1,"status":1},{"code":"SP1","createTime":1515642039000,"name":"视频聊天（一对一）","id":14,"type":1,"status":1},{"code":"SP2","createTime":1515642039000,"name":"视频聊天（群）","id":15,"type":1,"status":1},{"code":"SP3","createTime":1515642039000,"name":"视频聊天（聊天室）","id":16,"type":1,"status":1},{"code":"YY1","createTime":1515642039000,"name":"语音聊天（一对一）","id":17,"type":1,"status":1},{"code":"YY2","createTime":1515642039000,"name":"语音聊天（群）","id":18,"type":1,"status":1},{"code":"YY3","createTime":1515642039000,"name":"语音聊天（聊天室）","id":19,"type":1,"status":1},{"code":"MP","createTime":1515642039000,"name":"名片","id":20,"type":1,"status":1},{"code":"SC","createTime":1515642039000,"name":"我的收藏","id":21,"type":1,"status":1},{"code":"XC1","createTime":1515642039000,"name":"相册（一对一）","id":22,"type":1,"status":1},{"code":"XC2","createTime":1515642039000,"name":"相册（群）","id":23,"type":1,"status":1},{"code":"XC3","createTime":1515642039000,"name":"相册（聊天室）","id":24,"type":1,"status":1},{"code":"PS1","createTime":1515642039000,"name":"拍摄（一对一）","id":25,"type":1,"status":1},{"code":"PS1","createTime":1515642039000,"name":"拍摄（群）","id":26,"type":1,"status":1},{"code":"PS1","createTime":1515642039000,"name":"拍摄（聊天室）","id":27,"type":1,"status":1}]
     * message : 操作成功
     * version : 20180111112355
     * status : 1
     */

    private String errMsg;
    private String message;
    private String version;
    private String status;
    private List<SwitchDataBean> switchData;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SwitchDataBean> getSwitchData() {
        return switchData;
    }

    public void setSwitchData(List<SwitchDataBean> switchData) {
        this.switchData = switchData;
    }

    public static class SwitchDataBean implements Serializable {
        /**
         * code : QZ
         * createTime : 1515642039000
         * name : 新建群组
         * id : 2
         * type : 1
         * status : 1
         */

        private String code;
        private long   createTime;
        private String name;
        private int    id;
        private int    type;
        private int    status;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
