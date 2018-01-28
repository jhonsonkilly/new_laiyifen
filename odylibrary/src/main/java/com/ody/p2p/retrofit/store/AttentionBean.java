package com.ody.p2p.retrofit.store;

/**
 * Created by user on 2017/7/17.
 */

public class AttentionBean {


    /**
     * favoriteId : 0
     * isFavorite : 0
     * code : 0
     * message : success
     * data : null
     * totalCount : null
     * pageSize : null
     * totalPage : null
     */

    private long favoriteId;
    private int isFavorite;
    private String code;
    private String message;
    private Object data;
    private Object totalCount;
    private Object pageSize;
    private Object totalPage;

    public long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Object totalCount) {
        this.totalCount = totalCount;
    }

    public Object getPageSize() {
        return pageSize;
    }

    public void setPageSize(Object pageSize) {
        this.pageSize = pageSize;
    }

    public Object getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Object totalPage) {
        this.totalPage = totalPage;
    }
}
