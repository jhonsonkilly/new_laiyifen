package com.lyfen.android.ui.viewholder.order;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.laiyifen.lyfframework.network.RestRetrofit;

import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.app.OrderApi;
import com.lyfen.android.entity.network.CommonEntity;

import com.lyfen.android.entity.network.OrderDetailEntity;
import com.lyfen.android.ui.dialog.DialogCancle;
import com.lyfen.android.utils.RouterHelper;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.R;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;



/**
 * Created by mac on 2017/12/20.
 */

public class OrderStateHolder  extends BaseHolder<OrderDetailEntity.DataBean> {

    @Bind(R.id.common_btn_1)
    TextView commonBtn1;
    @Bind(R.id.common_btn_2)
    TextView commonBtn2;
    @Bind(R.id.common_btn_3)
    TextView commonBtn3;

    @Bind(R.id.common_btn_4)
    TextView commonBtn4;

    @Bind(R.id.common_btn_5)
    TextView commonBtn5;

    @Bind(R.id.common_btn_6)
    TextView commonBtn6;
    private OrderDetailEntity.DataBean data;

    Context context;

    public OrderStateHolder(Context context){
        this.context=context;
    }

    @Override
    protected View initView() {
        View inflate = View.inflate(UIUtils.getContext(), R.layout.item_order_state, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void refreshView() {
        data = getData();
        if (data.orderStatus == 1) {//代付款  显示取消订单cancal 1显示   去支付

            commonBtn1.setVisibility(data.canCancel == 1 ? View.VISIBLE : View.GONE);
            commonBtn3.setVisibility(View.GONE);
            commonBtn4.setVisibility(View.GONE);
            commonBtn5.setVisibility(View.GONE);
            commonBtn6.setVisibility(View.VISIBLE);

        } else if (data.orderStatus == 2) {//代发货  不显示所有按钮

            commonBtn1.setVisibility(View.GONE);
            commonBtn3.setVisibility(View.GONE);
            commonBtn4.setVisibility(View.GONE);
            commonBtn5.setVisibility(View.GONE);
            commonBtn6.setVisibility(View.GONE);

        } else if (data.orderStatus == 3) {//代收货  查看物流 确认收货  & 详情 申请售后canAfter =1

            commonBtn1.setVisibility(View.GONE);
            commonBtn3.setVisibility(View.VISIBLE);
            commonBtn4.setVisibility(View.GONE);
            commonBtn5.setVisibility(View.VISIBLE);
            commonBtn6.setVisibility(View.GONE);
        } else if (data.orderStatus == 4) {//代评价   去评价canComment 1   删除订单canDelete 1     查看物流  &详情 申请售后canAfter

            commonBtn1.setVisibility(View.GONE);
            commonBtn3.setVisibility(View.GONE);
            commonBtn4.setVisibility(data.canComment == 1 ? View.VISIBLE : View.GONE);
            commonBtn5.setVisibility(View.GONE);
            commonBtn6.setVisibility(View.GONE);
        } else if (data.orderStatus == 8) {//完成   删除    申请售后canAfter 1    查看物流 常显   去评价 commentstatus 1
//               & 详情 再次购买   显示 canAfter！=1  || commentstatus！=1


            commonBtn1.setVisibility(View.GONE);
            commonBtn3.setVisibility(View.VISIBLE);
            commonBtn4.setVisibility(data.commentStatus == 1 ? View.VISIBLE : View.GONE);
            commonBtn5.setVisibility(View.GONE);
            commonBtn6.setVisibility(View.GONE);
        }
        commonBtn2.setVisibility(data.canDelete == 1 ? View.VISIBLE : View.GONE);

        //            取消订单
        commonBtn1.setOnClickListener(view -> {


            RouterHelper.getHelper().openWeb("/order/cancelOrderReason.html?orderCode=" + data);


        });
//删除
        commonBtn2.setOnClickListener(view -> {

            DialogCancle dialogCancle = new DialogCancle(context, R.layout.dialog_common_canle);
            dialogCancle.show();
            dialogCancle.findViewById(R.id.common_tv_2).setOnClickListener(view1 -> {

                Map<String, String> stringStringMap = NetWorkMap.defaultMap();
                stringStringMap.put("orderCode", data.orderCode);
                stringStringMap.put("companyId", "30");

                RestRetrofit.getBeanOfClass(OrderApi.class).deleteOrder(stringStringMap).subscribe(new Observer<CommonEntity>() {
                    @Override
                    public void onCompleted() {

                        UIUtils.showToastSafe("删除成功");
                        dialogCancle.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        UIUtils.showToastSafe("删除失败");
                    }

                    @Override
                    public void onNext(CommonEntity commonEntity) {
                        if (commonEntity.code == 0) {
                            //fragmentActivity.removeIndex(data);
                        }

                    }
                });


            });


        });

        commonBtn3.setOnClickListener(view -> {
            RouterHelper.getHelper().openWeb("/my/logistics-information.html?orderCode=" + data.orderCode);
        });
//            评价
        commonBtn4.setOnClickListener(view -> RouterHelper.getHelper().openWeb("/my/evaluate.html?mpId=" + UIUtils.getContext()));

//            确认收货

        commonBtn5.setOnClickListener(view -> {
            DialogCancle dialogCancle = new DialogCancle(context, R.layout.dialog_common_canle);
            dialogCancle.show();

            dialogCancle.findViewById(R.id.common_tv_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        });

//            去支付
        commonBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.showToastSafe("去支付 原生收银台");
            }
        });

    }
}
