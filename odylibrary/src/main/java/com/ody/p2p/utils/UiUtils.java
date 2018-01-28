package com.ody.p2p.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ody.p2p.R;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.views.imagespan.OdyImageSpan;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import static android.R.attr.maxLength;

/**
 * Created by lxs on 2016/5/11.
 */
public class UiUtils {

    /**
     * @param activity
     * @param title
     * @see [自定义标题栏]
     */
    public static RelativeLayout getTitleBar(final Activity activity, String title) {
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        activity.setContentView(R.layout.layout_title);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.layout_title);
        RelativeLayout layout_title = (RelativeLayout) activity.findViewById(R.id.layout_title);
        TextView textView = (TextView) activity.findViewById(R.id.tv_title);
        textView.setText(title);
        ImageView back = (ImageView) activity.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activity.finish();
            }
        });
        return layout_title;
    }

    /**
     * double格式化(保留两位小数___带¥符号)
     *
     * @param dou
     * @return
     */
    public static String getMoneyDouble(double dou) {
        return "¥" + getDoubleForDouble(dou);
    }

    /**
     * double格式化(保留两位小数)
     *
     * @param dou
     * @return
     */
    public static String getDoubleForDouble(double dou) {
        DecimalFormat formatter = new DecimalFormat();
        formatter.applyPattern("#0.00");//格式代码，".00"代表保留2位小数，是0的输出0
        return formatter.format(dou);
    }

    public static String getDoubleForInteger(double dou) {
        DecimalFormat formatter = new DecimalFormat();
        formatter.applyPattern("#0");//格式代码，".00"代表保留2位小数，是0的输出0
        return formatter.format(dou);
    }

    public static String getDoubleForDouble(String money) {
        if (!TextUtils.isEmpty(money)) {
            DecimalFormat formatter = new DecimalFormat();
            formatter.applyPattern("#0.00");
            return formatter.format(new BigDecimal(money));
        } else {
            return "0.00";
        }
    }

    public static boolean isIdentityCard(String card) {
        if (isEmpty(card)) {
            return false;
        } else if (card.length() == 18) {
            Pattern pattern = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
            return pattern.matcher(card).matches();
        } else if (card.length() == 15) {
            Pattern pattern = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
            return pattern.matcher(card).matches();
        }
        return false;
    }

    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 毫秒数转化时间格式
     *
     * @param args
     * @return
     */
    public static String getTiem(Long args) {
        Date dat = new Date(args);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy.MM.dd");
        String sb = format.format(gc.getTime());
        System.out.println(sb);
        return sb;
    }

    /**
     * 字符大小 "¥"符号跟小数点后两位缩小
     * @param context
     * @param dou
     * @return
     */
    public static SpannableString getMoney(Context context, double dou) {
        String money = getMoneyDouble(dou);
        return getSpanMoney(context, money);
    }

    /**
     * 字符大小 "¥"符号跟小数点后两位缩小
     * @param context
     * @param price
     * @return
     */
    public static SpannableString getMoney(Context context, String price) {
        if (StringUtils.isEmpty(price)) {
            return new SpannableString("");
        }
        String money = context.getString(R.string.money_symbol) + getDoubleForDouble(price);
        return getSpanMoney(context, money);
    }

    private static SpannableString getSpanMoney(Context context, String money) {
        SpannableString styledText = new SpannableString(money);
        if (null == styledText) {
            return new SpannableString("");
        }
        if (OdyApplication.SCHEME.equals("ds") || OdyApplication.SCHEME.equals("saas")) {
            try {
                styledText.setSpan(new TextAppearanceSpan(context, R.style.text_moeny), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.text_moeny_sum), 1, money.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.text_moeny), money.length() - 2, money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (OdyApplication.SCHEME.equals("lyf")) {
//            styledText.setSpan(new TextAppearanceSpan(context, R.style.lyf_money), 0, money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {

        }
        return styledText;
    }


    /**
     * 字符大小 "¥"符号跟小数点后两位缩小
     * @param context
     * @param content
     * @return
     */
//    public static SpannableString getGiftName(Context context, String content) {
//        SpannableString styledText = new SpannableString(content);
//        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_gift_name), content.indexOf("//[") + 1, content.indexOf("//]") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        styledText.setSpan(new TextAppearanceSpan(context, R.style.text_moeny_sum), content.indexOf("//]") + 2, content.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return styledText;
//    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String getString(String str) {
        if (StringUtils.isEmpty(str)) {
            return "0";
        }
        return str;
    }


    /**
     * 设置文本图片混合
     * @param tv_show
     * @param context
     * @param url
     * @param content
     */
    public static void getStringSpan(final TextView tv_show, final Context context, final String url, final String content) {
//        final Handler mDelivery = new Handler(Looper.getMainLooper());
        if (!StringUtils.isEmpty(tv_show.getText().toString()) && !tv_show.getText().toString().equals(content))
            return;
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                OdyImageSpan span = new OdyImageSpan(context, Bitmap.createScaledBitmap(resource, resource.getWidth() * PxUtils.dipTopx(14) / resource.getHeight(), PxUtils.dipTopx(14), true));
                String contentShow = "a" + content;
                SpannableString spanString = new SpannableString(contentShow);
                spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_show.setText(spanString);
            }
        });
    }

    /**
     * 设置文本图片混合
     *
     * @param tv_show
     * @param context
     * @param url
     * @param content
     */
    public static void getStringSpan(final TextView tv_show, final Context context, final String url, final String content, final int position) {
//        final Handler mDelivery = new Handler(Looper.getMainLooper());
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int p = (int) tv_show.getTag();
                if (p != position) {
                    return;
                }
                OdyImageSpan span = new OdyImageSpan(context, Bitmap.createScaledBitmap(resource, resource.getWidth() * PxUtils.dipTopx(14) / resource.getHeight(), PxUtils.dipTopx(14), true));
                String contentShow = "a" + content;
                SpannableString spanString = new SpannableString(contentShow);
                spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_show.setText(spanString);
            }
        });
    }

    /**
     * 设置文本图片混合
     *
     * @param tv_show
     * @param context
     * @param res
     * @param content
     */
    public static void getStringSpan(final TextView tv_show, final Context context, final int res, final String content) {
        Glide.with(context).load(res).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                OdyImageSpan span = new OdyImageSpan(context, Bitmap.createScaledBitmap(resource, resource.getWidth() * PxUtils.dipTopx(14) / resource.getHeight(), PxUtils.dipTopx(14), true));
                String contentShow = "a" + content;
                SpannableString spanString = new SpannableString(contentShow);
                spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_show.setText(spanString);
            }
        });
    }

    /**
     * 设置文本图片混合
     *
     * @param tv_show
     * @param context
     * @param res
     */
    public static void getStringSpan(final TextView tv_show, final Context context, final int res) {
        Glide.with(context).load(res).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                OdyImageSpan span = new OdyImageSpan(context, Bitmap.createScaledBitmap(resource, resource.getWidth() * PxUtils.dipTopx(14) / resource.getHeight(), PxUtils.dipTopx(14), true));
                String contentShow = "a" + tv_show.getText().toString();
                SpannableString spanString = new SpannableString(contentShow);
                spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_show.setText(spanString);
            }
        });
    }

    /**
     * 文本混合
     *
     * @param context
     * @param text
     * @return
     */
    public static SpannableString getStick(Context context, String text) {
        SpannableString styledText = new SpannableString(text);
        try {
            styledText.setSpan(new TextAppearanceSpan(context, R.style.theme_text), text.indexOf("["), text.lastIndexOf("]") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(context, R.style.black_text), text.lastIndexOf("]") + 1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }
        return styledText;
    }

    /**
     * 设置消息类数量角标
     *
     * @param number
     * @param view
     */
    public static void setCareNum(long number, int type, TextView view) {
        if (null == view) {
            return;
        }
        view.setBackgroundResource(R.drawable.radius_theme_7);
//        view.setTextSize(PxUtils.pxTodip(24));这个不好用
//        view.setTextSize(R.dimen.textsize_12sp);

        view.setGravity(Gravity.CENTER);
        view.setPadding(PxUtils.dipTopx(2), 0, PxUtils.dipTopx(2), 0);
        view.setMinWidth(PxUtils.dipTopx(14));
        ViewGroup.LayoutParams para = view.getLayoutParams();
        para.height = PxUtils.dipTopx(14);
        if (number > 0) {
            if (number > 99) {
                view.setText(R.string.othermore);
//                para.width = PxUtils.dipTopx(20);
            } else {
                view.setText(number + "");
                if (number > 9) {
//                    para.width = PxUtils.dipTopx(16);
                    if (type == 1) {//消息类最大为9+
                        view.setText("9+");
                    }
                } else {
//                    para.width = PxUtils.dipTopx(14);
                }
            }
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        view.setLayoutParams(para);
    }
}
