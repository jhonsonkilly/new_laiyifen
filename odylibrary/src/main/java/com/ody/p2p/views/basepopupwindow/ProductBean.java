package com.ody.p2p.views.basepopupwindow;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ody on 2016/6/20.
 */
public class ProductBean implements Serializable {

    /**
     * productId : 1123
     * mpId : 1253
     * mpsId : 1253
     * code : 19910333JQH44
     * name : 海棠花01
     * ramark : 辅助信息，4.7英寸显示屏，后置摄像头
     * ziying : http://img.25pp.com/uploadfile/soft/images/2014/0816/20140816093121980.jpg
     * subTitle : null
     * type : 1
     * price : 0.01
     * tax : 0
     * stockNum : 71
     * lackOfStock : 0
     * brandId : 328
     * brandName : 花品牌01
     * categoryId : 1254
     * promotionType : null
     * promotionPrice : null
     * preferentialPrice : 0.01
     * promotionId : null
     * promotionIconUrl : null
     * categoryTreeNodeId : 1278
     * merchantSeriesId : 223
     * companyId : 1
     * merchantId : 100
     * merchantName : null
     * merchantType : null
     * freightAttribute : null
     * grossWeight : null
     * merchantProdVolume : null
     * shopId : null
     * shopName : null
     * shopType : null
     * freightTemplateId : null
     * warehouseNo : null
     * calculationUnit : null
     * standard : null
     * saleType : 2
     * saleIconUrl : null
     * mpSource : null
     * isBargain : null
     * isRent : 0
     * status : 4
     * managementState : 1
     * h5DetailUrl : http://www.cheuks.com/api/product/desc?mpId=1253&platform=1
     * pcDetailUrl : http://www.cheuks.com/api/product/desc?mpId=1253&platform=4
     * pics : [{"name":"海棠花5.png","url":"http://img10.360buyimg.com/n1/jfs/t910/142/160555319/141007/57405505/550651efN146a6b23.jpg","url60x60":"http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=160&w=160&m=0","url100x100":"http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=100&w=100&m=0"},{"name":"海棠花4.png","url":"http://img10.360buyimg.com/n1/jfs/t910/142/160555319/141007/57405505/550651efN146a6b23.jpg","url60x60":"http://yihe.kssws.ks-cdn.com/back_product/1463482288599_38.48810576048497_%E6%B5%B7%E6%A3%A0%E8%8A%B14.png@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://yihe.kssws.ks-cdn.com/back_product/1463482288599_38.48810576048497_%E6%B5%B7%E6%A3%A0%E8%8A%B14.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://yihe.kssws.ks-cdn.com/back_product/1463482288599_38.48810576048497_%E6%B5%B7%E6%A3%A0%E8%8A%B14.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://yihe.kssws.ks-cdn.com/back_product/1463482288599_38.48810576048497_%E6%B5%B7%E6%A3%A0%E8%8A%B14.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://yihe.kssws.ks-cdn.com/back_product/1463482288599_38.48810576048497_%E6%B5%B7%E6%A3%A0%E8%8A%B14.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://yihe.kssws.ks-cdn.com/back_product/1463482288599_38.48810576048497_%E6%B5%B7%E6%A3%A0%E8%8A%B14.png@base@tag=imgScale&h=160&w=160&m=0","url100x100":"http://yihe.kssws.ks-cdn.com/back_product/1463482288599_38.48810576048497_%E6%B5%B7%E6%A3%A0%E8%8A%B14.png@base@tag=imgScale&h=100&w=100&m=0"},{"name":"海棠花2.png","url":"http://img10.360buyimg.com/n1/jfs/t910/142/160555319/141007/57405505/550651efN146a6b23.jpg","url60x60":"http://yihe.kssws.ks-cdn.com/back_product/1463482288592_56.670119885005064_%E6%B5%B7%E6%A3%A0%E8%8A%B12.png@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://yihe.kssws.ks-cdn.com/back_product/1463482288592_56.670119885005064_%E6%B5%B7%E6%A3%A0%E8%8A%B12.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://yihe.kssws.ks-cdn.com/back_product/1463482288592_56.670119885005064_%E6%B5%B7%E6%A3%A0%E8%8A%B12.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://yihe.kssws.ks-cdn.com/back_product/1463482288592_56.670119885005064_%E6%B5%B7%E6%A3%A0%E8%8A%B12.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://yihe.kssws.ks-cdn.com/back_product/1463482288592_56.670119885005064_%E6%B5%B7%E6%A3%A0%E8%8A%B12.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://yihe.kssws.ks-cdn.com/back_product/1463482288592_56.670119885005064_%E6%B5%B7%E6%A3%A0%E8%8A%B12.png@base@tag=imgScale&h=160&w=160&m=0","url100x100":"http://yihe.kssws.ks-cdn.com/back_product/1463482288592_56.670119885005064_%E6%B5%B7%E6%A3%A0%E8%8A%B12.png@base@tag=imgScale&h=100&w=100&m=0"},{"name":"海棠花6.png","url":"http://img10.360buyimg.com/n1/jfs/t910/142/160555319/141007/57405505/550651efN146a6b23.jpg","url60x60":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_57.47938673641302_%E6%B5%B7%E6%A3%A0%E8%8A%B16.png@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_57.47938673641302_%E6%B5%B7%E6%A3%A0%E8%8A%B16.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_57.47938673641302_%E6%B5%B7%E6%A3%A0%E8%8A%B16.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_57.47938673641302_%E6%B5%B7%E6%A3%A0%E8%8A%B16.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_57.47938673641302_%E6%B5%B7%E6%A3%A0%E8%8A%B16.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_57.47938673641302_%E6%B5%B7%E6%A3%A0%E8%8A%B16.png@base@tag=imgScale&h=160&w=160&m=0","url100x100":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_57.47938673641302_%E6%B5%B7%E6%A3%A0%E8%8A%B16.png@base@tag=imgScale&h=100&w=100&m=0"},{"name":"海棠花3.png","url":"http://img10.360buyimg.com/n1/jfs/t910/142/160555319/141007/57405505/550651efN146a6b23.jpg","url60x60":"http://yihe.kssws.ks-cdn.com/back_product/1463482288601_10.336716050630667_%E6%B5%B7%E6%A3%A0%E8%8A%B13.png@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://yihe.kssws.ks-cdn.com/back_product/1463482288601_10.336716050630667_%E6%B5%B7%E6%A3%A0%E8%8A%B13.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://yihe.kssws.ks-cdn.com/back_product/1463482288601_10.336716050630667_%E6%B5%B7%E6%A3%A0%E8%8A%B13.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://yihe.kssws.ks-cdn.com/back_product/1463482288601_10.336716050630667_%E6%B5%B7%E6%A3%A0%E8%8A%B13.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://yihe.kssws.ks-cdn.com/back_product/1463482288601_10.336716050630667_%E6%B5%B7%E6%A3%A0%E8%8A%B13.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://yihe.kssws.ks-cdn.com/back_product/1463482288601_10.336716050630667_%E6%B5%B7%E6%A3%A0%E8%8A%B13.png@base@tag=imgScale&h=160&w=160&m=0","url100x100":"http://yihe.kssws.ks-cdn.com/back_product/1463482288601_10.336716050630667_%E6%B5%B7%E6%A3%A0%E8%8A%B13.png@base@tag=imgScale&h=100&w=100&m=0"},{"name":"海棠花1.png","url":"http://img10.360buyimg.com/n1/jfs/t910/142/160555319/141007/57405505/550651efN146a6b23.jpg","url60x60":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_82.43934057634237_%E6%B5%B7%E6%A3%A0%E8%8A%B11.png@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_82.43934057634237_%E6%B5%B7%E6%A3%A0%E8%8A%B11.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_82.43934057634237_%E6%B5%B7%E6%A3%A0%E8%8A%B11.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_82.43934057634237_%E6%B5%B7%E6%A3%A0%E8%8A%B11.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_82.43934057634237_%E6%B5%B7%E6%A3%A0%E8%8A%B11.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_82.43934057634237_%E6%B5%B7%E6%A3%A0%E8%8A%B11.png@base@tag=imgScale&h=160&w=160&m=0","url100x100":"http://yihe.kssws.ks-cdn.com/back_product/1463482288597_82.43934057634237_%E6%B5%B7%E6%A3%A0%E8%8A%B11.png@base@tag=imgScale&h=100&w=100&m=0"},{"name":"海棠花7.png","url":"http://yihe.kssws.ks-cdn.com/back_product/1463482289079_24.66847608971503_%E6%B5%B7%E6%A3%A0%E8%8A%B17.png","url60x60":"http://yihe.kssws.ks-cdn.com/back_product/1463482289079_24.66847608971503_%E6%B5%B7%E6%A3%A0%E8%8A%B17.png@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://yihe.kssws.ks-cdn.com/back_product/1463482289079_24.66847608971503_%E6%B5%B7%E6%A3%A0%E8%8A%B17.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://yihe.kssws.ks-cdn.com/back_product/1463482289079_24.66847608971503_%E6%B5%B7%E6%A3%A0%E8%8A%B17.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://yihe.kssws.ks-cdn.com/back_product/1463482289079_24.66847608971503_%E6%B5%B7%E6%A3%A0%E8%8A%B17.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://yihe.kssws.ks-cdn.com/back_product/1463482289079_24.66847608971503_%E6%B5%B7%E6%A3%A0%E8%8A%B17.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://yihe.kssws.ks-cdn.com/back_product/1463482289079_24.66847608971503_%E6%B5%B7%E6%A3%A0%E8%8A%B17.png@base@tag=imgScale&h=160&w=160&m=0","url100x100":"http://yihe.kssws.ks-cdn.com/back_product/1463482289079_24.66847608971503_%E6%B5%B7%E6%A3%A0%E8%8A%B17.png@base@tag=imgScale&h=100&w=100&m=0"},{"name":"海棠花8.png","url":"http://yihe.kssws.ks-cdn.com/back_product/1463482289198_63.9922481781333_%E6%B5%B7%E6%A3%A0%E8%8A%B18.png","url60x60":"http://yihe.kssws.ks-cdn.com/back_product/1463482289198_63.9922481781333_%E6%B5%B7%E6%A3%A0%E8%8A%B18.png@base@tag=imgScale&h=60&w=60&m=0","url220x220":"http://yihe.kssws.ks-cdn.com/back_product/1463482289198_63.9922481781333_%E6%B5%B7%E6%A3%A0%E8%8A%B18.png@base@tag=imgScale&h=220&w=220&m=0","url500x500":"http://yihe.kssws.ks-cdn.com/back_product/1463482289198_63.9922481781333_%E6%B5%B7%E6%A3%A0%E8%8A%B18.png@base@tag=imgScale&h=500&w=500&m=0","url800x800":"http://yihe.kssws.ks-cdn.com/back_product/1463482289198_63.9922481781333_%E6%B5%B7%E6%A3%A0%E8%8A%B18.png@base@tag=imgScale&h=800&w=800&m=0","url120x120":"http://yihe.kssws.ks-cdn.com/back_product/1463482289198_63.9922481781333_%E6%B5%B7%E6%A3%A0%E8%8A%B18.png@base@tag=imgScale&h=120&w=120&m=0","url160x160":"http://yihe.kssws.ks-cdn.com/back_product/1463482289198_63.9922481781333_%E6%B5%B7%E6%A3%A0%E8%8A%B18.png@base@tag=imgScale&h=160&w=160&m=0","url100x100":"http://yihe.kssws.ks-cdn.com/back_product/1463482289198_63.9922481781333_%E6%B5%B7%E6%A3%A0%E8%8A%B18.png@base@tag=imgScale&h=100&w=100&m=0"}]
     * attrs : [{"attrName":{"id":null,"name":"花颜色01","sortValue":null},"attrVal":{"id":1880,"value":"颜色A01_03","sortValue":1,"checked":false}},{"attrName":{"id":null,"name":"花大小01","sortValue":null},"attrVal":{"id":1888,"value":"花大小01_01_03","sortValue":1,"checked":false}}]
     * recommend : [{"title":"女神洗护","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护1","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护2","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护3","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护4","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护5","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护6","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护7","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护8","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护9","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护10","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护11","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护12","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护13","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护14","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"},{"title":"女神洗护15","recommendUrl":"http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg"}]
     * video : null
     * securityVOList : []
     * favoriteId : null
     * url60x60 : null
     * url220x220 : null
     * url500x500 : null
     * url800x800 : null
     * url120x120 : null
     * url160x160 : null
     * url100x100 : null
     */
    public String warehouseName;//仓库所在地
    public Scripts scripts;//详情4个角的角图
    public long productId;
    public long mpId;
    public long mpsId;
    public String code;
    public String name;
    public String subTitle;
    public int type;
    public double price;
    public double tax;
    public long stockNum;
    public int lackOfStock;
    public long categoryId;
    public String categoryName;
    public int promotionType;
    public double promotionPrice;
    public double marketPrice;
    public long promotionId;
    public String promotionIconUrl;
    public long merchantId;
    public String merchantName;
    public int merchantType;
    public int freightAttribute;
    public double grossWeight;
    public double merchantProdVolume;
    public long shopId;
    public String shopName;
    public int shopType;
    public long freightTemplateId;
    public String warehouseNo;
    public String calculationUnit;
    public String standard;
    public int saleType;
    public String saleIconUrl;
    public int mpSource;
    public int isBargain;
    public int isRent;
    public int status;
    public int managementState;
    public String picUrl;
    public String pointTips;
    public String brandStory;
    public Object video;
    public long favoriteId;//字段废弃  原因德生开始一直没有返回值
    public Integer isFavorite;//判断是都收藏的薪资段 0是为收藏
    public String h5DetailUrl;
    public String pcDetailUrl;
    public long offset;//假销量
    public long mpSalesVolume;//真销量
    public List<String> promotionIconUrls;
    public List<String> promotionIconList;//这个是用来装处理之后的促销标签的
    public int isSeckill;//秒杀活动标记 0:非秒杀活动 1:秒杀活动'
    public int isSeries;//是否为系列品：0：否；1：是
    public int isAreaSale;//区域内是否可售 0 不可售 1 可售
    public int isForcast;
    public long forcastPromotionId;
    public int forcastPromotionType;
    public double forcastPromotionPrice;
    public double forcastPreferentialPrice;
    public long forcastPromotionStartTime;
    public long forcastPromotionEndTime;
    public long nowDate = -1;
    public long promotionStartTime;
    public long promotionEndTime;
    public String brandPageUrl;
    public List<String> titleIconTexts;
    public List<String> titleIconUrls;

