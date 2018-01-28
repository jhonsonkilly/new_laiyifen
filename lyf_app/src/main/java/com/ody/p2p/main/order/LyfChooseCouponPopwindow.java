package com.ody.p2p.main.order;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.check.coupon.CouponBean;
import com.ody.p2p.check.coupon.UseCouponBean;
import com.ody.p2p.main.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.ToastUtils;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 来伊份  确认订单页面 优惠券选择
 * Created by caishengya on 2017/06/29.
 */

public class LyfChooseCouponPopwindow extends PopupWindow implements LyfChooseCouponToUseAdapter.LyfChooseCouponOnClick {

    private Context mContext;
    private SubmitListener listener;

    //添加优惠券相关
    private ImageView iv_back;
    private FrameLayout ll_add_coupon;
    private ImageView iv_add_close;
    private EditText et_cardnum;
    private EditText et_password;
    private TextView tv_submit;

    //优惠券列表相关
    private FrameLayout ll_coupon_list;
    private TextView tv_add;
    private ImageView iv_close;
    private CheckBox cb_check;
    private ListView lv_coupon;
    private TextView tv_choose_submit;

    private LyfChooseCouponToUseAdapter adapter;

    List<CouponBean.DataBean.CanUseCouponListBean> mData;

    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonUse;
    private RadioButton mRadioButtonNoUse;


    public LyfChooseCouponPopwindow(Context context) {
        this.mContext = context;
        //type--1 收银台 2 优惠券
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.popupwindow_lyf_choose_coupon, null);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setContentView(view);

        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        et_cardnum = (EditText) view.findViewById(R.id.et_cardnum);
        et_password = (EditText) view.findViewById(R.id.et_password);
        ll_add_coupon = (FrameLayout) view.findViewById(R.id.popupwindow_lyf_choose_coupon_framelayout_add_coupon);
        iv_add_close = (ImageView) view.findViewById(R.id.iv_add_close);
        tv_submit = (TextView) view.findViewById(R.id.tv_submit);

