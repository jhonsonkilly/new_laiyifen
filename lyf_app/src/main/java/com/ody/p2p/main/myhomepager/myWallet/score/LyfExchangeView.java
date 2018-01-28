package com.ody.p2p.main.myhomepager.myWallet.score;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.main.myhomepager.bean.LyfExchangeBean;
import com.ody.p2p.main.myhomepager.bean.LyfRuleBean;

/**
 * Created by caishengya on 2017/3/21.
 */

public interface LyfExchangeView extends BaseView {

    void initRule(LyfRuleBean bean);

    void exchangeYPeas(LyfExchangeBean bean);

    void toast(String msg);
}
