package com.lyfen.android.ui.viewholder.order;


import android.view.View;
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

public class OrderPeisongHolder extends BaseHolder<OrderDetailEntity.DataBean> {
    @Bind(R.id.text_peisong)
    TextView textPeisong;
    @Bind(R.id.text_yunfei)
    TextView textYunfei;
    @Bind(R.id.tv_pay)
    TextView tvPay;

    @Override
    protected View initView() {
        View inflate = View.inflate(UIUtils.getContext(), R.layout.item_order_peisong, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void refreshView() {
        OrderDetailEntity.DataBean data = getData();
        textPeisong.setText(data.deliveryModeName);

        textYunfei.setText("运费" + "¥" + data.orderDeliveryFeeAccounting + "");
        tvPay.setText(data.payMethod);

    }
}
