package com.ody.p2p.addressmanage.editaddress;

import android.text.TextUtils;

import com.ody.p2p.Constants;
import com.ody.p2p.addressmanage.bean.BaseRequestBean;
import com.ody.p2p.addressmanage.bean.SaveAddressBackBean;
import com.ody.p2p.base.ErrorBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import static com.ody.p2p.Constants.AREA_CODE_ADDRESS;

/**
 * Created by ody on 2016/6/16.
 */
public class EditAddressPresenterImpl implements EditAddressPresenter {
    private EditAddressView mView;
    public EditAddressPresenterImpl(EditAddressView view){
        mView = view;
    }

    @Override
    public void saveAddress(String userName,String provinceId,String provinceCode,String cityId,String regionId,String detailAddress,String telNum,int isDefault,String certificationCard) {
        Map<String,String> params = new HashMap<>();
        String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "");
        params.put("ut",ut);
        params.put("userName", userName);
        params.put("provinceCode",provinceCode);
        params.put("cityId", ""+cityId);
        params.put("regionId", regionId+"");
        params.put("detailAddress", detailAddress);
        params.put("mobile", telNum);
        params.put("defaultIs", isDefault + "");
        params.put("identityCardNumber",certificationCard);
        OkHttpManager.postAsyn(Constants.ADD_ADDRESS, new OkHttpManager.ResultCallback<SaveAddressBackBean>() {
            @Override
            public void onError(Request request, Exception e) {
//                mView.showToast("您网络不好...");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onResponse(SaveAddressBackBean response) {
                mView.save(response);
            }

        }, params);

    }

    @Override
    public void editAddress(final String id, String userName, String provinceId, String provinceCode, String cityId, String regionId, String detailAddress, String telNum, int isDefault, String certificationCard) {

        Map<String,String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("provinceCode",provinceCode);
        params.put("cityId", ""+cityId);
        params.put("regionId", regionId+"");
        params.put("detailAddress", detailAddress);
        params.put("mobile", telNum);
        params.put("defaultIs", isDefault + "");
        params.put("id", id);
        String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT,"");
        params.put("ut",ut);
        params.put("identityCardNumber",certificationCard);
        OkHttpManager.postAsyn(Constants.EDIT_ADDRESS, new OkHttpManager.ResultCallback<SaveAddressBackBean>() {
            @Override
            public void onError(Request request, Exception e) {
//                mView.showToast("您网络不好...");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onResponse(SaveAddressBackBean response) {
//                mView.edit(response);
                response.id=id;
                if(response!=null&&response.code.equals("0")){
                    mView.save(response);
                }
            }
        }, params);
    }

    @Override
    public void deleteAddress(int defaultIs,String id) {

        Map<String,String> params = new HashMap<>();
        params.put("id",id);
        String ut = OdyApplication.getValueByKey(Constants.USER_LOGIN_UT,"");
        params.put("ut",ut);



        OkHttpManager.postAsyn(Constants.DELETE_ADDRESS, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
//                mView.showToast("您网络不好...");
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                mView.delete(response);
            }
        }, params);
    }

    @Override
    public void confirmordersave(final String addressId) {
        Map<String,String> params=new HashMap<>();
        params.put("receiverId",addressId);
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.SAVE_ADDRESS,new OkHttpManager.ResultCallback<ErrorBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showToast(msg);
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ErrorBean response) {
                if(response==null){
                    return;
                }
                if(response.code.equals("0")){
                    OdyApplication.putValueByKey(Constants.ADDRESS_ID,addressId);
                    mView.finishActivity(null);
                }else if(response!=null&&response.code.equals("10200002")){
                    if(response.getData()!=null&&response.getData().getError()!=null){
                        mView.finishActivity(response.getData().getError());
                    }
                }
            }
        },params);
    }
}
