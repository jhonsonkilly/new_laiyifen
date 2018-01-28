package com.ody.p2p;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2017/12/4.
 */

public class PolicyBean implements Serializable {


    public String code;

    public List<Result> data;

    public static class Result implements Serializable {
        public String type;
        public String status;
        public String desc;
    }
}
