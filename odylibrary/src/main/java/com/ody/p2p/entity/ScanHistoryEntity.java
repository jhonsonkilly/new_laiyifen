package com.ody.p2p.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lxs on 2016/12/12.
 */
@Entity
public class ScanHistoryEntity {

    /*设置成主键*/
    @Id(autoincrement = true)
    private Long _id;
    private String spId;
    private String cost;
    private String url;
    private String spName;
    @Generated(hash = 891979150)
    public ScanHistoryEntity(Long _id, String spId, String cost, String url,
            String spName) {
        this._id = _id;
        this.spId = spId;
        this.cost = cost;
        this.url = url;
        this.spName = spName;
    }
    @Generated(hash = 351592646)
    public ScanHistoryEntity() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getSpId() {
        return this.spId;
    }
    public void setSpId(String spId) {
        this.spId = spId;
    }
    public String getCost() {
        return this.cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getSpName() {
        return this.spName;
    }
    public void setSpName(String spName) {
        this.spName = spName;
    }
}
