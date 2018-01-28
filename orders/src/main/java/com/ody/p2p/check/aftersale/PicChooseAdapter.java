package com.ody.p2p.check.aftersale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class PicChooseAdapter extends android.widget.BaseAdapter{

    public static int MAX_NUM=3;
    private List<String> mData;
    private Context context;
    private OnCameraClick onCameraClick;

    public PicChooseAdapter(Context context){
        this.context=context;
        mData=new ArrayList<>();
    }

    public void addPath(String path){
        mData.add(path);
        notifyDataSetChanged();
    }

    public void setPaths(List<String> paths){
        mData.clear();
        mData.addAll(paths);
        notifyDataSetChanged();
    }


    public List<String> getData(){
        return mData;
    }

    public void setListener(OnCameraClick onCameraClick){
        this.onCameraClick=onCameraClick;
    }

    @Override
    public int getCount() {
        if(mData.size()>=MAX_NUM){
            return MAX_NUM;
        }else{
            return mData.size()+1;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        Holder holder;
        if(view==null){
            holder=new Holder();
            view= LayoutInflater.from(context).inflate(R.layout.item_img,null);
            holder.img= (ImageView) view.findViewById(R.id.img);
            holder.delete= (ImageView) view.findViewById(R.id.delete);
            view.setTag(holder);
        }else{
            holder= (Holder) view.getTag();
        }
        if(parent.getChildCount()==i){
            if (i == mData.size()) {
                holder.delete.setVisibility(View.GONE);
                holder.img.setImageResource(R.drawable.ic_camera);
                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCameraClick.oncameraClickListener(mData.size());
                    }
                });
            } else {
                holder.delete.setVisibility(View.VISIBLE);
                GlideUtil.display(context,mData.get(i)).override(200, 200).into(holder.img);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem(i);
                    }
                });
            }
        }

        return view;
    }

    private void deleteItem(int pos){
        mData.remove(pos);
        notifyDataSetChanged();
    }

    private class Holder{
        private ImageView img;
        private ImageView delete;
    }

    public interface OnCameraClick{
        void oncameraClickListener(int size);
    }
}