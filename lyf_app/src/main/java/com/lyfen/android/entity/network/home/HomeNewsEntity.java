package com.lyfen.android.entity.network.home;

import java.util.List;

/**
 * Created by qj on 2017/5/8.
 */

public class HomeNewsEntity {

    /**
     * code : 0
     * message : 操作成功
     * errMsg : null
     * data : {"categoryLink":"http://m.laiyifen.com/cms/view/h5/category/code/v2/headlines.html","pageResult":{"listObj":[{"id":1003027700000047,"pageVer":null,"cmsArticleId":1003027700000048,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027700000047.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【电影来啦】逗比天团再度驾到，《银河护卫队2》约吗？","description":"时隔三年，漫威爸爸的银河尬舞队又来啦！这可是被称为宇宙最逗比的五人组呢，当然这部电影的最大卖点也就是逗比~那","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000085,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493870244441_4528_81.jpg","updateTime":1493889933000,"updateTimeStr":"1493889933000","createTime":1493871391000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003027800000000,"pageVer":null,"cmsArticleId":1003027800000001,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027800000000.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"《喜欢你2》剧透","description":"","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000088,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493953873223_9539_37.jpg","updateTime":1493953938000,"updateTimeStr":"1493953938000","createTime":1493953938000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003027100000080,"pageVer":null,"cmsArticleId":1003027100000081,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027100000080.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【任性小长假】人从众？教你新姿势，优雅过五一！","description":"喜大普奔的五一假期就要来啦，OH BABY泥萌准备好了吗~来场说走就走的旅行？可是这样真的好吗~~~&nbs","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000085,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493347196109_5101_80.jpg","updateTime":1493710593000,"updateTimeStr":"1493710593000","createTime":1493347515000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003026900000103,"pageVer":null,"cmsArticleId":1003026900000104,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003026900000103.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【古早味】新品尝鲜 | 百年好核，修成正果","description":"临安，地处浙西天目山区在这里拥有着国内最具规模的野生百年核桃林其坐拥36万株野生山核桃树林中最老的核桃树\u201c树","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003024800000065,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493196194770_663_81.jpg","updateTime":1493372555000,"updateTimeStr":"1493372555000","createTime":1493196322000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003026400000448,"pageVer":null,"cmsArticleId":1003026400000449,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003026400000448.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【还能有点啥】意大利可不只有比萨和意面！","description":"","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003024800000083,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1492765963726_2492_66.jpg","updateTime":1492766490000,"updateTimeStr":"1492766490000","createTime":1492766380000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null}],"total":32,"totalPage":7,"obj":null}}
     * trace : 37!$1#@18%&10!$,149194,null
     */

    public String code;
    public String message;
    public String errMsg;
    public DataEntity data;
    public String trace;

    public static class DataEntity {
        /**
         * categoryLink : http://m.laiyifen.com/cms/view/h5/category/code/v2/headlines.html
         * pageResult : {"listObj":[{"id":1003027700000047,"pageVer":null,"cmsArticleId":1003027700000048,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027700000047.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【电影来啦】逗比天团再度驾到，《银河护卫队2》约吗？","description":"时隔三年，漫威爸爸的银河尬舞队又来啦！这可是被称为宇宙最逗比的五人组呢，当然这部电影的最大卖点也就是逗比~那","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000085,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493870244441_4528_81.jpg","updateTime":1493889933000,"updateTimeStr":"1493889933000","createTime":1493871391000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003027800000000,"pageVer":null,"cmsArticleId":1003027800000001,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027800000000.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"《喜欢你2》剧透","description":"","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000088,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493953873223_9539_37.jpg","updateTime":1493953938000,"updateTimeStr":"1493953938000","createTime":1493953938000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003027100000080,"pageVer":null,"cmsArticleId":1003027100000081,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027100000080.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【任性小长假】人从众？教你新姿势，优雅过五一！","description":"喜大普奔的五一假期就要来啦，OH BABY泥萌准备好了吗~来场说走就走的旅行？可是这样真的好吗~~~&nbs","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000085,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493347196109_5101_80.jpg","updateTime":1493710593000,"updateTimeStr":"1493710593000","createTime":1493347515000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003026900000103,"pageVer":null,"cmsArticleId":1003026900000104,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003026900000103.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【古早味】新品尝鲜 | 百年好核，修成正果","description":"临安，地处浙西天目山区在这里拥有着国内最具规模的野生百年核桃林其坐拥36万株野生山核桃树林中最老的核桃树\u201c树","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003024800000065,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493196194770_663_81.jpg","updateTime":1493372555000,"updateTimeStr":"1493372555000","createTime":1493196322000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003026400000448,"pageVer":null,"cmsArticleId":1003026400000449,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003026400000448.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【还能有点啥】意大利可不只有比萨和意面！","description":"","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003024800000083,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1492765963726_2492_66.jpg","updateTime":1492766490000,"updateTimeStr":"1492766490000","createTime":1492766380000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null}],"total":32,"totalPage":7,"obj":null}
         */

        public String categoryLink;
        public PageResultEntity pageResult;

