package com.ody.p2p.check.orderlist;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangmeijuan on 16/6/17.
 */
public class OrderListPresenterImpl implements OrderListPresenter {
    private OrderListView orderListView;
    public static int PAGE_SIZE=10;
    protected String ORDER_LIST= Constants.OEDER_LIST;
    public OrderListPresenterImpl(OrderListView orderListView){
        this.orderListView=orderListView;
    }

    @Override
    public void orderlist(final int pageNo, int status) {
        Map<String,String> params=new HashMap<>();
        params.put("orderStatus",status+"");
        params.put("pageNo",pageNo+"");
        params.put("pageSize",PAGE_SIZE+"");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.getAsyn(ORDER_LIST, params ,new OkHttpManager.ResultCallback<OrderListBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(OrderListBean response) {
                if(response==null){
                    return;
                }
                if(response.code.equals("0")){
                    orderListView.orderlist(response);
                }
            }
        });
    }

    @Override
    public void cancelOrder(final String orderCode, final String orderMoney, final String deliveryFee) {
        Map<String,String> params=new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.CANCEL_ORDER, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if(response==null){
                    return;
                }
                if(response.code.equals("0")){
                    orderListView.refreshlist();
                }

            }
        },params);
    }

    @Override
    public void confrimReceive(String orderCode) {
        Map<String,String> params=new HashMap<>();
        params.put("orderCode",orderCode);
        params.put("ut",OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        OkHttpManager.postAsyn(Constants.CONFIRM_RECEIVE, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                orderListView.refreshlist();
            }
        },params);
    }

    @Override
    public void setOrderListUrl(String orderListUrl) {
        this.ORDER_LIST=orderListUrl;
    }
}
