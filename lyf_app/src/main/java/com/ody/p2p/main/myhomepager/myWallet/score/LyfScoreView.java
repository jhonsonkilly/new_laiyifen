package com.ody.p2p.main.myhomepager.myWallet.score;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.main.myhomepager.bean.LyfRuleBean;
import com.ody.p2p.main.myhomepager.bean.LyfScoreBean;
import com.ody.p2p.main.myhomepager.bean.LyfScoreDetailsBean;

/**
 * Created by caishengya on 2017/3/20.
 */

public interface LyfScoreView extends BaseView {

    void initScore(LyfScoreBean bean);//初始化积分

    void initListDetails(LyfScoreDetailsBean bean);

    void initRule(LyfRuleBean bean);
}
