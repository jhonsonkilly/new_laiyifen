package com.ody.p2p.check.orderlist;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/8/29.
 */
public class EvaluateBean extends BaseRequestBean {

    private List<EvaluateData> data;

    public List<EvaluateData> getData() {
        return data;
    }

    public void setData(List<EvaluateData> data) {
        this.data = data;
    }
}
