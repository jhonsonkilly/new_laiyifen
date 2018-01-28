package com.ody.p2p.main.specificfunction;

/**
 * Created by meijunqiang on 2017/3/16.
 * 描述：
 */

public class DataUtils {
    public static int parseStringToTalkingPrice(String price) {
        try {
            String s = Double.parseDouble(price) * 100 + "";
            if (s.contains(".")) {
                s = s.substring(0, s.indexOf('.'));
            }
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {

        }
        return 0;
    }
}
