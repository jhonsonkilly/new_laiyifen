package com.ody.p2p.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxs on 2016/7/20.
 */
public class CollectionBean {

    public String className;
    public List<Collection> classEvent = new ArrayList<>();

    public static class Collection{
        public String name;
        public String type;
        public String id;
        public Frame frame;
    }

    public static class Frame{
        public int x;
        public int y;
        public int width;
        public int heigh;
    }


}
