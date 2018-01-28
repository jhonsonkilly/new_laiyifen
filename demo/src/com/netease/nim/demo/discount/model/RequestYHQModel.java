package com.netease.nim.demo.discount.model;

/**
 * @author SevenCheng
 */

public class RequestYHQModel {


    /**
     * trace : 33!$9#@2%&10!$,0,63371098619447467273314
     * code : 0
     * couponId : dWHO3M3frheJBdkfTI2qATfmbe4LyfAzDBHx40b7-ljz1y6ujZvWMz3vmCjbPE1zYkSY-8SX99LX2MSDBI4MSE0n35HCsqtUmmWKg1P1MWVSc7k_eqIpy3YUmMnuLzeTdyTtyG2qkX73GMPJNQ_8Hn4iT-841iu1L4PZWP7DukE=&shareCode=dWHO3M3frheJBdkfTI2qATfmbe4LyfAzDBHx40b7-ljz1y6ujZvWMz3vmCjbPE1zYkSY-8SX99LX2MSDBI4MSE0n35HCsqtUmmWKg1P1MWVSc7k_eqIpy3YUmMnuLzeTdyTtyG2qkX73GMPJNQ_8Hn4iT-841iu1L4PZWP7DukE=&type=2&u=10007789&t=2
     * data : {"url60x60":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=60&w=60","url800x800":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=800&w=800","url100x100":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=100&w=100","title":"来伊份发福利，味你所爱，味爱分享！","content":"幸福就是，和爱人品美味。","url400x400":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=400&w=400","sharePicUrl":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png","url160x160":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=160&w=160","url300x300":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=300&w=300","url500x500":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=500&w=500","url220x220":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=220&w=220","linkUrl":"http://m.lyf.dev.laiyifen.com/my/coupons-receive.html?couponShareCode=dWHO3M3frheJBdkfTI2qATfmbe4LyfAzDBHx40b7-ljz1y6ujZvWMz3vmCjbPE1zYkSY-8SX99LX2MSDBI4MSE0n35HCsqtUmmWKg1P1MWVSc7k_eqIpy3YUmMnuLzeTdyTtyG2qkX73GMPJNQ_8Hn4iT-841iu1L4PZWP7DukE=&shareCode=dWHO3M3frheJBdkfTI2qATfmbe4LyfAzDBHx40b7-ljz1y6ujZvWMz3vmCjbPE1zYkSY-8SX99LX2MSDBI4MSE0n35HCsqtUmmWKg1P1MWVSc7k_eqIpy3YUmMnuLzeTdyTtyG2qkX73GMPJNQ_8Hn4iT-841iu1L4PZWP7DukE=&type=2&u=10007789&t=2","url120x120":"http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=120&w=120"}
     * message : 操作成功
     * memberId : 10007789
     */

    private String trace;
    private String code;
    private String couponId;
    private DataBean data;
    private String message;
    private String memberId;

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public static class DataBean {
        /**
         * url60x60 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=60&w=60
         * url800x800 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=800&w=800
         * url100x100 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=100&w=100
         * title : 来伊份发福利，味你所爱，味爱分享！
         * content : 幸福就是，和爱人品美味。
         * url400x400 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=400&w=400
         * sharePicUrl : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png
         * url160x160 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=160&w=160
         * url300x300 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=300&w=300
         * url500x500 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=500&w=500
         * url220x220 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=220&w=220
         * linkUrl : http://m.lyf.dev.laiyifen.com/my/coupons-receive.html?couponShareCode=dWHO3M3frheJBdkfTI2qATfmbe4LyfAzDBHx40b7-ljz1y6ujZvWMz3vmCjbPE1zYkSY-8SX99LX2MSDBI4MSE0n35HCsqtUmmWKg1P1MWVSc7k_eqIpy3YUmMnuLzeTdyTtyG2qkX73GMPJNQ_8Hn4iT-841iu1L4PZWP7DukE=&shareCode=dWHO3M3frheJBdkfTI2qATfmbe4LyfAzDBHx40b7-ljz1y6ujZvWMz3vmCjbPE1zYkSY-8SX99LX2MSDBI4MSE0n35HCsqtUmmWKg1P1MWVSc7k_eqIpy3YUmMnuLzeTdyTtyG2qkX73GMPJNQ_8Hn4iT-841iu1L4PZWP7DukE=&type=2&u=10007789&t=2
         * url120x120 : http://cdn.oudianyun.com/lyf-local/branch/frontier-guide/1484574626844_1521_74.png@base@tag=imgScale&q=80&m=0&h=120&w=120
         */

        private String url60x60;
        private String url800x800;
        private String url100x100;
        private String title;
        private String content;
        private String url400x400;
        private String sharePicUrl;
        private String url160x160;
        private String url300x300;
        private String url500x500;
        private String url220x220;
        private String linkUrl;
        private String url120x120;

        public String getUrl60x60() {
            return url60x60;
        }

        public void setUrl60x60(String url60x60) {
            this.url60x60 = url60x60;
        }

        public String getUrl800x800() {
            return url800x800;
        }

        public void setUrl800x800(String url800x800) {
            this.url800x800 = url800x800;
        }

        public String getUrl100x100() {
            return url100x100;
        }

        public void setUrl100x100(String url100x100) {
            this.url100x100 = url100x100;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl400x400() {
            return url400x400;
        }

        public void setUrl400x400(String url400x400) {
            this.url400x400 = url400x400;
        }

        public String getSharePicUrl() {
            return sharePicUrl;
        }

        public void setSharePicUrl(String sharePicUrl) {
            this.sharePicUrl = sharePicUrl;
        }

        public String getUrl160x160() {
            return url160x160;
        }

        public void setUrl160x160(String url160x160) {
            this.url160x160 = url160x160;
        }

        public String getUrl300x300() {
            return url300x300;
        }

        public void setUrl300x300(String url300x300) {
            this.url300x300 = url300x300;
        }

        public String getUrl500x500() {
            return url500x500;
        }

        public void setUrl500x500(String url500x500) {
            this.url500x500 = url500x500;
        }

        public String getUrl220x220() {
            return url220x220;
        }

        public void setUrl220x220(String url220x220) {
            this.url220x220 = url220x220;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getUrl120x120() {
            return url120x120;
        }

        public void setUrl120x120(String url120x120) {
            this.url120x120 = url120x120;
        }
    }
}
