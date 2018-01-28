package com.ody.p2p.views.scrollbanner;

import com.ody.p2p.base.BaseRequestBean;

import java.util.List;

/**
 * Created by lxs on 2016/8/26.
 */
public class HeadLinesBean extends BaseRequestBean {

    public Data data;

    public static class Data {
        public int total;
        public int totalPage;
        public PageResult pageResult;
        public String categoryLink;
    }


    public static class PageResult{
        public List<CmsPageArticleVO> listObj;
    }


    public static class CmsPageArticleVO {
        public long id;
        public long cmsArticleId;
        public String articleLink;
        public String displayTitle;
        public String description;
        public String articleContent;
        public String categoryName;
        public String categoryType;
        public String categoryCode;
        public String images;
        public String createTime;
        public long companyId;
    }
}
