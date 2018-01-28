package com.ody.p2p.base.action;

/**
 * <p> Created by qiujie on 2018/1/16/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class PidUtils {

    public static String getPid(String tag) {

        if (tag.equals("MainActivity")) {
            return "1";
        } else if (tag.equals("ShoppingCartActivity")) {

            return "3";
        } else if (tag.equals("LyfLoginFragment")) {
            return "4";

        } else if (tag.equals("LyfSweepActivity")) {

            return "6";
        } else if (tag.equals("LYFConfirmOrderActivity")) {
            return "7";
        } else if (tag.equals("ShangouActivity")) {

            return "10";
        } else if (tag.equals("ShangouActivity")) {
            return "11";
        } else if ((tag.equals("EditAddressActivity"))) {
            return "12";
        } else if (tag.equals("WebActivity")) {

            return "14";
        } else if (tag.equals("CashierStandActivity")) {
            return "16";
        } else if (tag.equals("PaySuccessActivity")) {
            return "17";
        } else if (tag.equals("PayFailActivity")) {
            return "18";
        } else if (tag.equals("LyfProductDetailActivity")) {
            return "19";
        } else if (tag.equals("OrderDetailActivity")) {
            return "21";
        } else if (tag.equals("LYFSearchActivity")) {

            return "22";
        }

        return "";
    }
}
