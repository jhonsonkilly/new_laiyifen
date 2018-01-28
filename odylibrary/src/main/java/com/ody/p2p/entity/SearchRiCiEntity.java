package com.ody.p2p.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lxs on 2016/12/12.
 */
@Entity
public class SearchRiCiEntity {

    @Id(autoincrement = true)
    private Long _id;
    private String riCiName;
    @Generated(hash = 2062058696)
    public SearchRiCiEntity(Long _id, String riCiName) {
        this._id = _id;
        this.riCiName = riCiName;
    }
    @Generated(hash = 2049941188)
    public SearchRiCiEntity() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getRiCiName() {
        return this.riCiName;
    }
    public void setRiCiName(String riCiName) {
        this.riCiName = riCiName;
    }


    @Override
    public String toString() {
        return riCiName;
    }
}
