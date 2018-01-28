package com.ody.p2p.main;

import com.ody.p2p.webactivity.UrlBean;
import com.ody.p2p.webactivity.WebActivity;

import dsshare.ShareBean;

/**
 * Created by lxs on 2017/2/9.
 */
public class LyfWebActivity extends WebActivity{

    @Override
    public void share(UrlBean bean) {
        super.share(bean);
        ShareBean shareBean = new ShareBean();
        ShareBean.Data data = new ShareBean.Data();
        data.linkUrl = bean.url;
        data.content = bean.description;
        data.title = bean.title;
        data.sharePicUrl = bean.pic;
        shareBean.data = data;
        //   分销的不做
//        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
//            SharePopupWindow popupWindow = new SharePopupWindow(this, SharePopupWindow.SHARE_H5, shareBean);
//            popupWindow.showAtLocation(iv_menu_more, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//        } else {
//            JumpUtils.ToActivity(JumpUtils.LOGIN);
//        }
    }
}
