package com.ody.p2p.scanhistory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.data.ScanHistoryBean;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class ScanHistoryActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv;
    private List<ScanHistoryBean> mDatas;
    private TextView tv_clear;
    private CustomDialog dialog;
    private ScanHisAdapter adapter;
    private ImageView iv_back;
    private LinearLayout ll_nohistory;
    private TextView txt_goshopp;

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_scan_history;
    }

    @Override
    public void initView(View view) {
        lv = (ListView) view.findViewById(R.id.lv_scan_hist);
        tv_clear = (TextView)view.findViewById(R.id.tv_head_right);
        iv_back = (ImageView) view.findViewById(R.id.iv_head_back);
        ll_nohistory = (LinearLayout) view.findViewById(R.id.ll_nohistory);
        txt_goshopp = (TextView) view.findViewById(R.id.txt_goshopp);
        tv_clear.setVisibility(View.VISIBLE);
        lv.setSelected(true);

        iv_back.setOnClickListener(this);
        txt_goshopp.setOnClickListener(this);

        helper = DatabaseHelper.getHelper(this);
//        try {
            ScanHistoryBean scanHistoryBean;
            mDatas = new ArrayList<ScanHistoryBean>();
            db = helper.getReadableDatabase();
            String sqlStr = "select * from history_search order by _id desc limit ?,?";
            Cursor custor = db.rawQuery(sqlStr, new String[]{String.valueOf(0), String.valueOf(100)});
            while (custor.moveToNext()) {
                int id = custor.getInt(custor.getColumnIndex("_id"));
                String spId = custor.getString(custor.getColumnIndex("spId"));
                String cost = custor.getString(custor.getColumnIndex("cost"));
                String url = custor.getString(custor.getColumnIndex("url"));
                String spName = custor.getString(custor.getColumnIndex("spName"));

                scanHistoryBean = new ScanHistoryBean();
                scanHistoryBean.setId(id);
                scanHistoryBean.setSpId(spId);
                scanHistoryBean.setCost(cost);
                scanHistoryBean.setSpName(spName);
                scanHistoryBean.setUrl(url);
                mDatas.add(scanHistoryBean);
            }
            custor.close();
            if (mDatas == null || mDatas.size() == 0){
                ll_nohistory.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
            }
            else{
                ll_nohistory.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
            }
        if (mDatas == null || mDatas.size() <=0){
            mDatas = new ArrayList<>();
            tv_clear.setClickable(false);
        }else {
            tv_clear.setClickable(true);
            tv_clear.setOnClickListener(this);
        }
        adapter = new ScanHisAdapter(this,mDatas);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://shoppingdetail");
//                activityRoute.withParams(Constants.SP_ID,mDatas.get(position).getSpId()).open();


                Bundle bd=new Bundle();
                bd.putString(Constants.SP_ID,mDatas.get(position).getSpId());
                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL,bd);
            }
        });

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        DatabaseHelper.getHelper(this).close();
    }
    @Override
    public void onClick(View v) {
            //圆角的dialog的显示

                /*
                * 这里会有个清空的操作*/
            if (v.getId() == R.id.tv_head_right) {
                if (mDatas != null && mDatas.size() != 0){
                    View view = LayoutInflater.from(ScanHistoryActivity.this).inflate(R.layout.myhomepager_scan_dialog_item, null);
//                    TextView tv_cancel = (TextView) view.findViewById(R.id.tv_dialog_cancel);
//                    TextView tv_positive = (TextView) view.findViewById(R.id.tv_dialog_positive);
//                    tv_cancel.setOnClickListener(new View.OnClickListener() {
//                                                     @Override
//                                                     public void onClick(View v) {
//                                                         dialog.dismiss();
//                                                     }
//                                                 });
//                    tv_positive.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            try {
//                                helper.getriciDao().delete(mDatas);
//                            } catch (Exception exception) {
//                                exception.printStackTrace();
//                            }
//                            mDatas = new ArrayList<ScanHistoryBean>();
//                            adapter = new ScanHisAdapter(getContext(),mDatas);
//                            lv.setAdapter(adapter);
//                            ll_nohistory.setVisibility(View.VISIBLE);
//                            lv.setVisibility(View.GONE);
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog = new AlertDialog.Builder(this, R.style.CircleDialog)
//                            .setView(view)
//                            .show();
                    dialog = new CustomDialog(ScanHistoryActivity.this,getContext().getString(R.string.clear_scanhistory));
                    dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                        @Override
                        public void Confirm() {
                            try {
                                helper.getriciDao().delete(mDatas);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            mDatas = new ArrayList<ScanHistoryBean>();
                            adapter = new ScanHisAdapter(getContext(),mDatas);
                            lv.setAdapter(adapter);
                            ll_nohistory.setVisibility(View.VISIBLE);
                            lv.setVisibility(View.GONE);

                        }
                    });
                    dialog.show();

            }}else if( v.getId() == R.id.iv_head_back){
                finish();
            }
        else if(v.getId() == R.id.txt_goshopp){
//                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://main");
//                activityRoute.withParams(Constants.GO_MAIN,0).open();

                Bundle bd=new Bundle();
                bd.putInt(Constants.GO_MAIN,0);
                JumpUtils.ToActivity(JumpUtils.MAIN,bd);
            }
    }

}
