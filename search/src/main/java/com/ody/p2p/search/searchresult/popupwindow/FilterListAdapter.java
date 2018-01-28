package com.ody.p2p.search.searchresult.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ody.p2p.base.BaseAdapter;
import com.ody.p2p.recycleviewutils.FullyGridLayoutManager;
import com.ody.p2p.search.R;
import com.ody.p2p.views.recyclerviewlayoutmanager.FullyLinearLayoutManager;
import com.ody.p2p.views.recyclerviewlayoutmanager.OneItemGridLayoutManager;

import java.util.List;

/**
 * Created by lxs on 2016/8/17.
 */
public class FilterListAdapter extends android.widget.BaseAdapter{

    public Context context;
    public List<ResultBean.AttributeResult> list;
    public ViewHolder holder = null;

    public FilterListAdapter(Context context, List<ResultBean.AttributeResult> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_filter, null);
            holder = new ViewHolder();
            holder.tv_filter_title = (TextView) view.findViewById(R.id.tv_filter_title);
            holder.tv_filter_open = (TextView) view.findViewById(R.id.tv_filter_open);
            holder.recycler_filter = (GridView) view.findViewById(R.id.recycler_filter);
            convertView = view;
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        final ResultBean.AttributeResult bean = list.get(position);
        holder.tv_filter_title.setText(bean.name);
        FilterListSelectAdapter adapter = new FilterListSelectAdapter(context,bean.attributeValues);
        holder.recycler_filter.setAdapter(adapter);
        holder.tv_filter_open.setSelected(bean.filterOpenFlag);
        if (bean.filterOpenFlag){
            holder.tv_filter_open.setText(R.string.pack_up);
            setListHeightAllClum(holder.recycler_filter);
        }else{
            holder.tv_filter_open.setText(R.string.all);
            setListHeightOneClum(holder.recycler_filter);
        }
        if (bean.attributeValues != null && bean.attributeValues.size() > 3){
            holder.tv_filter_open.setVisibility(View.VISIBLE);
        }else {
            holder.tv_filter_open.setVisibility(View.GONE);
        }
        holder.tv_filter_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.filterOpenFlag){
                    setListHeightOneClum(holder.recycler_filter);

                }else{
                    setListHeightAllClum(holder.recycler_filter);
                }
                bean.filterOpenFlag = !bean.filterOpenFlag;
                notifyDataSetChanged();
                if (initHeightListener != null){
                    initHeightListener.setHeight();
                }
            }
        });
        return  convertView;
    }


    public class ViewHolder{
        public TextView tv_filter_title;
        public TextView tv_filter_open;
        public GridView recycler_filter;
    }

    public void setListHeightAllClum(GridView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            if (i % 3 == 0){
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

    public void setListHeightOneClum(GridView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }


    public interface InitHeightListener{
        void setHeight();
    }

    public InitHeightListener initHeightListener;

    public void setHeightListener(InitHeightListener initHeightListener){
        this.initHeightListener = initHeightListener;
    }

}
