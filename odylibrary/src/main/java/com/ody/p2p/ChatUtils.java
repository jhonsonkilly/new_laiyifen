package com.ody.p2p;

import android.content.Context;
import android.content.Intent;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.EServiceContact;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

/**
 * Created by lxs on 2017/2/14.
 */
public class ChatUtils {

    public static void callService(final Context context){
        String userId = OdyApplication.getValueByKey(Constants.BC_USER_ID,"");
        if (StringUtils.isEmpty(userId)){
            JumpUtils.ToActivity(JumpUtils.LOGIN);
            return;
        }
        String password = OdyApplication.getValueByKey(Constants.BC_PASS,"");
        final String receiverId = OdyApplication.getValueByKey(Constants.RECEIVER_ID,"");
        String app_key = OdyApplication.getValueByKey(Constants.APP_KEY,"");
        final YWIMKit mIMKit = YWAPI.getIMKitInstance(userId, app_key);
        IYWLoginService loginService = mIMKit.getLoginService ();
        YWLoginParam loginParam = YWLoginParam.createLoginParam (userId, password);
        loginService.login (loginParam, new IWxCallback() {
            @Override
            public void onSuccess(Object... arg0) {
                final EServiceContact contact = new EServiceContact(receiverId, 0);
                Intent intent = mIMKit.getChattingActivityIntent(contact);
                context.startActivity(intent);
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                ToastUtils.showShort(description);
            }
        });
    }

}