    public int individualLimitNum;
    public int isDistribution;


    //预售
    public int isPresell;
    public long presellFinalStartTime;
    public long presellFinalEndTime;
    public long deliveryTime;
    public double presellDownPrice;
    public double presellOffsetPrice;
    public double presellTotalPrice;
    public double balancePayment;
    public int presellBookedNum;


    public List<Attrs> attrs;
    /**
     * attrName : {"id":null,"name":"花颜色01","sortValue":null}
     * attrVal : {"id":1880,"value":"颜色A01_03","sortValue":1,"checked":false}
     */


    public List<securityVOList> securityVOList;//服务保障

    public Scripts getScripts() {
        return scripts;
    }

    public void setScripts(Scripts scripts) {
        this.scripts = scripts;
    }



    /**
     * name : 海棠花5.png
     * url : http://img10.360buyimg.com/n1/jfs/t910/142/160555319/141007/57405505/550651efN146a6b23.jpg
     * url60x60 : http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=60&w=60&m=0
     * url220x220 : http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=220&w=220&m=0
     * url500x500 : http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=500&w=500&m=0
     * url800x800 : http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=800&w=800&m=0
     * url120x120 : http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=120&w=120&m=0
     * url160x160 : http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=160&w=160&m=0
     * url100x100 : http://yihe.kssws.ks-cdn.com/back_product/1463482288603_85.80361780339676_%E6%B5%B7%E6%A3%A0%E8%8A%B15.png@base@tag=imgScale&h=100&w=100&m=0
     */

