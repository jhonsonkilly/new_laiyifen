package com.ody.p2p.check.aftersale;

import android.text.TextUtils;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.aftersale.Bean.AfterSaleTypeBean;
import com.ody.p2p.check.aftersale.Bean.ApplyAfterSaleCauseListBean;
import com.ody.p2p.check.aftersale.Bean.ApplyReturnResultBean;
import com.ody.p2p.check.aftersale.Bean.ChangeProductBean;
import com.ody.p2p.check.aftersale.Bean.InitApplyRefundBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.BitmapUtil;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/6/29.
 */
public class AftersaleImpr implements AftersalePressenter {
    AftersaleView mView;

    public AftersaleImpr(AftersaleView mView) {
        this.mView = mView;
    }

    /**
     * 初始化退货退款
     *
     */
    @Override
    public void initRefound(String orderCode,String type) {
        Map<String, String> params = new HashMap<>();
        params.put("orderCode", orderCode + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        if(!TextUtils.isEmpty(type)){
            params.put("type",type);
        }
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.INIT_RETURN_PRODUCT, params, new OkHttpManager.ResultCallback<InitApplyRefundBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                if(code.equals("11002001")){
                    mView.showDialog(msg);
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(InitApplyRefundBean response) {
                mView.hideLoading();
                if (null != response && null != response.getData() && null != response.getData().getAfterSalesProductVOs()) {
                    mView.initReturnProduct(response);
                }
            }
        });
    }

    /**
     * 提交售后申请（新增或编辑）
     * @param orderCode
     * @param returnReasonId
     * @param returnRemark
     * @param picUrlList
     * @param returnSoItemList
     * @param type
     * @param swapProducts
     * @param isEdit
     * @param returnId
     */
    @Override
    public void applyRefoundProduct(String orderCode, String returnReasonId, String returnRemark, String picUrlList, String returnSoItemList, int type, String swapProducts,boolean isEdit,String returnId) {
            String url="";
            Map<String, String> params = new HashMap<>();
            params.put("returnReasonId", returnReasonId + "");//退货原因
            params.put("returnRemark", returnRemark);//退货说明
            params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
            if(!TextUtils.isEmpty(picUrlList)){
                params.put("picUrlList", picUrlList);
            }

            if(isEdit){
                params.put("returnCode",returnId+"");
                url=Constants.RETURN_UPDATA;
            }else{
                params.put("type",type+"");
                params.put("orderCode", orderCode + "");//单号
                params.put("returnSoItemList", returnSoItemList);//货的商品SoItemId
                if(type==4){
                    params.put("swapProducts",swapProducts);
                }
                url=Constants.RETURN_PRODUCT;
            }
            OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<BaseRequestBean>() {

                @Override
                public void onFinish() {
                    super.onFinish();
                }

                @Override
                public void onError(Request request, Exception e) {
                }

                @Override
                public void onResponse(BaseRequestBean response) {
                    if(response!=null&&response.code.equals("0")){
                        mView.finishActivity();
                    }
                }
            }, params);
    }


    /**
     * 获取退款、退货原因列表
     */
    @Override
    public void getafterSaleCauseList(int status) {
        mView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("afterSaleType", status + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(Constants.AFTERSALE_CAUSE_LIST, params, new OkHttpManager.ResultCallback<ApplyAfterSaleCauseListBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(ApplyAfterSaleCauseListBean response) {
                mView.hideLoading();
                if (null != response && null != response.getData() && null != response.getData().getOrderAfterSalesCauseVOs()) {
                    mView.getaftersale(response.getData().getOrderAfterSalesCauseVOs());
                }
            }
        });
    }


    /**
     * 拍照上传
     *
     * @param path
     */
    @Override
    public void uploadingPhotps(final String path) {
        mView.showLoading();
        String photoPath = BitmapUtil.compressImage(path);
        File file = new File(photoPath);
        try {
            OkHttpManager.postAsyn(Constants.UPLOADING_PHOTOS, new OkHttpManager.ResultCallback<PhotosBean>() {
                @Override
                public void onError(Request request, Exception e) {
                    mView.hideLoading();
                }

                @Override
                public void onResponse(PhotosBean response) {
                    mView.hideLoading();
                    if (null != response && null != response.getData() && null != response.getData().getFilePath()) {
                        mView.getPhotos(response.getData().getFilePath());
                    }
                }
            }, file, "file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 仅退款，同订单详情代发货仅退款
     * 此接口申请售后界面没用到，是早期的接口
     * @param orderCode
     * @param reasonId
     * @param remark
     */
    @Override
    public void applyReFund(String orderCode, int reasonId, String remark) {
        Map<String, String> params = new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("refundReasonId",reasonId+"");
        params.put("refundRemark",remark);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.RETURN_REFUND, new OkHttpManager.ResultCallback<ApplyReturnResultBean>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.showLongToast(e.getMessage());
            }

            @Override
            public void onResponse(ApplyReturnResultBean response) {
                if(response!=null&&response.code.equals("0")){
                    mView.finishActivity();
                }
            }
        },params);
    }

    /**
     * 获取售后类型
     * @param orderCode
     */
    @Override
    public void aftersaleType(String orderCode) {
        mView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.AFTER_SALE_TYPE, new OkHttpManager.ResultCallback<AfterSaleTypeBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onResponse(AfterSaleTypeBean response) {
                mView.hideLoading();
                if(response!=null&&response.code.equals("0")){
                    mView.aftersaletype(response);
                }
            }
        },params);
    }

    /**
     * 获取换货的商品信息
     * @param orderCode
     * @param returnMpId
     * @param soItemId
     */
    @Override
    public void aftersaleChangeProduct(String orderCode, String returnMpId, String soItemId) {
        mView.showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("returnMpId",returnMpId);
        params.put("soItemId",soItemId);
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.AFTER_SALE_CHANGE_PRODUCT, new OkHttpManager.ResultCallback<ChangeProductBean>() {

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ChangeProductBean response) {
                if(response!=null&&response.code.equals("0")&&response.data!=null){
                    mView.changeProduct(response);
                }
            }
        },params);
    }

}
