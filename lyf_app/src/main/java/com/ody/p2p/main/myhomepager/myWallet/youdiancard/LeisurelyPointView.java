package com.ody.p2p.main.myhomepager.myWallet.youdiancard;

import com.ody.p2p.main.myhomepager.bean.LeisurelyPointBean;
import com.ody.p2p.pay.payMode.payOnline.PayTypeListBean;

/**
 * Created by caishengya on 2017/2/28.
 */

public interface LeisurelyPointView {

    void initLeisurelyPoint(LeisurelyPointBean bean);

    void initPayType(PayTypeListBean bean);
}
