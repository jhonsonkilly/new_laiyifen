package com.lyfen.android.ui.viewholder.order;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.lyfen.android.entity.network.OrderDetailEntity;
import com.ody.p2p.AliServicePolicy;
import com.ody.p2p.Constants;
import com.ody.p2p.QiYuServicePolicy;
import com.ody.p2p.ServiceManager;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.entity.QIYuEntity;
import com.ody.p2p.entity.YiModel;
import com.ody.p2p.main.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.lyfen.android.utils.UIUtils.getContext;

/**
 * <p> Created by qiujie on 2017/12/16/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class OrderInfoHolder extends BaseHolder<OrderDetailEntity.DataBean> {

//    订单编号 常显
//    夫单编号：  orderstatus!=1 && praencOrdercode!=null
//    创建时间 常显
//    付款时间  orderstatus!=1 && paymenttimestr!=null
//    发货时间： orderstatus!=1 && orderstatus!=2 &&orderstatus！=10  &&shiptimester！=null
//    成交时间：  orderstatus==8  &&  receivertimeser!=null


    @Bind(R.id.total)
    TextView total;
    @Bind(R.id.price1)
    TextView price1;
    @Bind(R.id.price2)
    TextView price2;
    @Bind(R.id.price3)
    TextView price3;
    @Bind(R.id.order_id)
    TextView orderId;
    @Bind(R.id.order_id2)
    TextView orderId2;
    @Bind(R.id.order_time)
    TextView orderTime;
    @Bind(R.id.common_rllayout_horizontal_number_1)
    RelativeLayout commonRllayoutHorizontalNumber1;
    @Bind(R.id.common_rllayout_horizontal_number_2)
    RelativeLayout commonRllayoutHorizontalNumber2;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.common_lllayout_horizontal_number_1)
    LinearLayout commonLllayoutHorizontalNumber1;

    @Bind(R.id.kefu_online)
    TextView kefu_online;
    @Bind(R.id.kefu)
    TextView kefu;

    PopupWindow mPhoneWindow;
    private ColorDrawable mColorDrawable;
    private View view;
    View mPhoneView;

    private TextView tv_cancel, tv_phoneNum, tv_telNum;

    private LinearLayout lay_phone;

    private Intent mIntent;

    Context context;


    @Override
    protected View initView() {
        View inflate = View.inflate(getContext(), R.layout.item_personal_pay, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    public void setRootView(View view, Context context) {
        this.context = context;
        mPhoneView = LayoutInflater.from(getContext()).inflate(com.ody.p2p.myhomepager.R.layout.setting_popup_phone, null);
        tv_phoneNum = (TextView) mPhoneView.findViewById(com.ody.p2p.myhomepager.R.id.tv_about_phoneNum);
        tv_phoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tv_phoneNum.getText().toString()));
                context.startActivity(mIntent);
            }
        });

        tv_cancel = (TextView) mPhoneView.findViewById(com.ody.p2p.myhomepager.R.id.tv_about_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneWindow.dismiss();
            }
        });
        lay_phone = (LinearLayout) mPhoneView.findViewById(com.ody.p2p.myhomepager.R.id.lay_about_phone);
        lay_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneWindow.dismiss();
            }
        });
        this.view = view;
    }

    @Override
    public void refreshView() {
        showPhonePopupWindow();
        OrderDetailEntity.DataBean data = getData();
        total.setText("¥" + data.productAmount + "");
        if (data.taxAmount > 0) {
            commonRllayoutHorizontalNumber1.setVisibility(View.VISIBLE);
            price1.setText("-¥ " + data.taxAmount + "");

        }

        if (data.promotionAmount > 0) {
            commonRllayoutHorizontalNumber2.setVisibility(View.VISIBLE);
            price2.setText("-¥ " + data.promotionAmount + "");

        }
        price3.setText("-¥ " + data.orderPaidByCoupon + "");
        orderId.setText(data.orderCode + "");
        orderTime.setText(data.orderCreateTimeStr + "");

        if (!TextUtils.isEmpty(data.parentOrderCode)) {

            orderId2.setText(data.parentOrderCode + "");
            commonLllayoutHorizontalNumber1.setVisibility(View.VISIBLE);
        }

        kefu_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (OdyApplication.getString(Constants.SERVICE_TOGGLE, "0").equals("0")) {

                    ServiceManager.getInstance().doPolicy(new AliServicePolicy(), context);
                } else {
                    ServiceManager.getInstance().doPolicy(new QiYuServicePolicy(), QIYuEntity.ORDER, context);
                }

            }
        });
        kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneWindow.showAtLocation(view, Gravity.TOP, 0, 0);
            }
        });

    }


    private void showPhonePopupWindow() {
        mPhoneWindow = new PopupWindow(mPhoneView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        mPhoneWindow.setFocusable(true);
        mPhoneWindow.setOutsideTouchable(true);
        mPhoneWindow.setAnimationStyle(com.ody.p2p.myhomepager.R.style.popwindow_anim_style);
        mColorDrawable = new ColorDrawable(0x20000000);
        mPhoneWindow.setBackgroundDrawable(mColorDrawable);

    }
}
