package com.lyfen.android.entity.network.home;

/**
 * Created by qj on 2017/5/3.
 */

public enum HomeEnum {
    TYPE_HEAD("ad_header", 1000, "头部轮播图"),
    TYPE_CHANNEL("ad_channel", 1001, "频道区分"),

    TYPE_ENTRANCE("ad_entrance", 1002, "头部轮播图"),
    TYPE_NEWS("news", 1003, "广告热点"),

    TYPE_SECKILL("secondKill", 1004, "来抢购等4张图片"),
    TYPE_H5("h5_multipic", 1005, "H5混合"),

    TYPE_PAVILION("slideShow", 1006, "来抢购等4张图片"),
    TYPE_RANK("rank", 1007, "来抢购等4张图片"),

    TYPE_CATEGORY("ad_category", 1008, "来抢购等4张图片"),
    TYPE_PRODUCT("goods", 1009, "底部商品列表"),

    TYPE_SLIDER("slider", 1010, "来抢购等4张图片"),
    TYPE_SPACE("spacing", 1011, "空白填充部分"),

    TYPE_GOODS("product", 1012, "来抢购等4张图片");


    HomeEnum(String ad_header, int i, String name) {

    }



    private String type;
    private int index;

    private HomeEnum(String name, int index) {
        this.type = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (HomeEnum c : HomeEnum.values()) {
            if (c.getIndex() == index) {
                return c.type;
            }
        }
        return null;
    }
    public String getName() {
        return type;
    }
    public void setName(String name) {
        this.type = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }


}
