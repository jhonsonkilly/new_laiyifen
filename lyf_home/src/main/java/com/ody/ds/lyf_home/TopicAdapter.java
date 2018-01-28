package com.ody.ds.lyf_home;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ody.p2p.base.BaseAdapter;
import com.ody.p2p.retrofit.adviertisement.Ad;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;

import java.util.List;

/**
 * Created by lxs on 2016/12/6.
 */
public class TopicAdapter extends BaseAdapter{

    public Context context;
    public List<Ad> list;

    public TopicAdapter(Context context, List<Ad> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Ad getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_topic_item, null);
            holder = new ViewHolder();
            holder.iv_topic = (ImageView) view.findViewById(R.id.iv_topic);
            holder.tv_topic_title = (TextView) view.findViewById(R.id.tv_topic_title);
            holder.tv_topic_content = (TextView) view.findViewById(R.id.tv_topic_content);
            convertView = view;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Ad bean = list.get(position);
//        holder.tv_topic_title.setText(bean.title);
//        holder.tv_topic_content.setText(bean.name);
        GlideUtil.display(context,bean.imageUrl).into(holder.iv_topic);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.toActivity(getItem(position));
            }
        });
        return convertView;
    }


    public class ViewHolder {
        public ImageView iv_topic;
        public TextView tv_topic_title;
        public TextView tv_topic_content;
    }

}
