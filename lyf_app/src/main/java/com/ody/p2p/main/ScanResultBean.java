package com.ody.p2p.main;

import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.search.searchresult.popupwindow.ResultBean;

import java.util.List;

/**
 * Created by lxs on 2017/2/8.
 */
public class ScanResultBean extends BaseRequestBean{

    public Data data;

    public static class Data{
        public String visitLink;
        public String mpId;
        public List<ResultBean.ProductBean> productList;
    }
}
