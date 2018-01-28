package com.netease.nim.demo.redpacket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.redpacket.adapter.RedPacketRecordAdapter;
import com.netease.nim.demo.redpacket.model.RedRecordModel;
import com.netease.nim.demo.util.OptionsPickerView;
import com.netease.nim.demo.util.SelectDialog;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RedPacketRecordActivity extends UI implements OnLoadmoreListener {

    private RecyclerView       mRcv;
    private SmartRefreshLayout mSmartRefreshLayout;
    private int mPage = 2;
    private RedRecordModel.DataBean mData;
    private RedPacketRecordAdapter mRedPacketRecordAdapter;
    private List<RedRecordModel.DataBean.SendListBean> mSendList     = new ArrayList<>();
    private int                                        redRecordType = 1;  //1---收到的红包  2---发出的红包
    private TextView mYear;
    private String year = "";
    private OptionsPickerView mPvOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packet_record);
        year = new SimpleDateFormat("yyyy").format(new Date());

        initView();
    }


    private void initView() {
        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "红包记录";
        options.navigateId = R.drawable.icon_back_red_packet;
        setToolBar(R.id.toolbar, options);

        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smart_refresh_layout);
        mSmartRefreshLayout.setOnLoadmoreListener(this);

        findViewById(R.id.switch_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> names = new ArrayList<>();
                names.add("收到的红包");
                names.add("发出的红包");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                redRecordType = 1;
                                mPage = 1;
                                mSendList.clear();
                                initData(1);

                                break;

                            case 1:
                                redRecordType = 2;
                                mPage = 1;
                                mSendList.clear();
                                initData(1);

                                break;


                        }

                    }
                }, names);

            }
        });

        initRcv();
        initChoiceTime();

        //年份选择

        mYear = (TextView) findViewById(R.id.tv_year);
        mYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeChice();
            }
        });

        initData(1);

    }

    private void showTimeChice() {
        mPvOptions.show();
    }

    private void initChoiceTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String format = sdf.format(date);


        final ArrayList<String> options1Items = new ArrayList<>();
        options1Items.add("" + (Integer.parseInt(format) - 3));
        options1Items.add("" + (Integer.parseInt(format) - 2));
        options1Items.add("" + (Integer.parseInt(format) - 1));
        options1Items.add("" + format);

        //返回的分别是三个级别的选中位置
        mPvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String y = options1Items.get(options1);
                mYear.setText(y + "年");
                mPage = 1;
                mSendList.clear();
                year = y;
                initData(1);
            }
        })
                .setSelectOptions(3)
                .build();
        mPvOptions.setPicker(options1Items);

        mPvOptions.setOnEditListener(new OptionsPickerView.OnEditListener() {
            @Override
            public void onEdit(String s) {
                for (String options1Item : options1Items) {
                    if (options1Item.contains(s)) {
                        int i = options1Items.indexOf(options1Item);
                        mPvOptions.setSelectOptions(i);
                    }
                }

            }
        });


    }


    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    private void initData(final int page) {
        String url = "http://beta.touch.laiyifen.com/api/my/redPacket/packetsRecord?";
        String utl2 = url + "ut=" + Preferences.getUserMainToken() + "&year=" + year + "&type=" + redRecordType + "&page=" + page;
        OKhttpHelper.getSend(this, utl2, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    RedRecordModel model = new Gson().fromJson(s, new TypeToken<RedRecordModel>() {
                    }.getType());

                    if (model.getCode().equals("0")) {
                        mData = model.getData();

                        for (RedRecordModel.DataBean.SendListBean sendListBean : mData.getSendList()) {
                            mSendList.add(sendListBean);
                        }

                        if (mSendList.size() != 0) {
                            mPage++;
                        }
                        mRedPacketRecordAdapter.setNewData(mData, redRecordType);
                        mRedPacketRecordAdapter.notifyDataSetChanged();

                    }


                } catch (Exception e) {

                }
            }

            @Override
            public void onSendFail(String err) {

            }
        });
    }

    private void initRcv() {
        mRcv = (RecyclerView) findViewById(R.id.rcv);
        mRedPacketRecordAdapter = new RedPacketRecordAdapter(this, mData, mSendList, redRecordType);
        mRcv.setAdapter(mRedPacketRecordAdapter);
        mRcv.setLayoutManager(new LinearLayoutManager(this));


    }

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, RedPacketRecordActivity.class);
        activity.startActivity(intent);


    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore();
        initData(mPage);
    }
}
