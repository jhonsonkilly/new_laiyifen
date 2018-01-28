package com.netease.nim.demo.yidiancard;

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
import com.netease.nim.demo.sync.util.Common;
import com.netease.nim.demo.yidiancard.adapter.YDCardListAdapter;
import com.netease.nim.demo.yidiancard.model.YKD001Model;
import com.netease.nim.demo.yidiancard.model.YKD002Model;
import com.netease.nim.demo.yidiancard.widget.IOSDialog;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChooseYDCard extends UI {

    RecyclerView mRcv;
    private YDCardListAdapter mYdCardListAdapter;
    IOSDialog dialog;
    private TextView count;
    private View     mTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_yd);

        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "选择伊点卡";
        setToolBar(R.id.toolbar, options);
        initView();
        initData();
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Map<String, Object> body = new HashMap<>();
                    body.put("token", Preferences.getUserMainToken());

                    RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(body));
                    Request request = new Request.Builder()
                            .url(Common.AdapterPath + "YDK001")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        final String string = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initResponse(string);
                            }
                        });
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private List<YKD001Model.RowsBean> mRowsBeen = new ArrayList<>();

    private void initResponse(String string) {
        try {
            mRowsBeen.clear();
            YKD001Model model = new Gson().fromJson(string, new TypeToken<YKD001Model>() {
            }.getType());
            if (model.getCode().equals("0")) {
                List<YKD001Model.RowsBean> rows = model.getRows();
                for (YKD001Model.RowsBean row : rows) {
                    if (row.isCanBeSended()) {
                        mRowsBeen.add(row);
                    }
                }
                mTop.setVisibility(View.VISIBLE);
                count.setText("" + mRowsBeen.size());
                if (mYdCardListAdapter != null) {
                    mYdCardListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtil.showLongToast(this, "网络错误");
            }
        } catch (Exception e) {

        }
    }

    public static void startActivityForResult(Activity activity, int reqCode) {
        Intent intent = new Intent();
        intent.setClass(activity, ChooseYDCard.class);
        activity.startActivityForResult(intent, reqCode);
    }


    private void initView() {
        mRcv = (RecyclerView) findViewById(R.id.rcv);

        mYdCardListAdapter = new YDCardListAdapter(mRowsBeen, new YDCardListAdapter.OnItemclickListener() {
            @Override
            public void onClick(YKD001Model.RowsBean discountModel) {
                showDialog(discountModel);
            }

        });
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        mRcv.setAdapter(mYdCardListAdapter);
        count = (TextView) findViewById(R.id.tv_count);
        mTop = findViewById(R.id.top);
    }

    private void showDialog(final YKD001Model.RowsBean discountModel) {
        dialog = new IOSDialog(this, R.style.customDialog, R.layout.dialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView amount = (TextView) dialog.findViewById(R.id.tv_amount);
        amount.setText("面额" + discountModel.getAmount() + "元伊点卡");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendYDK(discountModel);

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }


        });

    }

    private void sendYDK(final YKD001Model.RowsBean discountModel) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Map<String, Object> body = new HashMap<>();
                    body.put("token", Preferences.getUserMainToken());
                    body.put("ycardNo", discountModel.getCardNo());
                    body.put("password", discountModel.getPwd());

                    RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(body));
                    Request request = new Request.Builder()
                            .url(Common.AdapterPath + "YDK002")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        final String string = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initSendYDK(string, discountModel);
                            }
                        });
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initSendYDK(String string, YKD001Model.RowsBean discountModel) {
        dialog.dismiss();
        try {
            YKD002Model model = new Gson().fromJson(string, new TypeToken<YKD002Model>() {
            }.getType());
            if (model.isSuccess()) { //操作成功
                Intent intent = new Intent();

                discountModel.setMemberId(model.getMemberId());
                discountModel.setType("伊点卡");
                discountModel.setStatus("待领取");
                discountModel.setSendUserId(Preferences.getUserAccount());
                intent.putExtra("YDKMOEL", discountModel);
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(ChooseYDCard.this, model.getMsg(), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        }

    }
}
