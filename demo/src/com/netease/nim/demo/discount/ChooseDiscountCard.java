package com.netease.nim.demo.discount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.discount.adapter.DiscountListAdapter;
import com.netease.nim.demo.discount.model.RequestYHQModel;
import com.netease.nim.demo.discount.model.YHQModel;
import com.netease.nim.demo.sync.OKhttpHelper;
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.demo.util.Constact;
import com.netease.nim.demo.yidiancard.model.DiscountModel;
import com.netease.nim.demo.yidiancard.widget.IOSDialog;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;

public class ChooseDiscountCard extends UI {

    RecyclerView mRcv;
    private DiscountListAdapter mYdCardListAdapter;
    IOSDialog dialog;
    private TextView count;
    private View     mTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_yd);

        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "选择优惠券";
        setToolBar(R.id.toolbar, options);
        initView();
        YHQ();
    }


    public static void startActivityForResult(Activity activity, int reqCode) {
        Intent intent = new Intent();
        intent.setClass(activity, ChooseDiscountCard.class);
        activity.startActivityForResult(intent, reqCode);
    }

    private List<DiscountModel> mDiscountModels = new ArrayList<>();

    private void initView() {
        mRcv = (RecyclerView) findViewById(R.id.rcv);


        mYdCardListAdapter = new DiscountListAdapter(mRows, new DiscountListAdapter.OnItemclickListener() {
            @Override
            public void onClick(YHQModel.DataBean.CanUseCouponListBean discountModel) {
                showDialog(discountModel);
            }
        });
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        mRcv.setAdapter(mYdCardListAdapter);

        count = (TextView) findViewById(R.id.tv_count);
        mTop = findViewById(R.id.top);
    }

    private void showDialog(final YHQModel.DataBean.CanUseCouponListBean discountModel) {
        dialog = new IOSDialog(this, R.style.customDialog, R.layout.dialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView amount = (TextView) dialog.findViewById(R.id.tv_amount);
        amount.setText("面额" + discountModel.getCouponValue() + "元优惠券");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendYHQ(discountModel);
                dialog.dismiss();
                //                Intent intent = new Intent();
                //                discountModel.setStatus("待领取");
                //                discountModel.setStatusType("优惠券");
                //                discountModel.setMemberId("99999");
                //                intent.putExtra(Constact.YHQMODEL, discountModel);
                //                setResult(Activity.RESULT_OK, intent);
                //                finish();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }


        });

    }

    private void sendYHQ(final YHQModel.DataBean.CanUseCouponListBean discountModel) {
        Map<String, Object> body = new HashMap<>();
        body.put("token", Preferences.getUserMainToken());
        body.put("cardNo", discountModel.getCouponCode());
        body.put("couponId", discountModel.getCouponId());
        body.put("type", discountModel.getType());
        OKhttpHelper.send(this, new Gson().toJson(body), Common.AdapterPath + "YHQ002", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                //
                System.out.println();
                try {
                    RequestYHQModel model = new Gson().fromJson(s, new TypeToken<RequestYHQModel>() {
                    }.getType());
                    if (model.getCode().equals("0")) {
                        Intent intent = new Intent();
                        discountModel.setStatus("待领取");
                        discountModel.setStatusType("优惠券");
                        discountModel.setMemberId(model.getMemberId());
                        discountModel.setCouponReceiveId(model.getCouponId());
                        discountModel.setIconUrl(model.getData().getUrl120x120());
                        discountModel.setSendUserId(Preferences.getUserAccount());
                        intent.putExtra(Constact.YHQMODEL, (Serializable) discountModel);
                        setResult(Activity.RESULT_OK, intent);
                        finish();

                    } else {
                        Toast.makeText(ChooseDiscountCard.this, model.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onSendFail(String err) {
                System.out.println(err);
            }
        });

    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private void YHQ() {
        Map<String, Object> body = new HashMap<>();
        body.put("token", Preferences.getUserMainToken());
        OKhttpHelper.send(this, new Gson().toJson(body), Common.AdapterPath + "YHQ001", new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                initYHQ(s);
            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }

    private List<YHQModel.DataBean.CanUseCouponListBean> mRows = new ArrayList<>();

    private void initYHQ(String string) {
        mRows.clear();
        YHQModel model = new Gson().fromJson(string, new TypeToken<YHQModel>() {
        }.getType());

        if (model.getCode().equals("0")) {
            int total = 0;
            mTop.setVisibility(View.VISIBLE);
        /*for (YHQModel.DataBean.CanUseCouponListBean data : model.getData().getCanUseCouponList()) {
            if (data.getCanShare() == 1)
                total++;
        }*/

            List<YHQModel.DataBean.CanUseCouponListBean> rows = model.getData().getCanUseCouponList();
            for (YHQModel.DataBean.CanUseCouponListBean row : rows) {
                if (row.getCanShare() == 1) {
                    mRows.add(row);
                }
            }
            count.setText(mRows.size() + "");
            mYdCardListAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showLongToast(this, model.getMessage());
        }


    }
}
