package com.ody.p2p.retrofit.store;

/**
 * Created by user on 2017/8/7.
 */

public class Commission {

    private String mpId;
    private String salaPrice;

    public Commission(String mpId, String salaPrice) {
        this.mpId = mpId;
        this.salaPrice = salaPrice;
    }

    public String getMpId() {
        return mpId;
    }

    public void setMpId(String mpId) {
        this.mpId = mpId;
    }

    public String getSalaPrice() {
        return salaPrice;
    }

    public void setSalaPrice(String salaPrice) {
        this.salaPrice = salaPrice;
    }
}
