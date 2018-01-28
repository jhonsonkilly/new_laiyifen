package com.ody.p2p.main.myhomepager.myWallet.score;

/**
 * Created by caishengya on 2017/3/20.
 */

public interface LyfScorePresenter {

    void getLyfScore();//获取当前用户积分

    void getList(String pageNO, String pointStatus);//获取积分明细列表

    void getExchangeRule();//获取兑换伊豆规则
}
