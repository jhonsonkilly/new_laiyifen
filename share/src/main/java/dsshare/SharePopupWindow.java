package dsshare;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.odianyun.sharesdksharedemo.R;
import com.ody.p2p.Constants;
import com.ody.p2p.UserInfoBean;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.retrofit.user.PointBean;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.TKUtil;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.basepopupwindow.BasePopupWindow;
import com.ody.p2p.webactivity.WebActivity;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 直播间点击底部导航里的分享跳出来的
 */
public class SharePopupWindow extends BasePopupWindow implements View.OnClickListener, PlatformActionListener {

    public static final int SHARE_HOME = 1;//首页
    public static final int SHARE_DETAIL = 2;//详情页
    public static final int SHARE_DETAIL_SMM_MESSAGE = 8;//详情页（laiyifen）
    public static final int SHARE_BRAND = 3;//品牌
    public static final int SHARE_ARTICLETOCMS = 4;//文章cms

    public static final int SHARE_ARTICLE = 5;//文章

    public static final int SHARE_H5 = 6;

    public static final int SHARE_COMMUNITY = 7;//社区分享
    public static final int SHARE_COUPON = 20;//优惠券 赠送好友
    public static final int SHARE_REDPACKET = 9;//红包分享

    private View v_empty;
    private LinearLayout ll_whole_multi_photos_friends;
    private LinearLayout ll_multi_photos_friends;
    private View v_divide_line;
    private LinearLayout ll_logo1;
    private LinearLayout ll_logo2;
    private LinearLayout ll_logo3;
    private LinearLayout ll_logo4;
    private LinearLayout ll_logo5;
    private LinearLayout ll_logo6;
    private LinearLayout ll_logo7;
    private LinearLayout ll_logo8;
    private TextView tv_cancel;
    private int type;
    private long mId;
    public boolean isLogin;
    public ShareBean shareBean;

    private LinearLayout mLLPush;
    private TextView mTextApplyForPush;
    private TextView mTextViewCreatePoster;

    private CustomDialog dialog = null;
    private boolean isShortMessage = false;
    private int isShareDistribution;
    private TextView tv_title;
    private TextView tv_desc;
    private ArrayList<String> dataList;
    private int isDistribution;
    private boolean isDistributorProductDetail = false;//是否是分销商品详情页
    private DrawPhotoBean drawPhotoBean;
    private Context mContext;
    private String distributeStr;//分销商佣金描述


    //flag:0---不含多图分享,复制链接和刷新   1---包含多图分享,复制链接和刷新,没有短信分享
    public SharePopupWindow(Context mContext, int type, long mId) {//传activity是为了控制"图片生成中"的popwindow的显示位置
        super(mContext, type);
        this.mContext = mContext;
        this.mId = mId;
        this.type = type;
        init(mContext);
    }

    public SharePopupWindow(Context mContext, int type, long mId, ArrayList<String> dataList, int isDistribution) {//传activity是为了控制"图片生成中"的popwindow的显示位置
        super(mContext, type);
        this.mContext = mContext;
        this.mId = mId;
        this.type = type;
        this.dataList = dataList;
        this.isDistribution = isDistribution;
        init(mContext);
    }

    public SharePopupWindow(Context mContext, int type, long mId, ArrayList<String> dataList, int isDistribution, DrawPhotoBean drawPhotoBean) {//传activity是为了控制"图片生成中"的popwindow的显示位置
        super(mContext, type);
        this.mContext = mContext;
        this.mId = mId;
        this.type = type;
        this.dataList = dataList;
        this.isDistribution = isDistribution;
        this.drawPhotoBean = drawPhotoBean;
        init(mContext);
    }

    //文章的分享

    //flag:0---不含多图分享,复制链接和刷新   1---包含多图分享,复制链接和刷新,没有短信分享
    public SharePopupWindow(Context mContext, int type, ShareBean bean) {//传activity是为了控制"图片生成中"的popwindow的显示位置
        super(mContext, type);
        this.mContext = mContext;
        this.type = type;
        init(mContext);

        this.shareBean = bean;
    }

