package com.ody.p2p.retrofit.home;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by user on 2017/3/16.
 */

public class StockPriceBean extends BaseRequestBean{

    public Data data;


    public static class Data{
        public List<Price> plist;
    }

    public static class Price{
        public String mpId;
        public double price;
        public String promotionPrice;
    }

}
