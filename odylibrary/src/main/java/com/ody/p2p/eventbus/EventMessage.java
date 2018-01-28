package com.ody.p2p.eventbus;

import android.widget.TextView;

import com.ody.p2p.entity.YiModel;
import com.ody.p2p.retrofit.home.AppHomePageBean;

/**
 * Created by lxs on 2016/10/18.
 */
public class EventMessage {

    public final static int REFRESH_UT = 1000;          //刷新ut
    public final static int REFRESH_H5 = 1000;          //刷新ut
    public final static int FINISH_ACTIVITY = 1001;     //关闭activity
    public final static int PAY_STATUS = 1002;          //支付状态
    public final static int JUMP_TO_LOGIN = 1003;       //跳转到登录界面
    public final static int MSG_REFRESH = 1004;         //刷新消息
    public final static int CHANGE_AREA = 1005;    //刷新区域；
    public final static int FRUSH_FILTER = 1006;
    public static final int FRUSH_FILTER_STATUS = 1007;
    public static final int IS_SP = 1008;
    public static final int FRUSH_FILTER_SCROLLX = 1009;
    public static final int FIRST_LOGIN = 1010;

    public static final int REDNUM = 1012;

    public static final int YUNXUN = 1023;

    public static final int JUMPIM = 1024;
    public String h5Url;
    public int flag;
    public int leftPositon;
    public int leftOffset;
    public long millis;
    public AppHomePageBean.HomeData homeData;
    public int redNumber;

    public YiModel yiModel;

    public final static int REFRESH = 1010;


}
