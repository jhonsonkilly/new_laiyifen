package com.ody.p2p.settings.accountSafe.modifyPsd2;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/16.
 */
public class ModifyPsdSecondPresenterImpl implements ModifyPsdSecondPresenter {
    
    private ModifyPsdSecondView modifyPsdSecondView;
    
    public ModifyPsdSecondPresenterImpl(ModifyPsdSecondView modifyPsdSecondView){
        this.modifyPsdSecondView = modifyPsdSecondView;
    }

    @Override
    public void confirmModifyPsd(String et_psd1, final String et_psd2, final String et_psd3) {
        boolean checkPsd = modifyPsdSecondView.checkPsd(et_psd1,et_psd2,et_psd3);
        if (!checkPsd){
            return;
        }
        final String phone = OdyApplication.getValueByKey(Constants.LOGIN_MOBILE_PHONE,null);
        Map<String,String> params = new HashMap<>();
        params.put("mobile",phone);
        params.put("password", et_psd1);
        params.put("password1", et_psd2);
        params.put("password2", et_psd3);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        String url;
//        if (!StringUtils.isEmpty(modifyPsdSecondView.getDsPsdModify())){
//            url = modifyPsdSecondView.getDsPsdModify();
//        }else {
//            url = Constants.MODIFY_PSD_CONFIRM;
//        }
        url = Constants.MODIFY_PSD_CONFIRM;
        OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onFailed(String code, String msg) {
                ToastUtils.showShort(msg);
                super.onFailed(code, msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response.code.equals("0")){
                    ToastUtils.showShort(response.message);
                    //login(phone,et_psd3);
                    modifyPsdSecondView.toAccountSafeActivity();
                }
                else{
                    ToastUtils.showShort(response.message);
                }
            }
        },params);
    }

    /**
     * 需要登录否???????????????????????
     * @param phone
     * @param et_psd3
     */
    public void login(final String phone, String et_psd3) {
        Map<String,String> params = new HashMap<>();
        params.put("mobile",phone);
        params.put("password",et_psd3);
        OkHttpManager.postAsyn(Constants.LOGIN, new OkHttpManager.ResultCallback<LoginBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(LoginBean response) {
                OdyApplication.putValueByKey(Constants.USER_LOGIN_UT, response.ut);
                OdyApplication.putValueByKey(Constants.LOGIN_STATE, true);
                OdyApplication.putValueByKey(Constants.LOGIN_MOBILE_PHONE, phone);
                OdyApplication.putString(Constants.HEAD_PIC_URL, response.data.headPicUrl);
                OdyApplication.putString(Constants.NICK_NAME, response.data.nickname);
                ToastUtils.showShort(response.message);
                modifyPsdSecondView.toAccountSafeActivity();
//                bindGuid();
            }
        },params);
    }
}
