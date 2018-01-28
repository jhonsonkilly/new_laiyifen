package com.ody.p2p.search.searchresult;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2016/8/22.
 */
public class FilterSelectBean {

    public List<AttrValueId> attributeJson = new ArrayList<>();

    public static class AttrValueId{
        public String attributeId;
        public List<String> attrValueIds = new ArrayList<>();
    }
}
