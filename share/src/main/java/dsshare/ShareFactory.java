package dsshare;

import android.content.Context;

import com.google.gson.Gson;
import com.odianyun.sharesdksharedemo.R;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.retrofit.user.PointBean;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/17.
 */

public class ShareFactory implements PlatformActionListener {
    private Context mContext;
    private static ShareFactory factory;
    private static Object lock = new Object();

    private ShareFactory(Context context) {
        mContext = context;
        ShareSDK.initSDK(context);
    }

    public static ShareFactory create(Context context) {
        synchronized (lock) {
            if (factory == null) {
                factory = new ShareFactory(context);
            }
        }
        return factory;
    }

    public void share(String params) {
        try {
            share(new Gson().fromJson(params, SocialShare.class));
        } catch (Exception ex) {

        }
    }

    public void share(SocialShare share) {
        Platform platform = ShareSDK.getPlatform(share.platform);
        if (share.platform.equals(WechatMoments.NAME)) {
            if (!platform.isClientValid()) {
                ToastUtils.showShort(mContext.getString(R.string.not_to_install_wechat));
                return;
            }
            WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);//分享类型:图片
            sp.setTitle(share.title);//标题
            sp.setText(share.description);//内容
            sp.setImageUrl(share.pic);//显示的分享的图片
            sp.setUrl(share.url == null ? "" : share.url);//链接
            sp.setTitleUrl(share.url == null ? "" : share.url);//标题图片
            platform.setPlatformActionListener(ShareFactory.this);// 设置分享事件回调
            platform.share(sp);
        } else if (share.platform.equals(Wechat.NAME)) {
            if (!platform.isClientValid()) {
                ToastUtils.showShort(mContext.getString(R.string.not_to_install_wechat));
                return;
            }
            Wechat.ShareParams sp = new Wechat.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);//分享类型:图片
            sp.setTitle(share.title);//标题
            sp.setText(share.description);//内容
            sp.setImageUrl(share.pic);//显示的分享的图片
            sp.setUrl(share.url == null ? "" : share.url);//链接
            sp.setTitleUrl(share.url == null ? "" : share.url);//标题图片
            platform.setPlatformActionListener(ShareFactory.this);// 设置分享事件回调
            platform.share(sp);
        } else if (share.platform.equals(QQ.NAME)) {
            if (!platform.isClientValid()) {
                ToastUtils.showShort(mContext.getString(R.string.not_to_install_qq));
                return;
            }
            QQ.ShareParams sp = new QQ.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);//分享类型:图片
            sp.setTitle(share.title);//标题
            sp.setText(share.description);//内容
            sp.setImageUrl(share.pic);//显示的分享的图片
            sp.setUrl(share.url == null ? "" : share.url);//链接
            sp.setTitleUrl(share.url == null ? "" : share.url);//标题图片
            platform.setPlatformActionListener(ShareFactory.this);// 设置分享事件回调
            platform.share(sp);
        } else if (share.platform.equals(QZone.NAME)) {
            if (!platform.isClientValid()) {
                ToastUtils.showShort(mContext.getString(R.string.not_to_install_qq));
                return;
            }
            QZone.ShareParams sp = new QZone.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);//分享类型:图片
            sp.setTitle(share.title);//标题
            sp.setText(share.description);//内容
            sp.setImageUrl(share.pic);//显示的分享的图片
            sp.setUrl(share.url == null ? "" : share.url);//链接
            sp.setTitleUrl(share.url == null ? "" : share.url);//标题图片
            platform.setPlatformActionListener(ShareFactory.this);// 设置分享事件回调
            platform.share(sp);
        } else if (share.platform.equals(SinaWeibo.NAME)) {
            ToastUtils.showShort("暂不支持");
            return;
//            SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//            sp.setShareType(Platform.SHARE_WEBPAGE);
//            sp.setTitle(share.title);//标题
//            sp.setText(share.description);//内容
//            sp.setImageUrl(share.pic);//显示的分享的图片
//            sp.setUrl(share.url == null ? "" : share.url);//链接
//            sp.setTitleUrl(share.url == null ? "" : share.url);//标题图片
//            platform.SSOSetting(false);
//            platform.setPlatformActionListener(ShareFactory.this);
//            platform.share(sp);
        } else if (share.platform.equals(ShortMessage.NAME)) {
            ShortMessage.ShareParams sp = new ShortMessage.ShareParams();
            sp.setText(share.title + "," + share.url);
            platform.setPlatformActionListener(ShareFactory.this);
            platform.share(sp);
        } else {
            ToastUtils.showShort("无效分享平台");
        }
    }

    private CustomDialog dialog = null;

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//        分享对象的类型:1话题 2本地 3活动 4用户 5app 6商品
        RetrofitFactory.getSharePoint("5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<PointBean>(new SubscriberListener<PointBean>() {
                    @Override
                    public void onNext(PointBean bean) {

                        if (bean != null && bean.code.equals("0") && bean.data != null) {
                            dialog = new CustomDialog(mContext, "分享成功，恭喜您获得" + bean.data.amount + "伊豆", 3);
                        } else if (bean != null && bean.data != null) {
                            dialog = new CustomDialog(mContext, bean.data.message, 3);
                        }
                        dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                            @Override
                            public void Confirm() {
                                dialog.dismiss();
                            }
                        });

                        if (dialog != null) {
                            dialog.show();
                        }
                    }
                }));
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtils.showShort(mContext.getString(R.string.share_failed));
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showShort(mContext.getString(R.string.share_canceled));
    }
}
