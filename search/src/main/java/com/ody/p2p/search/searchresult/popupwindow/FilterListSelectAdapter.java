package com.ody.p2p.search.searchresult.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ody.p2p.recycleviewutils.FullyGridLayoutManager;
import com.ody.p2p.search.R;

import java.util.List;

/**
 * Created by lxs on 2016/8/17.
 */
public class FilterListSelectAdapter extends BaseAdapter {

    public Context context;
    public List<ResultBean.AttributeValue> list;

    public FilterListSelectAdapter(Context context, List<ResultBean.AttributeValue> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recyler_class, null);
            holder = new ViewHolder();
            holder.cb_item = (CheckBox) view.findViewById(R.id.cb_item);
            convertView = view;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ResultBean.AttributeValue bean = list.get(position);
        holder.cb_item.setText(bean.value);
        holder.cb_item.setChecked(bean.isChecked);
        holder.cb_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (position != 0) {
                        bean.isChecked = isChecked;
                        list.get(0).isChecked = false;
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).isChecked = false;
                            bean.isChecked = true;
                        }
                    }
                } else {
                    boolean hasFlag = false;
                    bean.isChecked = false;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChecked) {
                            hasFlag = true;
                        }
                    }
                    if (!hasFlag) {
                        list.get(0).isChecked = true;
                    }
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }


    public class ViewHolder {
        public CheckBox cb_item;
    }
}
