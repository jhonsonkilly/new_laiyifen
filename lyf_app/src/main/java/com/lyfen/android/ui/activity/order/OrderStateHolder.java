package com.lyfen.android.ui.activity.order;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.app.OrderApi;
import com.lyfen.android.app.ShoppingCartApi;
import com.lyfen.android.entity.network.BuyAnginEntity;
import com.lyfen.android.entity.network.CommonEntity;
import com.lyfen.android.entity.network.OrderDetailEntity;
import com.lyfen.android.entity.network.dialog.BuyAgainEntity;
import com.lyfen.android.ui.dialog.DialogBuyAngin;
import com.lyfen.android.ui.dialog.DialogCancle;
import com.lyfen.android.utils.RouterHelper;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.Constants;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;


/**
 * Created by mac on 2017/12/20.
 */

public class OrderStateHolder extends BaseHolder<OrderDetailEntity.DataBean> {

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
    @Bind(R.id.common_btn_7)
    TextView commonBtn7;
    private OrderDetailEntity.DataBean data;

    Context context;

    public OrderStateHolder(Context context) {
        this.context = context;
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


            commonBtn7.setVisibility(data.canAfterSale != 1 || data.commentStatus != 1 ? View.VISIBLE : View.GONE);

        }
        commonBtn2.setVisibility(data.canDelete == 1 ? View.VISIBLE : View.GONE);
        //commonBtn7.setVisibility(View.VISIBLE);

//            取消订单
        commonBtn1.setOnClickListener(view -> {


            RouterHelper.getHelper().openWeb("/order/cancelOrderReason.html?orderCode=" + data.orderCode);


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

                        OrderDetailActivity act = (OrderDetailActivity) context;

                        act.finish();

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
        commonBtn4.setOnClickListener(view -> RouterHelper.getHelper().openWeb("/my/evaluate.html?mpId=" + data.orderCode));

//            确认收货

        commonBtn5.setOnClickListener(view -> {
            DialogCancle dialogCancle = new DialogCancle(context, R.layout.dialog_common_canle);
            dialogCancle.show();
            TextView textView = (TextView) dialogCancle.findViewById(R.id.tv_title);
            textView.setText("您确定收货吗？");

            dialogCancle.findViewById(R.id.common_tv_2).setOnClickListener(view12 -> {

                Map<String, String> stringStringMap = NetWorkMap.defaultMap();
                stringStringMap.put("orderCode", data.orderCode);

                RestRetrofit.getBeanOfClass(OrderApi.class).confirm(stringStringMap)
                        .subscribe(new Observer<CommonEntity>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(CommonEntity commonEntity) {

                                if (commonEntity.code == 0) {
                                    //fragmentActivity.initData(1);
                                } else {
                                    UIUtils.showToastSafe(commonEntity.message);
                                }

                            }
                        });


            });

        });

//            去支付
        commonBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(Constants.ORDER_ID, data.orderCode);
                bundle.putString(Constants.USER_ID, data.userId);
                bundle.putString(Constants.ORDER_MONEY, data.paymentAmount);
                JumpUtils.ToActivity(JumpUtils.PAYONLINE, bundle);
                //UIUtils.showToastSafe("去支付 原生收银台");

            }
        });

        commonBtn7.setOnClickListener(view -> {
            Map<String, String> stringStringMap = NetWorkMap.defaultMap();
            stringStringMap.put("orderCode", data.orderCode);

            RestRetrofit.getBeanOfClass(OrderApi.class).getOrderStockState(stringStringMap)
                    .subscribe(new Observer<BuyAgainEntity>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BuyAgainEntity commonEntity) {
                            if (commonEntity.code == 0) {
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt(Constants.GO_MAIN, 3);
//                                    JumpUtils.ToActivity(JumpUtils.MAIN, bundle);


                                DialogBuyAngin dialogBuyAngin = new DialogBuyAngin(context, R.layout.dialog_common_buy_angin);
                                if (null != commonEntity && null != commonEntity.data && null != commonEntity.data.availableProductList) {
                                    dialogBuyAngin.setData(commonEntity.data.availableProductList);
                                    dialogBuyAngin.show();
                                }
                                dialogBuyAngin.findViewById(R.id.common_tv_2).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            ArrayList<BuyAnginEntity> objects = new ArrayList<>();

                                            for (int i = 0; i < data.childOrderList.get(0).packageList.get(0).productList.size(); i++) {


                                                BuyAnginEntity buyAgainEntity = new BuyAnginEntity();
                                                buyAgainEntity.mpId = data.childOrderList.get(0).packageList.get(0).productList.get(i).mpId + "";
                                                buyAgainEntity.num = data.childOrderList.get(0).packageList.get(0).productList.get(i).num + "";

                                                objects.add(buyAgainEntity);
                                            }

                                            Map<String, String> stringStringMap1 = NetWorkMap.defaultMap();
                                            stringStringMap1.put("skus", com.laiyifen.lyfframework.utils.GsonUtils.toJson(objects));

                                            RestRetrofit.getBeanOfClass(ShoppingCartApi.class).addItem2Cart(stringStringMap1)
                                                    .subscribe(new Observer<CommonEntity>() {
                                                        @Override
                                                        public void onCompleted() {

                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {

                                                        }

                                                        @Override
                                                        public void onNext(CommonEntity commonEntity) {

                                                            if (commonEntity.code == 0) {
                                                                dialogBuyAngin.dismiss();
                                                                Bundle bundle = new Bundle();
                                                                bundle.putInt(Constants.GO_MAIN, 3);
                                                                JumpUtils.ToActivity(JumpUtils.MAIN, bundle);

                                                            } else {
                                                                UIUtils.showToastSafe(commonEntity.message);
                                                            }

                                                        }
                                                    });
                                        } catch (Exception e) {

                                        }


                                    }
                                });


                            } else {
                                UIUtils.showToastSafe(commonEntity.message);
                            }

                        }
                    });
        });

    }

}
