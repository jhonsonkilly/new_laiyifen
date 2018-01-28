package com.ody.p2p.search.searchkey;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by lxs on 2016/10/13.
 */
public class HistoryBean extends BaseRequestBean{

    public Data data;

    public static class Data{
        List<History>  data;
        public int totalCount;
        public int totalPage;
    }
    public static class History{
        public String name;
        public List<FootStepVO> values;
    }

    public static class FootStepVO{

        public String yearOrMonthOrDay;
        public String picUrl;
        public String mpId;
        public String price;
        public String name;
        public int shelvesStatus;
        public boolean choose;

    }

}
