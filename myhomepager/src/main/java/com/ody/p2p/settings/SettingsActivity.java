package com.ody.p2p.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.settings.ServiceAgerrment.ServiceAgreementActivity;
import com.ody.p2p.settings.aboutme.AboutMeActivity;
import com.ody.p2p.settings.accountSafe.AccountSafeActivity;
import com.ody.p2p.settings.exitAccount.ExitAccountPopupWindow;
import com.ody.p2p.settings.modifyUserInfo.ModifyUserInfoActivity;
import com.ody.p2p.settings.utils.DataClean;
import com.ody.p2p.settings.utils.UserPopupWindow;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.auth.AuthService;


public class SettingsActivity extends BaseActivity implements View.OnClickListener, SettingsView{

    TextView tv_name;
    RelativeLayout rl_big_back;
    TextView tv_cache;
    RelativeLayout rl_clean_cache;
    RelativeLayout rl_personal_info;
    RelativeLayout rl_exit_account;
    private RelativeLayout rl_show_more;
    private ImageView iv_right_icon;
    private TextView tv_right_icon;

    private RelativeLayout rl_account_safe;
    private RelativeLayout rl_aboutus, relative_service_agreement;
    private SettingsPresenter settingsPresenter;


    private boolean isLogin;

    @Override
    public void initPresenter() {
        settingsPresenter = new SettingsPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_settings;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        tv_cache = (TextView) view.findViewById(R.id.tv_cache);
        rl_clean_cache = (RelativeLayout) view.findViewById(R.id.rl_clean_cache);
        rl_personal_info = (RelativeLayout) view.findViewById(R.id.rl_personal_info);
        rl_exit_account = (RelativeLayout) view.findViewById(R.id.rl_exit_account);

        rl_account_safe = (RelativeLayout) view.findViewById(R.id.rl_account_safe);
        relative_service_agreement = (RelativeLayout) view.findViewById(R.id.relative_service_agreement);
        rl_aboutus = (RelativeLayout) view.findViewById(R.id.rl_aboutus);

        rl_show_more = (RelativeLayout) view.findViewById(R.id.rl_show_more);
        iv_right_icon = (ImageView) view.findViewById(R.id.iv_right_icon);
        tv_right_icon = (TextView) view.findViewById(R.id.tv_right_text);

        rl_show_more.setVisibility(View.VISIBLE);
        iv_right_icon.setVisibility(View.VISIBLE);
        tv_right_icon.setVisibility(View.GONE);
        iv_right_icon.setImageResource(R.drawable.icon_more);
        tv_name.setText(getString(R.string.setting));
        try {
//            if (DataClean.getTotalCacheSize(this).equals("0M")){
//                tv_cache.setVisibility(View.GONE);
//            }else {
            tv_cache.setText(DataClean.getTotalCacheSize(this));
//                tv_cache.setVisibility(View.VISIBLE);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        relative_service_agreement.setOnClickListener(this);
        rl_big_back.setOnClickListener(this);
        rl_clean_cache.setOnClickListener(this);
        rl_personal_info.setOnClickListener(this);
        rl_account_safe.setOnClickListener(this);
        rl_aboutus.setOnClickListener(this);
        rl_exit_account.setOnClickListener(this);
        rl_show_more.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        isLogin = OdyApplication.getValueByKey(Constants.LOGIN_STATE, false);//登录状态
        if (!isLogin){
            rl_exit_account.setVisibility(View.GONE);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    //    private AlertPopupWindow alertPopupWindow;
    private UserPopupWindow userPopupWindow;
    private ExitAccountPopupWindow exitAccountPopupWindow;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_aboutus) {
            Intent in = new Intent(SettingsActivity.this, AboutMeActivity.class);
            startActivity(in);
        }
        if (v.getId() == R.id.rl_big_back) {
            finish();
        }
        if (v.getId() == R.id.rl_clean_cache) {
            userPopupWindow = new UserPopupWindow(this, 1, null, new OKListener(), null, new CancelListener());
            userPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
        if (v.getId() == R.id.rl_personal_info) {
            mBaseOperation.forward(ModifyUserInfoActivity.class);
        }
        if (v.getId() == R.id.rl_account_safe) {
            if (isLogin){
                mBaseOperation.forward(AccountSafeActivity.class);
            }else {
//                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://login");
//                activityRoute.withParams(Constants.LOSINGTAP,"100").open();

                Bundle bd=new Bundle();
                bd.putString(Constants.LOSINGTAP,"100");
                JumpUtils.ToActivity(JumpUtils.LOGIN,bd);
            }
        }
        if (v.getId() == R.id.rl_exit_account) {
            exitAccountPopupWindow = new ExitAccountPopupWindow(this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    settingsPresenter.exitAccount();
                }
            });
            exitAccountPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            //IM的登出
            NIMClient.getService(AuthService.class).logout();
        }
        if (v.getId() == R.id.relative_service_agreement) {
            mBaseOperation.forward(ServiceAgreementActivity.class);
        }
        if(v.equals(rl_show_more)){
        }
    }

    @Override
    public void closePopwindow() {
        exitAccountPopupWindow.dismiss();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    public class OKListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            ImageLoader.getInstance().clearDiskCache();
//            ImageLoader.getInstance().clearMemoryCache();
            DataClean.clearAllCache(SettingsActivity.this);
            try {
                tv_cache.setText(DataClean.getTotalCacheSize(SettingsActivity.this));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ToastUtils.showShort(getString(R.string.clear_success));
            userPopupWindow.dismiss();
        }
    }

    public class CancelListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            userPopupWindow.dismiss();
        }
    }

}
