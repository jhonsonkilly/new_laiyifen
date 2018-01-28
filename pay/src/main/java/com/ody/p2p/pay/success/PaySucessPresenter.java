package com.ody.p2p.pay.success;

/**
 * Created by ${tang} on 2016/12/12.
 * 目前来伊份使用，德生不用
 *
 */

public interface PaySucessPresenter {
    void payInfo(String orderCode);

    void guessYouLike(int pageNo);

    void getAd(String adCode);
}
