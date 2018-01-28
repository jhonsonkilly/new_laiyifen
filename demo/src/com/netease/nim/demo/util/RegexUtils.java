package com.netease.nim.demo.util;

/**
 * Created by jasmin on 2018/1/3.
 */

public class RegexUtils {
    public static final String PHONE_NUMBER_REGEX = "^134[0-8]\\d{7}$|^13[^4]\\d{8}$|^14[5-9]\\d{8}$|^15[^4]\\d{8}$|^16[6]\\d{8}$|^17[0-8]\\d{8}$|^18[\\d]{9}$|^19[8,9]\\d{8}$";

    public static boolean isMobilePhoneNumber(String string){
        return string.matches(PHONE_NUMBER_REGEX);
    }
}
