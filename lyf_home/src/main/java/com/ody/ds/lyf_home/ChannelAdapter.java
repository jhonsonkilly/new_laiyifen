package com.ody.ds.lyf_home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.webactivity.WebActivity;

import java.util.List;

/**
 * Created by mac on 2017/9/6.
 */

public class ChannelAdapter extends BaseRecyclerViewAdapter {

    List<AppHomePageBean.StaticData.Channels> list;


    public ChannelAdapter(Context context, List datas) {
        super(context, datas);

        this.list = datas;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        final AppHomePageBean.StaticData.Channels channels = (AppHomePageBean.StaticData.Channels) getDatas().get(position);
        ChannelAdapter.ChannelHolder holder1 = (ChannelAdapter.ChannelHolder) holder;
        if (!TextUtils.isEmpty(channels.text)) {
            holder1.ic_tx.setVisibility(View.VISIBLE);
            holder1.ic_tx.setText(channels.text);
        } else {
            holder1.ic_tx.setVisibility(View.GONE);
        }

        GlideUtil.display(mContext, channels.src).into(holder1.ic_img);
        holder1.ic_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(channels.link.appData)) {
                    JumpUtils.ToActivity(channels.link.appData);

                }
            }
        });
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new ChannelAdapter.ChannelHolder(LayoutInflater.from(mContext).inflate(R.layout.item_channel_item, parent, false)));
    }


    public static class ChannelHolder extends BaseRecyclerViewHolder {

        public TextView ic_tx;
        public ImageView ic_img;
        private final LinearLayout ic_ll;


        public ChannelHolder(View view) {
            super(view);
            ic_tx = (TextView) view.findViewById(R.id.ic_tx);
            ic_img = (ImageView) view.findViewById(R.id.ic_img);
            ic_ll = (LinearLayout) view.findViewById(R.id.ic_ll);

        }
    }
}
