package com.lyfen.android.ui.viewholder.mine;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.lyfen.android.entity.network.mine.MyOrderEntity;
import com.lyfen.android.ui.BadgeView;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.lyfen.android.ui.activity.order.OrderListActivity;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * <p> Created by qiujie on 2017/12/14/p>
 * <p>Copyright:Copyright(c) 2016</p>
 * <p>Company:上海来伊份电子商务有限公司</p>
 * <p>类更新历史信息</p>
 *
 * @todo <a href="mailto:qiujie1@laiyifen.com">vernal(邱洁)</a>
 */

public class MyOrderHolder extends BaseHolder<MyOrderEntity> {
    @Bind(R.id.common_tv_1)
    TextView commonTv1;
    @Bind(R.id.common_tv_2)
    TextView commonTv2;
    @Bind(R.id.common_tv_3)
    TextView commonTv3;
    @Bind(R.id.common_tv_4)
    TextView commonTv4;
    @Bind(R.id.common_tv_5)
    TextView commonTv5;
    @Bind(R.id.ll_banner1)
    LinearLayout ll_content;
    FragmentActivity fragmentActivity;
    public MyOrderHolder(FragmentActivity activity) {
        fragmentActivity=activity;
    }

    @Override
    protected View initView() {
        View inflate = View.inflate(UIUtils.getContext(), R.layout.layout_myorder, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    //    待付款 unpay
//    待发货 undelivery
//    待收货 unreceive
//    待评价 unEvaluate
//    退换货 isAfter
    @Override
    public void refreshView() {

        try {
            MyOrderEntity data = getData();
            bingOrderNum(commonTv1, data.data.unpay);
            bingOrderNum(commonTv2, data.data.undelivery);

            bingOrderNum(commonTv3, data.data.unreceive);
            bingOrderNum(commonTv4, data.data.unEvaluate);
            bingOrderNum(commonTv5, data.data.isAfter);

        } catch (Exception e) {

        }

        ll_content.setOnClickListener(view -> {
            if (LoginHelper.needLogin(fragmentActivity)) {

                fragmentActivity.startActivity(new Intent(fragmentActivity, OrderListActivity.class));


            }
        });
        commonTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAct(1);
            }
        });

        commonTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAct(2);
            }
        });
        commonTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAct(3);
            }
        });
        commonTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAct(4);
            }
        });
        commonTv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAct(7);
            }
        });
    }


    public void startAct(int index) {
        if (LoginHelper.needLogin(fragmentActivity)) {
            Intent intent = new Intent(fragmentActivity, OrderListActivity.class);
            intent.putExtra("index", index);
            fragmentActivity.startActivity(intent);
        }

    }

    BadgeView mBadge;
    private void bingOrderNum(View iv, int badgeCnt) {
        mBadge = null;
        if (iv.getTag() != null) {
            mBadge = (BadgeView) iv.getTag();
        }
        if (mBadge == null && badgeCnt > 0) {

            mBadge = new BadgeView(UIUtils.getContext(), iv);

            mBadge.setTextSize(8);
//            mBadge.setTypeface(Typeface.create(Typeface.SANS_SERIF,
//                    Typeface.ITALIC));
            mBadge.setText(String.valueOf(badgeCnt));
            mBadge.show();
            iv.setTag(mBadge);
        } else if (mBadge != null && badgeCnt > 0) {
            mBadge.setText(String.valueOf(badgeCnt));
            mBadge.show();
        } else if (mBadge != null && badgeCnt == 0) {
            mBadge.hide();
        }

    }
}
