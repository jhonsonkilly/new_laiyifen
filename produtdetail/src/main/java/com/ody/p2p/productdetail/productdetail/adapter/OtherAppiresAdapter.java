package com.ody.p2p.productdetail.productdetail.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.produtdetail.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016-6-19.
 *
 * 更多的商品推荐
 */
public class OtherAppiresAdapter extends BaseRecyclerViewAdapter {
    public OtherAppiresAdapter(Activity mcontext,List list) {
        super(mcontext,list);
    }


    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.img_produtimg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ico_collection));
        viewHodler.txt_produttitle.setText(mContext.getResources().getString(R.string.produttitle));
        viewHodler.txt_produtprice.setText(mContext.getResources().getString(R.string.price));
    }

    @Override
    protected OtherAppiresAdapter.ViewHodler createViewHold(ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.produtdetail_appires_other_item, parent,false));
    }

    class ViewHodler extends BaseRecyclerViewHolder {
        ImageView img_produtimg;
        TextView txt_produtprice;
        TextView txt_produttitle;

        public ViewHodler(View itemView) {
            super(itemView);
            txt_produttitle = (TextView) itemView.findViewById(R.id.txt_produttitle);
            txt_produtprice = (TextView) itemView.findViewById(R.id.txt_produtprice);
            img_produtimg = (ImageView) itemView.findViewById(R.id.img_produtimg);
        }
    }
}

