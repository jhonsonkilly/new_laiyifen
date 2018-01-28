package dsshare;


import com.ody.p2p.base.BaseRequestBean;

/**
 * Created by ody on 2016/6/29.
 */
public class ShareBean extends BaseRequestBean {

    /**
     * errMsg : null
     * data : {"title":"德升官方时装商城","content":"买电器上京东，买时装来\u201c德升\u201d就够了！","sharePicUrl":"http://ksyun.storage.rw.kssws.ks-cdn.com/zs/picture/1472395441618_294_5fb753475e9794fa52064eab.png","linkUrl":"http://dsh5.dev.odianyun.com/share.html?type=null&paramId=null&distributorId=1"}
     */

    public String errMsg;
    /**
     * title : 德升官方时装商城
     * content : 买电器上京东，买时装来“德升”就够了！
     * sharePicUrl : http://ksyun.storage.rw.kssws.ks-cdn.com/zs/picture/1472395441618_294_5fb753475e9794fa52064eab.png
     * linkUrl : http://dsh5.dev.odianyun.com/share.html?type=null&paramId=null&distributorId=1
     * "shareImgUrl": "http://ksyun.storage.rw.kssws.ks-cdn.com/zs/picture/1472395441618_294_5fb753475e9794fa52064eab.png"
     */

    public Data data;

    public static class Data {
        //公共分享部分
        public String title;
        public String content;
        public String url;
        public String isDistribution;
        public String description;
        public String url160x160;
        public String linkUrl;
        public String sharePicUrl;
        public String distributeStr; //分销商佣金描述

        public String refIds;
        public String activityType;
        public String refType;

        public String id;
        public String type;
        public String typeName;
        public int createBy;
        public String createName;
        public String createHeadPicUrl;
        public String createTime;
        public int updateBy;
        public String updateTime;
        public int status;
        public int isTop;
        public int viewCount;
        public String distributorId;
        public String shareImgUrl;//这个shishi

        public String stuate;

        public ECard eCard;

        public String getStuate() {
            return stuate;
        }

        public void setStuate(String stuate) {
            this.stuate = stuate;
        }

        public Info info;

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }
    }


    public static class Info {
        public String couponCode;
        public String couponId;
        public String type;

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getType() {
            return type;
        }

        public void setType(String shareType) {
            this.type = shareType;
        }
    }

    public static class ECard {
        public String cardCode;
    }

}



