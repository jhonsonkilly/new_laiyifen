package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.check.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangmeijuan on 16/6/21.
 */
public class CommentPicAdapter extends android.widget.BaseAdapter {

    public static int MAX_NUM=5;
    private List<String> pics;
    private Context context;

    public CommentPicAdapter(Context context){
        this.context=context;
        pics=new ArrayList<>();
    }

    public void addPath(List<String> paths){
        pics.addAll(paths);
        notifyDataSetChanged();
    }

    public void addSinglepath(String path){
        pics.add(path);
        notifyDataSetChanged();
    }

    public void clear(){
        pics.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(pics.size()>=MAX_NUM){
            return MAX_NUM;
        }else{
            return pics.size()+1;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getPicSize(){
        return pics.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view==null){
            holder=new Holder();
            view= LayoutInflater.from(context).inflate(R.layout.item_order_comment_girdview,null);
            holder.img= (ImageView) view.findViewById(R.id.img);
            view.setTag(holder);
        }else{
            holder= (Holder) view.getTag();
        }
        if (i == pics.size()) {
            holder.img.setImageResource(R.drawable.ic_launcher);
        } else {
            if (pics.get(i).startsWith("http")){
                GlideUtil.display(context,pics.get(i)).into(holder.img);
            }else {
                GlideUtil.display(context,"file://" + pics.get(i)).into(holder.img);
            }
        }
        return view;
    }

    private class Holder{
        ImageView img;
    }
}
