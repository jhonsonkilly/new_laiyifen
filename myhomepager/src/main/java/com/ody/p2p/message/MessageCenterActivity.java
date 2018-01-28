package com.ody.p2p.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.utils.DateTimeUtils;

/**
 * Created by ody on 2016/8/12.
 */
public class MessageCenterActivity extends BaseActivity implements MessageCenterView, View.OnClickListener {
    LinearLayout linear_system_message, linear_customer;
    ImageView img_finish_icon;
    RecyclerView recyle_message_list;
    MessageCenterPressenter mPressenter;

    TextView tv_ds_kefu_title, tv_ds_kefu_time, tv_ds_kefu_info, tv_message_num;
    TextView tv_ds_sys_title, tv_ds_sys_time, tv_ds_sys_info;

    @Override
    public void initPresenter() {
        mPressenter = new MessageCenterPressenterImpr(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPressenter.getMsgSummary();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initView(View view) {
        recyle_message_list = (RecyclerView) view.findViewById(R.id.recyle_message_list);
        tv_message_num = (TextView) view.findViewById(R.id.tv_message_num);
        img_finish_icon = (ImageView) view.findViewById(R.id.img_finish_icon);
        linear_customer = (LinearLayout) view.findViewById(R.id.linear_customer);
        linear_system_message = (LinearLayout) view.findViewById(R.id.linear_system_message);
        tv_ds_kefu_title = (TextView) view.findViewById(R.id.tv_ds_kefu_title);
        tv_ds_kefu_time = (TextView) view.findViewById(R.id.tv_ds_kefu_time);
        tv_ds_kefu_info = (TextView) view.findViewById(R.id.tv_ds_kefu_info);
        tv_ds_sys_title = (TextView) view.findViewById(R.id.tv_ds_sys_title);
        tv_ds_sys_time = (TextView) view.findViewById(R.id.tv_ds_sys_time);
        tv_ds_sys_info = (TextView) view.findViewById(R.id.tv_ds_sys_info);


        img_finish_icon.setOnClickListener(this);
        linear_system_message.setOnClickListener(this);
        linear_customer.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        recyle_message_list.setLayoutManager(RecycleUtils.getLayoutManager(getContext()));
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void getHelpCenterBean(MessageCenterBean.Data mdata) {
//        tv_ds_kefu_title.setText(mdata.getCustomer().getCustomeLabel());
//        if (mdata.getCustomer().getLatestTime() > 1000) {
//            tv_ds_kefu_time.setText(DateTimeUtils.getformatFriendly(mdata.getCustomer().getLatestTime()));
//        }
//        tv_ds_kefu_info.setText(mdata.getCustomer().getCustomeMark());
        tv_ds_sys_title.setText(mdata.getLabelName());
        if (mdata.getUnReadMsgCount() < 1) {
            tv_message_num.setVisibility(View.GONE);
        } else if (mdata.getUnReadMsgCount() < 100) {
            tv_message_num.setVisibility(View.VISIBLE);
            tv_message_num.setText(mdata.getUnReadMsgCount() + "");
        } else {
            tv_message_num.setVisibility(View.VISIBLE);
            tv_message_num.setText("99");
        }
        if (mdata.getLatestTime() > 1000) {
            tv_ds_sys_time.setText(DateTimeUtils.getformatFriendly(mdata.getLatestTime()));
        }
        tv_ds_sys_info.setText(Html.fromHtml(mdata.getMsgContent()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.linear_system_message) {
            skipActivity("sysmessagelist");
        } else if (v.getId() == R.id.img_finish_icon) {
            finish();
        } else if (v.getId() == R.id.linear_customer) {
        }
    }
}