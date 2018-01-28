package com.ody.p2p.RefoundList;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ody on 2016/6/30.
 */
public class RefoundListImrp implements RefoundListPressenter {
    RefoundListView mView;
    boolean flage1 = false;
    boolean flage2 = false;
    List<AfterSaleBean.OrderRefundVOs> mList;
    private static int pageSize = 5;
    List<RefoundBaseBean> mDataList;

    public RefoundListImrp(RefoundListView mView) {
        this.mView = mView;
    }

    /**
     * 获取列表
     */
    @Override
    public void initRefoundList(final int pageNum) {
        mDataList = new ArrayList<>();
        getType1(pageNum);
    }

    private void getType1(final int pageNum) {
        if (pageNum > 1 && flage1) {//下拉没有更多的数据
            getType2(pageNum);
            return;
        } else {
            flage1 = false;
        }
        mView.showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("pageSize", pageSize + "");
        map.put("afterSaleType", "1");//订单状态(1 退款 2退货)
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.AFTERSALELIST, new OkHttpManager.ResultCallback<AfterSaleBean>() {
            @Override
            public void onError(Request request, Exception e) {
                getType2(pageNum);
                mView.hideLoading();
            }

            @Override
            public void onResponse(AfterSaleBean response) {
                mView.hideLoading();
                if (null != response.getData() && null != response.getData().getOrderRefundVOs() && response.getData().getOrderRefundVOs().size() > 0) {
                    if (response.getData().getOrderRefundVOs().size() < pageSize) {
                        flage1 = true;
                    }
                    for (AfterSaleBean.OrderRefundVOs or : response.getData().getOrderRefundVOs()) {
                        RefoundBaseBean bean = new RefoundBaseBean();
                        bean.setType(1);
                        bean.setOrderRefundVOs(or);
                        mDataList.add(bean);
                    }
//                    mView.getListData(response.getData().getOrderRefundVOs());
                    getType2(pageNum);
                }
            }
        }, map);
    }

    private void getType2(int pageNum) {
        if (pageNum > 1 && flage2) {//下拉没有更多的数据
            return;
        } else {
            flage2 = false;
        }
        mView.showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("pageSize", pageSize + "");
        map.put("afterSaleType", "2");//订单状态(1 退款 2退货)
        map.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.AFTERSALELIST, new OkHttpManager.ResultCallback<AfterSaleBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mView.hideLoading();
                mView.getListData(mDataList);
            }

            @Override
            public void onResponse(AfterSaleBean response) {
                mView.hideLoading();
                if (null != response.getData() && null != response.getData().getOrderRefundVOs() && response.getData().getOrderRefundVOs().size() > 0) {
                    if (response.getData().getOrderRefundVOs().size() < pageSize) {
                        flage2 = true;
                    }
                    for (AfterSaleBean.OrderRefundVOs or : response.getData().getOrderRefundVOs()) {
                        RefoundBaseBean bean = new RefoundBaseBean();
                        bean.setType(2);
                        bean.setOrderRefundVOs(or);
                        mDataList.add(bean);
                    }
                    mView.getListData(mDataList);
                }
            }
        }, map);
    }
}