    public List<Pics> pics;

    public String ramark;
    public String ziying;

    public String brandId;
    public String brandName;

    public String preferentialPrice;

    public long categoryTreeNodeId;
    public long merchantSeriesId;
    public String companyId;


    public Object url220x220;
    public Object url500x500;
    public Object url800x800;
    public Object url120x120;
    public Object url160x160;
    public Object url100x100;


    /**
     * title : 女神洗护
     * recommendUrl : http://img3.imgtn.bdimg.com/it/u=2509749820,3583201419&fm=21&gp=0.jpg
     */
    public List<Recommend> recommend;


    public static class securityVOList implements Serializable {

        /**
         * id : 232
         * companyId : 1
         * securityDate : 0
         * title : 新增一个
         * url : null
         * content : 新增一个
         * urlLink : security-content.html?mpId=596&sectid=232
         */

        public String id;
        public String companyId;
        public String securityDate;
        public String title;
        public String url;
        public String content;
        public String urlLink;
        public String contentPlain;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrlLink() {
            return urlLink;
        }

        public void setUrlLink(String urlLink) {
            this.urlLink = urlLink;
        }
    }

    /**
     * 详情4个角的角标
     */
    public static class Scripts implements Serializable{
        public int displayType;
        public String scriptIconUrl;
        public String scriptName;

