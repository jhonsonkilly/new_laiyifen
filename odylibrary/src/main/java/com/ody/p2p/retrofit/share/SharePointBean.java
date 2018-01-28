package com.ody.p2p.retrofit.share;

import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by lxs on 2017/2/21.
 */
public class SharePointBean extends BaseRequestBean{

    public Data data;

    public static class Data{
        public String amount;
        public String code;
        public String deadline;
        public String message;
    }
}
