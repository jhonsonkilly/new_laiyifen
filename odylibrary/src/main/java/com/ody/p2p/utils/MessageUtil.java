package com.ody.p2p.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.views.basepopupwindow.MenuPopBean;
import com.ody.p2p.views.basepopupwindow.SelectMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ody on 2016/9/8.
 */
public class MessageUtil {
//    public static String[] menuStr = {"消息", "扫码"};
//    public static int[] menuRes = {R.drawable.ic_message, R.drawable.ic_scan};
//    public static String[] meunSkip = {"message", "sweep"};
//    public static String[] menuStr = {"首页","消息"};
//    public static int[] menuRes = {R.drawable.ic_homepage,R.drawable.ic_message};
//    public static String[] meunSkip = {"main","message"};
    public static String[] menuStr = {"首页"};
    public static int[] menuRes = {R.drawable.ic_homepage};
    public static String[] meunSkip = {"main"};

    public static void setMegScan(Context context, View view) {
        setMessageutile(context, view, menuStr, meunSkip, menuRes);
    }

    /**
     * @param context 上下文对象
     * @param view    显示view的下方
     * @param menuStr 文描
     * @param skipTo  跳转的落地
     * @param menuRes 图片
     */
    public static void setMessageutile(Context context, View view, String[] menuStr, final String[] skipTo, int[] menuRes) {
        if (menuStr.length != skipTo.length && menuStr.length != menuRes.length) {
            return;
        }
        final List<MenuPopBean> popList = new ArrayList<>();
        for (int i = 0; i < menuStr.length; i++) {
            MenuPopBean bean = new MenuPopBean();
            bean.content = menuStr[i];
            bean.picRes = menuRes[i];
            popList.add(bean);
        }
        SelectMenu menu = new SelectMenu(context, popList);
        menu.setClickSelectListener(new SelectMenu.clickSelectMenuListener() {
            @Override
            public void click(int position) {
                if (position == 0){
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.GO_MAIN,0);
                    JumpUtils.ToActivity(JumpUtils.MAIN,bundle);
                    return;
                }
                if (position == 1) {
                    boolean isLogin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);//登录状态
                    if (!isLogin) {//消息中心需要登陆才能进
                        JumpUtils.ToActivity(JumpUtils.LOGIN);
                        return;
                    }
                    JumpUtils.ToActivity(skipTo[position]);
                }

            }
        });
        menu.showAsDropDown(view, PxUtils.dipTopx(-65), 0);
    }
}
