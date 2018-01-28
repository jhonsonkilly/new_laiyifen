package com.ody.p2p.pay.success;

import com.ody.p2p.base.BaseView;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.recmmend.Recommedbean;

/**
 * Created by ${tang} on 2016/12/12.
 */

public interface PaySuccessView extends BaseView {
    void payInfo(PaySuccessInfoBean response);

    void likeList(Recommedbean recommedbean);

    void initAd(FuncBean bean);
}
