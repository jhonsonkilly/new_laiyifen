import android.content.Context;

import com.odianyun.sharesdksharedemo.R;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.eventbus.JsEventMessage;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 第三方授权
 * Created by pzy on 2017/2/21.
 */
public class ThirdAuthorization {

    public static void getAuthorization(final Context context, final String type/*, PlatformActionListener listener*/) {
        ShareSDK.initSDK(context);
        Platform platform;
        if (type.equals(JsEventMessage.FUNCTION_BINDTHIRD_QQ)) {
            platform = ShareSDK.getPlatform(QQ.NAME);
        } else {
            platform = ShareSDK.getPlatform(Wechat.NAME);
        }
        platform.SSOSetting(false); // 设置false表示使用SSO授权方式
//        platform.setPlatformActionListener(listener);//设置回调监听
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String token = platform.getDb().getToken();
                String openId = platform.getDb().getUserId();

                //登录网关	1、QQ、2、微信
                if (type.equals(JsEventMessage.FUNCTION_BINDTHIRD_QQ)) {
                    bindThirdPlatform(1, token,openId,"1150085386");
//                    bindThirdPlatform(1, token,openId,util.ShareUtiles.getFromAssets(context,"QQ","AppId"));
                } else {
                    bindThirdPlatform(2, token,openId,"wxfac2b15cf7763717");
//                    bindThirdPlatform(2, token,openId,util.ShareUtiles.getFromAssets(context,"Wechat","AppId"));
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                ToastUtils.showShort("授权失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtils.showShort("取消绑定");
            }
        });
        if (platform.isAuthValid()) {
            platform.removeAccount(true);
        }
        if (platform.isClientValid()) {
            platform.showUser(null);// 获取到用户信息
        } else {
            if (type.equals(JsEventMessage.FUNCTION_BINDTHIRD_QQ)) {
                ToastUtils.showShort(context.getString(R.string.not_to_install_qq));
            } else {
                ToastUtils.showShort(context.getString(R.string.not_to_install_wechat));
            }
        }
    }

    /**
     * 用户登录后、绑定第三方账户
     * @param gateway
     * @param token
     */
    private static void bindThirdPlatform(int gateway, String token,String openId,String appid) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("gateway", gateway + "");
        params.put("openid", openId);
        params.put("appid", appid);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.BIND_THIRD_PLATFORM, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                ShareSDK.stopSDK();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response == null) {
                    return;
                }
                if ("0".equals(response.code)) {
                    EventMessage message = new EventMessage();
                    message.flag = EventMessage.REFRESH_UT;
                    EventBus.getDefault().post(message);
                }
            }
        }, params);
    }
}
