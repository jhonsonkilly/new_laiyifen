package com.netease.nim.demo.redpacket.model;

import java.io.Serializable;

/**
 * @author SevenCheng
 */

public class RedPacketModel implements Serializable {
    private String amount;
    private String msg;
    private int    count;
    private int    type; //1--普通红包  2--拼手气红包

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
