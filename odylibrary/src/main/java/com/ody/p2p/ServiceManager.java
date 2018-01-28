package com.ody.p2p;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.EServiceContact;
import com.google.gson.Gson;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.entity.YiModel;
import com.ody.p2p.eventbus.EventMessage;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.home.QIYuBean;
import com.ody.p2p.utils.AutoFitUtils;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.qiyukf.unicorn.api.msg.UnicornMessage;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.security.Policy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxs on 2017/2/14.
 */
public class ServiceManager {


    private static ServiceManager serviceManager;

    public static synchronized ServiceManager getInstance() {


        if (serviceManager == null) {

            serviceManager = new ServiceManager();
        }
        return serviceManager;
    }

    public void doPolicy(ServicePolicy policy, int type,Context context) {

        policy.doServicePolicy(type,context);
    }

    //兼容旗鱼和阿里客服的兼容
    public void doPolicy(ServicePolicy policy,Context context) {


        this.doPolicy(policy, -1,context);
    }


}
