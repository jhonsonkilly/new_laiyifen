package com.lyfen.android.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

/**
 * Created by qj on 2017/5/5.
 */

public class NumberUtils {
    public static String getDoubleForDouble(double dou) {
        try {
            DecimalFormat formatter = new DecimalFormat();
            formatter.applyPattern("#0.00");//格式代码，".00"代表保留2位小数，是0的输出0
            return formatter.format(dou);
        }catch (Exception e){
            return null;
        }

    }

    public static String getDoubleForDouble(String money) {
        if (!isEmpty(money)) {
            DecimalFormat formatter = new DecimalFormat();
            formatter.applyPattern("#0.00");
            return formatter.format(new BigDecimal(money));
        } else {
            return "0.00";
        }
    }


    /**
     * 检查手机号的非空和合法性
     *
     * @param mobile
     * @return
     */
    public static boolean checkPhone(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            UIUtils.showToastSafe("手机号不能为空");

            return false;
        }
        boolean isMobile = checkMobile(mobile);
        if (!isMobile) {
            UIUtils.showToastSafe("该手机号有误，请检查手机号");

            return false;
        }
        return true;
    }
    /**
     * 验证手机号是否合法
     *
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        return p.matcher(mobile).matches();
    }
}
