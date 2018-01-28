package com.lyfen.android.ui.viewholder.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laiyifen.lyfframework.listadapter.BaseHolder;
import com.laiyifen.lyfframework.utils.PreferenceUtils;
import com.lyfen.android.app.PrefrenceKey;
import com.lyfen.android.entity.network.mine.MyWalletEntity;
import com.lyfen.android.hybird.HybridEntity;
import com.lyfen.android.hybird.LyfWebViewActivity;
import com.lyfen.android.hybird.UrlEntity;
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

public class MyPacketHolder extends BaseHolder<MyWalletEntity> {
    @Bind(R.id.common_tv_1)
    TextView commonTv1;
    @Bind(R.id.common_tv_2)
    TextView commonTv2;
    @Bind(R.id.common_tv_3)
    TextView commonTv3;
    @Bind(R.id.common_tv_4)
    TextView commonTv4;
    @Bind(R.id.ll_content)
    LinearLayout ll_content;
    @Bind(R.id.common_linearLayout_1)
    LinearLayout commonLinearLayout1;
    @Bind(R.id.common_linearLayout_2)
    LinearLayout commonLinearLayout2;
    @Bind(R.id.common_linearLayout_3)
    LinearLayout commonLinearLayout3;
    @Bind(R.id.common_linearLayout_4)
    LinearLayout commonLinearLayout4;

    @Override
    protected View initView() {
        View inflate = View.inflate(UIUtils.getContext(), R.layout.layout_packec, null);
        ButterKnife.bind(this, inflate);

        return inflate;
    }

    @Override
    public void refreshView() {

        ll_content.setOnClickListener(view -> {

            if (LoginHelper.needLogin(UIUtils.getContext())) {
                getweb("/my/my-wallet.html");

            }
        });

        commonLinearLayout1.setOnClickListener(view -> {
            if (LoginHelper.needLogin(UIUtils.getContext())) {
                getweb("/pay/youdianCard.html");
            }

        });
        commonLinearLayout2.setOnClickListener(view -> {
            if (LoginHelper.needLogin(UIUtils.getContext())) {
                getweb("/my/pointCards-list.html");
            }

        });


        commonLinearLayout3.setOnClickListener(view -> {
            if (LoginHelper.needLogin(UIUtils.getContext())) {
                getweb("/my/integral.html");
            }

        });
        commonLinearLayout4.setOnClickListener(view -> {
            if (LoginHelper.needLogin(UIUtils.getContext())) {
                getweb("/my/coupons-list.html");
            }

        });


        try {
            MyWalletEntity data = getData();

            if (!TextUtils.isEmpty(data.data.yCardBalance)) {
                commonTv1.setText(data.data.yCardBalance);
            }

            if (!TextUtils.isEmpty(data.data.eCardBalance)) {
                commonTv2.setText(data.data.eCardBalance);
            }

            if (!TextUtils.isEmpty(data.data.point)) {
                commonTv3.setText(data.data.point);
            }

            if (!TextUtils.isEmpty(data.data.coupon)) {
                commonTv4.setText(data.data.coupon);
            }
        } catch (Exception e) {

        }

    }

    public void getweb(String url) {

        JumpUtils.ToWebActivity(UIUtils.getContext(), BuildConfig.H5URL+url);
//        UrlEntity urlEntity = new UrlEntity();
//        urlEntity.url = PreferenceUtils.getString(PrefrenceKey.H5_HOST, "") + url;
//
//        HybridEntity hybridEntity = new HybridEntity();
//        hybridEntity.param = urlEntity;
//        Intent intent = new Intent(UIUtils.getContext(), LyfWebViewActivity.class);
//
//        intent.putExtra("mode", hybridEntity);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        UIUtils.getContext().startActivity(intent);
    }
}
