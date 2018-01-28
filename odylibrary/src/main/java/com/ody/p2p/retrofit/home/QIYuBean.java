package com.ody.p2p.retrofit.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2017/11/29.
 */

public class QIYuBean implements Serializable {

    public String code;

    public QIYuBeanData data;

    public static class QIYuBeanData implements Serializable {

        public String uid;
        public List<InnData> data;
        public List<GroupIdList> groupIdList;

        public static class GroupIdList implements Serializable {
            @SerializedName("0")
            public int type1;
            @SerializedName("1")
            public int type2;
            @SerializedName("2")
            public int type3;
            @SerializedName("3")
            public int type4;


        }

        public static class InnData implements Serializable {

            public String index;

            public String value;
            public String label;
            public String href;
            public String type;
            public String key;

        }

    }

}