        ll_coupon_list = (FrameLayout) view.findViewById(R.id.popupwindow_lyf_choose_coupon_framelayout_list);
        iv_close = (ImageView) view.findViewById(R.id.iv_close);
        tv_add = (TextView) view.findViewById(R.id.tv_add);
        cb_check = (CheckBox) view.findViewById(R.id.cb_check);
        lv_coupon = (ListView) view.findViewById(R.id.popupwindow_lyf_choose_coupon_lv_coupon);
        tv_choose_submit = (TextView) view.findViewById(R.id.popupwindow_lyf_choose_coupon_tv_choose_submit);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.popupwindow_lyf_choose_coupon_radiogroup);
        mRadioButtonUse = (RadioButton) view.findViewById(R.id.popupwindow_lyf_choose_coupon_radiobutton_use);
        mRadioButtonNoUse = (RadioButton) view.findViewById(R.id.popupwindow_lyf_choose_coupon_radiobutton_no_use);

        adapter = new LyfChooseCouponToUseAdapter(mContext);
        lv_coupon.setAdapter(adapter);
        adapter.setmLyfChooseCouponOnClick(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList(true);
            }
        });
        iv_add_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList(true);
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_cardnum.getText().toString())) {
                    ToastUtils.showToast("请输入卡号");
                    return;
                }
                addCoupon(et_cardnum.getText().toString());
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList(false);
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (adapter.getCount() > 0) {
                        for (int i = 0; i < adapter.getCount(); i++) {
                            for (int j = 0; j < adapter.getItem(i).getCouponlist().size(); j++) {
                                adapter.getItem(i).getCouponlist().get(j).setSelected(0);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        tv_choose_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_check.isChecked()) {
                    saveUseCoupon("");
                    dismiss();
                } else {
                    chooseCoupon();
                }

            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.popupwindow_lyf_choose_coupon_radiobutton_use) {
                    useCouponlist(1);
                } else if (checkedId == R.id.popupwindow_lyf_choose_coupon_radiobutton_no_use) {
                    useCouponlist(0);
                }
            }
        });

        useCouponlist(1);
    }

    private void showList(boolean ishow) {
        if (ishow) {
            ll_coupon_list.setVisibility(View.VISIBLE);
            ll_add_coupon.setVisibility(View.GONE);
        } else {
            ll_add_coupon.setVisibility(View.VISIBLE);
            ll_coupon_list.setVisibility(View.GONE);
        }
    }

    private void chooseCoupon() {
        String couponId = "";
        if (adapter.getCount() > 0) {
            for (int i = 0; i < adapter.getCount(); i++) {
                for (int j = 0; j < adapter.getItem(i).getCouponlist().size(); j++) {
                    if (adapter.getItem(i).getCouponlist().get(j).getSelected() == 1) {
                        if (TextUtils.isEmpty(couponId)) {
                            couponId = adapter.getItem(i).getCouponlist().get(j).getCouponId();
                            Log.i("csy", "couponId = " + couponId);
                        } else {
                            couponId = couponId + "," + adapter.getItem(i).getCouponlist().get(j).getCouponId();
                        }
                    }
                }

            }
        }
        if (!TextUtils.isEmpty(couponId)) {
            dismiss();
            saveUseCoupon(couponId);
        } else {
            ToastUtils.showToast("请选择优惠券");
        }
    }

    private void useCouponlist(final int isAvailable) {

        Map<String, String> params = new HashMap<>();

        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));

        OkHttpManager.getAsyn(Constants.USE_COUPON, params, new OkHttpManager.ResultCallback<UseCouponBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(UseCouponBean response) {
                if (response != null && response.code.equals("0")) {
                    if (response.data != null && response.data.coupons != null && response.data.coupons.size() > 0) {
                        mData = new ArrayList<>();
                        for (int i = 0; i < response.data.coupons.size(); i++) {
                            if (response.data.coupons.get(i).isAvailable == isAvailable) {
                                mData.add(response.data.coupons.get(i));
                            }
                        }
                        if (mData.size() > 0) {
                            List<LyfCouponBean.CouponBean> list = creatData(mData);
                            Collections.sort(list, new Comparator<LyfCouponBean.CouponBean>() {
                                @Override
                                public int compare(LyfCouponBean.CouponBean t1, LyfCouponBean.CouponBean t2) {
                                    if(t2.getCouponlist().get(0).getThemeType()<=t1.getCouponlist().get(0).getThemeType()){
                                        return 1;
                                    }else{
                                        return -1;
                                    }
                                }
                            });
                            if (list != null && list.size() > 0) {
                                adapter.addData(list);
                                mRadioGroup.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (mData.size() == 0) {
                                if (isAvailable == 1) {
                                    ToastUtils.showToast("暂无可用优惠券");
                                } else {
                                    ToastUtils.showToast("暂无不可用优惠券");
                                }
                            }
                        }
                    } else {
                        cb_check.setChecked(true);
                        cb_check.setEnabled(false);
                        mRadioGroup.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void saveUseCoupon(String couponId) {

        Map<String, String> params = new HashMap<>();

        if (TextUtils.isEmpty(couponId)) {
            params.put("selected", 0 + "");
            params.put("couponId", "0");
        } else {
            if (couponId.contains(",")) {
                params.put("couponIds", couponId);
            } else {
                params.put("couponId", couponId);
            }
            params.put("selected", 1 + "");
        }

        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));

        OkHttpManager.getAsyn(Constants.SAVE_COUPON, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null && response.code.equals("0")) {
                    listener.showOrder();
                }
            }
        });
    }

    private void addCoupon(String couponCode) {
        Map<String, String> params = new HashMap<>();
        params.put("couponCode", couponCode);
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        OkHttpManager.postAsyn(Constants.BIND_COUPON, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showShort(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null && response.code.equals("0")) {
                    showList(true);
                    useCouponlist(1);
                }
            }
        }, params);
    }

    public void setListener(SubmitListener listener) {
        this.listener = listener;
    }

    @Override
    public void ChooseCounpon(int parentPos, int childPos) {
        cb_check.setChecked(false);
        if (adapter.getItem(parentPos).getCouponlist().get(childPos).isAvailable == 0) {
            return;
        }
        for (int j = 0; j < adapter.getItem(parentPos).getCouponlist().size(); j++) {
            if (j == childPos) {
                if (adapter.getItem(parentPos).getCouponlist().get(j).getSelected() == 1) {
                    adapter.getItem(parentPos).getCouponlist().get(j).setSelected(0);
                } else {
                    adapter.getItem(parentPos).getCouponlist().get(j).setSelected(1);
                }
            } else {
                adapter.getItem(parentPos).getCouponlist().get(j).setSelected(0);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public interface SubmitListener {
        void showOrder();
    }

    /**
     * 构造来伊份 优惠券数据格式
     *
     * @param list
     * @return
     */
    private List<LyfCouponBean.CouponBean> creatData(List<CouponBean.DataBean.CanUseCouponListBean> list) {
        List<String> merchantIdList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!merchantIdList.contains(list.get(i).merchantId)) {
                merchantIdList.add(list.get(i).merchantId);
            }
        }
        List<LyfCouponBean.CouponBean> data = new ArrayList<>();
        List<LyfCouponBean.CouponBean.Bean> beanList;
        LyfCouponBean.CouponBean couponBean;
        LyfCouponBean.CouponBean.Bean bean;
        for (int i = 0; i < merchantIdList.size(); i++) {
            couponBean = new LyfCouponBean.CouponBean();
            beanList = new ArrayList<>();
            couponBean.setCouponlist(beanList);
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).merchantId.equals(merchantIdList.get(i))) {
                    bean = new LyfCouponBean.CouponBean.Bean();
                    bean.setCouponValue(list.get(j).couponValue);
                    bean.setCouponId(list.get(j).couponId);
                    bean.setThemeTitle(list.get(j).themeTitle);
                    bean.setStartTime(list.get(j).startTime);
                    bean.setEndTime(list.get(j).endTime);
                    bean.setMoneyRule(list.get(j).moneyRule);
                    bean.setIsAvailable(list.get(j).isAvailable);
                    bean.setSelected(list.get(j).selected);
                    bean.setThemeType(list.get(j).themeType);
                    couponBean.getCouponlist().add(bean);
                    couponBean.setMerchantName(list.get(j).merchantName);
                    couponBean.setMerchantId(list.get(j).merchantId);
                }
            }
            data.add(couponBean);
        }
        return data.size() > 0 ? data : null;
    }
}
