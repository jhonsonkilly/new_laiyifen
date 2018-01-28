package com.odysaxx.photograph.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lling.photopicker.R;
import com.odysaxx.photograph.utils.ImageLoader;
import com.odysaxx.photograph.utils.OtherUtils;

import java.util.List;

/**
 * Created by ody on 2016/6/8.
 */
public class GridAdapter extends BaseAdapter {
    private List<String> pathList;
    Context context;
    int screenWidth;
    private int mColumnWidth;

    public GridAdapter(Context context,List<String> listUrls) {
        this.context=context;
        screenWidth = OtherUtils.getWidthInPx(context);
        mColumnWidth=  (screenWidth - OtherUtils.dip2px(context, 4))/3;
        this.pathList = listUrls;
    }

    @Override
    public int getCount() {
        return pathList.size();
    }

    @Override
    public String getItem(int position) {
        return pathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            convertView =   LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, null);
            imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mColumnWidth, mColumnWidth);
            imageView.setLayoutParams(params);
        }else {
            imageView = (ImageView) convertView.getTag();
        }
        ImageLoader.getInstance().display(getItem(position), imageView, mColumnWidth, mColumnWidth);
        return convertView;
    }
}