    //flag:0---不含多图分享,复制链接和刷新   1---包含多图分享,复制链接和刷新,没有短信分享
    public SharePopupWindow(Context mContext, int type, String shareJson) {//传activity是为了控制"图片生成中"的popwindow的显示位置
        super(mContext, type);
        this.mContext = mContext;
        this.type = type;
        init(mContext);
        if (StringUtils.isEmpty(shareJson)) return;
        ShareBean.Data bean = new Gson().fromJson(shareJson, ShareBean.Data.class);
        if ("true".equals(bean.isDistribution)) {
            setIsShareDistribution(1);
            isDistribution = 1;
            distributeStr = bean.distributeStr;
            setDistributorView();
        }
        if (bean != null) {
            if (StringUtils.isEmpty(bean.content) && !StringUtils.isEmpty(bean.description)) {
                bean.content = bean.description;
            }
            if (StringUtils.isEmpty(bean.linkUrl) && !StringUtils.isEmpty(bean.url)) {
                bean.linkUrl = bean.url;
            }
            if (StringUtils.isEmpty(bean.sharePicUrl) && !StringUtils.isEmpty(bean.url160x160)) {
                bean.sharePicUrl = bean.url160x160;
            }
            if (StringUtils.isEmpty(bean.sharePicUrl) && !StringUtils.isEmpty(bean.shareImgUrl)) {
                bean.sharePicUrl = bean.shareImgUrl;
            }
        }
        this.shareBean = new ShareBean();
        this.shareBean.data = bean;
    }

    private void init(Context mContext) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //社区分享特殊样式
        View mainView = null;
        //单独的社区分享UI
        if (type == SHARE_COMMUNITY) {
            mainView = inflater.from(mContext).inflate(R.layout.popwindow_community_share, null);
        } else {
            mainView = inflater.from(mContext).inflate(R.layout.popwindow_photo_share, null);
        }
        //空白区域
        v_empty = mainView.findViewById(R.id.v_empty);
        tv_title = (TextView) mainView.findViewById(R.id.tv_title);

        tv_desc = (TextView) mainView.findViewById(R.id.tv_distribution_desc);
//        if (!TextUtils.isEmpty(priceOne) && !TextUtils.isEmpty(priceTwo)) {
//                tv_desc.setVisibility(View.VISIBLE);
//                tv_desc.setText("每买一件该商品，您可以获得" + priceOne + "元鼓励金返现，您的上级可以获得" + priceTwo + "元鼓励金返现。");
//        } else {
//                tv_desc.setVisibility(View.GONE);
//
//        }

        mLLPush = (LinearLayout) mainView.findViewById(R.id.popupwindow_photo_share_push_ll);
        mTextApplyForPush = (TextView) mainView.findViewById(R.id.popupwindow_photo_share_apply_for);
        mTextViewCreatePoster = (TextView) mainView.findViewById(R.id.popupwindow_photo_share_create_poster);

        setDistributorView();
        if (type != SHARE_COMMUNITY) {
            //朋友圈多图
            ll_multi_photos_friends = (LinearLayout) mainView.findViewById(R.id.ll_multi_photos_friends);
            ll_whole_multi_photos_friends = (LinearLayout) mainView.findViewById(R.id.ll_whole_multi_photos_friends);
            v_divide_line = mainView.findViewById(R.id.v_divide_line);
            ll_logo6 = (LinearLayout) mainView.findViewById(R.id.ll_logo6);
            ll_logo8 = (LinearLayout) mainView.findViewById(R.id.ll_logo8);
        }
        //微信 朋友圈  QQ  QQ空间  新浪微博  短信  复制链接  刷新
        ll_logo1 = (LinearLayout) mainView.findViewById(R.id.ll_logo1);
        ll_logo2 = (LinearLayout) mainView.findViewById(R.id.ll_logo2);
        ll_logo3 = (LinearLayout) mainView.findViewById(R.id.ll_logo3);
        ll_logo4 = (LinearLayout) mainView.findViewById(R.id.ll_logo4);
        ll_logo5 = (LinearLayout) mainView.findViewById(R.id.ll_logo5);
        ll_logo7 = (LinearLayout) mainView.findViewById(R.id.ll_logo7);

        //取消
        tv_cancel = (TextView) mainView.findViewById(R.id.tv_cancel);

