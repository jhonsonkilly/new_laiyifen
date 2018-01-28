package com.ody.p2p.message.sysmessagelist;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.DateTimeUtils;

import java.util.List;

/**
 * Created by ody on 2016/8/15.
 */
public class SysMessageListAdapter extends BaseRecyclerViewAdapter<SysMessageListBean.Dataes> {

    public SysMessageListAdapter(Context context, List<SysMessageListBean.Dataes> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        viewHolder holde = (viewHolder) holder;
        holde.tv_sys_message_item_title.setText(mDatas.get(position).getMsgTitle());

        holde.tv_sysmessage_item_text.setText(Html.fromHtml(mDatas.get(position).getMsgContent()));

        holde.tv_sysmessage_item_time.setText(DateTimeUtils.getformatFriendly(mDatas.get(position).getSendTime()));
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item_sysmessage, parent, false));
    }

    class viewHolder extends BaseRecyclerViewHolder {
        TextView tv_sys_message_item_title, tv_sysmessage_item_text, tv_sysmessage_item_time;

        public viewHolder(View itemView) {
            super(itemView);
            tv_sysmessage_item_time = (TextView) itemView.findViewById(R.id.tv_sysmessage_item_time);
            tv_sysmessage_item_text = (TextView) itemView.findViewById(R.id.tv_sysmessage_item_text);
            tv_sys_message_item_title = (TextView) itemView.findViewById(R.id.tv_sys_message_item_title);
        }
    }
}
