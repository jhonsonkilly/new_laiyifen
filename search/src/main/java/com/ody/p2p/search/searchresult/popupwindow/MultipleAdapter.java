package com.ody.p2p.search.searchresult.popupwindow;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.search.R;

import java.util.List;


/**
 * Created by lxs on 2016/6/9.
 */
public class MultipleAdapter extends RecyclerView.Adapter<MultipleAdapter.ClassViewHolder>{

    private List<ResultBean.SortBean> classList;
    private Context context;
    private int res;
    private ColorStateList color;

    public MultipleAdapter(List<ResultBean.SortBean> classList, Context context,int res,ColorStateList color) {
        this.classList = classList;
        this.context = context;
        this.res = res;
        this.color = color;
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ClassViewHolder holder = new ClassViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_multiple, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, final int position) {
        holder.tv_multiple.setText(classList.get(position).sortTypeDesc);
        holder.tv_multiple.setSelected(classList.get(position).isSelected);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(position);
                notifyDataSetChanged();
                click.click(position);
            }
        });
        if (res != -1){
            holder.iv_selected.setImageResource(res);
        }
        if (color != null){
            holder.tv_multiple.setTextColor(color);
        }
        if (classList.get(position).isSelected){
            holder.iv_selected.setVisibility(View.VISIBLE);
        }
        else {
            holder.iv_selected.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_selected;
        public TextView tv_multiple;
        public RelativeLayout rl_item;

        public ClassViewHolder(View view) {
            super(view);
            iv_selected = (ImageView) view.findViewById(R.id.iv_selected);
            tv_multiple = (TextView) view.findViewById(R.id.tv_multiple);
            rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
        }
    }

    public void setSelected(int position){
        for (int i=0;i<classList.size();i++){
            classList.get(i).isSelected = false;
        }
        classList.get(position).isSelected = true;
    }

    public interface ItemClick{
        void click(int position);
    }

    public ItemClick click;

    public void setItemClick(ItemClick click){
        this.click = click;
    }

}
