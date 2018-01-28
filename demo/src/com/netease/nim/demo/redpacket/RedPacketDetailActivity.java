package com.netease.nim.demo.redpacket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.redpacket.adapter.RedPacketReciverAdapter;
import com.netease.nim.demo.redpacket.model.PacketDetailReturnModel;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.sync.OKhttpHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 红包详情UI
 */
public class RedPacketDetailActivity extends UI implements OnLoadmoreListener {

    private static String DATA    = "data";
    private static String TYPE    = "TYPE";
    private static String ISMINE  = "isMine";
    private int id; //红包id
    private int mPage = 1;
    private RecyclerView mRcv;
    List<PacketDetailReturnModel.DataBean.ReceiveListBean> receiveListBean = new ArrayList<>();
    private RedPacketReciverAdapter mRedPacketReciverAdapter;
    private PacketDetailReturnModel.DataBean.PacketInfoBean mPacketInfo;
    private int                                             isMine;  //1--自己的
    private SmartRefreshLayout                              mSmartRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packet_detail);
        isMine = getIntent().getIntExtra(ISMINE, -1);
        initView();

        initData(mPage);

    }

    private void initData(final int page) {
        id = getIntent().getIntExtra(DATA, 0);

        String url = "http://beta.touch.laiyifen.com/api/my/redPacket/packetDetail?";
        String utl2 = url + "ut=" + Preferences.getUserMainToken() + "&redPacketId=" + id + "&page=" + page;
        OKhttpHelper.getSend(this, utl2, new OKhttpHelper.OnSendSuccessListener() {
            @Override
            public void onSendSuccess(String s) {
                try {
                    PacketDetailReturnModel model = new Gson().fromJson(s, new TypeToken<PacketDetailReturnModel>() {
                    }.getType());

                    if (model.getCode().equals("0")) {
                        PacketDetailReturnModel.DataBean data = model.getData();
                        mPacketInfo = data.getPacketInfo();

                        //如果是随机红包
                        List<PacketDetailReturnModel.DataBean.ReceiveListBean> receiveList = data.getReceiveList();
                        for (PacketDetailReturnModel.DataBean.ReceiveListBean receive : receiveList) {
                            receiveListBean.add(receive);
                        }

                        if (receiveList.size() != 0) {
                            mPage++;
                        }

                        mRedPacketReciverAdapter.setData(mPacketInfo);
                        mRedPacketReciverAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onSendFail(String err) {

            }
        });

    }

    private void initView() {
        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "红包详情";
        options.navigateId = R.drawable.icon_back_red_packet;
        setToolBar(R.id.toolbar, options);

        mRcv = (RecyclerView) findViewById(R.id.rcv);
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smart_refresh_layout);
        mSmartRefreshLayout.setOnLoadmoreListener(this);
        findViewById(R.id.record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedPacketRecordActivity.startActivity(RedPacketDetailActivity.this);
            }
        });
        initRcv();
    }

    private void initRcv() {
        mRedPacketReciverAdapter = new RedPacketReciverAdapter(this, mPacketInfo, receiveListBean, getIntent().getStringExtra(TYPE), isMine);
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        mRcv.setAdapter(mRedPacketReciverAdapter);


    }


    /**
     * @param activity
     * @param data
     * @param type
     * @param isMine   1--自己  0--非自己
     */
    public static void startActivity(Activity activity, int data, String type, int isMine) {
        Intent intent = new Intent(activity, RedPacketDetailActivity.class);
        intent.putExtra(DATA, data);
        intent.putExtra(TYPE, type);
        intent.putExtra(ISMINE, isMine);
        activity.startActivity(intent);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore();
        initData(mPage);
    }
}
