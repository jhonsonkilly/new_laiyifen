package com.ody.p2p.search.searchresult;

import android.widget.LinearLayout;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by lxs on 2016/12/26.
 */
public class CartExtBean extends BaseRequestBean{

    public Data data;

    public static class Data{
        public String needMoney;
        public String description;
        public int hasSelectedGifts;
        public int isReachCondition;
        public String message;
        public double totalPrice;
        public int type;
        public List<GiftProduct> gifts;
    }

    public static class GiftProduct{
        public int canSaleNum;
        public int checkNum;
        public int checked;
        public String giftName;
        public long merchantId;
        public long mpId;
        public String picUrl;
        public double price;
        public long promotionId;
        public double weight;
    }
}
