package com.ody.p2p.search.searchkey;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by ody on 2016/5/31.
 * 搜索关键字联想 bean
 */
public class SearchKeywordListBean extends BaseRequestBean {

    /**
     * errMsg : null
     * data : [{"keyword":"哈得斯","count":200,"attachedInfo":null},{"keyword":"哈文迪 HEAVENLY","count":200,"attachedInfo":null},{"keyword":"哈斯其","count":200,"attachedInfo":null},{"keyword":"哈根达斯 Haagen-Dazs","count":200,"attachedInfo":null},{"keyword":"哈瓦纳","count":200,"attachedInfo":null},{"keyword":"曼哈迪","count":200,"attachedInfo":null},{"keyword":"哈根达斯冰淇淋","count":17,"attachedInfo":null},{"keyword":"哈根达斯392g","count":10,"attachedInfo":null},{"keyword":"haagen-dazs哈根达斯","count":8,"attachedInfo":null},{"keyword":"两杯哈根达斯","count":8,"attachedInfo":null}]
     * trace : 123!$1#@168%&192!$,74652,61762629050063872263442
     */

    public Object errMsg;
    public String trace;
    /**
     * keyword : 哈得斯
     * count : 200
     * attachedInfo : null
     */

    public List<Data> data;

    public static class Data {
        public String keyword;
        public int count;
        public Object attachedInfo;
    }
}