        public int getDisplayType() {
            return displayType;
        }

        public void setDisplayType(int displayType) {
            this.displayType = displayType;
        }

        public String getScriptIconUrl() {
            return scriptIconUrl;
        }

        public void setScriptIconUrl(String scriptIconUrl) {
            this.scriptIconUrl = scriptIconUrl;
        }

        public String getScriptName() {
            return scriptName;
        }

        public void setScriptName(String scriptName) {
            this.scriptName = scriptName;
        }
    }

    public static class Pics implements Serializable {
        public String name;
        public String url;
        public String url60x60;
        public String url400x400;
        public String url220x220;
        public String url500x500;
        public String url800x800;
        public String url120x120;
        public String url160x160;
        public String url100x100;

        public Pics(String name, String url, String url60x60,String url400x400, String url220x220, String url500x500, String url800x800, String url120x120, String url160x160, String url100x100) {
            this.name = name;
            this.url = url;
            this.url60x60 = url60x60;
            this.url400x400=url400x400;
            this.url220x220 = url220x220;
            this.url500x500 = url500x500;
            this.url800x800 = url800x800;
            this.url120x120 = url120x120;
            this.url160x160 = url160x160;
            this.url100x100 = url100x100;
        }

    }

    public static class Attrs implements Serializable {
        /**
         * id : null
         * name : 花颜色01
         * sortValue : null
         */

