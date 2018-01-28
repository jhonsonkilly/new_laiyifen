package com.lyfen.android.ui.viewholder.order;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.lyfen.android.entity.network.OrderDetailEntity;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * <p> Created by qiujie on 2017/12/16/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class OrderShangjiaHolder extends BaseHolder<OrderDetailEntity.DataBean> {


//     orderpromotiontype==0   显示  merchantName
//    orderpromotiontype==1   拼团
//    orderpromotiontype==5   砍价

//  orderpromotiontype=6   抽奖

    @Bind(R.id.buy_name)
    TextView buyName;
    @Bind(R.id.common_img_1)
    ImageView commonImg1;
    @Bind(R.id.common_img_2)
    ImageView commonImg2;

    @Override
    protected View initView() {
        View inflate = View.inflate(UIUtils.getContext(), R.layout.item_order_shangjia, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void refreshView() {
        OrderDetailEntity.DataBean data = getData();
        if (data.orderPromotionType == 0) {
            commonImg1.setVisibility(View.GONE);
            commonImg2.setVisibility(View.VISIBLE);
            buyName.setText(data.merchantName);
        } else if (data.orderPromotionType == 1) {
            commonImg1.setVisibility(View.VISIBLE);
            commonImg2.setVisibility(View.GONE);
            commonImg1.setBackground(UIUtils.getDrawable(R.mipmap.img_order_pingtuan));

        } else if (data.orderPromotionType == 6) {
            commonImg1.setBackground(UIUtils.getDrawable(R.mipmap.img_order_choujiang));
            commonImg1.setVisibility(View.VISIBLE);

            commonImg2.setVisibility(View.GONE);
        } else if (data.orderPromotionType == 5) {
            commonImg1.setBackground(UIUtils.getDrawable(R.mipmap.img_order_kanjia));
            commonImg1.setVisibility(View.VISIBLE);
            commonImg2.setVisibility(View.GONE);

        }

    }
}
