package com.ody.p2p.check.orderlist;

import com.google.gson.Gson;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ody on 2016/8/29.
 */
public class EvaluatePresenterImpl implements EvaluatePresenter {

    private EvaluateView mView;

    public EvaluatePresenterImpl(EvaluateView view) {
        mView = view;
    }


    //初始化商品评价页面
    @Override
    public void initEvaluate(String orderCode) {
//        http://dsapp.dev.odianyun.com/api/social/my/comment/init?
        // companyId=10&
        // orderCode=160831915338269890&
        // platformId=1&
        // ut=e155fb0677e0418dbc5e6e8187f8bb3c
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("orderCode", orderCode);
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.INIT_EVALUATE, params, new OkHttpManager.ResultCallback<EvaluateBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                mView.hideLoading();
            }

            @Override
            public void onResponse(EvaluateBean response) {
                mView.hideLoading();
                mView.showEvaluateInfo(response);
            }
        });
    }


    //提交商品评价
    @Override
    public void commiEvaluate(CommitEvaluateData data) {
        //ut	string	YES	用户登录token
        //UserMPCInputVO	UserMPCommentInputVO	YES	订单的商品评价集合
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        Gson gson = new Gson();
        String UserMPCInputVO = gson.toJson(data);
        params.put("inputJson", UserMPCInputVO);
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.COMMIT_EVALUATE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.failToast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                mView.hideLoading();
                if (response.code.equals("0")) {
                    mView.commitEvaluate(response);
                }
            }
        }, params);
    }

    //上传图片文件
    @Override
    public void upLoadPicture(final PhotoFileBean bean, final int position) {
        try {
            OkHttpManager.postAsyn(Constants.PUT_OBJECT_WITH_FORM, new OkHttpManager.ResultCallback<UpLoadBean>() {
                @Override
                public void onError(Request request, Exception e) {
                    mView.remove(bean, position);
                }

                @Override
                public void onResponse(UpLoadBean response) {
                    mView.upLoadResult(response, bean, position);
                }
            }, new File(bean.getFilePath()), "file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void evaluateRule() {
        Map<String, String> params = new HashMap<>();
        params.put("adCode", "comment_rule");
        params.put("pageCode", "APP_HOME");
        params.put("platform", "3");
        mView.showLoading();
        OkHttpManager.getAsyn(Constants.AD_LIST_NEW, params, new OkHttpManager.ResultCallback<FuncBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }

            @Override
            public void onResponse(FuncBean response) {
                if (null != response && null != response.getData() && null != response.getData().getComment_rule() && response.getData().getComment_rule().size() > 0) {
                    mView.skipToWeb(response.getData().getComment_rule().get(0).getLinkUrl());
                }
            }
        });

    }

    @Override
    public void submitAddtional(String inputJson) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("inputJson",inputJson);
        mView.showLoading();
        OkHttpManager.postAsyn(Constants.SUBMIT_ADDTIONAL_EVALUATE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
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
            public void onResponse(BaseRequestBean response) {
                if (response!=null&&response.code.equals("0")) {
                    mView.commitEvaluate(response);
                }
            }
        },params);
    }

}
