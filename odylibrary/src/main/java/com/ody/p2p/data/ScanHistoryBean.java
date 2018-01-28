package com.ody.p2p.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lxs on 2016/7/15.
 */
@DatabaseTable(tableName = "history_search")
public class ScanHistoryBean {
    /*设置成主键*/
    @DatabaseField(columnName = "_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "spId")
    private String spId;
    @DatabaseField(columnName = "cost")
    private String cost;
    @DatabaseField(columnName = "url")
    private String url;
    @DatabaseField(columnName = "spName")
    private String spName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }
}
