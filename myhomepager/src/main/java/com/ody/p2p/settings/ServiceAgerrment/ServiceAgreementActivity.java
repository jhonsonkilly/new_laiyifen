package com.ody.p2p.settings.ServiceAgerrment;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;

public class ServiceAgreementActivity extends BaseActivity {
    TextView tv_name;
    WebView web_view;
    private RelativeLayout rl_big_back;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_service_agreement;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        web_view = (WebView) view.findViewById(R.id.web_view);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        rl_big_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_name.setText(getString(R.string.service_agreement));
        web_view.loadUrl(Constants.SERVICE_AGREEMENT);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
