package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.squareup.okhttp.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ody on 2016/7/5.
 */
public class OrderRecycleViewNewAdapter extends BaseRecyclerViewAdapter<OrderListBean.DataBean.OrderListItemBean> {

    private OrderListPresenter presenter;
    protected Context mContext;
    protected OnOrderItemClick onOrderItemClick;

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    public OrderRecycleViewNewAdapter(List<OrderListBean.DataBean.OrderListItemBean> data, Context context, OrderListPresenter presenter) {
        super(context, data);
        mContext = context;
        this.presenter = presenter;
    }

    public class OrderViewHolder extends BaseRecyclerViewHolder {

        public TextView tv_date;
        public TextView tv_status;
        public LinearLayout ll_pic;
        public TextView tv_money;
        public LinearLayout ll_bottom;
        public TextView tv_three;
        public TextView tv_two;
        public TextView tv_one;
        public TextView tv_product_num;
        public TextView tv_actual_amount;
        public LinearLayout ll_root;
        public HorizontalScrollView hs_product_pic;
        public LinearLayout ll_single_pro;
        private ImageView iv_product;
        private TextView tv_name;
        private TextView tv_describe;
        private RelativeLayout rl_item;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_actual_amount = (TextView) itemView.findViewById(R.id.tv_actual_amount);
            tv_three = (TextView) itemView.findViewById(R.id.tv_three);
            tv_two = (TextView) itemView.findViewById(R.id.tv_two);
            tv_one = (TextView) itemView.findViewById(R.id.tv_one);
            ll_pic = (LinearLayout) itemView.findViewById(R.id.ll_pic);
            ll_bottom = (LinearLayout) itemView.findViewById(R.id.ll_bottom);
            tv_product_num = (TextView) itemView.findViewById(R.id.tv_product_num);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
            hs_product_pic = (HorizontalScrollView) itemView.findViewById(R.id.hs_product_pic);
            ll_single_pro= (LinearLayout) itemView.findViewById(R.id.ll_single_pro);
            iv_product= (ImageView) itemView.findViewById(R.id.iv_product);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_describe= (TextView) itemView.findViewById(R.id.tv_describe);
            rl_item= (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }


    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
        final OrderListBean.DataBean.OrderListItemBean orderListItemBean = mDatas.get(position);
        orderViewHolder.tv_date.setText(mContext.getString(R.string.order_b_coed)+":" + orderListItemBean.orderCode);
        orderViewHolder.tv_money.setText(mContext.getString(R.string.money_symbol) + UiUtils.getDoubleForDouble(orderListItemBean.paymentAmount));
        if(orderListItemBean.productList != null&&orderListItemBean.productList.size()>0){
            if(orderListItemBean.productList.size()==1){
                orderViewHolder.rl_item.setVisibility(View.GONE);
                orderViewHolder.ll_single_pro.setVisibility(View.VISIBLE);
                orderViewHolder.tv_name.setText(orderListItemBean.productList.get(0).name);
                if(!TextUtils.isEmpty(orderListItemBean.productList.get(0).picUrl)){
                    GlideUtil.display(mContext, orderListItemBean.productList.get(0).picUrl).into(orderViewHolder.iv_product);
                }
                if(orderListItemBean.productList.get(0).propertyTags!=null&&orderListItemBean.productList.get(0).propertyTags.size()>0){
                    String attrs="";
                    for(int i=0;i<orderListItemBean.productList.get(0).propertyTags.size();i++){
                        if(orderListItemBean.productList.get(0).propertyTags.get(i) != null ){
                            attrs+=orderListItemBean.productList.get(0).propertyTags.get(i).name+":"+orderListItemBean.productList.get(0).propertyTags.get(i).value+",";
                        }
                    }
                    attrs=attrs.substring(0,attrs.length()-1);
                    if(!TextUtils.isEmpty(attrs)){
                        orderViewHolder.tv_describe.setText(attrs);
                    }
                }
            }else{
                orderViewHolder.rl_item.setVisibility(View.VISIBLE);
                orderViewHolder.ll_single_pro.setVisibility(View.GONE);
                orderViewHolder.tv_product_num.setText(mContext.getString(R.string.common) + orderListItemBean.totalCount + mContext.getString(R.string.piece)+mContext.getString(R.string.product));
                orderViewHolder.ll_pic.removeAllViews();
                orderViewHolder.ll_pic.setClickable(false);
                for (int i = 0; i < orderListItemBean.productList.size(); i++) {
                    ImageView img = new ImageView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PxUtils.dipTopx(100),PxUtils.dipTopx(100));
                    params.setMargins(5, 0, 0, 0);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    img.setLayoutParams(params);
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onOrderItemClick.onOrderItemClickListener(orderListItemBean);
                        }
                    });
                    GlideUtil.display(mContext, orderListItemBean.productList.get(i).picUrl).into(img);
                    orderViewHolder.ll_pic.addView(img);
                }
            }
        }else{
            orderViewHolder.hs_product_pic.setVisibility(View.GONE);
            orderViewHolder.ll_single_pro.setVisibility(View.GONE);
        }
        orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderItemClick.onOrderItemClickListener(orderListItemBean);
            }
        });

        if (orderListItemBean.orderStatus == 1) {
            orderViewHolder.ll_bottom.setVisibility(View.VISIBLE);
            orderViewHolder.tv_three.setVisibility(View.GONE);
            orderViewHolder.tv_one.setVisibility(View.VISIBLE);
            orderViewHolder.tv_two.setVisibility(View.VISIBLE);
            orderViewHolder.tv_status.setText(R.string.wait_pay);
            orderViewHolder.tv_one.setBackgroundResource(R.drawable.red_solid_circle_coner);
            orderViewHolder.tv_one.setText(R.string.to_pay);
            orderViewHolder.tv_one.setTextColor(mContext.getResources().getColor(R.color.white));
            orderViewHolder.tv_two.setBackgroundResource(R.drawable.grey_stroke_white_solid);
            orderViewHolder.tv_two.setText(mContext.getString(R.string.cancel_order));
            orderViewHolder.tv_two.setTextColor(mContext.getResources().getColor(R.color.sub_title_color));
            orderViewHolder.tv_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialog dialog = new CustomDialog(mContext, mContext.getString(R.string.confirm_cancel_order));
                    dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                        @Override
                        public void Confirm() {
                            presenter.cancelOrder(orderListItemBean.orderCode, orderListItemBean.amount + "", orderListItemBean.payPrice + "");
                        }
                    });
                    dialog.show();
                }
            });

            orderViewHolder.tv_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ORDER_ID, orderListItemBean.orderCode);
                    bundle.putString(Constants.USER_ID, orderListItemBean.userId);
                    bundle.putString(Constants.ORDER_MONEY, orderListItemBean.paymentAmount + "");
                    JumpUtils.ToActivity(JumpUtils.PAYONLINE, bundle);
                }
            });
        } else if (orderListItemBean.orderStatus == 2) {
            orderViewHolder.ll_bottom.setVisibility(View.GONE);
            orderViewHolder.tv_status.setText(mContext.getString(R.string.undeliver_goods));
        } else if (orderListItemBean.orderStatus == 3) {
            orderViewHolder.ll_bottom.setVisibility(View.VISIBLE);
            orderViewHolder.tv_three.setVisibility(View.VISIBLE);
            orderViewHolder.tv_one.setVisibility(View.GONE);
            orderViewHolder.tv_two.setVisibility(View.VISIBLE);
            orderViewHolder.tv_status.setText(mContext.getString(R.string.unReceiving));
            orderViewHolder.tv_two.setText(mContext.getString(R.string.show_logistics));
            orderViewHolder.tv_two.setBackgroundResource(R.drawable.grey_stroke_white_solid);
            orderViewHolder.tv_two.setTextColor(mContext.getResources().getColor(R.color.sub_title_color));
            orderViewHolder.tv_three.setText(mContext.getString(R.string.confirm_take_delivery_of_goods));
            orderViewHolder.tv_three.setBackgroundResource(R.drawable.red_stroke_white_solid);
            orderViewHolder.tv_three.setTextColor(mContext.getResources().getColor(R.color.theme_color));
            orderViewHolder.tv_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    List<OrderTabBean> tabs = new ArrayList<>();
                    if (orderListItemBean.packageList != null && orderListItemBean.packageList.size() > 0) {
                        for (int i = 0; i < orderListItemBean.packageList.size(); i++) {
                            if (!TextUtils.isEmpty(orderListItemBean.packageList.get(i).packageCode)) {
                                OrderTabBean orderTabBean = new OrderTabBean();
                                orderTabBean.orderStatusName = mContext.getString(R.string.parcel) + (i + 1);
                                orderTabBean.orderCode = orderListItemBean.orderCode;
                                orderTabBean.packageCode = orderListItemBean.packageList.get(i).packageCode;
                                tabs.add(orderTabBean);
                            }
                        }
                        bundle.putSerializable("packageList", (Serializable) tabs);
                        JumpUtils.ToActivity(JumpUtils.LOGISTICS, bundle);
                    }
                }
            });
            orderViewHolder.tv_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //确认收货
                    CustomDialog dialog = new CustomDialog(mContext, mContext.getString(R.string.confirm_take_delivery_of_goods)+"？");
                    dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                        @Override
                        public void Confirm() {
                            presenter.confrimReceive(orderListItemBean.orderCode);
                            orderViewHolder.tv_three.setClickable(false);
                        }
                    });
                    dialog.show();

                }
            });
        } else if (orderListItemBean.orderStatus == 10) {
            orderViewHolder.tv_status.setText(mContext.getString(R.string.tocancle));
            orderViewHolder.tv_status.setTextColor(mContext.getResources().getColor(R.color.sub_title_color));
            if (orderListItemBean.canDelete == 1) {
                orderViewHolder.ll_bottom.setVisibility(View.VISIBLE);
                orderViewHolder.tv_three.setVisibility(View.GONE);
                orderViewHolder.tv_one.setVisibility(View.VISIBLE);
                orderViewHolder.tv_two.setVisibility(View.GONE);
                orderViewHolder.tv_one.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                orderViewHolder.tv_one.setText(R.string.delect_order);
                orderViewHolder.tv_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog dialog = new CustomDialog(mContext, mContext.getString(R.string.confirm)+R.string.delect_order+"?");
                        dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                            @Override
                            public void Confirm() {
                                deleteOrder(orderListItemBean.orderCode, position);
                            }
                        });
                        dialog.show();
                    }
                });

            } else {
                orderViewHolder.ll_bottom.setVisibility(View.GONE);
            }
        } else if (orderListItemBean.orderStatus == 8 || orderListItemBean.orderStatus == 4) {
            orderViewHolder.ll_bottom.setVisibility(View.VISIBLE);
            orderViewHolder.tv_three.setVisibility(View.VISIBLE);
            orderViewHolder.tv_one.setVisibility(View.GONE);
            orderViewHolder.tv_status.setText(R.string.the_deal);
            orderViewHolder.tv_status.setTextColor(mContext.getResources().getColor(R.color.sub_title_color));
            orderViewHolder.tv_three.setText(mContext.getString(R.string.show_logistics));
            orderViewHolder.tv_three.setBackgroundResource(R.drawable.grey_stroke_white_solid);
            orderViewHolder.tv_three.setTextColor(mContext.getResources().getColor(R.color.sub_title_color));
            orderViewHolder.tv_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    List<OrderTabBean> tabs = new ArrayList<>();
                    if (orderListItemBean.packageList != null && orderListItemBean.packageList.size() > 0) {
                        for (int i = 0; i < orderListItemBean.packageList.size(); i++) {
                            if (!TextUtils.isEmpty(orderListItemBean.packageList.get(i).packageCode)) {
                                OrderTabBean orderTabBean = new OrderTabBean();
                                orderTabBean.orderStatusName = mContext.getString(R.string.parcel) + (i + 1);
                                orderTabBean.orderCode = orderListItemBean.orderCode;
                                orderTabBean.packageCode = orderListItemBean.packageList.get(i).packageCode;
                                tabs.add(orderTabBean);
                            }
                        }
                        bundle.putSerializable("packageList", (Serializable) tabs);
                        JumpUtils.ToActivity(JumpUtils.LOGISTICS, bundle);
                    }

                }
            });
            if (orderListItemBean.canDelete == 1) {
                orderViewHolder.tv_two.setVisibility(View.VISIBLE);
                orderViewHolder.tv_two.setText(mContext.getString(R.string.delect_order));
                orderViewHolder.tv_two.setBackgroundResource(R.drawable.grey_stroke_white_solid);
                orderViewHolder.tv_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialog dialog = new CustomDialog(mContext, mContext.getString(R.string.confirm)+R.string.delect_order+"?");
                        dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                            @Override
                            public void Confirm() {
                                deleteOrder(orderListItemBean.orderCode, position);
                            }
                        });
                        dialog.show();
                    }
                });
            } else {
                orderViewHolder.tv_two.setVisibility(View.GONE);
            }
            if (orderListItemBean.canComment == 1) {
                orderViewHolder.tv_one.setVisibility(View.VISIBLE);
                orderViewHolder.tv_one.setText(R.string.to_evaluate);
                orderViewHolder.tv_one.setTextColor(mContext.getResources().getColor(R.color.white));
                orderViewHolder.tv_one.setBackgroundResource(R.drawable.red_solid_circle_coner);
                orderViewHolder.tv_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bd = new Bundle();
                        bd.putString("orderCode", orderListItemBean.orderCode);
                        JumpUtils.ToActivity(JumpUtils.SHOP_EVALUATE, bd);
                    }
                });
            } else {
                orderViewHolder.tv_one.setVisibility(View.GONE);
            }
        }

    }

    public void deleteOrder(String orderCode, final int position) {
        Map<String, String> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.DELETE_ORDER, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(mContext.getString(R.string.delect_faile));
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null && response.code.equals("0")) {
                    mDatas.remove(position);
                    notifyDataSetChanged();
                }
            }

        }, params);
    }

    public interface OnOrderItemClick {
        void onOrderItemClickListener(OrderListBean.DataBean.OrderListItemBean orderListItemBean);
    }

    public void setListener(OnOrderItemClick onOrderItemClick) {
        this.onOrderItemClick = onOrderItemClick;
    }
}
