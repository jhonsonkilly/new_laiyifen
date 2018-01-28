package com.netease.nim.demo.chatroom.model;

import java.util.List;

/**
 * @author SevenCheng
 */

public class ChatRoomNameModel {

    /**
     * status : 200
     * message : null
     * data : ["滚滚滚"]
     */

    private int status;
    private Object message;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
