package com.ody.p2p.main.TestBase;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.netease.nim.demo.main.util.IMRedDotManager;
import com.ody.p2p.Constants;
import com.ody.p2p.UpGradeBean;
import com.ody.p2p.base.BasePresenter;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.base.action.GrestCouponBean;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.entity.NewUserBean;
import com.ody.p2p.main.CurrDistributorBean;
import com.ody.p2p.main.InitDataBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.productdetail.productdetail.bean.ShoppingCountBean;
import com.ody.p2p.retrofit.home.AdBean;
import com.ody.p2p.retrofit.home.PersonalBean;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import static com.laiyifen.lyfframework.APPEnvironment.getPackageName;

/**
 * Created by mac on 2018/1/30.
 */

public class MainPresenter extends BaseCommPresenter<MainView> {

    private String appChannel;

    @Override
    public void initData(Bundle saveInstnce) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = getActivity().getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            appChannel = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getTanPin("newcomers_popup,registered_coupon");
        initApp(OdySysEnv.getVersionCode(), getPackageName());
        initCartNum();
//        getUpgrade(OdySysEnv.getVersion(), getPackageName(), OdySysEnv.getVersionCode(), appChannel);
//        presenter.firstLoginCoupon();
        getCurrDistributor();

//       IMRedDotManager.newInstent().init(true);
        IMRedDotManager.newInstent().init(getActivity());




        getGuangGao();
    }

    @Override
    public void handMsg(Message msg) {

    }


    public void initApp(String versionCode, String uniqueCode) {
        Map<String, String> params = new HashMap<>();
        params.put("versionCode", versionCode);
        params.put("uniqueCode", uniqueCode);
        OkHttpManager.postAsyn(Constants.INIT_DATA, iView.getNetTAG(), new OkHttpManager.ResultCallback<InitDataBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(InitDataBean response) {
                if (response != null) {
                    iView.initData(response);
                }
            }
        }, params);

    }


    public void initCartNum() {
        Map<String, String> map = new HashMap<String, String>();
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT, "");
        map.put("ut", ut);
        map.put("sessionId", OdySysEnv.getSessionId());
        OkHttpManager.getAsyn(Constants.PRODUCT_CARTCOUNT, map, iView.getNetTAG(), new OkHttpManager.ResultCallback<ShoppingCountBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(ShoppingCountBean response) {
                ShoppingCountBean bean = response;
                if (null != response && response.code.equals("0")) {
                    iView.initCartNum(bean.getData());
                }
            }

        });
    }


    public void getUpgrade(String versionName, String uniqueCode, String versionCode, String appChannel) {
        Map<String, String> params = new HashMap<>();
        params.put("versionCode", versionName);
        params.put("uniqueCode", uniqueCode);
        params.put("appVersionCode", versionCode);
        if (appChannel.equals("dev")) {
            appChannel = "odydev";
        }
        params.put("appChannel", appChannel);
        OkHttpManager.getAsyn(Constants.APP_UPGRADE, params, iView.getNetTAG(), new OkHttpManager.ResultCallback<UpGradeBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(UpGradeBean response) {
                if (response != null && response.data != null) {
                    iView.Upgrade(response.getData());
                }
            }
        });
    }


    public void getTanPin(String adCode) {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", adCode);
        params.put("platform", "3");
        params.put("pageCode", "APP_HOME");
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, iView.getNetTAG(), new OkHttpManager.ResultCallback<FuncBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(FuncBean response) {
                if (response != null) {
                    iView.initTanPin(response);
                }
            }
        });
    }


    public void isNewUser(final int flag) {
        Map<String, String> map = new HashMap<String, String>();
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT, "");
        map.put("ut", ut);
        map.put("userExtKeysStr", "newuser");
        OkHttpManager.postAsyn(Constants.IS_NEW_USER, iView.getNetTAG(), new OkHttpManager.ResultCallback<NewUserBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(NewUserBean response) {
                if (response != null) {
                    if (response != null && response.data != null && response.data.userExtMap != null) {
                        iView.initIsNewUser(response.data.userExtMap.newuser, flag);
                    }
                }
            }
        }, map);
    }


    public void firstLoginCoupon() {

        Map<String, String> params = new HashMap<>();
        params.put("adCode", "register_coupon_guide");
        params.put("platform", "3");
        params.put("pageCode", "APP_HOME");
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, new OkHttpManager.ResultCallback<GrestCouponBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(GrestCouponBean response) {
                iView.initFirstLoginCoupon(response);


//                try {
//                    TPDialog tpDialog = new TPDialog(LyfLoginFragment.this, response.data.register_coupon_guide, R.style.NobackDialog);
//                    tpDialog.show();
//                } catch (Exception e) {
//
//                }


            }
        });
    }

    /**
     * 获取当前店铺id
     */

    public void getCurrDistributor() {
        Map<String, String> map = new HashMap<String, String>();
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT, "");
        map.put("ut", ut);
        OkHttpManager.getAsyn(Constants.SHOP_INFO, map, iView.getNetTAG(), new OkHttpManager.ResultCallback<CurrDistributorBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CurrDistributorBean response) {
                Log.e("xiaobin", "response: id ----> " + response.data.getId());
                if (response != null && response.data != null) {
                    OdyApplication.putValueByKey("distributorId", String.valueOf(response.data.getId()));
                    iView.load(String.valueOf(response.data.getId()));
                }

            }
        });
    }


    public void getPersonalMes(boolean isImlogin) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));

        OkHttpManager.getAsyn(Constants.PERSONALDATA, params, new OkHttpManager.ResultCallback<PersonalBean>() {
            @Override
            public void onError(Request request, Exception e) {


            }

            @Override
            public void onResponse(PersonalBean response) {


                if (response != null) {
                    try {

                        iView.setPersonalData(response, isImlogin);
                    } catch (Exception e) {

                    }

                }
            }
        });
    }


    public void getGuangGao() {
        Map<String, String> params = new HashMap<>();
        params.put("pageCode", "APP_HOME");
        params.put("adCode", "APP_boot_ad");
        OkHttpManager.getAsyn(Constants.GUANGGAO, params, new OkHttpManager.ResultCallback<AdBean>() {
            @Override
            public void onError(Request request, Exception e) {


            }

            @Override
            public void onResponse(AdBean response) {

                iView.setGuangGaoData(response);

            }
        });
    }
}
