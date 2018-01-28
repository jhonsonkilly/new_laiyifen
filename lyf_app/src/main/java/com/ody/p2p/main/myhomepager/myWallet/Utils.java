package com.ody.p2p.main.myhomepager.myWallet;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;

import com.ody.p2p.main.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by meijunqiang on 2017/3/22.
 * 描述：
 */

public class Utils {
    /**
     * 匹配价格样式的工具，如有需要，可以手动添加字号和小数点
     *
     * @param prefix  价格前缀
     * @param pri     价格
     * @param suffix  价格后缀
     * @param context 上下文
     * @return
     */
    public static SpannableString buildPrice(String prefix, Double pri, String suffix, Context context) {
        if (prefix == null) {
            prefix = "";
        }
        if (suffix == null) {
            suffix = "";
        }
        String price = String.format("%.2f", pri);
        Log.d("tags", "价格是：" + price);
        String str = prefix + price + suffix;
        SpannableString msp = new SpannableString(str);
        msp.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.sp_30)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.sp_16)), 0, str.indexOf("¥") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.sp_18)), str.indexOf("."), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    /**
     * 匹配价格样式的工具，如有需要，可以手动添加字号和小数点
     *
     * @param prefix   价格前缀
     * @param price    价格
     * @param suffix   价格后缀
     * @param context  上下文
     * @param leftSize 前缀
     * @return
     */
    public static SpannableString buildPriceForAuto(String prefix, String price, String suffix, Context context, int leftSize) {
        if (prefix == null) {
            prefix = "";
        }
        if (suffix == null) {
            suffix = "";
        }
        String str = prefix + price + suffix;
        SpannableString msp = new SpannableString(str);
        if (str.length() > 0) {
            msp.setSpan(new AbsoluteSizeSpan(leftSize), 0, str.indexOf("¥") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return msp;
    }

    /**
     * 毫秒转时间
     * @param millions
     * @return
     */
    public static String millionsToDate(long millions){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(millions);
        return simpleDateFormat.format(date);
    }
}
