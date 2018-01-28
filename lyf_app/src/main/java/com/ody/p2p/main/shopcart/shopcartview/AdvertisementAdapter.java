package com.ody.p2p.main.shopcart.shopcartview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.main.R;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.views.scrollbanner.HeadLinesBean;
import com.ody.p2p.webactivity.WebActivity;

import java.util.List;

import static android.R.attr.id;
import static com.alibaba.mobileim.YWChannel.getResources;

/**
 * Created by pzy on 2016/12/12.
 */
public class AdvertisementAdapter extends BaseRecyclerViewAdapter<Ad> {
    public AdvertisementAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;
        final Ad mData=mDatas.get(position);
        viewHolder.tv_title.setText(mData.name);
        viewHolder.tv_sub_text.setText(mData.content);
        viewHolder.tv_sub_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.toActivity(mData);
            }
        });
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shopcart_advatisement,parent,false));
    }

    class ViewHolder extends BaseRecyclerViewHolder{
        TextView tv_title,tv_sub_text;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_sub_text=(TextView)itemView.findViewById(R.id.tv_sub_text);
        }
    }
}
