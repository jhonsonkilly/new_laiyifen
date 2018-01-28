package com.netease.nim.demo.nearpeople;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.core.NimUIKitImpl;

import java.util.List;

/**
 * @author SevenCheng
 */

public class NearPeopleAdapter extends RecyclerView.Adapter {
    private Context                        mContext;
    private List<NearPeopleModel.DataBean> peopleModels;

    public NearPeopleAdapter(Context context, List<NearPeopleModel.DataBean> peopleModels) {
        this.mContext = context;
        this.peopleModels = peopleModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearpeople, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final NearPeopleModel.DataBean nearPeopleBean = peopleModels.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            Glide.with(mContext).load(nearPeopleBean.getAvatar()).asBitmap().placeholder(NimUIKit.getUserInfoProvider().getDefaultIconResId()).into(viewHolder.avatar);
            viewHolder.name.setText(nearPeopleBean.getUserName());
            viewHolder.distance.setText(nearPeopleBean.getDistance());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NimUIKitImpl.getContactEventListener().onItemClick(mContext, nearPeopleBean.getUserId());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return peopleModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private TextView  name;
        private TextView  distance;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            distance = (TextView) itemView.findViewById(R.id.tv_distance);
        }
    }

}
