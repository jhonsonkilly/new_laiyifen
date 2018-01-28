package com.netease.nim.uikit.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.framework.NimSingleThreadExecutor;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

//import com.bumptech.glide.request.RequestOptions;

/**
 * 图片缓存管理组件
 */
public class ImageLoaderKit {

    private static final String TAG = "ImageLoaderKit";

    private Context context;

    public ImageLoaderKit(Context context) {
        this.context = context;
    }

    /**
     * 清空图像缓存
     */
    public void clear() {
        NIMGlideModule.clearMemoryCache(context);
    }

    /**
     * 构建图像缓存
     */
    public void buildImageCache() {
        // clear avatar cache
        clear();

        // build self avatar cache
        List<String> accounts = new ArrayList<>(1);
        accounts.add(NimUIKit.getAccount());
        NimUIKit.getImageLoaderKit().buildAvatarCache(accounts);
    }

    private void buildAvatarCache(List<String> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            return;
        }

        UserInfoProvider.UserInfo userInfo;
        for (String account : accounts) {
            userInfo = NimUIKit.getUserInfoProvider().getUserInfo(account);
            if (userInfo != null) {
                asyncLoadAvatarBitmapToCache(userInfo.getAvatar());
            }
        }

        LogUtil.i(TAG, "build avatar cache completed, avatar count=" + accounts.size());
    }

    /**
     * 获取通知栏提醒所需的头像位图，只存内存缓存/磁盘缓存中取，如果没有则返回空，自动发起异步加载
     * 注意：该方法在后台线程执行
     */
    public Bitmap getNotificationBitmapFromCache(final String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        final int imageSize = HeadImageView.DEFAULT_AVATAR_NOTIFICATION_ICON_SIZE;

        final Bitmap[] cachedBitmap = {null};
/*        final Bitmap[] bitmap = {null};
        try {
//            cachedBitmap = Glide.with(context)
//                    .asBitmap()
//                    .load(url)
//                    .apply(new RequestOptions()
//                            .centerCrop()
//                            .override(imageSize, imageSize))
//                    .submit()
//                    .get(200, TimeUnit.MILLISECONDS)// 最大等待200ms
//            ;
            Glide.with(context)
                    .load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            bitmap[0] =  resource;
                        }
                    });
            cachedBitmap = bitmap[0];
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        OkHttpClient client = new OkHttpClient();

        //获取请求对象
        Request request = new Request.Builder().url(url).build();

        //获取响应体

        ResponseBody body = null;
        try {
            body = client.newCall(request).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取流
        InputStream in = body.byteStream();
        //转化为bitmap
        final Bitmap bitmap = BitmapFactory.decodeStream(in);


        return bitmap;
    }

    /**
     * 异步加载头像位图到Glide缓存中
     */
    private void asyncLoadAvatarBitmapToCache(final String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        final int imageSize = HeadImageView.DEFAULT_AVATAR_THUMB_SIZE;
        NimSingleThreadExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Glide.with(context)
                        .load(url)
                        //                        .submit(imageSize, imageSize);
                        .override(600, 200);
            }
        });
    }
}
