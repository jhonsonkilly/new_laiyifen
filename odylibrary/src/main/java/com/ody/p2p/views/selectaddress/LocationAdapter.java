package com.ody.p2p.views.selectaddress;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by ody on 2016/6/14.
 */
public class LocationAdapter extends BaseRecyclerViewAdapter<RequestAddressBean.Data> {
    private Context mContext;
    private List<RequestAddressBean.Data> datas;
    private String mSeclected;
    private static int mSelectedColorId = 0;
    private static int mIvResId = R.drawable.icon_selected;

    public LocationAdapter(Context context, List<RequestAddressBean.Data> list) {
        super(context, list);
        mContext = context;
        this.datas = list;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holders, final int position) {
        ViewHolder holder = (ViewHolder) holders;
        holder.iv.setVisibility(View.GONE);
        holder.tv.setText(datas.get(position).name);
        holder.tv.setTextColor(mContext.getResources().getColor(R.color.main_title_color));
        holder.iv.setImageResource(mIvResId);
        if (mSeclected == datas.get(position).name) {
            if(mSelectedColorId == 0){
                holder.tv.setTextColor(mContext.getResources().getColor(R.color.theme_color));
            }else {
                holder.tv.setTextColor(mContext.getResources().getColor(mSelectedColorId));
            }
            holder.iv.setVisibility(View.VISIBLE);
        }
        holder.lay_location_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

    public static void setIvResId(int resId){
        mIvResId = resId;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (mInflater != null){
            View view = mInflater.inflate(R.layout.addressmanage_item_list_location, parent, false);
            holder = new ViewHolder(view);
        }
        return holder;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
    private onItemClickListener itemClickListener;

    public void setOnItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public static void setSelectedTextColor(int colorId){
        mSelectedColorId = colorId;
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tv;
        ImageView iv;
        LinearLayout lay_location_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_location_item);
            iv = (ImageView) itemView.findViewById(R.id.iv_selected);
            lay_location_item = (LinearLayout) itemView.findViewById(R.id.lay_location_item);
        }
    }

    public void setmSeclected(RequestAddressBean.Data seclected) {
        if (seclected == null) {
            mSeclected = null;
        } else {
            mSeclected = seclected.name;
        }
        this.notifyDataSetChanged();

    }

    public void setDatas(List<RequestAddressBean.Data> list) {
        datas = list;
        this.notifyDataSetChanged();
    }


}
