package com.netease.nim.demo.yidou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.netease.nim.demo.R;
import com.netease.nim.demo.yidou.adapter.YdouAdapter;
import com.netease.nim.demo.yidou.model.YdouListModel;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class YDOUActivity extends UI implements View.OnClickListener{


    RecyclerView mRcv;

    EditText     mEtCusnum;
    private YdouAdapter mYdouAdapter;

    private String senNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ydou);
        mRcv=findView(R.id.rcv);
        mEtCusnum=findView(R.id.et_cusnum);
        findView(R.id.tv_send).setOnClickListener(this);

        initToolBar();
        initRcvView();
    }

    private void initRcvView() {
        initData();


        mYdouAdapter = new YdouAdapter(this, mList, new YdouAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(YdouListModel model) {
                for (YdouListModel ydouListModel : mList) {
                    ydouListModel.setChecked(false);
                }

                senNum = model.getNum();
                if (model.getNum().equals("ALL")) {
                    senNum = "5200";
                }
                model.setChecked(true);
                mYdouAdapter.notifyDataSetChanged();

            }
        });
        mRcv.setLayoutManager(new GridLayoutManager(this, 4));
        mRcv.setAdapter(mYdouAdapter);

    }

    private List<YdouListModel> mList = new ArrayList<>();

    private void initData() {
        //初始化数据
        mList.add(new YdouListModel("10", false));
        mList.add(new YdouListModel("20", false));
        mList.add(new YdouListModel("30", false));
        mList.add(new YdouListModel("50", false));
        mList.add(new YdouListModel("100", false));
        mList.add(new YdouListModel("200", false));
        mList.add(new YdouListModel("500", false));
        mList.add(new YdouListModel("ALL", false));


    }

    private void initToolBar() {
        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "赠送伊豆";
        setToolBar(R.id.toolbar, options);

    }

    public static void startActivityForResult(Activity activity, int reqCode) {
        Intent intent = new Intent(activity, YDOUActivity.class);
        activity.startActivityForResult(intent, reqCode);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_send){
            String num = mEtCusnum.getText().toString();
            if (num.equals("")) {
                ToastUtil.showLongToast(this, "赠送出" + senNum + "颗伊豆");
            } else {
                ToastUtil.showLongToast(this, "赠送出" + num + "颗伊豆");

            }
        }
    }
}
