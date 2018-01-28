package com.netease.nim.demo.collect;

import java.io.Serializable;

/**
 * @author SevenCheng
 */

public class CollectVDModel implements Serializable{
    public long duration;
    public int height;
    public int width;



    public CollectVDModel(long duration, int height, int width) {
        this.duration = duration;
        this.height = height;
        this.width = width;
    }
}