        public static class PageResultEntity {
            /**
             * listObj : [{"id":1003027700000047,"pageVer":null,"cmsArticleId":1003027700000048,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027700000047.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【电影来啦】逗比天团再度驾到，《银河护卫队2》约吗？","description":"时隔三年，漫威爸爸的银河尬舞队又来啦！这可是被称为宇宙最逗比的五人组呢，当然这部电影的最大卖点也就是逗比~那","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000085,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493870244441_4528_81.jpg","updateTime":1493889933000,"updateTimeStr":"1493889933000","createTime":1493871391000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003027800000000,"pageVer":null,"cmsArticleId":1003027800000001,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027800000000.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"《喜欢你2》剧透","description":"","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000088,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493953873223_9539_37.jpg","updateTime":1493953938000,"updateTimeStr":"1493953938000","createTime":1493953938000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003027100000080,"pageVer":null,"cmsArticleId":1003027100000081,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003027100000080.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【任性小长假】人从众？教你新姿势，优雅过五一！","description":"喜大普奔的五一假期就要来啦，OH BABY泥萌准备好了吗~来场说走就走的旅行？可是这样真的好吗~~~&nbs","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003020801000085,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493347196109_5101_80.jpg","updateTime":1493710593000,"updateTimeStr":"1493710593000","createTime":1493347515000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003026900000103,"pageVer":null,"cmsArticleId":1003026900000104,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003026900000103.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【古早味】新品尝鲜 | 百年好核，修成正果","description":"临安，地处浙西天目山区在这里拥有着国内最具规模的野生百年核桃林其坐拥36万株野生山核桃树林中最老的核桃树\u201c树","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003024800000065,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1493196194770_663_81.jpg","updateTime":1493372555000,"updateTimeStr":"1493372555000","createTime":1493196322000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null},{"id":1003026400000448,"pageVer":null,"cmsArticleId":1003026400000449,"articleLink":"http://m.laiyifen.com/cms/view/h5/article/1003026400000448.html","cmsThemeId":null,"startDate":null,"endDate":null,"name":null,"displayTitle":"【还能有点啥】意大利可不只有比萨和意面！","description":"","articleContent":"","type":null,"sticky":null,"articleType":1,"url":null,"status":null,"categoryId":1003024800000083,"categoryName":null,"categoryCode":null,"categoryType":null,"images":"http://cdn.oudianyun.com/lyf/prod/back-cms/1492765963726_2492_66.jpg","updateTime":1492766490000,"updateTimeStr":"1492766490000","createTime":1492766380000,"companyId":null,"platformStr":null,"showSections":null,"sortValue":null,"templateCss":null,"templateHtml":null,"templateJs":null,"templateVariables":null,"isAvailable":null,"cmsPlatformList":[],"tagIds":[],"articleTags":[],"cmsPageSectionsHeaderVO":null,"cmsPageSectionsTailVO":null,"cmsPageSectionsNavigationVO":null}]
             * total : 32
             * totalPage : 7
             * obj : null
             */

            public int total;
            public int totalPage;
            public String obj;
            public List<ListObjEntity> listObj;

            public static class ListObjEntity {
                /**
                 * id : 1003027700000047
                 * pageVer : null
                 * cmsArticleId : 1003027700000048
                 * articleLink : http://m.laiyifen.com/cms/view/h5/article/1003027700000047.html
                 * cmsThemeId : null
                 * startDate : null
                 * endDate : null
                 *
                 * name : null
                 * displayTitle : 【电影来啦】逗比天团再度驾到，《银河护卫队2》约吗？
                 * description : 时隔三年，漫威爸爸的银河尬舞队又来啦！这可是被称为宇宙最逗比的五人组呢，当然这部电影的最大卖点也就是逗比~那
                 * articleContent :
                 * type : null
                 * sticky : null
                 * articleType : 1
                 * url : null
                 * status : null
                 * categoryId : 1003020801000085
                 * categoryName : null
                 * categoryCode : null
                 * categoryType : null
                 * images : http://cdn.oudianyun.com/lyf/prod/back-cms/1493870244441_4528_81.jpg
                 * updateTime : 1493889933000
                 * updateTimeStr : 1493889933000
                 * createTime : 1493871391000
                 * companyId : null
                 * platformStr : null
                 * showSections : null
                 * sortValue : null
                 * templateCss : null
                 * templateHtml : null
                 * templateJs : null
                 * templateVariables : null
                 * isAvailable : null
                 * cmsPlatformList : []
                 * tagIds : []
                 * articleTags : []
                 * cmsPageSectionsHeaderVO : null
                 * cmsPageSectionsTailVO : null
                 * cmsPageSectionsNavigationVO : null
                 */

                public String id;
                public String pageVer;
                public String cmsArticleId;
                public String articleLink;
                public String cmsThemeId;
                public String startDate;
                public String endDate;
                public String name;
                public String displayTitle;
                public String description;
                public String articleContent;
                public String type;
                public String sticky;
                public String articleType;
                public String url;
                public String status;
                public String categoryId;
                public String categoryName;
                public String categoryCode;
                public String categoryType;
                public String images;
                public String updateTime;
                public String updateTimeStr;
                public String createTime;
                public String companyId;
                public String platformStr;
                public String showSections;
                public String sortValue;
                public String templateCss;
                public String templateHtml;
                public String templateJs;
                public String templateVariables;
                public String isAvailable;
                public String cmsPageSectionsHeaderVO;
                public String cmsPageSectionsTailVO;
                public String cmsPageSectionsNavigationVO;
//                public List<?> cmsPlatformList;
//                public List<?> tagIds;
//                public List<?> articleTags;
            }
        }
    }
}
