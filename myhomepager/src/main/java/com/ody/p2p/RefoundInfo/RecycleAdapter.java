package com.ody.p2p.RefoundInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by ody on 2016/7/22.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.viewhodler> {
    List<String> mData;
    Context mContext;

    public RecycleAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public viewhodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewhodler(LayoutInflater.from(mContext).inflate(R.layout.myhomepager_item_recycl_image, parent, false));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(viewhodler holder, int position) {
        if(mData.get(position)!=null){
            GlideUtil.display(mContext, mData.get(position)).override(50, 50).into(holder.item_imge);
        }
    }

    class viewhodler extends RecyclerView.ViewHolder {
        ImageView item_imge;

        public viewhodler(View itemView) {
            super(itemView);
            item_imge = (ImageView) itemView.findViewById(R.id.item_imge);
        }
    }
}
