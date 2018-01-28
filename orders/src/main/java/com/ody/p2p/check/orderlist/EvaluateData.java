package com.ody.p2p.check.orderlist;

import java.util.List;

/**
 * Created by ody on 2016/8/29.
 */
public class EvaluateData {
    //mpId	Long	商品id
    //mpName	String	商品名称
    //mpPicPath	String	商品图片url
    //orderId	Long	订单id
    //orderCode	String	订单编号
    //rate	Integer	商品评分（星级）
    //content	String	评价内容

    private long mpId;
    private String mpName;
    private String mpPicPath;
    private long orderId;
    private String orderCode;
    private int rate;
    private String content;
    private List<String> mpcPicList;//商品晒图url（string）
    private String userUserName ; //用户名

    public List<String> getMpcPicList() {
        return mpcPicList;
    }

    public void setMpcPicList(List<String> mpcPicList) {
        this.mpcPicList = mpcPicList;
    }

    public String getUserUserName() {
        return userUserName;
    }

    public void setUserUserName(String userUserName) {
        this.userUserName = userUserName;
    }

    public long getMpId() {
        return mpId;
    }

    public void setMpId(long mpId) {
        this.mpId = mpId;
    }

    public String getMpName() {
        return mpName;
    }

    public void setMpName(String mpName) {
        this.mpName = mpName;
    }

    public String getMpPicPath() {
        return mpPicPath;
    }

    public void setMpPicPath(String mpPicPath) {
        this.mpPicPath = mpPicPath;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
