package com.lyfen.android.ui.activity.order;


import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.lyfen.android.entity.network.OrderDetailEntity;
import com.lyfen.android.utils.RouterHelper;
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

public class OrderAddressHolder extends BaseHolder<OrderDetailEntity.DataBean> {

    @Bind(R.id.order_state)
    TextView orderState;
    @Bind(R.id.order_reason)
    TextView orderReason;
    @Bind(R.id.tx_name)
    TextView txName;
    @Bind(R.id.tx_location)
    TextView txLocation;
    @Bind(R.id.tx_phone)
    TextView txPhone;
    @Bind(R.id.icon_next)
    ImageView icon_next;

    CountDownTimer time;

    @Override
    protected View initView() {
        View inflate = View.inflate(UIUtils.getContext(), R.layout.item_personal_mes, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void refreshView() {
        OrderDetailEntity.DataBean data = getData();
        orderState.setText(data.orderStatusName);
        txName.setText(data.receiver.receiverName);
        txLocation.setText(data.receiver.getLocation());
        txPhone.setText(data.receiver.receiverMobile);
        if (data.orderStatus == 1) {//代付款     t:等待付款   cancletimest
            icon_next.setVisibility(View.GONE);
            time = new CountDownTimer(Long.parseLong(data.cancelTime * 1000 + ""), 1000) {

                public void onTick(final long millisUntilFinished) {

                    // holder12.tx_count.setText(getTimeShort(millisUntilFinished / 1000));
                    orderReason.setText("您的订单已提交请在" + getTimeShort(millisUntilFinished / 1000) + "完成支付,超时订单自动取消");
                }

                public void onFinish() {


                }
            };
            time.start();


        } else if (data.orderStatus == 2) {//代发货     t:待发货           M:您的点单已付款，等待卖家发货。
            icon_next.setVisibility(View.GONE);
            orderReason.setText("您的点单已付款，等待卖家发货");
        } else if (data.orderStatus == 3) {//代收货      t:待发货   M:你的订单已发出，等待卖家收货 +  singnDescroibe

            icon_next.setVisibility(View.GONE);
            orderReason.setText("你的订单已发出，等待卖家收货" + data.signDescribe);

        } else if (data.orderStatus == 4) {//代评价       t:待评价  M;您已收货，给个评价吧。  小箭头 icon 评价H5
            orderReason.setText("您以收货,给个评价吧");
            icon_next.setVisibility(View.VISIBLE);
            icon_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RouterHelper.getHelper().openWeb("/my/evaluate.html?mpId=" + UIUtils.getContext());
                }
            });

        } else if (data.orderStatus == 8) {//完成       t: 交易完成  M:订单交成功，感谢您的购买。  小箭头，查看物流

            orderReason.setText("订单交成功，感谢您的购买");
            icon_next.setVisibility(View.VISIBLE);
            icon_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RouterHelper.getHelper().openWeb("/my/logistics-information.html?orderCode=" + data.orderCode);
                }
            });
        } else if (data.orderStatus == 10) {
            orderReason.setText("取消原因: " + data.orderCanceOperateTypeContext);
            icon_next.setVisibility(View.GONE);
        }


    }

    public String getTimeShort(long i) {

        long h = i / 3600;
        long m = (i % 3600) / 60;
        long s = (i % 3600) % 60;


        String dateString = +h + ":" + m + ":" + s;

        if (m < 10) {
            dateString = +h + ":" + "0" + m + ":" + s;
        }
        if (s < 10) {
            dateString = +h + ":" + m + ":" + "0" + s;
        }
        if (m < 10 && s < 10) {

            dateString = +h + ":" + "0" + m + ":" + "0" + s;
        }

        return dateString;
    }
}