        public AttrName attrName;
        /**
         * id : 1880
         * value : 颜色A01_03
         * sortValue : 1
         * checked : false
         */

        public AttrVal attrVal;

        public static class AttrName implements Serializable{
            public long id;
            public String name;
            public String sortValue;

        }

        public static class AttrVal implements Serializable{
            public long id;
            public String value;
            public int sortValue;
            public boolean checked;

        }
    }


    public static class Recommend implements Serializable {
        public String title;
        public String recommendUrl;

    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setMpId(long mpId) {
        this.mpId = mpId;
    }

    public void setMpsId(long mpsId) {
        this.mpsId = mpsId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setStockNum(long stockNum) {
        this.stockNum = stockNum;
    }

    public void setLackOfStock(int lackOfStock) {
        this.lackOfStock = lackOfStock;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public void setPromotionId(long promotionId) {
        this.promotionId = promotionId;
    }

    public void setPromotionIconUrl(String promotionIconUrl) {
        this.promotionIconUrl = promotionIconUrl;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setMerchantType(int merchantType) {
        this.merchantType = merchantType;
    }

    public void setFreightAttribute(int freightAttribute) {
        this.freightAttribute = freightAttribute;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public void setMerchantProdVolume(double merchantProdVolume) {
        this.merchantProdVolume = merchantProdVolume;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public void setFreightTemplateId(long freightTemplateId) {
        this.freightTemplateId = freightTemplateId;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public void setCalculationUnit(String calculationUnit) {
        this.calculationUnit = calculationUnit;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public void setSaleType(int saleType) {
        this.saleType = saleType;
    }

    public void setSaleIconUrl(String saleIconUrl) {
        this.saleIconUrl = saleIconUrl;
    }

    public void setMpSource(int mpSource) {
        this.mpSource = mpSource;
    }

    public void setIsBargain(int isBargain) {
        this.isBargain = isBargain;
    }

    public void setIsRent(int isRent) {
        this.isRent = isRent;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setManagementState(int managementState) {
        this.managementState = managementState;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setVideo(Object video) {
        this.video = video;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setH5DetailUrl(String h5DetailUrl) {
        this.h5DetailUrl = h5DetailUrl;
    }

    public void setPcDetailUrl(String pcDetailUrl) {
        this.pcDetailUrl = pcDetailUrl;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setMpSalesVolume(long mpSalesVolume) {
        this.mpSalesVolume = mpSalesVolume;
    }

    public long getNowDate() {
        return nowDate;
    }

    public void setNowDate(long nowDate) {
        this.nowDate = nowDate;
    }

    public int getIsSeckill() {
        return isSeckill;
    }

    public void setIsSeckill(int isSeckill) {
        this.isSeckill = isSeckill;
    }

    public long getPromotionStartTime() {
        return promotionStartTime;
    }

    public void setPromotionStartTime(long promotionStartTime) {
        this.promotionStartTime = promotionStartTime;
    }

    public long getPromotionEndTime() {
        return promotionEndTime;
    }

    public void setPromotionEndTime(long promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
    }

    public void setPromotionIconUrls(List<String> promotionIconUrls) {
        this.promotionIconUrls = promotionIconUrls;
    }

    public void setPromotionIconList(List<String> promotionIconList) {
        this.promotionIconList = promotionIconList;
    }

    public void setAttrs(List<Attrs> attrs) {
        this.attrs = attrs;
    }

    public List<Attrs> getAttrs() {
        return attrs;
    }

    public void setSecurityVOList(List<ProductBean.securityVOList> securityVOList) {
        this.securityVOList = securityVOList;
    }

    public void setPics(List<Pics> pics) {
        this.pics = pics;
    }

    public void setRamark(String ramark) {
        this.ramark = ramark;
    }

    public void setZiying(String ziying) {
        this.ziying = ziying;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public void setCategoryTreeNodeId(long categoryTreeNodeId) {
        this.categoryTreeNodeId = categoryTreeNodeId;
    }

    public void setMerchantSeriesId(long merchantSeriesId) {
        this.merchantSeriesId = merchantSeriesId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setUrl220x220(Object url220x220) {
        this.url220x220 = url220x220;
    }

    public void setUrl500x500(Object url500x500) {
        this.url500x500 = url500x500;
    }

    public void setUrl800x800(Object url800x800) {
        this.url800x800 = url800x800;
    }

    public void setUrl120x120(Object url120x120) {
        this.url120x120 = url120x120;
    }

    public void setUrl160x160(Object url160x160) {
        this.url160x160 = url160x160;
    }

    public void setUrl100x100(Object url100x100) {
        this.url100x100 = url100x100;
    }

    public void setRecommend(List<Recommend> recommend) {
        this.recommend = recommend;
    }
}
