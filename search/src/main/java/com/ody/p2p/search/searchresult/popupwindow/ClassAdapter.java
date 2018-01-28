package com.ody.p2p.search.searchresult.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ody.p2p.search.R;

import java.util.List;

/**
 * Created by lxs on 2016/6/14.
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassBean> classList;
    private Context context;


    public ClassAdapter(List<ClassBean> classList, Context context) {
        this.classList = classList;
        this.context = context;
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ClassViewHolder holder = new ClassViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyler_class, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, final int position) {
        holder.cb_item.setText(classList.get(position).className);
        holder.cb_item.setChecked(classList.get(position).isSelected);
        holder.cb_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                classList.get(position).isSelected = isChecked;
            }
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {

        public CheckBox cb_item;

        public ClassViewHolder(View view) {
            super(view);
            cb_item = (CheckBox) view.findViewById(R.id.cb_item);
        }
    }

}