        setContentView(mainView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        setOutsideTouchable(true);
        this.setFocusable(true);
        //设置背景透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        initSdk();
        //0---不含多图分享,复制链接和刷新   1---包含多图分享,复制链接和刷新,没有短信分享
        if (type == SHARE_HOME || type == SHARE_BRAND || type == SHARE_ARTICLE || type == SHARE_ARTICLETOCMS || type == SHARE_H5) {
            ll_whole_multi_photos_friends.setVisibility(View.GONE);
            v_divide_line.setVisibility(View.GONE);
            ll_logo7.setVisibility(View.GONE);
            ll_logo8.setVisibility(View.GONE);
            ll_logo6.setVisibility(View.VISIBLE);
        } else if (type == SHARE_DETAIL) {
            ll_whole_multi_photos_friends.setVisibility(View.VISIBLE);
            v_divide_line.setVisibility(View.VISIBLE);
            ll_logo7.setVisibility(View.VISIBLE);
            ll_logo8.setVisibility(View.VISIBLE);
            ll_logo6.setVisibility(View.GONE);
            //短信分享取消后,复制链接在第二行第一位,设置marginLeft=15dp
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) ll_logo7.getLayoutParams();
            params.setMargins(UiUtils.dip2px(mContext, 15), 0, 0, 0);
            ll_logo7.setLayoutParams(params);
        } else if (type == SHARE_DETAIL_SMM_MESSAGE) {
            ll_whole_multi_photos_friends.setVisibility(View.GONE);
            v_divide_line.setVisibility(View.GONE);
            ll_logo7.setVisibility(View.GONE);
            ll_logo8.setVisibility(View.GONE);
            ll_logo6.setVisibility(View.VISIBLE);

        } else if (type == SHARE_REDPACKET) {
            ll_whole_multi_photos_friends.setVisibility(View.GONE);
            v_divide_line.setVisibility(View.GONE);
            ll_logo2.setVisibility(View.GONE);
            ll_logo3.setVisibility(View.GONE);
            ll_logo4.setVisibility(View.GONE);
            ll_logo5.setVisibility(View.GONE);
            ll_logo6.setVisibility(View.GONE);
            ll_logo7.setVisibility(View.GONE);

        }
        if (type != SHARE_COMMUNITY) {
            ll_multi_photos_friends.setOnClickListener(this);
            ll_logo6.setOnClickListener(this);
            ll_logo8.setOnClickListener(this);
        }
        v_empty.setOnClickListener(this);
        ll_logo1.setOnClickListener(this);
        ll_logo2.setOnClickListener(this);
        ll_logo3.setOnClickListener(this);
        ll_logo4.setOnClickListener(this);
        ll_logo5.setOnClickListener(this);
        ll_logo7.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        mTextApplyForPush.setOnClickListener(this);
        mTextViewCreatePoster.setOnClickListener(this);
    }

    /**
     * 设置分销和正常商品时的UI展示
     */
    private void setDistributorView() {
        if (isDistribution != 0) { //如果是分销商品
            tv_desc.setVisibility(View.VISIBLE);
            tv_title.setText("鼓励金计算明细");
            tv_title.setVisibility(View.GONE);
            setDistributorDescText(dataList, distributeStr);
            setUserIsDistributorView(true);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_desc.setVisibility(View.GONE);
            mLLPush.setVisibility(View.GONE);
        }
        //生成海报按钮默认隐藏，只在分销商品详情页展示
        mTextViewCreatePoster.setVisibility(View.GONE);
    }

    private void refreshUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.USER_INFO, new OkHttpManager.ResultCallback<UserInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(UserInfoBean response) {
                if (null != response && null != response.getData()) {
                    //保存用户信息
                    saveUserInfo(response.getData());
                }
            }

            @Override
            public void onFinish() {
                setUserIsDistributorView(false);
            }
        }, params);
    }

    /**
     * 设置用户是否为分销商时的ui展示
     *
     * @param needRefreshUserInfo 是否需要刷新用户信息
     */
    private void setUserIsDistributorView(boolean needRefreshUserInfo) {
        if (OdyApplication.isDistributor()) {
            mLLPush.setVisibility(View.GONE);
        } else {
            //如果不是分销商，先更新一下用户信息看看是否有状态改变
            if (needRefreshUserInfo) {
                //默认先显示出来
                mLLPush.setVisibility(View.VISIBLE);
                refreshUserInfo();
            } else {
                mLLPush.setVisibility(View.VISIBLE);
            }
        }
    }

    private void saveUserInfo(UserInfoBean.Data data) {
        OdyApplication.putValueByKey(Constants.USER_IS_DISTRIBUTOR, data.isDistributor());
    }

    private void setDistributorDescText(List<String> dataList, String distributeStr) {
        if (dataList != null) {
            tv_desc.setVisibility(View.VISIBLE);
            int count = dataList.size();
            if (count == 1) {
                tv_desc.setText("成为推客后，每分享一次此商品给好友形成购买即可赚取鼓励金" + dataList.get(0) + "元");
            }
            if (count >= 2) {
                tv_desc.setText("成为推客后，每分享一次此商品给好友形成购买即可赚取鼓励金" + dataList.get(0)
                        + "元，您的推荐人赚取鼓励金" + dataList.get(1) + "元");
            }
//            if (count == 3) {
//                tv_desc.setText("成为推客后，每分享一次此商品给好友形成购买即可赚取鼓励金" + dataList.get(0)
//                        + "元，上级赚取鼓励金" + dataList.get(1) + "元，上级的上级赚取鼓励金"
//                        + dataList.get(2) + "元");
//            }
        } else if (!TextUtils.isEmpty(distributeStr)) {
            tv_desc.setVisibility(View.VISIBLE);
            tv_desc.setText(distributeStr);
        } else {
            tv_desc.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.equals(v_empty)) {
            dismiss();
        } else if (view.equals(tv_cancel)) {
            dismiss();
        } else if (view.equals(ll_multi_photos_friends)) {//微信朋友圈多图分享
            getShareImg(0);
            //dismiss();
        } else if (view.equals(ll_logo1)) {//微信
            if (type == SHARE_ARTICLE) {
                getShareArticle(2);
            } else if (type == SHARE_H5 || type == SHARE_REDPACKET) {
                shareUrlRequestSucceed(shareBean, 2);
            } else {
                getShareInfo(2);
            }

            //dismiss();
        } else if (view.equals(ll_logo2)) {//朋友圈
            if (type == SHARE_ARTICLE) {
                getShareArticle(1);
            } else if (type == SHARE_H5) {
                shareUrlRequestSucceed(shareBean, 1);
            } else {
                getShareInfo(1);
            }
        } else if (view.equals(ll_logo3)) {//QQ
            if (type == SHARE_ARTICLE) {
                getShareArticle(3);
            } else if (type == SHARE_H5) {
                shareUrlRequestSucceed(shareBean, 3);
            } else {
                getShareInfo(3);
            }
            //dismiss();

        } else if (view.equals(ll_logo4)) {//QQ空间
            if (type == SHARE_ARTICLE) {
                getShareArticle(4);
            } else if (type == SHARE_H5) {
                shareUrlRequestSucceed(shareBean, 4);
            } else {
                getShareInfo(4);
            }
            //dismiss();

        } else if (view.equals(ll_logo5)) {//新浪微博
            if (type == SHARE_ARTICLE) {
                getShareArticle(5);
            } else if (type == SHARE_H5) {
                shareUrlRequestSucceed(shareBean, 5);
            } else {
                getShareInfo(5);
            }
            //dismiss();
        } else if (view.equals(ll_logo6)) {//短信分享
            if (type == SHARE_ARTICLE) {
                getShareArticle(6);
            } else if (type == SHARE_H5) {
                shareUrlRequestSucceed(shareBean, 6);
            } else {
                getShareInfo(6);
            }
            //dismiss();
        } else if (view.equals(ll_logo7)) {//复制链接
            if (type == SHARE_H5) {
                shareUrlRequestSucceed(shareBean, 7);
            } else {
                getShareInfo(7);
            }
            //dismiss();
        } else if (view.equals(ll_logo8)) {//刷新
            if (refreshUIListener != null) {
                refreshUIListener.refreshUI();
            }
            dismiss();
        } else if (view.equals(mTextApplyForPush)) {
            //申请成为租客
            JumpUtils.ToWebActivity(JumpUtils.H5, OdyApplication.H5URL + "/applyToTuike.html", WebActivity.NO_TITLE, -1, "");
            dismiss();
        } else if (view.equals(mTextViewCreatePoster)) {
            //申请生成海报
            Bitmap bitmap = util.BitmapUtil.drawNewBitmap(mContext, drawPhotoBean);
            BitmapUtil.saveBitmapFile((Activity) mContext, bitmap, Constants.IMAGEPATH).subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showShort("保存失败");
                }

                @Override
                public void onNext(String s) {
                    // 最后通知图库更新
                    mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Constants.IMAGEPATH)));
                    ToastUtils.showShort("成功保存图片到" + s);
                    dismiss();
                }
            });
        }
    }

    private RefreshUIListener refreshUIListener;

    /**
     * 设置是否是商品详情页
     *
     * @param isDistributorProductDetail 是否是商品详情页
     */
    public void setIsDistributorProductDetail(boolean isDistributorProductDetail) {
        this.isDistributorProductDetail = isDistributorProductDetail;
        if (isDistributorProductDetail && OdyApplication.isDistributor()) {
            mTextViewCreatePoster.setVisibility(View.VISIBLE);
        } else {
            mTextViewCreatePoster.setVisibility(View.GONE);
        }
    }

    public interface RefreshUIListener {
        void refreshUI();
    }

    public void setRefreshUIListener(RefreshUIListener refreshUIListener) {
        this.refreshUIListener = refreshUIListener;
    }

    /**
     * 朋友圈注册数据
     */
    private HashMap<String, Object> map_circle;

    /**
     * 微信好友注册数据
     */
    private HashMap<String, Object> map_wxFriend;

    /**
     * QQ空间注册数据
     */
    private HashMap<String, Object> map_qzone;
    /**
     * QQ好友注册数据
     */
    private HashMap<String, Object> map_qqFriend;

    /**
     * 新浪微博注册数据
     */
    private HashMap<String, Object> map_sina;

    private void initSdk() {
        // 初始化sdk分享资源
        //ShareSDK.initSDK(mContext, "9ca572cc88e6");
        ShareSDK.initSDK(mContext);
        /*map_circle = new HashMap<String, Object>();
        map_wxFriend = new HashMap<String, Object>();
        map_qzone = new HashMap<String, Object>();
        map_qqFriend = new HashMap<String, Object>();
        map_sina = new HashMap<String, Object>();

//        map_circle.put("AppId", "wx38417d832927e5cd");
//        map_circle.put("Enable", "true");
//        map_circle.put("AppSecret", "9ffef1b80892e83b45744937bd40c272");

        map_circle.put("AppId", "wxfb41bca6552d30bb");
        map_circle.put("AppSecret", "fb37c9b3070bd97b72a7a13b8f1eac21");
        map_circle.put("BypassApproval", "false");
        map_circle.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(Wechat.NAME, map_circle);


        map_wxFriend.put("AppId", "wxfb41bca6552d30bb");
        map_wxFriend.put("AppSecret", "fb37c9b3070bd97b72a7a13b8f1eac21");
        map_wxFriend.put("BypassApproval", "false");
        map_wxFriend.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, map_wxFriend);


        map_qqFriend.put("AppId", "1104778625");
        map_qqFriend.put("AppKey", "RPgVFV81i2sHMtxW");
        map_qqFriend.put("Enable", "true");
        map_qqFriend.put("ShareByAppClient", "true");
        ShareSDK.setPlatformDevInfo(QQ.NAME, map_qqFriend);

        map_qzone.put("AppId", "1104778625");
        map_qzone.put("AppKey", "RPgVFV81i2sHMtxW");
        map_qzone.put("Enable", "true");
        map_qzone.put("ShareByAppClient", "true");
        ShareSDK.setPlatformDevInfo(QZone.NAME, map_qzone);

        map_sina.put("AppKey", "93042619");
        map_sina.put("AppSecret", "de534d2258a15c1679f0a8df014df602");
        map_sina.put("RedirectUrl", "http://www.sharesdk.cn");
        map_sina.put("ShareByAppClient", "true");//true
        map_sina.put("Enable", "true");//true
        map_sina.put("ShortLinkConversationEnable", "true");
        ShareSDK.setPlatformDevInfo(SinaWeibo.NAME, map_sina);*/
    }

    /**
     * 获取文章分享信息
     */
    public void getShareArticle(final int shareTarget) {
        isLogin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);
        if (isLogin) {
            Map<String, String> params = new HashMap<>();
            params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
            params.put("id", "" + mId);// 	type为1时不用传，为2时传mpId，为3时传brandId，为4时传cms文章id，为5时传个人文章id
            showLoading();
            OkHttpManager.postAsyn(Constants.SHARE_ARTICLE, new OkHttpManager.ResultCallback<ShareBean>() {

                @Override
                public void onFinish() {
                    super.onFinish();
                    hideLoading();
                }

                @Override
                public void onError(Request request, Exception e) {
                    ToastUtils.showShort(mContext.getString(R.string.share_failed));
                }

                @Override
                public void onResponse(ShareBean response) {
                    shareUrlRequestSucceed(response, shareTarget);
                }
            }, params);
        } else {
            Bundle bd = new Bundle();
            bd.putString(Constants.LOSINGTAP, "100");
            JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
        }
    }

    /**
     * 获取多图分享信息
     */
    public void getShareImg(final int shareTarget) {
        isLogin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);
        if (isLogin) {
            Map<String, String> params = new HashMap<>();
            params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
            if (type == SHARE_DETAIL_SMM_MESSAGE) {
                params.put("type", "2");
            } else {
                params.put("type", String.valueOf(type));
            }
            params.put("paramId", "" + mId);// 	type为1时不用传，为2时传mpId，为3时传brandId，为4时传cms文章id，为5时传个人文章id
            showLoading();
            OkHttpManager.postAsyn(Constants.SHARE_IMG, new OkHttpManager.ResultCallback<ShareBean>() {

                @Override
                public void onFinish() {
                    super.onFinish();
                    hideLoading();
                }

                @Override
                public void onError(Request request, Exception e) {
                    ToastUtils.showShort(mContext.getString(R.string.share_failed));
                }

                @Override
                public void onResponse(ShareBean response) {
                    shareUrlRequestSucceed(response, shareTarget);
                }
            }, params);
        } else {
            Bundle bd = new Bundle();
            bd.putString(Constants.LOSINGTAP, "100");
            JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
        }
    }

    /**
     * 联网获取分享信息
     *
     * @param shareTarget
     */
    public void getShareInfo(final int shareTarget) {
        isLogin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);
        if (isLogin) {
            Map<String, String> params = new HashMap<>();
            params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
            if (type == SHARE_DETAIL_SMM_MESSAGE) {
                params.put("type", "2");
            } else {
                params.put("type", String.valueOf(type));
            }
            params.put("paramId", "" + mId);// 	type为1时不用传，为2时传mpId，为3时传brandId，为4时传cms文章id，为5时传个人文章id
            if (OdyApplication.SCHEME.equals("lyf")) {
                params.put("shareType", isShareDistribution + "");
            }
            showLoading();
            OkHttpManager.postAsyn(Constants.SHARE_INFO, new OkHttpManager.ResultCallback<ShareBean>() {

                @Override
                public void onFinish() {
                    super.onFinish();
                    hideLoading();
                }

                @Override
                public void onError(Request request, Exception e) {
                    ToastUtils.showShort(mContext.getString(R.string.share_failed));
                }

                @Override
                public void onResponse(ShareBean response) {
                    shareUrlRequestSucceed(response, shareTarget);
                }
            }, params);
        } else {
            Bundle bd = new Bundle();
            bd.putString(Constants.LOSINGTAP, "100");
            JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
        }
    }

    public void shareErrorBack(int status) {
//        Looper.prepare();
        if (status == 1 || status == 2) {
            ToastUtils.showShort(mContext.getString(R.string.not_to_install_wechat));
        } else if (status == 3 || status == 4) {
            ToastUtils.showShort(mContext.getString(R.string.not_to_install_qq));
        } else {
            ToastUtils.showShort(mContext.getString(R.string.not_to_install_sian));
        }
//        Looper.loop();
    }

    public void shareUrlRequestSucceed(ShareBean response, int shareTarget) {
        if (response != null && response.data != null) {
            if (shareTarget == 0) {//朋友圈多图分享
                Platform friend = ShareSDK.getPlatform(WechatMoments.NAME);
                if (friend.isClientValid()) {
                    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
                    sp.setShareType(Platform.SHARE_IMAGE);//分享类型:图片
//                            sp.setTitle(response.data.title);
//                            sp.setText(response.data.nickname);
                    sp.setImageUrl(response.data.shareImgUrl);
//                            sp.setUrl(response.data.shareUrl);
//                            sp.setTitleUrl(response.data.shareUrl);
                    friend.setPlatformActionListener(SharePopupWindow.this);// 设置分享事件回调
                    friend.share(sp);
                } else {
                    shareErrorBack(shareTarget);
                }
            } else if (shareTarget == 1) {
                Platform friend = ShareSDK.getPlatform(WechatMoments.NAME);
                if (friend.isClientValid()) {
                    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
                    sp.setShareType(Platform.SHARE_WEBPAGE);//分享类型:图片
                    sp.setTitle(response.data.title);//标题
                    sp.setText(response.data.content);//内容
                    sp.setImageUrl(response.data.sharePicUrl);//显示的分享的图片
                    sp.setUrl(response.data.linkUrl);//链接
                    sp.setTitleUrl(response.data.linkUrl == null ? "" : response.data.linkUrl);//标题图片
                    friend.setPlatformActionListener(SharePopupWindow.this);// 设置分享事件回调
                    friend.share(sp);
                } else {
                    shareErrorBack(shareTarget);
                }
            } else if (shareTarget == 2) {
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                if (wechat.isClientValid()) {
                    Wechat.ShareParams sp = new Wechat.ShareParams();
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    sp.setTitle(response.data.title);//标题
                    sp.setText(response.data.content);//内容
                    sp.setImageUrl(response.data.sharePicUrl);//显示的分享的图片
                    sp.setUrl(response.data.linkUrl);//链接
                    sp.setTitleUrl(response.data.linkUrl == null ? "" : response.data.linkUrl);//标题图片
                    wechat.setPlatformActionListener(SharePopupWindow.this);
                    wechat.share(sp);
                } else {
                    shareErrorBack(shareTarget);
                }
            } else if (shareTarget == 3) {
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                if (qq.isClientValid()) {

                    QQ.ShareParams sp = new QQ.ShareParams();
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    sp.setTitle(response.data.title);//标题
                    sp.setText(response.data.content);//内容
                    sp.setImageUrl(response.data.sharePicUrl);//显示的分享的图片
                    sp.setUrl(response.data.linkUrl);//链接
                    sp.setTitleUrl(response.data.linkUrl == null ? "" : response.data.linkUrl);//标题图片
                    qq.setPlatformActionListener(SharePopupWindow.this);
                    qq.share(sp);
                } else {
                    shareErrorBack(shareTarget);
                }
            } else if (shareTarget == 4) {//QQ空间分享图片时,不能只有setImageUrl,至少得带上setTitleUrl(即便没setTitle导致这个title无法点击跳转)!!!
                //带上setTitle 不带setTitleUrl 时,QQ空间无法调起来
                Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                if (qzone.isClientValid()) {
                    QZone.ShareParams sp = new QZone.ShareParams();
                    sp.setShareType(Platform.SHARE_WEBPAGE);
                    sp.setTitle(response.data.title);//标题
                    sp.setText(response.data.content);//内容
                    sp.setImageUrl(response.data.sharePicUrl);//显示的分享的图片
                    sp.setUrl(response.data.linkUrl);//链接
                    sp.setTitleUrl(response.data.linkUrl == null ? "" : response.data.linkUrl);//标题图片
                    qzone.setPlatformActionListener(SharePopupWindow.this);
                    qzone.share(sp);
                } else {
                    shareErrorBack(shareTarget);
                }
            } else if (shareTarget == 5) {
                SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
                sp.setShareType(Platform.SHARE_WEBPAGE);
                sp.setTitle(response.data.title);//标题
                sp.setText(response.data.content);//内容
                sp.setImageUrl(response.data.sharePicUrl);//显示的分享的图片
                sp.setUrl(response.data.linkUrl);//链接
                sp.setTitleUrl(response.data.linkUrl);//标题图片
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                sina.SSOSetting(false);
                sina.setPlatformActionListener(SharePopupWindow.this);
                sina.share(sp);
            } else if (shareTarget == 6) {//短信分享
                isShortMessage = true;
                Platform shortMessage = ShareSDK.getPlatform(ShortMessage.NAME);
                ShortMessage.ShareParams sp = new ShortMessage.ShareParams();
//                         sp.setAddress("18516513113");  //可以把收信人直接带到短信界面,不过目前不需要这样做
                sp.setText(response.data.title + "," + response.data.linkUrl);
                shortMessage.setPlatformActionListener(SharePopupWindow.this);
                shortMessage.share(sp);
            } else if (shareTarget == 7) {//复制链接
                ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(response.data.sharePicUrl);
                ToastUtils.showShort(mContext.getString(R.string.text_copy_to__cilpboard));
            }
            if (type == SHARE_H5) {
                if (shareBean != null && shareBean.data != null) {
                    if (null != shareBean.data.info) {
                        //赠送优惠券
                        RetrofitFactory.couponShare(shareBean.data.getInfo().getCouponCode(), shareBean.data.getInfo().getCouponId(),
                                shareBean.data.getInfo().getType())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ApiSubscriber<BaseRequestBean>(new SubscriberListener<BaseRequestBean>() {
                                    @Override
                                    public void onNext(BaseRequestBean bean) {
                                        EventMessage msg = new EventMessage();
                                        msg.flag = EventMessage.REFRESH_UT;
                                        EventBus.getDefault().post(msg);
                                    }
                                }));
                    }
                    if (null != shareBean.data.eCard) {
                        //赠送优惠券
                        RetrofitFactory.eCardShare(shareBean.data.eCard.cardCode)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ApiSubscriber<BaseRequestBean>(new SubscriberListener<BaseRequestBean>() {
                                    @Override
                                    public void onNext(BaseRequestBean bean) {
                                        EventMessage msg = new EventMessage();
                                        msg.flag = EventMessage.REFRESH_UT;
                                        EventBus.getDefault().post(msg);
                                    }
                                }));
                    }
                }
            }
            dismiss();
        }
    }

    /**
     * 下面三个都是分享的回调
     *
     * @param platform
     * @param i
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String distributorId = OdyApplication.getValueByKey(Constants.DISTRIBUTOR_ID, "");

        //mTODO:meiyizhi这个接口是干嘛的
        if (type == SHARE_HOME) {
            TKUtil.upload(OdyApplication.gainContext(), "click_default_share", distributorId, "", "", 2);
        } else if (type == SHARE_DETAIL) {
            TKUtil.upload(OdyApplication.gainContext(), "click_product_share", distributorId, "", "", 2);
        } else if (type == SHARE_BRAND) {
            TKUtil.upload(OdyApplication.gainContext(), "click_brand_share", distributorId, "", "", 2);
        } else if (type == SHARE_ARTICLETOCMS) {
            TKUtil.upload(OdyApplication.gainContext(), "click_info_share", distributorId, "", "", 2);
        } else if (type == SHARE_DETAIL_SMM_MESSAGE) {
            RetrofitFactory.getSharePoint("6")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        } else if (type == SHARE_H5) {
            if (null == shareBean && null == shareBean.data) {
                return;
            }
            if (!StringUtils.isEmpty(shareBean.data.refIds)) {
                RetrofitFactory.getSharePoint(shareBean.data.refIds, shareBean.data.refType, shareBean.data.activityType)
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


        }
        if (isShortMessage) {//短信分享，当有多个来源时会提前提示分享成功
            isShortMessage = false;
        } else {
            ToastUtils.showShort(mContext.getString(R.string.share_succeed));
        }
        dismiss();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtils.showShort(mContext.getString(R.string.share_failed));
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showShort(mContext.getString(R.string.share_canceled));
    }

    public void setIsShareDistribution(int isShareDistribution) {
        this.isShareDistribution = isShareDistribution;
        if (isShareDistribution == 1 && tv_title != null) {
            if (isDistribution != 0) {
                tv_title.setText("鼓励金计算明细");
            } else {
                tv_title.setText("分享得鼓励金说明");
                Drawable rightDrawable = mContext.getResources().getDrawable(R.drawable.ic_question);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                tv_title.setCompoundDrawablePadding(PxUtils.dipTopx(30));
                tv_title.setCompoundDrawables(null, null, rightDrawable, null);
            }

            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    //TODO:meiyizhi 跳转至说明页
                    JumpUtils.ToActivity(JumpUtils.COMMISSION_CODE);
                }
            });
        }
    }


}
