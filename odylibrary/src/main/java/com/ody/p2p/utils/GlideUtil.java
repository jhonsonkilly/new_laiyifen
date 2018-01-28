package com.ody.p2p.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ody.p2p.R;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.views.glide.GlideRoundTransform;

public class GlideUtil {
    public static String getUrl(Context context, String url, int width) {
        if (url != null && url.contains("cdn.oudianyun")) {
            if (url.contains("@base@tag=imgScale")) {
                if (width > 0) {
                    url += "&w=" + width;
                }
                url += "&F=webp";
            } else {
                url += "@base@tag=imgScale";
                if (width > 0) {
                    url += "&w=" + width;
                }
                url += "&F=webp";
            }
        }
        return url;
    }

    public static String getUrl(Context context, String url) {
        return getUrl(context, url, 0);
    }

    public static DrawableRequestBuilder display(Context context, String url) {

        return Glide
                .with(context)
                .load(getUrl(context, url))
                .error(OdyApplication.resPlaceHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static DrawableRequestBuilder displayLimpid(Context context, String url) {
        return Glide
                .with(context)
                .load(getUrl(context, url))
//                .load(url)
//                .error(OdyApplication.resPlaceHolder)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE);
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    /**
     * gif
     *
     * @param context
     * @param imgView
     * @param resImg
     */
    public static void displayGif(Context context, ImageView imgView, String resImg) {
        try {
//            if (resImg.endsWith("gif")) {
//                Glide.with(context).load(resImg).asGif().dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgView);
//            } else {
                Glide.with(context).load(resImg)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .dontAnimate()
                        .into(imgView);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DrawableRequestBuilder display(Context context, int width, String url) {

        return Glide
                .with(context)
                .load(getUrl(context, url, width))
//                .error(OdyApplication.resPlaceHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static DrawableRequestBuilder display(Context context, String url, int dp) {

        return Glide
                .with(context)
                .load(getUrl(context, url))
//                .error(OdyApplication.resPlaceHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(context, dp));
    }

    public static DrawableRequestBuilder display(Context context, int imgRes) {

        return Glide
                .with(context)
                .load(imgRes)
//                .error(OdyApplication.resPlaceHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static DrawableRequestBuilder display(Fragment fragment, String url) {
        return Glide
                .with(fragment)
                .load(getUrl(fragment.getContext(), url))
                .error(OdyApplication.resPlaceHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static DrawableRequestBuilder display(Fragment fragment, String url, int width) {
        return Glide
                .with(fragment)
                .load(getUrl(fragment.getContext(), url, width))
                .error(OdyApplication.resPlaceHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static DrawableRequestBuilder display(FragmentActivity activity, String url) {
        return Glide
                .with(activity)
                .load(getUrl(activity, url))
                .error(OdyApplication.resPlaceHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static DrawableRequestBuilder display(Activity activity, String url) {
        return Glide
                .with(activity)
                .load(getUrl(activity, url))
                .error(OdyApplication.resPlaceHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static void clearCache() {

    }
}
