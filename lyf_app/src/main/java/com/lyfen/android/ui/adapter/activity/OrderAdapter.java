package com.lyfen.android.ui.adapter.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.laiyifen.lyfframework.network.RestRetrofit;
import com.laiyifen.lyfframework.utils.FrescoUtils;
import com.lyfen.android.app.NetWorkMap;
import com.lyfen.android.app.OrderApi;
import com.lyfen.android.app.ShoppingCartApi;
import com.lyfen.android.entity.network.BuyAnginEntity;
import com.lyfen.android.entity.network.CommonEntity;
import com.lyfen.android.entity.network.activity.OrderListEntity;
import com.lyfen.android.entity.network.dialog.BuyAgainEntity;
import com.lyfen.android.ui.dialog.DialogBuyAngin;
import com.lyfen.android.ui.dialog.DialogCancle;
import com.lyfen.android.ui.fragment.MyOrderListFragment;
import com.lyfen.android.utils.RouterHelper;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.Constants;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;


/**
 * <p> Created by qiujie on 2017/12/13/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.LvListHolder> {

    List<OrderListEntity.DataEntity.OrderListEntity2> orderListEntity2s;
    int postion;

    MyOrderListFragment fragmentActivity;

    public OrderAdapter(int mPosition, MyOrderListFragment activity) {
        this.postion = mPosition;
        this.fragmentActivity = activity;

    }

    @Override
    public LvListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_orderlist, null);
        return new LvListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(LvListHolder holder, int position) {
        holder.setData(orderListEntity2s.get(position));

    }

    @Override
    public int getItemCount() {
        return orderListEntity2s.size();
    }

    public void setData(List<OrderListEntity.DataEntity.OrderListEntity2> orderList) {
        orderListEntity2s = orderList;

    }

    public class LvListHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.common_tv_1)
        TextView commonTv1;
        @Bind(R.id.common_tv_2)
        TextView commonTv2;

        @Bind(R.id.common_tv_3)
        TextView commonTv3;
        @Bind(R.id.common_tv_4)
        TextView commonTv4;

//        @Bind(R.id.common_img_1)
//        SimpleDraweeView commonImg1;
//        @Bind(R.id.common_img_2)
//        SimpleDraweeView commonImg2;
//        @Bind(R.id.common_img_3)
//        SimpleDraweeView commonImg3;


        @Bind(R.id.ll_content)
        LinearLayout ll_contentl;


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

        @Bind(R.id.common_img_4)
        ImageView commonImg4;
        @Bind(R.id.common_img_5)
        ImageView commonImg5;

        public LvListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(OrderListEntity.DataEntity.OrderListEntity2 orderListEntity2) {


            if (orderListEntity2.orderPromotionType == 0) {
                commonImg4.setVisibility(View.GONE);
                commonImg5.setVisibility(View.VISIBLE);
                commonTv1.setText(orderListEntity2.merchantName);
            } else if (orderListEntity2.orderPromotionType == 1) {
                commonImg4.setVisibility(View.VISIBLE);
                commonImg5.setVisibility(View.GONE);
                commonImg4.setBackground(UIUtils.getDrawable(R.mipmap.img_order_pingtuan));
                commonTv1.setText("");

            } else if (orderListEntity2.orderPromotionType == 6) {
                commonImg4.setBackground(UIUtils.getDrawable(R.mipmap.img_order_choujiang));
                commonImg4.setVisibility(View.VISIBLE);
                commonTv1.setText("");


                commonImg5.setVisibility(View.GONE);
            } else if (orderListEntity2.orderPromotionType == 5) {
                commonImg4.setBackground(UIUtils.getDrawable(R.mipmap.img_order_kanjia));
                commonImg4.setVisibility(View.VISIBLE);
                commonImg5.setVisibility(View.GONE);
                commonTv1.setText("");


            }


            commonTv2.setText(orderListEntity2.orderStatusName);
            commonTv3.setText("共计" + orderListEntity2.totalCount + "件商品");
            commonTv4.setText("¥" + orderListEntity2.amount);

            if (null != orderListEntity2.productList) {
//                if (orderListEntity2.productList.size() >= 1) {
//                    commonImg1.setVisibility(View.VISIBLE);
//                    FrescoUtils.displayUrl(commonImg1, orderListEntity2.productList.get(0).picUrl);
//                }
//                if (orderListEntity2.productList.size() > 2) {
//                    commonImg2.setVisibility(View.VISIBLE);
//                    FrescoUtils.displayUrl(commonImg2, orderListEntity2.productList.get(1).picUrl);
//                }
//                if (orderListEntity2.productList.size() > 3) {
//                    commonImg3.setVisibility(View.VISIBLE);
//                    FrescoUtils.displayUrl(commonImg3, orderListEntity2.productList.get(2).picUrl);
//                }


                ll_contentl.removeAllViews();

                for (int i = 0; i < orderListEntity2.productList.size(); i++) {

                    if (i < 3) {


                        View inflate = View.inflate(UIUtils.getContext(), R.layout.item_order_img, null);
                        SimpleDraweeView viewById = (SimpleDraweeView) inflate.findViewById(R.id.img_product);
                        viewById.setVisibility(View.VISIBLE);
                        FrescoUtils.displayUrl(viewById, orderListEntity2.productList.get(i).picUrl);

                        ll_contentl.addView(inflate);
                    }


                }
//                for (int i = 0; i < orderListEntity2.productList.size(); i++) {
//                    if (i == 0) {
//                        commonImg1.setVisibility(View.VISIBLE);
//                        FrescoUtils.displayUrl(commonImg1, orderListEntity2.productList.get(0).picUrl);
//                    }
//                    if (i == 1) {
//                        commonImg2.setVisibility(View.VISIBLE);
//                        FrescoUtils.displayUrl(commonImg2, orderListEntity2.productList.get(1).picUrl);
//                    }
//                    if (i == 2) {
//                        commonImg3.setVisibility(View.VISIBLE);
//                        FrescoUtils.displayUrl(commonImg3, orderListEntity2.productList.get(2).picUrl);
//                    }
//
//                }


            }

            if (orderListEntity2.orderStatus == 1) {//代付款  显示取消订单cancal 1显示   去支付

                commonBtn1.setVisibility(orderListEntity2.canCancel == 1 ? View.VISIBLE : View.GONE);
                commonBtn3.setVisibility(View.GONE);
                commonBtn4.setVisibility(View.GONE);
                commonBtn5.setVisibility(View.GONE);
                commonBtn6.setVisibility(View.VISIBLE);

            } else if (orderListEntity2.orderStatus == 2) {//代发货  不显示所有按钮

                commonBtn1.setVisibility(View.GONE);
                commonBtn3.setVisibility(View.GONE);
                commonBtn4.setVisibility(View.GONE);
                commonBtn5.setVisibility(View.GONE);
                commonBtn6.setVisibility(View.GONE);

            } else if (orderListEntity2.orderStatus == 3) {//代收货  查看物流 确认收货  & 详情 申请售后canAfter =1

                commonBtn1.setVisibility(View.GONE);
                commonBtn3.setVisibility(View.VISIBLE);
                commonBtn4.setVisibility(View.GONE);
                commonBtn5.setVisibility(View.VISIBLE);
                commonBtn6.setVisibility(View.GONE);
            } else if (orderListEntity2.orderStatus == 4) {//代评价   去评价canComment 1   删除订单canDelete 1     查看物流  &详情 申请售后canAfter

                commonBtn1.setVisibility(View.GONE);
                commonBtn3.setVisibility(View.GONE);
                commonBtn4.setVisibility(orderListEntity2.canComment == 1 ? View.VISIBLE : View.GONE);
                commonBtn5.setVisibility(View.GONE);
                commonBtn6.setVisibility(View.GONE);
            } else if (orderListEntity2.orderStatus == 8) {//完成   删除    申请售后canAfter 1    查看物流 常显   去评价 commentstatus 1
//               & 详情 再次购买   显示 canAfter！=1  || commentstatus！=1


                commonBtn1.setVisibility(View.GONE);
                commonBtn3.setVisibility(View.VISIBLE);
                commonBtn4.setVisibility(orderListEntity2.commentStatus == 1 ? View.VISIBLE : View.GONE);
                commonBtn5.setVisibility(View.GONE);
                commonBtn6.setVisibility(View.GONE);

                commonBtn7.setVisibility(orderListEntity2.canAfterSale != 1 || orderListEntity2.commentStatus != 1 ? View.VISIBLE : View.GONE);
            }
            commonBtn2.setVisibility(orderListEntity2.canDelete == 1 ? View.VISIBLE : View.GONE);


//            取消订单
            commonBtn1.setOnClickListener(view -> {


                RouterHelper.getHelper().openWeb("/order/cancelOrderReason.html?orderCode=" + orderListEntity2.orderCode);


            });
//删除
            commonBtn2.setOnClickListener(view -> {

                DialogCancle dialogCancle = new DialogCancle(fragmentActivity.getActivity(), R.layout.dialog_common_canle);
                dialogCancle.show();
                dialogCancle.findViewById(R.id.common_tv_2).setOnClickListener(view1 -> {

                    Map<String, String> stringStringMap = NetWorkMap.defaultMap();
                    stringStringMap.put("orderCode", orderListEntity2.orderCode);
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
                                fragmentActivity.removeIndex(orderListEntity2);
                            }

                        }
                    });


                });


            });

            commonBtn3.setOnClickListener(view -> {
                RouterHelper.getHelper().openWeb("/my/logistics-information.html?orderCode=" + orderListEntity2.orderCode);
            });
//            评价
            commonBtn4.setOnClickListener(view -> RouterHelper.getHelper().openWeb("/my/evaluate.html?mpId=" + orderListEntity2.orderCode));

//            确认收货

            commonBtn5.setOnClickListener(view -> {
                DialogCancle dialogCancle = new DialogCancle(fragmentActivity.getActivity(), R.layout.dialog_common_canle);
                dialogCancle.show();
                TextView textView = (TextView) dialogCancle.findViewById(R.id.tv_title);
                textView.setText("您确定收货吗？");

                dialogCancle.findViewById(R.id.common_tv_2).setOnClickListener(view12 -> {

                    Map<String, String> stringStringMap = NetWorkMap.defaultMap();
                    stringStringMap.put("orderCode", orderListEntity2.orderCode);

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
                                        fragmentActivity.initData(1);
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
                    //UIUtils.showToastSafe("去支付 原生收银台");

                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ORDER_ID, orderListEntity2.orderCode);
                    bundle.putString(Constants.USER_ID, orderListEntity2.userId + "");
                    bundle.putString(Constants.ORDER_MONEY, orderListEntity2.paymentAmount + "");
                    JumpUtils.ToActivity(JumpUtils.PAYONLINE, bundle);
                }
            });

            commonBtn7.setOnClickListener(view -> {
                Map<String, String> stringStringMap = NetWorkMap.defaultMap();
                stringStringMap.put("orderCode", orderListEntity2.orderCode);

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


                                    DialogBuyAngin dialogBuyAngin = new DialogBuyAngin(fragmentActivity.getContext(), R.layout.dialog_common_buy_angin);
                                    if (null != commonEntity && null != commonEntity.data && null != commonEntity.data.availableProductList) {
                                        dialogBuyAngin.setData(commonEntity.data.availableProductList);
                                        dialogBuyAngin.show();
                                    }
                                    dialogBuyAngin.findViewById(R.id.common_tv_2).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            try {
                                                ArrayList<BuyAnginEntity> objects = new ArrayList<>();

                                                for (int i = 0; i < orderListEntity2.productList.size(); i++) {


                                                    BuyAnginEntity buyAgainEntity = new BuyAnginEntity();
                                                    buyAgainEntity.mpId = orderListEntity2.productList.get(i).mpId + "";
                                                    buyAgainEntity.num = orderListEntity2.productList.get(i).num + "";

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
}
