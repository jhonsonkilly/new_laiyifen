package com.ody.p2p.views.slidepager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ody.p2p.R;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.recycleviewutils.DecorationSpace;
import com.ody.p2p.recycleviewutils.RecycleUtils;

import java.util.List;

/**
 * Created by lxs on 2016/8/16.
 */
public class RecommendBannerAdapter<T extends BaseRecyclerViewAdapter> extends RecyclingPagerAdapter{
    private Context context;
    private int size;
    private boolean isInfiniteLoop;
    private List<T> adapters;
    private int count;
    private int colorId = -1;

    public RecommendBannerAdapter(Context context, List<T> adapters, int count) {
        this.context = context;
        this.adapters = adapters;
        this.count = count;
        this.size = adapters.size();
        isInfiniteLoop = false;
    }

    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : adapters.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.item_recommend, null);
        RecyclerView recycler_product = (RecyclerView) convertView.findViewById(R.id.recycler_product);
        if (colorId != -1){
            recycler_product.setBackgroundResource(R.color.white);
        }
//        recycler_product.setLayoutManager(new GridLayoutManager(context,count));
        recycler_product.setLayoutManager(RecycleUtils.getGridLayoutManager(context,count));
        recycler_product.addItemDecoration(new DecorationSpace(0,5,0,0));
        recycler_product.setAdapter(adapters.get(position%size));
        return convertView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public void setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        notifyDataSetChanged();
    }

    public void notifyDataChanged(){
        if (adapters != null){
            for (int i = 0; i < adapters.size(); i++) {
                adapters.get(i).notifyDataSetChanged();
            }
        }
    }

    public void setBackgroundColorId(int colorId){
        this.colorId = colorId;
    }

}
