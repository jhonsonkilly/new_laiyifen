package com.netease.nim.demo.main.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.demo.main.model.QuickBean;

import java.util.List;

/**
 * Created by jasmin on 2017/9/1.
 */

public class QuickAdapter extends BaseQuickAdapter<QuickBean,BaseViewHolder> {

    public QuickAdapter(int layoutResId, List<QuickBean> data) {
        super(layoutResId, data);
    }
    public QuickAdapter(List<QuickBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, QuickBean quickBean) {
        baseViewHolder.setText(R.id.tv_nickname, quickBean.getUser())
                .setText(R.id.tv_message, quickBean.getTitle())
                .setText(R.id.tv_date_time,quickBean.getDate())
                .setImageResource(R.id.img_head,quickBean.getImgID());
        baseViewHolder.getView(R.id.top_line).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.img_msg_status).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.tv_online_state).setVisibility(View.GONE);

    }


}
