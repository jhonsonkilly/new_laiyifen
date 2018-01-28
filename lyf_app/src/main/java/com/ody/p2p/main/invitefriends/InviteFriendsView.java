package com.ody.p2p.main.invitefriends;

import com.ody.p2p.UserInfoBean;
import com.ody.p2p.base.BaseView;
import com.ody.p2p.main.CurrDistributorBean;
import com.ody.p2p.retrofit.adviertisement.AdData;

/**
 * Created by pzy on 2017/10/12.
 */

public interface InviteFriendsView extends BaseView {

    void backUser(UserInfoBean.Data data);

    void initAdData(AdData adData);

    void backCurrdistri(CurrDistributorBean.Data response);
}
