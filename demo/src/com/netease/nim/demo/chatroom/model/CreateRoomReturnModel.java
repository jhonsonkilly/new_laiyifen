package com.netease.nim.demo.chatroom.model;

/**
 * @author SevenCheng
 */

public class CreateRoomReturnModel {

    /**
     * status : 200
     * message : null
     * data : {"roomId":21050723}
     */

    private int status;
    private Object message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * roomId : 21050723
         */

        private int roomId;

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }
    }
}
