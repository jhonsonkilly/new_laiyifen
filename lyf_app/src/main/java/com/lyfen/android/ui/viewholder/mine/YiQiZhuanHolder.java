package com.lyfen.android.ui.viewholder.mine;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.lyfen.android.entity.network.mine.PrecommissonEntity;
import com.lyfen.android.ui.activity.login.LoginHelper;
import com.lyfen.android.utils.UIUtils;
import com.ody.p2p.main.BuildConfig;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;

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

public class YiQiZhuanHolder extends BaseHolder<PrecommissonEntity> {
    @Bind(R.id.common_linearLayout_1)
    LinearLayout commonLinearLayout1;
    @Bind(R.id.common_tv_1)
    TextView commonTv1;
    @Bind(R.id.common_tv_2)
    TextView commonTv2;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    FragmentActivity fragmentActivity;

    public YiQiZhuanHolder(FragmentActivity activity) {
        this.fragmentActivity = activity;
    }

    @Override
    protected View initView() {
        View inflate = View.inflate(UIUtils.getContext(), R.layout.layout_yiqizhuan, null);
        ButterKnife.bind(this, inflate);

        return inflate;
    }

    @Override
    public void refreshView() {
        try {
            commonLinearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (LoginHelper.needLogin(fragmentActivity)) {
                        getweb("/placeChannel/index.html");
                    }

                }
            });

            commonTv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (LoginHelper.needLogin(fragmentActivity)) {
                        getweb("/my/my-income.html");
                    }
                }
            });
            commonTv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (LoginHelper.needLogin(fragmentActivity)) {
                        getweb("/my/report-center.html");
                    }
                }
            });
            PrecommissonEntity data = getData();

            if (!TextUtils.isEmpty(data.data.preCommissionAmount)) {
                tvMoney.setText("¥" + data.data.preCommissionAmount);


            }
        } catch (Exception e) {

        }


    }

    public void getweb(String url) {
        JumpUtils.ToWebActivity(fragmentActivity, BuildConfig.H5URL + url);

    }
}
