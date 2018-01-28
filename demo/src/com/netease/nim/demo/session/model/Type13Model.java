package com.netease.nim.demo.session.model;

import com.netease.nim.demo.discount.model.YHQModel;
import com.netease.nim.demo.yidiancard.model.YKD001Model;

/**
 * Created by jasmin on 2017/11/3.
 */

public class Type13Model {


    /**
     * yidou : {"money":"¥ 30","infoTitle":"全场通用券","cardNo":"卡号:8080 2020 4040 8888","time":"有效期 2017.12.30","type":"优惠券","title":"瞌睡会员唤醒满58元使用","passwd":"密码:123456","status":"待领取"}
     */

    private YHQModel.DataBean.CanUseCouponListBean yidou;

    public YHQModel.DataBean.CanUseCouponListBean getYidou() {
        return yidou;
    }

    public void setYidou(YHQModel.DataBean.CanUseCouponListBean yidou) {
        this.yidou = yidou;
    }

}
