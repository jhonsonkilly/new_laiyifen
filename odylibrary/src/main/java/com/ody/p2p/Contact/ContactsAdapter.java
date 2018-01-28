package com.ody.p2p.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by pzy on 2017/3/9.
 */

public class ContactsAdapter extends BaseRecyclerViewAdapter<ContactInfoData> {
    public ContactsAdapter(Context context, List<ContactInfoData> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHolder vHolder=(ViewHolder)holder;
        vHolder.tv_name.setText(mDatas.get(position).getName());
        vHolder.tv_phone.setText(mDatas.get(position).getPhone());
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_contacts, parent, false));
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tv_name,tv_phone;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            tv_phone=(TextView)itemView.findViewById(R.id.tv_phone);

        }
    }
}
